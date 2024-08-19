/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ChangeDetectionStrategy, OnInit, AfterViewInit, ChangeDetectorRef, ViewChild, ElementRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { DatePipe, Location } from "@angular/common";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { DateAdapter } from '@angular/material/core';
import { MatTableDataSource } from '@angular/material/table';
import * as _ from "lodash";
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { InizializzazioneService } from "src/app/shared/services/inizializzazione.service";
import { Constants } from 'src/app/core/commons/util/constants';
import { SharedService } from 'src/app/shared/services/shared.service';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
import { DettaglioRevoca } from "../../commons/models/dettaglio-revoca";
import { ModalitaAgevolazioneErogazioneDTO } from "../../commons/dto/modalita-agevolazione-erogazione-dto";
import { ModalitaAgevolazioneDTO } from "../../commons/dto/modalita-agevolazione-dto";
import { RigaModificaRevocaItem } from "../../commons/models/riga-modifica-revoca-item";
import { RigaRevocaItem } from "../../commons/models/riga-revoca-item";
import { CausaleErogazioneDTO } from "../../commons/dto/causale-erogazione-dto";
import { RecuperoDTO } from "../../commons/dto/recupero-dto";
import { RequestSalvaRevoche } from "../../commons/models/request-salva-revoche";
import { RequestModificaDisimpegno } from "../../commons/models/request-modifica-disimpegno";
import { EsitoSalvaRevocaDTO } from "../../commons/dto/esito-salva-revoca-dto";
import { DisimpegniService } from "../../services/disimpegni.service";

import { NgForm, FormControl, Validators } from "@angular/forms";
import { EsitoRichiestaErogazioneDTO } from "../../commons/dto/esito-richiesta-erogazione-dto";
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { AgevolazioneRow } from '../../commons/models/agevolazione-row';
import { Disimpegni2Service } from '../../services/disimpegni-2.service';

@Component({
  selector: 'app-nuovo-disimpegno',
  templateUrl: './nuovo-disimpegno.component.html',
  styleUrls: ['./nuovo-disimpegno.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoDisimpegnoComponent implements OnInit {

  action: string; // inserisci, modifica, dettaglio
  title: string;
  titleInserisci: string = 'Nuovo Disimpegno';
  titleModifica: string = 'Modifica Disimpegno';
  titleDettaglio: string = 'Dettaglio Disimpegno';
  matchUrlInserisci: string = 'nuovo-disimpegno';
  matchUrlModifica: string = 'modifica-disimpegno';
  matchUrlDettaglio: string = 'dettaglio-disimpegno';

  idProgetto: number;
  idRevoca: number;
  codiceProgetto: string;
  isDisabledRev: boolean;
  modalitaAgevolazione: ModalitaAgevolazioneDTO[];
  disimpegni: RigaModificaRevocaItem[];
  revoche: RigaRevocaItem[];
  inputNumberType: string = 'float';
  user: UserInfoSec;

  isAssociataIrr: boolean;
  isRevoca: boolean;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedImportoValidatoTotale: boolean;
  loadedCausaleDisimpegno: boolean;
  loadedMotivazioni: boolean;
  loadedModalitaRecupero: boolean;
  loadedDettaglioRevoca: boolean;
  loadedModalitaAgevolazione: boolean;
  loadedDisimpegni: boolean;
  loadedRevoche: boolean;
  loadedSalva: boolean = true;
  loadedCheckIrr: boolean = true;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  erogazioneMessageWarning: string;
  isErogazioneMessageWarningVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  //HEADER LABELS BUTTONS
  headerLabels: CodiceDescrizione[];
  headerButtons: CodiceDescrizione[];

  causaliDisimpegno: CodiceDescrizione[];
  motivazioni: CodiceDescrizione[];
  modalitaRecupero: CodiceDescrizione[];
  dettaglioRevoca: DettaglioRevoca;
  fieldCodiceCausaleDisimpegnoSelected: CodiceDescrizione;
  fieldMotivoRevocaSelected: CodiceDescrizione;
  fieldModalitaRecuperoSelected: CodiceDescrizione;
  flagOrdineRecupero: string;
  dataRevoca: Date;
  fieldImportoRevoca: number;
  fieldEstremiDeterminaRevoca: string;
  fieldNoteRevoca: string;
  importoValidatoTotale: number;

  modalitaAgevErogRows: Array<AgevolazioneRow>;
  displayedColumnsModalitaAgevolazioneErogazione: string[] = ['descBreveModalitaAgevolaz', 'quotaImportoAgevolato', 'importoErogazioni', 'totaleImportoRecuperato', 'data', 'importoNuovo'];
  dataSourceModalitaAgevolazioneErogazione: MatTableDataSource<RigaRevocaItem> = new MatTableDataSource<RigaRevocaItem>();

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private inizializzazioneService: InizializzazioneService,
    private datePipe: DatePipe,
    private disimpegniService: DisimpegniService,
    private disimpegni2Service: Disimpegni2Service,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private sharedService: SharedService,
    private configService: ConfigService,
    private _adapter: DateAdapter<any>
  ) {
    this._adapter.setLocale('it');
  }


  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (this.router.url.match('/' + this.matchUrlInserisci + '/')) {
        this.action = 'inserisci';
      } else if (this.router.url.match('/' + this.matchUrlModifica + '/')) {
        this.action = 'modifica';
      } else if (this.router.url.match('/' + this.matchUrlDettaglio + '/')) {
        this.action = 'dettaglio';
      } else {
        console.error('router url not matched', this.router.url);
      }
      if (data) {
        this.user = data;
        if (this.user.idIride && this.user.codiceRuolo && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            if (this.action == 'inserisci' && params['id']) {
              this.title = this.titleInserisci;
              this.idProgetto = +params['id'];
              this.loadData();
              this.loadModalitaAgevolazione();
              this.loadRevoche();
            } else if (this.action == 'modifica' && params['id'] && params['id2']) {
              this.title = this.titleModifica;
              this.idProgetto = +params['id'];
              this.idRevoca = +params['id2'];
              this.loadData();
              this.loadDettaglioRevoca();
            } else if (this.action == 'dettaglio' && params['id'] && params['id2']) {
              this.title = this.titleDettaglio;
              this.idProgetto = +params['id'];
              this.idRevoca = +params['id2'];
              this.loadData();
              this.loadDettaglioRevoca();
            } else {

            }
          });
        }
      }
    });
  }

  loadData() {
    //LOAD CODICE PROGETTO
    this.loadedCodiceProgetto = false;
    this.subscribers.codiceProgetto = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe((res: string) => {
      if (res) {
        this.codiceProgetto = res;
        this.setupHeaderLabelsButtons();
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedCodiceProgetto = true;
    });
    //LOAD CAUSALE DISIMPEGNO
    this.loadedCausaleDisimpegno = false;
    this.subscribers.causaleDisimpegno = this.disimpegniService.getCausaleDisimpegno().subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.causaliDisimpegno = res;
      }
      this.loadedCausaleDisimpegno = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedCausaleDisimpegno = true;
    });
    //LOAD MOTIVAZIONI
    this.loadedMotivazioni = false;
    this.subscribers.motivazioni = this.disimpegniService.getMotivazioni(this.idProgetto).subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.motivazioni = res;
      }
      this.loadedMotivazioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedMotivazioni = true;
    });
    //LOAD MODALITA RECUPERO
    this.loadedModalitaRecupero = false;
    this.subscribers.modalitaRecupero = this.disimpegniService.getModalitaRecupero().subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.modalitaRecupero = res;
      }
      this.loadedModalitaRecupero = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedModalitaRecupero = true;
    });
    this.loadedImportoValidatoTotale = false;
    this.subscribers.importoValidatoTot = this.disimpegniService.getImportoValidatoTotale(this.idProgetto).subscribe(res => {
      if (res) {
        this.importoValidatoTotale = res;
      }
      this.loadedImportoValidatoTotale = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero dell'importo valdiato totale.");
      this.loadedImportoValidatoTotale = true;
    });
  }

  loadDettaglioRevoca() {
    //LOAD DETTAGLIO REVOCA
    this.loadedDettaglioRevoca = false;
    let request: DisimpegniService.GetDettaglioRevocaParams = {
      idRevoca: this.idRevoca,
      idProgetto: this.idProgetto
    };
    this.subscribers.dettaglioRevoca = this.disimpegniService.getDettaglioRevoca(request).subscribe((res: DettaglioRevoca) => {
      if (res) {
        this.dettaglioRevoca = res;
        this.postFormFields();  // compileFormDettaglioErogazione // assegnaModalitaAgevolazione
      }
      this.loadedDettaglioRevoca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedDettaglioRevoca = true;
    });
  }

  loadModalitaAgevolazione() {
    this.loadedModalitaAgevolazione = false;
    this.subscribers.modalitaAgevolazione = this.disimpegniService.getModalitaAgevolazione(this.idProgetto).subscribe((res: ModalitaAgevolazioneDTO[]) => {
      if (res) {
        this.modalitaAgevolazione = res;
        this.loadDisimpegni();
      }
      this.loadedModalitaAgevolazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero del riepilogo erogazioni.");
      this.loadedModalitaAgevolazione = true;
    });
  }

  loadDisimpegni() {
    //LOAD DISIMPEGNI
    this.loadedDisimpegni = false;
    this.subscribers.disimpegni = this.disimpegniService.getDisimpegni(this.modalitaAgevolazione).subscribe((res: RigaModificaRevocaItem[]) => {
      if (res) {
        this.disimpegni = res;
      }
      this.loadedDisimpegni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedDisimpegni = true;
    });
  }

  loadRevoche() {
    //LOAD REVOCHE
    this.loadedRevoche = false;
    this.subscribers.revoche = this.disimpegniService.getRevoche(this.idProgetto).subscribe((res: RigaRevocaItem[]) => {
      if (res) {
        this.revoche = res;
        this.dataSourceModalitaAgevolazioneErogazione = new MatTableDataSource<RigaRevocaItem>(this.revoche);
      }
      this.loadedRevoche = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedRevoche = true;
    });
  }

  setImportoNuovo(row: RigaRevocaItem) {
    row.importoRevocaNew = this.sharedService.getNumberFromFormattedString(row.importoRevocaNewFormatted);
    if (row.importoRevocaNew != null) {
      row.importoRevocaNewFormatted = this.sharedService.formatValue(row.importoRevocaNew.toString());
    } else {
      row.importoRevocaNewFormatted = null;
      row.importoRevocaNew = null;
    }
  }

  postFormFields() {
    if (this.dettaglioRevoca) {
      this.fieldCodiceCausaleDisimpegnoSelected = ((this.dettaglioRevoca.codiceCausaleDisimpegno) ? {
        codice: this.dettaglioRevoca.codiceCausaleDisimpegno,
        descrizione: this.dettaglioRevoca.codiceCausaleDisimpegno // In attesa delle modifiche all'API
      } : undefined); // getCausaleDisimpegno
      this.fieldMotivoRevocaSelected = ((this.dettaglioRevoca.idMotivoRevoca) ? {
        codice: this.dettaglioRevoca.idMotivoRevoca.toString(),
        descrizione: this.dettaglioRevoca.idMotivoRevoca.toString() // In attesa delle modifiche all'API
      } : undefined); // getMotivazioni
      this.fieldModalitaRecuperoSelected = ((this.dettaglioRevoca.idModalitaRecupero) ? {
        codice: this.dettaglioRevoca.idModalitaRecupero.toString(),
        descrizione: this.dettaglioRevoca.idModalitaRecupero.toString() // In attesa delle modifiche all'API
      } : undefined); // getModalitaRecupero
      this.flagOrdineRecupero = this.dettaglioRevoca.ordineRecupero;
      this.dataRevoca = this.sharedService.parseDate(this.dettaglioRevoca.dataRevoca);
      this.fieldEstremiDeterminaRevoca = this.dettaglioRevoca.estremiDeterminaRevoca;
      this.fieldImportoRevoca = ((this.dettaglioRevoca.importoRevoca) ? this.dettaglioRevoca.importoRevoca : 0);
      this.fieldNoteRevoca = this.dettaglioRevoca.noteRevoca;
    }
    this.onSelectCodiceCausaleDisimpegno();
  }

  getFormFieldsInserisci() {
    let rev = new Array<RigaRevocaItem>();
    this.revoche.forEach(r => rev.push(Object.assign({}, r)));
    rev.forEach(r => {
      delete r['importoRevocaNewFormatted'];
    });
    let request: RequestSalvaRevoche = new RequestSalvaRevoche(this.idProgetto, this.fieldNoteRevoca, this.fieldEstremiDeterminaRevoca,
      this.dataRevoca ? this.sharedService.formatDateToString(this.dataRevoca) : null,
      this.fieldMotivoRevocaSelected ? +this.fieldMotivoRevocaSelected.codice : null,
      this.flagOrdineRecupero, this.fieldModalitaRecuperoSelected ? +this.fieldModalitaRecuperoSelected.codice : null,
      this.fieldCodiceCausaleDisimpegnoSelected ? this.fieldCodiceCausaleDisimpegnoSelected.codice : null, null, rev, this.disimpegni);
    return request;
  }

  getFormFieldsModifica() {
    let request: RequestModificaDisimpegno;
    if (this.dettaglioRevoca) {
      this.dettaglioRevoca.dataRevoca = this.dataRevoca ? this.sharedService.formatDateToString(this.dataRevoca) : null;
      this.dettaglioRevoca.codiceCausaleDisimpegno = this.fieldCodiceCausaleDisimpegnoSelected ? this.fieldCodiceCausaleDisimpegnoSelected.codice : null;
      this.dettaglioRevoca.estremiDeterminaRevoca = this.fieldEstremiDeterminaRevoca;
      this.dettaglioRevoca.idModalitaRecupero = this.fieldModalitaRecuperoSelected ? +this.fieldModalitaRecuperoSelected.codice : null;
      this.dettaglioRevoca.idMotivoRevoca = this.fieldMotivoRevocaSelected ? +this.fieldMotivoRevocaSelected.codice : null;
      this.dettaglioRevoca.importoRevoca = this.fieldImportoRevoca;
      this.dettaglioRevoca.noteRevoca = this.fieldNoteRevoca;
      this.dettaglioRevoca.ordineRecupero = this.flagOrdineRecupero;

      request = {
        dettaglioRevoca: this.dettaglioRevoca,
        idProgetto: this.idProgetto
      };
    }
    return request;
  }

  isFormDisabled() {
    let r: boolean = true;
    if (
      this.fieldCodiceCausaleDisimpegnoSelected &&
      this.fieldCodiceCausaleDisimpegnoSelected.codice &&
      (
        this.fieldCodiceCausaleDisimpegnoSelected.codice != 'REV' || (
          this.fieldCodiceCausaleDisimpegnoSelected.codice == 'REV' &&
          this.fieldMotivoRevocaSelected &&
          this.fieldMotivoRevocaSelected.codice &&
          this.flagOrdineRecupero &&
          this.fieldModalitaRecuperoSelected &&
          this.fieldModalitaRecuperoSelected.codice
        )
      ) &&
      this.dataRevoca &&
      this.datePipe.transform(this.dataRevoca, 'dd/MM/yyyy') &&
      (
        this.action == 'inserisci' || (
          this.action == 'modifica' &&
          this.fieldImportoRevoca
        )
      )

    ) {
      r = false;
    }
    return r;
  }

  goToDisimpegni(message?: string) {
    let url: string = "drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_DISIMPEGNI + "/disimpegni/" + this.idProgetto;
    if (message) {
      url += ";message=" + message;
    }
    this.router.navigateByUrl(url);
  }

  isLoading() {
    if (
      !this.loadedCodiceProgetto ||
      (
        (
          this.action == 'inserisci'
        ) && (
          !this.loadedCausaleDisimpegno ||
          !this.loadedMotivazioni ||
          !this.loadedModalitaRecupero ||
          !this.loadedModalitaAgevolazione ||
          !this.loadedDisimpegni ||
          !this.loadedRevoche
        )
      ) ||
      (
        (
          this.action == 'modifica' ||
          this.action == 'dettaglio'
        ) && (
          !this.loadedCausaleDisimpegno ||
          !this.loadedMotivazioni ||
          !this.loadedModalitaRecupero ||
          !this.loadedDettaglioRevoca
        )
      ) ||
      !this.loadedCheckIrr ||
      !this.loadedImportoValidatoTotale ||
      !this.loadedSalva
    ) {
      return true;
    }
    return false;
  }

  setupHeaderLabelsButtons() {
    this.headerLabels = [
      {
        codice: 'Beneficiario',
        descrizione: ((this.user && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) ? this.user.beneficiarioSelezionato.denominazione : '')
      },
      {
        codice: 'Codice progetto',
        descrizione: this.codiceProgetto
      }
    ];
    this.headerButtons = [
      {
        codice: 'dati-progetto-e-attivita-pregresse',
        descrizione: 'Dati progetto e attività pregresse'
      }
    ];
  }

  compareWithCodiceDescrizione(option: CodiceDescrizione, value: CodiceDescrizione) {
    return value && (option.codice === value.codice);
  }

  extractCodiceDescrizione(row) {
    let value: CodiceDescrizione = undefined;
    if (row.codice !== undefined || row.descrizione !== undefined) {
      value = {
        codice: row.codice,
        descrizione: row.descrizione
      };
    }
    return value;
  }

  onSelectCodiceCausaleDisimpegno() {
    if (!this.fieldCodiceCausaleDisimpegnoSelected || this.fieldCodiceCausaleDisimpegnoSelected.codice == 'REV') {
      this.isDisabledRev = false;
    } else {
      if (this.fieldMotivoRevocaSelected && this.fieldMotivoRevocaSelected.codice) {
        this.fieldMotivoRevocaSelected = undefined;
      }
      this.flagOrdineRecupero = undefined;
      if (this.fieldModalitaRecuperoSelected && this.fieldModalitaRecuperoSelected.codice) {
        this.fieldModalitaRecuperoSelected = undefined;
      }
      this.isDisabledRev = true;
    }
    if (this.fieldCodiceCausaleDisimpegnoSelected.codice == 'REV') {
      if (this.action === "modifica") {
        this.isRevoca = true;
        this.loadedCheckIrr = false;
        this.subscribers.checkIrr = this.disimpegni2Service.getRevocaConIrregolarita(this.idRevoca).subscribe(res => {
          if (res && res.esito) {
            this.showMessageWarning(res.msg);
            this.isAssociataIrr = true;
          }
          this.loadedCheckIrr = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di verifica associazione ad un'irregolarità.");
          this.loadedCheckIrr = true;
        });
      }
    }
  }

  onHeaderButtonsClick(btn: CodiceDescrizione) {

    switch (btn.codice) {
      case 'dati-progetto-e-attivita-pregresse':
        this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
          width: '90%',
          maxHeight: '95%',
          data: {
            idProgetto: this.idProgetto,
            apiURL: this.configService.getApiURL()
          }
        });
        break;
      default:
    }
  }

  salva() {
    this.loadedSalva = false;
    if (this.action == 'inserisci') {
      //verifica presenza campo tabella
      let salvaRevocheRequest: RequestSalvaRevoche = this.getFormFieldsInserisci();
      this.subscribers.creaDisimpegno = this.disimpegniService.salvaDisimpegni(salvaRevocheRequest).subscribe(data => {
        let messaggio: string = '';
        if (data.msgs && data.msgs.length > 0) {
          data.msgs.forEach(m => {
            messaggio += m.msgKey + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
        }
        if (data && data.esito) {
          this.goToDisimpegni(messaggio);
        } else {
          this.showMessageError(messaggio);
        }
        this.loadedSalva = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di salvataggio.");
        this.loadedSalva = true;
      });
    } else if (this.action == 'modifica') {
      let modificaErogazioneRequest: RequestModificaDisimpegno = this.getFormFieldsModifica();
      //return;  // Todo: test - remove
      this.subscribers.modificaDisimpegno = this.disimpegniService.modificaDisimpegno(modificaErogazioneRequest).subscribe((res: EsitoSalvaRevocaDTO) => {
        if (res) {
          if (res.esito) {
            this.showMessageSuccess(res.msgs.map(o => o.msgKey).join("<br>"));
            this.loadDettaglioRevoca();
          } else if (res.msgs) {
            this.showMessageError(res.msgs.map(o => o.msgKey).join("<br>"));
          } else {
            this.showMessageError('Nessun messaggio');
          }
        }
        this.loadedSalva = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di salvataggio.");
        this.loadedSalva = true;
      });
    }
  }

  //MESSAGGI SUCCESS E ERROR - start
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

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
  }


  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  //MESSAGGI SUCCESS E ERROR - end
}