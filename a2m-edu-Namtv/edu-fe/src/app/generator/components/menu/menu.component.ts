import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute, NavigationEnd, NavigationStart, Router } from '@angular/router';
import { I18nConfig } from 'src/app/config/i18n.config';
import { CommonConstant } from 'src/app/constants/common.constant';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { I18nService } from 'src/app/services/i18n.service';
import { Sys0101Service } from 'src/app/services/sys/sys0101.service';
declare var $:any;
@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  @ViewChild('btnShowHide', { read: ElementRef, static:false }) btnShowHide!: ElementRef;
  courseId:any;
  menuList: any[]=[];
  maxLv?: number
  language: any;
  courseObj: any;
  couSubjectLst: any;
  isScroll : any ;
  isSubMenu :any ;
  isBtnShowHide :any;
  constructor(
    private sys0101Service: Sys0101Service,
    private i18nService: I18nService,
    private commonService: CommonService,
    private edu0102Service: Edu0102Service,
    private router: Router,
    private route: ActivatedRoute,
  ) {
    this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language){
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe(
      (language: string) => {
        this.language = language;
        this.getDataNew();
      }
    );
   }

  ngOnInit() {
    this.getDataNew();
    this.initSubMenu();
    // this.getCourse();
    // this.handleChangeUrl();
    // this.commonService.isScroll$.subscribe((response:any)=>{
    //   this.isScroll = response;
    // })


  }
  getDataNew() {
    this.sys0101Service.getMenuByUser({ useYn: 'Y' }).subscribe(
      (res) => {
        if (res) {
          this.maxLv = res[0].lev;
          this.menuList = this.convertMenu(res);
          this.commonService.setMenuData(res);
        }
      }
    );
  }

  convertMenu(dataSource: any[]) {
    let tem: any = {};
    dataSource.forEach(ele => {
      tem[ele.menuId] = ele;
      if (ele.url && ele.url != "") {
        ele.urlPath = ele.url
      }
      else {
        ele.urlPath = "/";
      }
      ele.name = ele.menuNm
      ele.nameI18n = {
        kr: ele.menuNm,
        en: ele.menuNmEn,
        vi: ele.menuNmVi
      };

    })
    dataSource.forEach(ele => {
      if (tem[ele.upMenuId]) {
        if (tem[ele.upMenuId].children) {
          tem[ele.upMenuId].children.push(ele);
        }
        else {
          tem[ele.upMenuId].children = [];
          tem[ele.upMenuId].children.push(ele);
        }
      }
    })
    let results: any[] = []
    dataSource.forEach(ele => {
      if (ele.lev == this.maxLv) {
        results.push(ele);
      }
    })
    return results;
  }

  getData() {
    this.sys0101Service.search({ useYn: 'Y' }).subscribe(
      (response: any[]) => {
        this.toMenuList(response);
      }
    );
  }

  toMenuList(list: any[]) {
    // const menuList: Menu[] = list.map(r => this.toMenu(r));
    const menuList: any[] = list
    this.processData(menuList);
  }

  processData(menuList: any[]) {
    const menuMap: any = {};
    for (const item of menuList) {
      const parentId = item.parentId || 'ROOT';
      if (!menuMap[parentId]) {
        menuMap[parentId] = [];
      }
      menuMap[parentId].push(item);
    }

    for (const item of menuList) {
      item.children = menuMap[item.id];
    }
    this.menuList = menuMap['ROOT'];
  }

  // Change CourseId , Call APi get Course,Handle Btn ShowHide
  getCourse(){
    this.commonService.currentCourseId.subscribe( res => {
      if(res!='' && res!=undefined){
        this.courseId = res
        this.getCourseSubject(res);
      }else{
        this.handleShowHideSubMenu(false);
      }
    })
  }

  // Call APi get Course
  async getCourseSubject( courseId:any){
    let requestModel = new searchModel();
    requestModel.key = courseId;
  await  this.edu0102Service.getCourseByCondition(requestModel).toPromise().then((response:any) => {
    this.courseObj = response[CommonConstant.DETAIL_KEY];
    if(this.courseObj.subjectModels.length>0){
      this.handleShowHideSubMenu(true);
      this.couSubjectLst = this.courseObj.subjectModels.reverse();
      this.couSubjectLst = this.couSubjectLst.filter((element:any) => element.deleteFlag!=true);
      if(this.isScroll){
        this.couSubjectLst = this.couSubjectLst.filter((element:any) => element.status==true && element.documentStatus=="APPROVAL" );
      }
      this.couSubjectLst.forEach((element:any) => {
        if(element.chapterModels.length>0){
          // element.lev = 1
          element.children = element.chapterModels;
          element.children = element.children.filter((e:any) => e.deleteFlag!=true);
          // if(element.children.length>0){
          //   element.children.forEach((ele:any) => {
          //     ele.lev=2;
          //     ele.upMenuId = element.key
          //   });
          // }else{
          //   element.children=null;
          // }
        }
      });
    }else{
      this.couSubjectLst=[];
      this.handleShowHideSubMenu(false);
    }
    });
  }

  // Move to tabChapter Component
  moveToSubject(subjectId: any,showChild  : Boolean) {
    if(this.courseId){
      this.router.navigate(['/course/course0103'], {
        queryParams: { subjectId: subjectId, courseId: this.courseId},
      });
    }
    if(showChild==true){
      this.showHideChild(subjectId);
    }
    this.changeInFoCourse(this.courseId,subjectId,'');
  }

  // Move to tabLecture Component
  moveToChapter(subjectId:string,chapterId:string){
    this.router.navigate(['/course/course0101'], {
      queryParams: {
        chapterId: chapterId,
        subjectId : subjectId,
        courseId: this.courseId
      },
    });
    this.handleHightlight(chapterId);
    this.changeInFoCourse(this.courseId,subjectId,chapterId);
  }

  // Handle when click to Chapter in SubMenu
  handleHightlight(chapterId:String){
    // Remove  class "white-color" in  oldHightLight
    let oldHightLight =  Array.from(document.getElementsByClassName('white-color'));
    if(oldHightLight){
      oldHightLight.forEach(box => {
        box.classList.remove('white-color');
      });
    }
    //Add class "white-color" in  newHightLight
    let children = (document.getElementById('children'+chapterId) as HTMLElement)
    children.classList.add('white-color');

  }

  //Scroll to Subject or Lecture
  scrollToId(id: string,type:string,isShow : Boolean){
    let targetEle = document.getElementById(type+id);
        if(targetEle){
          let pos = targetEle.style.position;
          let top = targetEle.style.top;
          targetEle.style.position = 'relative';
          targetEle.style.top = '-140px';
          targetEle.scrollIntoView({behavior: 'smooth', block: 'start'});
          targetEle.style.top = top;
          targetEle.style.position = pos;
        }
        if(isShow){
          this.showHideChild(id);
        }
  }

  // Change Subject Name , Chapter Name in progress-bar component
  changeInFoCourse(courseId:any,subjectId:any,chapterId?:any){
    let course : any = {};
    course.courseId = courseId;
    course.subjectId = subjectId;
    course.chapterId = chapterId;
    this.commonService.changeInFoCourse(course);
  }

  //Show Hide Chapter When click to Subject
  showHideChild(subjectId:any){
    let parent  =  (document.getElementById('parent'+subjectId) as HTMLElement)
    let attribute =  parent.attributes[3];
    if(attribute.value!='false' && attribute != undefined){
      parent.setAttribute('aria-expanded','false');
    }else{
      parent.setAttribute('aria-expanded','true');
    }

    let children = (document.getElementById('children'+subjectId) as HTMLElement)
    let classChild =   children.classList.value;
    let isShow =  classChild.includes('show');
    if(isShow){
      children.classList.remove('show');
    }else{
      children.classList.add('show');
    }
  }

  // When Click to Menu and handle show hide SubMenu
  handleChangeMenu(menuId:any){
    if(menuId=='MNU_09' || menuId=='MNU_10' ){
      this.route.queryParams.subscribe(async (params) => {
        this.courseId = params['courseId'];
        if(this.courseId!=undefined && this.courseId!=''){
          this.isSubMenu=true;
          if(menuId=='MNU_09'){
            this.isScroll=false;
          }else{
            this.isScroll=true;
          }
        }else{
          this.isSubMenu=false;
          this.isBtnShowHide= false;
        }
      })
    }
    else{
      this.isSubMenu=false;
      this.isBtnShowHide= false;
    }
 }
  // When Click to Menu and handle show hide SubMenu
  handleChangeMenuSpecial(menuId:any){
    if(menuId=='MNU_12')
    this.router.navigate(['/community/post'], {
      queryParams: {
        
      },
    });
    
  }

 // When MenuCopenent Init
  initSubMenu(){
    let type = this.router.url.split('/')[1];
    if(type=='course'){
      this.courseId = this.router.url.split('courseId=')[1];
      let isPreview = this.router.url.includes('course0201Preview');
      if(this.courseId!=null && this.courseId !=undefined && this.courseId!=''){
        if(isPreview){
          this.isScroll=true;
        }
        this.getCourseSubject(this.courseId);
      }
    }
  }

  // Change icon and show hide SubMenu
  handleBtnShowhide(event : any){
    if(this.isSubMenu){
      this.btnShowHide.nativeElement.classList.remove('ri-arrow-up-s-fill')
      this.btnShowHide.nativeElement.classList.add('ri-arrow-down-s-fill')
    }else{
      this.btnShowHide.nativeElement.classList.add('ri-arrow-up-s-fill')
      this.btnShowHide.nativeElement.classList.remove('ri-arrow-down-s-fill')
    }
    this.isSubMenu=!this.isSubMenu ;
  }


  handleShowHideSubMenu(show : Boolean){
    this.isSubMenu = show;
    this.isBtnShowHide = show
  }

  handleChangeUrl(){
    this.router.events.subscribe((event: any) => {
      if (event instanceof NavigationEnd) {
        if(!event.url.includes('/course') || event.url.includes('/course/course0201')) this.handleShowHideSubMenu(false);
      }
    })
  }

}
