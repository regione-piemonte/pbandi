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
import { DisimpegniComponent } from './components/disimpegni/disimpegni.component';
import { NuovoDisimpegnoComponent } from './components/nuovo-disimpegno/nuovo-disimpegno.component';
import { RipartisciIrregolaritaComponent } from './components/ripartisci-irregolarita/ripartisci-irregolarita.component';
import { DisimpegniService } from './services/disimpegni.service';
import { DatiProgettoAttivitaPregresseModule } from '@pbandi/common-lib';
import { Disimpegni2Service } from './services/disimpegni-2.service';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        AppRoutingModule,
        PipeModule,
        DirectiveModule,
        DatiProgettoAttivitaPregresseModule
    ],
    exports: [
        DisimpegniComponent,
        NuovoDisimpegnoComponent,
        RipartisciIrregolaritaComponent,
    ],
    declarations: [
        DisimpegniComponent,
        NuovoDisimpegnoComponent,
        RipartisciIrregolaritaComponent,
    ],
    entryComponents: [
    ],
    providers: [
        DisimpegniService,
        Disimpegni2Service
    ]
})
export class DisimpegniModule { }
