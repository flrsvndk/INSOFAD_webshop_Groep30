import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-admin-categories',
  standalone: true,
  imports: [],
  templateUrl: './admin-categories.component.html',
  styleUrl: './admin-categories.component.scss'
})
export class AdminCategoriesComponent {
  private role: String = this.userService.getUserRole();

  constructor(private userService: UserService){}
  public isAdmin(): boolean {
    console.log(this.userService.isAdmin(this.role));
    return !this.userService.isAdmin(this.role);
  }
}
