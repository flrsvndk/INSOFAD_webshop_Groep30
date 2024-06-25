import {Injectable} from '@angular/core';
import {BehaviorSubject, catchError, Observable, throwError} from 'rxjs';
import {Product} from '../models/product.model';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Order} from '../models/order.model';
import {ProductType} from "../models/product-type.model";
import {ProductsService} from "./products.service";


const localStorageKey: string = "products-in-cart";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private productsInCart: ProductType[] = [];
  public $productInCart: BehaviorSubject<ProductType[]> = new BehaviorSubject<ProductType[]>([]);
  private baseUrl: string = environment.BASE_URL + "/orders";

  constructor(private http: HttpClient, private productService: ProductsService) {
    this.loadProductsFromLocalStorage();
  }

  public productName(productType: ProductType) : Observable<Product> {
    return this.productService.getProductById(productType.productId);
  }

  public addProductToCart(productType: ProductType) {
    let existingProductIndex: number = this.productsInCart.findIndex(product => product.id === productType.id);

    if (existingProductIndex !== -1) {
      if(productType.stock > productType.amount) {
        this.productsInCart[existingProductIndex].amount += 1;
      }
    } else {
        productType.amount = 1;
        this.productsInCart.push(productType);
    }

    this.saveProductsAndNotifyChange();
  }

  public removeProductFromCart(productIndex: number) {
    if (this.productsInCart[productIndex].amount > 1) {
      this.productsInCart[productIndex].amount -= 1;
    } else {
      this.productsInCart.splice(productIndex, 1);
    }

    this.saveProductsAndNotifyChange();
  }

  public clearCart() {
    this.productsInCart = [];
    this.saveProductsAndNotifyChange();
  }

  public allProductsInCart(): ProductType[] {
    return this.productsInCart.slice();
  }


  public addOrder(order: Order): Observable<Order> {
      console.log("Ontvangen order: " + order);

     return this.http.post<Order>(this.baseUrl, order).pipe(
        catchError(error => {
          console.error('Error adding order:', error);
          return throwError(error);
        })
     );
  }


  private saveProductsAndNotifyChange(): void {
    this.saveProductsToLocalStorage(this.productsInCart.slice());
    this.$productInCart.next(this.productsInCart.slice());
  }

  private saveProductsToLocalStorage(products: ProductType[]): void {
    localStorage.setItem(localStorageKey, JSON.stringify(products));
  }

  private loadProductsFromLocalStorage(): void {
    let productsOrNull = localStorage.getItem(localStorageKey);
    if (productsOrNull != null) {
      this.productsInCart = JSON.parse(productsOrNull);
      this.$productInCart.next(this.productsInCart.slice());
    }
  }
}
