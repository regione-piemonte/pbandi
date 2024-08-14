/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { DrawerComponent } from './components/drawer/drawer.component';
import { ContattiDialog } from './components/contatti-dialog/contatti-dialog';
import { MatSpinnerPbandi } from './components/mat-spinner-pbandi/mat-spinner-pbandi.component';
import { ConfermaDialog } from './components/conferma-dialog/conferma-dialog';
import { CertificazioneService } from './services/certificazione.service';
import { SharedService } from './services/shared.service';
import { ExcelService } from './services/excel.service';
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
        ConfermaDialog

    ],
    declarations: [
        DrawerComponent,
        ContattiDialog,
        MatSpinnerPbandi,
        ConfermaDialog
    ],
    entryComponents: [
    ],
    providers: [
        CertificazioneService,
        ExcelService,
        SharedService
    ]
})
export class SharedModule { }