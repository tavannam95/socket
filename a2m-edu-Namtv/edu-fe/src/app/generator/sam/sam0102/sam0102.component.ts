import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sam0102',
  templateUrl: './sam0102.component.html',
  styleUrls: ['./sam0102.component.css']
})
export class Sam0102Component implements OnInit {
  request: any = {};
  statusList = [
    {label: "All", value: null},
    {label: "Active", value: 'Y'},
    {label: "Block", value: 'N'},
  ]
  constructor() { }

  ngOnInit(): void {
  }

  onPageChange(event: any){

  }

}
