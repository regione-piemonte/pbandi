/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from '@angular/cdk/collections';
import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { ThemePalette } from '@angular/material/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AreaScientificaDTO } from '../../commons/vo/area-scientifica-DTO';
import { CheckListBandoLineaDTO } from '../../commons/vo/check-list-bando-linea-DTO';
import { CostantiBandoLineaDTO } from '../../commons/vo/costanti-bando-linea-DTO';
import { DocPagamBandoLineaDTO } from '../../commons/vo/doc-pagam-bando-linea-DTO';
import { EnteDiCompetenzaDTO } from '../../commons/vo/ente-di-competenza-DTO';
import { EnteDiCompetenzaRuoloAssociatoDTO } from '../../commons/vo/ente-di-competenza-ruolo-associato-DTO';
import { IdDescBreveDescEstesaVo } from '../../commons/vo/id-desc-breve-desc-estesa-vo';
import { RegolaAssociataDTO } from '../../commons/vo/regola-associata-DTO';
import { SettoreEnteDTO } from '../../commons/vo/settore-ente-DTO';
import { TipoDiAiutoAssociatoDTO } from '../../commons/vo/tipo-di-aiuto-associato-DTO';
import { CheckListService } from '../../services/checklist.service';
import { CostantiService } from '../../services/costanti.service';
import { DatiAggiuntiviBandoLineaService } from '../../services/dati-aggiuntivi-bando-linea.service';
import { DocModPagService } from '../../services/doc-mod-pag.service';
import { EnteCompetenzaService } from '../../services/ente-competenza.service';
import { ModelliDocumentoService } from '../../services/modelli-documento.service';
import { RegoleService } from '../../services/regole.service';
import { ModificaModelloDocumentoDialogComponent } from '../modifica-modello-documento-dialog/modifica-modello-documento-dialog.component';
import { NuovaAreaScientificaDialogComponent } from '../nuova-area-scientifica-dialog/nuova-area-scientifica-dialog.component';
import { NuovoModalitaPagamentoDialogComponent } from '../nuovo-modalita-pagamento-dialog/nuovo-modalita-pagamento-dialog.component';
import { NuovoTipoDocumentoDialogComponent } from '../nuovo-tipo-documento-dialog/nuovo-tipo-documento-dialog.component';
import { saveAs } from "file-saver-es";
import { CheclistDTO } from '../../commons/vo/checlist-DTO';
import { ModelloDTO } from '../../commons/vo/modello-DTO';
import { TemplateDTO } from '../../commons/vo/template-DTO';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';
import { DatiBandoService } from '../../services/dati-bando.service';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { ConfigurazioneBandoLineaService } from '../../services/configurazione-bando-linea.service';
import { EstremiBancariDTO } from './DTO/estremi-bancari-DTO';
import { ModAgevEstremiBancariDTO } from './DTO/mod-agev-estremi-Bancari-DTO';
import { GenericResponse } from 'src/app/shared/commons/dto/generic-response';
import { BancaSuggestVO } from './DTO/banca-suggest-vo';
import { EstremiContoDTO } from './DTO/estremi-conto-DTO';
import { NuovoEstremoBancarioDialog } from './nuovo-estremo-bancario-dialog/nuovo-estremo-bancario-dialog.component';
import { InsertEstremiBancariDTO } from './DTO/insert-estremi-bancari-DTO';
import { ParametriMonitoraggioTempi } from './DTO/parametri-monitoraggio-tempi';
import { CodiceDescrizioneDTO } from '@pbandi/common-lib';
import { ResponseCodeMessage } from 'src/app/shared/commons/dto/response-code-message-dto';
import { ContainerWithOptions } from './DTO/Container-with-options';
import { ModificaParametroMonitoraggioDialog } from './modifica-parametro-monitoraggio-dialog/modifica-parametro-monitoraggio-dialog.component';

export interface Task {
  name: string;
  completed: boolean;
  color: ThemePalette;
  subtasks?: ItemModelloDocumentoDuplicabile[];
}

export interface ItemModelloDocumentoDuplicabile {
  id: number;
  descrizione: string;
  descrizioneBreve: string;
  dataInizioValidita: Date;
  dataFineValidita: Date;
  completed: boolean;
  color: ThemePalette;
}



@Component({
  selector: 'app-configura-bando-linea',
  templateUrl: './configura-bando-linea.component.html',
  styleUrls: ['./configura-bando-linea.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ConfiguraBandoLineaComponent implements OnInit, AfterViewChecked {

  flagAssociazioneDestinatarioFinpiemonte = false;
  flagFinpiemonte = false; //per arbiliatere in modo arbitrario settare la  variabioe a true


  task: Task;
  allComplete: boolean = false;
  user: UserInfoSec;
  bando: string;
  idBando: number
  bandoLinea: string;
  idLineaDiIntervento: string;
  idBandoLinea: string;
  checkedTuttiBandoLinea: boolean = false;
  dataModelliDocumentiDuplicabili: Array<Decodifica>;
  params: URLSearchParams;
  progressivoAreaScientifica: number;
  showConservazione: boolean;
  currentTab: string;
  selectedTab: number = 0;


  ruoli: Array<Decodifica>;
  ruoloSelected: Decodifica;

  enti: Array<EnteDiCompetenzaDTO>;
  enteSelected: EnteDiCompetenzaDTO;
  settori: Array<SettoreEnteDTO>;
  settoreSelected: SettoreEnteDTO;
  pec: string = undefined;
  mail: string = undefined;
  mailIsValid: boolean = false;
  periodoConservazioneCorrente: number;
  periodoConservazioneGenerale: number;
  tipiAiuto: Array<Decodifica>;
  tipoAiutoSelected: Decodifica;

  temiPrioritari: Array<Decodifica>;
  temaPrioritarioSelected: FormControl = new FormControl();
  temaPrGroup: FormGroup = new FormGroup({ temaPrControl: new FormControl() });
  filteredOptionsTemaPr: Observable<Decodifica[]>;

  indicatoriQSN: Array<Decodifica>;
  indicatoreQSNSelected: FormControl = new FormControl();
  indQSNGroup: FormGroup = new FormGroup({ indQSNControl: new FormControl() });
  filteredOptionsindQSN: Observable<Decodifica[]>;

  indicatoriRisultato: Array<IdDescBreveDescEstesaVo>;
  indicatoreRisultatoSelected: FormControl = new FormControl();
  indRisGroup: FormGroup = new FormGroup({ indRisControl: new FormControl() });
  filteredOptionsindRis: Observable<IdDescBreveDescEstesaVo[]>;

  tipiPeriodo: Array<Decodifica>;
  tipoPeriodoSelected: Decodifica;
  areeScientifiche: Array<IdDescBreveDescEstesaVo>;
  areaScientificaSelected: IdDescBreveDescEstesaVo;

  regole: Array<IdDescBreveDescEstesaVo>;
  regolaSelected: FormControl = new FormControl();
  regolaGroup: FormGroup = new FormGroup({ regolaControl: new FormControl() });
  filteredOptionsRegola: Observable<IdDescBreveDescEstesaVo[]>;

  bandiLinea: Array<Decodifica>;
  bandoLineaSelected: Decodifica;

  tipiDocumento: Array<Decodifica>;
  tipoDocumentoSelected: FormControl = new FormControl();
  tipoDocGroup: FormGroup = new FormGroup({ tipoDocControl: new FormControl() });
  filteredOptionsTipoDoc: Observable<Decodifica[]>;

  modalitaPagamento: Array<Decodifica>;
  modalitaPagamentoSelected: FormControl = new FormControl();
  modGroup: FormGroup = new FormGroup({ modControl: new FormControl() });
  filteredOptionsMod: Observable<Decodifica[]>;

  normaIncentivazione: string;
  codiceNormativa: string;
  statiDomanda: Array<Decodifica>;
  statoDomandaSelected: Decodifica;
  tipiOperazione: Array<Decodifica>;
  tipoOperazioneSelected: Decodifica;
  statoInvioDomanda: number;
  statoProgetto: number;
  statoContoEconomico: number;
  tipoProgrammazione: string = "2016";

  // variabili tabelle vuote
  showEnteCompetenzaTable: boolean;
  showTipoAiutoTable: boolean;
  showTemaPrioritarioTable: boolean;
  showIndicatoreQSNTable: boolean;
  showIndicatoreRisultatoProgrammaTable: boolean;
  showTipoPeriodoTable: boolean;
  showAreaScientificaTable: boolean;
  showModPagBandoLineaCorTable: boolean;
  showModPagTuttiBandoLineaTable: boolean;
  showCheckListAssociateTable: boolean;
  showModelliCheckListTable: boolean;
  showRegolaTable: boolean;
  showModelliDocumentiAssociatiTable: boolean;
  showModelliDocumentiDuplicabili: boolean;

  @ViewChild("enteAssociaForm", { static: true }) enteAssociaForm: NgForm;

  messageError: string;
  isMessageErrorVisible: boolean;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  // loaded
  loadedObiettivoTematico = false;
  loadedAssociaEnteCompetenza = false;
  loadedEliminaEnteCompetenzaAssociato = false;
  loadedEntiCompetenzaAssociati = false;
  loadedRuoli = false;
  loadedEnte = false;
  loadedSettore = false;
  loadedAssociaTipiAiuto = false;
  loadedEliminaTipiAiuto = false;
  loadedFindTipiAiutoAssociati = false;
  loadedAssociaTemaPrioritario = false;
  loadedEliminaTemaPrioritarioAssociato = false;
  loadedTemaPrioritarioAssociato = false;
  loadedAssociaIndicatoreQSN = false;
  loadedEliminaIndicatoreQSNAssociato = false;
  loadedIndicatoriQSNAssociati = false;
  loadedAssociaIndicatoreRisultatoProgramma = false;
  loadedEliminaIndicatoreRisultatoProgramma = false;
  loadedIndicatoriRisultatoProgrammaAssociati = false;
  loadedAssociaTipoPeriodo = false;
  loadedEliminaTipoPeriodo = false;
  loadedTipoPeriodoAssociato = false;
  loadedAssociaAreaScientifica = false;
  loadedEliminaAreaScientificaAssociata = false;
  loadedAreeScientificheAssociate = false;
  loadedTipiAiutoDaAssociare = false;
  loadedTemaPrioritario = false;
  loadedIndicatoriQSN = false;
  loadedIndicatoriRisultatoProgramma = false;
  loadedTipoPeriodo = false;
  loadedAreaScientifica = false;
  loadedAssociaRegola = false;
  loadedEliminaRegola = false;
  loadedRegoleAssociate = false;
  loadedCercaRegole = false;
  loadedInserisciModello = false;
  loadedGetNomeDocumento = false;
  loadedDownloadDocumento = false;
  loadedGeneraDocumento = false;
  loadedEliminaModelloAssociato = false;
  loadedFindModelliAssociati = false;
  loadedFindBandoLinea = false;
  loadedFindModelliDaAssociare = false;
  loadedAssociaDocPagamTuttiBL = false;
  loadedAssociaDocPagamento = false;
  loadedEliminaDocPagamAssociato = false;
  loadedDocPagamentiAssociati = false;
  loadedEliminaDocPagamAssociatoTuttiBL = false;
  loadedDocPagamentiAssociatiTuttiBL = false;
  loadedTipiDocumento = false;
  loadedModalitaPagamento = false;
  loadedChecklistAssociabili = false;
  loadedAssociaChecklist = false;
  loadedChecklistAssociate = false;
  loadedEliminaChecklist = false;
  loadedSalvaCostanti = false;
  loadedCostantiBandoLinea = false;
  loadedStatoDomanda = false;
  loadedTipoOperazione = false;
  loadedIsruoliobbligatoriassociati = false;
  loadedProcesso = true;
  loadedFlagFinpemonte = false;
  loadedFlagAssocizioneFinpemonteRuolo = false;
  flagMonitoraggioTempi: any;

  // tabelle
  displayedColumnsEnteCompetenza: string[] = ['ruolo', 'ente', 'parolaChiaveACTA', 'consCor', 'consGen', 'mail', 'pec', 'azioni'];
  dataSourceEnteCompetenza: MatTableDataSource<EnteDiCompetenzaRuoloAssociatoDTO>;

  displayedColumnsTipoAiuto: string[] = ['aiuto', 'azioni'];
  dataSourceTipoAiuto: MatTableDataSource<TipoDiAiutoAssociatoDTO>;

  displayedColumnsTemaPrioritario: string[] = ['temaPrioritario', 'azioni'];
  dataSourceTemaPrioritario: MatTableDataSource<Decodifica>;

  displayedColumnsIndicatoreQSN: string[] = ['indicatoreQSN', 'azioni'];
  dataSourceIndicatoreQSN: MatTableDataSource<IdDescBreveDescEstesaVo>;

  displayedColumnsIndicatoreRisultatoProgramma: string[] = ['indicatoreRisultatoProgramma', 'azioni'];
  dataSourceIndicatoreRisultatoProgramma: MatTableDataSource<IdDescBreveDescEstesaVo>;

  displayedColumnsTipoPeriodo: string[] = ['tipoPeriodo', 'azioni'];
  dataSourceTipoPeriodo: MatTableDataSource<Decodifica>;

  displayedColumnsAreaScientifica: string[] = ['codice', 'areaScientifica', 'azioni'];
  dataSourceAreaScientifica: MatTableDataSource<AreaScientificaDTO>;

  displayedColumnsModPagBandoLineaCor: string[] = ['tipoDocumento', 'modalitaPagamento', 'azioni'];
  dataSourceModPagBandoLineaCor: MatTableDataSource<DocPagamBandoLineaDTO>;

  displayedColumnsModPagTuttiBandoLinea: string[] = ['tipoDocumento', 'modalitaPagamento', 'azioni'];
  dataSourceModPagTuttiBandoLinea: MatTableDataSource<DocPagamBandoLineaDTO>;

  displayedColumnsCheckListAssociate: string[] = ['nome', 'codice', 'descrizione', 'versione', 'azioni'];
  dataSourceCheckListAssociate: MatTableDataSource<CheckListBandoLineaDTO>;

  displayedColumnsRegola: string[] = ['regola', 'azioni'];
  dataSourceRegola: MatTableDataSource<RegolaAssociataDTO>;

  displayedColumnsModelliDocumentiAssociati: string[] = ['descrizione', 'download', 'generaModello', 'edit', 'delete'];
  dataSourceModelliDocumentiAssociati: MatTableDataSource<TemplateDTO>;

  displayedColumns: string[] = ['select', 'nome', 'codice', 'descrizione', 'versione'];
  dataSourceModelliChecklist: MatTableDataSource<CheckListBandoLineaDTO>;
  selection = new SelectionModel<CheckListBandoLineaDTO>(true, []);

  isCostanteFinpiemonte: boolean;
  isBandoFinpiemonte: boolean;

  @ViewChild('tabs', { static: false }) tabs;


  //SUBSCRIBERS
  subscribers: any = {};

  // paginator
  @ViewChild("paginatorModPagBandoLineaCor", { static: true }) paginatorModPagBandoLineaCor: MatPaginator;
  @ViewChild("paginatorModPagTuttiBandoLinea", { static: true }) paginatorModPagTuttiBandoLinea: MatPaginator;
  @ViewChild("paginatorCheckListAssociate", { static: true }) paginatorCheckListAssociate: MatPaginator;
  @ViewChild("paginatorModelliCheckList", { static: true }) paginatorModelliCheckList: MatPaginator;

  constructor(
    private userService: UserService,
    private router: Router,
    private dialog: MatDialog,
    private enteCompetenzaService: EnteCompetenzaService,
    private datiAggiuntiviBandoLineaService: DatiAggiuntiviBandoLineaService,
    private regoleService: RegoleService,
    private modelliDocumentoService: ModelliDocumentoService,
    private docModPagService: DocModPagService,
    private checkListService: CheckListService,
    private costantiService: CostantiService,
    private configurazioneBandoLineaService: ConfigurazioneBandoLineaService,
    private datiBandoService: DatiBandoService,
    private handleExceptionService: HandleExceptionService
  ) {
    this.dataSourceEnteCompetenza = new MatTableDataSource();
    this.dataSourceTipoAiuto = new MatTableDataSource();
    this.dataSourceTemaPrioritario = new MatTableDataSource();
    this.dataSourceIndicatoreQSN = new MatTableDataSource();
    this.dataSourceIndicatoreRisultatoProgramma = new MatTableDataSource();
    this.dataSourceTipoPeriodo = new MatTableDataSource();
    this.dataSourceAreaScientifica = new MatTableDataSource();
    this.dataSourceModPagBandoLineaCor = new MatTableDataSource();
    this.dataSourceModPagTuttiBandoLinea = new MatTableDataSource();
    this.dataSourceCheckListAssociate = new MatTableDataSource();
    this.dataSourceRegola = new MatTableDataSource();
    this.dataSourceModelliDocumentiAssociati = new MatTableDataSource();
  }

  async ngOnInit() {

    this.currentTab = "ENTE DI COMPETENZA";
    this.showConservazione = false;

    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

        this.bando = this.params.get('titoloBando');
        this.idBando = +this.params.get('idBando');
        //this.idBando = 2742;
        this.bandoLinea = this.params.get('nomeBandoLinea');
        this.idLineaDiIntervento = this.params.get('idLineaDiIntervento');
        this.idBandoLinea = this.params.get('idBandoLinea');

        // controlli sulle nuove cose 
        this.checkFinpiemonte();

        this.bandoIsEnteCompetenzaFinpiemonte();

        this.loadedProcesso = false;
        this.subscribers.idProcesso = this.datiBandoService.getIdProcessoByProgrBandoLineaIntervento(+this.idBandoLinea).subscribe(data => {
          this.tipoProgrammazione = data === 1 ? "PRE-2016" : "2016";
          this.loadedProcesso = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento del processo.");
          this.loadedProcesso = true;
        });

        /* ---------------- Inizio popolamento elementi tab ente competenza -------------- */
        this.caricaDatiTabellaEnteCompetenza();
        this.caricaDropEnteCompetenza();
        /* ---------------- Fine popolamento elementi tab ente competenza -------------- */
        this.getModalitaAgevolazioneList();
        this.getParametriMonitoraggioTempi();
      }
    });
  }

  get descBreveRuoloEnteRespAffid() { return Constants.DESC_BREVE_RUOLO_ENTE_RESP_AFFIDAMENTI; }
  get descBreveRuoloEnteRagioneriaDel() { return Constants.DESC_BREVE_RUOLO_ENTE_RAGIONERIA_DELEGATA; }

  private _filterTemaPr(descrizione: string): Decodifica[] {
    const filterValue = descrizione.toLowerCase();
    return this.temiPrioritari.filter(option => option.descrizioneBreve.toLowerCase().includes(filterValue) || option.descrizione.toLowerCase().includes(filterValue));
  }

  private _filterIndQSN(descrizione: string): Decodifica[] {
    const filterValue = descrizione.toLowerCase();
    return this.indicatoriQSN.filter(option => option.descrizioneBreve.toLowerCase().includes(filterValue) || option.descrizione.toLowerCase().includes(filterValue));
  }

  private _filterIndRis(descrizione: string): IdDescBreveDescEstesaVo[] {
    const filterValue = descrizione.toLowerCase();
    return this.indicatoriRisultato.filter(option => option.descBreve.toLowerCase().includes(filterValue) || option.descEstesa.toLowerCase().includes(filterValue));
  }

  private _filterRegola(descrizione: string): IdDescBreveDescEstesaVo[] {
    const filterValue = descrizione.toLowerCase();
    return this.regole.filter(option => option.descBreve.toLowerCase().includes(filterValue) || option.descEstesa.toLowerCase().includes(filterValue));
  }

  private _filterTipoDoc(descrizione: string): Decodifica[] {
    const filterValue = descrizione.toLowerCase();
    return this.tipiDocumento.filter(option => option.descrizioneBreve.toLowerCase().includes(filterValue) || option.descrizione.toLowerCase().includes(filterValue));
  }

  private _filterMod(descrizione: string): Decodifica[] {
    const filterValue = descrizione.toLowerCase();
    return this.modalitaPagamento.filter(option => option.descrizioneBreve.toLowerCase().includes(filterValue) || option.descrizione.toLowerCase().includes(filterValue));
  }

  check(type: string) {
    setTimeout(() => {
      if (type === 'T') {
        if (!this.temaPrioritarioSelected || (this.temaPrGroup.controls['temaPrControl'] && this.temaPrioritarioSelected.value !== this.temaPrGroup.controls['temaPrControl'].value)) {
          this.temaPrGroup.controls['temaPrControl'].setValue(null);
          this.temaPrioritarioSelected = new FormControl();
        }
      } else if (type === 'IQ') {
        if (!this.indicatoreQSNSelected || (this.indQSNGroup.controls['indQSNControl'] && this.indicatoreQSNSelected.value !== this.indQSNGroup.controls['indQSNControl'].value)) {
          this.indQSNGroup.controls['indQSNControl'].setValue(null);
          this.indicatoreQSNSelected = new FormControl();
        }
      } else if (type === 'IR') {
        if (!this.indicatoreRisultatoSelected || (this.indRisGroup.controls['indRisControl'] && this.indicatoreRisultatoSelected.value !== this.indRisGroup.controls['indRisControl'].value)) {
          this.indRisGroup.controls['indRisControl'].setValue(null);
          this.indicatoreRisultatoSelected = new FormControl();
        }
      } else if (type === 'R') {
        if (!this.regolaSelected || (this.regolaGroup.controls['regolaControl'] && this.regolaSelected.value !== this.regolaGroup.controls['regolaControl'].value)) {
          this.regolaGroup.controls['regolaControl'].setValue(null);
          this.regolaSelected = new FormControl();
        }
      } else if (type === 'TD') {
        if (!this.tipoDocumentoSelected || (this.tipoDocGroup.controls['tipoDocControl'] && this.tipoDocumentoSelected.value !== this.tipoDocGroup.controls['tipoDocControl'].value)) {
          this.tipoDocGroup.controls['tipoDocControl'].setValue(null);
          this.tipoDocumentoSelected = new FormControl();
        }
      } else if (type === 'M') {
        if (!this.modalitaPagamentoSelected || (this.modGroup.controls['modControl'] && this.modalitaPagamentoSelected.value !== this.modGroup.controls['modControl'].value)) {
          this.modGroup.controls['modControl'].setValue(null);
          this.modalitaPagamentoSelected = new FormControl();
        }
      }
    }, 200);
  }

  click(event: any, type: string) {
    if (type === 'T') {
      this.temaPrioritarioSelected.setValue(event.option.value);
    } else if (type === 'IQ') {
      this.indicatoreQSNSelected.setValue(event.option.value);
    } else if (type === 'IR') {
      this.indicatoreRisultatoSelected.setValue(event.option.value);
    } else if (type === 'R') {
      this.regolaSelected.setValue(event.option.value);
    } else if (type === 'TD') {
      this.tipoDocumentoSelected.setValue(event.option.value);
    } else if (type === 'M') {
      this.modalitaPagamentoSelected.setValue(event.option.value);
    }
  }

  displayFnIndRis(element: IdDescBreveDescEstesaVo): string {
    return element && element.descBreve && element.descEstesa ? (element.descBreve + " - " + element.descEstesa).trim() : '';
  }

  displayFnDecodifica(element: Decodifica): string {
    return element && element.descrizione && element.descrizioneBreve ? (element.descrizioneBreve + " - " + element.descrizione).trim() : '';
  }

  displayFnRegola(element: IdDescBreveDescEstesaVo): string {
    return element && element.descBreve && element.descEstesa ? (element.descBreve + " - " + element.descEstesa).trim() : '';
  }

  /* ----------------- Inizio tab ente competenza --------------------------- */
  associaEnte() {
    this.resetMessageError();
    this.resetMessageSuccess();
    var comodoPeriodoConservazioneCorrente;
    var comodoPeriodoConservazioneGenerale;
    if (this.showConservazione) {
      comodoPeriodoConservazioneCorrente = this.periodoConservazioneCorrente;
      comodoPeriodoConservazioneGenerale = this.periodoConservazioneGenerale;
    } else {
      comodoPeriodoConservazioneCorrente = null;
      comodoPeriodoConservazioneGenerale = null;
    }



    if (this.mail != undefined && this.mail != null) {
      this.mail.replace(" ", "");
      if (this.mail == "")
        this.mail = undefined;
    }


    if (this.pec != undefined && this.pec != null) {
      this.pec.replace(" ", "");
      if (this.pec == "")
        this.pec = undefined;
    }


    console.log(this.mail)

    this.loadedAssociaEnteCompetenza = true;
    this.subscribers.associaEnteCompetenza = this.enteCompetenzaService.associaEnteCompetenza(this.user, this.idBandoLinea, this.enteSelected.idEnteCompetenza.toString(),
      this.ruoloSelected.id.toString(), this.settoreSelected == undefined ? null : this.settoreSelected.idSettoreEnte.toString(), this.pec, this.mail, this.tipoProgrammazione,
      comodoPeriodoConservazioneCorrente, comodoPeriodoConservazioneGenerale, this.flagMonitoraggioTempi).subscribe(data => {
        if (data) {
          if (data.esito) {
            this.showMessageSuccess("Ente di competenza associato con successo.");
            this.caricaDropEnteCompetenza();
            this.svuotaCampiEnte();
            this.caricaDatiTabellaEnteCompetenza();
            this.bandoIsEnteCompetenzaFinpiemonte();
          } else {
            this.showMessageError("Errore in fase di associazione dell'ente di competenza.");
          }
        }
        this.loadedAssociaEnteCompetenza = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di associazione dell'ente di competenza.");
        this.loadedAssociaEnteCompetenza = false;
      });
  }

  caricaDropEnteCompetenza() {
    this.ruoli = new Array<Decodifica>();
    this.loadedRuoli = true;
    this.subscribers.ruoli = this.enteCompetenzaService.ruoli(this.user, this.idLineaDiIntervento, this.idBandoLinea).subscribe(data => {
      this.ruoli = data;
      this.loadedRuoli = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei ruoli.");
      this.loadedRuoli = false;
    });

    this.enti = new Array<EnteDiCompetenzaDTO>();
    this.loadedEnte = true;
    this.subscribers.ente = this.enteCompetenzaService.ente(this.user).subscribe(data => {
      this.enti = data;
      this.loadedEnte = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli enti.");
      this.loadedEnte = false;
    });
  }

  changeEnte() {
    this.settori = new Array<SettoreEnteDTO>();
    if (!(this.enteSelected == null)) {
      this.loadedSettore = true;
      this.subscribers.settore = this.enteCompetenzaService.settore(this.user, this.enteSelected.idEnteCompetenza.toString()).subscribe(data => {
        if (data[0].descBreveSettore == "ko") {

        } else {
          this.settori = data;
        }
        this.loadedSettore = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei settori.");
        this.loadedSettore = false;
      });

      if (this.ruoloSelected.descrizione == "Destinatario delle comunicazioni") {
        this.showConservazione = true;
        this.periodoConservazioneCorrente = 5;
        this.periodoConservazioneGenerale = 10;
      } else {
        this.showConservazione = false;
      }
      this.preImpostaFlagTempi();
    } else {
      this.showConservazione = false;
    }
  }

  changeRuolo() {
    if (this.ruoloSelected.descrizione == "Destinatario delle comunicazioni") {

      if (!(this.enteSelected == null)) {
        this.showConservazione = true;
        this.periodoConservazioneCorrente = 5;
        this.periodoConservazioneGenerale = 10;
      } else {
        this.showConservazione = false;
      }
      this.preImpostaFlagTempi();
    } else {
      this.showConservazione = false;
    }
  }

  caricaDatiTabellaEnteCompetenza() {

    this.loadedEntiCompetenzaAssociati = true;
    this.subscribers.entiCompetenzaAssociati = this.enteCompetenzaService.entiCompetenzaAssociati(this.user, this.idBandoLinea).subscribe(data => {
      this.dataSourceEnteCompetenza = new MatTableDataSource(data);
      if (this.dataSourceEnteCompetenza.filteredData.length == 0) {
        this.showEnteCompetenzaTable = false;
      } else {
        this.showEnteCompetenzaTable = true;
      }
      this.loadedEntiCompetenzaAssociati = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli enti di competenza associati.");
      this.loadedEntiCompetenzaAssociati = false;
    });
  }

  eliminaEnte(row: EnteDiCompetenzaRuoloAssociatoDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaEnteCompetenzaAssociato = true;
    this.subscribers.eliminaEnteCompetenzaAssociato = this.enteCompetenzaService.eliminaEnteCompetenzaAssociato(this.user, row.progrBandoLineaEnteComp.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Ente competenza eliminato con successo.");
          this.caricaDropEnteCompetenza();
          this.caricaDatiTabellaEnteCompetenza();
          this.bandoIsEnteCompetenzaFinpiemonte();
        } else {
          this.showMessageError("Errore in fase di eliminazione dell'ente di competenza.");
        }
      }
      this.loadedEliminaEnteCompetenzaAssociato = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione dell'ente di competenza.");
      this.loadedEliminaEnteCompetenzaAssociato = false;
    });
  }

  svuotaCampiEnte() {
    this.ruoloSelected = undefined;
    this.enteSelected = undefined;
    this.settoreSelected = undefined;
    this.pec = undefined;

    this.enteAssociaForm.form.get("ruolo").markAsTouched();
    this.enteAssociaForm.form.get("ente").markAsUntouched();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    var element = document.getElementById('scrollId')
    element.scrollIntoView();
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    var element = document.getElementById('scrollId')
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

  verificaPCActa(row: EnteDiCompetenzaRuoloAssociatoDTO) {
    console.log("verificaPCActa");
    this.subscribers.verificaParolaChiaveActa = this.datiAggiuntiviBandoLineaService.verificaParolaChiaveActa(this.user, row.progrBandoLineaEnteComp.toString()).subscribe(data => {
      console.log("verificaPCActa data=" + data);
      if (data != null) {

        console.log("data.esito=" + data.esito);
        console.log("data.message=" + data.message);
        console.log("data.params=" + data.params);
      }
    }, err => {
      console.log("verificaPCActa errore");
    });
  }

  /* ----------------- Fine tab ente competenza --------------------------- */

  /* ----------------- Inizio tab dati aggiuntivi --------------------------- */
  associaTipoAiuto() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAssociaTipiAiuto = true;
    this.subscribers.associaTipiAiuto = this.datiAggiuntiviBandoLineaService.associaTipiAiuto(this.user, this.idBandoLinea, this.tipoAiutoSelected.id.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Tipo di aiuto associato con successo.");
          this.caricaDropDatiAggiuntiviTipiAiuto();
          this.tipoAiutoSelected = undefined;
          this.caricaDatiTabellaTipoAiuto();
        } else {
          this.showMessageError("Errore in fase di associazione del tipo di aiuto.");
        }
      }
      this.loadedAssociaTipiAiuto = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di associazione del tipo di aiuto.");
      this.loadedAssociaTipiAiuto = false;
    });
  }

  associaIndicatoreRisultatoProgramma() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAssociaIndicatoreRisultatoProgramma = true;
    this.subscribers.associaIndicatoreRisultatoProgramma = this.datiAggiuntiviBandoLineaService.associaIndicatoreRisultatoProgramma(this.user, this.idBandoLinea,
      this.indicatoreRisultatoSelected?.value?.id.toString()).subscribe(data => {
        if (data) {
          if (data.esito) {
            this.showMessageSuccess("Indicatore risultato programma associato con successo.");
            this.caricaDropDatiAggiuntiviIndicatoriRisultato();
            this.indicatoreRisultatoSelected = undefined;
            this.caricaDatiTabellaRisultatoProgramma();
          } else {
            this.showMessageError("Errore in fase di associazione dell'indicatore risultato programma.");
          }
        }
        this.loadedAssociaIndicatoreRisultatoProgramma = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di associazione dell'indicatore risultato programma.");
        this.loadedAssociaIndicatoreRisultatoProgramma = false;
      });
  }

  associaTipoPeriodo() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAssociaTipoPeriodo = true;
    this.subscribers.associaTipoPeriodo = this.datiAggiuntiviBandoLineaService.associaTipoPeriodo(this.user, this.idBandoLinea, this.tipoPeriodoSelected.id.toString(), this.tipoProgrammazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Tipo periodo associato con successo.");
          this.caricaDropDatiAggiuntiviTipiPeriodo();
          this.tipoPeriodoSelected = undefined;
          this.caricaDatiTabellaTipoPeriodo();
        } else {
          this.showMessageError("Errore in fase di associazione del tipo periodo.");
        }
      }
      this.loadedAssociaTipoPeriodo = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di associazione del tipo periodo.");
      this.loadedAssociaTipoPeriodo = false;
    });
  }

  associaTemaPrioritario() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAssociaTemaPrioritario = true;
    this.subscribers.associaTemaPrioritario = this.datiAggiuntiviBandoLineaService.associaTemaPrioritario(this.user, this.temaPrioritarioSelected?.value?.id.toString(), this.idBandoLinea).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Tema prioritario associato con successo.");
          this.caricaDropDatiAggiuntiviTemiPrioritari();
          this.temaPrioritarioSelected = undefined;
          this.caricaDatiTabellaTemaPrioritario();
        } else {
          this.showMessageError("Errore in fase di associazione del tema prioritario.");
        }
      }
      this.loadedAssociaTemaPrioritario = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di associazione del tema prioritario.");
      this.loadedAssociaTemaPrioritario = false;
    });
  }

  associaIndicatoreQSN() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAssociaIndicatoreQSN = true;
    this.subscribers.associaIndicatoreQSN = this.datiAggiuntiviBandoLineaService.associaIndicatoreQSN(this.user, this.idBandoLinea, this.indicatoreQSNSelected?.value?.id.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Indicatore QSN associato con successo.");
          this.caricaDropDatiAggiuntiviIndicatoriQSN();
          this.indicatoreQSNSelected = undefined;
          this.caricaDatiTabellaIndicatoreQSN();
        } else {
          this.showMessageError("Errore in fase di associazione dell'indicatore QSN.");
        }
      }
      this.loadedAssociaIndicatoreQSN = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di associazione dell'indicatore QSN.");
      this.loadedAssociaIndicatoreQSN = false;
    });
  }

  caricaDatiTabellaTipoAiuto() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedFindTipiAiutoAssociati = true;
    this.subscribers.findTipiAiutoAssociati = this.datiAggiuntiviBandoLineaService.findTipiAiutoAssociati(this.user, this.idBandoLinea).subscribe(data => {
      if (data) {
        this.dataSourceTipoAiuto = new MatTableDataSource(data);
        if (this.dataSourceTipoAiuto.filteredData.length == 0) {
          this.showTipoAiutoTable = false;
        } else {
          this.showTipoAiutoTable = true;
        }
      }
      this.loadedFindTipiAiutoAssociati = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei tipo aiuto associati.");
      this.loadedFindTipiAiutoAssociati = false;
    });
  }

  eliminaTipoAiuto(row: TipoDiAiutoAssociatoDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaTipiAiuto = true;
    this.subscribers.eliminaTipiAiuto = this.datiAggiuntiviBandoLineaService.eliminaTipiAiuto(this.user, this.idBandoLinea, row.idTipoAiuto.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Tipo aiuto eliminato con successo");
          this.caricaDropDatiAggiuntiviTipiAiuto();
          this.caricaDatiTabellaTipoAiuto();
        } else {
          this.showMessageError("Errore in fase di eliminazione del tipo aiuto.");
        }
      }
      this.loadedEliminaTipiAiuto = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione del tipo aiuto.");
      this.loadedEliminaTipiAiuto = false;
    });
  }

  eliminaTemaPrioritario(row: Decodifica) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaTemaPrioritarioAssociato = true;
    this.subscribers.eliminaTemaPrioritarioAssociato = this.datiAggiuntiviBandoLineaService.eliminaTemaPrioritarioAssociato(this.user, row.id.toString(), this.idBandoLinea).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Tema prioritario eliminato con successo.");
          this.caricaDropDatiAggiuntiviTemiPrioritari();
          this.caricaDatiTabellaTemaPrioritario();
        } else {
          this.showMessageError("Errore in fase di eliminazione del tema prioritario.");
        }
      }
      this.loadedEliminaTemaPrioritarioAssociato = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione del tema prioritario.");
      this.loadedEliminaTemaPrioritarioAssociato = false;
    });
  }

  caricaDatiTabellaTemaPrioritario() {
    this.loadedTemaPrioritarioAssociato = true;
    this.subscribers.temaPrioritarioAssociato = this.datiAggiuntiviBandoLineaService.temaPrioritarioAssociato(this.user, this.idBandoLinea).subscribe(data => {
      if (data) {
        this.dataSourceTemaPrioritario = new MatTableDataSource(data);
        if (this.dataSourceTemaPrioritario.filteredData.length == 0) {
          this.showTemaPrioritarioTable = false;
        } else {
          this.showTemaPrioritarioTable = true;
        }
      }
      this.loadedTemaPrioritarioAssociato = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei temi prioritari associati.");
      this.loadedTemaPrioritarioAssociato = false;
    });
  }

  caricaDatiTabellaIndicatoreQSN() {
    this.loadedIndicatoriQSNAssociati = true;
    this.subscribers.indicatoriQSNAssociati = this.datiAggiuntiviBandoLineaService.indicatoriQSNAssociati(this.user, this.idBandoLinea).subscribe(data => {
      if (data) {
        this.dataSourceIndicatoreQSN = new MatTableDataSource(data);
        if (this.dataSourceIndicatoreQSN.filteredData.length == 0) {
          this.showIndicatoreQSNTable = false;
        } else {
          this.showIndicatoreQSNTable = true;
        }
      }
      this.loadedIndicatoriQSNAssociati = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli indicatori QSN.");
      this.loadedIndicatoriQSNAssociati = false;
    });
  }

  caricaDatiTabellaRisultatoProgramma() {
    this.loadedIndicatoriRisultatoProgrammaAssociati = true;
    this.subscribers.indicatoriRisultatoProgrammaAssociati = this.datiAggiuntiviBandoLineaService.indicatoriRisultatoProgrammaAssociati(this.user, this.idBandoLinea).subscribe(data => {
      if (data) {
        this.dataSourceIndicatoreRisultatoProgramma = new MatTableDataSource(data);
        if (this.dataSourceIndicatoreRisultatoProgramma.filteredData.length == 0) {
          this.showIndicatoreRisultatoProgrammaTable = false;
        } else {
          this.showIndicatoreRisultatoProgrammaTable = true;
        }
      }
      this.loadedIndicatoriRisultatoProgrammaAssociati = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli indicatori risultato programma associati.");
      this.loadedIndicatoriRisultatoProgrammaAssociati = false;
    });
  }

  eliminaIndicatoreQSN(row: IdDescBreveDescEstesaVo) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaIndicatoreQSNAssociato = true;
    this.subscribers.eliminaIndicatoreQSNAssociato = this.datiAggiuntiviBandoLineaService.eliminaIndicatoreQSNAssociato(this.user, this.idBandoLinea, row.id.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Indicatore QSN eliminato con successo.");
          this.caricaDropDatiAggiuntiviIndicatoriQSN();
          this.caricaDatiTabellaIndicatoreQSN();
        } else {
          this.showMessageError("Errore in fase di eliminazione dell'indicatore QSN.");
        }
      }
      this.loadedEliminaIndicatoreQSNAssociato = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione dell'indicatore QSN.");
      this.loadedEliminaIndicatoreQSNAssociato = false;
    });
  }

  eliminaIndicatoreRisultatoProgramma(row: IdDescBreveDescEstesaVo) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaIndicatoreRisultatoProgramma = true;
    this.subscribers.eliminaIndicatoreRisultatoProgramma = this.datiAggiuntiviBandoLineaService.eliminaIndicatoreRisultatoProgramma(this.user, this.idBandoLinea, row.id.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Indicatore risultato programma eliminato con successo.");
          this.caricaDropDatiAggiuntiviIndicatoriRisultato();
          this.caricaDatiTabellaRisultatoProgramma();
        } else {
          this.showMessageError("Errore in fase di eliminazione dell'indicatore risultato programma.");
        }
      }
      this.loadedEliminaIndicatoreRisultatoProgramma = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione dell'indicatore risultato programma.");
      this.loadedEliminaIndicatoreRisultatoProgramma = false;
    });
  }

  eliminaTipoPeriodo(row: Decodifica) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaTipoPeriodo = true;
    this.subscribers.eliminaTipoPeriodo = this.datiAggiuntiviBandoLineaService.eliminaTipoPeriodo(this.user, this.idBandoLinea, row.id.toString(), this.tipoProgrammazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Tipo periodo eliminato con successo.");
          this.caricaDropDatiAggiuntiviTipiPeriodo();
          this.caricaDatiTabellaTipoPeriodo();
        } else {
          this.showMessageError("Errore in fase di eliminazione del tipo periodo.");
        }
      }
      this.loadedEliminaTipoPeriodo = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione del tipo periodo.");
      this.loadedEliminaTipoPeriodo = false;
    });
  }

  caricaDatiTabellaTipoPeriodo() {
    this.loadedTipoPeriodoAssociato = true;
    this.subscribers.tipoPeriodoAssociato = this.datiAggiuntiviBandoLineaService.tipoPeriodoAssociato(this.user, this.idBandoLinea).subscribe(data => {
      if (data) {
        this.dataSourceTipoPeriodo = new MatTableDataSource(data);
        if (this.dataSourceTipoPeriodo.filteredData.length == 0) {
          this.showTipoPeriodoTable = false;
        } else {
          this.showTipoPeriodoTable = true;
        }
      }
      this.loadedTipoPeriodoAssociato = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei tipi periodo associati.");
      this.loadedTipoPeriodoAssociato = false;
    });
  }

  associaAreaScientifica() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAssociaAreaScientifica = true;
    this.subscribers.associaAreaScientifica = this.datiAggiuntiviBandoLineaService.associaAreaScientifica(this.user, this.idBandoLinea, this.areaScientificaSelected.id.toString(), this.areaScientificaSelected.descEstesa).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Area scientifica associata con successo.");
          this.caricaDropDatiAggiuntiviAreeScientifiche();
          this.areaScientificaSelected = undefined;
          this.caricaDatiTabellaAreaScientifica();
        } else {
          this.showMessageError("Errore in fase di associazione dell'area scientifica.");
        }
      }
      this.loadedAssociaAreaScientifica = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di associazione dell'area scientifica.");
      this.loadedAssociaAreaScientifica = false;
    });
  }

  eliminaAreaScientifica(row: Decodifica) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaAreaScientificaAssociata = true;
    this.subscribers.eliminaAreaScientificaAssociata = this.datiAggiuntiviBandoLineaService.eliminaAreaScientificaAssociata(this.user, this.idBandoLinea).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Area scientifica eliminata con successo.");
          this.caricaDropDatiAggiuntiviAreeScientifiche();
          this.caricaDatiTabellaAreaScientifica();
        } else {
          this.showMessageError("Errore in fase di eliminazione dell'area scientifica.");
        }
      }
      this.loadedEliminaAreaScientificaAssociata = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione dell'area scientifica.");
      this.loadedEliminaAreaScientificaAssociata = false;
    });
  }

  caricaDatiTabellaAreaScientifica() {
    this.loadedAreeScientificheAssociate = true;
    this.subscribers.areeScientificheAssociate = this.datiAggiuntiviBandoLineaService.areeScientificheAssociate(this.user, this.idBandoLinea).subscribe(data => {
      if (data) {
        this.dataSourceAreaScientifica = new MatTableDataSource(data);
        if (this.dataSourceAreaScientifica.filteredData.length == 0) {
          this.showAreaScientificaTable = false;
        } else {
          this.showAreaScientificaTable = true;
        }
      }
      this.loadedAreeScientificheAssociate = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle aree scientifiche associate.");
      this.loadedAreeScientificheAssociate = false;
    });
  }

  aggiungiAreaScientifica() {
    this.resetMessageError();
    this.resetMessageSuccess();
    const dialogRef = this.dialog.open(NuovaAreaScientificaDialogComponent, {
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!(result == undefined)) {
        var codice = result.data[0];
        var areaScientifica = result.data[1];
        this.progressivoAreaScientifica = this.progressivoAreaScientifica - 1;
        this.areeScientifiche.push(new IdDescBreveDescEstesaVo(this.progressivoAreaScientifica, codice, areaScientifica));
      }
    })
  }

  caricaDropDatiAggiuntiviTipiAiuto() {
    this.tipiAiuto = new Array<Decodifica>();
    this.loadedTipiAiutoDaAssociare = true;
    this.subscribers.tipiAiutoDaAssociare = this.datiAggiuntiviBandoLineaService.tipiAiutoDaAssociare(this.user, this.idLineaDiIntervento).subscribe(data => {
      this.tipiAiuto = data;
      this.loadedTipiAiutoDaAssociare = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei tipi aiuto.");
      this.loadedTipiAiutoDaAssociare = false;
    });
  }

  caricaDropDatiAggiuntiviTemiPrioritari() {
    this.temiPrioritari = new Array<Decodifica>();
    this.temaPrGroup.get("temaPrControl")?.setValue(null);
    this.temaPrioritarioSelected?.setValue(null);
    this.loadedTemaPrioritario = true;
    this.subscribers.temaPrioritario = this.datiAggiuntiviBandoLineaService.temaPrioritario(this.user, this.idLineaDiIntervento).subscribe(data => {
      this.temiPrioritari = data;

      this.filteredOptionsTemaPr = this.temaPrGroup.controls['temaPrControl'].valueChanges
        .pipe(
          startWith(''),
          map(value => typeof value === 'string' || value == null ? value : value.descrizione),
          map(name => name ? this._filterTemaPr(name) : (this.temiPrioritari ? this.temiPrioritari.slice() : null))
        );
      this.loadedTemaPrioritario = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei temi prioritari.");
      this.loadedTemaPrioritario = false;
    });
  }

  caricaDropDatiAggiuntiviIndicatoriQSN() {
    this.indicatoriQSN = new Array<Decodifica>();
    this.indQSNGroup.get("indQSNControl")?.setValue(null);
    this.indicatoreQSNSelected?.setValue(null);
    this.loadedIndicatoriQSN = true;
    this.subscribers.indicatoriQSN = this.datiAggiuntiviBandoLineaService.indicatoriQSN(this.user, this.idLineaDiIntervento).subscribe(data => {
      this.indicatoriQSN = data;

      this.filteredOptionsindQSN = this.indQSNGroup.controls['indQSNControl'].valueChanges
        .pipe(
          startWith(''),
          map(value => typeof value === 'string' || value == null ? value : value.descrizione),
          map(name => name ? this._filterIndQSN(name) : (this.indicatoriQSN ? this.indicatoriQSN.slice() : null))
        );
      this.loadedIndicatoriQSN = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli indicatori QSN.");
      this.loadedIndicatoriQSN = false;
    });
  }

  caricaDropDatiAggiuntiviIndicatoriRisultato() {
    this.indicatoriRisultato = new Array<IdDescBreveDescEstesaVo>();
    this.indRisGroup.get("indRisControl")?.setValue(null);
    this.indicatoreRisultatoSelected?.setValue(null);
    this.loadedIndicatoriRisultatoProgramma = true;
    this.subscribers.indicatoriRisultatoProgramma = this.datiAggiuntiviBandoLineaService.indicatoriRisultatoProgramma(this.user, this.idBandoLinea).subscribe(data => {
      this.indicatoriRisultato = data;

      this.filteredOptionsindRis = this.indRisGroup.controls['indRisControl'].valueChanges
        .pipe(
          startWith(''),
          map(value => typeof value === 'string' || value == null ? value : value.descrizione),
          map(name => name ? this._filterIndRis(name) : (this.indicatoriRisultato ? this.indicatoriRisultato.slice() : null))
        );
      this.loadedIndicatoriRisultatoProgramma = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli indicatori risultato.");
      this.loadedIndicatoriRisultatoProgramma = false;
    });
  }

  caricaDropDatiAggiuntiviTipiPeriodo() {
    this.tipiPeriodo = new Array<Decodifica>();
    this.loadedTipoPeriodo = true;
    this.subscribers.tipoPeriodo = this.datiAggiuntiviBandoLineaService.tipoPeriodo(this.user).subscribe(data => {
      this.tipiPeriodo = data;
      this.loadedTipoPeriodo = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei tipi periodo.");
      this.loadedTipoPeriodo = false;
    });
  }

  caricaDropDatiAggiuntiviAreeScientifiche() {
    this.areeScientifiche = new Array<IdDescBreveDescEstesaVo>();
    this.loadedAreaScientifica = true;
    this.subscribers.areaScientifica = this.datiAggiuntiviBandoLineaService.areaScientifica(this.user).subscribe(data => {
      this.areeScientifiche = data;
      this.loadedAreaScientifica = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle aree scientifiche.");
      this.loadedAreaScientifica = false;
    });
  }
  caricaDropDatiAggiuntivi() {
    this.tipiAiuto = new Array<Decodifica>();
    this.loadedTipiAiutoDaAssociare = true;
    this.subscribers.tipiAiutoDaAssociare = this.datiAggiuntiviBandoLineaService.tipiAiutoDaAssociare(this.user, this.idLineaDiIntervento).subscribe(data => {
      this.tipiAiuto = data;
      this.loadedTipiAiutoDaAssociare = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei tipi aiuto.");
      this.loadedTipiAiutoDaAssociare = false;
    });

    this.caricaDropDatiAggiuntiviTemiPrioritari();
    this.caricaDropDatiAggiuntiviIndicatoriQSN();
    this.caricaDropDatiAggiuntiviIndicatoriRisultato();

    this.tipiPeriodo = new Array<Decodifica>();
    this.loadedTipoPeriodo = true;
    this.subscribers.tipoPeriodo = this.datiAggiuntiviBandoLineaService.tipoPeriodo(this.user).subscribe(data => {
      this.tipiPeriodo = data;
      this.loadedTipoPeriodo = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei tipi periodo.");
      this.loadedTipoPeriodo = false;
    });

    this.areeScientifiche = new Array<IdDescBreveDescEstesaVo>();
    this.loadedAreaScientifica = true;
    this.subscribers.areaScientifica = this.datiAggiuntiviBandoLineaService.areaScientifica(this.user).subscribe(data => {
      this.areeScientifiche = data;
      this.loadedAreaScientifica = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle aree scientifiche.");
      this.loadedAreaScientifica = false;
    });

  }
  /* ----------------- Fine tab dati aggiuntivi --------------------------- */

  /* ----------------- Inizio tab regole --------------------------- */
  associaRegola() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAssociaRegola = true;
    this.subscribers.associaRegola = this.regoleService.associaRegola(this.user, this.idBandoLinea, this.regolaSelected?.value?.id.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Regola associata con successo.");
          this.caricaDropRegole();
          this.caricaDatiTabellaRegola();
        } else {
          this.showMessageError("Errore in fase di associazione della regola.");
        }
      }
      this.loadedAssociaRegola = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di associazione della regola.");
      this.loadedAssociaRegola = false;
    });
  }

  caricaDatiTabellaRegola() {
    this.loadedRegoleAssociate = true;
    this.subscribers.regoleAssociate = this.regoleService.regoleAssociate(this.user, this.idBandoLinea).subscribe(data => {
      if (data) {
        this.dataSourceRegola = new MatTableDataSource(data);
        if (this.dataSourceRegola.filteredData.length == 0) {
          this.showRegolaTable = false;
        } else {
          this.showRegolaTable = true;
        }
      }
      this.loadedRegoleAssociate = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle regole associate.");
      this.loadedRegoleAssociate = false;
    });
  }

  caricaDropRegole() {
    this.loadedCercaRegole = true;
    this.regole = new Array<IdDescBreveDescEstesaVo>();
    this.regolaGroup.get("regolaControl")?.setValue(null);
    this.regolaSelected?.setValue(null);
    this.regoleService.cercaRegole(this.user).subscribe(data => {
      this.regole = data;
      this.filteredOptionsRegola = this.regolaGroup.controls['regolaControl'].valueChanges
        .pipe(
          startWith(''),
          map(value => typeof value === 'string' || value == null ? value : value.descrizione),
          map(name => name ? this._filterRegola(name) : (this.regole ? this.regole.slice() : null))
        );
      this.loadedCercaRegole = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle regole.");
      this.loadedCercaRegole = false;
    });
  }

  eliminaRegola(row: RegolaAssociataDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaRegola = true;
    this.subscribers.eliminaRegola = this.regoleService.eliminaRegola(this.user, this.idBandoLinea, row.idRegola.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Regola eliminata con successo.");
          this.caricaDatiTabellaRegola();
        } else {
          this.showMessageError("Errore in fase di eliminazione della regola.");
        }
      }
      this.loadedEliminaRegola = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione della regola.");
      this.loadedEliminaRegola = false;
    });
  }
  /* ----------------- Fine tab regole --------------------------- */

  /* ----------------- Inizio tab modelli documento --------------------------- */
  associaModelliDocumento() {
    this.resetMessageError();
    this.resetMessageSuccess();
    var elencoIdTipoDocumentoIndex = new Array<number>();
    this.task.subtasks.forEach(t => {
      if (t.completed) {
        elencoIdTipoDocumentoIndex.push(t.id);
      }
    });

    var elencoIdTipoDocumentoIndexAssociato = new Array<number>();
    this.dataSourceModelliDocumentiAssociati.data.forEach(row => {
      elencoIdTipoDocumentoIndexAssociato.push(row.idTipoDocumentoIndex);
    });

    var request = new ModelloDTO(this.user.idUtente, this.bandoLineaSelected.id, Number(this.idBandoLinea), elencoIdTipoDocumentoIndex, elencoIdTipoDocumentoIndexAssociato);

    this.loadedInserisciModello = true;
    this.subscribers.inserisciModello = this.modelliDocumentoService.inserisciModello(request).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Modello di documento associato con successo");
          this.task.subtasks.forEach(t => t.completed = false);
          this.caricaDatiTabellaModelliDocumentiAssociati();
        } else {
          let duplicati: string = "";
          let count = 0;
          for (let idTipoDocumento of elencoIdTipoDocumentoIndex) {
            if (elencoIdTipoDocumentoIndexAssociato.includes(idTipoDocumento)) {
              duplicati += this.task.subtasks.find(t => t.id === idTipoDocumento).descrizione + ", ";
              count++;
            }
          }
          if (count > 0) {
            duplicati = duplicati.substring(0, duplicati.length - 2);
            if (count === 1) {
              duplicati += " è già stato associato.";
            } else {
              duplicati += " sono già stati associati.";
            }
            this.showMessageError(duplicati);
          }
        }

      }

      this.loadedInserisciModello = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di associazione del modello di documento");
      this.loadedInserisciModello = false;
    });
  }

  checkModDocDuplicSelAlmeno1() {
    var almeno1 = false;
    if (!(this.task == undefined)) {
      this.task.subtasks.forEach(t => {
        if (t.completed) {
          almeno1 = true;
        }
      })
    }
    return almeno1;
  }

  caricaDatiTabellaModelliDocumentiAssociati() {
    var comodoArrayModelliDocumentoAssociati = new Array<TemplateDTO>();
    this.loadedFindModelliAssociati = true;
    this.subscribers.findModelliAssociati = this.modelliDocumentoService.findModelliAssociati(this.user, this.idBandoLinea).subscribe(data => {
      console.log(data);
      comodoArrayModelliDocumentoAssociati = data;
      this.dataSourceModelliDocumentiAssociati = new MatTableDataSource(comodoArrayModelliDocumentoAssociati);
      if (this.dataSourceModelliDocumentiAssociati.filteredData.length == 0) {
        this.showModelliDocumentiAssociatiTable = false;
      } else {
        this.showModelliDocumentiAssociatiTable = true;
      }
      this.loadedFindModelliAssociati = false;
    }, err => {
      this.dataSourceModelliDocumentiAssociati = new MatTableDataSource(comodoArrayModelliDocumentoAssociati);
      if (this.dataSourceModelliDocumentiAssociati.filteredData.length == 0) {
        this.showModelliDocumentiAssociatiTable = false;
      } else {
        this.showModelliDocumentiAssociatiTable = true;
      }
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei modelli di documento associati.");
      this.loadedFindModelliAssociati = false;
    });
  }

  caricaDropModelliDocumento() {
    this.bandiLinea = new Array<Decodifica>();
    this.loadedFindBandoLinea = true;
    this.subscribers.findBandoLinea = this.modelliDocumentoService.findBandoLinea(this.user).subscribe(data => {
      this.bandiLinea = data;
      this.loadedFindBandoLinea = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei bandi linea");
      this.loadedFindBandoLinea = false;
    });
  }

  changeBandoLinea() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.dataModelliDocumentiDuplicabili = new Array<Decodifica>();
    if (!(this.bandoLineaSelected == null)) {
      this.loadedFindModelliDaAssociare = true;
      this.subscribers.findModelliDaAssociare = this.modelliDocumentoService.findModelliDaAssociare(this.user, this.bandoLineaSelected.id.toString()).subscribe(data => {

        this.dataModelliDocumentiDuplicabili = data;

        if (this.dataModelliDocumentiDuplicabili.length > 0) {
          this.showModelliDocumentiDuplicabili = true;
          this.trasformaArrayDecodificaInModDocDuplic(this.dataModelliDocumentiDuplicabili);
        } else {
          this.showModelliDocumentiDuplicabili = false;
        }
        this.loadedFindModelliDaAssociare = false;
      }, err => {
        if (this.dataModelliDocumentiDuplicabili.length > 0) {
          this.showModelliDocumentiDuplicabili = true;
          this.trasformaArrayDecodificaInModDocDuplic(this.dataModelliDocumentiDuplicabili);
        } else {
          this.showModelliDocumentiDuplicabili = false;
        }
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei modelli di documento duplicabili.");
        this.loadedFindModelliDaAssociare = false;
      });
    } else {
      if (this.dataModelliDocumentiDuplicabili.length > 0) {
        this.showModelliDocumentiDuplicabili = true;
        this.trasformaArrayDecodificaInModDocDuplic(this.dataModelliDocumentiDuplicabili);
      } else {
        this.showModelliDocumentiDuplicabili = false;
      }
    }
  }

  downloadModelloDocumentoAssociato(id: number) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedGetNomeDocumento = true;
    this.subscribers.getNomeDocumento = this.modelliDocumentoService.getNomeDocumento(this.user, this.idBandoLinea, id.toString()).subscribe(nomeFile => {
      this.loadedDownloadDocumento = true;
      this.subscribers.downloadDocumento = this.modelliDocumentoService.downloadDocumento(this.user, id.toString(), this.idBandoLinea).subscribe(data => {
        if (data) {
          saveAs(data, nomeFile);
        }
        this.loadedDownloadDocumento = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download del modello di documento associato.");
        this.loadedDownloadDocumento = false;
      });
      this.loadedGetNomeDocumento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del modello di documento associato");
      this.loadedGetNomeDocumento = false;
    });
  }

  eliminaModelloDocumentoAssociato(row: TemplateDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaModelloAssociato = true;
    this.subscribers.eliminaModelloAssociato = this.modelliDocumentoService.eliminaModelloAssociato(this.user, this.idBandoLinea, row.idTipoDocumentoIndex.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Modello documento eliminato con successo.");
          this.caricaDatiTabellaModelliDocumentiAssociati();
        } else {
          this.showMessageError("Errore in fase di eliminazione del modello di documento.");
        }
      }
      this.loadedEliminaModelloAssociato = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione del modello di documento.");
      this.loadedEliminaModelloAssociato = false;
    });
  }

  generaModelloDocumentoAssociato(id: number) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedGeneraDocumento = true;
    this.subscribers.generaDocumento = this.modelliDocumentoService.generaDocumento(this.user, this.idBandoLinea, id.toString()).subscribe(data => {
      this.showMessageSuccess("Modello di documento associato generato con successo.");
      this.loadedGeneraDocumento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di generazione del modello di documento associato");
      this.loadedGeneraDocumento = false;
    })
  }

  modificaModelloDocumentoAssociato(id: number) {
    var comodo = new Array<any>();
    comodo.push(this.user);
    comodo.push(this.idBandoLinea);
    comodo.push(id);
    this.dialog.open(ModificaModelloDocumentoDialogComponent, {
      width: '80%',
      height: '90%',
      data: comodo
    });
  }

  someComplete(): boolean {
    if (this.task.subtasks == null) {
      return false;
    }
    return this.task.subtasks.filter(t => t.completed).length > 0 && !this.allComplete;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.task.subtasks == null) {
      return;
    }
    this.task.subtasks.forEach(t => t.completed = completed);
  }

  trasformaArrayDecodificaInModDocDuplic(arrDecodifica: Array<Decodifica>) {

    var comodoArrayDecodificaInModDocDuplic = new Array<ItemModelloDocumentoDuplicabile>();
    for (let item of arrDecodifica) {
      var comodo: ItemModelloDocumentoDuplicabile = {
        id: item.id, descrizione: item.descrizione, descrizioneBreve: item.descrizioneBreve, dataInizioValidita: item.dataInizioValidita, dataFineValidita: item.dataFineValidita, completed: false, color: 'primary'
      }
      comodoArrayDecodificaInModDocDuplic.push(comodo);
    }

    this.task = {
      name: 'Tutti i documenti',
      completed: false,
      color: 'primary',
      subtasks: comodoArrayDecodificaInModDocDuplic
    };
  }

  updateAllComplete() {
    this.allComplete = this.task.subtasks != null && this.task.subtasks.every(t => t.completed);
  }
  /* ----------------- Fine tab modelli documento --------------------------- */

  /* ----------------- Inizio tab doc / mod pagam --------------------------- */
  associaDocModalitaPagamento() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.checkedTuttiBandoLinea) {
      this.loadedAssociaDocPagamTuttiBL = true;
      this.subscribers.associaDocPagamTuttiBL = this.docModPagService.associaDocPagamTuttiBL(this.user, this.tipoDocumentoSelected?.value?.id.toString(),
        this.modalitaPagamentoSelected?.value?.id.toString()).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.showMessageSuccess("Documento modalità di pagamento tutti i bando linea associato con successo.");
              this.caricaDatiTabellaModPagTuttiBandoLinea();
              this.tipoDocGroup.get("tipoDocControl").setValue(null);
              this.tipoDocumentoSelected.setValue(null);
              this.modGroup.get("modControl").setValue(null);
              this.modalitaPagamentoSelected.setValue(null);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedAssociaDocPagamTuttiBL = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di associazione del documento modalita di pagamento tutti i bando linea.");
          this.loadedAssociaDocPagamTuttiBL = false;
        });
    } else {
      this.loadedAssociaDocPagamento = true;
      this.subscribers.associaDocPagamento = this.docModPagService.associaDocPagamento(this.user, this.idBandoLinea, this.tipoDocumentoSelected?.value?.id.toString(),
        this.modalitaPagamentoSelected?.value?.id.toString()).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.showMessageSuccess("Documento modalità di pagamento associato con successo.");
              this.caricaDatiTabellaModPagBandoLineaCor();
              this.tipoDocGroup.get("tipoDocControl").setValue(null);
              this.tipoDocumentoSelected.setValue(null);
              this.modGroup.get("modControl").setValue(null);
              this.modalitaPagamentoSelected.setValue(null);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedAssociaDocPagamento = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di associazione del documento modalita di pagamento.");
          this.loadedAssociaDocPagamento = false;
        });
    }
  }

  aggiungiTipoDocumento() {
    this.dialog.open(NuovoTipoDocumentoDialogComponent, {
      width: '500px',
      data: this.user
    });
  }

  aggiungiModalitaPagamento() {
    this.dialog.open(NuovoModalitaPagamentoDialogComponent, {
      width: '500px',
      data: this.user
    });
  }

  caricaDatiTabellaModPagBandoLineaCor() {
    this.loadedDocPagamentiAssociati = true;
    this.subscribers.docPagamentiAssociati = this.docModPagService.docPagamentiAssociati(this.user, this.idBandoLinea).subscribe(data => {
      this.dataSourceModPagBandoLineaCor = new MatTableDataSource(data);
      this.paginatorModPagBandoLineaCor.length = data.length;
      this.paginatorModPagBandoLineaCor.pageIndex = 0;
      this.dataSourceModPagBandoLineaCor.paginator = this.paginatorModPagBandoLineaCor;
      if (this.dataSourceModPagBandoLineaCor.filteredData.length == 0) {
        this.showModPagBandoLineaCorTable = false;
      } else {
        this.showModPagBandoLineaCorTable = true;
      }
      this.loadedDocPagamentiAssociati = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei doc pagamenti bando linea corrente.");
      this.loadedDocPagamentiAssociati = false;
    });
  }

  caricaDatiTabellaModPagTuttiBandoLinea() {
    this.loadedDocPagamentiAssociatiTuttiBL = true;
    this.subscribers.docPagamentiAssociatiTuttiBL = this.docModPagService.docPagamentiAssociatiTuttiBL(this.user).subscribe(data => {
      this.dataSourceModPagTuttiBandoLinea = new MatTableDataSource(data);
      this.paginatorModPagTuttiBandoLinea.length = data.length;
      this.paginatorModPagTuttiBandoLinea.pageIndex = 0;
      this.dataSourceModPagTuttiBandoLinea.paginator = this.paginatorModPagTuttiBandoLinea;
      if (this.dataSourceModPagTuttiBandoLinea.filteredData.length == 0) {
        this.showModPagTuttiBandoLineaTable = false;
      } else {
        this.showModPagTuttiBandoLineaTable = true;
      }
      this.loadedDocPagamentiAssociatiTuttiBL = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei doc pagamenti tutti i bando linea.");
      this.loadedDocPagamentiAssociatiTuttiBL = false;
    });
  }

  caricaDropDocModPag() {
    this.tipiDocumento = new Array<Decodifica>();
    this.tipoDocGroup.get("tipoDocControl")?.setValue(null);
    this.tipoDocumentoSelected?.setValue(null);
    this.loadedTipiDocumento = true;
    this.subscribers.tipiDocumento = this.docModPagService.tipiDocumento(this.user).subscribe(data => {
      this.tipiDocumento = data;

      this.filteredOptionsTipoDoc = this.tipoDocGroup.controls['tipoDocControl'].valueChanges
        .pipe(
          startWith(''),
          map(value => typeof value === 'string' || value == null ? value : value.descrizione),
          map(name => name ? this._filterTipoDoc(name) : (this.tipiDocumento ? this.tipiDocumento.slice() : null))
        );

      this.loadedTipiDocumento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei tipi documento.");
      this.loadedTipiDocumento = false;
    });

    this.modalitaPagamento = new Array<Decodifica>();
    this.modGroup.get("modControl")?.setValue(null);
    this.modalitaPagamentoSelected?.setValue(null);
    this.loadedModalitaPagamento = true;
    this.subscribers.modalitaPagamento = this.docModPagService.modalitaPagamento(this.user).subscribe(data => {
      this.modalitaPagamento = data;

      this.filteredOptionsMod = this.modGroup.controls['modControl'].valueChanges
        .pipe(
          startWith(''),
          map(value => typeof value === 'string' || value == null ? value : value.descrizione),
          map(name => name ? this._filterMod(name) : (this.modalitaPagamento ? this.modalitaPagamento.slice() : null))
        );
      this.loadedModalitaPagamento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle modalita pagamento.");
      this.loadedModalitaPagamento = false;
    });
  }

  eliminaModPagBandoLineaCor(row: DocPagamBandoLineaDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaDocPagamAssociato = true;
    this.subscribers.eliminaDocPagamAssociato = this.docModPagService.eliminaDocPagamAssociato(this.user, row.progrEccezBanLinDocPag.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Modalità pagamento bando linea corrente eliminata con successo");
          this.caricaDatiTabellaModPagBandoLineaCor();
        } else {
          this.showMessageError("Errore in fase di eliminazione della modalita pagamento bando linea corrente.");
        }
      }
      this.loadedEliminaDocPagamAssociato = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione della modalita pagamento bando linea corrente.");
      this.loadedEliminaDocPagamAssociato = false;
    });
  }

  eliminaModPagTuttiBandoLinea(row: DocPagamBandoLineaDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaDocPagamAssociatoTuttiBL = true;
    this.subscribers.eliminaDocPagamAssociatoTuttiBL = this.docModPagService.eliminaDocPagamAssociatoTuttiBL(this.user, row.idTipoDocumento.toString(), row.idModalitaPagamento.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Modalità di pagamento tutti i bando linea eliminata con successo.");
          this.caricaDatiTabellaModPagTuttiBandoLinea();
        } else {
          this.showMessageError("Errore in fase di eliminazione della modalità di pagamento tutti i bando linea.");
        }
      }
      this.loadedEliminaDocPagamAssociatoTuttiBL = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione della modalità di pagamento tutti i bando linea.");
      this.loadedEliminaDocPagamAssociatoTuttiBL = false;
    });
  }
  /* ----------------- Fine tab doc / mod pagam --------------------------- */

  /* ----------------- Inizio tab checklist --------------------------- */
  associaChecklist() {
    this.resetMessageError();
    this.resetMessageSuccess();
    var idTipoDocumentoIndexArray = new Array<number>();
    var idModelloArray = new Array<number>();
    this.selection.selected.forEach(element => {
      idTipoDocumentoIndexArray.push(element.idTipoDocumentoIndex);
      idModelloArray.push(element.idModello);
    });

    var request = new CheclistDTO(idTipoDocumentoIndexArray, idModelloArray, this.user.idUtente, Number(this.idBandoLinea));

    this.loadedAssociaChecklist = true;
    this.subscribers.associaChecklist = this.checkListService.associaChecklist(request).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Checklist associata con successo.");
          this.selection.clear();
          this.caricaDatiTabellaCheckListAssociate();
        } else {
          this.showMessageError("Errore in fase di associazione della checklist.");
        }
      }
      this.loadedAssociaChecklist = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di associazione della checklist.");
      this.loadedAssociaChecklist = false;
    });
  }

  caricaDatiTabellaModelliChecklist() {
    this.loadedChecklistAssociabili = true;
    this.subscribers.checklistAssociabili = this.checkListService.checklistAssociabili(this.user).subscribe(data => {
      this.dataSourceModelliChecklist = new MatTableDataSource(data);
      this.paginatorModelliCheckList.length = data.length;
      this.paginatorModelliCheckList.pageIndex = 0;
      this.dataSourceModelliChecklist.paginator = this.paginatorModelliCheckList;
      if (this.dataSourceModelliChecklist.filteredData.length == 0) {
        this.showModelliCheckListTable = false;
      } else {
        this.showModelliCheckListTable = true;
      }
      this.loadedChecklistAssociabili = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei modelli checklist associabili");
      this.loadedChecklistAssociabili = false;
    });
  }

  caricaDatiTabellaCheckListAssociate() {
    this.loadedChecklistAssociate = true;
    this.subscribers.checklistAssociate = this.checkListService.checklistAssociate(this.user, this.idBandoLinea).subscribe(data => {
      this.dataSourceCheckListAssociate = new MatTableDataSource(data);
      this.paginatorCheckListAssociate.length = data.length;
      this.paginatorCheckListAssociate.pageIndex = 0;
      this.dataSourceCheckListAssociate.paginator = this.paginatorCheckListAssociate;
      if (this.dataSourceCheckListAssociate.filteredData.length == 0) {
        this.showCheckListAssociateTable = false;
      } else {
        this.showCheckListAssociateTable = true;
      }
      this.loadedChecklistAssociate = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle checklist associate");
      this.loadedChecklistAssociate = false;
    });
  }

  checkboxLabel(row?: CheckListBandoLineaDTO): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.idModello + 1}`;
  }

  eliminaChecklist(row: CheckListBandoLineaDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaChecklist = true;
    this.subscribers.eliminaChecklist = this.checkListService.eliminaChecklist(this.user, this.idBandoLinea, row.idTipoDocumentoIndex.toString()).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Checklist eliminata con successo.");
          this.caricaDatiTabellaCheckListAssociate();
        } else {
          this.showMessageError("Errore in fase di eliminazione della checklist.");
        }
      }
      this.loadedEliminaChecklist = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione della checklist.");
      this.loadedEliminaChecklist = false;
    });
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    var numRows;
    if (this.dataSourceModelliChecklist == undefined) {
      numRows = 0;
    } else {
      numRows = this.dataSourceModelliChecklist.data.length;
    }

    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSourceModelliChecklist.data.forEach(row => this.selection.select(row));
  }
  /* ----------------- Fine tab checklist --------------------------- */

  /* ----------------- Inizio tab costanti --------------------------- */
  getDatiCostanti() {
    this.loadedCostantiBandoLinea = true;
    this.subscribers.costantiBandoLinea = this.costantiService.costantiBandoLinea(this.user, this.idBandoLinea).subscribe(data => {

      this.codiceNormativa = data.codiceNormativa;
      this.normaIncentivazione = data.normaIncentivazione;
      this.statoInvioDomanda = data.statoInvioDomanda;
      this.statoProgetto = data.statoProgetto;
      this.statoContoEconomico = data.statoContoEconomico;

      this.statiDomanda = new Array<Decodifica>();
      this.loadedStatoDomanda = true;
      this.subscribers.statoDomanda = this.costantiService.statoDomanda(this.user).subscribe(dataStatoDomanda => {
        this.statiDomanda = dataStatoDomanda;
        this.statoDomandaSelected = this.statiDomanda.find(x => x.id == data.idStatoDomanda);
        this.loadedStatoDomanda = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento degli stato domanda");
        this.loadedStatoDomanda = false;
      });

      this.tipiOperazione = new Array<Decodifica>();
      this.loadedTipoOperazione = true;
      this.subscribers.tipoOperazione = this.costantiService.tipoOperazione(this.user).subscribe(dataTipoOperazione => {
        this.tipiOperazione = dataTipoOperazione;
        this.tipoOperazioneSelected = this.tipiOperazione.find(x => x.id == data.idTipoOperazione);
        this.loadedTipoOperazione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei tipi operazione");
        this.loadedTipoOperazione = false;
      });
      this.loadedCostantiBandoLinea = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento dei dati costanti");
      this.loadedCostantiBandoLinea = false;
    });
  }

  salvaCostanti() {
    this.resetMessageError();
    this.resetMessageSuccess();
    var request = new CostantiBandoLineaDTO(this.normaIncentivazione, this.codiceNormativa, this.tipoOperazioneSelected.id, this.statoDomandaSelected.id, Number(this.idBandoLinea), this.statoInvioDomanda, this.statoProgetto, this.statoContoEconomico);
    this.loadedSalvaCostanti = true;
    this.subscribers.salvaCostanti = this.costantiService.salvaCostanti(this.user, request).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Salvataggio delle costanti avvenuto con successo.");
        } else {
          this.showMessageError("Errore in fase di salvataggio delle costanti.");
        }
      }
      this.loadedSalvaCostanti = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio delle costanti.");
      this.loadedSalvaCostanti = false;
    });
  }
  /* ----------------- Fine tab costanti --------------------------- */




  /* ----------------- Inizio tab estremi bancari --------------------------- */


  modAgevEstrBancList: Array<ModAgevEstremiBancariDTO>;
  loadedGetEstremiBancari = false;
  loadedsendToAmmCont = false;
  loadedDeleteIban = false;
  loadedDeleteBanca = false;
  loadedInsertEstremiBancari = false;
  bancheList: Array<GenericResponse>






  getModalitaAgevolazioneList() {

    this.loadedGetEstremiBancari = true

    this.configurazioneBandoLineaService.getEstremiBancari(this.idBando).subscribe(

      // next
      data => {
        this.modAgevEstrBancList = data
        console.log(this.modAgevEstrBancList)
      },
      error => {
        this.resetMessageError();
        this.resetMessageSuccess();
        let err = this.handleExceptionService.handleNotBlockingError(error);
        this.showMessageError(err.message);
        console.log("errore nella chiamata")
      },//error
      () => { this.loadedGetEstremiBancari = false } // complete
    )


  }


  modAgevEstrBancListNew: Array<ModAgevEstremiBancariDTO> = []

  aggiungiBanca(modAgevEstrBanc: ModAgevEstremiBancariDTO) {

    console.log("modAgevEstrBanc", modAgevEstrBanc)

    let newElem = new EstremiBancariDTO
    modAgevEstrBanc.estremiBancariList.push(newElem);

    console.log("aggiungo una banca")
  }



  aggiungiEtremoBancario(modAgevEstrBanc: ModAgevEstremiBancariDTO, estremoBancario: EstremiBancariDTO) {

    console.log("aggiungo una iban")

    this.resetMessageError();
    this.resetMessageSuccess();
    const dialogRef = this.dialog.open(NuovoEstremoBancarioDialog, {
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!(result == undefined)) {
        var iban: string = result.data[0];
        var tipologiaConto: string = result.data[1];
        var moltiplicatore: number = result.data[2];

        this.insertEstremiBancari(modAgevEstrBanc, estremoBancario, iban, tipologiaConto, moltiplicatore);
        //this.progressivoAreaScientifica = this.progressivoAreaScientifica - 1;
        //this.areeScientifiche.push(new IdDescBreveDescEstesaVo(this.progressivoAreaScientifica, codice, areaScientifica));

        console.log(iban, tipologiaConto, moltiplicatore)
      }
    })
  }


  insertEstremiBancari(modAgevEstrBanc: ModAgevEstremiBancariDTO, estremoBancario: EstremiBancariDTO, iban: string, tipologiaConto: string, moltiplicatore: number) {

    let insertEstremiBancariDTO = new InsertEstremiBancariDTO()
    insertEstremiBancariDTO.idBanca = estremoBancario.idBanca
    insertEstremiBancariDTO.iban = iban
    insertEstremiBancariDTO.idModalitaAgevolazione = modAgevEstrBanc.modalitaAgevolazione.idModalitaAgevolazione
    insertEstremiBancariDTO.idBando = this.idBando

    insertEstremiBancariDTO.moltiplicatore = moltiplicatore == undefined ? undefined : moltiplicatore.toString()
    insertEstremiBancariDTO.tipologiaConto = tipologiaConto

    this.resetMessageError();
    this.resetMessageSuccess();

    this.loadedInsertEstremiBancari = true
    this.configurazioneBandoLineaService.insertEstremiBancari(insertEstremiBancariDTO).subscribe(

      // next
      data => {
        //this.modAgevEstrBancList= data
        console.log(data)
        let rcm: ResponseCodeMessage = data
        this.showMessageSuccess(rcm.message);
        this.getModalitaAgevolazioneList();
      },

      error => {
        let err = this.handleExceptionService.handleNotBlockingError(error);
        this.showMessageError(err.message);
        this.loadedInsertEstremiBancari = false
      },

      () => { this.loadedInsertEstremiBancari = false } // complete
    )

  }



  sendToAmmCont() {

    this.resetMessageError();
    this.resetMessageSuccess();

    this.loadedsendToAmmCont = true
    this.configurazioneBandoLineaService.sendToAmmCont(this.user.idUtente, this.idBando).subscribe(

      // next
      data => {
        //this.modAgevEstrBancList= data
        console.log(data)
        let rcm: ResponseCodeMessage = data
        this.showMessageSuccess(rcm.message);
      },
      error => {
        console.log("errore nella chiamata")
        let err = this.handleExceptionService.handleNotBlockingError(error);
        this.showMessageError(err.message);
        this.loadedsendToAmmCont = false
      },//error
      () => { this.loadedsendToAmmCont = false } // complete
    )

  }

  //////////////////////////// combobox autocomplete //////////////////////////////////

  bancheSuggestList: Array<BancaSuggestVO>
  filteredOptionsBanche$: Observable<BancaSuggestVO[]>;
  bancheGroup: FormGroup = new FormGroup({ bankControl: new FormControl() });
  bankSelected: FormControl = new FormControl();
  ibBanckSelected: number

  getBancheByDesc(descrizione: string) {
    if (descrizione?.length >= 3) {
      this.bancheSuggestList = [new BancaSuggestVO(null, "Caricamento...")];
      this.configurazioneBandoLineaService.getBancheByDesc(descrizione).subscribe(data => {
        console.log(data);
        this.bancheSuggestList = data;
        this.filteredOptionsBanche$ = this.bancheGroup.controls['bankControl'].valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descBanca),
            map(descBanca => descBanca ? this._filterBank(descBanca) : this.bancheSuggestList?.slice())
          );


      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei progetti.");
      });
    }
  }

  private _filterBank(desc: string): BancaSuggestVO[] {
    const filterValue = desc.toLowerCase();
    return this.bancheSuggestList.filter(option => option.descBanca.toLowerCase().includes(filterValue));
  }

  displayFnBank(element: BancaSuggestVO): string {
    return element && element.descBanca ? element.descBanca : '';
  }

  onClickSelectBank(event: any, estremoBancario: EstremiBancariDTO) {

    console.log(event);

    this.bankSelected.setValue(event.option.value);
    //this.ibBanckSelected = this.bankSelected.value.idBanca
    let bnkSelect: BancaSuggestVO = this.bankSelected.value
    console.log(bnkSelect);


    estremoBancario.idBanca = bnkSelect.idBanca
    estremoBancario.descBanca = bnkSelect.descBanca
    estremoBancario.isSendToAmministrativoContabile = false

  }

  //////////////////////////// combobox autocomplete fine //////////////////////////////////


  removeAssociazioneIban(modAgevEstrBanc: ModAgevEstremiBancariDTO, estremoBancario: EstremiBancariDTO, estremo: EstremiContoDTO) {

    this.loadedDeleteIban = true
    this.configurazioneBandoLineaService.removeAssociazioneIban(
      this.idBando,
      modAgevEstrBanc.modalitaAgevolazione.idModalitaAgevolazione,
      estremoBancario.idBanca,
      estremo
    ).subscribe(

      // next
      data => {
        //this.modAgevEstrBancList= data
        console.log(data)
        this.getModalitaAgevolazioneList();
      },
      error => {
        console.log("errore nella chiamata")
        this.loadedDeleteIban = false
      },//error
      () => { this.loadedDeleteIban = false } // complete
    )


    //estremiList.
  }

  emoveAssociazioneBanca(modAgevEstrBanc: ModAgevEstremiBancariDTO, estremoBancario: EstremiBancariDTO) {

    this.loadedDeleteBanca = true
    this.configurazioneBandoLineaService.removeAssociazioneBanca(
      this.idBando,
      modAgevEstrBanc.modalitaAgevolazione.idModalitaAgevolazione,
      estremoBancario.idBanca,
    ).subscribe(

      // next
      data => {
        //this.modAgevEstrBancList= data
        console.log(data)
      },
      error => {
        console.log("errore nella chiamata")
        this.loadedDeleteBanca = false
      },//error
      () => { this.loadedDeleteBanca = false } // complete
    )


    //estremiList.
  }


  /* ----------------- Fine tab estremi bancari --------------------------- */


  /* ----------------- Inizio tab monitoraggio tempi --------------------------- */


  loadedGetParametriMonitoraggioTempi = false;
  loadedAssociaParametroMonitoraggio = false;
  loadedDisassociaParametroMonitoraggio = false;

  parametriMonitoraggioTempiSelected: Decodifica;
  paramMonitTempNumGiorni: number

  parametriMonitoraggioTempiAssociatiList: Array<ParametriMonitoraggioTempi>
  dataSourceParmMonitTemp: MatTableDataSource<ParametriMonitoraggioTempi>
  displayedColumnsParamMonitTemp: string[] = ['paramMonitTemp', 'numGiorni', 'azioni'];


  parametriMonitoraggioTempiList: Array<GenericResponse>
  parmMonitTempControl = new FormControl('');
  filteredOptions: Observable<Array<GenericResponse>>;



  getParametriMonitoraggioTempiAssociati() {



    this.loadedGetParametriMonitoraggioTempi = true
    this.configurazioneBandoLineaService.getParametriMonitoraggioTempiAssociati(this.idBandoLinea).subscribe(

      // next
      data => {
        this.parametriMonitoraggioTempiAssociatiList = data
        //let parametriMonitoraggioTempiAssociatiListOption = ContainerWithOptions.createList(this.parametriMonitoraggioTempiAssociatiList)
        this.dataSourceParmMonitTemp = new MatTableDataSource(data);

        console.log(data)
      },
      error => {
        this.resetMessageError();
        this.resetMessageSuccess();
        let err = this.handleExceptionService.handleNotBlockingError(error);
        this.showMessageError(err.message);
        this.loadedGetParametriMonitoraggioTempi = false
      },//error
      () => { this.loadedGetParametriMonitoraggioTempi = false } // complete
    )


  }

  getParametriMonitoraggioTempi() {


    this.loadedGetParametriMonitoraggioTempi = true
    this.configurazioneBandoLineaService.getParametriMonitoraggioTempi().subscribe(

      // next
      data => {
        this.parametriMonitoraggioTempiList = data

        this.filteredOptions = this.parmMonitTempControl.valueChanges.pipe(
          startWith(''),
          map(value => this._filter(value || '')),
        );

        console.log(this.filteredOptions)

        console.log(data)
      },
      error => {
        this.resetMessageError();
        this.resetMessageSuccess();
        let err = this.handleExceptionService.handleNotBlockingError(error);
        this.showMessageError(err.message);
        this.loadedGetParametriMonitoraggioTempi = false
      },//error
      () => { this.loadedGetParametriMonitoraggioTempi = false } // complete
    )


  }

  private _filter(value: string): Array<GenericResponse> {
    const filterValue = value.toLowerCase();

    let r: Array<GenericResponse> = this.parametriMonitoraggioTempiList.filter(option => option.descrizione.toLowerCase().includes(filterValue));

    return r
  }

  onSelectParmMonit() {
    //his.parametriMonitoraggioTempiSelected = value;
    //console.log(this.parmMonitTempControl.value)
    //console.log(this.parametriMonitoraggioTempiSelected)
  }

  associaParametroMonitoraggio() {

    this.resetMessageError();
    this.resetMessageSuccess();

    this.loadedAssociaParametroMonitoraggio = true

    let newParamMonitTemp = new ParametriMonitoraggioTempi()
    newParamMonitTemp.idParametroMonit = this.parametriMonitoraggioTempiSelected.id
    newParamMonitTemp.numGiorni = this.paramMonitTempNumGiorni
    newParamMonitTemp.progrBandoLineaIntervento = +this.idBandoLinea
    this.configurazioneBandoLineaService.insertParametriMonitoraggioTempiAssociati(newParamMonitTemp).subscribe(

      // next
      data => {

        console.log(data)
        let rcm: ResponseCodeMessage = data
        this.showMessageSuccess(rcm.message);
        this.getParametriMonitoraggioTempiAssociati();
      },
      error => {
        let err = this.handleExceptionService.handleNotBlockingError(error);
        this.showMessageError(err.message);
        this.loadedAssociaParametroMonitoraggio = false
      },//error
      () => { this.loadedAssociaParametroMonitoraggio = false } // complete
    )

  }

  disassociaParametroMonitoraggio(row: ParametriMonitoraggioTempi) {

    this.resetMessageError();
    this.resetMessageSuccess();
    console.log("disassociaParametroMonitoraggio")

    this.loadedDisassociaParametroMonitoraggio = true
    this.configurazioneBandoLineaService.deleteParametriMonitoraggioTempiAssociati(row.idParamMonitBandoLinea).subscribe(
      data => {
        console.log(data)
        let rcm: ResponseCodeMessage = data
        this.showMessageSuccess(rcm.message);
        this.getParametriMonitoraggioTempiAssociati();
      },
      error => {
        console.log("errore nella chiamata")
        let err = this.handleExceptionService.handleNotBlockingError(error);
        this.showMessageError(err.message);
        this.loadedDisassociaParametroMonitoraggio = false
      },//error
      () => { this.loadedDisassociaParametroMonitoraggio = false } // complete
    );

  }

  modificaParametroMonitoraggio(row: ParametriMonitoraggioTempi) {

    console.log("aggiungo una iban")

    this.resetMessageError();
    this.resetMessageSuccess();
    const dialogRef = this.dialog.open(ModificaParametroMonitoraggioDialog, {
      data: {
        descrizione: row.descParametroMonit,
        numGironi: row.numGiorni
      },
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!(result == undefined)) {
        var newNumGironi = result.data.numGironi
        let elem: ParametriMonitoraggioTempi = ParametriMonitoraggioTempi.copy(row)
        elem.numGiorni = newNumGironi
        this.updateParametroMonitoraggio(elem);
      }
    })

  }

  updateParametroMonitoraggio(row: ParametriMonitoraggioTempi) {

    this.resetMessageError();
    this.resetMessageSuccess();

    console.log(row)

    this.loadedDisassociaParametroMonitoraggio = true
    this.configurazioneBandoLineaService.updateParametriMonitoraggioTempiAssociati(row).subscribe(
      data => {
        console.log(data)
        this.getParametriMonitoraggioTempiAssociati();
      },
      error => {
        console.log("errore nella chiamata")
        let err = this.handleExceptionService.handleNotBlockingError(error);
        this.showMessageError(err.message);
        this.loadedDisassociaParametroMonitoraggio = false
      },//error
      () => { this.loadedDisassociaParametroMonitoraggio = false } // complete
    );


  }


  mailCheck() {
    this.mail = this.mail.replace(" ", "");

    if (this.mail == undefined || this.mail == null)
      this.mailIsValid = false;

    else {

      if (this.mail == "") {
        this.mail = undefined;
        this.mailIsValid = false;
      }
      else {

        let pattern1 = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        if (pattern1.test(this.mail))
          this.mailIsValid = true;
        else
          this.mailIsValid = false;

      }
    }


  }


  displayFn(element: Decodifica): string {
    return element && element.descrizione ? element.descrizione : '';
  }






  /* ----------------- Inizio generale --------------------------- */
  tornaBandi() {
    this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_CONFIGURAZIONE_BANDO}/configurazioneBando`]);
  }

  isLoading() {
    if (this.loadedObiettivoTematico || this.loadedAssociaEnteCompetenza || this.loadedEliminaEnteCompetenzaAssociato
      || this.loadedEntiCompetenzaAssociati || this.loadedRuoli || this.loadedEnte || this.loadedSettore || this.loadedAssociaTipiAiuto
      || this.loadedEliminaTipiAiuto || this.loadedFindTipiAiutoAssociati || this.loadedAssociaTemaPrioritario
      || this.loadedEliminaTemaPrioritarioAssociato || this.loadedTemaPrioritarioAssociato || this.loadedAssociaIndicatoreQSN
      || this.loadedEliminaIndicatoreQSNAssociato || this.loadedIndicatoriQSNAssociati || this.loadedAssociaIndicatoreRisultatoProgramma
      || this.loadedEliminaIndicatoreRisultatoProgramma || this.loadedIndicatoriRisultatoProgrammaAssociati || this.loadedAssociaTipoPeriodo
      || this.loadedEliminaTipoPeriodo || this.loadedTipoPeriodoAssociato || this.loadedAssociaAreaScientifica || this.loadedEliminaAreaScientificaAssociata
      || this.loadedAreeScientificheAssociate || this.loadedTipiAiutoDaAssociare || this.loadedTemaPrioritario || this.loadedIndicatoriQSN
      || this.loadedIndicatoriRisultatoProgramma || this.loadedTipoPeriodo || this.loadedAreaScientifica || this.loadedAssociaRegola || this.loadedEliminaRegola
      || this.loadedRegoleAssociate || this.loadedCercaRegole || this.loadedInserisciModello || this.loadedGetNomeDocumento || this.loadedDownloadDocumento
      || this.loadedGeneraDocumento || this.loadedEliminaModelloAssociato || this.loadedFindModelliAssociati || this.loadedFindBandoLinea
      || this.loadedFindModelliDaAssociare || this.loadedAssociaDocPagamTuttiBL || this.loadedAssociaDocPagamento || this.loadedEliminaDocPagamAssociato
      || this.loadedDocPagamentiAssociati || this.loadedEliminaDocPagamAssociatoTuttiBL || this.loadedDocPagamentiAssociatiTuttiBL || this.loadedTipiDocumento
      || this.loadedModalitaPagamento || this.loadedChecklistAssociabili || this.loadedAssociaChecklist || this.loadedChecklistAssociate
      || this.loadedEliminaChecklist || this.loadedSalvaCostanti || this.loadedCostantiBandoLinea || this.loadedStatoDomanda || this.loadedTipoOperazione
      || this.loadedIsruoliobbligatoriassociati || !this.loadedProcesso
      || this.loadedGetEstremiBancari || this.loadedsendToAmmCont || this.loadedDeleteIban || this.loadedDeleteBanca || this.loadedInsertEstremiBancari
      || this.loadedGetParametriMonitoraggioTempi || this.loadedAssociaParametroMonitoraggio || this.loadedDisassociaParametroMonitoraggio
      || this.loadedFlagFinpemonte || this.loadedFlagAssocizioneFinpemonteRuolo) {

      return true;
    } else {
      return false;
    }
  }

  tabClick(tab) {
    this.resetMessageError();
    if (this.currentTab == "ENTE DI COMPETENZA") {
      this.loadedIsruoliobbligatoriassociati = true;
      this.subscribers.isruoliobbligatoriassociati = this.enteCompetenzaService.isruoliobbligatoriassociati(this.idBandoLinea, this.dataSourceEnteCompetenza.data).subscribe(data => {
        if (data.code == "OK") {
          this.currentTab = tab.tab.textLabel;
          switch (tab.tab.textLabel) {
            case "ENTE DI COMPETENZA":
              this.caricaDatiTabellaEnteCompetenza();
              this.caricaDropEnteCompetenza();
              break;
            case "DATI AGGIUNTIVI":
              this.caricaDatiTabellaTipoAiuto();
              this.caricaDatiTabellaTemaPrioritario();
              this.caricaDatiTabellaIndicatoreQSN();
              this.caricaDatiTabellaRisultatoProgramma();
              this.caricaDatiTabellaTipoPeriodo();
              this.caricaDatiTabellaAreaScientifica();
              this.caricaDropDatiAggiuntivi();
              this.progressivoAreaScientifica = 0;
              break;
            case "REGOLE":
              this.caricaDatiTabellaRegola();
              this.caricaDropRegole();
              break;
            case "MODELLI DI DOCUMENTO":
              this.caricaDatiTabellaModelliDocumentiAssociati();
              this.caricaDropModelliDocumento();
              break;
            case "DOCUMENTI / MODALITÀ DI PAGAMENTO":
              this.caricaDatiTabellaModPagBandoLineaCor();
              this.caricaDatiTabellaModPagTuttiBandoLinea();
              this.caricaDropDocModPag();
              break;
            case "CHECKLIST":
              this.caricaDatiTabellaModelliChecklist();
              this.caricaDatiTabellaCheckListAssociate();
              break;
            case "COSTANTI":
              this.getDatiCostanti();
              break;
          }
        } else {
          this.showMessageError("Per procedere è necessario inserire gli enti di competenza per i ruoli 'Destinatario delle comunicazioni', 'Programmatore del progetto' e 'Responsabile procedura attivazione'.");
          this.selectedTab = 0;
        }
        this.loadedIsruoliobbligatoriassociati = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di controllo obbligatorietà ruoli");
        this.loadedIsruoliobbligatoriassociati = false;
      });
    } else {
      this.currentTab = tab.tab.textLabel;
      switch (tab.tab.textLabel) {
        case "ENTE DI COMPETENZA":
          this.caricaDatiTabellaEnteCompetenza();
          this.caricaDropEnteCompetenza();
          break;
        case "DATI AGGIUNTIVI":
          this.caricaDatiTabellaTipoAiuto();
          this.caricaDatiTabellaTemaPrioritario();
          this.caricaDatiTabellaIndicatoreQSN();
          this.caricaDatiTabellaRisultatoProgramma();
          this.caricaDatiTabellaTipoPeriodo();
          this.caricaDatiTabellaAreaScientifica();
          this.caricaDropDatiAggiuntivi();
          this.progressivoAreaScientifica = 0;
          break;
        case "REGOLE":
          this.caricaDatiTabellaRegola();
          this.caricaDropRegole();
          break;
        case "MODELLI DI DOCUMENTO":
          this.caricaDatiTabellaModelliDocumentiAssociati();
          this.caricaDropModelliDocumento();
          break;
        case "DOCUMENTI / MODALITÀ DI PAGAMENTO":
          this.caricaDatiTabellaModPagBandoLineaCor();
          this.caricaDatiTabellaModPagTuttiBandoLinea();
          this.caricaDropDocModPag();
          break;
        case "CHECKLIST":
          this.caricaDatiTabellaModelliChecklist();
          this.caricaDatiTabellaCheckListAssociate();
          break;
        case "COSTANTI":
          this.getDatiCostanti();
          break;
      }
    }
  }

  ngAfterViewChecked() {
    if (this.tabs) {
      this.tabs.realignInkBar();
    }
  }



  bandoIsEnteCompetenzaFinpiemonte() {
    this.loadedFlagAssocizioneFinpemonteRuolo = true
    this.configurazioneBandoLineaService.bandoIsEnteCompetenzaFinpiemonte(this.idBandoLinea).subscribe(
      data => {
        console.log("flagFinpiemonte", data)
        this.flagAssociazioneDestinatarioFinpiemonte = data
        //this.flagAssociazioneDestinatarioFinpiemonte = true  //per arbiliatere in modo arbitrario la funzione COMMENTARE PRIMA DEL COMMIT

        this.loadedFlagFinpemonte = true
        this.userService.isCostanteFinpiemonte().subscribe(
          data => {
            console.log("flagFinpiemonte", data);
            this.flagFinpiemonte = data;
            if (this.flagFinpiemonte && this.flagAssociazioneDestinatarioFinpiemonte) {
              this.getParametriMonitoraggioTempiAssociati();
            }
          },
          error => {
            console.log("errore nella chiamata")
            let err = this.handleExceptionService.handleNotBlockingError(error);
            this.showMessageError(err.message);
            this.loadedFlagFinpemonte = false;
          },//error
          () => { this.loadedFlagFinpemonte = false } // complete
        );
      },
      error => {
        console.log("errore nella chiamata")
        let err = this.handleExceptionService.handleNotBlockingError(error);
        this.showMessageError(err.message);
        this.loadedFlagAssocizioneFinpemonteRuolo = false
      },//error
      () => { this.loadedFlagAssocizioneFinpemonteRuolo = false } // complete
    );
  }
  preImpostaFlagTempi() {
    if (this.ruoloSelected.id == 1 && this.enteSelected.idEnteCompetenza == 2) {
      this.flagMonitoraggioTempi = 1;
    } else {
      this.flagMonitoraggioTempi = 2;
    }

  }

  checkFinpiemonte() {

    this.subscribers.isCostFinp = this.userService.isCostanteFinpiemonte().subscribe(data => {
      if (data) {
        this.isCostanteFinpiemonte = data;
        this.subscribers.isBandoFinp = this.userService.isBandoCompetenzaFinpiemonte(+this.idBandoLinea).subscribe(data => {
          if (data) {
            this.isBandoFinpiemonte = data;
          }
        });
      }
    });
  }

  /* ----------------- Fine generale --------------------------- */
}
