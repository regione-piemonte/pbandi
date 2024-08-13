/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatSortModule } from '@angular/material/sort';
import { ArchivioFileModule as ArchivioFileLibModule, CoreModule as CoreLibModule, CoreModule, MaterialModule } from '@pbandi/common-lib';
import { SharedModule } from '../shared/shared.module';
import { ConsuntivoComponent } from './components/rendicontazione-a20/consuntivo/consuntivo.component';
import { AggiungiEntrataComponent } from './components/rendicontazione-a20/consuntivo/entrate-nette/aggiungi-entrata/aggiungi-entrata.component';
import { EntrateNetteComponent } from './components/rendicontazione-a20/consuntivo/entrate-nette/entrate-nette.component';
import { SalvaModificheComponent } from './components/rendicontazione-a20/consuntivo/entrate-nette/salva-modifiche/salva-modifiche.component';
import { SpeseComponent } from './components/rendicontazione-a20/consuntivo/spese/spese.component';
import { DocumentiDiSpesaComponent } from './components/rendicontazione-a20/documenti-di-spesa/documenti-di-spesa.component';
import { RendicontazioneA20Component } from './components/rendicontazione-a20/rendicontazione-a20.component';
import { SpecchiettoSaldoComponent } from './components/rendicontazione-a20/specchietto-saldo/specchietto-saldo.component';
import { DocumentiDiSpesaService } from './services/documenti-di-spesa.service';
import { RendicontazioneA20Service } from './services/rendicontazione-a20.service';


@NgModule({
imports: [
  CommonModule,
  MaterialModule,
  FormsModule,
  SharedModule,
  CoreModule,
  MatSortModule,
  ArchivioFileLibModule,
  CoreLibModule,
],
exports: [
  RendicontazioneA20Component,
  SpeseComponent,
  EntrateNetteComponent,
  DocumentiDiSpesaComponent,
  ConsuntivoComponent,
  AggiungiEntrataComponent,
  SpecchiettoSaldoComponent
],
declarations: [
  RendicontazioneA20Component,
  SpeseComponent,
  EntrateNetteComponent,
  DocumentiDiSpesaComponent,
  ConsuntivoComponent,
  AggiungiEntrataComponent,
  SalvaModificheComponent,
  SpecchiettoSaldoComponent
],
entryComponents: [
],
providers: [
  DocumentiDiSpesaService,
  RendicontazioneA20Service
]
})
export class RendicontazioneA20Module { }
