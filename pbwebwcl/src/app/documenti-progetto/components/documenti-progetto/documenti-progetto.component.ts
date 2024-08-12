import { HandleExceptionService } from './../../../core/services/handle-exception.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DocumentiProgettoService } from '../../services/documenti-progetto.service';
import { FormControl, FormGroup } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { CodiceDescrizioneDTO } from 'src/app/gestione-spesa-validata/commons/dto/codice-descrizione-dto';
import { DocumentoIndexDTO } from '../../commons/dto/documento-index-dto';
import { FiltroRicercaDocumentiDTO } from '../../commons/dto/filtro-ricerca-documenti-dto';
import { saveAs } from 'file-saver-es';
import { AllegatiDichiarazioneSpesaDialogComponent } from 'src/app/validazione/components/allegati-dichiarazione-spesa-dialog/allegati-dichiarazione-spesa-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { UploadDocumentoFirmatoDialogComponent } from '../upload-documento-firmato-dialog/upload-documento-firmato-dialog.component';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { AllegatiDichiarazioneDiSpesaDTO } from 'src/app/rendicontazione/commons/dto/allegati-dichiarazione-di-spesa-dto';
import { UploadDocumentiProgettoDialogComponent } from '../upload-documenti-progetto-dialog/upload-documenti-progetto-dialog.component';
import { DichiarazioneDiSpesaService } from 'src/app/rendicontazione/services/dichiarazione-di-spesa.service';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { AnteprimaDialogComponent, ArchivioFileService, InfoDocumentoVo } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { LinkDocumentiActaDialogComponent } from '../link-documenti-acta-dialog/link-documenti-acta-dialog.component';
import { FileFirmato } from '../../commons/dto/file-firmato';
import { ProgettoBenenficiarioDTO } from '../../commons/dto/progetto-beneficiario-dto';
import { Documento } from '../../commons/dto/documento';
import { SharedService } from 'src/app/shared/services/shared.service';
import * as moment from "moment";


@Component({
  selector: 'app-documenti-progetto',
  templateUrl: './documenti-progetto.component.html',
  styleUrls: ['./documenti-progetto.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DocumentiProgettoComponent implements OnInit, AfterViewChecked {

  idProgetto: number;
  user: UserInfoSec;
  criteriRicercaOpened: boolean = true;
  isBeneficiario: boolean;
  isIstruttore: boolean;
  isAltro: boolean;
  isIstrProg: boolean;
  isIstrLinkActa: boolean;
  tipiDocumentoIndexUploadable: Array<CodiceDescrizioneDTO>;
  categorieAnagrafica: Array<CodiceDescrizioneDTO>;
  isVisible: boolean;

  beneficiari: Array<CodiceDescrizioneDTO>;
  beneficiarioSelected: FormControl = new FormControl();
  beneficiarioGroup: FormGroup = new FormGroup({ beneficiarioControl: new FormControl() });
  filteredOptionsBen: Observable<CodiceDescrizioneDTO[]>;

  progetti: Array<ProgettoBenenficiarioDTO>;
  progettoSelected: FormControl = new FormControl();
  progettoGroup: FormGroup = new FormGroup({ progettoControl: new FormControl() });
  filteredOptionsProg: Observable<ProgettoBenenficiarioDTO[]>;

  tipiDocumento: Array<CodiceDescrizioneDTO>;
  tipoDocumentoSelected: CodiceDescrizioneDTO;

  dataDa: FormControl = new FormControl();
  dataA: FormControl = new FormControl();
  isDigitaliInFirmaChecked: boolean = false;
  isDigitaliInviatiChecked: boolean = false;
  dimMaxSingoloFile: number;
  estensioniConsentite: Array<string>;
  isMessageExtraProcVisible: boolean;
  progettoSearched: boolean;
  isTabDocActiveInfoVisible: boolean;

  documenti: Array<DocumentoIndexDTO>;
  documentiDomanda: Array<DocumentoIndexDTO>;
  documentiIstruttoria: Array<DocumentoIndexDTO>;
  documentiActiveInfo: Array<DocumentoIndexDTO>;
  isDocVisible: boolean;


  displayedColumns: string[];
  displayedColumnsBen: string[] = ['allegati', 'tipoDocumento', 'progetto', 'documento', 'beneficiario', 'nProtocollo', 'stato', 'invioextraproc'];
  displayedColumnsIstrProg: string[] = ['tipoDocumento', 'progetto', 'documento', 'delete', 'beneficiario', 'nProtocollo'];
  displayedColumnsAltro: string[] = ['allegati', 'tipoDocumento', 'progetto', 'documento', 'beneficiario', 'nProtocollo', 'stato'];

  dataSource: DocumentiDatasource = new DocumentiDatasource([]);

  @ViewChild('paginatorProg') paginator: MatPaginator;
  @ViewChild('sortProg') sort: MatSort;

  @ViewChild('tabs', { static: false }) tabs;
  selectedTabIndex: number;

  documentiDom: Array<Documento>;
  displayedColumnsDom: string[] = ['tipoDocumento', 'documento', 'dataMarcaTemp', 'dataVerificaFirma', 'dataProtocollo', 'numeroProtocollo'];
  dataSourceDom: MatTableDataSource<Documento> = new MatTableDataSource<Documento>([]);
  @ViewChild('paginatorDom') paginatorDom: MatPaginator;
  @ViewChild('sortDom') sortDom: MatSort;

  documentiIstr: Array<Documento>;
  displayedColumnsIstr: string[] = ['tipoDocumento', 'documento', 'dataMarcaTemp', 'dataVerificaFirma', 'dataProtocollo', 'numeroProtocollo'];
  dataSourceIstr: MatTableDataSource<Documento> = new MatTableDataSource<Documento>([]);
  @ViewChild('paginatorIstr') paginatorIstr: MatPaginator;
  @ViewChild('sortIstr') sortIstr: MatSort;

  displayedColumnsActiveInfo: string[] = ['tipoDocumento', 'progetto', 'documento', 'beneficiario', 'nProtocollo', 'stato'];
  dataSourceActiveInfo: DocumentiDatasource = new DocumentiDatasource([]);
  @ViewChild('paginatorActiveInfo') paginatorActiveInfo: MatPaginator;
  @ViewChild('sortActiveInfo') sortActiveInfo: MatSort;

  today: Date = new Date();

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedInizializzazione: boolean;
  loadedCostante: boolean;
  loadedBandoFP: boolean = true;
  loadedBeneficiari: boolean = true;
  loadedBeneficiariInit: boolean;
  loadedProgetti: boolean = true;
  loadedCerca: boolean = true;
  loadedDownload: boolean = true;
  loadedElimina: boolean = true;
  loadedAllegati: boolean = true;
  loadedIntegrazioni: boolean = true;
  loadedUpload: boolean = true;
  loadedRegola: boolean = true;
  loadedDocIstr: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};
  subscriptionBen: Subscription;

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private documentiProgettoService: DocumentiProgettoService,
    private handleExceptionService: HandleExceptionService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private dichiarazioneDiSpesaService: DichiarazioneDiSpesaService,
    private sharedService: SharedService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];

      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_BEN_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_PERSONA_FISICA) {
            this.isBeneficiario = true;
            this.displayedColumns = this.displayedColumnsBen;
          } else if (this.user.codiceRuolo === Constants.CODICE_RUOLO_OPE_ISTR_PROG) {
            this.isIstrProg = true;
            this.displayedColumns = this.displayedColumnsIstrProg;
          } else {
            this.isAltro = true;
            this.displayedColumns = this.displayedColumnsAltro;
          }
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_CERT) {
            this.isIstrLinkActa = true;
          }
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_ISTR_CREDITI) {
            this.isTabDocActiveInfoVisible = true;
          }
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_ISTRUTTORE
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADC_CERT
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_CERT
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OPE_SUP_IST
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OPE_ISTR_PROG
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ISTR_AFFIDAMENTI
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ISTR_CREDITI) {
            this.isIstruttore = true;
          }
          this.loadedInizializzazione = false;
          this.subscribers.inizializza = this.documentiProgettoService.inizializzaDocumentiDiProgetto(this.user.codiceRuolo).subscribe(data => {
            if (data) {
              //this.beneficiari = data.comboBeneficiari;
              this.tipiDocumento = data.comboTipiDocumentoIndex.filter(t => +t.codice !== Constants.ID_TIPO_DOC_INDEX_ACTIVE_INFO
                && +t.codice !== Constants.ID_TIPO_DOC_INDEX_ALLEGATI_DOMANDA && +t.codice !== Constants.ID_TIPO_DOC_INDEX_ALLEGATI_ISTRUTTORIA);
              // this.filteredOptionsBen = this.beneficiarioGroup.controls['beneficiarioControl'].valueChanges
              //   .pipe(
              //     startWith(''),
              //     map(value => typeof value === 'string' || value == null ? value : value.descrizione),
              //     map(name => name ? this._filterBen(name) : this.beneficiari.slice())
              //   );
              // if (this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.idBeneficiario) {
              //   let ben = this.beneficiari.find(b => b.codice === this.user.beneficiarioSelezionato.idBeneficiario.toString());
              //   this.beneficiarioSelected.setValue(ben);
              //   this.beneficiarioGroup.controls['beneficiarioControl'].setValue(ben);
              //   this.loadProgetti(true);
              // }
              this.dimMaxSingoloFile = data.dimMaxSingoloFile;
              this.tipiDocumentoIndexUploadable = data.comboTipiDocumentoIndexUploadable;
              this.categorieAnagrafica = data.categorieAnagrafica;
              this.estensioniConsentite = data.estensioniConsentite;
              this.loadBeneficiari(true);
            }
            this.loadedInizializzazione = true;
          }, err => {
            this.handleExceptionService.handleBlockingError(err);
          });
          this.loadedCostante = false;
          this.subscribers.costanteFin = this.userService.isCostanteFinpiemonte().subscribe(data => {
            this.isVisible = data;

            this.loadedCostante = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore durante il caricamento della costante.");
            this.loadedCostante = true;
          });
        }
      });
    });

    /*
    //Servizio per regola br50
    this.loadedRegola = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR50").subscribe(res => {
      if (res != undefined && res != null) {
        this.isBR50 = res;
      }
      this.loadedRegola = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegola = true;
    });*/


  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_DOC_PROGETTO;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  timeout: any;
  loadBeneficiari(init?: boolean) {
    this.loadedBeneficiari = false;
    if (init) {
      this.loadedBeneficiariInit = false;
    }
    if (this.beneficiarioGroup.controls['beneficiarioControl']?.value?.length >= 3 || init) {
      if (this.timeout) {
        clearTimeout(this.timeout);
        this.timeout = null;
      }
      this.timeout = setTimeout(() => {
        if (this.subscriptionBen) {
          this.subscriptionBen.unsubscribe();
        }
        let idBeneficiario = null;
        if (this.user?.beneficiarioSelezionato?.idBeneficiario && init) {
          idBeneficiario = this.user?.beneficiarioSelezionato?.idBeneficiario;
        }
        this.subscriptionBen = this.documentiProgettoService.getBeneficiariDocProgettoByDenomOrIdBen(this.beneficiarioGroup.controls['beneficiarioControl']?.value || "", idBeneficiario).subscribe(data => {
          if (data) {

            this.beneficiari = data;
            this.filteredOptionsBen = this.beneficiarioGroup.controls['beneficiarioControl'].valueChanges
              .pipe(
                startWith(''),
                map(value => typeof value === 'string' || value == null ? value : value.denominazione),
                map(name => name ? this._filterBen(name) : this.beneficiari.slice())
              );
            if (this.user?.beneficiarioSelezionato?.idBeneficiario && init) {
              let ben = this.beneficiari.find(b => b.codice === this.user.beneficiarioSelezionato.idBeneficiario.toString());
              this.beneficiarioSelected.setValue(ben);
              this.beneficiarioGroup.controls['beneficiarioControl'].setValue(ben);
              this.loadProgetti(true);
            }
          }
          this.loadedBeneficiari = true;
          if (init) {
            this.loadedBeneficiariInit = true;
          }
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }, init ? 0 : 1000);
    }
  }

  loadProgetti(isInit?: boolean) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.progetti = null;
    this.progettoSelected = new FormControl();
    this.progettoGroup.controls['progettoControl'].setValue(null);
    this.loadedProgetti = false;
    this.subscribers.progetti = this.documentiProgettoService.progettiBeneficiario(+this.beneficiarioSelected.value.codice, this.user.codiceRuolo).subscribe(data => {
      if (data) {
        this.progetti = data;

        this.filteredOptionsProg = this.progettoGroup.controls['progettoControl'].valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descrizione),
            map(name => name ? this._filterProg(name) : (this.progetti ? this.progetti.slice() : null))
          );
        if (this.idProgetto && isInit) {
          let prog = this.progetti.find(p => p.codice === this.idProgetto.toString());
          this.progettoSelected.setValue(prog);
          this.progettoGroup.controls['progettoControl'].setValue(prog);
        }
        if (isInit) {
          this.ricerca();
        }
      }
      this.loadedProgetti = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fasi di caricamento dei progetti.");
      this.loadedProgetti = true;
    });
  }

  check(type: string) {
    setTimeout(() => {
      if (type === 'B') {
        if (!this.beneficiarioSelected || (this.beneficiarioGroup.controls['beneficiarioControl'] && this.beneficiarioSelected.value !== this.beneficiarioGroup.controls['beneficiarioControl'].value)) {
          this.beneficiarioGroup.controls['beneficiarioControl'].setValue(null);
          this.beneficiarioSelected = new FormControl();
          this.progetti = null;
          this.progettoGroup.controls['progettoControl'].setValue(null);
          this.progettoSelected = new FormControl();
        }
      } else if (type === 'P') {
        if (!this.progettoSelected || (this.progettoGroup.controls['progettoControl'] && this.progettoSelected.value !== this.progettoGroup.controls['progettoControl'].value)) {
          this.progettoGroup.controls['progettoControl'].setValue(null);
          this.progettoSelected = new FormControl();
        }
      }
    }, 200);
  }

  click(event: any, type: string) {
    if (type === 'B') {
      this.beneficiarioSelected.setValue(event.option.value);
      this.loadProgetti();
    } else if (type === 'P') {
      this.progettoSelected.setValue(event.option.value);
    }
  }

  private _filterBen(descrizione: string): CodiceDescrizioneDTO[] {
    const filterValue = descrizione.toLowerCase();
    return this.beneficiari.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  private _filterProg(descrizione: string): ProgettoBenenficiarioDTO[] {
    const filterValue = descrizione.toLowerCase();
    return this.progetti.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  displayFn(element: CodiceDescrizioneDTO): string {
    return element && element.descrizione ? element.descrizione : '';
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  ricerca() {
    this.selectedTabIndex = 0;
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!this.beneficiarioSelected || !this.beneficiarioSelected.value) {
      this.showMessageError("È necessario selezionare il beneficiario.");
      return;
    }
    if ((this.dataDa.value && !this.dataA.value) || (!this.dataDa.value && this.dataA.value)) {
      this.showMessageError("I campi 'Data dal' e 'Data al' devono essere entrambi valorizzati.");
      return;
    }
    if (this.dataDa.value && this.dataA.value && new Date(this.dataDa.value) > new Date(this.dataA.value)) {
      this.showMessageError("Il campo 'Data dal' deve essere precedente al campo 'Data al'.");
      return;
    }
    this.loadedCerca = false;
    let request = new FiltroRicercaDocumentiDTO(this.beneficiarioSelected && this.beneficiarioSelected.value ? +this.beneficiarioSelected.value.codice : null,
      this.progettoSelected && this.progettoSelected.value ? +this.progettoSelected.value.codice : null, this.tipoDocumentoSelected ? +this.tipoDocumentoSelected.codice : null,
      this.dataDa.value ? new Date(this.dataDa.value) : null, this.dataA.value ? new Date(this.dataA.value) : null, this.isDigitaliInFirmaChecked, this.isDigitaliInviatiChecked,
      this.user.idSoggetto, this.user.codiceRuolo, null);
    this.subscribers.cerca = this.documentiProgettoService.cercaDocumenti(request).subscribe(data => {
      this.isMessageExtraProcVisible = false;
      if (data) {
        this.documenti = data;

        // filtro documenti dei progetti a cui il beneficiario non è abilitato, se ce ne sono
        let idProgettiBen: string[] = this.progetti.map(p => p.codice);
        this.documenti = this.documenti.filter(d => idProgettiBen?.includes(d.idProgetto));

        //filtro documenti domanda, istruttoria e finpis che vanno nei rispettivi tab
        this.documentiDomanda = this.documenti.filter(d => d.codTipoDoc === Constants.DESC_BREVE_TIPO_DOC_INDEX_ALLEGATI_DOMANDA);
        this.documentiIstruttoria = this.documenti.filter(d => d.codTipoDoc === Constants.DESC_BREVE_TIPO_DOC_INDEX_ALLEGATI_ISTRUTTORIA);
        if (this.isTabDocActiveInfoVisible) {
          this.documentiActiveInfo = this.documenti.filter(d => d.codTipoDoc === Constants.DESC_BREVE_TIPO_DOC_INDEX_ACTIVE_INFO);
          this.dataSourceActiveInfo = new DocumentiDatasource(this.documentiActiveInfo);
          this.paginatorActiveInfo.length = this.documenti.length;
          this.paginatorActiveInfo.pageIndex = 0;
          this.dataSourceActiveInfo.paginator = this.paginatorActiveInfo;
          this.dataSourceActiveInfo.sort = this.sortActiveInfo;
        }
        this.loadDocumentiDomandaIstruttoria();
        this.documenti = this.documenti.filter(d => d.codTipoDoc !== Constants.DESC_BREVE_TIPO_DOC_INDEX_ACTIVE_INFO
          && d.codTipoDoc !== Constants.DESC_BREVE_TIPO_DOC_INDEX_ALLEGATI_ISTRUTTORIA && d.codTipoDoc !== Constants.DESC_BREVE_TIPO_DOC_INDEX_ALLEGATI_DOMANDA);

        this.dataSource = new DocumentiDatasource(this.documenti);
        this.paginator.length = this.documenti.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        if (this.isBeneficiario && this.documenti.find(d => !d.timeStamped && !d.signed && (d.signable || d.signValid === false) && !d.flagTrasmDest)) {
          this.isMessageExtraProcVisible = true;
        }
        if (this.isIstrLinkActa && this.documenti.find(d => d.codTipoDoc === Constants.DESC_BREVE_TIPO_DOC_INDEX_ACTA) && !this.displayedColumns.find(d => d === "delete")) {
          this.displayedColumns.splice(4, 0, 'delete');
        }
      }
      this.isDocVisible = true;
      if (!this.isIstrLinkActa) {
        this.criteriRicercaOpenClose();
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca.");
      this.isMessageExtraProcVisible = false;
      this.loadedCerca = true;
    });

  }

  loadDocumentiDomandaIstruttoria() {
    this.progettoSearched = false;
    this.documentiDom = new Array<Documento>();
    this.documentiIstr = new Array<Documento>();
    this.dataSourceDom = new MatTableDataSource<Documento>(this.documentiDom);
    this.dataSourceIstr = new MatTableDataSource<Documento>(this.documentiIstr);
    if (this.progettoSelected?.value) {

      //aggiungo documenti recuperati con la ricerca su pbandi
      let docs = this.documentiDomanda.map(d => new Documento(d.descTipoDocumento, d.documento, d.protocollo, null, null, null, null,
        +d.idDocumentoIndex, null, d.codTipoDoc, null, null))
      this.dataSourceDom = new MatTableDataSource<Documento>(docs);
      this.setPaginatorSortDataSourceDom();
      docs = this.documentiIstruttoria.map(d => new Documento(d.descTipoDocumento, d.documento, d.protocollo, null, null, null, null,
        +d.idDocumentoIndex, null, d.codTipoDoc, null, null))
      this.dataSourceIstr = new MatTableDataSource<Documento>(docs);
      this.setPaginatorSortDataSourceIstr();

      this.progettoSearched = true;
      let num: string = this.progettoSelected.value.numeroDomanda;
      if (num?.toUpperCase().startsWith("FD") || num?.toUpperCase().startsWith("PNRR")) {
        if (num?.toUpperCase().startsWith("FD")) {
          num = num.replace(/[a-zA-Z]/g, '');
        }
        this.loadedDocIstr = false;
        this.subscribers.getDocumentoListFindom = this.documentiProgettoService.getDocumentoListFindom(num).subscribe(data => {
          if (data) {
            if (data.esitoPositivo) {
              if (data.documentiList?.length) {
                this.documentiDom = data.documentiList.filter(d => d.fonte === "DOC BENEFICIARIO" || d.fonte === "DOC BENEFICIARIO PNRR");
                this.documentiIstr = data.documentiList.filter(d => d.fonte === "DOC ISTRUTTORIA"); //manca l'estensione nel nome del file

                if (this.documentiDom?.length) {
                  docs = this.dataSourceDom.data;
                  docs.push(...this.documentiDom);
                  this.dataSourceDom = new MatTableDataSource<Documento>(docs);
                  this.setPaginatorSortDataSourceDom();
                }
                if (this.documentiIstr?.length && this.docIstruttoriaVisible()) {
                  docs = this.dataSourceIstr.data;
                  docs.push(...this.documentiIstr);
                  this.dataSourceIstr = new MatTableDataSource<Documento>(docs);
                  this.setPaginatorSortDataSourceIstr();
                }
              }
            } else {
              let success: string = "";
              let error: string = "";
              for (let m of data.apiMessages) {
                if (m.error) {
                  error += m.message + ", ";
                } else {
                  success += m.message + ", ";
                }
              }
              if (error.length) {
                error = error.substring(0, error.length - 1);
                this.showMessageError(error);
              }
              if (success.length) {
                success = success.substring(0, success.length - 1);
                this.showMessageSuccess(success);
              }
            }
          }
          this.loadedDocIstr = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento dei documenti della domanda.");
          this.loadedDocIstr = true;
        });
      }
    }
  }

  setPaginatorSortDataSourceDom() {
    setTimeout(() => {
      if (this.paginatorDom) {
        this.paginatorDom.length = this.dataSourceDom.data.length;
        this.paginatorDom.pageIndex = 0;
        this.dataSourceDom.paginator = this.paginatorDom;
        this.dataSourceDom.sort = this.sortDom;
        this.dataSourceDom.sortingDataAccessor = (item, property) => {
          switch (property) {
            case "dataMarcaTemp":
              if (item.dataMarcaturaTemporale) {
                let dt = this.sharedService.parseDate(item.dataMarcaturaTemporale);
                if (dt) {
                  return dt.getTime();
                }
              }
              return "";
            case "dataVerificaFirma":
              if (item.dataVerificaFirma) {
                let dt = this.sharedService.parseDate(item.dataVerificaFirma);
                if (dt) {
                  return dt.getTime();
                }
              }
              return "";
            case "dataProtocollo":
              if (item.dataProtocollo) {
                let dt = this.sharedService.parseDate(item.dataProtocollo);
                if (dt) {
                  return dt.getTime();
                }
              }
              return "";
            default: return item[property];
          }
        };
      }
    }, 0);
  }

  setPaginatorSortDataSourceIstr() {
    setTimeout(() => {
      if (this.paginatorIstr) {
        this.paginatorIstr.length = this.dataSourceIstr.data.length;
        this.paginatorIstr.pageIndex = 0;
        this.dataSourceIstr.paginator = this.paginatorIstr;
        this.dataSourceIstr.sort = this.sortIstr;
        this.dataSourceIstr.sortingDataAccessor = (item, property) => {
          switch (property) {
            case "dataMarcaTemp":
              if (item.dataMarcaturaTemporale) {
                let dt = this.sharedService.parseDate(item.dataMarcaturaTemporale);
                if (dt) {
                  return dt.getTime();
                }
              }
              return "";
            case "dataVerificaFirma":
              if (item.dataVerificaFirma) {
                let dt = this.sharedService.parseDate(item.dataVerificaFirma);
                if (dt) {
                  return dt.getTime();
                }
              }
              return "";
            case "dataProtocollo":
              if (item.dataProtocollo) {
                let dt = this.sharedService.parseDate(item.dataProtocollo);
                if (dt) {
                  return dt.getTime();
                }
              }
              return "";
            default: return item[property];
          }
        };
      }
    }, 0);
  }

  docIstruttoriaVisible() {
    if (this.user && this.progettoSearched
      && this.user.codiceRuolo !== Constants.CODICE_RUOLO_BENEFICIARIO
      && this.user.codiceRuolo !== Constants.CODICE_RUOLO_BEN_MASTER
      && this.user.codiceRuolo !== Constants.CODICE_RUOLO_PERSONA_FISICA
    ) {
      return true;
    }
    return false;
  }

  isAnteprimaVisible(nomeFile: string, codTipoDoc: string) { //duplicato di isFileWithPreview dentro AnteprimaDialogComponent
    if (codTipoDoc === Constants.DESC_BREVE_TIPO_DOC_INDEX_ACTA) {
      return false;
    }
    const splitted = nomeFile.split(".");
    if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "PDF" || splitted[splitted.length - 1] == "xml" || splitted[splitted.length - 1] == "XML" || splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M") {
      return true;
    } else {
      return false;
    }
  }

  downloadDocDomandaIstruttoria(item: Documento) {
    if (item.fonte?.length > 0) {
      this.loadedDownload = false;
      this.documentiProgettoService.downloadDocumentoFindom(item.idDocumentoIndex, item.fonte).subscribe(data => {
        if (data) {
          saveAs(new Blob([data.body]), item.nomeFile);
        }
        this.loadedDownload = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download del file");
        this.loadedDownload = true;
      });
    } else {
      let doc = new DocumentoIndexDTO(null, null, null, null, null, null, null, null, null, null, item.nomeFile, null, null, null, item.idDocumentoIndex.toString(),
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
      this.downloadAllegato(doc);
    }
  }

  openUploadDocumentiProgetto(type: string) {
    let tipoDoc: CodiceDescrizioneDTO;
    if (type === "ISTR") {
      tipoDoc = this.tipiDocumentoIndexUploadable.find(t => +t.codice === Constants.ID_TIPO_DOC_INDEX_ALLEGATI_ISTRUTTORIA);
    } else if (type === "DOM") {
      tipoDoc = this.tipiDocumentoIndexUploadable.find(t => +t.codice === Constants.ID_TIPO_DOC_INDEX_ALLEGATI_DOMANDA);
    }
    let dialogRef = this.dialog.open(UploadDocumentiProgettoDialogComponent,
      {
        width: '50%',
        data: {
          tipoDocumento: tipoDoc,
          codiceProgetto: this.progettoSelected.value.descrizione,
          //categorieAnagrafica: this.categorieAnagrafica,
          dimMaxSingoloFile: this.dimMaxSingoloFile,
          estensioniConsentite: this.estensioniConsentite,

        }
      });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.loadedUpload = false;
        let cat: string = "";
        this.categorieAnagrafica.forEach((c, i, array) => {
          cat += c.codice;
          if (i < array.length - 1) {
            cat += ",";
          }
        });
        this.subscribers.uploadDocProg = this.documentiProgettoService.salvaUpload(this.user.codiceRuolo, +res.tipoDocumento.codice, +this.progettoSelected.value.codice,
          cat, res.file).subscribe(data => {
            if (data) {
              this.ricerca();
              this.showMessageSuccess("Salvataggio del file eseguito con successo.");
            } else {
              this.showMessageError("Errore in fase di upload del file.");
            }
            this.loadedUpload = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di upload del file.");
            this.loadedDownload = true;
          });
      }
    })
  }

  openLinkDocActa() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let dialogRef = this.dialog.open(LinkDocumentiActaDialogComponent, {
      width: '50%',
      data: {
        idProgetto: +this.progettoSelected.value.codice
      }
    });
    dialogRef.afterClosed().subscribe(messaggio => {
      if (messaggio) {
        this.ricerca();
        this.showMessageSuccess(messaggio);
      }
    })
  }

  getCodTipoDocVerbaleChecklist(codTipoChecklist: string) {
    switch (codTipoChecklist) {
      case "CLIL":
        return "VCV";
      case "CL":
        return "VCD";
      case "CLILA":
        return "VCVA";
      case "CLA":
        return "VCDA";
      default:
        return "";
    }
  }

  openAllegati(doc: DocumentoIndexDTO, messageSuccess?: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAllegati = false;
    if (doc.codTipoDoc === "CLIL" || doc.codTipoDoc === "CLILA" || doc.codTipoDoc === "CL" || doc.codTipoDoc === "CLA") {
      //this.subscribers.integrazioni = this.documentiProgettoService.allegatiVerbaleChecklist(+doc.idTarget,doc.codTipoDoc === "CL" ? "VCD" : "VCV").subscribe(data => {
      this.subscribers.integrazioni = this.documentiProgettoService.allegatiVerbaleChecklist(+doc.idTarget, this.getCodTipoDocVerbaleChecklist(doc.codTipoDoc),
        +doc.idDocumentoIndex).subscribe(data => {
          let allegati = new AllegatiDichiarazioneDiSpesaDTO(data, null);
          let dialogRef = this.dialog.open(AllegatiDichiarazioneSpesaDialogComponent,
            {
              width: '50%',
              maxHeight: '90%',
              data: {
                allegati: allegati,
                title: doc.codTipoDoc === "CL" || doc.codTipoDoc === "CLA" ? `Documenti allegati alla Checklist di Validazione ${doc.idTarget}`
                  : `Verbali allegati alla Checklist in Loco ${doc.idTarget}`,
                nascondiSizeFile: true,
                aggiungiAllegatiEnabled: true,
                codTipoChecklist: doc.codTipoDoc,
                idDocumentoIndexChecklist: doc.idDocumentoIndex,
                idProgetto: +doc.idProgetto,
                messageSuccess: messageSuccess
              }
            });
          dialogRef.afterClosed().subscribe(res => {
            if (res && res.length > 0) {
              this.openAllegati(doc, res);
            }
          });
          this.loadedAllegati = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore nel recupero degli allegati.");
          this.loadedAllegati = true;
        });
    } else if (doc.codTipoDoc === "DS" || doc.codTipoDoc === "CFP") {
      //nuova gestione allegati DS e CFP come in validazione
      this.loadedBandoFP = false;
      this.subscribers.bandoFin = this.userService.isBandoCompetenzaFinpiemonte(+doc.idBandoLinea).subscribe(data => {
        let isFP = data;

        if (this.isVisible || !isFP) {
          this.subscribers.allegatiDichiarazione = this.dichiarazioneDiSpesaService.getAllegatiDichiarazioneDiSpesaIntegrazioni(+doc.idProgetto, +doc.idTarget).subscribe(data => {
            if (data) {
              this.visualizzaAllegatiDS(doc, data);
            }
            this.loadedAllegati = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore nel recupero degli allegati.");
            this.loadedAllegati = true;
          });
        } else {
          this.subscribers.allegatiDichiarazione = this.dichiarazioneDiSpesaService.getAllegatiDichiarazioneDiSpesa(+doc.idProgetto, +doc.idTarget).subscribe(data => {
            if (data) {
              this.visualizzaAllegatiDS(doc, data);
            }
            this.loadedAllegati = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore nel recupero degli allegati.");
            this.loadedAllegati = true;
          });
        }
        this.loadedBandoFP = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore durante la verifica del bando competenza FP.");
        this.loadedBandoFP = true;
      });
      // vecchia gestione allegati DS e CFP, commentata il 18-12-2023
      // this.subscribers.allegati = this.documentiProgettoService.allegati(+doc.idDocumentoIndex, doc.codTipoDoc).subscribe(data1 => {
      //   if (data1) {
      //     this.loadedIntegrazioni = false;
      //     this.subscribers.integrazioni = this.documentiProgettoService.allegatiIntegrazioniDS(+doc.idTarget, doc.codTipoDoc).subscribe(data2 => {
      //       let allegati = new AllegatiDichiarazioneDiSpesaDTO(data1, data2);
      //       this.dialog.open(AllegatiDichiarazioneSpesaDialogComponent,
      //         {
      //           width: '50%',
      //           maxHeight: '90%',
      //           data: {
      //             allegati: allegati,
      //             title: doc.codTipoDoc === 'DS' ? `Allegati alla Dichiarazione di Spesa ${doc.idTarget}` : `Allegati alla Comunicazione di Fine Progetto ${doc.idTarget}`
      //           }
      //         });
      //       this.loadedIntegrazioni = true;
      //     }, err => {
      //       this.handleExceptionService.handleNotBlockingError(err);
      //       this.showMessageError("Errore nel recupero degli allegati.");
      //       this.loadedIntegrazioni = true;
      //     });
      //   }
      //   this.loadedAllegati = true;
      // }, err => {
      //   this.handleExceptionService.handleNotBlockingError(err);
      //   this.showMessageError("Errore nel recupero degli allegati.");
      //   this.loadedAllegati = true;
      // });
    } else {
      this.documentiProgettoService.getAllegatiByTipoDoc(+doc.idProgetto, +doc.idTarget, doc.codTipoDoc).subscribe(data => {
        if (data) {
          this.visualizzaAllegatiDS(doc, data);
        }
        this.loadedAllegati = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nel recupero degli allegati.");
        this.loadedAllegati = true;
      });
    }
  }

  visualizzaAllegatiDS(doc: DocumentoIndexDTO, allegati: AllegatiDichiarazioneDiSpesaDTO[]) {
    this.dialog.open(AllegatiDichiarazioneSpesaDialogComponent,
      {
        width: '50%',
        maxHeight: '90%',
        data: {
          allegati: allegati,
          title: `Allegati ${doc.descTipoDocumento} ${doc.idTarget}`
        }
      });
  }

  anteprimaAllegato(doc: DocumentoIndexDTO) {
    this.resetMessageError();
    let comodo = new Array<any>();
    comodo.push(doc.documento);
    comodo.push(doc.idDocumentoIndex);
    comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(doc.codTipoDoc, doc.documento, null, null, null, null, null, doc.idDocumentoIndex, null)]));
    comodo.push(this.configService.getApiURL());
    comodo.push(doc.codTipoDoc);

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });

  }

  downloadAllegato(doc: DocumentoIndexDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();
    if (doc.codTipoDoc === Constants.DESC_BREVE_TIPO_DOC_INDEX_ACTA) {
      this.loadedDownload = false;
      this.subscribers.downlaodActa = this.documentiProgettoService.leggiFileActa(+doc.idDocumentoIndex).subscribe(res => {
        if (res) {
          let nomeFile = res.headers.get("header-nome-file");
          saveAs(new Blob([res.body]), nomeFile);
        }
        this.loadedDownload = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download del file");
        this.loadedDownload = true;
      });
    } else {
      this.loadedDownload = false;
      this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), doc.idDocumentoIndex).subscribe(res => {
        if (res) {
          saveAs(res, doc.documento);
        }
        this.loadedDownload = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download del file");
        this.loadedDownload = true;
      });
    }
  }

  downloadAllegatoTimeStamped(idDocumentoIndex: string) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaodFileMarcato = this.archivioFileService.leggiFileMarcato(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      let nomeFile = res.headers.get("header-nome-file");
      saveAs(new Blob([res.body]), nomeFile);
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  downloadAllegatoTimeStampedFirmaAutografa(idDocumentoIndex: string) {


    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    //this.subscribers.downlaodFileMarcato = this.dichiarazioneDiSpesaService.leggiFileFirmaAutografa(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
    this.subscribers.downlaodFileMarcato = this.archivioFileService.leggiFileFirmaAutografa(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      let nomeFile = res.headers.get("header-nome-file");
      saveAs(new Blob([res.body]), nomeFile);
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });




  }

  eliminaDocumento(idDocumentoIndex: string) {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione del file?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        let doc = this.documenti.find(d => d.idDocumentoIndex === idDocumentoIndex);
        if (doc.codTipoDoc === Constants.DESC_BREVE_TIPO_DOC_INDEX_ACTA) {
          this.loadedElimina = false;
          this.subscribers.eliminaDocActa = this.documentiProgettoService.cancellaRecordDocumentoIndex(+idDocumentoIndex).subscribe(data => {
            if (data) {
              this.ricerca();
              this.showMessageSuccess("Eliminazione del file eseguita con successo.");
            } else {
              this.showMessageError("Errore in fase di eliminazione del file.")
            }
            this.loadedElimina = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.loadedElimina = true;
            this.showMessageError("Errore in fase di eliminazione del file.");
          });
        } else {
          this.loadedElimina = false;
          this.subscribers.eliminaDoc = this.documentiProgettoService.cancellaFileConVisibilita(+idDocumentoIndex).subscribe(data => {
            if (data) {
              this.ricerca();
              this.showMessageSuccess("Eliminazione del file eseguita con successo.");
            } else {
              this.showMessageError("Errore in fase di eliminazione del file.")
            }
            this.loadedElimina = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.loadedElimina = true;
            this.showMessageError("Errore in fase di eliminazione del file.");
          });
        }
      }
    });
  }

  upload(doc: DocumentoIndexDTO, isError: boolean) {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(UploadDocumentoFirmatoDialogComponent, {
      width: '60%',
      data: {
        messageError: isError ? "Il documento firmato non corrisponde a quello originale." : null,
        nomeFile: doc.documento,
        isBR50: doc.isBR50,
        dimMaxSingoloFile: this.dimMaxSingoloFile
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.loadedUpload = false;
        let fileFirmato: FileFirmato = res

        if (fileFirmato.typeFirma == "digitale") {


          this.subscribers.uploadFileFirmato = this.dichiarazioneDiSpesaService.salvaFileFirmato(+doc.idDocumentoIndex, fileFirmato.file).subscribe(data1 => {
            if (data1) {
              let documento = this.documenti.find(d => d.idDocumentoIndex === doc.idDocumentoIndex);
              documento.codStatoDoc = "IN VERIFICA";
              this.dichiarazioneDiSpesaService.verificaFirmaDigitaleReturn(+doc.idDocumentoIndex).subscribe(data2 => {
                if (data2) {
                  this.documentiProgettoService.codiceStatoDocumentoIndex(+doc.idDocumentoIndex).subscribe(data3 => {
                    if (data3.trim() === "VALIDATO" || data3.trim() === "INVIATO") {
                      documento.codStatoDoc = data3.trim();
                      documento.signValid = null;
                      documento.signable = true;
                      documento.timeStamped = true;
                      documento.signed = true;
                      documento.flagFirmaCartacea = null;
                    } else if (data3.trim() === "NON-VALIDATO") {
                      documento.codStatoDoc = data3.trim();
                      documento.signValid = false;
                    }
                  });
                }
              }, err => {
                if (err.name !== "TimeoutError") {//ignoro l'errore di timeout
                  this.handleExceptionService.handleNotBlockingError(err);
                }
              });
            } else {
              this.showMessageError("Errore nell'upload del documento firmato.");
            }
            this.loadedUpload = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore nell'upload del documento firmato.");
            this.loadedUpload = true;
          });


        }

        else if (fileFirmato.typeFirma == "autografa") {

          this.subscribers.uploadFileFirmato = this.dichiarazioneDiSpesaService.salvaFileFirmaAutografa(+doc.idDocumentoIndex, fileFirmato.file).subscribe(data1 => {
            if (data1) {
              let documento = this.documenti.find(d => d.idDocumentoIndex === doc.idDocumentoIndex);
              documento.flagFirmaAutografa = true;
            } else {
              this.showMessageError("Errore nell'upload del documento firmato.");
            }
            this.loadedUpload = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore nell'upload del documento firmato.");
            this.loadedUpload = true;
          });


        }

        this.loadedUpload = true;

      }
    });
  }

  changeInvioExtraProcedura(event: MatSlideToggleChange, doc: DocumentoIndexDTO) {
    this.subscribers.extra = this.dichiarazioneDiSpesaService.salvaInvioCartaceo(+doc.idDocumentoIndex, event.checked ? "true" : "false").subscribe(data => {
      if (!data) {
        doc.flagFirmaCartacea = false;
        this.showMessageError("Errore in fase di modifica del tipo di invio.");
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      doc.flagFirmaCartacea = false;
      this.showMessageError("Errore in fase di modifica del tipo di invio.");
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

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedInizializzazione || !this.loadedProgetti || !this.loadedCerca || !this.loadedUpload
      || !this.loadedDownload || !this.loadedElimina || !this.loadedAllegati || !this.loadedIntegrazioni
      || !this.loadedDocIstr || !this.loadedBeneficiariInit || !this.loadedCostante || !this.loadedBandoFP) {
      return true;
    }
    return false;
  }

  ngAfterViewChecked() {
    if (this.tabs) {
      this.tabs.realignInkBar();
    }
  }
}

export class DocumentiDatasource extends MatTableDataSource<DocumentoIndexDTO> {

  _orderData(data: DocumentoIndexDTO[]): DocumentoIndexDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "tipoDocumento";
    }
    let direction = this.sort.direction || this.sort.start;
    let sortedData = null;

    // viene riordinato in ordine ascendente 
    switch (this.sort.active) {
      case "tipoDocumento":
        sortedData = data.sort((docA, docB) => {
          return docA.descTipoDocumento.localeCompare(docB.descTipoDocumento);
        });
        break;
      case "progetto":
        sortedData = data.sort((docA, docB) => {
          return docA.codiceProgettoVisualizzato.localeCompare(docB.codiceProgettoVisualizzato);
        });
        break;
      case "beneficiario":
        sortedData = data.sort((docA, docB) => {
          return docA.beneficiario.localeCompare(docB.beneficiario);
        });
        break;
      case "nProtocollo":
        sortedData = data.sort((docA, docB) => {
          if (docA.protocollo === null) {
            return 1;
          }
          return docA.protocollo.localeCompare(docB.protocollo);
        });
        break;
      default:
        sortedData = data.sort((docA, docB) => {
          return docA.descTipoDocumento.localeCompare(docB.descTipoDocumento);
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: DocumentoIndexDTO[]) {
    super(data);
  }
}