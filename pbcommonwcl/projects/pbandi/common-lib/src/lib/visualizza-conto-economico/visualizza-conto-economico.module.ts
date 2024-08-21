/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { VisualizzaContoEconomicoService } from './services/visualizza-conto-economico.service';
import { VisualizzaContoEconomicoDialogComponent } from './components/visualizza-conto-economico-dialog/visualizza-conto-economico-dialog.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        VisualizzaContoEconomicoDialogComponent
    ],
    declarations: [
        VisualizzaContoEconomicoDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        VisualizzaContoEconomicoService
    ]
})
export class VisualizzaContoEconomicoModule { }