import {Component, OnDestroy, OnInit} from '@angular/core';

import {CommonModule, CurrencyPipe, DatePipe} from "@angular/common";
import {OrderService} from "../../services/order.service";
import {ExistingOrder} from "../../models/existing-order.model";
import {UserOrderThumbnailComponent} from "./user-order-thumbnail/user-order-thumbnail.component";
import {FormsModule} from "@angular/forms";
import {Subscription} from "rxjs";
import {SortingFilteringService} from "../../services/sorting-filtering.service";

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  standalone: true,
  imports: [
    CommonModule,
    DatePipe,
    CurrencyPipe,
    UserOrderThumbnailComponent,
    FormsModule
  ],
  styleUrls: ['./order-history.component.scss']
})
export class OrderHistoryComponent implements OnInit, OnDestroy {

  public orders: ExistingOrder[];
  public alteredOrders: ExistingOrder[];

  public sortNewestFirst: boolean = true;
  public searchFilter: string = '';

  private orders$: Subscription;

  constructor(private orderService: OrderService,
              private sortFilterService: SortingFilteringService) {
  }

  ngOnInit(): void {
    this.orderService.getOrdersByCurrentUser().subscribe((orders: ExistingOrder[]) => {
      this.orders = orders;
      this.alterOrders();
    });
  }

  ngOnDestroy() {
    this.orders$?.unsubscribe();
  }

  public toggleSort() {
    this.sortNewestFirst = !this.sortNewestFirst;
    this.alterOrders();
  }

  public alterOrders() {
    this.alteredOrders = this.sortFilterService.alterOrders(this.orders, this.searchFilter, this.sortNewestFirst);
  }
}