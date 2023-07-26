import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';

import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';



@Injectable()
export class PostService {
  constructor(private http: HttpClient) {
  }

  searchPost(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/community/post/searchPost',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  searchNoti(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/community/post/searchNoti',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  checkedNoti(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/community/post/checkedNoti'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  savePost(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/community/post/savePost'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  getPostById(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/community/post/getPostById/'+request,
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getPostOnlyById(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/community/post/getPostOnlyById/'+request,
      params
    );
    return this.http.get(url, { headers: headers });
  }

  saveAnswer(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/community/post/saveAnswer'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  saveReply(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/community/post/saveReply'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  deleteReply(targetId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/community/post/deleteReply/' + targetId;
    return this.http.delete<any>(url, { headers: headers });
  }

  deleteAnswer(targetId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/community/post/deleteAnswer/' + targetId;
    return this.http.delete<any>(url, { headers: headers });
  }

  getApiURL(): any {
    return environment.apiURL;
  }
}
