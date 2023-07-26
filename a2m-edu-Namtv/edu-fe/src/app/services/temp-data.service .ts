import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TempDataService {

  private _postDiscusBackUrl: any;
  constructor() { }

  get postDiscusBackUrl() : any{
    return this._postDiscusBackUrl;
  }

  set postDiscusBackUrl(postDiscusBackUrl: any){
    this._postDiscusBackUrl = postDiscusBackUrl;
  }
}
