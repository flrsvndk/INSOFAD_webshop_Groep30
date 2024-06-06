import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, Router} from "@angular/router";
import {FormsModule, NgForm} from "@angular/forms";
import {ExistingOrder} from "../models/existing-order.model";
import {OrderService} from "../services/order.service";
import {RetourRequest} from "../models/retour-request.model";
import {RetourReason} from "../models/retour-reason.model";
import {RetourService} from "../services/retour.service";

@Component({
  selector: 'app-retour-page',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule,
    NgIf
  ],
  templateUrl: './retour-page.component.html',
  styleUrl: './retour-page.component.scss'
})
export class RetourPageComponent implements OnInit, OnDestroy {

  public reasons: RetourReason[];
  public order: ExistingOrder;

  public selectedProducts: { [key: string]: boolean } = {};
  public selectedReason: string | null = '';
  public comment: string = '';

  private reasons$: Subscription;
  private order$: Subscription;
  private route$: Subscription;
  private retour$: Subscription;

  private id: string;

  constructor(private retourService: RetourService,
              private orderService: OrderService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.route$ = this.route.params.subscribe(params => {
      this.id = params['id'];
    })

    this.reasons$ = this.retourService.getReasons().subscribe((res: RetourReason[]) => {
      this.reasons = res;
    });

    this.order$ = this.orderService.getOrdersByCurrentUser().subscribe((res: ExistingOrder[]) => {
      for (let order of res) {
        if (order.id == this.id) {
          this.order = order;
        }
      }
    });
  }

  ngOnDestroy() {
    this.reasons$?.unsubscribe();
    this.order$?.unsubscribe();
    this.route$?.unsubscribe();
    this.retour$?.unsubscribe();
  }

  public onSubmit(form: NgForm): void {
    if (form.valid) {
      this.retour$ = this.retourService.createRetourRequest(form, this.id).subscribe((res: RetourRequest) => {
        this.router.navigate(['/']);
      });
    }
  }

  public atLeastOneProductSelected(): boolean {
    return Object.values(this.selectedProducts).some(value => value);
  }
}