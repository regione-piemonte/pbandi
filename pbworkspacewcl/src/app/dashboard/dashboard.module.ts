/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from '../app-material.module';
import { SharedModule } from '../shared/shared.module';
import { FormsModule } from '@angular/forms';
import { DashboardHomeComponent } from './components/dashboard-home/dashboard-home.component';
import { DashboardSettingsComponent } from './components/dashboard-settings/dashboard-settings.component';
import { WidgetSettingsDialogComponent } from './components/widget-settings-dialog/widget-settings-dialog.component';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        SharedModule,
        FormsModule
    ],
    exports: [
    ],
    declarations: [
        DashboardHomeComponent,
        DashboardSettingsComponent,
        WidgetSettingsDialogComponent
    ],
    entryComponents: [
    ],
    providers: [
    ]
})
export class DashboardModule { }