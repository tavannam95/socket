import { CommonService } from 'src/app/services/common.service';
import { LazyLoadScriptService } from './../../../services/lazy-load-script.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";

@Component({
  selector: 'app-students0101',
  templateUrl: './students0101.component.html',
  styleUrls: ['./students0101.component.css']
})
export class Students0101Component implements OnInit, AfterViewInit {
  students: any[] = [];
  news: any[] = [];

  constructor(
    private loadScriptService: LazyLoadScriptService,
    private commonService: CommonService  ,
    public translate: TranslateService,
    private router: Router
  ) { }

  ngAfterViewInit(): void {
    this.loadScriptService.loadScript();
  }

  ngOnInit(): void {
    this.getStudents();
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

  getStudents(){
    this.students = this.commonService.getStudentsList();
  }
}

