/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { CaricaDichiarazioneFinaleComponent } from './components/carica-dichiarazione-finale/carica-dichiarazione-finale.component';
import { GestioneDichiarazioneFinaleComponent } from './components/gestione-dichiarazione-finale/gestione-dichiarazione-finale.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        CaricaDichiarazioneFinaleComponent,
        GestioneDichiarazioneFinaleComponent,
    ],
    declarations: [
        CaricaDichiarazioneFinaleComponent,
        GestioneDichiarazioneFinaleComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ]
})
export class CaricaDichiarazioneFinaleModule { }
