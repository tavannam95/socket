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
export class Bus0201Service {

  constructor(private http: HttpClient) { }

  getList(): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0201/list');
    return this.http.get<any>(url, { headers: header });
  }
  getPage(param: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0201/page', params);
    return this.http.get<any>(url, { headers: header });
  }
  insert(param: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0201/insert', params);
    return this.http.get<any>(url, { headers: header });
  }
  modify(param: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0201/modify', params);
    return this.http.get<any>(url, { headers: header });
  }
  delete(param: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0201/delete', params);
    return this.http.get<any>(url, { headers: header });

  }
}
