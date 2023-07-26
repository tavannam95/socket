import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { Gen0301Service } from 'src/app/services/gen/gen0301.service';

@Component({
  selector: 'app-modify-email-popup',
  templateUrl: './modify-email-popup.component.html',
  styleUrls: ['./modify-email-popup.component.css']
})
export class ModifyEmailPopupComponent implements OnInit {

  emailTmp: string = '';
  submitted: boolean = false;

  constructor(
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private dialog: MatDialog,
    private dialogRef: MatDialogRef<ModifyEmailPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private trans: TranslateService,
    private gen0301Service: Gen0301Service) { }

  ngOnInit(): void {
    this.emailTmp = this.data.email;
  }
  onSave(invalid: any){
    if (invalid){
      this.submitted = true;
      return;
    }
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.save'),
      accept: () => {
        if (this.emailTmp != this.data.email) {
           this.data.email = this.emailTmp;
          this.closePopup(1);
        } else {
          this.closePopup(0);
        }
      },
    });


  }
  closePopup(result: any) {
    this.dialogRef.close(result);
  }
}
