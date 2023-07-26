import { NewsService } from './../../../services/news.service';
import { LazyLoadScriptService } from './../../../services/lazy-load-script.service';
import { CommonService } from 'src/app/services/common.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-blog0102',
  templateUrl: './blog0102.component.html',
  styleUrls: ['./blog0102.component.css']
})
export class Blog0102Component implements OnInit, AfterViewInit {
  data: any = {};
  news: any[] = [];
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private commonService: CommonService,
    private loadScriptService: LazyLoadScriptService,
    private newsService: NewsService
  ) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params: any) =>{
      this.getNewsDetail(params.id);
    });

    this.getNews();

  }

  getNews(){
    this.newsService.getNews({start: 0,limit: 3}).subscribe((res: any)=>{
      this.news = res.datas;
      // this.lazyLoadService.loadScript();
    });
  }

  goToNewsDetail(id: any){
    this.router.navigate(["/blog/detail"],{
      queryParams: {id: id}
    })
  }

  ngAfterViewInit(): void {
    this.loadScriptService.loadScript();
  }

  getNewsDetail(id: any){
    this.newsService.getById(id).subscribe(res=>{
      this.data = res;
    })
  }
}
