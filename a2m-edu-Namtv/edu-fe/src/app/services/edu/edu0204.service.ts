import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { Course } from 'src/app/model/sys/Course';
import { schedule } from 'src/app/model/sys/schedule';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';



@Injectable()
export class Edu0204Service {
  constructor(private http: HttpClient) {
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0204/search', params
    );
    return this.http.get(url, { headers: headers });
  }
  checkDuplicates(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0204/checkDuplicates', params
    );
    return this.http.get(url, { headers: headers });
  }
  getScheduleById(courseId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0204/getScheduleById/' + courseId;
    return this.http.get<any>(url, { headers: headers });
  }

  getClassList(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0204/getClassList', params
    );
    return this.http.get(url, { headers: headers });
  }

  getRoomList(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0204/getRooms', params
    );
    return this.http.get(url, { headers: headers });
  }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0204/save',
    );
    return this.http.post<schedule>(url, request, { headers: headers });
  }

  delete(key: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0204/delete/' + key;
    return this.http.delete<Course>(url, { headers: headers });
  }

  deleteListCheck(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0204/deleteListCheck'
    return this.http.post<any>(url, request, { headers: headers });
  }

  getListSubjectByCourseId(courseId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0204/getListSubjectByCourseId/' + courseId;
    return this.http.get<any>(url, { headers: headers });
  }

  getScheduleList(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);

    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0204/getScheduleList',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  importExcel(file: File): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    const formData: FormData = new FormData();

    formData.append('file', file);

    return this.http.post(`${environment.apiURL}/edu/edu0204/importExcel`, formData, { headers: headers });
  }

  exportExcel(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0204/exportExcel'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  exportExcelList(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0204/exportExcelList'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

}
