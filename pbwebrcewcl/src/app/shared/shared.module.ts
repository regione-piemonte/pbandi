/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule, DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { DrawerComponent } from './components/drawer/drawer.component';
import { ContattiDialog } from './components/contatti-dialog/contatti-dialog';
import { MatSpinnerPbandi } from './components/mat-spinner-pbandi/mat-spinner-pbandi.component';
import { ConfermaDialog } from './components/conferma-dialog/conferma-dialog';
import { SharedService } from './services/shared.service';
import { VisualizzaTestoDialogComponent } from "./components/visualizza-testo-dialog/visualizza-testo-dialog.component";
import { DragDropModule } from "@angular/cdk/drag-drop";
import { MatDialogModule } from '@angular/material/dialog';
import { ConfermaWarningDialogComponent } from './components/conferma-warning-dialog/conferma-warning-dialog.component';
import { DrawerModule as DrawerLibModule } from '@pbandi/common-lib';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        AppRoutingModule,
        DragDropModule,
        MatDialogModule,
        DrawerLibModule
    ],
    exports: [
        DrawerComponent,
        ContattiDialog,
        MatSpinnerPbandi,
        ConfermaDialog,
        VisualizzaTestoDialogComponent,
        ConfermaWarningDialogComponent
    ],
    declarations: [
        DrawerComponent,
        ContattiDialog,
        MatSpinnerPbandi,
        VisualizzaTestoDialogComponent,
        ConfermaDialog,
        ConfermaWarningDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        SharedService,
        DatePipe
    ]
})
export class SharedModule { }
