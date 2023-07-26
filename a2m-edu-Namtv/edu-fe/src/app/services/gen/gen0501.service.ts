import { Injectable } from '@angular/core';
import { ApiUrlUtil } from './../../utils/api-url.util';
import { ParamUtil } from './../../utils/param.util';
import { RequestParam } from './../../model/common/request-param';
import { HeadersUtil } from './../../utils/headers.util';
import { environment } from './../../../environments/environment';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from 'src/app/model/gen/project';

@Injectable({
  providedIn: 'root',
})
export class Gen0501Service {
  constructor(private httpClient: HttpClient) {}

  getListProject(userUid: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/gen0501/getListProject/' + userUid
    );
    return this.httpClient.get<Project[]>(url, { headers: headers });
  }

  addNewProject(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/gen0501/save'
    );
    return this.httpClient.post(url, request, { headers: headers });
  }

  updateProject(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/gen0501/update'
    );
    return this.httpClient.put(url, request, { headers: headers });
  }

  deleteProject(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    let data = {
      projectId: request.projectId,
      dataFilePath: request.dataFilePath,
    };
    const params: RequestParam[] = ParamUtil.toRequestParams(data);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/gen0501/delete/',
      params
    );
    return this.httpClient.delete(url, { headers: headers });
  }

  search(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/gen0501/search/',
      params
    );
    return this.httpClient.get<Project[]>(url, { headers: headers });
  }

  killContainer(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    let data = {
      PROJECT_ID: request,
    };
    const params: RequestParam[] = ParamUtil.toRequestParams(data);
    const url = ApiUrlUtil.buildQueryString(
      environment.genDataURL + '/gen/killContainer',
      params
    );
    return this.httpClient.get(url, { headers: headers });
  }

  stopInstant(request: any) {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.manageApiUrl + '/manager/stopInstant?projectId=' + request
    );
    return this.httpClient.get(url, { headers: headers });
  }

  pushQueue(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthGenData();
    let data = {
      PROJECT_ID: request.projectId,
      GEN_CYCLE: request.genCycle,
      FILE_PATH: environment.apiURL + '/upload/csvData/' + request.newFleNm,
      LIST_COLUMNS: request.targets[0].tableInfos[0].listColumn,
    };
    const params: RequestParam[] = ParamUtil.toRequestParams(data);
    const url = ApiUrlUtil.buildQueryString(
      environment.genDataURL + '/gen/pushQueuee',
      params
    );
    return this.httpClient.get(url, { headers: headers });
  }

  checkTargetExists(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/gen0501/checkTargetExists/' + request
    );
    return this.httpClient.get(url, { headers: headers });
  }
}
