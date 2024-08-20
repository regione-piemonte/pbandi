/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MaterialModule } from "../app-material.module";
import { CoreModule } from "../core/core.module";
import { SharedModule } from "../shared/shared.module";
import { IterAutorizzativiComponent } from './components/iter-autorizzativi/iter-autorizzativi.component';
import { RespingiIterComponent } from './components/respingi-iter/respingi-iter.component';
import { AllegatiIterComponent } from './components/allegati-iter/allegati-iter.component';
import { DettaglioIterComponent } from './components/dettaglio-iter/dettaglio-iter.component';
import { OkDialogComponent } from './components/iter-autorizzativi/ok-dialog/ok-dialog.component';
import { AvviaIterAutorizzativoComponent } from './components/avvia-iter-autorizzativo/avvia-iter-autorizzativo.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreModule,

    ],
    exports: [
        IterAutorizzativiComponent
    ],
    declarations: [
        IterAutorizzativiComponent,
        RespingiIterComponent,
        AllegatiIterComponent,
        DettaglioIterComponent,
        OkDialogComponent,
        AvviaIterAutorizzativoComponent
    ],
    entryComponents: [
    ],
    providers: [
    ]
})
export class IterAutorizzativiModule { }