import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { I18nConfig } from 'src/app/config/i18n.config';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { Edu0203Service } from 'src/app/services/edu/edu0203.service';
import { I18nService } from 'src/app/services/i18n.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { environment } from 'src/environments/environment';
import { EquipmentFormComponent } from '../../edu0104/components/equipment-form/equipment-form.component';
import { EventFormComponent } from '../event-form/event-form.component';

@Component({
  selector: 'app-list-event',
  templateUrl: './list-event.component.html',
  styleUrls: ['./list-event.component.css']
})
export class ListEventComponent implements OnInit {

  constructor(
    private i18nService: I18nService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,


    private edu0203Service: Edu0203Service,
  ) { this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });}

  request: any = {};
  listChecked:  any [] = [];
  tsstUser!: any;
  pagingRequest: any = {};
  totalTsstUser!: any;
  language: any;

  event!:any;
  totalEvent!: any;
  listEvent!:any[];
  baseUrl = environment.apiURL;
  urlImage =  this.baseUrl+'/tcco-files/getFile/'+'1'+'?filePath=';

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  ngOnInit(): void {
    this.listEvent=[];
    this.initData();
    this.searchData();

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
    this.edu0203Service.search(this.pagingRequest).subscribe((response) => {
      this.listEvent = response.list;
      this.totalEvent = response.totalItems;
      CommonUtil.addIndexForListReverse(this.listEvent, this.pagingRequest.page, this.totalEvent);
      dialogSpinner.close();
    });
  }

  onSearchReset(){
    this.pagingRequest = {};
    this.initData();
    this.searchData();
  }



  showAddEventForm() {
    const dialogRef = this.dialog.open(EventFormComponent, {
      width: '0px',
      height: '0px',
      data: { event: {}},
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any) => {
        this.searchData();
    });
  }

  showUpdateForm(events: any, event:any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I'){
      const dialogRef = this.dialog.open(EventFormComponent, {
        width: '0px',
        height: '0px',
        data: {event: events},
        panelClass: 'custom-modalbox',
      });

      dialogRef.afterClosed().subscribe((response: any) => {
          this.searchData();
      });
    }
  }


  delete(data: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu0203Service.delete(data.key).subscribe({
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
      let tempList =  this.listEvent.filter(item =>  !this.listChecked.includes(item))
      this.listChecked.push(...tempList);
      this.changeFieldList(this.listChecked,true);
      // console.log(this.listChecked);
    }else{
      this.changeFieldList(this.listChecked,false);
      this.listChecked =  this.listChecked.filter(item => !this.listEvent.includes(item));
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

  checkEvent(eventModel : any){
    return eventModel.checked?true: false ;
    }

  handleListCheked(eventModel : any){
    if(this.listChecked.includes(eventModel)){
      var index = this.listChecked.indexOf(eventModel);
      if (index > -1) {
        eventModel.checked = false;
        this.listChecked.splice(index, 1);
      }
    }else{
      eventModel.checked = true ;
      this.listChecked.push(eventModel);
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
        this.edu0203Service.deleteListCheck(this.listChecked).subscribe({
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
