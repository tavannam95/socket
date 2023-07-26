import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Injectable()
export class CourseInfoService {

  constructor(private http: HttpClient) { }

  saveAll(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/courseInfo/saveAll'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  getAllList(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/courseInfo/getAllList',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/courseInfo/save'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  getListByCourseId(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/courseInfo/getCourseInfoByCourseId',
      params
    );
    return this.http.get(url, { headers: headers });
  }

}
