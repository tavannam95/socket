import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { CourseInfoService } from 'src/app/services/course/course-info.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';

@Component({
  selector: 'app-course-info',
  templateUrl: './course-info.component.html',
  styleUrls: ['./course-info.component.css']
})
export class CourseInfoComponent implements OnInit {
  courseInfo:any={};
  invalid : Boolean = false ;
  goal :any[]=[];
  submitted : any;
  courseProgram :any[]=[];
  courseId : any ;
  configCkeditor: any = {};
  typeView : any;

  checkView : any = true;
  courseObj: any = new Object();
  courseUid : any;
  @Output() afterSave = new EventEmitter<any>();

  constructor(
    private edu0102Service: Edu0102Service,
    private courseInfoServie : CourseInfoService,
    private route : ActivatedRoute,
    private router: Router,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    private toastrService: ToastrService,
    public commonService: CommonService,


  ) { }

  ngOnInit(): void {
    this.initConfigCk();
    this.route.queryParams.subscribe(res =>{
      this.courseId =  res['courseId'] ;
      if(this.courseId){
        this.getCourseById();
        this.initData();
      }
    })
  }

  initData(){
    let param :any = {};
    param.courseId = this.courseId;
    this.courseInfoServie.getAllList(param).subscribe(res=>{
      res[CommonConstant.DETAIL_KEY].courseInfo
      res[CommonConstant.DETAIL_KEY].courseProgram
    // Course Info
      if(!res[CommonConstant.DETAIL_KEY].courseInfo){
        if(this.commonService.permModify(this.courseObj.insUid, true)){
          this.typeView = 'ADD';
        }else{
          this.typeView = 'VIEW';
        }
        this.goal.push(new goalItem);
      }
      else{

        this.typeView = 'VIEW';
        this.courseInfo =  res[CommonConstant.DETAIL_KEY].courseInfo;
        let arrayGoal  = this.courseInfo.courseGoal.split('!#@');
        arrayGoal.forEach((element:any) => {
          if(element){
            let goalItem : any ={};
            goalItem.content = element;
            this.goal.push(goalItem);
          }
        });
      }
      // Course Program
      // if(res[CommonConstant.DETAIL_KEY].courseProgram.length==0){
      //   this.courseProgram.push(new courseProgramItem);
      // }else{
      //   this.courseProgram = res[CommonConstant.DETAIL_KEY].courseProgram;
      //   this.courseProgram.forEach((element:any) => {
      //     let listLeatue: lecture[]=[];

      //     let arrayLecture =  element.lecture.split("!#@");
      //     if(arrayLecture.length>0){
      //       arrayLecture.forEach((ele:any,index:any) => {
      //         if(ele!="" && ele!=undefined && ele!=null){
      //            listLeatue.push(new lecture);
      //            listLeatue[index].content= ele;
      //          }
      //         });
      //     }
      //     else{
      //       listLeatue.push(new lecture);
      //     }
      //     element.listLeatue = listLeatue;
      //   });
      // }


    })
  }

  initCheckView(){
    if(this.commonService.permModify(this.courseUid, true)){
      this.checkView = false;
    }else{
      this.checkView = true;
    }
  }

  getCourseById(){
    let requestModel = new searchModel();
    requestModel.key = this.courseId;
    this.edu0102Service.getCourseByCondition(requestModel).subscribe( (response:any) => {
    this.courseObj =  response[CommonConstant.DETAIL_KEY];
      this.courseUid = this.courseObj.insUid;
      this.initCheckView();
    });
  }

  // initUpDate(){
  //   let param :any = {};
  //   param.courseId = this.courseId;

  //   // Course Info
  //   // this.courseInfoServie.getListByCourseId(param).subscribe(res=>{
  //   //   if(!res[CommonConstant.DETAIL_KEY][0]){
  //   //     this.goal.push(new goalItem);
  //   //   }
  //   //   else{
  //   //     this.courseInfo =  res[CommonConstant.DETAIL_KEY][0];
  //   //     let arrayGoal  = this.courseInfo.courseGoal.split('!#@');
  //   //     arrayGoal.forEach((element:any) => {
  //   //       if(element){
  //   //         let goalItem : any ={};
  //   //         goalItem.content = element;
  //   //         this.goal.push(goalItem);
  //   //       }
  //   //     });
  //   //   }

  //   // })

  //   // Course Program
  //   // this.courseProgramService.getListByCourseId(param).subscribe(res=>{
  //   //
  //   //   if(res[CommonConstant.DETAIL_KEY].length==0){
  //   //     this.courseProgram.push(new courseProgramItem);
  //   //   }else{
  //   //     this.courseProgram = res[CommonConstant.DETAIL_KEY];
  //   //     this.courseProgram.forEach((element:any) => {
  //   //       let listLeatue: lecture[]=[];

  //   //       let arrayLecture =  element.lecture.split("!#@");
  //   //       if(arrayLecture.length>0){
  //   //         arrayLecture.forEach((ele:any,index:any) => {
  //   //           if(ele!="" && ele!=undefined && ele!=null){
  //   //              listLeatue.push(new lecture);
  //   //              listLeatue[index].content= ele;
  //   //            }
  //   //           });
  //   //       }
  //   //       else{
  //   //         listLeatue.push(new lecture);
  //   //       }
  //   //       element.listLeatue = listLeatue;
  //   //     });
  //   //   }
  //   // })
  // }

  remove(list : any,itemIndex: any){
      list.splice(itemIndex, 1);
  }

  add(list: any,type:string){
    if(type=="discription"){
      list.push(new goalItem);
    }else if(type=="program"){
      list.push(new lecture);
    }else{
      list.push(new courseProgramItem);
    }
  }

  // saveInfo(invalid :any ){
  //   if (invalid) {
  //     this.submitted = true;
  //     return;
  //   }else{
  //     this.courseInfo.courseId = this.courseId;
  //     this.courseInfo.courseGoal='';
  //     this.goal.forEach(element => {
  //       this.courseInfo.courseGoal +=element.content+'!#@';
  //     });
  //     this.courseInfoServie.save(this.courseInfo).subscribe((res:any) =>{
  //     });
  //   }
  // }

  // saveProgram(){

  //   this.courseProgram.forEach((element:any) => {
  //
  //     element.courseId = this.courseId;
  //     element.lecture = '';
  //     element.listLeatue.forEach((e:any) => {
  //       element.lecture+=e.content+"!#@";
  //     });
  //   });

  //   let request:any  = {};
  //   request.courseId = this.courseId;
  //   request.courseProgram = this.courseProgram;
  //   this.courseProgramService.save(request).subscribe(res=>{
  //   });

  // }

  saveAll(invalid:any){
    if (invalid) {
      this.submitted = true;
      return;
    }else{
      // Course Info
    this.initSaveCourseInFo();


      // Course Program
    //  let validPro = this.initSaveCourseProgram();
    //  if(validPro) return;

    }

    let request:any  = {};
    request.courseId = this.courseId;
    request.courseProgram = this.courseProgram;
    request.courseInfo = this.courseInfo;
    this.courseInfoServie.saveAll(request).subscribe({
      next: (response) => {

        if(!this.courseId){
          this.saved( this.courseId);
          this.typeView = 'VIEW';
          this.handleAfterSaveSuccess(this.trans.instant('message.success.saveSuccess'));
        }else{
          this.saved( this.courseId);
          this.typeView = 'VIEW';
          this.handleAfterSaveSuccess(this.trans.instant('message.success.saveSuccess'));
        }
      },
      error: () => {
        if(!this.courseId){
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        }else{
          this.toastrService.error(this.trans.instant('message.success.updateFailed'));
        }
      },

    });
  }

  initSaveCourseInFo(){
    this.courseInfo.courseId = this.courseId;
    // this.courseInfo.courseGoal='';
    this.courseInfo.courseDisciption= this.courseInfo.courseDisciption.trim();
    // let flag = false ;
    // this.goal.forEach((element:any,index:any) => {
    //   if(!element || element.content==''){
    //     let mes = this.trans.instant('course.info.goal.title') + (index+1).toString();
    //     this.popupValid(mes,index+1,'goal');
    //     flag = true;
    //     return;
    //   }
    // })
    this.courseInfo.courseGoal= this.courseInfo.courseGoal.trim();
    // return flag;
  }

  initSaveCourseProgram(){
    let valid : Boolean = false ;
    this.courseProgram.forEach((element:any,indx:any) => {
      element.title = element.title.trim();
      element.shortDiscription = element.shortDiscription.trim();
      if(element.shortDiscription==''){
        let mes = this.trans.instant('course.info.program.shortDiscription') ;
        this.popupValid(mes,indx+1,'short');
        valid = true;
        return
      }else if(element.title==''){
        let mes = this.trans.instant('course.info.program.title') ;
        this.popupValid(mes,indx+1,'title');
        valid = true;
        return
      }

      element.courseId = this.courseId;
      element.lecture = '';
      element.listLeatue.forEach((e:any,index : any) => {
        if(e.content==''){
          let mes = this.trans.instant('course.info.program.lecture')  + (index+1).toString();
          this.popupValid(mes,index+1,'lecture',indx+1);
          valid = true;
          return
        }
        element.lecture+=e.content.trim()+"!#@";
      });
    });
    return valid;
  }

  saved(courseId: any, courseNm ?: any) {
    // this.router.navigate(['/course/course0102'], {
    //   queryParams: {
    //     courseId: courseId,
    //   },
    // });
    let param : any = {};
    param.key = courseId;
    param.tab = "2";
    this.afterSave.emit(param);
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
        this.typeView = 'VIEW';
      },
    });
  }

  goList(){
    this.router.navigateByUrl('edu/edu0102');
  }

  popupValid(mes: any, index: any,type:any,parentIndex?:any) {
    this.confirmationService.confirm({
      header: mes,
      message: this.trans.instant('course.info.all.required'),
      acceptLabel: this.trans.instant('message.confirm.title'),
      acceptVisible: true,
      accept: () => {

        let targetEle = document.getElementById("lecture"+index);

        switch(type) {
          case 'goal':
            targetEle = document.getElementById("goal"+index);
            break;
          case 'lecture':
            targetEle =  document.getElementById("lecture"+index+'program'+parentIndex);
            break;
          case 'title':
            targetEle =  document.getElementById("title"+index);
            break;
          case 'short':
            targetEle =  document.getElementById("short"+index);
            break;
          default:
            break;
        }

        if(targetEle){
          let pos = targetEle.style.position;
          let top = targetEle.style.top;
          targetEle.style.position = 'relative';
          targetEle.style.top = '-200px';
          targetEle.scrollIntoView({behavior: 'smooth', block: 'start'});
          targetEle.style.top = top;
          targetEle.style.position = pos;
        }

      },
      reject: () => {

      },
    });
  }

  onEditorChange(even : any){

  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  showUpdateCourseInfoForm(){
    this.typeView = 'MODIF';
    // this.getSubjectHistory(this.subjectId);
  }

}

export class goalItem{
  content?: String;
  constructor() {
    this.content = "";
  }
  setContent(content : string){
    this.content=content;
  }
}

export class courseProgramItem{
  title?: String;
  shortDiscription?: String ;
  listLeatue :any[]=[];
  key?:number

  constructor() {
    this.title = "";
    this.shortDiscription = "";
    this.listLeatue = [new lecture];
  }
}

export class lecture{
  content?: String;
  constructor() {
    this.content = "";
  }



}
