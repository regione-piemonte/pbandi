/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DettaglioUtenteComponent } from './gestione-utenti/components/dettaglio-utente/dettaglio-utente.component';
import { ConfiguraBandoComponent } from './configurazione-bando/components/configura-bando/configura-bando.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { ErrorRoutingComponent } from './core/components/error-routing/error-routing.component';
import { DrawerComponent } from './shared/components/drawer/drawer.component';
import { RicercaBandiComponent } from './configurazione-bando/components/ricerca-bandi/ricerca-bandi.component';
import { ConfiguraBandoLineaComponent } from './configurazione-bando/components/configura-bando-linea/configura-bando-linea.component';
import { AssociazionePermessoRuoloComponent } from './associazione-permesso-ruolo/components/associazione-permesso-ruolo/associazione-permesso-ruolo.component';
import { AssociazioneRuoloPermessoComponent } from './associazione-ruolo-permesso/components/associazione-ruolo-permesso/associazione-ruolo-permesso.component';
import { CambiaBeneficiarioComponent } from './cambia-beneficiario/components/cambia-beneficiario/cambia-beneficiario.component';
import { GestioneUtentiComponent } from './gestione-utenti/components/gestione-utenti/gestione-utenti.component';
import { GestioneAssociazioneComponent } from './associazione-istruttore-progetti/components/gestione-associazione/gestione-associazione.component';
import { AssociaNuovoProgettoComponent } from './associazione-istruttore-progetti/components/associa-nuovo-progetto/associa-nuovo-progetto.component';
import { AssociaNuovoBandoComponent } from './associazione-istruttore-progetti/components/associa-nuovo-bando/associa-nuovo-bando.component';
import { RicercaIstruttoreComponent } from './associazione-istruttore-progetti/components/ricerca-istruttore/ricerca-istruttore.component';
import { GestioneNewsComponent } from './gestione-news/components/gestione-news/gestione-news.component';
import { GestioneTemplatesComponent } from './gestione-templates/components/gestione-templates/gestione-templates.component';
import { ConsoleApplicativaComponent } from './console-applicativa/components/console-applicativa/console-applicativa.component';
import { BoStorageComponent } from './bo-storage/components/bo-storage/bo-storage.component';

const routes: Routes = [
  { path: '', redirectTo: 'errorRouting', pathMatch: 'full' },
  { path: 'errorRouting', component: ErrorRoutingComponent },
  {
    path: 'drawer/:id', component: DrawerComponent,
    children: [
      { path: 'configurazioneBando', component: RicercaBandiComponent },
      { path: 'configuraBando', component: ConfiguraBandoComponent },
      { path: 'configuraBandoLinea', component: ConfiguraBandoLineaComponent },
      { path: 'associazionePermessoRuolo', component: AssociazionePermessoRuoloComponent },
      { path: 'associazioneRuoloPermesso', component: AssociazioneRuoloPermessoComponent },
      { path: 'cambiaBeneficiario', component: CambiaBeneficiarioComponent },
      { path: 'gestioneUtenti', component: GestioneUtentiComponent },
      { path: 'associazioniIstruttoreProgetti', component: RicercaIstruttoreComponent },
      { path: 'gestioneAssociazione', component: GestioneAssociazioneComponent },
      { path: 'associaNuovoProgetto', component: AssociaNuovoProgettoComponent },
      { path: 'associaNuovoBando', component: AssociaNuovoBandoComponent },
      { path: 'dettaglioUtente/:idSoggetto/:cf', component: DettaglioUtenteComponent },
      { path: 'gestioneNews', component: GestioneNewsComponent },
      { path: 'gestioneTemplates', component: GestioneTemplatesComponent },
      { path: 'consoleApplicativa', component: ConsoleApplicativaComponent },
      { path: 'boStorage', component: BoStorageComponent }
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
