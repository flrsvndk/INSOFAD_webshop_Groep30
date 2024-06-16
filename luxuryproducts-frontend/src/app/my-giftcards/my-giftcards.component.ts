import { Component } from '@angular/core';
import {CurrencyPipe, DatePipe, NgForOf, NgIf} from "@angular/common";
import {GiftcardService} from "../services/giftcard.service";
import {GiftcardPurchase} from "../models/giftcard-purchase.model";
import {GiftcardBought} from "../models/giftcard-bought.model";
import {GiftcardOwned} from "../models/giftcard-owned.model";
import {HttpResponse} from "../models/http-response.model";
import {Router} from "@angular/router";
import {AuthService} from "../auth/auth.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-my-giftcards',
  standalone: true,
    imports: [
        CurrencyPipe,
        DatePipe,
        NgForOf,
        NgIf
    ],
  templateUrl: './my-giftcards.component.html',
  styleUrl: './my-giftcards.component.scss'
})
export class MyGiftcardsComponent {
    ownedGiftcards: GiftcardOwned[];
    boughtGiftcards: GiftcardBought[];

    private ownedGiftcards$: Subscription;
    private boughtGiftcards$: Subscription;
    private charge$: Subscription;

    constructor(private giftcardService: GiftcardService, private router: Router) { }

    ngOnInit() {
        this.loadOwnedGiftcards();
        this.loadBoughtGiftcards();
    }

    ngOnDestory() {
        this.ownedGiftcards$.unsubscribe();
        this.boughtGiftcards$.unsubscribe();
        this.charge$.unsubscribe();
    }

    charge(value: number, id: number) {
        this.charge$ = this.giftcardService.chargeGiftcard(value, id).subscribe(
            (result: HttpResponse) => {
                console.log('Giftcard value updated successfully:', result);
            },
            (error) => {
                console.log(error);
            }
        );
        window.location.reload();
    }

    loadOwnedGiftcards() {
        this.ownedGiftcards$ = this.giftcardService.getGiftcardsByOwner().subscribe(giftcards => {
            this.ownedGiftcards = giftcards;
        });
    }

    loadBoughtGiftcards() {
        this.boughtGiftcards$ = this.giftcardService.getGiftcardsByBuyer().subscribe(giftcards => {
            this.boughtGiftcards = giftcards;
        });
    }

    calculateTotal(products: any[]): number {
        let total = 0;
        for (const product of products) {
            total += product.price;
        }
        return total;
    }
}
