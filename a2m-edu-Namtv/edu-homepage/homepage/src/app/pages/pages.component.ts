import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { LazyLoadScriptService } from '../services/lazy-load-script.service';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.css']
})
export class PagesComponent implements OnInit, AfterViewInit {

  constructor(
    private router: Router,
    private loadScriptService: LazyLoadScriptService,
  ) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    // this.loadScriptService.loadScript();
    this.loadScriptService.loadScriptBackToTop();
  }

  goToRegis(){
    // let a = document.createElement('a');
    // a.href = '/apply';
    // a.click();

    this.router.navigate(['/apply'], {
      queryParams: {},
      // state: { searchRequest: $.extend(true, {}, this.request) },
    });
  }
}
