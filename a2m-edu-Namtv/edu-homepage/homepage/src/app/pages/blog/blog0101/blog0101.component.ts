import { NewsService } from './../../../services/news.service';
import { LazyLoadScriptService } from './../../../services/lazy-load-script.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CommonService } from 'src/app/services/common.service';
import { lastValueFrom } from 'rxjs';
import { Router } from '@angular/router';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-blog0101',
  templateUrl: './blog0101.component.html',
  styleUrls: ['./blog0101.component.css']
})
export class Blog0101Component implements OnInit, AfterViewInit {
  request: any = {
    start: 0,
    limit: 4
  };
  datas: any[] = [];
  totalRecords: number = 0;
  constructor(
    private loadScriptService: LazyLoadScriptService,
    private commonService: CommonService,
    private router: Router,
    public translate: TranslateService,
    private newsService: NewsService
  ) { }

  ngAfterViewInit(): void {
    this.loadScriptService.loadScript();
  }

  async ngOnInit(): Promise<void> {
    await this.getNews();
    // this.loadScriptService.loadScript();
  }


  async getNews(){
    await lastValueFrom(this.newsService.getNews(this.request)).then((res:any)=>{
      this.datas = res.datas;
      this.totalRecords = res.count;
    })
    // this.commonService.getNews().subscribe({
    //   next: (res: any) =>{

    //     console.log(this.datas);
    //   },
    //   error: err =>{

    //   }
    // })
  }

  onChangePagi(event: any){
    console.log(event)
    this.request.start = event.page * event.rows;
    this.getNews();
  }

  goToDetail(id: any){
    this.router.navigate(["/blog/detail"],{
      queryParams: {id: id},
      state: { searchRequest: this.request }
    })
  }
}
