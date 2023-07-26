import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { RequestParam } from 'src/app/model/common/request-param';


@Injectable({
  providedIn: 'root'
})
export class DraftMailService {
  constructor(private http: HttpClient) {
  }

  getAll(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/draft/getAll', params);
    return this.http.get<any>(url, { headers: headers });
  }

  getDraftMailById(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/draft/getDraftMailById', params);
    return this.http.get<any>(url, { headers: headers });
  }

  saveDraft(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersUploadOnly();
    const url = environment.apiURL + '/draft/saveDraftOrSentMail';
    return this.http.post<any>(url, request, { headers: headers });
  }

  updateDraft(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/draft/updateDraft');
    return this.http.put<any>(url, request, { headers: headers });
  }

  // saveTccoFile(mailId, multipartFiles: any): Observable<any> {
  //   const headers: HttpHeaders = HeadersUtil.getHeadersUploadOnly();
  //   let formData: FormData = new FormData();
  //   // formData.append('files', multipartFiles);
  //   multipartFiles.forEach(file => {
  //     formData.append('files', file);
  //   });
  //   formData.append("mailId", mailId);
  //   const url = environment.apiURL + '/mail/file/uploadFile';
  //   return this.http.post<any>(url, formData, { headers: headers });
  // }

  // getTccoFileByDrftId(request):Observable<any> {
  //   const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
  //   const params: RequestParam[] = ParamUtil.toRequestParams(request);
  //   const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/mail/file/getTccoFileByDrftId', params);
  //   return this.http.get<any>(url, { headers: headers });
  // }

  deleteDraftOrSentMail(request: any):Observable<any>{
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/draft/deleteDraftOrSentMail', params);
    return this.http.get<any>(url, { headers: headers });
  }

  saveMailSggt(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersUploadOnly();
    const url = environment.apiURL + '/draft/saveMailSggt';
    return this.http.post<any>(url, request, { headers: headers });
  }

  getMailSggtList(request: any):Observable<any>{
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/draft/getMailSggtList', params);
    return this.http.get<any>(url, { headers: headers });
  }
}
