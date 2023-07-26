import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataUserService {

  private _menuData: any;
  private _menuPermission: any;
  private _courseData: any = {
    key: null,
    insUid: null,
  };

  userData = new BehaviorSubject<any>({});
  currentData = this.userData.asObservable();
  constructor() { }

  changeData(newData: any) {
    this.userData.next(newData);
  }

  get menuData() : any{
    return this._menuData
  }

  set menuData(menuData: any){
    this._menuData = menuData;
  }

  get menuPermission() : any{
    return this._menuPermission;
  }

  set menuPermission(menuPermission: any){
    this._menuPermission = menuPermission;
  }

  get courseData() : any{
    return this._courseData;
  }

  set courseData(courseData: any){
    this._courseData = courseData;
  }
}
