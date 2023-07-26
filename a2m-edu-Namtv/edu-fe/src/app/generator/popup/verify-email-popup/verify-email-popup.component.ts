import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { Gen0301Service } from 'src/app/services/gen/gen0301.service';

@Component({
  selector: 'app-verify-email-popup',
  templateUrl: './verify-email-popup.component.html',
  styleUrls: ['./verify-email-popup.component.css']
})
export class VerifyEmailPopupComponent implements OnInit {
  digit1: string = '';
  digit2: string = '';
  digit3: string = '';
  digit4: string = '';
  digit5: string = '';
  digit6: string = '';
  constructor(
    private toastr: ToastrService,
    private trans: TranslateService,
    private dialog: MatDialog,
    private dialogRef: MatDialogRef<VerifyEmailPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private gen0301Service: Gen0301Service) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.data["code"] = this.digit1 + this.digit2 + this.digit3 + this.digit4 + this.digit5 + this.digit6;
    this.gen0301Service.verifyCode(this.data).subscribe(res => {
      if (res) {
        this.gen0301Service.modifyEmail(this.data).subscribe(result => {
          if (result) {
            this.toastr.success(this.trans.instant('message.success.updateSuccess'));
          }
        });
      } else {
        this.toastr.error(this.trans.instant('message.error.saveFailed'));
      }
    });
    this.closePopup(1);
  }

  closePopup(res: any) {
    this.dialogRef.close(res);
  }

}
