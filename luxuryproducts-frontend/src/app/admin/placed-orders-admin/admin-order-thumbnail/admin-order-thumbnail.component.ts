import {Component, Input} from '@angular/core';
import {CurrencyPipe, DatePipe, NgIf} from "@angular/common";
import {ExistingOrder} from "../../../models/existing-order.model";
import {Subscription} from "rxjs";
import {OrderService} from "../../../services/order.service";
import {StaticDetails} from "../../../utils/static-details";

@Component({
  selector: 'app-admin-order-thumbnail',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe,
    NgIf
  ],
  templateUrl: './admin-order-thumbnail.component.html',
  styleUrl: './admin-order-thumbnail.component.scss'
})
export class AdminOrderThumbnailComponent {
  @Input() order: ExistingOrder;

  private status$: Subscription;

  constructor(private orderService: OrderService) { }

  public setProcessing() {
    this.status$ = this.orderService.setProcessing(this.order.id).subscribe(() => {
      this.order.status = StaticDetails.ORDER_PROCESSING;
    });
  }

  public setConfirmed() {
    this.status$ = this.orderService.setConfirmed(this.order.id).subscribe(() => {
      this.order.status = StaticDetails.ORDER_CONFIRMED;
    });
  }

  public setOutForDelivery() {
    this.status$ = this.orderService.setOutForDelivery(this.order.id).subscribe(() => {
      this.order.status = StaticDetails.ORDER_OUT_FOR_DELIVERY;
    });
  }

  public setDelivered() {
    this.status$ = this.orderService.setDelivered(this.order.id).subscribe(() => {
      this.order.status = StaticDetails.ORDER_DELIVERED;
    });
  }

  protected readonly StaticDetails = StaticDetails;
}
