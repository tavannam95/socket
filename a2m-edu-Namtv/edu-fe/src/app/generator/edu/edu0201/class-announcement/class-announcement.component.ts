import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { Edu020103Service } from 'src/app/services/edu/edu020103.service';

@Component({
  selector: 'app-class-announcement',
  templateUrl: './class-announcement.component.html',
  styleUrls: ['./class-announcement.component.css']
})
export class ClassAnnouncementComponent implements OnInit {
  classAnnouncement : any = {};
  listClassAnnoun : any[] = [];
  submitted : any = false;
  configCkeditor : any ;
  typeView : any;
  isEdit : Boolean = false;
  isClose : Boolean = true;
  title : String = '' ;


  constructor(
    private commonService : CommonService,
    private edu020103Service  : Edu020103Service,
    private edu0201Service  : Edu0201Service,
    private route : ActivatedRoute,
    public dialogRef: MatDialogRef<ClassAnnouncementComponent>,
    public dialog : MatDialog,
    private toastrService : ToastrService,
    private trans : TranslateService,
    private confirmationService: ConfirmationService,
    private toastr: ToastrService,

    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.initData();
    this.initConfigCk();
  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  onEditorChange(even : any){

  }

  initData(){

    this.data.classAnnouncement ? this.classAnnouncement = this.data.classAnnouncement : this.classAnnouncement = {}
    this.data.typeView ? this.typeView = this.data.typeView : this.typeView = 'VIEW';
    this.data.title ? this.title = this.data.title : this.title = '';
    this.data.isClose!=null ? this.isClose = this.data.isClose : this.isClose = true;

    if(this.commonService.userHasRole(CommonConstant.ROLE_SYS_STUDENT )){
      this.isEdit = false ;
      this.typeView = 'VIEW';
    }else{
      this.isEdit = true ;
    }
    //
  }

  onSave(invalid: any) {

    if (invalid) {
      this.submitted = true;
      return;
    }
    // console.log(this.classAnnouncement);
    //

    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.edu020103Service.save(this.classAnnouncement).subscribe({
      next: (response) => {
        this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
        //
        dialogSpinner.close();
        this.onCancel();
      },
      error: () => {
        this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        dialogSpinner.close();
      }
    })
  }

  onCancel() {
    if (this.classAnnouncement.key) {
      this.dialogRef.close(this.classAnnouncement);
    } else {
      this.dialogRef.close();
    }
  }

  editAnnoun(){
    this.typeView = 'EDIT';
  }

  delete(classAnnouncement : any){

    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu020103Service.delete(classAnnouncement.key).subscribe({
          next: (response) => {
            this.toastr.success(
              this.trans.instant('message.success.deleteSuccess')
            );
            this.dialogRef.close(this.classAnnouncement);
          },
          error:(response) =>{
            this.toastr.error(
              this.trans.instant('message.error.deleteFailed')
            );
          },
        })
      },
      reject: () => {},
    });
  }

}
