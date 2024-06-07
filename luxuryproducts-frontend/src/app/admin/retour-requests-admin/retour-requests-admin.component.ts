import {Component, OnDestroy, OnInit} from '@angular/core';
import {
  AdminOrderThumbnailComponent
} from "../placed-orders-admin/admin-order-thumbnail/admin-order-thumbnail.component";
import {SidepanelComponent} from "../sidepanel/sidepanel.component";
import {RetourRequest} from "../../models/retour-request.model";
import {Subscription} from "rxjs";
import {RetourService} from "../../services/retour.service";
import {AdminRequestThumbnailComponent} from "./admin-request-thumbnail/admin-request-thumbnail.component";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-retour-requests-admin',
  standalone: true,
  imports: [
    AdminOrderThumbnailComponent,
    SidepanelComponent,
    AdminRequestThumbnailComponent,
    NgForOf
  ],
  templateUrl: './retour-requests-admin.component.html',
  styleUrl: './retour-requests-admin.component.scss'
})
export class RetourRequestsAdminComponent implements OnInit, OnDestroy {

  public requests: RetourRequest[];

  private requests$: Subscription;

  constructor(private retourService: RetourService) { }

  ngOnInit(): void {
    this.requests$ = this.retourService.getRequests().subscribe((result: RetourRequest[]) => {
      this.requests = result;
    });
  }

  ngOnDestroy(): void {
    this.requests$?.unsubscribe();
  }





}
