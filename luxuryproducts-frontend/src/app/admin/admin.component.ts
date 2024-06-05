import { Component } from '@angular/core';
import {UserService} from "../services/user.service";
import {User} from "../models/user.model";
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {CurrencyPipe, DatePipe, NgIf} from "@angular/common";
import {UsersComponent} from "./users/users.component";
import {PlacedOrdersAdminComponent} from "./placed-orders-admin/placed-orders-admin.component";
import {SidepanelComponent} from "./sidepanel/sidepanel.component";

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    RouterLinkActive,
    RouterLink,
    CurrencyPipe,
    DatePipe,
    UsersComponent,
    PlacedOrdersAdminComponent,
    SidepanelComponent,
    RouterOutlet
  ],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent {

  protected admin: boolean;
  public loadedAdmin: boolean = false;

  constructor(private userService: UserService) {
    this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
      this.admin = value;
      this.loadedAdmin = true;
    });
    console.log(this.admin);
  }



}



















