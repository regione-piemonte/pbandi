/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { CreaAnteprimaPropostaComponent } from './components/crea-anteprima-proposta/crea-anteprima-proposta.component';
import { CreaPropostaComponent } from './components/crea-proposta/crea-proposta.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        CreaAnteprimaPropostaComponent,
        CreaPropostaComponent
    ],
    declarations: [
        CreaAnteprimaPropostaComponent,
        CreaPropostaComponent
    ],
    entryComponents: [
    ],
    providers: [
    ]
})
export class CreaPropostaModule { }