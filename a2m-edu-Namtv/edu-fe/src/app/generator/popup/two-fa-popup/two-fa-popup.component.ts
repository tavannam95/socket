import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { NgxQrcodeElementTypes, NgxQrcodeErrorCorrectionLevels } from '@techiediaries/ngx-qrcode';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';

import { Gen0301Service } from 'src/app/services/gen/gen0301.service';

@Component({
  selector: 'app-two-fa-popup',
  templateUrl: './two-fa-popup.component.html',
  styleUrls: ['./two-fa-popup.component.css']
})
export class TwoFaPopupComponent implements OnInit {
  elementType: any = NgxQrcodeElementTypes.URL;
  correctionLevel: any = NgxQrcodeErrorCorrectionLevels.HIGH;
  value: any = "otpauth://totp/DG?";
  code:any;
  constructor(
    private dialogRef: MatDialogRef<TwoFaPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private gen0301Service: Gen0301Service,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    private toastr: ToastrService,
  ) { }

  ngOnInit(): void {
    this.code = '';
    this.getData();
  }
  async getData() {
    const param = {
      "USER_INFO_ID" : this.data.userInfoId,
    }
    await this.gen0301Service.get2FaKeyById(param).subscribe(data => {
      this.value = this.value + "secret=" + data.FA_KEY + "&issuer=" + data.USER_ID;
    })
  }
  verifyCode(data:any){
    const param = {
      "verifyCode": data.verifyCode,
      "USER_INFO_ID" : this.data.userInfoId,
      "FA_ENABLE" : this.data.twoFAEnable
    }
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('button.save.title'),
      accept: () => {
        this.gen0301Service.modify2FaEnable(param).subscribe(res=>{
          if (res == 1) {
            this.toastr.success(
              this.trans.instant('message.success.saveSuccess')
            );
          } else {
            this.toastr.error(this.trans.instant('message.error.saveFailed'));
          }
          this.dialogRef.close(res);
        })
      }
    });


  }
  closePopup(){
    this.dialogRef.close();
  }
}
