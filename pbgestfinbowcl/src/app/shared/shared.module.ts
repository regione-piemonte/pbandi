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
import { NavigationService } from './services/navigation.service';
import { TornaIndietroComponent } from './components/torna-indietro/torna-indietro.component';
import { ConfermaDialog } from './components/conferma-dialog/conferma-dialog';
import { DrawerModule as DrawerLibModule } from '@pbandi/common-lib';
import { PbandiMasterSpinner } from './components/centered-pbandi-master-spinner/pbandi-master-spinner.component';
import { PbandiLocalSpinner } from './components/portable-pbandi-local-spinner/pbandi-local-spinner.component';

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
        TornaIndietroComponent,
        ConfermaDialog,
        PbandiMasterSpinner,
        PbandiLocalSpinner
    ],
    declarations: [
        DrawerComponent,
        ContattiDialog,
        MatSpinnerPbandi,
        TornaIndietroComponent,
        ConfermaDialog,
        PbandiMasterSpinner,
        PbandiLocalSpinner
    ],
    entryComponents: [
    ],
    providers: [
        SharedService,
        NavigationService
    ]
})
export class SharedModule { }