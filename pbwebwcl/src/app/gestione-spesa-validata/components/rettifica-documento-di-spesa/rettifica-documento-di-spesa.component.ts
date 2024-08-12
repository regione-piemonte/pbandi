import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { VoceDiSpesaPadre } from 'src/app/rendicontazione/commons/dto/voce-di-spesa';
import { NuovoModificaVoceSpesaDialogComponent } from 'src/app/rendicontazione/components/nuovo-modifica-voce-spesa-dialog/nuovo-modifica-voce-spesa-dialog.component';
import { DocumentoDiSpesaService } from 'src/app/rendicontazione/services/documento-di-spesa.service';
import { ConfermaWarningDialogComponent } from 'src/app/shared/conferma-warning-dialog/conferma-warning-dialog.component';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { RigaValidazioneItemDTO, RigaValidazioneItemDTOFormatted } from 'src/app/validazione/commons/dto/riga-validazione-item-dto';
import { ConfermaSalvaSpesaValidataRequest } from '../../commons/requests/conferma-salva-spesa-validata-request';
import { SalvaSpesaValidataRequest } from '../../commons/requests/salva-spesa-validata-request';
import { GestioneSpesaValidataService } from '../../services/gestione-spesa-validata.service';
import { DettaglioRettificheDialogComponent } from '../dettaglio-rettifiche-dialog/dettaglio-rettifiche-dialog.component';
import { NuovoDocumentoDiSpesaDTO } from 'src/app/validazione/commons/dto/nuovo-documento-di-spesa-dto';
import { ValidazioneService } from 'src/app/validazione/services/validazione.service';
import { AllegatiDettaglioDocComponent } from 'src/app/validazione/components/allegati-dettaglio-doc/allegati-dettaglio-doc.component';
import { NoteCreditoDettaglioDocComponent } from 'src/app/validazione/components/note-credito-dettaglio-doc/note-credito-dettaglio-doc.component';
import { RilievoDialogComponent } from '../rilievo-dialog/rilievo-dialog.component';
import { RilievoDocSpesaDTO } from '../../commons/dto/rilievo-doc-spesa-dto';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';

@Component({
  selector: 'app-rettifica-documento-di-spesa',
  templateUrl: './rettifica-documento-di-spesa.component.html',
  styleUrls: ['./rettifica-documento-di-spesa.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RettificaDocumentoDiSpesaComponent implements OnInit {

  idProgetto: number;
  idBandoLinea: number;
  idDocumentoDiSpesa: number;
  idDichiarazioneDiSpesa: number;
  rettificato: boolean;
  user: UserInfoSec;
  beneficiario: string;
  codiceProgetto: string;
  docSpesa: NuovoDocumentoDiSpesaDTO;
  pagamentiAssociati: Array<RigaValidazioneItemDTOFormatted>;
  vociSpesa: Array<RigaValidazioneItemDTOFormatted>;
  esistePropostaCertificazione: boolean;
  bottoneSalvaVisibile: boolean;
  macroVoci: Array<VoceDiSpesaPadre>;
  vociDiSpesa: Array<any>;
  isBR79: boolean;
  isBR63: boolean;
  isVisible: boolean = false;
  isFP: boolean = false;
  idStatoRichiesta: number;
  isRagioneriaDelegata: boolean;
  rilievo: RilievoDocSpesaDTO;

  displayedColumnsVociSpesa: string[] = ['desc', 'associato', 'validatoPrec', 'validato', 'rettifica', 'rif', 'azioni'];
  dataSourceVociSpesa: MatTableDataSource<RigaValidazioneItemDTOFormatted>;

  @ViewChild('voceForm', { static: true }) voceForm: NgForm;
  @ViewChild(AllegatiDettaglioDocComponent) allegatiDettaglioDocComponent: AllegatiDettaglioDocComponent;
  @ViewChild(NoteCreditoDettaglioDocComponent) noteCreditoDettaglioDocComponent: NoteCreditoDettaglioDocComponent;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedInizializza: boolean;
  loadedDettaglio: boolean;
  loadedVoce: boolean = true;
  loadedMacroVoci: boolean;
  loadedSalva: boolean = true;
  loadedConferma: boolean = true;
  loadedRettifiche: boolean = true;
  loadedRegolaBR79: boolean = true;
  loadedRegolaBR63: boolean = true;
  loadedIsFP: boolean = true;
  loadedIntegrazione: boolean = true;
  loadedRilievo: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private gestioneSpesaValidataService: GestioneSpesaValidataService,
    private documentoDiSpesaService: DocumentoDiSpesaService,
    private validazioneService: ValidazioneService,
    private sharedService: SharedService,
    private dialog: MatDialog,
    private configService: ConfigService
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.idDocumentoDiSpesa = +params['idDocumentoDiSpesa'];
      this.idDichiarazioneDiSpesa = +params['idDichiarazioneDiSpesa'];
      if (this.activatedRoute.snapshot.paramMap.get('rettificato') != null) {
        this.rettificato = this.activatedRoute.snapshot.paramMap.get('rettificato') ? true : false;
      }
    });
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
        if (this.user.codiceRuolo === Constants.CODICE_RUOLO_RAG_DEL) {
          this.isRagioneriaDelegata = true;
        }
        this.loadData();
        this.loadedMacroVoci = false;
        this.subscribers.macroVoci = this.documentoDiSpesaService.getMacroVociDiSpesa(this.idDocumentoDiSpesa, this.idProgetto, this.user.idUtente).subscribe(data => {
          if (data) {
            this.macroVoci = data;
          }
          this.loadedMacroVoci = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento delle macro voci di spesa.");
          this.loadedMacroVoci = true;
        });
      }
    });
  }

  loadData() {
    this.loadedInizializza = false;
    this.subscribers.inizializza = this.gestioneSpesaValidataService.inizializzaModificaSpesaValidata(this.idProgetto, this.idBandoLinea, this.idDocumentoDiSpesa,
      this.idDichiarazioneDiSpesa.toString(), this.user.codiceRuolo).subscribe(data => {
        if (data) {
          this.codiceProgetto = data.codiceVisualizzatoProgetto;
          this.loadDocSpesa();
          this.pagamentiAssociati = <Array<RigaValidazioneItemDTOFormatted>>data.pagamentiAssociati;
          if (this.pagamentiAssociati) {
            this.vociSpesa = this.pagamentiAssociati.filter(p => !p.rigaPagamento);
            this.vociSpesa.forEach(v => {
              v.importoValidatoVoceDiSpesaFormatted = v.importoValidatoVoceDiSpesa ? this.sharedService.formatValue(v.importoValidatoVoceDiSpesa.toString()) : '0,00';
            });
            this.dataSourceVociSpesa = new MatTableDataSource<RigaValidazioneItemDTOFormatted>(this.vociSpesa);
          }
          this.esistePropostaCertificazione = data.esistePropostaCertificazione;
          if (this.esistePropostaCertificazione) {
            this.showMessageWarning("Per il progetto è stata creata almeno una proposta di certificazione. Eventuali rettifiche sulla spesa validata potranno avere effetto sugli importi certificati.");
          }
          this.bottoneSalvaVisibile = data.bottoneSalvaVisibile;
        }
        this.loadedInizializza = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
    this.loadedRegolaBR79 = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR79").subscribe(res => {
      this.isBR79 = res;
      this.loadedRegolaBR79 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore durante il caricamento della regola BR79.");
      this.loadedRegolaBR79 = true;
    });

    this.userService.isCostanteFinpiemonte().subscribe(data => {
      this.isVisible = data;
      this.loadedIsFP = false;
      this.userService.isBandoCompetenzaFinpiemonte(this.idBandoLinea).subscribe(data => {
        this.isFP = data;
        this.loadedRegolaBR63 = false;
        this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR63").subscribe(res => {
          this.isBR63 = res;
          if ((this.isVisible || ((!this.isFP || this.isBR79))) && this.isBR63) {
            this.loadIntegrazione();
            this.loadRilievo();
          }
          this.loadedRegolaBR63 = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore durante il caricamento della regola BR63.");
          this.loadedRegolaBR63 = true;
        });
        this.loadedIsFP = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di verifica bando competenza Finpiemonte.");
        this.loadedIsFP = true;
      });
    });
  }

  loadIntegrazione() {
    this.loadedIntegrazione = false;
    this.validazioneService.initIntegrazione(this.idDichiarazioneDiSpesa.toString()).subscribe(data => {
      this.idStatoRichiesta = data?.idStatoRichiesta ? +data?.idStatoRichiesta : null;
      this.loadedIntegrazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di inizializzazione dell'integrazione.");
      this.loadedIntegrazione = true;
    });
  }

  loadRilievo() {
    this.loadedRilievo = false;
    this.gestioneSpesaValidataService.getRilievoDocSpesa(this.idDocumentoDiSpesa, this.idProgetto, this.idDichiarazioneDiSpesa).subscribe(data => {
      this.rilievo = data;
      this.loadedRilievo = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del rilievo.");
      this.loadedRilievo = true;
    });

  }

  loadDocSpesa() {
    this.loadedDettaglio = false;
    this.subscribers.inizializza = this.validazioneService.dettaglioDocumentoDiSpesaPerValidazione(this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto,
      this.idBandoLinea).subscribe(data => {
        if (data) {
          this.docSpesa = data;
        }
        this.loadedDettaglio = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore durante la ricerca del dettaglio del documento selezionato.");
        this.loadedDettaglio = true;
      });

  }

  openModificaVoceSpesa(idQuotaParte: number) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedVoce = false;
    this.subscribers.voce = this.gestioneSpesaValidataService.voceDiSpesa(idQuotaParte, this.idDocumentoDiSpesa, this.idProgetto).subscribe(data => {
      if (data) {
        let dialogRef = this.dialog.open(NuovoModificaVoceSpesaDialogComponent, {
          width: '60%',
          data: {
            isNuovo: false,
            idDocumentoDiSpesa: this.idDocumentoDiSpesa,
            idTipoDocumentoDiSpesa: this.docSpesa.idTipoDocumentoDiSpesa,
            idProgetto: this.idProgetto,
            user: this.user,
            macroVoci: this.macroVoci,
            descBreveTipoDocumento: this.docSpesa.descBreveTipoDocumentoDiSpesa,
            voce: data,
            tipoOperazioneFrom: Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_VALIDAZIONE  //disabilita tutto tranne macro e micro voce di spesa
          }
        });
        dialogRef.afterClosed().subscribe(res => {
          if (res) {
            this.showMessageSuccess(res);
            this.loadData();
          }
        });
      }
      this.loadedVoce = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento.");
      this.loadedVoce = true;
    });

  }

  openDettaglioRettifiche(progrPagQuotParteDocSp: number) {
    this.loadedRettifiche = false;
    this.subscribers.rettifiche = this.gestioneSpesaValidataService.dettaglioRettifiche(progrPagQuotParteDocSp).subscribe(data => {
      if (data) {
        this.dialog.open(DettaglioRettificheDialogComponent, {
          width: '50%',
          data: {
            rettifiche: data
          }
        });
      }
      this.loadedRettifiche = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento rettifiche");
      this.loadedRettifiche = true;
    });

  }

  setImporto(voce: RigaValidazioneItemDTOFormatted) {
    voce.importoValidatoVoceDiSpesa = this.sharedService.getNumberFromFormattedString(voce.importoValidatoVoceDiSpesaFormatted);
    if (voce.importoValidatoVoceDiSpesa != null) {
      voce.importoValidatoVoceDiSpesaFormatted = this.sharedService.formatValue(voce.importoValidatoVoceDiSpesa.toString());
      if (voce.importoValidatoVoceDiSpesa > voce.importoAssociatoVoceDiSpesa) {
        this.voceForm.form.get("importo" + voce.idQuotaParte).setErrors({ error: 'maggiore' });
      }
    } else {
      voce.importoValidatoVoceDiSpesaFormatted = "0,00";
      voce.importoValidatoVoceDiSpesa = 0;
    }
    voce.importoTotaleRettifica = voce.importoValidatoVoceDiSpesa - voce.importoValidatoPrecedenteVoceDiSpesa;
  }


  get noteRilievo(): string {
    return this.rilievo?.rilievoContabile || null;
  }

  set noteRilievo(note: string) {
    this.rilievo.rilievoContabile = note;
  }

  get isNoteRilievo(): boolean {
    return this.noteRilievo?.length > 0;
  }

  get dtChiusuraRilievi() {
    return this.rilievo?.dtChiusuraRilievo || null;
  }

  get isSospeso() {
    return this.docSpesa?.descrizioneStatoValidazione === Constants.DESC_STATO_DOC_SPESA_SOSPESO;
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
        this.loadedSalva = false;
        this.gestioneSpesaValidataService.salvaRilievoDocSpesa(this.idDocumentoDiSpesa, this.idProgetto, res).subscribe(data => {
          if (data?.esito) {
            this.noteRilievo = res;
            this.rilievo.dtRilievoContabile = new Date();
            this.showMessageSuccess(data.messaggio);
          } else {
            this.showMessageError("Errore durante il salvataggio del rilievo contabile.");
          }
          this.loadedSalva = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore durante il salvataggio del rilievo contabile.");
          this.loadedSalva = true;
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
        this.loadedSalva = false;
        this.gestioneSpesaValidataService.deleteRilievoDocSpesa(this.idDocumentoDiSpesa, this.idProgetto).subscribe(data => {
          if (data?.esito) {
            this.noteRilievo = null;
            this.rilievo.dtRilievoContabile = null;
            this.showMessageSuccess(data.messaggio);
          } else {
            this.showMessageError("Errore durante la cancellazione del rilievo.");
          }
          this.loadedSalva = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore durante la cancellazione del rilievo.");
          this.loadedSalva = true;
        });
      }
    });
  }

  sospendi() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedSalva = false;
    this.subscribers.sospendi = this.gestioneSpesaValidataService.sospendiDocumentoSpesaValid(this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto, this.docSpesa.noteValidazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.loadData();
          this.showMessageSuccess(data.messaggio);
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nella sospensione del documento.");
      this.loadedSalva = true;
    });
  }

  annullaSospendi() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedSalva = false;
    this.subscribers.sospendi = this.gestioneSpesaValidataService.annullaSospendiDocumentoSpesaValid(this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto, this.docSpesa.noteValidazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.loadData();
          this.showMessageSuccess(data.messaggio);
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nella sospensione del documento.");
      this.loadedSalva = true;
    });
  }

  isSalvaDisabled() {
    if (this.vociSpesa.find(v => v.importoValidatoVoceDiSpesa < 0 || v.importoValidatoVoceDiSpesa > v.importoAssociatoVoceDiSpesa)) {
      return true;
    }
    if (this.idStatoRichiesta == 1 || this.idStatoRichiesta == 4 || this.isSospeso) {
      return true;
    }
    return false;
  }

  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedSalva = false;
    let voci = new Array<RigaValidazioneItemDTOFormatted>();
    this.vociSpesa.forEach(v => voci.push(Object.assign({}, v)));
    let pagamenti: Array<RigaValidazioneItemDTO> = this.pagamentiAssociati.map(p => {
      let voce = voci.find(v => v.idQuotaParte === p.idQuotaParte);
      if (voce) {
        delete voce['importoValidatoVoceDiSpesaFormatted'];
        return voce;
      } else {
        delete p['importoValidatoVoceDiSpesaFormatted'];
        return p;
      }
    });
    let requestSalva = new SalvaSpesaValidataRequest(pagamenti, this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto);
    this.subscribers.salva = this.gestioneSpesaValidataService.salvaSpesaValidata(requestSalva).subscribe(data1 => {
      if (data1) {
        let messaggio1: string = '';
        if (data1.messaggi && data1.messaggi.length > 0) {
          data1.messaggi.forEach(m => {
            messaggio1 += m + "<br/>";
          });
          messaggio1 = messaggio1.substr(0, messaggio1.length - 5);
        }
        if (data1.esito) {
          if (data1.chiedereConfermaPerProseguire) {
            let dialogRef = this.dialog.open(ConfermaWarningDialogComponent, {
              width: '60%',
              data: {
                title: "Conferma",
                messageWarning: messaggio1
              }
            });

            dialogRef.afterClosed().subscribe(res => {
              if (res) {
                this.loadedConferma = false;

                let requestConferma = new ConfermaSalvaSpesaValidataRequest(this.idDichiarazioneDiSpesa, this.idDocumentoDiSpesa, this.idProgetto, pagamenti, this.docSpesa.noteValidazione);
                this.subscribers.conferma = this.gestioneSpesaValidataService.confermaSalvaSpesaValidata(requestConferma).subscribe(data2 => {
                  if (data2) {
                    let messaggio2: string = '';
                    if (data2.messaggi && data2.messaggi.length > 0) {
                      data2.messaggi.forEach(m => {
                        messaggio2 += m + "<br/>";
                      });
                      messaggio2 = messaggio2.substr(0, messaggio2.length - 5);
                    }
                    if (data2.esito) {
                      this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA}/gestioneChecklistSpesaValidata/${this.idProgetto}/${this.idBandoLinea}/${this.idDocumentoDiSpesa}/${this.idDichiarazioneDiSpesa};message=SUCCESS`);
                    } else {
                      this.showMessageError(messaggio2);
                    }
                  }
                  this.loadedConferma = true;
                }, err => {
                  this.handleExceptionService.handleNotBlockingError(err);
                  this.showMessageError("Errore in fase di conferma.");
                  this.loadedConferma = true;
                });
              }
            });
          } else {
            this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA}/gestioneChecklistSpesaValidata/${this.idProgetto}/${this.idBandoLinea}/${this.idDocumentoDiSpesa}/${this.idDichiarazioneDiSpesa};message=SUCCESS`)
          }
        } else {
          this.showMessageError(messaggio1);
        }

        this.loadedSalva = true;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalva = true;
    });
  }

  tornaAGestioneSpesaValidata() {
    this.router.navigateByUrl(`/drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA}/gestioneSpesaValidata/${this.idProgetto}/${this.idBandoLinea}`);
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

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedInizializza || !this.loadedVoce || !this.loadedMacroVoci
      || !this.loadedSalva || !this.loadedConferma || !this.loadedRettifiche
      || !this.loadedDettaglio || !this.loadedRegolaBR79 || !this.loadedIsFP
      || !this.loadedRegolaBR63 || !this.loadedIntegrazione || !this.loadedRilievo
      || (this.allegatiDettaglioDocComponent && this.allegatiDettaglioDocComponent.isLoading())
      || (this.noteCreditoDettaglioDocComponent && this.noteCreditoDettaglioDocComponent.isLoading()) ) {
      return true;
    }
    return false;
  }

}
