import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { SendMessageAction } from 'src/app/model/mail/send-message-action';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { ParamUtil } from 'src/app/utils/param.util';


@Injectable({
  providedIn: 'root',
})
export class MailService {
  constructor(private http: HttpClient) {}

  doLoginMailServer(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/doLoginMailServer';
    return this.http.post<any>(url, request, { headers: headers });
  }

  doLogoutMailServer(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/doLogoutMailServer';
    return this.http.post<any>(url, request, { headers: headers });
  }

  sendMessage(
    request: SendMessageAction,
    multipartFiles: any
  ): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersUploadOnly();
    let formData: FormData = new FormData();
    multipartFiles.forEach((file: string | Blob) => {
      formData.append('files', file);
    });
    let messageJson: string = JSON.stringify(request);
    formData.append('messageJson', messageJson);
    const url = environment.apiURL + '/mail/sendMessage';
    return this.http.post<any>(url, formData, { headers: headers });
  }

  sendMessageVN(request: SendMessageAction): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/sendMessage';
    return this.http.post<any>(url, request, { headers: headers });
  }

  sendDraftMessageVN(request: SendMessageAction): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/sendDraftMessage';
    return this.http.post<any>(url, request, { headers: headers });
  }

  getMessagesByFolder(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/mail/getMessagesByFolder',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  getMessageDetailByUid(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/mail/getMessageDetailByUid',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  downloadAttachment(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/mail/downloadAttachment',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  deleteMailServerByUids(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/doDeleteMailByUid';
    return this.http.post<any>(url, request, { headers: headers });
  }

  restoreMailServerByUids(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/doRestoreMailByUid';
    return this.http.post<any>(url, request, { headers: headers });
  }

  fileUpload(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    const url = environment.apiURL + '/mail/file/uploadFile';
    return this.http.post<any>(url, request, { headers: headers });
  }

  fileUploadSiteVN(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    const url = environment.apiURL + '/file/uploadFile';
    return this.http.post<any>(url, request, { headers: headers , reportProgress: true, observe: 'events'});
  }

  doUpdateUser(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/doUpdateUser';
    return this.http.post<any>(url, request, { headers: headers });
  }

  doInsertUser(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/doInsertUser';
    return this.http.post<any>(url, request, { headers: headers });
  }

  sendMailSalary(request: any[]) {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    const url = environment.apiURL + '/mail/sendMailSalary';
    return this.http.post<any>(url, request, { headers: headers });
  }

  genExcelToMail(request: FormData) {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    const url = environment.apiURL + '/mail/genExcelToGmail';
    return this.http.post<any>(url, request, { headers: headers });
  }

  uploadAttachments(multipartFiles:any) {
    const headers: HttpHeaders = HeadersUtil.getHeadersUploadOnly();
    let formData: FormData = new FormData();
    multipartFiles.forEach((file: string | Blob) => {
      formData.append('files', file);
    });
    const url = environment.apiURL + '/mail/sendMessage';
    return this.http.post<any>(url, formData, { headers: headers });
  }

  markAsReadOrUnRead(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/mail/markAsReadOrUnReadController';
    return this.http.post<any>(url, request, { headers: headers });
  }

  downloadAttachmentMail(path: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthAttachment();
    const url = ApiUrlUtil.buildQueryString(path);
    return this.http.get(url, { headers: headers });
  }
}
