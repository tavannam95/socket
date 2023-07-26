import { ApiUrlUtil } from './../../utils/api-url.util';
import { ParamUtil } from './../../utils/param.util';
import { RequestParam } from './../../model/common/request-param';
import { HeadersUtil } from './../../utils/headers.util';
import { Target } from './../../model/gen/target';
import { environment } from './../../../environments/environment';

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Gen0101Service {
  constructor(private httpClient: HttpClient) {}

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/gen0101/search',
      params
    );
    return this.httpClient.get(url, { headers: headers });
  }

  addNewTarget(request: Target): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/gen0101/save'
    );
    return this.httpClient.post<any>(url, request, { headers: headers });
  }

  updateTarget(request: Target): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    return this.httpClient.put<any>(
      environment.apiURL + '/gen0101/update',
      request,
      { headers: headers }
    );
  }

  deleteTarget(targetId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/gen0101/delete/' + targetId;
    return this.httpClient.delete<any>(url, { headers: headers });
  }

  checkConnection(request: Target): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.manageApiUrl + '/target/checkConnection'
    );
    return this.httpClient.post<any>(url, request, { headers: headers });
  }
}
