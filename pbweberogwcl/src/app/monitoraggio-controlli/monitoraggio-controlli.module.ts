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
import { MonitoraggioControlliComponent } from './components/monitoraggio-controlli/monitoraggio-controlli.component';
import { MonitoraggioControlliService } from './services/monitoraggio-controlli.service';
import { MostraProgettiCampioneDialogComponent } from './components/mostra-progetti-campione-dialog/mostra-progetti-campione-dialog';

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
    MonitoraggioControlliComponent,
    MostraProgettiCampioneDialogComponent
  ],
  declarations: [
    MonitoraggioControlliComponent,
    MostraProgettiCampioneDialogComponent
  ],
  entryComponents: [
  ],
  providers: [
    MonitoraggioControlliService
  ]
})
export class MonitoraggioControlliModule { }
