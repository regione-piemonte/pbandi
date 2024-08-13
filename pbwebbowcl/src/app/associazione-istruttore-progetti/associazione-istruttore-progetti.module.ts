/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { AssociazioneIstruttoreProgettiService } from './services/associazione-istruttore-progetti.service';
import { RicercaIstruttoreComponent } from './components/ricerca-istruttore/ricerca-istruttore.component';
import { GestioneAssociazioneComponent } from './components/gestione-associazione/gestione-associazione.component';
import { AssociaNuovoProgettoComponent } from './components/associa-nuovo-progetto/associa-nuovo-progetto.component';
import { AssociaNuovoBandoComponent } from './components/associa-nuovo-bando/associa-nuovo-bando.component';
import { NavigationAssociazioneIstruttoreProgettiService } from './services/navigation-associazione-istruttore-progetti.service';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [

    ],
    declarations: [
        RicercaIstruttoreComponent,
        GestioneAssociazioneComponent,
        AssociaNuovoProgettoComponent,
        AssociaNuovoBandoComponent],
    entryComponents: [
        
    ],
    providers: [
        AssociazioneIstruttoreProgettiService,
        NavigationAssociazioneIstruttoreProgettiService
    ]
})
export class AssociazioneIstruttoreProgettiModule { }