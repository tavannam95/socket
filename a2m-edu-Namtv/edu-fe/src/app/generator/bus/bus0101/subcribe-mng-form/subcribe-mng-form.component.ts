import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { Bus0101Service } from 'src/app/services/bus/bus0101.service';

@Component({
  selector: 'app-subcribe-mng-form',
  templateUrl: './subcribe-mng-form.component.html',
  styleUrls: ['./subcribe-mng-form.component.css']
})
export class SubcribeMngFormComponent implements OnInit {
  customerLst!: any;
  duration: any = {};
  clientData: any = {};
  submitted: any = false;
  constructor(
    private bus0101Service: Bus0101Service,
    private dialogRef: MatDialogRef<SubcribeMngFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private confirmationService: ConfirmationService,
    private toastr: ToastrService,
    private trans: TranslateService) { }

  ngOnInit(): void {
    this.getCustomerLst();
    if(this.data){
      this.clientData = this.data;
    }
  }
  getCustomerLst() {
    this.bus0101Service.getListCustomer().subscribe(res => {
      this.customerLst = res;
    })
  }
  closePopup() {
    this.dialogRef.close();
  }
  onSave(invalid: any) {
    if (invalid || this.clientData.EXPIRED_DT < this.clientData.INS_DT){
      this.submitted = true;
      return;
    }
    if(!this.data){
      let installDate = new Date();
      let val = installDate.getTime().toString() + this.clientData.CLIENT_ID;
      var token = this.makeid(20, val);

      const param = {
        "LICENSE_ID": installDate.getTime().toString(),
        "CLIENT_ID": this.clientData.CLIENT_ID,
        "INS_DT" : this.formatDate(this.clientData.INS_DT),
        "EXPIRED_DT" : this.formatDate(this.clientData.EXPIRED_DT),
        "TOKEN": token,
        "PRICE" : this.clientData.PRICE,
      }
      this.bus0101Service.insert(param).subscribe(res => {
        if (res == 1) {
          this.toastr.success(
            this.trans.instant('message.success.saveSuccess')
          );
        } else {
          this.toastr.error(this.trans.instant('message.error.saveFailed'));
        }
        this.dialogRef.close();
      });
    }else{
      this.confirmationService.confirm({
        header: this.trans.instant('message.confirm.title'),
        message: this.trans.instant('button.save.title'),
        accept: () => {
          const param = {
            "INS_DT" : this.formatDate(this.clientData.INS_DT),
            "EXPIRED_DT" : this.formatDate(this.clientData.EXPIRED_DT),
            "LICENSE_ID": this.data.LICENSE_ID,
            "PRICE" : this.clientData.PRICE,
          }
          this.bus0101Service.modify(param).subscribe(res => {
            if (res == 1) {
              this.toastr.success(
                this.trans.instant('message.success.saveSuccess')
              );
            } else {
              this.toastr.error(this.trans.instant('message.error.saveFailed'));
            }
            this.dialogRef.close();
          });
        },
      });

    }

  }
  formatDate(date: Date) {
    return date.getFullYear()
            + '-' + (date.getMonth()+1).toString().padStart(2, '0')
            + '-' + date.getDate().toString().padStart(2, '0');
  }
  makeid(length: number, time: string) {
    var result = '';
    var val = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz' + time
    var charactersLength = val.length;
    for (var i = 0; i < length; i++) {
      result += val.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result.toLocaleUpperCase();
  }
}






