import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { Gen0301Service } from 'src/app/services/gen/gen0301.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  password:any;
  confirmPassword: any;
  newPassword: any;
  submitted: boolean = false;
  constructor(
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private dialogRef: MatDialogRef<ChangePasswordComponent>,
    private trans: TranslateService,
    private gen0301Service: Gen0301Service,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
  }
  onSave(formData: any, invalid: any){
    if (invalid){
      this.submitted = true;
      return;
    }
    if(formData.newPassword === formData.confirmPassword){
      this.confirmationService.confirm({
        header: this.trans.instant('button.changePassword.title'),
        message: this.trans.instant('message.confirm.changePassword'),
        accept: () => {
          formData["userInfoId"] = this.data;
            this.gen0301Service.changePassword(formData).subscribe(res => {
              if (res) {
                this.toastr.success(
                  this.trans.instant('message.success.updateSuccess')
                );
              } else {
                this.toastr.error(this.trans.instant('message.error.saveFailed'));
              }
              this.closePopup();
            });
        }
      });
    }else{
      this.toastr.error(this.trans.instant('message.confirm.confirmPasswordNotMatch'));
    }
  }
  closePopup(){
    this.dialogRef.close();
  }
}
