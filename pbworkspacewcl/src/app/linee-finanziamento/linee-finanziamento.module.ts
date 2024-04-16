/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { LineeFinanziamentoComponent } from './components/linee-finanziamento/linee-finanziamento.component';
import { AvvioProgettoComponent } from './components/avvio-progetto/avvio-progetto.component';
import { ProgettoComponent } from './components/progetto/progetto.component';
import { LineeFinanziamentoService } from './services/linee-finanziamento.service';
import { SchedaProgettoService } from './services/scheda-progetto.service';
import { BeneficiarioProgettoDialogComponent } from './components/beneficiario-progetto-dialog/beneficiario-progetto-dialog.component';
import { RapprLegaleProgettoDialogComponent } from './components/rappr-legale-progetto-dialog/rappr-legale-progetto-dialog.component';
import { SedeInterventoDialogComponent } from './components/sede-intervento-dialog/sede-intervento-dialog.component';
import { NavigationLineeFinanziamentoService } from './services/navigation-linee-finanziamento.service';
import { NavigationAvvioProgettoService } from './services/navigation-avvio-progetto.service';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        LineeFinanziamentoComponent,
        AvvioProgettoComponent,
        ProgettoComponent,
        BeneficiarioProgettoDialogComponent,
        RapprLegaleProgettoDialogComponent,
        SedeInterventoDialogComponent
    ],
    declarations: [
        LineeFinanziamentoComponent,
        AvvioProgettoComponent,
        ProgettoComponent,
        BeneficiarioProgettoDialogComponent,
        RapprLegaleProgettoDialogComponent,
        SedeInterventoDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        LineeFinanziamentoService,
        SchedaProgettoService,
        NavigationLineeFinanziamentoService,
        NavigationAvvioProgettoService
    ]
})
export class LineeFinanziamentoModule { }