import {Component, Input, OnInit} from '@angular/core';
import {RetourRequest} from "../../../models/retour-request.model";
import {CurrencyPipe, DatePipe, NgForOf, NgIf} from "@angular/common";
import {StaticDetails} from "../../../utils/static-details";

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
export class AdminRequestThumbnailComponent implements OnInit {
  @Input() request: RetourRequest;

  ngOnInit() {
    console.log(this.request)
  }

  protected readonly StaticDetails = StaticDetails;

  public onAccept() {

  }

  public onDecline() {

  }

  public onRefund() {

  }
}
