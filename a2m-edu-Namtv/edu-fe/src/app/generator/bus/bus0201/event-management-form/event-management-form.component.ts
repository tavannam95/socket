import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { GenEventMgt } from 'src/app/model/bus/event-mgt';
import { Bus0201Service } from 'src/app/services/bus/bus0201.service';
import {LangUtil} from "../../../../utils/lang.util";


@Component({
  selector: 'app-event-management-form',
  templateUrl: './event-management-form.component.html',
  styleUrls: ['./event-management-form.component.css']
})
export class EventManagementFormComponent implements OnInit {
  eventMng: GenEventMgt = new GenEventMgt();
  eventTmp:any = {};
  submitted: any = false;

  constructor(
    private dialogRef: MatDialogRef<EventManagementFormComponent>,
    private bus0201Service: Bus0201Service,
    private confirmationService: ConfirmationService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private toastr: ToastrService,
    private trans: TranslateService,
    public langUtil: LangUtil
  ) { }

  ngOnInit(): void {
    if(this.data){
      this.eventTmp = structuredClone(this.data)
      this.eventMng.id = this.eventTmp.EVENT_ID;
      this.eventMng.name = this.eventTmp.EVENT_NM;
      this.eventMng.fromDate = new Date(this.eventTmp.FROM_DT);
      this.eventMng.toDate = new Date(this.eventTmp.TO_DT);
      this.eventMng.discount = this.eventTmp.DISCOUNT;
    }

  }
  // onChangeDate({value}: { value: any }) {
  //   this.eventMng.fromDate = new Date(value);
  // }

  closePopup(){
    this.dialogRef.close();
  }
  onSave(data: any, invalid: any) {
    if (invalid || this.eventMng.toDate < this.eventMng.fromDate){
      this.submitted = true;
      return;
    }
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('button.save.title'),
      accept: () => {
        data.fromDate = this.formatDate(data.fromDate);
        data.toDate = this.formatDate(data.toDate);
        if(this.data){
          data["id"] = this.eventTmp.EVENT_ID;
          this.bus0201Service.modify(data).subscribe(res=>{
              if (res == 1) {
                this.toastr.success(
                  this.trans.instant('message.success.saveSuccess')
                );
              } else {
                this.toastr.error(this.trans.instant('message.error.saveFailed'));
              };
          });
        }else{
          var date = new Date();
          data["id"] = date.getTime();
          this.bus0201Service.insert(data).subscribe(res=>{
            if (res == 1) {
              this.toastr.success(
                this.trans.instant('message.success.saveSuccess')
              );
            } else {
              this.toastr.error(this.trans.instant('message.error.saveFailed'));
            }
          })
        }

        this.dialogRef.close();
      },
    });

  }
  formatDate(date: Date) {
    return date.getFullYear() + '-' + (date.getMonth()+1).toString().padStart(2, '0') + '-' + date.getDate().toString().padStart(2, '0');
  }
}
