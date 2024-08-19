/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { DatePipe } from "@angular/common";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DateAdapter } from '@angular/material/core';
import { MatTableDataSource } from '@angular/material/table';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { saveAs } from "file-saver-es";
import { InizializzazioneService } from "src/app/shared/services/inizializzazione.service";
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { FiltroRilevazione } from "../../commons/models/filtro-rilevazione";
import { RequestGetProgettiCampione } from "../../commons/models/request-get-progetti-campione";
import { ElenchiProgettiCampionamento } from "../../commons/models/elenchi-progetti-campionamento";
import { EsitoGenerazioneReportDTO } from "../../commons/dto/esito-generazione-report-dto";
import { EsitoCampionamentoDTO } from "../../commons/dto/esito-campionamento-dto";
import { SoggettoTrasferimento } from "../../commons/models/soggetto-trasferimento";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
import { MonitoraggioControlliService } from "../../services/monitoraggio-controlli.service";
import { NgForm } from "@angular/forms";
import { ProgettoCampione } from '../../commons/models/progetto-campione';
import { SelectionModel } from '@angular/cdk/collections';
import { RegistroControlliService2 } from 'src/app/registro-controlli/services/registro-controlli2.service';
import { MostraProgettiCampioneDialogComponent } from '../mostra-progetti-campione-dialog/mostra-progetti-campione-dialog';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-monitoraggio-controlli',
  templateUrl: './monitoraggio-controlli.component.html',
  styleUrls: ['./monitoraggio-controlli.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class MonitoraggioControlliComponent implements OnInit {

  tabIndex: number = 0;
  isHideTab2: boolean = true;
  action: string;
  isModificaLimited: boolean;

  title: string = 'Monitoraggio controlli';
  titleInserisci: string = 'Gestione dati del progetto';
  titleModifica: string = 'Gestione dati del progetto';
  titleDettaglio: string = 'Gestione dati del progetto';

  idProgetto: number;
  codiceProgetto: string;
  userBeneficiarioSelezionatoCodiceFiscale: string;
  idErogazione: number;
  user: UserInfoSec;
  inputNumberType: string = 'float';

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  messageSuccessAllegati: string;
  isMessageSuccessAllegatiVisible: boolean;
  messageAllegatiError: string;
  isMessageErrorAllegatiVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  //HEADER LABELS BUTTONS
  headerLabels: CodiceDescrizione[];
  headerButtons: CodiceDescrizione[];

  // Form - start
  @ViewChild("ricercaForm", { static: true }) ricercaForm: NgForm;
  criteriRicercaOpened: boolean = true;
  beneficiari: Array<SoggettoTrasferimento>;
  causali: CodiceDescrizione[];
  modalitaAgevolazioneContoEconomico: CodiceDescrizione[];
  modalitaErogazione: CodiceDescrizione[];

  // Tab Estrazione progetti campionati
  epcNormativaSelected: CodiceDescrizione;
  epcAsseSelected: CodiceDescrizione;
  epcBandoSelected: CodiceDescrizione;
  flagEpcTipoControllo: string;
  epcAnnoContabileSelected: CodiceDescrizione;
  epcAutoritaControlloSelected: CodiceDescrizione;
  epcNormativeLista: CodiceDescrizione[];
  epcAsseLista: CodiceDescrizione[];
  epcBandiLista: CodiceDescrizione[];
  epcAnnoContabiliLista: CodiceDescrizione[];
  epcAutoritaControlloLista: CodiceDescrizione[];
  epcElenchiProgettiCampionamenti: ElenchiProgettiCampionamento;
  epcReportCampionamento: EsitoGenerazioneReportDTO;
  epcEsitoCampionamento: EsitoCampionamentoDTO;
  epcSettoreAttivitaLista: CodiceDescrizione[];
  isEpcConsultazione: boolean;
  epcIdLineaDiIntervento: number;
  epcIdAsse: number;
  isDisabledEpcAsse: boolean;
  isDisabledEpcBandi: boolean;

  // Tab Acquisizione progetti campionati
  apcNormativaSelected: CodiceDescrizione;
  flagApcTipoControllo: string;
  apcAnnoContabileSelected: CodiceDescrizione;
  apcAutoritaControlloSelected: CodiceDescrizione;
  dtApcCampione: Date;
  fieldElencoProgettiCampionati: string;
  apcRegistraCampionamentoProgetti: any;
  apcNormativeLista: CodiceDescrizione[];
  apcAsseLista: CodiceDescrizione[];
  apcBandiLista: CodiceDescrizione[];
  apcAnnoContabiliLista: CodiceDescrizione[];
  apcAutoritaControlloLista: CodiceDescrizione[];
  apcElenchiProgettiCampionamenti: ElenchiProgettiCampionamento;
  apcReportCampionamento: EsitoGenerazioneReportDTO;
  apcEsitoCampionamento: EsitoCampionamentoDTO;
  apcSettoreAttivitaLista: CodiceDescrizione[];
  isApcConsultazione: boolean;
  apcIdLineaDiIntervento: number;
  apcIdAsse: number;

  //TABLE DA AGGIUNGERE
  displayedColumnsDaAggiungere: string[] = ['isDisabledCheckbox', 'idProgetto', 'codice', 'titolo', 'beneficiario', 'bandoLinea'];
  displayedHeadersDaAggiungere: string[] = ['isDisabledCheckbox Text', 'Id progetto', 'Codice Progetto', 'Titolo progetto', 'Beneficiario', 'Bando linea'];
  displayedFootersDaAggiungere: string[] = [];
  displayedColumnsCustomDaAggiungere: string[] = ['checkbox', '', '', '', '', ''];
  displayedHeadersCustomDaAggiungere: string[] = this.displayedColumnsCustomDaAggiungere;
  displayedFootersCustomDaAggiungere: string[] = [];
  displayedColumnsCustomDaAggiungereAzioni: any = { customHeaderCheckbox: { defaultFieldUpdate: 'isDisabled', defaultFieldBindUpdate: 'isDisabledCheckbox', defaultFiledUpdate: 'isDisabled', defaultValues: [{ codice: true, descrizione: '' }] }, customCheckbox: { defaultFieldUpdate: 'isDisabled', defaultFieldBindUpdate: 'isDisabledCheckbox', defaultFiledUpdate: 'isDisabled', defaultValues: [{ codice: true, descrizione: '' }] } };
  dataSourceDaAggiungere: MatTableDataSource<any> = new MatTableDataSource<any>();  // AnyDatasource  // any -> ModalitaAgevolazioneErogazioneDTO

  displayedColumnsPresenti: string[] = ['dataCampione', 'idProgetto', 'codiceProgetto', 'titoloProgetto', 'beneficiario', 'bandoLinea'];
  dataSourcePresenti: MatTableDataSource<ProgettoCampione>;

  displayedColumnsAssenti: string[] = ['select', 'idProgetto', 'codiceProgetto', 'titoloProgetto', 'beneficiario', 'bandoLinea'];
  dataSourceAssenti: MatTableDataSource<ProgettoCampione>;

  progettiInesistenti: Array<string>;
  progettiCampione: Array<ProgettoCampione>;

  selection = new SelectionModel<ProgettoCampione>(true, []);

  showNuovaAcquisizione: boolean;

  // LOADED
  loadedgetCodiceProgetto: boolean;
  loadedgetNormative: boolean;
  loadedgetAsse: boolean;
  loadedgetBandi: boolean;
  loadedgetAnnoContabili: boolean;
  loadedgetAutoritaControllo: boolean;
  loadedregistraCampionamentoProgetti: boolean;
  loadedgeneraReportCampionamento: boolean;
  loadedgetProgettiCampione: boolean;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private inizializzazioneService: InizializzazioneService,
    private datePipe: DatePipe,
    private monitoraggioControlliService: MonitoraggioControlliService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private _adapter: DateAdapter<any>,
    private registroControlliService2: RegistroControlliService2,
    private dialog: MatDialog
  ) {
    this._adapter.setLocale('it');
    this.dataSourcePresenti = new MatTableDataSource();
    this.dataSourceAssenti = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.progettiInesistenti = new Array<string>();

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.idIride) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.tabIndex = 0;
            this.ngOnInitTabEpc(params);
          });
        }
      }
    });
  }

  ngOnInitTabApc(params: Params): void {
    this.idProgetto = +params['id'];
    this.onInitTabApc();
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSourceAssenti.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSourceAssenti.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: ProgettoCampione): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.idProgetto + 1}`;
  }

  onInitTabApc() {
    this.isApcConsultazione = true;
    this.apcIdLineaDiIntervento = undefined;
    this.apcIdAsse = undefined;
    this.apcNormativaSelected = undefined;
    this.flagApcTipoControllo = undefined;
    this.apcAnnoContabileSelected = undefined;
    this.apcAutoritaControlloSelected = undefined;
    this.apcRegistraCampionamentoProgetti = undefined;
    this.loadApcNormative(this.isApcConsultazione);
    this.loadApcAnnoContabili(this.isApcConsultazione);
    this.loadApcAutoritaControllo(this.isApcConsultazione);
    this.assegnaApcRegistraCampionamentoProgetti(); // Todo: remove test
    this.showNuovaAcquisizione = false;
  }

  ngOnInitTabEpc(params: Params): void {
    this.idProgetto = +params['id'];
    this.onInitTabEpc();
  }

  onInitTabEpc() {
    this.isEpcConsultazione = true;
    this.epcIdLineaDiIntervento = undefined;
    this.epcIdAsse = undefined;
    this.epcNormativaSelected = undefined;
    this.epcAsseSelected = undefined;
    this.epcBandoSelected = undefined;
    this.flagEpcTipoControllo = undefined;
    this.epcAnnoContabileSelected = undefined;
    this.epcAutoritaControlloSelected = undefined;
    this.isDisabledEpcAsse = true;
    this.isDisabledEpcBandi = true;
    this.loadEpcNormative(this.isEpcConsultazione);
    this.loadEpcAnnoContabili(this.isEpcConsultazione);
    this.loadEpcAutoritaControllo(this.isEpcConsultazione);
  }

  loadDati() {
    this.loadData();
  }

  loadData() {
    this.loadCodiceProgetto();
    //this.fillTable();
  }

  loadCodiceProgetto() {
    //LOAD CODICE PROGETTO
    this.loadedgetCodiceProgetto = true;
    this.subscribers.codiceProgetto = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      this.loadedgetCodiceProgetto = false;
      if (res) {
        this.codiceProgetto = res;
        if (this.user && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.codiceFiscale) {
          this.userBeneficiarioSelezionatoCodiceFiscale = this.user.beneficiarioSelezionato.codiceFiscale
        }
      }
    }, err => {
      this.loadedgetCodiceProgetto = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
    });
  }

  // Popolamento drop normativa tab estrazione progetti campionati
  loadEpcNormative(isConsultazione: boolean, preserveValue?: boolean) { // normativaSelected
    this.loadedgetNormative = true;
    this.subscribers.normativa = this.monitoraggioControlliService.getNormative(isConsultazione).subscribe((res: CodiceDescrizione[]) => {
      this.loadedgetNormative = false;
      if (res) {
        this.epcNormativeLista = res;
      }
    }, err => {
      this.loadedgetNormative = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
    });
  }

  loadEpcAsse(idLineaDiIntervento?: number, preserveValue?: boolean) { // asseSelected
    if (!idLineaDiIntervento) {
      this.epcAsseLista = undefined;
      this.isDisabledEpcAsse = true;
    } else {
      let request: MonitoraggioControlliService.GetAsseParams = {
        isConsultazione: this.isEpcConsultazione,
        idLineaDiIntervento: idLineaDiIntervento
      };

      this.loadedgetAsse = true;
      this.subscribers.asse = this.monitoraggioControlliService.getAsse(request).subscribe((res: CodiceDescrizione[]) => {
        this.loadedgetAsse = false;
        if (res) {
          this.epcAsseLista = res;
          this.isDisabledEpcAsse = false;
        }
      }, err => {
        this.loadedgetAsse = false;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError(err.message);
      });
    }
  }

  loadEpcBandi(idAsse?: number, preserveValue?: boolean) { // bandoSelected
    if (!idAsse) {
      this.epcBandiLista = undefined;
      this.isDisabledEpcBandi = true;
    } else {
      let request: MonitoraggioControlliService.GetBandiParams = {
        isConsultazione: this.isEpcConsultazione,
        idLineaDiIntervento: this.epcIdLineaDiIntervento,
        idAsse: idAsse
      };

      this.loadedgetBandi = true;
      this.subscribers.bandi = this.monitoraggioControlliService.getBandi(request).subscribe((res: CodiceDescrizione[]) => {
        this.loadedgetBandi = false;
        if (res) {
          this.epcBandiLista = res;
          this.isDisabledEpcBandi = false;
        }
      }, err => {
        this.loadedgetBandi = false;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError(err.message);
      });
    }
  }

  loadEpcAnnoContabili(isConsultazione?: boolean, preserveValue?: boolean) { // bandoSelected

    this.loadedgetAnnoContabili = true;
    this.subscribers.annoContabili = this.monitoraggioControlliService.getAnnoContabili(isConsultazione).subscribe((res: CodiceDescrizione[]) => {
      this.loadedgetAnnoContabili = false;
      if (res) {
        this.epcAnnoContabiliLista = res;
      }
    }, err => {
      this.loadedgetAnnoContabili = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
    });
  }

  loadEpcAutoritaControllo(isConsultazione?: boolean, preserveValue?: boolean) { // bandoSelected

    this.loadedgetAutoritaControllo = true;
    this.subscribers.autoritaControllo = this.monitoraggioControlliService.getAutoritaControllo(isConsultazione).subscribe((res: CodiceDescrizione[]) => {
      this.loadedgetAutoritaControllo = false;
      if (res) {
        this.epcAutoritaControlloLista = res;
      }
    }, err => {
      this.loadedgetAutoritaControllo = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
    });
  }

  // Monitoraggio controlli - Apc - Tab Acquisizione progetti campionati
  loadApcNormative(isConsultazione: boolean, preserveValue?: boolean) { // normativaSelected

    this.loadedgetNormative = true;
    this.subscribers.normativa = this.monitoraggioControlliService.getNormative(isConsultazione).subscribe((res: CodiceDescrizione[]) => {
      this.loadedgetNormative = false;
      if (res) {
        this.apcNormativeLista = res;
      }
    }, err => {
      this.loadedgetNormative = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
    });
  }

  loadApcAnnoContabili(isConsultazione?: boolean, preserveValue?: boolean) { // bandoSelected

    this.loadedgetAnnoContabili = true;
    this.subscribers.annoContabili = this.monitoraggioControlliService.getAnnoContabili(isConsultazione).subscribe((res: CodiceDescrizione[]) => {
      this.loadedgetAnnoContabili = false;
      if (res) {
        this.apcAnnoContabiliLista = res;
      }
    }, err => {
      this.loadedgetAnnoContabili = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
    });
  }

  loadApcAutoritaControllo(isConsultazione?: boolean, preserveValue?: boolean) { // bandoSelected

    this.loadedgetAutoritaControllo = true;
    this.subscribers.autoritaControllo = this.monitoraggioControlliService.getAutoritaControllo(isConsultazione).subscribe((res: CodiceDescrizione[]) => {
      this.loadedgetAutoritaControllo = false;
      if (res) {
        this.apcAutoritaControlloLista = res;
      }
    }, err => {
      this.loadedgetAutoritaControllo = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
    });
  }

  loadEpcGeneraReportCampionamento(isConsultazione?: boolean, preserveValue?: boolean) { // bandoSelected
    let request: FiltroRilevazione = {
      dataCampione: undefined,
      descAnnoContabile: ((this.epcAnnoContabileSelected && this.epcAnnoContabileSelected.codice) ? this.epcAnnoContabileSelected.descrizione : undefined),
      elencoProgetti: undefined,
      idAnnoContabile: ((this.epcAnnoContabileSelected && this.epcAnnoContabileSelected.codice) ? this.epcAnnoContabileSelected.codice : undefined),
      idAsse: ((this.epcAsseSelected && this.epcAsseSelected.codice) ? this.epcAsseSelected.codice : undefined),
      idAutoritaControllante: ((this.epcAutoritaControlloSelected && this.epcAutoritaControlloSelected.codice) ? this.epcAutoritaControlloSelected.codice : undefined),
      idBando: ((this.epcBandoSelected && this.epcBandoSelected.codice) ? this.epcBandoSelected.codice : undefined),
      idLineaIntervento: ((this.epcNormativaSelected && this.epcNormativaSelected.codice) ? this.epcNormativaSelected.codice : undefined),
      tipoControllo: this.flagEpcTipoControllo
    };

    this.loadedgeneraReportCampionamento = true;
    this.subscribers.generaReportCampionamento = this.registroControlliService2.generaReportCampionamento(request).subscribe(data => {
      this.loadedgeneraReportCampionamento = false;
      var anno = new Date().getFullYear();
      var mese = (new Date().getMonth() + 1) < 10 ? ("0" + (new Date().getMonth() + 1)) : new Date().getMonth() + 1;
      var giorno = (new Date().getDate()) < 10 ? ("0" + new Date().getDate()) : new Date().getDate();
      var ore = (new Date().getHours()) < 10 ? ("0" + new Date().getHours()) : new Date().getHours();
      var minuti = (new Date().getMinutes()) < 10 ? ("0" + new Date().getMinutes()) : new Date().getMinutes();
      saveAs(data, "reportConsultazione_" + anno + mese + giorno + "_" + ore + minuti + ".xls");
    }, err => {
      this.loadedgeneraReportCampionamento = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
    });
  }

  isReportDisabled() {
    return !(this.epcReportCampionamento && this.epcReportCampionamento.nomeDocumento && this.epcReportCampionamento.report);
  }

  scaricaIlReport() {
    if (this.isReportDisabled()) {
      saveAs(this.epcReportCampionamento.report, this.epcReportCampionamento.nomeDocumento);
    }
  }

  loadApcRegistraCampionamentoProgetti(isConsultazione?: boolean, preserveValue?: boolean) { // bandoSelected
    let filtroRilevazione: FiltroRilevazione = {
      dataCampione: ((this.dtApcCampione) ? this.datePipe.transform(this.dtApcCampione, 'dd/MM/yyyy') : undefined),
      descAnnoContabile: ((this.epcAnnoContabileSelected && this.epcAnnoContabileSelected.codice) ? this.epcAnnoContabileSelected.descrizione : undefined),
      elencoProgetti: this.fieldElencoProgettiCampionati,
      idAnnoContabile: ((this.epcAnnoContabileSelected && this.epcAnnoContabileSelected.codice) ? this.epcAnnoContabileSelected.codice : undefined),
      idAsse: ((this.epcAsseSelected && this.epcAsseSelected.codice) ? this.epcAsseSelected.codice : undefined),
      idAutoritaControllante: 'OI', // ((this.epcAutoritaControlloSelected && this.epcAutoritaControlloSelected.codice)?this.epcAutoritaControlloSelected.codice:undefined),
      idBando: ((this.epcBandoSelected && this.epcBandoSelected.codice) ? this.epcBandoSelected.codice : undefined),
      idLineaIntervento: ((this.epcNormativaSelected && this.epcNormativaSelected.codice) ? this.epcNormativaSelected.codice : undefined),
      tipoControllo: this.flagEpcTipoControllo
    };
    let request: RequestGetProgettiCampione = {
      filtro: filtroRilevazione,
      idProgetti: ''
    };

    this.loadedregistraCampionamentoProgetti = true;
    this.subscribers.autoritaControllo = this.monitoraggioControlliService.registraCampionamentoProgetti(request).subscribe((res: EsitoCampionamentoDTO) => {
      this.loadedregistraCampionamentoProgetti = false;
      if (res) {
        this.epcEsitoCampionamento = res;
        if (res.esito) {
          this.showMessageSuccess(res.messagge);
          this.assegnaApcRegistraCampionamentoProgetti(res);
        } else if (res.esito === false) {
          this.showMessageError(res.messagge);
        } else {
          this.showMessageError('Response non valida');
        }
      }
    }, err => {
      this.loadedregistraCampionamentoProgetti = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.assegnaApcRegistraCampionamentoProgetti();
    });
  }

  assegnaApcRegistraCampionamentoProgetti(res?: any) {
    if (!res) {
      res = [];
    }
    if (true) {
      res = {
        presenti: [
          {
            idProgetto: '1',
            codice: '123',
            titolo: 'Titolo 01',
            beneficiario: 'ASl 01',
            bandoLinea: 'Bando 1'
          },
          {
            idProgetto: '2',
            codice: '456',
            titolo: 'Titolo 02',
            beneficiario: 'ASl 02',
            bandoLinea: 'Bando 2'
          }
        ],
        daAggiungere: [
          {
            isDisabled: true,
            isDisabledCheckbox: { codice: true, descrizione: '' },
            idProgetto: '1',
            codice: '123',
            titolo: 'Titolo 01',
            beneficiario: 'ASl 01',
            bandoLinea: 'Bando 1'
          },
          {
            isDisabled: true,
            isDisabledCheckbox: { codice: false, descrizione: '' },
            idProgetto: '2',
            codice: '456',
            titolo: 'Titolo 02',
            beneficiario: 'ASl 02',
            bandoLinea: 'Bando 2'
          }
        ],
        inesistenti: [
          'Inesistente 01',
          'Inesistente 02',
          'Inesistente 03'
        ]
      };
    }
    this.apcRegistraCampionamentoProgetti = res;
  }

  onTorna() {
    this.goToErogazione();
  }

  goToErogazione() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_EROGAZIONE + "/erogazione", this.idProgetto]);
  }

  isEpcFormDisabled() {
    let r: boolean = false;
    if (!(this.epcNormativaSelected && this.epcNormativaSelected.codice)) {
      r = true;
    }
    return r;
  }

  isApcFormDisabled() {
    let r: boolean = false;
    if (
      !(this.apcNormativaSelected && this.apcNormativaSelected.codice) ||
      !this.flagApcTipoControllo ||
      !(this.apcAnnoContabileSelected && this.apcAnnoContabileSelected.codice) ||
      !(this.dtApcCampione && this.datePipe.transform(this.dtApcCampione, 'dd/MM/yyyy')) ||
      !this.fieldElencoProgettiCampionati
    ) {
      r = true;
    }
    return r;
  }

  compareWithCodiceDescrizione(option: CodiceDescrizione, value: CodiceDescrizione) {
    return value && (option.codice === value.codice);
  }

  onEpcNormativeSelected(preserveValue?: boolean) {
    this.epcBandoSelected = undefined;
    this.isDisabledEpcBandi = true;
    this.epcIdLineaDiIntervento = ((this.epcNormativaSelected && this.epcNormativaSelected.codice) ? parseInt(this.epcNormativaSelected.codice) : undefined);
    this.loadEpcAsse(this.epcIdLineaDiIntervento, preserveValue);
  }

  onEpcAsseSelected(preserveValue?: boolean) {
    this.epcIdAsse = ((this.epcAsseSelected && this.epcAsseSelected.codice) ? parseInt(this.epcAsseSelected.codice) : undefined);
    this.loadEpcBandi(this.epcIdAsse, preserveValue);
  }

  onCheckboxChange = (ev: any) => {
    if (ev) {
      let e: any = ev.event;
      let row: any = ev.row;
      let column: string = ev.column;
      if (row) {  // && row[column]
        if (e.checked != undefined) {
          let ret: boolean = e.checked;
          //
        }
      }
    }
  }

  onSelectedTabChange = (e: any) => {
    //let clickedIndex = e.index;
    //this.tabIndex = clickedIndex - 1;
    if (this.tabIndex == 2) {  // Tab Soggetti
      //this.goToDatiProgettoSoggetto(this.idProgetto);
    } else if (this.tabIndex == 1) {  // Tab Sedi
      this.onInitTabApc();
    } else if (this.tabIndex == 0) {  // Tab Dati Progetto
      this.onInitTabEpc();
    } else {
      //
    }
  }

  epcRicerca() {
    this.loadEpcGeneraReportCampionamento(this.isEpcConsultazione);
  }

  //MESSAGGI SUCCESS E ERROR - start
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

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  showMessageAllegatiSuccess(msg: string) {
    this.messageSuccessAllegati = msg;
    this.isMessageSuccessAllegatiVisible = true;
    const element = document.querySelector('#scrollIAllegatid');
    element.scrollIntoView();
  }

  showMessageAllegatiError(msg: string) {
    this.messageAllegatiError = msg;
    this.isMessageErrorAllegatiVisible = true;
    const element = document.querySelector('#scrollAllegatiId');
    element.scrollIntoView();
  }

  resetMessageAllegatiSuccess() {
    this.messageSuccessAllegati = null;
    this.isMessageSuccessAllegatiVisible = false;
  }

  resetMessageAllegatiError() {
    this.messageAllegatiError = null;
    this.isMessageErrorAllegatiVisible = false;
  }
  //MESSAGGI SUCCESS E ERROR - end

  apcRicercaConferma() {
    if ((this.selection.selected.length == 0)) {
      this.showMessageError("Non è stato selezionato nessun progetto da campionare.");
    } else {
      this.resetMessageError();
      var result: string;
      this.selection.selected.forEach((value, index) => {
        if (index == 0) {
          result = value.idProgetto.toString();
        } else {
          result = result + ";" + value.idProgetto.toString();
        }
      });

      let filtroRilevazione: FiltroRilevazione = {
        idAnnoContabile: this.apcAnnoContabileSelected.codice,
        idAutoritaControllante: this.apcAutoritaControlloSelected.codice,
        tipoControllo: this.flagApcTipoControllo,
        dataCampione: this.datePipe.transform(this.dtApcCampione, 'dd/MM/yyyy'),
        idLineaIntervento: ((this.apcNormativaSelected && this.apcNormativaSelected.codice) ? this.apcNormativaSelected.codice : undefined),
        elencoProgetti: result,
      };

      let request: RequestGetProgettiCampione = {
        filtro: filtroRilevazione,
        idProgetti: null
      };

      this.loadedregistraCampionamentoProgetti = true;
      this.subscribers.autoritaControllo = this.monitoraggioControlliService.registraCampionamentoProgetti(request).subscribe((res: EsitoCampionamentoDTO) => {
        this.loadedregistraCampionamentoProgetti = false;
        if (res) {
          if (res.esito == true) {
            this.showNuovaAcquisizione = true;
            this.showMessageSuccess("L’elaborazione è terminata correttamente.");
          }
        }
      }, err => {
        this.loadedregistraCampionamentoProgetti = false;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError(err.message);
      });
    }
  }

  onApcAnnoContabileSelected() {

  }

  apcRicerca() {
    this.resetMessageError();
    this.resetMessageSuccess();

    let filtroRilevazione: FiltroRilevazione = {
      idAnnoContabile: this.apcAnnoContabileSelected.codice,
      idAutoritaControllante: this.apcAutoritaControlloSelected.codice,
      tipoControllo: this.flagApcTipoControllo,
      dataCampione: this.datePipe.transform(this.dtApcCampione, 'dd/MM/yyyy'),
      idLineaIntervento: ((this.apcNormativaSelected && this.apcNormativaSelected.codice) ? this.apcNormativaSelected.codice : undefined),
      elencoProgetti: this.fieldElencoProgettiCampionati,
    };

    let request: RequestGetProgettiCampione = {
      filtro: filtroRilevazione,
      idProgetti: null
    };

    this.loadedgetProgettiCampione = true;
    this.subscribers.autoritaControllo = this.monitoraggioControlliService.getProgettiCampione(request).subscribe((res: ElenchiProgettiCampionamento) => {
      this.loadedgetProgettiCampione = false;
      if (res) {
        this.apcElenchiProgettiCampionamenti = res;
        this.selection = new SelectionModel<ProgettoCampione>(true, []);
        this.dataSourcePresenti = new MatTableDataSource(res.progettiGiaPresenti);
        this.dataSourceAssenti = new MatTableDataSource(res.progettiDaAggiungere);
        this.progettiCampione = res.progettiDelCampione;
        this.splitProgettiInesistenti(res.progettiScartati);
        this.masterToggle();
      }
    }, err => {
      this.loadedgetProgettiCampione = false;
      this.handleExceptionService.handleNotBlockingError(err);
      if (err.error.msg == "RequestGetProgettiCampione.filtro.elencoProgetti L'elenco dei progetti campionati non &egrave; scritto correttamente.") {
        this.showMessageError("L'elenco dei progetti campionati non è scritto correttamente.");
      } else {
        this.showMessageError(err.error.msg);
      }

    });
  }

  splitProgettiInesistenti(value: string) {
    this.progettiInesistenti = new Array<string>();
    this.progettiInesistenti = value.split(";");
  }

  nuovaAcquiszione() {
    this.showNuovaAcquisizione = false;
    this.apcNormativaSelected = undefined;
    this.flagApcTipoControllo = undefined;
    this.apcAnnoContabileSelected = undefined;
    this.apcAutoritaControlloSelected = undefined;
    this.dtApcCampione = undefined;
    this.fieldElencoProgettiCampionati = undefined;
    this.dataSourcePresenti = new MatTableDataSource();
    this.dataSourceAssenti = new MatTableDataSource();
    this.progettiInesistenti = new Array<string>();
    this.resetMessageError();
    this.resetMessageSuccess();
  }

  mostraProgetti() {
    const dialogRef = this.dialog.open(MostraProgettiCampioneDialogComponent, {
      data: this.progettiCampione
    });
  }

  isLoading() {
    if (this.loadedgetCodiceProgetto || this.loadedgetNormative || this.loadedgetAsse || this.loadedgetBandi || this.loadedgetAnnoContabili || this.loadedgetAutoritaControllo || this.loadedregistraCampionamentoProgetti || this.loadedgeneraReportCampionamento || this.loadedgetProgettiCampione) {
      return true;
    } else {
      return false;
    }
  }

  changeElencoProgetti() {
    this.fieldElencoProgettiCampionati = this.fieldElencoProgettiCampionati.replace(/\n/g, ";");
  }
}