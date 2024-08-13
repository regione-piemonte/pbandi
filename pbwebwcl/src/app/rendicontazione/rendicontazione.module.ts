/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SharedModule } from './../shared/shared.module';
import { NuovaQualificaDialogComponent } from './components/nuova-qualifica-dialog/nuova-qualifica-dialog.component';
import { RendicontazioneComponent } from './components/rendicontazione/rendicontazione.component';
import { MaterialModule } from './../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { GestioneFornitoriComponent } from './components/gestione-fornitori/gestione-fornitori.component';
import { DocumentoDiSpesaComponent } from './components/documento-di-spesa/documento-di-spesa.component';
import { RendicontazioneService } from './services/rendicontazione.service';
import { AssociaAffidamentoDialogComponent } from './components/associa-affidamento-dialog/associa-affidamento-dialog.component';
import { NuovoModicaFornitoreDialogComponent } from './components/nuovo-modica-fornitore-dialog/nuovo-modica-fornitore-dialog.component';
import { FornitoreService } from './services/fornitore.service';
import { NuovoModificaVoceSpesaDialogComponent } from './components/nuovo-modifica-voce-spesa-dialog/nuovo-modifica-voce-spesa-dialog.component';
import { DocumentoDiSpesaService } from './services/documento-di-spesa.service';
import { NuovoModificaQuietanzaDialogComponent } from './components/nuovo-modifica-quietanza-dialog/nuovo-modifica-quietanza-dialog.component';
import { NavigationRendicontazioneService } from './services/navigation-rendicontazione.service';
import { AssociaDocumentoProgettoDialogComponent } from './components/associa-documento-progetto-dialog/associa-documento-progetto-dialog.component';
import { FormNuovoModificaFornitoreComponent } from './components/form-nuovo-modifica-fornitore/form-nuovo-modifica-fornitore.component';
import { DichiarazioneDiSpesaComponent } from './components/dichiarazione-di-spesa/dichiarazione-di-spesa.component';
import { DichiarazioneDiSpesaService } from './services/dichiarazione-di-spesa.service';
import { NavigationDichiarazioneDiSpesaService } from './services/navigation-dichiarazione-di-spesa.service';
import { DichiarazioneDiSpesaContentComponent } from './components/dichiarazione-di-spesa-content/dichiarazione-di-spesa-content.component';
import { InvioDichiarazioneDiSpesaComponent } from './components/invio-dichiarazione-di-spesa/invio-dichiarazione-di-spesa.component';
import { ArchivioFileModule as ArchivioFileLibModule, CoreModule as CoreLibModule, DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule, HelpModule as HelpLibModule } from '@pbandi/common-lib';
import { IndicatoriModule } from '../indicatori/indicatori.module';
import { CronoprogrammaModule } from '../cronoprogramma/cronoprogramma.module';
import { ExcelRendicontazioneService } from './services/excel-rendicontazione.service';
import { DeclaratoriaModule } from '../declaratoria/declaratoria.module';
import { SpecchiettoSaldoComponent } from '../rendicontazione-a20/components/rendicontazione-a20/specchietto-saldo/specchietto-saldo.component';
import { RendicontazioneA20Component } from '../rendicontazione-a20/components/rendicontazione-a20/rendicontazione-a20.component';
import { RendicontazioneA20Module } from '../rendicontazione-a20/rendicontazione-a20.module';


@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
    ArchivioFileLibModule,
    CoreLibModule,
    IndicatoriModule,
    CronoprogrammaModule,
    DeclaratoriaModule,
    VisualizzaContoEconomicoModule,
    DatiProgettoAttivitaPregresseModule,
    HelpLibModule,
    RendicontazioneA20Module
  ],
  exports: [
    RendicontazioneComponent,
    GestioneFornitoriComponent,
    DocumentoDiSpesaComponent,
    AssociaAffidamentoDialogComponent,
    NuovaQualificaDialogComponent,
    NuovoModicaFornitoreDialogComponent,
    NuovoModificaVoceSpesaDialogComponent,
    NuovoModificaQuietanzaDialogComponent,
    AssociaDocumentoProgettoDialogComponent,
    FormNuovoModificaFornitoreComponent,
    DichiarazioneDiSpesaComponent,
    DichiarazioneDiSpesaContentComponent,
    InvioDichiarazioneDiSpesaComponent
  ],
  declarations: [
    RendicontazioneComponent,
    GestioneFornitoriComponent,
    DocumentoDiSpesaComponent,
    AssociaAffidamentoDialogComponent,
    NuovaQualificaDialogComponent,
    NuovoModicaFornitoreDialogComponent,
    NuovoModificaVoceSpesaDialogComponent,
    NuovoModificaQuietanzaDialogComponent,
    AssociaDocumentoProgettoDialogComponent,
    FormNuovoModificaFornitoreComponent,
    DichiarazioneDiSpesaComponent,
    DichiarazioneDiSpesaContentComponent,
    InvioDichiarazioneDiSpesaComponent
  ],
  entryComponents: [
  ],
  providers: [
    RendicontazioneService,
    FornitoreService,
    DocumentoDiSpesaService,
    NavigationRendicontazioneService,
    DichiarazioneDiSpesaService,
    NavigationDichiarazioneDiSpesaService,
    DatePipe,
    ExcelRendicontazioneService
  ]
})
export class RendicontazioneModule { }
