import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Cookie } from 'ng2-cookies';
import { BehaviorSubject, count, Observable } from 'rxjs';
import { AuthConstant } from 'src/app/constants/auth.constant';
import { CommonConstant } from 'src/app/constants/common.constant';
import { RequestParam } from 'src/app/model/common/request-param';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';
import { AuthenticationUtil } from '../../utils/authentication.util';
import { HeadersUtil } from '../../utils/headers.util';
import { DataUserService } from '../data-user.service';
import { isNullOrUndefined } from 'is-what';

declare var $: any;

@Injectable({
  providedIn: 'root'
})
export class CommonService {
  menuData = new BehaviorSubject<any>({})
  menuDataFull = new BehaviorSubject<any>({})
  courseId = new BehaviorSubject<String>('')
  course = new BehaviorSubject<any>({})
  tagName = new BehaviorSubject<String>('')
  currentData =this.menuData.asObservable();
  currentDataFull =this.menuDataFull.asObservable();
  currentCourseId= this.courseId.asObservable();
  currentInFoCourse = this.course.asObservable();
  currentTagName = this.tagName.asObservable();
  tempData : any = [];
  tempDataFull: any = [];



  constructor(
    private http: HttpClient,
    private dataUserService: DataUserService,
    private trans: TranslateService,
    private router: Router,
  ) {}

  private _aproval = {};
  approval = new BehaviorSubject<any>(this._aproval);
  approval$ = this.approval.asObservable();
  changeAproval(approval :any){
    this.approval.next(approval);
  }

  private _otherProfile = {};
  otherProfile = new BehaviorSubject<any>(this._otherProfile);
  otherProfile$ = this.otherProfile.asObservable();
  changeOtherProfile(otherProfile :any){
    this.otherProfile.next(otherProfile);
  }

  private _isScroll : Boolean = false;
  isScroll = new BehaviorSubject<Boolean>(this._isScroll);
  isScroll$ = this.isScroll.asObservable();
  changeIsScroll(isScroll:Boolean){
    this.isScroll.next(isScroll);
  }

  private _aprovalHistoryInfo = {};
  aprovalHistoryInfo = new BehaviorSubject<any>(this._aprovalHistoryInfo);
  aprovalHistoryInfo$ = this.aprovalHistoryInfo.asObservable();
  changeAprovalHistoryInfo(aprovalHistoryInfo :any){
    this.aprovalHistoryInfo.next(aprovalHistoryInfo);
  }

  private _backCourse = {};
  backCourse = new BehaviorSubject<any>(this._backCourse);
  backCourse$ = this.backCourse.asObservable();
  changeBackCourse(backCourse :any){
    this.backCourse.next(backCourse);
  }

  changeCourse(newCourse :any){
    this.courseId.next(newCourse);
  }

  changeTagName(newTag :any){
    this.tagName.next(newTag);
  }

  removeSpecialCharacter(string :String){
    return string.replace(/[&\/\\#,+()$~%.'":*?<>{}]/g, '');
    // return string.replace('[\x5F]+[a-z][0-9]', '')
  }

  changeInFoCourse(newInFoCourse :any){
    this.course.next(newInFoCourse);
  }

  setMenuData(data: any[]){
    data.forEach(e => {
      if(!e.children){
        const mnu = {
          kr: e.menuNm,
          en: e.menuNmEn,
          vi: e.menuNmVi,
          urlPath : e.urlPath,
          menuId: e.menuId,
          upMenuId: e.upMenuId
        }
        this.tempData.push(mnu);
      }

      const mnu = {
        kr: e.menuNm,
        en: e.menuNmEn,
        vi: e.menuNmVi,
        urlPath : e.urlPath,
        menuId: e.menuId,
        upMenuId: e.upMenuId
      }
      if(!e.children){
        this.tempData.push(mnu);
      }
      this.tempDataFull.push(mnu);
      // this.tempDataFull.push(data);
    })
    // console.log(this.tempData);
    this.menuData.next(this.tempData);
    this.menuDataFull.next(this.tempDataFull);
    this.tempData = [];
    this.tempDataFull = [];
  }

  keyInitForRsa(): Observable<any> {
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    return this.http.get<any>(environment.apiURL + '/public/keyInitForRsa', {
      headers: header
    });
  }

  decryptAccessToken(accessToken: any, privateKey: any): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    let url = environment.apiURL + '/public/decryptAccessToken?accessToken=' + accessToken + "&privateKey=" + privateKey;
    return this.http.get<any>(url, {headers: header});
  }

  checkAccessToken(): Observable<any>{
    let access_token = AuthenticationUtil.getToken();
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    let url = environment.apiURL + '/public/checkAccessToken?accessToken=' + access_token;
    return this.http.get<any>(url, {headers: header});
  }

  getUserInfo(): Observable<any>{
    const header: HttpHeaders = HeadersUtil.getHeadersAuth();
    let url = environment.apiURL + '/userInfo/getUserInfo';
    return this.http.get<any>(url, {headers: header});
  }

  getPublicUserInfo(request: any): Observable<any> {
    const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
    const params: RequestParam[] = ParamUtil.toRequestParams(request);
    const url = ApiUrlUtil.buildQueryString(
      environment.apiURL + '/public/getPublicUserInfo',
      params
    );
    return this.http.get(url, { headers: headers });
  }

  initDialog(): MatDialogConfig {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '0px';
    dialogConfig.height = '0px';
    dialogConfig.panelClass = 'custom-modalbox';
    return dialogConfig;
  }

  getCkConfigDefault1(){
    // const toolbarGroups = [
    //   { name: 'others', groups: ['codesnippet'] }
    // ];
    return {
      filebrowserBrowseUrl: '#browser',
      filebrowserUploadUrl: environment.apiURL + '/tcco-files/uploadFileCkeditor?type=files',
      filebrowserImageUploadUrl: environment.apiURL + '/tcco-files/uploadFileCkeditor?type=images',

      toolbar: [
        { name: 'history', items: ['Undo', 'Redo'] },
        { name: 'clipboard', items: ['Cut', 'Copy', 'Paste', 'PasteText'] },
        { name: 'basicStyles', items: ['Bold', 'Italic', 'Underline'] },
        { name: 'paragraph', items: ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote'] },
        { name: 'justify', items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'] },
        { name: 'links', items: ['Link', 'Unlink'] },
        { name: 'insert', items: ['Table', 'Image', 'Iframe'] },
        { name: 'styles', items: ['Font', 'FontSize', 'TextColor'] }
      ],

      fileTools_requestHeaders: {
        'X-Requested-With': 'XMLHttpRequest',
        'Authorization': AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY)
      },
      height: '25em'
    };
  }

  getCkConfigDefault(): any {
    let currentLang = this.trans.currentLang; //vi - en
    let configLang = "eng";
    if(currentLang=='vi'){
      configLang = 'vi'
    }
    const toolbarGroups = [
      { name: 'document', groups: ['mode', 'doctools', 'document'] },
      { name: 'editing', groups: ['find', 'selection', 'spellchecker', 'editing'] },
      { name: 'forms', groups: ['forms'] },

      { name: 'clipboard', groups: ['clipboard', 'undo'] },
      { name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph'] },
      '/',
      { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
      { name: 'links', groups: ['links'] },
      { name: 'styles', groups: ['styles'] },
      { name: 'colors', groups: ['colors'] },
      { name: 'tools', groups: ['tools'] },
      { name: 'others', groups: ['others'] },
      { name: 'about', groups: ['about'] },
      { name: 'insert', groups: ['codesnippet'] }
    ];
    const removeButtons: string = 'Source,Templates,Save,NewPage,Print,Replace,Scayt,SelectAll,Form,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Blockquote,CreateDiv,Language,Flash,HorizontalRule,Smiley,SpecialChar,PageBreak,ShowBlocks,About,Checkbox,Find,Styles,Format,Anchor';

    return {
      toolbarGroups: toolbarGroups,
      removePlugins: 'elementspath',
      removeButtons: removeButtons,
      disableNativeSpellChecker: false,
      ignoreEmptyParagraphValue: true,
      extraPlugins: "codesnippet",
      //codeSnippet_theme: 'school_book',
      //codeSnippet_languages: { javascript: 'JavaScript', php: 'PHP' },

      filebrowserBrowseUrl: '#browser',
      filebrowserUploadUrl: environment.apiURL + '/tcco-files/uploadFileCkeditor?type=files',
      filebrowserImageUploadUrl: environment.apiURL + '/tcco-files/uploadFileCkeditor?type=images',
      fileTools_requestHeaders: {
        'X-Requested-With': 'XMLHttpRequest',
        'Authorization': AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY)
      },
      height: '25em',
      image_previewText: ' ',
      image2_defaultLockRatio: true,
      language : configLang,
    };
  }

  writerSpin(spin : string){
    localStorage.setItem('spin',spin);
  }

  getPermisRoleMenu(){
    return this.dataUserService.menuPermission;
  }

  selectLangComcode(list: any[]){
    let currentLang = this.trans.currentLang; //vi - en
    list.forEach(comcode => {
      if(currentLang == 'en' ){
        comcode.label = comcode.commNmEn;
      }else if(currentLang == 'vi' ){
        comcode.label = comcode.commNmVi;
      }else{
        comcode.label = comcode.commNm;
      }
    });
  }

  permDelete(insUid: any, onlyInsUid?: any){
    let permission =  this.getPermisRoleMenu();
    let strRoles = AuthenticationUtil.getUserRole();
    let userUid = AuthenticationUtil.getUserUid();

    let arrRoles = strRoles.split("_");
    // let isAdmin = arrRoles.includes(CommonConstant.ROLE_SYS_ADMIN);
    let isMyRecord = userUid == insUid;
    // if(isAdmin) return true;
    if(onlyInsUid) return isMyRecord;
    return permission[CommonConstant.DEL_YN] && isMyRecord;
  }

  userHasRole(role: any){
    let strRoles = AuthenticationUtil.getUserRole();
    let arrRoles = strRoles.split("_");
    return arrRoles.includes(role);
  }

  permModify(insUid: any, onlyInsUid: any){
    let permission =  this.getPermisRoleMenu();
    let strRoles = AuthenticationUtil.getUserRole();
    let userUid = AuthenticationUtil.getUserUid();

    let arrRoles = strRoles.split("_");
    // let isAdmin = arrRoles.includes(CommonConstant.ROLE_SYS_ADMIN);
    let isMyRecord = userUid == insUid;
    // if(isAdmin) return true;
    if(onlyInsUid) return isMyRecord;
    return permission[CommonConstant.MOD_YN] && isMyRecord;
  }

  permCreate(){
    let permission =  this.getPermisRoleMenu();
    let strRoles = AuthenticationUtil.getUserRole();
    let userUid = AuthenticationUtil.getUserUid();

    let arrRoles = strRoles.split("_");
    let isAdmin = arrRoles.includes(CommonConstant.ROLE_SYS_ADMIN);
    if(isAdmin) return true;
    return permission[CommonConstant.WRT_YN];
  }

  getUserUid(){
    return AuthenticationUtil.getUserUid();
  }

  getAuthorInfo(list : any []){
    list.forEach((element:any) => {
      let param : any = {}
      param.insUid = element.insUid
      this.getPublicUserInfo(param).subscribe((response:any)=>{
        element.author = response[CommonConstant.DETAIL_KEY];
      })
    });
  }

  gotoOtherProfile(otherProfile : any){

    this.changeOtherProfile(otherProfile);

    this.router.navigate(['/my-profile'], {
      queryParams: {
        type : "customer"
        },
    });
  }


  chkNewVersion() {
    // let buildVersion = localStorage.getItem(CommonConstant.KEY_VERSION);
    // // console.log(buildVersion);
    // // console.log(environment.version)

    // if (isNullOrUndefined(buildVersion)) {
    //   localStorage.setItem(CommonConstant.KEY_VERSION, environment.version);
    // } else if (buildVersion != environment.version) {
    //   localStorage.setItem(CommonConstant.KEY_VERSION, environment.version);
    //   $.ajax({
    //     url: window.location.href,
    //     headers: {
    //       "Pragma": "no-cache",
    //       "Expires": -1,
    //       "Cache-Control": "no-cache"
    //     }
    //   }).done(function () {
    //     window.location.reload();
    //   });
    // }
  }


}
export class Test{
  subjectId = "";
  chapterId = "";
  docId = "";
}
