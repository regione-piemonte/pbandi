import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { IntegrazioneRendicontazioneComponent } from './components/integrazione-rendicontazione/integrazione-rendicontazione.component';
import { IntegrazioneRendicontazioneService } from './services/integrazione-rendicontazione.service';
import { ArchivioFileModule as ArchivioFileLibModule, CoreModule as CoreLibModule, HelpModule as HelpLibModule } from '@pbandi/common-lib';


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
    IntegrazioneRendicontazioneComponent
  ],
  declarations: [
    IntegrazioneRendicontazioneComponent
  ],
  entryComponents: [
  ],
  providers: [
    IntegrazioneRendicontazioneService
  ]
})
export class IntegrazioneRendicontazioneModule { }
