import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router, RoutesRecognized} from '@angular/router';
import {CommonService} from "../../../services/common/common.service";
import {I18nConfig} from "../../../config/i18n.config";
import {I18nService} from "../../../services/i18n.service";


@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.css']
})
export class BreadcrumbComponent implements OnInit {
  private history = [];
  private menu = [];
  private url = '';
  public currentMenu: any;
  public parentMenu: any;
  language: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private i18nService: I18nService,
              private commonService: CommonService) {

    router.events.subscribe((routerEvent) => {
      if (routerEvent instanceof NavigationEnd) {
        this.url = router.url;
        // parse the id from the url here e.g. routerEvent.split('/')[2]
      }
    });
    this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });
  }

  ngOnInit(): void {
    this.commonService.currentDataFull.subscribe(data => {
      this.menu = data;
      if (!this.menu[0]) return;
      this.menu.forEach((each, index) => {
        if (each['urlPath'] == this.url) {
          this.currentMenu = each;
          this.menu.forEach((each2, index) => {
            if (each2['menuId'] == each['upMenuId']) {
              this.parentMenu = each2;
              return;
            }
          })
          return;
        }
      })

    })

  }


}
