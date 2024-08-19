/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PipeModule } from 'src/app/shared/pipe.module';
import { DirectiveModule } from 'src/app/shared/directive.module';
import { SharedModule } from '../shared/shared.module';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { ErogazioneUtenteComponent } from './components/erogazione-utente/erogazione-utente.component';
import { NuovaErogazioneUtenteComponent } from './components/nuova-erogazione-utente/nuova-erogazione-utente.component';
import { ErogazioneComponent } from './components/erogazione/erogazione.component';
import { NuovaErogazioneComponent } from './components/nuova-erogazione/nuova-erogazione.component';
import { ErogazioneService } from './services/erogazione.service';
import { DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule, ArchivioFileModule as ArchivioFileLibModule } from '@pbandi/common-lib';
import { Erogazione2Service } from './services/erogazione2.service';
import { ConfermaRichiestaDialogComponent } from './components/conferma-richiesta-dialog/conferma-richiesta-dialog.component';
import { InvioRichiestaErogazioneComponent } from './components/invio-richiesta-erogazione/invio-richiesta-erogazione.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        AppRoutingModule,
        PipeModule,
        DirectiveModule,
        VisualizzaContoEconomicoModule,
        DatiProgettoAttivitaPregresseModule,
        ArchivioFileLibModule
    ],
    exports: [
        ErogazioneUtenteComponent,
        NuovaErogazioneUtenteComponent,
        ErogazioneComponent,
        NuovaErogazioneComponent,
        ConfermaRichiestaDialogComponent,
        InvioRichiestaErogazioneComponent
    ],
    declarations: [
        ErogazioneUtenteComponent,
        NuovaErogazioneUtenteComponent,
        ErogazioneComponent,
        NuovaErogazioneComponent,
        ConfermaRichiestaDialogComponent,
        InvioRichiestaErogazioneComponent,
    ],
    entryComponents: [
    ],
    providers: [
        ErogazioneService,
        Erogazione2Service
    ]
})
export class ErogazioneModule { }
