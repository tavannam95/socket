import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { searchModel } from 'src/app/model/search-model';
import { Course0101Service } from 'src/app/services/course/course0101.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { LectureFormComponent } from '../component/lecture-form/lecture-form.component';



@Component({
  selector: 'app-list-lecture',
  templateUrl: './list-lecture.component.html',
  styleUrls: ['./list-lecture.component.css']
})
export class ListLectureComponent implements OnInit {

  pageRequest = new searchModel();
  listLecture: any[] = [];
  totalLecture = 0;
  chapterId : any;
  subjectId : any;
  courseId : any;

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  constructor(
    private course0101Service: Course0101Service,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private route : ActivatedRoute,
    private router: Router,
  ){ }

  ngOnInit(): void {

    this.init()
  }

  init(){
    this.route.queryParams.subscribe(async (params) => {
      this.chapterId = params['chapterId'];
      this.subjectId = Number(params['subjectId']);
      this.courseId = Number(params['courseId']);
    });
    this.initData();
    this.onSearch();
  }

  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.onSearch();
  }

  delete(subject: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.course0101Service.delete(subject.key).subscribe({
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

  navigateToUpdate(lectureId: any) {
    //this.router.navigateByUrl('/course/course0101/lecture/'+id);
    if(this.chapterId){
      this.router.navigate(['/course/course0101/lecture/'], {
        queryParams: { lectureId : lectureId, chapterId : this.chapterId, subjectId : this.subjectId, courseId : this.courseId },
      });
    }

  }

  navigateToCreate() {
    //this.router.navigateByUrl('/course/course0101/lecture');
    if(this.chapterId){
      this.router.navigate(['/course/course0101/lecture/'], {
        queryParams: { lectureId : '', chapterId : this.chapterId, subjectId : this.subjectId, courseId : this.courseId },
      });
    }else {
      alert("Create A Chapter !");
    }
  }

  showUpdateForm(lecture: any, event: any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I')
    this.navigateToUpdate(lecture.key);
    // const dialogRef = this.dialog.open(LectureFormComponent, {
    //   width: '0px',
    //   height: '0px',
    //   data: {lecture: lecture},
    //   panelClass: 'custom-modalbox',
    // });

    // dialogRef.afterClosed().subscribe((response: any) => {
    //   if (response) {
    //     this.onSearch();
    //   }
    // });
  }

  showAddForm() {
    this.navigateToCreate();
    // const dialogRef = this.dialog.open(LectureFormComponent, {
    //   width: '0px',
    //   height: '0px',
    //   data: { lecture: {}},
    //   panelClass: 'custom-modalbox',
    // });

    // dialogRef.afterClosed().subscribe((response: any) => {
    //   if (response){
    //     this.onSearch();
    //   }
    // });
  }

  onSearch(){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.pageRequest.key = this.chapterId;
    this.pageRequest.getAll = true;
    this.course0101Service.search(this.pageRequest).subscribe((response) => {
      this.listLecture = response[CommonConstant.LIST_KEY];
      this.totalLecture = response[CommonConstant.COUNT_ITEMS_KEY];
      CommonUtil.addIndexForList(this.listLecture);
      dialogSpinner.close();
    });
  }

  onSearchReset(){
    this.pageRequest = new searchModel();
    this.initData();
    this.onSearch();
  }

  initData() {
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
  }
}
