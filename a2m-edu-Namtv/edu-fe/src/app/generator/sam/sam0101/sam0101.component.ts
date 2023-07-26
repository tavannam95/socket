import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sam0101',
  templateUrl: './sam0101.component.html',
  styleUrls: ['./sam0101.component.css']
})
export class Sam0101Component implements OnInit {

  dateValue: any;
  cities: any[] = [];
  selectedCities3: any[] = [];

  selectedCityCode!: string;

  request: any = {};

  submitted: any = false;

  constructor() { }

  ngOnInit(): void {
    this.cities = [
      {name: 'All', code: null},
      {name: 'New York', code: 'NY'},
      {name: 'Rome', code: 'RM'},
      {name: 'London', code: 'LDN'},
      {name: 'Istanbul', code: 'IST'},
      {name: 'Paris', code: 'PRS'}
    ];
  }


  onSubmit(invalid: any){
    if (invalid){
      this.submitted = true;
      return;
    }
  }
}
