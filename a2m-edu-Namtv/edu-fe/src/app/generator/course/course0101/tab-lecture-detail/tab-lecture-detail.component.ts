import { Component, OnInit, ViewChild } from '@angular/core';
import { LectureFormComponent } from '../component/lecture-form/lecture-form.component';

@Component({
  selector: 'app-tab-lecture-detail',
  templateUrl: './tab-lecture-detail.component.html',
  styleUrls: ['./tab-lecture-detail.component.css']
})

export class TabLectureDetailComponent implements OnInit {

  @ViewChild('chapterForm')
  chapterForm!: LectureFormComponent
  constructor() { }

  ngOnInit(): void {
    
  }

  // initData(){
  //   this.courseForm.initData();
  // }

  changeTab(tabNumber: any){
    switch(tabNumber){
      case 1:
        // this.roleList.getData();
        this.chapterForm.ngOnInit();
        break;

      // case 2:
      //   // this.roleUser.initData();
      //   this.lectureList.ngOnInit();
      //   break;

    }
  }
}
