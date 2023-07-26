import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { Edu020101Service } from 'src/app/services/edu/edu020101.service';
import { Edu020102Service } from 'src/app/services/edu/edu020102.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';

@Component({
  selector: 'app-my-class',
  templateUrl: './my-class.component.html',
  styleUrls: ['./my-class.component.css']
})
export class MyClassComponent implements OnInit {
  listClass : any = [] ;
  userUid : any =  AuthenticationUtil.getUserUid();
  constructor(
    private eud0201Service : Edu0201Service,
    private edu020101Service : Edu020101Service,
    private edu020102Service : Edu020102Service,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.getListClass();
  }

  getListClass( ){

    this.eud0201Service.getListClassByUserUid(this.userUid).subscribe(async (res: any) =>{
      this.listClass = await res.list;
      this.listClass.forEach((element:any) => {

        var currentDate = new Date();
        var startDate = new Date(element.startDate);
        var endDate = new Date(element.endDate);
        // var Difference_In_Time = endDate.getTime() - startDate.getTime();
        var totalDate = (endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24);
        if(currentDate.getTime() > startDate.getTime()){
          var completedDays = (currentDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24);
          var  percentComplete = completedDays/totalDate * 100 ;
          element.percentComplete = (Math.round(percentComplete * 100) / 100).toFixed(0);
          if(element.percentComplete>100) element.percentComplete = 100;
        }else{
          element.percentComplete = 0;
        }
        element.listStudent = [];
        element.listTeacher = [];
        const subjectModels = element.courseModel.subjectModels;
          const docCheck: any[] = [];
          const listDoc: any[] = [];
          subjectModels.forEach((sub:any) => {
            const listChapter = sub.chapterModels;
            listChapter.forEach((chap:any) => {
              chap.lectureModels.forEach((lec:any) => {
                this.getListViewDoc(lec);
                listDoc.push(lec);
                if(lec.listViewDoc.length > 0 && lec.listViewDoc[0].isComplete){
                  docCheck.push(lec);
                }
              });
              chap.listQuiz.forEach((quiz:any) => {
                quiz.typeDocument = 'QUIZ';
                this.getListViewDoc(quiz);
                listDoc.push(quiz);
                if(quiz.listViewDoc.length > 0 && quiz.listViewDoc[0].isComplete){
                  docCheck.push(quiz);
                }
              });
            });
          });
          if(docCheck.length > 0){
            var  percentDocComplete = docCheck.length/listDoc.length * 100 ;
            element.percentDocComplete = (Math.round(percentDocComplete * 100) / 100).toFixed(0);
            if(element.percentDocComplete>100) element.percentDocComplete = 100;
          }else{
            element.percentDocComplete = 0;
          }

      })
      this.listClass.forEach(async (element:any) => {
        await this.edu020101Service.getStudentByClass(element.key).subscribe(async (response:any) => {
          element.listStudent = await response.list ;

        });

       await this.edu020102Service.getTeacherByClass(element.key).subscribe(async (response:any) => {
          element.listTeacher = await response.list ;
        });
      });
    })
  }

  getListViewDoc(doc:any){
    if(doc.listViewDoc.length >0){
      doc.listViewDoc.forEach((e:any) => {
        if(e.userUid != this.userUid){
          doc.listViewDoc = doc.listViewDoc.filter((item:any) => item != e);
        }
      })
    }
  }

  gotoClass(clss : any){
    // this.commonService.changeIsScroll(true);
    this.router.navigate(["/edu/edu0201Form"],{
      queryParams: {id: clss.key }
    })
  }
}
