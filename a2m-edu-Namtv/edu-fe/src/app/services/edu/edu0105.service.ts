import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ResponseData } from 'src/app/model/common/response-data';
import { TsstUser } from 'src/app/model/sys/tsst-user';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Injectable()
export class Edu0105Service {
  constructor(private http: HttpClient) {
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0105/search',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  save(request: TsstUser): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0105/save'
    );
    return this.http.post<TsstUser>(url, request, { headers: headers });
  }

  update(request: TsstUser): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0105/user/' + request.userUid
    );
    return this.http.put<TsstUser>(url, request, { headers: headers });
  }

  delete(userUid: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0105/delete/' + userUid;
    return this.http.delete<TsstUser>(url, { headers: headers });
  }

  deleteListCheck(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0105/deleteListCheck'
    return this.http.post<any>(url, request, { headers: headers });
  }

  // changeStatus(param: any): Observable<any>{
  //   const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  //   const params: RequestParam[] = ParamUtil.toRequestParams(param);
  //   const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/edu/edu0105/modifyStatus', params);
  //   return this.http.get<any>(url, { headers: header });
  //  }

}
