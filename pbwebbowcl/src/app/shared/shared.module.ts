/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MatSpinnerPbandi } from './components/mat-spinner-pbandi/mat-spinner-pbandi.component';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { DrawerComponent } from './components/drawer/drawer.component';
import { ContattiDialog } from './components/contatti-dialog/contatti-dialog';
import { SharedService } from './services/shared.service';
import { ConfermaDialog } from './components/conferma-dialog/conferma-dialog';
import { NavigationService } from './services/navigation.service';
import { VisualizzaTestoDialogComponent } from './components/visualizza-testo-dialog/visualizza-testo-dialog.component';
import { DrawerModule as DrawerLibModule } from '@pbandi/common-lib';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        AppRoutingModule,
        DrawerLibModule
    ],
    exports: [
        DrawerComponent,
        ContattiDialog,
        MatSpinnerPbandi,
        ConfermaDialog,
        VisualizzaTestoDialogComponent
    ],
    declarations: [
        DrawerComponent,
        ContattiDialog,
        MatSpinnerPbandi,
        ConfermaDialog,
        VisualizzaTestoDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        SharedService,
        NavigationService
    ]
})
export class SharedModule { }