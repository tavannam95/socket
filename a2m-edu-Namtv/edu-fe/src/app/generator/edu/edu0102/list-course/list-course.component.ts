import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationService } from 'primeng/api';
import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonUtil } from 'src/app/utils/common-util';
import { I18nConfig } from 'src/app/config/i18n.config';
import { I18nService } from 'src/app/services/i18n.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/common/authentication.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import { CommonService } from 'src/app/services/common/common.service';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';


@Component({
  selector: 'app-list-course',
  templateUrl: './list-course.component.html',
  styleUrls: ['./list-course.component.css']
})
export class ListCourseComponent implements OnInit {

  constructor(
    private edu0102Service: Edu0102Service,
    private toastr: ToastrService,
    private i18nService: I18nService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,
    public commonService: CommonService,
  ) { this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });}

  request: any = {};
  listUser!: any[];
  tsstUser!: any;
  pagingRequest: any = {};
  totalTsstUser!: any;
  language: any;
  position: any;
  permission: any;

  course!:any;
  listCourse :any[] = [];
  totalCourse!:any;
  checkView : any = true;

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  ngOnInit(){
    this.course = {};
    this.listCourse = [];
    this.initData();
    this.searchData();
    // this.commonService.changeCourse(this.course.key);
  }

  // initCheckView(){
  //   if(this.commonService.permModify(this.subject.insUid, true)|| this.commonService.permModify(this.assistUId, true) || this.commonService.permModify(this.teacherUid, true)){
  //     this.checkView = false;
  //   }else{
  //     this.checkView = true;
  //   }
  // }

  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;
  }


  getPaging(event: any) {
    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.searchData();
  }

  searchData() {
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.edu0102Service.search(this.pagingRequest).subscribe((response) => {
      this.listCourse = response.list;
      this.totalCourse = response.totalItems;
      CommonUtil.addIndexForListReverse(this.listCourse, this.pagingRequest.page, this.totalCourse);
      dialogSpinner.close();
    });
  }

  onSearchReset(){
    this.pagingRequest = {};
    this.initData();
    this.searchData();
  }

  showAddForm() {
    this.router.navigate(['/course/course0102'], {
      queryParams: {
        courseId : '',
      },

    });
  }

  showUpdateForm(courseId: any, event :any ) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I')
    this.router.navigate(['/course/course0102'], {
      queryParams: { courseId: courseId },
    });
    // this.commonService.changeCourse(courseId);
  }

  delete(course: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu0102Service.delete(course.key).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              this.toastr.success(
                this.trans.instant('message.success.deleteSuccess')
              );
              this.searchData();
            } else {
              this.toastr.error(
                this.trans.instant('message.error.deleteFailed')
              );
            }
          },
          error: () => {
            this.toastr.error(
              this.trans.instant('message.error.deleteFailed2')
            );
          },
        });
      },
      reject: () => {},
    });
  }

}

