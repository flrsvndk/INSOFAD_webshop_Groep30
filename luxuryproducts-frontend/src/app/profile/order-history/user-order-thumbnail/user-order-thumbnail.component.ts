import {Component, Input, OnInit} from '@angular/core';
import {CurrencyPipe, DatePipe, NgIf} from "@angular/common";
import {ExistingOrder} from "../../../models/existing-order.model";
import {StaticDetails} from "../../../utils/static-details";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-user-order-thumbnail',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe,
    NgIf,
    RouterLink
  ],
  templateUrl: './user-order-thumbnail.component.html',
  styleUrl: './user-order-thumbnail.component.scss'
})
export class UserOrderThumbnailComponent implements OnInit {

  @Input() order: ExistingOrder;

  public orderWithinReturnPeriod: boolean;

  ngOnInit() {
    const orderDate: Date = new Date(this.order.orderDate);
    const today: Date = new Date();
    const daysDifference: number = Math.floor((today.getTime() - orderDate.getTime()) / (1000 * 3600 * 24));
    this.orderWithinReturnPeriod = daysDifference <= StaticDetails.RETOUR_PERIOD;
  }

  protected readonly StaticDetails = StaticDetails;
}
