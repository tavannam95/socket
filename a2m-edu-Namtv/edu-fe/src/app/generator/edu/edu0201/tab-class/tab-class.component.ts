import { ListClassAnnouncementComponent } from './../list-class-announcement/list-class-announcement.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-tab-class',
  templateUrl: './tab-class.component.html',
  styleUrls: ['./tab-class.component.css']
})
export class TabClassComponent implements OnInit {

  classId : any ;
  isEdit:Boolean = false;
  tab : any ;

  @ViewChild('confirmDialog')
  confirmDialog!: any ;
  @ViewChild('listClassAnnou')
  classAnnou!: ListClassAnnouncementComponent ;

  constructor(
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    private route : ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.route.queryParams.subscribe(async (params) => {
      this.classId = params['id'];
      this.tab = params['tab'];
      if(this.tab){
        this.changeTab(this.tab)
      }
    });
  }

  changeTab(tabNumber: any){

    if(tabNumber!=1 && !this.classId){
      let mes = this.trans.instant('edu.class.init.required')
      this.confirmationService.confirm({
        header: mes,
        key : 'tabClass',
        acceptLabel: this.trans.instant('button.confirm.title'),
        accept: () => {
        },

      });
      tabNumber=1;
    }

    const ele = document.getElementById('tabClass'+tabNumber);
    ele?.click();
  }

  afterSave(event : any){
    let tab  = event.tab;
    this.classId  = event.key;
    this.redirect(tab);
  }

  redirectByType(type : String){
    let tab : String = '1' ;
    this.confirmDialog.accept();
    switch(type) {

      case 'ListCourse':
        this.goList();
        // code block
        break;
      case 'CourseForm':
        tab='1';
        this.redirect(tab);
        break;
      case 'CourseInfo':
        tab='2';
        this.redirect(tab);
        break;
      case 'ListSubject':
        // this.courseInfoDialog.accept();
        tab='3';
        this.redirect(tab);
        break;
      default:
        this.redirect(tab);
        break;
    }
  }

  redirect(tab : any){
    this.router.navigate(['/edu/edu0201Form'], {
      queryParams: {
        id: this.classId,
        tab :2,
      },
    });
    //debugger
    // this.subjectList.init();
    this.init();
    this.classAnnou.searchData();
  }

  goList(){
    this.router.navigateByUrl('edu/edu0201');
  }


}
