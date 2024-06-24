import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, Subscription, throwError} from 'rxjs';
import { environment } from '../../environments/environment';
import {GiftcardPurchase} from "../models/giftcard-purchase.model";
import {HttpResponse} from "../models/http-response.model";
import {GiftcardBought} from "../models/giftcard-bought.model";
import {GiftcardOwned} from "../models/giftcard-owned.model";

@Injectable({
    providedIn: 'root'
})
export class GiftcardService {
    private baseUrl: string = environment.BASE_URL + "/giftcards";

    ownedGiftcards: GiftcardOwned[] = [];
    ownedGiftcards$: Subscription;

    constructor(private http: HttpClient) {
        this.ownedGiftcards$ = this.getGiftcardsByOwner().subscribe((res): void => {
            this.ownedGiftcards = res;
        });
    }

    public addOrder(giftcard: GiftcardPurchase): Observable<HttpResponse> {
        console.log("Ontvangen giftcard order: " + giftcard);

        return this.http.post<HttpResponse>(this.baseUrl, {
            message: giftcard.message,
            price: giftcard.price,
            ownerEmail: giftcard.ownerEmail
        });
    }

    getGiftcardsByOwner(): Observable<GiftcardOwned[]> {
        return this.http.get<GiftcardOwned[]>(this.baseUrl + "/getByOwner");
    }

    getGiftcardsByBuyer(): Observable<GiftcardBought[]> {
        return this.http.get<GiftcardBought[]>(this.baseUrl + "/getByBuyer");
    }

    getAllGiftcards() {
        return this.http.get<GiftcardOwned[]>(this.baseUrl + "/getAll");
    }

    lowerGiftcardValue(value: number, id: number) {
        return this.http.put<HttpResponse>(this.baseUrl + "/lowerValue", {
          id: id,
          value: value
      });
    }

    chargeGiftcard(value: number, id: number) {
        console.log("chargeGiftcard start");
        return this.http.put<HttpResponse>(this.baseUrl + "/charge", {
            id: id,
            value: value
        });
    }

    deleteGiftcard(id: number): Observable<HttpResponse> {
        console.log("deleteGiftcard start");
        return this.http.delete<HttpResponse>(this.baseUrl + "/" + id);
    }

    public getPriceWithGiftcards(giftcardsText: String, totalPrice: number) {
        let unpaid = totalPrice;
        let giftcardIds: String[] = giftcardsText.split(" ");
        let giftcards: GiftcardOwned[] = this.ownedGiftcards;

        for (let giftcard of giftcards) {
            for (let id of giftcardIds) {
                if (id == giftcard.id.toString()) {
                    console.log(giftcard);
                    unpaid -= Math.min(unpaid, giftcard.value);
                    break;
                }
            }
            if (unpaid == 0) {
                return 0;
            }
        }

        return unpaid;
    }

    public useGiftcards(giftcardsText: string, totalPrice: number) {
        let unpaid = totalPrice;
        let giftcardIds: String[] = giftcardsText.split(" ");
        let giftcards: GiftcardOwned[] = this.ownedGiftcards;


        for (let giftcard of giftcards) {
            for (let id of giftcardIds) {
                if (id == giftcard.id.toString()) {
                    console.log(giftcard);
                    let unpaidOld = unpaid;
                    unpaid -= Math.min(unpaid, giftcard.value);
                    giftcard.value -= Math.min(unpaidOld, giftcard.value);
                    this.lowerGiftcardValue(giftcard.value, giftcard.id).subscribe(
                        (result: HttpResponse) => {
                            console.log('Giftcard value updated successfully:', result);
                        },
                        (error) => {
                            console.log(error);
                        }
                    );
                    break;
                }
            }
            if (unpaid == 0) {
                return;
            }
        }
    }
}
