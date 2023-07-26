import { DatePipe } from '@angular/common';
import { Component, ElementRef, EventEmitter, Inject, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';

import { Course0101Service } from 'src/app/services/course/course0101.service';
import { searchModel } from 'src/app/model/search-model';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Lecture } from 'src/app/model/course/lecture';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { MultiUploadComponent } from 'src/app/generator/file/multi-upload/multi-upload.component';
import { LectureFileModel } from 'src/app/model/course/lecture-file';
import { ActivatedRoute, Router } from '@angular/router';
import { GetpdfService } from 'src/app/services/file/pdf.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { TccoFileModel } from 'src/app/model/common/tccoFile';
import { CommonService } from 'src/app/services/common/common.service';
import { CkeditorPreviewComponent } from 'src/app/generator/commons/ckeditor-preview/ckeditor-preview.component';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';


@Component({
  selector: 'app-lecture-form',
  templateUrl: './lecture-form.component.html',
  styleUrls: ['./lecture-form.component.css']
})
export class LectureFormComponent implements OnInit {

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  @ViewChild(MultiUploadComponent, { static: true })
  child!: MultiUploadComponent;

  //static:
  emailPattern = PatternValidate.EMAIL_PATTERN;
  phonePattern = PatternValidate.PHONE_PATTERN;
  phonePatternBasic = PatternValidate.PHONE_PATTERN_BASIC;
  configCkeditor: any = {};
  disable = false;
  showVideo = true;
  submitted: any = false;
  lectureTypes: any[] = [];
  typeOfDocument: any[] = [];
  CRUD: any;
  //data
  chapterList: any[] = [];
  listFile: any[] = [];
  lecture : any = {};
  currentChapterId : any;
  lectureId: any;
  subjectId : any;
  courseId : any;
  chapterNm :any = '';
  typeView : any;
  checkView : any = true;
  tags : any = '';
  constructor(
    private course0101Service: Course0101Service,
    private toastrService: ToastrService,
    private course0103Service: Course0103Service,
    public dialogRef: MatDialogRef<LectureFormComponent>,

    public dialog: MatDialog,
    public datepipe: DatePipe,
    private tccoStdService: TccoStdService,
    private route: ActivatedRoute,
    private router: Router,
    private pdfService: GetpdfService,
    private uploadService: FileUploadService,
    private commonService: CommonService,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.initData();
    this.initSubData();
  }

  initView(){
    const listLectureFile = this.lecture.lectureFileModel;
    listLectureFile.forEach((e: any) => {
      if(e.fileType == "document"){
        this.filePath = e.tccoFileModel.flePath;
        this.getFile();
      }
    });

  }

  initSubData(){
    this.initConfigCk()
    this.getChapterList();
    this.getLectureTypes();
    this.GetTypeDocument();
  }

  initData(){
    this.route.queryParams.subscribe(async (params) => {
      this.lectureId = params['lectureId'];
      this.currentChapterId = Number(params['chapterId']);
      this.subjectId = Number(params['subjectId']);
      this.courseId = Number(params['courseId']);
      if(this.lectureId){
        this.typeView = 'VIEW';
        this.CRUD = "U";
        this.handleUpdate(this.lectureId);
      }else{
        this.typeView = 'ADD';
        this.CRUD = "C";
        this.handleCreate();
      }
    });
    // this.initCheckView();
  }

  initCheckView(){
    if(this.commonService.permModify(this.lecture.insUid, true)){
      this.checkView = false;
    }else{
      this.checkView = true;
    }
  }

  handleUpdate(id: any){
    this.disable = true;
    this.course0101Service.getLectureById(id).subscribe({
      next: (response) => {
        this.lecture = response[CommonConstant.DETAIL_KEY];
        this.listFile = response[CommonConstant.DETAIL_KEY].lectureFileModel.map(function(item: any, index: any){
          let response = item.tccoFileModel;
          response.crud = "R";
          return response;
        });
        this.setStringTag(this.lecture.tags);
        this.initCheckView();
        this.initView();
      },
      error: () => {

      }
    })
  }
  setStringTag(tags : any){
    // let tagString = tags.slice(2, 10);
    // this.tags = tags.replace('"', '');
    let tagObj = JSON.parse(tags);
    if(tagObj != null){
      for(let i = 0; i < tagObj.length; i++) {
        if(i < (tagObj.length -1)){
          this.tags += tagObj[i] + ', ';
        }else{
          this.tags += tagObj[i] + '';
        }
      }
    }
    // tags.forEach((element : any) => {
    //   let listTag = element.slice(3, (element.length-2));
    // });
  }
  handleCreate(){
    this.lecture = new Lecture();
    this.lecture.status = 1;
    this.lecture.lectureFileModel = [];
    this.disable = false;
    this.lecture.chapterId = this.currentChapterId;
    this.lecture.lectureType = 'default'; // default
  }

  getLectureTypes() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.LECTURE_TYPE_COMM_CD)
      .subscribe((response) => {
        this.lectureTypes = response;
        this.commonService.selectLangComcode(this.lectureTypes);
        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  GetTypeDocument() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.TYPE_DOCUMENT)
      .subscribe((response) => {
        this.typeOfDocument = response;
        this.commonService.selectLangComcode(this.typeOfDocument);
        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  getChapterList(){
    let param = new searchModel();
    param.getAll = true;
    this.course0103Service.getSbjChapterSelect(param).subscribe((response) => {
        if(response[CommonConstant.LIST_KEY].length>0){
          this.chapterList = response[CommonConstant.LIST_KEY];
          this.getChapterNm();
        }
      });
  }

  getChapterNm() {
    let course: any = this.chapterList.find(
      ({ key }) => key === this.currentChapterId
    );
    this.chapterNm = course.chapterNm;
  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }


  saved(lectureId: any) {
    this.lecture.key = lectureId;
    this.router.navigate(['/course/course0101/lecture'], {
      queryParams: {
        lectureId : lectureId,
        chapterId : this.currentChapterId,
        subjectId : this.subjectId,
        courseId : this.courseId
      },
    });
  }

  onSave() {
    if (!this.lecture.key) {

      this.lecture.startDt = this.datepipe.transform(this.lecture.startDt, 'yyyy-MM-dd');
      this.lecture.endDt = this.datepipe.transform(this.lecture.endDt, 'yyyy-MM-dd');

      this.course0101Service.save(this.lecture).subscribe({
        next: (response) => {
          //this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          //this.save.emit(response);
          //this.navigateToUpdate(response[CommonConstant.KEY]);

          this.saved(response[CommonConstant.KEY]);
          this.handleAfterSaveSuccess(this.trans.instant('message.success.saveSuccess'));
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        },
      });
    } else {

      this.lecture.startDt = this.datepipe.transform(this.lecture.startDt, 'yyyy-MM-dd');
      this.lecture.endDt = this.datepipe.transform(this.lecture.endDt, 'yyyy-MM-dd');

      this.course0101Service.save(this.lecture).subscribe({
        next: (response) => {
          //this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
          this.initData();
          // this.child.ngOnInit();
          // this.save.emit(response);
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

        return
      },
    });
  }

  goList(){
    this.router.navigate(['/course/course0101'], {
      queryParams: {
        chapterId: this.currentChapterId,
        subjectId : this.subjectId,
        courseId: this.courseId,
        tab: 2
      },
    });
  }

  previewLecture(){
    //if(this.courseId){
      const dialogRef = this.dialog.open(CkeditorPreviewComponent, {
        width: '0px',
        height: '0px',
        data: { content : this.lecture.lectureContent },
        panelClass: 'custom-modalbox',
      });

      dialogRef.afterClosed().subscribe((response: any[]) => {
        if(response){
          this.initData();
        }

      });
    //}
  }

  backListDocument(){
    let data = {
      tabNumber: 2
    }
    this.commonService.changeBackCourse(data);
    this.router.navigate(['/course/course0101'], {
      queryParams: {
        chapterId: this.currentChapterId,
        subjectId : this.subjectId,
        courseId: this.courseId,
        tab: 3
      },
    });
  }

  showUpdateDocumentForm(){
    this.typeView = 'MODIF';
    //this.getSubjectHistory(this.subjectId);
  }

  checkLinkVideo(){
    this.showVideo = false;
  }
  onEditorChange(event: any) {

  }

  handleSave(invalid: any){
    if (invalid) {
      this.submitted = true;
      return;
    }
    this.onSave();
    // if(this.lecture.lectureType == 'default'){
    //   this.uploadMultiFileAndSave();
    // }else if(this.lecture.lectureType == 'document'){
    //   this.uploadFilePdf();
    // }
  }

  uploadFilePdfDone(tccoFileModel: TccoFileModel){
    let lectureFile = new LectureFileModel();
    lectureFile.tccoFileModel = tccoFileModel;
    lectureFile.crud = "C";
    lectureFile.fileType = "document";
    this.lecture.lectureFileModel.push(lectureFile);

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

  uploadMultiFileAndSave(){
    this.child.callFromParent();
  }

  onFileUploaded(event: any){
    event.data.forEach((tccoFileResponse: any) => {

      let lectureFile = new LectureFileModel();
      lectureFile.tccoFileModel = tccoFileResponse;
      lectureFile.crud = "C";
      lectureFile.fileType = "default";
      this.lecture.lectureFileModel.push(lectureFile);
    });

    event.listTccoFile.forEach((tccoFileResponse: any, index: any) => {
      if(tccoFileResponse.crud == "D"){
        this.lecture.lectureFileModel[index].crud = "D";
      }

   });

   this.onSave();
  }

  receiveArrTag(event: any){
    this.lecture.tags = JSON.stringify(event.arrTag);
  }

  deleteFile(seq:any, event: any){

  }

  navigateToUpdate(id: any) {
    this.router.navigateByUrl('/course/course0101/lecture/'+id);
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

      const listLectureFile = this.lecture.lectureFileModel;
      listLectureFile.forEach((e: any) => {
        if(e.fileType == "document"){
          e.crud = "D";
        }
      });
    }
  }
}

