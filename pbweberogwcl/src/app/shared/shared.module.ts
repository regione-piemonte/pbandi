/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { MatSpinnerPbandi } from './components/mat-spinner-pbandi/mat-spinner-pbandi.component';
import { CommonModule, DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../app-material.module';
import { AppRoutingModule } from '../app-routing.module';
import { DrawerComponent } from './components/drawer/drawer.component';
import { HeaderLabelsButtonsComponent } from './components/header-labels-buttons/header-labels-buttons.component';
import { SelectBeneficiarioComponent } from './components/select-beneficiario/select-beneficiario.component';
import { BlxDataTableComponent } from './components/blx-data-table/blx-data-table.component';
import { BlxAttachedComponent } from './components/blx-attached/blx-attached.component';
import { ContattiDialog } from './components/contatti-dialog/contatti-dialog';
import { SharedService } from './services/shared.service';
import { InizializzazioneService } from "./services/inizializzazione.service";
import { DrawerModule as DrawerLibModule } from '@pbandi/common-lib';
import { ConfermaWarningDialogComponent } from './components/conferma-warning-dialog/conferma-warning-dialog.component';

@NgModule({
	imports: [
		MaterialModule,
		CommonModule,
		FormsModule,
		AppRoutingModule,
		DrawerLibModule
	],
	exports: [
		HeaderLabelsButtonsComponent,
		SelectBeneficiarioComponent,
		BlxDataTableComponent,
		BlxAttachedComponent,
		DrawerComponent,
		ContattiDialog,
		MatSpinnerPbandi,
		ConfermaWarningDialogComponent
	],
	declarations: [
		HeaderLabelsButtonsComponent,
		SelectBeneficiarioComponent,
		BlxDataTableComponent,
		BlxAttachedComponent,
		DrawerComponent,
		ContattiDialog,
		MatSpinnerPbandi,
		ConfermaWarningDialogComponent
	],
	entryComponents: [
	],
	providers: [
		SharedService,
		InizializzazioneService,
		DatePipe
	]
})
export class SharedModule { }
