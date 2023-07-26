import { Pipe, PipeTransform } from '@angular/core';
import { Sys0101Service } from '../services/sys/sys0101.service';

@Pipe({
  name: 'hasRole'
})
export class HasRolePipe implements PipeTransform {

  constructor(
    private menuService: Sys0101Service
  ) {
  }

  transform(role: string, detail?: string): Promise<boolean> {
    return this.hasRole(role, detail);
  }

  async hasRole(role: string, detail: any): Promise<boolean> {
    const roleStr = await this.menuService.getRoles().toPromise();
    if (!roleStr) {
      return false;
    }
    const roles = roleStr.split(',');
    let detailExp = '';
    if (detail) {
      detailExp = detail.replace(/x/g, '[0-1]');
    }
    const regexStr = '^' + role + '_[0-1]*' + detailExp + '$';
    const regex = new RegExp(regexStr);
    return roles.some((r: any) => regex.test(r.trim()));
  }
}
