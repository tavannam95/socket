import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService, TreeNode } from 'primeng/api';
import { Bus0101Service } from 'src/app/services/bus/bus0101.service';
import { SubcribeMngFormComponent } from '../subcribe-mng-form/subcribe-mng-form.component';
import { LangUtil } from "../../../../utils/lang.util";
import { CommonUtil } from 'src/app/utils/common-util';

@Component({
  selector: 'app-subcribe-management',
  templateUrl: './subcribe-management.component.html',
  styleUrls: ['./subcribe-management.component.css']
})
export class SubcribeManagementComponent implements OnInit {
  selectedNode!: TreeNode;
  licenseLst: any = [];
  keySearch: string='';
  row: any = 10;
  page?: number;
  totalSubscribe :any;
  statusList: any = [];
  status: any = '';
  constructor(
    private trans: TranslateService,
    private bus0101Service:Bus0101Service,
    private dialog: MatDialog,
    public langUtil: LangUtil,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService
    ) { }

  ngOnInit(): void {
    this.keySearch = '';
    this.page = 0;
    this.statusList =[
      {
        name: this.trans.instant('sys.sys0101.status.all'),
        value: ''
      },
      {
        name: this.trans.instant('bus.subscribe.status.subscribed.title'),
        value: 1
      },
      {
        name: this.trans.instant('bus.subscribe.status.unSubscribed.title'),
        value: 0
      }
    ];

    this.getData(this.keySearch);


  }
  refresh(){
    this.keySearch = '';
    this.status = '';
    this.getData(this.keySearch);
  }
  getPaging(event : any){
    const param = {
      "page": event.page,
      "row" : event.rows,
      "keySearch": this.keySearch.toLowerCase(),
      "status": this.status
    }
    this.bus0101Service.getPage(param).subscribe(data => {
      this.licenseLst = data.list;
      CommonUtil.addIndexForListReverse(this.licenseLst, event.page, this.totalSubscribe);
      this.caculatePrice(this.licenseLst);
    });
  }
  getData(keySearch: any){
    const param = {
      "page": this.page,
      "row" : this.row,
      "keySearch": keySearch.toLowerCase(),
      "status": this.status
    }
    this.bus0101Service.getPage(param).subscribe(data => {
      if(data.totalItems){
        this.licenseLst = data.list;
        this.totalSubscribe = data.totalItems;
        CommonUtil.addIndexForListReverse(this.licenseLst, this.page, this.totalSubscribe);
        this.caculatePrice(this.licenseLst);
      }

    });
  }

  caculatePrice(dataList:any[]){
    dataList.forEach(data => {
      var insDate = new Date(data.INS_DT)
      var expDate = new Date(data.EXPIRED_DT)
      var time = this.monthDiff(insDate, expDate);
      data["TOTAL_PRICE"] = time * data.PRICE
    })
  }
  monthDiff(d1: any, d2: any) {
    var months;
    months = (d2.getFullYear() - d1.getFullYear()) * 12;
    months -= d1.getMonth();
    months += d2.getMonth();
    return months <= 0 ? 1 : months;
}
  addLicense(){
    this.dialog.open(SubcribeMngFormComponent).afterClosed().subscribe(res=>{
      this.getData('');
    });
  }
  modifyLicense(license: any){
    var licenseTmp = structuredClone(license);
    licenseTmp.CLIENT_ID = + licenseTmp.CLIENT_ID;
    licenseTmp.INS_DT = new Date(licenseTmp.INS_DT);
    licenseTmp.EXPIRED_DT = new Date(licenseTmp.EXPIRED_DT);

    this.dialog.open(
      SubcribeMngFormComponent,{
        data: licenseTmp
      }).afterClosed().subscribe(res=>{
        this.getData('');
    });
  }
  async changeStatus(license:any){
    if(license.STATUS === 1){
      var stt = 0;
    }else{
      var stt = 1;
    }
    const param = {
      "STATUS": stt,
      "LICENSE_ID" : license.LICENSE_ID
    }
    await this.confirmationService.confirm({
      header: this.trans.instant('bus.subscribe.changeStt.title'),
      message: this.trans.instant('bus.subscribe.changeStt.msg'),
      accept: () => {
        this.bus0101Service.changeStatus(param).subscribe(res => {
        if (res == 1) {
          this.toastr.success(
            this.trans.instant('message.success.saveSuccess')
          );
          license.STATUS = stt;
        } else {
          this.toastr.error(this.trans.instant('message.error.saveFailed'));
        }
        });

      },
    });

  }

}
