/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PipeModule } from 'src/app/shared/pipe.module';
import { DirectiveModule } from 'src/app/shared/directive.module';
import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { DatiProgettoComponent } from './components/dati-progetto/dati-progetto.component';
import { DatiProgettoSediDialogComponent } from './components/dati-progetto-sedi-dialog/dati-progetto-sedi-dialog.component';
import { DatiProgettoSoggettoComponent } from './components/dati-progetto-soggetto/dati-progetto-soggetto.component';
import { DatiProgettoService } from './services/dati-progetto.service';

import { ArchivioFileModule, DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule, HelpModule as HelpLibModule } from '@pbandi/common-lib';
import { DisattivaSoggettoDialogComponent } from './components/disattiva-soggetto-dialog/disattiva-soggetto-dialog.component';
import { MappeService } from './services/mappe.service';
import { DatiGeneraliComponent } from './components/dati-generali/dati-generali.component';
import { AllegatiComponent } from './components/allegati/allegati.component';
import { DatiBenefSediComponent } from './components/dati-benef-sedi/dati-benef-sedi.component';
import { SoggettiComponent } from './components/soggetti/soggetti.component';
import { BlocchiComponent } from './components/blocchi/blocchi.component';
import { DatiAggiuntiviComponent } from './components/dati-aggiuntivi/dati-aggiuntivi.component';
import { DatiSifComponent } from './components/dati-sif/dati-sif.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        AppRoutingModule,
        PipeModule,
        DirectiveModule,
        VisualizzaContoEconomicoModule,
        DatiProgettoAttivitaPregresseModule,
        ArchivioFileModule,
        HelpLibModule
    ],
    exports: [
        DatiProgettoComponent,
        DatiProgettoSediDialogComponent,
        DatiProgettoSoggettoComponent,
        DisattivaSoggettoDialogComponent
    ],
    declarations: [
        DatiProgettoComponent,
        DatiProgettoSediDialogComponent,
        DatiProgettoSoggettoComponent,
        DisattivaSoggettoDialogComponent,
        DatiGeneraliComponent,
        AllegatiComponent,
        DatiBenefSediComponent,
        SoggettiComponent,
        BlocchiComponent,
        DatiAggiuntiviComponent,
        DatiSifComponent,
    ],
    entryComponents: [
    ],
    providers: [
        DatiProgettoService,
        MappeService
    ]
})
export class DatiProgettoModule { }
