/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegistroControlliComponent } from './components/registro-controlli/registro-controlli.component';
import { RettificaForfettariaComponent } from './components/rettifica-forfettaria/rettifica-forfettaria.component';
import { NuovoEsitoControlloComponent } from './components/nuovo-esito-controllo/nuovo-esito-controllo.component';
import { SharedModule } from "../shared/shared.module";
import { MaterialModule } from "../app-material.module";
import { FormsModule } from "@angular/forms";
import { ModificaRegolariComponent } from './components/modifica-regolari/modifica-regolari.component';
import { ModificaIrregolariComponent } from './components/modifica-irregolari/modifica-irregolari.component';
import { DettaglioEsitoControlloIrregolaritaComponent } from './components/dettaglio-esito-controllo-irregolarita/dettaglio-esito-controllo-irregolarita.component';
import { RicercaEsitiControlliComponent } from './components/ricerca-esiti-controlli/ricerca-esiti-controlli.component';
import { DettaglioIrregolaritaComponent } from './components/dettaglio-irregolarita/dettaglio-irregolarita.component';
import { RegistraInvioComponent } from './components/registra-invio/registra-invio.component';
import { RegistroControlliService2 } from './services/registro-controlli2.service';
import { DettaglioEsitoControlloOpGestComponent } from './components/dettaglio-esito-controllo_op_gest/dettaglio-esito-controllo-op-gest.component';
import { RegistroControlliTablesComponent } from './components/registro-controlli-tables/registro-controlli-tables.component';


@NgModule({
  declarations: [
    RegistroControlliComponent,
    RettificaForfettariaComponent,
    NuovoEsitoControlloComponent,
    ModificaRegolariComponent,
    ModificaIrregolariComponent,
    DettaglioEsitoControlloOpGestComponent,
    DettaglioEsitoControlloIrregolaritaComponent,
    RicercaEsitiControlliComponent,
    DettaglioIrregolaritaComponent,
    RegistraInvioComponent,
    RegistroControlliTablesComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    MaterialModule,
    FormsModule
  ],
  providers: [
    RegistroControlliService2
  ]
})
export class RegistroControlliModule { }
