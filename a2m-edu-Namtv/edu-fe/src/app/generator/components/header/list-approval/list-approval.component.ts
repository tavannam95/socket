import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { ApproveService } from 'src/app/services/common/approve.service';
import { CommonService } from 'src/app/services/common/common.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';

@Component({
  selector: 'app-list-approval',
  templateUrl: './list-approval.component.html',
  styleUrls: ['./list-approval.component.css']
})
export class ListApprovalComponent implements OnInit {
  public static readonly APPROVAL_STATUS_PENDING_COMM_CD = CommonConstant.APPROVAL_STATUS_PENDING_COMM_CD;
  public static readonly APPROVAL_STATUS_APPROVAL_COMM_CD = CommonConstant.APPROVAL_STATUS_APPROVAL_COMM_CD;
  public static readonly APPROVAL_STATUS_REJECT_COMM_CD = CommonConstant.APPROVAL_STATUS_REJECT_COMM_CD;

  listPending: any = [];
  isAdmin = false;

  @Output() renewTotal = new EventEmitter();
  constructor(
    private approveService: ApproveService,
    private router: Router,
    private commonService: CommonService,
  ) { }

  ngOnInit(): void {
    this.isAdmin = false; //this.commonService.userHasRole(CommonConstant.ROLE_SYS_ADMIN)
    this.initData();
  }

  initData(){
    this.commonService.approval$.subscribe(data => {
      // 
      this.searchListPeding();
    })
  }

  searchListPeding(){
    this.approveService.searchListPeding( AuthenticationUtil.getUserUid(), this.isAdmin ).subscribe((response) => {
      // 
      this.listPending = response[CommonConstant.LIST_KEY];
      const totalApprove = this.listPending.length;
      this.renewTotal.emit(totalApprove);
    });
  }

  goDocument(pending: any){
    const url = pending.documentUrl.substring(1);
    this.router.navigateByUrl(url);
  }
}
