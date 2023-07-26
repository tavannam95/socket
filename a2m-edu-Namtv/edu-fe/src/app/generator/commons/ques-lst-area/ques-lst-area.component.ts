import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-ques-lst-area',
  templateUrl: './ques-lst-area.component.html',
  styleUrls: ['./ques-lst-area.component.css']
})
export class QuesLstAreaComponent implements OnInit,OnChanges {
  @Input() item : any;
  @Input() currentQues : any;
  @Input() submitInput : any;
  @Output() onPageChange = new EventEmitter();
  list : any;
  quesCurrentItem : any;
  submit = false;
  lst = [{id: 1},{id: 2},{id: 3},{id: 4},{id: 5},{id: 6},{id: 7},{id: 8},{id: 9}]
  lstFilter:any = [];
  currentPage : any = 0;
  lastPage = 0;
  pageSize : any = 5;
  idx = 0;
  quesIndexCurrent : any = 0;
  minIndex :any = 0;
  maxIndex :any = 0;

  constructor() { }
  ngOnChanges(changes: SimpleChanges): void {
    this.list = this.item;
    for(let i = 0; i < this.list.length;i++){
      this.list[i]['index'] = i;
    }
    this.submit = this.submitInput;
    this.minIndex = this.currentPage * this.pageSize;
    this.maxIndex = this.minIndex + this.pageSize;

    this.list.forEach((element:any, index : any) => {
      element['idx'] = index + 1;
    });
    //this.lastPage = Math.floor(this.lst.length / this.pageSize);
    this.lstFilter = this.list.filter((item : any, index : any, arr : any) => {
      return index >= this.minIndex && index < this.maxIndex;
    });
    this.quesCurrentItem = this.currentQues;
    this.quesIndexCurrent = this.quesCurrentItem.index;
    if(this.quesIndexCurrent >= this.maxIndex){
      this.nextIdx();
    }
    if(this.quesIndexCurrent < this.minIndex){
      this.prevIdx();
    }
    // if(this.idx != null){
    //   this.currentPage = this.idx / this.pageSize;
    //   this.minIndex = (this.currentPage) * this.pageSize;
    //   this.maxIndex = this.minIndex + this.pageSize;
    //   this.lstFilter = this.list.filter((item : any, index : any, arr : any) => {
    //     return index >= 0 && index >= this.minIndex && index < this.maxIndex;
    //   });
    // }
  }
  ngOnInit(): void {
  }

  goQuestion(index : any){
    this.onPageChange.emit(index);
    this.quesIndexCurrent = index

  }

  nextIdx(){
    if(this.maxIndex <= this.list.length){
      this.currentPage += 1;
      this.minIndex = (this.currentPage) * this.pageSize;
      this.maxIndex = this.minIndex + this.pageSize;
      if(this.minIndex < this.list.length){
        this.lstFilter = this.list.filter((item : any, index : any, arr : any) => {
          return index >= this.minIndex && index < this.maxIndex;
        });
    }
    }

  }

  prevIdx(){
    if(this.currentPage > 0){
      this.currentPage -= 1;
      this.minIndex = (this.currentPage) * this.pageSize;
      this.maxIndex = this.minIndex + this.pageSize;
      this.lstFilter = this.list.filter((item : any, index : any, arr : any) => {
        return index >= 0 && index >= this.minIndex && index < this.maxIndex;
      });
    }
  }

  firstIndex(){
    this.currentPage = 0;
    this.minIndex = 0;
    this.maxIndex = this.minIndex + this.pageSize;
    if(this.minIndex < this.list.length){
      this.lstFilter = this.list.filter((item : any, index : any, arr : any) => {
        return index >= this.minIndex && index < this.maxIndex;
      });
    }
  }
  lastIndex(){
    const pageSizes = this.list.length % this.pageSize;
    if(pageSizes != 0){
      this.currentPage = Math.floor(this.list.length / this.pageSize);
    }else{
      this.currentPage = Math.floor(this.list.length / this.pageSize) - 1;
    }
    if(this.maxIndex <= this.list.length){
      this.minIndex = (this.currentPage) * this.pageSize;
      this.maxIndex = this.list.length;
      if(this.minIndex < this.list.length){
        this.lstFilter = this.list.filter((item : any, index : any, arr : any) => {
          return index >= 0 && index >= this.minIndex && index < this.maxIndex;
        });
    }
    }

  }
}
