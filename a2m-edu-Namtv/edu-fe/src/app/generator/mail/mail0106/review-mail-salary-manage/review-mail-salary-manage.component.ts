import { AfterViewInit, Component, Inject, OnInit, ViewEncapsulation } from '@angular/core';
import { isNullOrUndefined } from 'is-what';
import { ToastrService } from 'ngx-toastr';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { MailService } from 'src/app/services/mail/mail.service';
import { LoaderService } from 'src/app/services/loader.service';

@Component({
  selector: 'app-review-mail-salary-manage',
  templateUrl: './review-mail-salary-manage.component.html',
  styleUrls: ['./review-mail-salary-manage.component.scss'],
  // encapsulation: ViewEncapsulation.None
})
export class ReviewMailSalaryManageComponent implements OnInit, AfterViewInit {

  from: string = "";
  subject: string = "";
  salaryMails: any[] = [];
  attachmentForAll: any[] = [];

  // public Editor = DecoupledEditor;
  // editorConfig = {
  //   extraPlugins: [Base64UploaderPlugin],
  // };
  isDisabled = true;

  constructor(
    private toastrService: ToastrService,
    private mailService: MailService,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<ReviewMailSalaryManageComponent>,
    private _translateService: TranslateService,
    private loaderService: LoaderService

  ) { }

  ngOnInit(): void {
    this.salaryMails = this.data.salaryMails;
    this.attachmentForAll = this.data.attachmentForAll;
    this.initAttachListName();
    if (!isNullOrUndefined(this.salaryMails[0])) {
      this.from = this.salaryMails[0].from;
      this.subject = this.salaryMails[0].subject;
    }
  }

  initAttachListName() {
    let attachmentForAllName = "";
    if (!isNullOrUndefined(this.attachmentForAll) && this.attachmentForAll.length > 0) {
      for (let index = 0; index < this.attachmentForAll.length; index++) {
        const element = this.attachmentForAll[index];
        if (index == this.attachmentForAll.length - 1) {
          attachmentForAllName = attachmentForAllName + element['name'];
        } else {
          attachmentForAllName = attachmentForAllName + element['name'] + ",";
        }
      }
    }
    this.salaryMails.forEach(ele => {
      let attachListName = "";
      if (attachmentForAllName !== "") {
        attachListName = attachmentForAllName;
      }

      if (!isNullOrUndefined(ele.privateFiles) && ele.privateFiles.length > 0) {
        if (attachmentForAllName !== "") {
          attachListName = attachListName + ",";
        }
        for (let index = 0; index < ele.privateFiles.length; index++) {
          const element = ele.privateFiles[index];
          if (index == ele.privateFiles.length - 1) {
            attachListName = attachListName + element['name'];
          } else {
            attachListName = attachListName + element['name'] + ",";
          }
        }
      }
      ele.attachListName = attachListName;
    })
  }

  ngAfterViewInit(): void {

  }

  closePopup() {
    this.dialogRef.close();
  }

  public onReady(editor:any) {
    editor.ui.getEditableElement().parentElement.insertBefore(
      editor.ui.view.toolbar.element,
      editor.ui.getEditableElement()
    );
  }

  async saveFileToDisk(salaryMails: any[]) {
    // save AttachmentForAll to Disk
    let formDataForAll = new FormData();
    if (!isNullOrUndefined(this.attachmentForAll) && this.attachmentForAll.length > 0) {
      this.attachmentForAll.forEach(attachment => {
        if (!isNullOrUndefined(attachment)) {
          if (attachment['size'] > 26214400) {
            const msg = this._translateService.instant('common.maximumFileSize');
            this.toastrService.error(msg);
            return;
          }
          formDataForAll.append(attachment['name'], attachment);
        }
      })
      if (!isNullOrUndefined(formDataForAll)) {
        await this.mailService.fileUploadSiteVN(formDataForAll).toPromise();
      }
    }

    // save PrivateFile to Disk
    for (const ele of salaryMails) {
      let attachments :any[] = [];
      if (!isNullOrUndefined(this.attachmentForAll) && this.attachmentForAll.length > 0) {
        this.attachmentForAll.forEach(ele => {
          attachments.push({
            "contentType": ele['type'],
            "size": ele['size'],
            "name": ele['name']
          })
        })
      }
      let i = 0;
      let formData = new FormData();
      let privateFiles: any[] = ele.privateFiles;
      if (!isNullOrUndefined(privateFiles)) {
        privateFiles.forEach(privateFile => {
          if (!isNullOrUndefined(privateFile)) {
            if (privateFile['size'] > 26214400) {
              const msg = this._translateService.instant('common.maximumFileSize');
              this.toastrService.error(msg);
              return;
            }
            formData.append(privateFile['name'], privateFile);
            i++;
            attachments.push({
              "contentType": privateFile['type'],
              "size": privateFile['size'],
              "name": privateFile['name']
            })
          }
        })
        if (!isNullOrUndefined(formData)) {
          await this.mailService.fileUploadSiteVN(formData).toPromise();
        }
      }
      ele.attachments = attachments;
    }
  }

  async onSend() {
    this.loaderService.change(true)
    await this.saveFileToDisk(this.salaryMails);
    this.mailService.sendMailSalary(this.salaryMails).subscribe(
      response => {
        this.loaderService.change(false)
        if (response['success'] === true) {
          const msg = this._translateService.instant('mail.merge.send.success');
          this.toastrService.success(msg);
          this. closePopup();
        } else if (response['success'] === false) {
          this.toastrService.error(response['message']);
        }
      }, error => {
        this.loaderService.change(false)
        const msg = this._translateService.instant('mail.merge.send.failed');
            this.toastrService.error(msg);
      });
  }

}
