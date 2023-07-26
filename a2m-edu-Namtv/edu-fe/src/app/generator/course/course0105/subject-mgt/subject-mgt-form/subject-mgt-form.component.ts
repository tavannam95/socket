import { DatePipe } from '@angular/common';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { Subject } from 'src/app/model/course/subject';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { AuthConstant } from 'src/app/constants/auth.constant';
import { Cookie } from 'ng2-cookies';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { searchModel } from 'src/app/model/search-model';
import { CommonConstant } from 'src/app/constants/common.constant';
import { ActivatedRoute, Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { CommonService } from 'src/app/services/common/common.service';
import { ConfirmationService } from 'primeng/api';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { Edu020102Service } from 'src/app/services/edu/edu020102.service';
import { Sys0103Service } from 'src/app/services/sys/sys0103.service';
import { ApproveService } from 'src/app/services/common/approve.service';
import { ControlContainer, NgForm } from '@angular/forms';

@Component({
    selector: 'app-subject-mgt-form',
    templateUrl: './subject-mgt-form.component.html',
    styleUrls: ['./subject-mgt-form.component.css'],
    providers : [{provide : ControlContainer, useExisting : NgForm}]
  })
  export class SubjectMgtFormComponent implements OnInit {
  @ViewChild('myForm')
  myForm!: NgForm;

  disable = false;
  changePassword = false;
  courseList: any[] = [];
  emailPattern = PatternValidate.EMAIL_PATTERN;
  phonePattern = PatternValidate.PHONE_PATTERN;
  phonePatternBasic = PatternValidate.PHONE_PATTERN_BASIC;
  submitted: any = false;
  dropdownSettings: IDropdownSettings = {};
  pdfFilePath: any = {};

  subject: any = {
    teacherModel:{
      fullName: ""
    },
    tsstUser: {
      tsstUserInfo: {
        fullName: ""
      }
    }
  };
  subjectId: any;
  configCkeditor: any = {};

  listTeacher: any;
  listAssist: any;
  assistUId: any;
  teacherUid: any;
  typeView : any;
  checkView : any = true;
  courseNm : any = "_";

  constructor(
    private course0102Service: Course0102Service,
    private sys0103Service: Sys0103Service,
    private toastrService: ToastrService,
    private edu0102Service: Edu0102Service,
    public dialogRef: MatDialogRef<SubjectMgtFormComponent>,
    private trans: TranslateService,
    public datepipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    public commonService: CommonService,
    private confirmationService: ConfirmationService,
    private edu020102Service: Edu020102Service,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fileUploadService: FileUploadService,

  ) {}

  ngOnInit(): void {

    this.route.queryParams.subscribe(async (params) => {
      this.subjectId = params['subjectId'];
    });
    this.initData();

    this.getCourseList();
    this.getTeachers();
    this.getAssist();
    this.initConfigCk();
  }

  initData(){
    if (this.subjectId) {
      this.typeView = 'VIEW';
      this.getSubjectHistory(this.subjectId);
    } else {
      this.typeView = 'ADD';
      this.subject = new Subject();
      this.subject.status = 1;
    }
  }

  initCheckView(){
    if(this.commonService.permModify(this.subject.insUid, true)|| this.commonService.permModify(this.assistUId, true) || this.commonService.permModify(this.teacherUid, true)){
      this.checkView = false;
    }else{
      this.checkView = true;
    }
  }

  async getSubjectHistory(param: any) {
  await  this.course0102Service.getSubjectHistory(param).toPromise().then((response) => {
      this.subject = response[CommonConstant.DETAIL_KEY];
      this.subject.subjectId = this.subject.subjectId;
      this.assistUId = this.subject.tsstUser.userUid;
      this.teacherUid = this.subject.teacherModel.userUid;

      var startDate = this.subject.startDt
        ? new Date(this.subject.startDt)
        : null;
      var endDate = this.subject.endDt ? new Date(this.subject.endDt) : null;
      this.subject.startDt = startDate;
      this.subject.endDt = endDate;
      this.initCheckView();
      this.changeAprovalHistoryInfo();
      this.getCourseNm();
    });
  }

  async getSubject(param: any){
    await  this.course0102Service.getSubjectById(param).toPromise().then((response) => {
        this.subject = response[CommonConstant.DETAIL_KEY];
        this.subject.subjectId = this.subject.key;
        this.assistUId = this.subject.tsstUser.userUid;
        this.teacherUid = this.subject.teacherModel.userUid;

        var startDate = this.subject.startDt
          ? new Date(this.subject.startDt)
          : null;
        var endDate = this.subject.endDt ? new Date(this.subject.endDt) : null;
        this.subject.startDt = startDate;
        this.subject.endDt = endDate;
        this.initCheckView();
        this.changeAprovalHistoryInfo();
        this.getCourseNm();
      });
    }

  getCourseList() {
    let param = new searchModel();
    param.getAll = true;
    this.edu0102Service.search(param).subscribe((response) => {
      this.courseList = response[CommonConstant.LIST_KEY];
      this.courseList = this.courseList.map( (e: any) =>{
        return {
          key: e.key,
          courseNm: e.courseNm
        };
      } );
    });
  }

  getCourseNm() {
    this.courseNm = ""
    if(this.subject.listCourse.length == 0) this.courseNm = "_";
    //debugger
    this.subject.listCourse.forEach( (element: any, index: number)=> {
      this.courseNm += element.courseNm
      if(this.subject.listCourse.length > 1 && index < this.subject.listCourse.length - 1) this.courseNm += ", ";
    });
  }

  onItemSelect(item: any) {
    // console.log(item);
  }
  onSelectAll(items: any) {
    // console.log(items);
  }

  initConfigCk() {
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  async saved(subjectId: any) {
    this.subject.subjectId = subjectId;
    await this.router.navigate(['/course/course0105/form'], {
      queryParams: {
        subjectId: subjectId,
      },
    });
  }

  onSave(invalid: any) {

    if (invalid) {
      this.submitted = true;
      return;
    } else {
      this.subject.startDt = this.datepipe.transform(
        this.subject.startDt,
        'yyyy-MM-dd'
      );
      this.subject.endDt = this.datepipe.transform(
        this.subject.endDt,
        'yyyy-MM-dd'
      );

      if (this.subject.key) {
        this.subject.editSubjectForm = true;
      }

      this.course0102Service.save(this.subject).subscribe({
        next: (response) => {
          if (!this.subject.key) {
            this.saved(response[CommonConstant.KEY]);
            this.handleAfterSaveSuccess(
              this.trans.instant('message.success.saveSuccess')
            );
          } else {
            this.saved(response[CommonConstant.KEY]);
            this.handleAfterSaveSuccess(
              this.trans.instant('message.success.updateSuccess')
            );
          }
        },
        error: () => {
          if (!this.subject.key) {
            this.toastrService.error(
              this.trans.instant('message.error.saveFailed')
            );
          } else {
            this.toastrService.error(
              this.trans.instant('message.success.updateFailed')
            );
          }
        },
      });
    }
  }

  checkInvalidForm(){
    const invalid = this.myForm.invalid;
    return invalid;
  }

  // onSaveApproval(invalid: any) {
  //   this.subject.documentStatus = CommonConstant.DOCUMENT_STATUS_APPROVAL;
  //   this.subject.approveHistory.documentUrl = this.router.url;

  //   if (invalid) {
  //     this.submitted = true;
  //     return;
  //   }
  //   this.onSave(invalid);
  // }

  handleAfterSaveSuccess(mes: any) {
    this.confirmationService.confirm({
      header: mes,
      message: this.trans.instant('message.confirm.goListScreen'),
      acceptLabel: this.trans.instant('button.confirm.title'),
      rejectLabel: this.trans.instant('button.cancel.title'),
      key: "subject-mgt-form",
      accept: () => {
        this.goList();
      },
      reject: () => {
        this.typeView = 'VIEW';
        this.initData();
      },
    });
  }

  goList() {
    this.router.navigate(['/course/course0105'], {

    });
  }

  onCancel(){
    this.router.navigate(['/course/course0105'], {

    });
  }

  onEditorChange(event: any) {
    // document.getElementsByClassName('editorPreview')[0].innerHTML = event.editor.getData();
  }

  exportPDF() {
    const subjectId = Number(this.subjectId);
    this.course0102Service
      .exportPDF(subjectId)
      .toPromise()
      .then((response) => {
        const myArray = response.detail.split('!#@');
        this.pdfFilePath.fleNm = myArray[1];
        this.pdfFilePath.fleTp = '.pdf';
        this.pdfFilePath.flePath = myArray[0] + this.pdfFilePath.fleTp;
        this.fileUploadService.download(this.pdfFilePath, true);
      });
  }

  exportDoc(subjectId: any) {
    this.course0102Service
      .exportDoc(subjectId)
      .toPromise()
      .then((response) => {
        const myArray = response.detail.split('!#@');
        this.pdfFilePath.fleNm = myArray[1];
        this.pdfFilePath.fleTp = '.docx';
        this.pdfFilePath.flePath = myArray[0] + this.pdfFilePath.fleTp;
        this.fileUploadService.download(this.pdfFilePath, true);
      });
  }

  getTeachers() {
    let request: any = {};
    request.getAll = true;
    request.searchText = '';
    this.edu020102Service.searchTeacher(request).subscribe((response) => {
      this.listTeacher = response.list;
    });
  }

  getAssist() {
    let param: any = {};
    this.sys0103Service.findByUserAssist(param).subscribe((res) => {
      this.listAssist = res.list;
    });
  }

  showUpdateSubjectForm(){
    this.typeView = 'MODIF';
    //this.getSubjectHistory(this.subjectId);
  }

  createApprovalHistoryInfo(){
    let aprovalHistoryInfo = {
      approvalHistoryId : this.subject.approvalHistoryId,
      refId: Number(this.subject.subjectId),
      refTable: CommonConstant.TABLE_SUBJECT,
      hasPermModify: !this.checkView
    }
    return aprovalHistoryInfo;
  }

  changeAprovalHistoryInfo(){
    let data = this.createApprovalHistoryInfo();
    this.commonService.changeAprovalHistoryInfo(
      data
    )
  }
}
