import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {RouterLink} from "@angular/router";
import {SidepanelComponent} from "../sidepanel/sidepanel.component";

@Component({
  selector: 'app-product-admin',
  standalone: true,
    imports: [
        RouterLink,
        SidepanelComponent
    ],
  templateUrl: './product-admin.component.html',
  styleUrl: './product-admin.component.scss'
})
export class ProductAdminComponent {

    protected admin: boolean;
    public loadedAdmin: boolean = false;
    constructor(private userService: UserService) {
        this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
            this.admin = value;
            this.loadedAdmin = true;
        });
        console.log(this.admin);
    }
}
