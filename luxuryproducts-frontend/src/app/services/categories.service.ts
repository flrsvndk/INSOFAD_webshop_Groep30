import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Category} from "../models/category.model";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {

  private baseUrl: string = environment.BASE_URL + "/category";

  constructor(private http: HttpClient) { }


  public getCategories(){
    return this.http.get<Category[]>(this.baseUrl)
  }

}
