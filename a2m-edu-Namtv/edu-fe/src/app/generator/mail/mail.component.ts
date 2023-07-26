
import { MatDialog } from '@angular/material/dialog';
import { Component, OnInit } from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { MailService } from 'src/app/services/mail/mail.service';
import { CommonService } from 'src/app/services/common/common.service';
import { MailMessage } from 'src/app/model/mail/mail';
import { CommonConstant } from 'src/app/constants/common.constant';
import { PopupMailComponent } from './popup-mail/popup-mail.component';

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  styleUrls: ['./mail.component.scss'],
})
export class MailComponent implements OnInit {
  isMenuMobile: boolean = false;
  constructor(
    private dialog: MatDialog,
    private mailService: MailService,
    private commonService: CommonService
  ) { }

  countNewMail: number = 0;

  ngOnInit(): void {
    this.getCountNewMail();
    this.commonService.chkNewVersion();
  }

  openMailSetting() {
    // this.dialog.open(PopupMailSignatureComponent, {
    //   width: '1400px',
    //   height: '700px',
    //   disableClose: true,
    //   data: {},
    // });
  }

  openCompose() {
    this.dialog.open(PopupMailComponent, {
      width: '1200px',
      height: '740px',
      disableClose: true,
      data: {},
    });
  }

  getCountNewMail() {
    // var request = new MailMessage();
    // request.REAL_COUNT = 0;
    // request.START = 0;
    // request.USERNAME = Cookie.get('userMail');
    // request.FOLDER_NM = 'INBOX';
    // request.OFFSET = CommonConstant.PAGE_SIZE;
    // this.mailService
    //   .getMessagesByFolder(request)
    //   .toPromise()
    //   .then((res) => {
    //     this.countNewMail = res['realUnreadCount'];
    //   });
  }
}
