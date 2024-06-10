import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import {ExistingOrder} from "../models/existing-order.model";
import {Product} from "../models/product.model";
import {OrderItem} from "../models/order-item.model";
import {ProductType} from "../models/product-type.model";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private baseUrl: string = environment.base_url + "/orders";

  constructor(private http: HttpClient) { }

  // createOrderItems(products: )

  getOrdersByCurrentUser(): Observable<ExistingOrder[]> {
    return this.http.get<ExistingOrder[]>(this.baseUrl + "/myOrders");
  }

  getAllOrders(): Observable<ExistingOrder[]> {
    return this.http.get<ExistingOrder[]>(this.baseUrl + "/all")
  }

  createOrderItems(products_in_cart: ProductType[]) {
    let orderItems: OrderItem[] = [];
    for (let product  of products_in_cart){
      let orderItem : OrderItem = new OrderItem(product.amount, product.id, product);
      orderItems.push(orderItem);
    }
    return orderItems;
  }

  public setProcessing(id: string): Observable<ExistingOrder> {
    return this.http.put<ExistingOrder>(this.baseUrl + "/processing", {
      id: id
    });
  }

  public setConfirmed(id: string): Observable<ExistingOrder> {
    return this.http.put<ExistingOrder>(this.baseUrl + "/confirmed", {
      id: id
    });
  }

  public setOutForDelivery(id: string): Observable<ExistingOrder> {
    return this.http.put<ExistingOrder>(this.baseUrl + "/out-for-delivery", {
      id: id
    });
  }

  public setDelivered(id: string): Observable<ExistingOrder> {
    return this.http.put<ExistingOrder>(this.baseUrl + "/delivered", {
      id: id
    });
  }
}
