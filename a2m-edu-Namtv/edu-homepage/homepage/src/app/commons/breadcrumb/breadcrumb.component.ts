import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router, RouterModule} from "@angular/router";
import {Menu} from "../../constants/menu";
@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.css']
})
export class BreadcrumbComponent implements OnInit {

  url: any
  menus: any[] = Menu.display_menus;
  directArray: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private routers: RouterModule,
    private router: Router) {
    router.events.subscribe((routerEvent) => {
      if (routerEvent instanceof NavigationEnd) {
        this.url = router.url;
        // parse the id from the url here e.g. routerEvent.split('/')[2]
      }
    });

  }

  ngOnInit(): void {
    let found = false;
    this.url = this.url.split('?')[0];
    for(let each of this.menus){
      this.directArray = [];
      this.directArray.push(each);
      if(each.url == this.url){
        found = true;
        return;
      } else if(each.children) {
        for(let each2 of each.children){
          this.directArray.length = 1;
          this.directArray.push(each2);
          if(each2.url == this.url){
            found = true;
            return;
          }
        }
      }
    }
  }

}
