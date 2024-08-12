import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { GestioneIntegrazioniComponent } from './components/gestione-integrazioni/gestione-integrazioni.component';
import { GestioneIntegrazioniService } from './services/gestione-integrazioni.service';
import { ArchivioFileModule as ArchivioFileLibModule, CoreModule as CoreLibModule, HelpModule as HelpLibModule } from '@pbandi/common-lib';
import { IntegrazioneProcedimentoRevocaComponent } from './components/integrazione-procedimento-revoca/integrazione-procedimento-revoca.component';
import { ConfermaInvioIntegrazioneComponent } from './components/conferma-invio-integrazione/conferma-invio-integrazione.component';
import { IntegrazioneDichiarazioneSpesaComponent } from './components/integrazione-dichiarazione-spesa/integrazione-dichiarazione-spesa.component';
import { IntegrazioneAiControlliComponent } from './components/integrazione-ai-controlli/integrazione-ai-controlli.component';
import { RichiestaProrogaIntegrazioneComponent } from './components/richiesta-proroga-integrazione/richiesta-proroga-integrazione.component';
import { DettaglioComponent } from './components/dettaglio/dettaglio.component';
import { AllegatiDocSospesiQuietanzeDialogComponent } from './components/allegati-doc-sospesi-quietanze-dialog/allegati-doc-sospesi-quietanze-dialog.component';


@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
    ArchivioFileLibModule,
    CoreLibModule,
    HelpLibModule
  ],
  exports: [
    GestioneIntegrazioniComponent
  ],
  declarations: [
    GestioneIntegrazioniComponent,
    IntegrazioneProcedimentoRevocaComponent,
    ConfermaInvioIntegrazioneComponent,
    IntegrazioneDichiarazioneSpesaComponent,
    IntegrazioneAiControlliComponent,
    RichiestaProrogaIntegrazioneComponent,
    DettaglioComponent,
    AllegatiDocSospesiQuietanzeDialogComponent
  ],
  entryComponents: [
  ],
  providers: [
    GestioneIntegrazioniService
  ]
})
export class GestioneIntegrazioniModule { }
