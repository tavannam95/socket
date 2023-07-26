import { Component, HostListener, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
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
  selector: 'app-mail0104',
  templateUrl: './mail0104.component.html',
  styleUrls: ['./mail0104.component.scss']
})
export class Mail0104Component implements OnInit {
  lstMessage:any[] = []
  now: Date = new Date();
  request:any;
  currentPage: number = 1;
  pageSize: number = CommonConstant.PAGE_SIZE
  dataUser = [];
  isCheckedAll: boolean = false;
  enableBtnDelete: boolean = false;
  size: number = 150;
  constructor(
    private mailService: MailService,
    private _translateService: TranslateService,
    private toastr: ToastrService,
    private router: Router,
    private sys0102Service: Sys0102Service,
    private loaderService: LoaderService
  ) { }

  ngOnInit(): void {
    this.request = new MailMessage()
    this.initRequest();
    this.getTrashMessages()
    this.getScreen()
  }

  initRequest() {
    // this.request.REAL_COUNT = 0
    this.request.START = 0
    this.request.USERNAME = Cookie.get('userMail')
    this.request.FOLDER_NM = CommonConstant.FOLDER_MAIL_TRASH
    this.request.OFFSET = CommonConstant.PAGE_SIZE
  }

  async getUserInfo() {
    await this.sys0102Service.getAllUser({
    }).toPromise().then(
      res => {
        if (res) {
          this.dataUser = res;
        }
      }
    )
  }

  async getTrashMessages() {
    this.loaderService.change(true)
    this.isCheckedAll = false;
    this.enableBtnDelete = false;
    await this.getUserInfo();
    await this.mailService.getMessagesByFolder(this.request).toPromise().then(res => {
      this.loaderService.change(false)
      if (!res) {
        return;
      }

      this.lstMessage = res['messages']
      this.request.REAL_COUNT = res['realCount']
      this.lstMessage.forEach((item, index) => {
        item['index'] = index + 1
        item['lst_recieve'] = item['to'][0]
        if (item['to'].length > 1) {
          for (let i = 1; i < item['to'].length; i++) {
            item['lst_recieve'] = item['lst_recieve'] + ', ' + item['to'][i]
          }
        };

        this.dataUser.forEach(ele => {
          if (item['lst_recieve'] == ele['email']) {
            item['lst_recieve'] = ele['nameKr'] + ' <' + item['lst_recieve'] + '>';
          }
        });
      });
    },
      (error) => {
        this.loaderService.change(false)
      }
    )
  }

  doDelete() {
    let chkDel: Boolean = false;
    this.lstMessage.forEach(ele => {
      if (ele.checked === true) {
        chkDel = true;
      }
    })

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
          let reqDel = new MailMessage()
          reqDel.FOLDER_NM = CommonConstant.FOLDER_MAIL_TRASH
          reqDel.USERNAME = Cookie.get('userMail')
          this.lstMessage.forEach(ele => {
            if (ele.checked === true) {
              reqDel.UIDS.push(ele.uid);
            }
          })
          this.mailService.deleteMailServerByUids(reqDel).toPromise().then(res => {
            this.loaderService.change(false)
            if (!res) {
              return;
            }
            if (res['STATUS'] == 'SUCCESS') {
              this.toastr.success(this._translateService.instant('message.success.deleteSuccess'));
            } else if (res['STATUS'] == 'FAIL') {
              this.toastr.error(this._translateService.instant('message.error.systemErr'));
            }
            // this.initRequest();
            this.getTrashMessages();
          },
            (error) => {
              this.loaderService.change(false)
            }
          )
        }
      });
    } else {
      this.toastr.warning("Xin vui lòng chọn mail cần xóa !");
    }
  }

  doSearch() {
    this.request.START = 0
    this.getTrashMessages();
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
      this.getTrashMessages();
    }
    this.isCheckedAll = false;
  }

  getDetailMessage(item:any) {
    this.router.navigate(['/mail/mail0104/detail'], { queryParams: { "uid": item.uid, "forder": CommonConstant.FOLDER_MAIL_TRASH } });
  }

  // restoreEmail(item) {
  // }

  deleteFromTrash(uid: number) {
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
        let reqDel = new MailMessage()
        reqDel.FOLDER_NM = CommonConstant.FOLDER_MAIL_TRASH
        reqDel.USERNAME = Cookie.get('userMail')
        reqDel.UIDS.push(uid)

        this.mailService.deleteMailServerByUids(reqDel).toPromise().then(res => {
          this.loaderService.change(false)
          if (!res) {
            return;
          }
          if (res['STATUS'] == 'SUCCESS') {
            this.toastr.success(this._translateService.instant('message.success.deleteSuccess'));
          } else if (res['STATUS'] == 'FAIL') {
            this.toastr.error(this._translateService.instant('message.error.systemErr'));
          }
          // this.initRequest();
          this.getTrashMessages();
        },
          (error) => {
            this.loaderService.change(false)
          }
        )
      }
    });
  }

  restoreMail(type: number) {
    let chkDel: Boolean = false;
    this.lstMessage.forEach(ele => {
      if (ele.checked === true) {
        chkDel = true;
      }
    })

    if (chkDel) {
      Swal.fire({
        title: this._translateService.instant('message.confirm.title'),
        text: this._translateService.instant('common.confirmText'),
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#34c38f',
        cancelButtonColor: '#f46a6a',
        confirmButtonText: 'Yes',
      }).then((result) => {
        if (result.value) {
          this.loaderService.change(true)
          let reqDel = new MailMessage()
          reqDel.FOLDER_NM = CommonConstant.FOLDER_MAIL_TRASH
          reqDel.USERNAME = Cookie.get('userMail')
          this.lstMessage.forEach(ele => {
            if (ele.checked === true) {
              reqDel.UIDS.push(ele.uid);
            }
          })
          reqDel['FOLDER_TO'] = type === 1 ? CommonConstant.FOLDER_MAIL_INBOX : type === 2 ? CommonConstant.FOLDER_MAIL_DRAFTS : type === 3 ? CommonConstant.FOLDER_MAIL_SENT : ""
          this.mailService.restoreMailServerByUids(reqDel).toPromise().then(res => {
            this.loaderService.change(false)
            if (!res) {
              return;
            }
            if (res['STATUS'] == 'SUCCESS') {
              this.toastr.success(this._translateService.instant('message.success.moveFolder'));
            } else if (res['STATUS'] == 'FAIL') {
              this.toastr.error(this._translateService.instant('message.error.systemErr'));
            }
            // this.initRequest();
            this.getTrashMessages();
          }).catch(err => {
            this.loaderService.change(false)
            this.toastr.error(err.error.error);
          })
        }
      });
    } else {
      this.toastr.warning(this._translateService.instant('message.warning.moveFolder'));
    }
  }

  selectAll(event:any) {
    if (event.currentTarget.checked == true) {
      this.lstMessage.forEach(ele => {
        ele['checked'] = true;
      })
    } else if (event.currentTarget.checked == false) {
      this.lstMessage.forEach(ele => {
        ele['checked'] = false;
      })
    }
  }

  onItemChange() {
    let countChecked: number = 0;
    let countUnChecked: number = 0;
    this.lstMessage.forEach(ele => {
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
    if (countUnChecked == (this.lstMessage.length)) {
      this.enableBtnDelete = false;
    }
  }

  markAsReadOrUnRead(type: string) {
    let chkDel: Boolean = false;
    this.lstMessage.forEach((ele) => {
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
          let reqDel = new MailMessage();
          reqDel.FOLDER_NM = CommonConstant.FOLDER_MAIL_TRASH;
          reqDel['TYPE'] = type;
          reqDel.USERNAME = Cookie.get('userMail');
          this.lstMessage.forEach((ele) => {
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
              this.getTrashMessages();
            });
        }
      });
    } else {

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

  getScreen() {
    var innerWidth = window.innerWidth;
    if (innerWidth < 430) {
      this.size = 4;
    }
    else if (innerWidth < 600) {
      this.size = 10;
    } else if (innerWidth < 876) {
      this.size = 30;
    }
    else {
      this.size = 150;
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event:any) {
    var innerWidth = window.innerWidth;
    if (innerWidth < 430) {
      this.size = 4;
    }
    else if (innerWidth < 600) {
      this.size = 10;
    } else if (innerWidth < 876) {
      this.size = 30;
    }
    else {
      this.size = 150;
    }
  }

}
