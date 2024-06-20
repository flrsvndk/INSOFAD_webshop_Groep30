import {Component, OnDestroy, OnInit} from '@angular/core';
import {PromocodeThumbnailComponent} from "../promocode-thumbnail/promocode-thumbnail.component";
import {SidepanelComponent} from "../../sidepanel/sidepanel.component";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {PromocodeService} from "../../../services/promocode.service";
import {Promocode} from "../../../models/promocode.model";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-create-promocode',
  standalone: true,
  imports: [
    PromocodeThumbnailComponent,
    SidepanelComponent,
    ReactiveFormsModule
  ],
  templateUrl: './create-promocode.component.html',
  styleUrl: './create-promocode.component.scss'
})
export class CreatePromocodeComponent implements OnInit, OnDestroy {
  public createPromocodeForm: FormGroup;
  public existingPromocodes: Promocode[];
  public promocodeAlreadyExists: boolean = false;

  private promocodeService$: Subscription;
  private getPromocodes$: Subscription;

  constructor(private fb: FormBuilder, private promocodeService: PromocodeService) {
  }

  ngOnInit(): void {
    this.createPromocodeForm = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.required, Validators.maxLength(255)]],
      percentageOff: ['', [Validators.required, Validators.min(5), Validators.max(50)]],
      maxUsages: ['', [Validators.required, Validators.min(1), Validators.max(1000)]],
      dedicatedPromocode: [false],
      dedicatedUserEmail: [{value: '', disabled: true}, [Validators.email, Validators.maxLength(100)]],
    });

    this.createPromocodeForm.get('dedicatedPromocode')?.valueChanges.subscribe((value) => {
      const dedicatedUserEmailControl = this.createPromocodeForm.get('dedicatedUserEmail');
      if (value) {
        dedicatedUserEmailControl?.enable();
      } else {
        dedicatedUserEmailControl?.disable();
        dedicatedUserEmailControl?.reset();
      }
    });

    this.getPromocodes();
  }

  ngOnDestroy() {
    this.promocodeService$.unsubscribe();
    this.getPromocodes$.unsubscribe();
  }

  public onNameInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    input.value = input.value.toUpperCase();
    this.createPromocodeForm.get('name')?.setValue(input.value, {emitEvent: false});
  }

  public onSubmit(): void {
    if (this.createPromocodeForm.valid) {
      const formData = this.createPromocodeForm.value;
      const newPromocode: Promocode = {
        id: 1,
        name: formData.name,
        description: formData.description,
        percentageOff: formData.percentageOff,
        maxUsages: formData.maxUsages,
        usages: 0,
        totalPriceOrders: 0,
        available: true,
        dedicatedPromocode: formData.dedicatedPromocode,
        dedicatedUserEmail: formData.dedicatedUserEmail
      };

      for (let promocode of this.existingPromocodes) {
        if (promocode.name === newPromocode.name) {
          this.promocodeAlreadyExists = true;
          return;
        }
      }

      this.promocodeService$ = this.promocodeService.createPromocode(newPromocode).subscribe({
        next(response) {
          console.log(response);
          window.location.reload();
        },
        error(error) {
          if (error.status === 200) {
            window.location.reload();
          } else {
            console.error(error);
          }
        }
      });
    }
  }

  private getPromocodes(): void {
    this.getPromocodes$ = this.promocodeService.getPromocodes().subscribe(
        (receivedPromocodes: Promocode[]) => {
          this.existingPromocodes = receivedPromocodes;
        }
    );
  }
}
