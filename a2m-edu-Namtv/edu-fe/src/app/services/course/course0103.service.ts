import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { Chapter } from 'src/app/model/course/chapter';

import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class Course0103Service {
  constructor(private http: HttpClient) {
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0103/search',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getSbjChapterSelect(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0103/getSbjChapterSelect',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  save(request: Chapter): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0103/save'
    );
    return this.http.post<Chapter>(url, request, { headers: headers });
  }

  saveOrdNo(request: Chapter): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0103/saveOrdNo'
    );
    return this.http.post<Chapter>(url, request, { headers: headers });
  }

  saveViewDoc(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0103/saveViewDoc'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  // update(request: Chapter): Observable<any> {
  //   const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
  //   const url = ApiUrlUtil.buildQueryString(
  //     environment.apiURL + '/course/course0103/save/' + request.chapterId
  //   );
  //   return this.http.put<Chapter>(url, request, { headers: headers });
  // }

  delete(chapterId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0103/delete/' + chapterId;
    return this.http.delete<Chapter>(url, { headers: headers });
  }

  getChapterById(chapterId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/course0103/getChapterById/' + chapterId;
    return this.http.get<any>(url, { headers: headers });
  }

  getChapterBySubjectId(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0103/getChapterBySubjectId', params
    );
    return this.http.get(url, { headers: headers });
  }

  saveCSM(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0103/saveCSM',
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  getInfoCourse(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0103/getInfoCourse/'+request,
      params
    );
    return this.http.get(url, { headers: headers });
  }


  searchByTag(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/course0103/searchByTag',
      params
    );
    return this.http.get(url, { headers: headers });
  }

}
