import { ProgressComponent } from './../progress/progress.component';
import { ParamUtil } from './../../../../utils/param.util';
import { paramBase } from './../../../../model/param-base';
import { Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { I18nConfig } from 'src/app/config/i18n.config';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { Edu0202Service } from 'src/app/services/edu/edu0202.service';
import { I18nService } from 'src/app/services/i18n.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { CandidateFormComponent } from '../candidate-form/candidate-form.component';
import { DatePipe } from '@angular/common'
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { searchModel } from 'src/app/model/search-model';
import { Edu0203Service } from 'src/app/services/edu/edu0203.service';
import { TsstUser } from 'src/app/model/sys/tsst-user';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { StudentFormExcelComponent } from '../../edu0101/component/student-form-excel/student-form-excel.component';

@Component({
  selector: 'app-list-candidate',
  templateUrl: './list-candidate.component.html',
  styleUrls: ['./list-candidate.component.css']
})
export class ListCandidateComponent implements OnInit,OnChanges {

  @Output()
  save: EventEmitter<any> = new EventEmitter();

  constructor(
    private toastr: ToastrService,
    private i18nService: I18nService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,
    public datepipe: DatePipe,
    private route : ActivatedRoute,
    private edu0102Service: Edu0102Service,
    private edu0101Service: Edu0101Service,
    private edu0201Service: Edu0201Service,
    private edu0202Service: Edu0202Service,
    private edu0203Service: Edu0203Service,
    private fileUploadService: FileUploadService,
    private toastrService: ToastrService,
    public dialogRef: MatDialogRef<ListCandidateComponent>,
  ) {
    this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.submitted= false;
    this.candidate = {};
    this.listCandidate = [];
    this.currentDate = new Date();
    this.initData(this.type);
    this.searchData();
  }
  tsstUser:any = {};
  listTsstUser:any = [];
  submitted :any;
  request: any = {};
  language: any;
  pagingRequest: any = {};
  pdfFilePath: any = {};
  candidate!: any;
  totalCandidate!: any;
  listCandidate: any [] = [];
  listCandidateNonCourse: any [] = [];
  listCandidateCourse: any [] = [];
  listCourse: any [] = [];
  listEvent: any [] = [];
  listChecked:  any [] = [];
  currentDate !: Date;
  progressType:String = '';
  @Input() type:any = '';

  @ViewChild("multiCheckBox") multiCheckBox!: ElementRef<any>;

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all'),value: ''  },
    { label: this.trans.instant('edu.edu0202.Pending'), value: 'Chưa giải quyết' },
    { label: this.trans.instant('edu.edu0202.InProgress'), value: 'Trong quá trình' },
    { label: this.trans.instant('edu.edu0202.Confirm'), value: 'Đã xác nhận' },
    { label: this.trans.instant('edu.edu0202.Close'), value: 'Đóng' },
  ];
  listProgress = [
    { label: this.trans.instant('edu.edu0202.Pending'), value: 'Chưa giải quyết' },
    { label: this.trans.instant('edu.edu0202.InProgress'), value: 'Trong quá trình' },
    { label: this.trans.instant('edu.edu0202.Confirm'), value: 'Đã xác nhận' },
    { label: this.trans.instant('edu.edu0202.Close'), value: 'Đóng' },
  ];

  ngOnInit(): void {
    this.getCourseList();
    this.getListEvent();
    // this.getPositionUpCommCd(CommonConstant.POSITION_COMM_CD);
    this.submitted= false;
    this.candidate = {};
    this.listCandidate = [];
    this.currentDate = new Date();

    this.route.queryParams.subscribe(async (params) => {
      this.type = params['type'];
      this.initData(this.type);
      this.searchData();
    });
  }
  initData(type?:any) {
    this.pagingRequest.page = 0;
    this.pagingRequest.endTime = this.currentDate
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;
    if(type=='Today'){
      // this.pagingRequest.progressType = 'Chưa giải quyết';
      this.pagingRequest.startTime = this.currentDate;
    }else if(type=='Pending'){
      this.pagingRequest.progressType = 'Chưa giải quyết';
      this.pagingRequest.startTime = new Date(new Date().setDate(this.currentDate.getDate() - 30));
    }else if(type=='InProgress'){
      //debugger
      this.pagingRequest.progressType = 'Trong quá trình';
      this.pagingRequest.startTime = new Date(new Date().setDate(this.currentDate.getDate() - 30));
    }else{
      // this.pagingRequest.progressType = 'Chờ';
      this.pagingRequest.startTime = new Date(new Date().setDate(this.currentDate.getDate() - 30));
    }
  }

  getPaging(event: any) {
    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.searchData();
    this.refreshCheckbox();
  }

  refreshCheckbox(){
    this.multiCheckBox.nativeElement.checked = false;
    this.listChecked = [];
  }

  searchData() {
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.pagingRequest.startTime = this.datepipe.transform(this.pagingRequest.startTime, 'yyyy/MM/dd');
    this.pagingRequest.endTime = this.datepipe.transform(this.pagingRequest.endTime, 'yyyy/MM/dd');
     this.edu0202Service.search(this.pagingRequest).toPromise().then((response) => {
      this.listCandidate = response.list;
      this.addFieldList(this.listCandidate);
      this.listCandidate.forEach(element => {
        element.startDate = element.startDate?new Date(element.startDate):null;
        element.endDate = element.endDate?new Date(element.endDate):null;
        // if(element.candidateprogressType=='Chưa giải quyết' || element.candidateprogressType=='Pending'){
        //   element.candidateprogressType = this.trans.instant('edu.edu0202.Pending');
        // }else if(element.candidateprogressType=='Trong quá trình' || element.candidateprogressType=='In Progress'){
        //   element.candidateprogressType = this.trans.instant('edu.edu0202.InProgress');
        // }else if(element.candidateprogressType=='Đã xác nhận' || element.candidateprogressType=='Confirm'){
        //   element.candidateprogressType = this.trans.instant('edu.edu0202.Confirm');
        // }else if(element.candidateprogressType=='Đóng' || element.candidateprogressType=='close'){
        //   element.candidateprogressType = this.trans.instant('edu.edu0202.Close');
        // }
      });
      this.totalCandidate = response.totalItems;
      CommonUtil.addIndexForListReverse(
        this.listCandidate,
        this.pagingRequest.page,
        this.totalCandidate
      );
      dialogSpinner.close();
    });
    this.pagingRequest.startTime = new Date(this.pagingRequest.startTime);
    this.pagingRequest.endTime = new Date(this.pagingRequest.endTime);
  }

  onSearchReset() {
    this.pagingRequest = {};
    this.initData(this.type);
    this.searchData();
  }


  viewDetail(candidate: any, event:any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I'){
      const dialogRef = this.dialog.open(CandidateFormComponent, {
        width: '0px',
        height: '0px',
        data: {candidate: candidate},
        panelClass: 'custom-modalbox',
      });

      dialogRef.afterClosed().subscribe((response: any) => {
        if (response) {
          this.searchData();
        }
      });
    }
  }

  delete(candidate: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu0202Service.delete(candidate.key).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              this.edu0202Service.changeData(this.candidate);
              this.toastr.success(
                this.trans.instant('message.success.deleteSuccess')
              );
              this.searchData();
            } else {
              this.toastr.error(
                this.trans.instant('message.error.deleteFailed')
              );
            }
          },
          error: () => {
            this.toastr.error(this.trans.instant('message.error.deleteFailed'));
          },
        });
      },
      reject: () => {},
    });
  }

  addFieldList(list:any){
    list.forEach((element:any) => {
      element.checked = false;
    });
  }

  changeFieldList(list:any,checked:boolean){
    list.forEach((element:any) => {
      element.checked = checked;
    });
  }

  checkCandidate(tsstUser : any){
  return tsstUser.checked?true: false ;
  }

  handleListCheked(tsstUser : any){
    if(this.listChecked.includes(tsstUser)){
      var index = this.listChecked.indexOf(tsstUser);
      if (index > -1) {
        tsstUser.checked = false ;
        this.listChecked.splice(index, 1);
      }
    }else{
      tsstUser.checked = true ;
      this.listChecked.push(tsstUser);
    }
  }

  handleMultiSelect(event : any){
  let isChecked =  event.currentTarget.checked;
  if(isChecked){
    let tempList =  this.listCandidate.filter(item =>  !this.listChecked.includes(item))
    this.listChecked.push(...tempList);
    this.changeFieldList(this.listChecked,true);
    // console.log(this.listChecked);
  }else{
    this.changeFieldList(this.listChecked,false);
    this.listChecked =  this.listChecked.filter(item => !this.listCandidate.includes(item));
    this.changeFieldList(this.listChecked,true);
  }
  }

  exportExel(isAll : Boolean){


    if(isAll){
      this.pagingRequest.getAll = true;
      this.pagingRequest.isExportAll = true;
    }
    else{
      this.pagingRequest.getAll = false ;
      this.pagingRequest.isExportAll = false;
    } ;
    if(!isAll && this.listChecked.length==0){
      this.showRequieq();
      return;
    }
    // this.initData();
    this.pagingRequest.startTime = this.datepipe.transform(this.pagingRequest.startTime, 'yyyy/MM/dd');
    this.pagingRequest.endTime = this.datepipe.transform(this.pagingRequest.endTime, 'yyyy/MM/dd');
    this.pagingRequest.listCheckedId =  this.listChecked.map(element => element.key);

      this.edu0202Service.exportExcel(this.pagingRequest).toPromise().then((response:any) => {
        const myArray = response.detail.split('!#@');
        this.pdfFilePath.fleNm = "Danh sách ứng viên -" + myArray[1];
        this.pdfFilePath.fleTp = '.xlsx';
        this.pdfFilePath.flePath = myArray[0] + this.pdfFilePath.fleTp;
        this.fileUploadService.download(this.pdfFilePath, true);
        this.pagingRequest.getAll = false ;

        this.pagingRequest.startTime = this.pagingRequest.startTime?new Date(this.pagingRequest.startTime):null;
        this.pagingRequest.endTime = this.pagingRequest.endTime?new Date(this.pagingRequest.endTime):null;
      })
  }

  showRequieq(){
    let mes = this.trans.instant('edu.edu0202.requiredChoose')
    this.confirmationService.confirm({
      header: mes,
      acceptLabel: this.trans.instant('button.confirm.title'),
      accept: () => {
        // tabNumber=1;
      },

    });
  }

  deletelistCheck(){
    if(this.listChecked.length==0){
      this.showRequieq();
      return ;
    }
    this.deleteListcheckPopup();
  }

  deleteListcheckPopup(){
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu0202Service.deleteListCheck(this.listChecked).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              this.edu0202Service.changeData(this.candidate);
              this.toastr.success(
                this.trans.instant('message.success.deleteSuccess')
              );
              this.searchData();
            } else {
              this.toastr.error(
                this.trans.instant('message.error.deleteFailed')
              );
            }
          },
          error: () => {
            this.toastr.error(this.trans.instant('message.error.deleteFailed'));
          },
        });
      },
      reject: () => {},
    });
  }

  changProgress(){
    if(this.listChecked.length==0){
      this.showRequieq();
      return ;
    }
    // let param : any = {};
    // param.listCheckedId = this.listChecked.map(element => element.key);
    // param.progressType = this.progressType;
    // this.edu0202Service.changeProgress(param).subscribe((res)=>{
    //   this.searchData();
    // });
    this.changeProgressPopup(this.listProgress);
  }

  changeProgressPopup(list : any){
    const dialogRef = this.dialog.open(ProgressComponent, {
      width: '0px',
      height: '0px',
      data: { listChoose: list },
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any) => {

    let param : any = {};
    param.listCheckedId = this.listChecked.map(element => element.key);
    if(response!=''){
        param.progressType = response;
        // //debugger
        this.edu0202Service.changeProgress(param).subscribe((res)=>{
          this.refreshCheckbox();
          this.searchData();
        });
        if(response == 'Đã xác nhận'){
          this.checkCourseOrClass(this.listChecked);
        }
      }
      this.listChecked = [];
    });
  }

  checkCourseOrClass(listChecked:any){
    for(let i = 0;i< listChecked.length;i++){
      let tsstUser = listChecked[i];
      if(tsstUser.courseModel.key == null){
        this.listCandidateNonCourse.push(tsstUser);
      }else{
        this.listCandidateCourse.push(tsstUser);
      }
    }
    if(this.listCandidateNonCourse.length > 0){
      alert(this.trans.instant('message.alertNoneCourse'));
      this.showAddUserFormExcel(this.listCandidateNonCourse);
    }else{
      if(this.listCandidateCourse.length > 0){
        let idx = 0
        let idxClass = 0;
        if(this.listCandidateCourse.length > 1){
          for(let j = 0; j < this.listCandidateCourse.length - 1; j++){
            if(this.listCandidateCourse[j].courseModel.key != this.listCandidateCourse[j+1].courseModel.key){
              idx += 1;
            }
            if(this.listCandidateCourse[j].courseModel.listClassModel.length != 0){
              idxClass += 1;
            }
          }
        }else{
          if(this.listCandidateCourse[0].courseModel.listClassModel.length != 0){
            idxClass += 1;
          }
          // this.showAddUserFormExcel(this.listCandidateCourse);
        }
        if(idx == 0 && idxClass != 0){
          this.showAddUserFormExcel(this.listCandidateCourse);
        }else if(idx != 0 && idxClass != 0){
          alert(this.trans.instant('message.alertCourses'));
        }else if((idx == 0 || idx !=0) && idxClass == 0){
          alert(this.trans.instant('message.alertNoneClass'));
        }
      }
    }
  }

  showAddUserFormExcel(listChecked:any) {
    const dialogRef = this.dialog.open(StudentFormExcelComponent, {
      width: '0px',
      height: '0px',
      data: {
        listTsstUser: listChecked,
        excel: true,
      },
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any) => {
      if (response){
        this.searchData();
      }
    });
  }

  getCourseList() {
    let param = new searchModel();
    param.getAll = true;
    this.edu0102Service.search(param).subscribe((response) => {
      this.listCourse = response[CommonConstant.LIST_KEY];
    });
  }

  getListEvent() {
    let param = new searchModel();
    param.getAll = true;
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.edu0203Service.search(param).subscribe((response) => {
      this.listEvent = response.list;
      dialogSpinner.close();
    });
  }



}
