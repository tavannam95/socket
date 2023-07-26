
import { TranslateService } from '@ngx-translate/core';
import { HttpClient, HttpEvent, HttpEventType } from '@angular/common/http';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { Cookie } from 'ng2-cookies';
import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import Swal from 'sweetalert2';
import { MailService } from 'src/app/services/mail/mail.service';
import { UserInfoService } from 'src/app/services/user-info.service';
import { LoaderService } from 'src/app/services/loader.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { CommonConstant } from 'src/app/constants/common.constant';
import { PopupMailComponent } from '../popup-mail/popup-mail.component';
import { MailMessage } from 'src/app/model/mail/mail';

@Component({
  selector: 'app-mail0105',
  templateUrl: './mail0105.component.html',
  styleUrls: ['./mail0105.component.scss'],
})
export class Mail0105Component implements OnInit {
  // public Editor = DecoupledEditor;

  isCheckDetail: boolean = false;
  request: Object = {};
  isOpen = false;
  mesDetail: any = {
    text: '',
    from: '',
    to: '',
    cc: '',
    bcc: '',
    subject: '',
  };
  avtSender: any;
  emailSender: any;
  testMarkup!: SafeHtml;
  LoaderSize: any = 0;

  messageAttachments: any = [];
  constructor(
    private _router: Router,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private mailService: MailService,
    private http: HttpClient,
    private sanitized: DomSanitizer,
    private _translateService: TranslateService,
    private toastr: ToastrService,
    private userInfoService: UserInfoService,
    private loaderService: LoaderService

  ) { }

  ngOnInit(): void {
    console.log(this.request);
    // this.request['USERNAME'] = Cookie.get('userMail');

    // this.route.queryParams.subscribe((params) => {
    //   if (params['uid']) {
    //     this.request['uid'] = params['uid'];
    //   }
    //   if (params['forder']) {
    //     this.request['FOLDER_NM'] = params['forder'];
    //   }
    // });
    this.getMessageDetailByUid();
    this.getAvtUser();
  }

  getMessageDetailByUid() {
    this.loaderService.change(true)
    this.mailService
      .getMessageDetailByUid(this.request)
      .subscribe((response) => {
        this.loaderService.change(false)
        if (response) {
          // console.log(response);

          response['mailHeaders'].forEach((element:any) => {
            if (element['name'] === 'To') {
              this.mesDetail.to = element['value'];
            }
            if (element['name'] === 'Cc') {
              this.mesDetail.cc = element['value'];
            }
            if (element['name'] === 'From') {
              this.mesDetail.from = element['value'];
            }
            if (element['name'] === 'Subject') {
              this.mesDetail.subject = element['value'];
            }
            if (element['name'] === 'Date')
              this.mesDetail.date = element['value'];
          });

          this.getAvtUser();

          this.mesDetail.text = response.text;
          this.testMarkup = this.sanitized.bypassSecurityTrustHtml(
            this.mesDetail.text
          );
          if (response['messageAttachments'].length > 0) {
            this.messageAttachments = CommonUtil.determineFileType(
              response['messageAttachments']
            );
          }
        }
      },
        (error) => {
          this.loaderService.change(false)
        }
      );
  }

  back() {
    // if (this.request['FOLDER_NM'] == CommonConstant.FOLDER_MAIL_INBOX) {
    //   this._router.navigate(['/mail/mail0101']);
    // }
    // if (this.request['FOLDER_NM'] == CommonConstant.FOLDER_MAIL_SENT) {
    //   this._router.navigate(['/mail/mail0102']);
    // }
    // if (this.request['FOLDER_NM'] == CommonConstant.FOLDER_MAIL_DRAFTS) {
    //   this._router.navigate(['/mail/mail0103']);
    // }
    // if (this.request['FOLDER_NM'] == CommonConstant.FOLDER_MAIL_TRASH) {
    //   this._router.navigate(['/mail/mail0104']);
    // }
  }

  replyMail() {
    // this.openPopupMail(
    //   this.request['uid'],
    //   this.request['FOLDER_NM'],
    //   CommonConstant.MAIL_REPLY
    // );
  }

  replyMailAll() {
    // this.openPopupMail(
    //   this.request['uid'],
    //   this.request['FOLDER_NM'],
    //   CommonConstant.MAIL_REPLY_ALL
    // );
  }

  forwardMail() {
    // this.openPopupMail(
    //   this.request['uid'],
    //   this.request['FOLDER_NM'],
    //   CommonConstant.MAIL_FORWARD
    // );
  }

  openPopupMail(uid: any, folder: any, type: any) {
    this.dialog.open(PopupMailComponent, {
      width: '1200px',
      height: '740px',
      disableClose: true,
      data: {
        uid: uid,
        folder: folder,
        type: type,
      },
    });
  }

  viewDetail() {
    this.isCheckDetail = !this.isCheckDetail;
  }

  sizeDownload: any = 0;
  attachmentSelected: string = "";

  downloadAttachment(att:any) {
    this.sizeDownload = att.size;
    this.attachmentSelected = att.name;
    var url = '';
    // var url =
    //   environment.apiMailHost +
    //   '/mail/downloadAttachment?USERNAME=' +
    //   Cookie.get('userMail') +
    //   '&name=' +
    //   att.name +
    //   '&folder=' +
    //   this.request['FOLDER_NM'] +
    //   '&uid=' +
    //   this.request['uid'];
    this.downloadFile(url, att.name);
  }

  downloadFile(url: string, fileName: string): void {
    // this.loaderService.change(true)
    this.http
      .get(url, {
        responseType: 'blob' as 'json',
        reportProgress: true, observe: "events"
      })
      .subscribe(
        (event: any) => {
          // console.log(event);
          switch (event.type) {
            case HttpEventType.Sent:
              // console.log('Request sent!');
              break;
            case HttpEventType.ResponseHeader:
              // console.log('Response header received!');
              break;
            // case HttpEventType.UploadProgress:
            //   const percentDone = Math.round(100 * event.loaded / event.total);
            // console.log(`File is ${percentDone}% uploaded.`);
            case HttpEventType.DownloadProgress:
              const kbLoaded = event.loaded;
              // console.log(`Download in progress! ${kbLoaded}Kb loaded`);
              this.LoaderSize = (kbLoaded / this.sizeDownload) * 100;
              // console.log(this.LoaderSize)
              break;
            case HttpEventType.Response: {
              // console.log('😺 Done!', response.body);
              let response = event.body;
              // this.loaderService.change(false)
              if (!response) {
                return;
              }

              if (!response.size) {
                alert('File not found in server!');
                return;
              }

              let dataType = response.type;
              let binaryData = [];
              binaryData.push(response);
              let downloadLink = document.createElement('a');
              downloadLink.href = window.URL.createObjectURL(
                new Blob(binaryData, { type: dataType })
              );
              downloadLink.setAttribute('download', fileName);
              document.body.appendChild(downloadLink);
              downloadLink.click();
              downloadLink.remove();
              this.attachmentSelected = "";
              this.LoaderSize = 0;
              break;
            }
          }
        },
        (error) => {
          // this.loaderService.change(false)
        }
      );
  }

  doDelete(uid: any) {
    // let mailUid = parseInt(this.request['uid']);
    let mailUid = '';
    Swal.fire({
      title: this._translateService.instant('message.confirm.title'),
      text: this._translateService.instant('message.confirm.delete'),
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#34c38f',
      cancelButtonColor: '#f46a6a',
      confirmButtonText: 'Yes',
    }).then((result) => {
      if (result.value) {
        let reqDel = new MailMessage();
        // reqDel.FOLDER_NM = this.request['FOLDER_NM'];
        reqDel.USERNAME = Cookie.get('userMail');
        reqDel.UIDS.push(mailUid);

        this.mailService
          .deleteMailServerByUids(reqDel)
          .toPromise()
          .then((res) => {
            if (!res) {
              return;
            }
            if (res['STATUS'] == 'SUCCESS') {
              this.toastr.success(
                this._translateService.instant('message.success.deleteSuccess')
              );
            } else if (res['STATUS'] == 'FAIL') {
              this.toastr.error(
                this._translateService.instant('message.error.systemErr')
              );
            }
            this.back();
          });
      }
    });
  }

  getAvtUser() {
    const param = {
      email: this.mesDetail.from,
    };
    this.userInfoService.getAvtUserByEmail(param).subscribe((res) => {
      if (res) {
        // this.avtSender = `${environment.apiHost}/public/resource/imageView-v2/${res.IMG_PATH}`;
      }
      // }else{
      //   this.avtSender ='/assets/images/default-avatar.jpg'
      // }
    });
  }

  // public onReady(editor:any) {
  //   editor.ui.getEditableElement().parentElement.insertBefore(
  //     editor.ui.view.toolbar.element,
  //     editor.ui.getEditableElement()
  //   );
  // }


}
