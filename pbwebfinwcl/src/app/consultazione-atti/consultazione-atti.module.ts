/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ConsultazioneAttiComponent } from './components/liquidazione/consultazione-atti.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        AppRoutingModule,
        SharedModule
    ],
    exports: [

    ],
    declarations: [
        ConsultazioneAttiComponent],
    entryComponents: [

    ],
    providers: [

    ]
})
export class ConsultazioneAttiModule { }