import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { PaginationConfig } from 'src/app/config/pagination.config';
import { Gen0601Service } from 'src/app/services/gen/gen0601.service';
import { ExtendsComponent } from './components/extends/extends.component';

@Component({
  selector: 'app-gen0601',
  templateUrl: './gen0601.component.html',
  styleUrls: ['./gen0601.component.css']
})
export class Gen0601Component implements OnInit {
  request: any = {};
  statusList: any[] = [];
  subInfoList: any[] = [];
  totalRecords: any = 0;
  listStatus: any[] = [
    { label: this.trans.instant('sys.sys0101.status.all'), value: null },
    { label: this.trans.instant('common.status.unpaid'), value: "05-01" },
    { label: this.trans.instant('common.status.paid'), value: "05-02" },
    { label: this.trans.instant('common.status.expired'), value: "05-03" }
  ];
  constructor(
    private gen0601Service: Gen0601Service,
    private toastr: ToastrService,
    private trans: TranslateService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.initData();
    this.search();
  }

  initData(){
    this.request.size = PaginationConfig.PAGE_SIZE;
    this.request.page = 0;
    this.request.offset = 0;
  }

  onChangePagi(event: any){
    this.request.offset = event.page * PaginationConfig.PAGE_SIZE;
    this.request.page = event.page;
    this.search();
  }

  search(){
    this.gen0601Service.search(this.request).subscribe({
      next: res => {
        if (res){
          this.totalRecords = res.totalRecords;
          this.subInfoList = this.addIndexForList(res.data); 
      
        }      
      },
      error: err => {

      }
    })
  }

  onSearchReset(){
    this.request = {};
    this.initData();
    this.search();
  }

  onExtend(data: any){
    const dialogRef = this.dialog.open(ExtendsComponent, {
      width: '0px',
      height: '0px',
      data: {
        license: data,
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result){
        
      }
    });
  }

  onUnsubscribe(data: any){

  }

  addIndexForList(data: any[]){
    let index = this.totalRecords - (this.request.page * PaginationConfig.PAGE_SIZE);
    data.forEach(ele =>{
      ele.index = index;
      index --;
    }); 
    return data;
  }
}
