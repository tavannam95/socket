import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';
import { Observable } from 'rxjs';
import { AuthConstant } from 'src/app/constants/auth.constant';
import { RoleGuard } from 'src/app/guard/role.guard';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { environment } from 'src/environments/environment';
import { CommonService } from './common.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {

  constructor(
    private http: HttpClient,
    private commonService: CommonService,
    private router: Router,
    private route : ActivatedRoute,
    private roleGuard: RoleGuard,
    @Inject(DOCUMENT) public document: any,
  ) {}

  getUserInfo(): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/api/userInfo/getUserInfo'
    );
    return this.http.get<any>(url, { headers: headers });
  }

  getFullToken() {
    return AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY)
  }

  public checkCredentials() {
    if (!this.isLogin()){
      if (!Cookie.check(AuthConstant.ACCESS_TOKEN_KEY)) {
        AuthenticationUtil.deleteAllCookie();
        this.redirectLogin();
        return false;
      }
    }
    return true;
  }

  logout(){
    AuthenticationUtil.deleteAllCookie();
    this.redirectLogin();
  }

  isLogin() {
    const params = new URLSearchParams(window.location.search);
    let accessToken = params.get('accessToken');
    if (accessToken) {
      AuthenticationUtil.saveToken(accessToken);
      // this.commonService.decryptAccessToken(accessToken, this.getPrivateKey()).subscribe(res=>{
      //   AuthenticationUtil.saveToken(res);
      //   console.log(res);
      // });
      return true;
    }
    return false;

  }

  getHostname(): string {
    return this.document.location.host;
  }

  redirectLogin(){
    this.commonService.keyInitForRsa().subscribe((res) => {
      let hostname = this.getHostname();
      AuthenticationUtil.savePrivateKeyToCookie(res.privateKey);
      window.location.href = environment.authURL +'?domain='+ hostname + '&key=' + res.privateKey;
    });
  }

  async getPermisRoleMenu(){
    let routerStateSnapshot = this.router.routerState.snapshot;
    let activatedRouteSnapshot = this.route.snapshot;
    return await this.roleGuard.getPermisRoleMenu(activatedRouteSnapshot, routerStateSnapshot);
  }

}
