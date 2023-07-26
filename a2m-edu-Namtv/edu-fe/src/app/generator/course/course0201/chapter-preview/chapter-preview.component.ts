import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { ListPostInCourseComponent } from 'src/app/generator/community/post/list-post-in-course/list-post-in-course.component';
import { Chapter } from 'src/app/model/course/chapter';
import { Course0103Service } from 'src/app/services/course/course0103.service';

@Component({
  selector: 'app-chapter-preview',
  templateUrl: './chapter-preview.component.html',
  styleUrls: ['./chapter-preview.component.css']
})
export class ChapterPreviewComponent implements OnInit {
  isViewPost = false;
  chapterId: any;
  listFile: any = [];
  courseId : any;
  chapter : any = new Chapter;
  public filePath: string = "";
  public fileType: string = "pdf";
  postChapterType : string = CommonConstant.POST_TYPE_CHAPTER;
  @ViewChild('listPost') listPost :ListPostInCourseComponent | undefined;

  MODE : any = "DEFAULT";
  constructor(
    private router: Router,
    private route : ActivatedRoute,
    private course0103Service : Course0103Service,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  async ngOnInit(): Promise<void> {
    if(this.data.MODE != null && this.data.MODE!= ""){
      this.MODE = this.data.MODE;
      this.chapter = this.data.chapter;
      this.listFile = this.data.listFile;
    }else{
      await this.route.queryParams.subscribe(async (params) => {
        this.chapterId = params['chapterId'];
        this.courseId = params['courseId'];
      });
      this.getChapter();
    }    
  }

  getChapter(){
    this.course0103Service.getChapterById(this.chapterId).subscribe({
      next: (response) => {

        this.chapter = response[CommonConstant.DETAIL_KEY];
        this.listFile = this.chapter.chapterFileModels.map(function(item: any, index: any){
          let tmp = item.tccoFileModel;
          tmp.crud = "R";
          return tmp;
        });
        this.initView();
        
      },
      error: () => {

      }
    })
  }

  initView(){
    const listChapterFile = this.chapter.chapterFileModels;
    listChapterFile.forEach((e: any) => {
      if(e.fileType == "document"){
        this.filePath = e.tccoFileModel.flePath;
        // this.getFile();
      }
    });
  }

  insertPostSuccess(){
    if(this.isViewPost && this.listPost){
        this.listPost.listPost = [];
        this.listPost.initPageRequest();
        this.listPost.initData();
    }
  }

  onCancel(){
    this.dialog.closeAll();
}

}
