/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { GestioneAffidamentiComponent } from './components/gestione-affidamenti/gestione-affidamenti.component';
import { AffidamentiService } from './services/affidamenti.service';
import { NuovoAffidamentoComponent } from './components/nuovo-affidamento/nuovo-affidamento.component';
import { ModificaAffidamentoComponent } from './components/modifica-affidamento/modifica-affidamento.component';
import { FornitoreAffidamentiDialogComponent } from './components/fornitore-affidamenti-dialog/fornitore-affidamenti-dialog.component';
import { VarianteAffidamentiDialogComponent } from './components/variante-affidamenti-dialog/variante-affidamenti-dialog.component';
import { DocumentiGeneratiDialogComponent } from './components/documenti-generati-dialog/documenti-generati-dialog.component';
import { NotificheAffidamentiDialogComponent } from './components/notifiche-affidamenti-dialog/notifiche-affidamenti-dialog.component';
import { NuovaChecklistDocumentaleComponent } from './components/nuova-checklist-documentale/nuova-checklist-documentale.component';
import { SafeHtmlPipe } from '../safeHTML.pipe';
import { RichiestaIntegrazioneDialogComponent } from './components/richiesta-integrazione-dialog/richiesta-integrazione-dialog.component';
import { ArchivioFileModule, CoreModule as CoreLibModule, DatiProgettoAttivitaPregresseModule, HelpModule as HelpLibModule } from '@pbandi/common-lib';
import { RespingiDialogComponent } from './components/respingi-dialog/respingi-dialog.component';
import { QuillModule } from 'ngx-quill';
import { ConfermaInvioVerificaDialogComponent } from './components/conferma-invio-verifica-dialog/conferma-invio-verifica-dialog.component';
import { SubcontrattoAffidamentiDialogComponent } from './components/subcontratto-affidamenti-dialog/subcontratto-affidamenti-dialog.component';
import { AllegatiChecklistDialogComponent } from './components/allegati-checklist-dialog/allegati-checklist-dialog.component';
import { AllegaVerbaleDialogComponent } from './components/allega-verbale-dialog/allega-verbale-dialog.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        ArchivioFileModule,
        CoreLibModule,
        DatiProgettoAttivitaPregresseModule,
        QuillModule,
        HelpLibModule
    ],
    exports: [
        GestioneAffidamentiComponent,
        NuovoAffidamentoComponent,
        ModificaAffidamentoComponent,
        FornitoreAffidamentiDialogComponent,
        VarianteAffidamentiDialogComponent,
        DocumentiGeneratiDialogComponent,
        NotificheAffidamentiDialogComponent,
        NuovaChecklistDocumentaleComponent,
        SafeHtmlPipe,
        RichiestaIntegrazioneDialogComponent,
        RespingiDialogComponent,
        ConfermaInvioVerificaDialogComponent,
        AllegatiChecklistDialogComponent
    ],
    declarations: [
        GestioneAffidamentiComponent,
        NuovoAffidamentoComponent,
        ModificaAffidamentoComponent,
        FornitoreAffidamentiDialogComponent,
        VarianteAffidamentiDialogComponent,
        DocumentiGeneratiDialogComponent,
        NotificheAffidamentiDialogComponent,
        NuovaChecklistDocumentaleComponent,
        SafeHtmlPipe,
        RichiestaIntegrazioneDialogComponent,
        RespingiDialogComponent,
        ConfermaInvioVerificaDialogComponent,
        SubcontrattoAffidamentiDialogComponent,
        AllegatiChecklistDialogComponent,
        AllegaVerbaleDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        AffidamentiService
    ]
})
export class AffidamentiModule { }