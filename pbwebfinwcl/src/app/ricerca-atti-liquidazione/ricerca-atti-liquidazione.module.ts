/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { RicercaAttiLiquidazioneComponent } from './components/ricerca-atti-liquidazione/ricerca-atti-liquidazione.component';
import { DettaglioAttoLiquidazioneComponent } from './components/dettaglio-atto-liquidazione/dettaglio-atto-liquidazione.component';
import { NavigationRicercaAttiService } from './services/navigation-ricerca-atti.service';
import { RicercaAttiService } from './services/ricerca-atti.service';
import { SharedModule } from '../shared/shared.module';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        AppRoutingModule,
        SharedModule
    ],
    exports: [
        RicercaAttiLiquidazioneComponent
    ],
    declarations: [
        RicercaAttiLiquidazioneComponent,
        DettaglioAttoLiquidazioneComponent
    ],
    entryComponents: [
    ],
    providers: [
        NavigationRicercaAttiService,
        RicercaAttiService
    ]
})
export class RicercaAttiLiquidazioneModule { }