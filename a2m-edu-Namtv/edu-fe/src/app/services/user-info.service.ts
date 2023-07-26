import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { HeadersUtil } from '../utils/headers.util';
import { ApiUrlUtil } from '../utils/api-url.util';
import { ParamUtil } from '../utils/param.util';
import { RequestParam } from '../model/common/request-param';

@Injectable({
  providedIn: 'root',
})
export class UserInfoService {


  constructor(private http: HttpClient) {}



  get(): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    // const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/userInfo/getUserInfo'
    );
    return this.http.get<any>(url, { headers: headers });
  }
  getAvtUserByEmail(param: any): Observable<any>{
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/userInfo/getAvt', params);
    return this.http.get<any[]>(url, { headers: headers });
  }
}
