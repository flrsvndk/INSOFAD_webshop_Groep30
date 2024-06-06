import { Component, OnInit } from '@angular/core';

import {CommonModule, CurrencyPipe, DatePipe} from "@angular/common";
import {OrderService} from "../../services/order.service";
import {ExistingOrder} from "../../models/existing-order.model";
import {UserOrderThumbnailComponent} from "./user-order-thumbnail/user-order-thumbnail.component";

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  standalone: true,
  imports: [
    CommonModule,
    DatePipe,
    CurrencyPipe,
    UserOrderThumbnailComponent
  ],
  styleUrls: ['./order-history.component.scss']
})
export class OrderHistoryComponent implements OnInit {
  orders: ExistingOrder[];

  constructor(private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.orderService.getOrdersByCurrentUser().subscribe((orders: ExistingOrder[]) => {
      this.orders = orders;
    });
  }
}

// import { Component, OnInit } from '@angular/core';
// import { OrderService } from '../../services/order.service'; // Pas dit pad aan naar waar je service zich bevindt
// import { Order } from '../../models/order.model'; // Pas dit pad aan naar waar je model zich bevindt
// import { CommonModule } from '@angular/common';
// import {ExistingOrder} from "../../models/existing-order.model";
// import {of} from "rxjs";
// import {OrderItem} from "../../models/order-item.model";
//
//
// @Component({
//   selector: 'app-order-history',
//   templateUrl: './order-history.component.html',
//   imports: [CommonModule],
//   standalone: true,
//   styleUrls: ['./order-history.component.scss']
// })
// export class OrderHistoryComponent implements OnInit {
//   orders: ExistingOrder[] = [];
//
//   constructor(private orderService: OrderService) { }
//
//   ngOnInit() {
//     this.loadOrders();
//   }
//
//   loadOrders() {
//     this.orderService
//         .getOrdersByCurrentUser()
//         .subscribe(orders => {
//           this.orders = orders;
//         });
//   }
//
//   // calculateTotal(products: any[]): number {
//   //   let total = 0;
//   //   for (const product of products) {
//   //     total += product.price;
//   //   }
//   //   return Number(total.toFixed(2));
//   // }
//     protected readonly OrderItem = OrderItem;
// }
