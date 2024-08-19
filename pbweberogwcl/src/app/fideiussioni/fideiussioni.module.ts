/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { SharedModule } from "../shared/shared.module";
import { MaterialModule } from "../app-material.module";
import { GestioneFideiussioniComponent } from './components/gestione-fideiussioni/gestione-fideiussioni.component';
import { NuovaFideiussioneComponent } from './components/nuova-fideiussione/nuova-fideiussione.component';
import { InizializzazioneService } from "../shared/services/inizializzazione.service";
import { FideiussioniService } from "./services/fideiussioni.service";
import { DettaglioFideiussioneComponent } from './components/dettaglio-fideiussione/dettaglio-fideiussione.component';
import { DatiProgettoAttivitaPregresseModule, HelpModule as HelpLibModule } from '@pbandi/common-lib';



@NgModule({
  declarations: [
    GestioneFideiussioniComponent,
    NuovaFideiussioneComponent,
    DettaglioFideiussioneComponent,
  ],
  imports: [
    FormsModule,
    MaterialModule,
    CommonModule,
    SharedModule,
    DatiProgettoAttivitaPregresseModule,
    HelpLibModule
  ],
  exports: [
    GestioneFideiussioniComponent,
    NuovaFideiussioneComponent
  ],
  entryComponents: [
  ],
  providers: [
    FideiussioniService,
    InizializzazioneService
  ]
})
export class FideiussioniModule { }
