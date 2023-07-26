import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { Equiqment } from 'src/app/model/sys/equipment';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Edu0201Service {
  constructor(private http: HttpClient) {
  }
  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0201/save';
    return this.http.post<any>(url, request, { headers: headers });
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0201/search',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getListClassInprogress(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0201/getListClassInprogress',
      params
    );
    return this.http.get(url, { headers: headers });
  }


  getClassById(classId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0201/getClassById/' + classId;
    return this.http.get<any>(url, { headers: headers });
  }


  getListClassByUserUid(userUid: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0201/getListClassByUserUid/' + userUid;
    return this.http.get<any>(url, { headers: headers });
  }

  delete(classId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0201/delete/' + classId;
    return this.http.delete<any>(url, { headers: headers });
  }

  deleteListCheck(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0201/deleteListCheck'
    return this.http.post<any>(url, request, { headers: headers });
  }

}
