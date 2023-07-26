import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { RequestParam } from 'src/app/model/common/request-param';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';



@Injectable()
export class Sys0101Service {
  constructor(private http: HttpClient) {
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/sys/sys0101/search', params);
    return this.http.get<any>(url, { headers: headers });
  }

  create(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/sys/sys0101/create';
    return this.http.post<any>(url, request, { headers: headers });
  }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/sys/sys0101/save';
    return this.http.post<any>(url, request, { headers: headers });
  }

  update(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/sys/sys0101/update';
    return this.http.put<any>(url, request, { headers: headers });
  }

  delete(menuId: string): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/sys/sys0101/delete?menuId=' + menuId;
    return this.http.get<any>(url, { headers: headers });
  }

  updateStatus(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/sys/sys0101/update/status';
    return this.http.put<any>(url, request, { headers: headers });
  }

  getMenuByUser(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/sys/sys0101/getMenuByUser', params);
    return this.http.get<any>(url, { headers: headers });
  }

  checkViewRecordBookMenu(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/sys/sys0101/checkViewRecordBookMenu', params);
    return this.http.get<any>(url, { headers: headers });
  }

  getRoles(): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/sys/sys0101/getRoles';
    return this.http.get<any>(url, { headers: headers });
  }

  getUserRoles(): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/sys/sys0101/getUserRoles');
    return this.http.get<any>(url, { headers: headers });
  }

  getCourseById(courseId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0102/getCourseById/' + courseId;
    return this.http.get<any>(url, { headers: headers });
  }
}
