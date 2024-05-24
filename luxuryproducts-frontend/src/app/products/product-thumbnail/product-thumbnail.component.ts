import {Component, EventEmitter, Input, Output} from '@angular/core';

import {Product} from '../../models/product.model';
import {ProductsService} from "../../services/products.service";

@Component({
  selector: 'app-product-thumbnail',
  templateUrl: './product-thumbnail.component.html',
  styleUrl: './product-thumbnail.component.scss'
})
export class ProductThumbnailComponent {
  @Input() public product!: Product;
  @Output() public onBuyProduct: EventEmitter<Product> = new EventEmitter<Product>();

  constructor(
      public productsService: ProductsService,
  ) {
  }

  public buyProduct(product: Product) {
    this.onBuyProduct.emit(product);
  }
}
