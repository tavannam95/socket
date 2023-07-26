import { LazyLoadScriptService } from './../../../services/lazy-load-script.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-students0102',
  templateUrl: './students0102.component.html',
  styleUrls: ['./students0102.component.css']
})
export class Students0102Component implements OnInit, AfterViewInit {

  constructor(private loadScriptService: LazyLoadScriptService) { }

  ngAfterViewInit(): void {
    this.loadScriptService.loadScript();
  }

  ngOnInit(): void {
  }

}