/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from '../app-material.module';
import { HomeSceltaProfiloComponent } from './components/home-scelta-profilo/home-scelta-profilo.component';
import { HomeOperatoreComponent } from './components/home-operatore/home-operatore.component';
import { SharedModule } from '../shared/shared.module';
import { HomeService } from './services/home.service';
import { ConsensoDialogComponent } from './components/consenso-dialog/consenso-dialog.component';
import { FormsModule } from '@angular/forms';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        SharedModule,
        FormsModule
    ],
    exports: [
        HomeSceltaProfiloComponent,
        HomeOperatoreComponent,
        ConsensoDialogComponent
    ],
    declarations: [
        HomeSceltaProfiloComponent,
        HomeOperatoreComponent,
        ConsensoDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        HomeService
    ]
})
export class HomeModule { }