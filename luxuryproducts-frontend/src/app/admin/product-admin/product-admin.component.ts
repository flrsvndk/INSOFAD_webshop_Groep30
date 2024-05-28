import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-product-admin',
  standalone: true,
  imports: [],
  templateUrl: './product-admin.component.html',
  styleUrl: './product-admin.component.scss'
})
export class ProductAdminComponent {

  constructor(private userService: UserService){}
  public isAdmin(): boolean {
    console.log(this.userService.isAdmin());
    return !this.userService.isAdmin();
  }
}
