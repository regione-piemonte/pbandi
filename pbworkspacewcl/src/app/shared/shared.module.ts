/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule, DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { DrawerComponent } from './components/drawer/drawer.component';
import { MatSpinnerPbandi } from './components/mat-spinner-pbandi/mat-spinner-pbandi.component';
import { ContattiDialog } from './components/contatti-dialog/contatti-dialog';
import { SharedService } from './services/shared.service';
import { ShowNewsComponent } from './components/show-news/show-news.component';
import { EllipsisDirective } from './directives/ellipsis.directive';
import { VisualizzaTestoDialogComponent } from './components/visualizza-testo-dialog/visualizza-testo-dialog.component';
import { SafeHtmlPipe } from './pipes/safeHTML.pipe';
import { DrawerModule as DrawerLibModule } from '@pbandi/common-lib';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        AppRoutingModule,
        DrawerLibModule
    ],
    exports: [
        DrawerComponent,
        MatSpinnerPbandi,
        ContattiDialog,
        ShowNewsComponent,
        EllipsisDirective,
        VisualizzaTestoDialogComponent,
        SafeHtmlPipe
    ],
    declarations: [
        DrawerComponent,
        MatSpinnerPbandi,
        ContattiDialog,
        ShowNewsComponent,
        EllipsisDirective,
        VisualizzaTestoDialogComponent,
        SafeHtmlPipe
    ],
    entryComponents: [
    ],
    providers: [
        SharedService,
        DatePipe
    ]
})
export class SharedModule { }