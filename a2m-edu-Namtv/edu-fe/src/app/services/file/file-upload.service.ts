import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as saveAs from 'file-saver';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';
import { CommonService } from '../common/common.service';

@Injectable()
export class FileUploadService {

  private baseUrl = environment.apiURL

  constructor(
    private http: HttpClient,
    private toastrService: ToastrService,
    private commonService : CommonService
  ) { }

  singleFileUpload(file: File): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    const formData: FormData = new FormData();

    formData.append('file', file);

    return this.http.post(`${this.baseUrl}/tcco-files/singleFileUpload`, formData, { headers: headers });
  }

  multiFileUpload(files: File[]): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    const formData: FormData = new FormData();

    for (let i = 0; i < files.length; i++) {

      formData.append('files', files[i]);
    }
    return this.http.post(`${this.baseUrl}/tcco-files/multiFileUpload`, formData, { headers: headers });
  }

  getFiles(): Observable<any> {
    return this.http.get(`${this.baseUrl}/files`);
  }

  download(tccoFileModel: any,isDelete?:Boolean): any{
    
    const headers: HttpHeaders = HeadersUtil.getHeaders();
    const req : any ={};
    req.fleNm =tccoFileModel.fleNm;
    req.fleNm = this.commonService.removeSpecialCharacter(req.fleNm);
    req.fleTp =tccoFileModel.fleTp;
    req.flePath =tccoFileModel.flePath;
    const body =  JSON.stringify(req);


    // const params: RequestParam[] = ParamUtil.toRequestParams(tccoFileModel);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/tcco-files/downloadv2'
    );

    // return this.http.post<any>(url, req, { headers: headers });
    return this.http.post(url,req, { headers: headers,

      responseType: 'blob' }).subscribe({
        next: async (blob) => {
          saveAs(blob, tccoFileModel.fleNm+tccoFileModel.fleTp);
          this.toastrService.success('Success', ' Downloaded !');
          if(isDelete){
         await   this.deleteFile(tccoFileModel.flePath).subscribe(res =>{

            })
          }
        },
        error: () => {
          this.toastrService.error('Failed', ' Downloaded  File Failed !');
        }
      });
  }

  deleteFile(filePath: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/tcco-files/deleteFile/' + filePath;
    return this.http.delete<any>(url, { headers: headers });
  }

  // download(request: any): Observable<Blob>{
  //   const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
  //   const params: RequestParam[] = ParamUtil.toRequestParams(request);
  //   const url = ApiUrlUtil.buildQueryString(
  //     environment.apiURL + '/tcco-files/downloadv2',
  //     params
  //   );
  //   return this.http.get(url, { headers: headers,
  //     responseType: 'blob' });
  // }
}
