import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ApiUrlUtil } from '../utils/api-url.util';
import { HeadersUtil } from '../utils/headers.util';

@Injectable({
  providedIn: 'root'
})
export class SignUpService {

  constructor(private http: HttpClient) { }

  insertUser(request: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeaders();
    const url = ApiUrlUtil.buildQueryString(environment.mainURL + '/signup');
    return this.http.post<any>(url, request, { headers: header });
   }
   getListPos(){
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(environment.apiURL + '/public/lstPos');
    return this.http.get<any>(url, { headers: header });
   }
}
