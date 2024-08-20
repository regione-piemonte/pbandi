/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { RicBenResponseService } from './services/ricben-response-service.service';
import { ModBenResponseService } from './services/modben-response-service.service';
import { RicercaBeneficiariComponent } from './components/ricerca-beneficiari/ricerca-beneficiari.component';
import { ModificaBeneficiariComponent } from './components/modifica-beneficiari/modifica-beneficiari.component';
import { EditDialogComponent } from './components/dialog-edit-modben/dialog-edit.component';
import { AttivitaIstruttoreAreaCreditiComponent } from './components/attivita-istruttore-area-crediti/attivita-istruttore-area-crediti.component';
import { RevocaBancariaService } from './services/revoca-bancaria.service';
import { CoreModule } from '@pbandi/common-lib';
import { RevocaBancariaComponent } from './components/revoca-bancaria/revoca-bancaria.component';
import { HistoryDialogComponent } from './components/dialog-history-modben/dialog-history.component';
import { AzioniRecuperoBancaComponent } from './components/azioni-recupero-banca/azioni-recupero-banca.component';
import { LiberazioneGaranteComponent } from './components/liberazione-garante/liberazione-garante.component';
import { AzioniRecuperoBancaService } from './services/azioni-recupero-banca-service';
import { DialogEditAzioneRecuperoBancaComponent } from './components/dialog-edit-azione-recupero-banca/dialog-edit-azione-recupero-banca.component';
import { LiberazioneGaranteDialogComponent } from './components/new-edit-liberazione-garante-dialog/libgar-dialog.component';
import { AttivitàIstruttoreAreaCreditiService } from './services/attivita-istruttore-area-crediti.service';
import { DatePipe } from '@angular/common';
import { EscussioneConfidiComponent } from './components/escussione-confidi/escussione-confidi.component';
import { AbbattimentoGaranzieService } from './services/abbattimento-garanzie.service';
import { AbbattimentoGaranzieComponent } from './components/abbattimento-garanzie/abbattimento-garanzie.component';
import { DialogEditAbbattimentoGaranzieComponent } from './components/dialog-edit-abbattimento-garanzie/dialog-edit-abbattimento-garanzie.component';
import { PianoRientroComponent } from './components/piano-rientro/piano-rientro.component';
import { SaldoStralcioComponent } from './components/saldo-stralcio/saldo-stralcio.component';
import { SaldoStralcioService } from './services/saldo-stralcio-service';
import { DialogPianoRientroComponent } from './components/dialog-piano-rientro/dialog-piano-rientro.component';
import { DialogEditSaldoStralcioComponent } from './components/dialog-edit-saldo-stralcio/dialog-edit-saldo-stralcio.component';
import { PassaggioPerditaComponent } from './components/passaggio-perdita/passaggio-perdita.component';
import { PassaggioPerditaService } from './services/passaggio-perdita-services';
import { LiberazioneBancaComponent } from './components/liberazione-banca/liberazione-banca.component';
import { MessaMoraComponent } from './components/messa-mora/messa-mora.component';
import { DialogEditMessaMoraComponent } from './components/messa-mora/dialog-edit-messa-mora/dialog-edit-messa-mora.component';
import { MessaMoraService } from './services/messa-mora-services';
import { IscrizioneRuoloComponent } from './components/iscrizione-ruolo/iscrizione-ruolo.component';
import { SegnalazioneCorteContiComponent } from './components/segnalazione-corte-conti/segnalazione-corte-conti.component';
import { DialogEditSegnalazioneCorteContiComponent } from './components/dialog-edit-segnalazione-corte-conti/dialog-edit-segnalazione-corte-conti.component';
import { SegnalazioneCorteContiService } from './services/segnalazione-corte-conti-service';
import { TransazioneComponent } from './components/transazione/transazione.component';
import { CessioneQuotaFinComponent } from './components/cessione-quota-finpiemonte/cessione-quota.component';
import { NoteGeneraliComponent } from './components/note-generali/note-generali.component';
import { RicercaProposteVarStCredComponent } from '../ricerca-proposte-var-st-cred/components/ricerca-proposte-var-st-cred/ricerca-proposte-var-st-cred.component';
import { NavigationRicercaBeneficiariService } from './services/navigation-ricerca-beneficiari.service';
import { RevAmmDialogComponent } from './components/dialog-revoca-amministrativa/dialog-rev-amm.component';
import { HistoryAIACdialogComponent } from './components/dialog-history-attistarecre/dialog-history-attistarecre.component';
import { DialogStoricoBoxComponent } from './components/dialog-storico-box/dialog-storico-box.component';
import { MatSortModule } from '@angular/material/sort';
import { AttivitaIstruttorieService } from './services/attivitaIstruttorie.service';
import { AttivitaIstruttoreGaranzieComponent } from './components/attivita-istruttore-garanzie/attivita-istruttore-garanzie.component';
import { DialogEditModgarComponent } from './components/dialog-edit-modgar/dialog-edit-modgar.component';
import { DialogEditstatoModgarComponent } from '../gestione-garanzie/components/modifica-garanzie/dialogs/dialog-editstato-modgar/dialog-edit-stato-credito-gar.component';
import { DialogHistoryEscussioneComponent } from './components/dialog-history-escussione/dialog-history-escussione.component';
import { DialogHistoryStatocreditoComponent } from './components/dialog-history-statocredito/dialog-history-statocredito.component';
import { DialogEditStatoescussioneComponent } from './components/dialog-edit-statoescussione/dialog-edit-statoescussione.component';
import { DialogRichiestaIntegrazione } from './components/dialog-richiesta-integrazione/dialog-richiesta-integrazione.component';
import { DialogInvioEsito } from './components/dialog-invio-esito/dialog-invio-esito.component';
import { DialogRevocaBancariaComponent } from './components/dialog-revoca-bancaria/dialog-revoca-bancaria.component';
import { DialogAzioniRecuperoBanca } from './components/dialog-azioni-recupero-banca/dialog-azioni-recupero-banca.component';
import { DialogSaldoStralcio } from './components/dialog-saldo-stralcio/dialog-saldo-stralcio.component';
import { DialogInsertEscussione } from './components/dialog-insert-escussione/dialog-insert-escussione.component';
import { DialogEliminazioneAllegato } from './components/dialog-eliminazione-allegato/dialog-eliminazione-allegato.component';
import { NumberFormatPipe } from './components/number-format/number.pipe';
import { NumberPipeInput } from './components/number-format-input/number.pipe.input';
import { GestioneAllegatiComponent } from './components/gestione-allegati/gestione-allegati.component';
import { DialogEditNoteGeneraliComponent } from './components/note-generali/dialog-edit-note-generali/dialog-edit-note-generali.component';
import { DialogPianoAmmortamento } from './components/dialog-piano-ammortamento/dialog-piano-ammortamento.component';
import { DialogEstrattoConto } from './components/dialog-estratto-conto/dialog-estratto-conto.component';
import { BoxRevocaBancariaComponent } from './components/box-attivita-istruttore/box-revoca-bancaria/box-revoca-bancaria.component';
import { DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule} from '@pbandi/common-lib';
import { BoxAttivitaIstruttoreModule } from './components/box-attivita-istruttore/box-attivita-istruttore.module';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreModule,
        MatSortModule,
        BoxAttivitaIstruttoreModule,
        DatiProgettoAttivitaPregresseModule,
        VisualizzaContoEconomicoModule
    ],
    exports: [
        RicercaBeneficiariComponent,
        ModificaBeneficiariComponent,
        NumberFormatPipe,
        NumberPipeInput,
        EditDialogComponent,
        DialogEditModgarComponent,
        DialogEditstatoModgarComponent,
        DialogEditStatoescussioneComponent,
        DialogRichiestaIntegrazione,
        DialogEliminazioneAllegato,
        DialogInvioEsito,
        HistoryDialogComponent,
        DialogHistoryEscussioneComponent,
        DialogHistoryStatocreditoComponent,
        DialogRevocaBancariaComponent,
        DialogAzioniRecuperoBanca,
        DialogSaldoStralcio,
        DialogInsertEscussione,
        AttivitaIstruttoreAreaCreditiComponent,
        AttivitaIstruttoreGaranzieComponent,
        RevocaBancariaComponent,
        LiberazioneGaranteComponent,
        LiberazioneGaranteDialogComponent,
        AzioniRecuperoBancaComponent,
        DialogEditAzioneRecuperoBancaComponent,
        EscussioneConfidiComponent,
        AbbattimentoGaranzieComponent,
        DialogEditAbbattimentoGaranzieComponent,
        PianoRientroComponent,
        SaldoStralcioComponent,
        DialogPianoRientroComponent,
        DialogEditSaldoStralcioComponent,
        PassaggioPerditaComponent,
        LiberazioneBancaComponent,
        MessaMoraComponent,
        DialogEditMessaMoraComponent,
        IscrizioneRuoloComponent,
        SegnalazioneCorteContiComponent,
        DialogEditSegnalazioneCorteContiComponent,
        TransazioneComponent,
        CessioneQuotaFinComponent,
        NoteGeneraliComponent,
        RevAmmDialogComponent,
        HistoryAIACdialogComponent,
        DialogEditNoteGeneraliComponent,
        DialogPianoAmmortamento,
        DialogEstrattoConto


    ],
    declarations: [
        RicercaBeneficiariComponent,
        ModificaBeneficiariComponent,
        NumberFormatPipe,
        NumberPipeInput,
        EditDialogComponent,
        DialogEditModgarComponent,
        DialogEditstatoModgarComponent,
        DialogEditStatoescussioneComponent,
        DialogRichiestaIntegrazione,
        DialogEliminazioneAllegato,
        DialogInvioEsito,
        HistoryDialogComponent,
        DialogHistoryEscussioneComponent,
        DialogHistoryStatocreditoComponent,
        DialogRevocaBancariaComponent,
        DialogAzioniRecuperoBanca,
        DialogSaldoStralcio,
        DialogInsertEscussione,
        AttivitaIstruttoreAreaCreditiComponent,
        AttivitaIstruttoreGaranzieComponent,
        RevocaBancariaComponent,
        AzioniRecuperoBancaComponent,
        LiberazioneGaranteComponent,
        LiberazioneGaranteDialogComponent,
        LiberazioneGaranteComponent,
        DialogEditAzioneRecuperoBancaComponent,
        EscussioneConfidiComponent,
        AbbattimentoGaranzieComponent,
        DialogEditAbbattimentoGaranzieComponent,
        PianoRientroComponent,
        SaldoStralcioComponent,
        DialogPianoRientroComponent,
        DialogEditSaldoStralcioComponent,
        PassaggioPerditaComponent,
        LiberazioneBancaComponent,
        MessaMoraComponent,
        DialogEditMessaMoraComponent,
        IscrizioneRuoloComponent,
        SegnalazioneCorteContiComponent,
        DialogEditSegnalazioneCorteContiComponent,
        CessioneQuotaFinComponent,
        TransazioneComponent,
        NoteGeneraliComponent,
        RevAmmDialogComponent,
        HistoryAIACdialogComponent,
        DialogStoricoBoxComponent,
        GestioneAllegatiComponent,
        DialogEditNoteGeneraliComponent,
        DialogPianoAmmortamento,
        DialogEstrattoConto,
        
    ],
    entryComponents: [
    ],
    providers: [
        RicBenResponseService,
        ModBenResponseService,
        RevocaBancariaService,
        DatePipe,
        AttivitàIstruttoreAreaCreditiService,
        RevocaBancariaService,
        AzioniRecuperoBancaService,
        AbbattimentoGaranzieService,
        SaldoStralcioService,
        PassaggioPerditaService,
        MessaMoraService,
        SegnalazioneCorteContiService,
        NavigationRicercaBeneficiariService,
        AttivitaIstruttorieService
    ]

    /* Se nel mondo esistesse un po’ di bene
    e ognun si considerasse suo fratello,
    ci sarebbe meno pensieri e meno pene
    e il mondo ne sarebbe assai più bello. 
    - cit.*/
})
export class GestioneCreditiModule { }
