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
export class Edu0101Service {
  constructor(private http: HttpClient) {
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0101/search',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getListStudentInprogress(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0101/getListStudentInprogress',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getListStudentByUserUid(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0101/getListStudentByUserUid',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  save(request: TsstUser): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0101/save'
    );
    return this.http.post<TsstUser>(url, request, { headers: headers });
  }

  saveForExcel(request: TsstUser): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0101/saveForExcel'
    );
    return this.http.post<TsstUser>(url, request, { headers: headers });
  }

  update(request: TsstUser): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0101/user/' + request.userUid
    );
    return this.http.put<TsstUser>(url, request, { headers: headers });
  }

  delete(userUid: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0101/delete/' + userUid;
    return this.http.delete<TsstUser>(url, { headers: headers });
  }

  getTsstUserByUserInfoId(studentId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0101/getTsstUserByUserInfoId/' + studentId;
    return this.http.get<any>(url, { headers: headers });
  }

  importExcel(file: File): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    const formData: FormData = new FormData();

    formData.append('file', file);

    return this.http.post(`${environment.apiURL}/edu/edu0101/importExcel`, formData, { headers: headers });
  }

  exportExcel(request: TsstUser): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0101/exportExcel'
    );
    return this.http.post<TsstUser>(url, request, { headers: headers });
  }

  exportExcelList(request: TsstUser): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0101/exportExcelList'
    );
    return this.http.post<TsstUser>(url, request, { headers: headers });
  }

  deleteListCheck(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0101/deleteListCheck'
    return this.http.post<any>(url, request, { headers: headers });
  }

  // changeStatus(param: any): Observable<any>{
  //   const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  //   const params: RequestParam[] = ParamUtil.toRequestParams(param);
  //   const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/edu/edu0101/modifyStatus', params);
  //   return this.http.get<any>(url, { headers: header });
  //  }

}
