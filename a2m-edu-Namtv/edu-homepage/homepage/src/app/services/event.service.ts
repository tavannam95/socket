import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RequestParam } from '../models/request-params';
import { ApiUrlUtil } from '../utils/api-url.util';
import { ParamUtil } from '../utils/param.util';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private http: HttpClient) { }

  getList(request : any): Observable<any> {
    var header = new HttpHeaders();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/getListEvent',
      params
    );
    return this.http.get(url, { headers: header });
  }

  getById(request : any): Observable<any> {
    var header = new HttpHeaders();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/getById/'+request,
      params
    );
    return this.http.get(url, { headers: header });
  }

}
