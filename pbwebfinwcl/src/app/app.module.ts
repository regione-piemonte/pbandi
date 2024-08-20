/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './app-material.module';
import { GestioneDisimpegniModule } from './gestione-disimpegni/gestione-disimpegni.module';
import { RicercaAttiLiquidazioneModule } from './ricerca-atti-liquidazione/ricerca-atti-liquidazione.module';
import { LiquidazioneModule } from './liquidazione/liquidazione.module';
import { ConsultazioneAttiModule } from './consultazione-atti/consultazione-atti.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    CoreModule,
    HttpClientModule,
    SharedModule,
    GestioneDisimpegniModule,
    RicercaAttiLiquidazioneModule,
    LiquidazioneModule,
    ConsultazioneAttiModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
