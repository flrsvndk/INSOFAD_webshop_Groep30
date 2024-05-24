import {Injectable} from "@angular/core";
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable, tap} from "rxjs";
import {environment} from "../../environments/environment";
import {User} from "../models/user.model";
import {AuthResponse} from "../auth/auth-response.model";
import {TokenService} from "../auth/token.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = environment.base_url + "/auth/user";

  public $userIsLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private tokenService: TokenService) {
  }

  public getUserByEmail(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}`);
  }

  public updateUser(user: User): Observable<AuthResponse> {
    return this.http
        .put<AuthResponse>(`${this.baseUrl}`, user)
        .pipe(
            tap((authResponse: AuthResponse) => {
              this.tokenService.storeToken(authResponse.token);
              this.$userIsLoggedIn.next(true);
            })
        );
  }

  public deleteUser(email: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${email}`);
  }
}
