import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Product} from '../../models/product.model';
import {ProductsService} from '../../services/products.service';
import {CartService} from '../../services/cart.service';
import {ProductType} from "../../models/product-type.model";


@Component({
  selector: 'app-product-detail',
  standalone: false,
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.scss'

})
export class ProductDetailComponent {
  @Input() public product!: Product;
  private productId: number;
  public chosenType: ProductType;
  public currentType: ProductType;
  public img: string;
  public type1Index: number = 0;
  public type2Index: number = 0;

  constructor(
    private activatedRoute: ActivatedRoute,
    public productsService: ProductsService,
    private cartService: CartService
  ) {
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.productId = params['id'];
    });

    this.productsService
      .getProductByIndex(this.productId)
      .subscribe((product: Product) => {
        this.product = product;
      });
  }

  public selectType(productType: ProductType, index: number){
    this.chosenType = productType;
    this.type1Index = index;
    this.img = this.chosenType.imgUrl;
    console.log(this.chosenType.name);
  }

  public selectSubType(productType: ProductType, index: number){
    this.currentType = productType;
    this.type2Index = index;
    this.img = this.chosenType.imgUrl;
    console.log(this.currentType.name + " " + this.chosenType.name);
  }


  public canOrder(): boolean{
    if(this.chosenType == null){
      return false;
    } else{
      return true;
    }
  }

  public onBuyProduct(product: Product) {
    if(this.currentType == null){
      if(product.productSpecification.types.includes(this.chosenType)) {
        this.cartService.addProductToCart(this.chosenType);
      }
    }
    if(product.productSpecification.types.includes(this.chosenType)) {
      this.cartService.addProductToCart(this.currentType);
    }
  }



}
