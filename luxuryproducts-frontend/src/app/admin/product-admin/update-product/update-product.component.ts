import {Component, Input} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Product} from "../../../models/product.model";
import {ProductSpecification} from "../../../models/product-specification.model";
import {ProductType} from "../../../models/product-type.model";
import {UserService} from "../../../services/user.service";
import {ProductsService} from "../../../services/products.service";
import {Router} from "@angular/router";
import {AuthResponse} from "../../../auth/auth-response.model";

@Component({
  selector: 'app-update-product',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './update-product.component.html',
  styleUrl: './update-product.component.scss'
})
export class UpdateProductComponent {

  @Input() public product!: Product;
  public productSpecification: ProductSpecification;
  public productSpecificationType: ProductType;

    protected admin: boolean;
    public loadedAdmin: boolean = false;
    constructor(private userService: UserService, private productService: ProductsService, private router: Router) {
        this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
            this.admin = value;
            this.loadedAdmin = true;
        });
        console.log(this.admin);
    }


  ngOnInit(): void {

  }


  // updateProduct(): void {
  //   this.productService
  //       .updateProductByIndex(this.product)
  //       .subscribe((authResponse: AuthResponse) => {
  //         console.log('Gebruikersgegevens succesvol bijgewerkt:', authResponse);
  //         alert('Gebruikersgegevens bijgewerk')
  //         this.router.navigate(['/profile']);
  //       }, (error) => {
  //         console.error('Fout bij het bijwerken van de gebruikersgegevens:', error);
  //         alert('Gebruikersgegevens niet bijgewerkt, probeer later opnieuw')
  //       });
  //    }

  // deleteProduct(): void {
  //   if (confirm('Weet je zeker dat je je account wilt verwijderen? Dit kan niet ongedaan worden gemaakt.')) {
  //     this.productService.delete(this.user.email).subscribe(() => {
  //       console.log('Account succesvol verwijderd');
  //       alert('Account verwijderd')
  //     }, (error) => {
  //       console.error('Fout bij het verwijderen van het account:', error);
  //     });
  //   }
  // }
}
