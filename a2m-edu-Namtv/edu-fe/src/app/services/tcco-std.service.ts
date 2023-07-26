import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HeadersUtil } from '../utils/headers.util';

@Injectable({
    providedIn: 'root',
})
export class TccoStdService {
  constructor(private http: HttpClient) {}

  getCommCdByUpCommCd(upCommCd: String): Observable<any> {
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    let url = environment.apiURL + '/tcco-std/getCommCdByUpCommCd?upCommCd=' + upCommCd;
    return this.http.get<any>(url, { headers: header});
  }

  getCommCdByCommCd(commCd: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    let url = environment.apiURL + '/tcco-std/getCommCdByCommCd?commCd=' +  commCd;
    return this.http.get<any>(url, {headers: header});
  }

  getCommNmByCommCd(commCd: any): Observable<any> {
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    let url = environment.apiURL + '/tcco-std/getCommCdByUpCommCd?upCommCd=' + commCd;
    return this.http.get<any>(url, { headers: header});
  }

}
