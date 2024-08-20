/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { GestioneAffidamentiComponent } from './affidamenti/components/gestione-affidamenti/gestione-affidamenti.component';
import { ModificaAffidamentoComponent } from './affidamenti/components/modifica-affidamento/modifica-affidamento.component';
import { NuovoAffidamentoComponent } from './affidamenti/components/nuovo-affidamento/nuovo-affidamento.component';
import { ErrorRoutingComponent } from './core/components/error-routing/error-routing.component';
import { CronoprogrammaComponent } from './cronoprogramma/components/cronoprogramma/cronoprogramma.component';
import { DrawerComponent } from './shared/components/drawer/drawer.component';
import { NuovaChecklistDocumentaleComponent } from './affidamenti/components/nuova-checklist-documentale/nuova-checklist-documentale.component';
import { IndicatoriComponent } from "./indicatori/components/indicatori/indicatori.component";
import { AppaltiComponent } from "./appalti/components/appalti/appalti.component";
import { NuovoAppaltoComponent } from "./appalti/components/nuovo-appalto/nuovo-appalto.component";
import { ModificaProceduraAggiudicazioneComponent } from "./appalti/components/modifica-procedura-aggiudicazione/modifica-procedura-aggiudicazione.component";
import { NuovaProceduraAggiudicazioneComponent } from "./appalti/components/nuova-procedura-aggiudicazione/nuova-procedura-aggiudicazione.component";
import { IndicatoriAvvioComponent } from "./indicatori/components/indicatori-avvio/indicatori-avvio.component";
import { CronoprogrammaAvvioComponent } from "./cronoprogramma/components/cronoprogramma-avvio/cronoprogramma-avvio.component";
import { QuadroPrevisionaleComponent } from "./quadroprevisionale/components/quadro-previsionale/quadro-previsionale.component";
import { ModificaAppaltoComponent } from "./appalti/components/modifica-appalto/modifica-appalto.component";
import { RimodulazioneContoEconomicoComponent } from './rimodulazione-conto-economico/components/rimodulazione-conto-economico/rimodulazione-conto-economico.component';
import { ProcediPropostaComponent } from './rimodulazione-conto-economico/components/procedi-proposta/procedi-proposta.component';
import { InvioPropostaComponent } from './rimodulazione-conto-economico/components/invio-proposta/invio-proposta.component';
import { ConcludiRimodulazioneComponent } from './rimodulazione-conto-economico/components/concludi-rimodulazione/concludi-rimodulazione.component';
import { InviaRichiestaComponent } from './rimodulazione-conto-economico/components/invia-richiesta/invia-richiesta.component';
import { CronoprogrammaFasiContainerComponent } from './cronoprogramma-fasi/components/cronoprogramma-fasi-container/cronoprogramma-fasi-container.component';
import { ContoEconomicoWaComponent } from './conto-economico-wa/components/conto-economico-wa/conto-economico-wa.component';
import { RimodulazioneContoEconomicoA20Component } from './rimodulazione-conto-economico-a20/components/rimodulazione-conto-economico-a20/rimodulazione-conto-economico-a20.component';
import { ConcludiRimodulazioneA20Component } from './rimodulazione-conto-economico-a20/components/concludi-rimodulazione-a20/concludi-rimodulazione-a20.component';

const routes: Routes = [
  { path: '', redirectTo: 'errorRouting', pathMatch: 'full' },
  { path: 'errorRouting', component: ErrorRoutingComponent },
  {
    path: 'drawer/:id', component: DrawerComponent,
    children: [
      //:id corrisponde ad :idProgetto, non utilizzarlo per altri parametri, altrimenti si crea un malfunzionamento sul
      //precaricamento dei dati i ndocumenti di progetto
      { path: 'affidamenti/:id', component: GestioneAffidamentiComponent },
      { path: 'nuovoAffidamento/:id', component: NuovoAffidamentoComponent },
      { path: 'modificaAffidamento/:id', component: ModificaAffidamentoComponent },
      { path: 'cronoprogramma/:id/:idBando', component: CronoprogrammaComponent },
      { path: 'cronoprogrammaAvvio/:id/:idBando', component: CronoprogrammaAvvioComponent },
      { path: 'cronoprogrammaFasi/:id/:idBandoLinea', component: CronoprogrammaFasiContainerComponent },
      { path: 'nuovaChecklist/:id', component: NuovaChecklistDocumentaleComponent },
      { path: 'indicatori/:id', component: IndicatoriComponent },
      { path: 'indicatori/:id/:sif', component: IndicatoriComponent },
      { path: 'indicatoriAvvio/:id', component: IndicatoriAvvioComponent },
      { path: 'appalti/:id', component: AppaltiComponent },
      { path: 'modificaAppalto/:id/:id1', component: ModificaAppaltoComponent },
      { path: 'nuovoAppalto/:id', component: NuovoAppaltoComponent },
      { path: 'nuovaProcedura/:id', component: NuovaProceduraAggiudicazioneComponent },
      { path: 'modificaProcedura/:id/:id1', component: ModificaProceduraAggiudicazioneComponent },
      { path: 'quadroPrevisionale/:id', component: QuadroPrevisionaleComponent },
      { path: 'rimodulazioneContoEconomico/:idProgetto/:idBando', component: RimodulazioneContoEconomicoComponent },
      { path: 'rimodulazioneContoEconomico/:idProgetto/:idBando/:idContoEconomico', component: RimodulazioneContoEconomicoComponent },
      { path: 'procediProposta/:idProgetto/:idBando/:idContoEconomico', component: ProcediPropostaComponent },
      { path: 'invioProposta/:idProgetto/:idBando/:idContoEconomico/:idDocumentoIndex', component: InvioPropostaComponent },
      { path: 'concludiRimodulazione/:idProgetto/:idBando/:idContoEconomico', component: ConcludiRimodulazioneComponent },
      { path: 'inviaRichiesta/:idProgetto/:idBando/:idContoEconomico', component: InviaRichiestaComponent },
      { path: 'gestioneQtes/:idProgetto/:idBando', component: ContoEconomicoWaComponent },
      { path: 'rimodulazioneContoEconomicoA20/:idProgetto/:idBando', component: RimodulazioneContoEconomicoA20Component },
      { path: 'concludiRimodulazioneA20/:idProgetto/:idBando/:idContoEconomico', component: ConcludiRimodulazioneA20Component },
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
