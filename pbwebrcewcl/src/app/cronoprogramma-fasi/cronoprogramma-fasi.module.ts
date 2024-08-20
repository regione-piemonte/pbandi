/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule, CronoprogrammaFasiModule as CronoprogrammaFasiLibModule } from '@pbandi/common-lib';
import { CronoprogrammaFasiContainerComponent } from './components/cronoprogramma-fasi-container/cronoprogramma-fasi-container.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        DatiProgettoAttivitaPregresseModule,
        VisualizzaContoEconomicoModule,
        CronoprogrammaFasiLibModule
    ],
    exports: [
    ],
    declarations: [
        CronoprogrammaFasiContainerComponent,

    ],
    entryComponents: [
    ],
    providers: [

    ]
})
export class CronoprogrammaFasiModule { }
