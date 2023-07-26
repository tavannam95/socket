import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { el } from '@fullcalendar/core/internal-common';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { ApproveService } from 'src/app/services/common/approve.service';
import { CommonService } from 'src/app/services/common/common.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import { FeedbaackComponent } from './feedbaack/feedbaack.component';

@Component({
  selector: 'app-approval-history',
  templateUrl: './approval-history.component.html',
  styleUrls: ['./approval-history.component.css'],
})
export class ApprovalHistoryComponent implements OnInit {

  approvalHistoryId: number = NaN;
  @Input() refId: number = NaN;
  @Input() refTable: number = NaN;
  @Output() changeViewType = new EventEmitter<string>();

  approveHistory: any = {
    empAprUid: '',
  };
  submitted: any = false;
  listApprover: any[] = [];
  statusTypes: any[] = [];
  approvalStatus: any = '';
  isApprover: Boolean = false;
  userUid: any = '';
  approverFullName: any = '_';
  MODE: String = 'VIEW_FORM';
  viewType : String = "";

  APPROVAL_STATUS_PENDING_COMM_CD =
    CommonConstant.APPROVAL_STATUS_PENDING_COMM_CD;
  _MODE_SUBMIT_FORM = 'SUBMIT_FORM';
  _MODE_VIEW_DETAIL = 'VIEW_FORM';
  _HAS_PERM_MODIFY = false;
  constructor(
    private approveService: ApproveService,
    private tccoStdService: TccoStdService,
    private commonService: CommonService,
    public dialog: MatDialog,
    private router: Router,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.userUid = AuthenticationUtil.getUserUid();
    this.getStatusType();
    this.getListApprover();

    this.commonService.aprovalHistoryInfo$.subscribe((response: any) => {
      this.refId = response.refId;
      this.refTable = response.refTable;
      this._HAS_PERM_MODIFY = response.hasPermModify;
      if (response.approvalHistoryId) {
        //get data approval
        this.getApprovalById(response.approvalHistoryId);
      } else {
        this.approvalStatus = this.trans.instant('approver.status.draft');
      }
    });
  }

  getApprovalById(id: any) {
    this.approveService.getApprovalById(id).subscribe((response: any) => {
      this.approveHistory = response[CommonConstant.DETAIL_KEY];

      this.aprStatus();

      this.isApprover =
        (this.approveHistory.empAprUid == this.userUid &&
          this.commonService.userHasRole(CommonConstant.ROLE_SYS_APPROVER));

      this.setApproverFullName();
    });
  }

  setApproverFullName() {
    const approver = this.listApprover.find(
      (apr) => apr.userUid == this.approveHistory.empAprUid
    );
    if(approver) this.approverFullName = approver.fullName;
    else this.approverFullName = "_"
  }

  getListApprover() {
    this.approveService.getListApprover({}).subscribe((response: any) => {
      this.listApprover = response[CommonConstant.LIST_KEY];
    });
  }

  getStatusType() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.APPROVAL_STATUS_COMM_CD)
      .subscribe((response) => {
        this.statusTypes = response;
        this.commonService.selectLangComcode(this.statusTypes);
      });
  }

  aprStatus() {
    if (!this.approveHistory) return;
    const comCd = this.statusTypes.find(
      (cd) => cd.commCd == this.approveHistory.approvalStatus
    );

    if (comCd) this.approvalStatus = comCd.label;
    else{
      this.approvalStatus = this.trans.instant('approver.status.draft');
    }
  }

  onApproval(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    }
    this.approveHistory.approvalStatus =
      CommonConstant.APPROVAL_STATUS_APPROVAL_COMM_CD;
    this.onSave();
  }

  onReject(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    }
    this.approveHistory.approvalStatus =
      CommonConstant.APPROVAL_STATUS_REJECT_COMM_CD;
    this.onSave();
  }

  onSave() {
    this.confirmationService.confirm({
      header: this.trans.instant('message.headerSubmitApprove'),
      message: this.trans.instant('message.submitApprove'),
      acceptLabel: this.trans.instant('button.confirm.title'),
      rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.approveService.save(this.approveHistory).subscribe((response) => {
          this.approveHistory = response[CommonConstant.DETAIL_KEY];
          this.toastrService.success(
            this.trans.instant('message.success.saveSuccess')
          );
          this.renewAlertApproval();
        });

        this.aprStatus();
      },
      reject: () => {},
    });
  }

  renewAlertApproval() {
    this.commonService.changeAproval('renew');
  }

  // addFeedBack(type : String ){
  //   const dialogRef = this.dialog.open(FeedbaackComponent, {
  //     width: '0px',
  //     height: '0px',
  //     data: { tsstUser: {}},
  //     panelClass: 'custom-modalbox',
  //   });
  //   dialogRef.afterClosed().subscribe((response: any) => {
  //     if (response){
  //       this.approveHistory.feedBack = response;
  //
  //       if(type=="approval"){
  //         this.approveHistory.approvalStatus = CommonConstant.APPROVAL_STATUS_APPROVAL_COMM_CD
  //       }else{
  //         this.approveHistory.approvalStatus = CommonConstant.APPROVAL_STATUS_REJECT_COMM_CD
  //       }
  //       this.onSave();
  //       // this.searchData();
  //     }
  //   });
  // }

  onSubmit() {
    const href = this.router.url;
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.title'),
      acceptLabel: this.trans.instant('button.confirm.title'),
      rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.approveService
          .sendApproveRequest(
            CommonConstant.APPROVAL_STATUS_PENDING_COMM_CD,
            this.refId,
            CommonConstant.TABLE_SUBJECT,
            href,
            this.approveHistory.empAprUid
          )
          .subscribe((response) => {
            this.approveHistory = response[CommonConstant.DETAIL_KEY];
            this.aprStatus();
            this.setApproverFullName();
            this.hideApprovalForm();
            this.toastrService.success(
              this.trans.instant('message.success.saveSuccess')
            );
          });
      },
      reject: () => {},
    });
  }


  onCancelSubmit() {
    this.approveHistory.deleteFlag = true;
    const href = this.router.url;
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.title'),
      acceptLabel: this.trans.instant('button.confirm.title'),
      rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.approveService.cancelSubmit(this.approveHistory).subscribe((response) => {
          this.approveHistory = response[CommonConstant.DETAIL_KEY];
          this.aprStatus();
          this.setApproverFullName();
          this.toastrService.success(
            this.trans.instant('message.success.saveSuccess')
          );
        });
      },
      reject: () => {},
    });
  }

  showApprovalForm() {
    this.MODE = this._MODE_SUBMIT_FORM;
  }

  hideApprovalForm() {
    this.MODE = this._MODE_VIEW_DETAIL;
  }

  changeViewTypeFnc(type : string ){
    this.viewType = type;
    this.changeViewType.emit(type);
  }

}
