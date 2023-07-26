import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class Sys0104Service {
  constructor(private http: HttpClient) {}

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/sys/sys0104/search', params);
    return this.http.get<any>(url, { headers: headers });
  }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/sys/sys0104/save';
    return this.http.post<any>(url, request, { headers: headers });
  }

  create(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/sys/sys0104/create';
    return this.http.post<any>(url, request, { headers: headers });
  }

  update(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/sys/sys0104/update';
    return this.http.put<any>(url, request, { headers: headers });
  }

  delete(commCd: string): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/sys/sys0104/delete?commCd=' + commCd;
    return this.http.get<any>(url, { headers: headers });
  }
}
