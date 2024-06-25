import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {ProductsComponent} from './products.component';
import {ProductThumbnailComponent} from './product-thumbnail/product-thumbnail.component';
import {ProductDetailComponent} from './product-detail/product-detail.component';

import {FormsModule} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatMenuTrigger} from "@angular/material/menu";
import {MatFormField} from "@angular/material/form-field";
import {MatSelect} from "@angular/material/select";
import {MatInput} from "@angular/material/input";
import {PromocodeAdvertComponent} from "../admin/promocode/promocode-advert/promocode-advert.component";

@NgModule({
  declarations: [
    ProductsComponent,
    ProductThumbnailComponent,
    ProductDetailComponent
  ],
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        MatButton,
        MatMenuTrigger,
        MatFormField,
        MatSelect,
        MatInput,
        PromocodeAdvertComponent
    ],
  exports: [
    ProductsComponent
  ]

})
export class ProductsModule {

}
