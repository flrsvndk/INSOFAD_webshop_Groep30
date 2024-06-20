import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Promocode} from "../models/promocode.model";
import {Observable} from "rxjs";
import {Order} from "../models/order.model";

@Injectable({
    providedIn: 'root'
})
export class PromocodeService {
    private baseUrl: string = environment.BASE_URL + "/promocodes";

    constructor(private http: HttpClient) {}

    getPromocodes(): Observable<Promocode[]> {
        return this.http.get<Promocode[]>(this.baseUrl);
    }

    getPromocodeById(promocodeId: number): Observable<Promocode> {
        return this.http.get<Promocode>(`${this.baseUrl}/${promocodeId}`);
    }

    deletePromocodeById(promocodeId: number): Observable<String> {
        return this.http.delete<String>(`${this.baseUrl}/${promocodeId}`);
    }

    archivePromocodeById(promocodeId: number): Observable<String> {
        return this.http.put<String>(`${this.baseUrl}/${promocodeId}`, promocodeId);
    }

    updatePromocode(promocode: Promocode): Observable<String> {
        const updateUrl: string = this.baseUrl + "/update";
        return this.http.put<String>(updateUrl, promocode)
    }

    updateAnalyticsPromocode(order: Order): Observable<String> {
        const updateUrl: string = this.baseUrl  + "/update/analytics";
        return this.http.put<String>(updateUrl, order);
    }

    createPromocode(promocode: Promocode): Observable<String> {
        return this.http.post<String>(`${this.baseUrl}`, promocode);
    }
}
