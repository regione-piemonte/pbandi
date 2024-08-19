/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { SharedService } from "../../../shared/services/shared.service";
import { RevocheService } from "../../services/revoche.service";
import { InizializzazioneService } from "../../../shared/services/inizializzazione.service";
import { CodiceDescrizione } from "../../../shared/commons/models/codice-descrizione";
import { DatiProgettoAttivitaPregresseDialogComponent } from "@pbandi/common-lib";
import { MatTableDataSource } from "@angular/material/table";
import { RequestSalvaRevoche } from "../../commons/requests/request-salva-revoche";
import { EsitoSalvaRevocaDTO } from "../../commons/dto/esito-salva-revoca-dto";
import { ConfermaDialog } from "../../../shared/components/conferma-dialog/conferma-dialog";
import { MatDialog } from "@angular/material/dialog";
import { RigaModificaRevocaItem } from 'src/app/disimpegni/commons/models/riga-modifica-revoca-item';
import { RigaRevocaItem } from '../../commons/models/riga-revoca-item';
import { Constants } from 'src/app/core/commons/util/constants';
import { EsitoOperazioni } from 'src/shared/api/models/esito-operazioni';

@Component({
	selector: 'app-gestione-revoche',
	templateUrl: './gestione-revoche.component.html',
	styleUrls: ['./gestione-revoche.component.scss']
})
export class GestioneRevocheComponent implements OnInit {
	idProgetto: number;
	idBandoLinea: number;
	codiceProgetto: string;
	user: UserInfoSec;
	isMaster: boolean;
	isBR69: boolean;
	esistePropostaCertif: EsitoOperazioni;

	isMessageSuccessVisible: any;
	isMessageErrorVisible: any;

	messageSuccess: any;
	messageError: any;

	noElementsMessageVisible: any;
	noElementsMessage: string = "Non ci sono elementi da visualizzare.";

	//LOADED
	loadedCodiceProgetto: boolean;
	loadedPropostaCertif: boolean;
	loadedRevoche: boolean;
	loadedChiudiAttivita: boolean = true;
	loadedElimina: boolean;
	loadedBR69: boolean;

	//OBJECT SUBSCRIBER
	subscribers: any = {};

	motivazioneSelected: CodiceDescrizione;
	dataRevoca: Date;

	estremiDetermina: string;
	note: string;

	// REQUEST
	requestSalvaRevoche: RequestSalvaRevoche;

	// RESPONSE
	motivazioni: Array<CodiceDescrizione>;
	importoValidatoTotale: number;
	revoche: Array<RigaModificaRevocaItem> = new Array<RigaModificaRevocaItem>();
	esitoSalvaRevoche: EsitoSalvaRevocaDTO;

	displayedColumns: string[] = [];
	displayedColumnsBase: string[] = ['modalitaDiAgevolazione', 'ultimoImportoAgevolato', 'importoGiaErogato', 'importoGiaRevocato', 'importoGiaRecuperato', 'data', 'riferimento', 'azioni'];
	displayedColumnsBR69: string[] = ['modalitaDiAgevolazione', 'ultimoImportoAgevolato', 'importoGiaErogato', 'importoGiaRevocato', 'importoGiaRecuperato', 'data', 'dataDecorrenza', 'riferimento', 'azioni'];
	dataSource: MatTableDataSource<RigaRevocaItem> = new MatTableDataSource<RigaRevocaItem>();

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
					if (this.activatedRoute.snapshot.paramMap.get('message')) {
						this.showMessageSuccess(this.activatedRoute.snapshot.paramMap.get('message'));
					}
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
		this.loadedPropostaCertif = false;
		this.subscribers.codice = this.revocheService.checkPropostaCertificazioneProgetto(this.idProgetto).subscribe(res => {
			if (res) {
				this.esistePropostaCertif = res;
			}
			this.loadedPropostaCertif = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.loadedPropostaCertif = true;
		});
		this.loadedBR69 = false;
		this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR69").subscribe(res => {
			this.isBR69 = res;
			this.displayedColumns = [];
			if (this.isBR69) {
				this.displayedColumns = this.displayedColumnsBR69;
			} else {
				this.displayedColumns = this.displayedColumnsBase;
			}
			this.loadedBR69 = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento della regola BR69.");
			this.loadedBR69 = true;
		});
		this.loadRevoche();
	}

	loadRevoche() {
		this.loadedRevoche = false;
		this.subscribers.codice = this.revocheService.getRevochePerModifica(this.idProgetto).subscribe(res => {
			if (res) {
				this.revoche = res;
				this.dataSource = new MatTableDataSource<RigaRevocaItem>();
				this.dataSource.data = this.revoche;
				if (res.length > 0) {
					this.noElementsMessageVisible = false;
				} else {
					this.noElementsMessageVisible = true;
				}
			}
			this.loadedRevoche = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.loadedRevoche = true;
		});
	}

	confermaDialog(idRevoca) {
		this.resetMessageError();
		this.resetMessageSuccess();
		let dialogRef = this.dialog.open(ConfermaDialog, {
			width: '40%',
			data: {
				message: "Confermi l'eliminazione?"
			}
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res === "SI") {
				// CHIAMA SERVIZIO ELIMINA REVOCA
				this.eliminaRevoca(idRevoca);
			}
		});
	}

	eliminaRevoca(idRevoca: any) {
		this.loadedElimina = true;
		this.subscribers.elimina = this.revocheService.cancellaRevoca(idRevoca).subscribe(res => {
			if (res.esito) {
				this.esitoSalvaRevoche = res;
				this.showMessageSuccess(res.msgs[0].msgKey);
				this.loadRevoche();
			} else {
				console.log(res.msgs[0].msgKey);
				this.showMessageError(res.msgs[0].msgKey);
			}
			this.loadedElimina = false;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.loadedElimina = false;
		});
	}

	goToAttivita() {
		this.resetMessageError();
		this.loadedChiudiAttivita = false;;
		this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_GESTIONE_REVOCHE).subscribe(data => {
			window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.loadedChiudiAttivita = true;
			this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
			this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
		});
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

	goToNuovaRevoca() {
		this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_REVOCHE}/nuovaRevoca/${this.idProgetto}/${this.idBandoLinea}`]);
	}

	goToModificaRevoca(idModalitaAgevolazione: number) {
		this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_REVOCHE}/modificaRevoca/${this.idProgetto}/${this.idBandoLinea}/${idModalitaAgevolazione}`]);
	}

	isLoading() {
		if (!this.loadedCodiceProgetto || !this.loadedRevoche || this.loadedElimina
			|| !this.loadedChiudiAttivita || !this.loadedPropostaCertif || !this.loadedBR69) {
			return true;
		}
		return false;
	}
}
