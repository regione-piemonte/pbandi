import { ArchivioFileContainerComponent } from './archivio-file-container/components/archivio-file-container/archivio-file-container.component';
import { DocumentoDiSpesaComponent } from './rendicontazione/components/documento-di-spesa/documento-di-spesa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { ErrorRoutingComponent } from './core/components/error-routing/error-routing.component';
import { GestioneFornitoriComponent } from './rendicontazione/components/gestione-fornitori/gestione-fornitori.component';
import { RendicontazioneComponent } from './rendicontazione/components/rendicontazione/rendicontazione.component';
import { DrawerComponent } from './shared/components/drawer/drawer.component';
import { ChecklistComponent } from './checklist/components/checklist/checklist.component';
import { NuovaChecklistComponent } from './checklist/components/nuova-checklist/nuova-checklist.component';
import { DichiarazioneDiSpesaComponent } from './rendicontazione/components/dichiarazione-di-spesa/dichiarazione-di-spesa.component';
import { InvioDichiarazioneDiSpesaComponent } from './rendicontazione/components/invio-dichiarazione-di-spesa/invio-dichiarazione-di-spesa.component';
import { ValidazioneComponent } from './validazione/components/validazione/validazione.component';
import { EsameDocumentoComponent } from './validazione/components/esame-documento/esame-documento.component';
import { GestioneSpesaValidataComponent } from './gestione-spesa-validata/components/gestione-spesa-validata/gestione-spesa-validata.component';
import { RettificaDocumentoDiSpesaComponent } from './gestione-spesa-validata/components/rettifica-documento-di-spesa/rettifica-documento-di-spesa.component';
import { DocumentiProgettoComponent } from './documenti-progetto/components/documenti-progetto/documenti-progetto.component';
import { IntegrazioneRendicontazioneComponent } from './integrazione-rendicontazione/components/integrazione-rendicontazione/integrazione-rendicontazione.component';
import { GestioneChecklistSpesaValidataComponent } from './gestione-spesa-validata/components/gestione-checklist-spesa-validata/gestione-checklist-spesa-validata.component';
import { ChecklistChiudiValidazioneComponent } from './validazione/components/checklist-chiudi-validazione/checklist-chiudi-validazione.component';
import { GestioneEconomieComponent } from './gestione-economie/components/gestione-economie/gestione-economie.component';
import { EconomiaComponent } from './gestione-economie/components/economia/economia.component';
import { ChecklistChiudiValidazioneConfermaComponent } from './validazione/components/checklist-chiudi-validazione-conferma/checklist-chiudi-validazione-conferma.component';
import { GestioneIntegrazioniComponent } from './gestione-integrazioni/components/gestione-integrazioni/gestione-integrazioni.component';
import { IntegrazioneProcedimentoRevocaComponent } from './gestione-integrazioni/components/integrazione-procedimento-revoca/integrazione-procedimento-revoca.component';
import { IntegrazioneDichiarazioneSpesaComponent } from './gestione-integrazioni/components/integrazione-dichiarazione-spesa/integrazione-dichiarazione-spesa.component';
import { IntegrazioneAiControlliComponent } from './gestione-integrazioni/components/integrazione-ai-controlli/integrazione-ai-controlli.component';
import { RendicontazioneA20Component } from './rendicontazione-a20/components/rendicontazione-a20/rendicontazione-a20.component';
import { GestioneChecklistControlliComponent } from './checklist-area-controlli/components/gestione-checklist-controlli/gestione-checklist-controlli.component';
import { RelazioneTecnicaComponent } from './relazione-tecnica/components/relazione-tecnica/relazione-tecnica.component';
import { ValidazioneRelazioneTecnicaComponent } from './validazione-relazione-tecnica/components/validazione-relazione-tecnica/validazione-relazione-tecnica.component';

const routes: Routes = [
  { path: '', redirectTo: 'errorRouting', pathMatch: 'full' },
  { path: 'errorRouting', component: ErrorRoutingComponent },
  {
    path: 'drawer/:id', component: DrawerComponent,
    children: [
      { path: 'rendicontazione/:idProgetto/:idBandoLinea', component: RendicontazioneComponent },
      { path: 'rendicontazione/:integrativa/:idProgetto/:idBandoLinea', component: RendicontazioneComponent },
      { path: 'gestioneFornitori/:idProgetto/:idBandoLinea', component: GestioneFornitoriComponent },

      { path: 'documentoDiSpesa/:idProgetto/:idBandoLinea', component: DocumentoDiSpesaComponent },
      { path: 'documentoDiSpesa/:idProgetto/:idBandoLinea/:idDocSpesa', component: DocumentoDiSpesaComponent },

      { path: 'dichiarazioneDiSpesa/:idProgetto/:idBandoLinea', component: DichiarazioneDiSpesaComponent },

      { path: 'invioDichiarazioneDiSpesa/:idProgetto/:idBandoLinea/:idDichiarazioneDiSpesa/:idDocumentoIndex', component: InvioDichiarazioneDiSpesaComponent },
      { path: 'validazione/:idProgetto/:idBandoLinea/:idDichiarazioneDiSpesa', component: ValidazioneComponent },
      { path: 'checklistChiudiValidazione/:idProgetto/:idBandoLinea/:idDichiarazioneDiSpesa', component: ChecklistChiudiValidazioneComponent },
      { path: 'checklistChiudiValidazioneConferma/:idProgetto/:idBandoLinea/:idDichiarazioneDiSpesa', component: ChecklistChiudiValidazioneConfermaComponent },
      { path: 'esameDocumento/:idProgetto/:idBandoLinea/:idDichiarazioneDiSpesa/:idDocumentoDiSpesa', component: EsameDocumentoComponent },
      { path: 'gestioneSpesaValidata/:idProgetto/:idBandoLinea', component: GestioneSpesaValidataComponent },
      { path: 'rettificaDocumentoDiSpesa/:idProgetto/:idBandoLinea/:idDocumentoDiSpesa/:idDichiarazioneDiSpesa', component: RettificaDocumentoDiSpesaComponent },
      { path: 'gestioneChecklistSpesaValidata/:idProgetto/:idBandoLinea/:idDocumentoDiSpesa/:idDichiarazioneDiSpesa', component: GestioneChecklistSpesaValidataComponent },
      { path: 'gestioneChecklistSpesaValidata/:idProgetto/:idBandoLinea', component: GestioneChecklistSpesaValidataComponent },
      { path: 'integrazioneRendicontazione/:idProgetto/:idBandoLinea', component: IntegrazioneRendicontazioneComponent },
      { path: 'archivioFile', component: ArchivioFileContainerComponent },
      { path: 'checklist/:idProgetto/:idBandoLinea', component: ChecklistComponent },
      { path: 'nuovaChecklist/:idProgetto/:idBandoLinea', component: NuovaChecklistComponent },
      { path: 'modificaChecklist/:idProgetto/:idBandoLinea/:idDocumentoIndex/:codiceTipo/:idDichiarazione', component: NuovaChecklistComponent },
      { path: 'documentiProgetto', component: DocumentiProgettoComponent },
      { path: 'documentiProgetto/:idProgetto', component: DocumentiProgettoComponent },
      { path: 'gestioneEconomie', component: GestioneEconomieComponent },
      { path: 'economia', component: EconomiaComponent },
      { path: 'economia/:idEconomia', component: EconomiaComponent },
      { path: 'gestioneIntegrazioni/:idProgetto/:idBandoLinea', component: GestioneIntegrazioniComponent },
      { path: 'integrazione-procedimento-revoca', component: IntegrazioneProcedimentoRevocaComponent },
      { path: 'integrazione-ai-controlli', component: IntegrazioneAiControlliComponent },
      { path: 'integrazione-rendicontazione', component: IntegrazioneDichiarazioneSpesaComponent },
      { path: 'checklistControlli/:idProgetto/:idBandoLinea/:idControllo/:idStatoChecklist', component: GestioneChecklistControlliComponent },
      { path: 'rendicontazione-a20/:idProgetto/:idBandoLinea', component: RendicontazioneA20Component },
      { path: 'rendicontazione-a20/:integrativa/:idProgetto/:idBandoLinea', component: RendicontazioneA20Component },
      { path: 'relazioneTecnica/:idProgetto/:idBandoLinea', component: RelazioneTecnicaComponent },
      { path: 'validazioneRelazioneTecnica/:idProgetto/:idBandoLinea', component: ValidazioneRelazioneTecnicaComponent },
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
