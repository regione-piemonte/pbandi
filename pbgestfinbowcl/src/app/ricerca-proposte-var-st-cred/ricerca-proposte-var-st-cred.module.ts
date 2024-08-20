/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CoreModule } from '@pbandi/common-lib';
import { MaterialModule } from '../app-material.module';
import { RicercaRichiesteComponent } from '../gestione-richieste/components/ricerca-richieste/ricerca-richieste.component';
import { SharedModule } from '../shared/shared.module';
import { RicercaProposteVarStCredComponent } from './components/ricerca-proposte-var-st-cred/ricerca-proposte-var-st-cred.component';
import { RicercaProposteVarStCredService } from './services/ricerca-proposte-var-st-cred.service';
import { DialogConfermaStatoPropostaVarCredComponent } from './components/dialog-conferma-stato-proposta-var-cred/dialog-conferma-stato-proposta-var-cred.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreModule
    ],
    exports: [
        RicercaProposteVarStCredComponent,
      
    ],
    declarations: [
        RicercaProposteVarStCredComponent,
        DialogConfermaStatoPropostaVarCredComponent,
        
    ],
    entryComponents: [
    ],
    providers: [
        RicercaProposteVarStCredService,
    ]
})
export class RicercaProposteVarStCredModule { }
