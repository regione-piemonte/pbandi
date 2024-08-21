/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { ArchivioFileModule } from '../archivioFile/archivio-file.module';
import { SharedModule } from '../shared/shared.module';
import { DatiProgettoAttivitaPregresseDialogComponent } from './components/dati-progetto-attivita-pregresse-dialog/dati-progetto-attivita-pregresse-dialog.component';
import { DatiProgettoAttivitaPregresseService } from './services/dati-progetto-attivita-pregresse.service';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        ArchivioFileModule
    ],
    exports: [
        DatiProgettoAttivitaPregresseDialogComponent
    ],
    declarations: [
        DatiProgettoAttivitaPregresseDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        DatiProgettoAttivitaPregresseService
    ]
})
export class DatiProgettoAttivitaPregresseModule { }