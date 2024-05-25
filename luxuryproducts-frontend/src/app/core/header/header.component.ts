import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../auth/auth.service';
import {Router} from '@angular/router';
import {CartService} from "../../services/cart.service";
import {ProductType} from "../../models/product-type.model";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user.model";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {

  public showHotCupIcon: boolean = false;
  public userIsLoggedIn: boolean = false;
  public user: User;
  public amountOfProducts: number = 0;

  constructor(private authService: AuthService, private router: Router, private cartService: CartService, private userService: UserService) {
  }

  public ngOnInit(): void {
    this.checkLoginState();
    this.cartService.$productInCart.subscribe((products: ProductType[]) => {
      this.amountOfProducts = products.reduce((total, product) => total + product.amount, 0);
    })

    this.userService
        .getUserByEmail()
        .subscribe((user: User) => {
          this.user = user;
        });
  }



  public onLogout(): void {
    this.authService.logOut();
    this.router.navigate(['/']);
  }

  public checkLoginState(): void {
    this.authService
      .$userIsLoggedIn
      .subscribe((loginState: boolean) => {
        this.userIsLoggedIn = loginState;
      });
  }
}
