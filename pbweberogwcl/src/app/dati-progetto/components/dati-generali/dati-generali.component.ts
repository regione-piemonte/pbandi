/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from '@angular/common';
import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { SharedService } from 'src/app/shared/services/shared.service';
import { CodiceDescrizione } from '../../commons/models/codice-descrizione';
import { DatiProgetto } from '../../commons/models/dati-progetto';
import { EsitoOperazioni } from '../../commons/models/esito-operazioni';
import { RequestSalvaCup } from '../../commons/models/request-salva-cup';
import { RequestSalvaTitoloProgetto } from '../../commons/models/request-salva-titolo-progetto';
import { SalvaDatiProgettoRequest } from '../../commons/requests/salva-dati-progetto-request';
import { DatiProgettoService } from '../../services/dati-progetto.service';
import { DatiSif } from '../../commons/dto/dati-sif';

@Component({
	selector: 'app-dati-generali',
	templateUrl: './dati-generali.component.html',
	styleUrls: ['./dati-generali.component.scss']
})
export class DatiGeneraliComponent implements OnInit, OnChanges {

	@Input("idProgetto") idProgetto: number;
	@Input("idBandoLinea") idBandoLinea: number;
	@Input("isProgrammazione2127") isProgrammazione2127: boolean;
	@Input("datiSif") datiSif: DatiSif;

	@Output("messageError") messageError = new EventEmitter<string>();
	@Output("messageSuccess") messageSuccess = new EventEmitter<string>();

	@Output("datiSifEvent") datiSifEvent = new EventEmitter<DatiSif>();

	user: UserInfoSec;
	action: string;
	isModificaLimited: boolean;

	isLineaBANDIREGP: boolean;
	is1420: boolean;
	isLineaPNRR: boolean;
	settoreAttivitaLista: CodiceDescrizione[];
	attivitaAtecoLista: CodiceDescrizione[];
	tipoOperazioneLista: CodiceDescrizione[];
	obiettivoTematicoLista: CodiceDescrizione[];
	classificazioneRALista: CodiceDescrizione[];
	prioritaQsnLista: CodiceDescrizione[];
	obiettivoGeneraleQsnLista: CodiceDescrizione[];
	obiettivoSpecificoQsnLista: CodiceDescrizione[];
	strumentoAttuativoLista: CodiceDescrizione[];
	settoreCptLista: CodiceDescrizione[];
	temaPrioritarioLista: CodiceDescrizione[];
	indicatoreRisultatoProgrammaLista: CodiceDescrizione[];
	indicatoreQsnLista: CodiceDescrizione[];
	tipoAiutoLista: CodiceDescrizione[];
	strumentoProgrammazioneLista: CodiceDescrizione[];
	dimensioneTerritorialeLista: CodiceDescrizione[];
	progettoComplessoLista: CodiceDescrizione[];
	grandeProgettoLista: CodiceDescrizione[];
	settoreCipeLista: CodiceDescrizione[];
	sottoSettoreCipeLista: CodiceDescrizione[];
	categoriaCipeLista: CodiceDescrizione[];
	naturaCipeLista: CodiceDescrizione[];
	tipologiaCipeLista: CodiceDescrizione[];
	tipoProceduraOriginariaLista: CodiceDescrizione[];
	idDomanda: number;
	datiProgetto: DatiProgetto;
	fieldCup: string;
	defaultCup: string;
	isDefaultCup: boolean;
	fieldTitoloProgetto: string;
	defaultTitoloProgetto: string;
	isDefaultTitoloProgetto: boolean;
	fieldNumeroDomanda: string;
	fieldCodiceVisualizzato: string;
	dtComitato: Date;
	dtConcessione: Date;
	descSettoreSelected: CodiceDescrizione;
	descAtecoSelected: CodiceDescrizione;
	descTipoOperazioneSelected: CodiceDescrizione;
	descObiettivoTemSelected: CodiceDescrizione;
	descSettoreCptSelected: CodiceDescrizione;
	descClassificazioneRaSelected: CodiceDescrizione;
	descStrumentoAttuativoSelected: CodiceDescrizione;
	descPrioritaQsnSelected: CodiceDescrizione;
	descObiettivoGeneraleQsnSelected: CodiceDescrizione;
	descObiettivoSpecificoQsnSelected: CodiceDescrizione;
	descSettoreCipeSelected: CodiceDescrizione;
	descSottoSettoreCipeSelected: CodiceDescrizione;
	descCategoriaCipeSelected: CodiceDescrizione;
	descNaturaCipeSelected: CodiceDescrizione;
	descTipologiaCipeSelected: CodiceDescrizione;
	descTipoProceduraOrigSelected: CodiceDescrizione;
	descTemaPrioritarioSelected: CodiceDescrizione;
	descIndRisultatoProgrammaSelected: CodiceDescrizione;
	descIndicatoreQsnSelected: CodiceDescrizione;
	descTipoAiutoSelected: CodiceDescrizione;
	descTipoStrumentoSelected: CodiceDescrizione;
	descDimensioneTerritorialeSelected: CodiceDescrizione;
	descProgettoComplessoSelected: CodiceDescrizione;
	descGrandeProgettoSelected: CodiceDescrizione;
	flagInvioMonit: boolean;
	flagRichiestaCup: boolean;
	ppp: boolean;
	flagStrategico: boolean;
	isDisabledCup: boolean;
	isDisabledTitoloProgetto: boolean;
	isDisabledNumeroDomanda: boolean;
	isDisabledCodiceVisualizzato: boolean;
	isDisabledDescSettore: boolean;
	isDisabledDescAteco: boolean;
	isDisabledDescTipoOperazione: boolean;
	isDisabledDescTipoStrumento: boolean;
	isDisabledDescSettoreCipe: boolean;
	isDisabledDescSottoSettoreCipe: boolean;
	isDisabledDescCategoriaCipe: boolean;
	isDisabledDescNaturaCipe: boolean;
	isDisabledDescTipologiaCipe: boolean;
	isDisabledDescClassificazioneRa: boolean;
	isBR58: boolean;

	subscribers: any = {};

	//LOADED
	loadedLineaPORFESR1420: boolean;
	loadedLineaREGP: boolean = true;
	loadedLineaPNRR: boolean = true;
	loadedSalvaDatiProgetto: boolean = true;
	loadedDatiProgetto: boolean;
	loadedSettoreAttivita: boolean;
	loadedAttivitaAteco: boolean;
	loadedTipoOperazione: boolean;
	loadedObiettivoTematico: boolean;
	loadedClassificazioneRA: boolean;
	loadedPrioritaQsn: boolean;
	loadedObiettivoGeneraleQsn: boolean;
	loadedObiettivoSpecificoQsn: boolean;
	loadedStrumentoAttuativo: boolean;
	loadedSettoreCpt: boolean;
	loadedTemaPrioritario: boolean;
	loadedIndicatoreRisultatoProgramma: boolean;
	loadedIndicatoreQsn: boolean;
	loadedTipoAiuto: boolean;
	loadedStrumentoProgrammazione: boolean;
	loadedDimensioneTerritoriale: boolean;
	loadedProgettoComplesso: boolean;
	loadedGrandeProgetto: boolean;
	loadedSettoreCipe: boolean;
	loadedSottoSettoreCipe: boolean;
	loadedCategoriaCipe: boolean;
	loadedNaturaCipe: boolean;
	loadedTipologiaCipe: boolean;
	loadedTipoProceduraOriginaria: boolean;
	loadedRegolaBR58: boolean;

	constructor(
		private datiProgettoService: DatiProgettoService,
		private handleExceptionService: HandleExceptionService,
		private sharedService: SharedService,
		private userService: UserService,
		private dialog: MatDialog,
		private datePipe: DatePipe,
	) { }

	ngOnInit(): void {
		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
			if (data) {
				this.user = data;
			}
		});
	}

	ngOnChanges(changes: SimpleChanges) {
		if (changes.idProgetto && this.idProgetto) {
			//caricamento iniziale della pagina
			this.loadData();
		}
		if (changes.datiSif) {
			console.log(this.datiSif)
		}
	}

	loadData(): void {
		if (this.isIstruttore()) {
			this.action = 'modifica';
		} else if (this.isBeneficiario()) {
			this.action = 'dettaglio';
		}
		this.loadLineaPORFESR1420(true);
		if (this.action == 'modifica') {
			this.isModificaLimited = true;
		}

		this.loadedLineaREGP = false;
		this.userService.hasProgettoLineaByDescBreve(this.idProgetto, Constants.DESC_BREVE_LINE_INTERVENTO_BANDIREGP).subscribe(res => {
			this.isLineaBANDIREGP = res;
			this.loadedLineaREGP = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di verifica linea BANDIREGP.");
			this.loadedLineaREGP = true;
		});
		this.loadedLineaPNRR = false;
		this.userService.hasProgettoLineaByDescBreve(this.idProgetto, Constants.DESC_BREVE_LINEA_INTERVENTO_PNRR).subscribe(res => {
			this.isLineaPNRR = res;
			this.loadedLineaPNRR = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di verifica linea PNRR.");
			this.loadedLineaPNRR = true;
		});
		this.loadedRegolaBR58 = false;
		this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR58").subscribe(res => {
			if (res) {
				this.isBR58 = res;
			}
			this.loadedRegolaBR58 = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di recupero della regola BR59.");
			this.loadedRegolaBR58 = true;
		});
	}

	loadLineaPORFESR1420(loadDatiProgetto?: boolean) {
		this.loadedLineaPORFESR1420 = false;
		this.subscribers.settoreAttivita = this.datiProgettoService.isLineaPORFESR1420(this.idProgetto).subscribe((res: boolean) => {
			this.is1420 = res;
			this.loadedLineaPORFESR1420 = true;
			if (loadDatiProgetto) {
				this.loadDatiProgetto();
			}
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di verifica bando linea POR-FESR 14-20.");
			this.is1420 = true;
			if (loadDatiProgetto) {
				this.loadDatiProgetto();
			}
			this.loadedLineaPORFESR1420 = true;
		});
	}

	loadDatiProgetto() {
		this.loadedDatiProgetto = false;
		this.subscribers.datiProgetto = this.datiProgettoService.getDatiProgetto(this.idProgetto).subscribe((res: DatiProgetto) => {
			if (res) {
				this.datiProgetto = res;
				this.idDomanda = res.idDomanda;
				this.fieldCup = res.cup;
				this.postDefaultCup(this.fieldCup);
				this.fieldTitoloProgetto = res.titoloProgetto;
				this.postDefaultTitoloProgetto(this.fieldTitoloProgetto);
				this.fieldNumeroDomanda = res.numeroDomanda;
				this.fieldCodiceVisualizzato = res.codiceVisualizzato;
				this.dtComitato = this.sharedService.getDateFromStrDateIta(res.dettaglio.dtComitato);
				this.dtConcessione = this.sharedService.getDateFromStrDateIta(res.dettaglio.dtConcessione);
				this.descSettoreSelected = ((res.dettaglio && res.dettaglio.idSettoreAttivita) ? {
					codice: res.dettaglio.idSettoreAttivita.toString(),
					descrizione: res.dettaglio.descSettore
				} : undefined);
				this.descAtecoSelected = ((res.dettaglio && res.dettaglio.idAttivitaAteco) ? {
					codice: res.dettaglio.idAttivitaAteco.toString(),
					descrizione: res.dettaglio.descAteco
				} : undefined);
				this.descTipoOperazioneSelected = ((res.dettaglio && res.dettaglio.idTipoOperazione) ? {
					codice: res.dettaglio.idTipoOperazione.toString(),
					descrizione: res.dettaglio.descTipoOperazione
				} : undefined);
				this.descObiettivoTemSelected = ((res.dettaglio && res.dettaglio.idObiettivoTem) ? {
					codice: res.dettaglio.idObiettivoTem.toString(),
					descrizione: res.dettaglio.descObiettivoTem
				} : undefined);
				this.descClassificazioneRaSelected = ((res.dettaglio && res.dettaglio.idClassificazioneRa) ? {
					codice: res.dettaglio.idClassificazioneRa.toString(),
					descrizione: res.dettaglio.descClassificazioneRa
				} : undefined);
				this.descPrioritaQsnSelected = ((res.dettaglio && res.dettaglio.idPrioritaQsn) ? {
					codice: res.dettaglio.idPrioritaQsn.toString(),
					descrizione: res.dettaglio.descPrioritaQsn
				} : undefined);
				this.descObiettivoGeneraleQsnSelected = ((res.dettaglio && res.dettaglio.idObiettivoGenQsn) ? {
					codice: res.dettaglio.idObiettivoGenQsn.toString(),
					descrizione: res.dettaglio.descObiettivoGeneraleQsn
				} : undefined);
				this.descObiettivoSpecificoQsnSelected = ((res.dettaglio && res.dettaglio.idObiettivoSpecifQsn) ? {
					codice: res.dettaglio.idObiettivoSpecifQsn.toString(),
					descrizione: res.dettaglio.descObiettivoSpecificoQsn
				} : undefined);
				this.descStrumentoAttuativoSelected = ((res.dettaglio && res.dettaglio.idStrumentoAttuativo) ? {
					codice: res.dettaglio.idStrumentoAttuativo.toString(),
					descrizione: res.dettaglio.descStrumentoAttuativo
				} : undefined);
				this.descSettoreCptSelected = ((res.dettaglio && res.dettaglio.idSettoreCpt) ? {
					codice: res.dettaglio.idSettoreCpt.toString(),
					descrizione: res.dettaglio.descSettoreCpt
				} : undefined);
				this.descTemaPrioritarioSelected = ((res.dettaglio && res.dettaglio.idTemaPrioritario) ? {
					codice: res.dettaglio.idTemaPrioritario.toString(),
					descrizione: res.dettaglio.descTemaPrioritario
				} : undefined);
				this.descIndRisultatoProgrammaSelected = ((res.dettaglio && res.dettaglio.idIndRisultatoProgram) ? {
					codice: res.dettaglio.idIndRisultatoProgram.toString(),
					descrizione: res.dettaglio.descIndRisultatoProgramma
				} : undefined);
				this.descIndicatoreQsnSelected = ((res.dettaglio && res.dettaglio.idIndicatoreQsn) ? {
					codice: res.dettaglio.idIndicatoreQsn.toString(),
					descrizione: res.dettaglio.descIndicatoreQsn
				} : undefined);
				this.descTipoAiutoSelected = ((res.dettaglio && res.dettaglio.idTipoAiuto) ? {
					codice: res.dettaglio.idTipoAiuto.toString(),
					descrizione: res.dettaglio.descTipoAiuto
				} : undefined);
				this.descTipoStrumentoSelected = ((res.dettaglio && res.dettaglio.idTipoStrumentoProgr) ? {
					codice: res.dettaglio.idTipoStrumentoProgr.toString(),
					descrizione: res.dettaglio.descTipoStrumento
				} : undefined);
				this.descDimensioneTerritorialeSelected = ((res.dettaglio && res.dettaglio.idDimensioneTerritor) ? {
					codice: res.dettaglio.idDimensioneTerritor.toString(),
					descrizione: res.dettaglio.descDimensioneTerritoriale
				} : undefined);
				this.descProgettoComplessoSelected = ((res.dettaglio && res.dettaglio.idProgettoComplesso) ? {
					codice: res.dettaglio.idProgettoComplesso.toString(),
					descrizione: res.dettaglio.descProgettoComplesso
				} : undefined);
				this.descGrandeProgettoSelected = ((res.dettaglio && res.dettaglio.idGrandeProgetto) ? {
					codice: res.dettaglio.idGrandeProgetto.toString(),
					descrizione: res.dettaglio.descGrandeProgetto
				} : undefined);
				this.descSettoreCipeSelected = ((res.dettaglio && res.dettaglio.idSettoreCipe) ? {
					codice: res.dettaglio.idSettoreCipe.toString(),
					descrizione: res.dettaglio.descSettoreCipe
				} : undefined);
				this.descSottoSettoreCipeSelected = ((res.dettaglio && res.dettaglio.idSottoSettoreCipe) ? {
					codice: res.dettaglio.idSottoSettoreCipe.toString(),
					descrizione: res.dettaglio.descSottoSettoreCipe
				} : undefined);
				this.descCategoriaCipeSelected = ((res.dettaglio && res.dettaglio.idCategoriaCipe) ? {
					codice: res.dettaglio.idCategoriaCipe.toString(),
					descrizione: res.dettaglio.descCategoriaCipe
				} : undefined);
				this.descNaturaCipeSelected = ((res.dettaglio && res.dettaglio.idNaturaCipe) ? {
					codice: res.dettaglio.idNaturaCipe.toString(),
					descrizione: res.dettaglio.descNaturaCipe
				} : undefined);
				this.descTipologiaCipeSelected = ((res.dettaglio && res.dettaglio.idTipologiaCipe) ? {
					codice: res.dettaglio.idTipologiaCipe.toString(),
					descrizione: res.dettaglio.descTipologiaCipe
				} : undefined);
				this.descTipoProceduraOrigSelected = ((res.dettaglio && res.dettaglio.idTipoProceduraOrig) ? {
					codice: res.dettaglio.idTipoProceduraOrig.toString(),
					descrizione: res.dettaglio.descTipoProceduraOrig
				} : undefined);
				this.flagInvioMonit = ((res.dettaglio && res.dettaglio.flagInvioMonit !== undefined) ? res.dettaglio.flagInvioMonit : undefined);
				this.flagRichiestaCup = ((res.dettaglio && res.dettaglio.flagRichiestaCup !== undefined) ? res.dettaglio.flagRichiestaCup : undefined);
				if (res.dettaglio) {
					this.ppp = res.dettaglio.flagPPP;
					this.flagStrategico = res.dettaglio.flagStrategico;
					if (res.dettaglio.dtFirmaAccordo || res.dettaglio.dtCompletamentoValutazione) {
						this.datiSif = new DatiSif(res.dettaglio.dtFirmaAccordo, res.dettaglio.dtCompletamentoValutazione);
						this.datiSifEvent.emit(this.datiSif);
					}
				}

				if (res.dettaglio && Object.keys(res.dettaglio).length === 0 && Object.getPrototypeOf(res.dettaglio) === Object.prototype) {
					this.showMessageError("Il dettaglio non è stato caricato");
				}
			}
			this.loadedDatiProgetto = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di recupero dei dati del progetto.");
			this.loadedDatiProgetto = true;
		});
	}

	loadSettoreAttivita(preserveValue?: boolean) {
		this.loadedSettoreAttivita = false;
		this.subscribers.settoreAttivita = this.datiProgettoService.getSettoreAttivita().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.settoreAttivitaLista = res;
				this.onSelectFieldDescSettoreSelected(preserveValue);
			}
			this.loadedSettoreAttivita = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento dei settori attività.");
			this.onSelectFieldDescSettoreSelected(preserveValue);
			this.loadedSettoreAttivita = true;
		});
	}

	loadAttivitaAteco(idSettoreAttivita?: number, preserveValue?: boolean) {
		this.loadedAttivitaAteco = false;
		if (idSettoreAttivita) {
			this.subscribers.attivitaAteco = this.datiProgettoService.getAttivitaAteco(idSettoreAttivita).subscribe((res: CodiceDescrizione[]) => {
				if (res) {
					this.attivitaAtecoLista = res;

				}
				this.loadedAttivitaAteco = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di caricamento delle attività ATECO.");
				this.loadedAttivitaAteco = true;
			});
		} else {
			this.attivitaAtecoLista = [] as CodiceDescrizione[];
			this.loadedAttivitaAteco = true;
		}
	}

	loadTipoOperazione() {
		this.loadedTipoOperazione = false;
		this.subscribers.tipoOperazione = this.datiProgettoService.getTipoOperazione().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.tipoOperazioneLista = res;
			}
			this.loadedTipoOperazione = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento dei tipi di operazione.");
			this.loadedTipoOperazione = true;
		});
	}

	loadObiettivoTematico() {
		this.loadedObiettivoTematico = false;
		this.subscribers.obiettivoTematico = this.datiProgettoService.getObiettivoTematico().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.obiettivoTematicoLista = res;
			}
			this.loadedObiettivoTematico = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento degli obiettivi telematici.");
			this.loadedObiettivoTematico = true;
		});
	}

	loadClassificazioneRA() {
		this.loadedClassificazioneRA = false;
		this.subscribers.classificazioneRA = this.datiProgettoService.getClassificazioneRA().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.classificazioneRALista = res;
			}
			this.loadedClassificazioneRA = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento delle claassicazioni RA.");
			this.loadedClassificazioneRA = true;
		});
	}

	loadPrioritaQsn(idLineaInterventoAsse?: number, preserveValue?: boolean) {
		this.loadedPrioritaQsn = false;
		if (idLineaInterventoAsse) {
			this.subscribers.prioritaQsn = this.datiProgettoService.getPrioritaQsn(idLineaInterventoAsse).subscribe((res: CodiceDescrizione[]) => {
				if (res) {
					this.prioritaQsnLista = res;
					this.onSelectDescPrioritaQsnSelected(preserveValue);
				}
				this.loadedPrioritaQsn = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di caricamento delle Priorità Qsn.");
				this.onSelectDescPrioritaQsnSelected(preserveValue);
				this.loadedPrioritaQsn = true;
			});
		} else {
			this.prioritaQsnLista = [] as CodiceDescrizione[];
			this.loadedPrioritaQsn = true;
		}
	}

	loadObiettivoGeneraleQsn(idPriorityQsn?: number, preserveValue?: boolean) {
		this.loadedObiettivoGeneraleQsn = false;
		if (idPriorityQsn) {
			this.subscribers.obiettivoGeneraleQsn = this.datiProgettoService.getObiettivoGeneraleQsn(idPriorityQsn).subscribe((res: CodiceDescrizione[]) => {
				if (res) {
					this.obiettivoGeneraleQsnLista = res;
					this.onSelectDescObiettivoGeneraleQsnSelected(preserveValue);
				}
				this.loadedObiettivoGeneraleQsn = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di caricamento degli obiettivi generali Qsn.");
				this.onSelectDescObiettivoGeneraleQsnSelected(preserveValue);
				this.loadedObiettivoGeneraleQsn = true;
			});
		} else {
			this.obiettivoGeneraleQsnLista = [] as CodiceDescrizione[];
			this.onSelectDescObiettivoGeneraleQsnSelected(preserveValue);
			this.loadedObiettivoGeneraleQsn = true;
		}
	}

	loadObiettivoSpecificoQsn(idObiettivoGenerale?: number, preserveValue?: boolean) {
		this.loadedObiettivoSpecificoQsn = false;
		if (idObiettivoGenerale) {
			this.subscribers.obiettivoSpecificoQsn = this.datiProgettoService.getObiettivoSpecificoQsn(idObiettivoGenerale).subscribe((res: CodiceDescrizione[]) => {
				if (res) {
					this.obiettivoSpecificoQsnLista = res;
				}
				this.loadedObiettivoSpecificoQsn = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di caricamento degli obiettivi specifici Qsn.");
				this.loadedObiettivoSpecificoQsn = true;
			});
		} else {
			this.obiettivoSpecificoQsnLista = [] as CodiceDescrizione[];
			this.loadedObiettivoSpecificoQsn = true;
		}
	}

	loadStrumentoAttuativo() {
		this.loadedStrumentoAttuativo = false;
		this.subscribers.strumentoAttuativo = this.datiProgettoService.getStrumentoAttuativo().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.strumentoAttuativoLista = res;
			}
			this.loadedStrumentoAttuativo = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento degli strumetni attuativi.");
			this.loadedStrumentoAttuativo = true;
		});
	}

	loadSettoreCpt() {
		this.loadedSettoreCpt = false;
		this.subscribers.settoreCpt = this.datiProgettoService.getSettoreCpt().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.settoreCptLista = res;
			}
			this.loadedSettoreCpt = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento dei settori Cpt.");
			this.loadedSettoreCpt = true;
		});
	}

	loadTemaPrioritario() {
		this.loadedTemaPrioritario = false;
		this.subscribers.temaPrioritario = this.datiProgettoService.getTemaPrioritario(this.idProgetto).subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.temaPrioritarioLista = res;
			}
			this.loadedTemaPrioritario = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento dei temi prioritari.");
			this.loadedTemaPrioritario = true;
		});
	}

	loadIndicatoreRisultatoProgramma() {
		this.loadedIndicatoreRisultatoProgramma = false;
		this.subscribers.indicatoreRisultatoProgramma = this.datiProgettoService.getIndicatoreRisultatoProgramma(this.idProgetto).subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.indicatoreRisultatoProgrammaLista = res;
			}
			this.loadedIndicatoreRisultatoProgramma = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento degli indicatori risultato programma.");
			this.loadedIndicatoreRisultatoProgramma = true;
		});
	}

	loadIndicatoreQsn() {
		this.loadedIndicatoreQsn = false;
		this.subscribers.indicatoreQsn = this.datiProgettoService.getIndicatoreQsn(this.idProgetto).subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.indicatoreQsnLista = res;
			}
			this.loadedIndicatoreQsn = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento degli indicatori Qsn.");
			this.loadedIndicatoreQsn = true;
		});
	}

	loadTipoAiuto() {
		this.loadedTipoAiuto = false;
		this.subscribers.tipoAiuto = this.datiProgettoService.getTipoAiuto(this.idProgetto).subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.tipoAiutoLista = res;
			}
			this.loadedTipoAiuto = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento dei tipi aiuto.");
			this.loadedTipoAiuto = true;
		});
	}

	loadStrumentoProgrammazione() {
		this.loadedStrumentoProgrammazione = false;
		this.subscribers.strumentoProgrammazione = this.datiProgettoService.getStrumentoProgrammazione().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.strumentoProgrammazioneLista = res;
			}
			this.loadedStrumentoProgrammazione = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento degli strumenti di programmazione.");
			this.loadedStrumentoProgrammazione = true;
		});
	}

	loadDimensioneTerritoriale() {
		this.loadedDimensioneTerritoriale = false;
		if (this.is1420) {
			this.subscribers.dimensioneTerritoriale = this.datiProgettoService.getDimensioneTerritoriale1420(this.idProgetto).subscribe((res: CodiceDescrizione[]) => {
				if (res) {
					this.dimensioneTerritorialeLista = res;
				}
				this.loadedDimensioneTerritoriale = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di caricamento delle dimensioni territoriali.");
				this.loadedDimensioneTerritoriale = true;
			});
		} else {
			this.subscribers.dimensioneTerritoriale = this.datiProgettoService.getDimensioneTerritoriale().subscribe((res: CodiceDescrizione[]) => {
				if (res) {
					this.dimensioneTerritorialeLista = res;
				}
				this.loadedDimensioneTerritoriale = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di caricamento delle dimensioni territoriali.");
				this.loadedDimensioneTerritoriale = true;
			});
		}
	}

	loadProgettoComplesso() {
		this.loadedProgettoComplesso = false;
		this.subscribers.progettoComplesso = this.datiProgettoService.getProgettoComplesso().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.progettoComplessoLista = res;
			}
			this.loadedProgettoComplesso = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento di progetti complessi.");
			this.loadedProgettoComplesso = true;
		});
	}

	loadGrandeProgetto() {
		this.loadedGrandeProgetto = false;
		this.subscribers.grandeProgetto = this.datiProgettoService.getGrandeProgetto().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.grandeProgettoLista = res;
			}
			this.loadedGrandeProgetto = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento dei grandi progetti.");
			this.loadedGrandeProgetto = true;
		});
	}

	loadSettoreCipe(preserveValue?: boolean) {
		this.loadedSettoreCipe = false;
		this.subscribers.settoreCipe = this.datiProgettoService.getSettoreCipe().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.settoreCipeLista = res;
				this.onSelectDescSettoreCipeSelected(preserveValue);
			}
			this.loadedSettoreCipe = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento dei Settori Cipe.");
			this.onSelectDescSettoreCipeSelected(preserveValue);
			this.loadedSettoreCipe = true;
		});
	}

	loadSottoSettoreCipe(idSettoreCipe?: number, preserveValue?: boolean) {
		this.loadedSottoSettoreCipe = false;
		if (idSettoreCipe) {
			this.subscribers.sottoSettoreCipe = this.datiProgettoService.getSottoSettoreCipe(idSettoreCipe).subscribe((res: CodiceDescrizione[]) => {
				if (res) {
					this.sottoSettoreCipeLista = res;
					this.onSelectDescSottoSettoreCipeSelected(preserveValue);
				}
				this.loadedSottoSettoreCipe = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di carimento dei sottosettori Cipe.");
				this.onSelectDescSottoSettoreCipeSelected(preserveValue);
				this.loadedSottoSettoreCipe = true;
			});
		} else {
			this.sottoSettoreCipeLista = [] as CodiceDescrizione[];
			this.loadedSottoSettoreCipe = true;
		}
	}

	loadCategoriaCipe(idSottoSettoreCipe?: number, preserveValue?: boolean) {
		this.loadedCategoriaCipe = false;
		if (idSottoSettoreCipe) {
			this.subscribers.categoriaCipe = this.datiProgettoService.getCategoriaCipe(idSottoSettoreCipe).subscribe((res: CodiceDescrizione[]) => {
				if (res) {
					this.categoriaCipeLista = res;
				}
				this.loadedCategoriaCipe = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di caricamento delle categorie Cipe.");
				this.loadedCategoriaCipe = true;
			});
		} else {
			this.categoriaCipeLista = [] as CodiceDescrizione[];
			this.loadedCategoriaCipe = true;
		}
	}

	loadNaturaCipe(preserveValue?: boolean) {
		this.loadedNaturaCipe = false;
		this.subscribers.naturaCipe = this.datiProgettoService.getNaturaCipe().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.naturaCipeLista = res;
				this.onSelectDescNaturaCipeSelected(preserveValue);
			}
			this.loadedNaturaCipe = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento delle nature Cipe.");
			this.onSelectDescNaturaCipeSelected(preserveValue);
			this.loadedNaturaCipe = true;
		});
	}

	loadTipologiaCipe(idNaturaCipe?: number, preserveValue?: boolean) {
		this.loadedTipologiaCipe = false;
		if (idNaturaCipe) {
			this.subscribers.tipologiaCipe = this.datiProgettoService.getTipologiaCipe(idNaturaCipe).subscribe((res: CodiceDescrizione[]) => {
				if (res) {
					this.tipologiaCipeLista = res;
				}
				this.loadedTipologiaCipe = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di caricamento delle tipologie Cipe.");
				this.loadedTipologiaCipe = true;
			});
		} else {
			this.tipologiaCipeLista = [] as CodiceDescrizione[];
			this.loadedTipologiaCipe = true;
		}
	}

	loadTipoProceduraOriginaria() {
		this.loadedTipoProceduraOriginaria = false;
		this.subscribers.tipoProceduraOriginaria = this.datiProgettoService.getTipoProceduraOriginaria().subscribe((res: CodiceDescrizione[]) => {
			if (res) {
				this.tipoProceduraOriginariaLista = res;
			}
			this.loadedTipoProceduraOriginaria = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento delle procedure originarie.");
			this.loadedTipoProceduraOriginaria = true;
		});
	}

	onSelectFieldDescSettoreSelected(preserveValue?: boolean) {
		if (!preserveValue && this.descAtecoSelected && this.descAtecoSelected.codice) {
			this.descAtecoSelected = undefined;
		}
		this.loadAttivitaAteco(((this.descSettoreSelected && this.descSettoreSelected.codice) ? parseInt(this.descSettoreSelected.codice) : undefined), preserveValue);
	}

	onSelectDescPrioritaQsnSelected(preserveValue?: boolean) {
		if (!preserveValue && this.descObiettivoGeneraleQsnSelected && this.descObiettivoGeneraleQsnSelected.codice) {
			this.descObiettivoGeneraleQsnSelected = undefined;
		}
		this.loadObiettivoGeneraleQsn(((this.descPrioritaQsnSelected && this.descPrioritaQsnSelected.codice) ? parseInt(this.descPrioritaQsnSelected.codice) : undefined), preserveValue);
	}

	onSelectDescObiettivoGeneraleQsnSelected(preserveValue?: boolean) {
		if (!preserveValue && this.descObiettivoSpecificoQsnSelected && this.descObiettivoSpecificoQsnSelected.codice) {
			this.descObiettivoSpecificoQsnSelected = undefined;
		}
		this.loadObiettivoSpecificoQsn(((this.descObiettivoGeneraleQsnSelected && this.descObiettivoGeneraleQsnSelected.codice) ? parseInt(this.descObiettivoGeneraleQsnSelected.codice) : undefined), preserveValue);
	}

	onSelectDescSettoreCipeSelected(preserveValue?: boolean) {
		if (!preserveValue && this.descSottoSettoreCipeSelected && this.descSottoSettoreCipeSelected.codice) {
			this.descSottoSettoreCipeSelected = undefined;
		}
		this.loadSottoSettoreCipe(((this.descSettoreCipeSelected && this.descSettoreCipeSelected.codice) ? parseInt(this.descSettoreCipeSelected.codice) : undefined), preserveValue);
	}

	onSelectDescSottoSettoreCipeSelected(preserveValue?: boolean) {
		if (!preserveValue && this.descCategoriaCipeSelected && this.descCategoriaCipeSelected.codice) {
			this.descCategoriaCipeSelected = undefined;
		}
		this.loadCategoriaCipe(((this.descSottoSettoreCipeSelected && this.descSottoSettoreCipeSelected.codice) ? parseInt(this.descSottoSettoreCipeSelected.codice) : undefined), preserveValue);
	}

	onSelectDescNaturaCipeSelected(preserveValue?: boolean) {
		if (!preserveValue && this.descTipologiaCipeSelected && this.descTipologiaCipeSelected.codice) {
			this.descTipologiaCipeSelected = undefined;
		}
		this.loadTipologiaCipe(((this.descNaturaCipeSelected && this.descNaturaCipeSelected.codice) ? parseInt(this.descNaturaCipeSelected.codice) : undefined), preserveValue);
	}

	onModificaCup = () => {  // DatiProgetto
		this.isDefaultCup = false;
		this.isDisabledCup = false;
	}

	onSalvaCup = () => {  // DatiProgetto
		this.loadedSalvaDatiProgetto = false;
		this.resetMessageSuccess();
		this.resetMessageError();
		let salvaDatiProgettoRequest: RequestSalvaCup = {
			cup: this.fieldCup,
			idProgetto: this.idProgetto
		};
		this.datiProgettoService.saveCup(salvaDatiProgettoRequest).subscribe((data: EsitoOperazioni) => {
			if (data) {
				if (data.esito) {
					this.showMessageSuccess(data.msg);
					this.postDefaultCup(this.fieldCup);
				} else {
					this.showMessageError(data.msg);
				}
			}
			this.loadedSalvaDatiProgetto = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di salvataggio del CUP.");
			this.loadedSalvaDatiProgetto = true;
		});
	}

	postDefaultCup = (defaultCup?: string) => {
		this.defaultCup = defaultCup;
		this.isDefaultCup = true;
		this.isDisabledCup = true;
	}

	onAnnullaCup = () => {  // DatiProgetto
		this.fieldCup = this.defaultCup;
		this.isDefaultCup = true;
		this.isDisabledCup = true;
	}

	onModificaTitoloProgetto = () => {  // DatiProgetto
		this.isDefaultTitoloProgetto = false;
		this.isDisabledTitoloProgetto = false;
	}

	onSalvaTitoloProgetto = () => {  // DatiProgetto
		this.loadedSalvaDatiProgetto = false;
		this.resetMessageSuccess();
		this.resetMessageError();
		let requestSalvaTitoloProgett: RequestSalvaTitoloProgetto = {
			idProgetto: this.idProgetto,
			titoloProgetto: this.fieldTitoloProgetto
		};
		this.datiProgettoService.saveTitoloProgetto(requestSalvaTitoloProgett).subscribe((data: EsitoOperazioni) => {
			if (data) {
				if (data.esito) {
					this.showMessageSuccess(data.msg);
					this.postDefaultTitoloProgetto(this.fieldTitoloProgetto);
				} else {
					this.showMessageError(data.msg);
				}
			}
			this.loadedSalvaDatiProgetto = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di salvataggio del titolo del progetto.");
			this.loadedSalvaDatiProgetto = true;
		});
	}

	postDefaultTitoloProgetto = (defaultTitoloProgetto?: string) => {
		this.defaultTitoloProgetto = defaultTitoloProgetto;
		this.isDefaultTitoloProgetto = true;
		this.isDisabledTitoloProgetto = true;
	}

	onAnnullaTitoloProgetto = () => {  // DatiProgetto
		this.fieldTitoloProgetto = this.defaultTitoloProgetto;
		this.isDefaultTitoloProgetto = true;
		this.isDisabledTitoloProgetto = true;
	}

	isFormDatiGeneraliDisabled() {
		let r: boolean = false;
		if (!this.isModificaLimited && !this.isLineaBANDIREGP &&
			(
				!this.idDomanda ||
				!this.descTipoOperazioneSelected ||
				!this.descTipoOperazioneSelected.codice ||
				(
					this.is1420 && (
						!this.descObiettivoTemSelected ||
						!this.descObiettivoTemSelected.codice ||
						!this.descClassificazioneRaSelected ||
						!this.descClassificazioneRaSelected.codice
					)
				) ||
				(
					!this.is1420 && !this.isProgrammazione2127 && (
						!this.descPrioritaQsnSelected ||
						!this.descPrioritaQsnSelected.codice ||
						!this.descObiettivoGeneraleQsnSelected ||
						!this.descObiettivoGeneraleQsnSelected.codice ||
						!this.descObiettivoSpecificoQsnSelected ||
						!this.descObiettivoSpecificoQsnSelected.codice
					)
				) ||
				(
					this.isProgrammazione2127 && (
						this.ppp === undefined || this.ppp === null ||
						this.flagStrategico === undefined || this.flagStrategico === null
					)
				) ||
				!this.descStrumentoAttuativoSelected ||
				!this.descStrumentoAttuativoSelected.codice ||
				!this.descSettoreCptSelected ||
				!this.descSettoreCptSelected.codice ||
				!this.descTemaPrioritarioSelected ||
				!this.descTemaPrioritarioSelected.codice ||
				!this.descIndRisultatoProgrammaSelected ||
				!this.descIndRisultatoProgrammaSelected.codice ||
				!this.descIndicatoreQsnSelected ||
				!this.descIndicatoreQsnSelected.codice ||
				!this.descTipoAiutoSelected ||
				!this.descTipoAiutoSelected.codice ||
				!this.descTipoStrumentoSelected ||
				!this.descTipoStrumentoSelected.codice ||
				!this.descDimensioneTerritorialeSelected ||
				!this.descDimensioneTerritorialeSelected.codice ||
				this.flagInvioMonit === undefined ||
				this.flagRichiestaCup === undefined
			)
		) {
			r = true;
		}
		return r;
	}

	confermaDialog() {
		let dialogRef = this.dialog.open(ConfermaDialog, {
			width: '40%',
			data: {
				message: "Confermi il salvataggio dei dati inseriti?"
			}
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res === "SI") {
				this.loadSalvaDatiProgetto();
			}
		});
	}

	loadSalvaDatiProgetto = () => {  // DatiProgetto
		this.loadedSalvaDatiProgetto = false;
		this.resetMessageSuccess();
		this.resetMessageError();
		let salvaDatiProgettoRequest: SalvaDatiProgettoRequest = {
			datiProgetto: {
				idProgetto: this.idProgetto,
				idDomanda: this.idDomanda,
				idSedeIntervento: this.datiProgetto.idSedeIntervento,
				progrSoggettoProgetto: this.datiProgetto.progrSoggettoProgetto,
				cup: this.fieldCup,
				titoloProgetto: this.fieldTitoloProgetto,
				numeroDomanda: this.fieldNumeroDomanda,
				codiceVisualizzato: this.fieldCodiceVisualizzato,
				dettaglio: {
					dtComitato: ((this.dtComitato) ? this.datePipe.transform(this.dtComitato, 'dd/MM/yyyy') : undefined),
					dtConcessione: ((this.dtConcessione) ? this.datePipe.transform(this.dtConcessione, 'dd/MM/yyyy') : undefined),
					idSettoreAttivita: ((this.descSettoreSelected && this.descSettoreSelected.codice) ? parseInt(this.descSettoreSelected.codice) : undefined),
					descSettore: ((this.descSettoreSelected && this.descSettoreSelected.descrizione) ? this.descSettoreSelected.descrizione : undefined),
					idAttivitaAteco: ((this.descAtecoSelected && this.descAtecoSelected.codice) ? parseInt(this.descAtecoSelected.codice) : undefined),
					descAteco: ((this.descAtecoSelected && this.descAtecoSelected.descrizione) ? this.descAtecoSelected.descrizione : undefined),
					idTipoOperazione: ((this.descTipoOperazioneSelected && this.descTipoOperazioneSelected.codice) ? parseInt(this.descTipoOperazioneSelected.codice) : undefined),
					descTipoOperazione: ((this.descTipoOperazioneSelected && this.descTipoOperazioneSelected.descrizione) ? this.descTipoOperazioneSelected.descrizione : undefined),
					idObiettivoTem: ((this.descObiettivoTemSelected && this.descObiettivoTemSelected.codice) ? parseInt(this.descObiettivoTemSelected.codice) : undefined),
					descObiettivoTem: ((this.descObiettivoTemSelected && this.descObiettivoTemSelected.descrizione) ? this.descObiettivoTemSelected.descrizione : undefined),
					idClassificazioneRa: ((this.descClassificazioneRaSelected && this.descClassificazioneRaSelected.codice) ? parseInt(this.descClassificazioneRaSelected.codice) : undefined),
					descClassificazioneRa: ((this.descClassificazioneRaSelected && this.descClassificazioneRaSelected.descrizione) ? this.descClassificazioneRaSelected.descrizione : undefined),
					idPrioritaQsn: ((this.descPrioritaQsnSelected && this.descPrioritaQsnSelected.codice) ? parseInt(this.descPrioritaQsnSelected.codice) : undefined),
					descPrioritaQsn: ((this.descPrioritaQsnSelected && this.descPrioritaQsnSelected.descrizione) ? this.descPrioritaQsnSelected.descrizione : undefined),
					idObiettivoGenQsn: ((this.descObiettivoGeneraleQsnSelected && this.descObiettivoGeneraleQsnSelected.codice) ? parseInt(this.descObiettivoGeneraleQsnSelected.codice) : undefined),
					descObiettivoGeneraleQsn: ((this.descObiettivoGeneraleQsnSelected && this.descObiettivoGeneraleQsnSelected.descrizione) ? this.descObiettivoGeneraleQsnSelected.descrizione : undefined),
					idObiettivoSpecifQsn: ((this.descObiettivoSpecificoQsnSelected && this.descObiettivoSpecificoQsnSelected.codice) ? parseInt(this.descObiettivoSpecificoQsnSelected.codice) : undefined),
					descObiettivoSpecificoQsn: ((this.descObiettivoSpecificoQsnSelected && this.descObiettivoSpecificoQsnSelected.descrizione) ? this.descObiettivoSpecificoQsnSelected.descrizione : undefined),
					idStrumentoAttuativo: ((this.descStrumentoAttuativoSelected && this.descStrumentoAttuativoSelected.codice) ? parseInt(this.descStrumentoAttuativoSelected.codice) : undefined),
					descStrumentoAttuativo: ((this.descStrumentoAttuativoSelected && this.descStrumentoAttuativoSelected.descrizione) ? this.descStrumentoAttuativoSelected.descrizione : undefined),
					idSettoreCpt: ((this.descSettoreCptSelected && this.descSettoreCptSelected.codice) ? parseInt(this.descSettoreCptSelected.codice) : undefined),
					descSettoreCpt: ((this.descSettoreCptSelected && this.descSettoreCptSelected.descrizione) ? this.descSettoreCptSelected.descrizione : undefined),
					idTemaPrioritario: ((this.descTemaPrioritarioSelected && this.descTemaPrioritarioSelected.codice) ? parseInt(this.descTemaPrioritarioSelected.codice) : undefined),
					descTemaPrioritario: ((this.descTemaPrioritarioSelected && this.descTemaPrioritarioSelected.descrizione) ? this.descTemaPrioritarioSelected.descrizione : undefined),
					idIndRisultatoProgram: ((this.descIndRisultatoProgrammaSelected && this.descIndRisultatoProgrammaSelected.codice) ? parseInt(this.descIndRisultatoProgrammaSelected.codice) : undefined),
					descIndRisultatoProgramma: ((this.descIndRisultatoProgrammaSelected && this.descIndRisultatoProgrammaSelected.descrizione) ? this.descIndRisultatoProgrammaSelected.descrizione : undefined),
					idIndicatoreQsn: ((this.descIndicatoreQsnSelected && this.descIndicatoreQsnSelected.codice) ? parseInt(this.descIndicatoreQsnSelected.codice) : undefined),
					descIndicatoreQsn: ((this.descIndicatoreQsnSelected && this.descIndicatoreQsnSelected.descrizione) ? this.descIndicatoreQsnSelected.descrizione : undefined),
					idTipoAiuto: ((this.descTipoAiutoSelected && this.descTipoAiutoSelected.codice) ? parseInt(this.descTipoAiutoSelected.codice) : undefined),
					descTipoAiuto: ((this.descTipoAiutoSelected && this.descTipoAiutoSelected.descrizione) ? this.descTipoAiutoSelected.descrizione : undefined),
					idTipoStrumentoProgr: ((this.descTipoStrumentoSelected && this.descTipoStrumentoSelected.codice) ? parseInt(this.descTipoStrumentoSelected.codice) : undefined),
					descTipoStrumento: ((this.descTipoStrumentoSelected && this.descTipoStrumentoSelected.descrizione) ? this.descTipoStrumentoSelected.descrizione : undefined),
					idDimensioneTerritor: ((this.descDimensioneTerritorialeSelected && this.descDimensioneTerritorialeSelected.codice) ? parseInt(this.descDimensioneTerritorialeSelected.codice) : undefined),
					descDimensioneTerritoriale: ((this.descDimensioneTerritorialeSelected && this.descDimensioneTerritorialeSelected.descrizione) ? this.descDimensioneTerritorialeSelected.descrizione : undefined),
					idProgettoComplesso: ((this.descProgettoComplessoSelected && this.descProgettoComplessoSelected.codice) ? parseInt(this.descProgettoComplessoSelected.codice) : undefined),
					descProgettoComplesso: ((this.descProgettoComplessoSelected && this.descProgettoComplessoSelected.descrizione) ? this.descProgettoComplessoSelected.descrizione : undefined),
					idGrandeProgetto: ((this.descGrandeProgettoSelected && this.descGrandeProgettoSelected.codice) ? parseInt(this.descGrandeProgettoSelected.codice) : undefined),
					descGrandeProgetto: ((this.descGrandeProgettoSelected && this.descGrandeProgettoSelected.descrizione) ? this.descGrandeProgettoSelected.descrizione : undefined),
					idSettoreCipe: ((this.descSettoreCipeSelected && this.descSettoreCipeSelected.codice) ? parseInt(this.descSettoreCipeSelected.codice) : undefined),
					descSettoreCipe: ((this.descSettoreCipeSelected && this.descSettoreCipeSelected.descrizione) ? this.descSettoreCipeSelected.descrizione : undefined),
					idSottoSettoreCipe: ((this.descSottoSettoreCipeSelected && this.descSottoSettoreCipeSelected.codice) ? parseInt(this.descSottoSettoreCipeSelected.codice) : undefined),
					descSottoSettoreCipe: ((this.descSottoSettoreCipeSelected && this.descSottoSettoreCipeSelected.descrizione) ? this.descSottoSettoreCipeSelected.descrizione : undefined),
					idCategoriaCipe: ((this.descCategoriaCipeSelected && this.descCategoriaCipeSelected.codice) ? parseInt(this.descCategoriaCipeSelected.codice) : undefined),
					descCategoriaCipe: ((this.descCategoriaCipeSelected && this.descCategoriaCipeSelected.descrizione) ? this.descCategoriaCipeSelected.descrizione : undefined),
					idNaturaCipe: ((this.descNaturaCipeSelected && this.descNaturaCipeSelected.codice) ? parseInt(this.descNaturaCipeSelected.codice) : undefined),
					descNaturaCipe: ((this.descNaturaCipeSelected && this.descNaturaCipeSelected.descrizione) ? this.descNaturaCipeSelected.descrizione : undefined),
					idTipologiaCipe: ((this.descTipologiaCipeSelected && this.descTipologiaCipeSelected.codice) ? parseInt(this.descTipologiaCipeSelected.codice) : undefined),
					descTipologiaCipe: ((this.descTipologiaCipeSelected && this.descTipologiaCipeSelected.descrizione) ? this.descTipologiaCipeSelected.descrizione : undefined),
					idTipoProceduraOrig: ((this.descTipoProceduraOrigSelected && this.descTipoProceduraOrigSelected.codice) ? parseInt(this.descTipoProceduraOrigSelected.codice) : undefined),
					descTipoProceduraOrig: ((this.descTipoProceduraOrigSelected && this.descTipoProceduraOrigSelected.descrizione) ? this.descTipoProceduraOrigSelected.descrizione : undefined),
					flagInvioMonit: this.flagInvioMonit,
					flagRichiestaCup: this.flagRichiestaCup,
					flagPPP: this.ppp,
					flagStrategico: this.flagStrategico
				}
			}
		};
		this.datiProgettoService.salvaDatiProgetto(salvaDatiProgettoRequest).subscribe((data: EsitoOperazioni) => {
			if (data) {
				if (data.esito) {
					this.isModificaLimited = true;
					this.showMessageSuccess(data.msg);
					this.loadLineaPORFESR1420(true);
				} else {
					this.showMessageError(data.msg);
				}
			}
			this.loadedSalvaDatiProgetto = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di salvataggio dei dati del progetto.");
			this.loadedSalvaDatiProgetto = true;
		});
	}

	salvaDatiGenerali() {
		if (this.isModificaLimited) {
			this.isModificaLimited = false; // Modifica
			if (!this.isDefaultCup) {
				this.onAnnullaCup();
			}
			if (!this.isDefaultTitoloProgetto) {
				this.onAnnullaTitoloProgetto();
			}
			this.isDisabledNumeroDomanda = true;
			this.isDisabledCodiceVisualizzato = true;
			this.isDisabledDescSettore = true;
			this.isDisabledDescAteco = true;
			this.isDisabledDescTipoOperazione = true;
			this.isDisabledDescTipoStrumento = true;
			this.isDisabledDescSettoreCipe = true;
			this.isDisabledDescSottoSettoreCipe = true;
			this.isDisabledDescCategoriaCipe = true;
			this.isDisabledDescNaturaCipe = true;
			this.isDisabledDescTipologiaCipe = true;
			if (!this.isLineaBANDIREGP) {
				this.loadSettoreAttivita(true);
				this.loadTipoOperazione();
				if (this.is1420) {
					this.loadObiettivoTematico();
					this.loadClassificazioneRA();
				} else {
					this.loadPrioritaQsn(this.datiProgetto.idLineaInterventoAsse, true);
				}
				this.loadStrumentoAttuativo();
				this.loadSettoreCpt();
				this.loadTemaPrioritario();
				this.loadIndicatoreRisultatoProgramma();
				this.loadIndicatoreQsn();
				this.loadTipoAiuto();
				this.loadStrumentoProgrammazione();
				this.loadDimensioneTerritoriale();
				this.loadProgettoComplesso();
				if (this.is1420) {
					this.loadGrandeProgetto();
				}
				this.loadSettoreCipe(true);
				this.loadNaturaCipe();
				this.loadTipoProceduraOriginaria();
			}
		} else {
			this.confermaDialog();  // Salva
		}
	}

	compareWithCodiceDescrizione(option: CodiceDescrizione, value: CodiceDescrizione) {
		return value && (option.codice === value.codice);
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
		let r = false;
		if (
			(
				(
					this.action == 'modifica'
				) && (
					// !this.loadedLineaPORFESR1420 ||
					!this.loadedDatiProgetto ||
					(
						!this.isModificaLimited && !this.isLineaBANDIREGP &&
						(
							!this.loadedSettoreAttivita ||
							!this.loadedAttivitaAteco ||
							!this.loadedTipoOperazione ||
							(
								this.is1420 && (
									!this.loadedObiettivoTematico ||
									!this.loadedClassificazioneRA
								)
							) ||
							(
								!this.is1420 && (
									!this.loadedPrioritaQsn ||
									!this.loadedObiettivoGeneraleQsn ||
									!this.loadedObiettivoSpecificoQsn
								)
							) ||
							!this.loadedStrumentoAttuativo ||
							!this.loadedSettoreCpt ||
							!this.loadedTemaPrioritario ||
							!this.loadedIndicatoreRisultatoProgramma ||
							!this.loadedIndicatoreQsn ||
							!this.loadedTipoAiuto ||
							!this.loadedStrumentoProgrammazione ||
							!this.loadedDimensioneTerritoriale ||
							(
								this.is1420 && (
									!this.loadedProgettoComplesso
								)
							) ||
							!this.loadedSettoreCipe ||
							!this.loadedSottoSettoreCipe ||
							!this.loadedCategoriaCipe ||
							!this.loadedNaturaCipe ||
							!this.loadedTipologiaCipe ||
							(
								this.is1420 && (
									!this.loadedTipoProceduraOriginaria
								)
							)
						)
					)
				)
			) || (
				(
					this.action == 'dettaglio'
				) && (
					!this.loadedLineaPORFESR1420 ||
					!this.loadedDatiProgetto
				)
			) || !this.loadedSalvaDatiProgetto || !this.loadedLineaREGP || !this.loadedLineaPNRR || !this.loadedRegolaBR58
		) {
			r = true;
		}
		return r;
	}
}
