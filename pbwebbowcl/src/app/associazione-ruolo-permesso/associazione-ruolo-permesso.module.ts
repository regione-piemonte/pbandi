/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { AssociazioneRpDialogComponent } from './components/associazione-rp-dialog/associazione-rp-dialog.component';
import { AssociazioneRuoloPermessoComponent } from './components/associazione-ruolo-permesso/associazione-ruolo-permesso.component';
import { AssociazioneRuoloPermessoService } from './services/associazione-ruolo-permesso.service';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        AssociazioneRuoloPermessoComponent,
        AssociazioneRpDialogComponent
    ],
    declarations: [
        AssociazioneRuoloPermessoComponent,
        AssociazioneRpDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        AssociazioneRuoloPermessoService
    ]
})
export class AssociazioneRuoloPermessoModule { }