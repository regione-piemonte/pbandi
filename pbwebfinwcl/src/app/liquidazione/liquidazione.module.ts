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
import { LiquidazioneComponent } from './components/liquidazione/liquidazione.component';
import { LiquidazioneService } from './services/liquidazione.service';

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

    LiquidazioneComponent],
    entryComponents: [

    ],
    providers: [
        LiquidazioneService
    ]
})
export class LiquidazioneModule { }