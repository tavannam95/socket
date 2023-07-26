import { LazyLoadScriptService } from './../../services/lazy-load-script.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit, AfterViewInit {

  constructor(private loadScriptService: LazyLoadScriptService) { }


  ngAfterViewInit(): void {
    this.loadScriptService.loadScript();
  }

  ngOnInit(): void {
  }

}
