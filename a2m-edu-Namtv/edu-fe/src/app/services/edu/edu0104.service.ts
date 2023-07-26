import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { Equiqment } from 'src/app/model/sys/equipment';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Injectable()
export class Edu0104Service {

  constructor(private http: HttpClient) {
  }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0104/save';
    return this.http.post<any>(url, request, { headers: headers });
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0104/search',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  delete(userUid: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0104/delete/' + userUid;
    return this.http.delete<Equiqment>(url, { headers: headers });
  }

  deleteListCheck(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0104/deleteListCheck'
    return this.http.post<any>(url, request, { headers: headers });
  }

}
