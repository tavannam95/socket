import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit, OnChanges {

  @Input() totalRecords = 0;
  @Output() onChange = new EventEmitter();
  @Input() rows: number = 10;
  currentPage: number = 0;
  event: any = {
    page: 0
  }
  totaPages: number = 0;
  constructor() { }
  
  ngOnChanges(): void {
    this.totaPages = Math.ceil(this.totalRecords / this.rows);
    this.event.rows = this.rows;
  }

  ngOnInit(): void {
  }

  onPrevious(){
    if (this.currentPage <= 0) return;
    this.currentPage -= 1; 
    this.onPagi(this.currentPage);
  }

  onPagi(page: any){
    this.currentPage = page;
    this.event.page = page;
    this.onChange.emit(this.event);
  }

  onNext(){
    if (this.currentPage >= this.totaPages - 1) return;
    this.currentPage += 1; 
    this.onPagi(this.currentPage);
  }
}
