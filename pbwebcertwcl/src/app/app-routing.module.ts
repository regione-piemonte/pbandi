/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { CampionamentoComponent } from './campionamento/components/campionamento/campionamento.component';
import { CaricaChecklistComponent } from './carica-checklist/components/carica-checklist/carica-checklist.component';
import { GestioneChecklistProgettoComponent } from './carica-checklist/components/gestione-checklist-progetto/gestione-checklist-progetto.component';
import { GestioneChecklistComponent } from './carica-checklist/components/gestione-checklist/gestione-checklist.component';
import { CaricaDichiarazioneFinaleComponent } from './carica-dichiarazione-finale/components/carica-dichiarazione-finale/carica-dichiarazione-finale.component';
import { GestioneDichiarazioneFinaleComponent } from './carica-dichiarazione-finale/components/gestione-dichiarazione-finale/gestione-dichiarazione-finale.component';
import { ErrorRoutingComponent } from './core/components/error-routing/error-routing.component';
import { CreaAnteprimaPropostaComponent } from './crea-proposta/components/crea-anteprima-proposta/crea-anteprima-proposta.component';
import { CreaPropostaComponent } from './crea-proposta/components/crea-proposta/crea-proposta.component';
import { AggiornamentoStatoPropostaComponent } from './gestione-proposte/components/aggiornamento-stato-proposta/aggiornamento-stato-proposta.component';
import { GestioneProposteComponent } from './gestione-proposte/components/gestione-proposte/gestione-proposte.component';
import { RicercaDocumentiComponent } from './ricerca-documenti/components/ricerca-documenti/ricerca-documenti.component';
import { DrawerComponent } from './shared/components/drawer/drawer.component';

const routes: Routes = [
  { path: '', redirectTo: 'errorRouting', pathMatch: 'full' },
  { path: 'errorRouting', component: ErrorRoutingComponent },
  {
    path: 'drawer/:id', component: DrawerComponent,
    children: [
      { path: 'creaAnteprimaProposta', component: CreaAnteprimaPropostaComponent },
      { path: 'creaProposta/:id', component: CreaPropostaComponent },
      { path: 'caricaChecklist', component: CaricaChecklistComponent },
      { path: 'gestioneChecklist/:id', component: GestioneChecklistComponent },
      { path: 'gestioneChecklistProgetto/:id', component: GestioneChecklistProgettoComponent },
      { path: 'gestioneProposte', component: GestioneProposteComponent },
      { path: 'aggiornamentoStatoProposta/:id', component: AggiornamentoStatoPropostaComponent },
      { path: 'caricaDichiarazioneFinale', component: CaricaDichiarazioneFinaleComponent },
      { path: 'gestioneDichiarazioneFinale/:id', component: GestioneDichiarazioneFinaleComponent },
      { path: 'ricercaDocumenti', component: RicercaDocumentiComponent },
      { path: 'campionamento', component: CampionamentoComponent }
    ]
  },
  { path: '**', redirectTo: 'errorRouting', pathMatch: 'full' }

];

const configRouter: ExtraOptions = {
  enableTracing: false,
  useHash: true
};

@NgModule({
  imports: [RouterModule.forRoot(routes, configRouter)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
