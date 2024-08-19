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
import { MaterialModule } from './app-material.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ErogazioneModule } from './erogazione/erogazione.module';
import { TrasferimentiModule } from './trasferimenti/trasferimenti.module';
import { DatiProgettoModule } from './dati-progetto/dati-progetto.module';
import { DisimpegniModule } from './disimpegni/disimpegni.module';
import { MonitoraggioControlliModule } from './monitoraggio-controlli/monitoraggio-controlli.module';
import { FideiussioniModule } from "./fideiussioni/fideiussioni.module";
import { RinunciaModule } from "./rinuncia/rinuncia.module";
import { ArchivioFileModule as ArchivioFileLibModule, CoreModule as CoreLibModule, VisualizzaContoEconomicoModule, DatiProgettoAttivitaPregresseModule, } from '@pbandi/common-lib';
import { RevocheModule } from "./revoche/revoche.module";
import { RecuperiModule } from "./recuperi/recuperi.module";
import { ChiusuraProgettoModule } from "./chiusura-progetto/chiusura-progetto.module";
import { RegistroControlliModule } from "./registro-controlli/registro-controlli.module";
import { SoppressioneModule } from './soppressione/soppressione.module';
import { QuillModule } from 'ngx-quill';
import { VerificaRichiestaErogazioneModule } from './verifica-richiesta-erogazione/verifica-richiesta-erogazione.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule,
    CoreModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ErogazioneModule,
    TrasferimentiModule,
    DatiProgettoModule,
    DisimpegniModule,
    MonitoraggioControlliModule,
    FideiussioniModule,
    RinunciaModule,
    RevocheModule,
    RecuperiModule,
    ChiusuraProgettoModule,
    RegistroControlliModule,
    ArchivioFileLibModule,
    CoreLibModule,
    VisualizzaContoEconomicoModule,
    DatiProgettoAttivitaPregresseModule,
    SoppressioneModule,
    QuillModule.forRoot(),
    VerificaRichiestaErogazioneModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
