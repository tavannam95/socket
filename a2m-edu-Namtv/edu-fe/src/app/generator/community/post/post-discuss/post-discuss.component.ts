import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { postAnswer } from 'src/app/model/community/post-answer';
import { CommonService } from 'src/app/services/common/common.service';
import { PostService } from 'src/app/services/community/post.service';
import { TempDataService } from 'src/app/services/temp-data.service ';
import { Location } from '@angular/common';

@Component({
  selector: 'app-post-discuss',
  templateUrl: './post-discuss.component.html',
  styleUrls: ['./post-discuss.component.css']
})
export class PostDiscussComponent implements OnInit {
  commingsoon= false;
  apiUrl : any = "";
  postId: number = 0;
  post: any = {
    postTitle: "",
    postContent: "",
    listAnswer: []
  };

  newAnswerContent: any = "";
  configCkeditor: any = {};
  constructor(
    private postService: PostService,
    private commonService: CommonService,
    private route: ActivatedRoute,
    private trans: TranslateService,
    private toastrService: ToastrService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private readonly location: Location,
  ) { }

  ngOnInit(): void {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.initConfigCk();
    this.apiUrl = this.postService.getApiURL();
    const routeParams = this.route.snapshot.paramMap;
    this.postId = Number(routeParams.get('postId'));
    if(this.postId > 0){
      this.initData();
    }else{

    }
  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  initData(){
    this.postService.getPostById(this.postId).subscribe({
      next: (response) => {
        this.post = response[CommonConstant.DETAIL_KEY];

      },
      error: () => {
        this.toastrService.error(this.trans.instant('message.error.systemErr'));
      }
    })
  }

  onEditorChange(event: any) {

  }

  tempAnswerObject: any = {};
  openReply(answer: any){
    answer.openReply = true;
    this.tempAnswerObject = answer;
  }

  tempReplyObject: any = {};
  editReply(reply: any){
    this.tempReplyObject = reply;
    this.tempReplyObject.editReply = true;
  }

  replyEdited(event: any){
    let reply = event.reply;
    this.tempReplyObject.replyContent = reply.replyContent;
    this.tempReplyObject.editReply = false;
  }

  replySaved(event: any){
    let reply = event.reply;
    this.tempAnswerObject.listReply.unshift(reply);
    this.tempAnswerObject.openReply = false;
  }

  removeReply(reply: any, answer: any, replyIndex: any){
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.postService.deleteReply(reply.key).subscribe({
          next: (response) => {
            this.toastrService.success(this.trans.instant('message.success.deleteSuccess'));

            answer.listReply.splice(replyIndex, 1);
          },
          error: () => {
            this.toastrService.error(this.trans.instant('message.error.deleteFailed'));
          },
        });
      },
      reject: () => {},
    });

  }

  removeAnswer(answer: any, post: any, answerIndex: any){
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.postService.deleteAnswer(answer.key).subscribe({
          next: (response) => {
            this.toastrService.success(this.trans.instant('message.success.deleteSuccess'));
            post.listAnswer.splice(answerIndex, 1);
          },
          error: () => {
            this.toastrService.error(this.trans.instant('message.error.deleteFailed'));
          },
        });
      },
      reject: () => {},
    });

  }

  saveAnswer(){
    let answer = new postAnswer();
    answer.answerContent = this.newAnswerContent;
    answer.postId = this.postId;
    this.postService.saveAnswer(answer).subscribe({
      next: (response) => {
        this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
        let answerResponse = response[CommonConstant.DETAIL_KEY];
        this.post.listAnswer.push(answerResponse);
        this.newAnswerContent = "";
      },
      error: () => {
        this.toastrService.error(this.trans.instant('message.success.updateFailed'));
      },
    });
  }

  editAnswer(answer: any){
    this.tempAnswerObject = answer;
    this.tempAnswerObject.editAnswer = true;
  }

  answerEdited(event: any){
    let answer = event.answer;
    this.tempAnswerObject.answerContent = answer.answerContent;
    this.tempAnswerObject.editAnswer = false;
  }

  openComment(){
    this.post.openComment = true;
  }

  commentSaved(event: any){
    let comment = event.answer;
    this.post.listAnswer.unshift(comment);
    this.post.openComment = false;
  }

  backFunction(){
    // this.router.navigateByUrl(this.tempDataService.postDiscusBackUrl);
    this.location.back();
  }

  goCourse(){
    if(this.post.referenceType == CommonConstant.POST_TYPE_LECTURE){
      this.gotoLecturePreview();
    }else{
      this.gotoChapterPreview();
    }
  }

  gotoLecturePreview(){
    this.router.navigate(['/course/course0201Lecture'], {
      queryParams: {
        lectureId: this.post.referenceId
      },
    });
  }

  gotoChapterPreview(){
    this.router.navigate(['/course/course0201Chapter'], {
      queryParams: {
        chapterId: this.post.referenceId
      },
    });
  }
}
