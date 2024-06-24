import {Component, Input, Output} from '@angular/core';
import {Product} from "../../../models/product.model";
import {CurrencyPipe} from "@angular/common";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {ProductsService} from "../../../services/products.service";

@Component({
  selector: 'app-product-thumbnail',
  standalone: true,
  imports: [
    CurrencyPipe,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './product-thumbnail.component.html'
})
export class ProductThumbnailComponent {
  @Input() product: Product;
  // public imgUrl: string = ;

  constructor(protected productsService: ProductsService) {
  }
}
