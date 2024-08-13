/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RelazioneTecnicaComponent } from './components/relazione-tecnica/relazione-tecnica.component';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { RelazioneTecnicaService } from './services/relazione-tecnica.service';



@NgModule({
  declarations: [RelazioneTecnicaComponent],
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule
  ],
  exports:[
  
  ],
  providers: [
    RelazioneTecnicaService,
  ]
})
export class RelazioneTecnicaModule { }
