import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Injectable()
export class GetpdfService {
private baseUrl = environment.apiURL

constructor(private httpService: HttpClient) {
}

getPdf(filePath: string, fileType: string) {
  return this.httpService.get<any>(`${this.baseUrl}/tcco-files/get/pdf/${filePath}`, { responseType: 'arraybuffer' as 'json' });
}

getExcel(fileName: string, fileType: string) {
  return this.httpService.get<any>(`${this.baseUrl}/tcco-files/get/excel/${fileName}`, { responseType: 'arraybuffer' as 'json' });
}
}
