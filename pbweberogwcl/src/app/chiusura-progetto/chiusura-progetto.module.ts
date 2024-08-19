/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChiusuraProgettoComponent } from './components/chiusura-progetto/chiusura-progetto.component';
import { ChiusuraUfficioProgettoComponent } from './components/chiusura-ufficio-progetto/chiusura-ufficio-progetto.component';
import { SharedModule } from "../shared/shared.module";
import { MaterialModule } from "../app-material.module";
import { FormsModule } from "@angular/forms";
import { DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';



@NgModule({
  declarations: [ChiusuraProgettoComponent, ChiusuraUfficioProgettoComponent],
  imports: [
    CommonModule,
    SharedModule,
    MaterialModule,
    FormsModule,
    DatiProgettoAttivitaPregresseModule
  ]
})
export class ChiusuraProgettoModule { }
