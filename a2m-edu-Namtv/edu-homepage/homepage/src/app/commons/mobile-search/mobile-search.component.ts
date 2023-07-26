import { Component, Input, OnInit, TemplateRef } from '@angular/core';

@Component({
  selector: 'app-mobile-search',
  templateUrl: './mobile-search.component.html',
  styleUrls: ['./mobile-search.component.css']
})
export class MobileSearchComponent implements OnInit {
  @Input() searchId: any = ""; 
  @Input() searchForm!: TemplateRef<any>;
  active = false;
  constructor() { }

  ngOnInit(): void {
  }

  close(){
    this.active = false;
  }

  open(){
    this.active = true;
  }
}
