/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { CoreModule } from '@pbandi/common-lib';
import { DatePipe } from '@angular/common';
import { MatSortModule } from '@angular/material/sort';
import { RicercaRiassicurazioniComponent } from './components/ricerca-riassicurazioni/ricerca-riassicurazioni.component';
import { NumberPipeInput } from '../gestione-crediti/components/number-format-input/number.pipe.input';
import { NumberFormatPipe } from '../gestione-crediti/components/number-format/number.pipe';
import { GestioneRiassicurazioniService } from './services/gestione-riassicurazioni.service';
import { GestioneGaranzieService } from '../gestione-garanzie/services/gestione-garanzie.service';
import { DialogDettaglioRiassicurazione } from './components/ricerca-riassicurazioni/dialog-dettaglio-riassicurazione/dialog-dettaglio-riassicurazione.component';
import { GestioneEscussioneRiassicurazioniComponent } from './components/gestione-escussione-riassicurazioni/gestione-escussione-riassicurazioni.component';
import { DialogModificaEscussioneRiassicurazioni } from './components/gestione-escussione-riassicurazioni/dialog-modifica-escussione-riassicurazioni/dialog-modifica-escussione-riassicurazioni.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreModule,
        MatSortModule
    ],
    exports: [
        RicercaRiassicurazioniComponent,
        DialogDettaglioRiassicurazione,
        GestioneEscussioneRiassicurazioniComponent,
        DialogModificaEscussioneRiassicurazioni
    ],
    declarations: [
        RicercaRiassicurazioniComponent,
        DialogDettaglioRiassicurazione,
        GestioneEscussioneRiassicurazioniComponent,
        DialogModificaEscussioneRiassicurazioni
    ],
    entryComponents: [
    ],
    providers: [
        DatePipe,
        GestioneRiassicurazioniService,
        GestioneGaranzieService
    ]
})
export class GestioneRiassicurazioniModule { }
