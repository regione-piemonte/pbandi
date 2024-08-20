/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppaltiComponent } from './components/appalti/appalti.component';
import { SharedModule } from "../shared/shared.module";
import { MaterialModule } from "../app-material.module";
import { FormsModule } from "@angular/forms";
import { NuovoAppaltoComponent } from './components/nuovo-appalto/nuovo-appalto.component';
import { ModificaProceduraAggiudicazioneComponent } from './components/modifica-procedura-aggiudicazione/modifica-procedura-aggiudicazione.component';
import { NuovaProceduraAggiudicazioneComponent } from './components/nuova-procedura-aggiudicazione/nuova-procedura-aggiudicazione.component';
import { ModificaAppaltoComponent } from './components/modifica-appalto/modifica-appalto.component';
import { DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';
import { Appalti2Service } from './services/appalti-2.service';
import { CronoprogrammaModule } from '../cronoprogramma/cronoprogramma.module';



@NgModule({
  declarations: [
    AppaltiComponent,
    NuovoAppaltoComponent,
    ModificaProceduraAggiudicazioneComponent,
    NuovaProceduraAggiudicazioneComponent,
    ModificaAppaltoComponent
  ],
  imports: [
    MaterialModule,
    CommonModule,
    SharedModule,
    FormsModule,
    DatiProgettoAttivitaPregresseModule,
    CronoprogrammaModule
  ],
  providers: [
    Appalti2Service
  ]
})
export class AppaltiModule { }
