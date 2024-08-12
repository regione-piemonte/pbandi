import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Resolve, Router } from '@angular/router';
import { HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { Observable } from 'rxjs';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { TabellaVociDiSpesa, mapToTabellaVociDiSpesa, ricalcolaTotaliSpese } from 'src/app/rendicontazione-a20/commons/conto-economico-vo';
import { EntrateResponse, Totali, mapVociDiEntrata, ricalcolaTotali } from 'src/app/rendicontazione-a20/commons/entrate-vo';
import { RendicontazioneA20Service } from 'src/app/rendicontazione-a20/services/rendicontazione-a20.service';
import { SpesaService } from 'src/app/rendicontazione-a20/services/spesa.service';
import { SharedService } from 'src/app/shared/services/shared.service';
@Component({
	selector: 'app-specchietto-saldo',
	templateUrl: './specchietto-saldo.component.html',
	styleUrls: ['./specchietto-saldo.component.scss'],
})
export class SpecchiettoSaldoComponent implements OnInit, Resolve<Observable<number>> {
	subscribers: any = {};
	idProgetto: number;
	idBandoLinea: number;
	idSoggetto;
	isValidazioneA20 = false;
	codiceRuolo;
	isIntegrativa: boolean;
	isBandoCultura = true;
	codiceProgetto: number;
	beneficiario: string;
	user: UserInfoSec;
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
	controlloSuperato: number;
	dichiarazioneDiSpesaInviabile: boolean;
	dichiarazioneDiSpesaInviabile2 = 3;
	saldoDifferenzaSpesaPreventivata: number;

	//LOADING
	loadedChiudiAttivita: boolean = true;
	loadedDatiEntrate: boolean = true;
	loadedDatiSpese: boolean = true;
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
	) {}

	ngOnInit(): void {
		this.loadedDatiEntrate = false;
		this.loadedDatiSpese = false;
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
			}
		});
	}

	aggiornaDati() {
		this.resolve();
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
			this.loadedDatiEntrate = true;
		});

		this.getSpesa(this.idProgetto).subscribe(data => {
			if (data) {
				this.spesaVo = mapToTabellaVociDiSpesa(data);
				this.dataTermineDomanda = this.spesaVo.dataTermineDomanda;
				this.dataTerminePreventivo = this.spesaVo.dataTerminePreventivo;
				this.saldoSpeseConnesseAttivita = this.spesaVo.figli[0].importoSpesaRendicontata;
				this.loadedChiudiAttivita = true;
				this.spesaVo.totali.preventivata = ricalcolaTotaliSpese(this.spesaVo).totaleVerde;
			}
			this.loadedDatiSpese = true;
		});
		return;
	}

	controlloSaldo(): number {
		const C =
			this.spesaVo?.figli[0]?.importoSpesaRendicontata +
			this.spesaVo?.figli[0]?.importoSpesaPreventivata +
			this.spesaVo?.figli[1]?.importoSpesaPreventivata +
			(this.spesaVo?.figli[1]?.importoSpesaRendicontata * this.spesaVo?.percentuale) / 100;
		const F = this.totali.totaleConsuntivoPresentato + this.spesaVo?.contributoConcesso;
		return parseFloat((C - F).toFixed(2));
	}

	differenzaSpesaPreventivata(): number {
		this.saldoDifferenzaSpesaPreventivata = this.spesaVo.totali.preventivo - this.spesaVo.totali.consuntivo;
		return this.spesaVo.totali.preventivata - this.saldoDifferenzaSpesaPreventivata;
	}

	controlloDichiarazioneInviabile() {
		if (this.controlloSaldo() >= 0) {
			return true;
		}
		return false;
	}

	saldoMaggioreDiZero() {
		if (this.controlloSaldo() > 0) {
			return true;
		}
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

	//Al momento non serve questo metodo, sto verificando se eliminarlo o meno
	entrateChange(event: any) {}

	isLoading() {
		if (!this.loadedChiudiAttivita || !this.loadedDatiEntrate || !this.loadedDatiSpese) {
			return true;
		}
		return false;
	}
}
