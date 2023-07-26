import { I18nConfig } from './constants/i18n.config';
import { TranslateService } from '@ngx-translate/core';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { LazyLoadScriptService } from './services/lazy-load-script.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {
  title = 'AtwoM Education Center';

  constructor(
    private lazyLoadService: LazyLoadScriptService,
    private translate: TranslateService
  ){
    let lang = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!lang) {
        lang = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.translate.setDefaultLang(lang);
    this.translate.use(lang);
  }

  ngAfterViewInit(): void {
    // this.lazyLoadService.loadScript();
  }

  ngOnInit(): void {
  }

}
