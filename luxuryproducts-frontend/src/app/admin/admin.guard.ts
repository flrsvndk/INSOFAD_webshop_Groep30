import {CanActivateFn} from "@angular/router";
import {UserService} from "../services/user.service";
import {inject} from "@angular/core";
import {TokenService} from "../auth/token.service";


export const AdminGuard: CanActivateFn = () => {
    const tokenService: TokenService = inject(TokenService);
        if (tokenService.isValid()) {
            const userService : UserService= inject(UserService);
            return userService.giveAuthentication("STAFF");
        } else {
            return false;
        }
}
