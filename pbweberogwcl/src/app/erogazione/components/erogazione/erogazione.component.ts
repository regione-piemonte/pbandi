/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ConfigService } from "../../../core/services/config.service";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { SharedService } from 'src/app/shared/services/shared.service';
import { InizializzazioneService } from "src/app/shared/services/inizializzazione.service";
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { EsitoOperazioni } from "../../commons/models/esito-operazioni";
import { ErogazioneService } from "../../services/erogazione.service";
import { SoggettoTrasferimento } from "../../commons/models/soggetto-trasferimento";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
import { CodiceDescrizioneCausale } from "../../commons/models/codice-descrizione-causale";
import { EsitoErogazioneDTO } from "../../commons/dto/esito-erogazione-dto";
import { GetDatiCalcolatiRequest } from "../../commons/requests/get-dati-calcolati-request";
import { DatiCalcolati } from "../../commons/models/dati-calcolati";
import { Erogazione as ErogazioneItem } from "../../commons/models/erogazione";
import { FideiussioneDTO } from "../../commons/dto/fideiussione-dto";
import { RichiestaErogazione } from "../../commons/models/richiesta-erogazione";
import { SalvaErogazioneRequest } from "../../commons/requests/salva-erogazione-request";
import { NgForm } from "@angular/forms";
import { Observable as __Observable } from 'rxjs';
import { DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { ModalitaAgevolazioneErogazioneTableDTO } from '../../commons/dto/modalita-agevolazione-erogazione-table-dto';

@Component({
  selector: 'app-erogazione',
  templateUrl: './erogazione.component.html',
  styleUrls: ['./erogazione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ErogazioneComponent implements OnInit {

  idDocumentoDiSpesa: number;
  idProgetto: number;
  idBandoLinea: number;
  codiceProgetto: string;
  tipoOperazioneCurrent: string;
  tipoOperazioneFrom: string;
  user: UserInfoSec;
  isNuovaErogazioneVisible: boolean;

  //LOADED
  loadedChiudiAttivita: boolean = true;
  loadedCodiceProgetto: boolean;
  loadedCausali: boolean;
  loadedModalitaAgevolazioneContoEconomico: boolean;
  loadedModalitaErogazione: boolean;
  loadedCodiciDirezione: boolean;
  loadedErogazioni: boolean;
  loadedEliminaErogazioni: boolean;
  loadedRiepilogoRichiestaErogazione: boolean;
  loadedDatiCalcolati: boolean;
  loadedErogazioniDatiCalcolati: boolean;
  loadedRicerca: boolean;
  loadedElimina: boolean = true;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  //HEADER LABELS BUTTONS
  headerLabels: CodiceDescrizione[];
  headerButtons: CodiceDescrizione[];

  //FORM
  @ViewChild("ricercaForm", { static: true }) ricercaForm: NgForm;
  criteriRicercaOpened: boolean = true;
  beneficiari: Array<SoggettoTrasferimento>;
  causali: CodiceDescrizione[];
  modalitaAgevolazioneContoEconomico: CodiceDescrizione[];
  modalitaErogazione: CodiceDescrizione[];
  codiciDirezione: CodiceDescrizione[];
  erogazione: EsitoErogazioneDTO;
  datiCalcolati: DatiCalcolati;
  beneficiarioSelected: SoggettoTrasferimento;
  causaleSelected: CodiceDescrizione;
  modalitaAgevolazioneContoEconomicoSelected: CodiceDescrizione;
  modalitaErogazioneSelected: CodiceDescrizione;
  codiciDirezioneSelected: CodiceDescrizione;
  dataDal: Date;
  dataAl: Date;
  codiceTrasferimento: string;
  tipo: string = '0';
  disabilitaNuovaErogazione: boolean;

  //TABLE
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  dataRequest: SalvaErogazioneRequest;
  dataResponse: any;
  displayedColumns: string[] = ['data', 'causale', 'denominazionebeneficiario', 'importotrasferimento', 'codicetrasferimento', 'flagpubblicoprivato', 'azioni'];
  dataSource: MatTableDataSource<ErogazioneItem> = new MatTableDataSource<ErogazioneItem>();
  //TABLE FIDEIUSSIONI
  displayedColumnsFideiussioni: string[] = ['numero', 'dataDecorrenza', 'importo', 'dataScadenza', 'descrizioneTipoTrattamento'];
  displayedHeadersFideiussioni: string[] = ['Numero', 'Data', 'Importo', 'Data scadenza', 'Tipo trattamento'];
  displayedFootersFideiussioni: string[] = [];
  displayedColumnsCustomFideiussioni: string[] = ['', 'date', 'number', 'date', ''];
  displayedHeadersCustomFideiussioni: string[] = this.displayedColumnsCustomFideiussioni;
  displayedFootersCustomFideiussioni: string[] = [];
  dataSourceFideiussioni: MatTableDataSource<FideiussioneDTO> = new MatTableDataSource<FideiussioneDTO>();
  //TABLE RICHIESTA EROGAZIONE
  displayedColumnsRichiestaErogazione: string[] = ['descCausaleErogazione', 'numeroRichiestaErogazione', 'dataRichiestaErogazione', 'importoRichiestaErogazione'];
  displayedHeadersRichiestaErogazione: string[] = ['Causale erogazione', 'Numero', 'Data', 'Importo richiesto'];
  displayedFootersRichiestaErogazione: string[] = [];
  displayedColumnsCustomRichiestaErogazione: string[] = ['', '', 'date', 'number'];
  displayedHeadersCustomRichiestaErogazione: string[] = this.displayedColumnsCustomRichiestaErogazione;
  displayedFootersCustomRichiestaErogazione: string[] = [];
  dataSourceRichiestaErogazione: MatTableDataSource<RichiestaErogazione> = new MatTableDataSource<RichiestaErogazione>();
  //TABLE MODALITA AGEVOLAZIONE EROGAZIONE
displayedColumnsModalitaAgevolazioneErogazione: string[] = ['descModalitaAgevolazione', 'numeroRiferimento', 'dtContabile', 'ultimoImportoAgevolato', 'importoErogato','importoRevocato', 'importoRecuperato', 'importoResiduoDaErogare', 'azioni'];
  dataSourceModalitaAgevolazioneErogazione: MatTableDataSource<ModalitaAgevolazioneErogazioneTableDTO> = new MatTableDataSource<ModalitaAgevolazioneErogazioneTableDTO>([]);
  isCausaleEditable: boolean;

  cols: number;
  rowHeight: any;

  constructor(
    private configService: ConfigService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private inizializzazioneService: InizializzazioneService,
    private erogazioneService: ErogazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private sharedService: SharedService
  ) { }

  ngOnInit(): void {

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.idIride && this.user.codiceRuolo && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) {
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER) {
            this.isNuovaErogazioneVisible = true;
          }
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            if (params['id']) {
              this.tipoOperazioneCurrent = 'modifica'; // Test
              this.idProgetto = +params['id'];
              this.idBandoLinea = +params['idBando'];
              this.loadData();
              this.loadErogazione();
            } else { //inserisci
              this.showMessageError('Identificativo Trasferimento non inserito');
            }
          });
        } else {
          console.warn('this.user.idIride', this.user.idIride);
          console.warn('this.user.beneficiarioSelezionato', this.user.beneficiarioSelezionato);
          console.warn('this.user.beneficiarioSelezionato.denominazione', ((this.user.beneficiarioSelezionato) ? this.user.beneficiarioSelezionato.denominazione : ''));
        }
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  loadData() {
    //LOAD CODICE PROGETTO
    this.loadedCodiceProgetto = false;
    this.subscribers.codiceProgetto = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
        this.setupHeaderLabelsButtons();
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });
    //LOAD CAUSALI EROGAZIONI
    this.loadedCausali = false;
    this.subscribers.causali = this.erogazioneService.getCausaliErogazioni(this.idProgetto).subscribe((res: CodiceDescrizioneCausale[]) => {
      if (res) {
        this.causali = res;
      }
      this.loadedCausali = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCausali = true;
    });
    //LOAD ALL MODALITA AGEVOLAZIONE CONTO ECONOMICO
    this.loadedModalitaAgevolazioneContoEconomico = false;
    this.subscribers.modalitaAgevolazioneContoEconomico = this.erogazioneService.getAllModalitaAgevolazioneContoEconomico(this.idProgetto).subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.modalitaAgevolazioneContoEconomico = res;
        this.disabilitaNuovaErogazione = !res || !res.length;
        if (this.disabilitaNuovaErogazione) {
          this.isMessageWarningVisible = this.disabilitaNuovaErogazione;
          this.messageWarning = "Per il conto economico del progetto {idProgetto} non risultano ancora associate le modalità di agevolazione previste. <br /><span>Effettuare una rimodulazione del conto economico oppure contattare l'assistenza prima di procedere con un'erogazione.".replace('{idProgetto}', this.idProgetto.toString());
        }
      }
      this.loadedModalitaAgevolazioneContoEconomico = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedModalitaAgevolazioneContoEconomico = true;
    });
    //LOAD MODALITA EROGAZIONE
    this.loadedModalitaErogazione = false;
    this.subscribers.modalitaErogazione = this.erogazioneService.getAllModalitaErogazione().subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.modalitaErogazione = res;
      }
      this.loadedModalitaErogazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedModalitaErogazione = true;
    });
    //LOAD CODICI DIREZIONE
    this.loadedCodiciDirezione = false;
    this.subscribers.codiciDirezione = this.erogazioneService.getAllCodiciDirezione().subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.codiciDirezione = res;
      }
      this.loadedCodiciDirezione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiciDirezione = true;
    });
  }

  loadErogazione() {
    //LOAD EROGAZIONI
    this.loadedErogazioni = false;
    let request: ErogazioneService.GetErogazioneParams = {
      idU: this.user.idUtente,
      idSoggettoBeneficiario: this.user.beneficiarioSelezionato.idBeneficiario,
      idProgetto: this.idProgetto
    };
    this.subscribers.erogazione = this.erogazioneService.getErogazione(request).subscribe((res: EsitoErogazioneDTO) => {
      if (res) {
        this.erogazione = res;
        this.loadDatiCalcolati();
      }
      this.loadedErogazioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedErogazioni = true;
    });
  }

  loadDatiCalcolati() {
    this.loadedDatiCalcolati = false;
    let datiCalcolatiRequest: GetDatiCalcolatiRequest = {
      erogazioneDTO: this.erogazione.erogazione,
      idProgetto: this.idProgetto
    };
    this.subscribers.datiCalcolati = this.erogazioneService.getDatiCalcolati(datiCalcolatiRequest).subscribe((res: DatiCalcolati) => {
      if (res) {
        this.datiCalcolati = res;
        this.assegnaErogazioneDatiCalcolati();
      }
      this.loadedDatiCalcolati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDatiCalcolati = true;
    });
  }

  assegnaErogazioneDatiCalcolati() {
    this.loadedErogazioniDatiCalcolati = false;
    this.causaleSelected = {
      codice: this.erogazione.erogazione.codCausaleErogazione,
      descrizione: this.erogazione.erogazione.descrizioneCausaleErogazione
    };
    this.modalitaAgevolazioneContoEconomicoSelected = {
      codice: this.erogazione.erogazione.codModalitaAgevolazione,
      descrizione: this.erogazione.erogazione.descModalitaAgevolazione
    };
    this.modalitaErogazioneSelected = {
      codice: this.erogazione.erogazione.codModalitaErogazione,
      descrizione: this.erogazione.erogazione.descModalitaErogazione
    };
    this.codiciDirezioneSelected = {
      codice: this.erogazione.erogazione.codDirezione,
      descrizione: this.erogazione.erogazione.descTipoDirezione
    };
    this.dataSourceFideiussioni = new FideiussioniDatasource(this.erogazione.erogazione.fideiussioni);
    this.dataSourceRichiestaErogazione = new RichiestaErogazioneDatasource(this.erogazione.erogazione.richiesteErogazioni);

    console.log(this.erogazione.erogazione.modalitaAgevolazioni);
    let modalita = new Array<ModalitaAgevolazioneErogazioneTableDTO>();
    let totUltimoAgev: number = 0;
    let totErogato: number = 0;
    let totRecuperato: number = 0;
    let totResiduo: number = 0;
    let totRevocato: number = 0;
    for (let mod of this.erogazione.erogazione.modalitaAgevolazioni) {
      modalita.push(new ModalitaAgevolazioneErogazioneTableDTO(mod.descModalitaAgevolazione, null, null,
        mod.ultimoImportoAgevolato, mod.importoTotaleErogazioni, mod.importoTotaleRecupero, mod.importoResiduoDaErogare, true, false, null,(mod.importoRevocato)?mod.importoRevocato:0 , mod.idModalitaAgevolazione) );
      if (mod.causaliErogazione && mod.causaliErogazione.length > 0) {
        for (let caus of mod.causaliErogazione) {
          modalita.push(new ModalitaAgevolazioneErogazioneTableDTO(caus.descCausale, caus.codRiferimentoErogazione,
            caus.dtContabile, null, caus.importoErogazione, null, null, false, false, caus.idErogazione,null, mod.idModalitaAgevolazione));
        }
      }
      totUltimoAgev += mod.ultimoImportoAgevolato;
      totErogato += mod.importoTotaleErogazioni;
      totRecuperato += mod.importoTotaleRecupero;
      totResiduo += mod.importoResiduoDaErogare;
      totRevocato +=mod.importoRevocato;
    }
    modalita.push(new ModalitaAgevolazioneErogazioneTableDTO("Totale", null, null, totUltimoAgev, totErogato, totRecuperato, totResiduo, false, true, null,totRevocato, null));
    console.log(modalita);
    this.dataSourceModalitaAgevolazioneErogazione = new MatTableDataSource<ModalitaAgevolazioneErogazioneTableDTO>(modalita);

    if (!this.disabilitaNuovaErogazione) {
      this.disabilitaNuovaErogazione = !this.erogazione.isRegolaAttiva;
      if (this.disabilitaNuovaErogazione) {
        this.isMessageWarningVisible = this.disabilitaNuovaErogazione;
        let messaggio: string = '';
        if (this.erogazione.messages && this.erogazione.messages.length > 0) {
          this.erogazione.messages.forEach(m => {
            messaggio += m + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
          this.messageWarning = messaggio;
        }

      }
    }
    if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER
      || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER
      || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_ISTRUTTORE
      || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE) {
      this.isCausaleEditable = true;
    }
    this.loadedErogazioniDatiCalcolati = true;
  }

  // columns and rows - start
  columns: string[];
  rows: any[];
  private fillTable() {
    this.columns = [];

    const row = [];
    for (let i = 0; i < 10; i++) {
      const columnLabel = 'Column_' + (i + 1);
      this.columns.push(columnLabel);
      row[columnLabel] = 'Data_' + (i + 1);
    }

    this.rows = [row, row, row, row, row];
  }
  // columns and rows - end

  isLoading() {
    let r: boolean = false;
    if (
      !this.loadedCodiceProgetto ||
      !this.loadedModalitaAgevolazioneContoEconomico ||
      !this.loadedCausali ||
      !this.loadedModalitaErogazione ||
      !this.loadedCodiciDirezione ||
      !this.loadedErogazioni ||
      !this.loadedDatiCalcolati ||
      !this.loadedChiudiAttivita
    ) {
      r = true;
    }
    return r;
  }

  ricerca(filtro: SalvaErogazioneRequest, isMessageSuccess?: boolean) {

    if (!isMessageSuccess) {
      this.resetMessageSuccess();
    }
    this.resetMessageError();
    if (this.dataDal > this.dataAl) {
      this.showMessageError("Il campo 'Data dal' deve essere precedente al campo 'Data al'.");
      return;
    }
    this.loadedRicerca = false;
    this.dataRequest = {
      datiCalcolati: {
        importoResiduoSpettante: this.erogazione.erogazione.importoTotaleResiduo,
        importoTotaleAgevolato: 0,  // da vedere  // todo: bind
        importoTotaleErogato: this.erogazione.erogazione.importoTotaleErogato,
        importoTotaleRichiesto: 0  // da vedere  // todo: bind
      },
      dettaglioErogazione: {
        codCausaleErogazione: this.causaleSelected.codice,
        codModalitaAgevolazione: this.modalitaAgevolazioneContoEconomicoSelected.codice,
        codModalitaErogazione: this.modalitaErogazioneSelected.codice,
        codTipoDirezione: this.codiciDirezioneSelected.codice,
        dataContabile: this.erogazione.erogazione.dataContabile,
        descCausaleErogazione: this.causaleSelected.descrizione,
        descModalitaAgevolazione: this.modalitaAgevolazioneContoEconomicoSelected.descrizione,
        descModalitaErogazione: this.modalitaErogazioneSelected.descrizione,
        descTipoDirezione: this.codiciDirezioneSelected.descrizione,
        idErogazione: this.erogazione.erogazione.idErogazione,
        importoCalcolato: 0,  // da vedere  // todo: bind
        importoErogazioneDaIterFinanziario: this.erogazione.erogazione.importoErogazioneFinanziario,
        importoErogazioneEffettiva: this.erogazione.erogazione.importoErogazioneEffettivo,
        importoResiduo: 0,  // da vedere  // todo: bind
        note: this.erogazione.erogazione.noteErogazione,
        numero: this.erogazione.erogazione.numeroRiferimento,
        percentualeErogazioneEffettiva: this.erogazione.erogazione.percentualeErogazioneEffettiva,
        percentualeErogazioneIterFinanziario: this.erogazione.erogazione.percentualeErogazioneFinanziaria
      },
      idProgetto: this.idProgetto
    };
    this.subscribers.ricercaErogazione = this.erogazioneService.inserisciErogazione(this.dataRequest).subscribe(data => {
      this.loadedRicerca = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  setupErogazioneItemDatasourceMock(datas?: any) {
    let data: Array<ErogazioneItem> = [
      {
        codCausaleErogazione: '',
        codModalitaAgevolazione: '',
        codModalitaErogazione: '',
        codTipoDirezione: '',
        dataContabile: '',
        descCausaleErogazione: '',
        descModalitaAgevolazione: '',
        descModalitaErogazione: '',
        descTipoDirezione: '',
        idErogazione: 0,
        importoCalcolato: 0,
        importoErogazioneDaIterFinanziario: 0,
        importoErogazioneEffettiva: 0,
        importoResiduo: 0,
        note: '',
        numero: '',
        percentualeErogazioneEffettiva: 0,
        percentualeErogazioneIterFinanziario: 0,
      }
    ];
    this.setupErogazioneItemDatasource(data);
  }

  resetErogazioneItemDatasource() {
    this.dataResponse = undefined;
    this.dataSource = undefined;
    this.paginator.length = 0;
    this.paginator.pageIndex = 0;
  }

  setupErogazioneItemDatasource(data: Array<ErogazioneItem>) {
    if (data) {
      this.dataResponse = data;
      this.dataSource = new ErogazioneItemDatasource(this.dataResponse);
      this.paginator.length = this.dataResponse.length;
      this.paginator.pageIndex = 0;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
  }

  setupHeaderLabelsButtons() {
    this.headerLabels = [
      {
        codice: 'Beneficiario',
        descrizione: ((this.user && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) ? this.user.beneficiarioSelezionato.denominazione : '')
      },
      {
        codice: 'Codice progetto',
        descrizione: this.codiceProgetto
      }
    ];
    this.headerButtons = [
      {
        codice: 'dati-progetto-e-attivita-pregresse',
        descrizione: 'Dati progetto e attività pregresse'
      },
      {
        codice: 'conto-economico',
        descrizione: 'Conto economico'
      }
    ];
  }

  compareWithCodiceDescrizione(option: CodiceDescrizione, value: CodiceDescrizione) {
    return value && (option.codice === value.codice);
  }

  onSelectCausale() {

  }

  onSelectModalitaAgevolazioneContoEconomico() {

  }

  onSelectModalitaErogazione() {

  }

  onSelectCodiciDirezione() {

  }

  onTorna() {
    this.chiudiAttivita();
  }

  chiudiAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_EROGAZIONE).subscribe((data: string) => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  visualizzaModalitaAgevolazioneErogazione(idErogazione: number, idModalitaAgevolazione: number) {
 
    console.log("@@@datasource modalita agevolazione");
    console.log(this.dataSourceModalitaAgevolazioneErogazione.data);
    
    
    this.goToDettaglioDocumento(this.idProgetto, idErogazione,idModalitaAgevolazione );
  }

  modificaModalitaAgevolazioneErogazione(idErogazione: number, idModalitaAgevolazione: number) {
    this.goToModificaDocumento(this.idProgetto, idErogazione, idModalitaAgevolazione);
  }

  eliminaModalitaAgevolazioneErogazione(idErogazione: number) {
    this.congermaDialog(idErogazione);
  }

  onNuovaErogazione() {
    this.goToNuovaErogazione(this.idProgetto);
  }

  congermaDialog(idErogazione: number) {
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        //LOAD DETTAGLIO EROGAZIONE
        this.loadedEliminaErogazioni = false;
        this.subscribers.erogazione = this.erogazioneService.eliminaErogazione(idErogazione).subscribe((res: EsitoOperazioni) => {
          if (res) {
            if (res.esito) {
              this.showMessageSuccess(res.msg);
              this.loadErogazione();
            } else if (res.msg) {
              this.showMessageError(res.msg);
            } else {
              this.showMessageError('Nessun messaggio');
            }
          }
          this.loadedEliminaErogazioni = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError(err.message);
          this.loadedEliminaErogazioni = true;
        });
      }
    });
  }

  onHeaderButtonsClick(btn: CodiceDescrizione) {
    switch (btn.codice) {
      case 'dati-progetto-e-attivita-pregresse':
        this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
          width: '90%',
          maxHeight: '95%',
          data: {
            idProgetto: this.idProgetto,
            apiURL: this.configService.getApiURL()
          }
        });
        break;
      case 'conto-economico':
        this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
          width: "90%",
          maxHeight: '90%',
          data: {
            idBandoLinea: this.idBandoLinea,
            idProgetto: this.idProgetto,
            apiURL: this.configService.getApiURL()
          }
        });
        break;
      default:
    }
  }

  goToDettaglioDocumento(idProgetto: number, idErogazione: number, idModalitaAgevolazione: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_EROGAZIONE + "/dettaglio-erogazione", idProgetto, this.idBandoLinea, idErogazione, idModalitaAgevolazione]);
    //, this.idProgetto, this.idBandoLinea, idDocumento, { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO }
  }

  goToNuovaErogazione(idProgetto: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_EROGAZIONE + "/nuova-erogazione", idProgetto, this.idBandoLinea]);
  }

  goToModificaDocumento(idProgetto: number, idErogazione: number, idModalitaAgevolazione: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_EROGAZIONE + "/modifica-erogazione", idProgetto, this.idBandoLinea, idErogazione, idModalitaAgevolazione]);
    //  //, this.idProgetto, this.idBandoLinea, idDocumento, { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA }
  }

  //MESSAGGI SUCCESS E ERROR - start
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

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  //MESSAGGI SUCCESS E ERROR - end
}

export class ErogazioneItemDatasource extends MatTableDataSource<ErogazioneItem> {

  private filterByLineaInterventoEnable: boolean = true;
  private idLineaIntervento: number;

  _orderData(data: ErogazioneItem[]): ErogazioneItem[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "numproposta";
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

  }

  constructor(data: ErogazioneItem[]) {
    super(data);
  }
}

export class FideiussioniDatasource extends MatTableDataSource<FideiussioneDTO> {

  _orderData(data: FideiussioneDTO[]): FideiussioneDTO[] {
    return data;
  }

  constructor(data: FideiussioneDTO[]) {
    super(data);
  }

}

export class RichiestaErogazioneDatasource extends MatTableDataSource<RichiestaErogazione> {

  _orderData(data: RichiestaErogazione[]): RichiestaErogazione[] {
    return data;
  }

  constructor(data: RichiestaErogazione[]) {
    super(data);
  }

}
