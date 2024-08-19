/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { ErrorRoutingComponent } from './core/components/error-routing/error-routing.component';
import { DrawerComponent } from './shared/components/drawer/drawer.component';
import { DisimpegniComponent } from './disimpegni/components/disimpegni/disimpegni.component';
import { NuovoDisimpegnoComponent } from './disimpegni/components/nuovo-disimpegno/nuovo-disimpegno.component';
import { RipartisciIrregolaritaComponent } from './disimpegni/components/ripartisci-irregolarita/ripartisci-irregolarita.component';
import { DatiProgettoComponent } from './dati-progetto/components/dati-progetto/dati-progetto.component';
import { DatiProgettoSoggettoComponent } from './dati-progetto/components/dati-progetto-soggetto/dati-progetto-soggetto.component';
import { ErogazioneComponent } from './erogazione/components/erogazione/erogazione.component';
import { NuovaErogazioneComponent } from './erogazione/components/nuova-erogazione/nuova-erogazione.component';
import { ErogazioneUtenteComponent } from './erogazione/components/erogazione-utente/erogazione-utente.component';
import { NuovaErogazioneUtenteComponent } from './erogazione/components/nuova-erogazione-utente/nuova-erogazione-utente.component';
import { TrasferimentiComponent } from './trasferimenti/components/trasferimenti/trasferimenti.component';
import { NuovoTrasferimentoComponent } from './trasferimenti/components/nuovo-trasferimento/nuovo-trasferimento.component';
import { GestioneFideiussioniComponent } from "./fideiussioni/components/gestione-fideiussioni/gestione-fideiussioni.component";
import { NuovaFideiussioneComponent } from "./fideiussioni/components/nuova-fideiussione/nuova-fideiussione.component";
import { DettaglioFideiussioneComponent } from "./fideiussioni/components/dettaglio-fideiussione/dettaglio-fideiussione.component";
import { CommunicazioneRinunciaComponent } from "./rinuncia/components/communicazione-rinuncia/communicazione-rinuncia.component";
import { InviaDichiarazioneRinunciaComponent } from "./rinuncia/components/invia-dichiarazione-rinuncia/invia-dichiarazione-rinuncia.component";
import { GestioneRevocheComponent } from "./revoche/components/gestione-revoche/gestione-revoche.component";
import { ModificaRevocaComponent } from "./revoche/components/modifica-revoca/modifica-revoca.component";
import { GestioneRecuperiComponent } from "./recuperi/components/gestione-recuperi/gestione-recuperi.component";
import { ModificaRecuperoComponent } from "./recuperi/components/modifica-recupero/modifica-recupero.component";
import { ChiusuraProgettoComponent } from "./chiusura-progetto/components/chiusura-progetto/chiusura-progetto.component";
import { ChiusuraUfficioProgettoComponent } from "./chiusura-progetto/components/chiusura-ufficio-progetto/chiusura-ufficio-progetto.component";
import { MonitoraggioControlliComponent } from "./monitoraggio-controlli/components/monitoraggio-controlli/monitoraggio-controlli.component";
import { RegistroControlliComponent } from "./registro-controlli/components/registro-controlli/registro-controlli.component";
import { RettificaForfettariaComponent } from "./registro-controlli/components/rettifica-forfettaria/rettifica-forfettaria.component";
import { RicercaEsitiControlliComponent } from './registro-controlli/components/ricerca-esiti-controlli/ricerca-esiti-controlli.component';
import { NuovoRecuperoComponent } from './recuperi/components/nuovo-recupero/nuovo-recupero.component';
import { InvioRichiestaErogazioneComponent } from './erogazione/components/invio-richiesta-erogazione/invio-richiesta-erogazione.component';
import { NuovaRevocaComponent } from './revoche/components/nuova-revoca/nuova-revoca.component';
import { SoppressioniComponent } from './soppressione/components/soppressioni/soppressioni.component';
import { VerificaRichiestaErogazioneComponent } from './verifica-richiesta-erogazione/components/verifica-richiesta-erogazione/verifica-richiesta-erogazione.component';

const routes: Routes = [
	{ path: '', redirectTo: 'errorRouting', pathMatch: 'full' },
	{ path: 'errorRouting', component: ErrorRoutingComponent },
	{
		path: 'drawer/:id', component: DrawerComponent,
		children: [
			//:id corrisponde ad :idProgetto, non utilizzarlo per altri parametri, altrimenti si crea un malfunzionamento sul
			//precaricamento dei dati in documenti di progetto
			{ path: 'monitoraggio-controlli', component: MonitoraggioControlliComponent },
			{ path: 'disimpegni/:id', component: DisimpegniComponent },
			{ path: 'disimpegni/nuovo-disimpegno/:id', component: NuovoDisimpegnoComponent },
			{ path: 'disimpegni/modifica-disimpegno/:id/:id2', component: NuovoDisimpegnoComponent },
			{ path: 'disimpegni/dettaglio-disimpegno/:id/:id2', component: NuovoDisimpegnoComponent },
			{ path: 'disimpegni/ripartisci-irregolarita/:id/:id2', component: RipartisciIrregolaritaComponent },
			{ path: 'dati-progetto/:id/:idBando', component: DatiProgettoComponent },
			{ path: 'dati-progetto/soggetti/nuovo-soggetto/:idProgetto/:idBando', component: DatiProgettoSoggettoComponent },
			{ path: 'dati-progetto/soggetti/dettaglio-soggetto/:idProgetto/:idBando/:progrSoggettoProgetto/:codiceFiscaleSoggetto', component: DatiProgettoSoggettoComponent },
			{ path: 'dati-progetto/soggetti/dettaglio-soggetto/:idProgetto/:idBando/:progrSoggettoProgetto/:progrSoggettiCorrelati/:idTipoSoggettoCorrelato/:codiceFiscaleSoggetto', component: DatiProgettoSoggettoComponent },
			{ path: 'dati-progetto/soggetti/modifica-soggetto/:idProgetto/:idBando/:progrSoggettoProgetto/:codiceFiscaleSoggetto', component: DatiProgettoSoggettoComponent },
			{ path: 'dati-progetto/soggetti/modifica-soggetto/:idProgetto/:idBando/:progrSoggettoProgetto/:progrSoggettiCorrelati/:idTipoSoggettoCorrelato/:codiceFiscaleSoggetto', component: DatiProgettoSoggettoComponent },
			{ path: 'avvio-lavori-richiesta-erogazione-acconto/:id/:idBando/:codCausale', component: ErogazioneUtenteComponent },
			{ path: 'avvio-lavori-richiesta-erogazione-acconto-nuova/:id/:idBando/:codCausale', component: NuovaErogazioneUtenteComponent },
			{ path: 'invio-richiesta-erogazione/:idProgetto/:idBando/:idDocumentoIndex', component: InvioRichiestaErogazioneComponent },
			{ path: 'erogazione/:id/:idBando', component: ErogazioneComponent },
			{ path: 'nuova-erogazione/:id/:idBando', component: NuovaErogazioneComponent },
			{ path: 'modifica-erogazione/:id/:idBando', component: NuovaErogazioneComponent },
			{ path: 'modifica-erogazione/:id/:idBando/:id2/:idModalitaAgevolazione', component: NuovaErogazioneComponent },
			{ path: 'dettaglio-erogazione/:id/:idBando/:id2/:idModalitaAgevolazione', component: NuovaErogazioneComponent },
			{ path: 'trasferimenti', component: TrasferimentiComponent },
			{ path: 'nuovo-trasferimento', component: NuovoTrasferimentoComponent },
			{ path: 'modifica-trasferimento/:idDocSpesa', component: NuovoTrasferimentoComponent },
			//{ path: 'nuovo-trasferimento/:idProgetto/:idBandoLinea/:idDocSpesa', component: NuovoTrasferimentoComponent }
			{ path: 'dettaglio-trasferimento/:idDocSpesa', component: NuovoTrasferimentoComponent },

			{ path: 'fideiussioni/:id', component: GestioneFideiussioniComponent },
			{ path: 'nuovaFideiussione/:id', component: NuovaFideiussioneComponent },
			{ path: 'dettaglioFideiussione/:id/:id1', component: DettaglioFideiussioneComponent },

			{ path: 'rinuncia/:idProgetto/:idBando', component: CommunicazioneRinunciaComponent },
			{ path: 'inviaDichRinuncia/:idProgetto/:idBando/:idDocumentoIndex', component: InviaDichiarazioneRinunciaComponent },

			{ path: 'revoche/:idProgetto/:idBandoLinea', component: GestioneRevocheComponent },
			{ path: 'nuovaRevoca/:idProgetto/:idBandoLinea', component: NuovaRevocaComponent },
			{ path: 'modificaRevoca/:idProgetto/:idBandoLinea/:id1', component: ModificaRevocaComponent },

			{ path: 'recuperi/:id', component: GestioneRecuperiComponent },
			{ path: 'modificaRecupero/:id/:id1', component: ModificaRecuperoComponent },
			{ path: 'nuovoRecupero/:idProgetto', component: NuovoRecuperoComponent },

			{ path: 'chiusura-progetto/:id', component: ChiusuraProgettoComponent },
			{ path: 'chiusura-ufficio-progetto/:id', component: ChiusuraUfficioProgettoComponent },
			{ path: 'chiusura-progetto-rinuncia/:id', component: ChiusuraUfficioProgettoComponent },

			{ path: 'controlli/:id', component: RegistroControlliComponent },
			{ path: 'rettifiche-forfettarie', component: RettificaForfettariaComponent },
			{ path: 'ricercaEsitiControlli', component: RicercaEsitiControlliComponent },

			{ path: 'soppressione/:idProgetto/:idBando', component: SoppressioniComponent },

			{ path: 'verifica-richiesta-erogazione/:idProgetto/:idBando/:idRichiestaErogazione', component: VerificaRichiestaErogazioneComponent },
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
