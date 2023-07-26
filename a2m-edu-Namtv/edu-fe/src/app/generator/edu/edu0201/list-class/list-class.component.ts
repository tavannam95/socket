import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { I18nConfig } from 'src/app/config/i18n.config';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { I18nService } from 'src/app/services/i18n.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { ClassFormComponent } from '../class-form/class-form.component';


@Component({
  selector: 'app-list-class',
  templateUrl: './list-class.component.html',
  styleUrls: ['./list-class.component.css'],
})
export class ListClassComponent implements OnInit {
  constructor(
    private toastr: ToastrService,
    private i18nService: I18nService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,

    private edu0201Service: Edu0201Service,
    private tccoStdService: TccoStdService,
    private commonService : CommonService,
  ) {
    this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });
  }

  isStudent = this.commonService.userHasRole(CommonConstant.ROLE_SYS_STUDENT );
  listChecked:  any [] = [];
  request: any = {};
  language: any;
  pagingRequest: any = {};
  class!: any;
  totalClass!: any;
  listClass!: any[];
  listClassStu: any[] = [];
  classTypeList : any[] = [];
  currentDate !: Date;

  disable = false;
  userInfo:any;



  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all') },
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: '0' },
    { label: this.trans.instant('sys.sys0101.status.active'), value: '1' },
  ];

  ngOnInit(): void {
    this.getTsstUser();
    // this.getPositionUpCommCd(CommonConstant.POSITION_COMM_CD);
    this.class = {};
    this.listClass = [];
    this.currentDate = new Date();
    this.initData();
    this.searchData();
    this.getClassType();
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
    this.edu0201Service.search(this.pagingRequest).subscribe((response) => {
      this.listClass = response.list;
      this.listClass.forEach(element => {
        element.startDate = element.startDate?new Date(element.startDate):null;
        element.endDate = element.endDate?new Date(element.endDate):null;
      });
      this.totalClass = response.totalItems;
      if(this.userInfo.userType == 'STU'){
        this.getShowClassForStu();
      }else{
        this.listClassStu = this.listClass;
      }
      CommonUtil.addIndexForListReverse(
        this.listClass,
        this.pagingRequest.page,
        this.totalClass
      );
      dialogSpinner.close();
    });
  }
  getShowClassForStu(){
    this.listClass.forEach((element) => {
      const listStudentMap = element.classStudentMapModels;
      listStudentMap.forEach((ele:any) => {
        const studentModel = ele.studentModel;
        if(studentModel.key == this.userInfo.userInfoId){
          this.listClassStu.push(element);
        }
      })
    })
  }
  onSearchReset() {
    this.pagingRequest = {};
    this.initData();
    this.searchData();
  }

  delete(classs: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu0201Service.delete(classs.key).subscribe({
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
            this.toastr.error(this.trans.instant('message.error.deleteFailed'));
          },
        });
      },
      reject: () => {},
    });
  }

    viewDetail(id: number, event:any) {
      let targetName = event.target.tagName;
      if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I')
      // if (!id) return;
      this.router.navigate(['/edu/edu0201Form'], {
        queryParams: { id: id },
      });
    }

    getClassType(){
      this.tccoStdService.getCommCdByUpCommCd(CommonConstant.CLASS_TYPE_COMM_CD).subscribe((response:any) => {
        this.classTypeList = response;
      this.commonService.selectLangComcode(this.classTypeList);

    });
   }

   handleClassType(classType : any){
    let typeLable = '';
    this.classTypeList.forEach(element => {
     if(element.commCd == classType ){
      typeLable = element.label;
      return;
     }
    } )
    return typeLable;

   }

  handleMultiSelect(event : any){
    let isChecked =  event.currentTarget.checked;
    if(isChecked){
      let tempList =  this.listClass.filter(item =>  !this.listChecked.includes(item))
      this.listChecked.push(...tempList);
      this.changeFieldList(this.listChecked,true);
      // console.log(this.listChecked);
    }else{
      this.changeFieldList(this.listChecked,false);
      this.listChecked =  this.listChecked.filter(item => !this.listClass.includes(item));
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

  checkClass(classModel : any){
    return classModel.checked?true: false ;
    }

  handleListCheked(classModel : any){
    if(this.listChecked.includes(classModel)){
      var index = this.listChecked.indexOf(classModel);
      if (index > -1) {
        classModel.checked = false;
        this.listChecked.splice(index, 1);
      }
    }else{
      classModel.checked = true ;
      this.listChecked.push(classModel);
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
        this.edu0201Service.deleteListCheck(this.listChecked).subscribe({
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
