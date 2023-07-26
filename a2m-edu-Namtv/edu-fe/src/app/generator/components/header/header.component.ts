import { Component, OnInit, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { I18nConfig, LanguageItem } from 'src/app/config/i18n.config';
import { CommonService } from 'src/app/services/common/common.service';
import { AuthenticationService } from 'src/app/services/common/authentication.service';
import { DataUserService } from 'src/app/services/data-user.service';
import { I18nService } from 'src/app/services/i18n.service';
import { AppJsService } from 'src/app/services/app-js.service';
import { Router } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { Edu0202Service } from 'src/app/services/edu/edu0202.service';
import { map, Subscription, timer } from 'rxjs';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import { Title } from '@angular/platform-browser';
import { environment } from 'src/environments/environment';
import { PostService } from 'src/app/services/community/post.service';
import { Course0101Service } from 'src/app/services/course/course0101.service';
import { searchModel } from 'src/app/model/search-model';
import { CommonConstant } from 'src/app/constants/common.constant';
import { ListApprovalComponent } from './list-approval/list-approval.component';
import { TagService } from 'src/app/services/tags/tag.service';
import { ListPostNotificatonComponent } from './list-post-notificaton/list-post-notificaton.component';
import { ListApprovedComponent } from './list-approved/list-approved.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @ViewChild('listApprovalCpn')
  listApprovalCpn!: ListApprovalComponent
  @ViewChild('listApprovedCpn')
  listApprovedCpn!: ListApprovedComponent
  @ViewChild('listPostNotiCpn')
  listPostNotiCpn!: ListPostNotificatonComponent

  languages: LanguageItem[] = I18nConfig.getLanguages();
  language?: any;
  iconPath: any;
  userInfo: any = {};
  menu: any;
  searchText: String ='';
  listTags : any = [];
  filteredTags: any = [];
  searchLanguage: any;
  totalCandidate :any;
  pending :any;
  inProgress :any;
  timerSubscription!: Subscription;
  hiddenNotify : any ;
  baseUrl = environment.apiURL;
  urlImage =  this.baseUrl+'/tcco-files/getFile/'+'1'+'?filePath=';
  pageRequest: any = new searchModel();

  isCandidate : Boolean = false ;
  isApprover: Boolean = false;
  isNotification : Boolean = true ;
  isShowAll : Boolean = true ;


  totalToday: number = 0;
  role  : String = '';

  constructor(
    private router: Router,
    private i18nService: I18nService,
    private translate: TranslateService,
    private commonService: CommonService,
    private authService: AuthenticationService,
    private dataUserService : DataUserService,
    private appJsService: AppJsService,
    private config: PrimeNGConfig,
    private edu0202Service: Edu0202Service,
    private titleService:Title,
    private postService : PostService,
    private lectureService : Course0101Service,
    private tagService : TagService,

  ) { }

  ngOnInit(): void {
    this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    this.hiddenNotify= true;
    if (!this.language){
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.setIconPath(this.language);
    this.getUserInfo();
    this.commonService.currentData.subscribe(data => {
      this.menu = data
    })
    this.getNotify()
    // this.getCountCandidateAllType()

    this.getListFilter(this.searchText);

    //300000
    this.timerSubscription = timer(0, 300000).pipe(
      map(() => {
        this.getCountCandidateAllType();
        if(this.listApprovalCpn){
          this.listApprovalCpn.initData();
        }
        if(this.listPostNotiCpn){
          this.listPostNotiCpn.initData();
        }
        if(this.listApprovedCpn){
          this.listApprovedCpn.initData();
        }

      })
    ).subscribe();

  }

  useLanguage(language: any) {
    this.translate.use(language);
    this.i18nService.language.emit(language);
    localStorage.setItem(I18nConfig.STORAGE_KEY, language);
    this.setIconPath(language);
    this.translate.get("primengConfig").subscribe(res=> {
      this.config.setTranslation(res);
    });
  }

  setIconPath(language: any){
    this.languages.forEach(item=>{
      if (item.key == language){
        this.iconPath = item.icon;
      }
    });
  }

  getUserInfo(){
    this.commonService.getUserInfo().subscribe(res=>{

      this.userInfo = res;
      this.dataUserService.changeData(res);
      this.dataUserService.currentData.subscribe(result => {
        this.userInfo = result;
        this.userInfo.roles.forEach((element : any) => {
          if(element.roleId== CommonConstant.ROLE_SYS_ADMIN  || CommonConstant.ROLE_SYS_MANAGER  || element.roleId== CommonConstant.ROLE_SYS_APPROVER ){
            this.hiddenNotify = false;
            return;
          }
        });
        this.saveUserRoleCookie(this.userInfo.roles);

        // this.isShowAll = (this.commonService.userHasRole(CommonConstant.ROLE_SYS_ADMIN ) || this.commonService.userHasRole(CommonConstant.ROLE_SYS_MANAGER )) && (this.commonService.userHasRole(CommonConstant.ROLE_SYS_APPROVER) || this.commonService.userHasRole(CommonConstant.ROLE_SYS_LECTURERS )|| this.commonService.userHasRole(CommonConstant.ROLE_SYS_TEA_ASSIS ))
         this.isApprover = this.commonService.userHasRole(CommonConstant.ROLE_SYS_APPROVER) || this.commonService.userHasRole(CommonConstant.ROLE_SYS_TEACHER )|| this.commonService.userHasRole(CommonConstant.ROLE_SYS_TEA_ASSIS )
        this.isCandidate = this.commonService.userHasRole(CommonConstant.ROLE_SYS_ADMIN ) || this.commonService.userHasRole(CommonConstant.ROLE_SYS_MANAGER )
        // this.isNotification = !this.commonService.userHasRole(CommonConstant.ROLE_SYS_STUDENT )
      });
    })
  }

  saveUserRoleCookie(roles: any[]){
    let result = "";
    roles.forEach((role: any, index: number) => {
      if(index == 0) result = role.roleId;
      if(index > 0) result += ("_"+role.roleId);
    });
    AuthenticationUtil.saveUserRole(result);
  }

  getNotify(){
    this.edu0202Service.currentData.subscribe(res =>{
      this.getCountCandidateAllType()
    })
  }

  logout(){
    this.authService.logout();
  }

  w(){
    this.appJsService.w();
  }


  filteredTag(event: any) {
    this.filteredTags=[];
    // this.pageRequest.searchText = event.query;
    this.getListFilter(event.query);
    this.filteredTags =  this.listTags.filter((element:any) =>{
      return element.toUpperCase().includes(event.query.toUpperCase());
    })
  }

   getListFilter(searchText : String ){
    this.pageRequest.getAll = true;
    this.pageRequest.searchText = searchText;
    // this.postService.searchPost(this.pageRequest).toPromise().then((res:any)=>{
    //   let list  = res.list ;
    //   this.genListTag(list);
    //   this.getListTag(list,this.listTags);

    // })

    //  this.lectureService.searchByTag(this.pageRequest).toPromise().then((res:any)=>{
    //   let list  = res.list ;
    //   this.genListTag(list);
    //   this.getListTag(list,this.listTags);
    // })

     this.tagService.searchTags(this.pageRequest).toPromise().then((res:any)=>{
      let list  = res.list ;
      this.listTags = list.map((item: any)=>{

        return item.tagName;
      })
      //
    })

  }

  getListTag(list:any,listTags :any){
    list.forEach((element:any) => {
      if(element.listTag){
        element.listTag.forEach((childEle:any) => {
          if(!listTags.includes(childEle)){
            listTags.push(childEle);
          }
        });
      }
    });
  }

  genListTag(list: any[]){
    list.forEach((element: any) => {
      if(element.tags){
        element.listTag = JSON.parse(element.tags);
      }
    });
  }

    //Start search tag
    searchTag(event : any){

      this.commonService.changeTagName(this.searchText)
      this.searchText = '';

      this.router.navigate(['/searchtag'], {});
      }


  onSelect(event: any){
    // this.router.navigateByUrl(event.urlPath);
  }

  cleanAccents(str: string){
      str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
      str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
      str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
      str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
      str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
      str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
      str = str.replace(/đ/g, "d");
      str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
      str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
      str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
      str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
      str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
      str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
      str = str.replace(/Đ/g, "D");
      // Combining Diacritical Marks
      str = str.replace(/\u0300|\u0301|\u0303|\u0309|\u0323/g, ""); // huyền, sắc, hỏi, ngã, nặng
      str = str.replace(/\u02C6|\u0306|\u031B/g, ""); // mũ â (ê), mũ ă, mũ ơ (ư)

      return str;
  }
  gotoCandidate(type : any){
    this.router.navigate(['/edu/edu0202'], {
      queryParams: { type: type },
    });
  }

  getCountCandidateAllType(){
    this.edu0202Service.getCountCandidateAllType().subscribe(res=>{
     let string = res.totalItems;
     let totalItems = string.split("!#@");
     this.totalCandidate = totalItems[0];
     this.pending = totalItems[1];
     this.inProgress = totalItems[2];
     if(this.totalCandidate>0){
       this.titleService.setTitle("("+this.totalCandidate+") "+"A2M Education Management System");
     }else{
      this.titleService.setTitle("A2M Education Management System");
     }
    })
  }


  renewTotal(){
    this.totalToday = Number(this.totalCandidate) + Number(this.totalApprove) +  Number(this.totalPostNoti) + Number(this.totalApproved)
  }

  totalPostNoti: number = 0;
  totalPostNotiChecked: number = 0;
  isNoNotification: Boolean = false;
  renewTotalPostNoti(obj : any){
    this.totalPostNoti = Number(obj.total);
    this.totalPostNotiChecked = Number(obj.totalChecked);
    this.renewTotal();
  }

  totalApprove: number = 0;
  renewTotalApproval(total : Number){
    this.totalApprove = Number(total);
    this.renewTotal();
  }

  totalApproved: number = 0;
  renewTotalApproved(total : Number){
    this.totalApproved = Number(total);
    this.renewTotal();
  }
}

