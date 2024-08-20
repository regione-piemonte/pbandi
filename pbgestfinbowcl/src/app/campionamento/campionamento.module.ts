/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CoreModule } from '@pbandi/common-lib';
import { MaterialModule } from '../app-material.module';
import { RicercaRichiesteComponent } from '../gestione-richieste/components/ricerca-richieste/ricerca-richieste.component';
import { SharedModule } from '../shared/shared.module';
import { RicercaCampionamentiComponent } from './components/ricerca-campionamenti/ricerca-campionamenti.component';
import { CampionamentoService } from './services/campionamento.services';
import { NuovoCampionamentoComponent } from './components/nuovo-campionamento/nuovo-campionamento.component';
import { DialogEditNuovoCampionamentoComponent } from './components/dialog-edit-nuovo-campionamento/dialog-edit-nuovo-campionamento.component';


@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreModule,
    
    ],
    exports: [
        RicercaCampionamentiComponent
      
    ],
    declarations: [
        RicercaCampionamentiComponent,
        NuovoCampionamentoComponent,
        DialogEditNuovoCampionamentoComponent
        
    ],
    entryComponents: [
    ],
    providers: [
        CampionamentoService,
        
    ]
})
export class CampionamentoModule { }
