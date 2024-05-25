import {CanActivateFn} from "@angular/router";
import {UserService} from "../services/user.service";
import {inject} from "@angular/core";

export const adminGuard: CanActivateFn = (route, state) => {
    // heeft iemand een geldige token?

    const userService: UserService = inject(UserService);

    return userService.isStaffMember();

};