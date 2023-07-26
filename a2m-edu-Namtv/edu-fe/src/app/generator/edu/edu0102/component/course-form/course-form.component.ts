import { map } from 'rxjs';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';

import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  Inject,
  ViewChild,
  ElementRef,
} from '@angular/core';

import { DatePipe, formatDate } from '@angular/common';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { Course } from 'src/app/model/sys/Course';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { AuthenticationService } from 'src/app/services/common/authentication.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { GetpdfService } from 'src/app/services/file/pdf.service';
import { CommonService } from 'src/app/services/common/common.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { CourseInfoService } from 'src/app/services/course/course-info.service';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.css']
})
export class CourseFormComponent implements OnInit {

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();


  disable = false;
  changePassword = false;
  tsstUser:any = {};
  courseId: any;
  submitted: any = false;

  course : any={};
  model: any = {};
  configCkeditor: any = {};

  listFile: any[] = [];
  url : any ='';
  img: any;
  imgPath: any = "data:image/gif;base64,R0lGODlhAQABAAAAACwAAAAAAQABAAA=";
  fileToUpload: any;
  typeView : any;
  checkView : any = false;
  @ViewChild('imgViewer')
  imgViewer!: ElementRef;
  public filePath: string = "";
  public fileType: string = "img";


  constructor(
    private edu0102Service: Edu0102Service,
    private toastrService: ToastrService,
    public dialogRef: MatDialogRef<CourseFormComponent>,
    public datepipe: DatePipe,
    private route : ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    public commonService: CommonService,
    private uploadService: FileUploadService,
    private pdfService: GetpdfService,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    private courseInfoServie : CourseInfoService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(async (params) => {
      this.courseId = params['courseId'];
    });
    if (this.courseId) {
      this.typeView = 'VIEW';
      this.initData(this.courseId);
    } else {
      this.typeView = 'ADD';
      this.course = new Course();
      this.course.status = false;
      this.course.displayStatus = false;
      this.course.isComingSoon = false;
      this.disable = true;
    }

    this.initConfigCk();
  }

  initData(param : any){
    this.edu0102Service.getCourseById(param).subscribe((response) => {
      this.course = response[CommonConstant.DETAIL_KEY];
      var startDate = this.course.courseStartDate? new Date(this.course.courseStartDate): null;
      var endDate = this.course.courseEndDate? new Date(this.course.courseEndDate): null;
      this.course.courseStartDate = startDate;
      this.course.courseEndDate = endDate;
      this.filePath = this.course.imgPath;
      this.course.imgNm = this.filePath;
      this.getFile();
    });
  }

  getFile() {
    if (this.fileType == "img") {
      this.pdfService.getPdf(this.filePath, this.fileType).subscribe((responseMessage) => {
        let file = new Blob([responseMessage], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);
        this.imgViewer.nativeElement.src = fileURL;
        //this.disable = true;
      })
    }
  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  private onFileBrowserFileClick(event: { data: { ckEditorFuncNum: any; fileUrl: any; }; }) {
    (window as { [key: string]: any })['CKEDITOR'].tools.callFunction(event.data.ckEditorFuncNum, event.data.fileUrl);
  }

  onEditorChange(event: any) {

  }

  saveAll(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    }
    if (this.fileToUpload) {
      (this.uploadService.singleFileUpload(this.fileToUpload)).subscribe(
       {
        next: (response) => {
          this.course.imgPath = response.flePath;
          this.onSave();

          //this.toastrService.success('Success', ' Uploaded !');
        },
        error: () => {
          //this.toastrService.error('Could not upload the file: ' + this.fileToUpload.name, 'Upload  Failed !');
        }
       });
    }else{
      this.onSave();
    }
  }

  saved(courseId: any, courseNm : any) {

    //debugger
    this.course.key = courseId;
    let param : any = {};
    param.key = courseId;
    param.tab = "1";
    this.save.emit(param);
    this.typeView = 'VIEW';
    // this.router.navigate(['/course/course0102'], {
    //   queryParams: {
    //     courseId: courseId,
    //   },
    // });
  }
  async onSave() {
      this.course.courseStartDate = this.datepipe.transform(this.course.courseStartDate, 'yyyy-MM-dd');
      this.course.courseEndDate = this.datepipe.transform(this.course.courseEndDate, 'yyyy-MM-dd');
      if(this.course.status){
        let isActive =  this.checkStatus();
        if(await isActive){
          this.saveCourse();
        }
      }else{
        this.saveCourse();
      }

  }

  saveCourse(){
    this.edu0102Service.save(this.course).subscribe({
      next: (response) => {
        if(!this.course.key){
          this.course = response[CommonConstant.DETAIL_KEY]
          this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.saved(response[CommonConstant.KEY], this.course.courseNm);
          // this.handleAfterSaveSuccess(this.trans.instant('message.success.saveSuccess'));
        }else{
          this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
          this.saved(response[CommonConstant.KEY], this.course.courseNm);
          // this.handleAfterSaveSuccess(this.trans.instant('message.success.updateSuccess'));
        }
      },
      error: () => {
        if(!this.course.key){
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
          //this.handleAfterSaveSuccess(this.trans.instant('message.error.saveFailed'));
        }else{
          this.toastrService.error(this.trans.instant('message.success.updateFailed'));
          //this.handleAfterSaveSuccess(this.trans.instant('message.success.updateFailed'));
        }
      },
    });
  }

  async checkStatus(){
    //debugger
    let listDocStatus : any ;
    if(this.course.subjectModels && this.course.subjectModels.length>0){
      listDocStatus =  this.course.subjectModels.map((element:any)=>{
        return element.documentStatus;
      })
    }

     if(listDocStatus && listDocStatus.includes('APPROVAL')){
      let flag = false ;
      let param :any = {};
      param.courseId = this.course.key;
  await    this.courseInfoServie.getAllList(param).toPromise().then(async res=>{
       let courseInfoModel = await res[CommonConstant.DETAIL_KEY].courseInfo;
        if(courseInfoModel) flag = true  ;
      })
      return flag ;
    }
    else{
      this.turnOnCheckPopup();
      return false;
    }
  }

  turnOnCheckPopup(){
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.courseForm.header'),
      key : "courseForm",
      message: this.trans.instant('message.confirm.courseForm.require'),
      acceptLabel: this.trans.instant('button.confirm.title'),
      rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.course.status = false;
      },
      reject: () => {
        this.course.status = false;
      },
    });
  }

  handleAfterSaveSuccess(mes: any) {
    this.confirmationService.confirm({
      header: mes,
      message: this.trans.instant('message.confirm.goListScreen'),
      acceptLabel: this.trans.instant('button.confirm.title'),
	    rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.goList()
      },
      reject: () => {
      this.typeView = 'VIEW';
      },
    });
  }

  goList(){
    this.router.navigateByUrl('edu/edu0102');
  }

  onCancel() {
    // if (this.tsstUser.userUid) {
    //   this.dialogRef.close(this.tsstUser);
    // } else {
    //   this.dialogRef.close();
    // }
    this.router.navigateByUrl('edu/edu0102');
  }


  onSelectImgFile(event: any) {
    if (event.target.files && event.target.files[0]) {
      this.fileToUpload = event.target.files[0];
      var fileURL = URL.createObjectURL(this.fileToUpload);
      this.imgViewer.nativeElement.src = fileURL;
      this.course.imgPath = this.fileToUpload.flePath;
      this.course.imgNm = this.fileToUpload.name;
      this.disable = false;
    }
  }

  showUpdateCourseForm(){
    this.typeView = 'MODIF';
    // this.getSubjectHistory(this.subjectId);
  }



}

