import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { ListChooseSubjectComponent } from '../tabSubject/list-choose-subject/list-choose-subject.component';
import { ListCloneSubjectComponent } from '../../course0105/list-clone-subject/list-clone-subject.component';

@Component({
  selector: 'app-list-subject',
  templateUrl: './list-subject.component.html',
  styleUrls: ['./list-subject.component.css']
})
export class ListSubjectComponent implements OnInit {

  @Output() handleCallRefresh = new EventEmitter

  pageRequest = new searchModel();
  listSubject: any[] = [];
  totalSubject = 0;
  courseId : any;
  courseObj: any = new Object();
  couSubjectLst: any[] = [];
  chooseSubject : any =[];
  approvalStatusTypes: any[] = [];
  minSbj : any;
  maxSbj : any;
  subject : any;
  checkView : any = true;
  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  APPROVAL_STATUS_APPROVAL_COMM_CD = CommonConstant.APPROVAL_STATUS_APPROVAL_COMM_CD;
  APPROVAL_STATUS_REJECT_COMM_CD = CommonConstant.APPROVAL_STATUS_REJECT_COMM_CD;
  APPROVAL_STATUS_PENDING_COMM_CD = CommonConstant.APPROVAL_STATUS_PENDING_COMM_CD;
  DOCUMENT_STATUS_DRAFT = CommonConstant.DOCUMENT_STATUS_DRAFT;


  constructor(
    private course0102Service: Course0102Service,
    private edu0102Service: Edu0102Service,
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
    this.init();
  }

  init(){
    this.getStatusType();
    this.chooseSubject = [];
    this.route.queryParams.subscribe(async (params) => {
      this.courseId = params['courseId'];
    });
    if(this.courseId){
      this.initData();
      this.searchData();
    }
  }

  getStatusType() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.APPROVAL_STATUS_COMM_CD)
      .subscribe((response) => {
        this.approvalStatusTypes = response;
        this.commonService.selectLangComcode(this.approvalStatusTypes);
      });
  }

  getLabelApproval(cdStatus : String){
    const cd = this.approvalStatusTypes.find( e =>  e.commCd == cdStatus )
    //
    if(cd) return  cd.label;
    return "";
  }

  initData() {
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
    this.pageRequest.searchStatus = "1";
  }

  // getPaging(event: any) {
  //   this.pageRequest.page = event.page;
  //   this.pageRequest.rows = event.rows;
  //   this.setPaging();
  // }

  initCheckView(){
    if(this.commonService.permModify(this.courseObj.insUid, true)){
      this.checkView = false;
    }else{
      this.checkView = true;
    }
  }

  searchData(){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.minSbj = this.pageRequest.page * (this.pageRequest.rows);
    this.maxSbj = this.pageRequest.rows * (this.pageRequest.page + 1);
    this.couSubjectLst = [];
    //let requestModel = new searchModel();
    this.pageRequest.key = this.courseId;

    let request: any = {};
    request = Object.assign(this.pageRequest, {});
    request.approvalStatus = "";

      this.edu0102Service.getCourseByCondition(request).subscribe((response) => {
        this.courseObj = response[CommonConstant.DETAIL_KEY];
        //this.couSubjectLst = this.courseObj.subjectModels;
        this.totalSubject = this.courseObj.subjectModels.length;
        CommonUtil.addIndexForList(this.courseObj.subjectModels, this.pageRequest.page, this.totalSubject);
        // if(this.maxSbj >= this.courseObj.subjectModels.length){
        //   this.couSubjectLst = this.courseObj.subjectModels;
        // }else{
        //   for(let i = this.minSbj; i < this.maxSbj; i++){
        //     if(this.courseObj.subjectModels[i].key){
        //       this.subject = this.courseObj.subjectModels[i];
        //     }
        //     this.couSubjectLst.push(this.subject);
        //   }
        // }
        this.couSubjectLst = this.courseObj.subjectModels;
        this.initCheckView();
        dialogSpinner.close();
      });
  }

  setPaging(){
    this.minSbj = this.pageRequest.page * (this.pageRequest.rows);
    this.maxSbj = this.pageRequest.rows * (this.pageRequest.page + 1);
    this.couSubjectLst = [];
    for(let i = this.minSbj; i < this.maxSbj; i++){
      if(this.courseObj.subjectModels[i].key){
        this.subject = this.courseObj.subjectModels[i];
      }
      this.couSubjectLst.push(this.subject);
    }
  }

  delete(subject: any, event: any) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.course0102Service.delete(subject.key, this.courseId).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              // this.commonService.changeCourse(this.courseId);
              this.toastr.success(
                this.trans.instant('message.success.deleteSuccess')
              );
              this.searchData();
              this.handleCallRefresh.emit();
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
    this.commonService.changeAprovalHistoryInfo(
      {}
    )
    if(this.courseId){
      this.router.navigate(['/course/course0103'], {
        queryParams: { subjectId : '', courseId: this.courseId },
      });
    }else {
      alert("Create A Course !");
    }
  }

  showUpdateForm(subjectId: any, event:any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I')
    this.router.navigate(['/course/course0103'], {
      queryParams: { subjectId: subjectId, courseId: this.courseId},
    });

  }

  chooseSubjectBtn() {
    if(this.courseId){
      const dialogRef = this.dialog.open(ListChooseSubjectComponent, {
        width: '0px',
        height: '0px',
        data: {
          listChooseSubject : this.couSubjectLst,
         },
        panelClass: 'custom-modalbox',
      });

      dialogRef.afterClosed().subscribe((response: any[]) => {
        if(response){
          this.searchData();
        }

      });
    }else {
      alert("Create A Course !");
    }
  }

  cloneSubjectBtn() {
    if(this.courseId){
      const dialogRef = this.dialog.open(ListCloneSubjectComponent, {
        width: '0px',
        height: '0px',
        data: {
          listChooseSubject : this.couSubjectLst,
         },
        panelClass: 'custom-modalbox',
      });

      dialogRef.afterClosed().subscribe((response: any[]) => {
        if(response){
          this.searchData();
        }

      });
    }else {
      alert("Create A Course !");
    }
  }

  onSearchReset(){
    this.pageRequest = new searchModel();
    this.initData();
    this.searchData();
  }



}
