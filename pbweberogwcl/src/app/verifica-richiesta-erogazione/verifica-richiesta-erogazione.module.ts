/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VerificaRichiestaErogazioneComponent } from './components/verifica-richiesta-erogazione/verifica-richiesta-erogazione.component';
import { MaterialModule } from '../app-material.module';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { AppRoutingModule } from '../app-routing.module';
import { DirectiveModule } from '../shared/directive.module';
import { ArchivioFileModule as ArchivioFileLibModule, DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule } from '@pbandi/common-lib';
import { PipeModule } from '../shared/pipe.module';



@NgModule({
  declarations: [VerificaRichiestaErogazioneComponent],
  imports: [
        MaterialModule, 
        CommonModule,
        FormsModule,
        SharedModule,
        AppRoutingModule,
        ArchivioFileLibModule,
        PipeModule,
        DirectiveModule,
        VisualizzaContoEconomicoModule,
        DatiProgettoAttivitaPregresseModule
    ],
  exports: [
      VerificaRichiestaErogazioneComponent
    ]
})
export class VerificaRichiestaErogazioneModule { }
