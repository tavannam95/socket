
import { HttpClient ,HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ParamUtil } from '../utils/param.util';
import { ApiUrlUtil } from '../utils/api-url.util';
import { RequestParam } from '../models/request-params';


@Injectable({
  providedIn: 'root'
})
export class ApplyService {

  constructor(private http: HttpClient) {}
  save(request: any): Observable<any> {
    ////debugger
    var header = new HttpHeaders();
    const url = environment.apiURL + '/public/candidate/save';
    const formData: FormData = new FormData();
    formData.append('file',request.file)
    const candidate = JSON.stringify(request.candidate);
    formData.append('candidate',candidate)
    return this.http.post<any>(url, formData,{headers: header});
  }
  getEventById(eventId: string): Observable<any> {
    var header = new HttpHeaders();
    const url = environment.apiURL + '/public/getById/' + eventId;
    return this.http.get<any>(url, { headers: header });
  }


}

