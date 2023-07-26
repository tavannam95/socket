import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonService } from 'src/app/services/common/common.service';
import { PostService } from 'src/app/services/community/post.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';

@Component({
  selector: 'app-list-post-notificaton',
  templateUrl: './list-post-notificaton.component.html',
  styleUrls: ['./list-post-notificaton.component.css']
})
export class ListPostNotificatonComponent implements OnInit {

  listNotification: any = [];

  @Output() renewTotal = new EventEmitter();
  constructor(
    private postService: PostService,
    private router: Router,
    private commonService: CommonService,
  ) { }

  ngOnInit(): void {
    this.initData();
  }

  initData(){
    this.onSearch();
  }

  onSearch(){
    let param = {
      userUid : AuthenticationUtil.getUserUid()
    }
    this.postService.searchNoti( param ).subscribe((response) => {
      // 
      this.listNotification = response[CommonConstant.LIST_KEY];
      let total = 0;
      let totalChecked = 0;
      this.listNotification.forEach((ele: any) => {
        if(!ele.checked){
            total ++
        }
        else {
          totalChecked++;
        }
      });
      let obj = {
        total: total,
        totalChecked: totalChecked
      }
      this.renewTotal.emit(obj);
    });
  }

  goDocument(noti: any){
    const postId = noti.postId;
    this.router.navigateByUrl('community/post/'+postId);
    this.onCheckedNoti(noti);
    
  }

  onCheckedNoti(noti: any){
    this.postService.checkedNoti( noti ).subscribe((response) => {
      this.refreshList();
    });
  }

  refreshList(){
    this.onSearch();
  }
}
