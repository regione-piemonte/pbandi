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
import { DecimalPipe } from "@angular/common";
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ConfigService } from "../../../core/services/config.service";

import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { InizializzazioneService } from "src/app/shared/services/inizializzazione.service";
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";

import { ErogazioneService } from "../../services/erogazione.service";
import { SoggettoTrasferimento } from "../../commons/models/soggetto-trasferimento";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
import { CodiceDescrizioneCausale } from "../../commons/models/codice-descrizione-causale";
import { EsitoErogazioneDTO } from "../../commons/dto/esito-erogazione-dto";
import { EsitoRichiestaErogazioneDTO } from "../../commons/dto/esito-richiesta-erogazione-dto";
import { RichiestaErogazione } from "../../commons/models/richiesta-erogazione";
import { GetDatiCalcolatiRequest } from "../../commons/requests/get-dati-calcolati-request";
import { DatiCalcolati } from "../../commons/models/dati-calcolati";
import { Erogazione as ErogazioneItem } from "../../commons/models/erogazione";
import { FideiussioneTipoTrattamentoDTO } from "../../commons/dto/fideiussione-tipo-trattamento-dto";
import { ModalitaAgevolazioneErogazioneDTO } from "../../commons/dto/modalita-agevolazione-erogazione-dto";
import { SalvaErogazioneRequest } from "../../commons/requests/salva-erogazione-request";
import { NgForm } from "@angular/forms";

import { Observable as __Observable } from 'rxjs';  // Todo: remove - tmp

import { DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';

//import { ObservableMedia } from '@angular/flex-layout';

@Component({
  selector: 'app-erogazione-utente',
  templateUrl: './erogazione-utente.component.html',
  styleUrls: ['./erogazione-utente.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ErogazioneUtenteComponent implements OnInit {
  idProgetto: number; // 0315000026
  idBandoLinea: number;
  codCausale: string;
  codiceProgetto: string;
  //
  tipoOperazioneCurrent: string;
  tipoOperazioneFrom: string;
  //documento: DocumentoDiSpesaDTO;
  //
  user: UserInfoSec;

  //LOADED
  loadedChiudiAttivita: boolean = true;
  loadedCodiceProgetto: boolean;
  //loadedBeneficiari: boolean;
  loadedCausali: boolean;
  loadedModalitaAgevolazioneContoEconomico: boolean;
  loadedModalitaErogazione: boolean;
  loadedCodiciDirezione: boolean;
  loadedErogazioni: boolean;
  loadedDatiRiepilogoRichiestaErogazione: boolean;
  loadedDatiCalcolati: boolean;
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
  datiRiepilogoRichiestaErogazione: EsitoRichiestaErogazioneDTO;
  erogazione: EsitoErogazioneDTO; // ErogazioneItem?
  datiCalcolati: DatiCalcolati;
  beneficiarioSelected: SoggettoTrasferimento;
  causaleSelected: CodiceDescrizione;
  modalitaAgevolazioneContoEconomicoSelected: CodiceDescrizione;
  modalitaErogazioneSelected: CodiceDescrizione;
  codiciDirezioneSelected: CodiceDescrizione;
  //dataDal: FormControl = new FormControl();
  //dataAl: FormControl = new FormControl();
  dataDal: Date;  // .setHours(0, 0, 0, 0);
  dataAl: Date;
  codiceTrasferimento: string;
  tipo: string = '0';

  //TABLE
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  dataRequest: SalvaErogazioneRequest;
  dataResponse: any;
  //displayedColumns: string[] = ['tipologia', 'progetto', 'fornitore', 'datanum', 'stato', 'rendicontazione', 'validato', 'azioni'];
  displayedColumns: string[] = ['data', 'causale', 'denominazionebeneficiario', 'importotrasferimento', 'codicetrasferimento', 'flagpubblicoprivato', 'azioni'];
  dataSource: MatTableDataSource<ErogazioneItem> = new MatTableDataSource<ErogazioneItem>();
  //TABLE FIDEIUSSIONI TIPO TRATTAMENTO
  displayedColumnsFideiussioniTipoTrattamento: string[] = ['descrizioneTipoTrattamento', 'importo'];
  displayedHeadersFideiussioniTipoTrattamento: string[] = ['Tipo trattamento', 'Importo'];
  displayedFootersFideiussioniTipoTrattamento: string[] = [];
  displayedColumnsCustomFideiussioniTipoTrattamento: string[] = ['', 'number'];
  displayedHeadersCustomFideiussioniTipoTrattamento: string[] = this.displayedColumnsCustomFideiussioniTipoTrattamento;
  displayedFootersCustomFideiussioniTipoTrattamento: string[] = [];
  dataSourceFideiussioniTipoTrattamento: MatTableDataSource<FideiussioneTipoTrattamentoDTO> = new MatTableDataSource<FideiussioneTipoTrattamentoDTO>();

  displayedColumnsRichiestaErogazione: string[] = ['descCausaleErogazione', 'numeroRichiestaErogazione', 'dataRichiestaErogazione', 'importoRichiestaErogazione'];
  displayedHeadersRichiestaErogazione: string[] = ['Causale erogazione', 'Numero', 'Data', 'Importo richiesto'];
  displayedFootersRichiestaErogazione: string[] = [];
  displayedColumnsCustomRichiestaErogazione: string[] = ['', '', 'date', 'number'];
  displayedHeadersCustomRichiestaErogazione: string[] = this.displayedColumnsCustomRichiestaErogazione;
  displayedFootersCustomRichiestaErogazione: string[] = [];
  dataSourceRichiestaErogazione: MatTableDataSource<RichiestaErogazione> = new MatTableDataSource<RichiestaErogazione>(); // old RichiestaErogazioneDTO
  causaleErogazione: string;
  percentualeDiErogazione: number;
  importoRichiesto: number;
  avanzamentoDellaSpesaPrevistaDaBando: number;
  importoDellaSpesaDaRaggiungere: number;
  //TABLE MODALITA AGEVOLAZIONE EROGAZIONE
  displayedColumnsModalitaAgevolazioneErogazione: string[] = ['descModalitaAgevolazione', 'numeroRiferimento', 'dtContabile', 'ultimoImportoAgevolato', 'importoGiaErogato', 'importoGiaRecuperato', 'importoResiduoDaErogare', 'azioni'];
  displayedHeadersModalitaAgevolazioneErogazione: string[] = ['Modalità e Causali agevolazione', 'Numero di riferimento', 'Data contabile', 'Ultimo importo agevolato', 'Importo già erogato', 'Importo già recuperato', 'Importo residuo da erogare', ''];
  displayedFootersModalitaAgevolazioneErogazione: string[] = ['Totale', '', '', '', '', '', '', ''];
  displayedColumnsCustomModalitaAgevolazioneErogazione: string[] = ['', '', 'date', 'number', 'number', 'number', 'number', 'azioni'];
  displayedHeadersCustomModalitaAgevolazioneErogazione: string[] = this.displayedColumnsCustomModalitaAgevolazioneErogazione;
  displayedFootersCustomModalitaAgevolazioneErogazione: string[] = ['', '', '', 'right', 'right', 'right', 'right', ''];
  displayedColumnsCustomModalitaAgevolazioneErogazioneAzioni: any = {};
  dataSourceModalitaAgevolazioneErogazione: MatTableDataSource<ModalitaAgevolazioneErogazioneDTO> = new MatTableDataSource<ModalitaAgevolazioneErogazioneDTO>();
  nestedField = 'causaliErogazione';
  nestedColumnsModalitaAgevolazioneErogazione: string[] = ['descCausale', 'codRiferimentoErogazione', 'dtContabile', 'importoErogazione', 'field1', 'field2', 'field3', 'azioni'];
  nestedHeadersModalitaAgevolazioneErogazione: string[] = ['Modalità e Causali agevolazione', 'Numero di riferimento', 'Data contabile', 'Ultimo importo agevolato', 'Importo già erogato', 'Importo già recuperato', 'Importo residuo da erogare', ''];
  nestedFootersModalitaAgevolazioneErogazione: string[] = ['Totale', '', '', '', '', '', '', ''];
  nestedColumnsCustomModalitaAgevolazioneErogazione: string[] = ['', '', 'date', 'number', 'number', 'number', 'number', 'azioni'];
  nestedHeadersCustomModalitaAgevolazioneErogazione: string[] = this.nestedColumnsCustomModalitaAgevolazioneErogazione;
  nestedFootersCustomModalitaAgevolazioneErogazione: string[] = ['', '', '', 'right', 'right', 'right', 'right', ''];
  nestedColumnsCustomModalitaAgevolazioneErogazioneAzioni: any = { visualizza: true, modifica: true, elimina: true };

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
        console.log('user', data);
        console.log('user.ruolo', data.ruolo);  // getInfoUtente
        console.log('user id', data.ruoli.find(d => d.descrizione == data.ruolo).id);
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) {  // this.user.idIride
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            if (params['id'] && params['codCausale']) {// idDocumentoDiSpesa == undefined -> nuovo documento di spesa, idDocumentoDiSpesa != undefined -> modifica documento di spesa
              this.tipoOperazioneCurrent = 'modifica'; // Test
              this.idProgetto = +params['id'];
              this.idBandoLinea = +params['idBando'];
              this.codCausale = params['codCausale'];
              console.log('NuovaErogazione', this.tipoOperazioneCurrent, this.codCausale);
              this.loadDati();
              this.loadDatiRiepilogoRichiestaErogazione();
              //this.loadErogazione();
            } else { //inserisci
              this.showMessageError('Identificativo Trasferimento non inserito');
              //this.loadDati();
            }
            //this.loadDati();
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

  loadDati() {
    this.loadData();
  }

  loadData() {
    console.log('this.erogazioneService.rootUrl', this.erogazioneService.rootUrl);
    //LOAD CODICE PROGETTO
    this.loadedCodiceProgetto = false;
    this.subscribers.codiceProgetto = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        console.log('getCodiceProgetto', res);
        this.codiceProgetto = res;
        this.setupHeaderLabelsButtons();
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero del codice del proegtto.");
      this.loadedCodiceProgetto = true;
    });
    //LOAD CAUSALI EROGAZIONI
    this.loadedCausali = false;
    this.subscribers.causali = this.erogazioneService.getCausaliErogazioni(this.idProgetto).subscribe((res: CodiceDescrizioneCausale[]) => {
      if (res) {
        console.log('getCausaliErogazioni', res);
        this.causali = res;
        //this.causaleSelected = this.causali[0];
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
        console.log('getAllModalitaAgevolazioneContoEconomico', res);
        this.modalitaAgevolazioneContoEconomico = res;
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
        console.log('getAllModalitaErogazione', res);
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
        console.log('getAllCodiciDirezione', res);
        this.codiciDirezione = res;
      }
      this.loadedCodiciDirezione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiciDirezione = true;
    });
    //this.fillTable();
  }

  loadDatiRiepilogoRichiestaErogazione() {
    //LOAD RIEPILOGO RICHIESTA EROGAZIONE
    this.loadedDatiRiepilogoRichiestaErogazione = false;
    let datiRiepilogoRichiestaErogazioneParams: ErogazioneService.GetDatiRiepilogoRichiestaErogazioneParams = {
      idProgetto: this.idProgetto,
      codCausale: this.codCausale
    };
    this.subscribers.datiRiepilogoRichiestaErogazione = this.erogazioneService.getDatiRiepilogoRichiestaErogazione(datiRiepilogoRichiestaErogazioneParams).subscribe((res: EsitoRichiestaErogazioneDTO) => {
      if (res) {
        console.log('getDatiRiepilogoRichiestaErogazione', res);
        this.datiRiepilogoRichiestaErogazione = res;
        console.log('test f', this.datiRiepilogoRichiestaErogazione.richiestaErogazione.fideiussioni);
        if (!this.datiRiepilogoRichiestaErogazione.richiestaErogazione.spesaProgetto.totaleSpesaSostenuta ||
          (this.datiRiepilogoRichiestaErogazione.messages && this.datiRiepilogoRichiestaErogazione.messages.length > 0)) {
          this.isMessageWarningVisible = true;
          this.messageWarning = this.datiRiepilogoRichiestaErogazione.messages.join('<br>');
        }
        if (!this.datiRiepilogoRichiestaErogazione.richiestaErogazione.fideiussioniTipoTrattamento) {
          this.datiRiepilogoRichiestaErogazione.richiestaErogazione.fideiussioniTipoTrattamento = [];
        }
        this.dataSourceFideiussioniTipoTrattamento = new FideiussioneTipoTrattamentoDatasource(this.datiRiepilogoRichiestaErogazione.richiestaErogazione.fideiussioniTipoTrattamento); // FideiussioneTipoTrattamentoDTO
        this.loadDati();
      }
      this.loadedDatiRiepilogoRichiestaErogazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDatiRiepilogoRichiestaErogazione = true;
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
        console.log('getErogazione', res);
        this.erogazione = res;
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
        console.log('test f', this.erogazione.erogazione.fideiussioni);
        this.dataSourceRichiestaErogazione = new RichiestaErogazioneDatasource(this.erogazione.erogazione.richiesteErogazioni); // old EsitoRichiestaErogazioneDTO
        if (this.erogazione.erogazione.richiesteErogazioni && this.erogazione.erogazione.richiesteErogazioni.length) {
          this.causaleErogazione = this.erogazione.erogazione.richiesteErogazioni[0].descCausaleErogazione;
          this.importoRichiesto = this.erogazione.erogazione.richiesteErogazioni[0].importoRichiestaErogazione;
          this.percentualeDiErogazione = 100 / this.erogazione.erogazione.spesaProgetto['totaleSpesaAmmessa'] * this.erogazione.erogazione.richiesteErogazioni[0].importoRichiestaErogazione;
        }
        let source = this.erogazione.erogazione.modalitaAgevolazioni
        source.forEach((element, index) => {
          if (element[this.nestedField] && Array.isArray(element[this.nestedField]) && element[this.nestedField].length) {
            //source = [...source, { ...element, [this.nestedField]: new MatTableDataSource(element[this.nestedField]) }];
            source[index] = { ...element, [this.nestedField]: new MatTableDataSource(element[this.nestedField]) };
          } else {
            //source = [...source, element];
            source[index] = element;
          }
        });
        this.erogazione.erogazione.modalitaAgevolazioni = source;
        this.dataSourceModalitaAgevolazioneErogazione = new ModalitaAgevolazioneErogazioneDatasource(this.erogazione.erogazione.modalitaAgevolazioni);  // ModalitaAgevolazioneErogazioneDTO
        console.log('this.dataSourceModalitaAgevolazioneErogazione', this.dataSourceModalitaAgevolazioneErogazione);
        this.displayedFootersModalitaAgevolazioneErogazione[3] = new DecimalPipe('it').transform(this.erogazione.erogazione.importoTotaleResiduo + this.erogazione.erogazione.importoTotaleErogato, '1.2-2').toString();
        this.displayedFootersModalitaAgevolazioneErogazione[4] = this.erogazione.erogazione.importoTotaleErogato.toString();
        this.displayedFootersModalitaAgevolazioneErogazione[5] = this.erogazione.erogazione.importoTotaleRecuperi.toString();
        this.displayedFootersModalitaAgevolazioneErogazione[6] = this.erogazione.erogazione.importoTotaleResiduo.toString();
        this.nestedFootersModalitaAgevolazioneErogazione[3] = new DecimalPipe('it').transform(this.erogazione.erogazione.importoTotaleResiduo + this.erogazione.erogazione.importoTotaleErogato, '1.2-2').toString();
        this.nestedFootersModalitaAgevolazioneErogazione[4] = this.erogazione.erogazione.importoTotaleErogato.toString();
        this.nestedFootersModalitaAgevolazioneErogazione[5] = this.erogazione.erogazione.importoTotaleRecuperi.toString();
        this.nestedFootersModalitaAgevolazioneErogazione[6] = this.erogazione.erogazione.importoTotaleResiduo.toString();
        this.nestedHeadersModalitaAgevolazioneErogazione = [];
        this.nestedFootersModalitaAgevolazioneErogazione = [];

        this.importoDellaSpesaDaRaggiungere = this.erogazione.erogazione.spesaProgetto['totaleSpesaAmmessa'] - this.erogazione.erogazione.importoTotaleResiduo;
        this.avanzamentoDellaSpesaPrevistaDaBando = 100 / this.erogazione.erogazione.spesaProgetto['totaleSpesaAmmessa'] * this.importoDellaSpesaDaRaggiungere;

        this.getDatiCalcolati();
      }
      this.loadedErogazioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedErogazioni = true;
    });
  }

  getDatiCalcolati() {
    this.loadedDatiCalcolati = false;
    let datiCalcolatiRequest: GetDatiCalcolatiRequest = {
      erogazioneDTO: this.erogazione.erogazione,
      idProgetto: this.idProgetto
    };
    this.subscribers.datiCalcolati = this.erogazioneService.getDatiCalcolati(datiCalcolatiRequest).subscribe((res: DatiCalcolati) => {
      if (res) {
        console.log('getDatiCalcolati', res);
        this.datiCalcolati = res;
      }
      this.loadedDatiCalcolati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDatiCalcolati = true;
    });
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

  onTorna() {
    this.chiudiAttivita();
  }

  chiudiAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_RICHIESTA_EROGAZIONE).subscribe((data: string) => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedModalitaAgevolazioneContoEconomico || !this.loadedCausali
      || !this.loadedModalitaErogazione || !this.loadedCodiciDirezione || !this.loadedDatiRiepilogoRichiestaErogazione
      || !this.loadedChiudiAttivita) {
      return true;
    }
    return false;
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
        importoTotaleAgevolato: 0,
        importoTotaleErogato: this.erogazione.erogazione.importoTotaleErogato,
        importoTotaleRichiesto: 0
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
        importoCalcolato: 0,  // todo: bind
        importoErogazioneDaIterFinanziario: this.erogazione.erogazione.importoErogazioneFinanziario,
        importoErogazioneEffettiva: this.erogazione.erogazione.importoErogazioneEffettivo,
        importoResiduo: 0,  // todo: bind
        note: this.erogazione.erogazione.noteErogazione,
        numero: this.erogazione.erogazione.numeroRiferimento,
        percentualeErogazioneEffettiva: this.erogazione.erogazione.percentualeErogazioneEffettiva,
        percentualeErogazioneIterFinanziario: this.erogazione.erogazione.percentualeErogazioneFinanziaria
      },
      idProgetto: this.idProgetto
    };
    this.subscribers.ricercaErogazione = this.erogazioneService.inserisciErogazione(this.dataRequest).subscribe(data => {
      //this.setupErogazioneItemDatasource(data);
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

  goToOperazioni() {

  }

  onVisualizzaModalitaAgevolazioneErogazione = (row: any) => {  // ModalitaAgevolazioneErogazioneDTO
    console.log('onVisualizzaModalitaAgevolazioneErogazione', row);
    this.goToDettaglioDocumento(this.idProgetto, row.idErogazione);  // row.idTrasferimento
  }

  onModificaModalitaAgevolazioneErogazione = (row: any) => {  // ModalitaAgevolazioneErogazioneDTO
    console.log('onModificaModalitaAgevolazioneErogazione', row);
    this.goToModificaDocumento(this.idProgetto, row.idErogazione);  // row.idTrasferimento
  }

  onEliminaModalitaAgevolazioneErogazione = (row: any) => {  // ModalitaAgevolazioneErogazioneDTO
    console.log('onEliminaModalitaAgevolazioneErogazione', row);
  }

  onNestedVisualizzaModalitaAgevolazioneErogazione = (row: any) => {  // ModalitaAgevolazioneErogazioneDTO
    console.log('onVisualizzaModalitaAgevolazioneErogazione', row);
    this.goToDettaglioDocumento(this.idProgetto, row.idErogazione);  // row.idTrasferimento
  }

  onNestedModificaModalitaAgevolazioneErogazione = (row: any) => {  // ModalitaAgevolazioneErogazioneDTO
    console.log('onModificaModalitaAgevolazioneErogazione', row);
    this.goToModificaDocumento(this.idProgetto, row.idErogazione);  // row.idTrasferimento
  }

  onNestedEliminaModalitaAgevolazioneErogazione = (row: any) => {  // ModalitaAgevolazioneErogazioneDTO
    console.log('onEliminaModalitaAgevolazioneErogazione', row);
  }

  onNuovaErogazione() {
    this.goToNuovaErogazioneUtente(this.idProgetto, this.codCausale);
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

  goToDettaglioDocumento(idProgetto: number, idErogazione: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_EROGAZIONE + "/dettaglio-erogazione", idProgetto, this.idBandoLinea, idErogazione]);
    //, this.idProgetto, this.idBandoLinea, idDocumento, { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO }
  }

  goToNuovaErogazioneUtente(idProgetto: number, idCausale: string) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_EROGAZIONE + "/avvio-lavori-richiesta-erogazione-acconto-nuova", idProgetto,
    this.idBandoLinea, idCausale]);
  }

  goToModificaDocumento(idProgetto: number, idErogazione: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_EROGAZIONE + "/modifica-erogazione", idProgetto, this.idBandoLinea, idErogazione]);
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

export class FideiussioneTipoTrattamentoDatasource extends MatTableDataSource<FideiussioneTipoTrattamentoDTO> {

  _orderData(data: FideiussioneTipoTrattamentoDTO[]): FideiussioneTipoTrattamentoDTO[] {
    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "numproposta"; // Todo: param
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

    switch (this.sort.active) {
      case "":
      default:
        sortedData = data;
        break;
    }

    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: FideiussioneTipoTrattamentoDTO[]) {
    super(data);
  }

}

export class RichiestaErogazioneDatasource extends MatTableDataSource<RichiestaErogazione> {  // old RichiestaErogazioneDTO

  _orderData(data: RichiestaErogazione[]): RichiestaErogazione[] {
    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "numproposta"; // Todo: param
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

    switch (this.sort.active) {
      case "":
      default:
        sortedData = data;
        break;
    }

    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: RichiestaErogazione[]) {
    super(data);
  }

}

export class ModalitaAgevolazioneErogazioneDatasource extends MatTableDataSource<ModalitaAgevolazioneErogazioneDTO> {

  _orderData(data: ModalitaAgevolazioneErogazioneDTO[]): ModalitaAgevolazioneErogazioneDTO[] {
    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "numproposta"; // Todo: param
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

    switch (this.sort.active) {
      case "":
      default:
        sortedData = data;
        break;
    }

    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: ModalitaAgevolazioneErogazioneDTO[]) {
    super(data);
  }

}