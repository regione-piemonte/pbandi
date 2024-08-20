/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { Affidamento } from '../../commons/dto/affidamento';
import { MotiveAssenzaDTO } from '../../commons/dto/motive-assenza-dto';
import { NormativaAffidamentoDTO } from '../../commons/dto/normativa-affidamento-dto';
import { ProceduraAggiudicazioneAffidamento } from '../../commons/dto/procedura-aggiudicazione-affidamento';
import { TipoAffidamentoDTO } from '../../commons/dto/tipo-affidamento-dt';
import { TipologiaAggiudicazioneDTO } from '../../commons/dto/tipologia-aggiudicazione-dto';
import { TipologiaAppaltoDTO } from '../../commons/dto/tipologia-appalto-dto';
import { SalvaAffidamentoRequest } from '../../commons/requests/salva-affidamento-request';
import { AffidamentiService } from '../../services/affidamenti.service';
import { AffidamentoDTO } from '../../commons/dto/affidamento-dto';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { FornitoreDTO } from 'src/app/shared/commons/dto/fornitore-dto';
import { VarianteAffidamentoDTO } from '../../commons/dto/variante-affidamento-dto';
import { FornitoreAffidamentoDTO } from '../../commons/dto/fornitore-affidamento-dto';
import { MatDialog } from '@angular/material/dialog';
import { FornitoreAffidamentiDialogComponent } from '../fornitore-affidamenti-dialog/fornitore-affidamenti-dialog.component';
import { VarianteAffidamentiDialogComponent } from '../variante-affidamenti-dialog/variante-affidamenti-dialog.component';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { CodiceDescrizioneDTO } from 'src/app/shared/commons/dto/codice-descrizione-dto';
import { DocumentoAllegato } from '../../commons/dto/documento-allegato';
import { NotificaDTO } from '../../commons/dto/notifica-dto';
import { NotificheAffidamentiDialogComponent } from '../notifiche-affidamenti-dialog/notifiche-affidamenti-dialog.component';
import { AffidamentoCheckListDTO } from '../../commons/dto/affidamento-checklist-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { saveAs } from "file-saver-es";
import { EsitoLockCheckListDTO } from '../../commons/dto/esito-lock-checklist-dto';
import { EntitaDTO } from '../../commons/dto/entita-dto';
import { ArchivioFileDialogComponent, InfoDocumentoVo, DocumentoAllegatoDTO, AnteprimaDialogComponent, DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { AssociateFileRequest } from '../../commons/requests/associate-file-request';
import { ConfigService } from 'src/app/core/services/config.service';
import { RespingiAffidamentoRequest } from '../../commons/requests/respingi-affidamento-request';
import { RespingiDialogComponent } from '../respingi-dialog/respingi-dialog.component';
import { SubcontrattoAffidamentiDialogComponent } from '../subcontratto-affidamenti-dialog/subcontratto-affidamenti-dialog.component';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { SubcontrattoAffidDTO } from '../../commons/dto/subcontratto-affid-dto';

@Component({
  selector: 'app-modifica-affidamento',
  templateUrl: './modifica-affidamento.component.html',
  styleUrls: ['./modifica-affidamento.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ModificaAffidamentoComponent implements OnInit {

  idOperazione: number;
  idAppalto: number;
  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;
  isIstruttore: boolean;
  isBeneficiario: boolean;

  tipoChecklistAffidamento: string;

  affidamento: AffidamentoDTO;
  hiddenAddButtons: boolean;
  hiddenSalva: boolean;
  hiddenRespingi: boolean;
  hiddenChecklistButtons: boolean;
  hiddenDisassociaAllegato: boolean;

  mappatura: Array<AffidamentoCheckListDTO>;
  normative: Array<NormativaAffidamentoDTO>;
  normativaSelected: NormativaAffidamentoDTO;
  tipologie: Array<TipoAffidamentoDTO>;
  tipologieFiltered: Array<TipoAffidamentoDTO>;
  tipologiaSelected: TipoAffidamentoDTO;
  categorie: Array<TipologiaAppaltoDTO>;
  categorieFiltered: Array<TipologiaAppaltoDTO>;
  categoriaSelected: TipologiaAppaltoDTO;
  importoBaseGara: number;
  importoBaseGaraFormatted: string;
  soglia: string = '0';
  importoRibAsta: number;
  importoRibAstaFormatted: string;
  precentualeRibAsta: number;
  precentualeRibAstaFormatted: string;
  importoAggiudicato: number;
  importoAggiudicatoFormatted: string;
  importoRendicontabile: number;
  importoRendicontabileFormatted: string;
  dataFirmaContratto: FormControl = new FormControl();
  dataInizioLavori: FormControl = new FormControl();
  dataConsegnaLavori: FormControl = new FormControl();
  oggetto: string;
  identificativoIntervento: string;
  cpa: string;
  cig: string;
  nonPrevisto: boolean = false;
  motivi: Array<MotiveAssenzaDTO>;
  motivoSelected: MotiveAssenzaDTO;
  tipologieAgg: Array<TipologiaAggiudicazioneDTO>;
  tipologieAggFiltered: Array<TipologiaAggiudicazioneDTO>;
  tipologiaAggSelected: TipologiaAggiudicazioneDTO;
  descrizione: string;
  dataGUUE: FormControl = new FormControl();
  dataGURI: FormControl = new FormControl();
  dataQuotidianiNazionali: FormControl = new FormControl();
  dataSitoWebAppaltante: FormControl = new FormControl();
  dataSitoWebOsservatorio: FormControl = new FormControl();
  fornitoriAssociabili: Array<FornitoreDTO>;
  ruoli: Array<CodiceDescrizioneDTO>;
  tipologieVarianti: Array<CodiceDescrizioneDTO>;
  allegatiPerData: Array<Array<DocumentoAllegato>> = new Array<Array<DocumentoAllegato>>();
  notifiche: Array<NotificaDTO>;

  allDisabled: boolean = true;
  isNAdisabled: boolean;
  hasValidationError: boolean;

  displayedColumnsVarianti: string[] = ['tipologia', 'importo', 'data', 'azioni'];
  dataSourceVarianti: MatTableDataSource<VarianteAffidamentoDTO> = new MatTableDataSource<VarianteAffidamentoDTO>();

  displayedColumnsFornitori: string[] = ['denominazione', 'cfiva', 'ruolo', 'azioni'];
  dataSourceFornitori: MatTableDataSource<FornitoreAffidamentoDTO> = new MatTableDataSource<FornitoreAffidamentoDTO>();
  displayedColumnsSubcontratti: string[] = ['denominazioneSub', 'cfivaSub', 'dtFirmaContratto', 'rifContratto', 'importo', 'azioniSub'];

  esitoLockCheckList: EsitoLockCheckListDTO;

  statoChecklistInLoco: string;
  statoChecklistDocumentale: string;
  entita: EntitaDTO;

  @ViewChild("paginatorFornitori", { static: true }) paginatorFornitori: MatPaginator;
  @ViewChild("paginatorVarianti", { static: true }) paginatorVarianti: MatPaginator;
  @ViewChild("affidamentiForm", { static: true }) affidamentiForm: NgForm;
  @ViewChild("aggiudicazioneForm", { static: true }) aggiudicazioneForm: NgForm;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedAffidamento: boolean;
  loadedMappatura: boolean;
  loadedNormative: boolean;
  loadedTipologie: boolean;
  loadedCategorie: boolean;
  loadedTipologieAggiudicazione: boolean;
  loadedMotiviAssenza: boolean;
  loadedAllegati: boolean;
  loadedFornitoriAssociabili: boolean;
  loadedTipologieVarianti: boolean;
  loadedRuoli: boolean;
  loadedNotifiche: boolean;
  loadedSave: boolean = true;
  loadedDownload: boolean = true;
  loadedRespingi: boolean = true;

  esitoCh: boolean = false;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private affidamentiService: AffidamentiService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private configService: ConfigService,
    private dialog: MatDialog,
    private sharedService: SharedService,
    private _adapter: DateAdapter<any>
  ) {
    this._adapter.setLocale('it');
  }
  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ISTR_AFFIDAMENTI) {
            this.isIstruttore = true;
          } else if (this.user.codiceRuolo === Constants.CODICE_RUOLO_BEN_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_PERSONA_FISICA) {
            this.isBeneficiario = true;
          }
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idAppalto = +params['id'];
            this.loadAffidamento();
          });

        }
      }
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_AFFIDAMENTO;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  loadAffidamento(message?: string) {

    this.loadedAffidamento = false;
    this.subscribers.affidamento = this.affidamentiService.getAffidamento(this.idAppalto).subscribe(data => {
      if (data) {
        this.affidamento = data;
        if (this.affidamento.varianti) {
          this.dataSourceVarianti = new MatTableDataSource<VarianteAffidamentoDTO>();
          this.dataSourceVarianti.data = this.affidamento.varianti;
          this.paginatorVarianti.length = this.affidamento.varianti.length;
          this.paginatorVarianti.pageIndex = 0;
          this.dataSourceVarianti.paginator = this.paginatorVarianti;
        }
        if (this.affidamento.fornitori) {
          this.dataSourceFornitori = new MatTableDataSource<FornitoreAffidamentoDTO>();
          this.dataSourceFornitori.data = this.affidamento.fornitori;
          this.paginatorFornitori.length = this.affidamento.fornitori.length;
          this.paginatorFornitori.pageIndex = 0;
          this.dataSourceFornitori.paginator = this.paginatorFornitori;
        }
        this.idProgetto = this.affidamento.proceduraAggiudicazioneDTO.idProgetto;
        this.subscribers.codice = this.affidamentiService.getCodiceProgetto(this.idProgetto).subscribe(res => {
          if (res) {
            this.codiceProgetto = res;
          }
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
        });
        if (message) {
          this.loadAllegati(message);
        } else {
          this.loadMappatura();
          this.loadCombo();
          this.loadAllegati();
          this.popolaCampi();
          if (this.activatedRoute.snapshot.paramMap.get('action') != null && this.activatedRoute.snapshot.paramMap.get('action') === "SUCCESS") {
            this.showMessageSuccess("Salvataggio avvento con successo.");
          }
          if (this.activatedRoute.snapshot.paramMap.get('checklist') != null && this.activatedRoute.snapshot.paramMap.get('checklist') === "SUCCESS") {
            this.showMessageSuccess("Il salvataggio della checklist è avvenuto correttamente.");
          }
        }

        this.valorizzaStatoDelleChecklist();


      }
      this.loadedAffidamento = true;


      // PK  condiziono il bottone della CheckList documentale a seconda che sia Loccata o meno
      /*
      pbandiwebrce ->   CPBEDettaglioAffidamento.goToChecklistValidazione

      gestioneAffidamentiBusiness.salvaLockCheckListValidazione(user
                .getIdUtente(), user.getIdIride(), idProgetto,
                idAffidamento);
      */
      this.subscribers.esitoLockCheckList = this.affidamentiService.salvaLockCheckListValidazione(this.idProgetto, this.idAppalto).subscribe(res => {
        if (res) {
          //console.log('salvaLockCheckListValidazione '+res);
          this.esitoCh = res.esito;
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
      });


    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  loadMappatura() {
    this.loadedMappatura = false;
    this.subscribers.mappatura = this.affidamentiService.getAllAffidamentoChecklist().subscribe(data => {
      if (data) {
        this.mappatura = data;
      }
      this.loadedMappatura = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  loadCombo() {
    this.loadedNormative = false;
    this.subscribers.normative = this.affidamentiService.getNormativeAffidamento().subscribe(data => {
      if (data) {
        this.normative = data;
        this.loadedTipologie = false;
        this.subscribers.tipologie = this.affidamentiService.getTipologieAffidamento().subscribe(data => {
          if (data) {
            this.tipologie = data;
            this.loadedCategorie = false;
            this.subscribers.categorie = this.affidamentiService.getTipologieAppalto().subscribe(data => {
              if (data) {
                this.categorie = data;
                this.loadedTipologieAggiudicazione = false;
                this.subscribers.tipologieAgg = this.affidamentiService.getTipologieProcedureAggiudicazione(this.idProgetto).subscribe(data => {
                  if (data) {
                    this.tipologieAgg = data;
                    this.normativaSelected = this.normative.find(n => n.idNorma === this.affidamento.idNorma);
                    this.onSelectNormativa();
                    this.tipologiaSelected = this.tipologie.find(t => t.idTipoAffidamento === this.affidamento.idTipoAffidamento);
                    this.onSelectTipologia();
                    this.categoriaSelected = this.categorie.find(c => c.idTipologiaAppalto === this.affidamento.idTipologiaAppalto);
                    this.onSelectCategoria();
                    this.tipologiaAggSelected = this.tipologieAgg.find(t => t.idTipologiaAggiudicaz === this.affidamento.proceduraAggiudicazioneDTO.idTipologiaAggiudicaz);
                    this.onSelectTipologiaAgg();
                    this.soglia = this.affidamento.sopraSoglia === "S" ? "2" : (this.affidamento.sopraSoglia === "N" ? "1" : "0");

                    if (this.isIstruttore) {
                      if (this.affidamento.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_IN_VERIFICA) {
                        if (this.statoChecklistInLoco === 'B' || this.statoChecklistDocumentale === 'B') {
                          //del blocco affidamenti disabilito solo normativa, tipologia, categoria,imp base gare, sopra sotto soglia, procedura agg
                          this.setAllDisabled(1);
                        } else {
                          //del blocco affidamenti lascio tutto abilitato fino a procedura agg compresa
                          this.setAllDisabled(2);
                        }
                      } else {
                        this.setAllDisabled();
                        this.hiddenSalva = true;
                      }
                      this.hiddenAddButtons = true;
                      this.hiddenRespingi = true;
                      if (this.affidamento.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_IN_VERIFICA && this.affidamento.respingibile) {
                        this.hiddenRespingi = false;
                      }
                      if (this.affidamento.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_RICHIESTA_INTEGRAZIONE) {
                        this.hiddenDisassociaAllegato = true;
                      }
                    } else if (this.isBeneficiario) {
                      this.hiddenRespingi = true;
                      this.hiddenChecklistButtons = true;

                      if (this.affidamento.idStatoAffidamento !== Constants.ID_STATO_AFFIDAMENTO_DA_INVIARE) {
                        this.setAllDisabled();
                        this.hiddenSalva = true;
                      }
                      if (this.affidamento.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_VERIFICA_DEFINITIVA
                        || this.affidamento.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_IN_VERIFICA) {
                        this.hiddenAddButtons = true;
                      }
                    } else {
                      this.setAllDisabled();
                      this.hiddenSalva = true;
                      this.hiddenAddButtons = true;
                      this.hiddenRespingi = true;
                      this.hiddenChecklistButtons = true;
                    }
                  }
                  this.loadedTipologieAggiudicazione = true;
                }, err => {
                  this.handleExceptionService.handleBlockingError(err);
                });
              }
              this.loadedCategorie = true;
            }, err => {
              this.handleExceptionService.handleBlockingError(err);
            });
          }
          this.loadedTipologie = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }
      this.loadedNormative = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.loadedMotiviAssenza = false;
    this.subscribers.motivi = this.affidamentiService.getMotiveAssenza().subscribe(data => {
      if (data) {
        this.motivi = data;
        this.motivoSelected = this.motivi.find(m => m.idMotivoAssenzaCig === this.affidamento.proceduraAggiudicazioneDTO.idMotivoAssenzaCIG);
      }
      this.loadedMotiviAssenza = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.loadedFornitoriAssociabili = false;
    this.subscribers.fornitoriAssociabili = this.affidamentiService.getFornitoriAssociabili().subscribe(data => {
      if (data) {
        this.fornitoriAssociabili = data;
      }
      this.loadedFornitoriAssociabili = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedFornitoriAssociabili = true;
    });
    this.loadedRuoli = false;
    this.subscribers.ruoli = this.affidamentiService.getRuoli().subscribe(data => {
      if (data) {
        this.ruoli = data;
      }
      this.loadedRuoli = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRuoli = true;
    });
    this.loadedTipologieVarianti = false;
    this.subscribers.tipologieVarianti = this.affidamentiService.getTipologieVarianti().subscribe(data => {
      if (data) {
        this.tipologieVarianti = data;
      }
      this.loadedTipologieVarianti = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedTipologieVarianti = true;
    });
    this.loadedNotifiche = false;
    this.subscribers.notifiche = this.affidamentiService.getNotifiche(this.affidamento.idAppalto).subscribe(data => {
      if (data) {
        this.notifiche = data;
      }
      this.loadedNotifiche = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedNotifiche = true;
    });
  }

  loadAllegati(message?: string) {

    this.loadedAllegati = false;
    this.allegatiPerData = new Array<Array<DocumentoAllegato>>();
    this.subscribers.allegati = this.affidamentiService.getAllegati(this.affidamento.idAppalto).subscribe(data => {
      if (data) {
        for (let d of data) {
          for (let array of this.allegatiPerData) {
            if (array.find(a => a.dataInvio === d.dataInvio)) {
              array.push(d);
              this.trovato = true;
              break;
            }
          }
          if (!this.trovato) {
            let array = new Array<DocumentoAllegato>();
            array.push(d);
            this.allegatiPerData.push(array);
          } else {
            this.trovato = false;
          }
        }
      }
      if (message) {
        this.showMessageSuccess(message);
      }
      this.loadedAllegati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedAllegati = true;
    });
  }

  valorizzaStatoDelleChecklist() {
    console.log('ANG modifica-affidamento  valorizzaStatoDelleChecklist');

    this.subscribers.statoChecklistDocumentale = this.affidamentiService.findEntita("PBANDI_T_APPALTO").subscribe(res => {
      if (res) {
        console.log("ANG modifica-affidamento  valorizzaStatoDelleChecklist res =" + res);
        this.entita = res;

        // Long idTipoDocIndexDoc = 29L; // Checklist affidamento documentale.
        this.getStatoCLDocumentale(29);

        // Long idTipoDocIndexInLoco = 30L; // Checklist affidamento in loco.
        this.getStatoCLDocumentale(30);

      }

    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  getStatoCLDocumentale(idTipoDocIndexDoc?: number) {

    console.log("ANG modifica-affidamento  getStatoCLDocumentale  idEntita=" + this.entita.idEntita);
    console.log("ANG modifica-affidamento  getStatoCLDocumentale  idAppalto=" + this.affidamento.idAppalto);
    console.log("ANG modifica-affidamento  getStatoCLDocumentale  idProgetto=" + this.idProgetto);

    this.subscribers.statoChecklistDocumentale = this.affidamentiService.leggiStatoChecklist(
      this.entita.idEntita, // 262
      this.affidamento.idAppalto, //idTarget
      idTipoDocIndexDoc,
      this.idProgetto
    ).subscribe(res => {
      if (res) {
        console.log("ANG modifica-affidamento  getStatoCLDocumentale");
        if (idTipoDocIndexDoc == 29) {
          this.statoChecklistDocumentale = res;
          console.log("ANG modifica-affidamento  statoChecklistDocumentale=" + res);
        }
        if (idTipoDocIndexDoc == 30) {
          this.statoChecklistInLoco = res;
          console.log("ANG modifica-affidamento  statoChecklistInLoco=" + res);
        }
      }

    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  trovato: boolean;

  popolaCampi() {
    this.oggetto = this.affidamento.oggettoAppalto;
    this.importoBaseGara = this.affidamento.bilancioPreventivo || null;
    if (this.importoBaseGara !== null) {
      this.importoBaseGaraFormatted = this.sharedService.formatValue(this.importoBaseGara.toString());
    }
    this.importoAggiudicato = this.affidamento.importoContratto || null;
    if (this.importoAggiudicato !== null) {
      this.importoAggiudicatoFormatted = this.sharedService.formatValue(this.importoAggiudicato.toString());
    }
    this.importoRendicontabile = this.affidamento.impRendicontabile || null;
    if (this.importoRendicontabile !== null) {
      this.importoRendicontabileFormatted = this.sharedService.formatValue(this.importoRendicontabile.toString());
    }
    this.importoRibAsta = this.affidamento.impRibassoAsta || null;
    if (this.importoRibAsta !== null) {
      this.importoRibAstaFormatted = this.sharedService.formatValue(this.importoRibAsta.toString());
    }
    this.precentualeRibAsta = this.affidamento.percRibassoAsta || null;
    if (this.precentualeRibAsta !== null) {
      this.precentualeRibAstaFormatted = this.sharedService.formatValue(this.precentualeRibAsta.toString());
    }
    if (this.affidamento.dtGuue) this.dataGUUE = new FormControl(new Date(this.affidamento.dtGuue));
    if (this.affidamento.dtGuri) this.dataGURI = new FormControl(new Date(this.affidamento.dtGuri));
    if (this.affidamento.dtQuotNazionali) this.dataQuotidianiNazionali = new FormControl(new Date(this.affidamento.dtQuotNazionali));
    if (this.affidamento.dtWebStazAppaltante) this.dataSitoWebAppaltante = new FormControl(new Date(this.affidamento.dtWebStazAppaltante));
    if (this.affidamento.dtWebOsservatorio) this.dataSitoWebOsservatorio = new FormControl(new Date(this.affidamento.dtWebOsservatorio));
    if (this.affidamento.dtInizioPrevista) this.dataInizioLavori = new FormControl(new Date(this.affidamento.dtInizioPrevista));
    if (this.affidamento.dtConsegnaLavori) this.dataConsegnaLavori = new FormControl(new Date(this.affidamento.dtConsegnaLavori));
    this.dataFirmaContratto = new FormControl(new Date(this.affidamento.dtFirmaContratto));
    this.identificativoIntervento = this.affidamento.interventoPisu;
    this.descrizione = this.affidamento.proceduraAggiudicazioneDTO.descProcAgg;
    this.cig = this.affidamento.proceduraAggiudicazioneDTO.cigProcAgg;
    this.cpa = this.affidamento.proceduraAggiudicazioneDTO.codProcAgg;
    if (!this.cig) {
      this.nonPrevisto = true;
    }
  }

  get isProceduraAggEditable() {
    if (this.isBeneficiario) {
      return true;
    }
    if (this.isIstruttore
      && this.affidamento?.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_IN_VERIFICA
      && this.statoChecklistInLoco !== 'B' && this.statoChecklistDocumentale !== 'B') {
      return true;
    }
    return false;
  }

  onSelectNormativa() {
    let rows = this.mappatura.filter(row => row.idNorma === this.normativaSelected.idNorma);
    this.tipologieFiltered = new Array<TipoAffidamentoDTO>();
    rows.map(row => row.idTipoAffidamento).forEach(id => {
      if (!this.tipologieFiltered.find(t => t.idTipoAffidamento === id)) {
        this.tipologieFiltered.push(this.tipologie.find(t => t.idTipoAffidamento === id));
      }
    });
    let sogliaVett = rows.map(row => row.sopraSoglia);
    this.isNAdisabled = sogliaVett.find(n => n !== "S" && n !== "N") != undefined ? false : true;
    this.allDisabled = sogliaVett.find(n => n === "S") != undefined && sogliaVett.find(n => n === "N") != undefined ? false : true;
    this.soglia = "0";
    this.tipologiaSelected = null;
    this.categoriaSelected = null;
  }

  onSelectTipologia() {
    let rows = this.mappatura.filter(row => row.idNorma === this.normativaSelected.idNorma && row.idTipoAffidamento === this.tipologiaSelected.idTipoAffidamento);
    this.categorieFiltered = new Array<TipologiaAppaltoDTO>();
    rows.map(row => row.idTipoAppalto).forEach(id => {
      if (!this.categorieFiltered.find(t => t.idTipologiaAppalto === id)) {
        this.categorieFiltered.push(this.categorie.find(t => t.idTipologiaAppalto === id));
      }
    });
    let sogliaVett = rows.map(row => row.sopraSoglia);
    this.isNAdisabled = sogliaVett.find(n => n !== "S" && n !== "N") != undefined ? false : true;
    this.allDisabled = sogliaVett.find(n => n === "S") != undefined && sogliaVett.find(n => n === "N") != undefined ? false : true;
    this.categoriaSelected = null;
  }

  onSelectCategoria() {
    let rows = this.mappatura.filter(row => row.idNorma === this.normativaSelected.idNorma
      && row.idTipoAffidamento === this.tipologiaSelected.idTipoAffidamento
      && row.idTipoAppalto === this.categoriaSelected.idTipologiaAppalto);
    this.tipologieAggFiltered = new Array<TipologiaAggiudicazioneDTO>();
    rows.map(row => row.idTipologiaAggiudicaz).forEach(id => {
      if (!this.tipologieAggFiltered.find(t => t.idTipologiaAggiudicaz === id)) {
        this.tipologieAggFiltered.push(this.tipologieAgg.find(t => t.idTipologiaAggiudicaz === id));
      }
    });
    let sogliaVett = rows.map(row => row.sopraSoglia);
    this.isNAdisabled = sogliaVett.find(n => n !== "S" && n !== "N") != undefined ? false : true;
    this.allDisabled = sogliaVett.find(n => n === "S") != undefined && sogliaVett.find(n => n === "N") != undefined ? false : true;
    this.tipologiaAggSelected = null;
  }

  onSelectTipologiaAgg() {
    let rows = this.mappatura.filter(row => row.idNorma === this.normativaSelected.idNorma
      && row.idTipoAffidamento === this.tipologiaSelected.idTipoAffidamento
      && row.idTipoAppalto === this.categoriaSelected.idTipologiaAppalto
      && row.idTipologiaAggiudicaz === this.tipologiaAggSelected.idTipologiaAggiudicaz)
      ;
    let sogliaVett = rows.map(row => row.sopraSoglia);
    this.isNAdisabled = sogliaVett.find(n => n !== "S" && n !== "N") != undefined ? false : true;
    this.allDisabled = sogliaVett.find(n => n === "S") != undefined && sogliaVett.find(n => n === "N") != undefined ? false : true;
  }

  setImportoBaseGara() {
    this.importoBaseGara = this.sharedService.getNumberFromFormattedString(this.importoBaseGaraFormatted);
    if (this.importoBaseGara !== null) {
      this.importoBaseGaraFormatted = this.sharedService.formatValue(this.importoBaseGara.toString());
    }
  }

  setImportoRibAsta() {
    this.importoRibAsta = this.sharedService.getNumberFromFormattedString(this.importoRibAstaFormatted);
    if (this.importoRibAsta !== null) {
      this.importoRibAstaFormatted = this.sharedService.formatValue(this.importoRibAsta.toString());
    }
  }

  setPercentualeRibAsta() {
    this.precentualeRibAsta = this.sharedService.getNumberFromFormattedString(this.precentualeRibAstaFormatted);
    if (this.precentualeRibAsta !== null) {
      this.precentualeRibAstaFormatted = this.sharedService.formatValue(this.precentualeRibAsta.toString());
    }
  }

  setImportoAggiudicato() {
    this.importoAggiudicato = this.sharedService.getNumberFromFormattedString(this.importoAggiudicatoFormatted);
    if (this.importoAggiudicato !== null) {
      this.importoAggiudicatoFormatted = this.sharedService.formatValue(this.importoAggiudicato.toString());
    }
  }

  setImportoRendicontabile() {
    this.importoRendicontabile = this.sharedService.getNumberFromFormattedString(this.importoRendicontabileFormatted);
    if (this.importoRendicontabile !== null) {
      this.importoRendicontabileFormatted = this.sharedService.formatValue(this.importoRendicontabile.toString());
    }
  }

  aggiungiVariante() {
    this.resetMessageSuccess();
    this.resetMessageError();

    let dialogRef = this.dialog.open(VarianteAffidamentiDialogComponent, {
      width: '50%',
      data: {
        tipologieVarianti: this.tipologieVarianti,
        disabled: false
      }
    });

    dialogRef.afterClosed().subscribe(res => {
      if (res !== "ANNULLA") {
        res.idAppalto = this.affidamento.idAppalto;
        this.loadedSave = false;
        this.subscribers.aggiungiVariante = this.affidamentiService.salvaVariante(res).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamento(data.message);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedSave = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSave = true;

          this.showMessageError("Errore nel salvataggio della variante.");
        });
      }
    });
  }

  modificaVariante(idVariante: number) {
    this.resetMessageSuccess();
    this.resetMessageError();

    let variante = this.affidamento.varianti.find(v => v.idVariante === idVariante);
    let dialogRef = this.dialog.open(VarianteAffidamentiDialogComponent, {
      width: '50%',
      data: {
        tipologieVarianti: this.tipologieVarianti,
        idTipologiaVariante: variante.idTipologiaVariante,
        importo: variante.importo,
        note: variante.note,
        disabled: false
      }
    });

    dialogRef.afterClosed().subscribe(res => {
      if (res !== "ANNULLA") {
        res.idAppalto = this.affidamento.idAppalto;
        res.idVariante = idVariante;
        this.loadedSave = false;
        this.subscribers.modificaVariante = this.affidamentiService.salvaVariante(res).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamento(data.message);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedSave = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSave = true;
          this.showMessageError("Errore nel salvataggio della variante.");
        });
      }
    });
  }

  visualizzaVariante(idVariante: number) {
    let variante = this.affidamento.varianti.find(v => v.idVariante === idVariante);
    this.dialog.open(VarianteAffidamentiDialogComponent, {
      width: '50%',
      data: {
        tipologieVarianti: this.tipologieVarianti,
        idTipologiaVariante: variante.idTipologiaVariante,
        importo: variante.importo,
        note: variante.note,
        disabled: true
      }
    });
  }

  eliminaVariante(idVariante: number) {
    this.resetMessageSuccess();
    this.resetMessageError();

    let variante = this.affidamento.varianti.find(v => v.idVariante === idVariante);
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione della seguente variante?<br>" + variante.descrizioneTipologiaVariante + " con Importo " + variante.importo.toFixed(2).replace(/\./g, ',')
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedSave = false;
        this.subscribers.eliminaVariante = this.affidamentiService.cancellaVariante(idVariante).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamento(data.message);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedSave = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSave = true;
          this.showMessageError("Errore nell'eliminazione della variante.");
        });
      }
    });
  }

  aggiungiFornitore() {
    this.resetMessageSuccess();
    this.resetMessageError();

    let dialogRef = this.dialog.open(FornitoreAffidamentiDialogComponent, {
      width: '50%',
      data: {
        fornitori: this.fornitoriAssociabili ? this.fornitoriAssociabili.filter(f1 => !this.affidamento.fornitori.find(f2 => f2.idFornitore === f1.idFornitore)) : [],
        ruoli: this.ruoli
      }
    });

    dialogRef.afterClosed().subscribe(res => {
      if (res !== "ANNULLA") {
        res.idAppalto = this.affidamento.idAppalto;
        this.loadedSave = false;
        this.subscribers.aggiungiFornitore = this.affidamentiService.salvaFornitore(res).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamento(data.message);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedSave = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSave = true;
          this.showMessageError("Errore nel salvataggio del fornitore.");
        });
      }
    });
  }

  eliminaFornitore(idFornitore: number) {
    this.resetMessageSuccess();
    this.resetMessageError();

    let fornitore = this.affidamento.fornitori.find(v => v.idFornitore === idFornitore);
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione del seguente fornitore?<br>" + (fornitore.denominazioneFornitore ? fornitore.denominazioneFornitore : (fornitore.cognomeFornitore + " " + fornitore.nomeFornitore)) + " con ruolo " + fornitore.descTipoPercettore
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedSave = false;
        this.subscribers.eliminaFornitore = this.affidamentiService.cancellaFornitore(idFornitore, fornitore.idAppalto, fornitore.idTipoPercettore).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamento(data.message);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedSave = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSave = true;
          this.showMessageError("Errore nell'eliminazione del fornitore.");
        });
      }
    });
  }

  aggiungiSubcontratto(fornitore: FornitoreAffidamentoDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();

    let dialogRef = this.dialog.open(SubcontrattoAffidamentiDialogComponent, {
      width: '40%',
      data: {
        fornitori: this.fornitoriAssociabili ?
          this.fornitoriAssociabili.filter(f1 => !this.affidamento.fornitori.find(f2 => f2.idFornitore === f1.idFornitore)// escludo i fornitori gia associati
            && (!(fornitore.subcontrattiAffid?.length > 0) || !fornitore.subcontrattiAffid.find(sub => sub.idSubcontraente === f1.idFornitore))) : [], //escludo i subcontraenti di questo fornitore
      }
    });

    dialogRef.afterClosed().subscribe(res => {
      if (res !== "ANNULLA") {
        res.idAppalto = this.affidamento.idAppalto;
        res.idFornitore = fornitore.idFornitore;
        this.loadedSave = false;
        this.affidamentiService.salvaSubcontratto(res).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamento(data.message);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedSave = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSave = true;
          this.showMessageError("Errore nel salvataggio del subcontratto.");
        });
      }
    });
  }

  modificaSubcontratto(fornitore: FornitoreAffidamentoDTO, subcontratto: SubcontrattoAffidDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();

    let dialogRef = this.dialog.open(SubcontrattoAffidamentiDialogComponent, {
      width: '40%',
      data: {
        fornitori: this.fornitoriAssociabili ?
          this.fornitoriAssociabili.filter(f1 => !this.affidamento.fornitori.find(f2 => f2.idFornitore === f1.idFornitore)// escludo i fornitori gia associati
            && !fornitore.subcontrattiAffid.find(sub => sub.idSubcontraente === f1.idFornitore && sub.idSubcontrattoAffidamento !== subcontratto.idSubcontrattoAffidamento)) : [], //escludo i subcontraenti di questo fornitore, tranne il subcontraente corrente
        subcontratto: subcontratto
      }
    });

    dialogRef.afterClosed().subscribe(res => {
      if (res !== "ANNULLA") {
        res.idAppalto = this.affidamento.idAppalto;
        res.idFornitore = fornitore.idFornitore;
        this.loadedSave = false;
        this.affidamentiService.salvaSubcontratto(res).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamento(data.message);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedSave = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSave = true;
          this.showMessageError("Errore nel salvataggio del subcontratto.");
        });
      }
    });
  }

  eliminaSubcontratto(subcontratto: SubcontrattoAffidDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();

    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione del subcontratto con " + subcontratto.denominazioneSubcontraente + "?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedSave = false;
        this.affidamentiService.cancellaSubcontratto(subcontratto.idSubcontrattoAffidamento).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamento(data.message);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedSave = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSave = true;
          this.showMessageError("Errore nell'eliminazione del subcontratto.");
        });
      }
    });
  }

  downloadAllegato(allegato: DocumentoAllegato) {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.loadedDownload = false;
    this.subscribers.download = this.sharedService.getContenutoDocumentoById(allegato.id).subscribe(res => {
      if (res) {
        saveAs(res, allegato.nome);
        this.showMessageSuccess("Download avvenuto con successo.");
      }
      this.loadedDownload = true;
    }, error => {
      this.handleExceptionService.handleNotBlockingError(error);
      this.loadedDownload = true;
      this.showMessageError("Errore nel download.");
    });
  }

  anteprimaAllegato(allegato: DocumentoAllegato) {
    this.resetMessageError();
    let all = new Array<DocumentoAllegato>();
    this.allegatiPerData.forEach(allegati => {
      all.push(...allegati);
    });
    let dataSourceTable = new MatTableDataSource<InfoDocumentoVo>(all.map(a => new InfoDocumentoVo(null, a.nome, null, a.sizeFile.toString(), true, null, null, a.id.toString(), null)));
    let comodo = new Array<any>();
    comodo.push(allegato.nome);
    comodo.push(allegato.id);
    comodo.push(dataSourceTable);
    comodo.push(this.configService.getApiURL());

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });
  }

  aggiungiAllegato() {
    let all = new Array<DocumentoAllegatoDTO>();
    this.allegatiPerData.forEach(allegati => {
      all.push(...allegati.map(a => new DocumentoAllegatoDTO(null, null, a.id, null, a.isDisassociabile, null, this.idProgetto, a.nome, null, null, null)))
    });
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegati: all,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded()
      }
    });

    dialogRef.afterClosed().subscribe((res: Array<InfoDocumentoVo>) => {
      if (res && res.length > 0) {
        this.loadedSave = false;
        let request = new AssociateFileRequest(this.idProgetto, this.idAppalto, res.map(a => a.idDocumentoIndex.toString()), null);
        this.subscribers.associa = this.affidamentiService.associateFileAffidamento(request).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamento(data.msg);
            } else {
              this.showMessageError(data.msg);
            }
          }
          this.loadedSave = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSave = true;
          this.showMessageError("Errore nell'associazione degli allegati.");
        });
      }
    });
  }

  disassociaAllegato(id: number) {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.loadedSave = false;
    this.subscribers.disassocia = this.affidamentiService.dissociateFileAffidamento(id, this.idProgetto, this.idAppalto).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.loadAffidamento(data.msg);
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedSave = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedSave = true;
      this.showMessageError("Errore nella disassociazione dell'allegato.");
    });
  }

  openNotifiche() {
    this.dialog.open(NotificheAffidamentiDialogComponent, {
      width: '70%',
      data: {
        notifiche: this.notifiche
      }
    });
  }

  checkRequiredForm(form: NgForm, name: string) {
    if (!form.form.get(name) || !form.form.get(name).value) {
      form.form.get(name).setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
  }

  setAllDisabled(option?: number) {
    if (!option) {
      Object.keys(this.affidamentiForm.form.controls).forEach(key => {
        if (this.affidamentiForm.form.controls[key]) {
          this.affidamentiForm.form.controls[key].disable();
        }
      });
      this.dataFirmaContratto.disable();
      this.dataInizioLavori.disable();
      this.dataConsegnaLavori.disable();
      Object.keys(this.aggiudicazioneForm.form.controls).forEach(key => {
        if (this.aggiudicazioneForm.form.controls[key]) {
          this.aggiudicazioneForm.form.controls[key].disable();
        }
      });
    } else if (option === 1) {
      this.affidamentiForm.form.controls["normativa"].disable();
      //this.affidamentiForm.form.controls["tipologia"].disable(); disabilitato in html
      // this.affidamentiForm.form.controls["categoria"].disable(); disabilitato in html
      this.affidamentiForm.form.controls["importoBaseGara"].disable();
      //this.affidamentiForm.form.controls["soglia"].disable();    disabilitato in html

      Object.keys(this.aggiudicazioneForm.form.controls).forEach(key => {
        if (this.aggiudicazioneForm.form.controls[key]) {
          this.aggiudicazioneForm.form.controls[key].disable();
        }
      });
    }
    this.dataGUUE.disable();
    this.dataGURI.disable();
    this.dataQuotidianiNazionali.disable();
    this.dataSitoWebAppaltante.disable();
    this.dataSitoWebOsservatorio.disable();
  }

  setAllMarkAsTouched() {
    Object.keys(this.affidamentiForm.form.controls).forEach(key => {
      if (this.affidamentiForm.form.controls[key]) {
        this.affidamentiForm.form.controls[key].markAsTouched();
      }
    });
    Object.keys(this.aggiudicazioneForm.form.controls).forEach(key => {
      if (this.aggiudicazioneForm.form.controls[key]) {
        this.aggiudicazioneForm.form.controls[key].markAsTouched();
      }
    });
    this.dataFirmaContratto.markAsTouched();
    this.dataInizioLavori.markAsTouched();
    this.dataConsegnaLavori.markAsTouched();
    this.dataGUUE.markAsTouched();
    this.dataGURI.markAsTouched();
    this.dataQuotidianiNazionali.markAsTouched();
    this.dataSitoWebAppaltante.markAsTouched();
    this.dataSitoWebOsservatorio.markAsTouched();
  }

  validate() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.hasValidationError = false;
    this.checkRequiredForm(this.affidamentiForm, "normativa");
    this.checkRequiredForm(this.affidamentiForm, "tipologia");
    this.checkRequiredForm(this.affidamentiForm, "categoria");
    this.checkRequiredForm(this.affidamentiForm, "importoAggiudicato");
    this.checkRequiredForm(this.affidamentiForm, "importoRendicontabile");
    this.checkRequiredForm(this.affidamentiForm, "oggetto");
    this.checkRequiredForm(this.aggiudicazioneForm, "cpa");
    this.checkRequiredForm(this.aggiudicazioneForm, "tipologiaAgg");
    this.checkRequiredForm(this.aggiudicazioneForm, "descrizione");
    if (!this.dataFirmaContratto || !this.dataFirmaContratto.value) {
      this.dataFirmaContratto.setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
    if (this.nonPrevisto) {
      this.checkRequiredForm(this.affidamentiForm, "importoBaseGara");
      this.checkRequiredForm(this.aggiudicazioneForm, "motivo");
    } else {
      this.checkRequiredForm(this.aggiudicazioneForm, "cig");
    }
    if (this.affidamentiForm.form.get("precentualeRibAsta") && this.affidamentiForm.form.get("precentualeRibAsta").value) {
      let perc = this.affidamentiForm.form.get("precentualeRibAsta").value;
      if (perc < 0 || perc > 100) {
        this.affidamentiForm.form.get("precentualeRibAsta").setErrors({ error: 'notAPercentage' });
      }
    }
    if (!this.allDisabled && this.isNAdisabled) {
      if (this.soglia === "0") {
        this.affidamentiForm.form.get("soglia").setErrors({ error: 'required' });
        this.hasValidationError = true;
      }
    }

    this.setAllMarkAsTouched();
    if (this.hasValidationError) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    }
  }

  setAffidamento(): Affidamento {
    let affidamento = new Affidamento();
    affidamento.idAppalto = this.idAppalto;
    affidamento.idProceduraAggiudicaz = this.affidamento.idProceduraAggiudicaz;
    affidamento.idProgetto = this.idProgetto;
    affidamento.oggettoAppalto = this.oggetto;
    affidamento.idTipologiaAppalto = this.categoriaSelected.idTipologiaAppalto;
    affidamento.descTipologiaAppalto = this.categoriaSelected.descTipologiaAppalto;
    affidamento.idTipoAffidamento = this.tipologiaSelected.idTipoAffidamento;
    affidamento.idStatoAffidamento = this.affidamento?.idStatoAffidamento ? this.affidamento?.idStatoAffidamento : Constants.ID_STATO_AFFIDAMENTO_DA_INVIARE;
    affidamento.descStatoAffidamento = this.affidamento?.descStatoAffidamento ? this.affidamento?.descStatoAffidamento : Constants.DESC_STATO_AFFIDAMENTO_DA_INVIARE;
    affidamento.idNorma = this.normativaSelected.idNorma;
    affidamento.bilancioPreventivo = this.importoBaseGara;
    affidamento.importoContratto = this.importoAggiudicato;
    affidamento.impRendicontabile = this.importoRendicontabile;
    affidamento.impRibassoAsta = this.importoRibAsta;
    affidamento.percRibassoAsta = this.precentualeRibAsta;
    affidamento.dtGuue = this.dataGUUE.value;
    affidamento.dtGuri = this.dataGURI.value;
    affidamento.dtQuotNazionali = this.dataQuotidianiNazionali.value;
    affidamento.dtWebStazAppaltante = this.dataSitoWebAppaltante.value;
    affidamento.dtWebOsservatorio = this.dataSitoWebOsservatorio.value;
    affidamento.dtInizioPrevista = this.dataInizioLavori.value;
    affidamento.dtConsegnaLavori = this.dataConsegnaLavori.value;
    affidamento.dtFirmaContratto = this.dataFirmaContratto.value;
    affidamento.interventoPisu = this.identificativoIntervento;
    affidamento.sopraSoglia = this.soglia === "0" ? null : (this.soglia === "1" ? "N" : "S");
    affidamento.descProcAgg = this.descrizione;
    affidamento.dtInserimento = new Date();
    if (!this.nonPrevisto) {
      affidamento.cigProcAgg = this.cig;
    }
    affidamento.codProcAgg = this.cpa;

    return affidamento;
  }

  salva() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.validate();
    if (!this.hasValidationError) {
      this.loadedSave = false;
      let procAgg = new ProceduraAggiudicazioneAffidamento(this.affidamento.idProceduraAggiudicaz, this.idProgetto, this.tipologiaAggSelected.idTipologiaAggiudicaz, this.motivoSelected && this.nonPrevisto ? this.motivoSelected.idMotivoAssenzaCig : null,
        this.descrizione, this.nonPrevisto ? null : this.cig, this.cpa, this.importoAggiudicato, null, this.dataFirmaContratto.value, new Date(), null, null, null, this.nonPrevisto);
      this.subscribers.save = this.affidamentiService.salvaAffidamento(new SalvaAffidamentoRequest(this.setAffidamento(), procAgg)).subscribe(data => {
        if (data) {
          if (data.esito) {
            this.loadAffidamento(data.msg);
            this.showMessageSuccess("Salvataggio avvenuto con successo.")
          } else {
            this.showMessageError(data.msg);
          }
        }
        this.loadedSave = true;
      }), err => {
        this.handleExceptionService.handleNotBlockingError(err);

        this.showMessageError("Errore nel salvataggio dell'affidamento.")
        this.loadedSave = true;
      };
    }
  }

  respingi() {
    let dialogRef = this.dialog.open(RespingiDialogComponent, {
      width: '60%'
    });
    dialogRef.afterClosed().subscribe(motivazione => {
      if (motivazione) {
        this.loadedRespingi = false;
        this.subscribers.respingi = this.affidamentiService.respingiAffidamento(
          new RespingiAffidamentoRequest(this.idAppalto, motivazione)).subscribe(data => {
            if (data) {
              if (data.esito) {
                this.goToGestioneAffidamenti(data.message);
              } else {
                this.showMessageError(data.message);
              }
            }
            this.loadedRespingi = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.loadedRespingi = true;
            this.showMessageError("Errore nel respingere l'affidamento.");
          });
      }
    });

  }

  goToGestioneAffidamenti(message?: string) {
    let url: string = "drawer/" + this.idOperazione + "/affidamenti/" + this.idProgetto;
    if (message) {
      url += ";message=" + message;
    }
    this.router.navigateByUrl(url);
  }

  goToDatiProgettoEAttivitaPregresse() {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  goToChecklistDocumentale() {
    console.log('ANG modifica-affidamento this.idProgetto=' + this.idProgetto);
    console.log('ANG modifica-affidamento this.idAppalto=' + this.idAppalto);
    //this.router.navigateByUrl("drawer/" +this.idOperazione + "/nuovaChecklist/"+this.idAppalto);
    this.tipoChecklistAffidamento = 'CLA';
    this.router.navigate(["drawer/" + this.idOperazione + "/nuovaChecklist/" + this.idAppalto, { progetto: this.idProgetto, tipoChecklistAffidamento: this.tipoChecklistAffidamento }]);
  }

  goToChecklistInLoco() {
    console.log('ANG modifica-affidamento goToChecklistInLoco');
    this.tipoChecklistAffidamento = 'CLILA';
    this.router.navigate(["drawer/" + this.idOperazione + "/nuovaChecklist/" + this.idAppalto, { progetto: this.idProgetto, tipoChecklistAffidamento: this.tipoChecklistAffidamento }]);
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

  isLoading() {
    if (!this.loadedAffidamento || !this.loadedMappatura || !this.loadedNormative || !this.loadedCategorie || !this.loadedTipologie
      || !this.loadedTipologieAggiudicazione || !this.loadedMotiviAssenza || !this.loadedSave || !this.loadedFornitoriAssociabili
      || !this.loadedRuoli || !this.loadedTipologieVarianti || !this.loadedNotifiche || !this.loadedDownload) {
      return true;
    }
    return false;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

}
