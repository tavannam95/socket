import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { CommonConstant } from 'src/app/constants/common.constant';
import { postReply } from 'src/app/model/community/post-reply';
import { CommonService } from 'src/app/services/common/common.service';
import { PostService } from 'src/app/services/community/post.service';

@Component({
  selector: 'app-reply-form',
  templateUrl: './reply-form.component.html',
  styleUrls: ['./reply-form.component.css']
})
export class ReplyFormComponent implements OnInit {

  configCkeditor: any = {};
  reply: any = {};
  @Input() inputReply: any = {};
  @Input() inputAnswer: any = {
    key: null,
  };
  @Output() replySaved = new EventEmitter();
  constructor(
    private postService: PostService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private trans: TranslateService,
    private toastrService: ToastrService,
  ) { }

  ngOnInit(): void {
    
    this.initConfigCk();
    if(this.inputReply.key){
      
      this.handleUpdate();
    }else{
      
      this.handleCreate();
    }
  }

  handleCreate(){
    this.reply = new postReply();
    this.reply.replyContent = "";
    this.reply.answerId = this.inputAnswer.key;
  }

  handleUpdate(){
    this.reply = this.inputReply;
  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  doUpdate(){
    this.postService.saveReply(this.reply).subscribe({
      next: (response) => {
        this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
        let replyResponse = response[CommonConstant.DETAIL_KEY];
        this.reply = replyResponse;
        this.reply.editReply = false;
        this.replySaved.emit({
          data: [],
          reply: replyResponse
        })
        
      },
      error: () => {
        this.toastrService.error(this.trans.instant('message.success.updateFailed'));
      },
    });
  }

  doCreate(){
    this.postService.saveReply(this.reply).subscribe({
      next: (response) => {
        this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
        let replyResponse = response[CommonConstant.DETAIL_KEY];
        this.replySaved.emit({
          data: [],
          reply: replyResponse
        })
      },
      error: () => {
        this.toastrService.error(this.trans.instant('message.error.saveFailed'));
      },
    });
  }

  onSave(){
    if(this.reply.key){
      this.doUpdate();
    }else{
      this.doCreate();
    }
  }

  onEditorChange(event: any) {
    
  }

}
