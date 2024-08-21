/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { CronoprogrammaFasiService } from './services/cronoprogramma-fasi.service';
import { CronoprogrammaFasiComponent } from './components/cronoprogramma-fasi/cronoprogramma-fasi.component';
import { ArchivioFileModule } from '../archivioFile/archivio-file.module';
import { DatiProgettoAttivitaPregresseModule } from '../dati-progetto-attivita-pregresse/dati-progetto-attivita-pregresse.module';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        DatiProgettoAttivitaPregresseModule,
        ArchivioFileModule
    ],
    exports: [
        CronoprogrammaFasiComponent,
    ],
    declarations: [
        CronoprogrammaFasiComponent,

    ],
    entryComponents: [
    ],
    providers: [
        CronoprogrammaFasiService
    ]
})
export class CronoprogrammaFasiModule { }
