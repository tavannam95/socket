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
export class Course0101Service {
  constructor(private http: HttpClient) {
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0101/search',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  searchByTag(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0101/searchByTag',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getLectureById(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0101/getLectureById/'+request,
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getInfoCourse(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0101/getInfoCourse/'+request,
      params
    );
    return this.http.get(url, { headers: headers });
  }

  save(request: Subject): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0101/save'
    );
    return this.http.post<Subject>(url, request, { headers: headers });
  }

  // update(request: Subject): Observable<any> {
  //   const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
  //   const url = ApiUrlUtil.buildQueryString(
  //     environment.apiURL + '/course/course0101/user/' + request.subjectId
  //   );
  //   return this.http.put<Subject>(url, request, { headers: headers });
  // }

  delete(subjectId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0101/delete/' + subjectId;
    return this.http.delete<Subject>(url, { headers: headers });
  }

}
