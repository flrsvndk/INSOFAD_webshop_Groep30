import {Component, OnDestroy, OnInit} from '@angular/core';
import { CartService } from "../services/cart.service";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
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
        ReactiveFormsModule,
        FormsModule
    ],
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit, OnDestroy {
    public bestelForm: FormGroup;
    public products_in_cart: ProductType[];
    public order: Order;
    public adress: Adress;
    public totalPriceBeforePromocodeAndGiftcards: number;
    public totalPriceWithPromocode: number;
    public totalPrice: number;
    public orderItems: OrderItem[];
    public promocode: Promocode;
    public giftcardString: string;

    private addOrder$: Subscription;

    constructor(private giftcardService: GiftcardService,
                private userService: UserService,
                private cartService: CartService,
                private router: Router,
                private fb: FormBuilder,
                private orderService: OrderService,
                private route: ActivatedRoute) {
        const navigation = this.router.getCurrentNavigation();
        if (navigation?.extras.state) {
            const state = navigation.extras.state as {
                totalPriceBeforePromocodeAndGiftcards: number;
                totalPriceWithPromocode: number;
                totalPrice: number;
                giftcardString: string;
                promocode: Promocode;
            };
            this.totalPriceBeforePromocodeAndGiftcards = state.totalPriceBeforePromocodeAndGiftcards;
            this.totalPriceWithPromocode = state.totalPriceWithPromocode;
            this.totalPrice = state.totalPrice;
            this.giftcardString = state.giftcardString;
            this.promocode = state.promocode;
        }
    }

    ngOnInit(): void {
        this.products_in_cart = this.cartService.allProductsInCart();
        this.bestelForm = this.fb.group({
            zipCode: [''],
            houseNumber: ['', [Validators.maxLength(5)]],
            Opmerkingen: [''],
            houseNumberAddition: [''],
            save: ['']
        });
    }

    ngOnDestroy() {
        this.addOrder$?.unsubscribe();
    }

    public clearCart() {
        this.cartService.clearCart();
    }

    public onSubmit() {
        const formData = this.bestelForm.value;

        this.orderItems = this.orderService.createOrderItems(this.products_in_cart);

        this.order = {
            notes: formData.Opmerkingen,

            adressDTO: this.adress = {
                zipCode: formData.zipCode,
                houseNumber: formData.houseNumber,
                houseNumberAddition: formData.houseNumberAddition,
                save: formData.save,
            },
            orderItems: this.orderItems,
            promocode: this.promocode
        };

        this.addOrder$ = this.cartService.addOrder(this.order)
            .subscribe(
            (result) => {
                console.log('Order added successfully:', result);
                this.clearCart();
                this.router.navigateByUrl('/paymentsuccessful');
            }
        );

        console.log(this.giftcardString);
        if (this.giftcardString != null && this.giftcardString != "") {
            this.giftcardService.useGiftcards(this.giftcardString, this.totalPriceWithPromocode);
        }
    }
}