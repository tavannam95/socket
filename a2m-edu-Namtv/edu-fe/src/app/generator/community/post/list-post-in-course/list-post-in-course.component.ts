import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { searchModel } from 'src/app/model/search-model';
import { PostService } from 'src/app/services/community/post.service';

@Component({
  selector: 'app-list-post-in-course',
  templateUrl: './list-post-in-course.component.html',
  styleUrls: ['./list-post-in-course.component.css']
})
export class ListPostInCourseComponent implements OnInit {

  listPost: any = [];
  apiUrl : any = "";
  pageRequest: any = new searchModel();
  listLecture: any[] = [];
  fullLoad = false;
  searchText : any = "";


  @Input() referenceType: String = "";
  @Input() referenceId: Number = NaN;
  

  constructor(
    private postService: PostService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    
    this.apiUrl = this.postService.getApiURL();
    this.initPageRequest();
    this.initData();    
  }

  initPageRequest(){
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
    this.pageRequest.referenceId = this.referenceId;
    this.pageRequest.referenceType = this.referenceType;
  }

  initData(){
    this.onSearch();
  }

  loadMore(){
    this.pageRequest.page++;
    this.onSearch();
  }

  onSearch(){
    this.postService.searchPost(this.pageRequest).subscribe({
      next: (response) => {
        let list = response[CommonConstant.LIST_KEY];
        this.genListTag(list);
        if(list.length < this.pageRequest.rows){
          this.fullLoad = true;
        }else{
          this.fullLoad = false;
        }
        this.listPost.push(...list)
        
      },
      error: () => {

      }
    })
  }

  search(){
    this.listPost = [];
    this.initPageRequest();
    this.pageRequest.searchText = this.searchText;
    this.onSearch();
  }

  badgeTagClick(tagName: any){
    this.listPost = [];
    this.initPageRequest();
    this.pageRequest.searchText = tagName;
    this.onSearch();
  }

  genListTag(list: any[]){
    list.forEach((element: any) => {
      element.listTag = JSON.parse(element.tags);
    });
  }

  goPostDiscuss(post: any){
    this.router.navigate(['/community/post/'+post.key], {
      queryParams: {

      },
    });
  }
}

