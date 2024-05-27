import { Component } from '@angular/core';
import {UserService} from "../services/user.service";
import {User} from "../models/user.model";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {CurrencyPipe, DatePipe, NgIf} from "@angular/common";

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    RouterLinkActive,
    RouterLink,
    CurrencyPipe,
    DatePipe
  ],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent {
  private role: String = this.userService.getUserRole();

  constructor(private userService: UserService){}
  public isAdmin(): boolean {
    console.log(this.userService.isAdmin(this.role));
    return !this.userService.isAdmin(this.role);
  }
}



















