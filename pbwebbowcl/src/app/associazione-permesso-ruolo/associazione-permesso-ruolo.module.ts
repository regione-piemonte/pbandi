/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { AssociazionePermessoRuoloComponent } from './components/associazione-permesso-ruolo/associazione-permesso-ruolo.component';
import { AssociazionePermessoRuoloService } from './services/associazione-permesso-ruolo.service';
import { AssociazionePrDialogComponent } from './components/associazione-pr-dialog/associazione-pr-dialog.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        AssociazionePermessoRuoloComponent,
        AssociazionePrDialogComponent
    ],
    declarations: [
        AssociazionePermessoRuoloComponent,
        AssociazionePrDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        AssociazionePermessoRuoloService
    ]
})
export class AssociazionePermessoRuoloModule { }