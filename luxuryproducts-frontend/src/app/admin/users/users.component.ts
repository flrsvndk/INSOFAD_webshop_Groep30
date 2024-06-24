import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {RouterLink} from "@angular/router";
import {User} from "../../models/user.model";
import {SidepanelComponent} from "../sidepanel/sidepanel.component";
import {CurrencyPipe, DatePipe, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {CdkTextColumn} from "@angular/cdk/table";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {CdkDragPlaceholder} from "@angular/cdk/drag-drop";

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    RouterLink,
    SidepanelComponent,
    CurrencyPipe,
    DatePipe,
    NgIf,
    CdkTextColumn,
    FormsModule,
    ReactiveFormsModule,
    CdkDragPlaceholder,
    NgOptimizedImage,
    NgForOf
  ],
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit{

  protected admin: boolean;
  public loadedAdmin: boolean = false;
  public userList: User[];
  public roles: string[] = ["user", "staff", "admin"];
  public newRoleForm: FormGroup;



  constructor(private userService: UserService, private fb: FormBuilder) {
  }

  public ngOnInit() {
    this.userService.giveAuthentication("ADMIN").then((value: boolean) => {
      this.admin = value;
      this.loadedAdmin = true;
    });

    this.userService.getAllUsers().subscribe((users: User[]) => {
      this.userList = users;
    });

    console.log(this.admin);

    this.newRoleForm = this.fb.group({
      email: ['', Validators.required],
      newRole: ['', Validators.required]
    });
  }

  public onSubmit(index: number){
    this.newRoleForm.get('email')?.setValue(this.userList.at(index)?.email)
    console.log(this.newRoleForm.value);
    this.userService.updateUserRole(this.newRoleForm.value).subscribe((response: string)=>{
      console.log(response);
    })
  }
}
