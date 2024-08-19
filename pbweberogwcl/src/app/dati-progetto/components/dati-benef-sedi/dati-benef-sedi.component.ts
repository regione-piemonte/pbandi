/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatRadioChange } from '@angular/material/radio';
import { MatTableDataSource } from '@angular/material/table';
import { HandleExceptionService } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { DettaglioSoggettoBeneficiarioDTO } from '../../commons/dto/dettaglio-soggetto-beneficiario-dto';
import { LinkMappaDTO } from '../../commons/dto/link-mappa-dto';
import { SedeProgettoDTO } from '../../commons/dto/sede-progetto-dto';
import { SoggettoProgettoDTO } from '../../commons/dto/soggetto-progetto-dto';
import { EsitoOperazioni } from '../../commons/models/esito-operazioni';
import { Recapiti } from '../../commons/models/recapiti';
import { RequestSalvaRecapito } from '../../commons/models/request-salva-recapito';
import { SedeProgetto } from '../../commons/models/sede-progetto';
import { DatiProgettoService } from '../../services/dati-progetto.service';
import { MappeService } from '../../services/mappe.service';
import { DatiProgettoSediDialogComponent } from '../dati-progetto-sedi-dialog/dati-progetto-sedi-dialog.component';
import { NgForm } from '@angular/forms';
import { ConfermaWarningDialogComponent } from 'src/app/shared/components/conferma-warning-dialog/conferma-warning-dialog.component';

@Component({
	selector: 'app-dati-benef-sedi',
	templateUrl: './dati-benef-sedi.component.html',
	styleUrls: ['./dati-benef-sedi.component.scss']
})
export class DatiBenefSediComponent implements OnInit {

	@Input("idProgetto") idProgetto: number;
	@Input("soggettiProgetto") soggettiProgetto: SoggettoProgettoDTO[];

	@Output("messageError") messageError = new EventEmitter<string>();
	@Output("messageSuccess") messageSuccess = new EventEmitter<string>();

	@ViewChild("recapitiForm") recapitiForm: NgForm;

	user: UserInfoSec;
	action: string;

	dettaglioSoggettoBeneficiario: DettaglioSoggettoBeneficiarioDTO;
	sediProgetto: SedeProgetto[];
	sediProgettoDataSource: SedeProgetto[];
	linkMappa: LinkMappaDTO;
	email: string;
	pec: string;
	isDisabledEmail: boolean;
	isDisabledPec: boolean;

	recapiti: Recapiti;

	//TABLE SEDI PROGETTO
	displayedColumnsSediProgetto: string[] = ['descIndirizzoComposto', 'partitaIva', 'descTipoSede', 'azioni'];  // descIndirizzo
	displayedHeadersSediProgetto: string[] = ['Indirizzo', 'C.F. / P.IVA', 'Tipo sede', ''];
	displayedFootersSediProgetto: string[] = [];
	displayedColumnsCustomSediProgetto: string[] = ['', '', '', 'azioni'];
	displayedHeadersCustomSediProgetto: string[] = this.displayedColumnsCustomSediProgetto;
	displayedColumnsCustomSediProgettoAzioni: any = { visualizza: true, modifica: 'isModificabile', elimina: 'isEliminabile' };
	dataSourceSediProgetto: SediProgettoDataSource = new SediProgettoDataSource([]);

	subscribers: any = {};

	// LOADED 
	loadedSediProgetto: boolean;
	loadedLinkMappa: boolean = true;
	loadedDettaglioSoggettoBeneficiario: boolean;
	loadedSalvaDatiBen: boolean = true;

	loadedRecapito: boolean = true;
	loadedSalvaRecapito: boolean = true;

	constructor(
		private datiProgettoService: DatiProgettoService,
		private handleExceptionService: HandleExceptionService,
		private dialog: MatDialog,
		private sharedService: SharedService,
		private mappeService: MappeService,
		private userService: UserService
	) { }

	ngOnInit(): void {
		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
			if (data) {
				this.user = data;

			}
		});
	}

	loadData() {
		if (this.isIstruttore()) {
			this.action = 'modifica';
		} else if (this.isBeneficiario()) {
			this.action = 'dettaglio';
		}
		this.loadDettaglioSoggettoBeneficiario();
		this.loadAllSediProgetto();
		//this.loadLinkMappa();
		this.loadRecapito();
	}

	loadDettaglioSoggettoBeneficiario() {
		this.loadedDettaglioSoggettoBeneficiario = false;
		this.subscribers.dettaglioSoggettoProgetto = this.datiProgettoService.getDettaglioSoggettoBeneficiario(this.user.idUtente, this.idProgetto, this.user.codiceRuolo).subscribe(res => {
			if (res) {
				this.dettaglioSoggettoBeneficiario = res;
				/* if (res.sediIntervento && res.sediIntervento.length) {
					 this.dettaglioSediProgetto = res.sediIntervento.map((sede: SedeProgettoDTO) => {
						 return {
							 descIndirizzo: sede.descIndirizzo + " " + sede.civico + " - " + sede.cap,
							 descTipoSede: sede.flagSedeAmministrativa === "S" ? 'SEDE AMMINISTRATIVA' : sede.descTipoSede,
							 partitaIva: sede.partitaIva
						 };
					 });
				 } else {
					 this.dettaglioSediProgetto = [];
				 }*/
				//this.dataSourceSediProgetto = new SediProgettoDataSource(this.dettaglioSediProgetto);
				//this.postFormFields();
			}
			this.loadedDettaglioSoggettoBeneficiario = true;
			// this.updateFormFields(true);
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento dei dati del beneficiario.");
			this.loadedDettaglioSoggettoBeneficiario = true;
		});
	}

	loadAllSediProgetto() {
		this.loadedSediProgetto = false;
		this.subscribers.allSediProgetto = this.datiProgettoService.getAllSediProgetto(this.idProgetto, this.user.codiceRuolo).subscribe((data: SedeProgetto[]) => {
			if (data) {
				this.sediProgetto = data;
				this.sediProgettoDataSource = data.map((sede: SedeProgetto) => {
					return {
						idSede: sede.idSede,
						descIndirizzoComposto: sede.descIndirizzoComposto,
						partitaIva: sede.partitaIva,
						descTipoSede: ((sede.flagSedeAmministrativa) ? 'SEDE AMMINISTRATIVA' : sede.descTipoSede),
						isModificabile: sede.isModificabile
					};
				}); // descIndirizzo: sede.descIndirizzo + ', ' + sede.descComune + ' (' + sede.descProvincia + ')'
				this.dataSourceSediProgetto = new SediProgettoDataSource(this.sediProgettoDataSource);
			}
			this.loadedSediProgetto = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento delle sedi del progetto.");
			this.loadedSediProgetto = true;
		});
	}

	loadLinkMappa() {
		this.loadedLinkMappa = false;
		this.subscribers.linkMappa = this.mappeService.linkMappa().subscribe(res => {
			if (res) {
				console.log(res);
				this.linkMappa = res;
			}
			this.loadedLinkMappa = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di recupero del link mappe.");
			this.loadedLinkMappa = true;
		});
	}

	onFlagPubblicoPrivatoChange(e: MatRadioChange) {
		this.dettaglioSoggettoBeneficiario.flagPubblicoPrivato;
		if (e.value === 2) {
			this.dettaglioSoggettoBeneficiario.codUniIpa = '';
		}
	}

	onVisualizzaSediProgetto = (row: SedeProgetto) => {
		this.openDettaglioDatiProgettoSedi(row);
	}

	onModificaSediProgetto = (row: SedeProgetto) => {
		this.openModificaDatiProgettoSedi(row);
	}

	onEliminaSediProgetto = (row: SedeProgetto) => {
		this.showMessageError('Eliminazione disattivata');
	}

	openDettaglioDatiProgettoSedi(row: SedeProgetto) {
		this.dialog.open(DatiProgettoSediDialogComponent, {
			width: "40%",
			data: {
				isModifica: false,
				idProgetto: this.idProgetto,
				idSede: row.idSede
			}
		});
	}

	openModificaDatiProgettoSedi(row: SedeProgetto) {
		this.dialog.open(DatiProgettoSediDialogComponent, {
			width: "40%",
			data: {
				isModifica: true,
				idProgetto: this.idProgetto,
				idSede: row.idSede
			}
		});
	}

	salvaDatiBeneficiarioSedi() {
		let sedi: SedeProgettoDTO[] = this.sediProgetto.map(s => new SedeProgettoDTO(s.cap, s.civico, s.codIstatComune, s.descBreveTipoSede, s.descComune, s.descIndirizzo, s.descNazione,
			s.descProvincia, s.descRegione, s.descTipoSede, s.email, s.fax, s.flagSedeAmministrativa ? "S" : "N", s.idComune, s.idIndirizzo, s.idNazione, s.idProgetto, s.idProvincia, s.idRecapiti,
			s.idRegione, s.idSede, s.idSoggettoBeneficiario, s.idTipoSede, s.partitaIva, s.progrSoggettoProgettoSede, s.telefono));
		this.dettaglioSoggettoBeneficiario.sediIntervento = sedi;
		this.loadedSalvaDatiBen = false;
		this.subscribers.salvaSoggettoBeneficiario = this.datiProgettoService.salvaSoggettoBeneficiario(this.dettaglioSoggettoBeneficiario).subscribe(data => {
			if (data) {
				if (data.esito && data.esito) {
					this.showMessageSuccess(data.msg);
				} else {
					this.showMessageError(data.msg);
				}
			}
			this.loadedSalvaDatiBen = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di salvataggio.");
			this.loadedSalvaDatiBen = true;
		});

	}

	loadRecapito() {
		this.loadedRecapito = false;
		this.subscribers.recapito = this.datiProgettoService.getRecapito(this.idProgetto).subscribe((res: Recapiti) => {
			if (res) {
				this.recapiti = res;
				// Può modificarlo solo il beneficiario
				let isErrorMail: boolean;
				let isErrorPec: boolean;
				if (res.flagEmailConfermata != "S") {
					this.isDisabledEmail = false;
					isErrorMail = true;
				} else if (this.sharedService.isBeneficiario(this.user)) {
					this.isDisabledEmail = false;
				} else {
					this.isDisabledEmail = true;
				}
				if (res.flagPecConfermata != "S") {
					this.isDisabledPec = false;
					isErrorPec = true;
				} else if (this.sharedService.isBeneficiario(this.user)) {
					this.isDisabledPec = false;
				} else {
					this.isDisabledPec = true;
				}

				if (isErrorMail && isErrorPec) {
					this.showMessageError("Indirizzo email e PEC da confermare.");
				} else if (isErrorMail) {
					this.showMessageError("Indirizzo email da confermare.");
				} else if (isErrorPec) {
					this.showMessageError("PEC da confermare.");
				}
				this.email = res.email;
				this.pec = res.pec;
			}
			this.loadedRecapito = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento dell'indirizzo email.");
			this.loadedRecapito = true;
		});
	}

	loadSalvaRecapito(type: string) {
		this.resetMessageError();
		this.resetMessageSuccess();
		if (!this.email || this.email.length === 0) {
			this.showMessageError("Valorizzare indirizzo e-mail.");
			return;
		}
		if (this.recapitiForm.form.get("email").invalid) {
			return;
		}
		let dialogRef = this.dialog.open(ConfermaWarningDialogComponent, {
			data: {
				title: (type === "S" ? "Salva" : "Conferma") + " indirizzo e-mail",
				messageWarning: "Attenzione! Verificare di aver inserito un indirizzo e-mail e non un indirizzo PEC prima di procedere."
			}
		});
		dialogRef.afterClosed().subscribe(ok => {
			if (ok) {
				this.loadedSalvaRecapito = false;
				this.recapiti.email = this.email;
				let requestSalvaRecapito: RequestSalvaRecapito = {
					idProgetto: this.idProgetto,
					recapito: this.recapiti
				};
				// Può modificarlo solo il beneficiario
				requestSalvaRecapito.recapito.flagEmailConfermata = "S";
				this.subscribers.salvaRecapito = this.datiProgettoService.salvaRecapito(this.user.idUtente, requestSalvaRecapito).subscribe((res: EsitoOperazioni) => {
					if (res) {
						if (res.esito) {
							this.resetMessageError();
							if (type === "S") {
								this.showMessageSuccess("Indirizzo email salvato con successo.");
							} else {
								this.showMessageSuccess("Indirizzo email confermato con successo.");
							}
							this.loadRecapito();
						} else {
							this.showMessageError(res.msg);
						}
					}
					this.loadedSalvaRecapito = true;
				}, err => {
					this.handleExceptionService.handleNotBlockingError(err);
					this.showMessageError("Errore in fase di salvataggio dell'indirizzo email.");
					this.loadedSalvaRecapito = true;
				});
			}
		});
	}

	loadSalvaRecapitoPec(type: string) {
		this.resetMessageError();
		this.resetMessageSuccess();
		if (!this.pec || this.pec.length === 0) {
			this.showMessageError("Valorizzare indirizzo PEC.");
			return;
		}
		if (this.recapitiForm.form.get("pec").invalid) {
			return;
		}
		this.loadedSalvaRecapito = false;
		this.recapiti.pec = this.pec;
		let requestSalvaRecapito: RequestSalvaRecapito = {
			idProgetto: this.idProgetto,
			recapito: this.recapiti
		};
		// Può modificarlo solo il beneficiario
		requestSalvaRecapito.recapito.flagPecConfermata = "S";
		this.datiProgettoService.salvaRecapitoPec(this.user.idUtente, requestSalvaRecapito).subscribe((res: EsitoOperazioni) => {
			if (res) {
				if (res.esito) {
					this.resetMessageError();
					if (type === "S") {
						this.showMessageSuccess("PEC salvata con successo.");
					} else {
						this.showMessageSuccess("PEC confermata con successo.");
					}
					this.loadRecapito();
				} else {
					this.showMessageError(res.msg);
				}
			}
			this.loadedSalvaRecapito = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di salvataggio della PEC.");
			this.loadedSalvaRecapito = true;
		});
	}

	isBeneficiario() {
		return this.sharedService.isBeneficiario(this.user);
	}

	isIstruttore() {
		return this.sharedService.isIstruttore(this.user);
	}

	showMessageError(msg: string) {
		this.messageError.emit(msg);
	}

	resetMessageError() {
		this.messageError.emit(null);
	}

	showMessageSuccess(msg: string) {
		this.messageSuccess.emit(msg);
	}

	resetMessageSuccess() {
		this.messageSuccess.emit(null);
	}

	isLoading() {
		if (!this.loadedDettaglioSoggettoBeneficiario || !this.loadedSalvaDatiBen || !this.loadedLinkMappa
			|| !this.loadedRecapito || !this.loadedSalvaRecapito || !this.loadedSediProgetto || !this.loadedSalvaRecapito) {
			return true;
		}
		return false;
	}
}

export class SediProgettoDataSource extends MatTableDataSource<SedeProgetto> {

	_orderData(data: SedeProgetto[]): SedeProgetto[] {
		if (!this.sort) return data;
		if (!this.sort.direction || this.sort.direction.length === 0) {
			this.sort.active = "";
		}
		let direction = this.sort.direction || "asc";
		let sortedData = null;
		// viene riordinato in ordine ascendente
		switch (this.sort.active) {
			case "descIndirizzoComposto":
				sortedData = data.sort((propA, propB) => {
					return propA.descIndirizzoComposto.localeCompare(propB.descIndirizzoComposto);
				});
				break;
			case "":
			default:
				sortedData = data;
				break;
		}
		return (direction === "desc") ? sortedData.reverse() : sortedData;
	}

	constructor(data: SedeProgetto[]) {
		super(data);
	}

}
