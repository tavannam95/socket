import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { PaginationConfig } from 'src/app/config/pagination.config';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css']
})
export class PaginatorComponent implements OnInit {
  @Input() totalRecords: any = 0;
  @Output() onPageChange = new EventEmitter
  pageLinkSize = PaginationConfig.PAGE_LINK_SIZE;
  rows = PaginationConfig.PAGE_SIZE
  constructor() { }

  ngOnInit(): void {
    
  }

  onChange(event: any){
    this.onPageChange.emit(event);
  }

}
