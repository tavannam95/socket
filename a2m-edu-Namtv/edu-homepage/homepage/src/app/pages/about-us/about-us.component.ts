import { LazyLoadScriptService } from './../../services/lazy-load-script.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import {CommonService} from "../../services/common.service";

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.css']
})
export class AboutUsComponent implements OnInit, AfterViewInit {
  feedback: any[] = [];
  content : any =''
  status : any ='Xem thêm'
  constructor(private loadScriptService: LazyLoadScriptService, private commonService: CommonService) { }


  ngAfterViewInit(): void {
    this.loadScriptService.loadScript();
  }

  ngOnInit(): void {
    this.getFeedback();
  }

  private getFeedback() {
    this.feedback = this.commonService.getStudentsList();
  }


  counter(i: number) {
    return new Array(i);
  }

  handleShowHide(){
    if(this.content!=''){
      this.status='Xem thêm'
      this.content='';
    }else{
      this.status='Ẩn bớt'
      this.content = ''
      +' Để đến với A2MEdu, bạn không cần có kiến thức lập trình, chỉ cần một tinh thần khao khát tự xây dựng tương lai của chính mình. Chúng tôi sẽ nỗ lực hết sức để giúp bạn tìm ra phiên bản tốt nhất của mình bằng sự tận tâm, tận lực để đồng hành cùng các bạn trẻ, kết nối các bạn tới những cơ hội tốt nhất để đi đến tương lai'
    }
  }
}
