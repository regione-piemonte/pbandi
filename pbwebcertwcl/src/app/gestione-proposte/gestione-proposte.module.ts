/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { GestioneProposteComponent } from './components/gestione-proposte/gestione-proposte.component';
import { AggiornamentoStatoPropostaComponent } from './components/aggiornamento-stato-proposta/aggiornamento-stato-proposta.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        GestioneProposteComponent,
        AggiornamentoStatoPropostaComponent
    ],
    declarations: [
        GestioneProposteComponent,
        AggiornamentoStatoPropostaComponent
    ],
    entryComponents: [
    ],
    providers: [
    ]
})
export class GestioneProposteModule { }
