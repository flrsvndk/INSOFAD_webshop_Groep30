import {Component, Input} from '@angular/core';
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
export class UserOrderThumbnailComponent {

  @Input() order: ExistingOrder;

  public orderWithinReturnPeriod: boolean = true;

  protected readonly StaticDetails = StaticDetails;
}
