import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'checkRole'
})
export class CheckRolePipe implements PipeTransform {

  roleDefine = {
    R: 0,
    W: 1,
    M: 2,
    D: 3,
    P: 4,
    E: 5,
    MNG: 6

  }
  transform(prgId: string, role: string): boolean {
    return this.checkRole(prgId, role);
  }


  checkRole(prgId: string, role: string) {
    return true;
  }
}
