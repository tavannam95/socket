import {EventEmitter, Injectable} from '@angular/core';

@Injectable()
export class I18nService {

  language: EventEmitter<string> = new EventEmitter();

  constructor() { }

}
