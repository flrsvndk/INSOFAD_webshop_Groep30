import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterOutlet} from '@angular/router';
import {AuthModule} from './auth/auth.module';
import {CoreModule} from './core/core.module';
import {ProductsModule} from "./products/products.module";
import {FooterComponent} from "./core/footer/footer.component";

@Component({
  selector: 'app-root',
  standalone: true,
    imports: [CommonModule, RouterOutlet, AuthModule, CoreModule, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'infirfs-auth-project';
}
