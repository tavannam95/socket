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
export class IqTestService {
  constructor(private http: HttpClient) {
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/quesIq/iqTest/search',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getIqTestById(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/quesIq/iqTest/getIqTestById/'+request,
      params
    );
    return this.http.get(url, { headers: headers });
  }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/quesIq/iqTest/save'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  saveNonUser(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/quesIq/iqTest/saveUser'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  delete(key: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/quesIq/iqTest/delete/' + key;
    return this.http.delete<Subject>(url, { headers: headers });
  }

  submit(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/quesIq/iqTest/submit'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  getIqTestSubmit(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/quesIq/iqTest/getQuesSubmit',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getListStuSubtmited(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);

    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/quesIq/iqTest/getListStuSubtmited',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  getListStatisticalByQuestion(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);

    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/quesIq/iqTest/getListQuestionStatistical',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }
}
