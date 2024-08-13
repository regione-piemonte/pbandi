/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { LineaInterventoVo } from '../../commons/vo/linea-intevento-vo';
import { LineaInterventoService } from '../../services/linea-intervento.service';
import { ConfigurazioneBandoService } from 'src/app/configurazione-bando/services/configurazione-bando.service';
import { NormativaVo } from '../../commons/vo/normativa-vo';
import { IdDescBreveDescEstesaVo } from '../../commons/vo/id-desc-breve-desc-estesa-vo';
import { UserService } from '../../../core/services/user.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { DatiBandoService } from '../../services/dati-bando.service';
import { MatTableDataSource } from '@angular/material/table';
import { NuovaMicroVoceDialogComponent } from '../nuova-micro-voce-dialog/nuova-micro-voce-dialog.component';
import { NuovaMacroVoceDialogComponent } from '../nuova-macro-voce-dialog/nuova-macro-voce-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { MatRadioChange } from '@angular/material/radio';
import { BandoLineaAssociatoVo } from 'src/app/configurazione-bando/commons/vo/bando-linea-associato-vo';
import { LineaInterventoDaAssociareVo } from 'src/app/configurazione-bando/commons/vo/linea-intervento-da-associare-vo';
import { LineaInterventoDaModificareVo } from 'src/app/configurazione-bando/commons/vo/linea-intervento-da-modificare-vo';
import { DatiBandoVo } from 'src/app/configurazione-bando/commons/vo/dati-bando-vo';
import { PbandiMateriaVo } from '../../commons/vo/pbandi-materia-vo';
import { VoceDiSpesaAssociataDTO } from '../../commons/vo/voce-di-spesa-associata-vo';
import { VociSpesaService } from '../../services/voci-spesa.service';
import { IdDescBreveDescEstesa2DTO } from '../../commons/vo/id-desc-breve-desc-estesa2-vo';
import { DatiAggiuntiviService } from '../../services/dati-aggiuntivi.service';
import { TipoDiTrattamentoAssociatoDTO } from '../../commons/vo/tipo-trattamento-associato-vo';
import { SoggettoFinanziatoreAssociatoDTO } from '../../commons/vo/soggetto-finanziatore-associato-vo';
import { CausaleDiErogazioneAssociataDTO } from '../../commons/vo/causale-erogazione-associata-vo';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { DataModalitaAgevolazioneDTO } from '../../commons/vo/data-modalita-agevolazione-DTO';
import { NaturaCipeVO } from '../../commons/vo/natura-cipe-vo';
import { Constants } from 'src/app/core/commons/util/constants';
import { VisualizzaTestoDialogComponent } from 'src/app/shared/components/visualizza-testo-dialog/visualizza-testo-dialog.component';

@Component({
  selector: 'app-configura-bando',
  templateUrl: './configura-bando.component.html',
  styleUrls: ['./configura-bando.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ConfiguraBandoComponent implements OnInit, AfterViewChecked {

  // dichiarazione variabili
  flagFinpiemonte = false;
  flagAssociazioneDestinatarioFinpiemonte = false;

  normativaSelected: NormativaVo;
  normative: Array<NormativaVo>;
  normativaLinInSelected: NormativaVo;
  normativeLinInt: Array<NormativaVo>;
  processoSelected: Decodifica;
  processi: Array<Decodifica>;
  dataPubblicazioneBando: FormControl = new FormControl();
  dataScadenzaDomande: FormControl = new FormControl();
  dataMinimaRendicontazione: FormControl = new FormControl();
  dataDeterminaApprovBando: FormControl = new FormControl();
  numeroApprovBando: string;
  titoloBando: string;
  graduatoriaRadio: string = '2';
  BandoSIFRadio: string = '2';
  FondoFondiRadio: string = '2';
  flagArt58: string = 'S';
  dotazioneFinanziaria: string;
  materiaRiferimentoSelected: PbandiMateriaVo;
  materieRiferimento: Array<PbandiMateriaVo>;
  settoreCIPESelected: Decodifica;
  settoriCIPE: Array<Decodifica>;
  codIntIstitSelected: Decodifica;
  codsIntIstit: Array<Decodifica>;
  tipiOperazione: Array<Decodifica>;
  tipoOperazioneSelected: Decodifica;
  settoreCPTSelected: Decodifica;
  settoriCPT: Array<Decodifica>;
  prioritaQSNSelected: Decodifica;
  prioritasQSN: Array<Decodifica>;
  obiettivoGeneraleQSNSelected: Decodifica;
  obiettiviGeneraleQSN: Array<Decodifica>;
  obiettivoSpecificoQSNSelected: Decodifica;
  obiettiviSpecificoQSN: Array<Decodifica>;
  sottoSettoreCIPESelected: IdDescBreveDescEstesaVo;
  sottoSettoriCIPE: Array<IdDescBreveDescEstesaVo>;
  naturaCIPESelected: NaturaCipeVO;
  natureCIPE: Array<NaturaCipeVO>;
  tipologiaAttivazioneSelected: Decodifica;
  tipologieAttivazione: Array<Decodifica>;

  macroVociSpesa: Array<VoceDiSpesaAssociataDTO>;
  macroVoceSpesaSelected: FormControl = new FormControl();
  macroGroup: FormGroup = new FormGroup({ macroControl: new FormControl() });
  filteredOptionsMacro: Observable<VoceDiSpesaAssociataDTO[]>;

  microVoceSpesaSelected: VoceDiSpesaAssociataDTO;
  microVociSpesa: Array<VoceDiSpesaAssociataDTO>;
  modalitaAgevolazioneSelected: Decodifica;
  modalitaAgevoErogazioneSelected: Decodifica;
  modalitasAgevolazione: Array<Decodifica>;
  modalitasAgevolazioneErogazione: Array<Decodifica>;
  importoAgevolato: string;
  periodoStabilita: string;
  importoDaErogare: string;
  checkedModalitaLiquidazione: boolean = true;
  tipoTrattamentoSelected: Decodifica;
  tipiTrattamento: Array<Decodifica>;
  soggettoFinanziatoreSelected: IdDescBreveDescEstesa2DTO;
  soggettiFinanziatore: Array<IdDescBreveDescEstesa2DTO>;
  causaleErogazioneSelected: IdDescBreveDescEstesaVo;
  causaliErogazione: Array<IdDescBreveDescEstesaVo>;
  tipoIndicatoreSelected: Decodifica;
  tipiIndicatore: Array<Decodifica>;

  indicatori: Array<IdDescBreveDescEstesaVo>;
  indicatoreSelected: FormControl = new FormControl();
  indicatoriGroup: FormGroup = new FormGroup({ indicatoriControl: new FormControl() });
  filteredOptionsInd: Observable<IdDescBreveDescEstesaVo[]>;

  infoIniziale: string = "";
  infoFinale: string = "";

  percentuale: string;
  sogliaSpesaQuietanziata: string;
  erogazione: string;
  limite: string;
  asseSelected: LineaInterventoVo;
  assi: Array<LineaInterventoVo>;
  misuraSelected: LineaInterventoVo;
  misure: Array<LineaInterventoVo>;
  lineaSelected: LineaInterventoVo;
  linee: Array<LineaInterventoVo>;
  catCIPESelected: Decodifica;
  catsCIPE: Array<Decodifica>;
  tipoCIPESelected: Decodifica;
  tipiCIPE: Array<Decodifica>;

  obsTem: Array<Decodifica>;
  obTemSelected: FormControl = new FormControl();
  obTemGroup: FormGroup = new FormGroup({ obTemControl: new FormControl() });
  filteredOptionsObTem: Observable<Decodifica[]>;

  classesRA: Array<Decodifica>;
  classRASelected: FormControl = new FormControl();
  classRAGroup: FormGroup = new FormGroup({ classRAControl: new FormControl() });
  filteredOptionsClassRA: Observable<Decodifica[]>;

  bandoSIFSelected: BandoLineaAssociatoVo;
  bandiSIF: Array<BandoLineaAssociatoVo>;
  livIstSelected: Decodifica;
  livsIst: Array<Decodifica>;
  tipoLocSelected: Decodifica;
  tiposLoc: Array<Decodifica>;
  progComplessoSelected: Decodifica;
  progsComplesso: Array<Decodifica>;
  clasMETSelected: Decodifica;
  classesMET: Array<Decodifica>;
  titoloBandoLinea: string;
  durProgDatConc: number;
  codRNA: string;
  idOperazione: number;
  showTableVoceSpesa = true;
  showLivIst = true;
  showBandoSIF = true;
  showFondoFondi = false;
  showTableLineaAssociata = false;
  showFormModificaLineaIntervento = false;
  dataDatiBando: DatiBandoVo;
  dataModificaLineaIntervento: LineaInterventoDaModificareVo;

  progressivoMacroVoceSpesa: number;
  progressivoMicroVoceSpesa: number;

  processoDatiBando: number;
  processoLineeIntervento: number;

  user: UserInfoSec;

  isNuovoBando: boolean;
  isSiffino: boolean;

  params: URLSearchParams;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //dichiarazione variabili per spinner
  loadedIsSiffino = true;
  loadedObiettivoTematico = false;
  loadedElencoBandiSif = false;
  loadedProcessoInterno = false;
  loadedSoggettiFinanziatori = false;
  loadedCausaleErogazione = false;
  loadedTipoTrattamento = false;
  loadedMacroVoceSpesa = false;
  loadedGetNormative = false;
  loadedSettoreCipe = false;
  loadedMateriaRiferimento = false;
  loadedCodiceIntesaIstituzionale = false;
  loadedTipoOperazione = false;
  loadedSettoreCPT = false;
  loadedNaturaCipe = false;
  loadedModalitaAgevolazione = false;
  loadedTipoIndicatore = false;
  loadedGetNormativePost2016 = false;
  loadedAsse = false;
  loadedCategoriaCipe = false;
  loadedTipologiaCipe = false;
  loadedTipoLocalizzazione = false;
  loadedProgettoComplesso = false;
  loadedMet = false;
  loadedSottoSettoreCipe = false;
  loadedTipologiaAttivazione = false;
  loadedLivelloIstruzione = false;
  loadedMisura = false;
  loadedPrioritaQSN = false;
  loadedObiettivoGeneraleQSN = false;
  loadedLinea = false;
  loadedClassificazioneRA = false;
  loadedMicroVoceSpesa = false;
  loadedAssociaLineaIntervento = false;
  loadedEliminaLineaIntervento = false;
  loadedModificaLineaIntervento = false;
  loadedObiettivoSpecificoQSN = false;
  loadedGetLineeAssociate = false;
  loadedVociAssociate = false;
  loadedSalvaModifiche = false;
  loadedIndicatore = false;
  loadedModalitaAssociata = false;
  loadedTipoTrattamentoAssociato = false;
  loadedSoggettiFinanziatoreAssociato = false;
  loadedCausaleErogazioneAssociata = false;
  loadedIndicatoreAssociato = false;
  loadedAssociaModalitaAgevolazione = false;
  loadedEliminaModalitaAgevolazioneAssociata = false;
  loadedAssociaTipoTrattamento = false;
  loadedEliminaTipoTrattamentoAssociato = false;
  loadedTipoTrattamentoAssociatoPredefinito = false;
  loadedAssociaSoggettoFinanziatore = false;
  loadedEliminaSoggettoFinanziatore = false;
  loadedAssociaCausale = false;
  loadedEliminaCausaleErogazione = false;
  loadedAssociaIndicatore = false;
  loadedEliminaIndicatore = false;
  loadedEliminaVoceAssociata = false;
  loadedAssociaVoceAssociata = false;
  loadedSalvaDatiBando = false;
  loadedCercaDatiBando = false;

  hasValidationErrorLinInt: boolean;
  @ViewChild("lineaInterventoForm", { static: true }) lineaInterventoForm: NgForm;
  hasValidationErrorDatiBando: boolean;
  @ViewChild("datiBandoForm", { static: true }) datiBandoForm: NgForm;
  hasValidationErrorVociSpesa: boolean;
  @ViewChild("vociSpesaForm", { static: true }) vociSpesaForm: NgForm;

  // dichiarazioni variabili tabelle
  displayedColumnsModAgev: string[] = ['codice', 'modAgev', 'impAgev', 'perStab', 'impErog', 'daUsInLiq', 'azioni'];
  //displayedColumnsModAgev: string[] = ['codice', 'modAgev', 'impAgev', 'daUsInLiq', 'azioni'];
  //dataSourceModAgev: MatTableDataSource<IdDescBreveDescEstesaVo>;
  dataSourceModAgev: MatTableDataSource<DataModalitaAgevolazioneDTO>;

  displayedColumnsTipoTrat: string[] = ['tipoTratt', 'predefinito', 'star', 'delete'];
  dataSourceTipoTrat: MatTableDataSource<TipoDiTrattamentoAssociatoDTO>;

  displayedColumnsSogFin: string[] = ['soggFin', 'perc', 'delete'];
  dataSourceSogFin: MatTableDataSource<SoggettoFinanziatoreAssociatoDTO>;

  displayedColumnsCausErog: string[] = ['causErog', 'soglSpQuiet', 'erog', 'lim', 'delete'];
  dataSourceCausErog: MatTableDataSource<CausaleDiErogazioneAssociataDTO>;

  displayedColumnsIndic: string[] = ['cod', 'ind', 'infoIniziale', 'infoFinale', 'delete'];
  dataSourceIndic: MatTableDataSource<IdDescBreveDescEstesa2DTO>;

  displayedColumnsLinInt: string[] = ['linAss', 'bandoLinea', 'settings', 'edit', 'delete'];
  dataSourceLinInt: MatTableDataSource<BandoLineaAssociatoVo>;

  displayedColumnsVociSpesa: string[] = ['codice', 'macroVoce', 'microVoce', 'speseGestione', 'azioni'];
  dataSourceVociSpesa: MatTableDataSource<IdDescBreveDescEstesa2DTO>;

  @ViewChild('tabs', { static: false }) tabs;

  //OBJECT SUBSCRIBER
  subscribers: any = {};
  isCostanteFinpiemonte: boolean = true;
  isBandoFinpiemonte: boolean = true;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog,
    private datiBandoService: DatiBandoService,
    private configurazioneBandoService: ConfigurazioneBandoService,
    private lineaInterventoService: LineaInterventoService,
    private vociSpesaService: VociSpesaService,
    private datiAggiuntiviService: DatiAggiuntiviService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService
  ) {
    this.dataSourceVociSpesa = new MatTableDataSource();
    this.dataSourceLinInt = new MatTableDataSource();
  }

  ngOnInit(): void {

    /* Inizio popolamento dati di tutti i tab tramite chiamate a servizi */
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
          this.idOperazione = +params['id'];
        });

        this.loadData();
        this.checkFinpiemonte();
      }
    });
    /* Fine popolamento dati di tutti i tab tramite chiamate a servizi */
  }

  setIsNuovoBando() {
    // Check se modifica o nuovo bando
    if (this.params.get('isNuovoBando') == "true") {
      this.isNuovoBando = true;
    } else {
      this.isNuovoBando = false;
    }
    if (this.params.get('message')) {
      this.showMessageSuccess(this.params.get('message'));
    }
  }

  loadData() {
    // Recupero parametri da url
    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    this.setIsNuovoBando();

    this.progressivoMacroVoceSpesa = 0;
    this.progressivoMicroVoceSpesa = 0;

    this.showTableVoceSpesa = true;


    /* Inizio tab linea intervento */
    this.loadObiettiviTematici();

    this.processi = new Array<Decodifica>();
    this.loadedProcessoInterno = true;
    this.subscribers.procInt = this.lineaInterventoService.processoInterno(this.user).subscribe(data => {
      this.processi = data;
      this.loadedProcessoInterno = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei processo interno");
      this.loadedProcessoInterno = false;
    });
    /* Fine tab linea intervento */

    /* Inzio tab dati aggiuntivi */
    this.soggettiFinanziatore = new Array<IdDescBreveDescEstesa2DTO>();
    this.loadedSoggettiFinanziatori = true;
    this.subscribers.soggFin = this.datiAggiuntiviService.soggettiFinanziatori(this.user).subscribe(data => {
      this.soggettiFinanziatore = data;
      this.loadedSoggettiFinanziatori = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei soggetti finanziatori");
      this.loadedSoggettiFinanziatori = false;
    });

    this.causaliErogazione = new Array<IdDescBreveDescEstesaVo>();
    this.loadedCausaleErogazione = true;
    this.subscribers.causaleErog = this.datiAggiuntiviService.causaleErogazione(this.user).subscribe(data => {
      this.causaliErogazione = data;
      this.loadedCausaleErogazione = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei causale erogazione");
      this.loadedCausaleErogazione = false;
    });

    this.tipiTrattamento = new Array<Decodifica>();
    this.loadedTipoTrattamento = true;
    this.subscribers.tipoTratt = this.datiAggiuntiviService.tipoTrattamento(this.user).subscribe(data => {
      this.tipiTrattamento = data;
      this.loadedTipoTrattamento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei tipo trattamento");
      this.loadedTipoTrattamento = false;
    });
    /* Fine tab dati aggiuntivi */

    // Tab voci di spesa
    this.loadMacroVociDiSpesa();

    if (this.isNuovoBando) {
      this.loadDataNuovoBando();
    } else {
      this.loadDataModificaBando();
    }





    this.subscribers.modalitaAgevoErogazione = this.datiAggiuntiviService.modalitaAgevolErogazione().subscribe(data => {
      if (data) {
        this.modalitasAgevolazioneErogazione = data;
      }
    })
  }

  checkFinpiemonte() {

    this.subscribers.isCostFinp = this.userService.isCostanteFinpiemonte().subscribe(data => {
      if (data) {
        this.isCostanteFinpiemonte = data;
        if (this.isCostanteFinpiemonte) {
          this.displayedColumnsCausErog = ['causErog', 'soglSpQuiet', 'modAgev', 'erog', 'lim', 'delete'];
        };
      }
    });
  }

  loadDataNuovoBando() {
    // this.processoDatiBando = 2;
    this.processoLineeIntervento = 2;
    // Tab dati bando e linea intervento
    this.normative = new Array<NormativaVo>();
    this.normativeLinInt = new Array<NormativaVo>();
    this.loadedGetNormative = true;
    this.subscribers.normative = this.configurazioneBandoService.getNormative(this.user).subscribe(data => {
      this.normative = data;
      this.normativeLinInt = data;
      this.loadedGetNormative = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle normative");
      this.loadedGetNormative = false;
    });

    /* Inzio Tab dati bando */
    this.graduatoriaRadio = '2';

    this.settoriCIPE = new Array<Decodifica>();
    this.loadedSettoreCipe = true;
    this.subscribers.settcipe = this.datiBandoService.settoreCipe(this.user).subscribe(data => {
      this.settoriCIPE = data;
      this.loadedSettoreCipe = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei settore CIPE");
      this.loadedSettoreCipe = false;
    });

    this.materieRiferimento = new Array<PbandiMateriaVo>();
    this.loadedMateriaRiferimento = true;
    this.subscribers.materia = this.datiBandoService.materiaRiferimento(this.user).subscribe(data => {
      this.materieRiferimento = data;
      this.loadedMateriaRiferimento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento della materia di riferimento");
      this.loadedMateriaRiferimento = false;
    });

    this.codsIntIstit = new Array<Decodifica>();
    this.loadedCodiceIntesaIstituzionale = true;
    this.subscribers.codiceIntesa = this.datiBandoService.codiceIntesaIstituzionale(this.user).subscribe(data => {
      this.codsIntIstit = data;
      this.loadedCodiceIntesaIstituzionale = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del codice intesa istituzionale");
      this.loadedCodiceIntesaIstituzionale = false;
    });

    this.tipiOperazione = new Array<Decodifica>();
    this.loadedTipoOperazione = true;
    this.subscribers.tipoOp = this.datiBandoService.tipoOperazione(this.user).subscribe(data => {
      this.tipiOperazione = data;
      this.loadedTipoOperazione = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del tipo operazione");
      this.loadedTipoOperazione = false;
    });

    this.settoriCPT = new Array<Decodifica>();
    this.loadedSettoreCPT = true;
    this.subscribers.settCpt = this.datiBandoService.settoreCPT(this.user).subscribe(data => {
      this.settoriCPT = data;
      this.loadedSettoreCPT = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del settore CPT");
      this.loadedSettoreCPT = false;
    });

    this.natureCIPE = new Array<NaturaCipeVO>();
    this.loadedNaturaCipe = true;
    this.subscribers.natura = this.datiBandoService.naturaCipe(this.user).subscribe(data => {
      this.natureCIPE = data;
      this.loadedNaturaCipe = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei natura CIPE");
      this.loadedNaturaCipe = false;
    });
    /* Fine Tab dati bando */
  }

  loadMacroVociDiSpesa() {
    this.macroVociSpesa = new Array<VoceDiSpesaAssociataDTO>();
    this.macroGroup.get("macroControl")?.setValue(null);
    this.macroVoceSpesaSelected.setValue(null);
    this.loadedMacroVoceSpesa = true;
    this.subscribers.macro = this.vociSpesaService.macroVoceSpesa(this.user).subscribe(data => {
      this.macroVociSpesa = data;

      this.filteredOptionsMacro = this.macroGroup.controls['macroControl'].valueChanges
        .pipe(
          startWith(''),
          map(value => typeof value === 'string' || value == null ? value : value.descrizione),
          map(name => name ? this._filterMacro(name) : (this.macroVociSpesa ? this.macroVociSpesa.slice() : null))
        );

      this.loadedMacroVoceSpesa = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei macro voce di spesa");
      this.loadedMacroVoceSpesa = false;
    });
  }

  loadObiettiviTematici() {
    this.obsTem = new Array<Decodifica>();
    this.obTemGroup.get("obTemControl").setValue(null);
    this.obTemSelected = new FormControl();
    this.loadedObiettivoTematico = true;
    this.subscribers.obiett = this.lineaInterventoService.obiettivoTematico(this.user).subscribe(data => {
      this.obsTem = data;

      this.filteredOptionsObTem = this.obTemGroup.controls['obTemControl'].valueChanges
        .pipe(
          startWith(''),
          map(value => typeof value === 'string' || value == null ? value : value.descrizione),
          map(name => name ? this._filterObTem(name) : (this.obsTem ? this.obsTem.slice() : null))
        );


      this.loadedObiettivoTematico = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli obiettivo tematico");
      this.loadedObiettivoTematico = false;
    });
  }

  loadDataModificaBando() {
    if (this.params.get("idLineaDiIntervento") === "null") {
      this.processoDatiBando = 1;
      this.processoLineeIntervento = 1;
    }
    this.loadedCercaDatiBando = true;
    this.subscribers.cercaDati = this.datiBandoService.cercaDatiBando(this.user, this.params.get('titoloBando'), this.params.get('nomeBandoLinea'), null, (this.params.get('idLineaDiIntervento') == null) || (this.params.get('idLineaDiIntervento') == undefined) ? null : this.params.get('idLineaDiIntervento'), this.params.get('idBando')).subscribe(data => {
      this.dataDatiBando = data;

      // controllo prima se si tratta di un bandofinpiemonte: 
  

      this.loadedCercaDatiBando = false;

      // TAB DATI BANDO, LINEA INTERVENTO E DATI AGGIUNTIVI
      this.normative = new Array<NormativaVo>();
      if (this.processoLineeIntervento == 1) {
        this.loadedGetNormative = true;
        this.subscribers.norm = this.configurazioneBandoService.getNormative(this.user).subscribe(data => {
          this.loadedGetNormative = false;
          this.normative = data;
          this.normativeLinInt = data;
          this.normativaSelected = this.normative.find(x => x.idLinea == this.dataDatiBando.idLineaDiIntervento);
          this.normativaLinInSelected = this.normativeLinInt.find(x => x.idLinea == this.dataDatiBando.idLineaDiIntervento);

          this.loadBandiSif();
          /* Inizio tab dati aggiuntivi  */
          this.modalitasAgevolazione = new Array<Decodifica>();
          this.loadedModalitaAgevolazione = true;
          this.datiAggiuntiviService.modalitaAgevolazione(this.user, (this.normativaSelected == undefined) || (this.normativaSelected == null) ? "5" : this.normativaSelected.idLinea.toString()).subscribe(data => {
            this.modalitasAgevolazione = data;
            this.loadedModalitaAgevolazione = false;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di caricamento delle modalità agevolazione");
            this.loadedModalitaAgevolazione = false;
          });

          this.tipiIndicatore = new Array<Decodifica>();
          this.loadedTipoIndicatore = true;
          this.subscribers.tipoInd = this.datiAggiuntiviService.tipoIndicatore(this.user, this.normativaSelected == undefined ? "5" : this.normativaSelected.idLinea.toString()).subscribe(data => {
            this.tipiIndicatore = data;
            this.loadedTipoIndicatore = false;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di caricamento dei tipi indicatore");
            this.loadedTipoIndicatore = false;
          });
          /* Fine tab dati aggiuntivi  */
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento delle normative");
          this.loadedGetNormative = false;
        });
      } else {
        this.loadedGetNormativePost2016 = true;
        this.subscribers.norm2016 = this.configurazioneBandoService.getNormativePost2016(this.user).subscribe(data => {
          this.loadedGetNormativePost2016 = false;
          this.normative = data;
          this.normativeLinInt = data;
          this.normativaSelected = this.normative.find(x => x.idLinea == this.dataDatiBando.idLineaDiIntervento);
          this.normativaLinInSelected = this.normativeLinInt.find(x => x.idLinea == this.dataDatiBando.idLineaDiIntervento);

          this.loadBandiSif();

          /* Inizio tab dati aggiuntivi  */
          this.modalitasAgevolazione = new Array<Decodifica>();
          this.loadedModalitaAgevolazione = true;
          this.subscribers.modAgev = this.datiAggiuntiviService.modalitaAgevolazione(this.user, (this.normativaSelected == undefined) || (this.normativaSelected == null) ? "5" : this.normativaSelected.idLinea.toString()).subscribe(data => {
            this.modalitasAgevolazione = data;
            this.loadedModalitaAgevolazione = false;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di caricamento delle modalità agevolazione");
            this.loadedModalitaAgevolazione = false;
          });

          this.tipiIndicatore = new Array<Decodifica>();
          this.loadedTipoIndicatore = true;
          this.subscribers.tipoInd = this.datiAggiuntiviService.tipoIndicatore(this.user, this.normativaSelected == undefined ? "5" : this.normativaSelected.idLinea.toString()).subscribe(data => {
            this.tipiIndicatore = data;
            this.loadedTipoIndicatore = false;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di caricamento dei tipi indicatore");
            this.loadedTipoIndicatore = false;
          });
          /* Fine tab dati aggiuntivi  */
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento delle normative");
          this.loadedGetNormativePost2016 = false;
        });
      }

      /* Inizio tab linea intervento */
      this.assi = new Array<LineaInterventoVo>();
      if (!((this.processoLineeIntervento == 1) && !this.isNuovoBando)) {
        this.loadedAsse = true;
        this.subscribers.asse = this.lineaInterventoService.asse(this.user, this.dataDatiBando.idLineaDiIntervento == null ? null : this.dataDatiBando.idLineaDiIntervento.toString()).subscribe(data => {
          this.assi = data;
          this.loadedAsse = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento degli asse");
          this.loadedAsse = false;
        });
      }

      this.catsCIPE = new Array<Decodifica>();
      this.loadedCategoriaCipe = true;
      this.subscribers.categoria = this.lineaInterventoService.categoriaCipe(this.user, this.dataDatiBando.idSottoSettoreCIPE.toString()).subscribe(data => {
        this.catsCIPE = data;
        this.loadedCategoriaCipe = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento delle categorie CIPE");
        this.loadedCategoriaCipe = false;
      });

      this.tipiCIPE = new Array<Decodifica>();
      this.loadedTipologiaCipe = true;
      this.subscribers.tipoCipe = this.lineaInterventoService.tipologiaCipe(this.user, this.dataDatiBando.idNaturaCIPE.toString()).subscribe(data => {
        this.tipiCIPE = data;
        this.loadedTipologiaCipe = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei tipi CIPE");
        this.loadedTipologiaCipe = false;
      });

      this.tiposLoc = new Array<Decodifica>();
      this.loadedTipoLocalizzazione = true;
      this.subscribers.tipoLocal = this.lineaInterventoService.tipoLocalizzazione(this.user, this.dataDatiBando.idLineaDiIntervento == null ? null : this.dataDatiBando.idLineaDiIntervento.toString()).subscribe(data => {
        this.tiposLoc = data;
        this.loadedTipoLocalizzazione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei tipo localizzazione");
        this.loadedTipoLocalizzazione = false;
      });

      this.progsComplesso = new Array<Decodifica>();
      this.loadedProgettoComplesso = true;
      this.subscribers.progComplesso = this.lineaInterventoService.progettoComplesso(this.user, this.dataDatiBando.idLineaDiIntervento == null ? null : this.dataDatiBando.idLineaDiIntervento.toString()).subscribe(data => {

        this.progsComplesso = data;
        this.loadedProgettoComplesso = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei progetto complesso");
        this.loadedProgettoComplesso = false;
      });

      this.classesMET = new Array<Decodifica>();
      this.loadedMet = true;
      this.subscribers.met = this.lineaInterventoService.met(this.user, this.dataDatiBando.idLineaDiIntervento == null ? null : this.dataDatiBando.idLineaDiIntervento.toString()).subscribe(data => {
        console.log(data)
        this.classesMET = data
        console.log(this.classesMET)
        /*let arr :  Array<Decodifica> = data
        this.classesMET = new Array<Decodifica>();
        let elem: Decodifica;
        let i =0
        while (i< arr.length){
          elem = arr[i]
          this.classesMET.push(new Decodifica(elem.id, elem.descrizione, elem.descrizioneBreve, elem.dataInizioValidita, elem.dataFineValidita, elem.hasPeriodoDiStabilita))
          i++
        }*/

        console.log("classesMET :::::::::  ", this.classesMET),
          this.loadedMet = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei met");
        this.loadedMet = false;
      });
      /* Fine tab linea intervento */

      /* Inizio tab dati bando */
      this.settoriCIPE = new Array<Decodifica>();
      this.loadedSettoreCipe = true;
      this.subscribers.settCipe = this.datiBandoService.settoreCipe(this.user).subscribe(data => {
        this.settoriCIPE = data;
        this.settoreCIPESelected = this.settoriCIPE.find(x => x.id == this.dataDatiBando.idSettoreCIPE);
        this.loadedSettoreCipe = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei settore CIPE");
        this.loadedSettoreCipe = false;
      });

      this.materieRiferimento = new Array<PbandiMateriaVo>();
      this.loadedMateriaRiferimento = true;
      this.subscribers.mat = this.datiBandoService.materiaRiferimento(this.user).subscribe(data => {
        this.materieRiferimento = data;
        this.materiaRiferimentoSelected = this.materieRiferimento.find(x => x.idMateria == this.dataDatiBando.idMateriaRiferimento);
        this.loadedMateriaRiferimento = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento della materia di riferimento");
        this.loadedMateriaRiferimento = false;
      });

      this.codsIntIstit = new Array<Decodifica>();
      this.loadedCodiceIntesaIstituzionale = true;
      this.subscribers.codIntesa = this.datiBandoService.codiceIntesaIstituzionale(this.user).subscribe(data => {
        this.codsIntIstit = data;
        this.codIntIstitSelected = this.codsIntIstit.find(x => x.id == this.dataDatiBando.idCodiceIntesaIstituzionale);
        this.loadedCodiceIntesaIstituzionale = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento del codice intesa istituzionale");
        this.loadedCodiceIntesaIstituzionale = false;
      });

      this.tipiOperazione = new Array<Decodifica>();
      this.loadedTipoOperazione = true;
      this.subscribers.tipoOp = this.datiBandoService.tipoOperazione(this.user).subscribe(data => {
        this.tipiOperazione = data;
        this.tipoOperazioneSelected = this.tipiOperazione.find(x => x.id == this.dataDatiBando.idTipoOperazione);
        this.loadedTipoOperazione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento del tipo operazione");
        this.loadedTipoOperazione = false;
      });

      this.settoriCPT = new Array<Decodifica>();
      this.loadedSettoreCPT = true;
      this.subscribers.settCpt = this.datiBandoService.settoreCPT(this.user).subscribe(data => {
        this.settoriCPT = data;
        this.settoreCPTSelected = this.settoriCPT.find(x => x.id == this.dataDatiBando.idSettoreCPT);
        this.loadedSettoreCPT = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento del settore CPT");
        this.loadedSettoreCPT = false;
      });

      this.sottoSettoriCIPE = new Array<IdDescBreveDescEstesaVo>();
      this.loadedSottoSettoreCipe = true;
      this.subscribers.sottoCpt = this.datiBandoService.sottoSettoreCipe(this.user, this.dataDatiBando.idSettoreCIPE.toString()).subscribe(data => {
        this.sottoSettoriCIPE = data;
        this.sottoSettoreCIPESelected = this.sottoSettoriCIPE.find(x => x.id == this.dataDatiBando.idSottoSettoreCIPE);
        this.loadedSottoSettoreCipe = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei sotto settore CIPE");
        this.loadedSottoSettoreCipe = false;
      });

      this.tipologieAttivazione = new Array<Decodifica>();
      this.loadedTipologiaAttivazione = true;
      this.subscribers.tipoAtt = this.datiBandoService.tipologiaAttivazione(this.user, this.dataDatiBando.idLineaDiIntervento == null ? "5" : this.dataDatiBando.idLineaDiIntervento.toString()).subscribe(data => {
        this.tipologieAttivazione = data;
        this.tipologiaAttivazioneSelected = this.tipologieAttivazione.find(x => x.id == this.dataDatiBando.idTipologiaAttivazione);
        this.loadedTipologiaAttivazione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento delle tipologia attivazione");
        this.loadedTipologiaAttivazione = false;
      });

      this.natureCIPE = new Array<NaturaCipeVO>();
      this.loadedNaturaCipe = true;
      this.subscribers.natura = this.datiBandoService.naturaCipe(this.user).subscribe(data => {
        this.natureCIPE = data;
        this.naturaCIPESelected = this.natureCIPE.find(x => x.idNaturaCipe == this.dataDatiBando.idNaturaCIPE);
        this.loadedNaturaCipe = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei natura CIPE");
        this.loadedNaturaCipe = false;
      });

      this.titoloBando = this.dataDatiBando.titoloBando;
      this.dataDeterminaApprovBando.setValue(new Date(this.dataDatiBando.dataDeterminaApprovazione));
      this.numeroApprovBando = this.dataDatiBando.determinaApprovazione;
      this.dataPubblicazioneBando.setValue(this.dataDatiBando.dataPubblicazione ? new Date(this.dataDatiBando.dataPubblicazione) : null);
      this.dataMinimaRendicontazione.setValue(this.dataDatiBando.dataPresentazioneDomande ? new Date(this.dataDatiBando.dataPresentazioneDomande) : null);
      this.dataScadenzaDomande.setValue(this.dataDatiBando.dataScadenza ? new Date(this.dataDatiBando.dataScadenza) : null);
      this.dotazioneFinanziaria = this.dataDatiBando.dotazioneFinanziaria.toString();

      if (this.dataDatiBando.isPrevistaGraduatoria) {
        this.graduatoriaRadio = '1';
      } else {
        this.graduatoriaRadio = '2';
      }
      /* Fine tab dati bando */
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di restituzione dei dati bando");
      this.loadedCercaDatiBando = false;
    });

    // Tab linea intervento
    this.getLineeAssociateTableData();

    // Tab dati aggiuntivi
    this.getModAgevTableData();
    this.getTipoTratTableData();
    this.getCausErogTableData();
    this.getIndicatTableData();
    this.getSogFinTableData();
    this.getVociSpesaTableData();
  }

  private _filterMacro(descrizione: string): VoceDiSpesaAssociataDTO[] {
    const filterValue = descrizione.toLowerCase();
    return this.macroVociSpesa.filter(option => option.descVoceDiSpesa.toLowerCase().includes(filterValue) || option.codTipoVoceDiSpesa.toLowerCase().includes(filterValue));
  }

  private _filterIndicatori(descrizione: string): IdDescBreveDescEstesaVo[] {
    const filterValue = descrizione.toLowerCase();
    return this.indicatori.filter(option => option.descBreve.toLowerCase().includes(filterValue) || option.descEstesa.toLowerCase().includes(filterValue));
  }

  private _filterClassRA(descrizione: string): Decodifica[] {
    const filterValue = descrizione.toLowerCase();
    return this.classesRA.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  private _filterObTem(descrizione: string): Decodifica[] {
    const filterValue = descrizione.toLowerCase();
    return this.obsTem.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  check(type: string) {
    setTimeout(() => {
      if (type === 'M') {
        if (!this.macroVoceSpesaSelected || (this.macroGroup.controls['macroControl'] && this.macroVoceSpesaSelected.value !== this.macroGroup.controls['macroControl'].value)) {
          this.macroGroup.controls['macroControl'].setValue(null);
          this.macroVoceSpesaSelected = new FormControl();
        }
      } else if (type === 'I') {
        if (!this.indicatoreSelected || (this.indicatoriGroup.controls['indicatoriControl'] && this.indicatoreSelected.value !== this.indicatoriGroup.controls['indicatoriControl'].value)) {
          this.indicatoriGroup.controls['indicatoriControl'].setValue(null);
          this.indicatoreSelected = new FormControl();
        }
      } else if (type === 'C') {
        if (!this.classRASelected || (this.classRAGroup.controls['classeRAControl'] && this.classRASelected.value !== this.classRAGroup.controls['classeRAControl'].value)) {
          this.classRAGroup.controls['classeRAControl'].setValue(null);
          this.classRASelected = new FormControl();
        }
      } else if (type === 'O') {
        if (!this.obTemSelected || (this.obTemGroup.controls['obTemControl'] && this.obTemSelected.value !== this.obTemGroup.controls['obTemControl'].value)) {
          this.obTemGroup.controls['obTemControl'].setValue(null);
          this.obTemSelected = new FormControl();
        }
      }
    }, 200);
  }

  click(event: any, type: string) {
    if (type === 'M') {
      this.macroVoceSpesaSelected.setValue(event.option.value);
    } else if (type === 'I') {
      this.indicatoreSelected.setValue(event.option.value);
    } else if (type === 'C') {
      this.classRASelected.setValue(event.option.value);
    } else if (type === 'O') {
      this.obTemSelected.setValue(event.option.value);
      this.changeObTematico();
    }
  }

  displayFnMacro(element: VoceDiSpesaAssociataDTO): string {
    return element && element.descVoceDiSpesa && element.codTipoVoceDiSpesa ? (element.descVoceDiSpesa + " - " + element.codTipoVoceDiSpesa).trim() : '';
  }

  displayFnInd(element: IdDescBreveDescEstesaVo): string {
    return element && element.descBreve && element.descEstesa ? (element.descBreve + " - " + element.descEstesa).trim() : '';
  }

  displayFnDecodifica(element: Decodifica): string {
    return element && element.descrizione ? (element.descrizione).trim() : '';
  }

  /* ------------------- Inizio tab dati bando ------------------- */

  validazioneCampiDatiBando() {
    this.hasValidationErrorDatiBando = false;

    if (this.processoDatiBando == 1) {
      this.controlRequiredDatiBandoForm("codsIntIstit");
      this.controlRequiredDatiBandoForm("tipoOperazione");
      this.controlRequiredDatiBandoForm("settoreCPT");
    } else {
      if (!this.dataDeterminaApprovBando || !this.dataDeterminaApprovBando.value) {
        this.dataDeterminaApprovBando.setErrors({ error: 'required' });
        this.hasValidationErrorDatiBando = true;
      }
    }

    if (this.processoDatiBando == 1) {
      if (!(this.isNuovoBando == false)) {
        this.controlRequiredDatiBandoForm("normativa");
      }
    } else {
      this.controlRequiredDatiBandoForm("normativa");
    }

    this.controlRequiredDatiBandoForm("dotazioneFinanziaria");
    this.controlRequiredDatiBandoForm("settoreCIPE");
    this.controlRequiredDatiBandoForm("sottoSettoreCIPE");
    this.controlRequiredDatiBandoForm("naturaCIPE");
    this.controlRequiredDatiBandoForm("tipologiaAttivazione");
    this.controlRequiredDatiBandoForm("titoloBando");
    this.controlRequiredDatiBandoForm("materiaRiferimento");

    if (this.is2127) {
      if (!this.dataDeterminaApprovBando || !this.dataDeterminaApprovBando.value) {
        this.dataDeterminaApprovBando.setErrors({ error: 'required' });
        this.hasValidationErrorDatiBando = true;
      }
      if (!this.dataPubblicazioneBando || !this.dataPubblicazioneBando.value) {
        this.dataPubblicazioneBando.setErrors({ error: 'required' });
        this.hasValidationErrorDatiBando = true;
      }
      this.controlRequiredDatiBandoForm("numeroApprovBando");
    }

    if (this.hasValidationErrorDatiBando) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    }
  }

  controlRequiredDatiBandoForm(name: string) {
    if (!this.datiBandoForm.form.get(name) || !this.datiBandoForm.form.get(name).value) {
      this.datiBandoForm.form.get(name).setErrors({ error: 'required' });
      this.hasValidationErrorDatiBando = true;
    }
  }

  changeNormativa() {
    this.normativaLinInSelected = this.normativaSelected;
    this.resetMessageError();
    this.resetMessageSuccess();
    this.codIntIstitSelected = null;
    this.tipoOperazioneSelected = null;
    this.settoreCPTSelected = null;
    this.dataDeterminaApprovBando.setValue(null);
    this.numeroApprovBando = null;
    // azzerare valori tab linea intervento
    this.asseSelected = null;
    this.misuraSelected = null;
    this.lineaSelected = null;
    this.titoloBandoLinea = null;
    this.prioritaQSNSelected = null;
    this.obiettiviGeneraleQSN = null;
    this.obiettiviSpecificoQSN = null;
    this.catCIPESelected = null;
    this.tipoCIPESelected = null;
    this.obTemSelected = null;
    this.classRASelected = null;
    this.durProgDatConc = null;
    this.processoSelected = null;
    this.bandoSIFSelected = null;
    this.livIstSelected = null;
    this.tipoLocSelected = null;
    this.progComplessoSelected = null;
    this.clasMETSelected = null;
    this.codRNA = null;

    this.assi = new Array<LineaInterventoVo>();
    this.livsIst = new Array<Decodifica>();
    this.tiposLoc = new Array<Decodifica>();
    this.progsComplesso = new Array<Decodifica>();
    this.classesMET = new Array<Decodifica>();

    if (!(this.normativaSelected == null)) {
      this.loadedAsse = true;
      this.subscribers.asse = this.lineaInterventoService.asse(this.user, this.normativaSelected.idLinea.toString()).subscribe(data => {
        this.assi = data;
        this.loadedAsse = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento degli asse");
        this.loadedAsse = false;
      });

      this.loadedLivelloIstruzione = true;
      this.subscribers.liv = this.lineaInterventoService.livelloIstruzione(this.user, this.normativaSelected.idLinea.toString()).subscribe(data => {
        this.livsIst = data;
        this.loadedLivelloIstruzione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei livello istituzione");
        this.loadedLivelloIstruzione = false;
      });

      this.loadedTipoLocalizzazione = true;
      this.subscribers.tipoLocal = this.lineaInterventoService.tipoLocalizzazione(this.user, this.normativaSelected.idLinea.toString()).subscribe(data => {
        this.tiposLoc = data;
        this.loadedTipoLocalizzazione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei tipo localizzazione");
        this.loadedTipoLocalizzazione = false;
      });

      this.loadedProgettoComplesso = true;
      this.subscribers.progComplesso = this.lineaInterventoService.progettoComplesso(this.user, this.normativaSelected.idLinea.toString()).subscribe(data => {
        this.progsComplesso = data;
        this.loadedProgettoComplesso = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei progetto complesso");
        this.loadedProgettoComplesso = false;
      });

      this.loadedMet = true;
      this.subscribers.met = this.lineaInterventoService.met(this.user, this.normativaSelected.idLinea.toString()).subscribe(data => {
        this.classesMET = data;
        this.loadedMet = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei met");
        this.loadedMet = false;
      });

    }

    this.tipologiaAttivazioneSelected = undefined;
    this.modalitaAgevolazioneSelected = undefined;
    this.tipoIndicatoreSelected = undefined;
    if (this.normativaSelected == undefined) {
      this.tipologieAttivazione = new Array<Decodifica>();
    } else {
      this.tipologieAttivazione = new Array<Decodifica>();
      this.loadedTipologiaAttivazione = true;
      this.subscribers.tipoAtt = this.datiBandoService.tipologiaAttivazione(this.user, this.normativaSelected.idLinea.toString()).subscribe(data => {
        this.tipologieAttivazione = data;
        this.loadedTipologiaAttivazione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento delle tipologia attivazione");
        this.loadedTipologiaAttivazione = false;
      });

      this.modalitasAgevolazione = new Array<Decodifica>();
      this.loadedModalitaAgevolazione = true;
      this.subscribers.modAgev = this.datiAggiuntiviService.modalitaAgevolazione(this.user, this.normativaSelected.idLinea.toString()).subscribe(data => {
        this.modalitasAgevolazione = data;
        this.loadedModalitaAgevolazione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento delle modalità agevolazione");
        this.loadedModalitaAgevolazione = false;
      });

      this.tipiIndicatore = new Array<Decodifica>();
      this.loadedTipoIndicatore = true;
      this.subscribers.tipoInd = this.datiAggiuntiviService.tipoIndicatore(this.user, this.normativaSelected.idLinea.toString()).subscribe(data => {
        this.tipiIndicatore = data;
        this.loadedTipoIndicatore = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei tipi indicatore");
        this.loadedTipoIndicatore = false;
      });
    }
    this.loadBandiSif();
  }

  changeSettoreCIPE() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.sottoSettoreCIPESelected = undefined;
    if (this.settoreCIPESelected == undefined) {
      this.sottoSettoriCIPE = new Array<IdDescBreveDescEstesaVo>();
    } else {
      this.sottoSettoriCIPE = new Array<IdDescBreveDescEstesaVo>();
      this.loadedSottoSettoreCipe = true;
      this.subscribers.sottoCipe = this.datiBandoService.sottoSettoreCipe(this.user, this.settoreCIPESelected.id.toString()).subscribe(data => {
        this.sottoSettoriCIPE = data;
        this.loadedSottoSettoreCipe = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei sotto settore CIPE");
        this.loadedSottoSettoreCipe = false;
      });
    }
  }

  changeSottoSettoreCIPE() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.catsCIPE = new Array<Decodifica>();
    if (!(this.sottoSettoreCIPESelected == null)) {
      this.loadedCategoriaCipe = true;
      this.subscribers.categoria = this.lineaInterventoService.categoriaCipe(this.user, this.sottoSettoreCIPESelected.id.toString()).subscribe(data => {
        this.catsCIPE = data;
        this.loadedCategoriaCipe = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento delle categorie CIPE");
        this.loadedCategoriaCipe = false;
      });
    }
  }

  changeNaturaCIPE() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.tipiCIPE = new Array<Decodifica>();
    if (!(this.naturaCIPESelected == null)) {
      this.loadedTipologiaCipe = true;
      this.subscribers.tipoCipe = this.lineaInterventoService.tipologiaCipe(this.user,
        this.naturaCIPESelected.idNaturaCipe.toString()).subscribe(data => {
          this.tipiCIPE = data;
          this.loadedTipologiaCipe = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento dei tipi CIPE");
          this.loadedTipologiaCipe = false;
        });
    }
  }

  salvaDatiBando() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.validazioneCampiDatiBando();
    if (!this.hasValidationErrorDatiBando) {
      var request;
      if (this.processoDatiBando == 1) {
        if (this.isNuovoBando) {
          request = new DatiBandoVo(this.user.idUtente, this.user.idUtente, null, this.titoloBando,
            this.dataPubblicazioneBando?.value ? new Date(this.dataPubblicazioneBando.value) : null,
            this.dataMinimaRendicontazione?.value ? new Date(this.dataMinimaRendicontazione.value) : null,
            this.dataScadenzaDomande?.value ? new Date(this.dataScadenzaDomande.value) : null,
            this.graduatoriaRadio == "1" ? true : false, false, false,
            Number(this.dotazioneFinanziaria), this.materiaRiferimentoSelected.idMateria, null, null, null, null,
            this.codIntIstitSelected.id, this.tipoOperazioneSelected.id, this.settoreCIPESelected.id, this.sottoSettoreCIPESelected.id,
            this.naturaCIPESelected.idNaturaCipe,
            this.settoreCPTSelected.id, this.tipologiaAttivazioneSelected.id, null, null, null, null, null, null);
        } else {
          request = new DatiBandoVo(this.dataDatiBando.idUtenteIns, this.user.idUtente, this.dataDatiBando.idBando, this.titoloBando,
            this.dataPubblicazioneBando?.value ? new Date(this.dataPubblicazioneBando.value) : null,
            this.dataMinimaRendicontazione?.value ? new Date(this.dataMinimaRendicontazione.value) : null,
            this.dataScadenzaDomande?.value ? new Date(this.dataScadenzaDomande.value) : null,
            this.graduatoriaRadio == "1" ? true : false, false, false, Number(this.dotazioneFinanziaria),
            this.materiaRiferimentoSelected.idMateria, null, null, null, null, this.codIntIstitSelected.id,
            this.tipoOperazioneSelected.id, this.settoreCIPESelected.id, this.sottoSettoreCIPESelected.id,
            this.naturaCIPESelected.idNaturaCipe, this.settoreCPTSelected.id,
            this.tipologiaAttivazioneSelected.id, null, null, null, null, null, null);
        }
      } else {
        if (this.isNuovoBando) {
          request = new DatiBandoVo(this.user.idUtente, this.user.idUtente, null, this.titoloBando, this.dataPubblicazioneBando?.value ? new Date(this.dataPubblicazioneBando.value) : null,
            this.dataMinimaRendicontazione?.value ? new Date(this.dataMinimaRendicontazione.value) : null,
            this.dataScadenzaDomande?.value ? new Date(this.dataScadenzaDomande.value) : null,
            this.graduatoriaRadio == "1" ? true : false, false, false,
            Number(this.dotazioneFinanziaria), this.materiaRiferimentoSelected.idMateria, null, null, null, null, null,
            this.naturaCIPESelected.idTipoOperazione,
            this.settoreCIPESelected.id, this.sottoSettoreCIPESelected.id, this.naturaCIPESelected.idNaturaCipe, null,
            this.tipologiaAttivazioneSelected.id, null, null,
            this.normativaSelected.idLinea, new Date(this.dataDeterminaApprovBando.value), this.numeroApprovBando, null);
        } else {
          request = new DatiBandoVo(this.dataDatiBando.idUtenteIns, this.user.idUtente, this.dataDatiBando.idBando, this.titoloBando,
            this.dataPubblicazioneBando?.value ? new Date(this.dataPubblicazioneBando.value) : null, this.dataMinimaRendicontazione?.value ? new Date(this.dataMinimaRendicontazione.value) : null,
            this.dataScadenzaDomande?.value ? new Date(this.dataScadenzaDomande.value) : null,
            this.graduatoriaRadio == "1" ? true : false, false, false, Number(this.dotazioneFinanziaria),
            this.materiaRiferimentoSelected.idMateria, null, null, null, null, null, this.naturaCIPESelected.idTipoOperazione,
            this.settoreCIPESelected.id,
            this.sottoSettoreCIPESelected.id, this.naturaCIPESelected.idNaturaCipe, null, this.tipologiaAttivazioneSelected.id, null, null, this.normativaSelected.idLinea,
            new Date(this.dataDeterminaApprovBando.value), this.numeroApprovBando, null);
        }
      }

      this.loadedSalvaDatiBando = true;
      this.subscribers.salvaBando = this.datiBandoService.salvaDatiBando(request).subscribe(data => {
        if (this.isNuovoBando) {
          if (data.esito) {

            this.router.navigateByUrl(`drawer/` + this.idOperazione + `/configuraBando?titoloBando=` + this.titoloBando + `&nomeBandoLinea=` +
              null + `&idLineaDiIntervento=` + this.normativaSelected.idLinea + `&idBando=` + data.idBando + `&isNuovoBando=false&message=Salvataggio avvenuto con successo.`).then(() =>
                this.loadData())
          } else {
            this.showMessageError("Impossibile creare nuovo bando.");
          }
        } else {
          if (!(data.esito)) {
            this.showMessageError("Impossibile salvare dati bando.");
          }
        }
        this.loadedSalvaDatiBando = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di salvataggio dei dati bando.");
        this.loadedSalvaDatiBando = false;
      });
    }
  }
  /* ------------------- Fine tab dati bando ------------------- */
  /* ------------------- Inizio tab voci di spesa ------------------- */
  changeMacroVoce() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.progressivoMicroVoceSpesa = 0;
    this.microVociSpesa = new Array<VoceDiSpesaAssociataDTO>();
    this.loadedMicroVoceSpesa = true;
    this.subscribers.micro = this.vociSpesaService.microVoceSpesa(this.user, this.macroVoceSpesaSelected?.value?.idVoceDiSpesa.toString()).subscribe(data => {
      this.microVociSpesa = data;
      this.loadedMicroVoceSpesa = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei micro voce di spesa");
      this.loadedMicroVoceSpesa = false;
    });
  }

  aggiungiMacroVoce() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let comodoDati = new Array<any>();
    comodoDati.push(this.user);
    comodoDati.push(this.tipoOperazioneSelected.id.toString());
    comodoDati.push(this.naturaCIPESelected.idNaturaCipe.toString());
    const dialogRef = this.dialog.open(NuovaMacroVoceDialogComponent, {
      width: '800px',
      data: comodoDati
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!(result == undefined)) {
        let descMacroVoceComodo = result.data[0];
        let codiceTipoComodo = result.data[1];
        let voceSpesaMonit: IdDescBreveDescEstesaVo = result.data[2];
        let speseGestione: IdDescBreveDescEstesaVo = result.data[3];
        this.progressivoMacroVoceSpesa = this.progressivoMacroVoceSpesa - 1;
        let voce = new VoceDiSpesaAssociataDTO(this.progressivoMacroVoceSpesa, null, descMacroVoceComodo,
          voceSpesaMonit == null ? null : voceSpesaMonit.id, codiceTipoComodo, null, null, null, speseGestione ? "S" : null);
        this.macroVociSpesa.push(voce);
        this.macroVoceSpesaSelected.setValue(voce);
        this.macroGroup.get("macroControl").setValue(voce);
        this.microVociSpesa = new Array<VoceDiSpesaAssociataDTO>();
        this.microVoceSpesaSelected = undefined;
      }
    })
  }

  aggiungiMicroVoce() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let comodoDati = new Array<any>();
    comodoDati.push(this.user);
    comodoDati.push(this.tipoOperazioneSelected.id.toString());
    comodoDati.push(this.naturaCIPESelected.idNaturaCipe.toString());
    const dialogRef = this.dialog.open(NuovaMicroVoceDialogComponent, {
      width: '800px',
      data: comodoDati
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!(result == undefined)) {
        let descMicroVoceComodo = result.data[0];
        let codiceTipoComodo = result.data[1];
        let voceSpesaMonit: IdDescBreveDescEstesaVo = result.data[2];
        let speseGestione: IdDescBreveDescEstesaVo = result.data[3];
        this.progressivoMicroVoceSpesa = this.progressivoMicroVoceSpesa - 1;
        this.microVociSpesa.push(new VoceDiSpesaAssociataDTO(this.progressivoMicroVoceSpesa, null, descMicroVoceComodo,
          voceSpesaMonit == null ? null : voceSpesaMonit.id, codiceTipoComodo, null, null, null, speseGestione ? "S" : null));
        this.microVoceSpesaSelected = this.microVociSpesa.find(({ idVoceDiSpesa }) => idVoceDiSpesa == this.progressivoMicroVoceSpesa);
      }
    })
  }

  eliminaVoceSpesa(idVoceSpesa: number) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaVoceAssociata = true;
    this.subscribers.eliminaVoce = this.vociSpesaService.eliminaVoceAssociata(this.user, idVoceSpesa.toString(), this.params.get('idBando')).subscribe(data => {
      this.loadedEliminaVoceAssociata = false;
      if (data.code == "OK") {
        this.getVociSpesaTableData();
        this.showMessageSuccess("Eliminazione avvenuta con successo.");
      } else {
        this.showMessageError("Impossibile eliminare la voce selezionata");
      }

    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione della voce di spesa");
      this.loadedEliminaVoceAssociata = false;
    });
  }

  validazioneCampiVociSpesa() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.hasValidationErrorVociSpesa = false;


    if (!this.macroVoceSpesaSelected || !this.macroVoceSpesaSelected.value || !this.macroVoceSpesaSelected.value.idVoceDiSpesa) {
      this.hasValidationErrorVociSpesa = true;
      this.macroGroup.get("macroControl").setErrors({ error: "required" })
      this.macroGroup.get("macroControl").markAsTouched();
    }

    if (this.hasValidationErrorVociSpesa) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    }
  }

  getVociSpesaTableData() {
    this.loadedVociAssociate = true;
    this.subscribers.vociAssociate = this.vociSpesaService.vociAssociate(this.user, this.params.get('idBando')).subscribe(data => {
      this.loadedVociAssociate = false;
      this.dataSourceVociSpesa = new MatTableDataSource(data);
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di restituzione delle voci associate");
      this.loadedVociAssociate = false;
    });
  }

  getMicroVoceFromString(word: string) {

    var newarr = word.split(" - ");

    if (newarr.length == 1) {
      return "";
    } else {
      return newarr[newarr.length - 1];
    }
  }

  getMacroVoceFromString(word: string) {

    var newarr = word.split(" - ");

    if (newarr.length == 1) {
      return word;
    } else {
      return newarr[0];
    }
  }

  associaVoceSpesa() {
    this.showTableVoceSpesa = true;

    this.resetMessageError();
    this.resetMessageSuccess();
    this.validazioneCampiVociSpesa();
    if (!this.hasValidationErrorVociSpesa) {
      this.loadedAssociaVoceAssociata = true;
      this.subscribers.associaVoce = this.vociSpesaService.associaVoceAssociata(this.user, this.params.get('idBando'), this.macroVoceSpesaSelected?.value?.idVoceDiSpesa.toString(),
        this.macroVoceSpesaSelected?.value?.descVoceDiSpesa.toString(), this.microVoceSpesaSelected == null ? null : this.microVoceSpesaSelected.idVoceDiSpesa.toString(),
        this.microVoceSpesaSelected == null ? null : this.microVoceSpesaSelected.descVoceDiSpesa.toString(),
        this.macroVoceSpesaSelected?.value?.idVoceDiSpesaMonit ? this.macroVoceSpesaSelected?.value?.idVoceDiSpesaMonit.toString() : null,
        this.macroVoceSpesaSelected?.value?.codTipoVoceDiSpesa, this.microVoceSpesaSelected == undefined ? undefined : this.microVoceSpesaSelected.codTipoVoceDiSpesa,
        this.macroVoceSpesaSelected?.value?.flagSpeseGestione, this.microVoceSpesaSelected?.flagSpeseGestione).subscribe(data => {
          this.loadedAssociaVoceAssociata = false;
          if (data.code == "OK") {
            this.showMessageSuccess("Associazione avvenuta con successo.");
            this.getVociSpesaTableData();
            this.loadMacroVociDiSpesa();
            this.microVociSpesa = new Array<VoceDiSpesaAssociataDTO>();
            this.microVoceSpesaSelected = undefined;
          } else {
            this.showMessageError("Impossibile associare la nuova voce di spesa.");
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di associazione di una nuova voce di spesa");
          this.loadedAssociaVoceAssociata = false;
        });
    }
  }
  /* ------------------- Fine tab voci di spesa ------------------- */
  /* ------------------- Inizio tab dati aggiuntivi ------------------- */
  changeTipoIndicatore() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.indicatori = new Array<IdDescBreveDescEstesaVo>();
    this.indicatoriGroup.get("indicatoriControl")?.setValue(null);
    this.indicatoreSelected.setValue(null);
    if (!(this.tipoIndicatoreSelected == null)) {
      this.loadedIndicatore = true;
      this.subscribers.ind = this.datiAggiuntiviService.indicatore(this.user, this.tipoIndicatoreSelected.id.toString()).subscribe(data => {
        this.indicatori = data;

        this.filteredOptionsInd = this.indicatoriGroup.controls['indicatoriControl'].valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descrizione),
            map(name => name ? this._filterIndicatori(name) : (this.indicatori ? this.indicatori.slice() : null))
          );
        this.loadedIndicatore = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento degli indicatori");
        this.loadedIndicatore = false;
      });
    }
  }

  getModAgevTableData() {
    this.loadedModalitaAssociata = true;
    this.subscribers.modAssociata = this.datiAggiuntiviService.modalitaAssociata(this.params.get('idBando')).subscribe(data => {


      this.loadedModalitaAssociata = false;
      this.dataSourceModAgev = new MatTableDataSource(data);
      console.log("dataSourceModAgev", data)
      console.log("dataSourceModAgev", this.dataSourceModAgev)
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di restituzione delle modalità agevolazioni");
      this.loadedModalitaAssociata = false;
    });
  }

  getTipoTratTableData() {
    this.loadedTipoTrattamentoAssociato = true;
    this.subscribers.tipoTratt = this.datiAggiuntiviService.tipoTrattamentoAssociato(this.user, this.params.get('idBando')).subscribe(data => {
      this.loadedTipoTrattamentoAssociato = false;
      this.dataSourceTipoTrat = new MatTableDataSource(data);
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di restituzione dei tipo trattamento");
      this.loadedTipoTrattamentoAssociato = false;
    });
  }

  getSogFinTableData() {
    this.loadedSoggettiFinanziatoreAssociato = true;
    this.subscribers.soggFinAssociato = this.datiAggiuntiviService.soggettiFinanziatoreAssociato(this.user, this.params.get('idBando')).subscribe(data => {
      this.loadedSoggettiFinanziatoreAssociato = false;
      this.dataSourceSogFin = new MatTableDataSource(data);
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di restituzione dei soggetti finanziatori");
      this.loadedSoggettiFinanziatoreAssociato = false;
    });
  }

  getCausErogTableData() {
    this.loadedCausaleErogazioneAssociata = true;
    this.subscribers.causaleErogazioneAssociata = this.datiAggiuntiviService.causaleErogazioneAssociata(this.user, this.params.get('idBando')).subscribe(data => {
      this.loadedCausaleErogazioneAssociata = false;
      this.dataSourceCausErog = new MatTableDataSource(data);
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di restituzione delle causali erogazione");
      this.loadedCausaleErogazioneAssociata = false;
    });
  }

  getIndicatTableData() {
    this.loadedIndicatoreAssociato = true;
    this.subscribers.indAssociato = this.datiAggiuntiviService.indicatoreAssociato(this.user, this.params.get('idBando')).subscribe(data => {
      this.loadedIndicatoreAssociato = false;
      this.dataSourceIndic = new MatTableDataSource(data);
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di restituzione degli indicatori");
      this.loadedIndicatoreAssociato = false;
    });
  }

  getLiquidFromString(stringaCompleta: string) {
    var liquidComodo = stringaCompleta.split('Modalita di liquidazione: ')[1];
    if (liquidComodo == "N") {
      return "No";
    } else {
      return "Si";
    }
  }

  getImpAgevFromString(stringaCompleta: string) {
    var comodo = stringaCompleta.split('%');
    if (comodo.length == 1) {
      return undefined;
    } else {
      var importoComodo = comodo[0].split(' - ')[1];
      return importoComodo + "%";
    }
  }


  getPeriodoStabilita(stringaCompleta: string) {

    try {

      var arr = stringaCompleta.split('Periodo Stabilita:');
      if (arr.length == 1)
        return undefined
      else {
        return arr[1].split('-')[0];
      }
    }
    catch (e) {
      return undefined
    }
  }

  getModAgevFromString(stringaCompleta: string) {
    return stringaCompleta.split(' - ')[0];
  }

  associaModalitaAgevolazione() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!(this.modalitaAgevolazioneSelected == undefined)) {
      this.loadedAssociaModalitaAgevolazione = true;
      this.subscribers.associaMod = this.datiAggiuntiviService.associaModalitaAgevolazione(this.user, this.params.get('idBando'), this.modalitaAgevolazioneSelected.id.toString(), this.importoAgevolato == undefined ? undefined : this.importoAgevolato.toString(), null, this.periodoStabilita, this.importoDaErogare, this.checkedModalitaLiquidazione ? "S" : "N").subscribe(data => {
        if (data) {
          if (data.esito) {
            this.showMessageSuccess("Associazione avvenuta con successo.")
            this.modalitaAgevolazioneSelected = undefined;
            this.importoAgevolato = undefined;
            this.getModAgevTableData();
          } else {
            this.showMessageError("Errore in fase di associazione della modalità di agevolazione.");
          }
        }
        this.loadedAssociaModalitaAgevolazione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di associazione della modalità di agevolazione.");
        this.loadedAssociaModalitaAgevolazione = false;
      });
    }
  }

  deleteModAgev(idModAgev: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaModalitaAgevolazioneAssociata = true;
    this.subscribers.eliminaMod = this.datiAggiuntiviService.eliminaModalitaAgevolazioneAssociata(this.user, this.params.get('idBando'), idModAgev).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Eliminazione avvenuta con successo.");
          this.getModAgevTableData();
        } else {
          this.showMessageError(data.message);
          //this.showMessageError("Errore in fase di eliminazione della modalità di agevolazione.");
        }
      }
      this.loadedEliminaModalitaAgevolazioneAssociata = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione della modalità di agevolazione.");
      this.loadedEliminaModalitaAgevolazioneAssociata = false;
    });
  }

  associaTipoTrattamento() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!(this.tipoTrattamentoSelected == undefined)) {
      this.loadedAssociaTipoTrattamento = true;
      this.subscribers.associaTipoTratt = this.datiAggiuntiviService.associaTipoTrattamento(this.user, this.params.get('idBando'), this.tipoTrattamentoSelected.id.toString()).subscribe(data => {
        if (data.code == "OK") {
          this.tipoTrattamentoSelected = undefined;
          this.getTipoTratTableData();
          this.showMessageSuccess("Associazione avvenuta con successo.")
        } else {
          this.showMessageError("Impossibile associare il tipo trattamento.");
        }
        this.loadedAssociaTipoTrattamento = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di associazione del tipo trattamento.");
        this.loadedAssociaTipoTrattamento = false;
      });
    }
  }

  deleteTipoTrat(idTipoTrat: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaTipoTrattamentoAssociato = true;
    this.subscribers.eliminaTipoTratt = this.datiAggiuntiviService.eliminaTipoTrattamentoAssociato(this.user, this.params.get('idBando'), idTipoTrat).subscribe(data => {
      if (data.code == "OK") {
        this.getTipoTratTableData();
        this.showMessageSuccess("Eliminazione avvenuta con successo.");
      } else {
        this.showMessageError("Impossibile eliminare il tipo trattamento.");
      }
      this.loadedEliminaTipoTrattamentoAssociato = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione del tipo trattamento.");
      this.loadedEliminaTipoTrattamentoAssociato = false;
    });
  }

  setPredefinito(idTipoTrat: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedTipoTrattamentoAssociatoPredefinito = true;
    this.subscribers.tipoTrattamentoAssociatoPredefinito = this.datiAggiuntiviService.tipoTrattamentoAssociatoPredefinito(this.user, this.params.get('idBando'), idTipoTrat).subscribe(data => {
      if (data.code == "OK") {
        this.getTipoTratTableData();
        this.showMessageSuccess("Tipo trattamento predefinito settato con successo.")
      } else {
        this.showMessageError("Impossibile settare come predefinito il tipo trattamento.");
      }
      this.loadedTipoTrattamentoAssociatoPredefinito = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di settaggio come predefinito del tipo trattamento.");
      this.loadedTipoTrattamentoAssociatoPredefinito = false;
    });
  }

  associaSogFin() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!(this.soggettoFinanziatoreSelected == undefined)) {
      this.loadedAssociaSoggettoFinanziatore = true;
      this.subscribers.associaSoggettoFinanziatore = this.datiAggiuntiviService.associaSoggettoFinanziatore(this.user, this.params.get('idBando'), this.soggettoFinanziatoreSelected.descBreve, this.soggettoFinanziatoreSelected.id.toString(), this.percentuale).subscribe(data => {
        if (data.code == "OK") {
          this.soggettoFinanziatoreSelected = undefined;
          this.percentuale = undefined;
          this.getSogFinTableData();
          this.showMessageSuccess("Associazione avvenuta con successo.");
        } else {
          this.showMessageError("Impossibile associare il soggetto finanziatore.");
        }
        this.loadedAssociaSoggettoFinanziatore = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di associazione del soggetto finanziatore.");
        this.loadedAssociaSoggettoFinanziatore = false;
      });
    }
  }

  deleteSogFin(idSogFin: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaSoggettoFinanziatore = true;
    this.subscribers.eliminaSoggettoFinanziatore = this.datiAggiuntiviService.eliminaSoggettoFinanziatore(this.user, this.params.get('idBando'), idSogFin).subscribe(data => {

      if (data.code == "OK") {
        this.getSogFinTableData();
        this.showMessageSuccess("Eliminazione avvenuta con successo.")
      } else {
        this.showMessageError("Impossibile eliminare il soggetto finanziatore.");
      }
      this.loadedEliminaSoggettoFinanziatore = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione del soggetto finanziatore.");
      this.loadedEliminaSoggettoFinanziatore = false;
    });
  }

  associaCausErog() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!(this.causaleErogazioneSelected == undefined)) {
      var request = new CausaleDiErogazioneAssociataDTO(null, Number(this.params.get('idBando')),
        this.causaleErogazioneSelected.id,
        Number(this.sogliaSpesaQuietanziata),
        Number(this.erogazione), Number(this.limite), null, null,
        this.causaleErogazioneSelected.descEstesa, null, null, null,
        (!(this.modalitaAgevoErogazioneSelected == undefined)) ? this.modalitaAgevoErogazioneSelected.descrizione : null,
        (!(this.modalitaAgevoErogazioneSelected == undefined)) ? this.modalitaAgevoErogazioneSelected.id : null,
        null);

      this.loadedAssociaCausale = true;
      this.subscribers.associaCausale = this.datiAggiuntiviService.associaCausale(this.user, request).subscribe(data => {
        if (data.code == "OK") {
          this.causaleErogazioneSelected = undefined;
          this.sogliaSpesaQuietanziata = undefined;
          this.erogazione = undefined;
          this.limite = undefined;
          this.modalitaAgevoErogazioneSelected = undefined;
          this.getCausErogTableData();
          this.showMessageSuccess("Associazione avvenuta con successo.");
        } else {
          this.showMessageError("Impossibile associare la causale erogazione.");
        }
        this.loadedAssociaCausale = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di associazione della causale erogazione.");
        this.loadedAssociaCausale = false;
      });
    }
  }

  deleteCausErog(progrBandoCausaleErogaz: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaCausaleErogazione = true;
    this.subscribers.eliminaCausaleErogazione = this.datiAggiuntiviService.eliminaCausaleErogazione(this.user, progrBandoCausaleErogaz).subscribe(data => {
      if (data) {
        if (data.code === "OK") {
          this.getCausErogTableData();
          this.showMessageSuccess("Eliminazione avvenuta con successo.");
        } else {
          this.showMessageError("Errore in fase di eliminazione della causale di erogazione.");
        }
      }
      this.loadedEliminaCausaleErogazione = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione della causale di erogazione.");
      this.loadedEliminaCausaleErogazione = false;
    });
  }

  associaIndicat() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!(this.indicatoreSelected == undefined)) {
      this.loadedAssociaIndicatore = true;
      this.subscribers.associaIndicatore = this.datiAggiuntiviService.associaIndicatore(this.user, this.params.get('idBando'), this.indicatoreSelected?.value?.id.toString(),
        this.infoIniziale, this.infoFinale).subscribe(data => {
          if (data.code == "OK") {
            this.indicatoreSelected.setValue(null);
            this.indicatoriGroup.get("indicatoriControl").setValue(null);
            this.tipoIndicatoreSelected = undefined;
            this.getIndicatTableData();
            this.showMessageSuccess(data.message);
          } else {
            this.showMessageError("Impossibile associare l'indicatore");
          }
          this.loadedAssociaIndicatore = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di associazione dell'indicatore");
          this.loadedAssociaIndicatore = false;
        });
    }
  }

  deleteIndicat(idIndicat: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaIndicatore = true;
    this.subscribers.eliminaIndicatore = this.datiAggiuntiviService.eliminaIndicatore(this.user, this.params.get('idBando'), idIndicat).subscribe(data => {

      if (data.code == "OK") {
        this.getIndicatTableData();
        this.showMessageSuccess(data.message);
      } else {
        this.showMessageError("Impossibile eliminare il soggetto finanziatore");
      }

      this.loadedEliminaIndicatore = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione dell'indicatore");
      this.loadedEliminaIndicatore = false;
    });
  }
  /* ------------------- Fine tab dati aggiuntivi ------------------- */
  /* ------------------- Inizio tab linea di intervento ------------------- */

  validazioneCampiLinInt() {
    this.hasValidationErrorLinInt = false;

    if (this.processoLineeIntervento == 1) {
      this.controlRequiredLineaInterventoForm("prioritaQSN");
      this.controlRequiredLineaInterventoForm("obiettivoGeneraleQSN");
      this.controlRequiredLineaInterventoForm("obiettivoSpecificoQSN");
    } else {
      if (!this.obTemGroup.get('obTemControl')?.value?.id) {
        this.obTemGroup.get('obTemControl').setErrors({ error: 'required' });
        this.obTemGroup.get('obTemControl').markAsTouched()
      }
      if (!this.classRAGroup.get('classRAControl')?.value?.id) {
        this.classRAGroup.get('classRAControl').setErrors({ error: 'required' });
        this.classRAGroup.get('classRAControl').markAsTouched()
      }

    }

    this.controlRequiredLineaInterventoForm("titoloBandoLinea");
    this.controlRequiredLineaInterventoForm("processo");
    this.controlRequiredLineaInterventoForm("catCIPE");
    this.controlRequiredLineaInterventoForm("tipoCIPE");
    this.controlRequiredLineaInterventoForm("durProgDatConc");

    if (this.hasValidationErrorLinInt) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    }
  }

  controlRequiredLineaInterventoForm(name: string) {
    if (!this.lineaInterventoForm.form.get(name) || !this.lineaInterventoForm.form.get(name).value) {
      this.lineaInterventoForm.form.get(name).setErrors({ error: 'required' });
      this.hasValidationErrorLinInt = true;
    }
  }

  changeNormativaLineaIntervento() {
    this.resetMessageError();
    this.resetMessageSuccess();
    // azzerare valori tab linea intervento
    this.asseSelected = null;
    this.misuraSelected = null;
    this.lineaSelected = null;
    this.titoloBandoLinea = null;
    this.prioritaQSNSelected = null;
    this.obiettiviGeneraleQSN = null;
    this.obiettiviSpecificoQSN = null;
    this.catCIPESelected = null;
    this.tipoCIPESelected = null;
    this.obTemSelected = null;
    this.classRASelected = null;
    this.durProgDatConc = null;
    this.processoSelected = null;
    this.bandoSIFSelected = null;
    this.livIstSelected = null;
    this.tipoLocSelected = null;
    this.progComplessoSelected = null;
    this.clasMETSelected = null;
    this.codRNA = null;

    this.assi = new Array<LineaInterventoVo>();
    this.livsIst = new Array<Decodifica>();
    this.tiposLoc = new Array<Decodifica>();
    this.progsComplesso = new Array<Decodifica>();
    this.classesMET = new Array<Decodifica>();

    if (!(this.normativaLinInSelected == null)) {
      this.loadedAsse = true;
      this.subscribers.assee = this.lineaInterventoService.asse(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
        this.assi = data;
        this.loadedAsse = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento degli asse");
        this.loadedAsse = false;
      });

      this.loadedLivelloIstruzione = true;
      this.subscribers.livelloIstruzione = this.lineaInterventoService.livelloIstruzione(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
        this.livsIst = data;
        this.loadedLivelloIstruzione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei livello istituzione");
        this.loadedLivelloIstruzione = false;
      });

      this.loadedTipoLocalizzazione = true;
      this.subscribers.tipoLocalizzazione = this.lineaInterventoService.tipoLocalizzazione(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
        this.tiposLoc = data;
        this.loadedTipoLocalizzazione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei tipo localizzazione");
        this.loadedTipoLocalizzazione = false;
      });

      this.loadedProgettoComplesso = true;
      this.subscribers.progettoComplesso = this.lineaInterventoService.progettoComplesso(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
        this.progsComplesso = data;
        this.loadedProgettoComplesso = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei progetto complesso");
        this.loadedProgettoComplesso = false;
      });

      this.loadedMet = true;
      this.subscribers.mett = this.lineaInterventoService.met(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
        this.classesMET = data;
        this.loadedMet = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei met");
        this.loadedMet = false;
      });
    }
  }

  changeAsse() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.misure = new Array<LineaInterventoVo>();
    this.prioritasQSN = new Array<Decodifica>();
    this.obiettiviGeneraleQSN = new Array<Decodifica>();
    this.obiettiviSpecificoQSN = new Array<Decodifica>();
    if (!(this.asseSelected == null)) {
      this.loadedMisura = true;
      this.subscribers.misura = this.lineaInterventoService.misura(this.user, this.asseSelected.idLinea.toString()).subscribe(data => {
        this.misure = data;
        this.loadedMisura = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento delle misure");
        this.loadedMisura = false;
      });

      this.loadedPrioritaQSN = true;
      this.subscribers.prioritaQSN = this.lineaInterventoService.prioritaQSN(this.user, this.asseSelected.idLinea.toString()).subscribe(data => {
        this.prioritasQSN = data;
        this.loadedPrioritaQSN = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento delle priorità QSN");
        this.loadedPrioritaQSN = false;
      });
    }
  }

  changePrioritaQSN() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.obiettiviGeneraleQSN = new Array<Decodifica>();
    if (!(this.prioritaQSNSelected == null)) {
      this.loadedObiettivoGeneraleQSN = true;
      this.subscribers.obiettivoGeneraleQSN = this.lineaInterventoService.obiettivoGeneraleQSN(this.user, this.prioritaQSNSelected.id.toString()).subscribe(data => {
        this.obiettiviGeneraleQSN = data;
        this.loadedObiettivoGeneraleQSN = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento degli obiettivo generale QSN");
        this.loadedObiettivoGeneraleQSN = false;
      });
    }
  }

  changeObiettivoGeneraleQSN() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.obiettiviSpecificoQSN = new Array<Decodifica>();
    if (!(this.obiettivoGeneraleQSNSelected == null)) {
      this.loadedObiettivoSpecificoQSN = true;
      this.subscribers.obiettivoSpecificoQSN = this.lineaInterventoService.obiettivoSpecificoQSN(this.user, this.obiettivoGeneraleQSNSelected.id.toString()).subscribe(data => {
        this.obiettiviSpecificoQSN = data;
        this.loadedObiettivoSpecificoQSN = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase degli obiettivo specifico QSN");
        this.loadedObiettivoSpecificoQSN = false;
      });
    }
  }

  changeMisura() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.linee = new Array<LineaInterventoVo>();
    if (!(this.misuraSelected == null)) {
      this.loadedLinea = true;
      this.subscribers.linea = this.lineaInterventoService.linea(this.user, this.misuraSelected.idLinea.toString()).subscribe(data => {
        this.linee = data;
        this.loadedLinea = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento delle linee");
        this.loadedLinea = false;
      });
    }
  }

  changeObTematico(isEditLinea?: boolean) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.classesRA = new Array<Decodifica>();
    this.classRAGroup.get("classRAControl")?.setValue(null);
    this.classRASelected.setValue(null);
    if (!(this.obTemSelected == null)) {
      this.loadedClassificazioneRA = true;
      this.subscribers.classificazioneRA = this.lineaInterventoService.classificazioneRA(this.user, this.obTemSelected?.value?.id.toString()).subscribe(data => {
        this.classesRA = data;

        this.filteredOptionsClassRA = this.classRAGroup.controls['classRAControl'].valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descrizione),
            map(name => name ? this._filterClassRA(name) : (this.classesRA ? this.classesRA.slice() : null))
          );
        if (this.dataModificaLineaIntervento?.idClassificazioneRa && isEditLinea) {
          this.classRASelected.setValue(this.classesRA.find(x => x.id == this.dataModificaLineaIntervento.idClassificazioneRa));
          this.classRAGroup.get("classRAControl")?.setValue(this.classRASelected?.value);
        }
        this.loadedClassificazioneRA = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento delle classificazione RA");
        this.loadedClassificazioneRA = false;
      });
    }
  }

  changeBandoSIF(event: MatRadioChange) {
    this.bandoSIFSelected = null;
    this.livIstSelected = null;

    if (event.value === "1") {
      this.showLivIst = true;
      this.showBandoSIF = false;
    } else {
      this.showLivIst = false;
      this.showBandoSIF = true;
    }
  }

  changeLivelloIstituzione() {
    if (this.livIstSelected == null) {
      this.showFondoFondi = false;
    } else {
      this.showFondoFondi = true;
    }
  }

  resetFormLineaIntervento() {
    this.normativaLinInSelected = this.normativaSelected;
    this.processoLineeIntervento = this.processoDatiBando;

    this.assi = new Array<LineaInterventoVo>();
    this.asseSelected = null;
    if (!((this.normativaLinInSelected == null) || this.normativaLinInSelected == undefined)) {
      this.loadedAsse = true;
      this.subscribers.asse = this.lineaInterventoService.asse(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
        this.assi = data;
        this.loadedAsse = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento degli asse");
        this.loadedAsse = false;
      });
    }

    this.misure = new Array<LineaInterventoVo>();
    this.misuraSelected = null;
    this.linee = new Array<LineaInterventoVo>();
    this.lineaSelected = null;
    this.titoloBandoLinea = null;
    this.prioritasQSN = new Array<Decodifica>();
    this.prioritaQSNSelected = null;
    this.obiettiviGeneraleQSN = new Array<Decodifica>();
    this.obiettivoGeneraleQSNSelected = null;
    this.obiettiviSpecificoQSN = new Array<Decodifica>();
    this.obiettivoSpecificoQSNSelected = null;

    this.catsCIPE = new Array<Decodifica>();
    this.catCIPESelected = null;
    this.loadedCategoriaCipe = true;
    this.subscribers.categoriaCipe = this.lineaInterventoService.categoriaCipe(this.user, this.sottoSettoreCIPESelected.id.toString()).subscribe(data => {
      this.catsCIPE = data;
      this.loadedCategoriaCipe = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle categorie CIPE");
      this.loadedCategoriaCipe = false;
    });

    this.tipiCIPE = new Array<Decodifica>();
    this.tipoCIPESelected = null;
    this.loadedTipologiaCipe = true;
    this.subscribers.tipologiaCipe = this.lineaInterventoService.tipologiaCipe(this.user,
      this.naturaCIPESelected.idNaturaCipe.toString()).subscribe(data => {
        this.tipiCIPE = data;
        this.loadedTipologiaCipe = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei tipi CIPE");
        this.loadedTipologiaCipe = false;
      });

    this.durProgDatConc = null;

    this.processi = new Array<Decodifica>();
    this.processoSelected = null;
    this.loadedProcessoInterno = true;
    this.subscribers.processoInterno = this.lineaInterventoService.processoInterno(this.user).subscribe(data => {
      this.processi = data;
      this.loadedProcessoInterno = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei processo interno");
      this.loadedProcessoInterno = false;
    });

    this.loadObiettiviTematici();

    this.classesRA = new Array<Decodifica>();
    this.classRASelected.setValue(null);
    this.classRAGroup.get("classRAControl").setValue(null);


    this.BandoSIFRadio = "2";

    this.loadBandiSif();

    this.livsIst = new Array<Decodifica>();
    this.livIstSelected = null;
    if (!((this.normativaLinInSelected == null) || this.normativaLinInSelected == undefined)) {
      this.loadedLivelloIstruzione = true;
      this.subscribers.livelloIstruzione = this.lineaInterventoService.livelloIstruzione(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
        this.livsIst = data;
        this.loadedLivelloIstruzione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei livello istituzione");
        this.loadedLivelloIstruzione = false;
      });
    }

    this.showFondoFondi = false;

    this.FondoFondiRadio = "2";

    this.tiposLoc = new Array<Decodifica>();
    this.tipoLocSelected = null;

    if (!((this.normativaLinInSelected == null) || this.normativaLinInSelected == undefined)) {
      this.loadedTipoLocalizzazione = true;
      this.subscribers.tipoLocalizzazione = this.lineaInterventoService.tipoLocalizzazione(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
        this.tiposLoc = data;
        this.loadedTipoLocalizzazione = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei tipo localizzazione");
        this.loadedTipoLocalizzazione = false;
      });
    }

    this.progsComplesso = new Array<Decodifica>();
    this.progComplessoSelected = null;
    if (!((this.normativaLinInSelected == null) || this.normativaLinInSelected == undefined)) {
      this.loadedProgettoComplesso = true;
      this.subscribers.progComplesso = this.lineaInterventoService.progettoComplesso(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
        this.progsComplesso = data;
        this.loadedProgettoComplesso = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei progetto complesso");
        this.loadedProgettoComplesso = false;
      });
    }

    this.classesMET = new Array<Decodifica>();
    this.clasMETSelected = null;
    if (!((this.normativaLinInSelected == null) || this.normativaLinInSelected == undefined)) {
      this.loadedMet = true;
      this.subscribers.met = this.lineaInterventoService.met(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
        this.classesMET = data;
        this.loadedMet = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei met");
        this.loadedMet = false;
      });
    }

    this.codRNA = null;
  }

  get is1420(): boolean {
    return this.normativaSelected?.descBreve === Constants.DESC_BREVE_NORMATIVA_14_20;
  }

  get is2127(): boolean {
    return this.normativaSelected?.descBreve === this.DESC_BREVE_NORMATIVA_21_27;
  }

  readonly DESC_BREVE_NORMATIVA_21_27 = Constants.DESC_BREVE_NORMATIVA_21_27;
  loadBandiSif() {
    if (this.normativaSelected?.descBreve === this.DESC_BREVE_NORMATIVA_21_27) {
      this.bandiSIF = new Array<BandoLineaAssociatoVo>();
      this.bandoSIFSelected = null;
      this.loadedElencoBandiSif = true;
      this.subscribers.elencoBandiSif = this.lineaInterventoService.elencoBandiSif(this.user, null).subscribe(data => {
        this.bandiSIF = data;
        if (this.bandiSIF?.length > 0 && this.dataModificaLineaIntervento?.progrBandoLineaIntervSif) {
          this.bandoSIFSelected = this.bandiSIF.find(x => x.progrBandoLineaIntervento == this.dataModificaLineaIntervento.progrBandoLineaIntervSif);
        }
        this.loadedElencoBandiSif = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei bandi SIF");
        this.loadedElencoBandiSif = false;
      });
      if (+this.params.get('idBando')) {
        this.loadedIsSiffino = false;
        this.userService.isBandoSiffino(null, +this.params.get('idBando')).subscribe(data => {
          this.isSiffino = data;
          this.loadedIsSiffino = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di verifica del bando SIF associato");
          this.loadedIsSiffino = true;
        });

      }
    }
  }

  associaLineaAssociata() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.validazioneCampiLinInt();
    if (!this.hasValidationErrorLinInt) {
      var request;

      var comodoLineaIntervento;
      if (this.lineaSelected == null) {
        if (this.misuraSelected == null) {
          if (this.asseSelected == null) {
            comodoLineaIntervento = this.normativaLinInSelected.idLinea;
          } else {
            comodoLineaIntervento = this.asseSelected.idLinea;
          }
        } else {
          comodoLineaIntervento = this.misuraSelected.idLinea;
        }
      } else {
        comodoLineaIntervento = this.lineaSelected.idLinea;
      }

      if (this.processoLineeIntervento == 1) {
        request = new LineaInterventoDaAssociareVo(Number(this.params.get('idBando')), comodoLineaIntervento, this.titoloBandoLinea, null, this.catCIPESelected.id, this.tipoCIPESelected.id, 1,
          this.durProgDatConc, this.processoSelected.id, null, null, null, null, null, null, null, null, null, null);
      } else {
        request = new LineaInterventoDaAssociareVo(Number(this.params.get('idBando')), comodoLineaIntervento, this.titoloBandoLinea, null, this.catCIPESelected.id, this.tipoCIPESelected.id, 1,
          this.durProgDatConc, this.processoSelected.id, this.BandoSIFRadio == "1" ? "S" : "N", this.bandoSIFSelected == null ? null : this.bandoSIFSelected.progrBandoLineaIntervento, this.tipoLocSelected == null ? null : this.tipoLocSelected.id,
          this.livIstSelected == null ? null : this.livIstSelected.id, this.progComplessoSelected == null ? null : this.progComplessoSelected.id,
          this.clasMETSelected == null ? null : this.clasMETSelected.id, this.FondoFondiRadio == "1" ? "S" : "N", this.classRASelected?.value?.id,
          this.codRNA, this.is2127 && this.BandoSIFRadio === "1" ? this.flagArt58 : null);
      }

      this.loadedAssociaLineaIntervento = true;
      this.subscribers.associaLineaIntervento = this.lineaInterventoService.associaLineaIntervento(request).subscribe(data => {
        this.loadedAssociaLineaIntervento = false;
        if (data.code == "OK" || data.code == "OKSIF") {
          this.getLineeAssociateTableData();
          this.showFormModificaLineaIntervento = false;
          this.resetFormLineaIntervento();
          this.loadBandiSif();
          this.flagArt58 = "S"; //risetto il default
          this.showMessageSuccess(data.code == "OK" ? "Associazione avvenuta con successo." : data.message);
        } else if (data.code == "ERRORSIF") {
          this.showMessageError(data.message);
        } else {
          this.showMessageError("Impossibile associare la linea.");
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di associazione della linea intervento.");
        this.loadedAssociaLineaIntervento = false;
        this.showFormModificaLineaIntervento = false;
      });
    }
  }

  deleteLineaAssociata(progrBandoLineaIntervento: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedEliminaLineaIntervento = true;
    this.subscribers.eliminaLineaIntervento = this.lineaInterventoService.eliminaLineaIntervento(this.user, progrBandoLineaIntervento).subscribe(data => {
      if (data) {
        if (data.code === "OK") {
          this.getLineeAssociateTableData();
          this.loadBandiSif();
          this.showMessageSuccess("Eliminazione avvenuta con successo.");
        } else {
          this.showMessageError("Errore in fase di eliminazione della linea intervento.");
        }
      }
      this.loadedEliminaLineaIntervento = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione della linea intervento.");
      this.loadedEliminaLineaIntervento = false;
    });
  }

  editLineaAssociata(progrBandoLineaIntervento: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.showFormModificaLineaIntervento = true;
    this.loadedModificaLineaIntervento = true;
    this.subscribers.modificaLineaIntervento = this.lineaInterventoService.modificaLineaIntervento(this.user, progrBandoLineaIntervento).subscribe(data => {
      this.loadedModificaLineaIntervento = false;
      this.dataModificaLineaIntervento = data;

      this.normativaLinInSelected = this.normativeLinInt.find(x => x.idLinea == this.dataModificaLineaIntervento.idNormativa);
      if (!(this.dataModificaLineaIntervento.idAsse == null)) {
        this.assi = new Array<LineaInterventoVo>();
        this.loadedAsse = true;
        this.subscribers.asse = this.lineaInterventoService.asse(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
          this.assi = data;
          this.loadedAsse = false;
          this.asseSelected = this.assi.find(x => x.idLinea == this.dataModificaLineaIntervento.idAsse);

          this.misure = new Array<LineaInterventoVo>();
          this.loadedMisura = true;
          this.subscribers.misura = this.lineaInterventoService.misura(this.user, this.asseSelected.idLinea.toString()).subscribe(data => {
            this.misure = data;
            this.loadedMisura = false;

            if (!(this.dataModificaLineaIntervento.idMisura == null)) {
              this.misuraSelected = this.misure.find(x => x.idLinea == this.dataModificaLineaIntervento.idMisura);
              this.linee = new Array<LineaInterventoVo>();
              this.loadedLinea = true;
              this.subscribers.linea = this.lineaInterventoService.linea(this.user, this.misuraSelected.idLinea.toString()).subscribe(data => {
                this.linee = data;
                this.loadedLinea = false;
                if (!(this.dataModificaLineaIntervento.idLinea == null)) {
                  this.lineaSelected = this.linee.find(x => x.idLinea == this.dataModificaLineaIntervento.idLinea);
                }
              }, err => {
                this.handleExceptionService.handleNotBlockingError(err);
                this.showMessageError("Errore in fase di caricamento delle linee");
                this.loadedLinea = false;
              });
            }
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di caricamento delle misure");
            this.loadedMisura = false;
          });

          if (this.processoLineeIntervento == 1) {
            this.prioritasQSN = new Array<Decodifica>();
            this.loadedPrioritaQSN = true;
            this.subscribers.prioritaQSN = this.lineaInterventoService.prioritaQSN(this.user, this.asseSelected.idLinea.toString()).subscribe(data => {
              this.prioritasQSN = data;
              this.loadedPrioritaQSN = false;

              this.prioritaQSNSelected = this.prioritasQSN.find(x => x.id == this.dataModificaLineaIntervento.idPrioritaQSN);

              this.obiettiviGeneraleQSN = new Array<Decodifica>();
              this.loadedObiettivoGeneraleQSN = true;
              this.subscribers.obiettiviGeneraleQSN = this.lineaInterventoService.obiettivoGeneraleQSN(this.user, this.prioritaQSNSelected.id.toString()).subscribe(data => {
                this.obiettiviGeneraleQSN = data;
                this.loadedObiettivoGeneraleQSN = false;

                this.obiettivoGeneraleQSNSelected = this.obiettiviGeneraleQSN.find(x => x.id == this.dataModificaLineaIntervento.idObiettivoGeneraleQSN);

                this.obiettiviSpecificoQSN = new Array<Decodifica>();
                this.loadedObiettivoSpecificoQSN = true;
                this.subscribers.obiettivoSpecificoQSN = this.lineaInterventoService.obiettivoSpecificoQSN(this.user, this.obiettivoGeneraleQSNSelected.id.toString()).subscribe(data => {
                  this.obiettiviSpecificoQSN = data;
                  this.loadedObiettivoSpecificoQSN = false;

                  this.obiettivoSpecificoQSNSelected = this.obiettiviSpecificoQSN.find(x => x.id == this.dataModificaLineaIntervento.idObiettivoSpecifQSN);
                }, err => {
                  this.handleExceptionService.handleNotBlockingError(err);
                  this.showMessageError("Errore in fase degli obiettivo specifico QSN");
                  this.loadedObiettivoSpecificoQSN = false;
                });
              }, err => {
                this.handleExceptionService.handleNotBlockingError(err);
                this.showMessageError("Errore in fase di caricamento degli obiettivo generale QSN");
                this.loadedObiettivoGeneraleQSN = false;
              });
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.showMessageError("Errore in fase di caricamento delle priorità QSN");
              this.loadedPrioritaQSN = false;
            });
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento degli asse");
          this.loadedAsse = false;
        });
      }

      this.processoSelected = this.processi.find(x => x.id == this.dataModificaLineaIntervento.idProcesso);
      this.catCIPESelected = this.catsCIPE.find(x => x.id == this.dataModificaLineaIntervento.idCategoriaCIPE);
      this.tipoCIPESelected = this.tipiCIPE.find(x => x.id == this.dataModificaLineaIntervento.idTipologiaCIPE);
      this.durProgDatConc = this.dataModificaLineaIntervento.mesiDurataDtConcessione;
      this.titoloBandoLinea = this.dataModificaLineaIntervento.nomeBandoLinea;
      this.flagArt58 = this.dataModificaLineaIntervento.flagArt58;

      if (!(this.processoLineeIntervento == 1)) {
        this.codRNA = this.dataModificaLineaIntervento.codAiutoRna;

        if (this.dataModificaLineaIntervento.flagSif == "S") {
          this.BandoSIFRadio = "1";
          this.showLivIst = true;
          this.showBandoSIF = false;
        } else {
          this.BandoSIFRadio = "2";
          this.showLivIst = false;
          this.showBandoSIF = true;
        }

        this.loadBandiSif();

        this.livsIst = new Array<Decodifica>();
        this.loadedLivelloIstruzione = true;
        this.subscribers.livelloIstruzione = this.lineaInterventoService.livelloIstruzione(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
          this.livsIst = data;

          this.livIstSelected = this.livsIst.find(x => x.id == this.dataModificaLineaIntervento.idLivelloIstituzione);
          if (this.livIstSelected == null) {
            this.showFondoFondi = false;
          } else {
            this.showFondoFondi = true;
          }
          this.loadedLivelloIstruzione = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento dei livello istituzione");
          this.loadedLivelloIstruzione = false;
        });

        this.obTemSelected.setValue(this.obsTem.find(x => x.id == this.dataModificaLineaIntervento.idObiettivoTem));
        this.obTemGroup.controls['obTemControl'].setValue(this.obTemSelected?.value);

        this.changeObTematico(true);

        this.tiposLoc = new Array<Decodifica>();
        this.loadedTipoLocalizzazione = true;
        this.subscribers.tipoLocalizzazione = this.lineaInterventoService.tipoLocalizzazione(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
          this.tiposLoc = data;
          this.loadedTipoLocalizzazione = false;

          if (!(this.dataModificaLineaIntervento.idTipoLocalizzazione == null)) {
            this.tipoLocSelected = this.tiposLoc.find(x => x.id == this.dataModificaLineaIntervento.idTipoLocalizzazione);
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento dei tipo localizzazione");
          this.loadedTipoLocalizzazione = false;
        });

        this.progsComplesso = new Array<Decodifica>();
        this.loadedProgettoComplesso = true;
        this.subscribers.progettoComplesso = this.lineaInterventoService.progettoComplesso(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
          this.progsComplesso = data;
          this.loadedProgettoComplesso = false;

          if (!(this.dataModificaLineaIntervento.idProgettoComplesso == null)) {
            this.progComplessoSelected = this.progsComplesso.find(x => x.id == this.dataModificaLineaIntervento.idProgettoComplesso);
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento dei progetto complesso");
          this.loadedProgettoComplesso = false;
        });

        this.classesMET = new Array<Decodifica>();
        this.loadedMet = true;
        this.subscribers.met = this.lineaInterventoService.met(this.user, this.normativaLinInSelected.idLinea.toString()).subscribe(data => {
          this.classesMET = data;
          this.loadedMet = false;

          if (!(this.dataModificaLineaIntervento.idClassificazioneMet == null)) {
            this.clasMETSelected = this.classesMET.find(x => x.id == this.dataModificaLineaIntervento.idClassificazioneMet);
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento dei met");
          this.loadedMet = false;
        });
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di modifica della linea intervento");
      this.loadedModificaLineaIntervento = false;
    });
  }

  getLineeAssociateTableData() {
    this.loadedGetLineeAssociate = true;
    this.subscribers.getLineeAssociate = this.lineaInterventoService.getLineeAssociate(this.user, this.params.get('idBando')).subscribe(data => {
      if (data == null) {
        this.showTableLineaAssociata = false;
      } else {
        this.showTableLineaAssociata = true;
        this.dataSourceLinInt = new MatTableDataSource(data);
      }
      this.loadedGetLineeAssociate = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di restituzione delle linee associate");
      this.loadedGetLineeAssociate = false;
    });
  }

  confermaModificheLineaAssociata() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.validazioneCampiLinInt();
    if (!this.hasValidationErrorLinInt) {
      var request;

      var comodoLineaIntervento;
      if (this.lineaSelected == null) {
        if (this.misuraSelected == null) {
          if (this.asseSelected == null) {
            comodoLineaIntervento = this.normativaLinInSelected.idLinea;
          } else {
            comodoLineaIntervento = this.asseSelected.idLinea;
          }
        } else {
          comodoLineaIntervento = this.misuraSelected.idLinea;
        }
      } else {
        comodoLineaIntervento = this.lineaSelected.idLinea;
      }

      if (this.processoLineeIntervento == 1) {
        request = new LineaInterventoDaModificareVo(Number(this.params.get('idBando')), this.normativaLinInSelected.idLinea, this.asseSelected == null ? null : this.asseSelected.idLinea, this.misuraSelected == null ? null : this.misuraSelected.idLinea, this.lineaSelected == null ? null : this.lineaSelected.idLinea,
          this.titoloBandoLinea, this.dataModificaLineaIntervento.idDefinizioneProcesso, this.prioritaQSNSelected.id, this.obiettivoGeneraleQSNSelected.id, this.obiettivoSpecificoQSNSelected.id,
          this.catCIPESelected.id, this.tipoCIPESelected.id, this.durProgDatConc, comodoLineaIntervento, this.dataModificaLineaIntervento.progrBandoLineaIntervento, this.processoSelected.id,
          this.dataModificaLineaIntervento.idUtenteIns, this.user.idUtente, this.dataModificaLineaIntervento.idAreaScientifica, this.dataModificaLineaIntervento.idUnitaOrganizzativa, this.dataModificaLineaIntervento.flagSchedin,
          this.dataModificaLineaIntervento.flagSif, this.dataModificaLineaIntervento.progrBandoLineaIntervSif, this.dataModificaLineaIntervento.nomeBandoLineaSif, this.dataModificaLineaIntervento.idTipoLocalizzazione, this.dataModificaLineaIntervento.idLivelloIstituzione,
          this.dataModificaLineaIntervento.idProgettoComplesso, this.dataModificaLineaIntervento.idClassificazioneMet, this.dataModificaLineaIntervento.flagFondoDiFondi, this.dataModificaLineaIntervento.idClassificazioneRa,
          this.dataModificaLineaIntervento.idObiettivoTem, this.dataModificaLineaIntervento.codAiutoRna, null);
      } else {
        request = new LineaInterventoDaModificareVo(Number(this.params.get('idBando')), this.normativaLinInSelected.idLinea, this.asseSelected == null ? null : this.asseSelected.idLinea, this.misuraSelected == null ? null : this.misuraSelected.idLinea, this.lineaSelected == null ? null : this.lineaSelected.idLinea,
          this.titoloBandoLinea, this.dataModificaLineaIntervento.idDefinizioneProcesso, this.dataModificaLineaIntervento.idPrioritaQSN, this.dataModificaLineaIntervento.idObiettivoGeneraleQSN, this.dataModificaLineaIntervento.idObiettivoSpecifQSN,
          this.catCIPESelected.id, this.tipoCIPESelected.id, this.durProgDatConc, comodoLineaIntervento, this.dataModificaLineaIntervento.progrBandoLineaIntervento, this.processoSelected.id,
          this.dataModificaLineaIntervento.idUtenteIns, this.user.idUtente, this.dataModificaLineaIntervento.idAreaScientifica, this.dataModificaLineaIntervento.idUnitaOrganizzativa, this.dataModificaLineaIntervento.flagSchedin,
          this.BandoSIFRadio == "1" ? "S" : "N", this.bandoSIFSelected == null ? null : this.bandoSIFSelected.progrBandoLineaIntervento, this.bandoSIFSelected == null ? null : this.bandoSIFSelected.nomeBandoLinea, this.tipoLocSelected == null ? null : this.tipoLocSelected.id, this.livIstSelected == null ? null : this.livIstSelected.id,
          this.progComplessoSelected == null ? null : this.progComplessoSelected.id, this.clasMETSelected == null ? null : this.clasMETSelected.id,
          this.FondoFondiRadio == "1" ? "S" : "N", this.classRASelected == null ? null : this.classRASelected?.value?.id,
          this.obTemSelected == null ? null : this.obTemSelected?.value?.id, this.codRNA, this.is2127 && this.BandoSIFRadio === "1" ? this.flagArt58 : null);
      }

      this.loadedSalvaModifiche = true;
      this.subscribers.salvaModifiche = this.lineaInterventoService.salvaModifiche(request).subscribe(data => {
        if (data) {
          if (data.code === "OK") {
            this.getLineeAssociateTableData();
            this.showFormModificaLineaIntervento = false;
            this.resetFormLineaIntervento();
            this.flagArt58 = "S"; //ripristino default
            this.showMessageSuccess("Salvataggio avvenuto con successo.");
          }
        }
        this.loadedSalvaModifiche = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di associazione della linea intervento");
        this.loadedSalvaModifiche = false;
        this.showFormModificaLineaIntervento = false;
      });
    }
  }

  configuraBandoLinea(idBandoLinea: string, nomeBandoLinea: string) {
    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/configuraBandoLinea?idLineaDiIntervento=` + this.normativaSelected.idLinea + `&idBandoLinea=` +
      idBandoLinea + `&nomeBandoLinea=` + nomeBandoLinea + `&titoloBando=` + this.titoloBando);
  }
  /* ------------------- Fine tab linea di intervento ------------------- */

  tornaBandi() {
    this.router.navigate([`drawer/` + this.idOperazione + `/configurazioneBando`]);
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

  isLoading() {
    if (this.loadedObiettivoTematico || this.loadedElencoBandiSif || this.loadedProcessoInterno || this.loadedSoggettiFinanziatori || this.loadedCausaleErogazione || this.loadedTipoTrattamento
      || this.loadedMacroVoceSpesa || this.loadedGetNormative || this.loadedSettoreCipe || this.loadedMateriaRiferimento || this.loadedCodiceIntesaIstituzionale || this.loadedTipoOperazione || this.loadedSettoreCPT
      || this.loadedNaturaCipe || this.loadedModalitaAgevolazione || this.loadedTipoIndicatore || this.loadedGetNormativePost2016 || this.loadedAsse || this.loadedCategoriaCipe || this.loadedTipologiaCipe || this.loadedTipoLocalizzazione
      || this.loadedProgettoComplesso || this.loadedMet || this.loadedMateriaRiferimento || this.loadedSottoSettoreCipe || this.loadedTipologiaAttivazione || this.loadedLivelloIstruzione
      || this.loadedMisura || this.loadedPrioritaQSN || this.loadedObiettivoGeneraleQSN || this.loadedLinea || this.loadedClassificazioneRA || this.loadedMicroVoceSpesa || this.loadedAssociaLineaIntervento
      || this.loadedEliminaLineaIntervento || this.loadedModificaLineaIntervento || this.loadedObiettivoSpecificoQSN || this.loadedGetLineeAssociate || this.loadedVociAssociate || this.loadedSalvaModifiche
      || this.loadedIndicatore || this.loadedModalitaAssociata || this.loadedTipoTrattamentoAssociato || this.loadedSoggettiFinanziatoreAssociato || this.loadedCausaleErogazioneAssociata
      || this.loadedIndicatoreAssociato || this.loadedAssociaModalitaAgevolazione || this.loadedEliminaModalitaAgevolazioneAssociata || this.loadedAssociaTipoTrattamento || this.loadedEliminaTipoTrattamentoAssociato || this.loadedTipoTrattamentoAssociatoPredefinito
      || this.loadedAssociaSoggettoFinanziatore || this.loadedEliminaSoggettoFinanziatore || this.loadedAssociaCausale || this.loadedEliminaCausaleErogazione || this.loadedAssociaIndicatore
      || this.loadedEliminaIndicatore || this.loadedEliminaVoceAssociata || this.loadedAssociaVoceAssociata || this.loadedSalvaDatiBando || this.loadedCercaDatiBando
      || !this.loadedIsSiffino) {
      return true;
    } else {
      return false;
    }
  }

  ngAfterViewChecked() {
    if (this.tabs) {
      this.tabs.realignInkBar();
    }
  }


  /*
  bandoIsEnteCompetenzaFinpiemonte(){

    
   
    this.loadedFlagFinpemonte = true
    this.configurazioneBandoLineaService.bandoIsEnteCompetenzaFinpiemonte(this.idBandoLinea).subscribe(
      data => {        
        console.log("flagFinpiemonte", data)
        this.flagAssociazioneDestinatarioFinpiemonte = data
      }, 
      error => {
        console.log("errore nella chiamata")
        let err = this.handleExceptionService.handleNotBlockingError(error);
        this.showMessageError(err.message);
        this.loadedFlagFinpemonte = false
      },//error
      () => { this.loadedFlagFinpemonte = false} // complete
    );

  }*/

  visualizzaInDialog(text: string) {
    this.dialog.open(VisualizzaTestoDialogComponent, {
      minWidth: "40%",
      data: {
        text: text
      }
    });
  }






}
