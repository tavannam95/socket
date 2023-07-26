import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Inject, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { Subject } from 'src/app/model/course/subject';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { LangUtil } from 'src/app/utils/lang.util';
import { environment } from 'src/environments/environment';
import { AuthConstant } from 'src/app/constants/auth.constant';
import { Cookie } from 'ng2-cookies';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { searchModel } from 'src/app/model/search-model';
import { CommonConstant } from 'src/app/constants/common.constant';
import { ActivatedRoute, Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { AuthenticationService } from 'src/app/services/common/authentication.service';
import { CommonService } from 'src/app/services/common/common.service';
import { Thickness } from '@syncfusion/ej2-angular-diagrams';
import { SubMenuService } from 'src/app/services/sub-menu/sub-menu.service';
import { ConfirmationService } from 'primeng/api';
import { StandardService } from 'src/app/services/course/standard.service';
import { Standard } from 'src/app/model/course/Standard';
import { NgForm } from '@angular/forms';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
@Component({
  selector: 'app-skill-form',
  templateUrl: './skill-form.component.html',
  styleUrls: ['./skill-form.component.css']
})
export class SkillFormComponent implements OnInit {

  @Output()
  saveSkill: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  @ViewChild('myForm')
  myForm!: NgForm;

  disable = false;
  submitted: any = false;
  pageRequest : any = new searchModel();
  standType : any = '18-02';
  standId : any;
  standard : any = {};
  listStandard : any[] = [];
  listStandards : any[] = [];
  listStandardHis : any[] = [];
  listStandardDel: any[] = [];
  stand : any;
  subjectId: any;
  configCkeditor: any = {};
  currentCourseId : any ;
  courseNm: any = "";
  courseId : any ;
  typeView : any;
  checkView : any = true;
  subject: any = {};
  assistUId: any;
  teacherUid: any;
  courseUid : any;
  constructor(
    private course0102Service: Course0102Service,
    private standardService: StandardService,
    private toastrService: ToastrService,
    public dialogRef: MatDialogRef<SkillFormComponent>,
    private trans: TranslateService,
    public datepipe: DatePipe,
    private route : ActivatedRoute,
    private router: Router,
    private commonService: CommonService,
    private confirmationService: ConfirmationService,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  async ngOnInit(): Promise<void> {
    this.route.queryParams.subscribe(async (params) => {
      this.subjectId = params['subjectId'];
    });
    if (this.subjectId) {
      await this.getSubjectById(this.subjectId);
      this.onSearchHistory();
    }
  }

  checkViewPerm(){
    if(this.commonService.permModify(this.subject.insUid, true)|| this.commonService.permModify(this.assistUId, true) || this.commonService.permModify(this.teacherUid, true)){
      this.checkView = false;
    }else{
      this.checkView = true;
    }
  }

  async getSubjectById(param: any) {
    await this.course0102Service.getSubjectById(param).toPromise().then((response) => {
      this.subject = response[CommonConstant.DETAIL_KEY];
      this.assistUId = this.subject.tsstUser.userUid;
      this.teacherUid = this.subject.teacherModel.userUid;
      this.courseUid = this.subject.courseUid;
    });
  }

  onSearchMain(param:any){
    this.checkView = true ;
    this.listStandard = [];
    this.listStandardDel = [];
    this.standardService.getStandBySubjectId(param, this.standType).subscribe((response) => {
      this.listStandard = response[CommonConstant.DETAIL_KEY];

      this.genIndex();
      // this.checkViewPerm();
      this.handleListStandBlank();
    });
  }

  async onSearchHistory(){
    await this.standardService.getStandardHistoryBySubjectId(this.subjectId, this.standType).toPromise().then((response) => {
      this.listStandard = response[CommonConstant.DETAIL_KEY];
      this.genIndex();
      this.checkViewPerm();
      this.handleListStandBlank();
    });
  }

  genIndex(){
    for(let i = 0; i < this.listStandard.length; i++){
      this.listStandard[i]['index'] = i + 1;
      this.stand = this.listStandard[i];
      // this.checkViewPerm();
    }
  }

  handleListStandBlank(){
    if (this.listStandard.length == 0) {
      this.checkView = true;
      this.typeView = 'ADD';
      this.standard = new Standard();
      this.listStandard.push(this.standard);
    }else{
      this.typeView = 'VIEW';
    }
  }

  addNewStand(index: any){
    const stand = new Standard();
    this.listStandard.push(stand);
    this.genIndexStand();
  }

  removeStand(index: any){
    const standDel = this.listStandard[index];
    standDel.deleteFlag = true;
    this.listStandardDel.push(standDel) ;
    this.listStandard.splice(index, 1);
    this.genIndexStand();
  }

  delStand(){
    for(let i = 0; i < this.listStandardDel.length; i++){
      const standDel = this.listStandardDel[i];
      this.listStandard.push(standDel);
    }
  }
  genIndexStand(){
    let index = 0;
    this.listStandard.forEach((stand: any)=>{
      index++;
      stand.index = index;
    })
  }

  onSave(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    } else{
      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
      for(let i = 0; i < this.listStandard.length; i++){
        this.listStandard[i].subjectId = this.subjectId;
        this.listStandard[i].status = true;
        this.listStandard[i].standType = this.standType;
        this.listStandard[i].standCode = 'CLO';
      }
      this.delStand();
      this.standardService.save(this.listStandard).subscribe({
        next: (response) => {
          this.onSearchHistory();
          this.getListStandard();
          this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.typeView = 'VIEW';
          dialogSpinner.close();

        },
        error: () => {
            this.toastrService.error(this.trans.instant('message.error.saveFailed'));
            dialogSpinner.close();

        },
      });
    }
  }

  getListStandardHis(){
  }

  getListStandard(){
    this.standardService.getListStandBySubjectId(this.subjectId).subscribe((response) => {
      this.listStandards = response[CommonConstant.DETAIL_KEY];
      this.standardService.getListStandHistoryBySubjectId(this.subjectId).subscribe((response) => {
        this.listStandardHis = response[CommonConstant.DETAIL_KEY];
        let param : any = [];
        param.listStandard = this.listStandards;
        param.listStandardHis = this.listStandardHis;
        this.saveSkill.emit(param);
      });
    });
  }

  onEditorChange(event: any) {
    // document.getElementsByClassName('editorPreview')[0].innerHTML = event.editor.getData();
  }

  async showUpdateStandForm(){
    // this.listStandard = [];
    // this.listStandardDel = [];
    //await this.getStandardHistoryBySubjectId();
    this.typeView = 'MODIF';
  }

  // async getStandardHistoryBySubjectId(){
  //   await this.standardService.getStandardHistoryBySubjectId(this.subjectId, this.standType).toPromise().then((response) => {
  //     this.listStandard = response[CommonConstant.DETAIL_KEY];
  //     this.genIndex();
  //     this.handleListStandBlank();

  //   });
  // }

  checkInvalidForm(){
    const invalid = this.myForm.invalid;
    return invalid;
  }

}
