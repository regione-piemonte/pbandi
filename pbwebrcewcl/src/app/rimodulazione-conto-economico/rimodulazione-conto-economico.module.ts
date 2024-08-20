/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { RimodulazioneContoEconomicoComponent } from './components/rimodulazione-conto-economico/rimodulazione-conto-economico.component';
import { RimodulazioneContoEconomicoService } from './services/rimodulazione-conto-economico.service';
import { ProcediPropostaComponent } from './components/procedi-proposta/procedi-proposta.component';
import { InvioPropostaComponent } from './components/invio-proposta/invio-proposta.component';
import { ConcludiRimodulazioneComponent } from './components/concludi-rimodulazione/concludi-rimodulazione.component';
import { ModificaFonteFinanziariaDialogComponent } from './components/modifica-fonte-finanziaria-dialog/modifica-fonte-finanziaria-dialog.component';
import { InviaRichiestaComponent } from './components/invia-richiesta/invia-richiesta.component';
import { DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';
import { PipeModule } from '../shared/pipe.module';
import { ContoEconomicoWaModule } from '../conto-economico-wa/conto-economico-wa.module';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        DatiProgettoAttivitaPregresseModule,
        PipeModule,
        ContoEconomicoWaModule
    ],
    exports: [
        RimodulazioneContoEconomicoComponent,
        ProcediPropostaComponent,
        InvioPropostaComponent,
        ConcludiRimodulazioneComponent,
        ModificaFonteFinanziariaDialogComponent,
        InviaRichiestaComponent
    ],
    declarations: [
        RimodulazioneContoEconomicoComponent,
        ProcediPropostaComponent,
        InvioPropostaComponent,
        ConcludiRimodulazioneComponent,
        ModificaFonteFinanziariaDialogComponent,
        InviaRichiestaComponent
    ],
    entryComponents: [
    ],
    providers: [
        RimodulazioneContoEconomicoService
    ]
})
export class RimodulazioneContoEconomicoModule { }