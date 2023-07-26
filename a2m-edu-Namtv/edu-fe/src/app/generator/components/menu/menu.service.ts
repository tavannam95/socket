import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { Observable } from 'rxjs';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { RequestParam } from 'src/app/model/common/request-param';
import { ParamUtil } from 'src/app/utils/param.util';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';

@Injectable()
export class MenuService {

  constructor(private http: HttpClient) { }

  findAll(request: any): Observable<any[]> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/sys/sys0101/getAllMenu', params);
    return this.http.get<any[]>(url, { headers: headers });
  }

  findByUserId(request: any): Observable<any[]> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/sys/sys0101/getMenuByUser', params);
    return this.http.get<any[]>(url, { headers: headers });
  }

}
