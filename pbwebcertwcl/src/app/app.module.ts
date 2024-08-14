/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './app-material.module';
import { CaricaChecklistModule } from './carica-checklist/carica-checklist.module';
import { CaricaDichiarazioneFinaleModule } from './carica-dichiarazione-finale/carica-dichiarazione-finale.module';
import { GestioneProposteModule } from './gestione-proposte/gestione-proposte.module';
import { RicercaDocumentiModule } from './ricerca-documenti/ricerca-documenti.module';
import { CampionamentoModule } from './campionamento/campionamento.module';
import { CreaPropostaModule } from './crea-proposta/crea-proposta.module';
import { DatePipe } from "@angular/common";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    CoreModule,
    SharedModule,
    CaricaChecklistModule,
    CaricaDichiarazioneFinaleModule,
    GestioneProposteModule,
    RicercaDocumentiModule,
    CampionamentoModule,
    CreaPropostaModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
