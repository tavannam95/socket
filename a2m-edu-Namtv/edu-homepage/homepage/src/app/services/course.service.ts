import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RequestParam } from '../models/request-params';
import { ApiUrlUtil } from '../utils/api-url.util';
import { ParamUtil } from '../utils/param.util';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private http: HttpClient) {
  }

  search(request: any): Observable<any> {
    request.searchStatus = 'true'
    var header = new HttpHeaders();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/searchCourses', params
    );
    return this.http.get(url, { headers: header });
  }

  // save(request: any): Observable<any> {
  //   var header = new HttpHeaders();
  //   const url = ApiUrlUtil.buildQueryString(
  //     environment.apiURL + '/edu/edu0102/save',
  //   );
  //   return this.http.post<any>(url, request, { headers: headers });
  // }

  // delete(key: String): Observable<any> {
  //   var header = new HttpHeaders();
  //   const url = environment.apiURL + '/edu/edu0102/delete/' + key;
  //   return this.http.delete<any>(url, { headers: headers });
  // }

  getCourseById(courseId: string): Observable<any> {
    var header = new HttpHeaders();
    const url = environment.apiURL + '/public/getCourseById/' + courseId;
    return this.http.get<any>(url, { headers: header });
  }

  // getCourseByCondition(request: any): Observable<any> {
  //   var header = new HttpHeaders();
  //   const params: RequestParam[] = ParamUtil.toRequestParams(request);
  //   const url = ApiUrlUtil.buildQueryString(
  //     environment.apiURL + '/edu/edu0102/getCourseByCondition', params
  //   );
  //   return this.http.get(url, { headers: headers });
  // }

  // getSubjectList(request: any): Observable<any> {
  //   var header = new HttpHeaders();
  //   const params: RequestParam[] = ParamUtil.toRequestParams(request);
  //   const url = ApiUrlUtil.buildQueryString(
  //     environment.apiURL + '/edu/edu0102/getSubjectList',
  //     params
  //   );
  //   return this.http.get(url, { headers: headers });
  // }

  getCourseInfo(request: any): Observable<any> {
    request.searchStatus = 'true'
    var header = new HttpHeaders();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/getCourseInfo', params
    );
    return this.http.get(url, { headers: header });
  }

}
