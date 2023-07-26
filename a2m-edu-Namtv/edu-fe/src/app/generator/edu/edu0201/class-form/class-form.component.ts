import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Inject, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { searchModel } from 'src/app/model/search-model';
import { Class } from 'src/app/model/sys/class';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { Edu020101Service } from 'src/app/services/edu/edu020101.service';
import { Edu020102Service } from 'src/app/services/edu/edu020102.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { StudentFormComponent } from '../../edu0101/component/student-form/student-form.component';
import { ListStuComponent } from '../list-stu/list-stu.component';
import { ListTeaComponent } from '../list-tea/list-tea.component';
import { Location } from '@angular/common';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { ClassAnnouncementComponent } from '../class-announcement/class-announcement.component';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';

@Component({
  selector: 'app-class-form',
  templateUrl: './class-form.component.html',
  styleUrls: ['./class-form.component.css']
})
export class ClassFormComponent implements OnInit {
  public get data(): any {
    return this._data;
  }
  public set data(value: any) {
    this._data = value;
  }

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  disable = false;
  showStatistical = false;
  emailPattern = PatternValidate.EMAIL_PATTERN;
  phonePattern = PatternValidate.PHONE_PATTERN;
  phonePatternBasic = PatternValidate.PHONE_PATTERN_BASIC;
  submitted: any = false;

  arrRoles : any[] = [];
  class : any ={}
  classId : any ;
  checkList : any =[];
  pagingRequest: any = {};
  tsstUser: any = {};
  checkTeacherList :  any =[];
  courseList: any[] = [];
  classTypeList: any[] = [];
  courseNm : any;
  isEdit:Boolean = false;
  isSave:Boolean = false;

  typeView : any;
  checkView : any = true;

  // private readonly canGoBack: boolean;

  constructor(
    public dialog: MatDialog,
    private toastrService: ToastrService,
    public dialogRef: MatDialogRef<StudentFormComponent>,
    public datepipe: DatePipe,
    @Inject(MAT_DIALOG_DATA)
    private _data: any,
    private edu0101Service : Edu0101Service,
    private edu0201Service : Edu0201Service,
    private edu020101Service : Edu020101Service,
    private edu020102Service : Edu020102Service,
    private edu0102Service : Edu0102Service,
    private router: Router,
    private route : ActivatedRoute,
    private trans: TranslateService,
    private commonService : CommonService,
    private confirmationService: ConfirmationService,
    private readonly location: Location,
    private tccoStdService: TccoStdService,

  ) {

    // this.canGoBack = !!(this.router.getCurrentNavigation()?.previousNavigation);
  }

  ngOnInit(): void {
    let strRoles = AuthenticationUtil.getUserRole();
    this.arrRoles = strRoles.split("_");
    this.getClassType();
    this.getUserInfo();
    this.route.queryParams.subscribe(async (params) => {
      this.classId = params['id'];
    });

    this.checkList = [];
    this.checkTeacherList = [];

    if(this.classId){
      this.typeView = 'VIEW';
      this.initData(this.classId);

    } else {
      this.typeView = 'ADD';
      this.isSave = true ;
      this.class = new Class();
      this.class.courseModel = {};
      this.class.courseModel.courseNm = '';
      this.class.classStatus = true ;
      this.class.isFinish = false ;
      this.typeView ='ADD';
    }

    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;

    this.getCourseList();
    this.getRole();
  }

  initCheckView(){

    if(this.commonService.permModify(this.class.insUid, true)){
      this.checkView = true;
      this.isSave = true ;
    }else{
      this.checkView = false;
      this.isSave = false ;
    }
  }

  getCourseList(){
    let param = new searchModel();
    param.getAll = true;
    this.edu0102Service.search(param).subscribe((response) => {
        this.courseList = response[CommonConstant.LIST_KEY];
        for(let i = 0; i < this.courseList.length;i++){
          if(this.courseList[i].key == this.class.courseId){
            this.courseNm = this.courseList[i].courseNm;
          }
        }
      });
  }

  initData(param : any){

    this.isEdit =  !this.commonService.userHasRole(CommonConstant.ROLE_SYS_STUDENT )

    this.edu0201Service.getClassById(param).subscribe((response) => {
      this.class = response.list;
      this.handleDate();

      this.initCheckView();
    });

    this.edu020101Service.getStudentByClass(this.classId).subscribe((response) => {
      this.checkList = response.list;
      if(this.arrRoles.includes(CommonConstant.ROLE_SYS_STUDENT)){
        this.showStatistical = true;
      }
      this.getViewStatistical();
      this.handleIndex(this.checkList);
    });

    this.edu020102Service.getTeacherByClass(this.classId).subscribe((response) => {
      this.checkTeacherList = response.list;
      this.handleIndex(this.checkTeacherList);
    });
  }

  getViewStatistical(){
    for(let i = 0; i < this.checkList.length; i++){
      let student = this.checkList[i];
      if(this.arrRoles.includes(CommonConstant.ROLE_SYS_ADMIN)){
        this.checkList[i].showStatistical = false;
      }else{
        if(student.key != this.tsstUser.userInfoId){
          this.checkList[i].showStatistical = true;
        }else{
          this.checkList[i].showStatistical = false
        }
      }

    }
  }

  getUserInfo(){
    this.commonService.getUserInfo().subscribe((response) =>{
        this.tsstUser = response;
      });
  }

  handleIndex(param : any){
    CommonUtil.addIndexForList(
      param,
      // this.pagingRequest.page,
      // param.length
    );
  }


  initSave(){
    let checkListId: any=[];
    this.checkList.forEach( (element: any) => {
      checkListId.push(element.key);
    });
    this.class.listStudentId = checkListId;

    checkListId = [];
    this.checkTeacherList.forEach( (element: any) => {
      checkListId.push(element.key);
    });
    this.class.listTeacherId = checkListId;

  }


  showUpdateSubjectForm(){
    this.typeView = 'MODIF';
  }

  onSave(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    }
    if(this.class.startDate>this.class.endDate){
      this.toastrService.error('Failed', 'Start Date have to  less End Date !');
      return
    }

    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.initSave();
    this.edu0201Service
    .save(this.class).subscribe({
      next: (response) => {
        this.class = response[CommonConstant.DETAIL_KEY];
        this.handleDate();
        this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
        if(!this.classId){
          this.addClassAnnoucement();
        }
        this.typeView = 'VIEW';

        this.emitSave();

        dialogSpinner.close()
      },
      error: () => {
        this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        dialogSpinner.close()
      },
    });
  }

  emitSave(){
    let param : any = {};
    param.key = this.class.key;
    param.tab = '1';
    this.save.emit(param);
  }

  handleDate(){
    this.class.startDate = this.class.startDate? new Date(this.class.startDate): null;
    this.class.endDate = this.class.endDate? new Date(this.class.endDate): null;
  }

  gotoList(){
    this.router.navigate(['/edu/edu0201'], {
    });
  }

  addClassAnnoucement(){
    let classAnnouncement : any = {}
    classAnnouncement.title = CommonConstant.CLASS_ANNOUN_TITLE ;
    classAnnouncement.content = CommonConstant.CLASS_ANNOUN_CONTENT ;
    classAnnouncement.classModel = this.class ;
    const dialogRef = this.dialog.open(ClassAnnouncementComponent, {
      width: '0px',
      height: '0px',
      data: { classAnnouncement: classAnnouncement,
              title : this.trans.instant('edu.edu0201.announ.titleDefaul'),
              isClose : false ,
              typeView :"EDIT",
            },
      panelClass: 'custom-modalbox',
    });
      dialogRef.afterClosed().subscribe((response: any) => {
        this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
        // this.initData();
        // this.gotoList();
        this.emitSave();
    });
  }

  showStudentForm(){
    const dialogRef = this.dialog.open(ListStuComponent, {
      width: '0px',
      height: '0px',
      data: { listStudent : this.checkList },
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any[]) => {
        if(response){
          response.forEach( (element) => {
            this.checkList.unshift(element);
          });
          this.handleIndex(this.checkList);
        }
      });
  }

  showTeacherForm(){
    const dialogRef = this.dialog.open(ListTeaComponent, {
      width: '0px',
      height: '0px',
      data: { listTeacher : this.checkTeacherList },
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any[]) => {
        if(response){
          response.forEach( (element) => {
            this.checkTeacherList.unshift(element);
          });
          this.handleIndex(this.checkTeacherList);
        }
      });
  }

  removeClass(param : any){
    this.checkList = this.checkList.filter((element:any) => element.key !=param);
    this.handleIndex(this.checkList);
  }
  removeTeacher(param : any){
    this.checkTeacherList = this.checkTeacherList.filter((element:any) => element.key !=param);
    this.handleIndex(this.checkTeacherList);
  }

  gotoCourse(clss : any){
    this.commonService.changeIsScroll(true);
    this.router.navigate(["/course/course0201Preview"],{
      queryParams: {courseId: clss.courseId }
    })
  }

  goToStatisticalCourse(classModel : any, studentId:any){
    this.commonService.changeIsScroll(true);
    this.router.navigate(["/course/course0201StatisticalCourse"],{
      queryParams:
      {
        courseId: classModel.courseId,
        studentId: studentId,
      }
    })
  }

  changeTab(tabNumber: any){

    const ele = document.getElementById('tab0'+tabNumber);

    if(tabNumber==2 && !this.classId){
      alert('chưa tạo class')
    }

      ele?.click();
  }

  editClass(){
    this.typeView = 'MODIF';
    this.isEdit = false;
  }

  goBack(){

    // if (this.canGoBack) {
    //   // We can safely go back to the previous location as
    //   // we know it's within our app.
      this.location.back();
    // } else {
    //   // There's no previous navigation.
    //   // Here we decide where to go. For example, let's say the
    //   // upper level is the index page, so we go up one level.
    //   this.router.navigate(['..'], {relativeTo: this.route});
    // }
  }

  getRole(){

    if(!this.commonService.userHasRole(CommonConstant.ROLE_SYS_STUDENT )){
      this.isEdit = true;
    }
  }

  getClassType(){
        this.tccoStdService.getCommCdByUpCommCd(CommonConstant.CLASS_TYPE_COMM_CD).subscribe((response:any) => {
          this.classTypeList = response;
        this.commonService.selectLangComcode(this.classTypeList);
      });
  }

}
