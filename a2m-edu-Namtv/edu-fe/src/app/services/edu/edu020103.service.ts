import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Edu020103Service {

  constructor(private http: HttpClient) { }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu020103/save';
    return this.http.post<any>(url, request, { headers: headers });
  }

  log(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu020103/log';
    return this.http.post<any>(url, request, { headers: headers });
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu020103/search';
    return this.http.post<any>(url, request ,{ headers: headers });
  }

  delete(classId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu020103/delete/' + classId;
    return this.http.delete<any>(url, { headers: headers });
  }
}
