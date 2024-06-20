import {Component, Input, OnDestroy} from '@angular/core';
import {RouterLink, RouterLinkActive} from "@angular/router";
import {Promocode} from "../../../models/promocode.model";
import {PromocodeService} from "../../../services/promocode.service";
import {CurrencyPipe} from "@angular/common";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-promocode-thumbnail',
  standalone: true,
    imports: [
        RouterLinkActive,
        RouterLink,
        CurrencyPipe
    ],
  templateUrl: './promocode-thumbnail.component.html',
  styleUrl: './promocode-thumbnail.component.scss'
})
export class PromocodeThumbnailComponent implements OnDestroy {
  @Input() promocode!: Promocode;

  private deletePromocode$: Subscription;
  private archivePromocode$: Subscription;

  constructor(private promocodeService: PromocodeService) {}

  protected onDeletePromocode(id: number) {

      if (confirm("Weet je zeker dat je deze promocode wil verwijderen? Dat kan niet ongedaan worden!")) {
          this.deletePromocode$ = this.promocodeService.deletePromocodeById(id).subscribe(
              {
                  next: () => {
                      window.location.reload();
                  },
                  error: (error) => {
                      if (error.status === 200) {
                          window.location.reload();
                      } else {
                          console.log(error);
                      }
                  }
              });
      }
  }

  protected onArchivePromocode(id: number) {
      this.archivePromocode$ = this.promocodeService.archivePromocodeById(id).subscribe(
          {
             next: () => {
                 window.location.reload();
             }, error: (error) => {
                  if (error.status === 200) {
                      window.location.reload();
                  } else {
                      console.log(error);
                  }
             }
          });
  }

  ngOnDestroy() {
      if (this.deletePromocode$) {
          this.deletePromocode$.unsubscribe();
      }

      if (this.archivePromocode$) {
          this.archivePromocode$.unsubscribe();
      }
  }
}
