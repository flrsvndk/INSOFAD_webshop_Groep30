import { Component, OnInit } from '@angular/core';
import { CartService } from "../services/cart.service";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import { Router } from "@angular/router";
import { Product } from '../models/product.model';
import { Order } from '../models/order.model';
import {User} from "../models/user.model";
import {Adress} from "../models/adress.model";
import {OrderItem} from "../models/order-item.model";
import {OrderService} from "../services/order.service";
import {UserService} from "../services/user.service";
import {ProductType} from "../models/product-type.model";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {
  public bestelForm: FormGroup;
  public products_in_cart: ProductType[];
  public order: Order;
  public adress: Adress;
  public orderItems: OrderItem[]

  constructor(private userService: UserService,private cartService: CartService, private router: Router, private fb: FormBuilder, private orderServive: OrderService) {}

  ngOnInit(): void {
    this.products_in_cart = this.cartService.allProductsInCart();
    // this.currentLoggedInUser = this.userService.getUserFromBearer(localStorage.getItem());
    this.bestelForm = this.fb.group({
      Postcode: [''],
      Huisnummer: ['', [Validators.maxLength(5)]],
      Opmerkingen: [''],
      HouseNummerAddition: [''],
      Opslaan: ['']
    });
  }

  public clearCart() {
    this.cartService.clearCart();
  }

  public onSubmit() {
      const formData = this.bestelForm.value;

      this.orderItems = this.orderServive.createOrderItems(this.products_in_cart);

      this.order = {
        notes: formData.Opmerkingen,

        adressDTO : this.adress = {
          zipcode: formData.Postcode,
          houseNumber: formData.Huisnummer,
          houseNumberAddition: formData.HouseNummerAddition,
          save: formData.Opslaan
        },
        orderItems: this.orderItems
      };

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

  }
}
