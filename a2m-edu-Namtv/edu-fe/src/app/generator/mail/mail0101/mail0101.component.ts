import {
  Component,
  HostListener,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Cookie } from 'ng2-cookies';
import { ToastrService } from 'ngx-toastr';
import { CommonConstant } from 'src/app/constants/common.constant';
import { MailMessage } from 'src/app/model/mail/mail';
import { LoaderService } from 'src/app/services/loader.service';
import { MailService } from 'src/app/services/mail/mail.service';
import { Sys0102Service } from 'src/app/services/sys/sys0102.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-mail0101',
  templateUrl: './mail0101.component.html',
  styleUrls: ['./mail0101.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class Mail0101Component implements OnInit {
  lstMessage: any[] = [];
  request: any;
  currentPage: number = 1;
  dataUser = [];
  isCheckedAll: boolean = false;
  enableBtnDelete: boolean = false;
  size: number = 150;
  constructor(
    private mailService: MailService,
    private router: Router,
    private _translateService: TranslateService,
    private toastr: ToastrService,
    private sys0102Service: Sys0102Service,
    private loaderService: LoaderService
  ) { }

  ngOnInit(): void {
    this.request = new MailMessage();
    this.initRequest();
    this.getInboxMessages();
    this.getUserInfo();
    this.getCountNewMail();
    this.getScreen();
  }

  initRequest() {
    this.request.REAL_COUNT = 0;
    this.request.START = 0;
    this.request.USERNAME = Cookie.get('userMail');
    this.request.FOLDER_NM = CommonConstant.FOLDER_MAIL_INBOX;
    this.request.OFFSET = CommonConstant.PAGE_SIZE;
  }

  async getUserInfo() {
    await this.sys0102Service
      .getAllUser({})
      .toPromise()
      .then((res) => {
        if (res) {
          this.dataUser = res;
        }
      });
  }

  async getInboxMessages() {
    this.loaderService.change(true)
    this.isCheckedAll = false;
    this.enableBtnDelete = false;
    await this.getUserInfo();
    await this.mailService
      .getMessagesByFolder(this.request)
      .toPromise()
      .then((res) => {
        this.loaderService.change(false)

        if (!res) {
          return;
        }
        this.lstMessage = res['messages'];
        this.request.REAL_COUNT = res['realCount'];
        this.lstMessage.forEach((item, index) => {
          item['index'] = index + 1;

          this.dataUser.forEach((ele) => {
            if (item['from'] == ele['email']) {
              item['from'] = ele['nameKr'] + ' <' + item['from'] + '>';
              // item['from'] = ele['nameKr'];
            }
          });
        });
      },
        (error) => {
          this.loaderService.change(false)
        });
  }

  doSearch() {
    this.request.START = 0;
    // this.request.OFFSET = 9
    this.getInboxMessages();
  }

  doReset() {
    this.request.SEARCH = null;
    this.request.START = 0;
    this.doSearch();
    this.currentPage = 1;
  }

  searchByPage(event:any) {
    if (event) {
      this.currentPage = event;
      this.request.START = (event - 1) * CommonConstant.PAGE_SIZE;
      this.getInboxMessages();
    }
    this.isCheckedAll = false;
  }

  doDelete(uid: number) {
    // this.confirmationService.confirm({
    //   header: this._translateService.instant('message.confirm.title'),
    //   message: this._translateService.instant('message.confirm.delete'),
    //   icon: 'fa fa-edit',

    //   accept: () => {
    //     let reqDel = new MailMessage()
    //     reqDel.FOLDER_NM = 'INBOX'
    //     reqDel.USERNAME = Cookie.get('userMail')
    //     reqDel.UIDS.push(uid)

    //     this.mailService.deleteMailServerByUids(reqDel).toPromise().then(res => {
    //       if (!res) {
    //         return;
    //       }
    //       if(res['STATUS'] == 'SUCCESS') {
    //         this.toastr.success(this._translateService.instant('message.success.deleteSuccess'));
    //       }else if(res['STATUS'] == 'FAIL') {
    //         this.toastr.error(this._translateService.instant('message.error.systemErr'));
    //       }
    //       // this.initRequest();
    //       this.getInboxMessages();
    //     })
    //   }
    // });
    Swal.fire({
      title: this._translateService.instant('message.confirm.title'),
      text: this._translateService.instant('common.msg.Confirm.delete'),
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#34c38f',
      cancelButtonColor: '#f46a6a',
      confirmButtonText: 'Yes',
    }).then((result) => {
      if (result.value) {
        this.loaderService.change(true)
        let reqDel = new MailMessage();
        reqDel.FOLDER_NM = CommonConstant.FOLDER_MAIL_INBOX;
        reqDel.USERNAME = Cookie.get('userMail');
        reqDel.UIDS.push(uid);

        this.mailService
          .deleteMailServerByUids(reqDel)
          .toPromise()
          .then((res) => {
            this.loaderService.change(false)

            if (!res) {
              return;
            }
            if (res['STATUS'] == 'SUCCESS') {
              // this.toastr.success(
              //   this._translateService.instant('message.success.deleteSuccess')
              // );
              Swal.fire(
                'Deleted',
                this._translateService.instant('message.success.deleteSuccess'),
                'success'
              );
            } else if (res['STATUS'] == 'FAIL') {
              // this.toastr.error(
              //   this._translateService.instant('message.error.systemErr')
              // );
              Swal.fire(
                'Deleted',
                this._translateService.instant('message.error.systemErr'),
                'error'
              );
            }
            // this.initRequest();
            this.getInboxMessages();
          },
            (error) => {
              this.loaderService.change(false)
            }
          );
      }
    },
    );
  }

  deleteSelected() {
    let chkDel: Boolean = false;
    this.lstMessage.forEach((ele:any) => {
      if (ele.checked === true) {
        chkDel = true;
      }
    });
    if (chkDel) {
      Swal.fire({
        title: this._translateService.instant('message.confirm.title'),
        text: this._translateService.instant('common.msg.Confirm.delete'),
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#34c38f',
        cancelButtonColor: '#f46a6a',
        confirmButtonText: 'Yes',
      }).then((result) => {
        if (result.value) {
          this.loaderService.change(true)

          let reqDel = new MailMessage();
          reqDel.FOLDER_NM = CommonConstant.FOLDER_MAIL_INBOX;
          reqDel.USERNAME = Cookie.get('userMail');
          this.lstMessage.forEach((ele:any) => {
            if (ele.checked === true) {
              reqDel.UIDS.push(ele.uid);
            }
          });

          this.mailService
            .deleteMailServerByUids(reqDel)
            .toPromise()
            .then((res) => {
              this.loaderService.change(false)
              if (!res) {
                return;
              }
              if (res['STATUS'] == 'SUCCESS') {
                Swal.fire(
                  'Deleted',
                  this._translateService.instant(
                    'message.success.deleteSuccess'
                  ),
                  'success'
                );
              } else if (res['STATUS'] == 'FAIL') {
                Swal.fire(
                  'Deleted',
                  this._translateService.instant('message.error.systemErr'),
                  'error'
                );
              }
              // this.initRequest();
              this.getInboxMessages();
            },
              (error) => {
                this.loaderService.change(false)
              }
            );
        }
      });
    } else {
      this.toastr.warning('Xin vui lòng chọn mail cần xóa !');
    }
  }

  getDetailMessage(item:any) {
    this.router.navigate(['/mail/mail0101/detail'], {
      queryParams: { uid: item.uid, forder: CommonConstant.FOLDER_MAIL_INBOX },
    });
  }

  selectAll(event:any) {
    if (event.currentTarget.checked == true) {
      this.lstMessage.forEach((ele:any) => {
        ele['checked'] = true;
      });
    } else if (event.currentTarget.checked == false) {
      this.lstMessage.forEach((ele:any) => {
        ele['checked'] = false;
      });
    }
  }

  chkReadOrUnRead(flags: []) {
    for (let i = 0; i < flags.length; i++) {
      if (flags[i] === 'SEEN') {
        return true;
      }
    }
    return false;
  }

  onItemChange() {
    let countChecked: number = 0;
    let countUnChecked: number = 0;
    this.lstMessage.forEach((ele) => {
      if (ele['checked'] == true) {
        this.enableBtnDelete = true;
        countChecked++;
      } else {
        countUnChecked += 1;
      }
    });
    if (countChecked != this.lstMessage.length) {
      this.isCheckedAll = false;
    } else if (countChecked == this.lstMessage.length) {
      this.isCheckedAll = true;
    }
    if (countUnChecked == this.lstMessage.length) {
      this.enableBtnDelete = false;
    }
  }

  markAsReadOrUnRead(type: string) {
    let chkDel: Boolean = false;
    this.lstMessage.forEach((ele:any) => {
      if (ele.checked === true) {
        chkDel = true;
      }
    });
    if (chkDel) {
      let message =
        type == 'MARK_AS_UNREAD'
          ? this._translateService.instant('common.msg.Confirm.MARK_AS_UNREAD')
          : this._translateService.instant('common.msg.Confirm.MARK_AS_READ');
      Swal.fire({
        title: this._translateService.instant('message.confirm.title'),
        text: message,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#34c38f',
        cancelButtonColor: '#f46a6a',
        confirmButtonText: 'Yes',
      }).then((result) => {
        if (result.value) {
          const reqDel = new MailMessage();
          reqDel.FOLDER_NM = CommonConstant.FOLDER_MAIL_INBOX;
          reqDel['TYPE'] = type;
          reqDel.USERNAME = Cookie.get('userMail');
          this.lstMessage.forEach((ele:any) => {
            if (ele.checked === true) {
              reqDel.UIDS.push(ele.uid);
            }
          });
          this.mailService
            .markAsReadOrUnRead(reqDel)
            .toPromise()
            .then((res) => {
              if (!res) {
                return;
              }
              if (res['STATUS'] == 'SUCCESS') {
                Swal.fire('message', message, 'success');
              } else if (res['STATUS'] == 'FAIL') {
                Swal.fire(
                  'message',
                  this._translateService.instant('message.error.systemErr'),
                  'error'
                );
              }
              this.getInboxMessages();
              this.getCountNewMail();
            });
        }
      });
    } else {
    }
  }

  getCountNewMail() {
    var request = new MailMessage();
    request.REAL_COUNT = 0;
    request.START = 0;
    request.USERNAME = Cookie.get('userMail');
    request.FOLDER_NM = 'INBOX';
    request.OFFSET = CommonConstant.PAGE_SIZE;
    this.mailService
      .getMessagesByFolder(request)
      .toPromise()
      .then((res) => {
        const idCountNewMail = document.getElementById('countNewMail');
        if(idCountNewMail!= null){
          idCountNewMail.textContent = '(' + res['realUnreadCount'] + ')';
        }
      });
  }

  getScreen() {
    var innerWidth = window.innerWidth;
    if (innerWidth < 430) {
      this.size = 4;
    } else if (innerWidth < 600) {
      this.size = 10;
    } else if (innerWidth < 876) {
      this.size = 30;
    } else {
      this.size = 150;
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event:any) {
    var innerWidth = window.innerWidth;
    if (innerWidth < 430) {
      this.size = 4;
    } else if (innerWidth < 600) {
      this.size = 10;
    } else if (innerWidth < 876) {
      this.size = 30;
    } else {
      this.size = 150;
    }
  }
}
