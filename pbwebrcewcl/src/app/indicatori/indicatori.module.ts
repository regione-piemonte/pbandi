/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndicatoriComponent } from './components/indicatori/indicatori.component';
import { MaterialModule } from "../app-material.module";
import { FormsModule } from "@angular/forms";
import { SharedModule } from "../shared/shared.module";
import { IndicatoriAvvioComponent } from './components/indicatori-avvio/indicatori-avvio.component';
import { DatiProgettoAttivitaPregresseModule, HelpModule as HelpLibModule } from '@pbandi/common-lib';
import { IndicatoriService } from './services/indicatori.service';
import { Indicatori2Service } from './services/indicatori2.service';



@NgModule({
  declarations: [IndicatoriComponent, IndicatoriAvvioComponent],
  imports: [
    MaterialModule,
    CommonModule,
    SharedModule,
    FormsModule,
    DatiProgettoAttivitaPregresseModule,
    HelpLibModule
  ],
  entryComponents: [
  ],
  providers: [
    IndicatoriService,
    Indicatori2Service
  ]
})
export class IndicatoriModule { }
