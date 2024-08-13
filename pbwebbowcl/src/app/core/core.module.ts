/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpClientXsrfModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { TimeoutInterceptor } from './interceptor/httpRequestInterceptor';
import { XsrfInterceptor } from './interceptor/httpXSRFInterceptor';
import { ConfigService } from './services/config.service';
import { HandleExceptionService } from './services/handle-exception.service';
import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { ErrorRoutingComponent } from './components/error-routing/error-routing.component';
import { MaterialModule } from '../app-material.module';
import { UserService } from './services/user.service';
registerLocaleData(localeIt, 'it');

@NgModule({
    imports: [
        CommonModule,
        HttpClientModule,
        HttpClientXsrfModule.withOptions({ cookieName: 'XSRF-TOKEN-COOKIE-WEBBO', headerName: 'X-XSRF-TOKEN-WEBBO', }),
        MaterialModule
    ],
    exports: [
        ErrorRoutingComponent
    ],
    declarations: [
        ErrorRoutingComponent
    ],
    entryComponents: [
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: TimeoutInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: XsrfInterceptor, multi: true },
        ConfigService,
        HandleExceptionService,
        UserService
    ]
})
export class CoreModule { }