/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { ConsoleApplicativaComponent } from './components/console-applicativa/console-applicativa.component';
import { ConsoleApplicativaService } from './services/console-applicativa.service';


@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
    ],
    declarations: [
        ConsoleApplicativaComponent
    ],
    entryComponents: [
    ],
    providers: [
        ConsoleApplicativaService
    ]
})
export class ConsoleApplicativaModule { }