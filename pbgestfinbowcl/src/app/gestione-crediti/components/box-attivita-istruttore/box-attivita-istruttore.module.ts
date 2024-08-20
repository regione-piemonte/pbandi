/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/app-material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from '@pbandi/common-lib';
import { MatSortModule } from '@angular/material/sort';
import { BoxRevocaBancariaComponent } from './box-revoca-bancaria/box-revoca-bancaria.component';
import { DialogModificaBoxRevocaBancariaComponent } from './box-revoca-bancaria/dialog-modifica-box-revoca-bancaria/dialog-modifica-box-revoca-bancaria.component';
import { DialogStoricoBoxRevocaBancariaComponent } from './box-revoca-bancaria/dialog-storico-box-revoca-bancaria/dialog-storico-box-revoca-bancaria.component';
import { BoxSaldoStralcioComponent } from './box-saldo-stralcio/box-saldo-stralcio.component';
import { DialogModificaBoxSaldoStralcioComponent } from './box-saldo-stralcio/dialog-modifica-box-saldo-stralcio/dialog-modifica-box-saldo-stralcio.component';
import { DialogStoricoBoxSaldoStralcioComponent } from './box-saldo-stralcio/dialog-storico-box-saldo-stralcio/dialog-storico-box-saldo-stralcio.component';
import { BoxAttivitaIstruttoreService } from '../../services/box-attivita-istruttore/box-attivita-istruttore.service';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        SharedModule,
        CoreModule,
        MatSortModule,
    ],
    exports: [
        BoxRevocaBancariaComponent,
        DialogModificaBoxRevocaBancariaComponent,
        DialogStoricoBoxRevocaBancariaComponent,

        BoxSaldoStralcioComponent,
        DialogModificaBoxSaldoStralcioComponent,
        DialogStoricoBoxSaldoStralcioComponent,

    ],
    declarations: [
        BoxRevocaBancariaComponent,
        DialogModificaBoxRevocaBancariaComponent,
        DialogStoricoBoxRevocaBancariaComponent,
        
        BoxSaldoStralcioComponent,
        DialogModificaBoxSaldoStralcioComponent,
        DialogStoricoBoxSaldoStralcioComponent,
    ],
    entryComponents: [
    ],
    providers: [
        BoxAttivitaIstruttoreService
    ]

})
export class BoxAttivitaIstruttoreModule { }
