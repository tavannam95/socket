import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { RequestParam } from 'src/app/model/common/request-param';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';


@Injectable({
  providedIn: 'root'
})
export class Gen0301Service {

  constructor(private http: HttpClient) { }

 getInfoUser(param: any): Observable<any>{
  const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  const params: RequestParam[] = ParamUtil.toRequestParams(param);
  const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/gen/gen0301/infomation', params);
  return this.http.get<any>(url, { headers: header });
 }

 getPositionName(param: any): Observable<any>{
  const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  const params: RequestParam[] = ParamUtil.toRequestParams(param);
  const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/gen/gen0301/position', params);
  return this.http.get<any>(url, { headers: header });
 }

 updateInfo(param: any): Observable<any>{
  const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  // const params: RequestParam[] = ParamUtil.toRequestParams(param);
  const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/gen/gen0301/modify');
  return this.http.post<any>(url,param, { headers: header });
 }

 verifyEmail(param: any): Observable<any>{
  const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  const params: RequestParam[] = ParamUtil.toRequestParams(param);
  const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/gen/gen0301/email', params);
  return this.http.get<any>(url, { headers: header });
 }

 verifyCode(param: any): Observable<any>{
  const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  const params: RequestParam[] = ParamUtil.toRequestParams(param);
  const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/gen/gen0301/verifycode', params);
  return this.http.get<any>(url, { headers: header });
 }

 modifyEmail(param: any): Observable<any>{
  const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  const params: RequestParam[] = ParamUtil.toRequestParams(param);
  const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/gen/gen0301/modifyEmail', params);
  return this.http.get<any>(url, { headers: header });
 }

 get2FaKeyById(param: any): Observable<any>{
  const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  const params: RequestParam[] = ParamUtil.toRequestParams(param);
  const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/gen/gen0301/2fakey', params);
  return this.http.get<any>(url, { headers: header });
 }

 modify2FaEnable(param: any): Observable<any>{
  const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  const params: RequestParam[] = ParamUtil.toRequestParams(param);
  const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/gen/gen0301/modify2FaEnable', params);
  return this.http.get<any>(url, { headers: header });
 }
 changePassword(param: any): Observable<any>{
  const header: HttpHeaders = HeadersUtil.getHeadersAuth();
  const params: RequestParam[] = ParamUtil.toRequestParams(param);
  const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/gen/gen0301/changePassword', params);
  return this.http.get<any>(url, { headers: header });
 }

 uploadAvatar(userId: any, atchFleSeq: any, multipartFile: any): Observable<any> {
  let headerVal: HttpHeaders = HeadersUtil.getHeadersUploadOnly();
  headerVal.append("Content-type", "multipart/form-data");
  let formData: FormData = new FormData();
  if (multipartFile) {
    formData.append('file', multipartFile);
    formData.append('userId', userId);
    formData.append('atchFleSeq', atchFleSeq);
  }
  return this.http.post<any>(environment.apiURL + '/gen/gen0301/uploadAvatar', formData, { headers: headerVal });
}
}
