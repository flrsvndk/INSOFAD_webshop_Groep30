import {inject, Injectable, OnInit} from "@angular/core";
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, map, Observable, tap} from "rxjs";
import {environment} from "../../environments/environment";
import {User} from "../models/user.model";
import {AuthResponse} from "../auth/auth-response.model";
import {TokenService} from "../auth/token.service";
import {when} from "jquery";
import {Product} from "../models/product.model";
import {ProductsService} from "./products.service";
import {CartService} from "./cart.service";
import {waitForAsync} from "@angular/core/testing";

@Injectable({
  providedIn: 'root'
})
export class UserService {
    private baseUrl: string = environment.base_url + "/auth/user";
    public $userIsLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

    constructor(private http: HttpClient, private tokenService: TokenService) {
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

    public getAllUsers(): Observable<User[]>{
        return this.http.get<User[]>(`${this.baseUrl}/all`)
    }

    public deleteUser(email: string): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${email}`);
    }

    public getUserByEmail(): Observable<User> {
        return this.http.get<User>(`${this.baseUrl}`);
    }

    public getUserRole() {
        return this.http.get(`${this.baseUrl}/role`,{responseType: 'text'});
    }


    public giveAuthentication(wantedRole: String): Promise<boolean> {
        return new Promise((resolve, reject) => {
            this.getUserRole()
                .pipe(map((role: String) => role))
                .subscribe({
                    next: (userRole: String) => {
                        if (userRole != null) {
                            console.log(userRole);
                            if (userRole === wantedRole) {
                                resolve(true);
                            } else if (wantedRole === "STAFF" && userRole === "ADMIN") {
                                console.log("admin but staff");
                                resolve(true);
                            } else {
                                resolve(false);
                            }
                        } else {
                            resolve(false);
                        }
                    },
                    error: (err) => {
                        console.error(err);
                        reject(false);
                    }
                });
        });
    }
}
