import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-course0201',
  templateUrl: './course0201.component.html',
  styleUrls: ['./course0201.component.css']
})
export class Course0201Component implements OnInit {
  pageRequest = new searchModel();
  baseUrl = environment.apiURL
  courseList: any[] = [];

  userUid : any =  AuthenticationUtil.getUserUid();
  userInfo : any = {
    userType: ''
  };
  constructor(
    private router: Router,
    private edu0102Service: Edu0102Service,
    private commonService: CommonService
  ) { }

  ngOnInit(): void {
    this.commonService.getUserInfo().subscribe(
      (response) =>{
        this.userInfo = response;
        if(this.userInfo.userType != 'STU'){
          this.searchFull();
        }else{
          this.getCourseByUserUid();
        }
      }
    );
  }

  ngAfterViewInit() {

  }

  getCourseByUserUid(){
    this.courseList = [];
    let param = new searchModel();
    param.key = this.userInfo.userUid;
    param.searchText = this.pageRequest.searchText;
    this.edu0102Service.getCourseByUserUid(param).subscribe((response) => {
      let lst = response[CommonConstant.LIST_KEY];
      lst.forEach((element: any) => {
        let imgUrl = this.baseUrl +'/tcco-files/getFile' + "/file" + '?filePath='+element.imgPath
        element.imgUrl = imgUrl;

        if(element.status){
          this.courseList.push(element)
        }else{

        }
      });
    });
  }

  searchFull(){
    this.courseList = [];
    let param = new searchModel();
    param.getAll = true;
    param.searchStatus = true;
    param.searchText = this.pageRequest.searchText;
    this.edu0102Service.search(param).subscribe((response) => {
      let lst = response[CommonConstant.LIST_KEY];
      lst.forEach((element: any) => {
        let imgUrl = this.baseUrl +'/tcco-files/getFile' + "/file" + '?filePath='+element.imgPath
        element.imgUrl = imgUrl;

        if(element.status){
          this.courseList.push(element)
        }else{

        }
      });
    });
  }
  onSearch(){
    if(this.userInfo.userType != 'STU'){
      this.searchFull();
    }else{
      this.getCourseByUserUid();
    }
  }

  openCourseInfo(courseId: any){
    this.router.navigate(['/course/course0201Preview'], {
      queryParams: { courseId: courseId},
    });

  }

  onSearchReset(){
    this.pageRequest.searchText = "";
    if(this.userInfo.userType != 'STU'){
      this.searchFull();
    }else{
      this.getCourseByUserUid();
    }
  }

  showAddCourseForm() {
    this.router.navigate(['/course/course0102'], {
      queryParams: {
        courseId : ''
      },

    });
  }

}
