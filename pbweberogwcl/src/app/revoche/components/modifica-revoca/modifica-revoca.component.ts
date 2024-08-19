/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { RevocheService } from "../../services/revoche.service";
import { InizializzazioneService } from "../../../shared/services/inizializzazione.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { Constants, DatiProgettoAttivitaPregresseDialogComponent } from "@pbandi/common-lib";
import { CodiceDescrizione } from "../../../shared/commons/models/codice-descrizione";
import { DettaglioRevoca } from "../../commons/models/dettaglio-revoca";
import { FormControl, NgForm } from "@angular/forms";
import { MatSelectChange } from "@angular/material/select";
import { EsitoSalvaRevocaDTO } from "../../commons/dto/esito-salva-revoca-dto";
import { RequestModificaRevoca } from "../../commons/requests/request-modifica-revoca";
import { MatDialog } from '@angular/material/dialog';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
	selector: 'app-modifica-revoca',
	templateUrl: './modifica-revoca.component.html',
	styleUrls: ['./modifica-revoca.component.scss']
})
export class ModificaRevocaComponent implements OnInit {
	idProgetto: number;
	idBandoLinea: number;
	idRevoca: number;
	codiceProgetto: string;
	user: UserInfoSec;
	isBR69: boolean;

	isMessageSuccessVisible: any;
	isMessageWarningVisible: any;
	isMessageErrorVisible: any;

	messageSuccess: any;
	messageWarning: any;
	messageError: any;

	noElementsMessageVisible: any;

	//OBJECT SUBSCRIBER
	subscribers: any = {};

	//LOADED
	loadedCodiceProgetto: boolean;
	loadedMotivazioni: boolean;
	loadedRevoca: boolean;
	loadedModifica: boolean;
	loadedBR69: boolean;

	// REQUEST
	dettaglioRevocaRequest: RevocheService.GetDettaglioRevocaParams;
	requestModificaRevoca: RequestModificaRevoca;

	// RESPONSE
	motivazioni: Array<CodiceDescrizione>;
	esitoSalvaRevoca: EsitoSalvaRevocaDTO;

	// FORM INPUT
	motivoSelected: CodiceDescrizione;
	importoFormatted: string;

	formControlDataRevoca: FormControl = new FormControl();
	formControlDataDecorrenza: FormControl = new FormControl();

	dettaglioRevoca: DettaglioRevoca = { modalitaAgevolazione: "" };

	@ViewChild("revocaForm", { static: true }) revocaForm: NgForm;

	constructor(
		private configService: ConfigService,
		private userService: UserService,
		private activatedRoute: ActivatedRoute,
		private revocheService: RevocheService,
		private inizializzazioneService: InizializzazioneService,
		private router: Router,
		private handleExceptionService: HandleExceptionService,
		private sharedService: SharedService,
		private dialog: MatDialog
	) {
	}

	ngOnInit(): void {
		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
			if (data) {
				this.user = data;
				if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
					this.subscribers.router = this.activatedRoute.params.subscribe(params => {
						this.idProgetto = +params['idProgetto'];
						this.idBandoLinea = +params['idBandoLinea'];
						this.idRevoca = +params['id1'];
						this.loadedRevoca = false;
						this.dettaglioRevocaRequest = { idRevoca: this.idRevoca, idProgetto: this.idProgetto }
						this.subscribers.codice = this.revocheService.getDettaglioRevoca(this.dettaglioRevocaRequest).subscribe(res => {
							if (res) {
								this.dettaglioRevoca = res;
								if (this.dettaglioRevoca.dataRevoca) {
									this.formControlDataRevoca.setValue(this.sharedService.parseDate(this.dettaglioRevoca.dataRevoca));
								}
								if (this.dettaglioRevoca.dtDecorRevoca) {
									this.formControlDataDecorrenza.setValue(this.sharedService.parseDate(this.dettaglioRevoca.dtDecorRevoca));
								}
								if (this.dettaglioRevoca.importoRevoca !== null) {
									this.importoFormatted = this.sharedService.formatValue(this.dettaglioRevoca.importoRevoca.toString());
								}
								this.loadData();
							}
							this.loadedRevoca = true;
						}, err => {
							this.handleExceptionService.handleNotBlockingError(err);
							this.showMessageError("Errore in fase di recupero del dettaglio.");
							this.loadedRevoca = true;
						});
					});
				}
			}
		});
	}

	loadData() {
		this.loadedCodiceProgetto = false;
		this.subscribers.codice = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
			if (res) {
				this.codiceProgetto = res;
			}
			this.loadedCodiceProgetto = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.loadedCodiceProgetto = true;
		});
		this.loadedMotivazioni = false;
		this.subscribers.codice = this.revocheService.findAndFilterMotivazioni(this.idProgetto).subscribe(res => {
			if (res) {
				this.motivazioni = res;
				if (this.dettaglioRevoca.idMotivoRevoca) {
					this.motivoSelected = this.motivazioni.find(d => d.codice === this.dettaglioRevoca.idMotivoRevoca.toString());
				}
			}
			this.loadedMotivazioni = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento dei motivi.");
			this.loadedMotivazioni = true;
		});
		this.loadedBR69 = false;
		this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR69").subscribe(res => {
			this.isBR69 = res;
			this.loadedBR69 = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento della regola BR69.");
			this.loadedBR69 = true;
		});
	}

	setImporto() {
		this.dettaglioRevoca.importoRevoca = this.sharedService.getNumberFromFormattedString(this.importoFormatted);
		if (this.dettaglioRevoca.importoRevoca != null) {
			this.importoFormatted = this.sharedService.formatValue(this.dettaglioRevoca.importoRevoca.toString());
		}
	}

	modificaRevoca() {
		this.resetMessageError();
		this.resetMessageSuccess()
		let hasError: boolean;
		if (this.motivoSelected == null || this.motivoSelected == undefined) {
			this.revocaForm.form.get('motivo').setErrors({ error: 'required' });
			hasError = true;
		}
		if (!this.formControlDataRevoca || !this.formControlDataRevoca.value) {
			this.formControlDataRevoca.setErrors({ error: 'required' });
			hasError = true;
		}
		if (this.isBR69 && (!this.formControlDataDecorrenza || !this.formControlDataDecorrenza.value)) {
			this.formControlDataDecorrenza.setErrors({ error: 'required' });
			hasError = true;
		}
		if (this.dettaglioRevoca.importoRevoca === null) {
			this.revocaForm.form.get('importo').setErrors({ error: 'required' });
			hasError = true;
		}
		this.revocaForm.form.markAllAsTouched();
		if (hasError) {
			this.showMessageError("Inserire tutti i campi obbligatori.");
			return;
		}
		this.loadedModifica = true;
		this.dettaglioRevoca.idMotivoRevoca = this.motivoSelected ? +this.motivoSelected.codice : null;
		this.dettaglioRevoca.dataRevoca = this.formControlDataRevoca && this.formControlDataRevoca.value ? this.sharedService.formatDateToString(this.formControlDataRevoca.value) : null;
		this.dettaglioRevoca.dtDecorRevoca = this.formControlDataDecorrenza && this.formControlDataDecorrenza.value ? this.sharedService.formatDateToString(this.formControlDataDecorrenza.value) : null;
		this.requestModificaRevoca = { revoca: this.dettaglioRevoca, idProgetto: this.idProgetto }
		this.subscribers.codice = this.revocheService.modificaRevoca(this.requestModificaRevoca).subscribe(res => {
			if (res.esito) {
				this.esitoSalvaRevoca = res;
				this.showMessageSuccess(res.msgs[0].msgKey);
			} else {
				this.showMessageError(res.msgs[0].msgKey);
			}
			this.loadedModifica = false;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di salvataggio.");
			this.loadedModifica = false;
		});
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////// UTILITY FUNCTIONS /////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	goToGestioneRevoche() {
		this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_REVOCHE}/revoche/${this.idProgetto}/${this.idBandoLinea}`]);
	}

	goToDatiProgettoEAttivitaPregresse() {
		this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
			width: '90%',
			maxHeight: '95%',
			data: {
				idProgetto: this.idProgetto,
				apiURL: this.configService.getApiURL()
			}
		});
	}

	showMessageSuccess(msg: string) {
		this.messageSuccess = msg;
		this.isMessageSuccessVisible = true;
		let top = document.getElementById('scrollId');
		top.scrollIntoView();
	}
	showMessageWarning(msg: string) {
		this.messageWarning = msg;
		this.isMessageWarningVisible = true;
		let top = document.getElementById('scrollId');
		top.scrollIntoView();
	}
	showMessageError(msg: string) {
		this.messageError = msg;
		this.isMessageErrorVisible = true;
		let top = document.getElementById('scrollId');
		top.scrollIntoView();
	}
	resetMessageSuccess() {
		this.messageSuccess = null;
		this.isMessageSuccessVisible = false;
	}
	resetMessageError() {
		this.messageError = null;
		this.isMessageErrorVisible = false;
	}
	resetMessageWarning() {
		this.messageWarning = null;
		this.isMessageWarningVisible = false;
	}


	onSelectMotivo($event: MatSelectChange) {
		this.motivoSelected = $event.value;
	}

	resetAllMessages() {
		this.resetMessageError();
		this.resetMessageSuccess();
		this.resetMessageWarning();
	}

	isLoading() {
		if (!this.loadedCodiceProgetto || !this.loadedMotivazioni || !this.loadedCodiceProgetto ||
			!this.loadedRevoca || this.loadedModifica || !this.loadedBR69) {
			return true;
		}
		return false;
	}
}
