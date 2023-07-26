import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-iqTest-statistical-result',
  templateUrl: './iqTest-statistical-result.component.html',
  styleUrls: ['./iqTest-statistical-result.component.css']
})
export class IqtestStatisticalResultComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  changeTab(tabNumber: any){
    const ele = document.getElementById('iqtestStatistical'+tabNumber);

      ele?.click();
  }
}
