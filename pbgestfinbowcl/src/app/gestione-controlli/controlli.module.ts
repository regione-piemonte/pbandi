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
import { RicercaAltriControlliComponent } from "./components/ricerca-altri-controlli/ricerca-altri-controlli.component";
import { RicercaControlliProgettiFinpComponent } from "./components/ricerca-controlli-progetti-finp/ricerca-controlli-progetti-finp.component";
import { ControlliService } from "./services/controlli.service";
import { InserimentoAltriControlliComponent } from './components/inserimento-altri-controlli/inserimento-altri-controlli.component';
import { GestioneControlliProgettiFinpComponent } from "./components/gestione-controlli-progetti-finp/gestione-controlli-progetti-finp.component";
import { DialogGestioneControlliComponent } from "./components/dialog-gestione-controlli/dialog-gestione-controlli.component";
import { ArchivioFileModule as ArchivioFileLibModule } from '@pbandi/common-lib';
import { DialogEditAltriControlliComponent } from './components/dialog-edit-altri-controlli/dialog-edit-altri-controlli.component';
import { GestioneAltriControlliComponent } from './components/gestione-altri-controlli/gestione-altri-controlli.component';
import { NavigationControlliService } from "./services/navigation-controlli.service";

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
        RicercaAltriControlliComponent,
        RicercaControlliProgettiFinpComponent,
        InserimentoAltriControlliComponent,
        GestioneControlliProgettiFinpComponent,
        DialogGestioneControlliComponent

    ],
    declarations: [
        RicercaAltriControlliComponent,
        RicercaControlliProgettiFinpComponent,
        InserimentoAltriControlliComponent,
        GestioneControlliProgettiFinpComponent,
        DialogGestioneControlliComponent,
        DialogEditAltriControlliComponent,
        GestioneAltriControlliComponent
    ],
    entryComponents: [
    ],
    providers: [
        ControlliService, 
        NavigationControlliService
    ]
})
export class ControlliModule { }