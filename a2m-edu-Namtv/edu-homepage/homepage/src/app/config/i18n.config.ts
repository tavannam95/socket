export class I18nConfig {

  public static readonly DEFAULT_LANGUAGE = 'kr';
  public static readonly STORAGE_KEY = 'language';

  public static getLanguages(): LanguageItem[] {
    return [
      {
        key: 'vi',
        name: 'Tiếng Việt',
        icon: '/assets/i18n/icon/vietnam.png'
      },
      {
        key: 'en',
        name: 'English',
        icon: '/assets/i18n/icon/kingdom.png'
      },
      {
        key: 'kr',
        name: '한국어',
        icon: '/assets/i18n/icon/south-korea.png'
      }
    ];
  }
}

export class LanguageItem {
  key?: string;
  name?: string;
  icon?: string;
}
