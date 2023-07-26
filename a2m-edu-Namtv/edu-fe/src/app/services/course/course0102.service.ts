import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ResponseData } from 'src/app/model/common/response-data';
import { Subject } from 'src/app/model/course/subject';

import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';



@Injectable()
export class Course0102Service {
  constructor(private http: HttpClient) {
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0102/search',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  save(request: Subject): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0102/save'
    );
    return this.http.post<Subject>(url, request, { headers: headers });
  }

  update(request: Subject): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0102/user/' + request.subjectId
    );
    return this.http.put<Subject>(url, request, { headers: headers });
  }

  delete(subjectId: String, courseId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0102/delete/' + subjectId +'/' +courseId;
    return this.http.delete<Subject>(url, { headers: headers });
  }

  setTrueDeleteFlag(subjectId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0102/setTrueDeleteFlag/' + subjectId;
    return this.http.delete<Subject>(url, { headers: headers });
  }

  getSubjectById(subjectId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0102/getSubjectById/' + subjectId;
    return this.http.get<any>(url, { headers: headers });
  }

  getSubjectHistory(subjectId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0102/getSubjectHistory/' + subjectId;
    return this.http.get<any>(url, { headers: headers });
  }

  getLectureScheduleBySubjectId(subjectId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0102/getLectureScheduleBySubjectId/' + subjectId;
    return this.http.get<any>(url, { headers: headers });
  }

  getSubjectListById(courseId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0102/getSubjectListById/' + courseId;
    return this.http.get<any>(url, { headers: headers });
  }

  saveCSM(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0102/saveCSM',
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  doChooseSubject(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0102/doChooseSubject',
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  exportPDFLectureSchedule(subjectId: Number): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0102/getLectureScheduleBySubjectId/exportPDF/' + subjectId;
    return this.http.get<any>(url, { headers: headers });
  }

  exportPDF(subjectId: Number): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0102/exportPDF/' + subjectId;
    return this.http.get<any>(url, { headers: headers });
  }

  exportDoc(subjectId: Number): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0102/exportDoc/' + subjectId;
    return this.http.get<any>(url, { headers: headers });
  }

}

