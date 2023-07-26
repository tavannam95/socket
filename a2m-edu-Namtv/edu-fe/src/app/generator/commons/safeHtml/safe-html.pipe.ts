import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'safeHtml'
})
export class SafeHtmlPipe implements PipeTransform {
  constructor(private sanitized: DomSanitizer) {}

  //demo: <div class="text-dark text-break" [innerHTML]="post.postContent | safeHtml">
  transform(value: any): unknown {
    return this.sanitized.bypassSecurityTrustHtml(value);
  }
}
