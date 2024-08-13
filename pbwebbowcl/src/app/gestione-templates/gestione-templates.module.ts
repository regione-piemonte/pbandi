/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { FormsModule } from '@angular/forms';
import { GestioneTemplatesService } from './services/gestione-templates.service';
import { GestioneTemplatesComponent } from './components/gestione-templates/gestione-templates.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        SharedModule,
        FormsModule
    ],
    exports: [
        GestioneTemplatesComponent
    ],
    declarations: [
        GestioneTemplatesComponent
    ],
    entryComponents: [
    ],
    providers: [
        GestioneTemplatesService
    ]
})
export class GestioneTemplatesModule { }