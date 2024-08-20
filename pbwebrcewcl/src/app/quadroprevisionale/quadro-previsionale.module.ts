/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuadroPrevisionaleComponent } from './components/quadro-previsionale/quadro-previsionale.component';
import { MaterialModule } from "../app-material.module";
import { SharedModule } from "../shared/shared.module";
import { FormsModule } from "@angular/forms";
import { DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';



@NgModule({
  declarations: [QuadroPrevisionaleComponent],
  imports: [
    MaterialModule,
    CommonModule,
    SharedModule,
    FormsModule,
    DatiProgettoAttivitaPregresseModule
  ]
})
export class QuadroPrevisionaleModule { }
