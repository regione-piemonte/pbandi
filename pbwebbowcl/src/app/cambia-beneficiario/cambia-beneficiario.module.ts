/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { CambiaBeneficiarioComponent } from './components/cambia-beneficiario/cambia-beneficiario.component';
import { CambiaBeneficiarioService } from './services/cambia-beneficiario.service';
import { ModificaDatiBeneficiarioComponent } from './components/modifica-dati-beneficiario/modifica-dati-beneficiario.component';
import { ConfirmCambiaBeneficiarioDialogComponent } from './components/confirm-cambia-beneficiario-dialog/confirm-cambia-beneficiario-dialog.component';


@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        CambiaBeneficiarioComponent
    ],
    declarations: [
        CambiaBeneficiarioComponent,
        ModificaDatiBeneficiarioComponent,
        ConfirmCambiaBeneficiarioDialogComponent,
        
    ],
    entryComponents: [
    ],
    providers: [
        CambiaBeneficiarioService
    ]
})
export class CambiaBeneficiarioModule { }