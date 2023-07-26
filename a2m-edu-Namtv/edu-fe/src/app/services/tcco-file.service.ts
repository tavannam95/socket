import { ApiUrlUtil } from './../utils/api-url.util';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TccoFile } from '../model/tccofile';
import { HeadersUtil } from '../utils/headers.util';

@Injectable({
  providedIn: 'root',
})
export class TccoFileService {
  constructor(private http: HttpClient) {}

  save(
    userUid: any,
    atchFleSeq: any,
    multipartFile: any
  ): Observable<TccoFile> {
    // if (multipartFile === undefined || multipartFile === null) {
    //   return null;
    // }
    const headerVal: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    let formData: FormData = new FormData();
    formData.append('userUid', userUid);
    formData.append('atchFleSeq', atchFleSeq);
    if (multipartFile) {
      formData.append('multipartFile', multipartFile);
    }

    return this.http.post<TccoFile>(
      environment.apiURL + '/tcco-files/save',
      formData,
      { headers: headerVal }
    );
  }

  update(
    userUid: any,
    atchFleSeq: any,
    multipartFile: any
  ): Observable<TccoFile> {
    const headerVal: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    let formData: FormData = new FormData();
    formData.append('userUid', userUid);
    formData.append('atchFleSeq', atchFleSeq);
    if (multipartFile) {
      formData.append('multipartFile', multipartFile);
    }

    return this.http.put<TccoFile>(
      environment.apiURL + '/tcco-files/update',
      formData,
      { headers: headerVal }
    );
  }

  getFileDetails(atchFleSeq: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/tcco-files/getFileDetails?atchFleSeq=' + atchFleSeq
    );
    return this.http.get(url, { headers: headers });
  }
}
