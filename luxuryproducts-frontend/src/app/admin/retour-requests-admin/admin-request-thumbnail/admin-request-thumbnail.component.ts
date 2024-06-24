import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {RetourRequest} from "../../../models/retour-request.model";
import {CurrencyPipe, DatePipe, NgForOf, NgIf} from "@angular/common";
import {StaticDetails} from "../../../utils/static-details";
import {RetourService} from "../../../services/retour.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-admin-request-thumbnail',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe,
    NgIf,
    NgForOf
  ],
  templateUrl: './admin-request-thumbnail.component.html',
  styleUrl: './admin-request-thumbnail.component.scss'
})
export class AdminRequestThumbnailComponent implements OnDestroy {
  @Input() request: RetourRequest;

  private $: Subscription;

  constructor(private retourService: RetourService) { }

  ngOnDestroy() {
    this.$?.unsubscribe();
  }

  public onAccept() {
    this.$ = this.retourService.acceptRetourRequest(this.request.id).subscribe(() => {
      this.request.state = StaticDetails.RETOUR_ACCEPTED;
    });
  }

  public onDecline() {
    this.$ = this.retourService.declineRetourRequest(this.request.id).subscribe(() => {
      this.request.state = StaticDetails.RETOUR_DECLINED;
    });
  }

  public onRefund() {
    this.$ = this.retourService.refundRetourRequest(this.request.id).subscribe(() => {
      this.request.state = StaticDetails.RETOUR_REFUNDED;
    });
  }

  protected readonly StaticDetails = StaticDetails;
}
