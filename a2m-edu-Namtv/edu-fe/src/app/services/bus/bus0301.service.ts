import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Bus0301Service {

  constructor(private http: HttpClient) { }
  getListCustomer(param: any){
    
    const params: RequestParam[] = ParamUtil.toRequestParams(param);
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/bus/bus0301/getRecordByMonth',params);
    return this.http.get<any>(url, { headers: header });
  }
}
