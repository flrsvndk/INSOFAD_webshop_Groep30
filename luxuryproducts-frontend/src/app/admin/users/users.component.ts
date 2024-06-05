import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {RouterLink} from "@angular/router";
import {User} from "../../models/user.model";
import {SidepanelComponent} from "../sidepanel/sidepanel.component";
import {CurrencyPipe, DatePipe, NgIf} from "@angular/common";
import {CdkTextColumn} from "@angular/cdk/table";

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    RouterLink,
    SidepanelComponent,
    CurrencyPipe,
    DatePipe,
    NgIf,
    CdkTextColumn
  ],
  templateUrl: './users.component.html'
})
export class UsersComponent {

  protected admin: boolean;
  public loadedAdmin: boolean = false;
  public userList: User[];

  constructor(private userService: UserService) {
    this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
      this.admin = value;
      this.loadedAdmin = true;
    });
    this.userService.getAllUsers().subscribe((users: User[]) => {
      this.userList = users;
    });
    console.log(this.admin);
  }
}
