import {Component, Input, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Product} from "../../../models/product.model";
import {ProductSpecification} from "../../../models/product-specification.model";
import {ProductType} from "../../../models/product-type.model";
import {UserService} from "../../../services/user.service";
import {ProductsService} from "../../../services/products.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthResponse} from "../../../auth/auth-response.model";
import {SidepanelComponent} from "../../sidepanel/sidepanel.component";
import {NgForOf, NgIf} from "@angular/common";
import {Category} from "../../../models/category.model";
import {CategoriesService} from "../../../services/categories.service";

@Component({
  selector: 'app-update-product',
  standalone: true,
    imports: [
        FormsModule,
        SidepanelComponent,
        NgIf,
        NgForOf
    ],
  templateUrl: './update-product.component.html',
  styleUrl: './update-product.component.scss'
})
export class UpdateProductComponent implements OnInit{

    public productId: string;
    public product: Product;

    protected admin: boolean;
    public loadedAdmin: boolean = false;
    public categories: Category[];

    constructor(
        private activatedRoute: ActivatedRoute,
        private userService: UserService,
        private productService: ProductsService,
        private router: Router,
        private categoriesService: CategoriesService) {
    }


    public ngOnInit(): void {
        this.activatedRoute.params.subscribe(params => {
            this.productId = params['id'];
        });

        this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
          this.admin = value;
          this.loadedAdmin = true;
        });
        console.log(this.admin);
        this.productService.getProductById(this.productId)
            .subscribe((product: Product) =>{
            this.product = product;
        });
        this.categoriesService.getCategories()
            .subscribe((categories: Category[]) => {
            this.categories = categories
        });
    }

    public onSubmit(){
        console.log(this.product);
        this.productService.updateProductByIndex(this.product)
            .subscribe((response: Product) => {
            console.log(response);
        })
    }
}
