/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { SharedModule } from "../shared/shared.module";
import { MaterialModule } from "../app-material.module";
import { FormsModule } from "@angular/forms";
import { GestioneRecuperiComponent } from './components/gestione-recuperi/gestione-recuperi.component';
import { ModificaRecuperoComponent } from './components/modifica-recupero/modifica-recupero.component';
import { DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';
import { NuovoRecuperoComponent } from './components/nuovo-recupero/nuovo-recupero.component';
import { RecuperiService } from './services/recuperi.service';


@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    MaterialModule,
    FormsModule,
    DatiProgettoAttivitaPregresseModule
  ],
  exports: [
    GestioneRecuperiComponent,
    ModificaRecuperoComponent,
    NuovoRecuperoComponent
  ],
  declarations: [
    GestioneRecuperiComponent,
    ModificaRecuperoComponent,
    NuovoRecuperoComponent
  ],
  entryComponents: [
  ],
  providers: [
    RecuperiService,
    DatePipe
  ]
})
export class RecuperiModule { }
