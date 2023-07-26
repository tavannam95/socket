import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { ViewDoc } from 'src/app/model/course/viewDoc';
import { searchModel } from 'src/app/model/search-model';
import { CommonService, Test } from 'src/app/services/common/common.service';
import { ScrollService } from 'src/app/services/common/scroll.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';


@Component({
  selector: 'app-course-preview',
  templateUrl: './course-preview.component.html',
  styleUrls: ['./course-preview.component.css']
})
export class CoursePreviewComponent implements OnInit,AfterViewInit {

  @ViewChild('courseContent')
  courseContent!: ElementRef;
  @ViewChild('row')
  row!: ElementRef;
  panelOpenState = false;

  userInfo:any;
  courseId: any;
  subjectId: any;
  chapterId: any;
  docId: any;
  courseObj: any = new Object();
  couSubjectLst: any[] = [];
  arrRoles : any[] = [];
  checkView : any = true;
  viewDoc : any = {};
  listDocument: any[] = [];
  listTypeDoc: any[] = [];

  subjectId1: any;
  chapterId2: any;
  docId3: any;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrService: ToastrService,
    private edu0102Service: Edu0102Service,
    private course0103Service: Course0103Service,
    private commonService : CommonService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private scrollService : ScrollService,
    private tccoStdService: TccoStdService,
  ) { }
  ngOnInit(): void {
    this.init();
  }

  async init(){
    await this.getUserInfo();
    let strRoles = AuthenticationUtil.getUserRole();
    this.arrRoles = strRoles.split("_");

    this.route.queryParams.subscribe(async (params) => {
      this.courseId = params['courseId'];
      // this.commonService.changeCourse(this.courseId);

      let requestModel = new searchModel();
      requestModel.key = this.courseId;
      let request: any = {};
      request = Object.assign(requestModel, {});
      request.approvalStatus = CommonConstant.APPROVAL_STATUS_APPROVAL_COMM_CD;
      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
      if (this.courseId) {


        await this.edu0102Service.getCourseByCondition(requestModel).subscribe((response) => {

          this.courseObj = response[CommonConstant.DETAIL_KEY];
          if(this.courseObj.subjectModels.length>0){
            this.couSubjectLst = this.courseObj.subjectModels.reverse();
            this.couSubjectLst = this.couSubjectLst.filter(element => element.deleteFlag!=true && element.documentStatus==CommonConstant.DOCUMENT_STATUS_APPROVAL);
            this.couSubjectLst.forEach(element => {
              if(element.chapterModels.length>0){
                element.chapterModels = element.chapterModels.filter((ele:any) => ele.deleteFlag!=true && ele.documentStatus==CommonConstant.DOCUMENT_STATUS_APPROVAL);
                element.chapterModels.forEach((ele:any) => {
                  ele.lectureModels = ele.lectureModels.filter((e:any) => e.deleteFlag!=true);
                  let listLecture = ele.lectureModels;
                  let listQuiz = ele.listQuiz;
                  ele.listDocument = [];
                  if(listLecture.length > 0) {
                    listLecture.forEach((element:any) =>{
                      this.getListViewDoc(element);
                      ele.listDocument.push(element);
                    });
                  }
                  if(listQuiz.length > 0){
                    listQuiz.forEach((element:any) =>{
                      this.getListViewDoc(element);
                      element.typeDocument = 'Quiz';
                      ele.listDocument.push(element);
                    });
                  }
                  ele.listDocument.sort((a:any, b:any) => a.ordNo - b.ordNo);

                });
              }
            });
          }
          this.initCheckView();
          dialogSpinner.close();
          });
      }

    });
    this.getTypeDocumentByCommCode();
  }

  ngAfterViewInit(): void {
    this.getBehaviorSubject();
  }

  firstFlag = true;
  ngAfterViewChecked() {
    if(this.firstFlag){
      let id = "";
      if(this.docId) {
        id = "doc_"+this.docId;
        const targetEle = document.getElementById(id);
        if(targetEle){
          let pos = targetEle.style.position;
          let top = targetEle.style.top;
          targetEle.style.position = 'relative';
          targetEle.style.top = '-350px';
          targetEle.scrollIntoView({behavior: 'smooth', block: 'start'});
          targetEle.style.top = top;
          targetEle.style.position = pos;
          this.firstFlag = false;
        }
      }
    }
  }

  getBehaviorSubject(){
    this.commonService.backCourse$.subscribe((response: any) => {
      this.subjectId = response.subjectId;
      this.chapterId = response.chapterId;
      this.docId = response.docId;
    });
  }

  getListViewDoc(doc:any){
    if(doc.listViewDoc.length >0){
      doc.listViewDoc.forEach((e:any) => {
        if(e.userUid != this.userInfo.userUid){
          doc.listViewDoc = doc.listViewDoc.filter((item:any) => item != e);
        }
      })
      if(doc.listViewDoc.length == 0){
        doc.listViewDoc[0] = new ViewDoc();
        doc.listViewDoc[0].isComplete = false;
      }
    }else{
      doc.listViewDoc[0] = new ViewDoc();
      doc.listViewDoc[0].isComplete = false;
    }
    doc.checked = doc.listViewDoc[0].isComplete;
  }

  getUserInfo(){
    this.commonService.getUserInfo().subscribe((response) =>{
        this.userInfo = response;
      });
  }

  async getTypeDocumentByCommCode() {
    await this.tccoStdService
       .getCommCdByUpCommCd(CommonConstant.TYPE_DOCUMENT)
       .toPromise().then((response) => {
           this.listTypeDoc = response;
           this.commonService.selectLangComcode(this.listTypeDoc);

         // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
       });
   }

  getTypeDocNm(typeDocument : any){

    const commCode = this.listTypeDoc.find(
      (element) => element.commCd == typeDocument
    );
      if(!commCode) return "";
    return commCode.label;

  }

  checkReadDoc(doc:any){
    if(!doc.listViewDoc[0].isRead){
      doc.listViewDoc[0].isRead = true;
      this.saveViewDoc(doc);
    }
    // this.saveViewDoc(doc);
  }

  // checkSchedule(doc : any){
  //   return doc.checked?true: false ;
  // }

  handleListCheked(doc : any){
    if(!doc.checked){
      doc.checked = true;
      doc.listViewDoc[0].isComplete = true;
    }else{
      doc.checked = false;
      doc.listViewDoc[0].isComplete = false;
    }
    this.saveViewDoc(doc);
  }

  saveViewDoc(doc:any){
    if(doc.typeDocument == 'Quiz'){
      doc.listViewDoc[0].quizId = doc.key;
    }else{
      doc.listViewDoc[0].docId = doc.key;
    }
    doc.listViewDoc[0].userUid = this.userInfo.userUid;

    if (doc.listViewDoc[0].key == undefined) {
      this.course0103Service.saveViewDoc(doc.listViewDoc[0]).subscribe({
        next: (response) => {
          // this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
        },
        error: () => {
          // this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        },
      });
    } else {
      this.course0103Service.saveViewDoc(doc.listViewDoc[0]).subscribe({
        next: (response) => {
          // this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
        },
        error: () => {
          // this.toastrService.error(this.trans.instant('message.success.updateFailed'));
        },
      });
    }
  }

  initCheckView(){
    if(this.commonService.permModify(this.courseObj.insUid, true)){
      this.checkView = false;
    }else{
      this.checkView = true;
    }
  }

  getStatisPer(){
    return this.arrRoles.includes('R000') || this.arrRoles.includes('R001') || this.arrRoles.includes('R002') || this.arrRoles.includes('R013')
  }

  handleLectureDetail(lectureId: any, chapterId:any, subjectId:any, event:any){
    this.router.navigate(['/course/course0201Lecture'], {
      queryParams: {
        lectureId: lectureId,
        chapterId: chapterId,
        subjectId: subjectId,
        courseId : this.courseId
      },
    });
  }

  handleChapterDetail(chapterId: any){
    this.router.navigate(['/course/course0201Chapter'], {
      queryParams: {
        chapterId: chapterId,
        courseId : this.courseId
      },
    });
  }

  handleQuizDetail(quizId:any, chapterId:any, subjectId:any){
    if(this.arrRoles.includes(CommonConstant.ROLE_SYS_STUDENT)){
      this.router.navigate(['/course/course0201QuizHis'], {
        queryParams: {
          quizId: quizId,
          chapterId: chapterId,
          subjectId: subjectId,
          courseId : this.courseId
        },
      });
    }else {
      this.router.navigate(['/course/course0104/quiz-statistical-result'], {
        queryParams: {
          quizId: quizId,
          chapterId: chapterId,
          subjectId: subjectId,
          courseId : this.courseId
        },
      });
    }
    // this.router.navigate(['/course/course0201Quiz'], {
    //   queryParams: {
    //     quizId: quizId,
    //     courseId : this.courseId
    //   },
    // });
  }


  showUpdateCourseForm(courseId: any) {
    this.router.navigate(['/course/course0102'], {
      queryParams: { courseId: courseId },
    });
    // this.commonService.changeCourse(courseId);
  }
}
