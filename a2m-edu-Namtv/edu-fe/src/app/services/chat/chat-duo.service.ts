import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable()
export class ChatDuoService {

  constructor(
    private http: HttpClient
  ) { }

  sendMessage(content: string): Observable<any>{
    return this.http.get(environment.apiHost + '/socket/send?content=' + content);
  }

}
