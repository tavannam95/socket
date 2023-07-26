import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { TsstUserInfo } from 'src/app/model/gen/tsst-user-info';
import { TsstUser } from 'src/app/model/sys/tsst-user';
import { SignUpService } from 'src/app/services/sign-up.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  user: any = {};
  userInfo: TsstUserInfo = new TsstUserInfo();
  gender: any = true;
  listPos: any = [];
  submitted: boolean = false;
  // pos: any = '';
  constructor(
    private trans: TranslateService,
    private toastr: ToastrService,
    private signupService: SignUpService) { }

  ngOnInit(): void {
    // this.getListPos();
  }

  getListPos(){
    this.signupService.getListPos().subscribe(data => {
      // this.listPos = data;
    });
  }

  onSave(invalid: any){
    if (invalid || this.user.password != this.user.confirmPassword){
      this.submitted = true;
      return;
    }
    this.user.dob = new Date(this.user.dob);
    // data.dob = data.dob.getFullYear() + '-' + (data.dob.getMonth()+1).toString().padStart(2, '0') + '-' + data.dob.getDate().toString().padStart(2, '0');
    if(this.user.confirmPassword === this.user.password){
      this.userInfo.gender = this.gender;
      this.userInfo.emailVerifyKey = this.makeToken(30);
      this.userInfo.twoFAKey = this.makeToken(30);
      this.user['tsstUserInfo'] = this.userInfo;
      let time  = new Date();
      // data['userUid'] = time.getTime();
      this.signupService.insertUser(this.user).subscribe(result => {
        if(result.status == true){
          this.toastr.success(this.trans.instant('message.success'));
          this.user = {};
          this.userInfo = new TsstUserInfo;
        }else{
          this.toastr.error(this.trans.instant('message.error.saveFailed'));
        }
      });
    }else{
      this.toastr.error(this.trans.instant('message.confirm.confirmPasswordNotMatch'));
    }

  }

  makeToken(length: number) {
    var result = '';
    var val = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
    var charactersLength = val.length;
    for (var i = 0; i < length; i++) {
      result += val.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result.toLocaleUpperCase();
  }
}
