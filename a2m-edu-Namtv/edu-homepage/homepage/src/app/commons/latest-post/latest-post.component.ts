import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CommonService } from 'src/app/services/common.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-latest-post',
  templateUrl: './latest-post.component.html',
  styleUrls: ['./latest-post.component.css']
})
export class LatestPostComponent implements OnInit {

  students: any[] = [];
  news: any[] = [];

  constructor(
    private commonService: CommonService,
    public translate: TranslateService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getNews();
  }

  getNews(){
    this.commonService.getNews({start: 0,limit: 3}).subscribe((res: any)=>{
      this.news = res.datas;
      // this.lazyLoadService.loadScript();
    });
  }

  goToNewsDetail(id: any){
    this.router.navigate(["/blog/detail"],{
      queryParams: {id: id}
    })
  }


}
