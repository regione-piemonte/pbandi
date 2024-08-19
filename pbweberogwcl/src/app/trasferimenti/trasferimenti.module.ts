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
import { TrasferimentiComponent } from './components/trasferimenti/trasferimenti.component';
import { NuovoTrasferimentoComponent } from './components/nuovo-trasferimento/nuovo-trasferimento.component';
import { TrasferimentiService } from './services/trasferimenti.service';
import { Trasferimenti2Service } from './services/trasferimenti2.service';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        AppRoutingModule,
        PipeModule,
        DirectiveModule,
    ],
    exports: [
        TrasferimentiComponent,
        NuovoTrasferimentoComponent,
    ],
    declarations: [
        TrasferimentiComponent,
        NuovoTrasferimentoComponent,
    ],
    entryComponents: [
    ],
    providers: [
        TrasferimentiService,
        Trasferimenti2Service
    ]
})
export class TrasferimentiModule { }
