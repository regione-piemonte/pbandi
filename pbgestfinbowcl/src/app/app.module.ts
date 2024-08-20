/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './app-material.module';
import { HttpClientModule } from '@angular/common/http';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { GestioneAnagraficaModule } from './gestione-anagrafica/gestione-anagrafica.module';
import { GestioneRichiesteModule } from './gestione-richieste/gestione-richieste.module';
import { InserisciRichiestaComponent } from './gestione-richieste/components/inserisci-richiesta/inserisci-richiesta/inserisci-richiesta.component';
import { StoricoRichiestaComponent } from './gestione-richieste/components/storico-richiesta/storico-richiesta.component';
import { ElaboraRichiestaComponent } from './gestione-richieste/components/elabora-richiesta/elabora-richiesta.component';
import { ElaboraDurcComponent } from './gestione-richieste/components/elabora-durc/elabora-durc.component';
import { ElaboraBdnaComponent } from './gestione-richieste/components/elabora-bdna/elabora-bdna.component';
import { ElaboraAntimafiaComponent } from './gestione-richieste/components/elabora-antimafia/elabora-antimafia.component';
import { RicercaProposteVarStCredModule } from './ricerca-proposte-var-st-cred/ricerca-proposte-var-st-cred.module';
import { CampionamentoModule } from './campionamento/campionamento.module';
import { MotivazioneComponent } from './gestione-richieste/components/motivazione/motivazione.component';
import { DettaglioDatiDimensioneComponent } from './gestione-anagrafica/components/dettaglio-dati-dimensione/dettaglio-dati-dimensione.component';
import { GestioneGaranzieModule } from './gestione-garanzie/gestione-garanzie.module';
import { GestioneCreditiModule } from './gestione-crediti/gestione-crediti.module';
import { AmbitoErogazioniModule } from './ambito-erogazioni/ambito-erogazioni/ambito-erogazioni.module';
import { ArchivioFileModule as ArchivioFileLibModule } from '@pbandi/common-lib';
import { RevocheModule } from './revoche/revoche.module';
import { ControlliModule } from './gestione-controlli/controlli.module';
import { IterAutorizzativiModule } from './iter-autorizzativi/iter-autorizzativi.module';
import { GestioneControdeduzioniModule } from './gestione-controdeduzioni/gestione-controdeduzioni.module';
import { GestioneContestazioniModule } from './gestione-contestazioni/gestione-contestazioni.module';
import { AcquisizioneCampionamentiModule } from './acquisizione-campionamenti/acquisizione-campionamenti.module';
import { GestioneRiassicurazioniModule } from './gestione-riassicurazioni/gestione-riassicurazioni.module';
import { DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule} from '@pbandi/common-lib';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    CoreModule,
    HttpClientModule,
    SharedModule,
    GestioneRichiesteModule,
    GestioneCreditiModule,
    GestioneControdeduzioniModule,
    GestioneAnagraficaModule,
    RicercaProposteVarStCredModule,
    CampionamentoModule,
    GestioneGaranzieModule,
    AmbitoErogazioniModule,
    ArchivioFileLibModule,
    RevocheModule,
    ControlliModule,
    IterAutorizzativiModule,
    GestioneContestazioniModule,
    AcquisizioneCampionamentiModule,
    GestioneRiassicurazioniModule,
    DatiProgettoAttivitaPregresseModule,
    VisualizzaContoEconomicoModule
  ],
  declarations: [
    AppComponent,
    InserisciRichiestaComponent,
    StoricoRichiestaComponent,
    ElaboraRichiestaComponent,
    ElaboraDurcComponent,
    ElaboraBdnaComponent,
    ElaboraAntimafiaComponent,
    MotivazioneComponent,
    DettaglioDatiDimensioneComponent

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
