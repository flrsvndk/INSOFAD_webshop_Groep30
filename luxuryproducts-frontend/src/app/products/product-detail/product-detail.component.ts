import {Component, Input} from '@angular/core';
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
  private productId: string;
  public chosenType1: ProductType;
  public chosenType2: ProductType;
  public img: string;
  public typeIndex1: number = 0;
  public typeIndex2: number = 0;

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
      .getProductById(this.productId)
      .subscribe((product: Product) => {
        this.product = product;
      });
  }

  public selectType(productType: ProductType, index: number){
    this.chosenType1 = productType;
    this.typeIndex1 = index;
    this.img = this.chosenType1.imgUrl;
    console.log(this.chosenType1.name);
    console.log(this.img);
  }

  public inStock(productType: ProductType): boolean{
    if(this.productsService.subSpecificationExist(productType)){
      return true;
    } else return productType.stock > 0;
  }

  public selectSubType(productType: ProductType, index: number){
    this.chosenType2 = productType;
    this.typeIndex2 = index;
    this.img = this.chosenType2.imgUrl;
    console.log(this.chosenType2.name + " " + this.chosenType2.name);
  }


  public canOrder(): boolean{
    if(this.chosenType1 == null){
      return false;
    } else if (!this.productsService.subSpecificationExist(this.chosenType1) && this.chosenType1) {
      return this.inStock(this.chosenType1);
    } else if(this.productsService.subSpecificationExist(this.chosenType1) && this.chosenType2){
      return this.inStock(this.chosenType2);
    } else {
      return false;
    }
  }

  public onBuyProduct(product: Product) {
    if(this.canOrder()) {
      if (this.chosenType2 == null) {
        if (product.productSpecification.types.includes(this.chosenType1)) {
          this.cartService.addProductToCart(this.chosenType1);
        }
      } else if (product.productSpecification.types.includes(this.chosenType1)) {
        this.cartService.addProductToCart(this.chosenType2);
      }
    }
  }



}
