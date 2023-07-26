import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {environment} from 'src/environments/environment';
import { RequestParam } from '../models/request-params';
import { ApiUrlUtil } from '../utils/api-url.util';
import { ParamUtil } from '../utils/param.util';


@Injectable({
    providedIn: 'root'
})
export class NewsService {

  constructor(private http: HttpClient) {
  }

  getNews(filter?:any): Observable<object> {
    const params: RequestParam[] = ParamUtil.toRequestParams(filter ?? {});
    const url = ApiUrlUtil.buildQueryString(environment.apiHost + '/homepage/news', params);
    return this.http.get(url, {  });
  }

  getById(id:number): Observable<object> {
    const url = environment.apiHost + '/homepage/getById?id=' + id;
    return this.http.get(url, {  });
  }

}
