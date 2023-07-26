
import { TranslateService } from '@ngx-translate/core';
import { FormGroup, FormControl } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ToastrService } from 'ngx-toastr';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import {
  Component,
  Inject,
  OnInit,
  ViewChild,
  AfterViewInit,
} from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { isNullOrUndefined } from 'is-what';
import { SendMessageAction } from 'src/app/model/mail/send-message-action';
import { SmtpMessage } from 'src/app/model/mail/smtp-message';
import { SendReplyMessageAction } from 'src/app/model/mail/send-reply-message-action';
import { MultiSelectComponent } from 'ng-multiselect-dropdown';
import { MailService } from 'src/app/services/mail/mail.service';
import { Router } from '@angular/router';
import { Sys0102Service } from 'src/app/services/sys/sys0102.service';
import { MailSignatureService } from 'src/app/services/mail/mailSignature.service';
import { DraftMailService } from 'src/app/services/mail/draftMail.service';
import { LoaderService } from 'src/app/services/loader.service';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Cookie } from 'ng2-cookies';
import { MailMessage } from 'src/app/model/mail/mail';
declare var $: any;

@Component({
  selector: 'app-popup-mail',
  templateUrl: './popup-mail.component.html',
  styleUrls: ['./popup-mail.component.scss'],
})
export class PopupMailComponent implements OnInit, AfterViewInit {
  // public Editor = DecoupledEditor;

  receiverEmail: any;
  ccEmail: any;
  bccEmail: any;

  listReceiverContactOld: any[] = [];
  listCcContactOld: any[] = [];
  listBccContactOld: any[] = [];

  listReceiverContactNew: any[] = [];
  listCcContactNew: any[] = [];
  listBccContactNew: any[] = [];

  listReceiverSelect: any[] = [];
  listCcSelect: any[] = [];
  listBccSelect: any[] = [];

  url: SafeResourceUrl | undefined;

  fileAttachment: any = new Array();

  @ViewChild('myEditor') myEditor: any;

  // editorConfig = {
  //   extraPlugins: [Base64UploaderPlugin],
  // };

  // loading: boolean = false;
  dutyList = [];
  fileToUpload: File[] = [];
  data: any[] = [];
  mailUsers = [];
  filteredUsers = [];
  emailSuggestions = [];
  editorConfig:any;
  Editor:any;
  sendMessageAction:any = SendMessageAction;
  smtpMessage:any = SmtpMessage;
  sendReplyMessageAction:any = SendReplyMessageAction;

  request: Object = [];
  type:any;
  mesDetail: any = {
    text: '',
    to: [],
    cc: [],
    bcc: [],
    from: '',
    subject: '',
  };

  mailUser: any = {};
  mailSignature: any = {};

  fields = { text: 'email', value: 'email' };

  showCc: boolean = false;
  showBcc: boolean = false;

  userInfo: any = {};
  messageAttachments: any = [];

  selectReceiverForm = new FormGroup({
    receiverEmail:  new FormControl([]),
    ccEmail: new FormControl([]),
    bccEmail: new FormControl([]),
  });

  spiner: boolean = false;
  LoaderSize: any = 0;
  sizeUpload: any = 0;
  attachmentSelected: string = "";
  uploadPer: any = 0;
  sizeDownload: any = 0;
  listSize: any[] = [];


  @ViewChild('receiverSelect') receiverSelect:any = MultiSelectComponent;
  @ViewChild('ccSelect') ccSelect:any = MultiSelectComponent;
  @ViewChild('bccSelect') bccSelect:any = MultiSelectComponent;

  constructor(
    private sanitized: DomSanitizer,
    private http: HttpClient,
    private toastrService: ToastrService,
    private mailService: MailService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<PopupMailComponent>,
    private router: Router,
    private sys0102Service: Sys0102Service,
    private mailSignatureService: MailSignatureService,
    private draftMailService: DraftMailService,
    private _translateService: TranslateService,
    @Inject(MAT_DIALOG_DATA) public mailData: any,
    private loaderService: LoaderService
  ) { }

  ngAfterViewInit(): void {
    $('#title-mail-input').focus(function () {
      $('.mb-3.title-input').addClass('focus');
    });

    $('#title-mail-input').focusout(function () {
      $('.mb-3.title-input').removeClass('focus');
    });
  }

  async ngOnInit(): Promise<void> {
    this.request = new MailMessage();
    this.type = CommonConstant.MAIL_DRAFT;
  }

  onCancel() {
    // if (this.tsstUser.userUid) {
    //   this.dialogRef.close(this.tsstUser);
    // } else {
      this.dialogRef.close();
    // }
  }
  openAttachReferer(info:any){}
  onClickCc(){}
  onClickBcc(){}
  onReady(event:any){}
  clickBtnUpload(){}
  onSelectFile(event:any, String:String){}
  downloadAttachment(item:any){}
  deleteAttachment(name:any){}
  onSend(type:any){}
}
