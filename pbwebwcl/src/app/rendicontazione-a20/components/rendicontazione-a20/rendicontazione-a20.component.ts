/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Resolve, Router } from '@angular/router';
import { DatiProgettoAttivitaPregresseDialogComponent, HandleExceptionService, UserInfoSec, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { Observable } from 'rxjs';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { TabellaVociDiSpesa, mapToTabellaVociDiSpesa } from '../../commons/conto-economico-vo';
import { EntrateResponse, Totali, mapVociDiEntrata, ricalcolaTotali } from '../../commons/entrate-vo';
import { RendicontazioneA20Service } from '../../services/rendicontazione-a20.service';
import { SpesaService } from '../../services/spesa.service';
import { SpecchiettoSaldoComponent } from './specchietto-saldo/specchietto-saldo.component';
@Component({
	selector: 'app-rendicontazione-a20',
	templateUrl: './rendicontazione-a20.component.html',
	styleUrls: ['./rendicontazione-a20.component.scss'],
})
export class RendicontazioneA20Component implements OnInit, Resolve<Observable<number>> {
	subscribers: any = {};
	idProgetto: number;
	idBandoLinea: number;
	isBandoCultura: string = 'true';
	idSoggetto;
	isValidazioneA20 = false;
	codiceRuolo;
	isIntegrativa: boolean;

	codiceProgetto: number;
	beneficiario: string;
	user: UserInfoSec;
	loadedChiudiAttivita: boolean = true;
	isMessageErrorVisible: boolean;
	messageError: any;
	rendicontazioneInfo: any;
	isReadOnly: boolean;
	isBR58: any;

	//SPESE X ENTRATE
	dataTerminePreventivo;
	dataTermineDomanda;
	// percImportoAgevolato: number;

	//SPESE
	spesaVo: TabellaVociDiSpesa;
	saldoSpeseConnesseAttivita: number;

	//ENTRATE
	entrateVo: EntrateResponse;
	totali: Totali = {
		totaleRichiesto: 0,
		totaleAmmesso: 0,
		totaleFinanzBancaRichiesto: 0,
		totaleFinanziamentoBanca: 0,
		totaleImpegnoContabile: 0,
		totaleImpegnoVincolante: 0,
		totaleQuietanzato: 0,
		totaleRendicontato: 0,
		totaleValidato: 0,
		totaleConsuntivoPresentato: 0,
	};

	//SALDO RENDICONTABILE
	saldoRendicontabile;

	constructor(
		private userService: UserService,
		private activatedRoute: ActivatedRoute,
		private sharedService: SharedService,
		private configService: ConfigService,
		private handleExceptionService: HandleExceptionService,
		public rendicontazioneA20Service: RendicontazioneA20Service,
		private dialog: MatDialog,
		private router: Router,
		private spesaService: SpesaService,
	) { }

	@ViewChild(SpecchiettoSaldoComponent) appSpecchiettoSaldo!: SpecchiettoSaldoComponent;

	saldoAggiornato() {
		this.appSpecchiettoSaldo.aggiornaDati();
	}

	ngOnInit(): void {
		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
			if (data) {
				this.user = data;
				(this.idSoggetto = this.user.idSoggetto), (this.codiceRuolo = this.user.codiceRuolo);
				this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
				if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADA_OPE_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_GDF || this.user.codiceRuolo === Constants.CODICE_RUOLO_OPE_SUP_IST) {
					this.isReadOnly = true;
				}
				this.subscribers.router = this.activatedRoute.params.subscribe(params => {
					this.idProgetto = +params['idProgetto'];
					this.idBandoLinea = +params['idBandoLinea'];
					this.isIntegrativa = params['integrativa'] ? true : false;
				});
				this.resolve();
				this.inizializzaRendicontazione();
			}
		});
	}

	resolve(): Observable<any> {
		this.loadedChiudiAttivita = false;
		this.getEntrate(this.idProgetto).subscribe(data => {
			if (data) {
				this.entrateVo = data;
				this.entrateVo.vociDiEntrataCultura = ricalcolaTotali(mapVociDiEntrata(data.vociDiEntrataCultura)).vociDiEntrataCultura;
				this.totali = ricalcolaTotali(mapVociDiEntrata(data.vociDiEntrataCultura)).totali;
				// this.getPercentualeImportoAgevolato(this.idBandoLinea).subscribe(
				// 	data => {
				// 		if (data) {
				// 			this.percImportoAgevolato = data;
				// 		}
				// 	},
				// 	err => {
				// 		console.error("Non trovo la percentuale dell'importo agevolato");
				// 	},
				// );
			}
		});

		this.getSpesa(this.idProgetto).subscribe(data => {
			if (data) {
				this.spesaVo = mapToTabellaVociDiSpesa(data);
				this.dataTermineDomanda = this.spesaVo.dataTermineDomanda;
				this.dataTerminePreventivo = this.spesaVo.dataTerminePreventivo;
				this.saldoSpeseConnesseAttivita = this.spesaVo.figli[0].importoSpesaRendicontata;
				this.loadedChiudiAttivita = true;
			}
		});
		return;
	}

	getSpesa(idProgetto: any) {
		return this.spesaService.getSpesa(this.idProgetto);
	}

	getEntrate(idProgetto: any) {
		return this.spesaService.getEntrate(this.idProgetto);
	}

	// getPercentualeImportoAgevolato(idBandoLinea: number) {
	// 	return this.spesaService.getPercentualeImportoAgevolato(this.entrateVo.vociDiEntrataCultura[0].idBando);
	// }

	entrateChange(event: any) {
		this.entrateVo.vociDiEntrataCultura = event;
	}

	speseChange(event: any) {
		this.spesaVo.figli = event;
	}

	spesaPreventivataChange(event: any) {
		this.spesaVo.totali.preventivata = event;
	}

	inizializzaRendicontazione() {
		this.rendicontazioneA20Service.inizializzaRendicontazione(this.idProgetto, this.idBandoLinea, this.idSoggetto, this.codiceRuolo).subscribe(
			data => {
				if (data) {
					this.rendicontazioneInfo = data;
					this.codiceProgetto = this.rendicontazioneInfo.codiceVisualizzatoProgetto;
					this.isBR58 = this.rendicontazioneInfo.isBR58;
				}
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	datiProgettoEAttivitaPregresse() {
		this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
			width: '90%',
			maxHeight: '95%',
			data: {
				idProgetto: this.idProgetto,
				apiURL: this.configService.getApiURL(),
			},
		});
	}

	contoEconomico() {
		this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
			width: '90%',
			maxHeight: '90%',
			data: {
				idBandoLinea: this.idBandoLinea,
				idProgetto: this.idProgetto,
				apiURL: this.configService.getApiURL(),
			},
		});
	}

	gestioneFornitori() {
		let params = this.isIntegrativa ? { da: 'rendicontazione', integrativa: true } : { da: 'rendicontazione' };
		this.router.navigate(['drawer/' + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE_CULTURA + '/gestioneFornitori/' + this.idProgetto + '/' + this.idBandoLinea, params]);
	}


	tornaAAttivitaDaSvolgere() {
		this.resetMessageError();
		this.loadedChiudiAttivita = false;
		this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto,
			this.isIntegrativa ? Constants.DESCR_BREVE_TASK_RENDICONTAZIONE_INTEGRATIVA_CULTURA : Constants.DESCR_BREVE_TASK_RENDICONTAZIONE_CULTURA).subscribe(
				data => {
					window.location.assign(this.configService.getPbworkspaceURL() + '/#/drawer/' + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + '/attivita');
				},
				err => {
					this.handleExceptionService.handleNotBlockingError(err);
					this.loadedChiudiAttivita = true;
					this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
					this.router.navigate(['/errorRouting'], { queryParams: { message: 'Sessione scaduta' }, skipLocationChange: true });
				},
			);
	}

	resetMessageError() {
		this.messageError = null;
		this.isMessageErrorVisible = false;
	}

	showMessageError(msg: string) {
		this.messageError = msg;
		this.isMessageErrorVisible = true;
		const element = document.querySelector('#scrollId');
		element.scrollIntoView();
	}

	isLoading() {
		if (!this.loadedChiudiAttivita) {
			return true;
		}
		return false;
	}
}
