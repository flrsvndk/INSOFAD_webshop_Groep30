import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user.model";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {AuthService} from "../../auth/auth.service";
import {AuthResponse} from "../../auth/auth-response.model";
import {Adress} from "../../models/adress.model";

@Component({
  selector: 'app-profile-update',
  templateUrl: './profile-update.component.html',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    RouterLink,
  ],
  styleUrls: ['./profile-update.component.scss']
})
export class ProfileUpdateComponent implements OnInit {
  public user: User;
  public adress: Adress;

  public updateUserForm: FormGroup;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    this.userService.getUserByEmail().subscribe((user: User) => {
      this.user = user;
    });
  }

  updateUser(): void {
    this.userService
        .updateUser(this.user)
        .subscribe((authResponse: AuthResponse) => {
          console.log('Gebruikersgegevens succesvol bijgewerkt:', authResponse);
          alert('Gebruikersgegevens bijgewerk')
          this.router.navigate(['/profile']);
        }, (error) => {
          console.error('Fout bij het bijwerken van de gebruikersgegevens:', error);
          alert('Gebruikersgegevens niet bijgewerkt, probeer later opnieuw')
        });
  }

  deleteAccount(): void {
    if (confirm('Weet je zeker dat je je account wilt verwijderen? Dit kan niet ongedaan worden gemaakt.')) {
      this.userService.deleteUser(this.user.email).subscribe(() => {
        console.log('Account succesvol verwijderd');
        alert('Account verwijderd')
      }, (error) => {
        console.error('Fout bij het verwijderen van het account:', error);
      });
    }
  }
}
