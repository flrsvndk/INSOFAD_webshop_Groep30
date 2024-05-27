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
import {adminGuard} from "./admin/admin.guard";
import {ProductAdminComponent} from "./admin/product-admin/product-admin.component";
import {PlacedOrdersAdminComponent} from "./admin/placed-orders-admin/placed-orders-admin.component";
import {UsersComponent} from "./admin/users/users.component";
import {AdminCategoriesComponent} from "./admin/admin-categories/admin-categories.component";

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

  {path: 'admin/Dashboard', component: AdminComponent, canActivate: [adminGuard]},
  {path: 'admin/Products', component: ProductAdminComponent, canActivate: [adminGuard]},
  {path:'admin/AllOrders', component: PlacedOrdersAdminComponent, canActivate: [adminGuard]},
  {path:'admin/allUsers', component: UsersComponent, canActivate: [adminGuard]},
  {path:'admin/Categories', component: AdminCategoriesComponent, canActivate: [adminGuard]},
  // {path:'admin/Products/newProduct', component: NewProductComponent, canActivate: [adminGuard]}

  {path: '**', component: HomeComponent}
];






