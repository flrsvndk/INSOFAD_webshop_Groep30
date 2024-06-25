import { Component, OnDestroy, OnInit } from '@angular/core';
import { PromocodeService } from "../../../services/promocode.service";
import { Subscription } from "rxjs";
import { Promocode } from "../../../models/promocode.model";

@Component({
  selector: 'app-promocode-advert',
  standalone: true,
  imports: [],
  templateUrl: './promocode-advert.component.html',
  styleUrls: ['./promocode-advert.component.scss'] // Corrected 'styleUrl' to 'styleUrls'
})
export class PromocodeAdvertComponent implements OnInit, OnDestroy {
  protected promocodes: Promocode[] = [];
  protected eligiblePromocodes: Promocode[] = [];
  public displayedPromocode: Promocode;

  private promocodes$: Subscription;

  constructor(private promocodeService: PromocodeService) {}

  ngOnInit() {
    this.promocodes$ = this.promocodeService.getPromocodes().subscribe((promocodes: Promocode[]) => {
      this.promocodes = promocodes;
      this.selectEligiblePromocodes();
      this.randomDisplayedPromocode();
    });
  }

  ngOnDestroy() {
    this.promocodes$?.unsubscribe();
  }

  private selectEligiblePromocodes() {
    this.eligiblePromocodes = this.promocodes.filter(promocode =>
        promocode.available &&
        !promocode.dedicatedPromocode &&
        promocode.usages < promocode.maxUsages
    );
  }

  private randomDisplayedPromocode() {
    if (this.eligiblePromocodes.length > 0) {
      const randomIndex = Math.floor(Math.random() * this.eligiblePromocodes.length);
      this.displayedPromocode = this.eligiblePromocodes[randomIndex];
    }
  }
}
