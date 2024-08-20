/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';
import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { ContoEconomicoWaComponent } from './components/conto-economico-wa/conto-economico-wa.component';
import { PipeModule } from '../shared/pipe.module';



@NgModule({
  declarations: [
    ContoEconomicoWaComponent
  ],
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
    DatiProgettoAttivitaPregresseModule,
    PipeModule
  ]
})
export class ContoEconomicoWaModule { }
