/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommunicazioneRinunciaComponent } from './components/communicazione-rinuncia/communicazione-rinuncia.component';
import { FormsModule } from "@angular/forms";
import { MaterialModule } from "../app-material.module";
import { SharedModule } from "../shared/shared.module";
import { InviaDichiarazioneRinunciaComponent } from './components/invia-dichiarazione-rinuncia/invia-dichiarazione-rinuncia.component';
import { DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule, HelpModule as HelpLibModule } from '@pbandi/common-lib';
import { Rinuncia2Service } from './services/rinuncia-2.service';



@NgModule({
  declarations: [CommunicazioneRinunciaComponent, InviaDichiarazioneRinunciaComponent],
  imports: [
    FormsModule,
    MaterialModule,
    CommonModule,
    SharedModule,
    VisualizzaContoEconomicoModule,
    DatiProgettoAttivitaPregresseModule,
    HelpLibModule
  ],
  providers: [
    Rinuncia2Service
  ]
})
export class RinunciaModule { }
