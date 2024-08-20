/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from 'src/app/app-material.module';
import { RicercaPropErogazioneComponent } from './components/ricerca-prop-erogazione/ricerca-prop-erogazione.component';
import { ControlliPreErogazioneComponent } from './components/controlli-pre-erogazione/controlli-pre-erogazione.component';
import { ModalitaRichiestaDialogComponent } from './components/modalita-richiesta-dialog/modalita-richiesta-dialog.component';
import { ControlloNonApplicabileDialogComponent } from './components/controllo-non-applicabile-dialog/controllo-non-applicabile-dialog.component';
import { FormsModule } from '@angular/forms';
import { CaricamentoDistinteComponent } from './components/caricamento-distinte/caricamento-distinte.component';
import { CreaDistintaComponent } from './components/crea-distinta/crea-distinta.component';
import { CopiaDistintaComponent } from './components/copia-distinta/copia-distinta.component';
import { ElencoDistinteComponent } from './components/elenco-distinte/elenco-distinte.component';
import { ArchivioFileModule as ArchivioFileLibModule } from '@pbandi/common-lib';
import { DettaglioDistintaComponent } from './components/dettaglio-distinta/dettaglio-distinta.component';
import { AvviaIterDialogComponent } from './components/avvia-iter-dialog/avvia-iter-dialog.component';
import { NuovoInterventoSostitutivoDialogComponent } from './components/nuovo-intervento-sostitutivo-dialog/nuovo-intervento-sostitutivo-dialog.component';
import { ListaInterventiDialogComponent } from './components/lista-interventi-dialog/lista-interventi-dialog.component';
import { CreaDistintaInterventiDialogComponent } from './components/crea-distinta-interventi-dialog/crea-distinta-interventi-dialog.component';


@NgModule({
  declarations: [
    RicercaPropErogazioneComponent,
    ControlliPreErogazioneComponent,
    ModalitaRichiestaDialogComponent,
    ControlloNonApplicabileDialogComponent,
    CaricamentoDistinteComponent,
    CreaDistintaComponent,
    CopiaDistintaComponent,
    ElencoDistinteComponent,
    DettaglioDistintaComponent,
    AvviaIterDialogComponent,
    NuovoInterventoSostitutivoDialogComponent,
    ListaInterventiDialogComponent,
    CreaDistintaInterventiDialogComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    HttpClientModule,
    MaterialModule,
    FormsModule,
    ArchivioFileLibModule
  ]
})
export class AmbitoErogazioniModule { }
