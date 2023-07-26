import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RequestParam } from '../models/request-params';
import { ApiUrlUtil } from '../utils/api-url.util';
import { ParamUtil } from '../utils/param.util';

@Injectable({
  providedIn: 'root'
})
export class TypicalService {

  constructor(private http: HttpClient) { }

  getList(request: any): Observable<any> {
    request.searchStatus = 'true'
    var header = new HttpHeaders();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/getListTypical', params
    );
    return this.http.get(url, { headers: header });
  }
}
