import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { Subject } from 'src/app/model/course/subject';

import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';



@Injectable()
export class IqTestPublicService {
  constructor(private http: HttpClient) {
  }

  saveNonUser(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeaders();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/quesIq/iqTest/saveUser'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }
  submit(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeaders();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/quesIq/iqTest/submit'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  getIqTestSubmit(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeaders();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/quesIq/iqTest/getQuesSubmit',
      params
    );
    return this.http.get(url, { headers: headers });
  }
}
