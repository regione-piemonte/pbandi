/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatDividerModule } from '@angular/material/divider'
import { MatCardModule } from '@angular/material/card'
import { ReactiveFormsModule } from '@angular/forms';
import { MatMenuModule } from '@angular/material/menu'
import { MatButtonModule } from '@angular/material/button'
import { MatGridListModule } from '@angular/material/grid-list'
import { MatSelectModule } from '@angular/material/select'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatInputModule } from '@angular/material/input'
import { MatListModule } from '@angular/material/list'
import { MatTableModule } from '@angular/material/table'
import { MatCheckboxModule } from '@angular/material/checkbox'
import { MatSortModule } from '@angular/material/sort'
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator'
import { MatRadioModule } from '@angular/material/radio'
import { MatDatepickerModule } from '@angular/material/datepicker'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { MatProgressBarModule } from '@angular/material/progress-bar'
import { MatDialogModule } from '@angular/material/dialog'
import { MatSnackBarModule } from '@angular/material/snack-bar'
import { MatTooltipModule } from '@angular/material/tooltip'
import { MatAutocompleteModule } from '@angular/material/autocomplete'
import { DateAdapter, MatNativeDateModule, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatTabsModule } from '@angular/material/tabs';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatSidenavModule } from '@angular/material/sidenav';
import { getItalianPaginatorIntl } from './italian-paginator-intl';
import { MatTreeModule } from '@angular/material/tree';
import { MatStepperModule } from '@angular/material/stepper';
import { CustomDateAdapter, MY_FORMATS } from './custom-date-adapter';
import { ScrollingModule } from '@angular/cdk/scrolling';

let dependences = [
    ReactiveFormsModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    MatGridListModule,
    MatCardModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatListModule,
    MatTableModule,
    MatCheckboxModule,
    MatSortModule,
    MatPaginatorModule,
    MatRadioModule,
    MatDatepickerModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    MatDialogModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatDividerModule,
    MatAutocompleteModule,
    MatNativeDateModule,
    MatTabsModule,
    MatExpansionModule,
    MatSidenavModule,
    MatTreeModule,
    MatStepperModule,
    ScrollingModule
];

@NgModule({
    imports: dependences,
    exports: dependences,
    providers: [
        { provide: MatPaginatorIntl, useValue: getItalianPaginatorIntl() },
        { provide: DateAdapter, useClass: CustomDateAdapter },
        { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
        { provide: MAT_DATE_LOCALE, useValue: "it-IT" }
    ]
})
export class MaterialModule { }