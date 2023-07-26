import { DatePipe } from '@angular/common';
import { Component, ElementRef, EventEmitter, Inject, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Cookie } from 'ng2-cookies';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { AuthConstant } from 'src/app/constants/auth.constant';
import { CommonConstant } from 'src/app/constants/common.constant';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { CkeditorPreviewComponent } from 'src/app/generator/commons/ckeditor-preview/ckeditor-preview.component';
import { MultiUploadComponent } from 'src/app/generator/file/multi-upload/multi-upload.component';
import { TccoFileModel } from 'src/app/model/common/tccoFile';
import { ChapterFileModel } from 'src/app/model/course/chapter-file';
import { LectureFileModel } from 'src/app/model/course/lecture-file';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { GetpdfService } from 'src/app/services/file/pdf.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { ChapterPreviewComponent } from '../../../course0201/chapter-preview/chapter-preview.component';
import { Chapter } from 'src/app/model/course/chapter';


@Component({
  selector: 'app-chapter-form',
  templateUrl: './chapter-form.component.html',
  styleUrls: ['./chapter-form.component.css']
})
export class ChapterFormComponent implements OnInit {

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  @ViewChild( 'upload' )
  child!: MultiUploadComponent;

  token: any;
  disable = false;
  changePassword = false;
  subjectList: any[] = [];
  courseList: any[] = [];
  emailPattern = PatternValidate.EMAIL_PATTERN;
  phonePattern = PatternValidate.PHONE_PATTERN;
  phonePatternBasic = PatternValidate.PHONE_PATTERN_BASIC;
  chapterTypes: any[] = [];
  submitted: any = false;
  listFile: any[] = [];

  organizationFormals: any[] = [];
  chapter : any = {};
  course : any = {};
  subject : any = {};
  chapterId : any;
  courseId: any;
  configCkeditor: any = {};
  courseRequest: any = {};
  subjectId : any ;
  subjectNm : any;
  typeView : any;
  checkView : any = false;

  constructor(
    private router: Router,
    private course0103Service: Course0103Service,
    private toastrService: ToastrService,
    private course0102Service: Course0102Service,
    public datepipe: DatePipe,
    private route : ActivatedRoute,
    private commonService: CommonService,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    private tccoStdService: TccoStdService,
    private pdfService: GetpdfService,
    private uploadService: FileUploadService,
    public dialog: MatDialog,

  ) { }

  ngOnInit(): void {
    this.getOrganizationFormals();
    this.getChapterTypes();
    this.route.queryParams.subscribe(async (params) => {
      this.chapterId = params['chapterId'];
      this.subjectId = Number(params['subjectId']);
      this.courseId = Number(params['courseId']);
    });
    if (this.chapterId) {
      this.typeView = 'VIEW';
      this.initData(this.chapterId);
      this.disable = true;

      this.handleUpdate(this.chapterId);
    } else {
      this.typeView = 'ADD';
      this.chapter = new Chapter();
      this.chapter.chapterType = 'default'
      this.chapter.status = true;
      this.disable = false;
      this.chapter.subjectId = this.subjectId;
    }
    this.initConfigCk();
    this.token = AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY);
    //this.getCourseList();
    this.getSubjectList();
  }

  initCheckView(){
    if(this.commonService.permModify(this.chapter.insUid, true)){
      this.checkView = false;
    }else{
      this.checkView = true;
    }
  }

  initData(param : any){
    this.course0103Service.getChapterById(param).subscribe((response) => {
      this.chapter = response[CommonConstant.DETAIL_KEY];
      var startDate = this.chapter.startDt? new Date(this.chapter.startDt): null;
      var endDate = this.chapter.endDt? new Date(this.chapter.endDt): null;
      this.chapter.startDt = startDate;
      this.chapter.endDt = endDate;
      this.chapter.subjectId = this.subjectId;

      this.initCheckView();
    });
  }

  getSubjectList(){
    let param = new searchModel();
    param.getAll = true;
    this.course0102Service.search(param).subscribe((response) => {
        this.subjectList = response[CommonConstant.LIST_KEY];
        for(let i = 0; i < this.subjectList.length;i++){
          if(this.subjectList[i].key == this.subjectId){
            this.subjectNm = this.subjectList[i].subjectNm;
          }
        }
      });
  }

  // getCourseList(){
  //   let param = new searchModel();
  //   param.getAll = true;
  //   this.edu0102Service
  //     .search(param)
  //     .subscribe((response) => {
  //       this.courseList = response[CommonConstant.LIST_KEY];
  //     });
  // }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  saved(chapterId: any) {
    this.chapter.key = chapterId;
    this.router.navigate(['/course/course0101'], {
      queryParams: {
        courseId: this.courseId,
        subjectId : this.subjectId,
        chapterId: chapterId
      },
    });
    //this.ngOnInit();
  }

  handleSave(invalid: any){
    if (invalid) {
      this.submitted = true;
      return;
    }

    if(this.chapter.chapterType == 'default'){
      this.uploadMultiFileAndSave();
    }else if(this.chapter.chapterType == 'document'){
      this.uploadFilePdf();
    }
  }

  onSave() {
 if (!this.chapter.key) {

      this.chapter.startDt = this.datepipe.transform(this.chapter.startDt, 'yyyy-MM-dd');
      this.chapter.endDt = this.datepipe.transform(this.chapter.endDt, 'yyyy-MM-dd');

      this.course0103Service.save(this.chapter).subscribe({
        next: (response) => {
          // this.commonService.changeCourse(this.courseId);
          this.save.emit(this.chapter)
          //this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.saved(response[CommonConstant.KEY]);
          this.handleAfterSaveSuccess(this.trans.instant('message.success.saveSuccess'));
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        },
      });
    } else {

      this.chapter.startDt = this.datepipe.transform(this.chapter.startDt, 'yyyy-MM-dd');
      this.chapter.endDt = this.datepipe.transform(this.chapter.endDt, 'yyyy-MM-dd');

      this.course0103Service.save(this.chapter).subscribe({
        next: (response) => {
          // this.commonService.changeCourse(this.courseId);
          this.save.emit(this.chapter)
          //this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
          this.saved(response[CommonConstant.KEY]);
          this.handleAfterSaveSuccess(this.trans.instant('message.success.updateSuccess'));
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.success.updateFailed'));
        },
      });
    }
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
    this.router.navigate(['/course/course0103'], {
      queryParams: {
        subjectId : this.subjectId,
        courseId: this.courseId,
        tab: 2
      },
    });
  }

  onCancel(){
    this.router.navigate(['/course/course0103'], {
      queryParams: {
        subjectId : this.subjectId,
        courseId: this.courseId,
        tab: 2
      },
    });
  }

  onEditorChange(event: any) {
  }

  showUpdateChapterForm(){
    this.typeView = 'MODIF';
  }

  getOrganizationFormals() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.ORGANIZATION_FORMAL)
      .subscribe((response) => {
        this.organizationFormals = response;
        this.commonService.selectLangComcode(this.organizationFormals);
        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  getChapterTypes() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.LECTURE_TYPE_COMM_CD)
      .subscribe((response) => {
        this.chapterTypes = response;
        this.commonService.selectLangComcode(this.chapterTypes);
        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  receiveArrTag(event: any){
    this.chapter.tags = JSON.stringify(event.arrTag);

  }

  handleUpdate(id: any){
    this.disable = true;
    this.course0103Service.getChapterById(id).subscribe({
      next: (response) => {
        this.chapter = response[CommonConstant.DETAIL_KEY];
        this.chapter.listTag = JSON.parse(this.chapter.tags);
        this.listFile = response[CommonConstant.DETAIL_KEY].chapterFileModels.map(function(item: any, index: any){
          let response = item.tccoFileModel;
          response.crud = "R";
          return response;
        });
        this.initView();
      },
      error: () => {

      }
    })
  }

  initView(){
    const listChapterFile = this.chapter.chapterFileModels;
    listChapterFile.forEach((e: any) => {
      if(e.fileType == "document"){
        this.filePath = e.tccoFileModel.flePath;
        this.getFile();
      }
    });

  }

  onFileUploaded(event: any){
    event.data.forEach((tccoFileResponse: any) => {

      let chapterFile : any = {} ;
      chapterFile.tccoFileModel = tccoFileResponse;
      chapterFile.crud = "C";
      chapterFile.fileType = "default";
      this.chapter.chapterFileModels.push(chapterFile);
    });

    event.listTccoFile.forEach((tccoFileResponse: any, index: any) => {
      if(tccoFileResponse.crud == "D"){
        this.chapter.chapterFileModels[index].crud = "D";
      }

   });

   this.onSave();
  }

  uploadMultiFileAndSave(){
    this.child.callFromParent();
  }

   //pdf
   @ViewChild('pdfViewer')
   pdfViewer!: ElementRef;
   public filePath: string = "";
   public fileType: string = "pdf";
   public fileList: string[] = [];

   getFile() {
    if (this.fileType == "pdf") {
      this.pdfService.getPdf(this.filePath, this.fileType).subscribe((responseMessage) => {
        let file = new Blob([responseMessage], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);
        this.pdfViewer.nativeElement.data = fileURL;
      })
    } else {

      this.pdfService.getExcel(this.filePath, this.fileType).subscribe((responseMessage) => {
        let file = new Blob([responseMessage], { type: 'application/vnd.ms-excel' });
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL, "_blank");
      })
    }
  }

  getFileInNewWindow() {
    if (this.fileType == "pdf") {
      this.pdfService.getPdf(this.filePath, this.fileType).subscribe((responseMessage) => {
        let file = new Blob([responseMessage], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      })
    } else {
      this.pdfService.getExcel(this.filePath, this.fileType).subscribe((responseMessage) => {
        let file = new Blob([responseMessage], { type: 'application/vnd.ms-excel' });
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      })
    }
  }

  fileToUpload: any;
  onSelectPDFFile(event: any) {
    if (event.target.files && event.target.files[0]) {
      this.fileToUpload = event.target.files[0];
      var fileURL = URL.createObjectURL(this.fileToUpload);
      this.pdfViewer.nativeElement.data = fileURL;

      const listChapterFile = this.chapter.chapterFileModels;
        listChapterFile.forEach((e: any) => {
          if(e.fileType == "document"){
            e.crud = "D";
          }
        });
      }
  }


  uploadFilePdfDone(tccoFileModel: TccoFileModel){
    let chapterFile = new ChapterFileModel();
    chapterFile.tccoFileModel = tccoFileModel;
    chapterFile.crud = "C";
    chapterFile.fileType = "document";
    this.chapter.chapterFileModels.push(chapterFile);

    this.onSave();
  }

  uploadFilePdf(){
    if (this.fileToUpload) {
      (this.uploadService.singleFileUpload(this.fileToUpload)).subscribe(
       {
        next: (response) => {

          this.uploadFilePdfDone(response);
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

  previewLecture(){
    //if(this.courseId){
      const dialogRef = this.dialog.open(ChapterPreviewComponent, {
        width: '0px',
        height: '0px',
        data: {
          MODE : "DIAGLOG",
          chapter: this.chapter,
          listFile: this.listFile
        },
        panelClass: 'custom-modalbox',
      });

      dialogRef.afterClosed().subscribe((response: any[]) => {
        if(response){

        }

      });
    //}
  }

}
