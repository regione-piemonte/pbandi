import { AfterContentChecked, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AllegatiDichiarazioneDiSpesaDTO } from 'src/app/rendicontazione/commons/dto/allegati-dichiarazione-di-spesa-dto';
import { DecodificaDTO } from 'src/app/rendicontazione/commons/dto/decodifica-dto';
import { DichiarazioneDiSpesaService } from 'src/app/rendicontazione/services/dichiarazione-di-spesa.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { RigaRicercaDocumentiDiSpesaDTO } from '../../commons/dto/riga-ricerca-documenti-di-spesa-dto';
import { CercaDocumentiDiSpesaValidazioneRequest } from '../../commons/requests/cerca-documenti-di-spesa-validazione-request';
import { ValidazioneService } from '../../services/validazione.service';
import { AllegatiDichiarazioneSpesaDialogComponent } from '../allegati-dichiarazione-spesa-dialog/allegati-dichiarazione-spesa-dialog.component';
import { ValidazioneMassivaDialogComponent } from '../validazione-massiva-dialog/validazione-massiva-dialog.component';
import { saveAs } from 'file-saver-es';
import { VerificaOperazioneMassivaRequest } from '../../commons/requests/verifica-operazione-massiva-request';
import { ConfermaWarningDialogComponent } from '../../../shared/conferma-warning-dialog/conferma-warning-dialog.component';
import { OperazioneMassivaRequest } from '../../commons/requests/operazione-massiva-request';
import { NavigationValidazioneService } from '../../services/navigation-validazione.service';
import { FiltroRicercaValidazione } from '../../commons/dto/filtro-ricerca-validazione';
import { DecodificaChecked } from '../../commons/dto/decodifica-checked';
import { ChiusuraValidazioneDialogComponent } from '../chiusura-validazione-dialog/chiusura-validazione-dialog.component';
import { ProseguiChiudiValidazioneRequest } from '../../commons/requests/prosegui-chiudi-validazione-request';
import { ArchivioFileService, DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { MensilitaDichiarazioneSpesaDTO } from '../../commons/dto/mensilita-dichiarazione-spesa-dto';
import { ValidaMensilitaRequest } from '../../commons/requests/valida-mensilita-request';
import { RichiestaIntegrazioneBoxComponent } from '../richiesta-integrazione-box/richiesta-integrazione-box.component';
import { RichiestaIntegrazioneButtonComponent } from '../richiesta-integrazione-button/richiesta-integrazione-button.component';

export enum Mesi {
  Gennaio, Febbraio, Marzo, Aprile, Maggio, Giugno, Luglio, Agosto, Settembre, Ottobre, Novembre, Dicembre
}

@Component({
  selector: 'app-validazione',
  templateUrl: './validazione.component.html',
  styleUrls: ['./validazione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ValidazioneComponent implements OnInit, AfterContentChecked {

  idOperazione: number;
  descBreveTask: string;
  idProgetto: number;
  idBandoLinea: number;
  idDichiarazioneDiSpesa: number;
  user: UserInfoSec;
  isAdgIstMaster: boolean;
  isOiIstMaster: boolean;
  isIstrAffidamenti: boolean;
  beneficiario: string;
  codiceProgetto: string;
  criteriRicercaOpened: boolean;
  isResultVisible: boolean;
  tipologieDoc: Array<DecodificaDTO>;
  tipologiaDocSelected: DecodificaDTO;
  tipologieFornitori: Array<DecodificaDTO>;
  tipologiaFornitoreSelected: DecodificaDTO;
  idComunicazFineProg: number;
  idDocumentoIndex: number;
  nomeFile: string;
  nomeFilePiuGreen: string;
  statoDichiarazione: string;
  protocollo: string;
  isProtocolloProvvisorio: boolean;
  esisteRichiestaIntegrazioneAperta: boolean;
  regolaInvioDigitale: string;
  descBreveEnte: string;
  flagFirmaCartacea: string;
  isFinale: boolean;
  documentoSpesaModificabile: boolean;
  validazioneMassivaAbilitata: boolean;
  richiestaIntegrazioneAbilitata: boolean;
  chiusuraValidazioneAbilitata: boolean;
  hasDocumenti: boolean;
  idComunicazFineProgPiuGreen: number;
  idDocumentoIndexPiuGreen: number;
  flagFirmaCartaceaPiuGreen: string;
  statoDichiarazionePiuGreen: string;
  protocolloPiuGreen: string;
  isProtocolloPiuGreenProvvisorio: boolean;
  taskVisible: boolean;
  tasks: Array<string>;
  taskSelected: string;
  numDocSpesa: string;
  dataDoc: FormControl = new FormControl();
  cfFornitore: string;
  pIvaFornitore: string;
  denomFornitore: string;
  cognomeFornitore: string;
  nomeFornitore: string;
  statiDocumenti: Array<DecodificaChecked>;
  isInvioExtraProcedura: boolean;
  isInvioExtraProceduraPiuGreen: boolean;
  isChiudiValidazioneEnabled: boolean;
  isChiudiValidazionePiuGreenEnabled: boolean;
  documentiSpesa: Array<RigaRicercaDocumentiDiSpesaDTO>;
  idDocumentiSpesaChecked: Array<number> = new Array<number>();
  allegatiDs: Array<AllegatiDichiarazioneDiSpesaDTO>;
  isBandoSif: boolean;
  sumImpErogPercettori: number;
  elencoMensilita: Array<MensilitaDichiarazioneSpesaDTO>;
  idStatoRichiesta: number;

  BR50: string = 'BR50';
  BR51: string = 'BR51';
  BR52: string = 'BR52';
  BR53: string = 'BR53';
  BR79: string = 'BR79';
  isBR50: boolean = false;
  isBR51: boolean = false;
  isBR52: boolean = false;
  isBR53: boolean = false;
  isBR79: boolean = false;

  // Costante SVILUPPO_FP (dev || pre prod)
  isVisible: boolean = false;

  // Costante Bandi FP
  isFP: boolean;

  // Dati Richiesta integrazione
  dtAutorizzata: String;
  dtAutorizzataTmp: String;
  dtNotifica: Date = null;
  lblRicevuta: string = "";
  dtScadenza: string;

  // Da trasmettere alla dialog Richiesta integrazione
  nDocuSpesaSospesi: number = 0;

  displayedColumns: string[];
  displayedColumnsNoTask: string[] = ['check', 'tipologia', 'data', 'num', 'fornitore', 'importo', 'validato', 'stato', 'azioni'];
  displayedColumnsTask: string[] = ['check', 'tipologia', 'data', 'num', 'fornitore', 'task', 'importo', 'validato', 'stato', 'azioni'];
  displayedColumnsBR79: string[] = ['check', 'tipologia', 'data', 'num', 'fornitore', 'importo', 'stato', 'azioni'];
  dataSource: DocumentiDatasource = new DocumentiDatasource([]);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  @ViewChild(RichiestaIntegrazioneBoxComponent) richiestaIntegrazioneBoxComponent: RichiestaIntegrazioneBoxComponent;
  @ViewChild(RichiestaIntegrazioneButtonComponent) richiestaIntegrazioneButtonComponent: RichiestaIntegrazioneButtonComponent;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedInizializza: boolean;
  loadedIsSif: boolean;
  loadedSumImpErog: boolean = true;
  loadedChiudiAttivita: boolean = true;
  loadedCerca: boolean = true;
  loadedDownload: boolean = true;
  loadedOperazione: boolean = true;
  loadedInizializzaPopupChiudiValidazione: boolean = true;
  loadedProsegui: boolean = true;
  loadedRegolaBR50: boolean = true;
  loadedRegolaBR51: boolean = true;
  loadedRegolaBR52: boolean = true;
  loadedRegolaBR53: boolean = true;
  loadedRegolaBR79: boolean = true;
  loadedInizializzaInt: boolean = true;
  prorogheDisponibili;
  prorogaDaElaborare;
  prorogheDisponibiliStorico;
  storicoProroghe;
  loadedMensilita: boolean = true;
  loadedSalvaMensilita: boolean = true;


  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dichiarazioneDiSpesaService: DichiarazioneDiSpesaService,
    private validazioneService: ValidazioneService,
    private archivioFileService: ArchivioFileService,
    private navigationService: NavigationValidazioneService,
    private dialog: MatDialog,
    private cdRef: ChangeDetectorRef,
    public datepipe: DatePipe
  ) { }

  ngAfterContentChecked(): void {
    this.cdRef.detectChanges();
  }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.idDichiarazioneDiSpesa = +params['idDichiarazioneDiSpesa'];
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER) {
            this.isAdgIstMaster = true;
          } else if (this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER) {
            this.isOiIstMaster = true;
          } else if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ISTR_AFFIDAMENTI) {
            this.isIstrAffidamenti = true;
          }
          this.inizializzaValidazione();
        }
      });
    });
  }

  inizializzaValidazione(fromChild?: boolean) {
    this.loadedInizializza = false;
    this.subscribers.inizializza = this.validazioneService.inizializzaValidazione(this.idDichiarazioneDiSpesa, this.idProgetto, this.idBandoLinea, this.user.codiceRuolo)
      .subscribe(data => {
        if (data) {
          this.codiceProgetto = data.codiceVisualizzatoProgetto;
          this.tipologieDoc = data.tipiDocumentoSpesa;
          this.tipologieFornitori = data.tipiFornitore;
          this.idDocumentoIndex = data.idDocumentoIndex;
          this.nomeFile = data.nomeFile;
          this.nomeFilePiuGreen = data.nomeFilePiuGreen;
          this.statiDocumenti = <Array<DecodificaChecked>>data.statiDocumentoSpesa;
          this.statiDocumenti.find(s => s.id === 0).checked = true;
          this.taskVisible = data.taskVisibile;
          if (this.taskVisible) {
            this.tasks = data.elencoTask;
            this.displayedColumns = this.displayedColumnsTask;
          } else if (this.isBR79) {
            this.displayedColumns = this.displayedColumnsBR79;
          } else {
            this.displayedColumns = this.displayedColumnsNoTask;
          }
          this.statoDichiarazione = data.statoDichiarazione ? data.statoDichiarazione.charAt(0).toUpperCase() + data.statoDichiarazione.slice(1) : null; //prima lettera della string uppercase
          this.protocollo = data.protocollo;
          this.descBreveEnte = data.descBreveEnte;
          this.esisteRichiestaIntegrazioneAperta = data.esisteRichiestaIntegrazioneAperta;
          /*
          SPOSTATO PER CONTROLLARE LE COSTANTI
          if (this.esisteRichiestaIntegrazioneAperta) {
            this.showMessageWarning(msgRichiestaIntegrazione);
            this.dtAutorizzata = data.dateInvio[0];
          }*/
          this.dtAutorizzataTmp = data.dateInvio[0];
          this.isFinale = data.dichiarazioneDiSpesaFinale;
          this.regolaInvioDigitale = data.regolaInvioDigitale;
          this.idDocumentoIndexPiuGreen = data.idDocumentoIndexPiuGreen;
          this.flagFirmaCartaceaPiuGreen = data.flagFirmaCartaceaPiuGreen;
          this.statoDichiarazionePiuGreen = data.statoDichiarazionePiuGreen ? data.statoDichiarazionePiuGreen.charAt(0).toUpperCase() + data.statoDichiarazionePiuGreen.slice(1) : null; //prima lettera della string uppercase
          this.protocolloPiuGreen = data.protocolloPiuGreen;
          this.flagFirmaCartacea = data.flagFirmaCartacea;
          if (this.regolaInvioDigitale === 'S' && this.flagFirmaCartacea === 'S') {
            this.isInvioExtraProcedura = true;
          }
          if (this.idDocumentoIndexPiuGreen) {
            if (this.regolaInvioDigitale === 'S' && this.flagFirmaCartaceaPiuGreen === 'S') {
              this.isInvioExtraProceduraPiuGreen = true;
            }
          }
          this.validazioneMassivaAbilitata = data.validazioneMassivaAbilitata;
          this.richiestaIntegrazioneAbilitata = data.richiestaIntegrazioneAbilitata;
          this.chiusuraValidazioneAbilitata = data.chiusuraValidazioneAbilitata;
          this.documentoSpesaModificabile = data.documentoSpesaModificabile;
          this.idComunicazFineProg = data.idComunicazFineProg;
          this.idComunicazFineProgPiuGreen = data.idComunicazFineProgPiuGreen;
          this.loadData();
          if (this.navigationService.filtroRicercaSelezionato) {
            if (this.navigationService.filtroRicercaSelezionato.tipologiaDocSelected) {
              this.tipologiaDocSelected = this.tipologieDoc.find(t => t.id === this.navigationService.filtroRicercaSelezionato.tipologiaDocSelected.id);
            }
            this.numDocSpesa = this.navigationService.filtroRicercaSelezionato.numDocSpesa;
            this.dataDoc = this.navigationService.filtroRicercaSelezionato.dataDoc;
            if (this.navigationService.filtroRicercaSelezionato.tipologiaFornitoreSelected) {
              this.tipologiaFornitoreSelected = this.tipologieFornitori.find(t => t.id === this.navigationService.filtroRicercaSelezionato.tipologiaFornitoreSelected.id);
            }
            this.cfFornitore = this.navigationService.filtroRicercaSelezionato.cfFornitore;
            this.taskSelected = this.navigationService.filtroRicercaSelezionato.taskSelected;
            this.pIvaFornitore = this.navigationService.filtroRicercaSelezionato.pIvaFornitore;
            this.denomFornitore = this.navigationService.filtroRicercaSelezionato.denomFornitore;
            this.cognomeFornitore = this.navigationService.filtroRicercaSelezionato.cognomeFornitore;
            this.nomeFornitore = this.navigationService.filtroRicercaSelezionato.nomeFornitore;
            this.statiDocumenti = this.navigationService.filtroRicercaSelezionato.statiDocumentiSelected;
            this.ricerca();
          } else {
            this.ricerca(true);
          }
          if (this.activatedRoute.snapshot.paramMap.get('message')) {
            this.showMessageSuccess(this.activatedRoute.snapshot.paramMap.get('message'));
          }
          if (fromChild) {
            this.richiestaIntegrazioneBoxComponent.once = false;
            this.richiestaIntegrazioneBoxComponent.initIntegrazione();
          }
        }
        this.loadedInizializza = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
  }

  loadData() {
    if (this.isVisible || this.descBreveEnte !== 'FIN') {
      this.subscribers.allegatiDichiarazione = this.dichiarazioneDiSpesaService.getAllegatiDichiarazioneDiSpesaIntegrazioni(this.idProgetto, this.idDichiarazioneDiSpesa).subscribe(data => {
        if (data) {
          this.allegatiDs = data;
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
      });
    } else {
      this.subscribers.allegatiDichiarazione = this.dichiarazioneDiSpesaService.getAllegatiDichiarazioneDiSpesa(this.idProgetto, this.idDichiarazioneDiSpesa).subscribe(data => {
        if (data) {
          this.allegatiDs = data;
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
      });
    }
    this.loadedIsSif = false;
    this.userService.isBandoSif(this.idBandoLinea).subscribe(data1 => {
      this.isBandoSif = data1;
      if (this.isBandoSif) {
        this.loadedSumImpErog = false;
        this.validazioneService.getSumImportoErogProgettiPercettori(this.idBandoLinea, this.idDichiarazioneDiSpesa).subscribe(data2 => {
          this.sumImpErogPercettori = data2;
          this.loadedSumImpErog = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di calcolo della somma degli importi erogazione dei percettori.");
          this.loadedSumImpErog = true;
        });
      }
      this.loadedIsSif = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica bando SIF.");
      this.loadedIsSif = true;
    });
    this.regole();
  }

  regole() {
    //Servizio per regola br50
    this.loadedRegolaBR50 = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, this.BR50).subscribe(res => {
      if (res != undefined && res != null) {
        this.isBR50 = res;
      }
      this.loadedRegolaBR50 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegolaBR50 = true;
    });

    //Servizio per regola br51
    this.loadedRegolaBR51 = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, this.BR51).subscribe(res => {
      if (res != undefined && res != null) {
        this.isBR51 = res;
      }
      this.loadedRegolaBR51 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegolaBR51 = true;
    });

    //Servizio per regola br52
    this.loadedRegolaBR52 = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, this.BR52).subscribe(res => {
      if (res != undefined && res != null) {
        this.isBR52 = res;
      }
      this.loadedRegolaBR52 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegolaBR52 = true;
    });

    //Servizio per regola br53
    this.loadedRegolaBR53 = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, this.BR53).subscribe(res => {
      if (res != undefined && res != null) {
        this.isBR53 = res;
      }
      this.loadedRegolaBR53 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegolaBR53 = true;
    });
    this.loadedRegolaBR79 = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, this.BR79).subscribe(res => {
      this.isBR79 = res;
      if (this.isBR79) {
        this.displayedColumns = this.displayedColumnsBR79;
        this.loadMensilita();
      }
      this.loadedRegolaBR79 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegolaBR79 = true;
    });

    this.subscribers.costanteFin = this.userService.isCostanteFinpiemonte().subscribe(data => {
      this.isVisible = data;
      this.subscribers.bandoFin = this.userService.isBandoCompetenzaFinpiemonte(this.idBandoLinea).subscribe(data => {
        this.isFP = data;
      });
    })
  }

  setIdStatoRichiesta(id: number) {
    this.idStatoRichiesta = id;
  }

  get mesi() {
    return Mesi;
  }

  loadMensilita() {
    this.loadedMensilita = false;
    this.validazioneService.recuperaMensilitaDichiarazioneSpesa(this.idDichiarazioneDiSpesa).subscribe(data => {
      this.elencoMensilita = data;
      if (this.elencoMensilita?.length > 0) {
        for (let mensilita of this.elencoMensilita) {
          if (!mensilita.esitoValidMesi && mensilita.sabbatico !== "S") {
            mensilita.esitoValidMesi = "OK"
          };
        }
      }
      this.loadedMensilita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle mensilità.");
      this.loadedMensilita = true;
    });
  }

  get hasIconsMensilita() {
    return this.elencoMensilita?.find(m => m.sabbatico === "S" || m.idDichMeseRipetuto);
  }

  get isSalvaMensilitaVisible() {
    return this.elencoMensilita?.find(m => m.esitoValidMesi);
  }



  areTutteNoteObbligatorieCompilate(): boolean {
    return this.elencoMensilita.every(mensilita => {
      return mensilita.esitoValidMesi !== 'KO' && mensilita.esitoValidMesi !== 'NV' || !!mensilita.note;
    });
  }



  salvaMensilita(isChiudiValidazione?: boolean) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedSalvaMensilita = false;
    let request = new Array<ValidaMensilitaRequest>();
    for (let mensilita of this.elencoMensilita) {
      let r: ValidaMensilitaRequest = {
        idPrg: mensilita.idPrg,
        mese: mensilita.mese,
        anno: mensilita.anno,
        idDichSpesa: mensilita.idDichSpesa,
        esitoValidMesi: mensilita.esitoValidMesi,
        note: mensilita.note
      }
      request.push(r);
    }
    this.validazioneService.validaMensilita(request).subscribe(data => {
      if (data) {
        if (data.esito) {
          if (isChiudiValidazione) {
            this.chiudiValidazione();
          } else {
            this.showMessageSuccess("Esito per mensilità rendicontate salvato con successo.");
          }
        } else {
          this.showMessageError("Errore in fase di salvataggio dell'esito per mensilità rendicontate.");
        }
      }
      this.loadedSalvaMensilita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio dell'esito per mensilità rendicontate.");
      this.loadedSalvaMensilita = true;
    });
  }

  generaProtocollo() {
    this.protocollo = "temp_NP" + this.idDocumentoIndex;
    this.isProtocolloProvvisorio = true;
  }

  annullaProtocollo() {
    this.protocollo = null;
    this.isProtocolloProvvisorio = false;
  }

  salvaProtocollo() {
    this.loadedCerca = false;
    this.subscribers.salvaProtocollo = this.validazioneService.salvaProtocollo(this.idDocumentoIndex, this.protocollo).subscribe(data => {
      if (data) {
        this.showMessageSuccess("N. protocollo salvato con successo.");
        this.isProtocolloProvvisorio = false;
      } else {
        this.showMessageError("Errore in fase di salvataggio del n. protocollo");
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio del n. protocollo");
      this.loadedCerca = true;
    });
  }

  generaProtocolloPiuGreen() {
    this.protocolloPiuGreen = "temp_NP" + this.idDocumentoIndexPiuGreen;
    this.isProtocolloPiuGreenProvvisorio = true;
  }

  annullaProtocolloPiuGreen() {
    this.protocolloPiuGreen = null;
    this.isProtocolloPiuGreenProvvisorio = false;
  }

  salvaProtocolloPiuGreen() {
    this.loadedCerca = false;
    this.subscribers.salvaProtocolloPiuGreen = this.validazioneService.salvaProtocollo(this.idDocumentoIndexPiuGreen, this.protocolloPiuGreen).subscribe(data => {
      if (data) {
        this.showMessageSuccess("N. protocollo salvato con successo.");
        this.isProtocolloPiuGreenProvvisorio = false;
      } else {
        this.showMessageError("Errore in fase di salvataggio del n. protocollo");
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio del n. protocollo");
      this.loadedCerca = true;
    });
  }

  downloadPdfDichiarazione() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.archivioFileService.leggiFile(this.configService.getApiURL(), this.idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, this.nomeFile);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  downloadPdfDichiarazionePiuGreen() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.archivioFileService.leggiFile(this.configService.getApiURL(), this.idDocumentoIndexPiuGreen.toString()).subscribe(res => {
      if (res) {
        saveAs(res, this.nomeFilePiuGreen);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  changeAllCheckbox(event: MatCheckboxChange) {
    if (event.checked) {
      this.idDocumentiSpesaChecked = this.documentiSpesa.map(d => d.idDocumentoDiSpesa);
      this.documentiSpesa.forEach(d => {
        if (d.descrizioneStatoDocumentoSpesa !== "SOSPESO") {
          d.checked = true;
        }
      });
    } else {
      this.idDocumentiSpesaChecked = new Array<number>();
      this.documentiSpesa.forEach(d => d.checked = false);
    }
  }

  changeCheckbox(event: MatCheckboxChange, idDocumentoDiSpesa: number) {
    if (event.checked) {
      this.idDocumentiSpesaChecked.push(idDocumentoDiSpesa);
    } else {
      this.idDocumentiSpesaChecked = this.idDocumentiSpesaChecked.filter(id => id !== idDocumentoDiSpesa);
    }
  }

  ricerca(isInit?: boolean) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedCerca = false;
    let stati = this.statiDocumenti.filter(s => s.checked);
    let s: Array<string> = null;
    if (stati && stati.length > 0) {
      if (!stati.find(s => s.id === 0)) {
        s = stati.map(s => s.id.toString());
      }
    } else {
      this.statiDocumenti.find(s => s.id === 0).checked = true;
    }
    this.hasDocumenti = this.navigationService.hasDoc;
    let request = new CercaDocumentiDiSpesaValidazioneRequest(this.idDichiarazioneDiSpesa, this.tipologiaDocSelected ? this.tipologiaDocSelected.id : null,
      this.tipologiaFornitoreSelected ? this.tipologiaFornitoreSelected.id : null, this.dataDoc.value ? new Date(this.dataDoc.value) : null, this.numDocSpesa && this.numDocSpesa.length ? this.numDocSpesa : null,
      this.cognomeFornitore && this.cognomeFornitore.length ? this.cognomeFornitore.toUpperCase() : null, this.nomeFornitore && this.nomeFornitore.length ? this.nomeFornitore.toUpperCase() : null,
      this.cfFornitore && this.cfFornitore.length ? this.cfFornitore : null, this.pIvaFornitore && this.pIvaFornitore.length ? this.pIvaFornitore : null,
      this.denomFornitore && this.denomFornitore.length ? this.denomFornitore.toUpperCase() : null, this.taskSelected, s);
    this.subscribers.ricerca = this.validazioneService.cercaDocumentiDiSpesaValidazione(request).subscribe(data => {
      if (data && data.length > 0) {
        this.documentiSpesa = data;
        this.dataSource = new DocumentiDatasource(this.documentiSpesa);
        if (this.navigationService.filtroRicercaSelezionato) {
          this.ripristinaSortPaginator();
        } else {
          this.paginator.length = this.documentiSpesa.length;
          this.paginator.pageIndex = 0;
          this.dataSource.paginator = this.paginator;
          this.setSort();
          this.dataSource.sort = this.sort;
        }
        if (isInit) {
          this.hasDocumenti = true;
        }

        if (this.isVisible || !this.isFP) {
          this.nDocuSpesaSospesi = 0;
          // Conto il num di documenti in stato sospeso
          this.documentiSpesa.forEach(element => {
            if (element.descrizioneStatoDocumentoSpesa == "SOSPESO") { this.nDocuSpesaSospesi += 1 }
          });
        }
      } else {
        this.documentiSpesa = null;
        this.dataSource = null;
        if (isInit) {
          this.hasDocumenti = false;
        }
      }
      this.navigationService.hasDoc = this.hasDocumenti;
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err);
      this.loadedCerca = true;
    });
    this.criteriRicercaOpened = false;
    this.isResultVisible = true;

  }

  visualizzaAllegati() {
    this.dialog.open(AllegatiDichiarazioneSpesaDialogComponent,
      {
        width: '50%',
        maxHeight: '90%',
        data: {
          allegati: this.allegatiDs,
          title: this.isFinale ? "Allegati alla dichiarazione di spesa finale con comunicazione di fine progetto" : "Allegati alla dichiarazione di spesa"
        }
      });
  }

  abilitaDisabilitaChiusuraValidazione() {
    this.isChiudiValidazioneEnabled = !this.isChiudiValidazioneEnabled;
  }

  abilitaDisabilitaChiusuraValidazionePiuGreen() {
    this.isChiudiValidazionePiuGreenEnabled = !this.isChiudiValidazionePiuGreenEnabled;
  }

  anteprimaReportDettaglio() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.validazioneService.reportDettaglioDocumentoDiSpesa(this.idDichiarazioneDiSpesa).subscribe(res => {
      //console.log(res.headers.get("header-nome-file"));
      //console.log(res.body.type + "  -  " + res.body.size);
      let nomeFile = res.headers.get("header-nome-file");
      saveAs(new Blob([res.body]), nomeFile);
      /*if (res) {
        saveAs(res, "reportValidazione" + this.idDichiarazioneDiSpesa + ".xls");
      }*/
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  eseguiValidazioneMassiva() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let dialogRef = this.dialog.open(ValidazioneMassivaDialogComponent, {
      width: '60%',
      data: {
        documentiSelezionati: this.idDocumentiSpesaChecked
      }
    });
    dialogRef.afterClosed().subscribe(res1 => {
      if (res1) {
        this.loadedCerca = false;
        let requestVerifica = new VerificaOperazioneMassivaRequest(res1.operazione, res1.isApplicaATuttoChecked, res1.isApplicaATuttoChecked ? null : this.idDocumentiSpesaChecked,
          this.idDichiarazioneDiSpesa, this.idBandoLinea);
        this.subscribers.verifica = this.validazioneService.verificaOperazioneMassiva(requestVerifica).subscribe(data1 => {
          if (data1) {
            if (data1.esito) {
              let dialogRef2 = this.dialog.open(ConfermaWarningDialogComponent, {
                width: '60%',
                data: {
                  title: "Validazione massiva documenti di spesa",
                  message: data1.messaggio,
                  messageWarning: data1.messaggioImportoAmmissibileSuperato
                }
              });

              dialogRef2.afterClosed().subscribe(res2 => {
                if (res2) {
                  this.loadedOperazione = false;
                  let requestOperazioneMassiva = new OperazioneMassivaRequest(res1.operazione, data1.idDocumenti, this.idDichiarazioneDiSpesa);
                  this.subscribers.operazioneMassiva = this.validazioneService.operazioneMassiva(requestOperazioneMassiva).subscribe(data2 => {
                    if (data2) {
                      if (data2.esito) {
                        this.ricerca();
                        this.showMessageSuccess(data2.messaggio);
                      } else {
                        this.showMessageError(data2.messaggio);
                      }
                    }
                    this.loadedOperazione = true;
                  }, err => {
                    this.handleExceptionService.handleNotBlockingError(err);
                    this.showMessageError("Errore nell'esecuzione dell'operazione.");
                    this.loadedOperazione = true;
                  });
                }
              });
            } else {
              this.showMessageError(data1.messaggio);
            }
          }
          this.loadedCerca = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di verifica.");
          this.loadedCerca = true;
        });
      }
    });
  }

  isChiudiDisabled() {
    if (this.esisteRichiestaIntegrazioneAperta && !this.isBR79) {
      //  console.log(this.esisteRichiestaIntegrazioneAperta);

      return true;
    }
    if (this.regolaInvioDigitale === 'N'
      || (!this.isInvioExtraProcedura && this.flagFirmaCartacea === "S")
      || (!this.isChiudiValidazioneEnabled && !this.isInvioExtraProcedura && this.chiusuraValidazioneAbilitata && this.statoDichiarazione !== 'Firmata')
      || (this.flagFirmaCartacea !== 'S' && this.idDocumentoIndex && this.statoDichiarazione === 'Firmata' && !(this.protocollo && this.protocollo.length > 0))
      || (this.flagFirmaCartacea !== 'S' && this.idDocumentoIndex && this.statoDichiarazione === 'Firmata' && this.protocollo && this.isProtocolloProvvisorio)) {
      return true;
    }
    if (!this.isInvioExtraProceduraPiuGreen && this.chiusuraValidazioneAbilitata && this.statoDichiarazionePiuGreen !== 'Firmata' && this.regolaInvioDigitale === 'S'
      && this.isChiudiValidazionePiuGreenEnabled) {
      //se il pulsante abilita chiusura validazione per il +green è presente ed è stato cliccato, il chiudi validazione deve essere abilitato
      return false;
    }
    if ((this.flagFirmaCartaceaPiuGreen !== 'S' && this.idDocumentoIndexPiuGreen && !(this.protocolloPiuGreen && this.protocolloPiuGreen.length > 0))
      || (this.flagFirmaCartaceaPiuGreen !== 'S' && this.idDocumentoIndexPiuGreen && this.statoDichiarazionePiuGreen !== 'Firmata')
      || (this.flagFirmaCartaceaPiuGreen !== 'S' && this.idDocumentoIndexPiuGreen && this.statoDichiarazionePiuGreen === 'Firmata' && !this.protocolloPiuGreen)
      || (this.flagFirmaCartaceaPiuGreen !== 'S' && this.idDocumentoIndexPiuGreen && this.statoDichiarazionePiuGreen === 'Firmata' && this.protocolloPiuGreen
        && this.isProtocolloPiuGreenProvvisorio)) {
      return true;
    }
    return false;
  }

  chiudiValidazioneCheckMensilita() {
    if (this.isBR79 && this.elencoMensilita?.length > 0) {
      this.salvaMensilita(true);
    } else {
      this.chiudiValidazione();
    }
  }

  isChiudiDisabledNew() {
    if (this.regolaInvioDigitale === 'N'
      || (!this.isInvioExtraProcedura && this.flagFirmaCartacea === "S")
      || (!this.isChiudiValidazioneEnabled && !this.isInvioExtraProcedura && this.chiusuraValidazioneAbilitata && this.statoDichiarazione !== 'Firmata')
      || (this.flagFirmaCartacea !== 'S' && this.idDocumentoIndex && this.statoDichiarazione === 'Firmata' && !(this.protocollo && this.protocollo.length > 0))
      || (this.flagFirmaCartacea !== 'S' && this.idDocumentoIndex && this.statoDichiarazione === 'Firmata' && this.protocollo && this.isProtocolloProvvisorio)) {
      return true;
    }
    if (!this.isInvioExtraProceduraPiuGreen && this.chiusuraValidazioneAbilitata && this.statoDichiarazionePiuGreen !== 'Firmata' && this.regolaInvioDigitale === 'S'
      && this.isChiudiValidazioneEnabled) {
      //se il pulsante abilita chiusura validazione per il +green è presente ed è stato cliccato, il chiudi validazione deve essere abilitato
      return false;
    }
    if ((this.flagFirmaCartaceaPiuGreen !== 'S' && this.idDocumentoIndexPiuGreen && !(this.protocolloPiuGreen && this.protocolloPiuGreen.length > 0))
      || (this.flagFirmaCartaceaPiuGreen !== 'S' && this.idDocumentoIndexPiuGreen && this.statoDichiarazionePiuGreen !== 'Firmata')
      || (this.flagFirmaCartaceaPiuGreen !== 'S' && this.idDocumentoIndexPiuGreen && this.statoDichiarazionePiuGreen === 'Firmata' && !this.protocolloPiuGreen)
      || (this.flagFirmaCartaceaPiuGreen !== 'S' && this.idDocumentoIndexPiuGreen && this.statoDichiarazionePiuGreen === 'Firmata' && this.protocolloPiuGreen
        && this.isProtocolloPiuGreenProvvisorio)) {
      return true;
    }
    return false;
  }


  chiudiValidazione() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.nDocuSpesaSospesi > 0) {
      this.showMessageError("Non è possibile chiudere la validazione in presenza di documenti di spesa in stato sospeso.");
      return;
    }
    this.loadedCerca = false;
    this.subscribers.chiudi = this.validazioneService.chiudiValidazione(this.idDichiarazioneDiSpesa, this.idDocumentoIndex, this.idBandoLinea,
      this.isInvioExtraProcedura ? "true" : "false").subscribe(data1 => {
        if (data1) {
          if (data1.esito) {
            this.loadedInizializzaPopupChiudiValidazione = false;

            //metodo che non so cosa faccia
            this.subscribers.inizializzaPopupChiudiValidazione = this.validazioneService.inizializzaPopupChiudiValidazione(this.idDichiarazioneDiSpesa, this.idProgetto,
              this.idBandoLinea).subscribe(data2 => {
                if (data2) {

                  //apertura dialog per inserimento note
                  let dialogRef = this.dialog.open(ChiusuraValidazioneDialogComponent, {
                    width: '60%',
                    maxHeight: '90%',
                    disableClose: true,
                    data: {
                      dataInit: data2
                    }
                  });

                  //chiusura dialog inserimento note
                  dialogRef.afterClosed().subscribe(res => {
                    if (res) {
                      this.loadedProsegui = false;

                      //prosegui chiudi validazione (?)
                      this.subscribers.prosegui = this.validazioneService.proseguiChiudiValidazione(new ProseguiChiudiValidazioneRequest(res.noteChiusura, res.dsIntegrativaConsentita,
                        res.idAppaltiSelezionati, this.idProgetto, this.idDichiarazioneDiSpesa)).subscribe(data3 => {
                          if (data3) {

                            //se chiamata ha avuto esito positivo
                            if (data3.esito) {

                              //vado alla checlist
                              this.goToChecklist(res.dsIntegrativaConsentita);

                            } else {
                              this.showMessageError(data3.messaggio);
                            }
                          }
                          this.loadedProsegui = true;
                        }, err => {
                          this.handleExceptionService.handleNotBlockingError(err);
                          this.showMessageError("Errore in fase di chiusura validazione.");
                          this.loadedProsegui = true;
                        });
                    }
                  });
                }
                this.loadedInizializzaPopupChiudiValidazione = true;
              }, err => {
                this.handleExceptionService.handleNotBlockingError(err);
                this.showMessageError("Errore in fase di recupero dati per chiusura validazione.");
                this.loadedInizializzaPopupChiudiValidazione = true;
              });
          } else {
            this.showMessageError(data1.messaggio);
          }
        }
        this.loadedCerca = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedCerca = true;
        this.showMessageError("Errore in fase di chiusura validazione.");
      });
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto,
      this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VALIDAZIONE ? Constants.DESCR_BREVE_TASK_VALIDAZIONE : Constants.DESCR_BREVE_TASK_VALIDAZIONE_INTEGRATIVA).subscribe(data => {
        window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedChiudiAttivita = true;
        this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
        this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
      });
  }

  goToModificaDocumento(idDocumento: number) {
    this.saveDataForNavigation();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE + "/documentoDiSpesa", this.idProgetto, this.idBandoLinea, idDocumento,
    { from: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_VALIDAZIONE, idOperazioneValidazione: this.idOperazione, idDichiarazioneDiSpesa: this.idDichiarazioneDiSpesa }]);
  }

  goToEsameDocumento(idDocumento: number) {
    this.saveDataForNavigation();
    this.router.navigate(["drawer/" + this.idOperazione + "/esameDocumento", this.idProgetto, this.idBandoLinea, this.idDichiarazioneDiSpesa,
      idDocumento]);
  }

  goToChecklist(dsIntegrativaConsentita: boolean) {
    this.saveDataForNavigation();
    let url = `drawer/${this.idOperazione}/checklistChiudiValidazione/${this.idProgetto}/${this.idBandoLinea}/${this.idDichiarazioneDiSpesa}`;
    url += dsIntegrativaConsentita ? ';dsIntegrativaConsentita=true' : '';
    this.router.navigateByUrl(url);
  }


  datiProgettoEAttivitaPregresse() {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  contoEconomico() {
    this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
      width: "90%",
      maxHeight: '90%',
      data: {
        idBandoLinea: this.idBandoLinea,
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
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

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  saveDataForNavigation() {
    this.navigationService.filtroRicercaSelezionato = new FiltroRicercaValidazione(this.tipologiaDocSelected, this.numDocSpesa, this.dataDoc, this.tipologiaFornitoreSelected,
      this.cfFornitore, this.taskSelected, this.pIvaFornitore, this.denomFornitore, this.cognomeFornitore, this.nomeFornitore, this.statiDocumenti);
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

  setSort() {
    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case "tipologia":
          return item.descrizioneTipologiaDocumento;
        case "data":
          if (item.dataDocumento) {
            let dt = this.sharedService.parseDate(item.dataDocumento);
            if (dt) {
              return dt.getTime();
            }
          }
          return "";
        case "num":
          return item.numeroDocumento;
        case "fornitore":
          return item.denominazioneFornitore;
        case "task":
          return item.task;
        case "importo":
          return item.importoTotaleDocumento;
        case "validato":
          return item.importoTotaleValidato;
        case "stato":
          return item.descrizioneStatoDocumentoSpesa;
        default: return item[property];
      }
    };
  }

  isLoading() {
    if (!this.loadedChiudiAttivita || !this.loadedInizializza || !this.loadedCerca
      || !this.loadedDownload || !this.loadedOperazione || !this.loadedInizializzaPopupChiudiValidazione
      || !this.loadedProsegui || !this.loadedRegolaBR50 || !this.loadedRegolaBR51 || !this.loadedRegolaBR52
      || !this.loadedRegolaBR53 || !this.loadedInizializzaInt || !this.loadedIsSif || !this.loadedSumImpErog
      || !this.loadedRegolaBR79 || !this.loadedMensilita || !this.loadedSalvaMensilita
      || (this.richiestaIntegrazioneBoxComponent && this.richiestaIntegrazioneBoxComponent.isLoading())
      || (this.richiestaIntegrazioneButtonComponent && this.richiestaIntegrazioneButtonComponent.isLoading())) {
      return true;
    }
    return false;
  }
}

export class DocumentiDatasource extends MatTableDataSource<RigaRicercaDocumentiDiSpesaDTO> {
  constructor(data: RigaRicercaDocumentiDiSpesaDTO[]) {
    super(data);
  }
}
