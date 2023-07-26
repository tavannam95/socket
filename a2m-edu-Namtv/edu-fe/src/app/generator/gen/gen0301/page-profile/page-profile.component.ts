import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { ChangePasswordComponent } from 'src/app/generator/popup/change-password/change-password.component';
import { ModifyEmailPopupComponent } from 'src/app/generator/popup/modify-email-popup/modify-email-popup.component';
import { TwoFaPopupComponent } from 'src/app/generator/popup/two-fa-popup/two-fa-popup.component';
import { VerifyEmailPopupComponent } from 'src/app/generator/popup/verify-email-popup/verify-email-popup.component';
import { CommonService } from 'src/app/services/common/common.service';
import { DataUserService } from 'src/app/services/data-user.service';
import { Gen0301Service } from 'src/app/services/gen/gen0301.service';

@Component({
  selector: 'app-page-profile',
  templateUrl: './page-profile.component.html',
  styleUrls: ['./page-profile.component.css']
})
export class PageProfileComponent implements OnInit {
  user: any = {};
  userTemp: any = {};
  statusModify : any;
  password:any;
  confirmPassword: any;
  newPassword: any;
  fa:boolean = true;
  dobTxt : string = '';
  img: any;
  imgPath: any = "data:image/gif;base64,R0lGODlhAQABAAAAACwAAAAAAQABAAA=";
  submitted : boolean = false;
  constructor(
    public dialog: MatDialog,
    private gen0301Service: Gen0301Service,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    private toastr: ToastrService,
    private dataUserService: DataUserService,

  ) { }

  ngOnInit(): void {
    this.getData();
  }
  getData() {
    this.dataUserService.currentData.subscribe(result => {
      result.dob = result.dob ? new Date(result.dob) : null;
      if(result.dob != null){
        this.dobTxt = result.dob.getFullYear() + '-' + (result.dob.getMonth()+1).toString().padStart(2, '0') + '-' + result.dob.getDate().toString().padStart(2, '0');
      }
      this.user = result;
      this.userTemp = structuredClone(this.user);
      if(result.dob == null){
        this.userTemp.dob = null;
      }
    });
  }

  modify() {
    this.userTemp.dob = this.userTemp.dob.getFullYear() + '-' + (this.userTemp.dob.getMonth()+1).toString().padStart(2, '0') + '-' + this.userTemp.dob.getDate().toString().padStart(2, '0');
    this.statusModify = true;
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.save'),
      accept: () => {
        // this.userTemp.gender = this.userTemp.gender.toString();

        this.gen0301Service.updateInfo(this.userTemp).subscribe((res) => {
          if (res) {
            this.toastr.success(
              this.trans.instant('message.success.updateSuccess')
            );
            this.statusModify = false;
            this.dataUserService.changeData(this.userTemp);
            this.getData();

          } else {
            this.toastr.error(this.trans.instant('message.error.saveFailed'));
          }
        });

      },
    });
  }
  modifyEmail() {
    this.dialog.open(ModifyEmailPopupComponent, {
      data: this.userTemp
    }).afterClosed().subscribe(data => {
      if (data == 1) {
        const param = {
          "USER_INFO_ID": this.user.userInfoId,
          "EMAIL": this.userTemp.email
        }
        this.gen0301Service.verifyEmail(param).subscribe();
        this.dialog.open(VerifyEmailPopupComponent, {
          data: param
        }).afterClosed().subscribe(res => {
          if(res == 1){
            this.dataUserService.changeData(this.userTemp);

          }
          this.getData();
        });
      }
    })
  }
  change2FAEnable() {
      this.dialog.open(TwoFaPopupComponent, {
        data: this.userTemp
      }).afterClosed().subscribe(res => {
        if(res == 1){
          this.dataUserService.changeData(this.userTemp);
        }
        this.getData();
      })
  }
  cancelModify(){
    this.statusModify = !this.statusModify;
    this.getData();
  }
  changePassword(){
    this.dialog.open(ChangePasswordComponent,
      {
        data: this.user.userInfoId
      });

  }

  onSelectImg(event: any){
    if (!event.target.files || !event.target.files[0]) {
      return;
  }
  this.img = event.target.files[0];
  this.confirmationService.confirm({
    header: this.trans.instant('message.confirm.title'),
    message: this.trans.instant('message.confirm.save'),
    accept: () => {

      this.gen0301Service.uploadAvatar(this.user.userUid, this.user.imgPath, this.img).subscribe(data => {

      });

      const reader = new FileReader();
        reader.readAsDataURL(this.img); // read file as data url
        reader.onload = (e) => { // called once readAsDataURL is completed

            this.imgPath = e.target?.result;
        };
    }
  });

  }
}
