import {Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {authGuard} from './auth/auth.guard';
import {LoginComponent} from './auth/login/login.component';
import {RegisterComponent} from './auth/register/register.component';
import {ProductsComponent} from "./products/products.component";
import {CartComponent} from "./cart/cart.component";
import {ProductDetailComponent} from './products/product-detail/product-detail.component';
import {ProfileComponent} from "./profile/profile.component";
import {ProfileUpdateComponent} from "./profile/profile-update/profile-update.component";
import {OrderComponent} from "./order/order.component";
import {PaymentSuccessfulComponent} from "./payment-successful/payment-successful.component";
import { OrderHistoryComponent } from './profile/order-history/order-history.component';
import {AdminComponent} from "./admin/admin.component";
import {AdminGuard} from "./admin/admin.guard";
import {ProductAdminComponent} from "./admin/product-admin/product-admin.component";
import {NewProductComponent} from "./admin/product-admin/new-product/new-product.component";
import {PlacedOrdersAdminComponent} from "./admin/placed-orders-admin/placed-orders-admin.component";
import {UsersComponent} from "./admin/users/users.component";
import {AdminCategoriesComponent} from "./admin/product-admin/admin-categories/admin-categories.component";
import {UpdateProductComponent} from "./admin/product-admin/update-product/update-product.component";
import {UpdateRoleComponent} from "./admin/users/update-role/update-role.component";
import {RetourPageComponent} from "./retour-page/retour-page.component";
import {GiftcardsComponent} from "./giftcards/giftcards.component";
import {MyGiftcardsComponent} from "./my-giftcards/my-giftcards.component";
import {GiftcardsAdminComponent} from "./admin/giftcards-admin/giftcards-admin.component";
import {RetourRequestsAdminComponent} from "./admin/retour-requests-admin/retour-requests-admin.component";

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'auth/login', component: LoginComponent},
  {path: 'auth/register', component: RegisterComponent},
  {path: 'products', component: ProductsComponent},
  {path: 'cart', component: CartComponent},
  {path: 'products/:id', component: ProductDetailComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [authGuard]},
  {path: 'profile-update', component: ProfileUpdateComponent, canActivate: [authGuard]},
  {path: 'products/:id', component: ProductDetailComponent},
  {path: 'order', component: OrderComponent, canActivate: [authGuard]},
  {path: 'order-history', component: OrderHistoryComponent, canActivate: [authGuard] },
  {path: 'paymentsuccessful', component: PaymentSuccessfulComponent, canActivate: [authGuard]},
  {path: 'orders', component: OrderComponent, canActivate: [authGuard]},
  {path: 'retour-page/:id', component: RetourPageComponent, canActivate: [authGuard]},
  {path: 'giftcards', component: GiftcardsComponent},
  {path: 'my-giftcards', component: MyGiftcardsComponent, canActivate: [authGuard]},

  {path: 'admin', component: AdminComponent, canActivate: [AdminGuard]},
  {path: 'admin/products', component: ProductAdminComponent, canActivate: [AdminGuard]},
  {path:'admin/products/new', component: NewProductComponent, canActivate: [AdminGuard]},
  {path:'admin/products/update', component: UpdateProductComponent, canActivate: [AdminGuard]},
  {path:'admin/orders', component: PlacedOrdersAdminComponent, canActivate: [AdminGuard]},
  {path:'admin/users', component: UsersComponent, canActivate: [AdminGuard]},
  {path:'admin/users/role', component: UpdateRoleComponent, canActivate: [AdminGuard]},
  {path:'admin/categories', component: AdminCategoriesComponent, canActivate: [AdminGuard]},
  {path: 'admin/**', component: AdminComponent, canActivate:[AdminGuard]},

  {path: '**', component: HomeComponent}
];






