/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { CoreModule } from '../core/core.module';
import { SharedModule } from '../shared/shared.module';
import { RicercaRichiesteComponent } from './components/ricerca-richieste/ricerca-richieste.component';
import { RichiesteService } from './services/richieste.service';
import { DialogGestioneRichiesteComponent } from './components/dialog-gestione-richieste/dialog-gestione-richieste.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreModule
    ],
    exports: [
        RicercaRichiesteComponent
    ],
    declarations: [
        RicercaRichiesteComponent,
        DialogGestioneRichiesteComponent
    ],
    entryComponents: [
    ],
    providers: [
        RichiesteService
    ]
})
export class GestioneRichiesteModule { }
