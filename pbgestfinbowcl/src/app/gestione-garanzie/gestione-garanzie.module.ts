/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { CoreModule } from '../core/core.module';
import { SharedModule } from '../shared/shared.module';
import { GestioneGaranzieComponent } from './components/gestione-garanzie/gestione-garanzie.component';
import { NavigationGestioneGaranzieService } from '../gestione-crediti/services/navigation-gestione-garanzie.service';
import { ModificaGaranzieComponent } from './components/modifica-garanzie/modifica-garanzie.component';
import { NumberFormatPipe } from '../gestione-crediti/components/number-format/number.pipe';
import { NumberPipeInput } from '../gestione-crediti/components/number-format-input/number.pipe.input';
import { GestioneGaranzieService } from './services/gestione-garanzie.service';
import { DatiProgettoAttivitaPregresseModule, VisualizzaContoEconomicoModule} from '@pbandi/common-lib';
import { DialogDettaglioRicercaGaranzieComponent } from './components/dialog-dettaglio-ricerca-garanzie/dialog-dettaglio-ricerca-garanzie.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreModule,
        DatiProgettoAttivitaPregresseModule,
        VisualizzaContoEconomicoModule
    ],
    exports: [
        GestioneGaranzieComponent,
        ModificaGaranzieComponent,
        DialogDettaglioRicercaGaranzieComponent
    ],
    declarations: [
        GestioneGaranzieComponent,
        ModificaGaranzieComponent,
        DialogDettaglioRicercaGaranzieComponent
    ],
    entryComponents: [
    ],
    providers: [
        GestioneGaranzieService,
        NavigationGestioneGaranzieService
    ]
})
export class GestioneGaranzieModule { }
