import { Component } from '@angular/core';
import {UserService} from "../../../services/user.service";
import {OrderService} from "../../../services/order.service";
import {SidepanelComponent} from "../../sidepanel/sidepanel.component";
import {Category} from "../../../models/category.model";
import {CategoriesService} from "../../../services/categories.service";

@Component({
  selector: 'app-admin-categories',
  standalone: true,
    imports: [
        SidepanelComponent
    ],
  templateUrl: './admin-categories.component.html',
  styleUrl: './admin-categories.component.scss'
})
export class AdminCategoriesComponent {

    protected admin: boolean;
    public loaded: boolean = false;
    public categories: Category[];
    constructor(private userService: UserService, private categoryService: CategoriesService) {
        this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
            this.admin = value;
        });
        console.log(this.admin);
        this.categoryService.getCategories().subscribe((categories: Category[]) => {
            this.categories = categories;
            this.loaded = true;
        })
    }

    public createNewCategory(){

    }

}
