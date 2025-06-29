import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Observable} from 'rxjs';

import {environment} from '../../environments/environment';
import {Product} from '../models/product.model';
import {ProductType} from "../models/product-type.model";

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private baseUrl: string = environment.BASE_URL + "/products";


  constructor(private http: HttpClient) {
  }

  public getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl);
  }

  public subSpecificationExist(prodtype: ProductType){
    if(prodtype == null){
      return false;
    } else return prodtype.subSpecification != null;
  }

  public calculatePrice(product: Product, type1Index: number, type2Index : number): any {
    if(!this.subSpecificationExist(product.productSpecification.types[type1Index])){
      return product.productSpecification.types.at(type1Index)?.price;
    } else if(!this.subSpecificationExist(product.productSpecification.types[type1Index].subSpecification.types[type2Index])){
      return product.productSpecification.types.at(type1Index)?.subSpecification.types.at(type2Index)?.price;
    }
  }

  public createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.baseUrl, product);
  }

  public getProductById(id: string): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/${id}`);
  }

  public updateProductByIndex(product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.baseUrl}`, product);
  }


}
