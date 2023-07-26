import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { PostService } from 'src/app/services/community/post.service';
import { Course0101Service } from 'src/app/services/course/course0101.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { SpinnerComponent } from '../../commons/spinner/spinner.component';
// import { PostService } from 'src/app/services/community/post.service';

@Component({
  selector: 'app-search-tag',
  templateUrl: './search-tag.component.html',
  styleUrls: ['./search-tag.component.css']
})
export class SearchTagComponent implements OnInit {

  totalPost : any ;
  totalChapter : any ;
  listChapter: any = [];
  searchText : String = '';
  listPost: any = [];
  apiUrl : any = "";
  fullLoad = false;
  pageRequestPost: any = new searchModel();
  pageRequestChapter: any = new searchModel();
  showHidePostBtn = false;
  showHideChapterBtn = false;

  constructor(
     private router: Router,
    //  private route : ActivatedRoute,
     private postService: PostService,
     private commonService : CommonService,
     private chapterService : Course0103Service,
     public dialog: MatDialog,
     private route : ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.apiUrl = this.postService.getApiURL();

    this.commonService.currentTagName.subscribe((res:String)=>{
      if( res){
        this.listPost = [];
        this.listChapter = [];
        this.searchText = res;
      }else{
        this.searchText = '';
      }

      this.pageRequestPost.searchText = this.pageRequestChapter.searchText = this.searchText;

      this.initPageRequestPost();
      this.searchPost();

      this.initPageRequestPChapter();
      this.searchChapter();

    })

  }


  goPostDiscuss(post: any){
    this.router.navigate(['/community/post/'+post.key], {
      queryParams: {

      },
    });
  }

  badgeTagClick(tagName: any,type?: any){
    this.searchText = tagName!=''?tagName:'';
    // if(type == 'all'){
      this.listPost = [];
      this.initPageRequestPost();
      this.pageRequestPost.searchText = tagName;
      this.searchPost();

      this.listChapter = [];
      this.initPageRequestPChapter();
      this.pageRequestChapter.searchText = tagName;
      this.searchChapter();
    // }else if(type=='post'){
    //   this.listPost = [];
    //   this.initPageRequestPost();
    //   this.pageRequestPost.searchText = tagName;
    //   this.searchPost();
    // }else{
    //   this.listLecture = [];
    //   this.initPageRequestPLecture();
    //   this.pageRequestLecture.searchText = tagName;
    //   this.searchLecture();
    // }
  }

  initPageRequestPost(){
    this.pageRequestPost.page = 0;
    this.pageRequestPost.rows = 5;
  }

  searchPost(){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.postService.searchPost(this.pageRequestPost).subscribe({
      next: (response) => {
        let list = response[CommonConstant.LIST_KEY];

        this.totalPost =  response.totalItems ;

        if(list.length>0) this.genListTag(list);

        this.listPost.push(...list)
        if(this.listPost.length ==this.totalPost)
        {this.showHidePostBtn = false }
        else{this.showHidePostBtn = true}
        dialogSpinner.close();
      },
      error: () => {
        dialogSpinner.close();
      }
    })
  }

  showMorePost(){
    this.pageRequestPost.page += 1 ;
    this.pageRequestPost.rows = 5;
    this.searchPost();
  }

  initPageRequestPChapter(){
    this.pageRequestChapter.page = 0;
    this.pageRequestChapter.rows = 5;
  }

  searchChapter(){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});

    this.chapterService.searchByTag(this.pageRequestChapter).subscribe({
      next: (response : any) => {
        let list = response[CommonConstant.LIST_KEY];
        this.totalChapter =  response.totalItems ;
        if(list.length>0) this.genListTag(list);
        this.listChapter.push(...list)

        if(this.listChapter.length == this.totalChapter ){
          this.showHideChapterBtn = false }
        else{this.showHideChapterBtn = true}
        dialogSpinner.close();
      },
      error: () => {
        dialogSpinner.close();
      }
    })
  }

  showMoreChapter(){
    this.pageRequestChapter.page += 1 ;
    this.pageRequestChapter.rows = 5;
    this.searchChapter();
  }

  moveToChapter(lectureId:any){
    this.chapterService.getInfoCourse(lectureId).subscribe((res:any)=>{


      let lectureId = res.detail.lectureId;
      let courseId = res.detail.courseId;

        this.router.navigate(['/course/course0201Lecture'], {
          queryParams: {
            lectureId: lectureId,
            courseId : courseId
          },
        });

    })
  }

  genListTag(list: any[]){
    list.forEach((element: any) => {
      element.listTag = JSON.parse(element.tags);
    });
  }

}
