import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HeadersUtil } from '../utils/headers.util';

@Injectable({
  providedIn: 'root',
})
export class AppJsService {
  constructor(@Inject(DOCUMENT) public document: any) {}

  w() {
    var e = this.document.documentElement.clientWidth;
    767 < e &&
      this.document.querySelector('.hamburger-icon').classList.toggle('open'),
      'horizontal' ===
        this.document.documentElement.getAttribute('data-layout') &&
        (this.document.body.classList.contains('menu')
          ? this.document.body.classList.remove('menu')
          : this.document.body.classList.add('menu')),
      'vertical' ===
        this.document.documentElement.getAttribute('data-layout') &&
        (e < 1025 && 767 < e
          ? (this.document.body.classList.remove('vertical-sidebar-enable'),
            'sm' ==
            this.document.documentElement.getAttribute('data-sidebar-size')
              ? this.document.documentElement.setAttribute(
                  'data-sidebar-size',
                  ''
                )
              : this.document.documentElement.setAttribute(
                  'data-sidebar-size',
                  'sm'
                ))
          : 1025 < e
          ? (this.document.body.classList.remove('vertical-sidebar-enable'),
            'lg' ==
            this.document.documentElement.getAttribute('data-sidebar-size')
              ? this.document.documentElement.setAttribute(
                  'data-sidebar-size',
                  'sm'
                )
              : this.document.documentElement.setAttribute(
                  'data-sidebar-size',
                  'lg'
                ))
          : e <= 767 &&
            (this.document.body.classList.add('vertical-sidebar-enable'),
            this.document.documentElement.setAttribute(
              'data-sidebar-size',
              'lg'
            ))),
      'twocolumn' ==
        this.document.documentElement.getAttribute('data-layout') &&
        (this.document.body.classList.contains('twocolumn-panel')
          ? this.document.body.classList.remove('twocolumn-panel')
          : this.document.body.classList.add('twocolumn-panel'));
  }
}
