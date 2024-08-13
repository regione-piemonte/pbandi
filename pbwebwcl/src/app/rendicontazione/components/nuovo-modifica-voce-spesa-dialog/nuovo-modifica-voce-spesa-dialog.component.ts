/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { VoceDiSpesaFiglia, VoceDiSpesaPadre } from '../../commons/dto/voce-di-spesa';
import { VoceDiSpesaDTO } from '../../commons/dto/voce-di-spesa-dto';
import { DocumentoDiSpesaService } from '../../services/documento-di-spesa.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
	selector: 'app-nuovo-modifica-voce-spesa-dialog',
	templateUrl: './nuovo-modifica-voce-spesa-dialog.component.html',
	styleUrls: ['./nuovo-modifica-voce-spesa-dialog.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoModificaVoceSpesaDialogComponent implements OnInit {
	idRouting: number;
	isBandoCultura: boolean;
	isNuovo: boolean;
	voce: VoceDiSpesaDTO;
	idDocumentoDiSpesa: number;
	idTipoDocumentoDiSpesa: number;
	idProgetto: number;
	user: UserInfoSec;
	residuoRendicontabile: number;
	costoOrario: number;
	descBreveTipoDocumento: string;
	categorieVoci: Array<{
		idTipologiaVoceDiSpesa: number;
		descrizione: string;
		percQuotaContributo: number;
	}>;
	macroVoci: Array<VoceDiSpesaPadre>;
	categoriaSelected: number;
	macroSelected: VoceDiSpesaPadre;
	microVoci: Array<VoceDiSpesaFiglia>;
	microSelected: VoceDiSpesaFiglia;
	oreLavorate: number;
	oreLavorateFormatted: string;
	quotaParte: number;
	quotaParteFormatted: string;
	importoAmmessoFinanziamentoFormatted: string;
	importoResiduoAmmessoFormatted: string;
	tipoOperazioneFrom: string;

	@ViewChild('voceForm', { static: true }) voceForm: NgForm;

	messageError: string;
	isMessageErrorVisible: boolean;

	//LOADED
	loadedSalva: boolean = true;

	//SUBSCRIBERS
	subscribers: any = {};

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		public dialogRef: MatDialogRef<NuovoModificaVoceSpesaDialogComponent>,
		private dialog: MatDialog,
		private documentoDiSpesaService: DocumentoDiSpesaService,
		private sharedService: SharedService,
		private handleExceptionService: HandleExceptionService,
		@Inject(MAT_DIALOG_DATA) public data: any,
	) { }

	ngOnInit(): void {
		this.idRouting = this.data.idRouting;
		this.isNuovo = this.data.isNuovo;
		this.idDocumentoDiSpesa = this.data.idDocumentoDiSpesa;
		this.idTipoDocumentoDiSpesa = this.data.idTipoDocumentoDiSpesa;
		this.idProgetto = this.data.idProgetto;
		this.user = this.data.user;
		this.macroVoci = this.data.macroVoci;
		console.log(this.data);

		if (this.idRouting == 94) {
			this.isBandoCultura = true;
		} else {
			this.isBandoCultura = false;
		}

		this.getCategorieVociDiSpesa();

		if (!this.macroVoci || this.macroVoci.length === 0) {
			this.showMessageError('Non esistono macro voci di spesa associate alla categoria selezionata');
		}
		this.residuoRendicontabile = this.data.residuoRendicontabile;
		this.costoOrario = this.data.costoOrario;
		this.descBreveTipoDocumento = this.data.descBreveTipoDocumento;
		if (!this.isNuovo) {
			this.voce = this.data.voce;
			this.macroSelected = this.macroVoci.find(v => (this.voce.idVoceDiSpesaPadre ? v.idVoceDiSpesa === this.voce.idVoceDiSpesaPadre : v.idVoceDiSpesa === this.voce.idVoceDiSpesa));
			this.onSelectMacro();
			this.microSelected = this.microVoci.find(v => v.idVoceDiSpesa === this.voce.idVoceDiSpesa);
			this.importoAmmessoFinanziamentoFormatted =
				this.voce.importoFinanziamento !== null && this.voce.importoFinanziamento !== undefined ? this.sharedService.formatValue(this.voce.importoFinanziamento.toString()) : '0,00';
			this.importoResiduoAmmessoFormatted = this.sharedService.formatValue(this.voce.importoResiduoAmmesso.toString());
			this.quotaParte = this.voce.importo;
			this.quotaParteFormatted = this.sharedService.formatValue(this.quotaParte.toString());
			this.oreLavorate = this.voce.oreLavorate;
			if (this.oreLavorate !== null) {
				this.oreLavorateFormatted = this.sharedService.formatValue(this.oreLavorate.toString());
			}
			this.tipoOperazioneFrom = this.data.tipoOperazioneFrom;
		}
		//in validazione, quota parte e ore lavorate sono readOnly (vedere vocidispesa.js)
	}

	onSelectCategoria() {
		this.macroVoci = this.data.macroVoci;
		this.macroVoci = this.macroVoci.filter(macro => macro.idTipologiaVoceDiSpesa === this.categoriaSelected);
	}

	onSelectMacro() {
		this.microSelected = null;
		this.microVoci = this.macroSelected.vociDiSpesaFiglie;
		if (!this.microVoci || this.microVoci.length === 0) {
			// Se la macro voce di spesa non ha figli allora
			// visualizzo l' importo ammesso e il residuo ammesso della macro voce
			if (!this.macroSelected.importoAmmessoFinanziamento) {
				this.macroSelected.importoAmmessoFinanziamento = 0;
			}
			if (!this.macroSelected.importoResiduoAmmesso) {
				this.macroSelected.importoResiduoAmmesso = 0;
			}
			this.importoAmmessoFinanziamentoFormatted = this.sharedService.formatValue(this.macroSelected.importoAmmessoFinanziamento.toString());
			this.importoResiduoAmmessoFormatted = this.sharedService.formatValue(this.macroSelected.importoResiduoAmmesso.toString());
		} else {
			this.importoAmmessoFinanziamentoFormatted = null;
			this.importoResiduoAmmessoFormatted = null;
		}
	}

	onSelectMicro() {
		if (!this.microSelected.importoAmmessoFinanziamento) {
			this.microSelected.importoAmmessoFinanziamento = 0;
		}
		if (!this.microSelected.importoResiduoAmmesso) {
			this.microSelected.importoResiduoAmmesso = 0;
		}
		this.importoAmmessoFinanziamentoFormatted = this.sharedService.formatValue(this.microSelected.importoAmmessoFinanziamento.toString());
		this.importoResiduoAmmessoFormatted = this.sharedService.formatValue(this.microSelected.importoResiduoAmmesso.toString());
	}

	setOreLavorate() {
		this.oreLavorate = this.sharedService.getNumberFromFormattedString(this.oreLavorateFormatted);
		if (this.oreLavorate !== null) {
			this.oreLavorateFormatted = this.sharedService.formatValue(this.oreLavorate.toString());
			this.quotaParte = this.costoOrario * this.oreLavorate;
			this.quotaParteFormatted = this.sharedService.formatValue(this.quotaParte.toString());
		}
	}

	setQuotaParte() {
		this.quotaParte = this.sharedService.getNumberFromFormattedString(this.quotaParteFormatted);
		if (this.quotaParte !== null) {
			this.quotaParteFormatted = this.sharedService.formatValue(this.quotaParte.toString());
			if (this.voceForm.form.get('oreLavorate')) {
				this.oreLavorate = this.quotaParte / this.costoOrario;
				this.oreLavorateFormatted = this.sharedService.formatValue(this.oreLavorate.toString());
			}
		}
	}

	controlNumberLengthVoceForm(name: string, min: number, max: number) {
		if (this.voceForm.form.get(name) && this.voceForm.form.get(name).value) {
			let s: string = this.voceForm.form.get(name).value.toString();
			let length: number;
			s = s.replace(/\./g, '');
			if (s.includes('e')) {
				let n = s.split('e');
				length = +n[1] + 1;
			} else {
				let stringa = s.split(',');
				length = stringa[0].length;
			}
			if (s.includes)
				if (length < min || length > max) {
					this.voceForm.form.get(name).setErrors({ error: 'wrongLength' });
					return true;
				}
		}
		return false;
	}

	controlNumberPositiveVoceForm(name: string) {
		if (this.voceForm.form.get(name) && this.voceForm.form.get(name).value !== undefined && this.voceForm.form.get(name).value !== null) {
			let value: number;
			value = this.sharedService.getNumberFromFormattedString(this.voceForm.form.get(name).value);
			if (value <= 0) {
				this.voceForm.form.get(name).setErrors({ error: 'negative' });
				return true;
			}
		}
	}

	salva() {
		this.resetMessageError();
		if (this.voceForm.form.get('oreLavorate') && (this.controlNumberLengthVoceForm('oreLavorate', 1, 7) || this.controlNumberPositiveVoceForm('oreLavorate'))) {
			return;
		}
		if (this.controlNumberLengthVoceForm('quotaParte', 1, 15) || this.controlNumberPositiveVoceForm('quotaParte')) {
			return;
		}
		if ((this.isNuovo && this.quotaParte > this.residuoRendicontabile) || (!this.isNuovo && this.quotaParte > this.residuoRendicontabile + this.voce.importo)) {
			this.showMessageError('Inserire una quota parte minore o uguale al residuo rendicontabile del documento.');
			return;
		}
		if (this.quotaParte > (this.microVoci && this.microSelected ? this.microSelected.importoResiduoAmmesso : this.macroSelected.importoResiduoAmmesso)) {
			let dRef = this.dialog.open(ConfermaDialog, {
				width: '40%',
				data: {
					message: 'La quota parte è superiore al residuo della VdS (ammesso per la rendicontazione sul progetto). Continuare ugualmente?',
				},
			});
			dRef.afterClosed().subscribe(res => {
				if (res === 'SI') {
					this.associaVoceDiSpesa();
				}
				return;
			});
		} else {
			this.associaVoceDiSpesa();
		}
	}

	associaVoceDiSpesa() {
		this.loadedSalva = false;
		let voceDTO = new VoceDiSpesaDTO(
			this.microSelected ? this.microSelected.idVoceDiSpesa : this.macroSelected.idVoceDiSpesa,
			this.microSelected ? this.microSelected.idRigoContoEconomico : this.macroSelected.idRigoContoEconomico,
			this.idProgetto,
			null,
			this.idDocumentoDiSpesa,
			this.quotaParte,
			this.microSelected ? this.microSelected.descVoceDiSpesa : this.macroSelected.descVoceDiSpesa,
			null,
			null,
			this.microSelected ? this.microSelected.importoAmmessoFinanziamento : this.macroSelected.importoAmmessoFinanziamento,
			this.microSelected ? this.macroSelected.idVoceDiSpesa : null,
			this.oreLavorate,
			this.costoOrario,
			this.idTipoDocumentoDiSpesa,
			null,
			null,
			this.microSelected ? this.microSelected.importoResiduoAmmesso : this.macroSelected.importoResiduoAmmesso,
			this.microSelected ? this.macroSelected.descVoceDiSpesa : null,
			null,
			null,
		);
		if (!this.isNuovo) {
			voceDTO.idQuotaParteDocSpesa = this.voce.idQuotaParteDocSpesa;
		}
		this.subscribers.salva = this.documentoDiSpesaService.associaVoceDiSpesa(voceDTO, this.user.idUtente).subscribe(
			data => {
				if (data) {
					if (data.esito) {
						this.dialogRef.close(data.messaggio);
					} else {
						this.showMessageError(data.messaggio);
					}
				}
				this.loadedSalva = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.loadedSalva = true;
				this.showMessageError('Errore nella creazione della voce di spesa.');
			},
		);
	}

	close() {
		this.dialogRef.close();
	}

	showMessageError(msg: string) {
		this.messageError = msg;
		this.isMessageErrorVisible = true;
	}

	resetMessageError() {
		this.messageError = null;
		this.isMessageErrorVisible = false;
	}

	isLoading() {
		if (!this.loadedSalva) {
			return true;
		}
		return false;
	}

	getCategorieVociDiSpesa() {
		this.subscribers = this.documentoDiSpesaService.getCategorieVociDiSpesa().subscribe(
			data => {
				if (data) {
					this.categorieVoci = data;
				}
				this.loadedSalva = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.loadedSalva = true;
				this.showMessageError('Errore nel caricamento delle categorie di voci di spesa.');
			},
		);
	}
}
