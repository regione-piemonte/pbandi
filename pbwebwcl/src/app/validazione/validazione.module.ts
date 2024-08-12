import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ValidazioneComponent } from './components/validazione/validazione.component';
import { ValidazioneMassivaDialogComponent } from './components/validazione-massiva-dialog/validazione-massiva-dialog.component';
import { RichiestaIntegrazioneDialogComponent } from './components/richiesta-integrazione-dialog/richiesta-integrazione-dialog.component';
import { EsameDocumentoComponent } from './components/esame-documento/esame-documento.component';
import { ValidazioneService } from './services/validazione.service';
import { AllegatiDichiarazioneSpesaDialogComponent } from './components/allegati-dichiarazione-spesa-dialog/allegati-dichiarazione-spesa-dialog.component';
import { NavigationValidazioneService } from './services/navigation-validazione.service';
import { ChiusuraValidazioneDialogComponent } from './components/chiusura-validazione-dialog/chiusura-validazione-dialog.component';
import { ChecklistChiudiValidazioneComponent } from './components/checklist-chiudi-validazione/checklist-chiudi-validazione.component';
import { ArchivioFileModule as ArchivioFileLibModule, CoreModule as CoreLibModule, DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule } from '@pbandi/common-lib';
import { ChecklistModule } from '../checklist/checklist.module';
import { ChecklistChiudiValidazioneConfermaComponent } from './components/checklist-chiudi-validazione-conferma/checklist-chiudi-validazione-conferma.component';
import { QuillModule } from 'ngx-quill';
import { EsitoValidazioneDichSpesaComponent } from './components/esito-validazione-dich-spesa/esito-validazione-dich-spesa.component';
import { GestioneProrogaComponent } from './components/gestione-proroga/gestione-proroga.component';
import { ConfermaChiudiIntegrazioneComponent } from './components/conferma-chiudi-integrazione/conferma-chiudi-integrazione.component';
import { DettaglioRichiestaIntegrazioneDialogComponent } from './components/dettaglio-richiesta-integrazione-dialog/dettaglio-richiesta-integrazione-dialog.component';
import { AllegatiDettaglioDocComponent } from './components/allegati-dettaglio-doc/allegati-dettaglio-doc.component';
import { DatiGenAffidDettaglioDocComponent } from './components/dati-gen-affid-dettaglio-doc/dati-gen-affid-dettaglio-doc.component';
import { NoteCreditoDettaglioDocComponent } from './components/note-credito-dettaglio-doc/note-credito-dettaglio-doc.component';
import { RichiestaIntegrazioneBoxComponent } from './components/richiesta-integrazione-box/richiesta-integrazione-box.component';
import { RichiestaIntegrazioneButtonComponent } from './components/richiesta-integrazione-button/richiesta-integrazione-button.component';

@NgModule({
  imports: [
    MaterialModule,
    CommonModule,
    FormsModule,
    SharedModule,
    ArchivioFileLibModule,
    CoreLibModule,
    ChecklistModule,
    VisualizzaContoEconomicoModule,
    DatiProgettoAttivitaPregresseModule,
    QuillModule
  ],
  exports: [
    ValidazioneComponent,
    ValidazioneMassivaDialogComponent,
    RichiestaIntegrazioneDialogComponent,
    EsameDocumentoComponent,
    AllegatiDichiarazioneSpesaDialogComponent,
    ChiusuraValidazioneDialogComponent,
    ChecklistChiudiValidazioneComponent,
    ChecklistChiudiValidazioneConfermaComponent,
    DettaglioRichiestaIntegrazioneDialogComponent,
    AllegatiDettaglioDocComponent,
    DatiGenAffidDettaglioDocComponent,
    NoteCreditoDettaglioDocComponent,
    RichiestaIntegrazioneBoxComponent,
    RichiestaIntegrazioneButtonComponent
  ],
  declarations: [
    ValidazioneComponent,
    ValidazioneMassivaDialogComponent,
    RichiestaIntegrazioneDialogComponent,
    EsameDocumentoComponent,
    AllegatiDichiarazioneSpesaDialogComponent,
    ChiusuraValidazioneDialogComponent,
    ChecklistChiudiValidazioneComponent,
    ChecklistChiudiValidazioneConfermaComponent,
    EsitoValidazioneDichSpesaComponent,
    GestioneProrogaComponent,
    ConfermaChiudiIntegrazioneComponent,
    DettaglioRichiestaIntegrazioneDialogComponent,
    AllegatiDettaglioDocComponent,
    DatiGenAffidDettaglioDocComponent,
    NoteCreditoDettaglioDocComponent,
    RichiestaIntegrazioneBoxComponent,
    RichiestaIntegrazioneButtonComponent
  ],
  entryComponents: [
  ],
  providers: [
    ValidazioneService,
    NavigationValidazioneService,
  ]
})
export class ValidazioneModule { }
