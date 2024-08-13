/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { GestioneSpesaValidataComponent } from './components/gestione-spesa-validata/gestione-spesa-validata.component';
import { RettificaDocumentoDiSpesaComponent } from './components/rettifica-documento-di-spesa/rettifica-documento-di-spesa.component';
import { GestioneSpesaValidataService } from './services/gestione-spesa-validata.service';
import { DichiarazioniDialogComponent } from './components/dichiarazioni-dialog/dichiarazioni-dialog.component';
import { NavigationGestioneSpesaValidataService } from './services/navigation-gestione-spesa-validata.service';
import { AllegatiDialogComponent } from './components/allegati-dialog/allegati-dialog.component';
import { DettaglioRettificheDialogComponent } from './components/dettaglio-rettifiche-dialog/dettaglio-rettifiche-dialog.component';
import { GestioneChecklistSpesaValidataComponent } from './components/gestione-checklist-spesa-validata/gestione-checklist-spesa-validata.component';
import { ArchivioFileModule as ArchivioFileLibModule, CoreModule as CoreLibModule, DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule } from '@pbandi/common-lib';
import { ChecklistModule } from '../checklist/checklist.module';
import { ValidazioneModule } from '../validazione/validazione.module';
import { RilievoDialogComponent } from './components/rilievo-dialog/rilievo-dialog.component';


@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
    ArchivioFileLibModule,
    CoreLibModule,
    VisualizzaContoEconomicoModule,
    ChecklistModule,
    DatiProgettoAttivitaPregresseModule,
    ValidazioneModule
  ],
  exports: [
    GestioneSpesaValidataComponent,
    RettificaDocumentoDiSpesaComponent,
    DichiarazioniDialogComponent,
    AllegatiDialogComponent,
    DettaglioRettificheDialogComponent,
    GestioneChecklistSpesaValidataComponent
  ],
  declarations: [
    GestioneSpesaValidataComponent,
    RettificaDocumentoDiSpesaComponent,
    DichiarazioniDialogComponent,
    AllegatiDialogComponent,
    DettaglioRettificheDialogComponent,
    GestioneChecklistSpesaValidataComponent,
    RilievoDialogComponent
  ],
  entryComponents: [
  ],
  providers: [
    GestioneSpesaValidataService,
    NavigationGestioneSpesaValidataService
  ]
})
export class GestioneSpesaValidataModule { }
