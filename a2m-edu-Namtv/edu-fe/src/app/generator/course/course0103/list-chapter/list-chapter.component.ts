import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { el } from '@fullcalendar/core/internal-common';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonUtil } from 'src/app/utils/common-util';

@Component({
  selector: 'app-list-chapter',
  templateUrl: './list-chapter.component.html',
  styleUrls: ['./list-chapter.component.css']
})
export class ListChapterComponent implements OnInit {
  @Output() handleRefreshAll = new EventEmitter

  pageRequest = new searchModel();
  listChapter: any[] = [];
  totalChapter = 0;
  subjectId : any;
  courseId: any;
  subjectObj: any = new Object();
  chapterLst: any[] = [];
  organizationFormals: any[] = [];
  organizationFormalNm: String ='';
  couChapterLst: any[] = [];
  chooseChapter : any =[];
  subject: any = {};
  assistUId: any;
  teacherUid: any;
  checkView : any = true;
  courseUid : any;
  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  constructor(
    private course0102Service: Course0102Service,
    private course0103Service: Course0103Service,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,
    private route : ActivatedRoute,
    private commonService : CommonService,
    private tccoStdService: TccoStdService,
  ){ }

  ngOnInit(): void {
    this.init()
  }

  init(){
    this.route.queryParams.subscribe(async (params) => {
      this.subjectId = params['subjectId']?params['subjectId']:"";
      this.courseId = params['courseId']?params['courseId']:"";
    });
    this.initData();
    if(this.subjectId){
      this.onSearch();

    }
  }


  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.pageRequest.key = this.subjectId;
    this.onSearch();

  }

  delete(chapter: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.course0103Service.delete(chapter.key).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              this.handleRefreshAll.emit();
              // this.commonService.changeCourse(this.courseId);
              this.toastr.success(
                this.trans.instant('message.success.deleteSuccess')
              );
              this.onSearch();
            } else {
              this.toastr.error(
                this.trans.instant('message.error.deleteFailed')
              );
            }
          },
          error: () => {
            this.toastr.error(
              this.trans.instant('message.error.deleteFailed')
            );
          },
        });
      },
      reject: () => {},
    });
  }

  showAddForm() {
    if(this.subjectId){
      this.router.navigate(['/course/course0101'], {
        queryParams: {
        chapterId: '',
        subjectId : this.subjectId,
        courseId: this.courseId
        },
      });
    }else {
      alert("Create A Subject !");
    }
  }

  showUpdateForm(chapterId: any, event:any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I')
    if(this.subjectId){
      this.router.navigate(['/course/course0101'], {
        queryParams: {
          chapterId: chapterId,
          subjectId : this.subjectId,
          courseId: this.courseId
        },
      });
      // this.commonService.changeCourse(this.courseId);
    }
  }

  initCheckView(){
    if(this.commonService.permModify(this.subject.insUid, true)|| this.commonService.permModify(this.assistUId, true) || this.commonService.permModify(this.teacherUid, true)){
      this.checkView = false;
    }else{
      this.checkView = true;
    }
  }

  async getSubjectById(param: any) {
    if(param == "" || param == null) return;

  await  this.course0102Service.getSubjectById(param).toPromise().then((response) => {
      this.subject = response[CommonConstant.DETAIL_KEY];
      this.assistUId = this.subject.tsstUser.userUid;
      this.teacherUid = this.subject.teacherModel.userUid;
      this.courseUid = this.subject.courseUid;
      this.initCheckView();
    });
  }

  async onSearchMain(){
    this.pageRequest = new searchModel();
    this.initData();
    this.pageRequest.key = this.subjectId;
    this.pageRequest.documentStatus = "APPROVAL";
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
  await  this.course0103Service.search(this.pageRequest).toPromise().then(async (response) => {
      this.listChapter = response[CommonConstant.LIST_KEY];
      this.totalChapter = response[CommonConstant.COUNT_ITEMS_KEY];
      // CommonUtil.addIndexForListReverse(this.listChapter, this.pageRequest.page, this.totalChapter);
      CommonUtil.addIndexForList(this.listChapter);
      this.courseUid = response.courseUid;
    await  this.getSubjectById(this.subjectId);
      dialogSpinner.close();
    });
  }

  async onSearch(){
    this.pageRequest = new searchModel();
    this.initData();
    this.pageRequest.key = this.subjectId;
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    await  this.course0103Service.search(this.pageRequest).toPromise().then(async (response) => {
      this.listChapter = response[CommonConstant.LIST_KEY];
      this.listChapter.forEach((element:any) => {
        element.organiztionNm = this.getOrganiztionNm(element.organizationFormal);
      });
      this.totalChapter = response[CommonConstant.COUNT_ITEMS_KEY];
      CommonUtil.addIndexForList(this.listChapter);
      this.courseUid = response.courseUid;
    await  this.getSubjectById(this.subjectId);
      dialogSpinner.close();
    });
  }

  onSearchReset(){
    this.pageRequest = new searchModel();
    this.initData();
    this.onSearch();
  }

  initData() {
    this.chooseChapter = [];
    this.couChapterLst = [];
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
    this.pageRequest.getAll = true;
    this.getOrganizationFormals();
  }

  async getOrganizationFormals() {
   await this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.ORGANIZATION_FORMAL)
      .toPromise().then((response) => {
          this.organizationFormals = response;
          this.commonService.selectLangComcode(this.organizationFormals);

        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  getOrganiztionNm(organizationFormal : any){

    const commCode = this.organizationFormals.find(
      (element) => element.commCd == organizationFormal
    );
      if(!commCode) return "";
    return commCode.label;

  }

}
