import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, FormArray, FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import {CurrencyPipe, NgForOf, NgIf, NgOptimizedImage, NgSwitch, NgSwitchCase} from "@angular/common";
import {Product} from "../../../models/product.model";
import {ProductsService} from "../../../services/products.service";
import {Category} from "../../../models/category.model";
import {User} from "../../../models/user.model";
import {UserService} from "../../../services/user.service";
import {CategoriesService} from "../../../services/categories.service";

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './new-product.component.html',
  imports: [
    ReactiveFormsModule,
    NgSwitch,
    NgSwitchCase,
    NgForOf,
    CurrencyPipe,
    NgIf,
    NgOptimizedImage
  ],
  standalone: true
})
export class NewProductComponent {

  productForm: FormGroup;
  categories: Category[];

    protected admin: boolean;
    public loadedAdmin: boolean = false;

  constructor(private userService: UserService, private fb: FormBuilder, private productService: ProductsService, private categoryService: CategoriesService) {
      this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
          this.admin = value;
          this.loadedAdmin = true;
      });
      console.log(this.admin);
    this.categoryService.getCategories().subscribe((categories: Category[]) => {
      this.categories = categories;
    });
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      category: ['']
    });
  }

  onSubmit(): void {
    console.log(this.productForm.value);
    if (this.productForm.valid) {
      this.productService.createProduct(this.productForm.value).subscribe();
      this.productForm.reset();
    }
  }
}

//   form: FormGroup;
//
//   constructor(private fb: FormBuilder) {
//     this.form = this.fb.group({
//       inputs: this.fb.array([])
//     });
//   }
//
//   ngOnInit(): void {
//   }
//
//   get inputs(): FormArray {
//     return this.form.get('inputs') as FormArray;
//   }
//
//   addType(): void {
//     const input = this.fb.group({
//       type: ['text'],
//       value: ['']
//     });
//     this.inputs.push(input);
//   }
//
//   removeInput(index: number): void {
//     this.inputs.removeAt(index);
//   }
//
//   submitForm(): void {
//     console.log(this.form.value);
//   }
// }
// import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
// import {Component, OnInit} from "@angular/core";
// import {UserService} from "../../../services/user.service";
// import {ProductsService} from "../../../services/products.service";
// import {Router} from "@angular/router";
//
//
//
// @Component({
//   selector: 'newProductComponent',
//   templateUrl: './new-product.component.html',
//   standalone: true,
//   imports: [
//     ReactiveFormsModule
//   ],
//   styleUrls: ['./new-product.component.scss']
// })
// export class NewProductComponent implements OnInit{
//   //
//   public productForm: FormGroup;
//   public typeForm: FormGroup;
//   public types: FormGroup[];
//   private admin: boolean;
//
//
//   constructor(private fb: FormBuilder, private userService: UserService, private productService: ProductsService, private router: Router) {
//     this.admin = !this.userService.isAdmin();
//     console.log(this.admin);
//   }
//
//
//   ngOnInit(): void {
//     this.productForm = this.fb.group({
//       name: ['', Validators.required],
//       description: ['', Validators.required],
//       category: this.fb.group({
//         name: ['', Validators.required]
//       }),
//       categoryId: [0, Validators.required],
//       productSpecificationsDTO: this.fb.group({
//         name: ['', Validators.required],
//         typesDTO: this.typeForma
//       })
//     });
//   }
//
//
//   public isAdmin(): boolean {
//     return this.admin;
//   }
//
//   public createSpecification(): void{
//
//   }
//
//   public addType(): void{
//     this.types.push(
//       this.typeForm = this.fb.group({
//         name: ['', Validators.required],
//         price: [0, [Validators.required, Validators.min(0)]],
//         imgUrl: ['', Validators.required]
//       })
//     )
//   }
//
// }
//
//
//   //
//   // createType(): FormGroup {
//   //   return this.fb.group({
//   //     name: ['', Validators.required],
//   //     price: [0, [Validators.required, Validators.min(0)]],
//   //     imgUrl: ['', Validators.required],
//   //     subSpecification: ['']
//   //   });
//   // }
//   //
//   // get typesDTO(): FormArray {
//   //   return this.productForm.get('productSpecificationsDTO.typesDTO') as FormArray;
//   // }
//   //
//   // addType(): void {
//   //   this.typesDTO.push(this.createType());
//   // }
//   //
//   // removeType(index: number): void {
//   //   this.typesDTO.removeAt(index);
//   // }
//   //
//   // onSubmit(): void {
//   //   console.log(this.productForm.value);
//   // }
//
//
//   // addSpecification() {
//   //   const specs = this.productForm.get('specifications') as FormArray;
//   //   specs.push(this.fb.group({
//   //     type: ['', Validators.required],
//   //     details: ['']
//   //   }));
//   // }
//   //
//   // removeSpecification(index: number) {
//   //   const specs = this.productForm.get('specifications') as FormArray;
//   //   specs.removeAt(index);
//   // }
//   //
//   // onSubmit() {
//   //   // Logic to handle form submission
//   //   if (this.productForm.valid) {
//   //     const productData: Product = this.productForm.value;
//   //     console.log(productData);
//   //   }
//   // }
// // }
//
// // import {Component, Input} from '@angular/core';
// // import {UserService} from "../../../services/user.service";
// // import {FormGroup, FormsModule} from "@angular/forms";
// // import {User} from "../../../models/user.model";
// // import {Adress} from "../../../models/adress.model";
// // import {Router} from "@angular/router";
// // import {AuthResponse} from "../../../auth/auth-response.model";
// // import {ProductsService} from "../../../services/products.service";
// // import {ProductSpecification} from "../../../models/product-specification.model";
// // import {Product} from "../../../models/product.model";
// // import {ProductType} from "../../../models/product-type.model";
// //
// // @Component({
// //   selector: 'app-new-product',
// //   standalone: true,
// //   imports: [
// //     FormsModule
// //   ],
// //   templateUrl: './new-product.component.html',
// //   styleUrl: './new-product.component.scss'
// // })
// // export class NewProductComponent {
// //   private role: String = this.userService.getUserRole();
// //
// //   public product: Product;
// //   public productSpecification: ProductSpecification;
// //   public productSpecificationType: ProductType;
// //
// //
// //   constructor(private userService: UserService, private productService: ProductsService, private router: Router) {}
// //
// //   private admin: boolean;
// //   constructor(private userService: UserService){
// //     this.admin = !this.userService.isAdmin();
// //     console.log(this.admin);
// //   }
// //   public isAdmin(): boolean {
// //     return this.admin;
// //   }
// //
// //
// //   createProduct(): void {
// //     this.productService
// //         .createProduct(this.product)
// //         .subscribe((response: String) => {
// //           console.log(response);
// //           alert('Product Toegevoegd')
// //           this.router.navigate(['/admin/Products']);
// //         }, (error) => {
// //           console.error('Fout bij het toevoegen van het product:', error);
// //           alert('Product niet toegevoegd, probeer later opnieuw')
// //         });
// //   }
// //
// //   // deleteProduct(): void {
// //   //   if (confirm('Weet je zeker dat je je account wilt verwijderen? Dit kan niet ongedaan worden gemaakt.')) {
// //   //     this.productService.de(this.user.email).subscribe(() => {
// //   //       console.log('Account succesvol verwijderd');
// //   //       alert('Account verwijderd')
// //   //     }, (error) => {
// //   //       console.error('Fout bij het verwijderen van het account:', error);
// //   //     });
// //   //   }
// //   // }
// // }
