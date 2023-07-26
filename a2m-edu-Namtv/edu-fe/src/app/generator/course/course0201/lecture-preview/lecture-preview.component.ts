import { Component, OnInit, ViewChild } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { ListPostInCourseComponent } from 'src/app/generator/community/post/list-post-in-course/list-post-in-course.component';
import { Lecture } from 'src/app/model/course/lecture';
import { Course0101Service } from 'src/app/services/course/course0101.service';
import { TempDataService } from 'src/app/services/temp-data.service ';
import { Location } from "@angular/common";
import { CommonService } from 'src/app/services/common/common.service';

@Component({
  selector: 'app-lecture-preview',
  templateUrl: './lecture-preview.component.html',
  styleUrls: ['./lecture-preview.component.css']
})
export class LecturePreviewComponent implements OnInit {
  isViewPost = false;
  disable = false;
  lecture: any = new Lecture();
  lectureId: any;
  listFile: any = [];
  subjectId: any;
  chapterId: any;
  courseId : any;
  videoUrl : any;
  contentLecture : any;
  public filePath: string = "";
  public fileType: string = "pdf";
  postLectureType : string = CommonConstant.POST_TYPE_LECTURE;

  @ViewChild('listPost') listPost :ListPostInCourseComponent | undefined;
  constructor(
    private route : ActivatedRoute,
    private course0101Service: Course0101Service,
    private tempDataService: TempDataService,
    private router: Router,
    private sanitizer : DomSanitizer,
    private location: Location,
    private commonService : CommonService,
  ) { }

  async ngOnInit(): Promise<void> {
    await this.route.queryParams.subscribe(async (params) => {
      this.lectureId = params['lectureId'];
      this.chapterId = params['chapterId'];
      this.subjectId = params['subjectId'];
      this.courseId = params['courseId'];
    });
    this.getLecture();
    this.tempDataService.postDiscusBackUrl = this.router.url;
  }
  getLecture(){
    this.course0101Service.getLectureById(this.lectureId).subscribe({
      next: (response) => {

        this.lecture = response[CommonConstant.DETAIL_KEY];
        let aa = this.lecture.lectureContent;
        let bbb = "";

        (document.getElementById("id_tag_content") as any).innerHTML = aa;
        this.listFile = this.lecture.lectureFileModel.map(function(item: any, index: any){
          let tmp = item.tccoFileModel;
          tmp.crud = "R";
          return tmp;
        });
        let linkVideo = this.lecture.linkVideo;
        if(linkVideo == '' || linkVideo == null){
          this.disable = true;
        }
        this.videoUrl = this.sanitizer.bypassSecurityTrustResourceUrl(linkVideo);
        let LectureContent = this.lecture.lectureContent;
        this.contentLecture = this.sanitizer.bypassSecurityTrustHtml(LectureContent);
        this.initView();
      },
      error: () => {

      }
    })
  }

  backSubject(){
    let data = {
      subjectId : this.subjectId,
      chapterId: this.chapterId,
      docId: this.lectureId,
    }
    this.commonService.changeBackCourse(data);
    this.router.navigate(['/course/course0201Preview'], {
      queryParams: { courseId: this.courseId},
    });
  }


  initView(){
    const listLectureFile = this.lecture.lectureFileModel;
    listLectureFile.forEach((e: any) => {
      if(e.fileType == "document"){
        this.filePath = e.tccoFileModel.flePath;
        // this.getFile();
      }
    });
  }

  // getFile() {
  //   if (this.fileType == "pdf") {
  //     this.pdfService.getPdf(this.filePath, this.fileType).subscribe((responseMessage) => {
  //       let file = new Blob([responseMessage], { type: 'application/pdf' });
  //       var fileURL = URL.createObjectURL(file);
  //       this.pdfViewer.nativeElement.data = fileURL;
  //     })
  //   } else {

  //     this.pdfService.getExcel(this.filePath, this.fileType).subscribe((responseMessage) => {
  //       let file = new Blob([responseMessage], { type: 'application/vnd.ms-excel' });
  //       var fileURL = URL.createObjectURL(file);
  //       window.open(fileURL, "_blank");
  //     })
  //   }
  // }

  insertPostSuccess(){
    if(this.isViewPost && this.listPost){
        this.listPost.listPost = [];
        this.listPost.initPageRequest();
        this.listPost.initData();
    }
  }
}
