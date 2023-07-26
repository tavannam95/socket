import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { ApproveService } from 'src/app/services/common/approve.service';
import { CommonService } from 'src/app/services/common/common.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';

@Component({
  selector: 'app-list-approved',
  templateUrl: './list-approved.component.html',
  styleUrls: ['./list-approved.component.css']
})
export class ListApprovedComponent implements OnInit {

  list: any = [];
  isAdmin = false;

  @Output() renewTotal = new EventEmitter();
  constructor(
    private approveService: ApproveService,
    private router: Router,
    private commonService: CommonService,
  ) { }

  ngOnInit(): void {
    this.initData();
  }

  initData(){
    this.commonService.approval$.subscribe(data => {
      // 
      this.search();
    })
  }

  search(){
    var param = {
      insUid: AuthenticationUtil.getUserUid(),
      //approvalStatus: CommonConstant.APPROVAL_STATUS_APPROVAL_COMM_CD,
      noti: true,
      viewedAprHistory: false,
    }
    this.approveService.search( param ).subscribe((response) => {
      // 
      this.list = response[CommonConstant.LIST_KEY];
      const totalApproved = this.list.length;
      this.renewTotal.emit(totalApproved);
    });
  }

  goDocument(apr: any){
    this.doViewed(apr);
    const url = apr.documentUrl.substring(1);
    this.router.navigateByUrl(url);
  }

  doViewed(approveHistory: any){
    approveHistory.viewedAprHistory = true;
    this.approveService.save(approveHistory).subscribe((response) => {
      this.search();
      this.renewAlertApproval();
    });
  }

  renewAlertApproval() {
    this.commonService.changeAproval('renew');
  }
}
