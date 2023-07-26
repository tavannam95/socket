import { LazyLoadScriptService } from './../../../services/lazy-load-script.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import {CommonService} from "../../../services/common.service";

@Component({
  selector: 'app-instructors0101',
  templateUrl: './instructors0101.component.html',
  styleUrls: ['./instructors0101.component.css']
})
export class Instructors0101Component implements OnInit, AfterViewInit {
  teachers: any[] = [];
  constructor(
    private lazyLoadService: LazyLoadScriptService,
    private commonService: CommonService
  ){}
  ngOnInit(): void {
    this.getTeachersList();
  }

  ngAfterViewInit(): void {
    this.lazyLoadService.loadScript();
  }

  private getTeachersList() {
    this.teachers = this.commonService.getTeachersList()
  }
}
