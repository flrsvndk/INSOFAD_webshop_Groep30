import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Promocode} from '../../../models/promocode.model';
import {PromocodeService} from '../../../services/promocode.service';
import {CommonModule} from '@angular/common';
import {SidepanelComponent} from "../../sidepanel/sidepanel.component";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-promocode-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, SidepanelComponent],
  templateUrl: './promocode-edit.component.html',
  styleUrl: './promocode-edit.component.scss'
})
export class PromocodeEditComponent implements OnInit, OnDestroy{
  @Input() public promocode!: Promocode;
  public editPromocodeForm: FormGroup;
  public promocodeAlreadyExists: boolean;
  public existingPromocodes: Promocode[];
  private promocodeId: string;

  private route$: Subscription;
  private promocodeService$: Subscription;
  private updatePromocode$: Subscription;
  private getPromocodes$: Subscription;

  constructor(
      private fb: FormBuilder,
      private promocodeService: PromocodeService,
      private activatedRoute: ActivatedRoute,
      private router: Router,
  ) {}

  ngOnInit() {
    this.route$ = this.activatedRoute.params.subscribe(params => {
        this.promocodeId = params['id'];
    });

      this.loadPromocode();

      this.editPromocodeForm = this.fb.group({
      name: [this.promocode?.name || '', [Validators.required]],
      description: [this.promocode?.description || '', [Validators.required]],
      percentageOff: [this.promocode?.percentageOff || 5, [Validators.required, Validators.min(5), Validators.max(50)]],
      maxUsages: [this.promocode?.maxUsages || 1, [Validators.required, Validators.min(1), Validators.max(1000)]],
      dedicatedPromocode: [this.promocode?.dedicatedPromocode || false],
      dedicatedUserEmail: [{value: this.promocode?.dedicatedUserEmail || '', disabled: !this.promocode?.dedicatedPromocode}]
    });

    this.editPromocodeForm.get('dedicatedPromocode')?.valueChanges.subscribe(value => {
      if (value) {
        this.editPromocodeForm.get('dedicatedUserEmail')?.enable();
      } else {
        this.editPromocodeForm.get('dedicatedUserEmail')?.disable();
      }
    });

    this.getPromocodes();
    this.promocodeAlreadyExists = false;
  }

  ngOnDestroy() {
    this.route$?.unsubscribe();
    this.promocodeService$?.unsubscribe();
    this.getPromocodes$?.unsubscribe();
    this.updatePromocode$?.unsubscribe();
  }

  private loadPromocode():void {
    this.promocodeService$ = this.promocodeService.getPromocodeById(this.promocodeId)
        .subscribe((promocode: Promocode) => {
          this.promocode = promocode;
          this.editPromocodeForm.patchValue({
            name: promocode.name,
            description: promocode.description,
            percentageOff: promocode.percentageOff,
            maxUsages: promocode.maxUsages,
            dedicatedPromocode: promocode.dedicatedPromocode,
            dedicatedUserEmail: promocode.dedicatedUserEmail
          });
          if (promocode.dedicatedPromocode) {
            this.editPromocodeForm.get('dedicatedUserEmail')?.enable();
          }
        });
  }

  public onNameInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    input.value = input.value.toUpperCase();
    this.editPromocodeForm.get('name')?.setValue(input.value);
  }

  public onSubmit(): void {
    if (this.editPromocodeForm.valid) {
      const formData = this.editPromocodeForm.value;
      const submittedPromocodeWithChanges: Promocode = {
        ...this.promocode,
        name: formData.name,
        description: formData.description,
        percentageOff: formData.percentageOff,
        maxUsages: formData.maxUsages,
        dedicatedPromocode: formData.dedicatedPromocode,
        dedicatedUserEmail: formData.dedicatedUserEmail
      };

      for (let promocode of this.existingPromocodes) {
        if (promocode.name === submittedPromocodeWithChanges.name && promocode.id !== submittedPromocodeWithChanges.id) {
          this.promocodeAlreadyExists = true;
          return;
        }
      }

      this.updatePromocode$ = this.promocodeService.updatePromocode(submittedPromocodeWithChanges).subscribe({
        next: (response) => {
          console.log(response);
          window.location.reload();
        },
        error: (error) => {
          if (error.status === 200) {
            this.router.navigate(['/admin/promocodes'])
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
