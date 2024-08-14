/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { CaricaChecklistComponent } from './components/carica-checklist/carica-checklist.component';
import { GestioneChecklistComponent } from './components/gestione-checklist/gestione-checklist.component';
import { GestioneChecklistProgettoComponent } from './components/gestione-checklist-progetto/gestione-checklist-progetto.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        CaricaChecklistComponent,
        GestioneChecklistComponent,
        GestioneChecklistProgettoComponent
    ],
    declarations: [
        CaricaChecklistComponent,
        GestioneChecklistComponent,
        GestioneChecklistProgettoComponent
    ],
    entryComponents: [
    ],
    providers: [
    ]
})
export class CaricaChecklistModule { }
