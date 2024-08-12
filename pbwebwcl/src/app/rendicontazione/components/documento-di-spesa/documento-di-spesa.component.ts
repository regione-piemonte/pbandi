import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatRadioChange } from '@angular/material/radio';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import {
	ArchivioFileDialogComponent,
	ArchivioFileService,
	DatiProgettoAttivitaPregresseDialogComponent,
	InfoDocumentoVo,
	VisualizzaContoEconomicoDialogComponent,
	VisualizzaContoEconomicoService,
} from '@pbandi/common-lib';
import { saveAs } from 'file-saver-es';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { UserInfoSec } from '../../../core/commons/dto/user-info';
import { HandleExceptionService } from '../../../core/services/handle-exception.service';
import { UserService } from '../../../core/services/user.service';
import { DatiFatturaElettronica } from '../../commons/dto/dati-fattura-elettronica';
import { DecodificaDTO } from '../../commons/dto/decodifica-dto';
import { DocumentoAllegatoDTO } from '../../commons/dto/documento-allegato-dto';
import { DocumentoDiPagamentoDTO } from '../../commons/dto/documento-di-pagamento-dto';
import { DocumentoDiSpesaDTO } from '../../commons/dto/documento-di-spesa-dto';
import { FatturaRiferimentoDTO } from '../../commons/dto/fattura-riferimento.dto';
import { FornitoreComboDTO } from '../../commons/dto/fornitore-combo-dto';
import { FornitoreFormDTO } from '../../commons/dto/fornitore-form-dto';
import { QualificaDTO } from '../../commons/dto/qualifica-dto';
import { SalCorrenteDTO } from '../../commons/dto/sal-corrente-dto';
import { VoceDiSpesaPadre } from '../../commons/dto/voce-di-spesa';
import { VoceDiSpesaDTO } from '../../commons/dto/voce-di-spesa-dto';
import { AssociaFilesRequest } from '../../commons/requests/associa-files-request';
import { PagamentiAssociatiRequest } from '../../commons/requests/pagamenti-associati-request';
import { TipologiaDocumentoSpesaVo } from '../../commons/vo/tipologia-documento-spesa-vo';
import { DocumentoDiSpesaService } from '../../services/documento-di-spesa.service';
import { FornitoreService } from '../../services/fornitore.service';
import { RendicontazioneService } from '../../services/rendicontazione.service';
import { AssociaAffidamentoDialogComponent } from '../associa-affidamento-dialog/associa-affidamento-dialog.component';
import { NuovaQualificaDialogComponent } from '../nuova-qualifica-dialog/nuova-qualifica-dialog.component';
import { NuovoModicaFornitoreDialogComponent } from '../nuovo-modica-fornitore-dialog/nuovo-modica-fornitore-dialog.component';
import { NuovoModificaQuietanzaDialogComponent } from '../nuovo-modifica-quietanza-dialog/nuovo-modifica-quietanza-dialog.component';
import { NuovoModificaVoceSpesaDialogComponent } from '../nuovo-modifica-voce-spesa-dialog/nuovo-modifica-voce-spesa-dialog.component';

import * as _moment from 'moment';
// tslint:disable-next-line:no-duplicate-imports
import { default as _rollupMoment, Moment } from 'moment';

const moment = _rollupMoment || _moment;
// tslint:disable-next-line:no-duplicate-imports
import { MatDatepicker } from '@angular/material/datepicker';
import { EsitoImportoSaldoDTO } from '../../commons/dto/esito-importo-dto';
import { ParametroCompensiDTO } from '../../commons/dto/parametro-compensi-dto';
import { DichiarazioneDiSpesaService } from '../../services/dichiarazione-di-spesa.service';

export interface Tile {
	color: string;
	cols: number;
	rows: number;
	text: string;
}

@Component({
	selector: 'app-documento-di-spesa',
	templateUrl: './documento-di-spesa.component.html',
	styleUrls: ['./documento-di-spesa.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DocumentoDiSpesaComponent implements OnInit {

	visualizzaInCulturaPubblico: boolean;
	tipoBeneficiario: number;
	isBandoCultura: boolean;
	idProgetto: number;
	idBandoLinea: number;
	idRouting: number;
	idDocumentoDiSpesa: number;
	tipoOperazioneCurrent: string;
	tipoOperazioneFrom: string;
	documento: DocumentoDiSpesaDTO;
	beneficiario: string;
	isValidazione: boolean = false;
	idOperazioneValidazione: number;
	idAppaltocigCpaAffidamentoData: number;
	cigCpaAffidamentoData: string;
	codiceProgetto: string;
	tipologiaBandoCulturaBeneficiarioPubblico: Array<TipologiaDocumentoSpesaVo>;
	tipologiaSelected: TipologiaDocumentoSpesaVo;
	tipologie: Array<TipologiaDocumentoSpesaVo> = null;
	tipoFornitoreSelected: DecodificaDTO;
	tipologieFornitore: Array<DecodificaDTO>;
	attivitaSelected: DecodificaDTO;
	attivitas: Array<DecodificaDTO>;
	qualifiche: Array<DecodificaDTO>;
	riferimentoFatturaSelected: FatturaRiferimentoDTO;
	riferimentoFatture: Array<FatturaRiferimentoDTO>;
	fornitoreSelected: FornitoreComboDTO;
	fornitori: Array<FornitoreComboDTO>;
	showQualificheTable: boolean;
	orderLine = 'prova';
	numero: string;
	periodo: string;
	periodoMeseAnno: FormControl = new FormControl();
	data: FormControl = new FormControl();
	documentoSelected: string = '2';
	descrizione: string;
	imponibile: number;
	imponibileFormatted: string;
	imponibileRitAcc: number;
	imponibileRitAccFormatted: string;
	oreLavorate: number;
	costoOrario: number;
	costoOrarioFormatted: string;
	rendicontabile: number;
	rendicontabileFormatted: string;
	importoRendicontabile: number;
	importoRendicontabileFormatted: string;
	importo: number;
	importoFormatted: string;
	importoIva: number;
	importoIvaFormatted: string;
	importoIvaIndetraibile: number;
	importoIvaIndetraibileFormatted: string;
	totaleDocumento: number;
	totaleDocumentoFormatted: string;
	destinazioneTrasferta: string;
	durataTrasferta: number;
	durataTrasfertaFormatted: string;
	tipoInvioSelected: string = '2';
	taskSelected: string;
	tasks: Array<string>;
	user: UserInfoSec;
	isReadOnly: boolean;
	taskVisibile: boolean;
	rendicontabileFatturaRifFormatted: string;
	totaleDocumentoFatturaRifFormatted: string;
	totaleQuietanzatoFatturaRifFormatted: string;
	allegatiFornitore: Array<DocumentoAllegatoDTO>;
	allegatiDocumentoDiSpesaDaSalvare: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();
	allegatiDocumentoDiSpesa: Array<DocumentoAllegatoDTO>;
	idAllegatoXml: string;
	macroVoci: Array<VoceDiSpesaPadre>;
	totaleRendicontato: number = 0;
	residuoRendicontabile: number = 0;
	totaleQuietanzato: number = 0;
	totaleNoteCredito: number;
	rendicontatoQuietanzato: number = 0;
	residuoQuietanzabile: number = 0;
	modalitaQuitanza: Array<DecodificaDTO>;
	causaliQuietanza: Array<DecodificaDTO>;
	allegatiAmmessi: boolean;
	causaleQuietanzaVisibile: boolean;
	idTipoOperazioneProgetto: number;
	idProcessoBandoLinea: number;
	utenteAbilitatoAdAssociarePagamenti: boolean;
	utenteAbilitatoAdAssociareVociDiSpesa: boolean;
	noteDiCreditoFattura: Array<DocumentoDiSpesaDTO>;
	noteValidatore: string;
	flagElettXml: string;
	salCorrente: SalCorrenteDTO;
	spesaAmmessaContoEconomico: number;
	isSpesaAmmessaInf5Milioni: boolean;
	quotaImportoAgevolato: number;
	importoASaldo: number;
	isIntegrativa: boolean;
	parametriCompensi: Array<ParametroCompensiDTO>;
	categoriaSelected: number;
	categorie: Array<number> = [];
	giorniLavorabiliMensili: number;
	giorniLavorabiliMensiliFormatted: string;
	oreMensiliLavorate: number;
	oreMensiliLavorateFormatted: string;
	sospensioniBrevi: number;
	sospensioniBreviFormatted: string;
	sospensioniLungheGgTotali: number;
	sospensioniLungheGgTotaliFormatted: string;
	sospensioniLungheGgLav: number;
	sospensioniLungheGgLavFormatted: string;
	ggQuietanza: number;

	// Regole
	BR42: string = 'BR42';
	BR48: string = 'BR48';
	BR50: string = 'BR50';
	BR51: string = 'BR51';
	BR52: string = 'BR52';
	BR54: string = 'BR54';
	BR56: string = 'BR56';
	BR62: string = 'BR62';
	BR65: string = 'BR65';
	isBR42: boolean;
	isBR48: boolean;
	isBR50: boolean;
	isBR51: boolean;
	isBR52: boolean;
	isBR54: boolean;
	isBR56: boolean;
	isBR58: boolean;
	isBR62: boolean;
	isBR65: boolean;
	documentazioneDaAllegare: string;

	displayedColumnsQuietanziatoDefault: string[] = ['modalita', 'data', 'importo', 'residuo'];//, 'allegati', 'azioni' dipende dalle regole
	displayedColumnsQuietanziato: string[] = [];
	dataSourceQuietanziato: MatTableDataSource<DocumentoDiPagamentoDTO>;

	displayedColumnsQualifiche: string[] = ['checked', 'qualifica', 'costoOrario', 'note'];
	dataSourceQualifiche: MatTableDataSource<QualificaDTO>;

	displayedColumnsVociSpesa: string[] = ['voceDiSpesa', 'ammesso', 'residuo', 'rendicontato', 'azioni'];
	dataSourceVociSpesa: MatTableDataSource<VoceDiSpesaDTO>;

	displayedColumnsNoteDiCreditoFattura: string[] = ['index', 'data', 'numero', 'importo', 'azioni'];
	dataSourceNoteDiCreditoFattura: MatTableDataSource<DocumentoDiSpesaDTO>;

	//MESSAGGI SUCCESS E ERROR
	messageSuccess: string;
	isMessageSuccessVisible: boolean;
	messageWarning: string;
	isMessageWarningVisible: boolean;
	messageWarning2: string;
	isMessageWarning2Visible: boolean;
	messageError: string;
	isMessageErrorVisible: boolean;
	messageError2: string;
	isMessageError2Visible: boolean;

	//LOADED
	loadedInizializzazione: boolean;
	loadedTasks: boolean = true;
	loadedTipologieDocSpesa: boolean;
	loadedSalCorrente: boolean = true;
	loadedAttivita: boolean;
	loadedQualifiche: boolean;
	loadedFornitori: boolean = true;
	loadedQualificheFornitore: boolean = true;
	loadedRiferimentoFattura: boolean = true;
	loadedDownload: boolean = true;
	loadedAllegatiFornitore: boolean = true;
	loadedSave: boolean = true;
	loadedMacroVoci: boolean = true;
	loadedVociDiSpesa: boolean = true;
	loadedAssociaFiles: boolean = true;
	loadedAllegatiDocSpesa: boolean = true;
	loadedDisassociaAllegato: boolean = true;
	loadedModalitaQuietanza: boolean = true;
	loadedCausaliQuietanza: boolean = true;
	loadedDocumento: boolean = true;
	loadedElimina: boolean = true;
	loadedQuietanze: boolean = true;
	loadedNoteCreditoFattura: boolean = true;
	loadedSpesaAmmessa: boolean = true;
	loadedRegolaBR50 = true;
	loadedRegolaBR48 = true;
	loadedRegolaBR56 = true;
	loadedQuotaImportAgevolato = true;
	loadedParametriCompensi = true;
	loadedImportoASaldo = true;
	loadedDocumentazioneDaAllegare = true;
	loadedBandoCultura: boolean;
	BR48Loaded: boolean = true;
	BR50Loaded: boolean = true;
	BR54Loaded: boolean = true;
	BR56Loaded: boolean = true;
	BR62Loaded: boolean = true;
	BR65Loaded: boolean = true;

	//OBJECT SUBSCRIBER
	subscribers: any = {};

	@ViewChild("ricercaForm", { static: true }) ricercaForm: NgForm;

	get descBreveTipoDocAutocertificazioneSpese() { return Constants.DESC_BREVE_TIPO_DOC_SPESA_AUTOCERTIFICAZIONE_SPESE; }
	get descBreveTipoDocSALCQ() { return Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_CON_QUIETANZA; }
	get descBreveTipoDocSALSQ() { return Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_SENZA_QUIETANZA; }
	get descBreveTipoDocCompensoMensTirocinante() { return Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_MENSILE_TIROCINANTE; }
	get descBreveTipoDocCompensoImpArtigiana() { return Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_IMPRESA_ARTIGIANA; }
	get descBreveTipoDocCompensoSoggGestore() { return Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_SOGGETTO_GESTORE; }
	get descBreveTipoDocCedolino() { return Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO; }
	get descBreveTipoDocCedolinoCostiStandard() { return Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD; }


	get isStatoDaCompletare() { return this.documento?.descBreveStatoDocumentoSpesa === Constants.DESC_BREVE_STATO_DOC_SPESA_DA_COMPLETARE; }

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private rendicontazioneService: RendicontazioneService,
		private fornitoreService: FornitoreService,
		private documentoDiSpesaService: DocumentoDiSpesaService,
		private sharedService: SharedService,
		private handleExceptionService: HandleExceptionService,
		private contoEconomicoService: VisualizzaContoEconomicoService,
		private dichiarazioneDiSpesaService: DichiarazioneDiSpesaService,
		private dialog: MatDialog,
		private userService: UserService,
		private _snackBar: MatSnackBar,
		private archivioFileService: ArchivioFileService,
		private configService: ConfigService,
	) { }

	tiles: Tile[] = [
		{ text: 'One', cols: 3, rows: 1, color: 'lightblue' },
		{ text: 'Two', cols: 1, rows: 2, color: 'lightgreen' },
		{ text: 'Three', cols: 1, rows: 1, color: 'lightpink' },
		{ text: 'Four', cols: 2, rows: 1, color: '#DDBDF1' },
	];

	ngOnInit(): void {
		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
			if (data) {
				this.user = data;
				this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

				if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADA_OPE_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_GDF) {
					this.isReadOnly = true;
				}
				this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
					this.idRouting = +params['id'];
				});
				this.subscribers.router = this.activatedRoute.params.subscribe(params => {
					this.idProgetto = +params['idProgetto'];
					this.idBandoLinea = +params['idBandoLinea'];
					this.loadedBandoCultura = false;
					this.dichiarazioneDiSpesaService.isBandoCultura(this.idBandoLinea).subscribe(data => {
						this.isBandoCultura = data;
						this.inizializza();
						this.loadedBandoCultura = true;
					}, err => {
						this.handleExceptionService.handleNotBlockingError(err);
						this.showMessageError("Errore in fase di verifica bando cultura.")
						this.loadedBandoCultura = true;
					});
				});
			}
		});
	}

	inizializza() {
		this.subscribers.router = this.activatedRoute.params.subscribe(params => {
			if (params['idDocSpesa']) {
				// idDocumentoDiSpesa == undefined -> nuovo documento di spesa, idDocumentoDiSpesa != undefined -> modifica documento di spesa
				this.idDocumentoDiSpesa = +params['idDocSpesa'];
				if (this.activatedRoute.snapshot.paramMap.get('current') != null) {
					this.tipoOperazioneCurrent = this.activatedRoute.snapshot.paramMap.get('current');
				}
				if (this.activatedRoute.snapshot.paramMap.get('from') != null) {
					this.tipoOperazioneFrom = this.activatedRoute.snapshot.paramMap.get('from');
				}
				if (this.activatedRoute.snapshot.paramMap.get('idOperazioneValidazione') != null) {
					this.idOperazioneValidazione = +this.activatedRoute.snapshot.paramMap.get('idOperazioneValidazione');
				}
			}
			this.isIntegrativa = this.activatedRoute.snapshot.paramMap.get('integrativa') ? true : false;
			if (this.idDocumentoDiSpesa) {
				// dettaglio, modifica, clona, validazione
				this.loadDocumento();
				if (
					this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA &&
					(this.tipoOperazioneFrom === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_INSERISCI || this.tipoOperazioneFrom === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA)
				) {
					// se arrivo da inserimento o clona mostro i messaggi del salvataggio
					let msgsucc = this.documentoDiSpesaService.messageSuccess;
					let msgerr = this.documentoDiSpesaService.messageError;
					if (msgsucc) {
						this.showMessageSuccess(msgsucc);
						this.documentoDiSpesaService.messageSuccess = null;
					}
					if (msgerr) {
						this.showMessageError(msgerr);
						this.documentoDiSpesaService.messageError = null;
					}
				}
				if (this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO) {
					this.data.disable();
				}
				if (this.tipoOperazioneFrom === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_VALIDAZIONE) {
					this.isValidazione = true;
				}
			} else {
				//inserisci
				this.loadDati();
			}

			this.checkRegola(this.BR48, this.BR48Loaded);
			this.checkRegola(this.BR50, this.BR50Loaded);
			this.checkRegola(this.BR54, this.BR54Loaded);
			this.checkRegola(this.BR56, this.BR56Loaded);
			this.checkRegola(this.BR62, this.BR62Loaded);
			this.checkRegola(this.BR65, this.BR65Loaded);
		});

	}

	get descBrevePagina(): string {
		return Constants.DESC_BREVE_PAGINA_HELP_DOC_SPESA;
	}

	get apiURL(): string {
		return this.configService.getApiURL();
	}

	checkRegola(regola: string, loaded: boolean): boolean {
		loaded = false;
		this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, regola).subscribe(
			res => {
				if (res != undefined && res != null) {
					switch (regola) {
						case this.BR48:
							this.isBR48 = res;
							break;
						case this.BR50:
							this.isBR50 = res;
							break;
						case this.BR54:
							this.isBR54 = res;
							break;
						case this.BR56:
							this.isBR56 = res;
							break;
						case this.BR62:
							this.isBR62 = res;
							break;
						case this.BR65:
							this.isBR65 = res;
							if (this.isBR65) {
								this.checkSpesaAmmessaContoEconomico();
							}
							break;
					}
				}
				loaded = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				loaded = true;
				//return false
			},
		);
		return false;
	}

	loadedRegole(): boolean {
		if (!this.BR48Loaded || !this.BR50Loaded || !this.BR54Loaded || !this.BR56Loaded || !this.BR62Loaded || !this.BR65Loaded) {
			return false;
		}
		return true;
	}

	checkSpesaAmmessaContoEconomico() {
		this.loadedSpesaAmmessa = false;
		this.contoEconomicoService.getSpesaAmmessaContoEconomico(this.idProgetto, this.configService.getApiURL()).subscribe(
			data => {
				this.spesaAmmessaContoEconomico = data;
				if (this.spesaAmmessaContoEconomico <= 5000000) {
					this.isSpesaAmmessaInf5Milioni = true;
				}
				this.loadedSpesaAmmessa = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di lettura dell'importo spesa ammessa dal conto economico.");
				this.loadedSpesaAmmessa = true;
			},
		);
	}

	setCampiFromDocumento() {
		switch (this.documento.descBreveTipoDocumentoDiSpesa) {
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.imponibileRitAcc = this.documento.imponibile;
				this.imponibileRitAccFormatted = this.imponibileRitAcc !== null ? this.sharedService.formatValue(this.imponibileRitAcc.toString()) : null;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				this.calcolaTotaleDocumento();
				this.numero = this.documento.numeroDocumento;
				this.costoOrario = this.documento.costoOrario;
				this.costoOrarioFormatted = this.costoOrario !== null ? this.sharedService.formatValue(this.costoOrario.toString()) : null;
				this.tipoInvioSelected = this.documento.tipoInvio === "C" ? "1" : "2";
				this.documentoSelected = this.documento.flagElettronico ? "1" : "2";
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_A_PROGETTO:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.imponibileRitAcc = this.documento.imponibile;
				this.imponibileRitAccFormatted = this.imponibileRitAcc !== null ? this.sharedService.formatValue(this.imponibileRitAcc.toString()) : null;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				this.calcolaTotaleDocumento();
				this.numero = this.documento.numeroDocumento;
				this.tipoInvioSelected = this.documento.tipoInvio === "C" ? "1" : "2";
				this.documentoSelected = this.documento.flagElettronico ? "1" : "2";
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_SPESE:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.destinazioneTrasferta = this.documento.destinazioneTrasferta;
				this.durataTrasferta = this.documento.durataTrasferta;
				this.durataTrasfertaFormatted = this.durataTrasferta !== null ? this.sharedService.formatValue(this.durataTrasferta.toString()) : null;
				this.imponibile = this.documento.imponibile;
				this.imponibileFormatted = this.imponibile !== null ? this.sharedService.formatValue(this.imponibile.toString()) : null;
				this.importoIva = this.documento.importoIva;
				this.importoIvaFormatted = this.importoIva !== null ? this.sharedService.formatValue(this.importoIva.toString()) : null;
				this.importoIvaIndetraibile = this.documento.importoIvaACosto;
				this.importoIvaIndetraibileFormatted = this.importoIvaIndetraibile !== null ? this.sharedService.formatValue(this.importoIvaIndetraibile.toString()) : null;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				this.calcolaTotaleDocumento();
				this.numero = this.documento.numeroDocumento;
				this.tipoInvioSelected = this.documento.tipoInvio === "C" ? "1" : "2";
				this.documentoSelected = this.documento.flagElettronico ? "1" : "2";
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_CREDITO:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.imponibile = this.documento.imponibile;
				this.imponibileFormatted = this.imponibile !== null ? this.sharedService.formatValue(this.imponibile.toString()) : null;
				this.importoIva = this.documento.importoIva;
				this.importoIvaFormatted = this.importoIva !== null ? this.sharedService.formatValue(this.importoIva.toString()) : null;
				this.importoIvaIndetraibile = this.documento.importoIvaACosto;
				this.importoIvaIndetraibileFormatted = this.importoIvaIndetraibile !== null ? this.sharedService.formatValue(this.importoIvaIndetraibile.toString()) : null;
				this.calcolaTotaleDocumento();
				this.numero = this.documento.numeroDocumento;
				this.tipoInvioSelected = this.documento.tipoInvio === "C" ? "1" : "2";
				this.documentoSelected = this.documento.flagElettronico ? "1" : "2";
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.importoRendicontabile = this.documento.importoTotaleDocumentoIvato;
				this.importoRendicontabileFormatted = this.importoRendicontabile !== null ? this.sharedService.formatValue(this.importoRendicontabile.toString()) : null;
				this.tipoInvioSelected = this.documento.tipoInvio === "C" ? "1" : "2";
				this.calcolaTotaleDocumentoRendicontabile();
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE_COSTI_SEMPLIFICATI:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_IMPRESA_ARTIGIANA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_SOGGETTO_GESTORE:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.importo = this.documento.importoTotaleDocumentoIvato;
				this.importoFormatted = this.importo !== null ? this.sharedService.formatValue(this.importo.toString()) : null;
				this.periodo = this.documento.numeroDocumento;
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				this.periodo = this.documento.numeroDocumento;
				this.costoOrario = this.documento.costoOrario;
				this.costoOrarioFormatted = this.costoOrario !== null ? this.sharedService.formatValue(this.costoOrario.toString()) : null;
				this.tipoInvioSelected = this.documento.tipoInvio === "C" ? "1" : "2";
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_AUTOCERTIFICAZIONE_SPESE:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				this.periodo = this.documento.numeroDocumento;
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_MENSILE_TIROCINANTE:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				let periodo = moment();
				periodo.month(this.documento.mese - 1);
				periodo.year(this.documento.anno);
				this.periodoMeseAnno.setValue(periodo);
				this.giorniLavorabiliMensili = this.documento.ggLavorabiliMese;
				this.giorniLavorabiliMensiliFormatted = this.giorniLavorabiliMensili !== null ? this.sharedService.formatValue(this.giorniLavorabiliMensili.toString()) : null;
				this.oreMensiliLavorate = this.documento.oreMeseLavorate;
				this.oreMensiliLavorateFormatted = this.oreMensiliLavorate !== null ? this.sharedService.formatValue(this.oreMensiliLavorate.toString()) : null;
				this.sospensioniBrevi = this.documento.sospBrevi;
				this.sospensioniBreviFormatted = this.sospensioniBrevi !== null ? this.sharedService.formatValue(this.sospensioniBrevi.toString()) : null;
				this.sospensioniLungheGgTotali = this.documento.sospLungheGgTot;
				this.sospensioniLungheGgTotaliFormatted = this.sospensioniLungheGgTotali !== null ? this.sharedService.formatValue(this.sospensioniLungheGgTotali.toString()) : null;
				this.sospensioniLungheGgLav = this.documento.sospLungheGgLav;
				this.sospensioniLungheGgLavFormatted = this.sospensioniLungheGgLav !== null ? this.sharedService.formatValue(this.sospensioniLungheGgLav.toString()) : null;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_CON_QUIETANZA:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				this.tipoInvioSelected = this.documento.tipoInvio === "C" ? "1" : "2";
				this.calcolaTotaleDocumentoRendicontabile();
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_SENZA_QUIETANZA:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				this.tipoInvioSelected = this.documento.tipoInvio === "C" ? "1" : "2";
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_DOCUMENTO_GENERICO:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				this.numero = this.documento.numeroDocumento;
				this.tipoInvioSelected = this.documento.tipoInvio === "C" ? "1" : "2";
				this.documentoSelected = this.documento.flagElettronico ? "1" : "2";
				this.calcolaTotaleDocumentoRendicontabile();
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCI:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_FATTURA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_FATTURA_FIDEIUSSORIA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_FATTURA_LEASING:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_DEBITO:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_PRESTAZIONE_OCCASIONALE:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_QUOTA_AMMORTAMENTO:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_RICEVUTA_LOCAZIONE:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_DIRETTE:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_ATTO_ACQUISTO:
			default:
				this.data.setValue(new Date(this.documento.dataDocumentoDiSpesa));
				this.descrizione = this.documento.descrizioneDocumentoDiSpesa;
				this.imponibile = this.documento.imponibile;
				this.imponibileFormatted = this.imponibile !== null ? this.sharedService.formatValue(this.imponibile.toString()) : null;
				this.importoIva = this.documento.importoIva;
				this.importoIvaFormatted = this.importoIva !== null ? this.sharedService.formatValue(this.importoIva.toString()) : null;
				this.importoIvaIndetraibile = this.documento.importoIvaACosto;
				this.importoIvaIndetraibileFormatted = this.importoIvaIndetraibile !== null ? this.sharedService.formatValue(this.importoIvaIndetraibile.toString()) : null;
				this.rendicontabile = this.documento.importoRendicontabile;
				this.rendicontabileFormatted = this.rendicontabile !== null ? this.sharedService.formatValue(this.rendicontabile.toString()) : null;
				this.calcolaTotaleDocumento();
				this.numero = this.documento.numeroDocumento;
				this.tipoInvioSelected = this.documento.tipoInvio === "C" ? "1" : "2";
				this.documentoSelected = this.documento.flagElettronico ? "1" : "2";
				break;
		}
		this.noteValidatore = this.documento.noteValidazione;
		this.flagElettXml = this.documento.flagElettXml;
	}

	loadDocumento() {
		this.loadedDocumento = false;
		this.subscribers.doc = this.documentoDiSpesaService.getDocumentoDiSpesa(this.idProgetto, this.idDocumentoDiSpesa).subscribe(
			data => {
				if (data) {
					this.documento = data;
					this.loadDati(true);
				}
				this.loadedDocumento = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	loadDati(loadDocumento?: boolean) {
		this.loadedInizializzazione = false;
		this.subscribers.inizializza = this.rendicontazioneService.inizializzaRendicontazione(this.idProgetto, this.idBandoLinea, this.user.idSoggetto, this.user.codiceRuolo).subscribe(
			dataInizializzazione => {
				if (dataInizializzazione) {
					this.isBR42 = dataInizializzazione.allegatiAmmessi;
					this.isBR51 = dataInizializzazione.allegatiAmmessiDocumentoSpesa;
					this.isBR52 = dataInizializzazione.allegatiAmmessiQuietanze;
					this.taskVisibile = dataInizializzazione.taskVisibile;
					this.codiceProgetto = dataInizializzazione.codiceVisualizzatoProgetto;
					this.allegatiAmmessi = dataInizializzazione.allegatiAmmessi;
					this.causaleQuietanzaVisibile = dataInizializzazione.causalePagamentoVisible;
					this.idProcessoBandoLinea = dataInizializzazione.idProcessoBandoLinea;
					this.idTipoOperazioneProgetto = dataInizializzazione.idTipoOperazioneProgetto;
					this.utenteAbilitatoAdAssociarePagamenti = dataInizializzazione.utenteAbilitatoAdAssociarePagamenti;
					this.utenteAbilitatoAdAssociareVociDiSpesa = dataInizializzazione.utenteAbilitatoAdAssociareVociDiSpesa;
					if (this.taskVisibile) {
						this.loadedTasks = false;
						this.subscribers.elencoTask = this.rendicontazioneService.getElencoTask(this.user, this.idProgetto).subscribe(
							dataTask => {
								if (dataTask) {
									this.tasks = dataTask;
									if (this.idDocumentoDiSpesa && this.documento.task) {
										this.taskSelected = this.documento.task;
									}
								}
								this.loadedTasks = true;
							},
							err => {
								this.handleExceptionService.handleBlockingError(err);
							},
						);
					}
					if (!this.activatedRoute.snapshot.paramMap.get('integrativa') && dataInizializzazione.solaVisualizzazione.esito) {
						this.isReadOnly = true;
					}
				}
				this.isBR58 = dataInizializzazione.isBR58;
				if (this.isBR58) {
					this.loadSalCorrente();
				}


				this.showQualificheTable = false;

				this.todayDate = new Date();
				this.todayDate.setHours(0, 0, 0, 0);

				this.loadedTipologieDocSpesa = false;

				if (this.isBandoCultura) {
					this.subscribers.tipoDocSpesa = this.rendicontazioneService.findTipologieDocumentiDiSpesaCultura(this.idBandoLinea.toString(), this.idProgetto.toString()).subscribe(
						data => {
							if (data) {
								this.subscribers.tipoBeneficiario = this.documentoDiSpesaService.getTipoBeneficiario(this.user.idSoggetto, this.idProgetto).subscribe(data2 => {
									if (data2 == 2) {
										this.visualizzaInCulturaPubblico = true;
										this.tipoBeneficiario = 2;
										this.tipologie = data.filter(tipologia => tipologia.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_ATTO_DI_LIQUIDAZIONE);
									} else {
										this.tipologie = data.filter(tipologia => tipologia.descBreveTipoDocSpesa !== Constants.DESC_BREVE_TIPO_DOC_SPESA_ATTO_DI_LIQUIDAZIONE);
									}
									this.setDatiTipoDoc(loadDocumento);
									this.loadedTipologieDocSpesa = true;
								}, err => {
									this.handleExceptionService.handleBlockingError(err);
								});
							}
						}
					);
				}
				else {
					this.subscribers.tipoDocSpesa = this.rendicontazioneService.findTipologieDocumentiDiSpesa(this.idBandoLinea.toString()).subscribe(
						data => {
							if (data) {
								this.tipologie = data.filter(tipologia => tipologia.descBreveTipoDocSpesa !== Constants.DESC_BREVE_TIPO_DOC_SPESA_ATTO_DI_LIQUIDAZIONE);
								this.setDatiTipoDoc(loadDocumento);
								this.loadedTipologieDocSpesa = true;
							}
						}
					);
				}
				this.loadedInizializzazione = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			}
		);
		this.loadedAttivita = false;
		this.subscribers.home = this.rendicontazioneService.getAttivita(this.user.idUtente).subscribe(
			data => {
				if (data) {
					this.attivitas = data;
					this.attivitas.unshift(new DecodificaDTO(0, '', ''));
					if (this.idDocumentoDiSpesa && this.documento.idTipoOggettoAttivita) {
						this.attivitaSelected = this.attivitas.find(t => t.id === this.documento.idTipoOggettoAttivita);
					}
				}
				this.loadedAttivita = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
		this.loadedQualifiche = false;
		this.rendicontazioneService.getQualifiche(this.user).subscribe(
			data => {
				if (data) {
					this.qualifiche = data;
				}
				this.loadedQualifiche = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	setDatiTipoDoc(loadDocumento?: boolean) {
		if (this.idDocumentoDiSpesa) {
			this.tipologiaSelected = this.tipologie.find(t => t.descBreveTipoDocSpesa === this.documento.descBreveTipoDocumentoDiSpesa);
			if (!this.tipologiaSelected) {
				throw new Error("Tipologia documento non trovata");
			}
			if (this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA) {
				if (
					this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO ||
					this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD
				) {
					this.showMessageWarning(
						"Dopo aver salvato un cedolino, non è possibile modificare alcuni dati (campi disabilitati).<br> Se necessario, eliminare il cedolino dall'elenco documenti e procedere ad un nuovo inserimento.",
					);
				} else if (this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE_COSTI_SEMPLIFICATI) {
					this.showMessageWarning(
						"Dopo aver salvato delle spese generali a costi semplificati, non è possibile modificare alcuni dati (campi disabilitati)..<br> Se necessario, eliminare il documento dall'elenco documenti e procedere ad un nuovo inserimento.",
					);
				} else if (this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_AUTOCERTIFICAZIONE_SPESE) {
					this.showMessageWarning(
						"Dopo aver salvato un'autocertificazione spese, non è possibile modificare alcuni dati (campi disabilitati).<br> Se necessario, eliminare il documento dall'elenco documenti e procedere ad un nuovo inserimento.",
					);
				} else if (this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_SENZA_QUIETANZA) {
					this.showMessageWarning(
						"Dopo aver salvato uno stato avanzamento lavori senza quietanza, non è possibile modificare alcuni dati (campi disabilitati).<br> Se necessario, eliminare il documento dall'elenco documenti e procedere ad un nuovo inserimento.",
					);
				} else if (this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_MENSILE_TIROCINANTE) {
					this.showMessageWarning("Dopo aver salvato un compenso mensile tirocinante, non è possibile modificare alcuni dati (campi disabilitati).<br> Se necessario, eliminare il documento dall'elenco documenti e procedere ad un nuovo inserimento.");
				} else if (this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_IMPRESA_ARTIGIANA) {
					this.showMessageWarning(
						"Dopo aver salvato un compenso impresa artigiana, non è possibile modificare alcuni dati (campi disabilitati)..<br> Se necessario, eliminare il documento dall'elenco documenti e procedere ad un nuovo inserimento.",
					);
				} else if (this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_SOGGETTO_GESTORE) {
					this.showMessageWarning(
						"Dopo aver salvato un compenso soggetto gestore, non è possibile modificare alcuni dati (campi disabilitati)..<br> Se necessario, eliminare il documento dall'elenco documenti e procedere ad un nuovo inserimento.",
					);
				}
			}
			this.changeTipoDocumento(true);
			if (loadDocumento) {
				this.setCampiFromDocumento();
				if (
					this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA ||
					this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO ||
					this.tipoOperazioneFrom === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_VALIDAZIONE
				) {
					this.calcolaResiduoRendicontabile();
					this.loadDatiModifica();
				}
			}
			if (this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA) {
				this.numero = null;
				this.periodo = null;
				this.data = new FormControl();
				this.calcolaResiduoRendicontabile();
				this.calcolaResiduoQuietanzabile();
			}


		}
	}

	loadDatiModifica() {
		this.loadAllegatiDocSpesa();
		this.loadedMacroVoci = false;
		this.subscribers.macroVoci = this.documentoDiSpesaService.getMacroVociDiSpesa(this.idDocumentoDiSpesa, this.idProgetto, this.user.idUtente).subscribe(
			data => {
				if (data) {
					this.macroVoci = data;
					this.loadElencoVociSpesa(true);
				}
				this.loadedMacroVoci = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);

		if (this.documento.descBreveTipoDocumentoDiSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_FATTURA) {
			this.loadedNoteCreditoFattura = false;
			this.subscribers.noteCredito = this.documentoDiSpesaService.getNoteDiCreditoFattura(this.idProgetto, this.idDocumentoDiSpesa).subscribe(data => {
				if (data) {
					this.noteDiCreditoFattura = data;
					this.dataSourceNoteDiCreditoFattura = new MatTableDataSource<DocumentoDiSpesaDTO>();
					this.dataSourceNoteDiCreditoFattura.data = this.noteDiCreditoFattura;
					this.totaleNoteCredito = 0;
					this.noteDiCreditoFattura.forEach(n => this.totaleNoteCredito += n.importoTotaleDocumentoIvato);
				}
				this.loadElencoQuietanze();
				this.loadedNoteCreditoFattura = true;
			}, err => {
				this.handleExceptionService.handleBlockingError(err);
			});
		} else if (this.documento.descBreveTipoDocumentoDiSpesa !== Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_CREDITO
			&& this.documento.descBreveTipoDocumentoDiSpesa !== Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD
			&& this.documento.descBreveTipoDocumentoDiSpesa !== Constants.DESC_BREVE_TIPO_DOC_SPESA_AUTOCERTIFICAZIONE_SPESE
			&& this.documento.descBreveTipoDocumentoDiSpesa !== Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_SENZA_QUIETANZA
			&& this.documento.descBreveTipoDocumentoDiSpesa !== Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE_COSTI_SEMPLIFICATI
			&& this.documento.descBreveTipoDocumentoDiSpesa !== Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_MENSILE_TIROCINANTE
			&& this.documento.descBreveTipoDocumentoDiSpesa !== Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_IMPRESA_ARTIGIANA
			&& this.documento.descBreveTipoDocumentoDiSpesa !== Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_SOGGETTO_GESTORE) {
			this.loadElencoQuietanze();
		}
		this.loadedModalitaQuietanza = false;
		this.subscribers.modalitaQuietanza = this.documentoDiSpesaService.getModalitaQuietanza(this.idDocumentoDiSpesa, this.idProgetto, this.user.idUtente).subscribe(data => {
			if (data) {
				this.modalitaQuitanza = data;
			}
			this.loadedModalitaQuietanza = true;
		}, err => {
			this.handleExceptionService.handleBlockingError(err);
		});
		this.loadedCausaliQuietanza = false;
		this.subscribers.causaliQuietanza = this.documentoDiSpesaService.getCausaliQuietanza().subscribe(data => {
			if (data) {
				this.causaliQuietanza = data;
			}
			this.loadedCausaliQuietanza = true;
		}, err => {
			this.handleExceptionService.handleBlockingError(err);
		});
	}

	loadSalCorrente() {
		if (this.idDocumentoDiSpesa && this.documento.descBreveStatoDocumentoSpesa !== Constants.DESC_BREVE_STATO_DOC_SPESA_DA_COMPLETARE
			&& this.documento.descBreveStatoDocumentoSpesa !== Constants.DESC_BREVE_STATO_DOC_SPESA_DICHIARABILE) {
			this.loadedSalCorrente = false;
			this.rendicontazioneService.getSALByIdDocSpesa(this.idProgetto, this.idDocumentoDiSpesa).subscribe(
				data => {
					if (data) {
						this.salCorrente = data;
						this.loadQuotaImportAgevolato();
					}
					this.loadedSalCorrente = true;
				},
				err => {
					this.handleExceptionService.handleBlockingError(err);
				},
			);
		} else {
			this.loadedSalCorrente = false;
			this.rendicontazioneService.getSALCorrente(this.idProgetto).subscribe(
				data => {
					if (data) {
						this.salCorrente = data;
						this.loadQuotaImportAgevolato();
					}
					this.loadedSalCorrente = true;
				},
				err => {
					this.handleExceptionService.handleBlockingError(err);
				},
			);
		}
	}

	loadQuotaImportAgevolato() {
		this.loadedQuotaImportAgevolato = false;
		this.documentoDiSpesaService.getQuotaImportoAgevolato(this.idProgetto).subscribe(
			data => {
				if (data) {
					this.quotaImportoAgevolato = data;
					// if (this.salCorrente.ultimoIter) {
					// 	this.loadImportoASaldo();
					// }
				}
				this.loadedQuotaImportAgevolato = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	warningBloccante: boolean;
	esitoImportoASaldo: EsitoImportoSaldoDTO;
	loadImportoASaldo() {
		this.loadedImportoASaldo = false;
		this.documentoDiSpesaService.getImportoASaldo(this.idProgetto).subscribe(
			data => {
				if (data) {
					this.esitoImportoASaldo = data;
					if (data.esito) {
						this.importoASaldo = data.importoSpeso - data.sommaErogato;
					}
				}
				this.loadedImportoASaldo = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	loadAllegatiDocSpesa() {
		this.loadedAllegatiDocSpesa = false;
		//sostituire flagElettronico con quello del documento
		this.subscribers.allegatiDocSpesa = this.documentoDiSpesaService.getAllegatiDocumentoDiSpesa(this.idDocumentoDiSpesa, this.idProgetto, 'N', this.user.idUtente).subscribe(
			data => {
				if (data) {
					this.allegatiDocumentoDiSpesa = data;
				} else {
					this.allegatiDocumentoDiSpesa = null;
				}
				this.loadedAllegatiDocSpesa = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	loadElencoVociSpesa(isInit?: boolean) {
		this.loadedVociDiSpesa = false;
		if (this.tipoOperazioneFrom !== Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_VALIDAZIONE) {
			this.subscribers.voci = this.documentoDiSpesaService
				.getVociDiSpesaAssociateRendicontazione(
					this.idDocumentoDiSpesa,
					this.idProgetto,
					this.user.idSoggetto,
					this.user.codiceRuolo,
					this.tipoOperazioneCurrent,
					this.documento.descBreveStatoDocumentoSpesa,
					this.user.idUtente,
				)
				.subscribe(
					data => {
						if (data) {
							this.dataSourceVociSpesa = new MatTableDataSource<VoceDiSpesaDTO>();
							this.dataSourceVociSpesa.data = data;
							this.totaleRendicontato = 0;
							this.dataSourceVociSpesa.data.forEach(v => (this.totaleRendicontato += v.importo));
							this.calcolaResiduoRendicontabile();
							//se arrivo da un inserimento o clonazione e se esiste un'unica macrovoce senza voci figlie, allora la aggiungo automaticamente
							if (
								isInit &&
								this.macroVoci.length === 1 &&
								(!this.macroVoci[0].vociDiSpesaFiglie ||
									(!this.macroVoci[0].vociDiSpesaFiglie.length &&
										!data.length &&
										this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA &&
										(this.tipoOperazioneFrom === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_INSERISCI || this.tipoOperazioneFrom === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA)))
							) {
								this.loadedSave = false;
								let voceDTO = new VoceDiSpesaDTO(
									this.macroVoci[0].idVoceDiSpesa,
									this.macroVoci[0].idRigoContoEconomico,
									this.idProgetto,
									null,
									this.idDocumentoDiSpesa,
									this.residuoRendicontabile,
									this.macroVoci[0].descVoceDiSpesa,
									null,
									null,
									this.macroVoci[0].importoAmmessoFinanziamento,
									this.macroVoci[0].idVoceDiSpesa,
									this.oreLavorate,
									this.costoOrario,
									this.tipologiaSelected.idTipoDocumentoSpesa,
									null,
									null,
									this.macroVoci[0].importoResiduoAmmesso,
									this.macroVoci[0].descVoceDiSpesa,
									null,
									null,
								);
								this.subscribers.salva = this.documentoDiSpesaService.associaVoceDiSpesa(voceDTO, this.user.idUtente).subscribe(
									data => {
										if (data) {
											if (!data.esito) {
												this.showMessageError(data.messaggio);
											}
										}
										this.loadElencoVociSpesa();
										this.loadedSave = true;
									},
									err => {
										this.handleExceptionService.handleNotBlockingError(err);
										this.loadedSave = true;
										this.showMessageError('Errore nella creazione della voce di spesa.');
									},
								);
							}
						}
						this.loadedVociDiSpesa = true;
					},
					err => {
						this.handleExceptionService.handleBlockingError(err);
					},
				);
		} else {
			this.subscribers.voci = this.documentoDiSpesaService
				.getVociDiSpesaAssociateValidazione(
					this.idDocumentoDiSpesa,
					this.idProgetto,
					this.user.idSoggetto,
					this.user.codiceRuolo,
					Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA,
					this.documento.descBreveStatoDocumentoSpesa,
					this.user.idUtente,
				)
				.subscribe(
					data => {
						if (data) {
							this.dataSourceVociSpesa = new MatTableDataSource<VoceDiSpesaDTO>();
							this.dataSourceVociSpesa.data = data;
							this.totaleRendicontato = 0;
							this.dataSourceVociSpesa.data.forEach(v => (this.totaleRendicontato += v.importo));
							this.calcolaResiduoRendicontabile();
						}
						this.loadedVociDiSpesa = true;
					},
					err => {
						this.handleExceptionService.handleBlockingError(err);
					},
				);
		}
	}

	loadElencoQuietanze() {
		this.loadedQuietanze = false;
		let request = new PagamentiAssociatiRequest(
			this.idDocumentoDiSpesa,
			this.tipoInvioSelected === '1' ? 'C' : 'D',
			this.documento.descBreveStatoDocumentoSpesa,
			this.tipoOperazioneFrom === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_VALIDAZIONE ? Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA : this.tipoOperazioneCurrent,
			this.idProgetto,
			this.idBandoLinea,
			this.user.codiceRuolo,
			this.isValidazione,
		);
		this.subscribers.quietanze = this.documentoDiSpesaService.getQuietanzeAssociate(request).subscribe(
			data => {
				if (data) {
					this.subscribers.quietanze = this.documentoDiSpesaService.getGgScadenzaQuietanza(this.idBandoLinea).subscribe(
						data => {
							if (data) {
								this.ggQuietanza = data;
							}
						},
						err => {
							this.handleExceptionService.handleBlockingError(err);
						},
					);
					this.dataSourceQuietanziato = new MatTableDataSource<DocumentoDiPagamentoDTO>();
					this.dataSourceQuietanziato.data = data;
					this.totaleQuietanzato = 0;
					this.dataSourceQuietanziato.data.forEach(q => (this.totaleQuietanzato += q.importoPagamento));
					this.displayedColumnsQuietanziato = [];
					this.displayedColumnsQuietanziato.push(...this.displayedColumnsQuietanziatoDefault);
					if (this.BR42 || this.BR52) {
						this.displayedColumnsQuietanziato.push('allegati', 'azioni');
					} else {
						this.displayedColumnsQuietanziato.push('azioni');
					}
					this.rendicontatoQuietanzato = this.totaleQuietanzato < this.totaleRendicontato ? this.totaleQuietanzato : this.totaleRendicontato;
					this.calcolaResiduoQuietanzabile();
				}
				this.loadedQuietanze = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	calcolaResiduoRendicontabile() {
		this.residuoRendicontabile = (this.rendicontabile ? this.rendicontabile : this.importoRendicontabile ? this.importoRendicontabile : this.importo ? this.importo : 0) - this.totaleRendicontato;
		this.residuoRendicontabile = +Number(this.residuoRendicontabile).toFixed(2);
	}

	calcolaResiduoQuietanzabile() {
		let quietanzatoConNoteDiCredito: number = 0;
		if (this.totaleQuietanzato !== 0) {
			quietanzatoConNoteDiCredito = this.totaleQuietanzato;
		}
		if (this.totaleNoteCredito) {
			quietanzatoConNoteDiCredito += this.totaleNoteCredito;
		}
		this.residuoQuietanzabile = this.totaleDocumento - quietanzatoConNoteDiCredito;
		this.residuoQuietanzabile = +Number(this.residuoQuietanzabile).toFixed(2);
	}

	chosenYearHandler(normalizedYear: Moment) {
		const ctrlValue = this.periodoMeseAnno.value || moment();
		ctrlValue.year(normalizedYear.year());
		this.periodoMeseAnno.setValue(ctrlValue);
	}

	chosenMonthHandler(normalizedMonth: Moment, datepicker: MatDatepicker<Moment>) {
		const ctrlValue = this.periodoMeseAnno.value;
		ctrlValue.month(normalizedMonth.month());
		this.periodoMeseAnno.setValue(ctrlValue);
		datepicker.close();
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
		if (this.idDocumentoDiSpesa) {
			if (this.tipoOperazioneFrom) {
				let params = this.activatedRoute.snapshot.paramMap.get('integrativa')
					? { da: 'documento', idDoc: this.idDocumentoDiSpesa, from: this.tipoOperazioneFrom, current: this.tipoOperazioneCurrent, integrativa: true }
					: { da: 'documento', idDoc: this.idDocumentoDiSpesa, from: this.tipoOperazioneFrom, current: this.tipoOperazioneCurrent };
				this.router.navigate(['drawer/' + this.idRouting + '/gestioneFornitori/' + this.idProgetto + '/' + this.idBandoLinea, params]);
			} else {
				let params = this.activatedRoute.snapshot.paramMap.get('integrativa')
					? { da: 'documento', idDoc: this.idDocumentoDiSpesa, current: this.tipoOperazioneCurrent, integrativa: true }
					: { da: 'documento', idDoc: this.idDocumentoDiSpesa, current: this.tipoOperazioneCurrent };
				this.router.navigate(['drawer/' + this.idRouting + '/gestioneFornitori/' + this.idProgetto + '/' + this.idBandoLinea, params]);
			}
		} else {
			let params = this.activatedRoute.snapshot.paramMap.get('integrativa') ? { da: 'documento', integrativa: true } : { da: 'documento' };
			this.router.navigate(['drawer/' + this.idRouting + '/gestioneFornitori/' + this.idProgetto + '/' + this.idBandoLinea, params]);
		}
	}

	tornaAlleRendicontazioni() {
		if (this.isBandoCultura) {
			this.router.navigate([
				'drawer/' +
				Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE_CULTURA +
				'/rendicontazione-a20/' +
				(this.activatedRoute.snapshot.paramMap.get('integrativa') ? 'integrativa/' : '') +
				this.idProgetto +
				'/' +
				this.idBandoLinea,
			]);
		} else {
			this.router.navigate([
				'drawer/' +
				Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE +
				'/rendicontazione/' +
				(this.activatedRoute.snapshot.paramMap.get('integrativa') ? 'integrativa/' : '') +
				this.idProgetto +
				'/' +
				this.idBandoLinea,
			]);
		}
	}

	tornaAInvioDichiarazione() {
		let url = `drawer/${this.idRouting}/dichiarazioneDiSpesa/${this.idProgetto}/${this.idBandoLinea}`;
		url += this.isIntegrativa ? ';integrativa=true' : '';
		this.router.navigateByUrl(url);
	}

	tornaAValidazione() {
		let idDichiarazioneDiSpesa = +this.activatedRoute.snapshot.paramMap.get('idDichiarazioneDiSpesa');
		this.router.navigateByUrl(`drawer/${this.idOperazioneValidazione}/validazione/${this.idProgetto}/${this.idBandoLinea}/${idDichiarazioneDiSpesa}`);
	}

	nuovoDocumentoDiSpesa() {
		let url = `drawer/${this.idRouting}/documentoDiSpesa/${this.idProgetto}/${this.idBandoLinea}`;
		url += this.isIntegrativa ? ';integrativa=true' : '';
		this.router.navigateByUrl(url);
	}

	hasValidationError: boolean;
	todayDate: Date;

	controlRequiredRicercaForm(name: string) {
		if (this.ricercaForm.form.get(name) && !this.ricercaForm.form.get(name).value) {
			this.ricercaForm.form.get(name).setErrors({ error: 'required' });
			this.hasValidationError = true;
		}
	}

	controlNumberLengthRicercaForm(name: string, min: number, max: number) {
		if (this.ricercaForm.form.get(name) && this.ricercaForm.form.get(name).value) {
			let s: string = this.ricercaForm.form.get(name).value.toString();
			let length: number;
			s = s.replace(/\./g, '');
			s = s.replace(',', '');
			if (s.includes('e')) {
				let n = s.split('e');
				length = +n[1] + 1;
			} else {
				length = s.length;
			}
			if (length < min || length > max) {
				this.ricercaForm.form.get(name).setErrors({ error: 'wrongLength' });
				this.hasValidationError = true;
			}
		}
	}

	controlNumberPositiveRicercaForm(name: string, positiveZero?: boolean) {
		if (this.ricercaForm.form.get(name) && this.ricercaForm.form.get(name).value !== undefined && this.ricercaForm.form.get(name).value !== null) {
			let value: number;
			if (typeof this.ricercaForm.form.get(name).value === 'string') {
				value = this.sharedService.getNumberFromFormattedString(this.ricercaForm.form.get(name).value);
			} else {
				value = this.ricercaForm.form.get(name).value;
			}
			if (value < 0 && !positiveZero) {
				this.ricercaForm.form.get(name).setErrors({ error: 'negative' });
				this.hasValidationError = true;
			} else if (value <= 0 && positiveZero) {
				this.ricercaForm.form.get(name).setErrors({ error: 'negativeOrZero' });
				this.hasValidationError = true;
			}
		}
	}

	/*
	data<today
	numero.lenghth tra 1 e 70 compresi
	descrizione.length tra 1 e 200 compresi
	imponibile >0 e .lenght tra 1 e 17 compresi
	rendicontabile > 0 e .lenght tra 1 e 17 compresi
	iva >0 e .lenght tra 1 e 17 compresi
	iva a costo(?) >0 e .lenght tra 1 e 17 compresi
	importo totale documento >0 e .lenght tra 1 e 17 compresi
	
	//SOLO NOTA SPESE
	durata trasferta >0 e .lenght tra 1 e 4 compresi
	
	SOLO CEDOLINO - CEDOLINO COSTI STANDARD
	ore lavorate > 0 e != 0
	*/

	validazioneCampi() {
		this.hasValidationError = false;
		if (!this.data || !this.data.value) {
			this.data.setErrors({ error: 'required' });
			this.hasValidationError = true;
		} else if (this.data && this.data.value && new Date(this.data.value) > this.todayDate) {
			this.data.setErrors({ error: 'maggioreToday' });
			this.hasValidationError = true;
		}
		switch (this.tipologiaSelected.descBreveTipoDocSpesa) {
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO:
				this.controlRequiredRicercaForm('numero');
				this.controlNumberLengthRicercaForm('numero', 1, 70);
				this.controlRequiredRicercaForm('descrizione');
				this.controlRequiredRicercaForm('tipologiaFornitore');
				this.controlRequiredRicercaForm('fornitore');
				this.controlRequiredRicercaForm('imponibileRitAcc');
				this.controlNumberLengthRicercaForm('imponibileRitAcc', 1, 17);
				this.controlNumberPositiveRicercaForm('imponibileRitAcc');
				this.controlNumberPositiveRicercaForm('oreLavorate');
				this.controlRequiredRicercaForm('rendicontabile');
				this.controlNumberLengthRicercaForm('rendicontabile', 1, 17);
				this.controlNumberPositiveRicercaForm('rendicontabile', true);
				this.controlNumberLengthRicercaForm('totaleDocumento', 1, 17);
				this.controlNumberPositiveRicercaForm('totaleDocumento');
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_A_PROGETTO:
				this.controlRequiredRicercaForm('numero');
				this.controlNumberLengthRicercaForm('numero', 1, 70);
				this.controlRequiredRicercaForm('descrizione');
				this.controlRequiredRicercaForm('tipologiaFornitore');
				this.controlRequiredRicercaForm('fornitore');
				if (this.idProcessoBandoLinea === 2 && (this.idTipoOperazioneProgetto === 1 || this.idTipoOperazioneProgetto === 2)) {
					this.controlRequiredRicercaForm('affidamento');
				}
				this.controlRequiredRicercaForm('imponibileRitAcc');
				this.controlNumberLengthRicercaForm('imponibileRitAcc', 1, 17);
				this.controlNumberPositiveRicercaForm('imponibileRitAcc');
				this.controlRequiredRicercaForm('rendicontabile');
				this.controlNumberLengthRicercaForm('rendicontabile', 1, 17);
				this.controlNumberPositiveRicercaForm('rendicontabile', true);
				this.controlNumberLengthRicercaForm('totaleDocumento', 1, 17);
				this.controlNumberPositiveRicercaForm('totaleDocumento');
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_SPESE:
				this.controlNumberLengthRicercaForm('durataTrasferta', 0, 4);
				this.controlNumberPositiveRicercaForm('durataTrasferta');
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_CREDITO:
				this.controlRequiredRicercaForm('numero');
				this.controlNumberLengthRicercaForm('numero', 1, 70);
				this.controlRequiredRicercaForm('descrizione');
				this.controlRequiredRicercaForm('tipologiaFornitore');
				this.controlRequiredRicercaForm('fornitore');
				if (this.idProcessoBandoLinea === 2 && (this.idTipoOperazioneProgetto === 1 || this.idTipoOperazioneProgetto === 2)) {
					this.controlRequiredRicercaForm('affidamento');
				}
				this.controlRequiredRicercaForm('riferimentoFatturaSelected');
				this.controlRequiredRicercaForm('imponibile');
				this.controlNumberLengthRicercaForm('imponibile', 1, 17);
				this.controlNumberPositiveRicercaForm('imponibile');
				this.controlNumberLengthRicercaForm('impIva', 1, 17);
				this.controlNumberPositiveRicercaForm('impIva');
				this.controlNumberLengthRicercaForm('impIvaInd', 1, 17);
				this.controlNumberPositiveRicercaForm('impIvaInd');
				this.controlNumberLengthRicercaForm('totaleDocumento', 1, 17);
				this.controlNumberPositiveRicercaForm('totaleDocumento');
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE:
				this.controlRequiredRicercaForm('importoRendicontabile');
				this.controlNumberLengthRicercaForm('importoRendicontabile', 1, 17);
				this.controlNumberPositiveRicercaForm('importoRendicontabile', true);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE_COSTI_SEMPLIFICATI:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_IMPRESA_ARTIGIANA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_SOGGETTO_GESTORE:
				this.controlRequiredRicercaForm('periodo');
				this.controlNumberLengthRicercaForm('periodo', 1, 70);
				this.controlRequiredRicercaForm('importo');
				this.controlNumberLengthRicercaForm('importo', 1, 17);
				this.controlNumberPositiveRicercaForm('importo');
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD:
				this.controlRequiredRicercaForm('periodo');
				this.controlNumberLengthRicercaForm('periodo', 1, 70);
				this.controlRequiredRicercaForm('descrizione');
				this.controlRequiredRicercaForm('tipologiaFornitore');
				this.controlRequiredRicercaForm('fornitore');
				this.controlNumberPositiveRicercaForm('oreLavorate');
				this.controlRequiredRicercaForm('rendicontabile');
				this.controlNumberLengthRicercaForm('rendicontabile', 1, 17);
				this.controlNumberPositiveRicercaForm('rendicontabile', true);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_AUTOCERTIFICAZIONE_SPESE:
				this.controlRequiredRicercaForm('periodo');
				this.controlNumberLengthRicercaForm('periodo', 1, 70);
				this.controlRequiredRicercaForm('descrizione');
				this.controlRequiredRicercaForm('tipologiaFornitore');
				this.controlRequiredRicercaForm('fornitore');
				this.controlRequiredRicercaForm('rendicontabile');
				this.controlNumberLengthRicercaForm('rendicontabile', 1, 17);
				this.controlNumberPositiveRicercaForm('rendicontabile', true);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_MENSILE_TIROCINANTE:
				if (!this.periodoMeseAnno || !this.periodoMeseAnno.value) {
					this.periodoMeseAnno.setErrors({ error: 'required' });
					this.hasValidationError = true;
				}
				this.controlRequiredRicercaForm("descrizione");
				this.controlRequiredRicercaForm("tipologiaFornitore");
				this.controlRequiredRicercaForm("fornitore");
				this.controlRequiredRicercaForm("categoria");
				this.controlRequiredRicercaForm("giorniLavMens");
				this.controlRequiredRicercaForm("oreMensLav");
				this.controlRequiredRicercaForm("sospBrevi");
				this.controlRequiredRicercaForm("sospLungheGgTotali");
				this.controlRequiredRicercaForm("sospLungheGgLav");
				this.controlRequiredRicercaForm("rendicontabile");
				this.controlNumberLengthRicercaForm("rendicontabile", 1, 17);
				this.controlNumberPositiveRicercaForm("rendicontabile", true);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_CON_QUIETANZA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_SENZA_QUIETANZA:
				this.controlRequiredRicercaForm('descrizione');
				this.controlRequiredRicercaForm('rendicontabile');
				this.controlNumberLengthRicercaForm('rendicontabile', 1, 17);
				this.controlNumberPositiveRicercaForm('rendicontabile', true);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_DOCUMENTO_GENERICO:
				this.controlRequiredRicercaForm('numero');
				this.controlNumberLengthRicercaForm('numero', 1, 70);
				this.controlRequiredRicercaForm('descrizione');
				this.controlRequiredRicercaForm('rendicontabile');
				this.controlNumberLengthRicercaForm('rendicontabile', 1, 17);
				this.controlNumberPositiveRicercaForm('rendicontabile', true);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_ATTO_DI_LIQUIDAZIONE:
				if (this.isBandoCultura && this.tipoBeneficiario == 2) {
					this.controlRequiredRicercaForm('numero');
					this.controlNumberLengthRicercaForm('numero', 1, 70);
					this.controlRequiredRicercaForm('descrizione');
					if (this.idProcessoBandoLinea === 2 && (this.idTipoOperazioneProgetto === 1 || this.idTipoOperazioneProgetto === 2)) {
						this.controlRequiredRicercaForm('affidamento');
					}
					this.controlRequiredRicercaForm('rendicontabile');
					this.controlNumberLengthRicercaForm('rendicontabile', 1, 17);
					this.controlNumberPositiveRicercaForm('rendicontabile', true);
				} else {
					this.controlRequiredRicercaForm('numero');
					this.controlNumberLengthRicercaForm('numero', 1, 70);
					this.controlRequiredRicercaForm('descrizione');
					this.controlRequiredRicercaForm('tipologiaFornitore');
					this.controlRequiredRicercaForm('fornitore');
					if (this.idProcessoBandoLinea === 2 && (this.idTipoOperazioneProgetto === 1 || this.idTipoOperazioneProgetto === 2)) {
						this.controlRequiredRicercaForm('affidamento');
					}
					this.controlRequiredRicercaForm('imponibile');
					this.controlNumberLengthRicercaForm('imponibile', 1, 17);
					this.controlNumberPositiveRicercaForm('imponibile');
					this.controlRequiredRicercaForm('rendicontabile');
					this.controlNumberLengthRicercaForm('rendicontabile', 1, 17);
					this.controlNumberPositiveRicercaForm('rendicontabile', true);
					this.controlNumberLengthRicercaForm('impIva', 1, 17);
					this.controlNumberPositiveRicercaForm('impIva');
					this.controlNumberLengthRicercaForm('impIvaInd', 1, 17);
					this.controlNumberPositiveRicercaForm('impIvaInd');
					this.controlNumberLengthRicercaForm('totaleDocumento', 1, 17);
					this.controlNumberPositiveRicercaForm('totaleDocumento');
				}
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCI:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_FATTURA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_FATTURA_FIDEIUSSORIA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_FATTURA_LEASING:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_DEBITO:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_PRESTAZIONE_OCCASIONALE:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_QUOTA_AMMORTAMENTO:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_RICEVUTA_LOCAZIONE:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_DIRETTE:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_ATTO_ACQUISTO:
			default:
				this.controlRequiredRicercaForm('numero');
				this.controlNumberLengthRicercaForm('numero', 1, 70);
				this.controlRequiredRicercaForm('descrizione');
				this.controlRequiredRicercaForm('tipologiaFornitore');
				this.controlRequiredRicercaForm('fornitore');
				if (this.idProcessoBandoLinea === 2 && (this.idTipoOperazioneProgetto === 1 || this.idTipoOperazioneProgetto === 2)) {
					this.controlRequiredRicercaForm('affidamento');
				}
				this.controlRequiredRicercaForm('imponibile');
				this.controlNumberLengthRicercaForm('imponibile', 1, 17);
				this.controlNumberPositiveRicercaForm('imponibile');
				this.controlRequiredRicercaForm('rendicontabile');
				this.controlNumberLengthRicercaForm('rendicontabile', 1, 17);
				this.controlNumberPositiveRicercaForm('rendicontabile', true);
				this.controlNumberLengthRicercaForm('impIva', 1, 17);
				this.controlNumberPositiveRicercaForm('impIva');
				this.controlNumberLengthRicercaForm('impIvaInd', 1, 17);
				this.controlNumberPositiveRicercaForm('impIvaInd');
				this.controlNumberLengthRicercaForm('totaleDocumento', 1, 17);
				this.controlNumberPositiveRicercaForm('totaleDocumento');
				break;
		}
		if (this.hasValidationError) {
			this.showMessageError('ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.');
		}
	}

	setDocumento() {
		let documento: DocumentoDiSpesaDTO;
		switch (this.tipologiaSelected.descBreveTipoDocSpesa) {
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, null, null, null, this.tipologiaSelected.descTipoDocumentoSpesa,
					this.tipologiaSelected.descBreveTipoDocSpesa, this.tipoFornitoreSelected.descrizione, null, this.user.beneficiarioSelezionato.idBeneficiario, null, null,
					this.fornitoreSelected.idFornitore, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null, this.tipologiaSelected.idTipoDocumentoSpesa, this.tipoFornitoreSelected.id, null, this.imponibileRitAcc,
					null, null, this.rendicontabile, this.totaleDocumento, null, null, null, null, null, null, this.numero, null, null, null, null, this.costoOrario,
					this.taskVisibile && this.taskSelected ? this.taskSelected : null, null, null, this.dataSourceQualifiche.data.find(q => q.comodoIdTabella === this.radioSelected).progrFornitoreQualifica,
					null, null, null, this.noteValidatore ? this.noteValidatore : null, null, this.tipoInvioSelected === "1" ? "C" : "D", this.documentoSelected === "1" ? true : false, null, null, this.flagElettXml,
					null, null, null, null, null, null, null, null);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_A_PROGETTO:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, null, null, null, this.tipologiaSelected.descTipoDocumentoSpesa,
					this.tipologiaSelected.descBreveTipoDocSpesa, this.tipoFornitoreSelected.descrizione, null, this.user.beneficiarioSelezionato.idBeneficiario, null, null,
					this.fornitoreSelected.idFornitore, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null, this.tipologiaSelected.idTipoDocumentoSpesa, this.tipoFornitoreSelected.id, null, this.imponibileRitAcc,
					null, null, this.rendicontabile, this.totaleDocumento, null, null, null, null, null, null, this.numero, null, null, null, null, null,
					this.taskVisibile && this.taskSelected ? this.taskSelected : null, null, null, null, null, null, null, this.noteValidatore ? this.noteValidatore : null, null,
					this.tipoInvioSelected === "1" ? "C" : "D", this.documentoSelected === "1" ? true : false, this.idAppaltocigCpaAffidamentoData, this.cigCpaAffidamentoData, this.flagElettXml,
					null, null, null, null, null, null, null, null);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_SPESE:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, this.destinazioneTrasferta, null, null,
					this.tipologiaSelected.descTipoDocumentoSpesa, this.tipologiaSelected.descBreveTipoDocSpesa, this.tipoFornitoreSelected.descrizione, this.durataTrasferta,
					this.user.beneficiarioSelezionato.idBeneficiario, null, null, this.fornitoreSelected.idFornitore, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null, this.tipologiaSelected.idTipoDocumentoSpesa,
					this.tipoFornitoreSelected.id, this.attivitaSelected ? this.attivitaSelected.id : null, this.imponibile, this.importoIva, this.importoIvaIndetraibile, this.rendicontabile, this.totaleDocumento, null, null, null, null,
					null, null, this.numero, null, null, null, null, null, this.taskVisibile && this.taskSelected ? this.taskSelected : null, null, null, null, null, null, null,
					this.noteValidatore ? this.noteValidatore : null, null, this.tipoInvioSelected === "1" ? "C" : "D", this.documentoSelected === "1" ? true : false, this.idAppaltocigCpaAffidamentoData,
					this.cigCpaAffidamentoData, this.flagElettXml, null, null, null, null, null, null, null, null);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_CREDITO:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, null, null, null, this.tipologiaSelected.descTipoDocumentoSpesa,
					this.tipologiaSelected.descBreveTipoDocSpesa, this.tipoFornitoreSelected.descrizione, null, this.user.beneficiarioSelezionato.idBeneficiario,
					this.riferimentoFatturaSelected ? this.riferimentoFatturaSelected.idDocumentoDiSpesa : null, null, this.fornitoreSelected.idFornitore, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null,
					this.tipologiaSelected.idTipoDocumentoSpesa, this.tipoFornitoreSelected.id, null, this.imponibile, this.importoIva, this.importoIvaIndetraibile, null, this.totaleDocumento, null, null,
					null, null, null, null, this.numero, null, null, null, null, null, this.taskVisibile && this.taskSelected ? this.taskSelected : null, null, null, null, null, null, null,
					this.noteValidatore ? this.noteValidatore : null, null, this.tipoInvioSelected === "1" ? "C" : "D", this.documentoSelected === "1" ? true : false, this.idAppaltocigCpaAffidamentoData,
					this.cigCpaAffidamentoData, this.flagElettXml, null, null, null, null, null, null, null, null);

				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, null, null, null, null, this.tipologiaSelected.descTipoDocumentoSpesa,
					this.tipologiaSelected.descBreveTipoDocSpesa, null, null, this.user.beneficiarioSelezionato.idBeneficiario, null, null, null, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null,
					this.tipologiaSelected.idTipoDocumentoSpesa, null, null, null, null, null, null, this.importoRendicontabile, null, null, null, null, null, null, null, null, null, null, null, null,
					this.taskVisibile && this.taskSelected ? this.taskSelected : null, null, null, null, null, null, null, this.noteValidatore ? this.noteValidatore : null, null,
					this.tipoInvioSelected === "1" ? "C" : "D", null, null, null, this.flagElettXml, null, null, null, null, null, null, null, null);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE_COSTI_SEMPLIFICATI:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_IMPRESA_ARTIGIANA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_SOGGETTO_GESTORE:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, null, null, null, null, this.tipologiaSelected.descTipoDocumentoSpesa,
					this.tipologiaSelected.descBreveTipoDocSpesa, null, null, this.user.beneficiarioSelezionato.idBeneficiario, null, null, null, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null,
					this.tipologiaSelected.idTipoDocumentoSpesa, null, null, null, null, null, null, this.importo, null, null, null, null, null, null, this.periodo, null, null, null, null, null,
					this.taskVisibile && this.taskSelected ? this.taskSelected : null, null, null, null, null, null, null, this.noteValidatore ? this.noteValidatore : null, null, "D", null, null,
					null, this.flagElettXml, null, null, null, null, null, null, null, null);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, null, null, null, this.tipologiaSelected.descTipoDocumentoSpesa,
					this.tipologiaSelected.descBreveTipoDocSpesa, this.tipoFornitoreSelected.descrizione, null, this.user.beneficiarioSelezionato.idBeneficiario, null, null,
					this.fornitoreSelected.idFornitore, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null, this.tipologiaSelected.idTipoDocumentoSpesa, this.tipoFornitoreSelected.id, null, null, null, null,
					this.rendicontabile, null, null, null, null, null, null, null, this.periodo, null, null, null, null, this.costoOrario, this.taskVisibile && this.taskSelected ? this.taskSelected : null,
					null, null, this.dataSourceQualifiche.data.find(q => q.comodoIdTabella === this.radioSelected).progrFornitoreQualifica, null, null, null, this.noteValidatore ? this.noteValidatore : null,
					null, this.tipoInvioSelected === "1" ? "C" : "D", null, null, null, this.flagElettXml, null, null, null, null, null, null, null, null);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_AUTOCERTIFICAZIONE_SPESE:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, null, null, null, this.tipologiaSelected.descTipoDocumentoSpesa,
					this.tipologiaSelected.descBreveTipoDocSpesa, this.tipoFornitoreSelected.descrizione, null, this.user.beneficiarioSelezionato.idBeneficiario, null, null,
					this.fornitoreSelected.idFornitore, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null, this.tipologiaSelected.idTipoDocumentoSpesa, this.tipoFornitoreSelected.id, null, null, null, null,
					this.rendicontabile, null, null, null, null, null, null, null, this.periodo, null, null, null, null, null, this.taskVisibile && this.taskSelected ? this.taskSelected : null,
					null, null, null, null, null, null, this.noteValidatore ? this.noteValidatore : null,
					null, this.tipoInvioSelected === "1" ? "C" : "D", null, null, null, this.flagElettXml, null, null, null, null, null, null, null, null);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_MENSILE_TIROCINANTE:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, null, null, null, this.tipologiaSelected.descTipoDocumentoSpesa,
					this.tipologiaSelected.descBreveTipoDocSpesa, this.tipoFornitoreSelected.descrizione, null, this.user.beneficiarioSelezionato.idBeneficiario, null, null,
					this.fornitoreSelected.idFornitore, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null, this.tipologiaSelected.idTipoDocumentoSpesa, this.tipoFornitoreSelected.id, null, null, null, null,
					this.rendicontabile, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, this.noteValidatore ? this.noteValidatore : null,
					null, this.tipoInvioSelected === "1" ? "C" : "D", null, null, null, this.flagElettXml, this.idParametroCompenso, this.giorniLavorabiliMensili, this.sospensioniBrevi,
					this.sospensioniLungheGgTotali, this.sospensioniLungheGgLav, this.oreMensiliLavorate, this.periodoMeseAnno.value.month() + 1, this.periodoMeseAnno.value.year());
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_CON_QUIETANZA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_SENZA_QUIETANZA:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, null, null, null,
					this.tipologiaSelected.descTipoDocumentoSpesa, this.tipologiaSelected.descBreveTipoDocSpesa, null, null, this.user.beneficiarioSelezionato.idBeneficiario, null, null,
					null, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null, this.tipologiaSelected.idTipoDocumentoSpesa, null, null, null, null, null,
					this.rendicontabile, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, this.noteValidatore ? this.noteValidatore : null, null, this.tipoInvioSelected === "1" ? "C" : "D", null, null, null, null,
					null, null, null, null, null, null, null, null);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_DOCUMENTO_GENERICO:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, null, null, null, this.tipologiaSelected.descTipoDocumentoSpesa,
					this.tipologiaSelected.descBreveTipoDocSpesa, null, null, this.user.beneficiarioSelezionato.idBeneficiario, null, null, null, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null,
					this.tipologiaSelected.idTipoDocumentoSpesa, null, null, null, null, null, this.rendicontabile, null, null, null, null, null, null, null, this.numero, null, null, null, null, null,
					this.taskVisibile && this.taskSelected ? this.taskSelected : null, null, null, null, null, null, null, this.noteValidatore ? this.noteValidatore : null, null,
					this.tipoInvioSelected === "1" ? "C" : "D", this.documentoSelected === "1" ? true : false, null, null, this.flagElettXml,
					null, null, null, null, null, null, null, null);
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_ATTO_DI_LIQUIDAZIONE:
				if (this.isBandoCultura && this.tipoBeneficiario == 2) {
					documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, null, null, null,
						this.tipologiaSelected.descTipoDocumentoSpesa, this.tipologiaSelected.descBreveTipoDocSpesa, null, null,
						this.user.beneficiarioSelezionato.idBeneficiario, null, null, null, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null,
						this.tipologiaSelected.idTipoDocumentoSpesa, null, null, this.rendicontabile, null, null, this.rendicontabile, null, null,
						null, null, null, null, null, this.numero, null, null, null, null, null, this.taskVisibile && this.taskSelected ? this.taskSelected : null,
						null, null, null, null, null, null, this.noteValidatore ? this.noteValidatore : null, null, this.tipoInvioSelected === '1' ? 'C' : 'D',
						this.documentoSelected === '1' ? true : false, this.idAppaltocigCpaAffidamentoData, this.cigCpaAffidamentoData, this.flagElettXml,
						null, null, null, null, null, null, null, null);
				}
				break;
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCI:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_FATTURA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_FATTURA_FIDEIUSSORIA:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_FATTURA_LEASING:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_DEBITO:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_PRESTAZIONE_OCCASIONALE:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_QUOTA_AMMORTAMENTO:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_RICEVUTA_LOCAZIONE:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_DIRETTE:
			case Constants.DESC_BREVE_TIPO_DOC_SPESA_ATTO_ACQUISTO:
			default:
				documento = new DocumentoDiSpesaDTO(null, this.codiceProgetto, null, new Date(this.data.value), null, this.descrizione, null, null, null, this.tipologiaSelected.descTipoDocumentoSpesa,
					this.tipologiaSelected.descBreveTipoDocSpesa, this.tipoFornitoreSelected.descrizione, null, this.user.beneficiarioSelezionato.idBeneficiario, null, null,
					this.fornitoreSelected.idFornitore, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario, null, this.tipologiaSelected.idTipoDocumentoSpesa, this.tipoFornitoreSelected.id, null, this.imponibile,
					this.importoIva, this.importoIvaIndetraibile, this.rendicontabile, this.totaleDocumento, null, null, null, null, null, null, this.numero, null, null, null, null, null,
					this.taskVisibile && this.taskSelected ? this.taskSelected : null, null, null, null, null, null, null, this.noteValidatore ? this.noteValidatore : null, null,
					this.tipoInvioSelected === "1" ? "C" : "D", this.documentoSelected === "1" ? true : false, this.idAppaltocigCpaAffidamentoData, this.cigCpaAffidamentoData, this.flagElettXml,
					null, null, null, null, null, null, null, null);
				break;
		}
		if (this.idDocumentoDiSpesa && this.tipoOperazioneCurrent !== Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA) {
			documento.idDocumentoDiSpesa = this.idDocumentoDiSpesa;
		}

		if (this.tipoOperazioneFrom === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_VALIDAZIONE) {
			documento.codiceFiscaleFornitore = this.documento.codiceFiscaleFornitore;
		}
		return documento;
	}

	salva() {
		this.resetMessageSuccess();
		this.resetMessageError();
		this.validazioneCampi();
		if (!this.hasValidationError) {
			if (this.isValidazione) {
				this.salvaDocumentoDiSpesaValidazione();
			} else {
				this.salvaDocumentoDiSpesa();
			}
		}
	}

	salvaDocumentoDiSpesa() {
		this.loadedSave = false;
		this.subscribers.salvaDocumentoDiSpesa = this.documentoDiSpesaService.salvaDocumentoDiSpesa(this.setDocumento(), this.idBandoLinea, this.user.idUtente, this.tipoBeneficiario).subscribe(
			data1 => {
				if (data1) {
					if (data1.esito) {
						let idDocSpesa = data1.id;
						if (this.allegatiDocumentoDiSpesaDaSalvare && this.allegatiDocumentoDiSpesaDaSalvare.length > 0) {
							//se ci sono allegati da salvare
							this.loadedAssociaFiles = false;
							let request = new AssociaFilesRequest(
								this.allegatiDocumentoDiSpesaDaSalvare.map(all => +all.idDocumentoIndex),
								idDocSpesa,
								this.idProgetto,
							);
							this.subscribers.salvaAllegati = this.documentoDiSpesaService.associaAllegatiADocSpesa(request).subscribe(
								data2 => {
									if (data2) {
										if (
											data2.elencoIdDocIndexFilesAssociati &&
											data2.elencoIdDocIndexFilesAssociati.length > 0 &&
											(!data2.elencoIdDocIndexFilesNonAssociati || data2.elencoIdDocIndexFilesNonAssociati.length === 0)
										) {
											//tutti associati
											this.documentoDiSpesaService.messageSuccess = data1.messaggio;
										} else if (
											data2.elencoIdDocIndexFilesNonAssociati &&
											data2.elencoIdDocIndexFilesNonAssociati.length > 0 &&
											(!data2.elencoIdDocIndexFilesAssociati || data2.elencoIdDocIndexFilesAssociati.length === 0)
										) {
											//tutti non associati
											this.documentoDiSpesaService.messageSuccess = data1.messaggio;
											this.documentoDiSpesaService.messageError = "Errore nell'associazione degli allegati al documento.";
										} else if (
											data2.elencoIdDocIndexFilesAssociati &&
											data2.elencoIdDocIndexFilesAssociati.length > 0 &&
											data2.elencoIdDocIndexFilesNonAssociati &&
											data2.elencoIdDocIndexFilesNonAssociati.length > 0
										) {
											//alcuni associati e alcuni non associati
											let message = "Errore nell'associazione dei seguenti allegati al documento: ";
											data2.elencoIdDocIndexFilesNonAssociati.forEach(id => {
												let allegato = this.allegatiDocumentoDiSpesaDaSalvare.find(a => a.idDocumentoIndex === id.toString());
												message += allegato.nome + ', ';
											});
											message = message.substr(0, message.length - 2);
											this.documentoDiSpesaService.messageSuccess = data1.messaggio;
											this.documentoDiSpesaService.messageError = message;
										}
										if (!this.idDocumentoDiSpesa || this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA) {
											//se sono in inserisci/clona
											//i messaggi vengono mostrati dopo il navigate da inserisci/clona in modifica
											this.allegatiDocumentoDiSpesaDaSalvare = new Array<InfoDocumentoVo>();
											this.goToModificaFromInserisciClona(idDocSpesa);
										} else if (this.idDocumentoDiSpesa && this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA) {
											//se sono in modifica
											this.allegatiDocumentoDiSpesaDaSalvare = new Array<InfoDocumentoVo>();
											this.loadDatiModifica();
											let msgsucc = this.documentoDiSpesaService.messageSuccess;
											let msgerr = this.documentoDiSpesaService.messageError;
											if (msgsucc) {
												this.showMessageSuccess(msgsucc);
												this.documentoDiSpesaService.messageSuccess = null;
											}
											if (msgerr) {
												this.showMessageError(msgerr);
												this.documentoDiSpesaService.messageError = null;
											}
										}
									}
									this.loadedAssociaFiles = true;
								},
								err => {
									this.handleExceptionService.handleNotBlockingError(err);
									this.loadedAssociaFiles = true;
									this.showMessageError("Errore nell'associazione degli allegati al documento.");
								},
							);
						} else {
							//se non ci sono allegati da salvare
							if (!this.idDocumentoDiSpesa || this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA) {
								//se sono in inserisci/clona
								//i messaggi vengono mostrati dopo il navigate da inserisci in modifica
								this.documentoDiSpesaService.messageSuccess = 'Salvataggio avvenuto con successo.';
								this.goToModificaFromInserisciClona(idDocSpesa);
							} else if (this.idDocumentoDiSpesa && this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA) {
								//se sono in modifica
								this.showMessageSuccess(data1.messaggio);
								this.loadDatiModifica();
							}
						}
						this.calcolaTotaleDocumento();
					} else {
						this.showMessageError(data1.messaggio);
					}
				}
				this.loadedSave = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.loadedSave = true;
				this.showMessageError('Errore nel salvataggio del documento.');
			},
		);
	}

	salvaDocumentoDiSpesaValidazione() {
		this.loadedSave = false;
		this.subscribers.salvaDocumentoDiSpesaValidazione = this.documentoDiSpesaService.salvaDocumentoDiSpesaValidazione(this.setDocumento(), this.user.idUtente).subscribe(
			data1 => {
				if (data1) {
					let msg: string = '';
					data1.messaggi.forEach(m => {
						msg += m + '<br>';
					});
					msg = msg.substr(0, msg.length - 4);
					if (data1.esito) {
						this.showMessageSuccess(msg);
					} else {
						this.showMessageError(msg);
					}
				}
				this.loadedSave = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.loadedSave = true;
				this.showMessageError('Errore nel salvataggio del documento.');
			},
		);
	}

	goToModificaFromInserisciClona(idDocSpesa: number) {
		if (this.tipoOperazioneCurrent === Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA) {
			this.tipoOperazioneFrom = this.tipoOperazioneCurrent;
			this.tipoOperazioneCurrent = Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA;
		} else {
			//se sono in inserimento popolo le variabili per il navigate in modifica
			this.tipoOperazioneFrom = Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_INSERISCI;
			this.tipoOperazioneCurrent = Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA;
		}
		let params = this.activatedRoute.snapshot.paramMap.get('integrativa')
			? { current: this.tipoOperazioneCurrent, from: this.tipoOperazioneFrom, integrativa: true }
			: { current: this.tipoOperazioneCurrent, from: this.tipoOperazioneFrom };
		this.router.navigate(['drawer/' + this.idRouting + '/documentoDiSpesa', this.idProgetto, this.idBandoLinea, idDocSpesa, params]);
	}

	nuovoFornitore() {
		this.resetMessageSuccess();
		this.resetMessageError();
		const dialogRef = this.dialog.open(NuovoModicaFornitoreDialogComponent, {
			width: '60%',
			disableClose: true,
			data: {
				tipologie: this.tipologieFornitore,
				qualifiche: this.qualifiche,
				isNuovo: true,
				user: this.user,
				idProgetto: this.idProgetto,
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (!res.isNuovo) {
				this.tipoFornitoreSelected = this.tipologieFornitore.find(t => t.id === res.idTipoFornitore);
				this.loadFornitori(this.tipoFornitoreSelected, res.idFornitore);
			}
		});
	}

	selezionaFornitoreXml(fornitoreAnagrafica: FornitoreFormDTO, fornitoreFattura: FornitoreFormDTO) {
		this.resetMessageSuccess();
		this.resetMessageError();
		const dialogRef = this.dialog.open(NuovoModicaFornitoreDialogComponent, {
			width: '60%',
			disableClose: true,
			data: {
				tipologie: this.tipologieFornitore,
				fornitore: Object.assign({}, fornitoreAnagrafica),
				fornitoreAnagrafica: fornitoreAnagrafica,
				fornitoreFattura: fornitoreFattura,
				isNuovo: false,
				isSeleziona: true,
				isXml: true,
				user: this.user,
				idProgetto: this.idProgetto,
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (!res.isNuovo) {
				this.tipoFornitoreSelected = this.tipologieFornitore.find(t => t.id === res.idTipoFornitore);
				this.loadFornitori(this.tipoFornitoreSelected, res.idFornitore);
			}
		});
	}

	nuovoFornitoreXml(fornitore: FornitoreFormDTO) {
		this.resetMessageSuccess();
		this.resetMessageError();
		const dialogRef = this.dialog.open(NuovoModicaFornitoreDialogComponent, {
			width: '60%',
			disableClose: true,
			data: {
				tipologie: this.tipologieFornitore,
				fornitore: fornitore,
				isNuovo: true,
				isXml: true,
				user: this.user,
				idProgetto: this.idProgetto,
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (!res.isNuovo) {
				this.tipoFornitoreSelected = this.tipologieFornitore.find(t => t.id === res.idTipoFornitore);
				this.loadFornitori(this.tipoFornitoreSelected, res.idFornitore);
			}
		});
	}

	modificaFornitore() {
		this.resetMessageSuccess();
		this.resetMessageError();
		this.loadedFornitori = false;
		this.subscribers.cercaFornitore = this.fornitoreService.cercaFornitore(this.fornitoreSelected.idFornitore, this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario).subscribe(
			fornitore => {
				if (fornitore) {
					const dialogRef = this.dialog.open(NuovoModicaFornitoreDialogComponent, {
						width: '60%',
						disableClose: true,
						data: {
							tipologie: this.tipologieFornitore,
							qualifiche: this.qualifiche,
							fornitore: fornitore,
							allegati: fornitore.documentiAllegati,
							isNuovo: false,
							user: this.user,
							idProgetto: this.idProgetto,
						},
					});
					dialogRef.afterClosed().subscribe(res => {
						this.loadFornitori(this.tipoFornitoreSelected, res.idFornitore);
					});
				}
				this.loadedFornitori = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	associaAffidamento() {
		const dialogRef = this.dialog.open(AssociaAffidamentoDialogComponent, {
			minWidth: '40%',
			data: {
				idProgetto: this.idProgetto,
				idFornitoreDocSpesa: this.fornitoreSelected ? this.fornitoreSelected.idFornitore : null,
				codiceRuolo: this.user.codiceRuolo,
				idAppaltoSelected: this.idAppaltocigCpaAffidamentoData ? this.idAppaltocigCpaAffidamentoData : 0,
			},
		});
		dialogRef.afterClosed().subscribe(result => {
			if (result && !(result.data == undefined)) {
				this.cigCpaAffidamentoData = result.data.codProcAgg;
				this.idAppaltocigCpaAffidamentoData = result.data.idAppalto;
			}
		});
	}

	nuovaQualifica() {
		// Aprire dialog solo se selezionato il fornitore
		if (this.fornitoreSelected == null) {
			this.openSnackBar('Selezionare un fornitore di tipo PERSONA FISICA.');
		} else {
			const dialogRef = this.dialog.open(NuovaQualificaDialogComponent, {
				width: '40%',
				data: this.fornitoreSelected.idFornitore,
			});
			dialogRef.afterClosed().subscribe(result => {
				if (!(result == undefined)) {
					if (result.data) {
						this.subscribers.salvaQualifica = this.fornitoreService.salvaQualifica(result.request).subscribe(
							data => {
								this.changeFornitore();
								this.openSnackBar('Qualifica inserita con successo');
							},
							err => {
								this.handleExceptionService.handleBlockingError(err);
							},
						);
					}
				}
			});
		}
	}

	aggiungiAllegati() {
		let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
			maxWidth: '100%',
			width: window.innerWidth - 100 + 'px',
			height: window.innerHeight - 50 + 'px',
			data: {
				allegatiDaSalvare: this.allegatiDocumentoDiSpesaDaSalvare,
				allegati: this.allegatiDocumentoDiSpesa,
				apiURL: this.configService.getApiURL(),
				user: this.user,
				drawerExpanded: this.sharedService.getDrawerExpanded(),
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res && res.length > 0) {
				for (let a of res) {
					if (this.allegatiDocumentoDiSpesaDaSalvare.find(all => all.idDocumentoIndex === a.idDocumentoIndex) === undefined) {
						this.allegatiDocumentoDiSpesaDaSalvare.push(a);
					}
				}
			}
		});
	}

	downloadAllegato(idDocumentoIndex: string, nomeDocumento: string) {
		this.loadedDownload = false;
		this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex).subscribe(
			res => {
				if (res) {
					saveAs(res, nomeDocumento);
				}
				this.loadedDownload = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError('Errore in fase di download del file');
				this.loadedDownload = true;
			},
		);
	}

	rimuoviAllegatoDocumentoDiSpesaDaSalvare(idDocumentoIndex: string) {
		this.allegatiDocumentoDiSpesaDaSalvare.splice(
			this.allegatiDocumentoDiSpesaDaSalvare.findIndex(a => a.idDocumentoIndex === idDocumentoIndex),
			1,
		);
	}

	disassociaAllegatoDocumentoDiSpesa(idDocumentoIndex: string) {
		this.resetMessageSuccess();
		this.resetMessageError();
		this.loadedDisassociaAllegato = false;
		this.subscribers.disassociaAllegato = this.documentoDiSpesaService.disassociaAllegatoDocumentoDiSpesa(+idDocumentoIndex, this.idDocumentoDiSpesa, this.idProgetto, this.user.idUtente).subscribe(
			data => {
				if (data) {
					if (data.esito) {
						this.showMessageSuccess(data.messaggio);
						this.loadAllegatiDocSpesa();
					} else {
						this.showMessageError(data.messaggio);
					}
				}
				this.loadedDisassociaAllegato = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError('Errore nella disassociazione del file.');
				this.loadedDisassociaAllegato = true;
			},
		);
	}

	nuovaVoceDiSpesa() {
		this.resetMessageSuccess();
		this.resetMessageError();
		let dialogRef = this.dialog.open(NuovoModificaVoceSpesaDialogComponent, {
			width: '60%',
			data: {
				isNuovo: true,
				idDocumentoDiSpesa: this.idDocumentoDiSpesa,
				idTipoDocumentoDiSpesa: this.tipologiaSelected.idTipoDocumentoSpesa,
				idProgetto: this.idProgetto,
				user: this.user,
				macroVoci: this.macroVoci,
				residuoRendicontabile: this.residuoRendicontabile,
				costoOrario: this.costoOrario,
				descBreveTipoDocumento: this.documento.descBreveTipoDocumentoDiSpesa,
				idRouting: this.idRouting,
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res) {
				this.showMessageSuccess(res);
				this.loadElencoVociSpesa();
			}
		});
	}

	modificaVoceDiSpesa(voce: VoceDiSpesaDTO) {
		this.resetMessageSuccess();
		this.resetMessageError();
		let dialogRef = this.dialog.open(NuovoModificaVoceSpesaDialogComponent, {
			width: '60%',
			data: {
				isNuovo: false,
				idDocumentoDiSpesa: this.idDocumentoDiSpesa,
				idTipoDocumentoDiSpesa: this.tipologiaSelected.idTipoDocumentoSpesa,
				idProgetto: this.idProgetto,
				user: this.user,
				macroVoci: this.macroVoci,
				residuoRendicontabile: this.residuoRendicontabile,
				costoOrario: this.costoOrario,
				descBreveTipoDocumento: this.documento.descBreveTipoDocumentoDiSpesa,
				voce: voce,
				tipoOperazioneFrom: this.tipoOperazioneFrom,
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res) {
				this.showMessageSuccess(res);
				this.loadElencoVociSpesa();
			}
		});
	}

	cancellaVoceDiSpesa(voce: VoceDiSpesaDTO) {
		this.resetMessageSuccess();
		this.resetMessageError();
		let dialogRef = this.dialog.open(ConfermaDialog, {
			width: '40%',
			data: {
				message: "Confermi l'eliminazione della voce di spesa associata al documento?",
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res === 'SI') {
				this.loadedElimina = false;
				this.subscribers.eliminaVoce = this.documentoDiSpesaService.disassociaVoceDiSpesa(voce.idQuotaParteDocSpesa, this.user.idUtente).subscribe(
					data => {
						if (data) {
							if (data.esito) {
								this.showMessageSuccess(data.messaggio);
								this.loadElencoVociSpesa();
							} else {
								this.showMessageError(data.messaggio);
							}
						}
						this.loadedElimina = true;
					},
					err => {
						this.handleExceptionService.handleNotBlockingError(err);
						this.loadedElimina = true;
						this.showMessageError("Errore nell'eliminazione della voce di spesa.");
					},
				);
			}
		});
	}

	checkFlagS() {
		if (this.dataSourceQuietanziato && this.dataSourceQuietanziato.data.length > 0) {
			let quietanza = this.dataSourceQuietanziato.data.find(q => q.flagPagamento === "S")
			if (quietanza != undefined) {
				return true;
			}
		}
		return false;
	}

	nuovaQuietanza() {
		this.resetMessageSuccess();
		this.resetMessageError();
		let dialogRef = this.dialog.open(NuovoModificaQuietanzaDialogComponent, {
			width: '50%',
			data: {
				isNuovo: true,
				idDocumentoDiSpesa: this.idDocumentoDiSpesa,
				idProgetto: this.idProgetto,
				idBandoLinea: this.idBandoLinea,
				isValidazione: this.isValidazione,
				user: this.user,
				modalita: this.modalitaQuitanza,
				tipoOperazioneFrom: "nuova",
				causali: this.causaliQuietanza,
				causaleQuietanzaVisibile: this.causaleQuietanzaVisibile,
				residuoQuietanzabile: this.residuoQuietanzabile,
				vociDiSpesa: this.dataSourceVociSpesa.data,
				isBR62: this.isBR62,
				quietanze: this.dataSourceQuietanziato.data.length,
				ggQuietanza: this.ggQuietanza,
				dataDocumento: this.data.value
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res) {
				this.showMessageSuccess(res);
				this.loadElencoQuietanze();
			}
		});
	}

	checkAttachment(quietanza: DocumentoDiPagamentoDTO) {
		if (quietanza && quietanza.flagPagamento == 'S') {
			return true;
		}
		return false;
	}

	dateFormat(data) {
		let dataFormatted = new Date();
		let giornoMeseAnno = data.split("/");
		dataFormatted.setDate = giornoMeseAnno[0];
		dataFormatted.setMonth = giornoMeseAnno[1];
		dataFormatted.setFullYear = giornoMeseAnno[2];
		return dataFormatted;
	}

	modificaQuietanza(quietanza: DocumentoDiPagamentoDTO) {
		this.resetMessageSuccess();
		this.resetMessageError();
		let dialogRef = this.dialog.open(NuovoModificaQuietanzaDialogComponent, {
			width: '50%',
			data: {
				isNuovo: false,
				idDocumentoDiSpesa: this.idDocumentoDiSpesa,
				idProgetto: this.idProgetto,
				idBandoLinea: this.idBandoLinea,
				isValidazione: this.isValidazione,
				user: this.user,
				quietanza: quietanza,
				modalita: this.modalitaQuitanza,
				causali: this.causaliQuietanza,
				causaleQuietanzaVisibile: this.causaleQuietanzaVisibile,
				residuoQuietanzabile: this.residuoQuietanzabile,
				tipoOperazioneFrom: "modifica",
				vociDiSpesa: this.dataSourceVociSpesa.data,
				ggQuietanza: this.ggQuietanza,
				isBR62: this.isBR62,
				quietanze: this.dataSourceQuietanziato.data.length,
				dataDocumento: this.data.value
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res) {
				this.showMessageSuccess(res);
				this.loadElencoQuietanze();
			}
		});
	}

	cancellaQuietanza(quietanza: DocumentoDiPagamentoDTO) {
		this.resetMessageSuccess();
		this.resetMessageError();
		let dialogRef = this.dialog.open(ConfermaDialog, {
			width: '40%',
			data: {
				message: 'Confermi la cancellazione del pagamento?',
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res === 'SI') {
				this.loadedElimina = false;
				this.subscribers.eliminaQuietanza = this.documentoDiSpesaService.disassociaQuietanza(quietanza.id).subscribe(
					data => {
						if (data) {
							if (data.esito) {
								this.showMessageSuccess(data.messaggio);
								this.loadElencoQuietanze();
							} else {
								this.showMessageError(data.messaggio);
							}
						}
						this.loadedElimina = true;
					},
					err => {
						this.handleExceptionService.handleNotBlockingError(err);
						this.loadedElimina = true;
						this.showMessageError("Errore nell'eliminazione del pagamento.");
					},
				);
			}
		});
	}

	aggiungiAllegatiQuietanza(quietanza: DocumentoDiPagamentoDTO) {
		this.resetMessageSuccess();
		this.resetMessageError();
		let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
			maxWidth: '100%',
			width: window.innerWidth - 100 + 'px',
			height: window.innerHeight - 50 + 'px',
			data: {
				allegati: quietanza.documentiAllegati,
				apiURL: this.configService.getApiURL(),
				user: this.user,
				drawerExpanded: this.sharedService.getDrawerExpanded(),
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res && res.length > 0) {
				this.loadedAssociaFiles = false;
				let request = new AssociaFilesRequest(
					res.map(all => +all.idDocumentoIndex),
					quietanza.id,
					this.idProgetto,
				);
				this.subscribers.associaAllegatiQuietanza = this.documentoDiSpesaService.associaAllegatiAQuietanza(request).subscribe(
					data => {
						if (data) {
							if (
								data.elencoIdDocIndexFilesAssociati &&
								data.elencoIdDocIndexFilesAssociati.length > 0 &&
								(!data.elencoIdDocIndexFilesNonAssociati || data.elencoIdDocIndexFilesNonAssociati.length === 0)
							) {
								//tutti associati
								this.showMessageSuccess('Tutti gli allegati sono stati associati correttamente al pagamento.');
								this.loadElencoQuietanze();
							} else if (
								data.elencoIdDocIndexFilesNonAssociati &&
								data.elencoIdDocIndexFilesNonAssociati.length > 0 &&
								(!data.elencoIdDocIndexFilesAssociati || data.elencoIdDocIndexFilesAssociati.length === 0)
							) {
								//tutti non associati
								this.showMessageError("Errore nell'associazione degli allegati al pagamento.");
							} else if (
								data.elencoIdDocIndexFilesAssociati &&
								data.elencoIdDocIndexFilesAssociati.length > 0 &&
								data.elencoIdDocIndexFilesNonAssociati &&
								data.elencoIdDocIndexFilesNonAssociati.length > 0
							) {
								//alcuni associati e alcuni non associati
								this.loadElencoQuietanze();
								let message = "Errore nell'associazione dei seguenti allegati al pagamento: ";
								data.elencoIdDocIndexFilesNonAssociati.forEach(id => {
									let allegato = this.allegatiDocumentoDiSpesaDaSalvare.find(a => a.idDocumentoIndex === id.toString());
									message += allegato.nome + ', ';
								});
								message = message.substr(0, message.length - 2);
								this.showMessageError(message);
							}
						}
						this.loadedAssociaFiles = true;
					},
					err => {
						this.handleExceptionService.handleNotBlockingError(err);
						this.loadedAssociaFiles = true;
						this.showMessageError("Errore nell'associazione degli allegati al pagamento.");
					},
				);
			}
		});
	}

	disassociaAllegatoQuietanza(idDocumentoIndex: string, idPagamento: number) {
		this.resetMessageSuccess();
		this.resetMessageError();
		this.loadedDisassociaAllegato = false;
		this.subscribers.disassociaAllegatoQuietanza = this.documentoDiSpesaService.disassociaAllegatoQuietanza(+idDocumentoIndex, idPagamento, this.idProgetto).subscribe(
			data => {
				if (data) {
					if (data.esito) {
						this.showMessageSuccess(data.messaggio);
						this.loadElencoQuietanze();
					} else {
						this.showMessageError(data.messaggio);
					}
				}
				this.loadedDisassociaAllegato = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError('Errore nella disassociazione del file.');
				this.loadedDisassociaAllegato = true;
			},
		);
	}

	stateRadio = false;
	radioSelected = 0;
	//click radio tabella qualifiche
	public clickRadio(event: Event, value: number) {
		// event.preventDefault();
		this.radioSelected = value;
		this.stateRadio = true;
		this.setCostoOrario(value);
	}

	setCostoOrario(comodoIdTabella: number, isInit?: boolean) {
		if (this.dataSourceQualifiche && this.dataSourceQualifiche.data && this.dataSourceQualifiche.data.length > 0) {
			this.costoOrario = this.dataSourceQualifiche.data.find(q => q.comodoIdTabella === comodoIdTabella).costoOrario;
			this.costoOrarioFormatted = this.sharedService.formatValue(this.costoOrario.toString());
			if (!this.idDocumentoDiSpesa || (this.idDocumentoDiSpesa && Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA && !isInit)) {
				this.calcolaRendicontabileQualifiche();
			} else {
				this.calcolaOreLavorate();
			}
		}
	}

	calcolaRendicontabileQualifiche() {
		if (!this.oreLavorate || !this.costoOrario) {
			this.rendicontabile = null;
			this.rendicontabileFormatted = null;
			return;
		}
		this.rendicontabile = this.oreLavorate * this.costoOrario;
		this.rendicontabileFormatted = this.sharedService.formatValue(this.rendicontabile.toString());
		this.calcolaResiduoRendicontabile();
	}

	calcolaOreLavorate() {
		if (!this.rendicontabile || !this.costoOrario) {
			this.oreLavorate = null;
			return;
		}
		this.oreLavorate = this.rendicontabile / this.costoOrario;
		this.oreLavorate = +this.oreLavorate.toFixed(2);
	}

	changeFornitore(isInit?: boolean, descAppalto?: string, idAppalto?: number) {
		//descAppalto, idAppalto valorizzati solo se si arriva dalla lettura della fattura elettronica
		this.resetMessageError2();
		this.cigCpaAffidamentoData = null;
		this.idAppaltocigCpaAffidamentoData = null;
		if (descAppalto && idAppalto) {
			this.cigCpaAffidamentoData = descAppalto;
			this.idAppaltocigCpaAffidamentoData = idAppalto;
		} else if (this.idDocumentoDiSpesa && isInit && this.documento.idAppalto) {
			//inizializzazione in modifica
			if (this.documento.idFornitore === this.fornitoreSelected.idFornitore) {
				//se il fornitore corrente è quello salvato, mostro l'affidamento assocaito, altrimenti lo resetto
				this.idAppaltocigCpaAffidamentoData = this.documento.idAppalto;
				this.cigCpaAffidamentoData = this.documento.descrizioneAppalto;
			}
		}
		this.idAppaltocigCpaAffidamentoData = null;
		if (!this.fornitoreSelected.formaGiuridicaValida && !this.fornitoreSelected.cfValido) {
			let msg =
				"Errore per il fonitore selezionato: codice fiscale errato - forma giuridica assente.<br>È necessario correggerlo in 'gestione fornitori' o mediante il comando 'modifica fornitore' per poter salvare il documento di spesa.";
			this.showMessageError2(msg);
		} else if (!this.fornitoreSelected.formaGiuridicaValida) {
			let msg =
				"Errore per il fonitore selezionato: forma giuridica assente.<br>È necessario correggerlo in 'gestione fornitori' o mediante il comando 'modifica fornitore' per poter salvare il documento di spesa.";
			this.showMessageError2(msg);
		} else if (!this.fornitoreSelected.cfValido) {
			let msg =
				"Errore per il fonitore selezionato: codice fiscale errato.<br>È necessario correggerlo in 'gestione fornitori' o mediante il comando 'modifica fornitore' per poter salvare il documento di spesa.";
			this.showMessageError2(msg);
		} else {
			this.loadAllegatiFornitore();
			if (
				this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO ||
				this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD
			) {
				this.loadQualifiche(isInit);
			}
			if (this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_NOTA_CREDITO) {
				this.loadRiferimentoFattura(isInit);
			}
		}
	}

	loadAllegatiFornitore() {
		this.loadedAllegatiFornitore = false;
		this.subscribers.allegatiForn = this.documentoDiSpesaService.getAllegatiFornitore(this.fornitoreSelected.idFornitore, this.idProgetto, this.user.idUtente).subscribe(
			data => {
				if (data) {
					this.allegatiFornitore = data;
				}
				this.loadedAllegatiFornitore = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.loadedAllegatiFornitore = true;
			},
		);
	}

	loadQualifiche(isInit?: boolean) {
		this.showQualificheTable = true;
		this.loadedQualificheFornitore = false;
		this.subscribers.qualifiche = this.fornitoreService.getQualificheFornitore(this.user, this.fornitoreSelected.idFornitore).subscribe(
			data => {
				var comodoQualificheFornitore = new Array<QualificaDTO>();
				comodoQualificheFornitore = data;
				var i = 0;
				for (var itemQualificaFornitore of comodoQualificheFornitore) {
					itemQualificaFornitore.comodoIdTabella = i;
					i++;
				}
				if (!this.idDocumentoDiSpesa) {
					this.radioSelected = 0;
				} else {
					this.radioSelected = comodoQualificheFornitore.find(c => c.progrFornitoreQualifica === this.documento.progrFornitoreQualifica).comodoIdTabella;
				}
				this.dataSourceQualifiche = new MatTableDataSource(comodoQualificheFornitore);
				this.setCostoOrario(this.radioSelected, isInit);

				this.loadedQualificheFornitore = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	loadRiferimentoFattura(isInit?: boolean) {
		this.loadedRiferimentoFattura = false;
		this.subscribers.home = this.fornitoreService.getFattureFornitore(this.idProgetto, this.fornitoreSelected.idFornitore).subscribe(
			data => {
				if (data) {
					this.riferimentoFatture = data;
					if (this.idDocumentoDiSpesa && isInit && this.documento.idDocRiferimento) {
						this.riferimentoFatturaSelected = this.riferimentoFatture.find(r => r.idDocumentoDiSpesa === this.documento.idDocRiferimento);
						this.setImportiFatturaRif();
					}
				}
				this.loadedRiferimentoFattura = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	resetInputValue() {
		this.numero = null;
		this.periodo = null;
		this.data = new FormControl();
		this.documentoSelected = '2';
		this.descrizione = null;
		this.imponibile = null;
		this.imponibileFormatted = null;
		this.imponibileRitAcc = null;
		this.imponibileRitAccFormatted = null;
		this.oreLavorate = null;
		this.rendicontabile = null;
		this.rendicontabileFormatted = null;
		this.importoRendicontabile = null;
		this.importoRendicontabileFormatted = null;
		this.importo = null;
		this.importoFormatted = null;
		this.importoIva = null;
		this.importoIvaFormatted = null;
		this.importoIvaIndetraibile = null;
		this.importoIvaIndetraibileFormatted = null;
		this.totaleDocumento = null;
		this.totaleDocumentoFormatted = null;
		this.durataTrasferta = null;
		this.durataTrasfertaFormatted = null;
		this.tipoInvioSelected = '2';
		this.messageWarning2 = null;
		this.isMessageWarning2Visible = false;
		this.resetMessageError2();
		this.fornitoreSelected = null;
		this.tipoFornitoreSelected = null;
		this.cigCpaAffidamentoData = null;
		this.idAppaltocigCpaAffidamentoData = null;
		this.riferimentoFatturaSelected = null;
		this.attivitaSelected = null;
	}

	resetInputErrors() {
		Object.keys(this.ricercaForm.form.controls).forEach(key => {
			if (this.ricercaForm.form.controls[key]) {
				this.ricercaForm.form.controls[key].setErrors(null);
			}
		});
	}

	changeTipoDocumento(isInit?: boolean) {
		if (!isInit) {
			this.resetMessageWarning();
			this.warningBloccante = false;
		}
		if (!this.idDocumentoDiSpesa) {
			this.resetInputValue();
			if (this.isBR58 && this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_SENZA_QUIETANZA) {
				if (this.salCorrente) {
					if (this.quotaImportoAgevolato && this.salCorrente?.percImportoContrib) {
						this.rendicontabile = +((this.quotaImportoAgevolato * (this.salCorrente.percImportoContrib / 100)).toFixed(2));
						this.rendicontabileFormatted = this.sharedService.formatValue(this.rendicontabile.toString());
						if (this.esitoImportoASaldo && !this.esitoImportoASaldo.esito) {
							this.showMessageWarning(this.esitoImportoASaldo.messaggio);
							this.warningBloccante = true;
						}
						/*if (this.salCorrente.ultimoIter && this.importoASaldo) {
							if (this.quotaImportoAgevolato >= this.esitoImportoASaldo.importoSpeso) {
								if (this.importoASaldo < 0) {
									this.showMessageWarning("Attenzione! Saldo negativo. Impossibile procedere con la richiesta di saldo. Contattare l'ente istruttore!");
								}
								this.rendicontabile = this.importoASaldo;
								this.rendicontabileFormatted = this.sharedService.formatValue(this.rendicontabile.toString());
							}
							else {
								this.showMessageWarning("ATTENZIONE! L'IMPORTO SPESO (Q.E. rendicontazione)  NON può essere maggiore dell'IMPORTO CONCESSO (Q.E. iniziale). Correggere prima il QTES!");
								this.warningBloccante = true;
							}
						}*/
					}
				} else {
					this.showMessageWarning("ATTENZIONE! Per procedere con la creazione del documento di spesa è necessario aver compilato il cronoprogramma.");
					this.warningBloccante = true;
				}
			}
		}
		this.loadedDocumentazioneDaAllegare = false;
		this.documentoDiSpesaService.getDocumentazioneDaAllegare(this.idBandoLinea, this.tipologiaSelected.idTipoDocumentoSpesa).subscribe(data => {
			this.documentazioneDaAllegare = data;
			this.loadedDocumentazioneDaAllegare = true;
		}, err => {
			this.handleExceptionService.handleNotBlockingError(err);
			this.showMessageError("Errore in fase di caricamento della documentazione da allegare.");
			this.loadedDocumentazioneDaAllegare = true;
		});

		this.resetInputErrors();
		this.showQualificheTable = false;

		this.subscribers.tipoFornitore = this.rendicontazioneService.findTipologieFornitoreByIdTipoDocSpesa(this.tipologiaSelected.idTipoDocumentoSpesa).subscribe(data => {
			this.tipologieFornitore = data;
			if (this.idDocumentoDiSpesa && isInit && this.documento.idTipoFornitore) { //se sono appena atterrato sulla pagina di modifica
				this.tipoFornitoreSelected = this.tipologieFornitore.find(t => t.id === this.documento.idTipoFornitore);
				this.loadFornitori(this.tipoFornitoreSelected, this.documento.idFornitore);
			} else {
				this.tipoFornitoreSelected = null;
				this.fornitoreSelected = null;
				this.showQualificheTable = false;
				this.cigCpaAffidamentoData = null;
				this.idAppaltocigCpaAffidamentoData = null;
			}
		}, err => {
			this.handleExceptionService.handleBlockingError(err);
		});
		if (this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_MENSILE_TIROCINANTE) {
			this.loadedParametriCompensi = false;
			this.documentoDiSpesaService.getParametriCompensi().subscribe(data => {
				this.parametriCompensi = data;
				if (this.parametriCompensi?.length > 0) {
					this.categorie = this.parametriCompensi.map(p => p.categoria);
					if (this.documento && this.documento.idParametroCompenso) {
						this.categoriaSelected = this.categorie.find(c => c === this.documento.idParametroCompenso);
					}
				}
				this.loadedParametriCompensi = true;
			}, err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError("Errore in fase di caricamento dei parametri dei compensi.");
				this.loadedParametriCompensi = true;
			});
		}
	}

	get idParametroCompenso(): number {
		if (this.parametriCompensi?.length > 0) {
			let p = this.parametriCompensi.find(p => p.categoria === this.categoriaSelected);
			return p?.idParametroCompenso || null;
		}
		return null;
	}

	get oreSettimanali(): number {
		if (this.parametriCompensi?.length > 0) {
			let p = this.parametriCompensi.find(p => p.categoria === this.categoriaSelected);
			return p?.oreSettimanali || null;
		}
		return null;
	}

	get giorniLavorabiliSettimanali(): number {
		if (this.parametriCompensi?.length > 0) {
			let p = this.parametriCompensi.find(p => p.categoria === this.categoriaSelected);
			return p?.giorniLavorabiliSettimanali || null;
		}
		return null;
	}

	get compensoMensile(): string {
		if (this.parametriCompensi?.length > 0) {
			let p = this.parametriCompensi.find(p => p.categoria === this.categoriaSelected);
			if (p?.compensoDovutoMensile !== null && p?.compensoDovutoMensile !== undefined) {
				return this.sharedService.formatValue(p.compensoDovutoMensile.toString());
			}
		}
		return null;
	}

	get orarioMedioGiornaliero(): string {
		if (this.parametriCompensi?.length > 0) {
			let p = this.parametriCompensi.find(p => p.categoria === this.categoriaSelected);
			if (p?.orarioMedioGiornaliero !== null && p?.orarioMedioGiornaliero !== undefined) {
				return this.sharedService.formatValue(p.orarioMedioGiornaliero.toString());
			}
		}
		return null;
	}

	changeTipoFornitore(dataTipoFornitore: DecodificaDTO, idFornitore?: number, descAppalto?: string, idAppalto?: number) {
		//idFornitore, descAppalto, idAppalto valorizzati solo se si arriva dalla lettura della fattura elettronica
		this.fornitoreSelected = null;
		this.showQualificheTable = false;
		this.cigCpaAffidamentoData = null;
		this.idAppaltocigCpaAffidamentoData = null;
		this.loadFornitori(dataTipoFornitore, idFornitore, descAppalto, idAppalto);
	}

	loadFornitori(dataTipoFornitore: DecodificaDTO, idFornitore?: number, descAppalto?: string, idAppalto?: number) {
		this.resetMessageError2();
		this.loadedFornitori = false;
		this.subscribers.fornitori = this.rendicontazioneService.findFornitoriCombo(this.user.beneficiarioSelezionato.idBeneficiario, dataTipoFornitore.id, idFornitore ? idFornitore : null).subscribe(
			data => {
				this.fornitori = data;
				if (idFornitore) {
					//idFornitore restituito da salvaFornitore oopure da getDocumentoDiSpesa: seleziono in automatico il fornitore appena creato/modificato
					this.fornitoreSelected = this.fornitori.find(f => f.idFornitore === idFornitore);
					this.changeFornitore(true, descAppalto, idAppalto);
				}
				this.loadedFornitori = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	changeDocumento(event: MatRadioChange) {
		if (event.value === '1') {
			this.tipoInvioSelected = '2';
		}
	}

	changeTipoInvio(event: MatRadioChange) {
		if (event.value === '1') {
			this.messageWarning2 = "L'invio degli allegati in cartaceo non consentirà la possibilità di trasmettere il documento in una dichiarazione di spesa digitale.";
			this.isMessageWarning2Visible = true;
		} else {
			this.messageWarning2 = null;
			this.isMessageWarning2Visible = false;
		}
	}

	selectAttivita() {
		if (this.attivitaSelected.id === 0) {
			this.attivitaSelected = null;
			this.ricercaForm.form.get('attivitaSelected').setValue(null);
		}
	}

	//è presente solo uno tra imponibile e imponibileRitAcc
	calcolaTotaleDocumento() {

		this.imponibile = this.sharedService.getNumberFromFormattedString(this.imponibileFormatted ? this.imponibileFormatted : '0');
		this.imponibileRitAcc = this.sharedService.getNumberFromFormattedString(this.imponibileRitAccFormatted ? this.imponibileRitAccFormatted : '0');
		this.importoIva = this.sharedService.getNumberFromFormattedString(this.importoIvaFormatted ? this.importoIvaFormatted : '0');
		let impImponibile = this.imponibile ? this.imponibile : 0;
		let impImponibileRitAcc = this.imponibileRitAcc ? this.imponibileRitAcc : 0;
		let impIva = this.importoIva ? this.importoIva : 0;
		this.totaleDocumento = impImponibile + impImponibileRitAcc + impIva;
		this.totaleDocumentoFormatted = this.sharedService.formatValue(this.totaleDocumento.toString());
		if (this.ricercaForm && this.ricercaForm.form.get('totaleDocumento')) {
			this.ricercaForm.form.get('totaleDocumento').markAsTouched();
		}
		this.calcolaResiduoQuietanzabile();
	}

	calcolaTotaleDocumentoRendicontabile() {
		//rendicontabile presente in documento generico, importo rendicontabile presente in spese generali forfettarie
		//solo uno dei due alla volta
		if (
			this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_DOCUMENTO_GENERICO ||
			this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE ||
			this.tipologiaSelected.descBreveTipoDocSpesa === Constants.DESC_BREVE_TIPO_DOC_SPESA_SAL_CON_QUIETANZA
		) {
			this.rendicontabile = this.sharedService.getNumberFromFormattedString(this.rendicontabileFormatted ? this.rendicontabileFormatted : '0');
			this.importoRendicontabile = this.sharedService.getNumberFromFormattedString(this.importoRendicontabileFormatted ? this.importoRendicontabileFormatted : '0');
			let rend = this.rendicontabile ? this.rendicontabile : 0;
			let impRend = this.importoRendicontabile ? this.importoRendicontabile : 0;
			this.totaleDocumento = rend + impRend;
			this.totaleDocumentoFormatted = this.sharedService.formatValue(this.totaleDocumento.toString());
			if (this.ricercaForm && this.ricercaForm.form.get('totaleDocumento')) {
				this.ricercaForm.form.get('totaleDocumento').markAsTouched();
			}
			this.calcolaResiduoQuietanzabile();
		}
	}

	setImponibile() {
		if (this.imponibile !== null) {
			this.imponibileFormatted = this.sharedService.formatValue(this.imponibile.toString());
		}
	}

	setImponibileRitAcc() {
		if (this.imponibileRitAcc !== null) {
			this.imponibileRitAccFormatted = this.sharedService.formatValue(this.imponibileRitAcc.toString());
		}
	}

	setImportoIva() {
		if (this.importoIva !== null) {
			this.importoIvaFormatted = this.sharedService.formatValue(this.importoIva.toString());
		}
		if (this.isBR65 && this.isSpesaAmmessaInf5Milioni) {
			this.importoIvaIndetraibileFormatted = this.importoIvaFormatted;
			this.setImportoIvaIndetraibile();
		}
	}

	setImportoIvaIndetraibile() {
		this.importoIvaIndetraibile = this.sharedService.getNumberFromFormattedString(this.importoIvaIndetraibileFormatted);
		if (this.importoIvaIndetraibile !== null) {
			this.importoIvaIndetraibileFormatted = this.sharedService.formatValue(this.importoIvaIndetraibile.toString());
		}
	}

	setRendicontabile() {
		this.rendicontabile = this.sharedService.getNumberFromFormattedString(this.rendicontabileFormatted);
		if (this.rendicontabile !== null) {
			this.rendicontabileFormatted = this.sharedService.formatValue(this.rendicontabile.toString());
		}
		this.calcolaResiduoRendicontabile();
	}

	setImportoRendicontabile() {
		this.importoRendicontabile = this.sharedService.getNumberFromFormattedString(this.importoRendicontabileFormatted);
		if (this.importoRendicontabile !== null) {
			this.importoRendicontabileFormatted = this.sharedService.formatValue(this.importoRendicontabile.toString());
		}
		this.calcolaResiduoRendicontabile();
	}

	setImporto() {
		this.importo = this.sharedService.getNumberFromFormattedString(this.importoFormatted);
		if (this.importo !== null) {
			this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
		}
		this.calcolaResiduoRendicontabile();
	}

	setImportiFatturaRif() {
		if (this.riferimentoFatturaSelected.importoRendicontabile !== null) {
			this.rendicontabileFatturaRifFormatted = this.sharedService.formatValue(this.riferimentoFatturaSelected.importoRendicontabile.toString());
		} else {
			this.rendicontabileFatturaRifFormatted = '0,00';
		}
		if (this.riferimentoFatturaSelected.importoTotaleDocumentoIvato !== null) {
			this.totaleDocumentoFatturaRifFormatted = this.sharedService.formatValue(this.riferimentoFatturaSelected.importoTotaleDocumentoIvato.toString());
		} else {
			this.totaleDocumentoFatturaRifFormatted = '0,00';
		}
		if (this.riferimentoFatturaSelected.importoTotaleQuietanzato !== null) {
			this.totaleQuietanzatoFatturaRifFormatted = this.sharedService.formatValue(this.riferimentoFatturaSelected.importoTotaleQuietanzato.toString());
		} else {
			this.totaleQuietanzatoFatturaRifFormatted = '0,00';
		}
	}

	setDurata() {
		this.durataTrasferta = this.sharedService.getNumberFromFormattedString(this.durataTrasfertaFormatted);
		if (this.durataTrasferta !== null) {
			this.durataTrasfertaFormatted = this.sharedService.formatValue(this.durataTrasferta.toString());
		}
	}

	setGiorniLavorabiliMensili() {
		this.giorniLavorabiliMensili = this.sharedService.getNumberFromFormattedString(this.giorniLavorabiliMensiliFormatted);
		if (this.giorniLavorabiliMensili !== null) {
			this.giorniLavorabiliMensiliFormatted = this.sharedService.formatValue(this.giorniLavorabiliMensili.toString());
		}
		this.calcolaCompensoMensileDovuto();
	}

	setOreMensiliLavorate() {
		this.oreMensiliLavorate = this.sharedService.getNumberFromFormattedString(this.oreMensiliLavorateFormatted);
		if (this.oreMensiliLavorate !== null) {
			this.oreMensiliLavorateFormatted = this.sharedService.formatValue(this.oreMensiliLavorate.toString());
		}
		this.calcolaCompensoMensileDovuto();
	}

	setSospensioniBrevi() {
		this.sospensioniBrevi = this.sharedService.getNumberFromFormattedString(this.sospensioniBreviFormatted);
		if (this.sospensioniBrevi !== null) {
			this.sospensioniBreviFormatted = this.sharedService.formatValue(this.sospensioniBrevi.toString());
		}
		this.calcolaCompensoMensileDovuto();
	}

	setSospensioniLungheGgTotali() {
		this.sospensioniLungheGgTotali = this.sharedService.getNumberFromFormattedString(this.sospensioniLungheGgTotaliFormatted);
		if (this.sospensioniLungheGgTotali !== null) {
			this.sospensioniLungheGgTotaliFormatted = this.sharedService.formatValue(this.sospensioniLungheGgTotali.toString());
		}
		this.calcolaCompensoMensileDovuto();
	}

	setSospensioniLungheGgLav() {
		this.sospensioniLungheGgLav = this.sharedService.getNumberFromFormattedString(this.sospensioniLungheGgLavFormatted);
		if (this.sospensioniLungheGgLav !== null) {
			this.sospensioniLungheGgLavFormatted = this.sharedService.formatValue(this.sospensioniLungheGgLav.toString());
		}
		this.calcolaCompensoMensileDovuto();
	}

	calcolaCompensoMensileDovuto() {
		let GLM = this.giorniLavorabiliMensili || 0;
		let GSLL = this.sospensioniLungheGgLav || 0;
		let GSB = this.sospensioniBrevi || 0;
		let calcolo = ((GLM - GSLL) * 0.7) - GSB;
		let calcoloFixed = +(calcolo.toFixed(2));
		let CM = this.compensoMensile ? this.sharedService.getNumberFromFormattedString(this.compensoMensile) : 0;

		if (this.oreMensiliLavorate > calcoloFixed) {
			if (GLM !== 0) {
				let compensoDovuto = CM * ((GLM - GSLL) / GLM);
				this.rendicontabile = +(compensoDovuto.toFixed(0));
				this.rendicontabileFormatted = this.sharedService.formatValue(this.rendicontabile.toString());
				this.setRendicontabile();
			}
		} else {
			let OEL = this.oreMensiliLavorate || 0;
			let OMG = this.orarioMedioGiornaliero ? this.sharedService.getNumberFromFormattedString(this.orarioMedioGiornaliero) : 0;
			if (GLM !== 0 && (GLM - GSLL) !== 0 && OMG !== 0) {
				let compensoDovuto = CM * (OEL + (GSB * OMG)) * ((GLM - GSLL) / (GLM) / ((GLM - GSLL) * OMG));
				this.rendicontabile = +(compensoDovuto.toFixed(0));
				this.rendicontabileFormatted = this.sharedService.formatValue(this.rendicontabile.toString());
				this.setRendicontabile();
			}
		}
	}

	isLetturaXmlVisible() {
		if (
			!this.user ||
			!this.user.codiceRuolo ||
			(this.user.codiceRuolo !== Constants.CODICE_RUOLO_BEN_MASTER && this.user.codiceRuolo !== Constants.CODICE_RUOLO_PERSONA_FISICA && this.user.codiceRuolo !== Constants.CODICE_RUOLO_BENEFICIARIO)
		) {
			return false;
		}
		if (this.tipologiaSelected.descBreveTipoDocSpesa !== 'FT' && this.tipologiaSelected.descBreveTipoDocSpesa !== 'FF' && this.tipologiaSelected.descBreveTipoDocSpesa !== 'FL') {
			return false;
		}
		if (this.documentoSelected !== '1') {
			return false;
		}
		return true;
	}

	letturaXml() {
		this.resetMessageError();
		this.resetMessageSuccess();
		let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
			maxWidth: '100%',
			width: window.innerWidth - 100 + 'px',
			height: window.innerHeight - 50 + 'px',
			data: {
				apiURL: this.configService.getApiURL(),
				user: this.user,
				drawerExpanded: this.sharedService.getDrawerExpanded(),
				onlyOneFile: true,
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res && res.length === 1) {
				this.loadedSave = false;
				this.subscribers.letturaXml = this.documentoDiSpesaService
					.esitoLetturaXmlFattElett(res[0].idDocumentoIndex, this.user.beneficiarioSelezionato.idBeneficiario, this.idTipoOperazioneProgetto)
					.subscribe(
						data => {
							if (data) {
								if (data.esito == 'XML_NON_CORRETTO') {
									this.showMessageError('ATTENZIONE! Formato file fattura errato o formato trasmissione non previsto!');
									// Xml della fatt. elett. non formalmente valido (al momento tag <FormatoTrasmissione> non valido).
								} else if (data.esito == 'NESSUN_XML_SELEZIONATO') {
									// L'utente non ha selezionato nulla da Archivio File.
								} else if (data.esito == 'FORNITORE_NUOVO') {
									this.showMessageError('ATTENZIONE! Il fornitore rilevato nel documento non è presente in gestione fornitori.');
									// Fornitore nuovo ma non inseribile a causa dell'affidamento (comune di Vercelli, primo bando).
									this.setDatiLetturaXml(data.datiFatturaElettronica, data.fornitoreDb, res[0]);
								} else if (data.esito == 'FORNITORE_NUOVO_TIPO_OP_3') {
									// Fornitore nuovo e inseribile: forzo un click sul bottone Nuovo Fornitore, passando i dati del fornitore per precompilare la popup.
									let fornitore = new FornitoreFormDTO();
									fornitore.denominazioneFornitore = data.datiFatturaElettronica.denominazioneFornitore;
									fornitore.codiceFiscaleFornitore = data.datiFatturaElettronica.partitaIvaFornitore;
									fornitore.partitaIvaFornitore = data.datiFatturaElettronica.partitaIvaFornitore;
									fornitore.flagPubblicoPrivato = data.datiFatturaElettronica.flagPubblicoPrivatoFornitore === 'S' ? 1 : 2;
									fornitore.idTipoSoggetto = data.datiFatturaElettronica.idTipoFornitore;
									this.nuovoFornitoreXml(fornitore);
									this.setDatiLetturaXml(data.datiFatturaElettronica, data.fornitoreDb, res[0]);
								} else if (data.esito == 'FORNITORE_UGUALE') {
									// Fornitore della fattura già presente nel db.
									this.setDatiLetturaXml(data.datiFatturaElettronica, data.fornitoreDb, res[0]);
								} else if (data.esito == 'FORNITORE_DIVERSO') {
									// Fornitore della fattura già presente nel db, ma con dati diversi: non può essere modificato, quindi si usa il fornitore del db.
									this.setDatiLetturaXml(data.datiFatturaElettronica, data.fornitoreDb, res[0]);
								} else if (data.esito == 'FORNITORE_DIVERSO_TIPO_OP_3') {
									// Fornitore della fattura già presente nel db, ma con dati diversi: verrà aperta una popup per scegliere tra i 2 fornitori.
									let fornitore = new FornitoreFormDTO();
									fornitore.denominazioneFornitore = data.datiFatturaElettronica.denominazioneFornitore;
									fornitore.codiceFiscaleFornitore = data.datiFatturaElettronica.partitaIvaFornitore;
									fornitore.partitaIvaFornitore = data.datiFatturaElettronica.partitaIvaFornitore;
									fornitore.flagPubblicoPrivato = data.datiFatturaElettronica.flagPubblicoPrivatoFornitore === 'S' ? 1 : 2;
									fornitore.idTipoSoggetto = data.datiFatturaElettronica.idTipoFornitore;
									this.selezionaFornitoreXml(data.fornitoreDb, fornitore);
									this.setDatiLetturaXml(data.datiFatturaElettronica, data.fornitoreDb, res[0]);
								} else if (data.esito == 'ERRORE_NON_PREVISTO') {
									this.showMessageError('Errore in fase di lettura dal file xml.');
								}
							}
							this.loadedSave = true;
						},
						err => {
							this.handleExceptionService.handleNotBlockingError(err);
							this.showMessageError('Errore in fase di lettura dal file xml.');
							this.loadedSave = true;
						},
					);
			}
		});
	}

	setDatiLetturaXml(dati: DatiFatturaElettronica, fornitore: FornitoreFormDTO, allegato: InfoDocumentoVo) {
		if (this.idAllegatoXml && this.allegatiDocumentoDiSpesaDaSalvare.find(all => all.idDocumentoIndex === this.idAllegatoXml)) {
			this.allegatiDocumentoDiSpesaDaSalvare = this.allegatiDocumentoDiSpesaDaSalvare.filter(all => all.idDocumentoIndex !== this.idAllegatoXml);
			this.idAllegatoXml = null;
		}
		if (this.allegatiDocumentoDiSpesaDaSalvare.find(all => all.idDocumentoIndex === allegato.idDocumentoIndex) === undefined) {
			this.allegatiDocumentoDiSpesaDaSalvare.push(allegato);
			this.idAllegatoXml = allegato.idDocumentoIndex;
		}
		this.flagElettXml = dati.flagElettXml;
		this.numero = dati.numeroDocumento;
		if (dati.dataDocumentoDiSpesa) {
			this.data.setValue(this.sharedService.parseDate(dati.dataDocumentoDiSpesa));
		} else {
			this.data.setValue(null);
		}
		this.documentoSelected = dati.flagElettronico ? '1' : '2';
		this.descrizione = dati.descrizioneDocumentoDiSpesa;
		this.imponibile = dati.imponibile;
		if (this.imponibile !== null) {
			this.imponibileFormatted = this.sharedService.formatValue(this.imponibile.toString());
		}
		this.importoIva = dati.importoIva;
		if (this.importoIva !== null) {
			this.importoIvaFormatted = this.sharedService.formatValue(this.importoIva.toString());
		}
		this.totaleDocumento = dati.importoTotaleDocumentoIvato;
		if (this.totaleDocumento !== null) {
			this.totaleDocumentoFormatted = this.sharedService.formatValue(this.totaleDocumento.toString());
		}
		if (fornitore) {
			this.tipoFornitoreSelected = this.tipologieFornitore.find(t => t.id === fornitore.idTipoSoggetto);
			this.changeTipoFornitore(this.tipoFornitoreSelected, fornitore.idFornitore, dati.descrizioneAppalto, dati.idAppalto);
		} else {
			this.tipoFornitoreSelected = null;
			this.fornitoreSelected = null;
			this.cigCpaAffidamentoData = null;
			this.idAppaltocigCpaAffidamentoData = null;
		}
	}

	vaiAllaFatturaRiferimento() {
		let params = this.activatedRoute.snapshot.paramMap.get('integrativa') ? { current: this.tipoOperazioneCurrent, integrativa: true } : { current: this.tipoOperazioneCurrent };
		this.router.navigate([
			'drawer/' + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE + '/documentoDiSpesa',
			this.idProgetto,
			this.idBandoLinea,
			this.riferimentoFatturaSelected.idDocumentoDiSpesa,
			params,
		]);
	}

	vaiAllaNotaDiCreditoAssociata(idDocumentoDiSpesa: number) {
		let params = this.activatedRoute.snapshot.paramMap.get('integrativa') ? { current: this.tipoOperazioneCurrent, integrativa: true } : { current: this.tipoOperazioneCurrent };
		this.router.navigate(['drawer/' + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE + '/documentoDiSpesa', this.idProgetto, this.idBandoLinea, idDocumentoDiSpesa, params]);
	}

	showMessageSuccess(msg: string) {
		this.messageSuccess = msg;
		this.isMessageSuccessVisible = true;
		const element = document.querySelector('#scrollId');
		element.scrollIntoView();
	}

	showMessageWarning(msg: string) {
		this.messageWarning = msg;
		this.isMessageWarningVisible = true;
		const element = document.querySelector('#scrollId');
		element.scrollIntoView();
	}

	showMessageError(msg: string) {
		this.messageError = msg;
		this.isMessageErrorVisible = true;
		const element = document.querySelector('#scrollId');
		element.scrollIntoView();
	}

	showMessageError2(msg: string) {
		this.messageError2 = msg;
		this.isMessageError2Visible = true;
		const element = document.querySelector('#scroll2Id');
		element.scrollIntoView();
	}

	resetMessageSuccess() {
		this.messageSuccess = null;
		this.isMessageSuccessVisible = false;
	}

	resetMessageError() {
		this.messageError = null;
		this.isMessageErrorVisible = false;
	}

	resetMessageError2() {
		this.messageError2 = null;
		this.isMessageError2Visible = false;
	}

	resetMessageWarning() {
		this.messageWarning = null;
		this.isMessageWarningVisible = false;
	}

	openSnackBar(message: string) {
		this._snackBar.open(message, '', {
			duration: 2000,
		});
	}

	isLoading() {
		if (!this.loadedInizializzazione || !this.loadedTasks || !this.loadedTipologieDocSpesa || !this.loadedAttivita || !this.loadedFornitori
			|| !this.loadedQualificheFornitore || !this.loadedRiferimentoFattura || !this.loadedDownload || !this.loadedAllegatiFornitore || !this.loadedSave
			|| !this.loadedVociDiSpesa || !this.loadedMacroVoci || !this.loadedAssociaFiles || !this.loadedAllegatiDocSpesa || !this.loadedDisassociaAllegato
			|| !this.loadedModalitaQuietanza || !this.loadedCausaliQuietanza || !this.loadedDocumento || !this.loadedElimina || !this.loadedNoteCreditoFattura
			|| !this.loadedQualifiche || !this.loadedRegole() || !this.loadedSalCorrente || !this.loadedSpesaAmmessa
			|| !this.loadedQuotaImportAgevolato || !this.loadedDocumentazioneDaAllegare || !this.loadedParametriCompensi || !this.loadedImportoASaldo
			|| !this.loadedBandoCultura) {
			return true;
		}
		return false;
	}
}