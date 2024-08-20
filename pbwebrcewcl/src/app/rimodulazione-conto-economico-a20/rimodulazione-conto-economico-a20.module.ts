/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';
import { PipeModule } from '../shared/pipe.module';
import { RimodulazioneContoEconomicoA20Component } from './components/rimodulazione-conto-economico-a20/rimodulazione-conto-economico-a20.component';
import { ContoEconomicoWaModule } from '../conto-economico-wa/conto-economico-wa.module';
import { RimodulazioneContoEconomicoA20Service } from './services/rimodulazione-conto-economico-a20.service';
import { ConcludiRimodulazioneA20Component } from './components/concludi-rimodulazione-a20/concludi-rimodulazione-a20.component';
import { SpecchiettoSaldoComponent } from './components/specchietto-saldo/specchietto-saldo.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        DatiProgettoAttivitaPregresseModule,
        PipeModule,
        ContoEconomicoWaModule
    ],
    exports: [
        RimodulazioneContoEconomicoA20Component,
        ConcludiRimodulazioneA20Component,
    ],
    declarations: [
        RimodulazioneContoEconomicoA20Component,
        ConcludiRimodulazioneA20Component,
        SpecchiettoSaldoComponent,
    ],
    entryComponents: [
    ],
    providers: [
        RimodulazioneContoEconomicoA20Service
    ]
})
export class RimodulazioneContoEconomicoA20Module { }