/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ValidazioneRelazioneTecnicaComponent } from './components/validazione-relazione-tecnica/validazione-relazione-tecnica.component';
import { MaterialModule } from '../app-material.module';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { RelazioneTecnicaService } from '../relazione-tecnica/services/relazione-tecnica.service';
import { NotaRelTecDialogComponent } from './components/nota-rel-tec-dialog/nota-rel-tec-dialog.component';



@NgModule({
  declarations: [ValidazioneRelazioneTecnicaComponent, NotaRelTecDialogComponent],
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
  ], 
  providers:[
  ]
})
export class ValidazioneRelazioneTecnicaModule { }
