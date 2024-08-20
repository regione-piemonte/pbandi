/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { RicercaBeneficiariComponent } from './gestione-crediti/components/ricerca-beneficiari/ricerca-beneficiari.component'; //AME
import { ModificaBeneficiariComponent } from './gestione-crediti/components/modifica-beneficiari/modifica-beneficiari.component'; //AME
import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { ErrorRoutingComponent } from './core/components/error-routing/error-routing.component';
import { DrawerComponent } from './shared/components/drawer/drawer.component';
import { RicercaSoggettiComponent } from './gestione-anagrafica/components/ricerca-soggetti/ricerca-soggetti.component';
import { AnagraficaBeneficiarioComponent } from './gestione-anagrafica/components/anagrafica-beneficiario/anagrafica-beneficiario.component';
import { StoricoBlocchiAttiviComponent } from './gestione-anagrafica/components/storico-blocchi-attivi/storico-blocchi-attivi.component';
import { VisualizzaStoricoPfComponent } from './gestione-anagrafica/components/visualizza-storico-pf/visualizza-storico-pf.component';
import { DatiDomandaEGComponent } from './gestione-anagrafica/components/dati-domanda-eg/dati-domanda-eg.component';
import { RicercaRichiesteComponent } from './gestione-richieste/components/ricerca-richieste/ricerca-richieste.component';
import { ModificaAnagraficaEGComponent } from './gestione-anagrafica/components/modifica-anagrafica-eg/modifica-anagrafica-eg.component';
import { ModificaAnagraficaPFComponent } from './gestione-anagrafica/components/modifica-anagrafica-pf/modifica-anagrafica-pf.component';
import { DatiDomandaPFComponent } from './gestione-anagrafica/components/dati-domanda-pf/dati-domanda-pf.component';
import { ModificaDatiDomandaEGComponent } from './gestione-anagrafica/components/modifica-dati-domanda-eg/modifica-dati-domanda-eg.component';
import { ModificaDatiDomandaPFComponent } from './gestione-anagrafica/components/modifica-dati-domanda-pf/modifica-dati-domanda-pf.component';
import { StoricoBeneficiarioComponent } from './gestione-anagrafica/components/storico-beneficiario/storico-beneficiario.component';
import { VisualizzaStoricoComponent } from './gestione-anagrafica/components/visualizza-storico/visualizza-storico.component';
import { AttivitaIstruttoreAreaCreditiComponent } from './gestione-crediti/components/attivita-istruttore-area-crediti/attivita-istruttore-area-crediti.component';
import { InserisciRichiestaComponent } from './gestione-richieste/components/inserisci-richiesta/inserisci-richiesta/inserisci-richiesta.component';
import { ElaboraRichiestaComponent } from './gestione-richieste/components/elabora-richiesta/elabora-richiesta.component';
import { ElaboraDurcComponent } from './gestione-richieste/components/elabora-durc/elabora-durc.component';
import { ElaboraBdnaComponent } from './gestione-richieste/components/elabora-bdna/elabora-bdna.component';
import { ElaboraAntimafiaComponent } from './gestione-richieste/components/elabora-antimafia/elabora-antimafia.component';
import { RicercaProposteVarStCredComponent } from './ricerca-proposte-var-st-cred/components/ricerca-proposte-var-st-cred/ricerca-proposte-var-st-cred.component';
import { RicercaCampionamentiComponent } from './campionamento/components/ricerca-campionamenti/ricerca-campionamenti.component';
import { NuovoCampionamentoComponent } from './campionamento/components/nuovo-campionamento/nuovo-campionamento.component';
import { VisualizzaSoggCorrDipDomComponent } from './gestione-anagrafica/components/visualizza-sogg-corr-dip-dom/visualizza-sogg-corr-dip-dom.component';
import { ModificaSoggCorrDipDomComponent } from './gestione-anagrafica/components/modifica-sogg-corr-dip-dom/modifica-sogg-corr-dip-dom.component';
import { PersonaGiuridicaIndipendenteDomandaComponent } from './gestione-anagrafica/components/anagrafica-beneficiario/persona-giuridica-indipendente-domanda/persona-giuridica-indipendente-domanda.component';
import { PersonaFisicaIndipendenteDomandaComponent } from './gestione-anagrafica/components/anagrafica-beneficiario/persona-fisica-indipendente-domanda/persona-fisica-indipendente-domanda.component';
import { VisualizzaSoggCorrDipDomPfComponent } from './gestione-anagrafica/components/visualizza-sogg-corr-dip-dom-pf/visualizza-sogg-corr-dip-dom-pf.component';
import { GestioneGaranzieComponent } from './gestione-garanzie/components/gestione-garanzie/gestione-garanzie.component';
import { AttivitaIstruttoreGaranzieComponent } from './gestione-crediti/components/attivita-istruttore-garanzie/attivita-istruttore-garanzie.component';
import { ModificaGaranzieComponent } from './gestione-garanzie/components/modifica-garanzie/modifica-garanzie.component';
import { RicercaPropErogazioneComponent } from './ambito-erogazioni/ambito-erogazioni/components/ricerca-prop-erogazione/ricerca-prop-erogazione.component';
import { ControlliPreErogazioneComponent } from './ambito-erogazioni/ambito-erogazioni/components/controlli-pre-erogazione/controlli-pre-erogazione.component';
import { CaricamentoDistinteComponent } from './ambito-erogazioni/ambito-erogazioni/components/caricamento-distinte/caricamento-distinte.component';
import { CreaDistintaComponent } from './ambito-erogazioni/ambito-erogazioni/components/crea-distinta/crea-distinta.component';
import { CopiaDistintaComponent } from './ambito-erogazioni/ambito-erogazioni/components/copia-distinta/copia-distinta.component';
import { ElencoDistinteComponent } from './ambito-erogazioni/ambito-erogazioni/components/elenco-distinte/elenco-distinte.component';
import { PropostaRevocaComponent } from './revoche/components/proposta-revoca/proposta-revoca.component';
import { ProvvedimentiRevocaComponent } from './revoche/components/provvedimenti-revoca/provvedimenti-revoca.component';
import { ProcedimentiRevocaComponent } from './revoche/components/procedimenti-revoca/procedimenti-revoca.component';
import { VisualizzaModificaRevocaComponent } from './revoche/components/proposta-revoca/visualizza-modifica-revoca/visualizza-modifica-revoca.component';
import { NuovaPropostaRevocaComponent } from './revoche/components/proposta-revoca/nuova-proposta-revoca/nuova-proposta-revoca.component';
import { VisualizzaModificaProcRevocaComponent } from './revoche/components/procedimenti-revoca/visualizza-modifica-proc-revoca/visualizza-modifica-proc-revoca.component';
import { RicercaControlliProgettiFinpComponent } from './gestione-controlli/components/ricerca-controlli-progetti-finp/ricerca-controlli-progetti-finp.component';
import { RicercaAltriControlliComponent } from './gestione-controlli/components/ricerca-altri-controlli/ricerca-altri-controlli.component';
import { InserimentoAltriControlliComponent } from './gestione-controlli/components/inserimento-altri-controlli/inserimento-altri-controlli.component';
import { IterAutorizzativiComponent } from './iter-autorizzativi/components/iter-autorizzativi/iter-autorizzativi.component';
import { ControdeduzioniComponent } from './gestione-controdeduzioni/components/controdeduzioni/controdeduzioni.component';
import { GestioneControlliProgettiFinpComponent } from './gestione-controlli/components/gestione-controlli-progetti-finp/gestione-controlli-progetti-finp.component';
import { GestioneAltriControlliComponent } from './gestione-controlli/components/gestione-altri-controlli/gestione-altri-controlli.component';
import { ModificaProvRevocaComponent } from './revoche/components/provvedimenti-revoca/modifica-prov-revoca/modifica-prov-revoca.component';
import { ContestazioniComponent } from './gestione-contestazioni/components/contestazioni/contestazioni.component';
import { InserisciContestazioneComponent } from './gestione-contestazioni/components/inserisci-contestazione/inserisci-contestazione.component';
import { DettaglioDistintaComponent } from './ambito-erogazioni/ambito-erogazioni/components/dettaglio-distinta/dettaglio-distinta.component';
import { AcquisizioneProgettiCampionatiComponent } from './acquisizione-campionamenti/components/acquisizione-progetti-campionati/acquisizione-progetti-campionati.component';
import { RicercaRiassicurazioniComponent } from './gestione-riassicurazioni/components/ricerca-riassicurazioni/ricerca-riassicurazioni.component';
import { GestioneEscussioneRiassicurazioniComponent } from './gestione-riassicurazioni/components/gestione-escussione-riassicurazioni/gestione-escussione-riassicurazioni.component';

const routes: Routes = [
  { path: '', redirectTo: 'errorRouting', pathMatch: 'full' },
  { path: 'errorRouting', component: ErrorRoutingComponent },
  {
    path: 'drawer/:id', component: DrawerComponent,
    children: [

      // Questi paths sono stati scritti da una squadra di professionisti, ognuno con il proprio tocco personale, ecco spiegati i nomi.

      //RINO'S PATH
      { path: 'ricercaSoggetti', component: RicercaSoggettiComponent },
      { path: 'anagraficaBeneficiario', component: AnagraficaBeneficiarioComponent },
      { path: 'anagraficaBeneficiario/personaGiuridicaIndipendenteDomanda', component: PersonaGiuridicaIndipendenteDomandaComponent },
      { path: 'anagraficaBeneficiario/personaFisicaIndipendenteDomanda', component: PersonaFisicaIndipendenteDomandaComponent },
      { path: 'storicoBlocchiAttivi', component: StoricoBlocchiAttiviComponent },
      { path: 'storicoBeneficiario', component: StoricoBeneficiarioComponent },
      { path: 'visualizzaStorico', component: VisualizzaStoricoComponent },
      { path: 'visualizzaStoricoPF', component: VisualizzaStoricoPfComponent },
      { path: 'modificaAnagraficaEG', component: ModificaAnagraficaEGComponent },
      { path: 'modificaAnagraficaPF', component: ModificaAnagraficaPFComponent },
      { path: 'datiDomandaPF', component: DatiDomandaPFComponent },
      { path: 'modificaDatiDomandaPF', component: ModificaDatiDomandaPFComponent },
      { path: 'datiDomandaEG', component: DatiDomandaEGComponent },
      { path: 'modificaDatiDomandaEG', component: ModificaDatiDomandaEGComponent },
      { path: 'gestioneRichieste', component: RicercaRichiesteComponent },
      { path: 'inserisciRichiesta', component: InserisciRichiestaComponent },
      { path: 'elaboraRichiesta', component: ElaboraRichiestaComponent },
      { path: 'elaboraDurc', component: ElaboraDurcComponent },
      { path: 'elaboraBdna', component: ElaboraBdnaComponent },
      { path: 'elaboraAntimafia', component: ElaboraAntimafiaComponent },

      //Ame's paths
      { path: 'monitoraggioCrediti', component: RicercaBeneficiariComponent },
      { path: 'modificaBeneficiario', component: ModificaBeneficiariComponent },
      // COMPONENTE DEPRECATO - { path: 'estrattoConto', component: EstrattoContoComponent },
      // COMPONENTE DEPRECATO - { path: 'pianoAmmortamento', component: PianoAmmortamentoComponent },
      { path: 'attivitaIstruttoreAreaCrediti', component: AttivitaIstruttoreAreaCreditiComponent },
      { path: 'attivitaIstruttoreGaranzie', component: AttivitaIstruttoreGaranzieComponent },
      { path: 'gestioneRiassicurazioni', component: RicercaRiassicurazioniComponent },
      { path: 'gestioneEscussioneRiassicurazioni', component: GestioneEscussioneRiassicurazioniComponent },
      // DA ULTIMARE - { path: 'gestioneGaranzie', component: GestioneGaranzieComponent },
      // DA ULTIMARE - { path: 'modificaGaranzia', component: ModificaGaranzieComponent },

      // Franky,s path
      { path: 'proposteVariazioniStatoCrediti', component: RicercaProposteVarStCredComponent },
      { path: 'ricercaCampionamenti', component: RicercaCampionamentiComponent },
      { path: 'nuovoCampionamento', component: NuovoCampionamentoComponent },
      { path: 'visualizzaSoggCorrDipDom', component: VisualizzaSoggCorrDipDomComponent },
      { path: 'visualizzaSoggCorrDipDomPf', component: VisualizzaSoggCorrDipDomPfComponent },
      { path: 'modificaSoggCorrDipDom', component: ModificaSoggCorrDipDomComponent },
      // gestione controlli
      { path: 'ricercaControlliProgettiFinp', component: RicercaControlliProgettiFinpComponent },
      { path: 'ricercaAltriControlli', component: RicercaAltriControlliComponent },
      { path: 'inserimentoAltriControlli', component: InserimentoAltriControlliComponent },
      { path: 'gestioneControlliProgettiFinp', component: GestioneControlliProgettiFinpComponent },
      { path: 'gestioneAltriControlli', component: GestioneAltriControlliComponent },
      { path: 'acquisizioneProgettiFinp', component: AcquisizioneProgettiCampionatiComponent },

      //iter autorizzativi Martina
      { path: 'iterAutorizzativi', component: IterAutorizzativiComponent },
      // contestazioni Martina
      { path: 'contestazioni/:idProgetto/:idBandoLinea', component: ContestazioniComponent },
      { path: 'inserisci-contestazione', component: InserisciContestazioneComponent },


      //MARCELLO'S PATH - Mannaggia a lui
      { path: 'gestioneGaranzie', component: GestioneGaranzieComponent },
      { path: 'modificaGaranzia', component: ModificaGaranzieComponent },
      { path: 'controdeduzioni/:idProgetto/:idBandoLinea', component: ControdeduzioniComponent },



      //Lorenzo's path
      //erogazioni
      { path: 'ambitoErogazioni', component: RicercaPropErogazioneComponent },
      { path: 'controlliPreErogazione', component: ControlliPreErogazioneComponent },
      { path: 'caricamentoDistinte', component: CaricamentoDistinteComponent },
      { path: 'creaDistinta', component: CreaDistintaComponent },
      { path: 'copiaDistinta', component: CopiaDistintaComponent },
      { path: 'elencoDistinte', component: ElencoDistinteComponent },
      { path: 'dettaglioDistinta', component: DettaglioDistintaComponent },

      //revoche
      { path: 'proposteRevoca', component: PropostaRevocaComponent },
      { path: 'procedimentiRevoca', component: ProcedimentiRevocaComponent },
      { path: 'provvedimentiRevoca', component: ProvvedimentiRevocaComponent },
      { path: 'modificaRevoca', component: VisualizzaModificaRevocaComponent },
      { path: 'nuovaPropostaRevoca', component: NuovaPropostaRevocaComponent },
      { path: 'modificaProcedimentoRevoca', component: VisualizzaModificaProcRevocaComponent },
      { path: 'modificaProvvedimentoRevoca', component: ModificaProvRevocaComponent },
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
