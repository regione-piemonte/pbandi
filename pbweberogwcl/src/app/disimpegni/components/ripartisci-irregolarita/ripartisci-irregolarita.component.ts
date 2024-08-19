/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { DateAdapter } from '@angular/material/core';
import * as _ from "lodash";
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { InizializzazioneService } from "src/app/shared/services/inizializzazione.service";
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";

import { DisimpegniService } from "../../services/disimpegni.service";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
import { ModalitaAgevolazioneDTO } from "../../commons/dto/modalita-agevolazione-dto";
import { RigaModificaRevocaItem } from "../../commons/models/riga-modifica-revoca-item";
import { Decodifica } from "../../commons/models/decodifica";
import { RequestFindIrregolarita } from '../../commons/models/request-find-irregolarita';
import { Revoca } from "../../commons/models/revoca";
import { RequestAssociaIrregolarita } from '../../commons/models/request-associa-irregolarita';
import { EsitoSalvaRevocaDTO } from '../../commons/dto/esito-salva-revoca-dto';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { DettaglioRevocaIrregolarita } from '../../commons/models/dettaglio-revoca-irregolarita';
import { DsDettaglioRevocaIrregolarita } from '../../commons/models/ds-dettaglio-revoca-irregolarita';
import { SaveDettaglioDTO } from '../../commons/dto/save-dettaglio-dto';
import { SaveDsDTO } from '../../commons/dto/save-ds-dto';
import { SaveIrregolaritaDTO } from '../../commons/dto/save-irregolarita-dto';
import { MatRadioChange } from '@angular/material/radio';

@Component({
  selector: 'app-ripartisci-irregolarita',
  templateUrl: './ripartisci-irregolarita.component.html',
  styleUrls: ['./ripartisci-irregolarita.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RipartisciIrregolaritaComponent implements OnInit {

  action: string;
  idProgetto: number;
  idRevoca: number;
  isMaster: boolean;
  codiceProgetto: string;
  user: UserInfoSec;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedModalitaAgevolazione: boolean;
  loadedFindDsIrregolarita: boolean;
  loadedFindClassRevocaIrreg: boolean;
  loadedFindIrregolarita: boolean;
  loadedSalvaIrregolarita: boolean = true;
  loadedDisimpegni: boolean;
  loadedSalva: boolean = true;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  //HEADER LABELS BUTTONS
  headerLabels: CodiceDescrizione[];
  headerButtons: CodiceDescrizione[];

  dsIrregolarita: number[];
  classRevocaIrreg: Decodifica[];
  irregolarita: Revoca;
  totaleImportoIrregolarita: number = 0;
  totaleIimportoAgevolazioneIrreg: number = 0;
  modalitaAgevolazione: ModalitaAgevolazioneDTO[];
  disimpegni: RigaModificaRevocaItem[];

  constructor(
    private configService: ConfigService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private inizializzazioneService: InizializzazioneService,
    private disimpegniService: DisimpegniService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private sharedService: SharedService,
    private _adapter: DateAdapter<any>
  ) {
    this._adapter.setLocale('it');
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.idIride && this.user.codiceRuolo && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) {  // this.user.idIride
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_ISTRUTTORE
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OPE_SUP_IST) {
            this.isMaster = true;
          }
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            if (params['id']) {
              this.idProgetto = +params['id'];
              this.idRevoca = +params['id2'];
              this.loadData();
            } else {
              this.showMessageError('Identificativo progetto non inserito');
            }
          });
        } else {
          console.warn('this.user.idIride', this.user.idIride);
          console.warn('this.user.beneficiarioSelezionato', this.user.beneficiarioSelezionato);
          console.warn('this.user.beneficiarioSelezionato.denominazione', ((this.user.beneficiarioSelezionato) ? this.user.beneficiarioSelezionato.denominazione : ''));
        }
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  loadData() {
    this.loadCodiceProgetto();
    this.loadModalitaAgevolazione();
    this.loadFindDsIrregolarita();
    this.loadFindClassRevocaIrreg();
  }

  loadCodiceProgetto() {
    this.loadedCodiceProgetto = false;
    this.subscribers.codiceProgetto = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
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
  }

  loadFindDsIrregolarita() {
    this.loadedFindDsIrregolarita = false;
    this.subscribers.findDsIrregolarita = this.disimpegniService.findDsIrregolarita(this.idProgetto).subscribe((res: number[]) => {
      if (res) {
        console.log('findDsIrregolarita', res);
        this.dsIrregolarita = res;
      }
      this.loadedFindDsIrregolarita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedFindDsIrregolarita = true;
    });
  }



  loadFindClassRevocaIrreg() {
    //LOAD FIND CLASS REVOCA IRREG
    this.loadedFindClassRevocaIrreg = false;
    this.subscribers.findClassRevocaIrreg = this.disimpegniService.findClassRevocaIrreg(this.idProgetto).subscribe((res: Decodifica[]) => {
      if (res) {
        console.log('findClassRevocaIrreg', res);
        this.classRevocaIrreg = res;
      }
      this.loadedFindClassRevocaIrreg = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedFindClassRevocaIrreg = true;
    });
  }

  loadFindIrregolarita() {
    //LOAD FIND IRREGOLARITA
    this.loadedFindIrregolarita = false;
    let requestFindIrregolarita: RequestFindIrregolarita = {
      idRevoca: this.idRevoca,
      revoche: this.disimpegni
    };
    this.subscribers.findIrregolarita = this.disimpegniService.findIrregolarita(requestFindIrregolarita).subscribe((res: Revoca) => {
      if (res) {
        console.log('findIrregolarita', res);
        this.irregolarita = res;
        if (this.irregolarita.irregolarita && this.irregolarita.irregolarita.length > 0) {
          this.irregolarita.irregolarita.forEach(irr => {
            if (irr.dettagliRevocaIrregolarita && irr.dettagliRevocaIrregolarita.length > 0) {
              irr.checked = true;
              irr.dettagliRevocaIrregolarita.forEach(det => {
                if (det.importoAudit != null) {
                  det.importoAuditFormatted = this.sharedService.formatValue(det.importoAudit.toString());
                }
                if (det.dsDettagliRevocaIrregolarita && det.dsDettagliRevocaIrregolarita.length > 0) {
                  det.dsDettagliRevocaIrregolarita.forEach(detds => {
                    if (detds.importoIrregolareDs != null) {
                      detds.importoIrregolareDsFormatted = this.sharedService.formatValue(detds.importoIrregolareDs.toString());
                    }
                  });
                } else {
                  det.dsDettagliRevocaIrregolarita = new Array<DsDettaglioRevocaIrregolarita>();
                  det.dsDettagliRevocaIrregolarita.push(new DsDettaglioRevocaIrregolarita(null, null, null));
                }
              });
            } else {
              irr.dettagliRevocaIrregolarita = new Array<DettaglioRevocaIrregolarita>();
              let dsDettagliRevocaIrregolarita = new Array<DsDettaglioRevocaIrregolarita>();
              dsDettagliRevocaIrregolarita.push(new DsDettaglioRevocaIrregolarita(null, null, null));
              irr.dettagliRevocaIrregolarita.push(new DettaglioRevocaIrregolarita(dsDettagliRevocaIrregolarita,
                this.classRevocaIrreg && this.classRevocaIrreg[0] ? this.classRevocaIrreg[0].id : null, null, irr.idIrregolarita, irr.idRevoca,
                irr.importoAgevolazioneIrreg, null, "D"));
            }
            this.totaleImportoIrregolarita += irr.importoIrregolarita;
            this.totaleIimportoAgevolazioneIrreg += irr.importoAgevolazioneIrreg;
          });
        }
      }
      this.loadedFindIrregolarita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedFindIrregolarita = true;
    });
  }

  getDescClassFromId(id: number) {
    if (!this.classRevocaIrreg || this.classRevocaIrreg.length === 0 || !this.classRevocaIrreg.find(c => c.id === id)) {
      return "";
    }
    return this.classRevocaIrreg.find(c => c.id === id).descrizione;
  }

  changeTipologia(event: MatRadioChange, det: DettaglioRevocaIrregolarita) {
    if (event.value === "D") {
      if (!det.dsDettagliRevocaIrregolarita || det.dsDettagliRevocaIrregolarita.length === 0) {
        det.dsDettagliRevocaIrregolarita = new Array<DsDettaglioRevocaIrregolarita>();
        det.dsDettagliRevocaIrregolarita.push(new DsDettaglioRevocaIrregolarita(null, null, null));
      }
    }
  }

  addRow(dettaglioRevocaIrregolarita: DettaglioRevocaIrregolarita, indice: number) {
    dettaglioRevocaIrregolarita.dsDettagliRevocaIrregolarita.splice(indice + 1, 0, new DsDettaglioRevocaIrregolarita(
      dettaglioRevocaIrregolarita.idDettRevocaIrreg, null, null));
  }

  deleteRow(dettaglioRevocaIrregolarita: DettaglioRevocaIrregolarita, indice: number) {
    dettaglioRevocaIrregolarita.dsDettagliRevocaIrregolarita.splice(indice, 1);
  }

  setImportoAudit(det: DettaglioRevocaIrregolarita) {
    det.importoAudit = this.sharedService.getNumberFromFormattedString(det.importoAuditFormatted);
    if (det.importoAudit != null) {
      det.importoAuditFormatted = this.sharedService.formatValue(det.importoAudit.toString());
    } else {
      det.importoAuditFormatted = null;
      det.importoAudit = null;
    }
  }

  setImportoIrrDs(detds: DsDettaglioRevocaIrregolarita) {
    detds.importoIrregolareDs = this.sharedService.getNumberFromFormattedString(detds.importoIrregolareDsFormatted);
    if (detds.importoIrregolareDs != null) {
      detds.importoIrregolareDsFormatted = this.sharedService.formatValue(detds.importoIrregolareDs.toString());
    } else {
      detds.importoIrregolareDsFormatted = null;
      detds.importoIrregolareDs = null;
    }
  }

  calcolaSommaImportiAssociati() {
    let somma: number = 0;
    let irreg = this.irregolarita.irregolarita.filter(irr => irr.checked);
    if (irreg && irreg.length > 0) {
      for (let irr of irreg) {
        somma += irr.importoAgevolazioneIrreg;
      }
    }
    return +somma.toFixed(2);
  }

  calcolaSommaImportiIrregolari(idIrregolarita: number, idClassificazione: number) {
    let somma: number = 0;
    let irr = this.irregolarita.irregolarita.find(irr => irr.idIrregolarita === idIrregolarita);
    if (irr.dettagliRevocaIrregolarita && irr.dettagliRevocaIrregolarita.length > 0) {
      let dett = irr.dettagliRevocaIrregolarita.filter(det => det.idClassRevocaIrreg === idClassificazione);
      if (dett && dett[0] && dett[0].dsDettagliRevocaIrregolarita.length > 0) {
        for (let detds of dett[0].dsDettagliRevocaIrregolarita) {
          if (detds.importoIrregolareDs !== null) {
            somma += detds.importoIrregolareDs;
          }
        }
      }

    }
    return +somma.toFixed(2);
  }

  validateIrr() {
    let hasError: boolean;
    let messaggi = new Array<string>();
    // Verifica che la somma degli importi associati non superi l'importo della revoca.
    let importiAssociati: number = this.calcolaSommaImportiAssociati();
    if (this.irregolarita.importoRevocato && importiAssociati > this.irregolarita.importoRevocato) {
      messaggi.push("La somma degli importi delle irregolarità selezionate non deve superare l'importo revocato");
      hasError = true;
    }
    if (!hasError) {
      for (let irr of this.irregolarita.irregolarita) {
        if (irr.checked) {
          let sommaImporti: number = 0;
          let i: number = 0;
          if (irr.dettagliRevocaIrregolarita && irr.dettagliRevocaIrregolarita.length > 0) {
            for (let det of irr.dettagliRevocaIrregolarita) {
              let prefisso = irr.descPeriodoVisualizzata + " " + (i + 1) + ") " + irr.motivoRevocaIrregolarita + ": ";
              // Test su importo e importoAudit.
              if (det.importoAudit == null && det.importo == null) {
                // tutto ok.
              } else if (det.importoAudit != null && det.importo == null) {
                messaggi.push(prefisso + "è stato valorizzato il campo 'Importo Audit' ma non il campo 'Importo'.");
                hasError = true;
              } else if (det.importoAudit > det.importo) {
                messaggi.push(prefisso + "l'importo Audit è superiore all'importo totale della classificazione.");
                hasError = true;
              }
              // Test su Ds e importoIrregolare.
              if (det.dsDettagliRevocaIrregolarita && det.dsDettagliRevocaIrregolarita.length > 0) {
                for (let detds of det.dsDettagliRevocaIrregolarita) {
                  if (detds.idDichiarazioneSpesa === null && detds.importoIrregolareDs === null && det.tipologia === 'D') {
                    messaggi.push(prefisso + "non sono stati valorizzati il campo 'Dichiarazione di Spesa' e il campo 'Importo Irregolare'.");
                    hasError = true;
                  } else if (detds.idDichiarazioneSpesa !== null && detds.importoIrregolareDs === null) {
                    messaggi.push(prefisso + "è stato valorizzato il campo 'Dichiarazione di Spesa' ma non il campo 'Importo Irregolare'.");
                    hasError = true;

                  } else if (detds.idDichiarazioneSpesa === null && detds.importoIrregolareDs !== null) {
                    messaggi.push(prefisso + "è stato valorizzato il campo 'Importo Irregolare' ma non il campo 'Dichiarazione di Spesa'.");
                    hasError = true;

                  }
                }
              }
              // Test su somma importiIrregolari
              if (det.tipologia === "D") {
                let sommaImportiIrregolari: number = this.calcolaSommaImportiIrregolari(det.idIrregolarita, det.idClassRevocaIrreg);
                if ((det.importo === null && sommaImportiIrregolari > 0)) {
                  messaggi.push(prefisso + "la somma degli importi irregolari DS non coincide con l'importo della classificazione.");
                  hasError = true;
                }
                if ((det.importo > 0 && sommaImportiIrregolari > 0 && sommaImportiIrregolari > det.importo)) {
                  messaggi.push(prefisso + "la somma degli importi irregolari DS supera l'importo della classificazione.");
                  hasError = true;

                }
                if ((det.importo > 0 && sommaImportiIrregolari > 0 && sommaImportiIrregolari < det.importo)) {
                  messaggi.push(prefisso + "la somma degli importi irregolari DS è inferiore all'importo della classificazione.");
                  hasError = true;

                }
              }
              if (det.importo != null) {
                sommaImporti += det.importo;
              }
            }
          }

          if (irr.importoAgevolazioneIrreg == null)
            irr.importoAgevolazioneIrreg = 0;
          if (sommaImporti !== irr.importoAgevolazioneIrreg) {
            messaggi.push(irr.descPeriodoVisualizzata + " " + (i + 1) + ") : la somma degli importi delle classificazioni non coincide con l'importo associato all'irregolarità.");
            hasError = true;
          }
        }
      }
    }
    if (hasError && messaggi.length > 0) {
      let messaggio: string = '';
      messaggi.forEach(m => {
        messaggio += m + "<br/>";
      });
      messaggio = messaggio.substr(0, messaggio.length - 5);
      this.showMessageError(messaggio);

    }
    return hasError;
  }

  getRequestAssociaIrregolarita() {
    let elencoSaveDettaglioDTO = new Array<SaveDettaglioDTO>();
    let elencoSaveDsDTO = new Array<SaveDsDTO>();
    let elencoSaveIrregolaritaDTO = new Array<SaveIrregolaritaDTO>();
    for (let irr of this.irregolarita.irregolarita) {
      if (irr.checked) {
        elencoSaveIrregolaritaDTO.push(new SaveIrregolaritaDTO(irr.idIrregolarita, this.idRevoca, irr.importoAgevolazioneIrreg));
        for (let det of irr.dettagliRevocaIrregolarita) {
          elencoSaveDettaglioDTO.push(new SaveDettaglioDTO(det.idClassRevocaIrreg, det.idIrregolarita, this.idRevoca, det.importo,
            det.importoAudit, det.tipologia));
          if (det.tipologia === "D" && det.dsDettagliRevocaIrregolarita && det.dsDettagliRevocaIrregolarita.length > 0) {
            for (let detDs of det.dsDettagliRevocaIrregolarita) {
              elencoSaveDsDTO.push(new SaveDsDTO(det.idClassRevocaIrreg, det.idIrregolarita, this.idRevoca, detDs.idDichiarazioneSpesa,
                detDs.importoIrregolareDs, det.tipologia));
            }
          }
        }
      }
    }
    let request = new RequestAssociaIrregolarita(elencoSaveDettaglioDTO, elencoSaveDsDTO, elencoSaveIrregolaritaDTO, this.idRevoca);
    return request;
  }

  salva() {
    if (!this.validateIrr()) {
      this.loadedSalvaIrregolarita = false;
      let request: RequestAssociaIrregolarita = this.getRequestAssociaIrregolarita();
      this.subscribers.salvaIrregolarita = this.disimpegniService.salvaIrregolarita(request).subscribe((data: EsitoSalvaRevocaDTO) => {
        if (data) {
          if (data.esito) {
            this.goToDisimpegni("Salvataggio avvenuto con successo.");
          } else {
            let messaggio: string = '';
            if (data.msgs && data.msgs.length > 0) {
              data.msgs.forEach(m => {
                messaggio += m.msgKey + "<br/>";
              });
              messaggio = messaggio.substr(0, messaggio.length - 5);
              this.showMessageError(messaggio);
            }
          }
        }
        this.loadedSalvaIrregolarita = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di salvataggio.");
        this.loadedSalvaIrregolarita = true;
      });
    }
  }

  loadModalitaAgevolazione() {
    //LOAD MODALITA AGEVOLAZIONE
    this.loadedModalitaAgevolazione = false;
    this.subscribers.modalitaAgevolazione = this.disimpegniService.getModalitaAgevolazione(this.idProgetto).subscribe((res: ModalitaAgevolazioneDTO[]) => {
      if (res) {
        console.log('getModalitaAgevolazione', res);
        this.modalitaAgevolazione = res;
        this.loadDisimpegni();
      }
      this.loadedModalitaAgevolazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadDisimpegni();
      this.loadedModalitaAgevolazione = true;
    });
  }

  loadDisimpegni() {
    //LOAD DISIMPEGNI
    this.loadedDisimpegni = false;
    this.subscribers.disimpegni = this.disimpegniService.getDisimpegni(this.modalitaAgevolazione).subscribe((res: RigaModificaRevocaItem[]) => {
      if (res) {
        console.log('getDisimpegni', res);
        this.disimpegni = res;
        this.loadFindIrregolarita();
      }
      this.loadedDisimpegni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadFindIrregolarita();
      this.loadedDisimpegni = true;
    });
  }

  goToDisimpegni(message?: string) {
    let url: string = "drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_DISIMPEGNI + "/disimpegni/" + this.idProgetto;
    if (message) {
      url += ";message=" + message;
    }
    this.router.navigateByUrl(url);
  }

  isLoading() {
    let r = false;
    if (
      !this.loadedCodiceProgetto ||
      !this.loadedFindDsIrregolarita ||
      !this.loadedFindClassRevocaIrreg ||
      !this.loadedFindIrregolarita ||
      !this.loadedSalva ||
      !this.loadedSalvaIrregolarita
    ) {
      r = true;
    }
    return r;
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

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  //MESSAGGI SUCCESS E ERROR - end
}