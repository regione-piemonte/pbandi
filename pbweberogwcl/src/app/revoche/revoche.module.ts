/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GestioneRevocheComponent } from './components/gestione-revoche/gestione-revoche.component';
import { SharedModule } from "../shared/shared.module";
import { MaterialModule } from "../app-material.module";
import { FormsModule } from "@angular/forms";
import { ModificaRevocaComponent } from './components/modifica-revoca/modifica-revoca.component';
import { DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';
import { ConfermaDialogComponent } from './components/conferma-dialog/conferma-dialog.component';
import { NuovaRevocaComponent } from './components/nuova-revoca/nuova-revoca.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    MaterialModule,
    FormsModule,
    DatiProgettoAttivitaPregresseModule,
  ],
  exports: [
    GestioneRevocheComponent,
    ModificaRevocaComponent,
    ConfermaDialogComponent,
    NuovaRevocaComponent
  ],
  declarations: [
    GestioneRevocheComponent,
    ModificaRevocaComponent,
    ConfermaDialogComponent,
    NuovaRevocaComponent
  ],
})
export class RevocheModule { }
