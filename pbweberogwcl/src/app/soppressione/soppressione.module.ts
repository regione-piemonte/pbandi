/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DirectiveModule } from 'src/app/shared/directive.module';
import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';

import { DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';
import { SoppressioneService } from './services/soppressione.service';
import { SoppressioniComponent } from './components/soppressioni/soppressioni.component';
import { NuovoModificaSoppressioneDialogComponent } from './components/nuovo-modifica-soppressione-dialog/nuovo-modifica-soppressione-dialog.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        AppRoutingModule,
        DirectiveModule,
        DatiProgettoAttivitaPregresseModule
    ],
    exports: [
        SoppressioniComponent,
        NuovoModificaSoppressioneDialogComponent
    ],
    declarations: [
        SoppressioniComponent,
        NuovoModificaSoppressioneDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        SoppressioneService
    ]
})
export class SoppressioneModule { }
