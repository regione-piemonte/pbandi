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
import { IndicatoriService } from './services/indicatori.service';
import { VisualizzaTestoDialogComponent } from './components/visualizza-testo-dialog/visualizza-testo-dialog.component';

@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    SharedModule,
    FormsModule,
  ],
  exports: [
    IndicatoriComponent,
    VisualizzaTestoDialogComponent
  ],
  declarations: [
    IndicatoriComponent,
    VisualizzaTestoDialogComponent
  ],
  entryComponents: [
  ],
  providers: [
    IndicatoriService
  ]
})
export class IndicatoriModule { }
