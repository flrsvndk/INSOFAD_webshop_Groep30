import {Component, Input} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UserService} from "../../../services/user.service";
import {User} from "../../../models/user.model";

@Component({
  selector: 'app-update-role',
  standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
  templateUrl: './update-role.component.html',
  styleUrl: './update-role.component.scss'
})
export class UpdateRoleComponent {
    protected admin: boolean;
    public loadedAdmin: boolean = false;
    @Input() public user: User;
    constructor(private userService: UserService) {
        this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
            this.admin = value;
            this.loadedAdmin = true;
        });
        console.log(this.admin);
    }
}
