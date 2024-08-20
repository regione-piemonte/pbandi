/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AnagraficaBeneficiarioComponent } from './components/anagrafica-beneficiario/anagrafica-beneficiario.component';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { CoreModule } from '@pbandi/common-lib';
import { AnagraficaBeneficiarioService } from './services/anagrafica-beneficiario.service';
import { ModificaAnagraficaService } from './services/modifica-anagrafica.service';
import { ModificaAnagraficaPFComponent } from './components/modifica-anagrafica-pf/modifica-anagrafica-pf.component';
import { ModificaAnagraficaEGComponent } from './components/modifica-anagrafica-eg/modifica-anagrafica-eg.component';
import { DatiDomandaService } from './services/dati-domanda-service';
import { DatiDomandaEGComponent } from './components/dati-domanda-eg/dati-domanda-eg.component';
import { DatiDomandaPFComponent } from './components/dati-domanda-pf/dati-domanda-pf.component';
import { ModificaDatiDomandaEGComponent } from './components/modifica-dati-domanda-eg/modifica-dati-domanda-eg.component';
import { ModificaDatiDomandaPFComponent } from './components/modifica-dati-domanda-pf/modifica-dati-domanda-pf.component';
import { SuggestionService } from './services/suggestion-service.service';
import { RicercaSoggettiComponent } from './components/ricerca-soggetti/ricerca-soggetti.component';
import { StoricoBenficiarioService } from './services/storico-benficiario.service';
import { StoricoBeneficiarioComponent } from './components/storico-beneficiario/storico-beneficiario.component';
import { VisualizzaStoricoComponent } from './components/visualizza-storico/visualizza-storico.component';
import { VisualizzaStoricoPfComponent } from './components/visualizza-storico-pf/visualizza-storico-pf.component';
import { StoricoBlocchiAttiviComponent } from './components/storico-blocchi-attivi/storico-blocchi-attivi.component';
import { EditBloccoComponent } from './components/edit-blocco/edit-blocco.component';
import { VisualizzaSoggCorrDipDomComponent } from './components/visualizza-sogg-corr-dip-dom/visualizza-sogg-corr-dip-dom.component';
import { ModificaSoggCorrDipDomComponent } from './components/modifica-sogg-corr-dip-dom/modifica-sogg-corr-dip-dom.component';
import { ListaSoggettiCorrelatiComponent } from './commons/lista-soggetti-correlati/lista-soggetti-correlati.component';
import { PersonaFisicaContainerComponent } from './components/anagrafica-beneficiario/persona-fisica-container/persona-fisica-container.component';
import { PersonaGiuridicaContainerComponent } from './components/anagrafica-beneficiario/persona-giuridica-container/persona-giuridica-container.component';
import { PersonaGiuridicaIndipendenteDomandaComponent } from './components/anagrafica-beneficiario/persona-giuridica-indipendente-domanda/persona-giuridica-indipendente-domanda.component';
import { PersonaFisicaIndipendenteDomandaComponent } from './components/anagrafica-beneficiario/persona-fisica-indipendente-domanda/persona-fisica-indipendente-domanda.component';
import { BlocchiSoggettoDomandaComponent } from './components/blocchi-soggetto-domanda/blocchi-soggetto-domanda.component';
import { StoricoBlocchiDomandaDialogComponent } from './components/storico-blocchi-domanda-dialog/storico-blocchi-domanda-dialog.component';
import { DimensioneImpresaDomandaComponent } from './components/dimensione-impresa-domanda/dimensione-impresa-domanda.component';
import { DsanDomandaComponent } from './components/dsan-domanda/dsan-domanda.component';
import { VisualizzaSoggCorrDipDomPfComponent } from './components/visualizza-sogg-corr-dip-dom-pf/visualizza-sogg-corr-dip-dom-pf.component';
import { DialogDettaglioAltreSediEGComponent } from './components/dialog-dettaglio-altre-sedi-EG/dialog-dettaglio-altre-sedi-EG.component';


@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
    CoreModule
  ],
  exports: [
    AnagraficaBeneficiarioComponent,
    ModificaAnagraficaPFComponent,
    ModificaAnagraficaEGComponent,
    DatiDomandaEGComponent,
    DatiDomandaPFComponent,
    ModificaDatiDomandaEGComponent,
    ModificaDatiDomandaPFComponent,
    RicercaSoggettiComponent,
    StoricoBeneficiarioComponent,
    VisualizzaStoricoComponent,
    VisualizzaStoricoPfComponent,
    StoricoBlocchiAttiviComponent,
    EditBloccoComponent,
    VisualizzaSoggCorrDipDomComponent,
    ModificaSoggCorrDipDomComponent,
    StoricoBlocchiDomandaDialogComponent,
    DimensioneImpresaDomandaComponent,
    DsanDomandaComponent,
    DialogDettaglioAltreSediEGComponent
  ],
  declarations: [
    AnagraficaBeneficiarioComponent,
    ModificaAnagraficaPFComponent,
    ModificaAnagraficaEGComponent,
    DatiDomandaEGComponent,
    DatiDomandaPFComponent,
    ModificaDatiDomandaEGComponent,
    ModificaDatiDomandaPFComponent,
    RicercaSoggettiComponent,
    StoricoBeneficiarioComponent,
    VisualizzaStoricoComponent,
    VisualizzaStoricoPfComponent,
    StoricoBlocchiAttiviComponent,
    EditBloccoComponent,
    VisualizzaSoggCorrDipDomComponent,
    ModificaSoggCorrDipDomComponent,
    ListaSoggettiCorrelatiComponent,
    PersonaFisicaContainerComponent,
    PersonaGiuridicaContainerComponent,
    PersonaGiuridicaIndipendenteDomandaComponent,
    PersonaFisicaIndipendenteDomandaComponent,
    BlocchiSoggettoDomandaComponent,
    StoricoBlocchiDomandaDialogComponent,
    DimensioneImpresaDomandaComponent,
    DsanDomandaComponent,
    VisualizzaSoggCorrDipDomPfComponent,
    DialogDettaglioAltreSediEGComponent
  ],
  entryComponents: [
  ],
  providers: [
    AnagraficaBeneficiarioService,
    ModificaAnagraficaService,
    DatiDomandaService,
    SuggestionService,
    StoricoBenficiarioService
  ]
})

export class GestioneAnagraficaModule { }
