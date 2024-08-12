import { Router, ActivatedRoute } from '@angular/router';
import { AfterViewChecked, ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { RendicontazioneService } from '../../services/rendicontazione.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { DecodificaDTO } from '../../commons/dto/decodifica-dto';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { MatDialog } from '@angular/material/dialog';
import { FornitoreService } from '../../services/fornitore.service';
import { FornitoreDTO } from '../../commons/dto/fornitore-dto';
import { Fornitore } from '../../commons/dto/fornitore';
import { Constants } from 'src/app/core/commons/util/constants';
import { FornitoreFormDTO } from '../../commons/dto/fornitore-form-dto';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { DocumentoAllegatoDTO } from '../../commons/dto/documento-allegato-dto';
import { AssociaFilesRequest } from '../../commons/requests/associa-files-request';
import { saveAs } from 'file-saver-es';
import { NuovoModicaFornitoreDialogComponent } from '../nuovo-modica-fornitore-dialog/nuovo-modica-fornitore-dialog.component';
import { ArchivioFileDialogComponent, ArchivioFileService, DatiProgettoAttivitaPregresseDialogComponent, InfoDocumentoVo, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { SharedService } from 'src/app/shared/services/shared.service';

export interface UserData {
  tipologia: string;
  codiceFiscale: string;
  denominazione: string;
  pIva: string;
  qualifica: string;
  costoOrario: string;
  nota: string;
}

@Component({
  selector: 'app-gestione-fornitori',
  templateUrl: './gestione-fornitori.component.html',
  styleUrls: ['./gestione-fornitori.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneFornitoriComponent implements AfterViewChecked {

  idProgetto: number;
  idBandoLinea: number;
  user: UserInfoSec;
  da: string;
  from: string;
  current: string;
  idDocSpesa: number;
  isReadOnly: boolean;

  displayedColumns: string[] = [];
  displayedColumnsPersonaFisica: string[] = ['tipologia', 'codiceFiscale', 'cognome', 'nome', 'documenti'];
  displayedColumnsPersonaGiuridica: string[] = ['tipologia', 'codiceFiscale', 'denominazione', 'pIvaAltroCodice', 'documenti'];
  dataSource: FornitoriDatasource;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  criteriRicercaOpened: boolean = true;
  isElencoVisible: boolean;
  beneficiario: string;
  codiceProgetto: string;
  tipologie: Array<DecodificaDTO>;
  tipologiaSelected: DecodificaDTO;
  codiceFiscale: string;
  partitaIva: string;
  cognome: string;
  nome: string;
  denominazione: string;
  altroCodIdentificativo: string;
  fornitori: Array<Fornitore>;
  fornitoreSelected: Fornitore;
  qualifiche: Array<DecodificaDTO>;
  qualificaSelected: DecodificaDTO;
  idRouting: number;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  tooltipWarning: string;
  isTooltipWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedTipologie: boolean;
  loadedInizializzazione: boolean;
  loadedQualifiche: boolean;
  loadedCerca: boolean = true;
  loadedElimina: boolean = true;
  loadedSaveAllegati: boolean = true;
  loadedDownload: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private rendicontazioneService: RendicontazioneService,
    private fornitoreService: FornitoreService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private sharedService: SharedService,
    private dialog: MatDialog,
    private cdRef: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    
    this.subscribers.router = this.activatedRoute.parent.params.subscribe(params => {
      this.idRouting = params.id;
    });
    
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {

      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
    });

    this.da = this.activatedRoute.snapshot.paramMap.get('da');
    if (this.activatedRoute.snapshot.paramMap.get('idDoc')) {
      this.idDocSpesa = +this.activatedRoute.snapshot.paramMap.get('idDoc');
      this.current = this.activatedRoute.snapshot.paramMap.get('current');
      if (this.activatedRoute.snapshot.paramMap.get('from')) {
        this.from = this.activatedRoute.snapshot.paramMap.get('from');
      }
    }
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
        if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADA_OPE_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_GDF) {
          this.isReadOnly = true;
        }
        this.loadData();
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_GEST_FORNITORI;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  loadData() {
    this.loadedInizializzazione = false;
    this.subscribers.inizializza = this.rendicontazioneService.inizializzaRendicontazione(this.idProgetto, this.idBandoLinea, this.user.idSoggetto, this.user.codiceRuolo).subscribe(dataInizializzazione => {
      if (dataInizializzazione) {
        this.codiceProgetto = dataInizializzazione.codiceVisualizzatoProgetto;
      }
      this.loadedInizializzazione = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.loadedTipologie = false;
    this.subscribers.tipoFornitore = this.rendicontazioneService.findTipologieFornitore().subscribe(data => {
      if (data) {
        this.tipologie = data;
        this.tipologiaSelected = this.tipologie[0];
      }
      this.loadedTipologie = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.loadedQualifiche = false;
    this.rendicontazioneService.getQualifiche(this.user).subscribe(data => {
      if (data) {
        this.qualifiche = data;
      }
      this.loadedQualifiche = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  onSelectTipologia() {
    this.codiceFiscale = null;
    this.qualificaSelected = null;
    this.partitaIva = null;
    this.altroCodIdentificativo = null;
    this.denominazione = null;
    this.cognome = null;
    this.nome = null;
  }

  nuovoFornitore() {
    let dialogRef = this.dialog.open(NuovoModicaFornitoreDialogComponent, {
      width: '60%',
      disableClose: true,
      data: {
        tipologie: this.tipologie,
        qualifiche: this.qualifiche,
        isNuovo: true,
        user: this.user,
        idProgetto: this.idProgetto
      }
    });

    dialogRef.afterClosed().subscribe(res => {
      if (!res.isNuovo) {
        this.ricerca();
      }
    });
  }

  modificaFornitore(fornitore: Fornitore) {
    let fornitoreForm = new FornitoreFormDTO();
    fornitoreForm.altroCodice = fornitore.altroCodice;
    fornitoreForm.cfAutoGenerato = fornitore.idNazione ? "S" : "N";
    fornitoreForm.codUniIpa = fornitore.codUniIpa;
    fornitoreForm.codiceFiscaleFornitore = fornitore.codiceFiscaleFornitore;
    fornitoreForm.cognomeFornitore = fornitore.cognomeFornitore;
    fornitoreForm.denominazioneFornitore = fornitore.denominazioneFornitore;
    fornitoreForm.flagPubblicoPrivato = fornitore.flagPubblicoPrivato;
    fornitoreForm.idAttivitaAteco = fornitore.idAttivitaAteco;
    fornitoreForm.idFormaGiuridica = fornitore.idFormaGiuridica;
    fornitoreForm.idFornitore = fornitore.idFornitore;
    fornitoreForm.idNazione = fornitore.idNazione;
    fornitoreForm.idTipoSoggetto = fornitore.idTipoSoggetto;
    fornitoreForm.nomeFornitore = fornitore.nomeFornitore;
    fornitoreForm.partitaIvaFornitore = fornitore.partitaIvaFornitore;
    let dialogRef = this.dialog.open(NuovoModicaFornitoreDialogComponent, {
      width: '60%',
      disableClose: true,
      data: {
        tipologie: this.tipologie,
        qualifiche: this.qualifiche,
        fornitore: fornitoreForm,
        allegati: fornitore.documentiAllegati,
        isNuovo: false,
        user: this.user,
        idProgetto: this.idProgetto
      }
    });

    dialogRef.afterClosed().subscribe(res => {
      this.ricerca();
    });
  }

  eliminaFornitore(idFornitore: number) {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione del fornitore?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedElimina = false;
        this.subscribers.elimina = this.fornitoreService.eliminaFornitore(idFornitore).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.showMessageSuccess(data.messaggi[0]);
              this.ricerca(true);
            } else {
              this.showMessageError(data.messaggi[0]);
            }
          }
          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedElimina = true;
          this.showMessageError("Errore nell'eliminazione del fornitroe.");
        });
      }
    });
  }

  aggiungiAllegati(fornitore: Fornitore) {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegati: fornitore.documentiAllegati,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded()
      }
    });

    dialogRef.afterClosed().subscribe((res: Array<InfoDocumentoVo>) => {
      if (res && res.length > 0) {
        this.associaAllegatiFornitore(fornitore, res.map(r => new DocumentoAllegatoDTO(null, null, +r.idDocumentoIndex, null, true, null, null, r.nome, null, null, null)));
      }
    });
  }

  associaAllegatiFornitore(fornitore: Fornitore, allegati: Array<DocumentoAllegatoDTO>) {
    this.loadedSaveAllegati = false;
    this.subscribers.associaAllegati = this.fornitoreService.associaAllegatiAFornitore(
      new AssociaFilesRequest(allegati.map(a => a.id), fornitore.idFornitore, this.idProgetto)).subscribe(data => {
        if (data) {
          if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
            && (!data.elencoIdDocIndexFilesNonAssociati || data.elencoIdDocIndexFilesNonAssociati.length === 0)) { //tutti associati
            fornitore.documentiAllegati.push(...allegati);
            this.showMessageSuccess("Tutti gli allegati sono stati associati correttamente al fornitore.");
          } else if (data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0
            && (!data.elencoIdDocIndexFilesAssociati || data.elencoIdDocIndexFilesAssociati.length === 0)) { //tutti non associati
            this.showMessageError("Errore nell'associazione degli allegati al fornitore.");
          } else if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
            && data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0) { //alcuni associati e alcuni non associati
            fornitore.documentiAllegati.push(...allegati.filter(a => data.elencoIdDocIndexFilesAssociati.includes(a.id)));
            let message = "Errore nell'associazione dei seguenti allegati al fornitore: ";
            data.elencoIdDocIndexFilesNonAssociati.forEach(id => {
              let allegato = allegati.find(a => a.id === id);
              message += allegato.nome + ", ";
            });
            message = message.substr(0, message.length - 2);
            this.showMessageError(message);
          }
        }
        this.loadedSaveAllegati = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nell'associazione degli allegati al fornitore");
        this.loadedSaveAllegati = true;
      });
  }

  downloadAllegato(idDocumentoIndex: string, nomeDocumento: string) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  disassociaAllegato(allegato: DocumentoAllegatoDTO, fornitore: Fornitore) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedElimina = false;
    this.subscribers.disassociaAllegato = this.fornitoreService.disassociaAllegatoFornitore(allegato.id, fornitore.idFornitore, this.idProgetto).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess(data.messaggio);
          fornitore.documentiAllegati = fornitore.documentiAllegati.filter(q => q.id !== allegato.id);
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedElimina = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nella disassociazione dell'allegato'.");
      this.loadedElimina = true;
    });
  }

  ricerca(isMessageSuccess?: boolean) {
    if (!isMessageSuccess) {
      this.resetMessageSuccess();
    }
    this.resetMessageWarning();
    this.resetMessageError();
    this.loadedCerca = false;
    let filtro = new FornitoreDTO(this.codiceFiscale, null/*?*/, null/*?*/, this.cognome, null, null, this.denominazione, this.qualificaSelected ? this.qualificaSelected.descrizione : null,
      this.tipologiaSelected.descrizioneBreve, this.tipologiaSelected.descrizione, null, null/*?*/, null/*?*/, null, null, null, this.qualificaSelected ? this.qualificaSelected.id : null,
      null/*?*/, this.tipologiaSelected.id, null/*?*/, null, this.nome, this.partitaIva, this.altroCodIdentificativo, null, null/*?*/);
    this.subscribers.cerca = this.fornitoreService.ricercaElencoFornitori(filtro, this.user.beneficiarioSelezionato.idBeneficiario, this.idProgetto).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.fornitori = data.fornitori;
          if (this.tipologiaSelected.id === Constants.ID_TIPO_FORNITORE_PERSONA_FISICA) {
            this.displayedColumns = this.displayedColumnsPersonaFisica;
          } else {
            this.displayedColumns = this.displayedColumnsPersonaGiuridica;
          }
          if (!this.displayedColumns.includes("azioni") && this.user.codiceRuolo !== Constants.CODICE_RUOLO_ADA_OPE_MASTER && this.user.codiceRuolo !== Constants.CODICE_RUOLO_GDF) {
            this.displayedColumns.push('azioni');
          }
          this.fornitori.forEach(f => {
            if (f.codiceFiscaleFornitore.indexOf('PBAN') > -1) {
              f.codiceFiscaleFornitore = "N.D.";
            }
            let cfpi = f.codiceFiscaleFornitore.trim();
            let alci = f.partitaIvaFornitore ? f.partitaIvaFornitore.trim() : (f.altroCodice ? f.altroCodice.trim() : null);
            if (!this.verificaCodice(cfpi)) {
              f.isCFInvalid = true;
            }
            if (alci == '00000000000') {
              f.isAltroCodInvalid = true;
            }

          });
          this.dataSource = new FornitoriDatasource(this.fornitori);
          this.paginator.length = this.fornitori.length;
          this.paginator.pageIndex = 0;
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
          this.criteriRicercaOpened = false;
          this.isElencoVisible = true;
        } else {
          this.showMessageError(data.messaggio);
          this.isElencoVisible = false;
        }
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nella ricerca dei fornitori.")
      this.loadedCerca = true;
      this.isElencoVisible = false;
    });
  }

  controllaCF(cf: string) {
    let validi, i, s, set1, set2, setpari, setdisp;
    if (cf == '') return '';
    cf = cf.toUpperCase();
    if (cf.length != 16) {
      return "La lunghezza del codice fiscale non è\r\n"
        + "corretta: il codice fiscale dovrebbe essere lungo\n"
        + "esattamente 16 caratteri.\n";
    }
    validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (i = 0; i < 16; i++) {
      if (validi.indexOf(cf.charAt(i)) == -1)
        return "Il codice fiscale contiene un carattere non valido `" +
          cf.charAt(i) +
          "'.\r\nI caratteri validi sono le lettere e le cifre.\n";
    }
    set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
    s = 0;
    for (i = 1; i <= 13; i += 2) {
      s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
    }
    for (i = 0; i <= 14; i += 2) {
      s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
    }
    if (s % 26 != cf.charCodeAt(15) - 'A'.charCodeAt(0)) {
      return "Il codice fiscale non è corretto:\r\n" +
        "il codice di controllo non corrisponde.\n";
    }
    return '';
  }

  controllaPIVA(pi: string) {
    if (pi == '') {
      return '';
    }
    if (pi.length != 11) {
      return "La lunghezza della partita IVA non è\n" +
        "corretta: la partita IVA dovrebbe essere lunga\n" +
        "esattamente 11 caratteri.\n";
    }
    let validi = "0123456789";
    for (let i = 0; i < 11; i++) {
      if (validi.indexOf(pi.charAt(i)) == -1) {
        return "Partita IVA / codice fiscale contiene un carattere non valido '" +
          pi.charAt(i) + "'.\r\nI caratteri validi sono le cifre.\n";
      }
    }
    let s = 0;
    for (let i = 0; i <= 9; i += 2)
      s += pi.charCodeAt(i) - '0'.charCodeAt(0);
    for (let i = 1; i <= 9; i += 2) {
      let c = 2 * (pi.charCodeAt(i) - '0'.charCodeAt(0));
      if (c > 9) c = c - 9;
      s += c;
    }
    if ((10 - s % 10) % 10 != pi.charCodeAt(10) - '0'.charCodeAt(0)) {
      return "Partita IVA / codice fiscale non valido:\r\n" +
        "il codice di controllo non corrisponde.\n";
    }
    return '';
  }

  verificaCodice(cod: string) {
    let err = '';
    if (cod == '') {
      err = "Compilare il campo Codice Fiscale\n";
    } else if (/^([A-Z]){4}_([0-9]){11}\*$/.test(cod)) {
      err = '';
    } else if (cod == '00000000000') {
      err = "Il Codice Fiscale non è formalmente corretto\n";
    } else if (cod.length == 16) {
      err = this.controllaCF(cod);
    } else if (cod.length == 11 && cod != '00000000000') {
      err = this.controllaPIVA(cod);
    } else if (cod === 'N.D.') {
      err = '';
    } else {
      err = "Il codice introdotto non è valido:\r\n" +
        "  - un codice fiscale deve essere lungo 16 caratteri \r\n" +
        "  - una partita IVA deve essere lunga 11 caratteri \r\n";
    }

    if (err > '') {
      let result = err.replace("\r\n", '<br />')
      //this.showMessageError("<div class='contentMsg'><p>VALORE ERRATO</p><p>" + result + "</p></div>");
      return false;
    } else {
      return true;
      //showErrorMessage("Il codice è valido.");
    }
  }

  goBack() {
    if (this.da === "rendicontazione") {
      this.router.navigate(["drawer/" + this.idRouting + "/rendicontazione/" +
        (this.activatedRoute.snapshot.paramMap.get('integrativa') ? "integrativa/" : "") + this.idProgetto + "/" + this.idBandoLinea]);
    } else if (this.da === "documento" && this.idDocSpesa) {
      if (this.from) {
        let params = this.activatedRoute.snapshot.paramMap.get('integrativa') ?
          { from: this.from, current: this.current, integrativa: true }
          : { from: this.from, current: this.current };
        this.router.navigate(["drawer/" + this.idRouting + "/documentoDiSpesa/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idDocSpesa, params]);
      } else {
        let params = this.activatedRoute.snapshot.paramMap.get('integrativa') ?
          { current: this.current, integrativa: true }
          : { current: this.current };
        this.router.navigate(["drawer/" + this.idRouting + "/documentoDiSpesa/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idDocSpesa, params]);
      }
    } else {
      let url = `drawer/${this.idRouting}/documentoDiSpesa/${this.idProgetto}/${this.idBandoLinea}`;
      url += this.activatedRoute.snapshot.paramMap.get('integrativa') ? ';integrativa=true' : '';
      this.router.navigateByUrl(url);
    }
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
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

  showMessageWarning(msg: string, tooltip?: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
    if (tooltip) {
      this.isTooltipWarningVisible = true;
      this.tooltipWarning = tooltip;
    }
    const element = document.querySelector('#scrollIdWarn');
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
    this.tooltipWarning = null;
    this.isTooltipWarningVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedInizializzazione || !this.loadedTipologie || !this.loadedQualifiche || !this.loadedCerca || !this.loadedElimina || !this.loadedSaveAllegati || !this.loadedDownload) {
      return true;
    }
    return false;
  }

  ngAfterViewChecked() {
    if (this.dataSource) {
      const skip = this.paginator.pageSize * this.paginator.pageIndex;
      let itemsShowed = this.dataSource.sortData(this.dataSource.filteredData, this.dataSource.sort).filter((u, i) => i >= skip)
        .filter((u, i) => i < this.paginator.pageSize);
      if (itemsShowed.find(f => f.isCFInvalid || f.isAltroCodInvalid) != undefined) {
        if (!this.isMessageWarningVisible) {
          this.showMessageWarning("ATTENZIONE! Alcuni codici, evidenziati nell’elenco, risultano formalmente non corretti o non ammissibili. È necessario provvedere alla rettifica dei dati.",
            itemsShowed[0].idTipoSoggetto === Constants.ID_TIPO_FORNITORE_PERSONA_GIURIDICA ?
              "Il Codice Fiscale, se presente, può solo essere costituito da 11 caratteri numerici o 16 caratteri alfanumerici. In alternativa, è possibile indicare un altro codice identificativo (max 30 caratteri alfanumerici)."
              :
              "Il Codice Fiscale è obbligatorio per le persone fisiche."
          );
        }
      } else {
        this.resetMessageWarning();
      }
      this.cdRef.detectChanges();
    }
  }
}


export class FornitoriDatasource extends MatTableDataSource<Fornitore> {

  _orderData(data: Fornitore[]): Fornitore[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "id";
    }
    let direction = this.sort.direction || this.sort.start;
    let sortedData = null;

    // viene riordinato in ordine ascendente
    switch (this.sort.active) {
      case "codiceFiscale":
        sortedData = data.sort((fornA, fornB) => {
          return fornA.codiceFiscaleFornitore.localeCompare(fornB.codiceFiscaleFornitore);
        });
        break;
      case "denominazione":
        sortedData = data.sort((fornA, fornB) => {
          return fornA.denominazioneFornitore.localeCompare(fornB.denominazioneFornitore);
        });
        break;
      case "cognome":
        sortedData = data.sort((fornA, fornB) => {
          return fornA.cognomeFornitore.localeCompare(fornB.cognomeFornitore);
        });
        break;
      case "nome":
        sortedData = data.sort((fornA, fornB) => {
          return fornA.nomeFornitore.localeCompare(fornB.nomeFornitore);
        });
        break;
      case "pIvaAltroCodice":
        sortedData = data.sort((fornA, fornB) => {
          let codA = fornA.partitaIvaFornitore ? fornA.partitaIvaFornitore : fornA.altroCodice;
          let codB = fornB.partitaIvaFornitore ? fornB.partitaIvaFornitore : fornB.altroCodice;
          return codA ? codA.localeCompare(codB) : 1;
        });
        break;
      default:
        if (data && data[0] && data[0].cognomeFornitore) {
          sortedData = data.sort((fornA, fornB) => {
            return fornA.cognomeFornitore.localeCompare(fornB.cognomeFornitore);
          });
        } else {
          sortedData = data.sort((fornA, fornB) => {
            return fornA.denominazioneFornitore.localeCompare(fornB.denominazioneFornitore);
          });
        }
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: Fornitore[]) {
    super(data);
  }
}
