/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { RicercaDocumentiComponent } from './components/ricerca-documenti/ricerca-documenti.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        RicercaDocumentiComponent
    ],
    declarations: [
        RicercaDocumentiComponent
    ],
    entryComponents: [
    ],
    providers: [
    ]
})
export class RicercaDocumentiModule { }
