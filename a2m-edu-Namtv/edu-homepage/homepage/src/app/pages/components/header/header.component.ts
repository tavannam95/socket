import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { I18nConfig, LanguageItem } from 'src/app/constants/i18n.config';
import { I18nService } from 'src/app/services/i18n.service';
import {Menu} from "src/app/constants/menu";
import {CommonService} from "../../../services/common.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  languages: LanguageItem[] = I18nConfig.getLanguages();
  langKey?: any;
  langName?:any;
  langIcon: any;
  menus: any[] = Menu.display_menus;
  categories: any[] = [];

  constructor(
    private i18nService: I18nService,
    private translate: TranslateService,
    private commonService: CommonService
  ) { }

  ngOnInit(): void {
    this.langKey = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.langKey){
      this.langKey = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.setLanguage(this.langKey);
    this.getCategories();
  }

  onChangeLang(lang: any){
    this.langName = lang.name;
    this.langIcon = lang.icon;
    this.translate.use(lang.key);
    this.i18nService.language.emit(lang.key);
    localStorage.setItem(I18nConfig.STORAGE_KEY, lang.key);

  }

  setLanguage(language: any){
    this.languages.forEach(item=>{
      if (item.key == language){
        this.langName = item.name;
        this.langIcon = item.icon;
      }
    });
  }

  private getCategories() {
    this.categories = this.commonService.getCategories();
  }
}
