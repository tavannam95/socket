import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { searchModel } from 'src/app/model/search-model';
import { QuizService } from 'src/app/services/course/quiz.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { QuizViewSubmittedComponent } from '../../quiz-view-submitted/quiz-view-submitted.component';
import { CommonService } from 'src/app/services/common/common.service';

@Component({
  selector: 'app-statistical-result-student',
  templateUrl: './statistical-result-student.component.html',
  styleUrls: ['./statistical-result-student.component.css']
})
export class StatisticalResultStudentComponent implements OnInit {
  totalRecords = NaN;
  pageRequest = new searchModel();
  listClassSelected: any[] = [];
  studentSubmitSearch: any = {
    fullName : "",
    classIds : "",
    quizId : NaN
  }
  listClass: any[] = [];
  listStuSubmited: any[] = [];
  listStuSubmitedFilter: any[] = [];
  courseId : any = "";
  subjectId: any;
  chapterId: any;
  quizId: any;
  constructor(
    private router: Router,
    private route : ActivatedRoute,
    private quizService: QuizService,
    public dialog: MatDialog,
    private edu0201Service: Edu0201Service,
    private commonService : CommonService,
  ) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const quizId = Number(routeParams.get('quizId'));
    this.studentSubmitSearch.quizId = quizId;

    this.route.queryParams.subscribe(async (params) => {
      this.courseId = params['courseId'];
      this.subjectId = params['subjectId'];
      this.chapterId = params['chapterId'];
      this.quizId = params['quizId'];
      this.studentSubmitSearch.quizId = Number(params['quizId']);
      this.getListClass();
    });

    this.initData();
  }

  initData(){
    this.initPaging();
    this.onSearch();
  }

  initPaging(){
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
  }

  onSearch(){
    this.getListStuSubtmited();
  }

  getListStuSubtmited(){
    this.listClassSelected.forEach((item: any, index : number) => {
      if(index == 0){
        this.studentSubmitSearch.classIds = item.key + ", "
      }else if(index > 0){
        this.studentSubmitSearch.classIds += item.key + ", "
      }
      if(index == this.listClassSelected.length-1){
        this.studentSubmitSearch.classIds = this.studentSubmitSearch.classIds.substring(0, this.studentSubmitSearch.classIds.length-2)
      }
    });

    if(this.listClassSelected.length == 0){
      this.studentSubmitSearch.classIds = "";
    }

    let param = Object.assign(this.studentSubmitSearch, this.pageRequest)

    this.quizService.getListStuSubtmited(param).subscribe((response) => {
      this.listStuSubmited = response[CommonConstant.LIST_KEY];
      this.listStuSubmitedFilter = this.listStuSubmited;
      this.totalRecords = response[CommonConstant.COUNT_ITEMS_KEY];
      CommonUtil.addIndexForListReverse(this.listStuSubmited, this.pageRequest.page, this.totalRecords);
      if(this.listStuSubmited.length>0){
        var initStu = this.listStuSubmited[0];
        //this.gotoStudent(initStu, 0);
      }

    });
  }

  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.onSearch();
  }

  showView(stu: any){
    // this.router.navigate(['/course/course0201QuizView'], {
    //   queryParams: {
    //   },
    // });
    const dialogRef = this.dialog.open(QuizViewSubmittedComponent, {
      data: {
        student: stu,
      },
    });
    dialogRef.afterClosed().subscribe(result => {

    });
  };

  backSubject(){
    let data = {
      subjectId : this.subjectId,
      chapterId: this.chapterId,
      docId: this.quizId,
    }
    this.commonService.changeBackCourse(data);
    this.router.navigate(['/course/course0201Preview'], {
      queryParams: { courseId: this.courseId},
    });
  }

  retakeQuiz(){
    this.router.navigate(['/course/course0201Quiz'], {
      queryParams: {
        quizId: this.studentSubmitSearch.quizId,
        courseId : this.courseId
      },
    });
  }

  getListClass() {
    const param = {
      courseId: this.courseId
    }
    this.edu0201Service.search(param).subscribe((response) => {
      this.listClass = response.list;
    });
  }

  onSearchReset() {
    this.studentSubmitSearch.fullName = "";
    this.studentSubmitSearch.classIds = "";
    this.listClassSelected = [];
    this.onSearch();
  }
}
