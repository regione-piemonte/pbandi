import { AfterViewInit, ChangeDetectorRef, Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { DatiProgettoAttivitaPregresseDialogComponent, HandleExceptionService, UserInfoSec, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { DocumentiDiSpesaService } from 'src/app/rendicontazione-a20/services/documenti-di-spesa.service';
import { DecodificaDTO } from 'src/app/rendicontazione/commons/dto/decodifica-dto';
import { ElencoDocumentiSpesaItem } from 'src/app/rendicontazione/commons/dto/elenco-documenti-spesa-item';
import { FiltroRicercaRendicontazionePartners } from 'src/app/rendicontazione/commons/dto/filtro-ricerca-rendicontazione-partners';
import { VoceDiSpesaFiglia, VoceDiSpesaPadre } from 'src/app/rendicontazione/commons/dto/voce-di-spesa';
import { FiltroRicercaDocumentiSpesa } from 'src/app/rendicontazione/commons/requests/filtro-ricerca-documenti-spesa';
import { TipologiaDocumentoSpesaVo } from 'src/app/rendicontazione/commons/vo/tipologia-documento-spesa-vo';
import { AssociaDocumentoProgettoDialogComponent } from 'src/app/rendicontazione/components/associa-documento-progetto-dialog/associa-documento-progetto-dialog.component';
import { DocumentoDiSpesaService } from 'src/app/rendicontazione/services/documento-di-spesa.service';
import { ExcelRendicontazioneService } from 'src/app/rendicontazione/services/excel-rendicontazione.service';
import { NavigationRendicontazioneService } from 'src/app/rendicontazione/services/navigation-rendicontazione.service';
import { RendicontazioneService } from 'src/app/rendicontazione/services/rendicontazione.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
	selector: 'app-documenti-di-spesa',
	templateUrl: './documenti-di-spesa.component.html',
	styleUrls: ['./documenti-di-spesa.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DocumentiDiSpesaComponent implements OnInit, AfterViewInit {
	@Input() isBandoCultura;
	idProgetto: number;
	idBandoLinea: number;
	isIntegrativa: boolean;
	criteriRicercaOpened: boolean = true;
	isPrimaRicerca: boolean = true;
	documentiRadio: string = '1';
	tipologiaSelected: TipologiaDocumentoSpesaVo;
	categoriaSelected;
	statoSelected: DecodificaDTO;
	fornitoreSelected: DecodificaDTO;
	voceDiSpesaSelected: VoceDiSpesaFiglia;
	taskSelected: string;
	rendicontazioneSelected: DecodificaDTO;
	numero: string;
	dataDa: FormControl = new FormControl();
	dataA: FormControl = new FormControl();
	beneficiario: string;
	codiceProgetto: string;
	user: UserInfoSec;
	taskVisibile: boolean;
	isReadOnly: boolean;
	isBR58: boolean;
	hasDocumentoDichiarabile: boolean;

	tipologie: Array<TipologiaDocumentoSpesaVo>;
	categorie: Array<any>;
	stati: Array<DecodificaDTO>;
	fornitori: Array<DecodificaDTO>;
	vociDiSpesa: Array<VoceDiSpesaPadre>;
	tasks: Array<string>;
	rendicontazioneCombo: FiltroRicercaRendicontazionePartners;

	documentiSpesa: Array<ElencoDocumentiSpesaItem>;
	documentiSpesaExcel: Array<ElencoDocumentiSpesaItem>;
	isDocSpesaVisible: boolean;
	esitoRicerca: boolean;

	displayedColumns: string[] = ['tipologia', 'progetto', 'fornitore', 'datanum', 'stato', 'rendicontazione', 'validato', 'azioni'];
	dataSource: MatTableDataSource<ElencoDocumentiSpesaItem>;

	@ViewChild(MatPaginator) paginator: MatPaginator;
	@ViewChild(MatSort) sort: MatSort;
	@ViewChild('ricercaForm', { static: true }) ricercaForm: NgForm;

	messageSuccess: string;
	isMessageSuccessVisible: boolean;
	messageError: string;
	isMessageErrorVisible: boolean;
	messageInfo: string;
	isMessageInfoVisible: boolean;

	//LOADED
	loadedTipologie: boolean;
	loadedCategorie: boolean;
	loadedTasks: boolean;
	loadedStati: boolean;
	loadedFornitori: boolean;
	loadedVociSpesa: boolean;
	loadedRendicontazione: boolean;
	loadedCerca: boolean = true;
	loadedChiudiAttivita: boolean = true;
	loadedElimina: boolean = true;

	//OBJECT SUBSCRIBER
	subscribers: any = {};

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private configService: ConfigService,
		private rendicontazioneService: RendicontazioneService,
		private documentoDiSpesaService: DocumentoDiSpesaService,
		private documentiDiSpesaService: DocumentiDiSpesaService,
		private handleExceptionService: HandleExceptionService,
		private navigationService: NavigationRendicontazioneService,
		private sharedService: SharedService,
		private cdRef: ChangeDetectorRef,
		private userService: UserService,
		private excelRendicontazioneService: ExcelRendicontazioneService,
		private dialog: MatDialog,
		private _adapter: DateAdapter<any>,
	) {
		this._adapter.setLocale('it');
	}

	ngOnInit(): void {
		this.subscribers.router = this.activatedRoute.params.subscribe(params => {
			this.idProgetto = +params['idProgetto'];
			this.idBandoLinea = +params['idBandoLinea'];
			this.isIntegrativa = params['integrativa'] ? true : false;
		});
		this.inizializza();
	}

	inizializza() {
		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
			if (data) {
				this.user = data;
				this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
				if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADA_OPE_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_GDF || this.user.codiceRuolo === Constants.CODICE_RUOLO_OPE_SUP_IST) {
					this.isReadOnly = true;
				}

				this.rendicontazioneService.inizializzaRendicontazione(this.idProgetto, this.idBandoLinea, this.user.idSoggetto, this.user.codiceRuolo).subscribe(
					dataInizializzazione => {
						this.taskVisibile = dataInizializzazione.taskVisibile;
						this.codiceProgetto = dataInizializzazione.codiceVisualizzatoProgetto;
						if (!this.isIntegrativa && dataInizializzazione.solaVisualizzazione.esito) {
							this.isReadOnly = true;
							this.messageInfo = dataInizializzazione.solaVisualizzazione.codiceMessaggio;
							this.isMessageInfoVisible = true;
						}
						this.isBR58 = dataInizializzazione.isBR58;
						this.hasDocumentoDichiarabile = dataInizializzazione.hasDocumentoDichiarabile;
						this.loadData();
					},
					err => {
						this.handleExceptionService.handleBlockingError(err);
					},
				);
			}
		});
	}

	loadData() {
		let filtro: FiltroRicercaDocumentiSpesa;
		this.isDocSpesaVisible = this.navigationService.lastElencoDocSpesaVisible;
		if (this.navigationService.filtroRicercaDocumentiSpesaSelezionato) {
			filtro = this.navigationService.filtroRicercaDocumentiSpesaSelezionato;
			if (this.isDocSpesaVisible) {
				this.ricerca(filtro);
			}
			this.numero = filtro.numero;
			this.documentiRadio = filtro.documentiDiSpesa === Constants.VALUE_RADIO_DOCUMENTI_DI_SPESA_GESTITI ? '1' : '2';
			this.dataDa.setValue(filtro.data);
			this.dataA.setValue(filtro.dataA);
		} else {
			this.ricerca(null);
		}
		this.loadedRendicontazione = false;
		this.subscribers.partners = this.documentoDiSpesaService.getPartners(this.idProgetto, this.idBandoLinea).subscribe(
			data => {
				if (data) {
					this.rendicontazioneCombo = data;
					if (filtro && filtro.partner) {
						this.rendicontazioneSelected = this.rendicontazioneCombo.opzioni ? this.rendicontazioneCombo.opzioni.find(o => o.descrizioneBreve === filtro.partner) : null;
						if (!this.rendicontazioneSelected) {
							this.rendicontazioneSelected = this.rendicontazioneCombo.partners ? this.rendicontazioneCombo.partners.find(p => p.descrizioneBreve === filtro.partner) : null;
						}
					} else {
						this.rendicontazioneSelected = this.rendicontazioneCombo.opzioni ? this.rendicontazioneCombo.opzioni[0] : null;
					}
				}
				this.loadedRendicontazione = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
		this.loadedTasks = false;
		this.subscribers.tasks = this.rendicontazioneService.getElencoTask(this.user, this.idProgetto).subscribe(
			data => {
				if (data) {
					this.tasks = data;
					this.taskSelected = filtro && filtro.task ? filtro.task : null;
				}
				this.loadedTasks = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
		this.loadedTipologie = false;
		this.subscribers.tipologie = this.documentiDiSpesaService.getTipologieDocumento(this.idBandoLinea.toString()).subscribe(
			data => {
				if (data) {
					this.tipologie = data;
					this.tipologiaSelected = filtro && filtro.idTipologia ? this.tipologie.find(t => t.idTipoDocumentoSpesa === filtro.idTipologia) : null;
				}
				this.loadedTipologie = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);

		this.loadedCategorie = false;
		this.subscribers.categorie = this.documentiDiSpesaService.getCategorie(this.idProgetto).subscribe(
			data => {
				if (data) {
					this.categorie = data;
					this.categoriaSelected = filtro && filtro.idCategoria ? this.categorie.find(t => t.idTipologiaVoceDiSpesa === filtro.idCategoria) : null;
				}
				this.loadedCategorie = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);

		this.loadedStati = false;
		this.subscribers.stati = this.rendicontazioneService.getStatiDocumentoDiSpesa().subscribe(
			data => {
				if (data) {
					this.stati = data;
					if (this.isReadOnly && this.messageInfo) {
						this.statoSelected = null;
					} else {
						this.statoSelected = filtro ? this.stati.find(s => s.id === filtro.idStato) : this.stati.find(s => s.id === 0);
					}
				}
				this.loadedStati = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
		this.loadedFornitori = false;
		this.subscribers.fornitori = this.documentiDiSpesaService.getFornitori(this.idProgetto).subscribe(
			data => {
				if (data) {
					this.fornitori = data;
					this.fornitoreSelected = filtro && filtro.idFornitore ? this.fornitori.find(f => f.id === filtro.idFornitore) : null;
				}
				this.loadedFornitori = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
		this.loadedVociSpesa = false;
		this.subscribers.voci = this.documentiDiSpesaService.getVociDiSpesaRicerca(this.idProgetto, this.isBandoCultura).subscribe(
			data => {
				if (data) {
					this.vociDiSpesa = data;
					for (let voce of this.vociDiSpesa) {
						this.voceDiSpesaSelected = filtro && filtro.idVoceDiSpesa ? voce.vociDiSpesaFiglie.find(v => v.idVoceDiSpesa === filtro.idVoceDiSpesa) : null;
						if (this.voceDiSpesaSelected) {
							break;
						}
					}
				}
				this.loadedVociSpesa = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	ngAfterViewInit(): void {
		if (this.paginator && this.dataSource) {
			this.paginator.length = this.documentiSpesa.length;
			this.paginator.pageIndex = 0;
			this.dataSource.paginator = this.paginator;
			this.dataSource.sort = this.sort;
		}
		this.cdRef.detectChanges();
	}

	criteriRicercaOpenClose() {
		this.criteriRicercaOpened = !this.criteriRicercaOpened;
	}

	setFiltro() {
		let stato: number;
		if (this.isPrimaRicerca) {
			if (this.isReadOnly && this.messageInfo) {
				//se rendicontazione in sola visualizzazione per comunic. fine progetto / rinuncia / progetto chiuso, ricerco tutti gli stati
				stato = null;
			} else {
				stato = 0;
			}
		} else {
			stato = this.statoSelected ? this.statoSelected.id : null;
		}
		let filtro = new FiltroRicercaDocumentiSpesa(
			this.rendicontazioneSelected ? this.rendicontazioneSelected.descrizioneBreve : Constants.VALUE_RADIO_RENDICONTAZIONE_CAPOFILA,
			//this.documentiRadio === "1" ? Constants.VALUE_RADIO_DOCUMENTI_DI_SPESA_GESTITI : Constants.VALUE_RADIO_DOCUMENTI_DI_SPESA_TUTTI,
			(this.documentiRadio = null),
			this.tipologiaSelected ? this.tipologiaSelected.idTipoDocumentoSpesa : null,
			(stato = null),
			this.fornitoreSelected ? this.fornitoreSelected.id : null,
			this.voceDiSpesaSelected ? this.voceDiSpesaSelected.idVoceDiSpesa : null,
			this.numero,
			this.dataDa.value ? new Date(this.dataDa.value) : null,
			this.dataA.value ? new Date(this.dataA.value) : null,
			this.taskSelected ? this.taskSelected : null,
			this.categoriaSelected ? this.categoriaSelected.idTipologiaVoceDiSpesa : null,
		);
		if (this.isPrimaRicerca) {
			this.isPrimaRicerca = false;
		}
		return filtro;
	}

	ricerca(filtro: FiltroRicercaDocumentiSpesa, isMessageSuccess?: boolean) {
		if (!isMessageSuccess) {
			this.resetMessageSuccess();
		}
		this.resetMessageError();
		if (this.dataDa.value && this.dataA.value && new Date(this.dataDa.value) > new Date(this.dataA.value)) {
			this.showMessageError("Il campo 'Data dal' deve essere precedente al campo 'Data al'.");
			return;
		}
		this.loadedCerca = false;
		this.subscribers.ricerca = this.documentoDiSpesaService
			.ricercaDocumentiDiSpesa(this.idProgetto, this.codiceProgetto, this.user.beneficiarioSelezionato.idBeneficiario, this.user.codiceRuolo, filtro ? filtro : this.setFiltro())
			.subscribe(
				data => {
					if (data) {
						this.esitoRicerca = data.esito;
						if (data.esito) {
							this.isDocSpesaVisible = true;
							this.criteriRicercaOpened = false;
							this.documentiSpesa = data.documenti.filter(doc => doc.idProgetto === this.idProgetto);
							this.documentiSpesaExcel = new Array<ElencoDocumentiSpesaItem>();
							this.documentiSpesa.forEach(q => this.documentiSpesaExcel.push(Object.assign({}, q)));
							this.documentiSpesaExcel.forEach(d => {
								d.estremi = d.estremi.replace('\n', ' ');
								d.fornitore = d.fornitore.replace('\n', ' ');
							});
							this.documentiSpesa.forEach(d => {
								d.progetti = d.progetti.replace(new RegExp(d.progetto), "<span class='boldText'>" + d.progetto + '</span>');
								d.stato = d.stato.replace(/\nVALIDATO/g, "\n<span class='greenColor'>VALIDATO</span>");
								d.stato = d.stato.replace(/^VALIDATO/, "<span class='greenColor'>VALIDATO</span>");
								d.stato = d.stato.replace(/\nNON VALIDATO/g, "\n<span class='red-color'>NON VALIDATO</span>");
								d.stato = d.stato.replace(/^NON VALIDATO/, "<span class='red-color'>NON VALIDATO</span>");
								d.stato = d.stato.replace(/\nIN VALIDAZIONE/g, "\n<span class='orange-color'>IN VALIDAZIONE</span>");
								d.stato = d.stato.replace(/^IN VALIDAZIONE/, "<span class='orange-color'>IN VALIDAZIONE</span>");
								d.stato = d.stato.replace(/\nPARZIALMENTE VALIDATO/g, "\n<span class='purpleColor'>PARZIALMENTE VALIDATO</span>");
								d.stato = d.stato.replace(/^PARZIALMENTE VALIDATO/, "<span class='purpleColor'>PARZIALMENTE VALIDATO</span>");
								d.stato = d.stato.replace(/\nDICHIARABILE/g, "\n<span class='blue-color'>DICHIARABILE</span>");
								d.stato = d.stato.replace(/^DICHIARABILE/, "<span class='blue-color'>DICHIARABILE</span>");
							});
							this.dataSource = new MatTableDataSource<ElencoDocumentiSpesaItem>(this.documentiSpesa);
							if (filtro) {
								this.ripristinaSortPaginator();
							} else {
								this.paginator.length = this.documentiSpesa.length;
								this.paginator.pageIndex = 0;
								this.dataSource.paginator = this.paginator;
								this.setSort();
								this.dataSource.sort = this.sort;
							}
						} else {
							this.isDocSpesaVisible = true;
							this.criteriRicercaOpened = true;
							this.showMessageError(data.messaggio);
						}
					}
					this.loadedCerca = true;
				},
				err => {
					this.handleExceptionService.handleBlockingError(err);
				},
			);
	}

	setSort() {
		this.dataSource.sortingDataAccessor = (item, property) => {
			switch (property) {
				case 'datanum':
					return item.estremi;
				case 'rendicontazione':
					return this.sharedService.getNumberFromFormattedString(item.importi);
				default:
					return item[property];
			}
		};
	}

	eliminaDocumento(idDocumento: string) {
		let doc = this.documentiSpesa.find(d => d.idDocumento === idDocumento);
		let dialogRef = this.dialog.open(ConfermaDialog, {
			width: '40%',
			data: {
				message: "Confermi l'eliminazione del documento " + doc.tipologia + '?',
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res === 'SI') {
				this.loadedElimina = false;
				this.subscribers.elimina = this.documentoDiSpesaService.eliminaDocumentoDiSpesa(this.idProgetto, +idDocumento).subscribe(
					data => {
						if (data) {
							if (data.esito) {
								this.showMessageSuccess(data.messaggio);
								if (this.isBR58) {
									this.hasDocumentoDichiarabile = false;
								}
								this.ricerca(null, true);
							} else {
								this.showMessageError(data.messaggio);
							}
						}
						this.loadedElimina = true;
					},
					err => {
						this.handleExceptionService.handleNotBlockingError(err);
						this.loadedElimina = true;
						this.showMessageError("Errore nell'eliminazione del documento.");
					},
				);
			}
		});
	}

	openDialogAssociaDocumento(documento: ElencoDocumentiSpesaItem) {
		documento.associaButtonClicked = true;
		let dialogRef = this.dialog.open(AssociaDocumentoProgettoDialogComponent, {
			width: '40%',
			data: {
				idDocumentoDiSpesa: +documento.idDocumento,
				idProgetto: this.idProgetto,
				idProgettoDocumento: documento.idProgetto,
			},
		});
		dialogRef.afterClosed().subscribe(res => {
			if (res) {
				this.showMessageSuccess(res);
				this.ricerca(null, true);
			}
			documento.associaButtonClicked = false;
		});
	}

	downloadExcel() {
		this.excelRendicontazioneService.generaExcel(this.documentiSpesaExcel);
	}

	goToDettaglioDocumento(idDocumento: string) {
		this.saveDataForNavigation();
		let params = this.isIntegrativa ? { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO, integrativa: true } : { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO };
		this.router.navigate(['drawer/' + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE_CULTURA + '/documentoDiSpesa', this.idProgetto, this.idBandoLinea, idDocumento, params]);
	}

	goToModificaDocumento(idDocumento: string) {
		this.saveDataForNavigation();
		let params = this.isIntegrativa ? { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA, integrativa: true } : { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA };
		this.router.navigate(['drawer/' + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE_CULTURA + '/documentoDiSpesa', this.idProgetto, this.idBandoLinea, idDocumento, params]);
	}

	clonaDocumento(idDocumento: string) {
		this.saveDataForNavigation();
		let params = this.isIntegrativa ? { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA, integrativa: true } : { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA };
		this.router.navigate(['drawer/' + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE_CULTURA + '/documentoDiSpesa', this.idProgetto, this.idBandoLinea, idDocumento, params]);
	}

	showMessageSuccess(msg: string) {
		this.messageSuccess = msg;
		this.isMessageSuccessVisible = true;
		const element = document.querySelector('#scrollId');
		element.scrollIntoView();
	}

	showMessageError(msg: string) {
		this.messageError = msg;
		this.isMessageErrorVisible = true;
		const element = document.querySelector('#scrollId');
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

	nuovoDocumentoDiSpesa() {
		this.saveDataForNavigation();
		let url = `drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE_CULTURA}/documentoDiSpesa/${this.idProgetto}/${this.idBandoLinea}`;
		url += this.isIntegrativa ? ';integrativa=true' : '';
		this.router.navigateByUrl(url);
	}

	dichiarazioneDiSpesa() {
		this.saveDataForNavigation();
		let url = `drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE_CULTURA}/dichiarazioneDiSpesa/${this.idProgetto}/${this.idBandoLinea}`;
		url += this.isIntegrativa ? ';integrativa=true' : '';
		this.router.navigateByUrl(url);
	}

	saveDataForNavigation() {
		this.navigationService.filtroRicercaDocumentiSpesaSelezionato = this.setFiltro();
		this.navigationService.lastElencoDocSpesaVisible = this.isDocSpesaVisible;
		this.salvaSortPaginator();
	}

	salvaSortPaginator() {
		if (this.dataSource) {
			this.navigationService.sortDirectionTable = this.dataSource.sort.direction;
			this.navigationService.sortActiveTable = this.dataSource.sort.active;
			this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
			this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
		}
	}

	ripristinaSortPaginator() {
		if (this.navigationService.sortDirectionTable != null && this.navigationService.sortDirectionTable != undefined) {
			this.sort.direction = this.navigationService.sortDirectionTable;
			this.sort.active = this.navigationService.sortActiveTable;
			this.setSort();
			this.dataSource.sort = this.sort;
		}
		if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
			this.paginator.pageIndex = this.navigationService.paginatorPageIndexTable;
			this.paginator.pageSize = this.navigationService.paginatorPageSizeTable;
			this.dataSource.paginator = this.paginator;
		}
	}

	isLoading() {
		if (
			!this.loadedChiudiAttivita ||
			!this.loadedTipologie ||
			!this.loadedStati ||
			!this.loadedTasks ||
			!this.loadedFornitori ||
			!this.loadedVociSpesa ||
			!this.loadedRendicontazione ||
			!this.loadedCerca ||
			!this.loadedElimina
		) {
			return true;
		}
		return false;
	}
}
