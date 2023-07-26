import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { searchModel } from 'src/app/model/search-model';
import { Edu0103Service } from 'src/app/services/edu/edu0103.service';
import { CommonUtil } from 'src/app/utils/common-util';

@Component({
  selector: 'app-room-list',
  templateUrl: './room-list.component.html',
  styleUrls: ['./room-list.component.css']
})
export class RoomListComponent implements OnInit {
  pageRequest = new searchModel();
  listRoom!: any[];
  totalRoom = 0;
  selectedRow: any = {};

  listChecked:  any [] = [];
  datasDeleted: any[] = [];

  constructor(
    private edu0103Service: Edu0103Service,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.initData();
    this.onSearch();
  }

  save(){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    let request: any = {}
    request.roomModels = this.listRoom;
    request.idsDel = this.datasDeleted.join(",");
    this.edu0103Service.save(request).subscribe(
      (res: any) => {
        if(res[CommonConstant.STATUS_KEY] == CommonConstant.RESULT_OK){
          this.toastr.success(this.trans.instant('message.success.saveSuccess'));
          //updateActive();
          this.onSearch();
        }else{
          this.toastr.error(res[CommonConstant.MESSAGES_KEY]);
        }
        dialogSpinner.close()
      },
      err => {
        this.toastr.error(this.trans.instant('message.error.saveFailed'));
        dialogSpinner.close()
      }
    )
  }

  onDelete(){
    // if (!this.selectedRow.key) {
    //   this.toastr.error(this.trans.instant('apv.error.needSelectParent'))
    //   return
    // }
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'fa fa-trash',
      accept: () => {
        this.delete();
        this.onSearch();
      }
    })
  }

  delete() {
    for (var i = 0; i < this.listRoom.length; i++) {
      if (this.listRoom[i].key == this.selectedRow.key) {
        this.listRoom.splice(i, 1);
        this.datasDeleted.push(this.selectedRow.key);
      }
    }
    this.listRoom.splice
  }

  add(){
    this.listRoom.unshift({
      roomNm: '',
      key: '',
      status: 'false',
      checked: false
    });
  }

  changedRow(rowIndex: any){
    this.listRoom[rowIndex].state = 'U';
  }

  updateActive(rowIndex: any){
    this.listRoom[rowIndex].state = 'U';
    this.listRoom[rowIndex].status = this.listRoom[rowIndex].status == 'true' ? 'false' : 'true';
    this.listRoom[rowIndex].checked = !this.listRoom[rowIndex].checked;
  }

  onSearch(){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.edu0103Service.search(this.pageRequest).subscribe((response) => {
      this.listRoom = response[CommonConstant.LIST_KEY];
      this.totalRoom = response[CommonConstant.COUNT_ITEMS_KEY];
      for(let i =0; i< this.listRoom.length;i++){
        if(this.listRoom[i].status){
          this.listRoom[i].checked = true;
        }
      }
      CommonUtil.addIndexForListReverse(this.listRoom, this.pageRequest.page, this.totalRoom);
      dialogSpinner.close();
    });
  }

  onSearchReset() {
    this.pageRequest.searchText = '';
    this.datasDeleted = [];
    this.onSearch();
  }

  initData() {
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
  }


  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.onSearch();
  }

  changeFieldList(list:any,check:boolean){
    list.forEach((element:any) => {
      element.check = check;
    });
  }

  checkRoom(room : any){
    return room.check?true: false ;
  }

  handleListCheked(room : any){
    if(this.listChecked.includes(room)){
      var index = this.listChecked.indexOf(room);
      if (index > -1) {
        room.check = false ;
        this.listChecked.splice(index, 1);
      }
    }else{
      room.check = true ;
      this.listChecked.push(room);
    }
  }

  handleMultiSelect(event : any){
    let isChecked =  event.currentTarget.checked;
    if(isChecked){
      let tempList =  this.listRoom.filter(item =>  !this.listChecked.includes(item))
      this.listChecked.push(...tempList);
      this.changeFieldList(this.listChecked,true);
      // console.log(this.listChecked);
    }else{
      this.changeFieldList(this.listChecked,false);
      this.listChecked =  this.listChecked.filter(item => !this.listRoom.includes(item));
      this.changeFieldList(this.listChecked,true);
    }
  }

  showRequieq(){
    let mes = this.trans.instant('edu.edu0202.requiredChoose')
    this.confirmationService.confirm({
      header: mes,
      acceptLabel: this.trans.instant('button.confirm.title'),
      accept: () => {
        // tabNumber=1;
      },

    });
  }

  deletelistCheck(){
    if(this.listChecked.length==0){
      this.showRequieq();
      return ;
    }
    this.deleteListcheckPopup();
  }

  deleteListcheckPopup(){
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu0103Service.deleteListCheck(this.listChecked).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              // this.edu0101Service.changeData(this.candidate);
              this.toastr.success(
                this.trans.instant('message.success.deleteSuccess')
              );
              this.onSearch ();
            } else {
              this.toastr.error(
                this.trans.instant('message.error.deleteFailed')
              );
            }
          },
          error: () => {
            this.toastr.error(this.trans.instant('message.error.deleteFailed'));
          },
        });
      },
      reject: () => {},
    });
  }

}
