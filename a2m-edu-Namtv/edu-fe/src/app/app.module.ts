import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Sys0101Service } from './services/sys/sys0101.service';
import { I18nService } from './services/i18n.service';
import { DatePipe } from '@angular/common';
import { Sys0102Service } from './services/sys/sys0102.service';
import { FormsModule } from '@angular/forms';

import { AuthGuard } from './guard/auth.guard';
import { CommonService } from './services/common/common.service';
import { AuthenticationService } from './services/common/authentication.service';
import { RoleGuard } from './guard/role.guard';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { Sys0103Service } from './services/sys/sys0103.service';
import { HttpErrorInterceptor } from './auth/interceptor/http-error.interceptor';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ToastrModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    FontAwesomeModule,
  ],
  providers: [
    Sys0101Service,
    Sys0102Service,
    Sys0103Service,
    I18nService,
    HttpClient,
    TranslateService,
    DatePipe,
    AuthGuard,
    CommonService,
    AuthenticationService,
    RoleGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
