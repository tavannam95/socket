import { group } from '@angular/animations';
import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { count } from 'rxjs';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { Note } from 'src/app/model/course/note';
import { Standard } from 'src/app/model/course/Standard';
import { CommonService } from 'src/app/services/common/common.service';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { StandardService } from 'src/app/services/course/standard.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';

@Component({
  selector: 'app-standard-summary-table',
  templateUrl: './standard-summary-table.component.html',
  styleUrls: ['./standard-summary-table.component.css']
})
export class StandardSummaryTableComponent implements OnInit {
  @Input('listStandard') listStandard : any[] = []
  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();
  standresults: any;
  pdfFilePath : any = {};
  disable = false;
  submitted: any = false;
  subjectId : any;
  // listStandard : any[] = [];
  listStandardNote : any[] = [];
  listStand : any[] = [];
  listChapterSchedule : any [] = [];
  listNote : any[] = [];
  listStandNm : any[] = [];
  listKnow : any[] = [];
  listKnowNote : any[] = [];
  listKnowLength = 0;
  listSkill : any[] = [];
  listSkillNote : any[] = [];
  listSkillLength = 0;
  listQua : any[] = [];
  listQuaNote : any[] = [];
  listQuaLength = 0;
  listStandNote : any = {};
  widthFull = 100
  widthStand: any;
  widthNo = 5;
  widthContent = 15;
  typeView : any;
  checkView : any = true;
  subject: any = {};
  assistUId: any;
  teacherUid: any;
  courseUid : any;
  viewMode : any = "";// change  View option approval - history

  constructor(
    private standardService: StandardService,
    private course0102Service: Course0102Service,
    private tccoStdService: TccoStdService,
    private commonService: CommonService,
    private route : ActivatedRoute,
    private toastrService: ToastrService,
    public dialogRef: MatDialogRef<StandardSummaryTableComponent>,
    private trans: TranslateService,
    public datepipe: DatePipe,
    private fileUploadService :FileUploadService,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(async (params) => {
      this.subjectId = params['subjectId'];
    });
    if(this.subjectId){
      this.getSubjectById(this.subjectId)
      this.initData();
      this.getStandNote();
    }
  }

  checkViewPerm(){
    if(this.commonService.permModify(this.subject.insUid, true)|| this.commonService.permModify(this.assistUId, true) || this.commonService.permModify(this.teacherUid, true)){
      this.checkView = false;
    }else{
      this.checkView = true;
    }
  }
  getSubjectById(param: any) {
    this.course0102Service.getSubjectById(param).subscribe((response) => {
      this.subject = response[CommonConstant.DETAIL_KEY];
      this.assistUId = this.subject.tsstUser.userUid;
      this.teacherUid = this.subject.teacherModel.userUid;
      this.courseUid = this.subject.courseUid;
      this.checkViewPerm();
    });
  }

  initData(){
    this.listKnow = [];
    this.listKnowLength = 0;
    this.listQua = [];
    this.listQuaLength = 0;
    this.listSkill = [];
    this.listSkillLength = 0;
    if(this.viewMode == "PUBLIC"){
      this.getListStandBySubjectId();
      this.getLectureScheduleBySubjectId();
    }else{
      this.getListStandHistoryBySubjectId();
      this.getLectureScheduleHistoryBySubjectId();
    }
  }

  refreshCss(){
    this.listKnow = [];
    this.listQua = [];
    this.listSkill= [];
  }

  getListStandBySubjectId(){
    this.refreshCss();
    // this.standardService.getListStandBySubjectId(this.subjectId).subscribe((response) => {
    //   this.listStandard = response[CommonConstant.DETAIL_KEY];
      this.refreshCss();
      for(let i = 0; i < this.listStandard.length; i++){
        this.listStandard[i]['index'] = i + 1;
        const standType = this.listStandard[i].standType;
        if(standType == '18-01'){
          this.listKnow.push(this.listStandard[i]);
          this.listKnowLength = this.listKnow.length;
        }
        if(standType == '18-02'){
          this.listSkill.push(this.listStandard[i]);
          this.listSkillLength = this.listSkill.length;
        }
        if(standType == '18-03'){
          this.listQua.push(this.listStandard[i]);
          this.listQuaLength = this.listQua.length;
        }
      }
      this.widthStand = (this.widthFull-this.widthNo-this.widthContent)/this.listStandard.length;
    // });
  }

  getListStandHistoryBySubjectId(){
    this.refreshCss();
    this.standardService.getListStandHistoryBySubjectId(this.subjectId).subscribe((response) => {
      this.listStandard = response[CommonConstant.DETAIL_KEY];
      this.refreshCss();
      for(let i = 0; i < this.listStandard.length; i++){
        this.listStandard[i]['index'] = i + 1;
        const standType = this.listStandard[i].standType;
        if(standType == '18-01'){
          this.listKnow.push(this.listStandard[i]);
          this.listKnowLength = this.listKnow.length;
        }
        if(standType == '18-02'){
          this.listSkill.push(this.listStandard[i]);
          this.listSkillLength = this.listSkill.length;
        }
        if(standType == '18-03'){
          this.listQua.push(this.listStandard[i]);
          this.listQuaLength = this.listQua.length;
        }
      }
      this.widthStand = (this.widthFull-this.widthNo-this.widthContent)/this.listStandard.length;
    });
  }

  getLectureScheduleHistoryBySubjectId(){
    this.standardService.getLectureScheduleHistoryBySubjectId(this.subjectId).subscribe((response) => {
      this.listChapterSchedule = response[CommonConstant.LIST_KEY];
      // console.log(this.listChapterSchedule);
      for(let i = 0; i < this.listChapterSchedule.length; i++){
        this.listChapterSchedule[i].listStandNote = this.listChapterSchedule[i].listStandNoteHis;
        this.checkViewPerm();
      }
      this.typeView = 'VIEW';
      if(this.listChapterSchedule.length == 0){
        this.disable = true;
      }else{
        this.disable = false;
      }
    });
  }

  getLectureScheduleBySubjectId(){
    this.standardService.getLectureScheduleBySubjectId(this.subjectId).subscribe((response) => {
      this.listChapterSchedule = response[CommonConstant.LIST_KEY];
      for(let i = 0; i < this.listChapterSchedule.length; i++){
        this.listChapterSchedule[i].listStandNote = this.listChapterSchedule[i].listStandNote;
        this.checkView = true ;
        // this.checkViewPerm();
      }
      this.typeView = 'VIEW';
      if(this.listChapterSchedule.length == 0){
        this.disable = true;
      }else{
        this.disable = false;
      }
    });
  }

  onSave(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    } else{
      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
      this.standardService.saveNoteHistory(this.listChapterSchedule).subscribe({
        next: (response) => {
          this.initData();
          // this.commonService.changeCourse(this.courseId);
          // if(!this.listChapterSchedule[0].listStandNote[0].key){
            this.save.emit(response);
            this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
            this.typeView = 'VIEW';
            dialogSpinner.close();
          // }else{
          //   this.save.emit(response);
          //   this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
          // }
        },
        error: () => {
          // if(!this.listChapterSchedule[0].listStandNote[0].key){
            this.toastrService.error(this.trans.instant('message.error.saveFailed'));
            dialogSpinner.close();
            // this.handleAfterSaveSuccess(this.trans.instant('message.error.saveFailed'));
          // }else{
          //   this.toastrService.error(this.trans.instant('message.success.updateFailed'));
          //   //this.handleAfterSaveSuccess(this.trans.instant('message.success.updateFailed'));
          // }

        },
      });
    }
  }

  exportPDF(subjectId : any){
    this.standardService.exportPDF(subjectId).toPromise().then((response)=>{
      const myArray = response.detail.split("!#@");
      this.pdfFilePath.fleNm = myArray[1];
      this.pdfFilePath.fleTp = '.pdf';
      this.pdfFilePath.flePath = myArray[0] + this.pdfFilePath.fleTp;
      this.fileUploadService.download(this.pdfFilePath, true);
    })
  }

  exportDoc(subjectId : any){
    this.course0102Service.exportDoc(subjectId).toPromise().then((response)=>{
      const myArray = response.detail.split("!#@");
      this.pdfFilePath.fleNm = myArray[1];
      this.pdfFilePath.fleTp = '.docx';
      this.pdfFilePath.flePath = myArray[0] + this.pdfFilePath.fleTp;
      this.fileUploadService.download(this.pdfFilePath, true);
    })
  }

  getStandNote() {
    this.tccoStdService.getCommCdByUpCommCd(CommonConstant.STAND_NOTE).subscribe((response) => {
        this.listNote = response;
        for(let i = 0; i < this.listNote.length; i++){
          this.listNote[i]['noteContent'] = this.listNote[i].description + ' (' + this.listNote[i].commNm +  ')';
        }
      });
  }

  getStand() {
    this.tccoStdService.getCommCdByUpCommCd(CommonConstant.STANDARD).subscribe((response) => {
        this.listStandNm = response;
      });
  }

  showUpdateSyntheticForm(){
    this.typeView = 'MODIF';
  }
}
