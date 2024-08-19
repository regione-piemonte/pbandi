/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { AnteprimaDialogComponent, ArchivioFileService, HandleExceptionService, InfoDocumentoVo, UserInfoSec } from '@pbandi/common-lib';
import { saveAs } from 'file-saver-es';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { InizializzazioneService } from 'src/app/shared/services/inizializzazione.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AffidamentoDTO } from '../../commons/affidamento-dto';
import { AllegatoDTO } from '../../commons/allegato-dto';
import { VerificaRichiestaErogazioneService } from '../../services/verifica-richiesta-erogazione.service';


@Component({
	selector: 'app-verifica-richiesta-erogazione',
	templateUrl: './verifica-richiesta-erogazione.component.html',
	styleUrls: ['./verifica-richiesta-erogazione.component.scss'],
})
export class VerificaRichiestaErogazioneComponent implements OnInit {
	codiceProgetto: string;
	beneficiario: string;

	subscribers: any = {};

	idProgetto: number;
	idBandoLinea: number;
	idErogazione: number;
	idSoggetto;
	codiceRuolo;
	isIntegrativa: boolean;

	//RIEPILOGO
	causaleErogazione: string;
	importoRichiesto: number;
	dataRichiesta: Date;
	allegatoRiepilogo: Array<any>;

	//FIDEIUSSIONI
	fideiussioni: Array<any>;

	//ALLEGATI
	allegati: Array<any>;

	//RAPPRESENTANTE LEGALE
	dataNascita: Date;
	nome: string;
	cognome: string;
	comuneNascita: string;
	comuneResidenza: string;
	indirizzoResidenza: string;
	capResidenza: number;

	//ESTREMI BANCARI
	iban: string;

	//AFFIDAMENTI:
	affidamenti: Array<any>;
	displayedColumns: string[] = ['fornitore', 'servizioAffidato', 'documento', 'denominazioneFile'];
	dataSourceIng: MatTableDataSource<AffidamentoDTO>;
	dataSourceLav: MatTableDataSource<AffidamentoDTO>;

	//MESSAGGI
	messageSuccess: string;
	isMessageSuccessVisible: boolean;
	messageError: string;
	isMessageErrorVisible: boolean;

	//LOADED
	loadedDownload: boolean = true;
	loadedPage: boolean = false;
	user: UserInfoSec;
	isReadOnly: boolean;
	statoVerifica: boolean;
	constructor(
		private userService: UserService,
		private activatedRoute: ActivatedRoute,
		private sharedService: SharedService,
		private configService: ConfigService,
		private handleExceptionService: HandleExceptionService,
		private dialog: MatDialog,
		private router: Router,
		private inizializzazioneService: InizializzazioneService,
		private verificaRichiestaErogazioneService: VerificaRichiestaErogazioneService,
		private archivioFileService: ArchivioFileService,
	) { }

	ngOnInit(): void {
		// Costanti da usare nel servizio del pulsante Torna alle attivita da svolgere:
		// Constants.DESCR_BREVE_TASK_VERIF_RICH_EROGAZIONE
		// Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VERIF_RICH_EROGAZIONE

		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(
			data => {
				if (!data) return;
				this.user = data;
				this.idSoggetto = this.user.idSoggetto;
				this.codiceRuolo = this.user.codiceRuolo;
				this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

				if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
					this.subscribers.router = this.activatedRoute.params.subscribe(params => {
						this.idProgetto = +params['idProgetto'];
						this.idErogazione = +params['idRichiestaErogazione']; //Da verificare che effettivamente sia il numero richiesta
						this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(data => {
							this.codiceProgetto = data;
						});
						this.loadErogazione(this.idProgetto, this.idErogazione);
					});
				}
			},
			err => this.handleExceptionService.handleBlockingError(err),
		);
	}

	loadErogazione(idProgetto: number, idErogazione: number) {
		this.subscribers = this.verificaRichiestaErogazioneService.getDettaglioVerificaErogazione(idProgetto, idErogazione).subscribe(
			data => {
				if (data) {
					const erogazione = data.erogazione;
					if (erogazione) {
						this.causaleErogazione = erogazione.causaleErogazione.descCausale ?? "";

						this.fideiussioni = erogazione.fideiussioni ?? "";
						this.allegati = erogazione.allegati ?? "";
						this.allegatoRiepilogo = erogazione.allegatiRichiestaErogazione ?? "";
						if (erogazione.rappresentanteLegale && erogazione.rappresentanteLegale[0] != null) {
							this.dataNascita = new Date(erogazione.rappresentanteLegale[0].dataNascita);
							this.nome = erogazione.rappresentanteLegale[0].nome ?? "";
							this.cognome = erogazione.rappresentanteLegale[0].cognome ?? "";
							this.comuneNascita = erogazione.rappresentanteLegale[0].luogoNascita ?? "";
							this.comuneResidenza = erogazione.rappresentanteLegale[0].comuneResidenza ?? "";
							this.indirizzoResidenza = erogazione.rappresentanteLegale[0].indirizzoResidenza ?? "";
							this.capResidenza = erogazione.rappresentanteLegale[0].capResidenza ?? "";
						}
						if (erogazione.estremiBancari) {
							this.iban = erogazione.estremiBancari.iban ?? "";
						}
						if (erogazione.richiestaErogazione) {
							this.isReadOnly = erogazione.richiestaErogazione.flagBollinoVerificaErogaz ?? false;
							this.importoRichiesto = erogazione.richiestaErogazione.importoErogazioneRichiesto ?? "";
							this.dataRichiesta = erogazione.richiestaErogazione.dtRichiestaErogazione ?? "";
							if (this.isReadOnly) {
								this.statoVerifica = erogazione.richiestaErogazione.flagBollinoVerificaErogaz === 'P';
							}
						}
						this.affidamenti = erogazione.affidamenti;
						if (this.affidamenti) {
							this.dataSourceIng = new MatTableDataSource<AffidamentoDTO>(this.affidamenti.filter(e => e.flagAffidServtec == 'S'));
							this.dataSourceLav = new MatTableDataSource<AffidamentoDTO>(this.affidamenti.filter(e => e.flagAffidServtec == 'L'));
						}
					}
					this.loadedPage = true;
				}
			},
			err => {
				this.showMessageError('Errore in fase di caricamento del dettaglio erogazione');
				console.error(err);
				this.loadedPage = true;
			},
		);
	}

	verificaRichiestaErogazione(isVerificata: boolean) {
		this.resetMessageSuccess();
		this.resetMessageError();
		this.subscribers = this.verificaRichiestaErogazioneService.postVerificaErogazione(this.idErogazione, isVerificata).subscribe(
			data => {
				if (data) {
					if (data.esito) {
						this.isReadOnly = true;
						this.statoVerifica = isVerificata;
					} else {
						this.showMessageError(data.msg);
					}
				}
			},
			err => {
				this.showMessageError("Errore in fase di verifica dell'erogazione");
				console.error(err);
			},
		);
	}

	anteprimaAllegato(doc: AllegatoDTO) {
		this.resetMessageError();
		let comodo = new Array<any>();
		comodo.push(doc.nomeFile);
		comodo.push(doc.idDocumentoIndex);
		comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(doc.idTipoDocumentoIndex.toString(), doc.nomeFile, null, null, null, null, null, doc.idDocumentoIndex.toString(), null)]));
		comodo.push(this.configService.getApiURL());
		comodo.push(doc.idTipoDocumentoIndex);
		this.dialog.open(AnteprimaDialogComponent, {
			data: comodo,
			panelClass: 'anteprima-dialog-container',
		});
	}

	downloadAllegato(doc: AllegatoDTO) {
		this.resetMessageSuccess();
		this.resetMessageError();
		this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), doc.idDocumentoIndex.toString()).subscribe(
			res => {
				if (res) {
					saveAs(res, doc.nomeFile);
				}
			},
			err => {
				this.showMessageError('Errore in fase di download del file');
				console.error(err);
			},
		);
	}

	showMessageSuccess(msg: string) {
		this.messageSuccess = msg;
		this.isMessageSuccessVisible = true;
	}

	showMessageError(msg: string) {
		this.messageError = msg;
		this.isMessageErrorVisible = true;
	}

	resetMessageSuccess() {
		this.messageSuccess = null;
		this.isMessageSuccessVisible = false;
	}

	resetMessageError() {
		this.messageError = null;
		this.isMessageErrorVisible = false;
	}

	onTorna() {
		this.chiudiAttivita();
	}

	chiudiAttivita() {
		this.resetMessageError();
		this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_VERIF_RICH_EROGAZIONE).subscribe(
			(data: string) => {
				window.location.assign(this.configService.getPbworkspaceURL() + '/#/drawer/' + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + '/attivita');
			},
			err => {
				this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
				this.router.navigate(['/errorRouting'], { queryParams: { message: 'Sessione scaduta' }, skipLocationChange: true });
				console.error(err);
			},
		);
	}
}
