/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropostaRevocaComponent } from './components/proposta-revoca/proposta-revoca.component';
import { ProcedimentiRevocaComponent } from './components/procedimenti-revoca/procedimenti-revoca.component';
import { ProvvedimentiRevocaComponent } from './components/provvedimenti-revoca/provvedimenti-revoca.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { MaterialModule } from 'src/app/app-material.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ArchivioFileModule as ArchivioFileLibModule } from '@pbandi/common-lib';
import { VisualizzaModificaRevocaComponent } from './components/proposta-revoca/visualizza-modifica-revoca/visualizza-modifica-revoca.component';
import { ArchiviaRevocaComponent } from './components/proposta-revoca/archivia-revoca/archivia-revoca.component';
import { NuovaPropostaRevocaComponent } from './components/proposta-revoca/nuova-proposta-revoca/nuova-proposta-revoca.component';
import { VisualizzaModificaProcRevocaComponent } from './components/procedimenti-revoca/visualizza-modifica-proc-revoca/visualizza-modifica-proc-revoca.component';
import { ModificaProvRevocaComponent } from './components/provvedimenti-revoca/modifica-prov-revoca/modifica-prov-revoca.component';
import { AvviaProcedimentoRevDialogComponent } from './components/procedimenti-revoca/avvia-procedimento-rev-dialog/avvia-procedimento-rev-dialog.component';
import { RichiestaIntegrazioneDialogComponent } from './components/procedimenti-revoca/richiesta-integrazione-dialog/richiesta-integrazione-dialog.component';
import { ArchiviaProcedimentoRevDialogComponent } from './components/procedimenti-revoca/archivia-procedimento-rev-dialog/archivia-procedimento-rev-dialog.component';
import { RichiestaProrogaDialogComponent } from './components/procedimenti-revoca/richiesta-proroga-dialog/richiesta-proroga-dialog.component';
import { EmissioneProvvedimentoDialogComponent } from './components/provvedimenti-revoca/emissione-provvedimento-dialog/emissione-provvedimento-dialog.component';
import { ConfermaProvvedimentoDialogComponent } from './components/provvedimenti-revoca/conferma-provvedimento-dialog/conferma-provvedimento-dialog.component';
import { RitiroInAutotutelaDialogComponent } from './components/provvedimenti-revoca/ritiro-in-autotutela-dialog/ritiro-in-autotutela-dialog.component';
import { ModaleCampiErogazioneDialogComponent } from './components/procedimenti-revoca/modale-campi-erogazione-dialog/modale-campi-erogazione-dialog.component';


@NgModule({
  declarations: [PropostaRevocaComponent, ProcedimentiRevocaComponent, ProvvedimentiRevocaComponent, VisualizzaModificaRevocaComponent, ArchiviaRevocaComponent, NuovaPropostaRevocaComponent, VisualizzaModificaProcRevocaComponent, ModificaProvRevocaComponent, AvviaProcedimentoRevDialogComponent, RichiestaIntegrazioneDialogComponent, ArchiviaProcedimentoRevDialogComponent, RichiestaProrogaDialogComponent, EmissioneProvvedimentoDialogComponent, ConfermaProvvedimentoDialogComponent, RitiroInAutotutelaDialogComponent, ModaleCampiErogazioneDialogComponent],
  imports: [
    CommonModule,
    SharedModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ArchivioFileLibModule
  ]
})
export class RevocheModule { }
