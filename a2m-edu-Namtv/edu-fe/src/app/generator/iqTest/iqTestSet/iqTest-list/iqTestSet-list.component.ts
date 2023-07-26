import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { Clipboard } from '@angular/cdk/clipboard';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { CommonService } from 'src/app/services/common/common.service';
import { IqTestService } from 'src/app/services/quesIq/iqTest.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonUtil } from 'src/app/utils/common-util';


@Component({
  selector: 'app-iqTest-list',
  templateUrl: './iqTestSet-list.component.html',
  styleUrls: ['./iqTestSet-list.component.css']
})
export class IqTestSetListComponent implements OnInit {

  disable = true;
  quesTypes: any = {};
  quesType : any;
  quesLevels: any = {};
  queslevel : any;
  quesCategorys : any = {};
  quesCategory : any

  pageRequest : any = {};
  userInfo : any;
  listIqTest: any[] = [];
  totalIqTest : any;

  tsstUser: any;
  userUid: any;
  listUser : any;
  userId : any;
  userType : any;
  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  constructor(
    private tccoStdService: TccoStdService,
    private iqTestService: IqTestService,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,
    private route : ActivatedRoute,
    private clipboard: Clipboard,
    private commonService: CommonService,
  ){ }

  ngOnInit(): void {
    this.commonService.getUserInfo().subscribe(
      (response) =>{
        this.userInfo = response;
        let listRole = response.roles.map((element:any) => {return element.roleId});
        this.getRole(listRole);
        this.userUid = this.userInfo.userUid;
        this.userType = this.userInfo.userType;
        this.pageRequest.userUid = this.userUid;
      }
  );
    this.getQuesCategory();
    this.getQuesTypes();
    this.initData();
    this.onSearch();

  }
  getRole(listRole:any){
    // for(let i = 0; i < listRole.length; i++){
      if(listRole.includes('R000')){
        this.disable = false
      }
    // }
  }

  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.onSearch();

  }
  onSearch(){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.iqTestService.search(this.pageRequest).subscribe((response) => {
      this.listIqTest = response.list;
      this.totalIqTest = response.totalItems;
      CommonUtil.addIndexForListReverse(this.listIqTest, this.pageRequest.page, this.totalIqTest);
      this.setCommNm();
      for(let j = 0; j < this.listIqTest.length; j++){
        this.listUser = this.listIqTest[j].listUser;
        // if(this.userType != "TEA"){
          this.listIqTest[j].tested = false;
          for(let i = 0; i < this.listUser.length;i++){
            this.tsstUser = this.listUser[i].tsstUserModel;
            if(this.tsstUser != null){
              this.userId = this.tsstUser.userUid;
              if(this.userId == this.userUid){
                this.listIqTest[j].tested = true;
              }
            }
          }
        // }
      }
      dialogSpinner.close();
    });
  }
  delete(ques: any, event: Event) {

    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.iqTestService.delete(ques.key).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              this.toastr.success(
                this.trans.instant('message.success.deleteSuccess')
              );
              this.onSearch();
            } else {
              this.toastr.error(
                this.trans.instant('message.error.deleteFailed')
              );
            }
          },
          error: () => {
            this.toastr.error(
              this.trans.instant('message.error.deleteFailed')
            );
          },
        });
      },
      reject: () => {},
    });
  }

  showAddForm() {
    this.router.navigate(['/ques/ques0102/iqTestForm'], {
      queryParams: {
        iqTestId : '',
      },
    });
  }

  showUpdateForm(id: any) {
    this.router.navigate(['/ques/ques0102/iqTestForm'], {
      queryParams: {
        iqTestId: id,
      },
    });

  }

  showViewPublic(id: any) {
    // this.router.navigate(['/iqTestViewPublic'], {
    //   queryParams: {
    //     id: id,
    //   },
    // });
    let origin = window.location.origin;
    let linkIQTest = origin + '/#/iqTestViewPublic?id=' + id;
    this.clipboard.copy(linkIQTest);
    alert('Copy link ' + linkIQTest + ' success!')
  }

  showView(id: any) {
    this.router.navigate(['ques/ques0102/iqTestView'], {
      queryParams: {
        iqTestId: id,
      },
    });

  }

  getLink(event:any){

  }
  showStatistical(id: any) {
    this.router.navigate(['ques/ques0102/iqtestStatistic'], {
      queryParams: {
        iqTestId: id,
      },
    });
  }

  onSearchReset(){
  //  this.pageRequest = new IqQues();
    this.initData();
    this.onSearch();
  }

  initData() {
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
  }
  setCommNm(){
    for(let i = 0; i < this.listIqTest.length; i++){
      this.quesCategory = this.listIqTest[i].quesCategory;
      this.quesType = this.listIqTest[i].quesType;
      for(let j = 0; j < this.quesCategorys.length; j++){
        if(this.quesCategorys[j].commCd == this.quesCategory){
          const categoryNm = this.quesCategorys[j].commNm;
          this.listIqTest[i].quesCategory = categoryNm;
        }
      }
      for(let g = 0; g < this.quesTypes.length; g++){
        if(this.quesTypes[g].commCd == this.quesType){
          this.listIqTest[i].quesType = this.quesTypes[g].commNm;
        }
      }
    }
  }
  getQuesCategory() {
    this.tccoStdService.getCommCdByUpCommCd(CommonConstant.IQ_QUES_CATEGORY_COMM_CD).subscribe((response) => {
        this.quesCategorys = response;
      });
  }

  getQuesTypes() {
    this.tccoStdService.getCommCdByUpCommCd(CommonConstant.IQ_QUES_TYPE_COMM_CD).subscribe((response) => {
        this.quesTypes = response;
      });
  }

}
