import { DatePipe } from '@angular/common';
import { AfterContentChecked, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { MatDialog } from '@angular/material/dialog';
import { MatStepper } from '@angular/material/stepper';
import { ActivatedRoute, Router } from '@angular/router';
import { CronoprogrammaListFasiItemAllegati, DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { saveAs } from 'file-saver-es';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { CronoprogrammaFasiContainerComponent } from 'src/app/cronoprogramma/components/cronoprogramma-fasi-container/cronoprogramma-fasi-container.component';
import { CronoprogrammaComponent } from 'src/app/cronoprogramma/components/cronoprogramma/cronoprogramma.component';
import { DeclaratoriaComponent } from 'src/app/declaratoria/components/declaratoria/declaratoria.component';
import { IndicatoriComponent } from 'src/app/indicatori/components/indicatori/indicatori.component';
import { SpecchiettoSaldoComponent } from 'src/app/rendicontazione-a20/components/rendicontazione-a20/specchietto-saldo/specchietto-saldo.component';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { EsitoOperazioneVerificaDichiarazioneSpesa } from '../../commons/dto/esito-operazione-verifica-dichiarazione-spesa';
import { InizializzaInvioDichiarazioneDiSpesaDTO } from '../../commons/dto/inizializza-invio-dichiarazione-di-spesa-dto';
import { SalCorrenteDTO } from '../../commons/dto/sal-corrente-dto';
import { TipoAllegatoDTO } from '../../commons/dto/tipo-allegato-dto';
import { AnteprimaDichiarazioneDiSpesaCulturaRequest, AnteprimaDichiarazioneDiSpesaRequest } from '../../commons/requests/anteprima-dichiarazione-di-spesa-request';
import { VerificaDichiarazioneDiSpesaRequest } from '../../commons/requests/verifica-dichiarazione-di-spesa-request';
import { DichiarazioneDiSpesaService } from '../../services/dichiarazione-di-spesa.service';
import { NavigationDichiarazioneDiSpesaService } from '../../services/navigation-dichiarazione-di-spesa.service';
import { RendicontazioneService } from '../../services/rendicontazione.service';
import { DichiarazioneDiSpesaContentComponent } from '../dichiarazione-di-spesa-content/dichiarazione-di-spesa-content.component';

@Component({
	selector: 'app-dichiarazione-di-spesa',
	templateUrl: './dichiarazione-di-spesa.component.html',
	styleUrls: ['./dichiarazione-di-spesa.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DichiarazioneDiSpesaComponent implements OnInit, AfterContentChecked {
	isBandoCultura: boolean;
	idProgetto: number;
	idBandoLinea: number;
	isIntegrativa: boolean;
	beneficiario: string;
	codiceProgetto: string;
	progettoPiuGreen: boolean;
	idProgettoContributo: number;
	importoRichiestoErogazioneSaldoAmmesso: boolean;
	user: UserInfoSec;
	data: FormControl = new FormControl();
	tipoDichiarazione: string = 'I';
	tipoDichNotFinale: boolean = true;
	initDTO: InizializzaInvioDichiarazioneDiSpesaDTO;
	esitoVerificaDichiarazioneSpesa: EsitoOperazioneVerificaDichiarazioneSpesa;
	isVerificaVisible: boolean;
	cronoprogrammaObbligatorio: boolean;
	indicatoriObbligatori: boolean;
	regola30attiva: boolean;
	salCorrente: SalCorrenteDTO;
	idRouting: number;

	messageSuccess: string;
	isMessageSuccessVisible: boolean;
	messageError: string;
	isMessageErrorVisible: boolean;
	messageWarning: string;
	isMessageWarningVisible: boolean;

	isBR54: boolean = false;
	isBR56: boolean = false;
	isBR58: boolean = false;

	//LOADED
	loadedInizializza: boolean;
	loadedVerifica: boolean = true;
	loadedDownload: boolean = true;
	loadedElimina: boolean = true;
	loadedRappresentantiLegali: boolean = true;
	loadedDelegati: boolean = true;
	loadedIban: boolean = true;
	loadedTipoAllegati: boolean = true;
	loadedAllegati: boolean = true;
	loadedAssociaAllegati: boolean = true;
	loadedCrea: boolean = true;
	loadBr: boolean = true;
	loadedRegolaBR54: boolean = true;
	loadedRegolaBR56: boolean;
	loadedRegolaBR58: boolean = true;
	loadedSalCorrente: boolean = true;
	loadedDatiColonneQteSalCorrente: boolean = true;
	loadedSaldoContabile: boolean = true;

	//OBJECT SUBSCRIBER
	subscribers: any = {};

	@ViewChild(DichiarazioneDiSpesaContentComponent) contentComponent: DichiarazioneDiSpesaContentComponent;
	@ViewChild(IndicatoriComponent) indicatoriComponent: IndicatoriComponent;
	@ViewChild(DeclaratoriaComponent) declaratoriaComponent: DeclaratoriaComponent;
	@ViewChild(CronoprogrammaComponent) cronoprogrammaComponent: CronoprogrammaComponent;
	@ViewChild(CronoprogrammaFasiContainerComponent) cronoprogrammaFasiContainerComponent: CronoprogrammaFasiContainerComponent;
	@ViewChild('stepper', { static: true }) stepper: MatStepper;
	@ViewChild(SpecchiettoSaldoComponent) specchiettoSaldoComponent: SpecchiettoSaldoComponent;
	declaratoriaVisibile: any;
	isLeggeBilancioAttiva: boolean;

	constructor(
		private router: Router,
		private activatedRoute: ActivatedRoute,
		private userService: UserService,
		private handleExceptionService: HandleExceptionService,
		private dichiarazioneDiSpesaService: DichiarazioneDiSpesaService,
		private navigationService: NavigationDichiarazioneDiSpesaService,
		private rendicontazioneService: RendicontazioneService,
		private cdRef: ChangeDetectorRef,
		private datePipe: DatePipe,
		private dialog: MatDialog,
		private configService: ConfigService,
		private _adapter: DateAdapter<any>,
	) {
		this._adapter.setLocale('it');
	}

	ngOnInit(): void {
		this.subscribers.router = this.activatedRoute.parent.params.subscribe(params => {
			this.idRouting = params.id;
			if (params.id == Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE_CULTURA) {
				this.isBandoCultura = true;
			}
		});

		this.subscribers.router = this.activatedRoute.params.subscribe(params => {
			this.idProgetto = +params['idProgetto'];
			this.idBandoLinea = +params['idBandoLinea'];
		});

		this.data.setValue(new Date());
		this.isIntegrativa = this.activatedRoute.snapshot.paramMap.get('integrativa') ? true : false;
		if (this.isIntegrativa) {
			this.tipoDichiarazione = 'IN';
			this.onChangeTipoDichiarazione();
		}

		console.log(this.isIntegrativa);

		this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
			if (data) {
				this.user = data;
				this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
				this.loadedInizializza = false;
				this.subscribers.initDichiarazione = this.dichiarazioneDiSpesaService.inizializzaInvioDichiarazioneDiSpesa(this.idProgetto, this.idBandoLinea).subscribe(
					dataInizializzazione => {
						this.initDTO = dataInizializzazione;
						this.codiceProgetto = dataInizializzazione.codiceVisualizzatoProgetto;
						this.idProgettoContributo = dataInizializzazione.idProgettoContributo;
						this.progettoPiuGreen = dataInizializzazione.progettoPiuGreen;
						this.importoRichiestoErogazioneSaldoAmmesso = dataInizializzazione.importoRichiestoErogazioneSaldoAmmesso;
						this.indicatoriObbligatori = dataInizializzazione.indicatoriObbligatori;
						this.cronoprogrammaObbligatorio = dataInizializzazione.cronoprogrammaObbligatorio;
						this.declaratoriaVisibile = dataInizializzazione.regolaBR61attiva;
						//this.declaratoriaVisibile = true;
						this.isLeggeBilancioAttiva = dataInizializzazione.regolaBR60attiva;

						// Se il progetto ha associata la regola BR44 (hPiuGreen = true), è consentita solo la dichiarazione FINALE.
						if (this.progettoPiuGreen) {
							this.tipoDichiarazione = 'F';
							this.onChangeTipoDichiarazione();
						}
						this.regola30attiva = dataInizializzazione.regola30attiva;
						if (this.navigationService.tipoDichiarazioneSelezionato) {
							this.tipoDichiarazione = this.navigationService.tipoDichiarazioneSelezionato;
							this.onChangeTipoDichiarazione();
							this.isVerificaVisible = this.navigationService.lastIsVerificaVisible;
							if (this.isVerificaVisible) {
								this.verificaDichiarazioneSpesa();
							}
						}
						this.loadRegolaBR58();
						this.loadedInizializza = true;
					},
					err => {
						this.handleExceptionService.handleBlockingError(err);
					},
				);
			}
		});
		//Servizio per regola br54
		this.loadedRegolaBR54 = false;
		this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, 'BR54').subscribe(
			res => {
				if (res != undefined && res != null) {
					this.isBR54 = res;
					if (this.isBR54 && !this.isIntegrativa) {
						this.tipoDichiarazione = 'F';
						this.onChangeTipoDichiarazione();
					}
				}
				this.loadedRegolaBR54 = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.loadedRegolaBR54 = true;
			},
		);
		//Servizio per regola br56
		this.loadedRegolaBR56 = false;
		this.subscribers.regolBR56 = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, 'BR56').subscribe(
			res => {
				if (res) {
					this.isBR56 = res;
				}
				this.loadedRegolaBR56 = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.loadedRegolaBR56 = true;
			},
		);
	}

	get descBrevePagina(): string {
		return Constants.DESC_BREVE_PAGINA_HELP_DICH_SPESA;
	}

	get apiURL(): string {
		return this.configService.getApiURL();
	}

	loadRegolaBR58() {
		this.loadedRegolaBR58 = false;
		this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, 'BR58').subscribe(
			res => {
				this.isBR58 = res;
				if (this.isBR58) {
					this.loadSalCorrente();
				}
				this.loadedRegolaBR58 = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.loadedRegolaBR58 = true;
			},
		);
	}

	loadSalCorrente() {
		this.loadedSalCorrente = false;
		this.rendicontazioneService.getSALCorrente(this.idProgetto).subscribe(
			data => {
				if (data) {
					this.salCorrente = data;
					this.tipoDichiarazione = this.salCorrente.descBreveTipoDs;
					this.onChangeTipoDichiarazione();
					if (this.salCorrente.idIter) {
						this.loadDatiColonneQteSal();
					}
				}
				this.loadedSalCorrente = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.loadedSalCorrente = true;
				this.showMessageError('Errore durante il caricamento del SAL corrente.');
			},
		);
	}

	loadDatiColonneQteSal() {
		this.loadedDatiColonneQteSalCorrente = false;
		this.rendicontazioneService.getDatiColonneQteSALCorrente(this.idProgetto, this.salCorrente.idIter).subscribe(
			data => {
				if (data) {
					let msg = "Attenzione! Per procedere all'invio della DS occorre compilare i dati obbligatori del QTES: <br/>";
					let showMsg: boolean;
					for (let d of data) {
						if (!d.estremiAttoApprovazione || d.estremiAttoApprovazione.length === 0) {
							msg += '- estremi atto approvazione del ' + d.descColonnaQtes + '<br/>';
							showMsg = true;
						}
					}
					if (showMsg) {
						this.showMessageWarning(msg);
					}
				}
				this.loadedDatiColonneQteSalCorrente = true;
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.loadedDatiColonneQteSalCorrente = true;
				this.showMessageError('Errore durante il caricamento dei dati del SAL corrente.');
			},
		);
	}

	onChangeTipoDichiarazione() {
		this.tipoDichNotFinale = this.tipoDichiarazione !== 'F' ? true : false;
		this.isVerificaVisible = false;
		if (this.tipoDichiarazione === 'F') {
			this.data.setValue(new Date());
			this.data.disable();
		} else {
			this.data.enable();
		}
	}

	verificaDichiarazioneSpesa() {
		console.log('tipo dichiarazione' + this.tipoDichiarazione);

		this.resetMessageSuccess();
		this.resetMessageError();
		if (!this.data.value) {
			this.showMessageError('E’ necessario inserire almeno un campo di ricerca.');
			return;
		} else if (this.data.value > new Date()) {
			this.showMessageError("Errore: la data non puo' essere superiore alla data odierna");
			return;
		}

		this.loadedVerifica = false;
		this.isVerificaVisible = false;
		let request = new VerificaDichiarazioneDiSpesaRequest(this.idProgetto, this.idBandoLinea, this.data.value, this.user.beneficiarioSelezionato.idBeneficiario, this.tipoDichiarazione);
		this.subscribers.verifica = this.dichiarazioneDiSpesaService.verificaDichiarazioneDiSpesa(request).subscribe(
			data => {
				if (data) {
					if (!data.esito && (!data.documentiDiSpesa || data.documentiDiSpesa.length === 0)) {
						this.showMessageError(data.messaggi[0]);
					} else {
						this.contentComponent.loadData();
						this.esitoVerificaDichiarazioneSpesa = data;
						this.data.disable();
						this.isVerificaVisible = true;
					}
				}
				this.loadedVerifica = true;
			},
			err => {
				this.handleExceptionService.handleBlockingError(err);
			},
		);
	}

	get isStepperVisible() {
		// si vede se
		// - è una DS finale 
		// - è una DS cultura (ha solo finale e integrativa)
		// - è una DS con la BR58 (SAL)
		return !this.tipoDichNotFinale || this.isBR58 || this.isBandoCultura;
	}

	isTabAltriDatiDisabled() {
		if (this.isBR54 && (!this.contentComponent || !this.contentComponent.numDocSpesaEsitoPositivo)) {
			return true;
		}
		if (this.isBandoCultura) {
			if (!this.contentComponent || (this.contentComponent.rappresentanteLegaleSelected == null) || !this.contentComponent.ibanConfermato) {
				return true;
			}
		} else {
			if (!this.contentComponent || (this.contentComponent.rappresentanteLegaleSelected == null)
				|| (!this.isBR58 && !this.contentComponent.ibanConfermato)
				|| (this.importoRichiestoErogazioneSaldoAmmesso && !this.contentComponent.importoRichiestaErog)
			) {
				return true;
			}
		}

		return false;
	}

	isDeclaratoriaCompleta() {
		return this.declaratoriaComponent && !this.declaratoriaComponent.controllaFormDeclaratoria();
	}

	isDichiarazioneNonInviabile() {
		return this.specchiettoSaldoComponent && !this.specchiettoSaldoComponent.controlloDichiarazioneInviabile();
	}

	isSaldoMaggioreDiZero() {
		return this.specchiettoSaldoComponent && this.specchiettoSaldoComponent.saldoMaggioreDiZero();
	}

	isAnteprimaVisible() {
		if (!this.contentComponent) {
			return false;
		}
		return this.contentComponent.isAnteprimaVisible;
	}

	selectionStepperChange(event: any) {
		console.log(event);

		if (this.validaCampi()) {
			setTimeout(() => {
				if (event.selectedIndex === 1) {
					this.stepper.previous();
				}
			}, 1);
			return;
		}
		if (event.selectedIndex === 1 && this.contentComponent.isTipoAllegatiVisible) {
			this.salvaTipoAllegati();
		}
	}

	salvaTipoAllegati() {
		this.resetMessageError();
		this.subscribers.salvaTipoAllegati = this.dichiarazioneDiSpesaService.salvaTipoAllegati(this.getTipoAllegati(), this.tipoDichiarazione).subscribe(
			data => {
				if (data && !data.esito) {
					this.showMessageError(data.messaggio);
				}
			},
			err => {
				this.handleExceptionService.handleNotBlockingError(err);
				this.showMessageError('Errore nel salvataggio della documentazione allegata');
			},
		);
	}

	getTipoAllegati() {
		let tipoAllegati: Array<TipoAllegatoDTO>;
		if (this.contentComponent.isTipoAllegatiVisible) {
			tipoAllegati = this.contentComponent.tipoAllegati.map(
				t =>
					new TipoAllegatoDTO(
						t.descTipoAllegato,
						t.idTipoDocumentoIndex,
						t.progrBandoLineaIntervento,
						t.idDichiarazioneSpesa,
						t.idMicroSezioneModulo,
						t.descTipoAllegato.startsWith('<b>') ? 'S' : t.isChecked ? 'S' : 'N',
						t.numOrdinamentoMicroSezione,
						t.idProgetto,
					),
			);
		}
		return tipoAllegati;
	}

	validaCampi() {
		let error: boolean;
		if (this.contentComponent.numDocSpesaEsitoPositivo === 0 && this.tipoDichNotFinale) {
			this.showMessageError(
				"Nessun documento di spesa ha superato i controlli di verifica per la dichiarazione di spesa impostata. Non è possibile procedere con l'invio della dichiarazione di spesa.",
			);
			return true;
		}
		if (this.contentComponent.isRappresentantiVisible && !this.contentComponent.rappresentanteLegaleSelected) {
			this.contentComponent.rappresentanteForm.form.get('rappresentanteLegale').setErrors({ error: 'required' });
			this.contentComponent.rappresentanteForm.form.get('rappresentanteLegale').markAsTouched();
			error = true;
		}
		if (this.contentComponent.isRappresentantiVisible && !this.contentComponent.ibanConfermato && !this.isBR58) {
			this.contentComponent.rappresentanteForm.form.get('ibanConfermato').setErrors({ error: 'required' });
			this.contentComponent.rappresentanteForm.form.get('ibanConfermato').markAsTouched();
			error = true;
		}
		if (this.contentComponent.importoRichiestoErogazioneSaldoAmmesso && !this.contentComponent.importoRichiestaErog && !this.isBandoCultura) {
			this.contentComponent.importoForm.form.get('importoRichiestaErog').setErrors({ error: 'required' });
			this.contentComponent.importoForm.form.get('importoRichiestaErog').markAsTouched();
			error = true;
		}
		if (this.contentComponent.importoRichiestoErogazioneSaldoAmmesso && this.contentComponent.importoRichiestaErog && this.contentComponent.importoRichiestaErog < 0) {
			this.contentComponent.importoForm.form.get('importoRichiestaErog').setErrors({ error: 'minore' });
			this.contentComponent.importoForm.form.get('importoRichiestaErog').markAsTouched();
			error = true;
		}
		if (this.contentComponent.importoRichiestoErogazioneSaldoAmmesso && this.contentComponent.importoRichiestaErog && this.contentComponent.importoRichiestaErog > 99999999999999999.99) {
			this.contentComponent.importoForm.form.get('importoRichiestaErog').setErrors({ error: 'maggiore' });
			this.contentComponent.importoForm.form.get('importoRichiestaErog').markAsTouched();
			error = true;
		}
		if (this.contentComponent.importoForm) {
			if (this.contentComponent.importoRichiestoErogazioneSaldoAmmesso && this.contentComponent.importoForm.form.get('importoRichiestaErog').hasError('pattern') && !this.isBandoCultura) {
				error = true;
			}
		}

		if (error) {
			console.log(error);

			this.showMessageError('ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.');
		}
		return error;
	}

	anteprimaDichiarazione(isPiuGreen?: boolean) {
		this.resetMessageSuccess();
		this.resetMessageError();
		if (this.validaCampi()) {
			return;
		}
		this.loadedDownload = false;

		let request = new AnteprimaDichiarazioneDiSpesaRequest(
			this.idBandoLinea,
			this.idProgetto,
			this.progettoPiuGreen && isPiuGreen ? this.idProgettoContributo : null,
			this.user.idSoggetto,
			new Date(this.data.value),
			this.tipoDichiarazione,
			this.contentComponent.rappresentanteLegaleSelected ? this.contentComponent.rappresentanteLegaleSelected.id : null,
			this.contentComponent.delegatoSelected ? this.contentComponent.delegatoSelected.id : null,
			this.user.beneficiarioSelezionato.idBeneficiario,
			this.tipoDichiarazione === 'F' && this.importoRichiestoErogazioneSaldoAmmesso ? this.contentComponent.importoRichiestaErog : null,
			this.tipoDichiarazione === 'F' ? this.contentComponent.osservazioni : null,
			this.getTipoAllegati(),
		);
		this.subscribers.downlaodAnteprima = this.dichiarazioneDiSpesaService.anteprimaDichiarazioneDiSpesa(request).subscribe(
			res => {
				if (res) {
					let nomePdf: string = "";
					if (this.tipoDichiarazione === 'F') {
						nomePdf = "Dichiarazione_finale_con_CFP_0_" + this.datePipe.transform(new Date(), 'ddMMyyyy') + '.pdf';
					} else {
						nomePdf = "DichiarazioneDiSpesa_0_" + this.datePipe.transform(new Date(), 'ddMMyyyy') + '.pdf';
					}
					saveAs(res, nomePdf);
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

	anteprimaDichiarazioneConDeclaratoria(isPiuGreen?: boolean) {
		this.resetMessageSuccess();
		this.resetMessageError();
		if (this.validaCampi()) {
			return;
		}
		this.loadedDownload = false;

		let request = new AnteprimaDichiarazioneDiSpesaCulturaRequest(
			this.idBandoLinea,
			this.idProgetto,
			this.progettoPiuGreen && isPiuGreen ? this.idProgettoContributo : null,
			this.user.idSoggetto,
			new Date(this.data.value),
			this.tipoDichiarazione,
			this.contentComponent.rappresentanteLegaleSelected ? this.contentComponent.rappresentanteLegaleSelected.id : null,
			this.contentComponent.delegatoSelected ? this.contentComponent.delegatoSelected.id : null,
			this.user.beneficiarioSelezionato.idBeneficiario,
			this.tipoDichiarazione === 'F' && this.importoRichiestoErogazioneSaldoAmmesso ? this.contentComponent.importoRichiestaErog : null,
			this.tipoDichiarazione === 'F' ? this.contentComponent.osservazioni : null,
			this.getTipoAllegati(),
			this.declaratoriaComponent.myForm.value,
		);
		this.subscribers.downlaodAnteprima = this.dichiarazioneDiSpesaService.anteprimaDichiarazioneDiSpesaCultura(request).subscribe(
			res => {
				if (res) {
					let nomePdf: string = "";
					if (this.tipoDichiarazione === 'F') {
						nomePdf = "Dichiarazione_finale_con_CFP_0_" + this.datePipe.transform(new Date(), 'ddMMyyyy') + '.pdf';
					} else {
						nomePdf = "DichiarazioneDiSpesa_0_" + this.datePipe.transform(new Date(), 'ddMMyyyy') + '.pdf';
					}
					saveAs(res, nomePdf);
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

	isIndicatoriEmpty() {
		if (this.indicatoriComponent?.dataSource?.data?.length || this.indicatoriComponent?.dataSourceAltri?.data?.length) {
			return false;
		}
		return true;
	}

	salvaIndicatoriCronoprogramma() {
		this.resetMessageError();
		this.resetMessageSuccess();
		this.oneTime = false;
		this.indicatoriComponent.isConfermatoSuccess = false;
		this.indicatoriComponent.salva(true);
		if (!this.isBR58) {
			this.cronoprogrammaComponent.isConfermatoSuccess = false;
			this.cronoprogrammaComponent.salva(true);
		} else {
			this.cronoprogrammaFasiContainerComponent.isConfermatoSuccess = false;
			this.cronoprogrammaFasiContainerComponent.salva();
		}
	}

	controlliIndicatoriCronoprogramma() {
		let errorIndicatori: boolean;
		if (this.indicatoriObbligatori || (!this.indicatoriObbligatori && this.indicatoriComponent.isTableIndicatoriCompilata())) {
			errorIndicatori = this.indicatoriComponent.controllaDatiPerChiusura();
		}
		if (errorIndicatori) {
			return;
		}

		if (!this.isBR58) {
			let errorCronoprogramma: boolean;
			if (this.cronoprogrammaObbligatorio || (!this.cronoprogrammaObbligatorio && this.cronoprogrammaComponent.isTableCronoprogrammaCompilata())) {
				errorCronoprogramma = this.cronoprogrammaComponent.controllaDatiPerChiusura();
			}
			if (errorCronoprogramma) {
				return;
			}
		} else {
			let fasiAttiveChiusa: CronoprogrammaListFasiItemAllegati[] = this.cronoprogrammaFasiContainerComponent.cronoprogrammaFasiComponent.dataCronoprogramma.filter(d =>
				d.fasiItemList?.find(item => item.faseIterAttiva && item.flagFaseChiusa === 1),
			);
			let faseCorrenteChiusa: boolean = fasiAttiveChiusa?.find(f => f.fasiItemList[0]?.idIter === this.salCorrente?.idIter) !== undefined;
			if (!faseCorrenteChiusa || this.cronoprogrammaFasiContainerComponent.cronoprogrammaFasiComponent.checkErrori()) {
				this.showMessageError('Completare la compilazione del cronoprogramma prima di procedere alla creazione della dichiarazione di spesa.');
				return;
			}
		}

		this.creaDichiarazione();
	}

	creaDichiarazioneTest() {
		console.log(this.contentComponent.rappresentanteLegaleSelected, this.contentComponent.delegatoSelected);
	}

	creaDichiarazione() {
		this.resetMessageError();
		this.resetMessageSuccess();
		if (this.validaCampi()) {
			return;
		}
		this.loadedCrea = false;
		if (!this.isBandoCultura) {
			this.subscribers.crea = this.dichiarazioneDiSpesaService
				.inviaDichiarazioneDiSpesa(
					this.idBandoLinea,
					this.idProgetto,
					this.idProgettoContributo,
					this.user.idSoggetto,
					new Date(this.data.value),
					this.tipoDichiarazione,
					this.contentComponent.rappresentanteLegaleSelected ? this.contentComponent.rappresentanteLegaleSelected.id : null,
					this.contentComponent.delegatoSelected ? this.contentComponent.delegatoSelected.id : null,
					this.contentComponent.relazioneTecnica,
					this.getTipoAllegati(),
					this.user.beneficiarioSelezionato.idBeneficiario,
					this.tipoDichiarazione === 'F' && this.importoRichiestoErogazioneSaldoAmmesso ? this.contentComponent.importoRichiestaErog : null,
					this.tipoDichiarazione === 'F' ? this.contentComponent.osservazioni : null,
					this.isBR58,
				)
				.subscribe(
					data => {
						if (data) {
							if (data.esito) {
								this.saveDataForNavigation();
								let url =
									`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE}/invioDichiarazioneDiSpesa/` +
									`${this.idProgetto}/${this.idBandoLinea}/${data.idDichiarazioneSpesa}/${data.idDocumentoIndex}`;
								url += this.isIntegrativa ? ';integrativa=true' : '';
								url += this.contentComponent.tipoInvioDS === 'C' ? ';tipoInvio=C' : '';
								url += this.tipoDichNotFinale ? '' : ';finale=true';
								url += this.progettoPiuGreen ? ';piuGreen=true' : '';
								this.router.navigateByUrl(url);
							} else {
								this.showMessageError(data.msg);
							}
						}
						this.loadedCrea = true;
					},
					err => {
						this.handleExceptionService.handleNotBlockingError(err);
						this.showMessageError('Errore in fase di invio della dichiarazione di spesa');
						this.loadedCrea = true;
					},
				);
		} else {
			this.creaDichiarazioneCultura();
		}
	}

	creaDichiarazioneCultura() {
		this.resetMessageError();
		this.resetMessageSuccess();
		if (this.validaCampi()) {
			return;
		}
		this.loadedCrea = false;
		this.subscribers.crea = this.dichiarazioneDiSpesaService
			.inviaDichiarazioneDiSpesaCultura(
				this.idBandoLinea,
				this.idProgetto,
				this.idProgettoContributo,
				this.user.idSoggetto,
				new Date(this.data.value),
				this.tipoDichiarazione,
				this.contentComponent.rappresentanteLegaleSelected ? this.contentComponent.rappresentanteLegaleSelected.id : null,
				this.contentComponent.delegatoSelected ? this.contentComponent.delegatoSelected.id : null,
				this.contentComponent.relazioneTecnica,
				this.getTipoAllegati(),
				this.user.beneficiarioSelezionato.idBeneficiario,
				this.tipoDichiarazione === 'F' && this.importoRichiestoErogazioneSaldoAmmesso ? this.contentComponent.importoRichiestaErog : null,
				this.tipoDichiarazione === 'F' ? this.contentComponent.osservazioni : null,
				this.isBR58,
				this.declaratoriaComponent.myForm.value,
			)
			.subscribe(
				data => {
					if (data) {
						if (data.esito) {
							this.saveDataForNavigation();
							let url = `drawer/${this.idRouting}/invioDichiarazioneDiSpesa/` + `${this.idProgetto}/${this.idBandoLinea}/${data.idDichiarazioneSpesa}/${data.idDocumentoIndex}`;
							url += this.isIntegrativa ? ';integrativa=true' : '';
							url += this.contentComponent.tipoInvioDS === 'C' ? ';tipoInvio=C' : '';
							url += this.tipoDichNotFinale ? '' : ';finale=true';
							this.router.navigateByUrl(url);
						} else {
							this.showMessageError(data.msg);
						}
					}
					this.loadedCrea = true;
				},
				err => {
					this.handleExceptionService.handleNotBlockingError(err);
					this.showMessageError('Errore in fase di invio della dichiarazione di spesa');
					this.loadedCrea = true;
				},
			);
	}

	saveDataForNavigation() {
		this.contentComponent.saveDataForNavigation();
	}

	tornaAlleRendicontazioni() {
		if (!this.isBandoCultura) {
			this.saveDataForNavigation();
			this.router.navigate(['drawer/' + this.idRouting + '/rendicontazione/' + (this.isIntegrativa ? 'integrativa/' : '') + this.idProgetto + '/' + this.idBandoLinea]);
		} else {
			this.saveDataForNavigation();
			this.router.navigate(['drawer/' + this.idRouting + '/rendicontazione-a20/' + (this.isIntegrativa ? 'integrativa/' : '') + this.idProgetto + '/' + this.idBandoLinea]);
		}
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

	showMessageWarning(msg: string) {
		this.messageWarning = msg;
		this.isMessageWarningVisible = true;
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

	resetMessageWarning() {
		this.messageWarning = null;
		this.isMessageWarningVisible = false;
	}

	isLoading() {
		if (
			!this.loadedInizializza ||
			!this.loadedVerifica ||
			!this.loadedElimina ||
			!this.loadedIban ||
			!this.loadedAllegati ||
			!this.loadedRappresentantiLegali ||
			!this.loadedDelegati ||
			!this.loadedDownload ||
			!this.loadedTipoAllegati ||
			!this.loadedAssociaAllegati ||
			!this.loadedCrea ||
			!this.loadedRegolaBR54 ||
			!this.loadedRegolaBR56 ||
			!this.loadedRegolaBR58 ||
			!this.loadedSalCorrente ||
			!this.loadedDatiColonneQteSalCorrente ||
			(this.specchiettoSaldoComponent && this.specchiettoSaldoComponent.isLoading())
		) {
			return true;
		}
	}
	oneTime = false;
	ngAfterContentChecked() {
		this.cdRef.detectChanges();
		if (
			this.indicatoriComponent.isConfermatoSuccess &&
			((!this.isBR58 && this.cronoprogrammaComponent.isConfermatoSuccess) || (this.isBR58 && this.cronoprogrammaFasiContainerComponent.isConfermatoSuccess)) &&
			!this.oneTime
		) {
			this.oneTime = true;
			this.controlliIndicatoriCronoprogramma();
		}
	}
}
