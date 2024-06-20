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
  public orderItems: OrderItem[];
  public promocode: Promocode;

  private addOrder$: Subscription;


  constructor(private cartService: CartService,
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
    this.bestelForm = this.fb.group({
      Postcode: [''],
      Huisnummer: ['', [Validators.maxLength(5)]],
      Opmerkingen: [''],
      HouseNummerAddition: [''],
      Opslaan: ['']
    });
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
    }
}
