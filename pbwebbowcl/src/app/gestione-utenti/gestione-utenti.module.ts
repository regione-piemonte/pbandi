/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


import { CommonModule, registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { GestioneUtentiComponent } from './components/gestione-utenti/gestione-utenti.component';
import { GestioneUtentiService } from './services/gestione-utenti.service';
import { CaricaUtentiComponent } from './components/carica-utenti-dialog/carica-utenti.component';
import { NuovoUtenteComponent } from './components/nuovo-utente-dialog/nuovo-utente.component';
import { DettaglioUtenteComponent } from './components/dettaglio-utente/dettaglio-utente.component';
import { GestioneBenefProgComponent } from './components/gestione-benef-prog/gestione-benef-prog.component';
import { NuovoModificaAssocBenProgDialogComponent } from './components/nuovo-modifica-assoc-ben-prog-dialog/nuovo-modifica-assoc-ben-prog-dialog.component';
registerLocaleData(localeIt, 'it');

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        GestioneUtentiComponent,
        CaricaUtentiComponent,
        NuovoUtenteComponent,
        DettaglioUtenteComponent,
        GestioneBenefProgComponent,
        NuovoModificaAssocBenProgDialogComponent
    ],
    declarations: [
        GestioneUtentiComponent,
        CaricaUtentiComponent,
        NuovoUtenteComponent,
        DettaglioUtenteComponent,
        GestioneBenefProgComponent,
        NuovoModificaAssocBenProgDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        GestioneUtentiService
    ]

})
export class GestioneUtentiModule { }