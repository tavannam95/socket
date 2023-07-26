import { Component, EventEmitter, Inject, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonService } from 'src/app/services/common/common.service';
import { PostService } from 'src/app/services/community/post.service';

@Component({
  selector: 'app-form-post-form',
  templateUrl: './form-post-form.component.html',
  styleUrls: ['./form-post-form.component.css']
})
export class FormPostFormComponent implements OnInit, OnChanges {

  configCkeditor: any = {};
  submitted: any = false;
  @Input() post: any = {
    postTitle: "",
    postContent: ""
  }
  @Input() tags: any = "";
  @Input() postId: any = "";
  @Input() classId: any = "";
  @Input() type: any = "" ;
  @Input() referenceType : any = "" ;
  @Input() referenceId: any = "" ;
  @Input() onlyInsert: Boolean = false;


  @Output() saveSuccess = new EventEmitter
  constructor(
    private postService: PostService,
    private commonService: CommonService,
    private trans: TranslateService,
    private toastrService: ToastrService,
    private confirmationService: ConfirmationService,
    private router: Router,
    public dialog: MatDialog,
    private dialogRef: MatDialogRef<FormPostFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    this.post.tags = this.tags;
  }

  ngOnInit(): void {
    this.postId = this.data.postId;

    this.initConfigCk();
    if(this.postId!=""){
      this.handleUpdate();
    }
  }

  handleUpdate(){
    this.initData();
  }
  initDataDialog(){
    this.postId = this.data.postId;
    this.type = this.data.type;
    this.classId = this.data.classId;
    this.referenceType = this.data.referenceType;
    this.referenceId = this.data.referenceId;
  }

  initData(){
    this.initDataDialog();
    this.postService.getPostOnlyById(this.postId).subscribe({
      next: (response) => {

        this.post = response[CommonConstant.DETAIL_KEY];

      },
      error: () => {
        this.toastrService.error(this.trans.instant('message.error.systemErr'));
      }
    })
  }

  handleCreate(){
    if(this.referenceType  && this.referenceId){
      this.post.referenceType = this.referenceType;
      this.post.referenceId = this.referenceId;
    }
  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  onEditorChange(event: any) {

  }

  handleSave(invalid: any){
    if (invalid) {
      this.submitted = true;
      return;
    }
    this.onSave();
  }

  initSave(){
    this.post.type = this.type;
    this.post.classId = this.classId;
    this.post.referenceType = this.referenceType;
    this.post.referenceId = this.referenceId;
  }

  onSave(){
    this.initSave();
    if (!this.post.key) {
      this.postService.savePost(this.post).subscribe({
        next: (response) => {

          this.post.key = response[CommonConstant.KEY];
          let mes = this.trans.instant('message.success.saveSuccess');
          //this.toastrService.success(mes);
            this.handleAfterSaveSuccess(mes);

        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
          //this.handleAfterSaveSuccess(this.trans.instant('message.error.saveFailed'));
        },
      });
    } else {
      this.postService.savePost(this.post).subscribe({
        next: (response) => {
          let mes = this.trans.instant('message.success.updateSuccess')
          //this.toastrService.success(mes);

          this.handleAfterSaveSuccess(mes);
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.success.updateFailed'));
          //this.handleAfterSaveSuccess(this.trans.instant('message.success.updateFailed'));
        },
      });
    }
  }

  // handleAfterSaveSuccessOnlyIns(mes: any){
  //   this.post = {
  //     postTitle: "",
  //     postContent: "",
  //     tags : this.tags
  //   }

  //   this.confirmationService.confirm({
  //     header: mes,
  //     message: this.trans.instant(mes),
  //     acceptLabel: this.trans.instant('button.submit.title'),
  //     key: "form-post-form",
  //     rejectVisible: false,
  //     accept: () => {
  //       this.saveSuccess.emit()
  //       this.dialogRef.close();
  //     },
  //     reject: () => {


  //     },
  //   });
  // }

  handleAfterSaveSuccess(mes: any) {
  this.toastrService.success(
    this.trans.instant('message.success.saveSuccess')
  );
  this.dialogRef.close(this.post);
  }

  onCancel(){
    this.dialog.closeAll();
}

  // goList(){
  //   this.router.navigateByUrl('community/post');
  // }

  receiveArrTag(event: any){
    this.post.tags = JSON.stringify(event.arrTag);
  }

  receiveArrUser(event: any){
    this.post.listNotiModel = event.arrTag;
  }
}
