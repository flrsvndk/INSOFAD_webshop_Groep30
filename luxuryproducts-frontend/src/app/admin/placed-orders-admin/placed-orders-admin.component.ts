import { Component } from '@angular/core';
import {CurrencyPipe, DatePipe, NgIf} from "@angular/common";
import {ExistingOrder} from "../../models/existing-order.model";
import {OrderService} from "../../services/order.service";
import {UserService} from "../../services/user.service";
import {SidepanelComponent} from "../sidepanel/sidepanel.component";

@Component({
  selector: 'app-placed-orders-admin',
  standalone: true,
    imports: [
        CurrencyPipe,
        DatePipe,
        NgIf,
        SidepanelComponent
    ],
  templateUrl: './placed-orders-admin.component.html',
  styleUrl: './placed-orders-admin.component.scss'
})
export class PlacedOrdersAdminComponent {
    public orders: ExistingOrder[];
    protected admin: boolean;
    public loadedAdmin: boolean = false;
    constructor(private orderService: OrderService, private userService: UserService) {
        this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
            this.admin = value;
            this.loadedAdmin = true;
        });
        console.log(this.admin);
    }

    ngOnInit(): void {
        this.orderService.getAllOrders().subscribe((orders: ExistingOrder[]) => {
            this.orders = orders;
        });
    }

    public isAdmin(): boolean {
        return this.admin;
    }

}
