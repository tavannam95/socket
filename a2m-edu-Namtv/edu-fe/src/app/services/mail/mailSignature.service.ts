
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';

@Injectable({
  providedIn: 'root',
})
export class MailSignatureService {
  constructor(private http: HttpClient) {}

  saveMailSignature(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/saveMailSignature';
    return this.http.post<any>(url, request, { headers: headers });
  }

  getListMailSignature(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/mail/getListMailSignatureByUid',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  saveMailUser(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/saveMailUser';
    return this.http.post<any>(url, request, { headers: headers });
  }

  getMailUserByUid(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/mail/getMailUserByUid',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  getMailSignatureInfo(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/mail/getMailSignatureInfo',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  updateMailSignature(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/updateMailSignature';
    return this.http.put<any>(url, request, { headers: headers });
  }

  deleteMailSignature(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/mail/deleteMailSignature',
      params
    );
    return this.http.delete<any>(url, { headers: headers });
  }
}
