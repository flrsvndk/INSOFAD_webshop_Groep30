import {Component, OnDestroy, OnInit} from '@angular/core';
import { CartService } from "../services/cart.service";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Order } from '../models/order.model';
import {Adress} from "../models/adress.model";
import {OrderItem} from "../models/order-item.model";
import {OrderService} from "../services/order.service";
import {ProductType} from "../models/product-type.model";
import {Promocode} from "../models/promocode.model";

import {Subscription} from "rxjs";
import {GiftcardService} from "../services/giftcard.service";
import {GiftcardOwned} from "../models/giftcard-owned.model";
import {HttpResponse} from "../models/http-response.model";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit, OnDestroy {
  public bestelForm: FormGroup;
  public products_in_cart: ProductType[];
  public order: Order;
  public adress: Adress;
  public totalPrice: number;
  public orderItems: OrderItem[];
  public promocode: Promocode;

  private addOrder$: Subscription;

  constructor(private giftcardService: GiftcardService,
              private userService: UserService,
              private cartService: CartService,
              private router: Router,
              private fb: FormBuilder,
              private orderService: OrderService,) {
      const navigation = this.router.getCurrentNavigation();
      if (navigation?.extras.state) {
          const state = navigation.extras.state as {
              promocode: Promocode;
          };
          this.promocode = state.promocode;}
  }

  ngOnInit(): void {
    this.products_in_cart = this.cartService.allProductsInCart();
    // this.currentLoggedInUser = this.userService.getUserFromBearer(localStorage.getItem());
    this.bestelForm = this.fb.group({
      Postcode: [''],
      Huisnummer: ['', [Validators.maxLength(5)]],
      Opmerkingen: [''],
      HouseNummerAddition: [''],
      Opslaan: [''],
      Giftcards: ['']
    });
    this.totalPrice = this.products_in_cart.reduce((total, product) => total + product.price * product.amount, 0);
  }

  ngOnDestroy() {
    this.addOrder$.unsubscribe();
  }

  public clearCart() {
    this.cartService.clearCart();
  }

  public onSubmit() {
        const formData = this.bestelForm.value;

        this.orderItems = this.orderService.createOrderItems(this.products_in_cart);

        this.order = {
            notes: formData.Opmerkingen,

            adressDTO : this.adress = {
                zipcode: formData.Postcode,
                houseNumber: formData.Huisnummer,
                houseNumberAddition: formData.HouseNummerAddition,
                save: formData.Opslaan,
            },
            orderItems: this.orderItems,
            promocode: this.promocode
        };

        this.addOrder$ = this.cartService.addOrder(this.order).subscribe(
            (result) => {
                console.log('Order added successfully:', result);
                this.clearCart();
                this.router.navigateByUrl('/paymentsuccessful');
                },
            (error) => {
                console.error('Failed to add order:', error);
            }
            );
        this.cartService.addOrder(this.order).subscribe(
          (result) => {
            console.log('Order added successfully:', result);
            this.clearCart();
            this.router.navigateByUrl('/paymentsuccessful');
          },
          (error) => {
            console.error('Failed to add order:', error);
          }
        );

        if (formData.Giftcards != "") {
          this.useGiftcards(formData.Giftcards);
        }
  }

  public useGiftcards(giftcardsText: String) {
    let unpaid = this.totalPrice;
    let giftcardIds: String[] = giftcardsText.split(" ");
    let giftcards: GiftcardOwned[] = this.giftcardService.ownedGiftcards;


    for (let giftcard of giftcards) {
      for (let id of giftcardIds) {
        if (id == giftcard.id.toString()) {
          console.log(giftcard);
          let unpaidOld = unpaid;
          unpaid -= Math.min(unpaid, giftcard.value);
          giftcard.value -= Math.min(unpaidOld, giftcard.value);
          this.giftcardService.lowerGiftcardValue(giftcard.value, giftcard.id).subscribe(
              (result: HttpResponse) => {
                console.log('Giftcard value updated successfully:', result);
                this.router.navigateByUrl('/paymentsuccessful');
              },
              (error) => {
                console.log(error);
              }
          );
          break;
        }
      }
      if (unpaid == 0) {
        return;
      }
    }
  }
}
