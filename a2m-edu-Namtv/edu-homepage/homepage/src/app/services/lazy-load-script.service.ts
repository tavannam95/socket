import { Injectable, Inject } from '@angular/core';
import { ReplaySubject, Observable } from 'rxjs';
import { DOCUMENT } from '@angular/common';
declare var $: any;
@Injectable()
export class LazyLoadScriptService {

    private scripts = [
        { id: 'appJsId', url : 'assets/js/app.js' }
    ];

    constructor(@Inject(DOCUMENT) private readonly document: Document) { }

    private initScript(id: string,url: string) {
        $('#'+ id).remove();
        const body = <HTMLDivElement> document.body;
        const script = document.createElement('script');
        script.innerHTML = '';
        script.src = url;
        script.async = false;
        script.defer = true;
        script.id = id;
        body.appendChild(script);
    }

    loadScript(){
        this.scripts.forEach((ele: any) =>{
            this.initScript(ele.id,ele.url);
        });
    }

    loadScriptBackToTop(){
        this.initScript("backToTopId", "assets/js/vendor/backtotop.min.js");
    }
}