import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { CommonConstant } from 'src/app/constants/common.constant';
import { postAnswer } from 'src/app/model/community/post-answer';
import { CommonService } from 'src/app/services/common/common.service';
import { PostService } from 'src/app/services/community/post.service';

@Component({
  selector: 'app-answer-form',
  templateUrl: './answer-form.component.html',
  styleUrls: ['./answer-form.component.css']
})
export class AnswerFormComponent implements OnInit {

  configCkeditor: any = {};
  answer: any = {};
  @Input() inputAnswer: any = {};
  @Input() inputPost: any = {
    key: null,
  };
  @Output() answerSaved = new EventEmitter();
  
  constructor(
    private postService: PostService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private trans: TranslateService,
    private toastrService: ToastrService,
  ) { }

  ngOnInit(): void {
    this.initConfigCk();
    if(this.inputAnswer.key){
      
      this.handleUpdate();
    }else{
      
      this.handleCreate();
    }
  }

  handleCreate(){
    this.answer = new postAnswer();
    this.answer.answerContent = "";
    this.answer.postId = this.inputPost.key;
  }

  handleUpdate(){
    this.answer = this.inputAnswer;
  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  onEditorChange(event: any) {
    
  }

  onSave(){
    if(this.answer.key){
      this.doUpdate();
    }else{
      this.doCreate();
    }
  }

  doUpdate(){
    this.postService.saveAnswer(this.answer).subscribe({
      next: (response) => {
        this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
        let answerResponse = response[CommonConstant.DETAIL_KEY];
        this.answer = answerResponse;
        this.answer.editReply = false;
        this.answerSaved.emit({
          data: [],
          answer: answerResponse
        })
        
      },
      error: () => {
        this.toastrService.error(this.trans.instant('message.success.updateFailed'));
      },
    });
  }

  doCreate(){
    this.postService.saveAnswer(this.answer).subscribe({
      next: (response) => {
        this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
        let answerResponse = response[CommonConstant.DETAIL_KEY];
        this.answerSaved.emit({
          data: [],
          answer: answerResponse
        })
      },
      error: () => {
        this.toastrService.error(this.trans.instant('message.error.saveFailed'));
      },
    });
  }



}
