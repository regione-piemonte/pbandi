/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { CronoprogrammaService } from './services/cronoprogramma.service';
import { CronoprogrammaComponent } from './components/cronoprogramma/cronoprogramma.component';
import { CronoprogrammaAvvioComponent } from './components/cronoprogramma-avvio/cronoprogramma-avvio.component';
import { DatiProgettoAttivitaPregresseModule, HelpModule as HelpLibModule } from '@pbandi/common-lib';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        DatiProgettoAttivitaPregresseModule,
        HelpLibModule
    ],
    exports: [
        CronoprogrammaComponent,
        CronoprogrammaAvvioComponent
    ],
    declarations: [
        CronoprogrammaComponent,
        CronoprogrammaAvvioComponent
    ],
    entryComponents: [
    ],
    providers: [
        CronoprogrammaService
    ]
})
export class CronoprogrammaModule { }
