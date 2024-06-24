import {Component, OnDestroy, OnInit} from "@angular/core";
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import { Category } from "../../../models/category.model";
import { UserService } from "../../../services/user.service";
import { ProductsService } from "../../../services/products.service";
import { CategoriesService } from "../../../services/categories.service";
import { SidepanelComponent } from "../../sidepanel/sidepanel.component";
import {Product} from "../../../models/product.model";
import {NgForOf, NgIf} from "@angular/common";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  imports: [
    ReactiveFormsModule,
    SidepanelComponent,
    NgForOf,
    NgIf
  ],
  standalone: true
})
export class NewProductComponent implements OnInit, OnDestroy {

  protected admin: boolean;
  public loadedAdmin: boolean = false;

  public newProductForm: FormGroup;
  private productSubscription: Subscription ;

  public categories: Category[] = [];

  constructor(
      private userService: UserService,
      private fb: FormBuilder,
      private productService: ProductsService,
      private categoryService: CategoriesService
  ) {}

  public ngOnInit(): void {
    this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
      this.admin = value;
      this.loadedAdmin = true;
    });

    this.categoryService.getCategories().subscribe((categories: Category[]) => {
      this.categories = categories;
    });

    this.newProductForm = this.fb.group({
      prodName: ['', Validators.required],
      description: ['', Validators.required],
      categoryId: ['', Validators.required],
      productSpecificationsDTO: this.initSpecification()
    });
  }

  public ngOnDestroy() {
    this.productSubscription.unsubscribe();
  }

  public getTypes(): FormArray {
    return (this.newProductForm.get('productSpecificationsDTO') as FormGroup).get('typesDTO') as FormArray;
  }

  public getType(index: number): FormGroup {
    return this.getTypes().at(index) as FormGroup;
  }

  public getSubSpecification(index: number): FormGroup | null {
    return this.getType(index).get('productSpecificationsDTO') as FormGroup;
  }

  public addType() {
    this.getTypes().push(this.initType());
  }

  public addTypeWithSpecification (specification: FormGroup | null){
    if(specification) {
      (specification.get('typesDTO') as FormArray).push(this.initType());

    }
  }

  public addSpecification(typeIndex: number) : void {
    this.getType(typeIndex).addControl('productSpecificationsDTO', this.initSpecification());
  }

  public getTypesForSubType1(specification: FormGroup | null): FormArray | null{
    if(specification){
      return specification.get('typesDTO') as FormArray;
    } else {
      return null;
    }
  }

  public removeType(index: number): void{
    this.getTypes().removeAt(index);
  }

  public removeSpecification(index: number) : void {
    this.getType(index).removeControl('productSpecificationsDTO');
  }

  public initSpecification(): FormGroup{
    return this.fb.group({
      specificationName: ['', Validators.required],
      typesDTO: this.fb.array([
        this.initType()
      ])
    })
  }

  public initType(): FormGroup {
    return this.fb.group({
      typeName: ['', [Validators.required, Validators.minLength(3)]],
      price: ['', [Validators.required, Validators.pattern('[0-9]+(\.[0-9]{1,2})?')]],
      stock: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      imgUrl: ['', [Validators.required, Validators.pattern('https?://.+')]]
    });
  }

  public onSubmit(): void {
    console.log(this.newProductForm.value);
    console.log(this.newProductForm.valid);
    if (this.newProductForm.valid) {
      this.productSubscription = this.productService.createProduct(this.newProductForm.value).subscribe((response: Product) => {
        console.log(response);

        this.newProductForm.reset({
          prodName: ['', Validators.required],
          description: ['', Validators.required],
          categoryId: ['', Validators.required],
          productSpecificationsDTO: this.initSpecification()
        });

        alert("Product created");
      });
    }
  }
}
