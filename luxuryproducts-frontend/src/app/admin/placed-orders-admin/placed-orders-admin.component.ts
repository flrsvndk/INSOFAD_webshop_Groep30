import {Component, OnDestroy, OnInit} from '@angular/core';
import {CurrencyPipe, DatePipe, NgIf} from "@angular/common";
import {ExistingOrder} from "../../models/existing-order.model";
import {OrderService} from "../../services/order.service";
import {UserService} from "../../services/user.service";
import {SidepanelComponent} from "../sidepanel/sidepanel.component";
import {AdminOrderThumbnailComponent} from "./admin-order-thumbnail/admin-order-thumbnail.component";
import {FormsModule} from "@angular/forms";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-placed-orders-admin',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe,
    NgIf,
    SidepanelComponent,
    AdminOrderThumbnailComponent,
    FormsModule,
  ],
  templateUrl: './placed-orders-admin.component.html',
  styleUrl: './placed-orders-admin.component.scss'
})
export class PlacedOrdersAdminComponent implements OnInit, OnDestroy {

    public orders: ExistingOrder[];
    public alteredOrders: ExistingOrder[];

    public sortNewestFirst: boolean = true;
    public searchFilter: string = '';

    public loadedAdmin: boolean = false;

    private orders$: Subscription;

    protected admin: boolean;


    constructor(private orderService: OrderService, private userService: UserService) {
         this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
            this.admin = value;
            this.loadedAdmin = true;
        });
    }

    ngOnInit(): void {
        this.orders$ = this.orderService.getAllOrders().subscribe((orders: ExistingOrder[]) => {
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

      // filter on order id
      this.alteredOrders = this.searchFilter ? this.orders.filter(order => order.id == this.searchFilter) : this.orders.slice();

      // Sort by age
      this.alteredOrders = this.alteredOrders
        .filter(order => order.orderDate !== undefined)
        .sort((a, b) => {
          const dateA = new Date(a.orderDate!);
          const dateB = new Date(b.orderDate!);

          if (this.sortNewestFirst) {
            return dateB.getTime() - dateA.getTime();
          } else {
            return dateA.getTime() - dateB.getTime();
          }
        });
    }

  // Deze method is unused.

    // public isAdmin(): boolean {
    //     return this.admin;
    // }
}
