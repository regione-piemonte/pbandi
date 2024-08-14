/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { CampionamentoComponent } from './components/campionamento/campionamento.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        CampionamentoComponent
    ],
    declarations: [
        CampionamentoComponent
    ],
    entryComponents: [
    ],
    providers: [
    ]
})
export class CampionamentoModule { }
