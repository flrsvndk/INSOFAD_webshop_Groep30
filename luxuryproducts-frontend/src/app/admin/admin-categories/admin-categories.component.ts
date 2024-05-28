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

  constructor(private userService: UserService){}
  public isAdmin(): boolean {
    console.log(this.userService.isAdmin());
    return !this.userService.isAdmin();
  }
}
