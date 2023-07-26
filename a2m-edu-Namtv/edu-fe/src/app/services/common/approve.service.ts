import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApproveHistory } from 'src/app/model/common/approveHistory';
import { RequestParam } from 'src/app/model/common/request-param';

import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';



@Injectable()
export class ApproveService {
  private _aproval = {};
  approval = new BehaviorSubject<any>(this._aproval);
  approval$ = this.approval.asObservable();

  constructor(private http: HttpClient) {
  }

  changeAproval(approval :any){
    
    this.approval.next(approval);
  }

  sendApproveRequest(status: any, refId: Number, refTable: any, url: any, empAprUid: any){
    const request = new ApproveHistory();
    request.approvalStatus = status;
    request.refId = refId;
    request.refTable = refTable;
    request.documentUrl = url;
    request.viewedAprHistory = false;
    request.empAprUid = empAprUid;
    return this.save(request)
  }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/approve/save'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  cancelSubmit(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/approve/cancelSubmit'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  getListApprover(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/approve/getListApprover',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  searchListPeding(userUid: any, isAdmin: Boolean): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(userUid);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/approve/searchListPeding/'+userUid+'/'+isAdmin,
      params
    );
    return this.http.get(url, { headers: headers });
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/approve/search/',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getApprovalById(id: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/approve/getApprovalById/' + id;
    return this.http.get<any>(url, { headers: headers });
  }
}
