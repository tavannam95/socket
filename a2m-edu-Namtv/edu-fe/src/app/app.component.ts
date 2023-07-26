import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { PrimeNGConfig } from 'primeng/api';
import { I18nConfig } from './config/i18n.config';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'DATA GENERATION';

  constructor(private translate: TranslateService,private config: PrimeNGConfig) {

    let lang = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!lang) {
        lang = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.translate.setDefaultLang(lang);
    this.translate.use(lang);
    this.translate.get("primengConfig").subscribe(res=> {
      this.config.setTranslation(res);
    });
  }
}
