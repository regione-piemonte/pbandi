import { AfterContentChecked, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { RendicontazioneService } from 'src/app/rendicontazione/services/rendicontazione.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { CodiceDescrizioneDTO } from '../../commons/dto/codice-descrizione-dto';
import { GestioneSpesaValidataService } from '../../services/gestione-spesa-validata.service';
import { saveAs } from 'file-saver-es';
import { CercaDocumentiSpesaValidataRequest } from '../../commons/requests/cerca-documenti-spesa-validata-request';
import { DocumentoDiSpesa } from 'src/app/rendicontazione/commons/dto/documento-di-spesa';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { DichiarazioniDialogComponent } from '../dichiarazioni-dialog/dichiarazioni-dialog.component';
import { NavigationGestioneSpesaValidataService } from '../../services/navigation-gestione-spesa-validata.service';
import { FiltroRicercaSpesaValidata } from '../../commons/dto/filtro-ricerca-spesa-validata';
import { DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { MensilitaProgettoDTO } from '../../commons/dto/mensilita-progetto-dto';
import { DichiarazioneSpesaValidataComboDTO } from '../../commons/dto/dichiarazione-spesa-validata-combo-dto';
import { AllegatiDichiarazioneSpesaDialogComponent } from 'src/app/validazione/components/allegati-dichiarazione-spesa-dialog/allegati-dichiarazione-spesa-dialog.component';
import { DichiarazioneDiSpesaService } from 'src/app/rendicontazione/services/dichiarazione-di-spesa.service';
import { AllegatiDichiarazioneDiSpesaDTO } from 'src/app/rendicontazione/commons/dto/allegati-dichiarazione-di-spesa-dto';
import { RichiestaIntegrazioneBoxComponent } from 'src/app/validazione/components/richiesta-integrazione-box/richiesta-integrazione-box.component';
import { RichiestaIntegrazioneButtonComponent } from 'src/app/validazione/components/richiesta-integrazione-button/richiesta-integrazione-button.component';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { ConfermaWarningDialogComponent } from 'src/app/shared/conferma-warning-dialog/conferma-warning-dialog.component';
import { RilievoDialogComponent } from '../rilievo-dialog/rilievo-dialog.component';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';

export enum Mesi {
  Gennaio, Febbraio, Marzo, Aprile, Maggio, Giugno, Luglio, Agosto, Settembre, Ottobre, Novembre, Dicembre
}

@Component({
  selector: 'app-gestione-spesa-validata',
  templateUrl: './gestione-spesa-validata.component.html',
  styleUrls: ['./gestione-spesa-validata.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneSpesaValidataComponent implements OnInit, AfterContentChecked {

  idProgetto: number;
  idBandoLinea: number;
  user: UserInfoSec;
  beneficiario: string;
  codiceProgetto: string;
  isRagioneriaDelegata: boolean;
  taskVisibile: boolean;
  elencoTask: Array<string>;
  taskSelected: string;
  criteriRicercaOpened: boolean = true;
  isResultVisible: boolean;
  dichiarazioniDiSpesa: Array<DichiarazioneSpesaValidataComboDTO>;
  dichiarazioneDiSpesaSelected: DichiarazioneSpesaValidataComboDTO;
  vociDiSpesa: Array<CodiceDescrizioneDTO>;
  voceDiSpesaSelected: CodiceDescrizioneDTO;
  tipologieDoc: Array<CodiceDescrizioneDTO>;
  tipologiaDocSelected: CodiceDescrizioneDTO;
  tipologieFornitori: Array<CodiceDescrizioneDTO>;
  tipologiaFornitoreSelected: CodiceDescrizioneDTO;
  esistePropostaCertificazione: boolean;
  numDocSpesa: string;
  dataDoc: FormControl = new FormControl();
  cfFornitore: string;
  pIvaFornitore: string;
  denomFornitore: string;
  cognomeFornitore: string;
  nomeFornitore: string;
  documenti: string = 'F';
  docSpesa: Array<DocumentoDiSpesa>;
  elencoMensilita: Array<MensilitaProgettoDTO>;
  allegatiDs: Array<AllegatiDichiarazioneDiSpesaDTO>;
  BR79: string = "BR79";
  BR63: string = "BR63";
  isBR79: boolean;
  isBR63: boolean;
  richiestaIntegrazioneAbilitata: boolean;
  idStatoRichiesta: number;
  nDocuSpesaSospesi: number = 0;
  isNullaDaRilevareChecked: boolean;
  hasDocSpesaConRilievo: boolean;

  displayedColumns: string[];
  displayedColumnsNoTask: string[] = ['tipologia', 'num', 'data', 'fornitore', 'stato', 'importo', 'numds', 'azioni', 'dich'];
  displayedColumnsTask: string[] = ['tipologia', 'num', 'data', 'task', 'fornitore', 'stato', 'importo', 'numds', 'azioni', 'dich'];
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
  messageInfo: string;
  isMessageInfoVisible: boolean;

  //visualizzazione modifiche
  isVisible: boolean = false;
  // Costante Bandi FP
  isFP: boolean = true;

  //LOADED
  loadedInizializza: boolean;
  loadedChiudiAttivita: boolean = true;
  loadedCerca: boolean = true;
  loadedDownload: boolean = true;
  loadedRegolaBR79: boolean = true;
  loadedRegolaBR63: boolean = true;
  loadedMensilita: boolean = true;
  loadedSalvaMensilita: boolean = true;
  loadedIsFP: boolean = true;
  loadedAllegatiDS: boolean = true;

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
    private gestioneSpesaValidataService: GestioneSpesaValidataService,
    private navigationService: NavigationGestioneSpesaValidataService,
    private cdRef: ChangeDetectorRef,
    private dialog: MatDialog
  ) { }

  ngAfterContentChecked(): void {
    this.cdRef.detectChanges();
  }

  //MANCA IL NAVIGATION SERVICE

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
    });
    this.isVisibleFunc();
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data && data.codiceRuolo) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
        if (this.user.codiceRuolo === Constants.CODICE_RUOLO_RAG_DEL) {
          this.isRagioneriaDelegata = true;
        }
        this.inizializzaGestioneSpesaValidata();
      }
    });
  }

  inizializzaGestioneSpesaValidata(fromChild?: boolean) {
    this.loadedInizializza = false;
    this.subscribers.inizializza = this.gestioneSpesaValidataService.inizializzaSpesaValidata(this.idProgetto, this.isVisible && this.isFP).subscribe(data => {
      if (data) {
        this.codiceProgetto = data.codiceVisualizzatoProgetto;
        this.taskVisibile = data.taskVisibile;
        if (this.taskVisibile) {
          this.elencoTask = data.elencoTask;
          this.displayedColumns = this.displayedColumnsTask;
        } else {
          this.displayedColumns = this.displayedColumnsNoTask;
        }
        this.dichiarazioniDiSpesa = data.comboDichiarazioniDiSpesa;
        if (this.dichiarazioniDiSpesa?.length === 1) {
          this.dichiarazioneDiSpesaSelected = this.dichiarazioniDiSpesa[0];
          if (!this.isNoteRilievo && this.dtChiusuraRilievi) {
            //se non sono presenti rilievi ma c'è la data chiusura setto il nulla da rilevare
            //dopo il servizio di ricerca verifico se sono presenti rilievi nei documenti
            this.isNullaDaRilevareChecked = true;
          }
        }
        this.vociDiSpesa = data.comboVociDiSpesa;
        this.tipologieDoc = data.comboTipiDocumentoDiSpesa;
        this.tipologieFornitori = data.comboTipiFornitore;
        this.richiestaIntegrazioneAbilitata = data.richiestaIntegrazioneAbilitata;
        this.esistePropostaCertificazione = data.esistePropostaCertificazione;
        if (this.esistePropostaCertificazione) {
          this.showMessageWarning("Per il progetto è stata creata almeno una proposta di certificazione. Eventuali rettifiche sulla spesa validata potranno avere effetto sugli importi certificati.");
        }
        let filtro = this.navigationService.filtroRicercaSpesaValidataSelezionato;
        if (filtro) {
          this.dichiarazioneDiSpesaSelected = filtro.dichiarazioneDiSpesaSelected ? this.dichiarazioniDiSpesa.find(d => d.codice === filtro.dichiarazioneDiSpesaSelected.codice) : null;
          this.voceDiSpesaSelected = filtro.voceDiSpesaSelected ? this.vociDiSpesa.find(v => v.codice === filtro.voceDiSpesaSelected.codice) : null;
          this.tipologiaDocSelected = filtro.tipologiaDocSelected ? this.tipologieDoc.find(t => t.codice === filtro.tipologiaDocSelected.codice) : null;
          this.taskSelected = filtro.taskSelected ? this.elencoTask.find(t => t === filtro.taskSelected) : null;
          this.tipologiaFornitoreSelected = filtro.tipologiaFornitoreSelected ? this.tipologieFornitori.find(t => t.codice === filtro.tipologiaFornitoreSelected.codice) : null;
          this.numDocSpesa = filtro.numDocSpesa;
          this.dataDoc = filtro.dataDoc;
          this.cfFornitore = filtro.cfFornitore;
          this.pIvaFornitore = filtro.pIvaFornitore;
          this.denomFornitore = filtro.denomFornitore;
          this.cognomeFornitore = filtro.cognomeFornitore;
          this.nomeFornitore = filtro.nomeFornitore;
          this.documenti = filtro.documenti;
        }
        this.loadData();
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

  get descBreveTipoDSIntermedia() { return Constants.DESC_BREVE_TIPO_DS_INTERMEDIA; }
  get descBreveTipoDSFinale() { return Constants.DESC_BREVE_TIPO_DS_FINALE; }
  get descBreveTipoDSFinaleComunicazione() { return Constants.DESC_BREVE_TIPO_DS_FINALE_COMUNICAZIONE; }
  get descBreveTipoDSIntegrativa() { return Constants.DESC_BREVE_TIPO_DS_INTEGRATIVA; }
  get isFinale() {
    return this.dichiarazioneDiSpesaSelected?.descBreveTipoDichiarazioneSpesa === this.descBreveTipoDSFinale
      || this.dichiarazioneDiSpesaSelected?.descBreveTipoDichiarazioneSpesa === this.descBreveTipoDSFinaleComunicazione
  }

  get noteRilievo(): string {
    return this.dichiarazioneDiSpesaSelected?.noteRilievoContabile || null;
  }

  set noteRilievo(note: string) {
    this.dichiarazioneDiSpesaSelected.noteRilievoContabile = note;
  }

  get isNoteRilievo(): boolean {
    return this.noteRilievo?.length > 0;
  }

  get dtChiusuraRilievi() {
    return this.dichiarazioneDiSpesaSelected?.dtChiusuraRilievi || null;
  }

  set dtChiusuraRilievi(date: Date) {
    this.dichiarazioneDiSpesaSelected.dtChiusuraRilievi = date;
  }

  get dtConfermaValiditaRilievi() {
    return this.dichiarazioneDiSpesaSelected?.dtConfermaValiditaRilievi || null;
  }

  set dtConfermaValiditaRilievi(date: Date) {
    this.dichiarazioneDiSpesaSelected.dtConfermaValiditaRilievi = date;
  }

  get idStatoRichiestaCompletata() {
    return Constants.ID_STATO_RICHIESTA_COMPLETATA;
  }

  get idStatoRichiestaChiusaUfficio() {
    return Constants.ID_STATO_RICHIESTA_CHIUSA_UFFICIO;
  }

  setIdStatoRichiesta(id: number) {
    this.idStatoRichiesta = id;
  }

  isVisibleFunc() {
    // Controllo se abilitare le nuove finzioni e disabilitare le vecchie in base all'ambiente
    this.subscribers.costanteFin = this.userService.isCostanteFinpiemonte().subscribe(data => {
      console.log("isCostanteFinpiemonte: ", data)

      this.isVisible = data;
    })
  }

  loadData() {
    this.loadedRegolaBR79 = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, this.BR79).subscribe(res => {
      this.isBR79 = res;
      if (this.isBR79) {
        this.loadMensilita();
      }
      this.loadedRegolaBR79 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica della regola BR79.");
      this.loadedRegolaBR79 = true;
    });

    this.loadedIsFP = false;
    this.subscribers.bandoFin = this.userService.isBandoCompetenzaFinpiemonte(this.idBandoLinea).subscribe(data => {
      this.isFP = data;
      this.loadedIsFP = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica bando competenza Finpiemonte.");
      this.loadedIsFP = true;
    });
    this.loadedRegolaBR63 = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, this.BR63).subscribe(res => {
      this.isBR63 = res;
      if (!(this.isBR63 && this.isRagioneriaDelegata) || this.dichiarazioneDiSpesaSelected?.codice) {
        this.ricerca();
      }
      if (this.isBR63) {
        if (!this.isRagioneriaDelegata && (this.isVisible || !this.isFP || this.isBR79)) {
          this.showMessageInfo("Per visionare i rilievi e gestire le richieste integrazioni è necessario effettuare una ricerca selezionando una dichiarazione di spesa.");
        }
      } else if (!this.isBR63 && !this.isRagioneriaDelegata && (this.isVisible || !this.isFP || this.isBR79)) {
        this.showMessageInfo("Per gestire le richieste integrazioni è necessario effettuare una ricerca selezionando una dichiarazione di spesa.");
      }
      this.loadedRegolaBR63 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica della regola BR62.");
      this.loadedRegolaBR63 = true;
    });
  }

  get mesi() {
    return Mesi;
  }

  areTutteNoteObbligatorieCompilate(): boolean {
    return this.elencoMensilita.every(mensilita => {
      return mensilita.esito !== 'KO' && mensilita.esito !== 'NV' || !!mensilita.note;
    });
  }


  loadMensilita() {
    this.loadedMensilita = false;
    this.gestioneSpesaValidataService.recuperaMensilitaProgetto(this.idProgetto).subscribe(data => {
      this.elencoMensilita = data;
      if (this.elencoMensilita?.length > 0) {
        for (let mensilita of this.elencoMensilita) {
          if (!mensilita.esito && mensilita.sabbatico !== "S") {
            mensilita.esito = "OK"
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
    return this.elencoMensilita?.find(m => m.sabbatico === "S" || m.erogato);
  }

  get isSalvaMensilitaVisible() {
    return this.elencoMensilita?.find(m => m.esito);
  }

  salvaMensilita() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedSalvaMensilita = false;
    this.gestioneSpesaValidataService.validaMensilitaProgetto(this.elencoMensilita).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess("Esito per mensilità rendicontate salvato con successo.");
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

  changeDS() {
    this.isResultVisible = false;
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

  ricerca() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.isBR63 && this.isRagioneriaDelegata) {
      if (!this.dichiarazioneDiSpesaSelected?.codice) {
        this.showMessageError("Attenzione! È necessario selezionare una dichiarazione di spesa per effettuare la ricerca.");
        return;
      } else if (!this.dtChiusuraRilievi) {
        this.showMessageInfo("Per aggiungere un rilievo generico alla Dichiarazione di Spesa, cliccare sul pulsante AGGIUNGI RILIEVO CONTABILE.<br/>Per aggiungere un rilievo ad un documento di spesa specifico, entrare in modifica del documento.<br/>Se non si devono aggiungere rilievi, selezionare la checkbox 'Nulla da rilevare'.");
      } else {
        this.resetMessageInfo();
      }
    }

    if (this.dichiarazioneDiSpesaSelected?.codice) {
      this.loadedAllegatiDS = false;
      if (this.isVisible || !this.isFP) {
        this.subscribers.allegatiDichiarazione = this.dichiarazioneDiSpesaService.getAllegatiDichiarazioneDiSpesaIntegrazioni(this.idProgetto, +this.dichiarazioneDiSpesaSelected.codice).subscribe(data => {
          this.allegatiDs = data;
          this.loadedAllegatiDS = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Attenzione! Errore in fase di caricamento degli allegati della dichiarazione di spesa.");
          this.loadedAllegatiDS = true;
        });
      } else {
        this.subscribers.allegatiDichiarazione = this.dichiarazioneDiSpesaService.getAllegatiDichiarazioneDiSpesa(this.idProgetto, +this.dichiarazioneDiSpesaSelected.codice).subscribe(data => {

          this.allegatiDs = data;
          this.loadedAllegatiDS = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Attenzione! Errore in fase di caricamento degli allegati della dichiarazione di spesa.");
          this.loadedAllegatiDS = true;
        });
      }
    }
    this.loadedCerca = false;
    let request = new CercaDocumentiSpesaValidataRequest(this.idProgetto, this.dichiarazioneDiSpesaSelected ? +this.dichiarazioneDiSpesaSelected.codice : null,
      this.voceDiSpesaSelected ? +this.voceDiSpesaSelected.codice : null, this.tipologiaDocSelected ? +this.tipologiaDocSelected.codice : null,
      this.taskVisibile ? this.taskSelected : null, this.numDocSpesa, this.dataDoc.value ? new Date(this.dataDoc.value) : null,
      this.tipologiaFornitoreSelected ? +this.tipologiaFornitoreSelected.codice : null, this.cfFornitore, this.pIvaFornitore, this.denomFornitore, this.cognomeFornitore,
      this.nomeFornitore, this.documenti === 'T' ? true : false, null);
    this.subscribers.cerca = this.gestioneSpesaValidataService.cercaDocumentiSpesaValidata(request).subscribe(data => {
      if (data) {
        this.docSpesa = data;
        if (this.isBR63 && this.dichiarazioneDiSpesaSelected?.codice) {
          if (this.docSpesa?.find(d => d.rilievoContabile?.length > 0)) {
            this.hasDocSpesaConRilievo = true;
            this.isNullaDaRilevareChecked = false;
            if (!this.displayedColumns?.find(d => d === "rilievi")) {
              this.displayedColumns.push("rilievi");
            }
          } else {
            this.hasDocSpesaConRilievo = false;
            if (this.displayedColumns?.find(d => d === "rilievi")) {
              this.displayedColumns.pop();
            }
          }
        }
        this.dataSource = new DocumentiDatasource(this.docSpesa);
        if (this.navigationService.filtroRicercaSpesaValidataSelezionato) {
          this.ripristinaSortPaginator();
        } else {
          this.paginator.length = this.docSpesa.length;
          this.paginator.pageIndex = 0;
          this.dataSource.paginator = this.paginator;
          this.setSort();
          this.dataSource.sort = this.sort;
        }

        if (this.isBR63 && (this.isVisible || !this.isFP)) {
          this.nDocuSpesaSospesi = 0;
          // Conto il num di documenti in stato sospeso
          this.docSpesa.forEach(element => {
            if (element.descrizioneStato == "SOSPESO") {
              this.nDocuSpesaSospesi += 1;
            }
          });
        }

        this.criteriRicercaOpened = false;
        this.isResultVisible = true;
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca.");
      this.loadedCerca = true;
    });
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  anteprimaReportDettaglio() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.gestioneSpesaValidataService.reportDettaglioDocumentoDiSpesa(this.idProgetto).subscribe(res => {
      let nomeFile = res.headers.get("header-nome-file");
      saveAs(new Blob([res.body]), nomeFile);
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  openDichiarazioni(idDocumentoDiSpesa: number) {
    this.dialog.open(DichiarazioniDialogComponent, {
      width: '60%',
      data: {
        idDocumentoDiSpesa: idDocumentoDiSpesa,
        idProgetto: this.idProgetto,
        isVisible: this.isVisible,
        isFP: this.isFP
      }
    });
  }

  changeNullaDaRilevare(event: MatCheckboxChange) {
    if (event.checked) {
      let dialogRef = this.dialog.open(ConfermaWarningDialogComponent, {
        data: {
          title: "Conferma la correttezza della Dichiarazione di Spesa",
          messageWarning: "Attenzione! Dopo la conferma verrà inviata una notifica all'istruttore con il bollino verde per la dichiarazione di spesa."
        }
      });
      dialogRef.afterClosed().subscribe(res => {
        if (res) {
          //elimino rilievi esistenti, set dtChiudiRilievi
          //TODO: invio notifica istruttore
          let idDocumentiConRilievo: number[] = this.docSpesa?.filter(d => d.rilievoContabile?.length > 0)?.map(d => d.id) || [];
          this.loadedCerca = false;
          this.gestioneSpesaValidataService.setNullaDaRilevare(+this.dichiarazioneDiSpesaSelected?.codice, this.idProgetto, idDocumentiConRilievo).subscribe(data => {
            if (data?.esito) {
              this.noteRilievo = null;
              this.dichiarazioneDiSpesaSelected.dtRilievoContabile = null;
              this.dtChiusuraRilievi = new Date();
              this.ricerca();
              this.showMessageSuccess(data.messaggio);
            } else {
              this.showMessageError("Errore durante il salvataggio dell'operazione.");
            }
            this.loadedCerca = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore durante il salvataggio dell'operazione.");
            this.loadedCerca = true;
          });
        } else {
          this.isNullaDaRilevareChecked = false;
        }
      });
    }
  }

  aggiungiModificaRilievo() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let dialogRef = this.dialog.open(RilievoDialogComponent, {
      width: "50%",
      data: {
        note: this.noteRilievo || null
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.loadedCerca = false;
        this.gestioneSpesaValidataService.salvaRilievoDS(+this.dichiarazioneDiSpesaSelected?.codice, res).subscribe(data => {
          if (data?.esito) {
            this.noteRilievo = res;
            this.dichiarazioneDiSpesaSelected.dtRilievoContabile = new Date();
            this.showMessageSuccess(data.messaggio);
          } else {
            this.showMessageError("Errore durante il salvataggio del rilievo contabile.");
          }
          this.loadedCerca = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore durante il salvataggio del rilievo contabile.");
          this.loadedCerca = true;
        });
      }
    });
  }

  deleteRilievo() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let dialogRef = this.dialog.open(ConfermaDialog, {
      data: {
        message: "Confermare la cancellazione del rilievo della Dichiarazione di Spesa?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedCerca = false;
        this.gestioneSpesaValidataService.deleteRilievoDS(+this.dichiarazioneDiSpesaSelected?.codice).subscribe(data => {
          if (data?.esito) {
            this.noteRilievo = null;
            this.dichiarazioneDiSpesaSelected.dtRilievoContabile = null;
            this.showMessageSuccess(data.messaggio);
          } else {
            this.showMessageError("Errore durante la cancellazione del rilievo.");
          }
          this.loadedCerca = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore durante la cancellazione del rilievo.");
          this.loadedCerca = true;
        });
      }
    });
  }

  openDettaglioRilievo(doc: DocumentoDiSpesa) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.dialog.open(RilievoDialogComponent, {
      width: "50%",
      data: {
        note: doc.rilievoContabile,
        date: doc.dtRilievoContabile,
        readOnly: true
      }
    });
  }

  chiudiRilievi() {
    this.resetMessageError();
    this.resetMessageSuccess();

    if (!this.isNoteRilievo && !this.hasDocSpesaConRilievo) {
      this.showMessageError("Impossibile chiudere i rilievi: aggiungere un rilievo alla Dichirazione di Spesa o ad un documento oppure selezionare il check 'Nulla da rilevare'.");
      return;
    }

    let dialogRef = this.dialog.open(ConfermaWarningDialogComponent, {
      data: {
        title: "Conferma della chiusura dei rilievi",
        messageWarning: "Attenzione! Dopo la conferma non sarà più possibile aggiungere rilievi alla dichiarazione di spesa e ai documenti di spesa."
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        //setto dtChiusuraRilievi
        this.loadedCerca = false;
        this.gestioneSpesaValidataService.chiudiRilievi(+this.dichiarazioneDiSpesaSelected?.codice).subscribe(data => {
          if (data?.esito) {
            this.dtChiusuraRilievi = new Date();
            this.showMessageSuccess(data.messaggio);
          } else {
            this.showMessageError("Errore durante la chiusura dei rilievi.");
          }
          this.loadedCerca = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore durante la chiusura dei rilievi.");
          this.loadedCerca = true;
        });
      }
    });
  }

  confermaValiditaRilievi() {
    this.resetMessageError();
    this.resetMessageSuccess();

    if (this.nDocuSpesaSospesi > 0) {
      this.showMessageError("È necessario annullare la sospensione dei documenti di spesa per procedere alla conferma validità.");
      return;
    }
    let dialogRef = this.dialog.open(ConfermaDialog, {
      data: {
        message: "Confermare la validità dei rilievi?",
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        //notifica a ragioneria delegata e set dtConfermaValidita
        this.loadedCerca = false;
        this.gestioneSpesaValidataService.confermaValiditaRilievi(+this.dichiarazioneDiSpesaSelected?.codice).subscribe(data => {
          if (data?.esito) {
            this.dtConfermaValiditaRilievi = new Date();
            this.showMessageSuccess(data.messaggio);
          } else {
            this.showMessageError("Errore durante la conferma della validità dei rilievi.");
          }
          this.loadedCerca = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore durante la conferma della validità dei rilievi.");
          this.loadedCerca = true;
        });
      }
    });
  }

  setFiltro() {
    return new FiltroRicercaSpesaValidata(this.taskSelected, this.dichiarazioneDiSpesaSelected, this.voceDiSpesaSelected, this.tipologiaDocSelected,
      this.tipologiaFornitoreSelected, this.numDocSpesa, this.dataDoc, this.cfFornitore, this.pIvaFornitore, this.denomFornitore, this.cognomeFornitore, this.nomeFornitore,
      this.documenti);
  }

  saveDataForNavigation() {
    this.navigationService.filtroRicercaSpesaValidataSelezionato = this.setFiltro();
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
        case "numero":
          return item.numeroDocumento;
        case "fornitore":
          return item.denominazioneFornitore;
        case "task":
          return item.task;
        case "importo":
          return item.importiValidati ? item.importiValidati[0] : "";
        case "validato":
          return item.importoTotaleValidato;
        case "stato":
          return item.descrizioneStato;
        case "numds":
          return item.numDichiarazioni ? item.numDichiarazioni[0] : "";
        default: return item[property];
      }
    };
  }

  goToModificaDocumento(idDocumentoDiSpesa: number, idDichiarazioneDiSpesa: number, rettificato: boolean) {
    this.saveDataForNavigation();
    let url: string = `drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA}/rettificaDocumentoDiSpesa/${this.idProgetto}/${this.idBandoLinea}/${idDocumentoDiSpesa}/${idDichiarazioneDiSpesa}`;
    url += rettificato ? ';rettificato=true' : '';
    this.router.navigateByUrl(url);
  }

  goToChecklistSpesaValidata() {
    this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA}/gestioneChecklistSpesaValidata/${this.idProgetto}/${this.idBandoLinea}`);
  }

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_GESTIONE_SPESA_VALIDATA).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
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
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageInfo(msg: string) {
    this.messageInfo = msg;
    this.isMessageInfoVisible = true;
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

  resetMessageInfo() {
    this.messageInfo = null;
    this.isMessageInfoVisible = false;
  }

  isLoading() {
    if (!this.loadedChiudiAttivita || !this.loadedInizializza || !this.loadedCerca || !this.loadedDownload
      || !this.loadedRegolaBR79 || !this.loadedMensilita || !this.loadedSalvaMensilita
      || !this.loadedIsFP || !this.loadedAllegatiDS
      || (this.richiestaIntegrazioneBoxComponent && this.richiestaIntegrazioneBoxComponent.isLoading())
      || (this.richiestaIntegrazioneButtonComponent && this.richiestaIntegrazioneButtonComponent.isLoading())) {
      return true;
    }
    return false;
  }

}

export class DocumentiDatasource extends MatTableDataSource<DocumentoDiSpesa> {
  constructor(data: DocumentoDiSpesa[]) {
    super(data);
  }
}