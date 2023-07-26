import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { SafeResourceUrl } from '@angular/platform-browser';
import { MailSalaryInfo } from '../_model/mail-salary-info';
import { NgForm } from '@angular/forms';
import { SendMessageAction } from '../_model/send-message-action';
import { SmtpMessage } from '../_model/smtp-message';
import { SendReplyMessageAction } from '../_model/send-reply-message-action';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';
import { isNullOrUndefined } from 'is-what';
import { ReviewMailSalaryManageComponent } from './review-mail-salary-manage/review-mail-salary-manage.component';
import { TranslateService } from '@ngx-translate/core';
import { MailService } from 'src/app/services/mail/mail.service';
import { MailUserService } from 'src/app/services/mail/mailUser.service';

@Component({
  selector: 'app-mail0106',
  templateUrl: './mail0106.component.html',
  styleUrls: ['./mail0106.component.scss']
})
export class Mail0106Component implements OnInit {

  excelSalaryFile: any;
  allSendFiles: any[] = [];
  excelSalaryFileName: string = "";
  allSendFileName: string = "";
  isFileSalary: boolean = false;
  isFileSendAll: boolean = false;
  mailSalaryInfo: any;
  expenditureForm: any[] = [];
  url: SafeResourceUrl = "";

  attachmentFolderName: string = "";
  privateFilesUpload: any[] = [];

  @ViewChild('myEditor') myEditor: any;
  @ViewChild('gmailColumn') gmailColumn!: ElementRef;
  @ViewChild('attachmentColumn') attachmentColumn!: ElementRef;
  @ViewChild('startRow') startRow!: ElementRef;
  @ViewChild('finishRow') finishRow!: ElementRef;
  @ViewChild('subject') subject!: ElementRef;
  // public Editor = DecoupledEditor;
  // editorConfig = {
  //   extraPlugins: [Base64UploaderPlugin],
  // };

  @ViewChild('myForm') myForm!: NgForm;

  loading: boolean = false;
  dutyList = [];
  fileToUpload: File[] = [];
  data: any[] = [];
  mailUsers = [];

  sendMessageAction: SendMessageAction = {};
  smtpMessage!: SmtpMessage;
  sendReplyMessageAction!: SendReplyMessageAction;
  userInfo: any = {};
  messageAttachments: any = [];

  constructor(
    private toastrService: ToastrService,
    private mailService: MailService,
    private dialog: MatDialog,
    private router: Router,
    private mailUserService: MailUserService,
    private translate: TranslateService
  ) {
  }

  ngOnInit(): void {
    this.mailSalaryInfo = new MailSalaryInfo();
    this.mailSalaryInfo.content = "";
    this.mailSalaryInfo.fromUser = Cookie.get('userMail');
  }

  ngAfterViewInit(): void {
    // this.neroUtil.init('attachment');
  }

  getMailUser() {
    this.mailUserService.getMailByID(this.userInfo).subscribe(
      (response) => {
        this.dutyList = response['datas'];
        this.dutyList.forEach(element => {
          // if (element['empNo'] == this.userInfo['empNo']) {
          element['email'] = element['receiveMail'];
          element['nameKr'] = element['receiveMail'];
          // }
          this.data.push(element);
        });
      })
  };

  convertNullToString(value: any) {
    if (value == null || value == undefined) {
      return "";
    } else {
      return value;
    }
  }

  public onReady(editor:any) {
    editor.ui.getEditableElement().parentElement.insertBefore(
      editor.ui.view.toolbar.element,
      editor.ui.getEditableElement()
    );
  }


  selectFileSalary() {
    let ele = document.getElementById('fileInput');
    ele?.click();
  }

  async genExcelToMail() {
    if (isNullOrUndefined(this.mailSalaryInfo.subject) || this.mailSalaryInfo.subject == '') {
      const msg = this.translate.instant('com.com0101.search.titlePlaceholder');
      this.toastrService.error(msg);
      this.subject.nativeElement.focus();
      return;
    } else if (isNullOrUndefined(this.excelSalaryFile)) {
      const msg = this.translate.instant('mail.pleaseUploadFile');
      this.toastrService.error(msg);
      return;
    } else if (isNullOrUndefined(this.mailSalaryInfo.gmailColumn) || this.mailSalaryInfo.gmailColumn == '') {
      const msg = this.translate.instant("mail.merge.placeholder.column");
      this.toastrService.error(msg);
      this.gmailColumn.nativeElement.focus();
      return;
    } else if (isNullOrUndefined(this.mailSalaryInfo.startRow)) {
      const msg = this.translate.instant('mail.merge.placeholder.startRow')
      this.toastrService.error(msg);
      this.startRow.nativeElement.focus();
      return;
    } else if (isNullOrUndefined(this.mailSalaryInfo.finishRow)) {
      const msg = this.translate.instant('mail.merge.placeholder.finishRow');
      this.toastrService.error(msg);
      this.finishRow.nativeElement.focus();
      return;
    }
    if (!isNullOrUndefined(this.mailSalaryInfo.attachmentColumn) && this.mailSalaryInfo.attachmentColumn !== '') {
      if (isNullOrUndefined(this.attachmentFolderName) || this.attachmentFolderName == "") {
        const msg = this.translate.instant('mail.merge.uploadFile.info.title');
        this.toastrService.error(msg);
        return;
      }
    }
    let formData = new FormData();
    formData.append('file', this.excelSalaryFile);
    for (var key in this.mailSalaryInfo) {
      formData.append(key, this.mailSalaryInfo[key]);
    }
    this.mailService.genExcelToMail(formData).subscribe(response => {
      let mailSalarys: any[] = response;
      mailSalarys.forEach(element => {
        if (!isNullOrUndefined(element.privateFileListName)) {
          let privateFileNames: String[] = element.privateFileListName.split(",");
          let privateFiles: any[] = [];
          privateFileNames.forEach(fileName => {
            let privateFile = this.privateFilesUpload.find(ele => ele.name == fileName);
            privateFiles.push(privateFile);
          })
          element.privateFiles = privateFiles;
        }
      });

      this.openReviewMailSalary(mailSalarys, this.allSendFiles);
    }, error => {

    });
  }

  openReviewMailSalary(salaryMails:any, attachmentForAll:any) {
    // DataUtilService.fixPosisionPage(true);
    const dialogRef = this.dialog.open(ReviewMailSalaryManageComponent, {
      width: '1200px',
      height: '740px',
      maxWidth: '90vw',
      disableClose: true,
      data: {
        salaryMails: salaryMails,
        attachmentForAll: attachmentForAll
      },
      // panelClass: 'custom-modalbox'
    });

    dialogRef.afterClosed().subscribe(
      result => {
        // DataUtilService.fixPosisionPage(false);
      }
    );
  }

  uploadFileSalary(event:any) {
    this.isFileSalary = true;
    this.excelSalaryFile = event.target.files[0];
    this.excelSalaryFileName = this.excelSalaryFile.name;
  }

  // uploadFileSendAll(event) {
  //     this.isFileSendAll = true;
  //     this.allSendFile = event.target.files[0];
  //     this.allSendFileName = this.allSendFile.name;
  // }

  onRemoveFileSalary() {
    this.excelSalaryFile = null;
    this.excelSalaryFileName = '';
    this.isFileSalary = false;
  }

  // onRemoveFileSendAll() {
  //     this.allSendFile = null;
  //     this.allSendFileName = '';
  //     this.isFileSendAll = false;
  // }

  onFolderSelected(event:any) {
    if (event.target.files.length > 0) {
      let files = event.target.files;
      this.attachmentFolderName = files[0].webkitRelativePath.split("/")[0];
      for (let i = 0; i < files.length; i++) {
        this.privateFilesUpload.push(files[i]);
      }
    }
    else {
      const msg = this.translate.instant('mail.merge.uploadFileAgain.title');
      this.toastrService.error(msg);
      return;
    }
  }

  onRemoveFolder() {
    this.privateFilesUpload = [];
    this.attachmentFolderName = '';
  }

  clickUlfSendAll() {
    document.getElementById("sendAllFiles")?.click();
  }

  deleteFileSendAll(name: string) {
    this.allSendFiles.forEach((f, index) => {
      if (f.name == name) {
        this.allSendFiles.splice(index, 1);
      }
    })
  }

  getFilesSendAll(files: FileList) {
    if (files.item(0) != null || files.item(0) != undefined) {
      this.allSendFiles.forEach(f => {
        // if (f.name == files.item(0)['name']) {
        //   return;
        // }
      })
      this.allSendFiles.push(files.item(0));
    }
  }

}
