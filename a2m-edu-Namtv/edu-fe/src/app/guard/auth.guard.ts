import { Injectable } from '@angular/core';
import {ActivatedRoute, ActivatedRouteSnapshot, CanActivate, CanLoad, Route, RouterStateSnapshot} from '@angular/router';
import { Cookie } from 'ng2-cookies/cookie';
import { AuthConstant } from '../constants/auth.constant';
import { AuthenticationService } from '../services/common/authentication.service';
import { AuthenticationUtil } from '../utils/authentication.util';

@Injectable()
export class AuthGuard implements CanActivate, CanLoad {

  constructor(
    private authService: AuthenticationService,
    private route: ActivatedRoute
  ) {
    this.saveAccessToken();
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.checkLogin();
  }

  canLoad(route: Route): boolean {
    return this.checkLogin();
  }

  private checkLogin(): boolean {
    return this.authService.checkCredentials();
  }

  saveAccessToken(){
    let accessToken = this.route.snapshot.params["accessToken"]
    if (accessToken){
        AuthenticationUtil.saveToken(accessToken);
    }
  }
}
