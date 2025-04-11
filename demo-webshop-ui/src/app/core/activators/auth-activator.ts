import { Injectable } from "@angular/core";
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from "@angular/router";
import { Observable } from "rxjs";
import { UserService } from "../services";
import { map } from 'rxjs/operators';

@Injectable()
export class AuthActivator implements CanActivate {
  constructor(private userService: UserService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean|UrlTree>|Promise<boolean|UrlTree>|boolean|UrlTree {
    
    return this.userService.isAuthenticated.pipe(map(
      value => {
        if (value) {
          return value;
        } else {
          return this.router.parseUrl('/');
        }
      }
    ));
  }
}