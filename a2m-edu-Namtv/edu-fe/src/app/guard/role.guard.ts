import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
} from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { CommonConstant } from '../constants/common.constant';
import { DataUserService } from '../services/data-user.service';
import { Sys0101Service } from '../services/sys/sys0101.service';
import { AuthenticationUtil } from '../utils/authentication.util';

@Injectable()
export class RoleGuard implements CanActivate {
  constructor(
    public router: Router,
    private menuService: Sys0101Service,
    private dataUserService: DataUserService,
  ) {}

  async canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<boolean> {
    let url = state.url;
    let urlParts = url.split('?');
    let path = urlParts[0];

    if(url.startsWith("/course/course010") && false){
      let isMyCourseOrAdmin = await this.isMyCourseOrAdmin(state);
      if(!isMyCourseOrAdmin){
        this.router.navigate(['/403']);
        return false;
      }
    }
    
    // let roles = await this.menuService.getRoles().toPromise();
    let roles = await lastValueFrom(this.menuService.getRoles());

    this.dataUserService.menuData = roles;
    if (Array.isArray(roles) || roles.length > 0) {
      try {
        // if (url.startsWith('/')) {
        //     url = url.substring(1);
        // }
        let menus: string[] = [];

        let menuPermission = {
          readYn: false,
          wrtYn: false,
          modYn: false,
          delYn: false,
          pntYn: false,
          excDnYn: false,
        };

        roles.forEach((role: any) => {
          let roleByMenu = role.split('$');
          menus.push(roleByMenu[0]);

          let menu = roleByMenu[0];
          let perm = roleByMenu[1];

          let arrPerm = perm.split(':');

          if (roleByMenu[0] !== '' && roleByMenu[0] !== '/') {
            let containPerm = url.startsWith(menu) && perm.includes('Y');
            if (containPerm) {
              menuPermission.readYn = menuPermission.readYn || arrPerm[PERMISSION.readYn] == 'Y';
              menuPermission.wrtYn = menuPermission.wrtYn || arrPerm[PERMISSION.wrtYn] == 'Y';
              menuPermission.modYn = menuPermission.modYn || arrPerm[PERMISSION.modYn] == 'Y';
              menuPermission.delYn = menuPermission.delYn || arrPerm[PERMISSION.delYn] == 'Y';
              menuPermission.pntYn = menuPermission.pntYn || arrPerm[PERMISSION.pntYn] == 'Y';
              menuPermission.excDnYn = menuPermission.excDnYn || arrPerm[PERMISSION.excDnYn] == 'Y';
            }
          }
        });

        this.dataUserService.menuPermission = menuPermission;

        let hasPerm = roles.some((role: any) => {
          if (role) {
            let roleByMenu = role.split('$');
            menus.push(roleByMenu[0]);

            // "" empty url for Dashboard
            if (roleByMenu[0] !== '' && roleByMenu[0] !== '/') {
              return (
                url.startsWith(roleByMenu[0]) && roleByMenu[1].includes('Y')
              );
            }
            // else {
            // if(roleByMenu[0] == "/dashboard" && roleByMenu[1].includes('Y')){
            //   this.router.navigate(['/dashboard']);
            // } else if (roleByMenu[0] == "/client-dashboard" && roleByMenu[1].includes('Y')) {
            //    this.router.navigate(['/client-dashboard']);
            // }
            // }
          }
        });

        // if (path == '/dashboard' && menus.indexOf('/client-dashboard') > -1) {
        //   this.router.navigate(['/client-dashboard']);
        //   return false;
        // }
        if (path == '' || path == '/') {
          if (menus.indexOf('/dashboard') > -1) {
            this.router.navigate(['/dashboard']);
          } else {
            this.router.navigate(['/client-dashboard']);
          }
          return false;
        }

        if (hasPerm) return true;
      } catch (err) {}
    }
    this.router.navigate(['/403']);
    return false;
  }

  async isMyCourseOrAdmin(state: RouterStateSnapshot){
    let result: Boolean = true;
    const userUid = AuthenticationUtil.getUserUid();
    let strRoles = AuthenticationUtil.getUserRole();
    let arrRoles = strRoles.split("_");
    let isAdmin = arrRoles.includes(CommonConstant.ROLE_SYS_ADMIN);
    if(isAdmin) return true;

    const child = state.root.firstChild;
    if(child){
      const param = child.queryParams;
      const currentCourseId = param['courseId'];
      const beforeCourse = this.dataUserService.courseData;

      if( Number(currentCourseId) == Number(beforeCourse.key)){
        result = userUid == beforeCourse.insUid;
        
      }else{
        let otherCourse = await lastValueFrom(this.menuService.getCourseById(currentCourseId));
        this.dataUserService.courseData = otherCourse[CommonConstant.DETAIL_KEY];;
        result = userUid == this.dataUserService.courseData.insUid;
        
      }
      
    }
    return result;
  }

  async getPermisRoleMenu(//not use
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<any> {
    let url = state.url;
    let urlParts = url.split('?');
    let path = urlParts[0];

    let result = {
      readYn: false,
      wrtYn: false,
      modYn: false,
      delYn: false,
      pntYn: false,
      excDnYn: false,
    };
    let hasPerm = false;
    // let roles = await this.menuService.getRoles().toPromise();
    // let roles = await lastValueFrom(this.menuService.getRoles());
    let roles = this.dataUserService.menuData;

    if (Array.isArray(roles) || roles.length > 0) {
      try {
        // if (url.startsWith('/')) {
        //     url = url.substring(1);
        // }
        let menus: string[] = [];

        roles.forEach((role: any) => {
          let roleByMenu = role.split('$');
          menus.push(roleByMenu[0]);

          let menu = roleByMenu[0];
          let perm = roleByMenu[1];

          let arrPerm = perm.split(':');

          if (roleByMenu[0] !== '' && roleByMenu[0] !== '/') {
            let containPerm = url.startsWith(menu) && perm.includes('Y');
            if (containPerm) {
              hasPerm = true;
              result.readYn = result.readYn || arrPerm[PERMISSION.readYn] == 'Y';
              result.wrtYn = result.wrtYn || arrPerm[PERMISSION.wrtYn] == 'Y';
              result.modYn = result.modYn || arrPerm[PERMISSION.modYn] == 'Y';
              result.delYn = result.delYn || arrPerm[PERMISSION.delYn] == 'Y';
              result.pntYn = result.pntYn || arrPerm[PERMISSION.pntYn] == 'Y';
              result.excDnYn = result.excDnYn || arrPerm[PERMISSION.excDnYn] == 'Y';
            }
          }
        });

        /*let hasPerm = roles.some((role: any) => {
              if (role) {
                  let roleByMenu = role.split('$');
                  menus.push(roleByMenu[0]);

                  // "" empty url for Dashboard
                  if (roleByMenu[0] !== "" && roleByMenu[0] !== "/") {
                      return url.startsWith(roleByMenu[0]) && roleByMenu[1].includes('Y');
                  }
                  // else {
                    // if(roleByMenu[0] == "/dashboard" && roleByMenu[1].includes('Y')){
                    //   this.router.navigate(['/dashboard']);
                    // } else if (roleByMenu[0] == "/client-dashboard" && roleByMenu[1].includes('Y')) {
                    //    this.router.navigate(['/client-dashboard']);
                    // }
                  // }
              }
            });*/

        if (path == '/dashboard' && menus.indexOf('/client-dashboard') > -1) {
          this.router.navigate(['/client-dashboard']);
          return {
            readYn: false,
            wrtYn: false,
            modYn: false,
            delYn: false,
            pntYn: false,
            excDnYn: false,
          };
        }
        if (path == '' || path == '/') {
          if (menus.indexOf('/dashboard') > -1) {
            this.router.navigate(['/dashboard']);
          } else {
            this.router.navigate(['/client-dashboard']);
          }
          return {
            readYn: false,
            wrtYn: false,
            modYn: false,
            delYn: false,
            pntYn: false,
            excDnYn: false,
          };
        }

        if (hasPerm) return result;
      } catch (err) {}
    }
    this.router.navigate(['/403']);
    return {
      readYn: false,
      wrtYn: false,
      modYn: false,
      delYn: false,
      pntYn: false,
      excDnYn: false,
    };
  }//not use
}

export enum PERMISSION {
  readYn = 0,
  wrtYn = 1,
  modYn = 2,
  delYn = 3,
  pntYn = 4,
  excDnYn = 5,
}
