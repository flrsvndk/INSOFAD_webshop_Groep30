import { Component } from '@angular/core';
import {CurrencyPipe, DatePipe, NgForOf, NgIf} from "@angular/common";
import {GiftcardOwned} from "../../models/giftcard-owned.model";
import {GiftcardBought} from "../../models/giftcard-bought.model";
import {GiftcardService} from "../../services/giftcard.service";
import {Router} from "@angular/router";
import {HttpResponse} from "../../models/http-response.model";
import {SidepanelComponent} from "../sidepanel/sidepanel.component";

@Component({
  selector: 'app-giftcards-admin',
  standalone: true,
  imports: [
    CurrencyPipe,
    NgForOf,
    NgIf,
    DatePipe,
    SidepanelComponent
  ],
  templateUrl: './giftcards-admin.component.html',
  styleUrl: './giftcards-admin.component.scss'
})
export class GiftcardsAdminComponent {
  giftcards: GiftcardOwned[];

  constructor(private giftcardService: GiftcardService, private router: Router) { }

  ngOnInit() {
    this.loadAllGiftcards();
  }

  delete(id: number) {
    this.giftcardService.deleteGiftcard(id).subscribe(
        (result: HttpResponse) => {
          console.log('Giftcard canceled successfully:', result);
        },
        (error) => {
          console.log(error);
        }
    );
    window.location.reload();
  }

  loadAllGiftcards() {
    this.giftcardService.getAllGiftcards().subscribe(giftcards => {
      this.giftcards = giftcards;
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
