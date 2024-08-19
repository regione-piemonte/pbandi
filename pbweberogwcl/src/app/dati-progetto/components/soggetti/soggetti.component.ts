/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { HandleExceptionService } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { SoggettoProgettoDTO } from '../../commons/dto/soggetto-progetto-dto';
import { Recapiti } from '../../commons/models/recapiti';
import { RequestCambiaStatoSoggettoProgetto } from '../../commons/models/request-cambia-stato-soggetto-progetto';
import { DatiProgettoService } from '../../services/dati-progetto.service';
import { DisattivaSoggettoDialogComponent } from '../disattiva-soggetto-dialog/disattiva-soggetto-dialog.component';
import { EsitoOperazioni } from '../../commons/models/esito-operazioni';

@Component({
	selector: 'app-soggetti',
	templateUrl: './soggetti.component.html',
	styleUrls: ['./soggetti.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class SoggettiComponent implements OnInit {

	@Input("idProgetto") idProgetto: number;
	@Input("idBando") idBando: number;
	@Input("soggettiProgetto") soggettiProgetto: SoggettoProgettoDTO[];
	@Input("isBenPersonaFisica") isBenPersonaFisica: boolean;

	@Output("loadSoggettiProgetto") loadSoggettiProgetto = new EventEmitter<boolean>();

	@Output("messageError") messageError = new EventEmitter<string>();
	@Output("messageSuccess") messageSuccess = new EventEmitter<string>();
	@Output("messageWarning") messageWarning = new EventEmitter<string>();

	user: UserInfoSec;
	action: string;

	recapiti: Recapiti;
	is1420: boolean;
	existsRichiesta: boolean;
	isDisabledEmail: boolean;
	email: string;

	showOnlySoggettiVisible: boolean;

	isFinPiemonte: boolean;

	//TABLE SOGGETTI PROGETTO

	displayedColumnsSoggettiProgetto: string[] = ['codiceFiscaleSoggetto', 'denominazione', 'descTipoSoggettoCorrelato', 'descTipoSoggetto', 'abilitatoAccesso', 'azioni'];
	displayedHeadersSoggettiProgetto: string[] = ['Codice fiscale', 'Cognome nome / Denominazione', 'Ruolo', 'Tipo soggetto', "Abilitato all'accesso", ''];
	displayedFootersSoggettiProgetto: string[] = [];
	displayedColumnsCustomSoggettiProgetto: string[] = ['', '', '', '', '', 'azioni'];
	displayedHeadersCustomSoggettiProgetto: string[] = this.displayedColumnsCustomSoggettiProgetto;
	displayedColumnsCustomSoggettiProgettoAzioni: any = {
		visualizza: 'isDettaglio', modifica: 'isModificabile', slide: 'hasSlide', warning: 'isWarning', personOffButton: 'isRifiutabile', personOffIconRifiuto: 'rifiutata',
		personOffIconDisabled: 'disattivazioneDefinitiva',
		tooltip: {
			slideTrue: "Disabilita soggetto dal progetto", slideFalse: "Abilita al progetto il soggetto non più attivo dal $", slideFalseFieldTypes: ['date'], slideFalseFields: ['dtFineValidita'],
			warning: "Richiesta inviata all'istruttore: in attesa di conferma", personOffButton: "Rifiuta la richiesta di abilitazione del soggetto",
			personOffIconRifiuto: "", personOffIconDisabled: "Soggetto disabilitato in modo permanente"
		}
	};
	dataSourceSoggettiProgetto: MatTableDataSource<SoggettoProgettoDTO> = new MatTableDataSource<SoggettoProgettoDTO>();
	dataSourceSoggettiProgettoSlides: boolean[] = [];

	//LOADED
	loadedLineaPORFESR1420: boolean = true;
	loadedRecapito: boolean = true;
	loadedCambiaStatoSoggettoProgetto: boolean = true;
	loadedSalvaRecapito: boolean = true;
	loadedFinpiemonte: boolean = true;

	subscribers: any = {};

	constructor(
		private datiProgettoService: DatiProgettoService,
		private handleExceptionService: HandleExceptionService,
		private dialog: MatDialog,
		private sharedService: SharedService,
		private userService: UserService,
		private activatedRoute: ActivatedRoute,
		private router: Router,
	) { }

	ngOnInit(): void {
		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
			if (data) {
				this.user = data;
			}
		});
	}

	loadData(isInit?: boolean) {
		this.soggettiProgetto = this.soggettiProgetto.filter((row: SoggettoProgettoDTO) => {
			return this.showOnlySoggettiVisible ? ((!row.dtFineValidita || row.inAttesaEsito) && !row.rifiutata && !row.disattivazioneDefinitiva) : true;
		});
		this.loadedFinpiemonte = false;
		this.subscribers.isFinpiemonte = this.datiProgettoService.isFinpiemonte(this.idProgetto).subscribe(data => {
			this.isFinPiemonte = data;
			this.soggettiProgetto.forEach(s => {
				if (s.rifiutata || s.disattivazioneDefinitiva) {
					// se il soggetto è stato rifiutato o disabilitato permantentemente non è modificabile o attivabile
					s.isModificabile = false;
					s.hasSlide = false;
					s.isWarning = false;
					s.isRifiutabile = false;
				} else {

					// per finpiemonte il beneficiario non può modificare
					// beneficiario soggetto giuridico/persona fisica modificabile solo se ruolo utente è istruttore
					// altri soggetti modificabili sempre dall'istruttore
					// altri soggetti modificabili dal beneficiario se non sono in attesa di esito dall'istruttore           

					if (this.sharedService.isBeneficiario(this.user) && (!this.isBeneficiarioRappresentanteLegale() || this.isFinPiemonte)) {
						s.isModificabile = false;
					} else {
						if (s.idTipoSoggetto !== 2) {
							if (this.isIstruttore()) {
								s.isModificabile = true;
							} else {
								if (s.inAttesaEsito) {
									s.isModificabile = false;
								} else {
									s.isModificabile = true;
								}
							}
						} else {
							if (this.isIstruttore()) {
								s.isModificabile = true;
							} else {
								s.isModificabile = false;
							}
						}
					}
					if (s.codiceFiscaleSoggetto === this.user.codFisc) {
						s.hasSlide = false;
					} else if (s.idTipoAnagrafica === 1) { //BENEFICIARIO sia PERSONA FISICA sia SOGGETTO GIURIDICO
						s.hasSlide = false;
					} else {
						s.hasSlide = s.isModificabile;
					}
					s.isWarning = this.sharedService.isBeneficiario(this.user) && s.inAttesaEsito;
					if (s.inAttesaEsito && this.isIstruttore()) {
						s.isRifiutabile = true;
						if (!this.existsRichiesta) {
							this.existsRichiesta = true;
						}
					}
				}
				s.isDettaglio = !s.isModificabile;
			});
			this.dataSourceSoggettiProgetto = new SoggettiProgettoDatasource(this.soggettiProgetto);
			this.dataSourceSoggettiProgettoSlides = this.soggettiProgetto.map((row: SoggettoProgettoDTO) => !row.dtFineValidita);
			let s = this.soggettiProgetto.filter((row: SoggettoProgettoDTO) => row.descTipoSoggettoCorrelato === "Beneficiario" && row.descTipoSoggetto === "Persona Fisica");
			if (!s || s.length !== 1) {
				if (!this.soggettiProgetto.filter((row: SoggettoProgettoDTO) => !row.dtFineValidita && row.descTipoSoggettoCorrelato == "Rappresentante Legale").length) {
					this.showMessageWarning("Attenzione! Almeno un Rappresentante Legale deve essere associato e risultare attivo sul progetto.");
				}
			}
			if (isInit) {
				this.loadData2();
			}
			this.loadedFinpiemonte = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento.");
			this.loadedFinpiemonte = true;
		});
	}

	loadData2() {
		if (this.isIstruttore() || this.isBeneficiarioRappresentanteLegale()) {
			this.action = 'modifica';
		} else {
			this.action = 'dettaglio';
		}
		if (this.isIstruttore()) {
			this.displayedColumnsCustomSoggettiProgettoAzioni.tooltip.personOffIconRifiuto = "La richiesta di abilitazione del soggetto è stata rifiutata";
		} else {
			this.displayedColumnsCustomSoggettiProgettoAzioni.tooltip.personOffIconRifiuto = "L'istruttore ha rifiutato la richiesta di abilitazione del soggetto";
		}
		if (this.activatedRoute.snapshot.paramMap.get('success')) {
			this.showMessageSuccess("Operazione eseguita con successo.");
		}
		this.loadLineaPORFESR1420();
		if (this.isBenPersonaFisica) {
			this.loadRecapito();
		}
	}

	loadLineaPORFESR1420() {
		this.loadedLineaPORFESR1420 = false;
		this.subscribers.settoreAttivita = this.datiProgettoService.isLineaPORFESR1420(this.idProgetto).subscribe((res: boolean) => {
			this.is1420 = res;
			this.loadedLineaPORFESR1420 = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di verifica bando linea POR-FESR 14-20.");
			this.is1420 = true;
			this.loadedLineaPORFESR1420 = true;
		});
	}

	loadRecapito() {
		let s = this.soggettiProgetto.filter((row: SoggettoProgettoDTO) => row.idTipoAnagrafica === 1 && row.idTipoSoggetto === 1);   //BENEFICIARIO PERSONA FISICA
		if (s && s[0] && s.length === 1) {
			this.loadedRecapito = true;
			this.subscribers.recapito = this.datiProgettoService.leggiEmailBeneficiarioPF(this.idProgetto, s[0].idSoggetto).subscribe(res => {
				if (res) {
					this.recapiti = new Recapiti();
					this.recapiti.email = res.email;
					this.recapiti.flagEmailConfermata = res.flagEmailConfermata;
					// Può modificarlo solo il beneficiario
					if (res.flagEmailConfermata != "S") {
						this.isDisabledEmail = false;
						this.showMessageError("Indirizzo email da confermare.");
					} else if (this.sharedService.isBeneficiario(this.user)) {
						this.isDisabledEmail = false;
					} else {
						this.isDisabledEmail = true;
					}
					this.email = res.email;
				}
				this.loadedRecapito = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di caricamento dell'indirizzo email.");
				this.loadedRecapito = true;
			});
		}
	}

	loadSalvaRecapito(type: string) {
		let s = this.soggettiProgetto.filter((row: SoggettoProgettoDTO) => row.idTipoAnagrafica === 1 && row.idTipoSoggetto === 1);   //BENEFICIARIO PERSONA FISICA
		if (s && s[0] && s.length === 1) {
			this.loadedSalvaRecapito = false;
			this.recapiti.email = this.email;
			// Può modificarlo solo il beneficiario
			this.subscribers.salvaRecapito = this.datiProgettoService.salvaEmailBeneficiarioPF(this.idProgetto, s[0].idSoggetto, this.email).subscribe((res: EsitoOperazioni) => {
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
	}

	onVisualizzaSoggettiProgetto = (row: SoggettoProgettoDTO) => {
		if (row) {
			this.goToDettaglioDatiProgettoSoggetto(this.idProgetto, row);
		}
	}

	onModificaSoggettiProgetto = (row: SoggettoProgettoDTO) => {
		if (row && this.action == 'modifica') {
			this.goToModificaDatiProgettoSoggetto(this.idProgetto, row);
		}
	}

	onSlideSoggettiProgetto(object: any) {
		this.resetMessageError();
		this.resetMessageSuccess();
		if (object.slide) {
			this.cambiaStato(object);
		} else {
			let dialogRef = this.dialog.open(DisattivaSoggettoDialogComponent, {
				width: '40%'
			});
			dialogRef.afterClosed().subscribe(res => {
				if (res) {
					if (res === "DEF") {
						this.loadedCambiaStatoSoggettoProgetto = false;;
						this.subscribers.disabilita = this.datiProgettoService.disabilitaSoggettoPermanentemente(object.soggetto.idSoggetto, this.idProgetto).subscribe(data => {
							if (data && data > 0) {
								this.showMessageSuccess("Operazione eseguita con successo.");
							} else {
								this.showMessageError("Errore durante la disabilitazione permanente del soggetto.");
							}
							this.loadSoggettiProgetto.emit(true);
							this.loadedCambiaStatoSoggettoProgetto = true;
						}, err => {
							this.handleExceptionService.handleNotBlockingError(err);
							this.showMessageError("Errore durante la disabilitazione permanente del soggetto.");
							this.loadSoggettiProgetto.emit(true);
							this.loadedCambiaStatoSoggettoProgetto = true;
						});
					} else if (res === "TEMP") {
						this.cambiaStato(object);
					}
				} else {
					this.loadSoggettiProgetto.emit(true);
				}
			})
		}

	}

	cambiaStato(object: any) {
		this.loadedCambiaStatoSoggettoProgetto = false;
		let requestCambiaStatoSoggettoProgetto: RequestCambiaStatoSoggettoProgetto = {
			progrSoggettoProgetto: object.soggetto.progrSoggettoProgetto,
			progrSoggettiCorrelati: object.soggetto.progrSoggettiCorrelati,
			codiceFiscale: object.soggetto.codiceFiscaleSoggetto,
			idUtente: this.user.idUtente,
			idSoggetto: this.user.idSoggetto,
			codideRuolo: this.user.codiceRuolo,
			ruoloNuovoUtente: object.soggetto.idTipoSoggettoCorrelato,
			utenteAbilitatoProgetto: object.slide ? "ON" : "OFF",
			idProgetto: this.idProgetto
		};
		this.subscribers.cambiaStatoSoggettoProgetto = this.datiProgettoService.cambiaStatoSoggettoProgetto(this.user.idUtente, requestCambiaStatoSoggettoProgetto).subscribe(res => {
			if (res) {
				this.showMessageSuccess("Operazione eseguita con successo");
			} else {
				this.showMessageError("Errore durante il cambio dello stato del soggetto.");
			}
			this.loadSoggettiProgetto.emit(true);
			this.loadedCambiaStatoSoggettoProgetto = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore durante il cambio dello stato del soggetto.");
			this.loadSoggettiProgetto.emit(true);
			this.loadedCambiaStatoSoggettoProgetto = true;
		});
	}

	onRifiutaRichiestaSoggetto(row: SoggettoProgettoDTO) {
		this.loadedCambiaStatoSoggettoProgetto = false;
		this.subscribers.rifiuta = this.datiProgettoService.richiestaAccessoNegata(this.user.idUtente, row.progrSoggettoProgetto, row.progrSoggettiCorrelati).subscribe(data => {
			if (data !== null && data > 0) {
				this.showMessageSuccess("Richiesta rifiutata correttamente.");
				this.loadSoggettiProgetto.emit(true);
			} else {
				this.showMessageError("Errore in fase di riufiuto della richiesta.");
			}
			this.loadedCambiaStatoSoggettoProgetto = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di riufiuto della richiesta.");
			this.loadedCambiaStatoSoggettoProgetto = true;
		});
	}

	ngClassFunctionTableSoggetti = (row: any, i: number) => {
		let ngClass: any = {
			'grey-color': (this.dataSourceSoggettiProgettoSlides != undefined && !this.dataSourceSoggettiProgettoSlides[i]) || row.rifiutata || row.disattivazioneDefinitiva,
			'beneficiario-corrente': row && row.idTipoSoggetto == 2,
			'isnt-1420': row && row.idTipoSoggetto == 2 && !this.is1420,
			'yellow-background-color': row && row.inAttesaEsito && this.isIstruttore()
		};

		Object.keys(ngClass).forEach(element => {
			if (!ngClass[element]) {
				delete ngClass[element];
			}
		});
		return ngClass;
	}

	onMostraSoloSoggettiAttiviClick(e: any) {
		this.showOnlySoggettiVisible = !this.showOnlySoggettiVisible;
		this.loadSoggettiProgetto.emit(true);
	}


	goToNuovoSoggetto() {
		this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_DATI_PROGETTO + "/dati-progetto/soggetti/nuovo-soggetto", this.idProgetto, this.idBando]);
	}

	goToDettaglioDatiProgettoSoggetto(idProgetto: number, row: SoggettoProgettoDTO) {
		if (row.progrSoggettoProgetto && !row.progrSoggettiCorrelati && !row.idTipoSoggettoCorrelato) { // && row.idTipoSoggetto
			let url = `drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_DATI_PROGETTO}/dati-progetto/soggetti/dettaglio-soggetto/${idProgetto}/${this.idBando}/${row.progrSoggettoProgetto}/${row.codiceFiscaleSoggetto}`;
			if (row.idTipoAnagrafica === 1 && row.idTipoSoggetto === 1) { //BENEFICIARIO PERSONA FISICA
				url += ";benefPersonaFisica=true"
			}
			this.router.navigateByUrl(url);
		} else if (row.progrSoggettoProgetto && row.progrSoggettiCorrelati && row.idTipoSoggettoCorrelato) {
			this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_DATI_PROGETTO + "/dati-progetto/soggetti/dettaglio-soggetto", idProgetto, this.idBando, row.progrSoggettoProgetto, row.progrSoggettiCorrelati, row.idTipoSoggettoCorrelato, row.codiceFiscaleSoggetto]);
		} else {
			console.error('parametri mancanti');
		}
	}

	goToModificaDatiProgettoSoggetto(idProgetto: number, row: SoggettoProgettoDTO) {
		if (row.progrSoggettoProgetto && !row.progrSoggettiCorrelati && !row.idTipoSoggettoCorrelato) { // && row.idTipoSoggetto
			let url = `drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_DATI_PROGETTO}/dati-progetto/soggetti/modifica-soggetto/${idProgetto}/${this.idBando}/${row.progrSoggettoProgetto}/${row.codiceFiscaleSoggetto}`;
			if (row.idTipoAnagrafica === 1 && row.idTipoSoggetto === 1) { //BENEFICIARIO PERSONA FISICA
				url += ";benefPersonaFisica=true"
			}
			this.router.navigateByUrl(url);
		} else if (row.progrSoggettoProgetto && row.progrSoggettiCorrelati && row.idTipoSoggettoCorrelato) {
			this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_DATI_PROGETTO + "/dati-progetto/soggetti/modifica-soggetto", idProgetto, this.idBando, row.progrSoggettoProgetto, row.progrSoggettiCorrelati, row.idTipoSoggettoCorrelato, row.codiceFiscaleSoggetto]);
		} else {
			console.error('parametri mancanti');
		}
	}

	isBeneficiario() {
		return this.sharedService.isBeneficiario(this.user);
	}

	isIstruttore() {
		return this.sharedService.isIstruttore(this.user);
	}

	isBeneficiarioRappresentanteLegale(): boolean {
		if (this.user && (this.user.codiceRuolo === Constants.CODICE_RUOLO_BEN_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_PERSONA_FISICA)) {
			if (this.soggettiProgetto) {
				let s = this.soggettiProgetto.find(s => s.codiceFiscaleSoggetto === this.user.codFisc);
				if (s && !s.dtFineValidita) {
					return true;
				}
			}
		}
		return false;
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

	showMessageWarning(msg: string) {
		this.messageWarning.emit(msg);
	}

	isLoading() {
		if (!this.loadedFinpiemonte || !this.loadedCambiaStatoSoggettoProgetto || !this.loadedLineaPORFESR1420 || !this.loadedRecapito) {
			return true;
		}
		return false;
	}
}


export class SoggettiProgettoDatasource extends MatTableDataSource<SoggettoProgettoDTO> {

	_orderData(data: SoggettoProgettoDTO[]): SoggettoProgettoDTO[] {
		if (!this.sort) return data;
		if (!this.sort.direction || this.sort.direction.length === 0) {
			this.sort.active = "";
		}
		let direction = this.sort.direction || "asc";
		let sortedData = null;
		// viene riordinato in ordine ascendente
		switch (this.sort.active) {
			case "codiceFiscaleSoggetto":
				sortedData = data.sort((propA, propB) => {
					return propA.codiceFiscaleSoggetto.localeCompare(propB.codiceFiscaleSoggetto);
				});
				break;
			case "denominazione":
				sortedData = data.sort((propA, propB) => {
					return propA.denominazione.localeCompare(propB.denominazione);
				});
				break;
			case "descTipoSoggettoCorrelato":
				sortedData = data.sort((propA, propB) => {
					return propA.descTipoSoggettoCorrelato.localeCompare(propB.descTipoSoggettoCorrelato);
				});
				break;
			case "descTipoSoggetto":
				sortedData = data.sort((propA, propB) => {
					return propA.descTipoSoggetto.localeCompare(propB.descTipoSoggetto);
				});
				break;
			case "":
			default:
				sortedData = data;
				break;
		}
		return (direction === "desc") ? sortedData.reverse() : sortedData;
	}

	constructor(data: SoggettoProgettoDTO[]) {
		super(data);
	}

}