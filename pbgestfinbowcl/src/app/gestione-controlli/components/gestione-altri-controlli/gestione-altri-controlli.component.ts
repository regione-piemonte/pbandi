/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { stripGeneratedFileSuffix } from '@angular/compiler/src/aot/util';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ControlloLocoVo } from '../../commons/controllo-loco-vo';
import { RichiestaIntegrazioneVo } from '../../commons/richiesta-integrazione-vo';
import { RichiestaProrogaVo } from '../../commons/richiesta-proroga-vo';
import { ControlliService } from '../../services/controlli.service';
import { DialogEditAltriControlliComponent } from '../dialog-edit-altri-controlli/dialog-edit-altri-controlli.component';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { log } from 'console';

@Component({
  selector: 'app-gestione-altri-controlli',
  templateUrl: './gestione-altri-controlli.component.html',
  styleUrls: ['./gestione-altri-controlli.component.scss']
})
export class GestioneAltriControlliComponent implements OnInit {
  userloaded: boolean = true;
  subscribers: any = {};
  user: UserInfoSec;
  idUtente: any;
  loadedStControllo: boolean = true;
  listaStatoControllo: AttivitaDTO[] = [];
  controllo: ControlloLocoVo = new ControlloLocoVo();
  idControllo: number;
  controlloLoaded: boolean = true;
  dataInizioControllo: Date;
  dataFineControllo: Date;
  importoDaControllare: number;
  importoIrregolarita: number;
  importoAgevIrreg: number;
  importoDaControllareFormatted: string
  importoIrregolaritaFormatted: string;
  importoAgevIrregFormatted: string;
  dataVisitaControllo: Date;
  lisTipoVisita: string[] = [];
  descTipoVisita: string;
  istruttoreVisita: string;
  dataAvvioControlli: Date;
  descAttivitaControllo: string;
  result: boolean;
  idAttivitaControllo: number;
  flagSif: string;
  isLoadedIntegrazione: boolean = true;
  richiestaIntegrVO: RichiestaIntegrazioneVo = new RichiestaIntegrazioneVo;
  isRichiesta: boolean;
  dataAutorizzata: Date;
  dataNotifica: Date;
  isSave: boolean;
  richProroga: RichiestaProrogaVo = new RichiestaProrogaVo();
  isNotificaButton: boolean;
  isSaveIntegrazione: boolean;
  isSalvaDataNotifica: boolean;
  isErrorDate: boolean;
  idProgetto: any;
  descBando;
  descTipoControllo;
  descStatoControllo: any;
  idStatoControllo: number
  denominazione;
  descAttivita;
  idAutoritaControllante: number;
  isDettaglio: number;
  string: string
  saveUpdate: any;
  numProtocollo: string;
  listaAutoritaControllante: AttivitaDTO[] = [];
  tipologiaControllo: string;
  fileLetteraAvvioControllo: File = null;
  fileLetteraAccompagnatoria: File = null;
  fileLetteraEsitoControllo: File = null;
  fileVerbaleControllo: File = null;
  fileCheckListControllo: File = null;
  elencoFileDaGestire: File[] = [];
  isErrorAllegati: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  messageSuccess: any;
  isMessageSuccessVisible: boolean;
  idStatoRichiesta: number;
  isDataAvvioControlli: boolean;
  isProroga: boolean;
  elencoProroghe: RichiestaProrogaVo[];
  isEnableProroga: boolean;
  dataScadenza: Date;
  isDataNotifica: boolean;
  idStatoChecklist: number; // questo parametro mi permette di sapere la modalita in cui la checklist è stata salvata:  1: Bozza, 2 per Definitivo , e null se non è ancora stata salvata

  formDataElenco = new Array<FormData>();
  esitoDTO: EsitoDTO = new EsitoDTO(null, null, null);
  descStatoChecklist: string;
  // idStatoPrecedente del controllo; 
  idStatoPrecControllo: number
  isAllegatiDaSavlare: boolean;
  idRevoca: number;
  constructor(private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private controlliService: ControlliService,
    public sharedService: SharedService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
    public dialog: MatDialog,
    private configService: ConfigService
  ) { }

  ngOnInit(): void {
    this.userloaded = false;
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.userloaded = true;

        this.loadedStControllo = false;
        this.subscribers.staprop = this.controlliService.getListaStatoControllo().subscribe({
          next: data => this.listaStatoControllo = data,
          error: errore => console.log(errore),
          complete: () => this.loadedStControllo = true
        });


        this.route.queryParams.subscribe(params => {
          this.idControllo = params['idControllo'],
            this.idProgetto = params['idProgetto'],
            this.isDettaglio = params['dettaglio'],
            this.idStatoChecklist = params['idStatoChecklist']
          if (this.idStatoChecklist == 1) {
            this.descStatoChecklist = "Bozza";
          } else if (this.idStatoChecklist == 2) {
            this.descStatoChecklist = "Definitivo"
          }

        });

        this.getInfoControllo();
        this.lisTipoVisita.push("Amministrativa");
        this.lisTipoVisita.push("Tecnica");
        this.lisTipoVisita.push("Video-call");
        this.checkRichiestaIntegrazione();
        this.userloaded = true;
      }
    });

    this.subscribers.listaAutoritaControllante = this.controlliService.getAutoritaControlante().subscribe({
      next: data => this.listaAutoritaControllante = data,
      error: errore => console.log(errore),
      complete: () => this.loadedStControllo = true
    })

  }
  disableStato(idStato): boolean {
    if (idStato == 3)
      return true
    else
      return false;
  }
  annullaRichiestaIntegrazione() {
    let dialogRef = this.dialog.open(DialogEditAltriControlliComponent, {
      width: '30%',
      data: {
        element: 6,
        controllo: this.controllo,
        idUtente: this.idUtente,
        idRichiesta: this.richiestaIntegrVO.idRichiestaIntegr
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data) {
        console.log(data);
        // this.result=data;
        // this.result = data; 
      }
    });
  }
  gestisciProroga() {

    this.richProroga.idTarget = this.richiestaIntegrVO.idRichiestaIntegr;
    let dialogRef = this.dialog.open(DialogEditAltriControlliComponent, {
      width: '50%',
      data: {
        element: 5,
        idUtente: this.idUtente,
        idControllo: this.idControllo,
        proroga: this.richProroga,
        idRichiestaIntegr: this.richiestaIntegrVO.idRichiestaIntegr

      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data) {
        this.checkRichiestaIntegrazione();
        this.getProroga();
      }
    })

  }

  salvaDataNotifica() {
    this.isErrorDate = false;
    if (this.dataNotifica) {
      this.richiestaIntegrVO.dataNotifica = this.dataNotifica;
      this.subscribers.salva = this.controlliService.salvaDataNotifica(this.richiestaIntegrVO).subscribe(data => {
        if (data) {
          this.isSalvaDataNotifica = data;
          this.isDataNotifica = true;
          this.checkRichiestaIntegrazione();
        }
      })
    } else {
      this.isErrorDate = true;
    }
  }



  checkRichiestaIntegrazione() {
    this.isLoadedIntegrazione = false;
    this.subscribers.checkRichiestaIntegrazione = this.controlliService.checkRichiestaIntegrAltroControllo(this.idControllo).subscribe(data => {
      if (data) {
        this.isLoadedIntegrazione = true;
        this.richiestaIntegrVO = data;
        this.dataAutorizzata = this.richiestaIntegrVO.dataRichiesta;
        this.dataNotifica = this.richiestaIntegrVO.dataNotifica;
        this.dataScadenza = this.richiestaIntegrVO.dataScadenza;
        this.idStatoRichiesta = this.richiestaIntegrVO.idStatoRichiesta;
        if (this.richiestaIntegrVO.idRichiestaIntegr) {
          this.isRichiesta = true;
          this.isProroga = true;
          this.getProroga();
        }
        if (this.dataNotifica) {
          this.isDataNotifica = true;
        }


      } else {
        this.isRichiesta = false
      }
    })
  }
  getProroga() {
    this.subscribers.richProroga = this.controlliService.getRichProroga(this.richiestaIntegrVO.idRichiestaIntegr).subscribe(
      {
        next: (risposta) => {
          this.richProroga = risposta;
          if (this.richProroga.idRichiestaProroga)
            this.isNotificaButton = true;
          this.getElencoProroghe();
        },
        error: (error) => console.log("errore in fase di completamento " + error),
        complete: () => console.log("completato")
      }
    )
  }

  getElencoProroghe() {
    this.subscribers.elencoProroghe = this.controlliService.getElencoRichProroga(this.richiestaIntegrVO.idRichiestaIntegr).subscribe(data => {
      if (data) {
        this.elencoProroghe = data;
        if (this.elencoProroghe.length > 0 || this.richProroga.idRichiestaProroga) {
          this.isEnableProroga = true;
        }
      }
    })
  }

  toggleBadgeVisibility() {
    if (this.richProroga.idRichiestaProroga) {
      return true;
    } else {
      return false;
    }
  }
  gestisciAllegati() {
    // gestione allegati
    let dialogRef = this.dialog.open(DialogEditAltriControlliComponent, {
      width: '40%',
      data: {
        element: 2,
        idControllo: this.idControllo,
        idUtente: this.idUtente,
        controllo: this.controllo,
        idStatoControllo: this.idStatoControllo,
        files: this.elencoFileDaGestire,
        idRichiestaIntegr: this.richiestaIntegrVO.idRichiestaIntegr,
        idStatoRichiesta: this.richiestaIntegrVO.idStatoRichiesta
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data) {
        this.elencoFileDaGestire = data;
        this.fileLetteraAvvioControllo = this.elencoFileDaGestire[0];
        this.fileLetteraAccompagnatoria = this.elencoFileDaGestire[1];
        this.fileLetteraEsitoControllo = this.elencoFileDaGestire[2];
        this.fileVerbaleControllo = this.elencoFileDaGestire[3];
        this.fileCheckListControllo = this.elencoFileDaGestire[4];
        console.log(data);
      }
    });

  }

  controlloAllegati() {
    this.resetMessageError()
    this.isAllegatiDaSavlare = false;
    console.log("id Stato controllo " + this.idStatoControllo);
    // dentro l'elenco dei file Allegati controllo ci sono 5 files in questo ordine da 0 a 4
    // this.fileLetteraAvvioControllo 
    // this.fileLetteraAccompagnatoria 
    // this.fileLetteraEsitoControllo 
    // this.fileVerbaleControllo 
    // this.fileCheckListControllo

    this.fileLetteraAvvioControllo = this.elencoFileDaGestire[0];
    this.fileLetteraAccompagnatoria = this.elencoFileDaGestire[1];
    this.fileLetteraEsitoControllo = this.elencoFileDaGestire[2];
    this.fileVerbaleControllo = this.elencoFileDaGestire[3];
    this.fileCheckListControllo = this.elencoFileDaGestire[4];

    if (this.idStatoControllo != this.idStatoPrecControllo) {

      switch (this.idStatoControllo) {
        case 4:
        case 5:
          if (this.fileVerbaleControllo != null && this.fileCheckListControllo != null && this.idStatoChecklist == 2) {
            // salvo Controllo e gli allegati corrispondenti
            this.isAllegatiDaSavlare = true;
            this.salvaControllo();

          } else {
            this.isErrorAllegati = true;
            this.showMessageError("controllare gli allegati:Verbale di controllo in loco , Checklist di controllo interno e la checklist deve essere salvata in definitivo");
          }
          break;
        case 2:
          if (this.fileVerbaleControllo != null && this.fileCheckListControllo != null && this.fileLetteraEsitoControllo != null && this.idStatoChecklist == 2) {
            this.isAllegatiDaSavlare = true;
            this.salvaControllo();
          } else {
            this.isErrorAllegati = true;
            this.showMessageError("controllare gli allegati: Verbale di controllo in loco, Checklist di controllo interno, Lettera di esito controllo in loco e la checklist deve essere salvata in definitivo");
          }
          break;
        case 3:
          this.salvaControllo();
          break;
        case 1:
          this.salvaControllo();
          break;
        default:
          // this.salvaControllo();
          break;
      }

    } else {
      this.salvaControllo();
    }

    console.log(this.isErrorAllegati);

  }


  // entita che varia da 1 a 4 
  // 1 per la tabella pbandi_t_controllo_finp
  // 2 per la tabella PBANDI_T_RICHIESTA_INTEGRAZ
  // 3 per la tabella pbandi_t_controllo_altri
  // 4 per la tabella PBANDI_T_GESTIONE_REVOCA

  addAllegato(idTipoDocumentoIndex: number, idUtente: number, nomeFile: string, file: File, idTarget: number, idProgetto: number, entita: number) {

    let formData = new FormData();
    formData.append("file", file, nomeFile);
    formData.append("idUtente", idUtente.toString());
    formData.append("nomeFile", nomeFile);
    formData.append("idTarget", idTarget.toString());
    formData.append("idTipoDocumentoIndex", idTipoDocumentoIndex.toString());
    formData.append("idProgetto", idProgetto.toString());
    formData.append("entita", entita.toString());

    this.formDataElenco.push(formData);
  }

  callToSalvaAllegati() {

    switch (this.idStatoControllo) {
      case 4:
      case 5:
        let idRevoca = this.esitoDTO.id;
        this.subscribers.salvaAllegati = this.controlliService.salvaAllegati2(this.elencoFileDaGestire, this.idStatoControllo, this.idProgetto, false, this.idControllo, idRevoca).subscribe(data => {
          if (data) {
            this.isSave = data;
            setTimeout(() => {
              this.goBack();
            }, 3000);
          }
        })

        break;

      case 2:
        this.subscribers.salvaAllegati = this.controlliService.salvaAllegati2(this.elencoFileDaGestire, this.idStatoControllo, this.idProgetto, false, this.idControllo).subscribe(data => {
          if (data) {
            this.isSave = data;
            setTimeout(() => {
              this.goBack();
            }, 3000);
          }
        })
        break;
    }
  }

  setDataNull(idData: number) {
    if (idData == 1) {
      this.dataInizioControllo = null;
    }
    if (idData == 2) {
      this.dataFineControllo = null
    }
    if (idData == 3) {
      this.dataVisitaControllo = null;
    }
    if (idData == 4) {
      this.dataNotifica = null;
    }
    if (idData == 5) {
      this.dataAvvioControlli = null;
    }
  }

  salvaControllo() {

    this.setCampi();
    this.subscribers.salvaControllo = this.controlliService.updateAltroControllo(this.controllo, this.idUtente).subscribe(data => {
      if (data) {
        console.log(data);
        if (data.esito == true) {
          this.esitoDTO = data;
          this.idRevoca = this.esitoDTO.id;
          this.showMessageSuccess(data.messaggio);
          if (this.isAllegatiDaSavlare == false) {
            setTimeout(() => {
              this.goBack();
            }, 5000);
          } else {
            // Salvataggio degli allegati
            this.callToSalvaAllegati();
          }
        } else {
          this.showMessageError(data.messaggio);
        };


      }
    });

  }

  avviaIterEsitoPositivoControllo() {
    this.subscribers.avviaIterEsitoPositivoControllo = this.controlliService.avviaIterEsitoPositivoControllo(this.idProgetto, this.idControllo, true).subscribe(data => {
      if (data) {
        console.log(data);
        this.result = data;
        if (this.result == true) {
          this.showMessageSuccess("Iter autorizzativo avviato con successo!");
          setTimeout(() => {
            this.resetMessageSuccess();
          }, 3000);
        } else {
          this.showMessageError("Errore durante l'avvio dell'iter!");
          setTimeout(() => {
            this.resetMessageError();
          }, 3000);
        }
      }
    });
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }
  showMessageSuccess(msg: string) {
    this.isMessageSuccessVisible = true;
    this.messageSuccess = msg;
  }
  richiestaIntegrazione() {
    this.setCampi();
    let dialogRef = this.dialog.open(DialogEditAltriControlliComponent, {
      width: '50%',
      data: {
        element: 4,
        controllo: this.controllo,
        idUtente: this.idUtente,
        idControllo: this.idControllo,
        file: this.fileLetteraAccompagnatoria,
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data) {
        console.log(data);
        this.isSaveIntegrazione = data;
        setTimeout(() => {
          this.goBack();
        }, 3000);
      }
    })
  }

  setCampi() {

    console.log(this.descTipoVisita);

    this.controllo.idStatoControllo = this.idStatoControllo;
    this.controllo.dataFineControlli = this.dataFineControllo;
    this.controllo.dataInizioControlli = this.dataInizioControllo;
    this.controllo.dataVisitaControllo = this.dataVisitaControllo;
    this.controllo.dataAvvioControlli = this.dataAvvioControlli;
    this.controllo.descTipoVisita = this.descTipoVisita;
    this.controllo.numProtocollo = this.numProtocollo;
    this.controllo.idStatoChecklist = this.idStatoChecklist;
    if (this.tipologiaControllo != null) {
      this.controllo.tipoControllo = this.tipologiaControllo;
    }

    if (this.importoAgevIrreg != null) {
      this.controllo.importoAgevIrreg = this.importoAgevIrreg;
    }
    if (this.importoDaControllare != null) {
      this.controllo.importoDaControllare = this.importoDaControllare;
    }
    if (this.importoIrregolarita != null) {
      this.controllo.importoIrregolarita = this.importoIrregolarita;
    }
    (this.istruttoreVisita != null) ? (this.controllo.istruttoreVisita = this.istruttoreVisita) : null;
  }

  setImporto(campoNumber: number) {
    switch (campoNumber) {
      case 1:
        this.importoDaControllare = this.sharedService.getNumberFromFormattedString(this.importoDaControllareFormatted);
        if (this.importoDaControllare !== null) {
          this.importoDaControllareFormatted = this.sharedService.formatValue(this.importoDaControllare.toString());
        }
        break;
      case 2:
        this.importoIrregolarita = this.sharedService.getNumberFromFormattedString(this.importoIrregolaritaFormatted);
        if (this.importoIrregolarita !== null) {
          this.importoIrregolaritaFormatted = this.sharedService.formatValue(this.importoIrregolarita.toString());
        }
        break
      case 3:
        this.importoAgevIrreg = this.sharedService.getNumberFromFormattedString(this.importoAgevIrregFormatted);
        if (this.importoAgevIrreg !== null) {
          this.importoAgevIrregFormatted = this.sharedService.formatValue(this.importoAgevIrreg.toString());
        }
        break;
      default:
        break;
    }
  }
  avviaControlloInLoco() {

    this.setCampi();
    let dialogRef = this.dialog.open(DialogEditAltriControlliComponent, {
      width: '40%',
      data: {
        element: 3, // 3 corrisponde all'avvio iter controllo in loco 
        idControllo: this.idControllo,
        idUtente: this.idUtente,
        controllo: this.controllo,
        file: this.fileLetteraAvvioControllo
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data) {
        console.log(data);
        this.result = data;
        setTimeout(() => {
          this.goBack();
        }, 3000);
      }
    });


  }
  getInfoControllo() {
    this.controlloLoaded = false;
    this.controlliService.getInfoAltroControllo(this.idControllo, this.idProgetto).subscribe(data => {
      if (data) {
        this.controllo = data;
        this.idStatoPrecControllo = data.idStatoControllo;
        this.controlloLoaded = true;
        this.dataAvvioControlli = this.controllo.dataAvvioControlli;
        this.idAutoritaControllante = this.controllo.idAutoritaControllante;
        this.idStatoControllo = this.controllo.idStatoControllo;
        this.descBando = this.controllo.descBando;
        this.denominazione = this.controllo.denominazione;
        this.descTipoControllo = this.controllo.descTipoControllo;
        this.descAttivitaControllo = this.controllo.descAttivita;
        this.istruttoreVisita = this.controllo.istruttoreVisita;
        this.dataFineControllo = this.controllo.dataFineControlli;
        this.dataInizioControllo = this.controllo.dataInizioControlli;
        this.dataVisitaControllo = this.controllo.dataVisitaControllo;
        this.importoAgevIrreg = this.controllo.importoAgevIrreg;
        this.idAttivitaControllo = this.controllo.idAttivitaContrLoco;
        this.flagSif = this.controllo.flagSif;
        this.numProtocollo = this.controllo.numProtocollo;
        if (this.dataAvvioControlli != null) {
          this.isDataAvvioControlli = true;
        }
        if (this.importoAgevIrreg) {
          this.importoAgevIrregFormatted = this.sharedService.formatValue(this.importoAgevIrreg.toString());
        }
        this.importoDaControllare = this.controllo.importoDaControllare
        if (this.importoDaControllare) {
          this.importoDaControllareFormatted = this.sharedService.formatValue(this.importoDaControllare.toString());
        }
        this.importoIrregolarita = this.controllo.importoIrregolarita
        if (this.importoIrregolarita) {
          this.importoIrregolaritaFormatted = this.sharedService.formatValue(this.importoIrregolarita.toString());
        }
        this.descTipoVisita = this.controllo.descTipoVisita;
        (this.descTipoControllo == "In loco") ? this.tipologiaControllo = 'L' : this.tipologiaControllo = 'D';
      }

    })
  }
  goBack() {
    this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI + "/ricercaAltriControlli"], { queryParams: {} });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  isRichiestaIntegr() {
    if ((this.idStatoControllo == 3 || this.idStatoControllo == 1)) {
      if ((this.isRichiesta == false || this.idStatoRichiesta == 5 || this.idStatoRichiesta == 3 || this.idStatoRichiesta == 2)) {
        return true;
      }
    }
    return false;
  }

  gestioneCheckList() {
    const descBreve = Constants.DESCR_BREVE_TASK_CHECKLIST;
    const descTask = 'Gestione checklist del progetto';

    this.navigateToChecklist(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI, "checklistControlli", this.controllo.idProgetto,
      this.controllo.progrBandoLinea, descBreve, descTask);

  }
  navigateToChecklist(configComponentURL: string, idAttivita: number, destComponentPath: string, idProgetto: number, idBando: number, descBreveTask: string, descTask: string, idBusiness?: number) {
    let url: string = `${configComponentURL}/#/drawer/${idAttivita}/${destComponentPath}/${idProgetto}/${idBando}/${this.controllo.idControllo}/${this.idStatoChecklist}`
      // + (idBusiness ? `/${idBusiness}` : '')
      + `?idSg=${this.user.idSoggetto}&idSgBen=${this.controllo.idSoggettoBenef}&idU=${this.user.idUtente}&role=${this.user.codiceRuolo}`;
    + `&idPrj=${idProgetto}&taskIdentity=${descBreveTask}&taskName=${descTask}&wkfAction=StartTaskNeoFlux`;
    window.location.href = url;
  }

  isLoading() {
    if (!this.userloaded || !this.loadedStControllo || !this.controlloLoaded) {
      return true;
    }
    return false;
  }

}
