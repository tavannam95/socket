import { state } from '@angular/animations';
import { AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { CourseFormComponent } from 'src/app/generator/edu/edu0102/component/course-form/course-form.component';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { ListSubjectComponent } from '../list-subject/list-subject.component';
import { CourseInfoComponent } from './course-info/course-info.component';

@Component({
  selector: 'app-tabSubject',
  templateUrl: './tabSubject.component.html',
  styleUrls: ['./tabSubject.component.css']
})
export class TabSubjectComponent implements OnInit, AfterViewInit {

  @ViewChild('courseForm')
  courseForm!: CourseFormComponent
  @ViewChild('courseInfo')
  courseInfo!: CourseInfoComponent
  @ViewChild('subjectList')
  subjectList!: ListSubjectComponent
  @ViewChild('confirmDialog')
  confirmDialog!: any ;
  @ViewChild('courseInfoDialog')
  courseInfoDialog!: any ;

  courseId: any;
  subjectId:any;
  tab:any = 1;
  couSubjectLst: any[] = [];
  courseObj: any = new Object();
  col:string = 'col-lg-12';
  isSavedCourse : Boolean = false;
  constructor(
    private route : ActivatedRoute,
    private edu0102Service: Edu0102Service,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    private router: Router,
    public dialog: MatDialog,
  ) {
    // const s = this.router.getCurrentNavigation().extras.state;
  }

   ngOnInit()  {
    // this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.init();
  }

  init(){
    this.route.queryParams.subscribe( (params) => {
      this.courseId = params['courseId'];
      this.tab = params['tab'];
      ;
      if(this.courseId){
        if(this.tab){
          this.changeTab(this.tab);
        }
          this.getData();
      }
    });
  }

  ngAfterViewInit(){
    this.route.queryParams.subscribe( (params) => {
      const tab = params['tab'];
      if(tab){
        this.changeTab(tab);
      }
    });
  }

   getData(){
    let requestModel = new searchModel();
    requestModel.key = this.courseId;
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.edu0102Service.getCourseByCondition(requestModel).subscribe( (response:any) => {
    this.courseObj =  response[CommonConstant.DETAIL_KEY];
    if(this.courseObj.subjectModels.length>0){
      this.couSubjectLst = this.courseObj.subjectModels.reverse();
    }
    dialogSpinner.close();
    });
  }

  changeTab(tabNumber: any){
    if(this.courseId==""){
      let mes = this.trans.instant('course.info.init.required')
      this.confirmationService.confirm({
        header: mes,
        key:'tabSubject',
        acceptLabel: this.trans.instant('button.confirm.title'),
        accept: () => {
          // tabNumber=1;
        },

      });
      tabNumber=1;
    }


    const ele = document.getElementById('tabSubject'+tabNumber);


    ele?.click();
      let doan = "";
  }

  afterSave(event : any){
    this.courseId = event.key ;
    let tab = event.tab ;
    this.isSavedCourse = true;

    if(tab =="1"){
      this.confirmationService.confirm({
        header: this.trans.instant('message.success.saveSuccess'),
        key : "tabSubject" ,
        message: this.trans.instant('message.success.choose'),
        accept: () => {
        },
        reject: () => {
        this.redirect("1");
        },
      });
    } else if(tab =="2"){
      this.confirmationService.confirm({
        header: this.trans.instant('message.success.saveSuccess'),
        key : "courseInfo" ,
        message: this.trans.instant('message.success.choose'),
        accept: () => {
          this.redirect("3");
        },
        reject: () => {
        this.redirect("2");
        },
      });

    }

  }

  redirectByType(type : String){
    let tab : String = '1' ;
    this.confirmDialog.accept();
    switch(type) {

      case 'ListCourse':
        this.goListCourse();
        // code block
        break;
      case 'CourseForm':
        tab='1';
        this.redirect(tab);
        break;
      case 'CourseInfo':
        tab='2';
        this.redirect(tab);
        break;
      case 'ListSubject':
        this.courseInfoDialog.accept();
        tab='3';
        this.redirect(tab);
        break;
      default:
        this.redirect(tab);
        break;
    }
  }


  redirect(tab : any){
    this.router.navigate(['/course/course0102'], {
      queryParams: {
        courseId: this.courseId,
        tab : tab
      },
    });
    //debugger
    this.subjectList.init();
    this.init();
  }

  goListCourse(){
    this.router.navigateByUrl('edu/edu0102');
  }

}
