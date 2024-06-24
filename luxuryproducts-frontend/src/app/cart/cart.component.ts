import {Component, OnDestroy, OnInit} from '@angular/core';
import {CurrencyPipe, NgFor, NgIf} from '@angular/common';
import {CartService} from '../services/cart.service';
import {Router} from '@angular/router';
import {AuthService} from "../auth/auth.service";
import {ProductType} from "../models/product-type.model";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Promocode} from "../models/promocode.model";
import {PromocodeService} from "../services/promocode.service";
import {Subscription} from "rxjs";
import {GiftcardService} from "../services/giftcard.service";
import {Giftcard} from "../models/giftcard.model";

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CurrencyPipe, NgFor, NgIf, ReactiveFormsModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent implements OnInit, OnDestroy {
  public products_in_cart: ProductType[];
  public userIsLoggedIn: boolean = false;
  public amountOfProducts: number = 0;

  public promocodeForm: FormGroup;
  public promocodes: Promocode[];
  private existingPromocode: Promocode;
  protected usedPromocode: Promocode;
  protected promocodeError: boolean;

  public giftcardForm: FormGroup;
  public giftcardString: string = "";

  private productsInCart$: Subscription;
  private loginState$: Subscription;

  constructor(private cartService: CartService,
              private router: Router,
              private authService: AuthService,
              private fb: FormBuilder,
              private promocodeService: PromocodeService,
              private giftcardService: GiftcardService) {

  }

  ngOnInit() {
    this.products_in_cart = this.cartService.allProductsInCart();
    this.productsInCart$ = this.cartService.$productInCart.subscribe((products: ProductType[]) => {
      this.products_in_cart = products;
      this.amountOfProducts = products.length;
      this.checkLoginState();
    });

    this.promocodeForm = this.fb.group({
      Promocode: ['', [Validators.required]]
    });
    this.giftcardForm = this.fb.group({
      Giftcard: ['']
    });

    this.promocodeService.getPromocodes().subscribe((promocodes: Promocode[]) => {
      this.promocodes = promocodes;
    });
  }

  ngOnDestroy(): void {
    this.productsInCart$?.unsubscribe();
    this.loginState$?.unsubscribe();
  }
  public clearCart() {
    this.cartService.clearCart();
  }

  public removeProductFromCart(product_index: number) {
    this.cartService.removeProductFromCart(product_index);
  }

  public getTotalPriceWithoutPromocodeAndGiftcards(): number {
    return this.products_in_cart.reduce(
        (total , product) =>
            total + product.price * product.amount, 0
    );
  }

  public getTotalPriceWithPromocode(): number {
    if (this.usedPromocode) {
      return this.getTotalPriceWithoutPromocodeAndGiftcards() * (1 - (this.usedPromocode.percentageOff / 100));
    } else {
      return this.getTotalPriceWithoutPromocodeAndGiftcards();
    }
  }

  public getTotalPrice() {
    if (this.usedPromocode) {
      return this.getTotalPriceWithoutPromocodeAndGiftcards() * (1 - (this.usedPromocode.percentageOff / 100));
    } else {
      return this.giftcardService.getPriceWithGiftcards(this.giftcardString, this.getTotalPriceWithoutPromocodeAndGiftcards());
    }
  }

  onInvalidOrder(){
    return this.amountOfProducts === 0;
  }

  onOrder() {
    if (!this.userIsLoggedIn){
      this.router.navigateByUrl('/auth/login');
    }
    else {
      this.router.navigate(['/orders'],
          {state: {
              totalPriceBeforePromocodeAndGiftcards: this.getTotalPriceWithoutPromocodeAndGiftcards(),
              totalPriceWithPromocode: this.getTotalPriceWithPromocode(),
              totalPrice: this.getTotalPrice(),
              giftcardString: this.giftcardString,
              promocode: this.usedPromocode}});
            }
  }

  public checkLoginState(): void {
    this.loginState$ = this.authService
        .$userIsLoggedIn
        .subscribe((loginState: boolean) => {
          this.userIsLoggedIn = loginState;
        });
  }

  public onNameInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    input.value = input.value.toUpperCase();
    this.promocodeForm.get('promocode')?.setValue(input.value, {emitEvent: false});
  }

  public onGiftcardsUpdate(event: Event) {
    const input = event.target as HTMLInputElement;
    this.giftcardString = input.value.toUpperCase();
    console.log(this.giftcardString);
  }

  protected onSubmitPromocode() {
    return this.validatePromocode();
  }

  private validatePromocode(): boolean {
    const potentialPromocodeName = this.promocodeForm.get('Promocode')?.value ?? '';

    if (this.checkIfInputEmpty(potentialPromocodeName)) {
      return false;
    }

    if (!this.checkIfPromocodeExists(potentialPromocodeName)) {
      this.promocodeError = true;
      return false;
    }

    if (!this.checkIfPromocodeIsAvailable()) {
      this.promocodeError = true;
      return false
    }

    if (this.checkIfDedicatedPromocode() &&
        !this.checkIfDedicatedEmailMatches(localStorage.getItem('userEmail') || '')) {
      this.promocodeError = true;
      return false;
    }

    if (!this.checkIfPromocodeUsageLimitReached()) {
      this.promocodeError = true;
      return false;
    }

    this.promocodeError = false;
    this.usedPromocode = this.existingPromocode;
    return true;
  }

  private checkIfInputEmpty(potentialPromocodeName: String): boolean {
    return potentialPromocodeName.length < 1;
  }

  private checkIfPromocodeExists(potentialPromocodeName: String): boolean {
    for (const existingPromocode of this.promocodes) {
      if (existingPromocode.name == potentialPromocodeName) {
        this.existingPromocode = existingPromocode;
        return true;
      }
    }
    return false;
  }

  private checkIfPromocodeIsAvailable(): boolean {
    return this.existingPromocode.available;
  }

  private checkIfDedicatedPromocode(): boolean {
    return this.existingPromocode.dedicatedPromocode;
  }

  private checkIfDedicatedEmailMatches(userEmail: String): boolean {
    return userEmail == this.existingPromocode.dedicatedUserEmail;
  }

  private checkIfPromocodeUsageLimitReached(): boolean {
    return this.existingPromocode.usages < this.existingPromocode.maxUsages;
  }
}
