import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { I18nConfig } from 'src/app/config/i18n.config';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { Edu0104Service } from 'src/app/services/edu/edu0104.service';
import { I18nService } from 'src/app/services/i18n.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { EquipmentFormComponent } from '../components/equipment-form/equipment-form.component';

@Component({
  selector: 'app-list-equipment',
  templateUrl: './list-equipment.component.html',
  styleUrls: ['./list-equipment.component.css']
})
export class ListEquipmentComponent implements OnInit {

  constructor(
    private edu0101Service: Edu0101Service,
    private toastr: ToastrService,
    private i18nService: I18nService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private commonService: CommonService,
    private tccoStdService: TccoStdService,
    private edu0104Service: Edu0104Service,
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

  equiqment!:any;
  totalEquiqment!: any;
  listEquiqment!:any[];
  equipTypes: any[] = [];

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  ngOnInit(): void {
    this.getEquipType();
    // this.getPositionUpCommCd(CommonConstant.POSITION_COMM_CD);
    this.tsstUser = {};
    this.listUser = [];

    this.listEquiqment=[];
    this.initData();
    this.searchData();

  }
  index = 0;
  getLabelEquipType(commCd : String){
    const cd = this.equipTypes.find( e =>  e.commCd == commCd )
    if(cd) return  cd.label;
    return "_";
  }

  getEquipType() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.EQUIPMENT_TYPE_COMM_CD)
      .subscribe((response) => {
        this.equipTypes = response;
        this.commonService.selectLangComcode(this.equipTypes);
      });
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
    this.edu0104Service.search(this.pagingRequest).subscribe((response) => {
      this.listEquiqment = response.list;
      this.totalEquiqment = response.totalItems;
      this.addFieldList(this.listEquiqment);
      CommonUtil.addIndexForListReverse(this.listEquiqment, this.pagingRequest.page, this.totalEquiqment);
      dialogSpinner.close();
    });
  }

  onSearchReset(){
    this.pagingRequest = {};
    this.initData();
    this.searchData();
  }



  showAddEquiqmentForm() {
    const dialogRef = this.dialog.open(EquipmentFormComponent, {
      width: '0px',
      height: '0px',
      data: { equiqment: {}},
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any) => {
        this.searchData();
    });
  }

  showUpdateForm(equiqment: any, event:any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I'){
      const dialogRef = this.dialog.open(EquipmentFormComponent, {
        width: '0px',
        height: '0px',
        data: {equiqment: equiqment},
        panelClass: 'custom-modalbox',
      });

      dialogRef.afterClosed().subscribe((response: any) => {
          this.searchData();
      });
    }
  }

  // delete1(userUid:string){
  //   this.edu0101Service.delete(userUid)
  //   .subscribe(tsstUser=>{
  //     this.searchData();
  //   })
  // }

  delete(equipment: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu0104Service.delete(equipment.key).subscribe({
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
      let tempList =  this.listEquiqment.filter(item =>  !this.listChecked.includes(item))
      this.listChecked.push(...tempList);
      this.changeFieldList(this.listChecked,true);
      // console.log(this.listChecked);
    }else{
      this.changeFieldList(this.listChecked,false);
      this.listChecked =  this.listChecked.filter(item => !this.listEquiqment.includes(item));
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

  checkEquiqment(equiqment : any){
    return equiqment.checked?true: false ;
    }

  handleListCheked(equiqment : any){
    if(this.listChecked.includes(equiqment)){
      var index = this.listChecked.indexOf(equiqment);
      if (index > -1) {
        equiqment.checked = false;
        this.listChecked.splice(index, 1);
      }
    }else{
      equiqment.checked = true;
      this.listChecked.push(equiqment);
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
        this.edu0104Service.deleteListCheck(this.listChecked).subscribe({
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
