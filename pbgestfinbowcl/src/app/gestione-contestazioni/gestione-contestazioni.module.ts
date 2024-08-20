/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { CoreModule, MaterialModule } from "@pbandi/common-lib";
import { SharedModule } from "../shared/shared.module";
import { ContestazioniComponent } from "./components/contestazioni/contestazioni.component";
import { InserisciContestazioneComponent } from './components/inserisci-contestazione/inserisci-contestazione.component';
import { AllegatiContestazioneComponent } from './components/allegati-contestazione/allegati-contestazione.component';
import { ConfermaInvioContestazioneComponent } from './components/conferma-invio-contestazione/conferma-invio-contestazione.component';
import { EliminaContestazioneComponent } from './components/elimina-contestazione/elimina-contestazione.component';
import { DettaglioAllegatiContestazioniComponent } from './components/dettaglio-allegati-contestazioni/dettaglio-allegati-contestazioni.component';
import { ArchivioFileModule as ArchivioFileLibModule } from '@pbandi/common-lib';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreModule,
        ArchivioFileLibModule
    ],
    exports: [
      ContestazioniComponent
    ],
    declarations: [
      ContestazioniComponent,
      InserisciContestazioneComponent,
      AllegatiContestazioneComponent,
      ConfermaInvioContestazioneComponent,
      EliminaContestazioneComponent,
      DettaglioAllegatiContestazioniComponent
    ],
    entryComponents: [
    ],
})

export class GestioneContestazioniModule { }