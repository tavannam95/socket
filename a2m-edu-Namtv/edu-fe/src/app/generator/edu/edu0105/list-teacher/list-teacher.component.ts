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

import { Edu0105Service } from 'src/app/services/edu/edu0105.service';
import { TeacherFormComponent } from '../teacher-form/teacher-form.component';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';

@Component({
  selector: 'app-list-teacher',
  templateUrl: './list-teacher.component.html',
  styleUrls: ['./list-teacher.component.css']
})
export class ListTeacherComponent implements OnInit {

  constructor(
    private edu0105Service: Edu0105Service,
    private toastr: ToastrService,
    private i18nService: I18nService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private tccoStdService: TccoStdService,
    private langUtil: LangUtil
  ) { this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });}

  listChecked:  any [] = [];
  request: any = {};
  listUser!: any[];
  tsstUser!: any;
  pagingRequest: any = {};
  totalTsstUser!: any;
  language: any;
  position: any;

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  ngOnInit(): void {
    // this.getPositionUpCommCd(CommonConstant.POSITION_COMM_CD);
  this.tsstUser = {};
    this.listUser = [];
    this.initData();
    this.searchData();

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
    this.edu0105Service.search(this.pagingRequest).subscribe((response) => {
      this.listUser = response.listUser;
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
    const dialogRef = this.dialog.open(TeacherFormComponent, {
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

  showUpdateForm(tsstUser: any, event:any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I'){
      const dialogRef = this.dialog.open(TeacherFormComponent, {
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
  //   this.edu0105Service.delete(userUid)
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
        this.edu0105Service.delete(tsstUser.userUid).subscribe({
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

  checkTeacher(tsstUser : any){
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
        this.edu0105Service.deleteListCheck(this.listChecked).subscribe({
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
  //       this.edu0105Service.changeStatus(param).subscribe(res => {
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


}


