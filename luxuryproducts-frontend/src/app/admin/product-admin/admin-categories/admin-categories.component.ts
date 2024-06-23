import {Component, OnInit, TrackByFunction} from '@angular/core';
import {UserService} from "../../../services/user.service";
import {OrderService} from "../../../services/order.service";
import {SidepanelComponent} from "../../sidepanel/sidepanel.component";
import {Category} from "../../../models/category.model";
import {CategoriesService} from "../../../services/categories.service";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-admin-categories',
  standalone: true,
    imports: [
        SidepanelComponent,
        ReactiveFormsModule,
        NgForOf,
        NgIf
    ],
  templateUrl: './admin-categories.component.html',
  styleUrl: './admin-categories.component.scss'
})
export class AdminCategoriesComponent implements OnInit {

    protected admin: boolean;
    public loaded: boolean = false;
    public newCategory: FormGroup;
    public categories: Category[];
    trackByFn: TrackByFunction<Category>;
    constructor(private userService: UserService, private categoryService: CategoriesService, private fb: FormBuilder) {
    }

    public ngOnInit(){
        this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
            this.admin = value;
        });
        console.log(this.admin);
        this.categoryService.getCategories().subscribe((categories: Category[]) => {
            this.categories = categories;
            this.loaded = true;
        });
        this.newCategory = this.fb.group({
            name: ['', Validators.required]
        })
    }

    public onCreateNewCategory(){
        console.log(this.newCategory.get('name')?.value)
        this.categoryService.createCategory(this.newCategory.get('name')?.value as string).subscribe(
            (response: string) => {
                alert("Category Created with id: "+ response);
            }
        );
    }

}
