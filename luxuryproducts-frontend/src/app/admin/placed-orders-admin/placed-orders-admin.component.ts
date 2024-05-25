import { Component } from '@angular/core';
import {CurrencyPipe, DatePipe, NgIf} from "@angular/common";
import {ExistingOrder} from "../../models/existing-order.model";
import {OrderService} from "../../services/order.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-placed-orders-admin',
  standalone: true,
    imports: [
        CurrencyPipe,
        DatePipe,
        NgIf
    ],
  templateUrl: './placed-orders-admin.component.html',
  styleUrl: './placed-orders-admin.component.scss'
})
export class PlacedOrdersAdminComponent {
    orders: ExistingOrder[];
    private role: String = this.userService.getUserRole();

    constructor(private orderService: OrderService, private userService: UserService) {
    }

    ngOnInit(): void {
        this.orderService.getAllOrders().subscribe((orders: ExistingOrder[]) => {
            this.orders = orders;
        });
    }

    public isAdmin(): boolean {
        console.log(this.userService.isAdmin(this.role));
        return !this.userService.isAdmin(this.role);
    }

}
