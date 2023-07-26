import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { ListCloneSubjectComponent } from '../list-clone-subject/list-clone-subject.component';

@Component({
  selector: 'app-subject-mgt',
  templateUrl: './subject-mgt.component.html',
  styleUrls: ['./subject-mgt.component.css']
})
export class SubjectMgtComponent implements OnInit {

  @Output() handleCallRefresh = new EventEmitter

  pageRequest = new searchModel();
  listSubject: any[] = [];
  totalSubject = 0;
  courseId : any;
  courseObj: any = new Object();
  couSubjectLst: any[] = [];
  chooseSubject : any =[];
  approvalStatusTypes: any[] = [];
  minSbj : any;
  maxSbj : any;
  subject : any;
  listChecked:  any [] = [];

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  APPROVAL_STATUS_APPROVAL_COMM_CD = CommonConstant.APPROVAL_STATUS_APPROVAL_COMM_CD;
  APPROVAL_STATUS_REJECT_COMM_CD = CommonConstant.APPROVAL_STATUS_REJECT_COMM_CD;
  APPROVAL_STATUS_PENDING_COMM_CD = CommonConstant.APPROVAL_STATUS_PENDING_COMM_CD;
  DOCUMENT_STATUS_DRAFT = CommonConstant.DOCUMENT_STATUS_DRAFT;


  constructor(
    private course0102Service: Course0102Service,
    private edu0102Service: Edu0102Service,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,
    private route : ActivatedRoute,
    private commonService : CommonService,
    private tccoStdService: TccoStdService,
  ){ }

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.getStatusType();
    this.chooseSubject = [];
    this.initData();
    this.searchData();
  }

  getStatusType() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.APPROVAL_STATUS_COMM_CD)
      .subscribe((response) => {
        this.approvalStatusTypes = response;
        this.commonService.selectLangComcode(this.approvalStatusTypes);
      });
  }

  getLabelApproval(cdStatus : String){
    const cd = this.approvalStatusTypes.find( e =>  e.commCd == cdStatus )
    //
    if(cd) return  cd.label;
    return "";
  }

  initData() {
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
    this.pageRequest.searchStatus = "1";
  }

  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.searchData();
  }

  searchData(){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.course0102Service.search(this.pageRequest).subscribe((response) => {
      this.listSubject = response.list;
      this.totalSubject = response.totalItems;
      CommonUtil.addIndexForList(this.listSubject, this.pageRequest.page, this.totalSubject);
      dialogSpinner.close();
    });
  }

  delete(subject: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.course0102Service.setTrueDeleteFlag(subject.key).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              // this.commonService.changeCourse(this.courseId);
              this.toastr.success(
                this.trans.instant('message.success.deleteSuccess')
              );
              this.searchData();
              this.handleCallRefresh.emit();
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
    this.commonService.changeAprovalHistoryInfo(
      {}
    )

    this.router.navigate(['/course/course0105/form'], {
      queryParams: { subjectId : ''},
    });
  }

  showUpdateForm(subjectId: any, event:any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I')
    this.router.navigate(['/course/course0105/form'], {
      queryParams: { subjectId: subjectId, courseId: this.courseId},
    });
  }

  preClone(){
    let checkListId: any=[];
    this.listChecked.forEach( (element: any) => {
      checkListId.push(element.key);
    });

    return {
      listSubjectId: checkListId,
    }
  }

  cloneSubjectBtn() {
      // const dialogRef = this.dialog.open(ListCloneSubjectComponent, {
      //   width: '0px',
      //   height: '0px',
      //   data: { listChooseSubject : this.couSubjectLst },
      //   panelClass: 'custom-modalbox',
      // });

      // dialogRef.afterClosed().subscribe((response: any[]) => {
      //   if(response){
      //     this.searchData();
      //   }

      // });
      var request = this.preClone();

      this.course0102Service.saveCSM(request).subscribe({
        next: (response) => {
          this.toastr.success('Success', ' Added !');
          this.searchData();
        },
        error: () => {
          this.toastr.error('Failed', 'Add  Failed !');
        },
      });
  }

  onSearchReset(){
    this.pageRequest = new searchModel();
    this.initData();
    this.searchData();
  }


  handleMultiSelect(event : any){
    let isChecked =  event.currentTarget.checked;
    if(isChecked){
      let tempList =  this.listSubject.filter(item =>  !this.listChecked.includes(item))
      this.listChecked.push(...tempList);
      this.changeFieldList(this.listChecked,true);
      // console.log(this.listChecked);
    }else{
      this.changeFieldList(this.listChecked,false);
      this.listChecked =  this.listChecked.filter(item => !this.listSubject.includes(item));
      this.changeFieldList(this.listChecked,true);
    }
  }

  addFieldList(list:any){
    list.forEach((element:any) => {
      element.checked = false;
    });
  }

  changeFieldList(list:any,checked:boolean){
    list.forEach((element:any) => {
      element.checked = checked;
    });
  }

  checkSubject(subjectModel : any){
    return subjectModel.checked?true: false ;
    }

  handleListCheked(subjectModel : any){
    if(this.listChecked.includes(subjectModel)){
      var index = this.listChecked.indexOf(subjectModel);
      if (index > -1) {
        subjectModel.checked = false;
        this.listChecked.splice(index, 1);
      }
    }else{
      subjectModel.checked = true ;
      this.listChecked.push(subjectModel);
    }
  }


}
