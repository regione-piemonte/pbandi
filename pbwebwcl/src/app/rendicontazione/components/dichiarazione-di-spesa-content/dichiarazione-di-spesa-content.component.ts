import { AfterContentChecked, ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { ArchivioFileDialogComponent, ArchivioFileService } from '@pbandi/common-lib';
import { saveAs } from 'file-saver-es';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { DecodificaDTO } from '../../commons/dto/decodifica-dto';
import { DocumentoAllegatoDTO } from '../../commons/dto/documento-allegato-dto';
import { DocumentoDiSpesa } from '../../commons/dto/documento-di-spesa';
import { EsitoOperazioneVerificaDichiarazioneSpesa } from '../../commons/dto/esito-operazione-verifica-dichiarazione-spesa';
import { InizializzaInvioDichiarazioneDiSpesaDTO } from '../../commons/dto/inizializza-invio-dichiarazione-di-spesa-dto';
import { TipoAllegatoDTO } from '../../commons/dto/tipo-allegato-dto';
import { AssociaFilesRequest } from '../../commons/requests/associa-files-request';
import { DichiarazioneDiSpesaService } from '../../services/dichiarazione-di-spesa.service';
import { NavigationDichiarazioneDiSpesaService } from '../../services/navigation-dichiarazione-di-spesa.service';

@Component({
  selector: 'app-dichiarazione-di-spesa-content',
  templateUrl: './dichiarazione-di-spesa-content.component.html',
  styleUrls: ['./dichiarazione-di-spesa-content.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DichiarazioneDiSpesaContentComponent implements OnInit, AfterContentChecked {

  @Input() idProgetto: number;
  @Input() idBandoLinea: number;
  @Input() isBandoCultura: boolean;
  @Input() user: UserInfoSec;
  @Input() esitoVerificaDichiarazioneSpesa: EsitoOperazioneVerificaDichiarazioneSpesa;
  @Input() initDTO: InizializzaInvioDichiarazioneDiSpesaDTO;
  @Input() tipoDichiarazione: string;
  @Input() tipoDichNotFinale: boolean;
  @Input() isVerificaVisible: boolean;
  @Input() importoRichiestoErogazioneSaldoAmmesso: boolean;
  @Input() isBR56: boolean;
  @Input() isBR58: boolean;

  @Output("messageSuccess") messageSuccess = new EventEmitter<string>();
  @Output("messageError") messageError = new EventEmitter<string>();
  @Output("resetMessagesSuccessError") resetMessagesSuccessError = new EventEmitter<boolean>();

  allegatoGenerico: boolean;
  uploadRelazioneTecnicaAmmesso: boolean;
  allegatiAmmessi: boolean;
  taskVisibile: boolean;
  numDocSpesaEsitoPositivo: number;
  numDocSpesaEsitoNegativo: number;
  documenti: Array<DocumentoDiSpesa>;
  documentiEsitoNegativo: Array<DocumentoDiSpesa>;
  allegati: Array<DocumentoAllegatoDTO>;
  tipoAllegati: Array<TipoAllegatoDTO>;
  isNoAllegati: boolean;
  rappresentantiLegali: Array<DecodificaDTO>;
  rappresentanteLegaleSelected: DecodificaDTO;
  delegati: Array<DecodificaDTO>;
  delegatoSelected: DecodificaDTO;
  iban: string;
  ibanConfermato: boolean;
  tipoInvioDS: string;
  relazioneTecnica: File;
  isAllegatiVisible: boolean;
  isTipoAllegatiVisible: boolean;
  isRappresentantiVisible: boolean;
  isDelegatiVisible: boolean;
  isAnteprimaVisible: boolean;
  importoRichiestaErog: number;
  importoRichiestaErogFormatted: string;
  osservazioni: string;
  idRouting: number;

  //Variabili dopo modifica
  isBeneficiarioPrivatoCittadino: Boolean;
  beneficiario: string;
  cfBeneficiario: string;


  displayedColumns: string[];
  displayedColumnsNoTasks: string[] = ['tipologia', 'data', 'numero', 'fornitore', 'esito', 'azioni'];
  displayedColumnsTasks: string[] = ['tipologia', 'data', 'numero', 'task', 'fornitore', 'esito', 'azioni'];
  dataSource: DocumentiDatasource;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild("rappresentanteForm", { static: true }) rappresentanteForm: NgForm;
  @ViewChild("importoForm", { static: true }) importoForm: NgForm;

  messageWarning: string;
  isMessageWarningVisible: boolean;

  //LOADED
  loadedDownload: boolean = true;
  loadedElimina: boolean = true;
  loadedBeneficiario: boolean = true;
  loadedRappresentantiLegali: boolean = true;
  loadedDelegati: boolean = true;
  loadedIban: boolean = true;
  loadedTipoAllegati: boolean = true;
  loadedAllegati: boolean = true;
  loadedAssociaAllegati: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private cdRef: ChangeDetectorRef,
    private handleExceptionService: HandleExceptionService,
    private dichiarazioneDiSpesaService: DichiarazioneDiSpesaService,
    private navigationService: NavigationDichiarazioneDiSpesaService,
    private archivioFileService: ArchivioFileService,
    private sharedService: SharedService,
    private configService: ConfigService,
    private dialog: MatDialog,
    private _adapter: DateAdapter<any>
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
  }

  loadData() {

    this.loadAllegati();

    this.loadedRappresentantiLegali = false;
    this.subscribers.rappresentanti = this.dichiarazioneDiSpesaService.getRappresentantiLegali(this.idProgetto).subscribe(data => {
      if (data) {
        this.rappresentantiLegali = data;
        if (this.navigationService.rappresentanteLegaleSelected) {
          this.rappresentanteLegaleSelected = this.rappresentantiLegali.find(r => r.id === this.navigationService.rappresentanteLegaleSelected.id);
        }
      }
      this.loadedRappresentantiLegali = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });



    this.loadedBeneficiario = false;
    this.subscribers.beneficiario = this.dichiarazioneDiSpesaService.getIsBeneficiarioPrivatoCittadino(this.idProgetto).subscribe(data => {
      if (data) {
        this.isBeneficiarioPrivatoCittadino = data;
        if (this.isBeneficiarioPrivatoCittadino) {
          this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
          this.cfBeneficiario = this.user.beneficiarioSelezionato.codiceFiscale;
          this.setRappresentanteLegaleSelected();
        }
      }
      this.loadedBeneficiario = true;
    }, err => {
      this.loadedBeneficiario = true;
      this.handleExceptionService.handleBlockingError(err);
    });



    this.loadedDelegati = false;
    this.subscribers.delegati = this.dichiarazioneDiSpesaService.getDelegati(this.idProgetto).subscribe(data => {
      if (data) {
        this.delegati = data;
        if (this.navigationService.delegatoSelected) {
          this.delegatoSelected = this.delegati.find(d => d.id === this.navigationService.delegatoSelected.id);
        }
      }
      this.loadedDelegati = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });


    this.loadedIban = false;
    this.subscribers.iban = this.dichiarazioneDiSpesaService.getIban(this.idProgetto, this.user.beneficiarioSelezionato.idBeneficiario).subscribe(data => {
      if (data) {
        this.iban = data;
      }
      this.loadedIban = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });


    this.loadedTipoAllegati = false;
    this.subscribers.tipoAllegati = this.dichiarazioneDiSpesaService.getTipoAllegati(this.idProgetto, this.idBandoLinea, this.tipoDichiarazione, null).subscribe(data => {
      if (data) {
        this.tipoAllegati = data;
        if (this.tipoAllegati.length > 0) {
          this.tipoAllegati.forEach((t, i) => {
            t.idTipoAllegato = i;
            t.hasCheckbox = !t.descTipoAllegato.startsWith("<b>");
            if (t.hasCheckbox && t.flagAllegato === "S") {
              t.isChecked = true;
            }
            if (t.descTipoAllegato.includes("class='noallegati'") && t.isChecked) {
              this.isNoAllegati = true;
            }
          });
          if (this.isNoAllegati) {
            this.tipoAllegati.forEach(t => {
              if (!t.isChecked) {
                t.isDisabled = true;
              }
            });
          }
        }
      }
      this.loadedTipoAllegati = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }


  setRappresentanteLegaleSelected() {
    var idRappresentanteLegale = this.user.beneficiarioSelezionato.idBeneficiario
    var descrizioneRappresentanteLegale = this.user.beneficiarioSelezionato.denominazione
    var descrizioneBreveRappresentanteLegale = null

    this.rappresentanteLegaleSelected = new DecodificaDTO(idRappresentanteLegale, descrizioneRappresentanteLegale, descrizioneBreveRappresentanteLegale);
    //this.rappresentanteLegaleSelected.descrizione = this.user.beneficiarioSelezionato.denominazione
    //this.rappresentanteLegaleSelected.id = this.user.beneficiarioSelezionato.idBeneficiario
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.initDTO) {
      this.allegatoGenerico = this.initDTO.regolaBR53attiva;
      this.allegatiAmmessi = this.initDTO.allegatiAmmessi;
      this.uploadRelazioneTecnicaAmmesso = this.initDTO.uploadRelazioneTecnicaAmmesso;
      this.taskVisibile = this.initDTO.taskVisibile;
    }

    /*
    if (this.user) {
      this.loadData();
    }*/

    if (changes.tipoDichiarazione && this.tipoDichiarazione) {
      this.rappresentanteLegaleSelected = null;
      if (this.rappresentanteForm.form.get("rappresentanteLegale")) {
        this.rappresentanteForm.form.get("rappresentanteLegale").setValue(null);
        this.rappresentanteForm.form.get("rappresentanteLegale").markAsUntouched();
      }
      this.delegatoSelected = null;
      this.ibanConfermato = false;
      this.relazioneTecnica = null;
      this.importoRichiestaErog = null;
      this.importoRichiestaErogFormatted = null;
      this.osservazioni = null;
    }
    if (changes.esitoVerificaDichiarazioneSpesa && this.esitoVerificaDichiarazioneSpesa) {
      this.resetMessageWarning();
      this.documenti = this.esitoVerificaDichiarazioneSpesa.documentiDiSpesa;
      if (this.documenti.find(d => d.tipoInvio === "C") !== undefined) {
        this.tipoInvioDS = "C";
      }
      let neg = this.documenti.filter(d => d.motivazione);
      this.documentiEsitoNegativo = neg;
      this.numDocSpesaEsitoNegativo = neg ? neg.length : 0;
      let pos = this.documenti.filter(d => !d.motivazione);
      this.numDocSpesaEsitoPositivo = pos ? pos.length : 0;
      this.displayedColumns = this.taskVisibile ? this.displayedColumnsTasks : this.displayedColumnsNoTasks;
      this.dataSource = new DocumentiDatasource(this.documentiEsitoNegativo);
      if (this.navigationService.tipoDichiarazioneSelezionato) {
        this.ripristinaSortPaginator();
      } else {
        if (this.paginator) {
          this.paginator.length = this.documentiEsitoNegativo.length;
          this.paginator.pageIndex = 0;
          this.dataSource.paginator = this.paginator;
        }
        this.setSort();
        this.dataSource.sort = this.sort;
      }
      this.isTipoAllegatiVisible = false;
      this.isAllegatiVisible = false;
      this.isRappresentantiVisible = false;
      this.isDelegatiVisible = false;
      this.isAnteprimaVisible = true;
      if (this.esitoVerificaDichiarazioneSpesa.esito) {
        this.isRappresentantiVisible = true;
      }
      const messageInvioCartaceo: string = "Attenzione! La presenza di documenti per i quali è stato definito un invio extra procedura (ovvero con allegati non digitali) preclude la possibilità di inviare la Dichiarazione di spesa per via telematica mediante l'attuale procedura.";
      if (!this.tipoDichNotFinale) {
        this.importoRichiestaErog = this.navigationService.lastImportoRichiestaErog;
        this.osservazioni = this.navigationService.lastOsservazioni;
      }
      if (this.allegatiAmmessi || this.allegatoGenerico) {
        if (this.isRappresentantiVisible) {
          this.isAllegatiVisible = true;
          this.isTipoAllegatiVisible = true;
          this.isDelegatiVisible = true;
        }
        if (this.tipoInvioDS === 'C') {
          this.isAllegatiVisible = false;
          this.showMessageWarning(messageInvioCartaceo);
        }
      } else {
        //this.isTipoAllegatiVisible = true;
        this.isDelegatiVisible = true;
      }
    }
  }

  loadAllegati() {
    this.loadedAllegati = false;
    this.subscribers.allegati = this.dichiarazioneDiSpesaService.getAllegatiDichiarazioneDiSpesaPerIdProgetto(this.idProgetto, this.tipoDichiarazione).subscribe(data => {
      if (data) {
        this.allegati = data;
      } else {
        this.allegati = new Array<DocumentoAllegatoDTO>();
      }
      this.loadedAllegati = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  onChangeTipoAllegato(tipoAllegato: TipoAllegatoDTO) {
    if (tipoAllegato.descTipoAllegato.includes("class='noallegati'")) {
      this.tipoAllegati.forEach(t => {
        if (t.idTipoAllegato !== tipoAllegato.idTipoAllegato) {
          if (tipoAllegato.isChecked) {
            t.isChecked = false;
            t.isDisabled = true;
          } else {
            t.isDisabled = false;
          }
        }
      });
    }
  }

  aggiungiAllegati() {
    this.resetMessages();
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegati: this.allegati,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded()
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res && res.length > 0) {
        this.loadedAssociaAllegati = false;
        this.subscribers.associa = this.dichiarazioneDiSpesaService.associaAllegatiADichiarazioneDiSpesa(
          new AssociaFilesRequest(res.map(r => +r.idDocumentoIndex), null, this.idProgetto), this.tipoDichiarazione).subscribe(data => {
            if (data) {
              if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
                && (!data.elencoIdDocIndexFilesNonAssociati || data.elencoIdDocIndexFilesNonAssociati.length === 0)) { //tutti associati
                this.showMessageSuccess("Tutti gli allegati sono stati associati correttamente alla dichiarazione.");
                this.loadAllegati();
              } else if (data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0
                && (!data.elencoIdDocIndexFilesAssociati || data.elencoIdDocIndexFilesAssociati.length === 0)) { //tutti non associati
                this.showMessageError("Errore nell'associazione degli allegati alla dichiarazione.");
              } else if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
                && data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0) { //alcuni associati e alcuni non associati
                this.loadAllegati();
                let message = "Errore nell'associazione dei seguenti allegati alla dichiarazione: ";
                data.elencoIdDocIndexFilesNonAssociati.forEach(id => {
                  let allegato = this.allegati.find(a => a.id === id);
                  message += allegato.nome + ", ";
                });
                message = message.substr(0, message.length - 2);
                this.showMessageError(message);
              }
            }
            this.loadedAssociaAllegati = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.loadedAssociaAllegati = true;
            this.showMessageError("Errore nell'associazione degli allegati alla dichiarazione.")
          });
      }
    });
  }

  downloadAllegato(idDocumentoIndex: string, nomeDocumento: string) {
    this.resetMessages();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file.");
      this.loadedDownload = true;
    });
  }

  disassociaAllegato(idDocumentoIndex: number) {
    this.resetMessages();
    this.loadedElimina = false;
    this.subscribers.disassociaAllegato = this.dichiarazioneDiSpesaService.disassociaAllegatoDichiarazioneDiSpesa(this.idProgetto, idDocumentoIndex, null, this.tipoDichiarazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess(data.messaggio);
          this.allegati = this.allegati.filter(a => a.id !== idDocumentoIndex);
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedElimina = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nella disassociazione dell'allegato.");
      this.loadedElimina = true;
    });
  }

  handleFileInput(file: File) {
    this.relazioneTecnica = file;
  }

  setImportoRichiestaErog() {
    this.importoRichiestaErog = this.sharedService.getNumberFromFormattedString(this.importoRichiestaErogFormatted);
    if (this.importoRichiestaErog !== null) {
      this.importoRichiestaErogFormatted = this.sharedService.formatValue(this.importoRichiestaErog.toString());
    }
  }

  saveDataForNavigation() {
    this.navigationService.tipoDichiarazioneSelezionato = this.tipoDichiarazione;
    this.navigationService.lastIsVerificaVisible = this.isVerificaVisible;

    if (this.rappresentanteLegaleSelected) {
      this.navigationService.rappresentanteLegaleSelected = this.rappresentanteLegaleSelected;
    }
    if (this.delegatoSelected) {
      this.navigationService.delegatoSelected = this.delegatoSelected;
    }
    if (this.tipoDichiarazione === 'F') {
      this.navigationService.lastImportoRichiestaErog = this.importoRichiestaErog;
      this.navigationService.lastOsservazioni = this.osservazioni;
    }
    this.salvaSortPaginator();
  }

  salvaSortPaginator() {
    if (this.dataSource && this.dataSource.data && this.dataSource.data.length) {
      if (this.dataSource.sort) {
        this.navigationService.sortDirectionTable = this.dataSource.sort.direction;
        this.navigationService.sortActiveTable = this.dataSource.sort.active;
      }
      if (this.dataSource.paginator) {
        this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
        this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
      }
    }
  }

  ripristinaSortPaginator() {
    if (this.navigationService.sortDirectionTable != null && this.navigationService.sortDirectionTable != undefined && this.sort) {
      this.sort.direction = this.navigationService.sortDirectionTable;
      this.sort.active = this.navigationService.sortActiveTable;
      this.setSort();
      this.dataSource.sort = this.sort;
    }
    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined && this.paginator) {
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
          return item.codiceFiscaleFornitore;
        case "task":
          return item.task;
        default: return item[property];
      }
    };
  }

  goToModificaDocumento(idDocumento: number) {

    this.saveDataForNavigation();
    let params = this.activatedRoute.snapshot.paramMap.get('integrativa') ?
      { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA, from: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DICHIARAZIONE_DI_SPESA, integrativa: true } :
      { current: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA, from: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DICHIARAZIONE_DI_SPESA };
    this.router.navigate(["drawer/" + this.idRouting + "/documentoDiSpesa", this.idProgetto, this.idBandoLinea, idDocumento, params]);
  }


  showMessageSuccess(msg: string) {
    this.messageSuccess.emit(msg);
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError.emit(msg);
  }

  resetMessages() {
    this.resetMessagesSuccessError.emit(true);
  }

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  ngAfterContentChecked() {
    this.cdRef.detectChanges();
  }
}

export class DocumentiDatasource extends MatTableDataSource<DocumentoDiSpesa> {
  constructor(data: DocumentoDiSpesa[]) {
    super(data);
  }
}
