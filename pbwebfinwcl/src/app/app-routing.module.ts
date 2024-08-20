/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { ConsultazioneAttiComponent } from './consultazione-atti/components/liquidazione/consultazione-atti.component';
import { ConsultazioneAttiModule } from './consultazione-atti/consultazione-atti.module';
import { ErrorRoutingComponent } from './core/components/error-routing/error-routing.component';
import { AssociazioniPerBeneficiarioComponent } from './gestione-disimpegni/components/associazioni-per-beneficiario/associazioni-per-beneficiario.component';
import { AssociazioniComponent } from './gestione-disimpegni/components/associazioni/associazioni.component';
import { DettaglioImpegnoComponent } from './gestione-disimpegni/components/dettaglio-impegno/dettaglio-impegno.component';
import { GestioneDisimpegniComponent } from './gestione-disimpegni/components/gestione-disimpegni/gestione-disimpegni.component';
import { NuovaAssociazioneComponent } from './gestione-disimpegni/components/nuova-associazione/nuova-associazione.component';
import { LiquidazioneComponent } from './liquidazione/components/liquidazione/liquidazione.component';
import { DettaglioAttoLiquidazioneComponent } from './ricerca-atti-liquidazione/components/dettaglio-atto-liquidazione/dettaglio-atto-liquidazione.component';
import { RicercaAttiLiquidazioneComponent } from './ricerca-atti-liquidazione/components/ricerca-atti-liquidazione/ricerca-atti-liquidazione.component';
import { DrawerComponent } from './shared/components/drawer/drawer.component';

const routes: Routes = [
  { path: '', redirectTo: 'errorRouting', pathMatch: 'full' },
  { path: 'errorRouting', component: ErrorRoutingComponent },
  {
    path: 'drawer/:id', component: DrawerComponent,
    children: [
      { path: 'gestioneImpegni', component: GestioneDisimpegniComponent },
      { path: 'dettaglioImpegno', component: DettaglioImpegnoComponent },
      { path: 'ricercaAttiLiquidazione', component: RicercaAttiLiquidazioneComponent },
      { path: 'associazioniBeneficiario', component: AssociazioniPerBeneficiarioComponent },
      { path: 'associazioni', component: AssociazioniComponent },
      { path: 'nuovaAssociazione', component: NuovaAssociazioneComponent },
      { path: 'dettaglioAtto', component: DettaglioAttoLiquidazioneComponent },
      { path: 'liquidazione/:idProgetto/:idBando', component: LiquidazioneComponent },
      { path: 'consultazioneAtti/:idProgetto/:idBando', component: ConsultazioneAttiComponent }
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
