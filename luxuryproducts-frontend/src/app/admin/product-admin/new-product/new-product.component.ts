// import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
// import {Component, OnInit} from "@angular/core";
// import {UserService} from "../../../services/user.service";
// import {ProductsService} from "../../../services/products.service";
// import {Router} from "@angular/router";
// import {Product} from "../../../models/product.model";
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
// export class NewProductComponent implements OnInit {
//   productForm: FormGroup;
//
//   private role: String = this.userService.getUserRole();
//
//   constructor(private fb: FormBuilder, private userService: UserService, private productService: ProductsService, private router: Router) {}
//
//   public isAdmin(): boolean {
//     console.log(this.userService.isAdmin(this.role));
//     return !this.userService.isAdmin(this.role);
//   }
//
//   ngOnInit(): void {
//     this.productForm = this.fb.group({
//       name: ['', Validators.required],
//       description: [''],
//       price: ['', Validators.required],
//       specifications: this.fb.array([])
//     });
//   }
//
//   addSpecification() {
//     const specs = this.productForm.get('specifications') as FormArray;
//     specs.push(this.fb.group({
//       type: ['', Validators.required],
//       details: ['']
//     }));
//   }
//
//   removeSpecification(index: number) {
//     const specs = this.productForm.get('specifications') as FormArray;
//     specs.removeAt(index);
//   }
//
//   onSubmit() {
//     // Logic to handle form submission
//     if (this.productForm.valid) {
//       const productData: Product = this.productForm.value;
//       console.log(productData);
//     }
//   }
// }
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
// //   public isAdmin(): boolean {
// //     console.log(this.userService.isAdmin(this.role));
// //     return !this.userService.isAdmin(this.role);
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
