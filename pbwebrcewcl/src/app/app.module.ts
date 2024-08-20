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
import { DatePipe } from "@angular/common";
import { AffidamentiModule } from './affidamenti/affidamenti.module';
import { CronoprogrammaModule } from './cronoprogramma/cronoprogramma.module';
import { IndicatoriModule } from "./indicatori/indicatori.module";
import { QuadroPrevisionaleModule } from "./quadroprevisionale/quadro-previsionale.module";
import { ArchivioFileModule, CoreModule as CoreLibModule, DatiProgettoAttivitaPregresseModule, CronoprogrammaFasiModule as CronoprogrammaFasiLibModule } from '@pbandi/common-lib';
import { AppaltiModule } from "./appalti/appalti.module";
import { RimodulazioneContoEconomicoModule } from './rimodulazione-conto-economico/rimodulazione-conto-economico.module';
import { QuillModule } from 'ngx-quill';
import { MatDialogModule } from '@angular/material/dialog';
import { CronoprogrammaFasiModule } from './cronoprogramma-fasi/cronoprogramma-fasi.module';
import { RimodulazioneContoEconomicoA20Module } from './rimodulazione-conto-economico-a20/rimodulazione-conto-economico-a20.module';

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
    AffidamentiModule,
    DatiProgettoAttivitaPregresseModule,
    CronoprogrammaModule,
    CronoprogrammaFasiLibModule,
    CronoprogrammaFasiModule,
    AppaltiModule,
    IndicatoriModule,
    QuadroPrevisionaleModule,
    ArchivioFileModule,
    CoreLibModule,
    RimodulazioneContoEconomicoModule,
    RimodulazioneContoEconomicoA20Module,
    QuillModule.forRoot(),
    MatDialogModule,
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }

