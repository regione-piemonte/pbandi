/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { FormsModule } from '@angular/forms';
import { GestioneNewsService } from './services/gestione-news.service';
import { GestioneNewsComponent } from './components/gestione-news/gestione-news.component';
import { NewsDialogComponent } from './components/news-dialog/news-dialog.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        SharedModule,
        FormsModule
    ],
    exports: [
        GestioneNewsComponent,
        NewsDialogComponent
    ],
    declarations: [
        GestioneNewsComponent,
        NewsDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
        GestioneNewsService
    ]
})
export class GestioneNewsModule { }