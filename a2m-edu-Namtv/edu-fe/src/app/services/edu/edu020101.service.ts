import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Edu020101Service {

  constructor(private http: HttpClient) {
  }

  searchStudent(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0201/searchStudent',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getStudentByClass(ClassId: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0201/searchStudentByClassId/' + ClassId;
    return this.http.get(url, { headers: headers });
  }

  getClassByStudentId(studentId: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0201/searchClassByStudentId/' + studentId;
    return this.http.get(url, { headers: headers });
  }

  getCourseByStudentId(studentId: any) : Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0201/getCourseByStudentId/' + studentId;
    return this.http.get(url, { headers: headers });
  }

}
