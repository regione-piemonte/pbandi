/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MatSpinnerPbandi } from './components/mat-spinner-pbandi/mat-spinner-pbandi.component';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { GetStringDialog } from './components/get-string-dialog/get-string-dialog.component';
import { ConfermaDialog } from './components/conferma-dialog/conferma-dialog';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule
    ],
    exports: [
        MatSpinnerPbandi,
        GetStringDialog,
        ConfermaDialog
    ],
    declarations: [
        MatSpinnerPbandi,
        GetStringDialog,
        ConfermaDialog
    ],
    entryComponents: [
    ],
    providers: [
    ]
})
export class SharedModule { }