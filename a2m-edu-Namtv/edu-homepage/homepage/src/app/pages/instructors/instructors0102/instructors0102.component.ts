import {LazyLoadScriptService} from './../../../services/lazy-load-script.service';
import {Component, OnInit, AfterViewInit} from '@angular/core';
import {CommonService} from "../../../services/common.service";
import { ActivatedRoute, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-instructors0102',
  templateUrl: './instructors0102.component.html',
  styleUrls: ['./instructors0102.component.css']
})
export class Instructors0102Component implements OnInit, AfterViewInit {
  teacher: any = {}
  id : any;

  constructor(
    private lazyLoadService: LazyLoadScriptService,
    private commonService: CommonService,
    private route : ActivatedRoute
  ) {
  }


  ngOnInit(): void {

    console.log(this.route)
    this.route.queryParams.subscribe(async (params) => {
      this.id = params['id'];
      this.getTeacherInfo(this.id);
    });
  }

  ngAfterViewInit(): void {
    this.lazyLoadService.loadScript();
  }

  private getTeacherInfo(number: number) {
    this.teacher = this.commonService.getTeacherInfo(number);
  }
}
