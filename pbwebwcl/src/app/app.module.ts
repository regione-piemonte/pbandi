/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './app-material.module';
import { HttpClientModule } from '@angular/common/http'
import { FormsModule } from '@angular/forms';
import { RendicontazioneModule } from './rendicontazione/rendicontazione.module';
import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { ChecklistModule } from './checklist/checklist.module';
import { ValidazioneModule } from './validazione/validazione.module';
import { GestioneSpesaValidataModule } from './gestione-spesa-validata/gestione-spesa-validata.module';
import { DocumentiProgettoModule } from './documenti-progetto/documenti-progetto.module';
import { IntegrazioneRendicontazioneModule } from './integrazione-rendicontazione/integrazione-rendicontazione.module';
import {
  ArchivioFileModule as ArchivioFileLibModule, CoreModule as CoreLibModule, DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule,
  CronoprogrammaFasiModule as CronoprogrammaFasiLibModule, HelpModule as HelpLibModule
} from '@pbandi/common-lib';
import { ArchivioFileContainerModule } from './archivio-file-container/archivio-file-container.module';
import { GestioneEconomieModule } from './gestione-economie/gestione-economie.module';
import { IndicatoriModule } from './indicatori/indicatori.module';
import { CronoprogrammaModule } from './cronoprogramma/cronoprogramma.module';
import { QuillModule } from 'ngx-quill';
import { GestioneIntegrazioniModule } from './gestione-integrazioni/gestione-integrazioni.module';
import { ChecklistAreaControlliModule } from './checklist-area-controlli/checklist-area-controlli.module';
import { MatMomentDateModule } from "@angular/material-moment-adapter"
import { RendicontazioneA20Module } from './rendicontazione-a20/rendicontazione-a20.module';
import { DeclaratoriaModule } from './declaratoria/declaratoria.module';
import { RelazioneTecnicaModule } from './relazione-tecnica/relazione-tecnica.module';
import { ValidazioneRelazioneTecnicaModule } from './validazione-relazione-tecnica/validazione-relazione-tecnica.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    RendicontazioneModule,
    RendicontazioneA20Module,
    SharedModule,
    CoreModule,
    ArchivioFileContainerModule,
    ValidazioneModule,
    GestioneSpesaValidataModule,
    DocumentiProgettoModule,
    IntegrazioneRendicontazioneModule,
    ChecklistModule,
    ArchivioFileLibModule,
    CoreLibModule,
    GestioneEconomieModule,
    IndicatoriModule,
    CronoprogrammaModule,
    DeclaratoriaModule,
    VisualizzaContoEconomicoModule,
    DatiProgettoAttivitaPregresseModule,
    GestioneIntegrazioniModule,
    CronoprogrammaFasiLibModule,
    QuillModule.forRoot(),
    HelpLibModule,
    ChecklistAreaControlliModule,
    MatMomentDateModule,
    RelazioneTecnicaModule,
    ValidazioneRelazioneTecnicaModule
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
