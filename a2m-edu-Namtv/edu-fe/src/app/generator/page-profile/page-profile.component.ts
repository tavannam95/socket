import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { ChangePasswordComponent } from 'src/app/generator/popup/change-password/change-password.component';
import { ModifyEmailPopupComponent } from 'src/app/generator/popup/modify-email-popup/modify-email-popup.component';
import { TwoFaPopupComponent } from 'src/app/generator/popup/two-fa-popup/two-fa-popup.component';
import { VerifyEmailPopupComponent } from 'src/app/generator/popup/verify-email-popup/verify-email-popup.component';
import { CommonService } from 'src/app/services/common/common.service';
import { DataUserService } from 'src/app/services/data-user.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { Gen0301Service } from 'src/app/services/gen/gen0301.service';
import { environment } from 'src/environments/environment';

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
  isGuest : boolean = false;
  url : any ='';
  type : String ='' ;
  configCkeditor: any = {};
  isEditIntro = false ;



  baseUrl = environment.apiURL
  constructor(
    public dialog: MatDialog,
    private gen0301Service: Gen0301Service,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    private toastr: ToastrService,
    private dataUserService: DataUserService,
    private uploadService: FileUploadService,
    private route : ActivatedRoute,
    private CommonService : CommonService,

  ) { }

  ngOnInit(): void {
    this.initConfigCk();
    this.url = 'assets/images/users/client.png';
    this.route.queryParams.subscribe( (params :any) => {
      this.type =  params.type;
      if(this.type){
        this.getCustomerData();
      }else{
        this.type='';
        this.getData();
      }
    })

  }

  initConfigCk(){
    this.configCkeditor = this.CommonService.getCkConfigDefault();
  }

  onEditorChange(event: any) {

  }

  getCustomerData(){
    this.CommonService.otherProfile$.subscribe((response:any)=>{
      //debugger
      this.setData(response);
    })
  }

  getData() {

    this.dataUserService.currentData.subscribe(result => {
      this.setData(result);
    });
  }

  setData(result :any){
    result.dob = result.dob ? new Date(result.dob) : null;
    if(result.dob != null){
      this.dobTxt = result.dob.getFullYear() + '-' + (result.dob.getMonth()+1).toString().padStart(2, '0') + '-' + result.dob.getDate().toString().padStart(2, '0');
    }
    this.user = result;
    this.userTemp = structuredClone(this.user);
    if(this.userTemp.imgPath!=''||this.userTemp.imgPath!=null ||this.userTemp.imgPath != undefined){
      this.url =  this.baseUrl+'/tcco-files/getFile/'+this.userTemp.dob+'?filePath='+this.userTemp.imgPath;
    }
    if(result.dob == null){
      this.userTemp.dob = null;
    }
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
    //debugger
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
      this.uploadService.deleteFile(this.userTemp.imgPath).subscribe()
        this.uploadService.singleFileUpload(this.img).subscribe(
          (_response: any) => {
            console.log(_response)
            this.url='';
            this.url+=this.baseUrl+'/tcco-files/getFile/'+_response.atchFleSeq+'?filePath='+_response.flePath;
            console.log(this.user);
            this.userTemp.imgPath = _response.flePath;
            console.log(typeof this.userTemp.dob)
            if(typeof this.userTemp.dob !='string'){
              this.userTemp.dob = this.userTemp.dob.getFullYear() + '-' + (this.userTemp.dob.getMonth()+1).toString().padStart(2, '0') + '-' + this.userTemp.dob.getDate().toString().padStart(2, '0');
            }

            this.gen0301Service.updateInfo(this.userTemp).subscribe((res) => {
              if (res) {
                this.toastr.success(
                  this.trans.instant('message.success.updateSuccess')
                );
                this.statusModify = false;
                this.dataUserService.changeData(this.userTemp);
              } else {
                this.toastr.error(this.trans.instant('message.error.saveFailed'));
              }
            });
          });

      const reader = new FileReader();
        reader.readAsDataURL(this.img); // read file as data url
        reader.onload = (e) => { // called once readAsDataURL is completed

            this.imgPath = e.target?.result;
        };
    }
  });

  }


  cancelModifyIntro(){
    this.isEditIntro = !this.isEditIntro;
    this.getData();
  }

  modifyIntro(){
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.save'),
      accept: () => {

        this.userTemp.dob = this.userTemp.dob.getFullYear() + '-' + (this.userTemp.dob.getMonth()+1).toString().padStart(2, '0') + '-' + this.userTemp.dob.getDate().toString().padStart(2, '0');
        this.gen0301Service.updateInfo(this.userTemp).subscribe((res) => {
          if (res) {
            this.toastr.success(
              this.trans.instant('message.success.updateSuccess')
            );
            this.isEditIntro = false;
            this.dataUserService.changeData(this.userTemp);
          } else {
            this.toastr.error(this.trans.instant('message.error.saveFailed'));
          }
        });

      },
    });
  }

}
