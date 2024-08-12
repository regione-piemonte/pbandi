import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { AnteprimaDialogComponent, ArchivioFileDialogComponent, ArchivioFileService, DocumentoAllegatoDTO, HandleExceptionService, InfoDocumentoVo, UserInfoSec } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AllegatiDichiarazioneSpesaVO } from '../../commons/dto/allegatiDichiarazioneSpesaVO';
import { GestioneIntegrazioniService } from '../../services/gestione-integrazioni.service';
import { Location } from '@angular/common';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { AllegatiPrecedentementeVO } from '../../commons/allegatiPrecedentementeVO';
import { infoQuietanzeVO } from '../../commons/dto/infoQuietanzeVO';
import { saveAs } from 'file-saver-es';
import { Constants } from 'src/app/core/commons/util/constants';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DocIntegrRendDTO } from '../../commons/dto/doc-integr-rend-dto';

@Component({
  selector: 'app-integrazione-dichiarazione-spesa',
  templateUrl: './integrazione-dichiarazione-spesa.component.html',
  styleUrls: ['./integrazione-dichiarazione-spesa.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class IntegrazioneDichiarazioneSpesaComponent implements OnInit {

  subscribers: any = {};
  user: UserInfoSec;
  idProgetto: number;
  idBandoLinea: number;
  codiceProgetto: number;
  nRevoca: number;
  idIntegrazione;
  idDichiarazioneSpesa;

  dataInvioQuietanze;

  columnsToDisplay = ['documento', 'fornitore', 'importo', 'notaIstruttore'];
  displayedColumns = ['modalita', 'data', 'importo', 'expand2'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement;

  columnsToDisplayWithExpand2 = [...this.displayedColumns, 'expand2'];
  expandedElement2;


  // Messaggi 
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  isMessageErrorVisible: boolean;
  messageError: string;

  documentiDiSpesaSospesi;


  //allegati
  allegati: Array<DocumentoAllegatoDTO>;

  allegatiDaSalvare: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();
  allegatoDaSalvare: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();
  allegatiDaSalvareDocumentoSpesa: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();
  allegatiDaSalvareQuietanze: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();

  allegatiDaInviare: Array<AllegatiDichiarazioneSpesaVO> = new Array<AllegatiDichiarazioneSpesaVO>();
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];

  //INTEGRAZIONE
  letteraAccIntegrazRendicont: Array<DocIntegrRendDTO> = new Array<DocIntegrRendDTO>();
  letteraAccIntegrazRendicontEsiste: boolean = null;
  allegatiIntegrazioneDSEsiste: boolean = null;

  //SPESA
  allegatiIntegrazioneDocS;
  allegatiDocumentoSpesaDocS;
  allegatiNuovaIntegrazioneDocS;
  allegatiDichiarazioneSpesa: Array<AllegatiPrecedentementeVO> = new Array<AllegatiPrecedentementeVO>();
  // allegatiIntegrazioneDS : Array<AllegatiPrecedentementeVO> = new Array<AllegatiPrecedentementeVO>();
  allegatiIntegrazioneDS


  allegatiNuovaIntegrazioneDocSEsiste: boolean = null;
  allegatiDocumentoSpesaDocSEsiste: boolean = null;
  allegatiIntegrazioneDocSEsiste: boolean = null;
  documentiDiSpesaSospesiEsiste: boolean = null;
  allegatiDichiarazioneSpesaEsiste: boolean = null;

  //QUIETANZE
  allegatiNuovaIntegrazioneQuietanza;
  allegatiQuietanze;
  allegatiIntegrazioneQuietanza;
  infoQuietanze: Array<infoQuietanzeVO> = new Array<infoQuietanzeVO>();

  allegatiIntegrazioneQuietanzaEsiste: boolean = null;
  allegatiNuovaIntegrazioneQuietanzaEsiste: boolean = null;
  allegatiQuietanzeEsiste: boolean = null;
  infoQuietanzeEsiste: boolean = null;

  allegatiDocumentoSpesaDocSDataInvio;
  allegatiIntegrazioneDocSDataInvio;
  infoQuietanzeDatainvio;
  allegatiIntegrazioneDSdataInvio;
  dataInvioSpesa;

  stato: string;
  statu = "ciao";
  infoReportValidazione: any[];
  infoReportValidazioneEsiste: boolean;
  infoReportValidazioneDatainvio: any;

  nessunRisultato = "Nessun risultato da visualizzare"
  titoloDocumenti: string;
  fileFormatoNonPdf: boolean;
  fileFormatoPdf: boolean;
  BR51: boolean;
  BR52: boolean;
  BR53: boolean;
  BR42: boolean;

  // LOADED
  loadedAllegatiDocS: boolean = true;
  loadedAllegatiDS: boolean = true;
  loadedAllegatiIntDocS: boolean = true;
  loadedAllegatiIntDS: boolean = true;
  loadedAllegatiNewIntDocS: boolean = true;
  loadedAllegatiNewIntDS: boolean = true;
  loadedAllegatiIntQuietanza: boolean = true;
  loadedAllegatiQuietanza: boolean = true;
  loadedAllegatiNewIntQuietanza: boolean = true;
  loadedDelete: boolean = true;
  loadedSalva: boolean = true;
  loadedDownload: boolean = true;
  loadedRegoleInteg: boolean = true;
  loadedLetteraAccIntRend: boolean = true;
  loadedReportValidazione: boolean = true;
  loadedDocSospesi: boolean = true;
  loadedDocSpesaIntegrati: boolean = true;
  loadedQuietanze: boolean = true;


  constructor(private dialog: MatDialog,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private integrazioniService: GestioneIntegrazioniService,
    private location: Location,
    private archivioFileService: ArchivioFileService,
  ) { }

  ngOnInit(): void {
    this.getDati();
    this.getRegoleIntegrazione();
    this.getAllegati();
    this.getReportValidazione();

    //  this.stato = 'storico' ? this.getDocumentiDiSpesaIntegrati() : this.getDocumentiDiSpesaSospesi();
    if (this.stato === 'storico') {
      //  this.titoloDocumenti = 'Documenti di spesa integrati';
      this.titoloDocumenti = 'Documenti integrati';
      this.getDocumentiDiSpesaIntegrati()
    } else {
      this.titoloDocumenti = 'Documenti sospesi';
      this.getDocumentiDiSpesaSospesi()
    }
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_GEST_INT_DICH_SPESA;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  getDati() {
    this.activatedRoute.queryParams.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.codiceProgetto = params['codiceProgetto'];
      this.idIntegrazione = params['idIntegrazione'];
      this.idDichiarazioneSpesa = params['idDichiarazioneSpesa'];
      this.stato = params['stato'];



    });
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
    })
  }

  getRegoleIntegrazione() {
    this.loadedRegoleInteg = false;
    this.integrazioniService.getRegoleIntegrazione(this.idBandoLinea).subscribe(data => {
      this.BR51 = data.allegatiAmmessiDocumentoSpesa;
      this.BR52 = data.allegatiAmmessiQuietanze;
      this.BR53 = data.allegatiAmmessiGenerici;
      this.BR42 = data.allegatiAmmessi;
      /* console.log('BR51');console.log(this.BR51);
      console.log('BR52');console.log(this.BR52);
      console.log('stato');console.log(this.stato);
      console.log('BR42');console.log(this.BR42); */
      this.loadedRegoleInteg = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle regole.");
      this.loadedRegoleInteg = true;
    });
  }

  getAllegati() {
    this.getAllegatiIntegrazioneDS();
    this.getAllegatiDichiarazioneSpesa();
    this.getLetteraAccIntegrazRendicont();
    this.getAllegatiNuovaIntegrazioneDS();
  }

  getLetteraAccIntegrazRendicont() {
    this.letteraAccIntegrazRendicont = [];
    this.loadedLetteraAccIntRend = false;
    this.integrazioniService.getLetteraAccIntegrazRendicont(this.idIntegrazione).subscribe(data => {

      if (data.length > 0) {
        this.letteraAccIntegrazRendicont = data;
        this.letteraAccIntegrazRendicontEsiste = true;
      } else {
        this.letteraAccIntegrazRendicont = data;
        this.letteraAccIntegrazRendicontEsiste = false;
      }
      this.loadedLetteraAccIntRend = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento della lettera accompagnatoria dell'integrazione.");
      this.loadedLetteraAccIntRend = true;
    });
  }

  getAllegatiDocumentoSpesaDocS(idDocumentoDiSpesa) {
    this.loadedAllegatiDocS = false;
    this.integrazioniService.getAllegatiDocumentoSpesaDocS(idDocumentoDiSpesa, this.idDichiarazioneSpesa).subscribe(data => {

      if (data.length > 0) {
        this.allegatiDocumentoSpesaDocS = data;
        this.allegatiDocumentoSpesaDocSEsiste = true;
        this.allegatiDocumentoSpesaDocSDataInvio = this.allegatiDocumentoSpesaDocS[0].data;
      } else {
        this.allegatiDocumentoSpesaDocS = data;
        this.allegatiDocumentoSpesaDocSEsiste = false;
      }
      this.loadedAllegatiDocS = true;

    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati del documento.");
      this.loadedAllegatiDocS = true;
    });
  }

  getReportValidazione() {
    this.loadedReportValidazione = false;
    this.integrazioniService.getReportValidazione(this.idIntegrazione).subscribe(data => {

      if (data.length > 0) {
        this.infoReportValidazione = data;
        this.infoReportValidazioneEsiste = true;
        for (let i = 0; i < this.infoReportValidazione.length; ++i) {
          this.infoReportValidazioneDatainvio = this.infoReportValidazione[i].data;

        }
      } else {
        this.infoReportValidazione = data;
        this.infoReportValidazioneEsiste = false;
      }
      this.loadedReportValidazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del report di validazione.");
      this.loadedReportValidazione = true;
    });
  }

  getAllegatiIntegrazioneDocS(idDocumentoDiSpesa) {
    this.loadedAllegatiIntDocS = false;
    this.integrazioniService.getAllegatiIntegrazioneDocS(idDocumentoDiSpesa, this.idDichiarazioneSpesa, this.idIntegrazione).subscribe(data => {

      if (data.length > 0) {
        this.allegatiIntegrazioneDocS = data;
        this.allegatiIntegrazioneDocSEsiste = true;
        this.allegatiIntegrazioneDocSDataInvio = this.allegatiIntegrazioneDocS[0].data;
      } else {
        this.allegatiIntegrazioneDocS = data;
        this.allegatiIntegrazioneDocSEsiste = false;
      }
      this.loadedAllegatiIntDocS = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati di integrazione.");
      this.loadedAllegatiIntDocS = true;
    });
  }

  getAllegatiDichiarazioneSpesa() {
    this.allegatiDichiarazioneSpesa = [];
    this.loadedAllegatiDS = false;
    this.integrazioniService.getAllegatiDichiarazioneSpesa(this.idDichiarazioneSpesa).subscribe(data => {

      if (data.length > 0) {
        this.allegatiDichiarazioneSpesa = data;
        this.dataInvioSpesa = data[0].data;
        this.allegatiDichiarazioneSpesaEsiste = true;
      } else {
        this.allegatiDichiarazioneSpesa = data;
        this.allegatiDichiarazioneSpesaEsiste = false;
      }
      this.loadedAllegatiDS = true;

    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati della dichiarazione di spesa.");
      this.loadedAllegatiDS = true;
    });
  }

  getAllegatiIntegrazioneDS() {
    this.allegatiIntegrazioneDS = [];
    this.loadedAllegatiIntDS = false;
    this.integrazioniService.getAllegatiIntegrazioneDS(this.idDichiarazioneSpesa, this.idIntegrazione).subscribe(data => {

      if (data.length > 0) {
        this.allegatiIntegrazioneDS = data;
        // this.allegatiIntegrazioneDSdataInvio = this.allegatiIntegrazioneDS[0].data;
        this.allegatiIntegrazioneDSEsiste = true;
      } else {
        this.allegatiIntegrazioneDS = data;
        this.allegatiIntegrazioneDSEsiste = false;
      }

      this.loadedAllegatiIntDS = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati dell'integrazione alla dichiarazione di spesa.");
      this.loadedAllegatiIntDS = true;
    });
  }

  getDocumentiDiSpesaSospesi() {
    this.loadedDocSospesi = false;
    this.integrazioniService.getDocumentiDiSpesaSospesi(this.idDichiarazioneSpesa, this.idProgetto).subscribe(data => {

      if (data.length > 0) {
        this.documentiDiSpesaSospesi = data;
        this.documentiDiSpesaSospesiEsiste = true;
      } else {
        this.documentiDiSpesaSospesi = data;
        this.documentiDiSpesaSospesiEsiste = false;
      }
      this.loadedDocSospesi = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei documenti sospesi.");
      this.loadedDocSospesi = true;
    });
  }

  getDocumentiDiSpesaIntegrati() {
    this.loadedDocSpesaIntegrati = false;
    this.integrazioniService.getDocumentiDiSpesaIntegrati(this.idProgetto, this.idDichiarazioneSpesa, this.idIntegrazione).subscribe(data => {

      if (data.length > 0) {
        this.documentiDiSpesaSospesi = data;
        this.documentiDiSpesaSospesiEsiste = true;
      } else {
        this.documentiDiSpesaSospesi = data;
        this.documentiDiSpesaSospesiEsiste = false;
      }
      this.loadedDocSpesaIntegrati = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei documenti integrati.");
      this.loadedDocSpesaIntegrati = true;
    });
  }

  getAll(idDocumentoDiSpesa) {
    this.getQuietanze(idDocumentoDiSpesa);
    this.getAllegatiDocumentoSpesaDocS(idDocumentoDiSpesa);
    this.getAllegatiIntegrazioneDocS(idDocumentoDiSpesa);
    this.getAllegatiNuovaIntegrazioneDocS(idDocumentoDiSpesa);

  }

  getAllQuietanze(idPagamento) {
    this.getAllegatiQuietanza(idPagamento);
    this.getAllegatiNuovaIntegrazioneQuietanza(idPagamento);
    this.getAllegatiIntegrazioneQuietanza(idPagamento);

  }

  getQuietanze(idDocumentoDiSpesa) {
    this.loadedQuietanze = false;
    this.integrazioniService.getQuietanze(idDocumentoDiSpesa).subscribe(data => {

      if (data.length > 0) {
        this.infoQuietanze = data;
        this.infoQuietanzeEsiste = true;
        for (let i = 0; i < this.infoQuietanze.length; ++i) {
          this.infoQuietanzeDatainvio = this.infoQuietanze[i].dataPagamento;

        }
      } else {
        this.infoQuietanze = data;
        this.infoQuietanzeEsiste = false;
      }
      this.loadedQuietanze = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle quietanze.");
      this.loadedQuietanze = true;
    });
  }

  getAllegatiQuietanza(idPagamento: number) {
    this.loadedAllegatiQuietanza = false;
    this.integrazioniService.getAllegatiQuietanza(idPagamento, this.idDichiarazioneSpesa).subscribe(data => {

      if (data.length > 0) {
        this.allegatiQuietanze = data;
        this.allegatiQuietanzeEsiste = true;
        this.dataInvioQuietanze = this.allegatiQuietanze[0].data;

      } else {
        this.allegatiQuietanze = data;
        this.allegatiQuietanzeEsiste = false;
      }

      this.loadedAllegatiQuietanza = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati della quietanza.");
      this.loadedAllegatiQuietanza = true;
    });
  }

  getAllegatiIntegrazioneQuietanza(idPagamento: number) {
    this.loadedAllegatiIntQuietanza = false;
    this.integrazioniService.getAllegatiIntegrazioneQuietanza(idPagamento, this.idDichiarazioneSpesa, this.idIntegrazione).subscribe(data => {

      if (data.length > 0) {
        this.allegatiIntegrazioneQuietanza = data;
        this.allegatiIntegrazioneQuietanzaEsiste = true;
      } else {
        this.allegatiIntegrazioneQuietanza = data;
        this.allegatiIntegrazioneQuietanzaEsiste = false;
      }


      this.loadedAllegatiIntQuietanza = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati dell'integrazione della quietanza.");
      this.loadedAllegatiIntQuietanza = true;
    });
  }

  getAllegatiNuovaIntegrazioneQuietanza(idPagamento: number) {
    this.loadedAllegatiNewIntQuietanza = false;
    this.integrazioniService.getAllegatiNuovaIntegrazioneQuietanza(idPagamento, this.idIntegrazione).subscribe(data => {

      if (data.length > 0) {
        this.allegatiNuovaIntegrazioneQuietanza = data;
        this.allegatiNuovaIntegrazioneQuietanzaEsiste = true;
      } else {
        this.allegatiNuovaIntegrazioneQuietanza = data;
        this.allegatiNuovaIntegrazioneQuietanzaEsiste = false;
      }


      this.loadedAllegatiNewIntQuietanza = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati della nuova integrazione della quietanza.");
      this.loadedAllegatiNewIntQuietanza = true;
    });
  }
  getAllegatiNuovaIntegrazioneDocS(idDocumentoDiSpesa) {
    this.loadedAllegatiNewIntDocS = false;
    this.integrazioniService.getAllegatiNuovaIntegrazioneDocS(idDocumentoDiSpesa, this.idIntegrazione).subscribe(data => {

      if (data.length > 0) {
        this.allegatiNuovaIntegrazioneDocS = data;
        this.allegatiNuovaIntegrazioneDocSEsiste = true;
      } else {
        this.allegatiNuovaIntegrazioneDocS = data;
        this.allegatiNuovaIntegrazioneDocSEsiste = false;
      }
      this.loadedAllegatiNewIntDocS = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati della nuova integrazione del documento.");
      this.loadedAllegatiNewIntDocS = true;
    });
  }

  getAllegatiNuovaIntegrazioneDS() {
    this.allegatiGenerici = [];
    this.letteraAccompagnatoria = [];
    this.loadedAllegatiNewIntDS = false;
    this.integrazioniService.getAllegatiNuovaIntegrazioneDS(this.idDichiarazioneSpesa, this.idIntegrazione).subscribe(data => {

      if (data) {
        for (let a of data) {
          if (a.flagEntita === 'I') {
            this.letteraAccompagnatoria.push(Object.assign({}, a))
          } else {
            this.allegatiGenerici.push(Object.assign({}, a))
          }
        }
      } else {
        this.allegati = new Array<DocumentoAllegatoDTO>();
      }
      this.loadedAllegatiNewIntDS = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati della nuova integrazione della dichiarazione di spesa.");
      this.loadedAllegatiNewIntDS = true;
    });
  }

  aggiungiAllegatiSemplici() {
    this.allegatiDaSalvare = [];
    this.resetMessages();
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegatiDaSalvare: this.allegatiDaSalvare,
        allegati: this.allegati,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded()
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res && res.length > 0) {
        for (let a of res) {
          a.idEntita = 63;
          a.flagEntita = null;
          a.idTarget = this.idDichiarazioneSpesa;
          a.idIntegrazioneSpesa = this.idIntegrazione;
          if (this.allegatiDaSalvare.find(all => all.idDocumentoIndex === a.idDocumentoIndex) === undefined) {
            this.allegatiDaSalvare.push(a);
          }
        }
      }

      this.loadedSalva = false;
      this.integrazioniService.salvaAllegati(this.allegatiDaSalvare).subscribe(data => {
        this.allegatiDaSalvare = [];
        if (data.code === 'OK') {
          this.getAllegatiNuovaIntegrazioneDS();
          this.showMessageSuccess(data.message);
        } else {
          this.showMessageError((data?.message) ? data?.message : "Non è stato possibile inserire l'allegato.");
        }
        this.loadedSalva = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
        this.showMessageError("Errore in fase di salvataggio degli allegati.");
        this.loadedSalva = true;
      });

    });
  }

  aggiungiLettera() {
    this.resetMessages();
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegatoDaSalvare: this.allegatoDaSalvare,
        allegati: this.allegati,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded(),
        onlyOneFile: true
      }
    });
    dialogRef.afterClosed().subscribe(res => {


      if (res && res.length > 0) {
        for (let a of res) {
          if (this.allegatoDaSalvare.find(all => all.idDocumentoIndex === a.idDocumentoIndex) === undefined) {
            a.idEntita = 63;
            a.flagEntita = 'I';
            a.idTarget = this.idDichiarazioneSpesa;
            a.idIntegrazioneSpesa = this.idIntegrazione;
            this.allegatoDaSalvare.push(a);

          }
        }
      }

      this.loadedSalva = false;
      this.integrazioniService.salvaAllegati(this.allegatoDaSalvare).subscribe(data => {
        this.allegatoDaSalvare = [];
        if (data.code === 'OK') {
          this.showMessageSuccess("Lettera accompagnatoria salvata con successo.");
          this.getAllegatiNuovaIntegrazioneDS();
        } else {
          this.showMessageError((data?.message) ? data?.message : "Non è stato possibile inserire l'allegato'.");
        }
        this.loadedSalva = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
        this.showMessageError("Errore in fase di salvataggio degli allegati.");
        this.loadedSalva = true;
      });

    });
  }


  aggiungiAllegatiSpesa(numero, element) {
    this.allegatiDaSalvareDocumentoSpesa = [];
    this.resetMessages();
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegatiDaSalvareDocumentoSpesa: this.allegatiDaSalvareDocumentoSpesa,
        allegati: this.allegati,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded()
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res.length > 0) {
        if (res && res.length > 0) {
          for (let a of res) {
            if (this.allegatiDaSalvareDocumentoSpesa.find(all => all.idDocumentoIndex === a.idDocumentoIndex) === undefined) {
              a.idEntita = 123;
              a.idTarget = element;
              a.idIntegrazioneSpesa = this.idIntegrazione;
              this.allegatiDaSalvareDocumentoSpesa.push(a);
            }
          }
        }
        this.loadedSalva = false;
        this.integrazioniService.salvaAllegati(this.allegatiDaSalvareDocumentoSpesa).subscribe(data => {
          if (data.code === 'OK') {
            this.showMessageSuccess(data.message);
            this.getAllegatiNuovaIntegrazioneDocS(element);
            this.allegatiDaSalvareDocumentoSpesa = [];
          } else if (data.code === 'KO') {
            this.allegatiDaSalvareDocumentoSpesa = [];
            this.showMessageError(data.message);
          }
          this.loadedSalva = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
          this.showMessageError("Errore in fase di salvataggio degli allegati.");
          this.loadedSalva = true;
        });
      }
    });

  }

  aggiungiAllegatiQuietanze(element, idPagamento) {
    this.allegatiDaSalvareQuietanze = [];
    this.resetMessages();
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegatiDaSalvareQuietanze: this.allegatiDaSalvareQuietanze,
        allegati: this.allegati,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded()
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      console.log(res);

      if (res.length > 0) {
        if (res && res.length > 0) {
          for (let a of res) {
            if (this.allegatiDaSalvareQuietanze.find(all => all.idDocumentoIndex === a.idDocumentoIndex) === undefined) {
              a.idEntita = 70;
              a.idTarget = idPagamento;
              a.idIntegrazioneSpesa = this.idIntegrazione;
              this.allegatiDaSalvareQuietanze.push(a);
            }
          }

        }
        this.loadedSalva = false;
        this.integrazioniService.salvaAllegati(this.allegatiDaSalvareQuietanze).subscribe(data => {
          if (data.code === 'OK') {
            this.showMessageSuccess(data.message);
            this.getAllegatiNuovaIntegrazioneQuietanza(idPagamento);
            this.allegatiDaSalvareQuietanze = [];
          } else if (data.code === 'KO') {
            this.showMessageError(data.message);
            this.allegatiDaSalvareQuietanze = [];
          }
          this.loadedSalva = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
          this.showMessageError("Errore in fase di salvataggio degli allegati.");
          this.loadedSalva = true;
        });
      }
    });

  }

  rimuoviAllegatiDaSalvare(idDocumentoIndex: string) {
    this.resetMessages();
    this.allegatiDaSalvare.splice(this.allegatiDaSalvare.findIndex(a => a.idDocumentoIndex === idDocumentoIndex), 1);
    this.showMessageSuccess("Allegato disassociato con successo.");
  }

  aggiungiAllegato() {
    this.resetMessages();
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegatoDaSalvare: this.allegatoDaSalvare,
        allegati: this.allegati,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded(),
        onlyOneFile: true
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res && res.length > 0) {
        for (let a of res) {
          if (this.allegatoDaSalvare.find(all => all.idDocumentoIndex === a.idDocumentoIndex) === undefined) {
            this.allegatoDaSalvare.push(a);
          }
        }
      }

    });
  }

  rimuoviAllegatoDaSalvare(idDocumentoIndex: string) {
    this.resetMessages();
    this.allegatoDaSalvare.splice(this.allegatoDaSalvare.findIndex(a => a.idDocumentoIndex === idDocumentoIndex), 1);
  }

  anteprimaAllegato(lettera) {
    console.log(lettera);

    this.resetMessages();
    let comodo = new Array<any>();
    comodo.push(lettera.nomeFile); //nome del file
    comodo.push(lettera.idDocumentoIndex);
    comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(lettera.codTipoDoc, lettera.nomeFile, null, null, null, null, null, lettera.idDocumentoIndex, null)]));
    comodo.push(this.configService.getApiURL());
    comodo.push(lettera.codTipoDoc); //dalla pbandi_t_documenti_index e pbandi_c_tipo_documento_index

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });

  }

  isAnteprimaVisible(nomeFile: string) { //duplicato di isFileWithPreview dentro AnteprimaDialogComponent
    const splitted = nomeFile.split(".");
    if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "PDF" || splitted[splitted.length - 1] == "xml" || splitted[splitted.length - 1] == "XML" || splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M") {
      return true;
    } else {
      return false;
    }
  }

  deleteAllegato(idFileEntita, numero, id) {
    this.resetMessages();
    this.loadedDelete = false;
    this.integrazioniService.rimuoviAllegato(idFileEntita).subscribe(data => {
      if (data) {
        this.showMessageSuccess("Allegato disassociato con successo.");
        switch (numero) {
          case 1:
            this.getAllegatiNuovaIntegrazioneDS();
            break;
          case 2:
            this.getAllegatiNuovaIntegrazioneDocS(id);
            break;
          case 3:
            this.getAllegatiNuovaIntegrazioneQuietanza(id);
            break;
          case 4:
            this.getAllegatiNuovaIntegrazioneDS();
            break;
          default:
            break;
        }

      } else {
        this.showMessageError("Non è stato possibile eliminare l'allegato.");
      }
      this.loadedDelete = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione dell'allegato.");
      this.loadedDelete = true;
    });
  }

  scaricaFile(idDocumentoIndex: string) {
    this.resetMessages();
    this.loadedDownload = false;
    this.archivioFileService.leggiFileConNome(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        let nome = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nome);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
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

  resetMessages() {
    this.resetMessageSuccess();
    this.resetMessageError();
  }

  goBack() {
    this.location.back();
  }


  isLoading() {
    if (!this.loadedAllegatiDocS || !this.loadedAllegatiDS || !this.loadedAllegatiIntDocS || !this.loadedAllegatiIntDS
      || !this.loadedAllegatiNewIntDocS || !this.loadedAllegatiNewIntDS || !this.loadedAllegatiIntQuietanza || !this.loadedAllegatiQuietanza
      || !this.loadedAllegatiNewIntQuietanza || !this.loadedDelete || !this.loadedSalva || !this.loadedDownload
      || !this.loadedRegoleInteg || !this.loadedLetteraAccIntRend || !this.loadedReportValidazione || !this.loadedDocSospesi
      || !this.loadedDocSpesaIntegrati || !this.loadedQuietanze) {
      return true;
    }
    return false;
  }

}