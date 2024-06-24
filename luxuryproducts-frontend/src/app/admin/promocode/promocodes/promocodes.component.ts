import {Component, OnDestroy, OnInit} from '@angular/core';
import {Promocode} from "../../../models/promocode.model";
import {PromocodeService} from "../../../services/promocode.service";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {PromocodeThumbnailComponent} from "../promocode-thumbnail/promocode-thumbnail.component";
import {SidepanelComponent} from "../../sidepanel/sidepanel.component";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-promocodes',
  standalone: true,
  imports: [
    RouterLinkActive,
    RouterLink,
    PromocodeThumbnailComponent,
    SidepanelComponent
  ],
  templateUrl: './promocodes.component.html',
  styleUrl: './promocodes.component.scss'
})
export class PromocodesComponent implements OnInit, OnDestroy {
  protected promocodes: Promocode[];

  private promocodes$: Subscription;

  constructor(private promocodeService: PromocodeService) {}

  ngOnInit() {
    this.loadPromocodes();
  }

  ngOnDestroy() {
    this.promocodes$?.unsubscribe();
  }

  private loadPromocodes() {
    this.promocodes$ = this.promocodeService.getPromocodes().subscribe((promocodes: Promocode[]) => {
      this.promocodes = promocodes;
    });
  }
}
