import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {AuthResponse} from "../auth/auth-response.model";
import {GiftcardPurchase} from "../models/giftcard-purchase.model";
import {GiftcardService} from "../services/giftcard.service";
import {HttpResponse} from "../models/http-response.model";

@Component({
  selector: 'app-giftcards',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    NgForOf,
    NgClass
  ],
  templateUrl: './giftcards.component.html',
  styleUrl: './giftcards.component.scss'
})
export class GiftcardsComponent {
    public giftcardForm: FormGroup;
    public giftcard: GiftcardPurchase;
    public price: number;

    constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private giftcardService: GiftcardService) {
    }

    ngOnInit(): void {
        this.giftcardForm = this.fb.group({
            email: ['', [Validators.email, Validators.required, Validators.maxLength(64), Validators.minLength(5)]],
            message: ['', [Validators.maxLength(256)]]
        });
        this.price = 10;
    }

    public selectPrice(price: number): void {
      this.price = price
    }

    public onSubmit(): void {
        const form = document.forms[0];
        // @ts-ignore
        console.log((<HTMLInputElement>document.getElementById("email")).value);
        // @ts-ignore
        this.giftcard = new GiftcardPurchase((<HTMLInputElement>document.getElementById("message")).value,
            this.price,
            (<HTMLInputElement>document.getElementById("email")).value);
        console.log(this.giftcard);

        this.giftcardService.addOrder(this.giftcard).subscribe(
            (result: HttpResponse) => {
                console.log('Giftcard order added successfully:', result);
                this.router.navigateByUrl('/paymentsuccessful');
            },
            (error) => {
              if (error.error.text == "Giftcard created") {
                this.router.navigateByUrl('/paymentsuccessful');
              }
              else {
                console.log(error.error.text);
              }
            }
        );
    }
}
