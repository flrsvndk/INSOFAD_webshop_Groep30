import {CanActivateFn} from "@angular/router";
import {UserService} from "../services/user.service";
import {inject} from "@angular/core";
import {TokenService} from "../auth/token.service";

export const adminGuard: CanActivateFn = (route, state) => {
    // heeft iemand een geldige token?
    const tokenService: TokenService = inject(TokenService);

    if(tokenService.isValid()){
        const userService: UserService = inject(UserService);
        return !userService.isStaffMember();
    }
    return false;

};