import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { Subject } from 'src/app/model/course/subject';

import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';



@Injectable()
export class StandardService {
  constructor(private http: HttpClient) {
  }

  getStandBySubjectId(subjectId: any, standType:any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/standard/getStandardBySubjectId/' + subjectId +'/' + standType;
    return this.http.get(url, { headers: headers });
  }

  getStandardHistoryBySubjectId(subjectId: any, standType:any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/standard/getStandardHistoryBySubjectId/' + subjectId +'/' + standType;
    return this.http.get(url, { headers: headers });
  }

  getListStandBySubjectId(subjectId: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/standard/getListStandardBySubjectId/' + subjectId;
    return this.http.get(url, { headers: headers });
  }

  getListStandHistoryBySubjectId(subjectId: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/standard/getListStandHistoryBySubjectId/' + subjectId;
    return this.http.get(url, { headers: headers });
  }

  getListStandardNoteBySubjectId(subjectId: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/standard/getListStandardNoteBySubjectId/' + subjectId;
    return this.http.get(url, { headers: headers });
  }

  getLectureScheduleBySubjectId(subjectId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/standard/getLectureScheduleBySubjectId/' + subjectId;
    return this.http.get<any>(url, { headers: headers });
  }

  getLectureScheduleHistoryBySubjectId(subjectId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/standard/getLectureScheduleHistoryBySubjectId/' + subjectId;
    return this.http.get<any>(url, { headers: headers });
  }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/standard/save'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }
  saveNote(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/standard/saveNote'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  saveNoteHistory(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/standard/saveNoteHistory'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  exportPDF(subjectId: Number): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/standard/exportPDF/' + subjectId;
    return this.http.get<any>(url, { headers: headers });
  }

  exportDoc(subjectId: Number): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/standard/exportDoc/' + subjectId;
    return this.http.get<any>(url, { headers: headers });
  }

}
