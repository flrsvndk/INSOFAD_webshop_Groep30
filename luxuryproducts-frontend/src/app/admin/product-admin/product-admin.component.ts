import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {RouterLink} from "@angular/router";
import {SidepanelComponent} from "../sidepanel/sidepanel.component";
import {ProductThumbnailComponent} from "./product-thumbnail/product-thumbnail.component";
import {Product} from "../../models/product.model";
import {ProductsService} from "../../services/products.service";

@Component({
  selector: 'app-product-admin',
  standalone: true,
    imports: [
        RouterLink,
        SidepanelComponent,
        ProductThumbnailComponent
    ],
  templateUrl: './product-admin.component.html',
  styleUrl: './product-admin.component.scss'
})
export class ProductAdminComponent {

    protected admin: boolean;
    public loadedAdmin: boolean = false;
    public allProducts: Product[];

    constructor(private userService: UserService, private productsService: ProductsService) {
        this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
            this.admin = value;
            this.loadedAdmin = true;
        });
        console.log(this.admin);
        this.productsService.getProducts().subscribe((products: Product[])=>{
            this.allProducts = products;
        });
    }
}
