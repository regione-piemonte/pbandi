/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { CodiceDescrizione } from 'src/app/disimpegni/commons/models/codice-descrizione';
import { InizializzazioneService } from 'src/app/shared/services/inizializzazione.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { EsitoSalvaRevocaDTO } from '../../commons/dto/esito-salva-revoca-dto';
import { RigaModificaRevocaItem } from '../../commons/models/riga-modifica-revoca-item';
import { RigaRevocaItem } from '../../commons/models/riga-revoca-item';
import { RequestSalvaRevoche } from '../../commons/requests/request-salva-revoche';
import { RevocheService } from '../../services/revoche.service';

@Component({
	selector: 'app-nuova-revoca',
	templateUrl: './nuova-revoca.component.html',
	styleUrls: ['./nuova-revoca.component.scss']
})
export class NuovaRevocaComponent implements OnInit {
	idProgetto: number;
	idBandoLinea: number;
	codiceProgetto: string;
	user: UserInfoSec;
	isMaster: boolean;

	motivazioneSelected: CodiceDescrizione;
	dataRevoca: Date;
	dataDecorrenza: Date;
	estremiDetermina: string;
	note: string;
	requestSalvaRevoche: RequestSalvaRevoche;
	confermaSalvataggio: boolean = false;
	isBR69: boolean;

	// RESPONSE
	motivazioni: Array<CodiceDescrizione>;
	importoValidatoTotale: number;
	revoche: Array<RigaRevocaItem> = new Array<RigaRevocaItem>();
	esitoSalvaRevoche: EsitoSalvaRevocaDTO;

	displayedColumns: string[] = ['modalitaDiAgevolazione', 'ultimoImportoAgevolato', 'importoGiaErogato', 'importoGiaRevocato', 'importoGiaRecuperato', 'importoNuovaRevoca'];
	dataSource: MatTableDataSource<RigaRevocaItem> = new MatTableDataSource<RigaRevocaItem>();

	@ViewChild("nuovoForm", { static: true }) nuovoForm: NgForm;

	isMessageSuccessVisible: any;
	isMessageWarningVisible: any;
	isMessageErrorVisible: any;

	messageSuccess: any;
	messageWarning: any;
	messageError: any;

	//LOADED
	loadedCodiceProgetto: boolean;
	loadedMotivazioni: boolean;
	loadedImportoValidatoTotale: boolean;
	loadedRevoche: boolean;
	loadedRevocaTutto: boolean = true;
	loadedCheckRevoche: boolean = true;
	loadedSalvaRevoche: boolean;
	loadedBR69: boolean;

	//OBJECT SUBSCRIBER
	subscribers: any = {};

	constructor(
		private configService: ConfigService,
		private userService: UserService,
		private activatedRoute: ActivatedRoute,
		private revocheService: RevocheService,
		private inizializzazioneService: InizializzazioneService,
		private router: Router,
		private handleExceptionService: HandleExceptionService,
		private datePipe: DatePipe,
		private dialog: MatDialog,
		private sharedService: SharedService
	) { }

	ngOnInit(): void {
		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
			if (data) {
				this.user = data;
				if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
					this.subscribers.router = this.activatedRoute.params.subscribe(params => {
						this.idProgetto = +params['idProgetto'];
						this.idBandoLinea = +params['idBandoLinea'];
						if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER
							|| this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER
							|| this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE) {
							this.isMaster = true;
						}
						this.loadData();
					});
				}
			}
		});
	}

	loadData() {

		// Codice progetto
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

		// POPOLA LA COMBO MOTIVAZIONI
		this.loadedMotivazioni = false;
		this.subscribers.codice = this.revocheService.findAndFilterMotivazioni(this.idProgetto).subscribe(res => {
			if (res) {
				this.motivazioni = res;
			}
			this.loadedMotivazioni = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.loadedMotivazioni = true;
		});
		// IMPORTO VALIDATO TOTALE
		this.loadedImportoValidatoTotale = false;
		this.subscribers.codice = this.revocheService.getImportoValidatoTotale(this.idProgetto).subscribe(res => {
			if (res) {
				this.importoValidatoTotale = res;
			}
			this.loadedImportoValidatoTotale = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.loadedImportoValidatoTotale = true;
		});

		// REVOCHE
		this.loadedRevoche = false;
		this.subscribers.codice = this.revocheService.getRevoche(this.idProgetto).subscribe(res => {
			if (res) {
				this.revoche = res;
				this.dataSource = new MatTableDataSource<RigaRevocaItem>();
				this.dataSource.data = this.revoche;
			}
			this.loadedRevoche = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.loadedRevoche = true;
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

	setImportoNuovaRevoca(riga: RigaRevocaItem) {
		riga.importoRevocaNew = this.sharedService.getNumberFromFormattedString(riga.importoNuovaRevocaFormatted);
		if (riga.importoRevocaNew != null) {
			riga.importoNuovaRevocaFormatted = this.sharedService.formatValue(riga.importoRevocaNew.toString());
		}
	}

	revocaTutto() {
		this.resetMessageSuccess();
		this.resetMessageError();
		this.resetMessageWarning();
		for (let item of this.revoche) {
			if (item.isRigaModalita && item.importoRevocato !== 0) {
				item.importoRevocaNew = 0;
				if (!item.importoErogato) {
					item.importoErogato = 0;
				}
				if (!item.importoAgevolato) {
					item.importoAgevolato = 0;
				}
				if (!item.importoRevocato) {
					item.importoRevocato = 0;
				}
				if (item.importoErogato > item.importoAgevolato) {
					item.importoRevocaNew = item.importoErogato - item.importoAgevolato;
				} else {
					item.importoRevocaNew = item.importoErogato;
				}
				item.importoRevocaNew = item.importoRevocaNew - item.importoRevocato;
				if (item.importoRevocaNew !== null) {
					item.importoNuovaRevocaFormatted = this.sharedService.formatValue(item.importoRevocaNew.toString());
				}
			}
		}
		this.showMessageWarning("Il Sistema ha calcolato gli importi da revocare per modalità di agevolazione.");

	}

	annulla() {
		this.resetMessageSuccess();
		this.resetMessageWarning();
		this.resetMessageError();
		this.confermaSalvataggio = false;
	}

	checkRevoche() {
		this.resetMessageSuccess();
		this.resetMessageError();
		this.resetMessageWarning();
		let hasError: boolean;
		if (this.motivazioneSelected == null || this.motivazioneSelected == undefined) {
			this.nuovoForm.form.get('motivazione').setErrors({ error: 'required' });
			hasError = true;
		}
		if (!this.dataRevoca) {
			this.nuovoForm.form.get('dataRevoca').setErrors({ error: 'required' });
			hasError = true;
		}
		if (!this.dataDecorrenza && this.isBR69) {
			this.nuovoForm.form.get('dataDecorrenza').setErrors({ error: 'required' });
			hasError = true;
		}
		this.nuovoForm.form.markAllAsTouched();
		if (hasError) {
			this.showMessageError("Inserire tutti i campi obbligatori.")
			return;
		}
		let rev = new Array<RigaRevocaItem>();
		this.revoche.forEach(r => rev.push(Object.assign({}, r)));
		rev.forEach(r => {
			delete r['importoNuovaRevocaFormatted'];
		});

		this.requestSalvaRevoche = {
			dtRevoca: this.dataRevoca ? this.datePipe.transform(this.dataRevoca, 'dd/MM/yyyy') : undefined,
			estremi: this.estremiDetermina ? this.estremiDetermina : undefined,
			idMotivoRevoca: this.motivazioneSelected ? parseInt(this.motivazioneSelected.codice) : undefined,
			idProgetto: this.idProgetto,
			note: this.note ? this.note : undefined,
			revoche: rev,
			dtDecorRevoca: this.dataDecorrenza ? this.datePipe.transform(this.dataDecorrenza, 'dd/MM/yyyy') : undefined,
		};
		this.loadedCheckRevoche = false;
		this.revocheService.checkRevoche(this.requestSalvaRevoche).subscribe(res => {
			if (res) {
				let messaggio: string = '';
				if (res.msgs && res.msgs.length > 0) {
					res.msgs.forEach(m => {
						messaggio += m.msgKey + "<br/>";
					});
					messaggio = messaggio.substr(0, messaggio.length - 5);
				}
				if (res.esito) {
					this.confermaSalvataggio = true;
					this.showMessageWarning(messaggio);

				} else {
					this.showMessageError(messaggio);
				}
			}
			this.loadedCheckRevoche = true;

		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di validazione dei campi.");
			this.loadedCheckRevoche = true;
		});
	}

	conferma() {
		this.loadedSalvaRevoche = true;
		this.resetMessageSuccess();
		this.resetMessageError();
		this.resetMessageWarning();
		this.subscribers.codice = this.revocheService.salvaRevoche(this.requestSalvaRevoche).subscribe(res => {
			if (res) {
				let messaggio: string = '';
				if (res.msgs && res.msgs.length > 0) {
					res.msgs.forEach(m => {
						messaggio += m.msgKey + "<br/>";
					});
					messaggio = messaggio.substr(0, messaggio.length - 5);
				}
				if (res.esito) {
					this.goToRevoche("Salvataggio avvenuto con successo.");
				} else {
					if (res && res.msgs) {
						this.showMessageError(messaggio);
					} else {
						this.showMessageError("Errore in fase di salvataggio.");
					}
				}
			}
			this.loadedSalvaRevoche = false;
			this.confermaSalvataggio = false;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.loadedSalvaRevoche = false;
			this.confermaSalvataggio = false;
		});
	}

	goToRevoche(message?: string) {
		let url: string = `drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_REVOCHE}/revoche/${this.idProgetto}/${this.idBandoLinea}`;
		if (message) {
			url += ";message=" + message;
		}
		this.router.navigateByUrl(url);
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

	isLoading() {
		if (!this.loadedCodiceProgetto || !this.loadedMotivazioni || !this.loadedImportoValidatoTotale
			|| !this.loadedRevoche || !this.loadedRevocaTutto || !this.loadedCheckRevoche
			|| this.loadedSalvaRevoche || !this.loadedBR69) {
			return true;
		}
		return false;
	}
}
