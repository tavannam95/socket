import { LazyLoadScriptService } from './../../services/lazy-load-script.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ApplyService } from 'src/app/services/apply.service';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { Dialog } from '@angular/cdk/dialog';
import { NotifyComponent } from '../notify/notify.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { SpinnerDialogComponent } from 'src/app/commons/spinner-dialog/spinner-dialog.component';
import { CommonService } from 'src/app/services/common.service';
import { ActivatedRoute } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { searchModel } from 'src/app/models/search-model';
import { CourseService } from 'src/app/services/course.service';

interface City {
  name: string,
  code: string
}

@Component({
  selector: 'app-apply',
  templateUrl: './apply.component.html',
  styleUrls: ['./apply.component.css']
})
export class ApplyComponent implements OnInit, AfterViewInit {

  //Star Valid Form
  emailPattern = PatternValidate.EMAIL_PATTERN;
  phonePattern = PatternValidate.PHONE_PATTERN;
  phonePatternBasic = PatternValidate.PHONE_PATTERN_BASIC;
  submitted: any = false;
  //End Valid Form

   candidate : any={};
   file! : any ;
   request : any={};

   eventCourse: any = {};
   course: any = {};
   courseId: any;
   courseNm: any;
   courseList:any[] = [];
  constructor(
    private loadScriptService: LazyLoadScriptService,
    private applyService: ApplyService,
    public dialog: MatDialog,
    private route : ActivatedRoute,
    private courseService : CourseService,
  ) {}
  ngAfterViewInit(): void {
    this.loadScriptService.loadScript();
    // this.getCourseList();
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(async (params) => {
      this.courseId = params['courseId'];
      // this.candidate.courseNm = params['courseNm'];
    });
    // this.getCourseById();
    // this.getCourseList();
  }

  // ====================================
  handleFile(event: any){
    ////debugger
    if (!event.target.files || !event.target.files[0]) {
      return;
  }
  this.file = event.target.files[0];
  }

  onSave(invalid: any,form : any){
    if (invalid) {
      return;
    }else{
      this.candidate.insDate = new Date();
      this.candidate.updDate = new Date();
      this.candidate.candidateprogressType = 'Chưa giải quyết';
      // this.request.file = this.file;
      this.candidate.courseModel = this.course;
      this.candidate.eventModel = this.eventCourse;
      this.request.candidate = this.candidate;
      let dialogSpinner = this.dialog.open(SpinnerDialogComponent, {panelClass: 'transparent',disableClose: true});
      // const dialogRef = this.commonService.getSpinnerDialog();
      this.applyService.save(this.request).subscribe(
        (response: any)=>{
          dialogSpinner.close();

          this.openDialog()
          form.resetForm();
        },(error: any) => {
          dialogSpinner.close();
        }
      )
    }
  }

  // getCourseList() {
  //   let param = new searchModel();
  //   param.getAll = true;
  //   this.courseService.search(param).subscribe((response) => {
  //     // //debugger
  //     this.courseList = response[CommonConstant.LIST_KEY];
  //   });
  // }

  getCourseById(){
    this.courseService.getCourseById(this.courseId).subscribe( (response:any) => {
    this.course =  response[CommonConstant.DETAIL_KEY];

    });
  }

  converPhoneToString(number :any){
    this.candidate.candidatePhone = number.toString();
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(NotifyComponent, {
      width: '0px',
      height: '0px',
      data: {
        messages: 'Success'
      },
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any) => {
    });
  }

  handleKeyupPhone(event: any){
    let keyStr = event.key;
    let isNumber = !Number.isNaN(Number(keyStr));

    if(!isNumber){
      if(keyStr!=null || keyStr!=undefined || keyStr!=''){
      this.candidate.candidatePhone = this.candidate.candidatePhone.replace(keyStr, "");
        this.candidate.candidatePhone = this.candidate.candidatePhone.replace(/\D/g,'');
      }
    }
  }



}
