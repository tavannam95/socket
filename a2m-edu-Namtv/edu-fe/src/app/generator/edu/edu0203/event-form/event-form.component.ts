import { DatePipe } from '@angular/common';
import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { Edu0203Service } from 'src/app/services/edu/edu0203.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { environment } from 'src/environments/environment';
import { StudentFormComponent } from '../../edu0101/component/student-form/student-form.component';
import { searchModel } from 'src/app/model/search-model';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonService } from 'src/app/services/common/common.service';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  styleUrls: ['./event-form.component.css']
})
export class EventFormComponent implements OnInit {
  disable = true;
  changePassword = false;
  tsstUser:any = {};
  emailPattern = PatternValidate.EMAIL_PATTERN;
  phonePattern = PatternValidate.PHONE_PATTERN;
  phonePatternBasic = PatternValidate.PHONE_PATTERN_BASIC;
  submitted: any = false;
  fileToUpload : any ;
  urlImage : any;
  courseList : any[] = [];
  baseUrl = environment.apiURL

  configCkeditor: any = {};
  event : any={};
  eventId : any;
  save: any;
  @ViewChild('imgViewer')
  imgViewer!: ElementRef;

  constructor(
    private toastrService: ToastrService,
    public dialogRef: MatDialogRef<StudentFormComponent>,
    public datepipe: DatePipe,
    private edu0203Service : Edu0203Service,
    private edu0102Service : Edu0102Service,
    private uploadService: FileUploadService,
    public commonService: CommonService,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private trans: TranslateService,


  ) {}

  ngOnInit(): void {
    this.getCourseList();
    this.eventId = this.data.event.key;
    if (this.eventId) {
      this.initData();
    } else {
      this.event.status = true;
    }

    this.initConfigCk();
  }

  initData(){
    this.event = this.data.event;

    if(this.event.filePath!=null ||this.event.filePath!=undefined || this.event.filePath!='' ){

      this.getFile()

      this.disable = false;
    }else{
      this.disable = true ;
    }
  }

  getFile() {

    this.urlImage =  this.baseUrl+'/tcco-files/getFile/'+this.event.dob+'?filePath='+this.event.filePath;

  }

  onSave(invalid: any) {
    // console.log(this.event);
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});

    if (invalid) {
      this.submitted = true;
      return;
    } else if (!this.event.equipmentId) {
      this.edu0203Service
      .save(this.event).subscribe({
        next: (response) => {
          this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.dialogRef.close(this.event);
          dialogSpinner.close()
        },
        error: () => {
          dialogSpinner.close()
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        },
      });
    }
  }

  saveAll(invalid: any){


      if(!this.event.key){
        if(!this.fileToUpload){
          this.toastrService.error('Choose One File : ' , 'Save  Failed !');
          return
        }
        this.uploadService.singleFileUpload(this.fileToUpload).subscribe(response=>{
          this.event.filePath = response.flePath;
          this.onSave(invalid);
        })
      }else{
        //update
        if(this.fileToUpload){
          this.uploadService.deleteFile(this.event.filePath).subscribe();
          this.uploadService.singleFileUpload(this.fileToUpload).subscribe(response=>{
            this.event.filePath = response.flePath;
            this.onSave(invalid);
          })
        }else{
          this.onSave(invalid);
        }
      }

    // if (this.fileToUpload || this.event.filePath) {
    //   if(this.event.filePath) {
    //     this.uploadService.deleteFile(this.event.filePath).subscribe();
    //     if(!this.fileToUpload){
    //       this.onSave(invalid);
    //     }
    //   }
    //   (this.uploadService.singleFileUpload(this.fileToUpload)).subscribe(
    //     {
    //       next: (response) => {
    //         this.event.filePath = response.flePath;
    //         this.onSave(invalid);
    //               //this.toastrService.success('Success', ' Uploaded !');
    //             },
    //             error: () => {
    //               //this.toastrService.error('Could not upload the file: ' + this.fileToUpload.name, 'Upload  Failed !');
    //             }
    //           });
    // }

  }

  getCourseList() {
    let param = new searchModel();
    param.getAll = true;
    this.edu0102Service.search(param).subscribe((response) => {
      this.courseList = response[CommonConstant.LIST_KEY];
    });
  }

  onCancel() {
    if (this.tsstUser.userUid) {
      this.dialogRef.close(this.tsstUser);
    } else {
      this.dialogRef.close();
    }
  }

  changeImage(event : any){
    if (event.target.files && event.target.files[0]) {
      this.fileToUpload = event.target.files[0] ;
      var fileURL = URL.createObjectURL(this.fileToUpload);
      this.imgViewer.nativeElement.src = fileURL;
      this.disable = false ;
    }
  }

  onEditorChange(event: any) {

  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  private onFileBrowserFileClick(event: { data: { ckEditorFuncNum: any; fileUrl: any; }; }) {
    (window as { [key: string]: any })['CKEDITOR'].tools.callFunction(event.data.ckEditorFuncNum, event.data.fileUrl);
  }

}
