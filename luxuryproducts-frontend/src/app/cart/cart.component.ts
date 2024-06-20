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

  private productsInCart$: Subscription;
  private loginState$: Subscription;

  constructor(private cartService: CartService,
              private router: Router,
              private authService: AuthService,
              private fb: FormBuilder,
              private promocodeService: PromocodeService) {

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

    this.promocodeService.getPromocodes().subscribe((promocodes: Promocode[]) => {
      this.promocodes = promocodes;
    });
  }

  ngOnDestroy(): void {
    if (this.productsInCart$) {
      this.productsInCart$.unsubscribe();
    }
    if (this.loginState$) {
      this.loginState$.unsubscribe();
    }
  }

  public clearCart() {
    this.cartService.clearCart();
  }

  public removeProductFromCart(product_index: number) {
    this.cartService.removeProductFromCart(product_index);
  }

  public getTotalPriceWithoutPromocode(): number {
    return this.products_in_cart.reduce(
        (total , product) =>
            total + product.price * product.amount, 0
    );
  }

  public getTotalPriceWithPromocode(): number {
    if (this.usedPromocode) {
      return this.getTotalPriceWithoutPromocode() * (1 - (this.usedPromocode.percentageOff / 100));
    } else {
      return this.getTotalPriceWithoutPromocode();
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
              totalPriceBeforePromocode: this.getTotalPriceWithoutPromocode(),
              totalPriceAfterPromocode: this.getTotalPriceWithPromocode(),
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
