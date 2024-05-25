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
  private role: String = this.userService.getUserRole();

  constructor(private userService: UserService){}
  public isAdmin(): boolean {
    console.log(this.userService.isAdmin(this.role));
    return !this.userService.isAdmin(this.role);
  }
}
