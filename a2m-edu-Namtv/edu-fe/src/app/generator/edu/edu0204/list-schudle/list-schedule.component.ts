import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationService } from 'primeng/api';
import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonUtil } from 'src/app/utils/common-util';
import { I18nConfig } from 'src/app/config/i18n.config';
import { I18nService } from 'src/app/services/i18n.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/services/common/common.service';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { Edu0204Service } from 'src/app/services/edu/edu0204.service';
import { ScheduleFormExcelComponent } from '../component/schedule-form-excel/schedule-form-excel.component';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { Edu0103Service } from 'src/app/services/edu/edu0103.service';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-list-schedule',
  templateUrl: './list-schedule.component.html',
  styleUrls: ['./list-schedule.component.css']
})
export class ListScheduleComponent implements OnInit {

  constructor(
    private edu0204Service: Edu0204Service,
    private edu0201Service: Edu0201Service,
    private edu0103Service: Edu0103Service,
    public datepipe: DatePipe,
    private toastr: ToastrService,
    private i18nService: I18nService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,
    public commonService: CommonService,
  ) { this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });}

  isStudent = this.commonService.userHasRole(CommonConstant.ROLE_SYS_STUDENT );
  listChecked:  any [] = [];
  listClass: any[] = [];
  listRoom: any[] = [];
  request: any = {};
  listUser!: any[];
  tsstUser!: any;
  pagingRequest: any = {};
  pageRequest: any = {};
  totalTsstUser!: any;
  language: any;
  position: any;
  permission: any;

  schedule!:any;
  listSchedule :any[] = [];
  totalSchedule!:any;

  disable = false;
  userInfo:any;
  scheduleSearch: any = {
    userId : "",
    userType : "",
  }

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  ngOnInit(){
    this.getListClass();
    this.getListRoom();
    this.getTsstUser();
    this.schedule = {};
    this.listSchedule = [];
    this.initData();
    // this.searchData();
    this.commonService.changeCourse(this.schedule.key);
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

  getListClass(){
    this.pageRequest.getAll = true;
    this.pageRequest.searchStatus = true;

    this.edu0201Service.search(this.pageRequest).subscribe( (response) =>  {
      this.listClass =  response.list
   });
  }

  getListRoom(){
    this.pageRequest.getAll = true;
    this.pageRequest.searchStatus = true;

    this.edu0103Service.search(this.pageRequest).subscribe( (response) =>  {
      this.listRoom =  response.list
   });
  }

  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;
    this.getDataByRole();
  }


  getPaging(event: any) {
    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.searchData();

  }

  searchData() {
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    if(this.pagingRequest.startTime != null && this.pagingRequest.endTime != null){
      this.pagingRequest.startTime = this.datepipe.transform(this.pagingRequest.startTime, 'yyyy-MM-dd HH:mm:ss');
      this.pagingRequest.endTime = this.datepipe.transform(this.pagingRequest.endTime, 'yyyy-MM-dd HH:mm:ss');
    }
    let param = Object.assign(this.scheduleSearch, this.pagingRequest)
    // this.edu0204Service.search(this.pagingRequest).subscribe((response) => {
    this.edu0204Service.getScheduleList(param).subscribe((response) => {
      this.listSchedule = response[CommonConstant.LIST_KEY];
      this.totalSchedule = response[CommonConstant.COUNT_ITEMS_KEY];
      // this.totalSchedule = this.listSchedule.length;
      CommonUtil.addIndexForListReverse(this.listSchedule, this.pagingRequest.page, this.totalSchedule);
      dialogSpinner.close();
    });
    if(this.pagingRequest.startTime != null && this.pagingRequest.endTime != null){
      this.pagingRequest.startTime = new Date(this.pagingRequest.startTime);
      this.pagingRequest.endTime = new Date(this.pagingRequest.endTime);
    }
  }

  onSearchReset(){
    this.pagingRequest = {};
    this.scheduleSearch = {};
    this.initData();
    // this.searchData();
  }

  showAddForm() {
    ;
    this.router.navigate(['/edu/edu0204Form'], {
      queryParams: {
        scheduleId : ''
      },

    });
  }

  showAddUserFormExcel() {
    const dialogRef = this.dialog.open(ScheduleFormExcelComponent, {
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

  showUpdateForm(scheduleId: any, event :any ) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I')
    this.router.navigate(['/edu/edu0204Form'], {
      queryParams: { scheduleId: scheduleId },
    });
    // this.commonService.changeCourse(courseId);
  }

  delete(schedule: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu0204Service.delete(schedule.key).subscribe({
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
              this.trans.instant('message.error.deleteFailed2')
            );
          },
        });
      },
      reject: () => {},
    });
  }

  getDataByRole(){
    this.commonService.getUserInfo().toPromise().then(res=>{

      let listRole = res.roles.map((element:any) => {return element.roleId});

      if(listRole.includes(CommonConstant.ROLE_SYS_ADMIN) || listRole.includes(CommonConstant.ROLE_SYS_MANAGER)){
        this.scheduleSearch.userId = res.userUid;
        this.scheduleSearch.userType = 'admin';
        // this.searchData();
      } if(listRole.includes(CommonConstant.ROLE_SYS_TEA_ASSIS)){
        this.scheduleSearch.userId = res.userUid;
        this.scheduleSearch.userType = 'assist';
        // this.searchData();
      } if(listRole.includes(CommonConstant.ROLE_SYS_TEACHER) && !(listRole.includes(CommonConstant.ROLE_SYS_ADMIN) || listRole.includes(CommonConstant.ROLE_SYS_MANAGER))){
        this.scheduleSearch.userId = res.userUid;
        this.scheduleSearch.userType = 'teacher';
        // this.searchData();
      } if(listRole.includes(CommonConstant.ROLE_SYS_STUDENT)){
        this.scheduleSearch.userId = res.userInfoId;
        this.scheduleSearch.userType = 'student';
        // this.searchData();
      }
      this.searchData();
    })
  }

  handleMultiSelect(schedule : any){
    let isChecked =  schedule.currentTarget.checked;
    if(isChecked){
      let tempList =  this.listSchedule.filter(item =>  !this.listChecked.includes(item))
      this.listChecked.push(...tempList);
      this.changeFieldList(this.listChecked,true);
      // console.log(this.listChecked);
    }else{
      this.changeFieldList(this.listChecked,false);
      this.listChecked =  this.listChecked.filter(item => !this.listSchedule.includes(item));
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

  checkSchedule(scheduleModel : any){
    return scheduleModel.checked?true: false ;
    }

  handleListCheked(scheduleModel : any){
    if(this.listChecked.includes(scheduleModel)){
      var index = this.listChecked.indexOf(scheduleModel);
      if (index > -1) {
        scheduleModel.checked = false;
        this.listChecked.splice(index, 1);
      }
    }else{
      scheduleModel.checked = true ;
      this.listChecked.push(scheduleModel);
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
        this.edu0204Service.deleteListCheck(this.listChecked).subscribe({
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

