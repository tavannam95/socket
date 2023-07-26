import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';

import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root',
})
export class TagService {
  constructor(private http: HttpClient) {
  }

  searchTags(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/tags/search',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  searchUser(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/tags/searchUser',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getApiURL(): any {
    return environment.apiURL;
  }
}
