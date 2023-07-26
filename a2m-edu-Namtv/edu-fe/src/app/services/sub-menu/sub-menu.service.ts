import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubMenuService {
  constructor(private http: HttpClient) { }
  subMenu = new BehaviorSubject<any>({});
  currentData = this.subMenu.asObservable();

  changeData(newData: any) {

    this.subMenu.next(newData);
  }
}
