/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from '../app-material.module';
import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { HandleExceptionService } from './services/handle-exception.service';
registerLocaleData(localeIt, 'it');

@NgModule({
    imports: [
        CommonModule,
        MaterialModule,
    ],
    exports: [

    ],
    declarations: [

    ],
    entryComponents: [
    ],
    providers: [
        HandleExceptionService
    ]
})
export class CoreModule { }