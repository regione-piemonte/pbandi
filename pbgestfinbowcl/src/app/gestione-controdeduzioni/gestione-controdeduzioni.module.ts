/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { CoreModule, MaterialModule } from "@pbandi/common-lib";
import { SharedModule } from "../shared/shared.module";
import { ControdeduzioniComponent } from './components/controdeduzioni/controdeduzioni.component';
import { DialogAddControdeduzione } from "./components/dialog-add-controdeduzione/dialog-add-controdeduzione.component";
import { DialogEditAllegati } from "./components/dialog-edit-allegati/dialog-edit-allegati.component";
import { DialogAddProroga } from "./components/dialog-add-proroga/dialog-add-proroga.component";
import { AllegatiControdeduzioneComponent } from "./components/allegati-controdeduzioni/allegati-controdeduzioni.component";
import { EliminaControdeduzioneComponent } from "./components/elimina-controdeduzione/elimina-controdeduzione.component";
import { AccessoAgliAttiComponent } from "./components/accesso-agli-atti/accesso-agli-atti.component";
import { InviaControdeduzione } from "./components/invia-controdeduzione/invia-controdeduzione.component";

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreModule
    ],
    exports: [
        ControdeduzioniComponent,
        EliminaControdeduzioneComponent,
        EliminaControdeduzioneComponent,
        AccessoAgliAttiComponent,
        InviaControdeduzione,
        AllegatiControdeduzioneComponent,
        DialogAddControdeduzione,
        DialogEditAllegati,
        DialogAddProroga
    ],
    declarations: [
        ControdeduzioniComponent,
        EliminaControdeduzioneComponent,
        AccessoAgliAttiComponent,
        InviaControdeduzione,
        AllegatiControdeduzioneComponent,
        DialogAddControdeduzione,
        DialogEditAllegati,
        DialogAddProroga
    ],
    entryComponents: [
    ],
})

export class GestioneControdeduzioniModule { }