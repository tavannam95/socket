import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Bus0101Service {

  constructor(private http: HttpClient) { }

  getList(param: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0101/list', params);
    return this.http.get<any>(url, { headers: header });
  }
  getPage(param: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0101/page', params);
    return this.http.get<any>(url, { headers: header });
  }
  getListCustomer(){
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0101/listCustomer');
    return this.http.get<any>(url, { headers: header });
  }
  insert(param: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0101/insert', params);
    return this.http.get<any>(url, { headers: header });
   }
   modify(param: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0101/modify', params);
    return this.http.get<any>(url, { headers: header });
   }
   changeStatus(param: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0101/modifyStatus', params);
    return this.http.get<any>(url, { headers: header });
   }
}
