import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationService } from 'primeng/api';
import { ToastrComponentlessModule, ToastrService } from 'ngx-toastr';
import { TsstUserInfo } from './../../../../model/gen/tsst-user-info';
import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { TsstUser } from './../../../../model/sys/tsst-user';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonUtil } from 'src/app/utils/common-util';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { I18nConfig } from 'src/app/config/i18n.config';
import { I18nService } from 'src/app/services/i18n.service';
import { LangUtil } from 'src/app/utils/lang.util';
import { StudentFormComponent } from '../component/student-form/student-form.component';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { StudentFormExcelComponent } from '../component/student-form-excel/student-form-excel.component';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';


@Component({
  selector: 'app-list-student',
  templateUrl: './list-student.component.html',
  styleUrls: ['./list-student.component.css']
})
export class ListStudentComponent implements OnInit {

  constructor(
    private edu0101Service: Edu0101Service,
    private edu0201Service: Edu0201Service,
    private toastr: ToastrService,
    private i18nService: I18nService,
    private toastrService: ToastrService,
    private confirmationService: ConfirmationService,
    public commonService: CommonService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private tccoStdService: TccoStdService,
    private fileUploadService: FileUploadService,
    private langUtil: LangUtil
  ) { this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });}

  pdfFilePath: any = {};
  request: any = {};
  listClass: any[] = [];
  listChecked:  any [] = [];
  listUser!: any[];
  tsstUser!: any;
  pagingRequest: any = {};
  pageRequest: any = {};
  totalTsstUser!: any;
  language: any;
  position: any;
  disable = false;
  userInfo:any;
  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  ngOnInit(): void {
    this.getListClass();
    this.getTsstUser();
    // this.getPositionUpCommCd(CommonConstant.POSITION_COMM_CD);
    this.tsstUser = {};
    this.listUser = [];
    this.initData();
    this.searchData();

  }

  getTsstUser(){
    this.commonService.getUserInfo().subscribe(
      (response) =>{
        this.userInfo = response;
        if(this.userInfo.userType != 'STU'){
          this.disable = false;
        }else{
          this.disable = true;
        }
      }
    );
  }
  // getPositionUpCommCd(upCommCd: any) {
  //   this.tccoStdService.getCommCdByUpCommCd(upCommCd).subscribe((response) => {
  //     this.position = response;
  //     response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
  //   });
  // }

  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;
  }


  getPaging(event: any) {
    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.searchData();

  }


  searchData() {
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.edu0101Service.search(this.pagingRequest).subscribe((response) => {
      this.listUser = response.listUser;
      this.addFieldList(this.listUser);
      //this.listUser.reverse();
      this.totalTsstUser = response.totalItems;
      CommonUtil.addIndexForListReverse(this.listUser, this.pagingRequest.page, this.totalTsstUser);
      dialogSpinner.close();
    });
  }

  onSearchReset(){
    this.pagingRequest = {};
    this.initData();
    this.searchData();
  }

  showAddUserForm() {
    const dialogRef = this.dialog.open(StudentFormComponent, {
      width: '0px',
      height: '0px',
      data: { tsstUser: {}},
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any) => {
      if (response){
        this.searchData();
      }
    });
  }

  showAddUserFormExcel() {
    const dialogRef = this.dialog.open(StudentFormExcelComponent, {
      width: '0px',
      height: '0px',
      data: {
        listTsstUser: [],
        excel: false,
      },
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any) => {
      if (response){
        this.searchData();
      }
    });
  }

  showUpdateForm(tsstUser: any, event:any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I'){
      if (!tsstUser) return;
      const dialogRef = this.dialog.open(StudentFormComponent, {
        width: '0px',
        height: '0px',
        data: {tsstUser: tsstUser},
        panelClass: 'custom-modalbox',
      });

      dialogRef.afterClosed().subscribe((response: any) => {
        if (response) {
          this.searchData();
        }
      });
    }
  }

  // delete1(userUid:string){
  //   this.edu0101Service.delete(userUid)
  //   .subscribe(tsstUser=>{
  //     this.searchData();
  //   })
  // }

  delete(tsstUser: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu0101Service.delete(tsstUser.userUid).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
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
            this.toastr.error(
              this.trans.instant('message.error.deleteFailed')
            );
          },
        });
      },
      reject: () => {},
    });




  }

  // async changeStatus(tsstUser:any){
  //   if(tsstUser.status === true){
  //     var stt = 0;
  //   }else{
  //     var stt = 1;
  //   }
  //   const param = {
  //     "STATUS": stt,
  //     "USER_UID" : tsstUser.userUid
  //   }
  //   await this.confirmationService.confirm({
  //     header: this.trans.instant('bus.subscribe.changeStt.title'),
  //     message: this.trans.instant('bus.subscribe.changeStt.msg'),
  //     accept: () => {
  //       this.edu0101Service.changeStatus(param).subscribe(res => {
  //       if (res == 1) {
  //         this.toastr.success(
  //           this.trans.instant('message.success.saveSuccess')
  //         );
  //         tsstUser.status = stt;
  //       } else {
  //         this.toastr.error(this.trans.instant('message.error.saveFailed'));
  //       }
  //       });

  //     },
  //   });
  // }

  getListClass(){
    this.pageRequest.getAll = true;
    this.pageRequest.searchStatus = true;

    this.edu0201Service.search(this.pageRequest).subscribe( (response) =>  {
      this.listClass =  response.list
   });
  }

  handleMultiSelect(event : any){
    let isChecked =  event.currentTarget.checked;
    if(isChecked){
      let tempList =  this.listUser.filter(item =>  !this.listChecked.includes(item))
      this.listChecked.push(...tempList);
      this.changeFieldList(this.listChecked,true);
      // console.log(this.listChecked);
    }else{
      this.changeFieldList(this.listChecked,false);
      this.listChecked =  this.listChecked.filter(item => !this.listUser.includes(item));
      this.changeFieldList(this.listChecked,true);
    }
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

  checkStudent(tsstUser : any){
    return tsstUser.checked?true: false ;
    }

  handleListCheked(tsstUser : any){
    if(this.listChecked.includes(tsstUser)){
      var index = this.listChecked.indexOf(tsstUser);
      if (index > -1) {
        tsstUser.checked = false;
        this.listChecked.splice(index, 1);
      }
    }else{
      tsstUser.checked = true ;
      this.listChecked.push(tsstUser);
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
    this.pagingRequest.listCheckedId =  this.listChecked.map(element => element.eamStudentInfo.studentInfoId);
    // this.pagingRequest.listChecked =  this.listChecked;
      this.edu0101Service.exportExcelList(this.pagingRequest).toPromise().then((response:any) => {
        const myArray = response.detail.split('!#@');
        this.pdfFilePath.fleNm = "Danh sách sinh viên -" + myArray[1];
        this.pdfFilePath.fleTp = '.xlsx';
        this.pdfFilePath.flePath = myArray[0] + this.pdfFilePath.fleTp;
        this.fileUploadService.download(this.pdfFilePath, true);
        this.pagingRequest.getAll = false ;

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
        this.edu0101Service.deleteListCheck(this.listChecked).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              // this.edu0101Service.changeData(this.candidate);
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
}

