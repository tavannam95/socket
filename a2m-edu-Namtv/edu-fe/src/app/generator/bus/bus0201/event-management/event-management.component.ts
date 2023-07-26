import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { Bus0201Service } from 'src/app/services/bus/bus0201.service';
import { EventManagementFormComponent } from '../event-management-form/event-management-form.component';
import {LangUtil} from "../../../../utils/lang.util";
import { PaginationConfig } from 'src/app/config/pagination.config';
import { CommonUtil } from 'src/app/utils/common-util';

@Component({
  selector: 'app-event-management',
  templateUrl: './event-management.component.html',
  styleUrls: ['./event-management.component.css']
})
export class EventManagementComponent implements OnInit {
  eventLst: any = [];
  totalEvent: any;
  keySearch: any;
  row: any = PaginationConfig.PAGE_SIZE;
  page?: number;
  constructor(
    private trans: TranslateService,
    private bus0201Service:Bus0201Service,
    private dialog: MatDialog,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    public langUtil: LangUtil
  ) { }

  ngOnInit(): void {
    this.page = 0;
    this.keySearch = '';
    this.getData('');

  }
  refresh(){
    this.keySearch = '';
    this.getData(this.keySearch);
  }
  getPaging(event: any){
    const param = {
      "page": event.page,
      "row" : event.rows,
      "keySearch" : this.keySearch.toLowerCase()
    }
    this.bus0201Service.getPage(param).subscribe(result => {
      this.eventLst  = result.list;
      this.totalEvent = result.totalEvent;
      CommonUtil.addIndexForListReverse(this.eventLst, event.page, this.totalEvent);
    })
  }
  getData(keySearch: any){
    const param = {
      "page": this.page,
      "row" : this.row,
      "keySearch" : keySearch.toLowerCase()
    }
    this.bus0201Service.getPage(param).subscribe(result => {
      this.eventLst  = result.list;
      this.totalEvent = result.totalEvent;
      CommonUtil.addIndexForListReverse(this.eventLst, this.page, this.totalEvent);
    })
  }
  addEvent(){
    this.dialog.open(EventManagementFormComponent).afterClosed().subscribe(res=>{
      this.getData('');
    });
  }
  modifyEvent(data: any){
    this.dialog.open(
      EventManagementFormComponent,
      {
        data: data
      }).afterClosed().subscribe(res=>{
        this.getData(this.keySearch);
    });
  }
  delEvent(id: any){
    const param = {
      "id": id
    }
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      accept: () => {
        this.bus0201Service.delete(param).subscribe(res=>{
          if (res == 1) {
            this.toastr.success(
              this.trans.instant('message.success.deleteSuccess')
            );
            this.getData(this.keySearch);
          } else {
            this.toastr.error(this.trans.instant('message.error.deleteFailed'));
          }
        });

      }
    });

  }
}
