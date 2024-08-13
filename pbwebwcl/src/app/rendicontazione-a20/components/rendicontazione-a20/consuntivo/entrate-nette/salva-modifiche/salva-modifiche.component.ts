/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SpesaService } from 'src/app/rendicontazione-a20/services/spesa.service';

@Component({
	selector: 'app-salva-modifiche',
	templateUrl: './salva-modifiche.component.html',
	styleUrls: ['./salva-modifiche.component.css'],
})
export class SalvaModificheComponent implements OnInit {
	message: string;
	errore: boolean;
	constructor(public dialogRef: MatDialogRef<SalvaModificheComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private spesaService: SpesaService) {}

	ngOnInit(): void {
		this.message = this.data.success;
		this.errore = this.data.errore;
	}

	onConfirmClick() {
		if (this.data.isSpesa) {
			this.spesaService.salvaSpesePreventivate(this.data.vociDiSpesaDaSalvare).subscribe(
				data => {
					this.message = 'Voce di spesa inserita con successo';
					this.errore = false;
					setTimeout(() => {
						this.errore = null;
						this.closeDialog();
					}, 2000);
				},
				err => {
					console.log(err);
					this.errore = true;
					this.message = 'Non è stato possibile inserire la voce di spesa';
				},
			);
		}
	}

	closeDialog() {
		this.dialogRef.close();
	}
}
