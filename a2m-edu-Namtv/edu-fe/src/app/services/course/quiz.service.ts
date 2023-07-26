import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RequestParam } from 'src/app/model/common/request-param';
import { ResponseData } from 'src/app/model/common/response-data';
import { Subject } from 'src/app/model/course/subject';

import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';



@Injectable()
export class QuizService {
  constructor(private http: HttpClient) {
  }

  getQuizByChapter(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/getQuizByChapter',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getQuizById(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/getQuizById/'+request,
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getQuizSubmit(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/getQuizSubmit',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  getQuizSubmitStu(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/getQuizSubmitStu',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  save(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/save'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  submit(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/submit'
    );
    return this.http.post<any>(url, request, { headers: headers });
  }

  getListStuSubtmited(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);

    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/getListStuSubtmited',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  getListHisQuizStuSubtmited(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);

    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/getListHisQuizStuSubtmited',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  getStatisResult(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);

    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/getStatisResult',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  getListQuestionResult(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);

    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/getListQuestionResult',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }

  // update(request: Subject): Observable<any> {
  //   const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
  //   const url = ApiUrlUtil.buildQueryString(
  //     environment.apiURL + '/course/quiz/user/' + request.subjectId
  //   );
  //   return this.http.put<Subject>(url, request, { headers: headers });
  // }

  delete(key: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/quiz/delete/' + key;
    return this.http.delete<Subject>(url, { headers: headers });
  }

  importExcel(file: File): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuthSendingFile();
    const formData: FormData = new FormData();

    formData.append('file', file);

    return this.http.post(`${environment.apiURL}/course/quiz/importExcel`, formData, { headers: headers });
  }

  exportPDF(quizId: Number): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/quiz/exportPDF/' + quizId;
    return this.http.get<any>(url, { headers: headers });
  }

  deletePDF(filePath: String): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const url = environment.apiURL + '/course/quiz/deletePDF/' + filePath;
    return this.http.delete<Subject>(url, { headers: headers });
  }

  getScaleCorrectByQuizItem(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/getScaleCorrectByQuizItem/'+request,
      params
    );
    return this.http.get(url, { headers: headers });
  }


  getListStatisticalByQuestion(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);

    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/course/quiz/getListQuestionStatistical',
      params
    );
    return this.http.get<any>(url, { headers: headers });
  }
}
