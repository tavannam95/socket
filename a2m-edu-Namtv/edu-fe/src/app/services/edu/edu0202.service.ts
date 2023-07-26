import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Edu0202Service {

  canData = new BehaviorSubject<any>({});
  currentData = this.canData.asObservable();
  constructor(private http: HttpClient) {
  }
  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0202/search',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getCandidateById(candidateId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0202/getCandidateById/' + candidateId;
    return this.http.get<any>(url, { headers: headers });
  }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0202/save';
    return this.http.post<any>(url, request, { headers: headers });
  }

  delete(candidateId: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0202/delete/' + candidateId;
    return this.http.delete<any>(url, { headers: headers });
  }

  deleteListCheck(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/edu/edu0202/deleteListCheck'
    return this.http.post<any>(url, request, { headers: headers });
  }

  getCountCandidateAllType(): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0202/getCountCandidateAllType'
    );
    return this.http.get(url, { headers: headers });
  }

  exportExcel(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0202/exportExcel'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  changeProgress(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/edu/edu0202/changeProgress'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  changeData(newData: any) {

    this.canData.next(newData);
  }

}
