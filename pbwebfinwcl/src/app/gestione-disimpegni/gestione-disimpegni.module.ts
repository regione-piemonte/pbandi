/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { GestioneDisimpegniComponent } from './components/gestione-disimpegni/gestione-disimpegni.component';
import { DettaglioImpegnoComponent } from './components/dettaglio-impegno/dettaglio-impegno.component';
import { AssociazioniPerBeneficiarioComponent } from './components/associazioni-per-beneficiario/associazioni-per-beneficiario.component';
import { AssociazioniComponent } from './components/associazioni/associazioni.component';
import { NuovaAssociazioneComponent } from './components/nuova-associazione/nuova-associazione.component';
import { ImportaImpegnoDialogComponent } from './components/importa-impegno-dialog/importa-impegno-dialog.component';
import { NavigationGestioneImpegniService } from './services/navigation-gestione-impegni.service';
import { GestioneImpegniService } from './services/gestione-impegni.service';
import { SharedModule } from '../shared/shared.module';
import { EliminaDialogComponent } from './components/elimina-dialog/elimina-dialog.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        AppRoutingModule,
        SharedModule
    ],
    exports: [
        GestioneDisimpegniComponent,
        EliminaDialogComponent
    ],
    declarations: [
        GestioneDisimpegniComponent,
        DettaglioImpegnoComponent,
        AssociazioniPerBeneficiarioComponent,
        AssociazioniComponent,
        NuovaAssociazioneComponent,
        ImportaImpegnoDialogComponent,
        EliminaDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        NavigationGestioneImpegniService,
        GestioneImpegniService
    ]
})
export class GestioneDisimpegniModule { }