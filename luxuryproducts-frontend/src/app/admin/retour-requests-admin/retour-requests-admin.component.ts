import {Component, OnDestroy, OnInit} from '@angular/core';
import {AdminOrderThumbnailComponent} from "../placed-orders-admin/admin-order-thumbnail/admin-order-thumbnail.component";
import {SidepanelComponent} from "../sidepanel/sidepanel.component";
import {RetourRequest} from "../../models/retour-request.model";
import {Subscription} from "rxjs";
import {RetourService} from "../../services/retour.service";
import {AdminRequestThumbnailComponent} from "./admin-request-thumbnail/admin-request-thumbnail.component";
import {NgForOf} from "@angular/common";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-retour-requests-admin',
  standalone: true,
  imports: [
    AdminOrderThumbnailComponent,
    SidepanelComponent,
    AdminRequestThumbnailComponent,
    NgForOf,
    FormsModule
  ],
  templateUrl: './retour-requests-admin.component.html',
  styleUrl: './retour-requests-admin.component.scss'
})
export class RetourRequestsAdminComponent implements OnInit, OnDestroy {

  public retourRequests: RetourRequest[] = [];
  public alteredRetourRequests: RetourRequest[] = [];

  public sortNewestFirst: boolean = true;
  public filterStatus: string = '';

  private requests$: Subscription;

  constructor(private retourService: RetourService) { }

  ngOnInit(): void {
    this.requests$ = this.retourService.getRequests().subscribe((result: RetourRequest[]) => {
      this.retourRequests = result;
      this.alterRetoursList();
    });
  }

  ngOnDestroy(): void {
    this.requests$?.unsubscribe();
  }

  public toggleSort(): void {
    this.sortNewestFirst = !this.sortNewestFirst;
    this.alterRetoursList();
  }

  public filterRetourRequests(): void {
    this.alteredRetourRequests = this.filterStatus !== '' ? this.retourRequests.filter(retour => retour.state === this.filterStatus) : this.retourRequests;
    this.alterRetoursList();
  }

  public alterRetoursList() {

    this.alteredRetourRequests = this.filterStatus !== '' ? this.retourRequests.filter(retour => retour.state === this.filterStatus) : this.retourRequests;

    this.alteredRetourRequests = this.alteredRetourRequests
      .filter(retour => retour.dateTime !== undefined)
      .sort((a, b) => {
        const dateA = new Date(a.dateTime!);
        const dateB = new Date(b.dateTime!);

        if (this.sortNewestFirst) {
          return dateB.getTime() - dateA.getTime();
        } else {
          return dateA.getTime() - dateB.getTime();
        }
      });
  }
}
