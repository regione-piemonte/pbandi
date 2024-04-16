/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { AttivitaComponent } from './components/attivita/attivita.component';
import { AttivitaService } from './services/attivita.service';
import { NotificheComponent } from './components/notifiche/notifiche.component';
import { DettaglioNotificaDialog } from './components/dettaglio-notifica-dialog/dettaglio-notifica-dialog';
import { HelpModule as HelpLibModule, CoreModule as CoreLibModule, DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreLibModule,
        HelpLibModule,
        DatiProgettoAttivitaPregresseModule
    ],
    exports: [
        AttivitaComponent,
        NotificheComponent,
        DettaglioNotificaDialog
    ],
    declarations: [
        AttivitaComponent,
        NotificheComponent,
        DettaglioNotificaDialog
    ],
    entryComponents: [
    ],
    providers: [
        AttivitaService
    ]
})
export class AttivitaModule { }