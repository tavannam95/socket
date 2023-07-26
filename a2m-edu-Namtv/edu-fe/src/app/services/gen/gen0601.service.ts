import { Injectable } from '@angular/core';
import { ApiUrlUtil } from './../../utils/api-url.util';
import { ParamUtil } from './../../utils/param.util';
import { RequestParam } from './../../model/common/request-param';
import { HeadersUtil } from './../../utils/headers.util';
import { environment } from './../../../environments/environment';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Gen0601Service {
  constructor(private httpClient: HttpClient) {}

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/gen/gen0601/search/', params);
    return this.httpClient.get<any[]>(url, { headers: headers });
  }

  saveLicense(request: any):Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/gen/gen0601/saveLicense';
    return this.httpClient.post(url, request, { headers: headers });
  }
}
