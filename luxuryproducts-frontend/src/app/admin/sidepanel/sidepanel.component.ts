import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-sidepanel',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './sidepanel.component.html',
  styleUrl: './sidepanel.component.scss'
})
export class SidepanelComponent {

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
