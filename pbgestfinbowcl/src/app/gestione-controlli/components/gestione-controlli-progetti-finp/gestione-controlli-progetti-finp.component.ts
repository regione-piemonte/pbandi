/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ControlliService } from 'src/app/gestione-controlli/services/controlli.service';
import { ControlloLocoVo } from 'src/app/gestione-controlli/commons/controllo-loco-vo';
import { SharedService } from 'src/app/shared/services/shared.service';
import { UserService } from 'src/app/core/services/user.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { MatDialog } from '@angular/material/dialog';
import { DialogGestioneControlliComponent } from '../dialog-gestione-controlli/dialog-gestione-controlli.component';
import { RichiestaIntegrazioneVo } from '../../commons/richiesta-integrazione-vo';
import { RichiestaProrogaVo } from '../../commons/richiesta-proroga-vo';
import { Constants } from 'src/app/core/commons/util/constants';
import { DocumentoIndexVO } from '../../commons/documento-index-vo';
import { ConfigService } from 'src/app/core/services/config.service';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';

@Component({
  selector: 'app-gestione-controlli-progetti-finp',
  templateUrl: './gestione-controlli-progetti-finp.component.html',
  styleUrls: ['./gestione-controlli-progetti-finp.component.scss']
})
export class GestioneControlliProgettiFinpComponent implements OnInit {

  userloaded: boolean = true;
  subscribers: any = {};
  user: UserInfoSec;
  idUtente: any;
  descBando;
  descTipoControllo;
  descStatoControllo: any;
  idStatoControllo: number
  denominazione;
  descAttivita;
  idControllo;
  myForm: FormGroup;
  isSubmitted;
  suggInIstruttoria;
  suggerimentoTipoViste;
  errore = null;
  message = null;
  idProgetto: any;
  loadedStControllo: boolean = true;
  listaStatoControllo: AttivitaDTO[] = [];
  controllo: ControlloLocoVo = new ControlloLocoVo();
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
  elencoFiles: Array<DocumentoIndexVO> = [];
  isDataAvviocontrolli: boolean;
  fileLetteraAvvioControllo: File = null;
  fileLetteraAccompagnatoria: File = null;
  fileLetteraEsitoControllo: File = null;
  fileVerbaleControllo: File = null;
  fileCheckListControllo: File = null;
  elencoFileDaGestire: File[] = [];
  isErrorAllegati: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  isMessageSuccessVisible: boolean;
  messageSuccess: string;
  isAbilita: boolean;
  idStatoRichiesta: number;
  elencoProroghe: RichiestaProrogaVo[];
  isEnableProroga: boolean;
  isDataNotifica: boolean;
  dataScadenza: Date;
  formDataElenco = new Array<FormData>();

  // questa proprieta mi serve per controllare se c'è un allegato da salvare o no 
  isAllegatiDaSavlare: boolean = false;
  esitoDTO: EsitoDTO = new EsitoDTO(null, null, null); idStatoPrecedenteControllo: number;
  idRevoca: number;
  ;


  constructor(
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private controlliService: ControlliService,
    public sharedService: SharedService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
    public dialog: MatDialog,
    private configService: ConfigService
  ) {

  }


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
      }
    });

    this.route.queryParams.subscribe(params => {

      this.idControllo = params['idControllo'],
      this.idProgetto = params["idProgetto"];

      this.getInfoControllo();
      this.lisTipoVisita.push("Amministrativa");
      this.lisTipoVisita.push("Tecnica");
      this.lisTipoVisita.push("Video-call");
      this.checkRichiestaIntegrazione();
    });
  }
  getInfoControllo() {
    this.controlloLoaded = false;
    this.controlliService.getInfoControllo(this.idControllo, this.idProgetto).subscribe(data => {
      if (data) {
        this.controllo = data;
        this.controlloLoaded = true;
        this.idStatoPrecedenteControllo = data.idStatoControllo;
        this.dataAvvioControlli = this.controllo.dataAvvioControlli;
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
        if (this.dataAvvioControlli != null) {
          this.isDataAvviocontrolli = true;
        }
      }

    })
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
  setCampi() {
    this.controllo.idStatoControllo = this.idStatoControllo;
    this.controllo.dataFineControlli = this.dataFineControllo;
    this.controllo.dataInizioControlli = this.dataInizioControllo;
    this.controllo.dataVisitaControllo = this.dataVisitaControllo;
    this.controllo.dataAvvioControlli = this.dataAvvioControlli;
    this.controllo.descTipoVisita = this.descTipoVisita;

    if (this.importoDaControllare != null ) {
      this.controllo.importoDaControllare = this.importoDaControllare;
    }

    if (this.importoAgevIrreg != null && this.idStatoControllo!=2) {
      this.controllo.importoAgevIrreg = this.importoAgevIrreg;
    }
   
    if (this.importoIrregolarita != null && this.idStatoControllo!=2) {
      this.controllo.importoIrregolarita = this.importoIrregolarita;
    }
    (this.istruttoreVisita != null) ? (this.controllo.istruttoreVisita = this.istruttoreVisita) : null;
  }

  avviaControlloInLoco() {

    this.setCampi();
    let dialogRef = this.dialog.open(DialogGestioneControlliComponent, {
      width: '30%',
      data: {
        element: 1,
        controllo: this.controllo,
        idUtente: this.idUtente,
        idControllo: this.idControllo,
        file: this.fileLetteraAvvioControllo,
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
  annullaRichiestaIntegrazione() {
    let dialogRef = this.dialog.open(DialogGestioneControlliComponent, {
      width: '30%',
      data: {
        element: 4,
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
    })
  }

  gestisciAllegati() {
    // gestione allegati
    this.resetMessageError()
    this.isErrorAllegati = false;
    let dialogRef = this.dialog.open(DialogGestioneControlliComponent, {
      width: '50%',
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

      // dentro l'elenco dei file Allegati controllo ci sono 5 files in questo ordine da 0 a 4
      // this.fileLetteraAvvioControllo 
      // this.fileLetteraAccompagnatoria 
      // this.fileLetteraEsitoControllo 
      // this.fileVerbaleControllo 
      // this.fileCheckListControllo
    });
  }

  richiestaIntegrazione() {
    this.setCampi();
    let dialogRef = this.dialog.open(DialogGestioneControlliComponent, {
      width: '50%',
      data: {
        element: 3,
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
  disableStato(idStato): boolean {
    if (idStato == 3)
      return true
    else
      return false;
  }
  checkRichiestaIntegrazione() {
    this.isLoadedIntegrazione = false;
    this.subscribers.checkRichiestaIntegrazione = this.controlliService.checkRichiestaIntegrazione(this.idControllo).subscribe(data => {
      if (data) {
        this.isLoadedIntegrazione = true;
        this.richiestaIntegrVO = data;
        this.dataAutorizzata = this.richiestaIntegrVO.dataRichiesta;
        this.dataNotifica = this.richiestaIntegrVO.dataNotifica;
        this.dataScadenza = this.richiestaIntegrVO.dataScadenza;
        this.idStatoRichiesta = this.richiestaIntegrVO.idStatoRichiesta;
        console.log(this.richiestaIntegrVO);

        if (this.richiestaIntegrVO.idRichiestaIntegr) {
          this.isRichiesta = true;
          this.getProroga();
          // this.abilitaRichiesta(); 
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
  gestisciProroga() {

    this.richProroga.idTarget = this.richiestaIntegrVO.idRichiestaIntegr;
    let dialogRef = this.dialog.open(DialogGestioneControlliComponent, {
      width: '40%',
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
        console.log(data);
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
  controlloAllegati() {
    this.isAllegatiDaSavlare = false;
    console.log("id Stato controllo " + this.idStatoControllo);
    // dentro l'elenco dei file Allegati controllo ci sono 5 files in questo ordine da 0 a 4
    // this.fileLetteraAvvioControllo 
    // this.fileLetteraAccompagnatoria 
    // this.fileLetteraEsitoControllo 
    // this.fileVerbaleControllo 
    // this.fileCheckListControllo

    if (this.elencoFileDaGestire) {
      this.fileLetteraAvvioControllo = this.elencoFileDaGestire[0];
      this.fileLetteraAccompagnatoria = this.elencoFileDaGestire[1];
      this.fileLetteraEsitoControllo = this.elencoFileDaGestire[2];
      this.fileVerbaleControllo = this.elencoFileDaGestire[3];
      this.fileCheckListControllo = this.elencoFileDaGestire[4];
    }

    if (this.idStatoControllo != this.idStatoPrecedenteControllo) {
      switch (this.idStatoControllo) {
        case 4:
        case 5:
          if (this.fileVerbaleControllo != null && this.fileCheckListControllo != null) {
            // salvo Controllo e gli allegati corrispondenti
            this.isAllegatiDaSavlare = true
            this.salvaControllo();
          } else {
            this.isErrorAllegati = true;
            this.showMessageError("controllare gli allegati:Verbale di controllo in loco e Checklist di controllo interno ");
          }
          break;
        case 2:
          if (this.fileVerbaleControllo != null && this.fileCheckListControllo != null && this.fileLetteraEsitoControllo != null) {
            this.isAllegatiDaSavlare = true
            this.salvaControllo();
          } else {
            this.isErrorAllegati = true;
            this.showMessageError("controllare gli allegati: Verbale di controllo in loco, Checklist di controllo interno, Lettera di esito controllo in loco!");
          }

          break;
        case 3:
          this.salvaControllo();
          break;
        case 1:
          this.salvaControllo();
          break;
        default:
          break;
      }
    } else {
      this.salvaControllo();
    }

    console.log(this.isErrorAllegati);

  }
  // entita che varia da 1 a 4 (parametro passato dal metodo chiamante)
  // 1 per la tabella pbandi_t_controllo_finp
  // 2 per la tabella PBANDI_T_RICHIESTA_INTEGRAZ
  // 3 per la tabella pbandi_t_controllo_altri
  // 4 per la tabella PBANDI_T_GESTIONE_REVOCA

  addAllegato(idTipoDocumentoIndex: number, idUtente: number, nomeFile: string, file: File, idTarget: number, idProgetto: number, entita: number) {

    let formData = new FormData();
    formData.append("fileVerbale", file, nomeFile);
    formData.append("idUtente", idUtente.toString());
    formData.append("idTarget", idTarget.toString());
    formData.append("idTipoDocumentoIndex", idTipoDocumentoIndex.toString());
    formData.append("idProgetto", idProgetto.toString());
    formData.append("entita", entita.toString());

    this.formDataElenco.push(formData);
  }

  // questo metodo in realtà fa l'update del controllo già esistente
  salvaControllo() {

    this.setCampi();
    this.subscribers.avviaIter = this.controlliService.updateControlloFinp(this.controllo, this.idUtente, this.idAttivitaControllo).subscribe(data => {
      if (data.esito == true) {
        this.isSave == true;
        this.esitoDTO = data;
        this.showMessageSuccess(data.messaggio);
        // se non ci sono allegati da salvare torno subito
        if (this.isAllegatiDaSavlare == false) {
          setTimeout(() => {
            this.goBack();
          }, 3000);
        } else {
          this.callToSalvaAllegati();
        }
      } else {
        this.showMessageError(data.messaggio);
      }
    });
  }

  callToSalvaAllegati() {

    switch (this.idStatoControllo) {
      case 4:
      case 5:

        let idRevoca = this.esitoDTO.id;
        this.subscribers.salvaAllegati = this.controlliService.salvaAllegati2(this.elencoFileDaGestire, this.idStatoControllo, this.idProgetto, true, this.idControllo, idRevoca).subscribe(data => {
          if (data) {
            this.isSave = data;
            setTimeout(() => {
              this.goBack();
            }, 3000);
          }
        })

        break;

      case 2:
        this.subscribers.salvaAllegati = this.controlliService.salvaAllegati2(this.elencoFileDaGestire, this.idStatoControllo, this.idProgetto, true, this.idControllo).subscribe(data => {
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

  avviaIterEsitoPositivoControllo() {
    this.subscribers.avviaIterEsitoPositivoControllo = this.controlliService.avviaIterEsitoPositivoControllo(this.idProgetto, this.idControllo, false).subscribe(data => {
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

  getElencoFile() {
    this.subscribers.elencoAllegati = this.controlliService.getElencoAllegati(this.idControllo, 570).subscribe(data => {
      if (data) {
        this.elencoFiles = data;
      }
    });
  }

  gestioneCheckList() {
    const descBreve = Constants.DESCR_BREVE_TASK_CHECKLIST;
    const descTasc = 'Gestione checklist del progetto';
    this.navigateToAttivita(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CHECKLIST, "nuovaChecklist", this.controllo.idProgetto,
      descBreve, descTasc);

  }

  navigateToAttivita(configComponentURL: string, idAttivita: number, destComponentPath: string, idProgetto: number, descBreveTask: string, descTask: string) {
    let url: string = `${configComponentURL}/#/drawer/${idAttivita}/${destComponentPath}/${idProgetto}?idSg=${this.user.idSoggetto}`
      + `&idSgBen=${this.controllo.idSoggettoBenef}&idU=${this.user.idUtente}&role=${this.user.codiceRuolo}`
      + `&idPrj=${idProgetto}&taskIdentity=${descBreveTask}&taskName=${descTask}&wkfAction=StartTaskNeoFlux`;
    window.location.href = url;
  }
  goBack() {
    this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_FIN + "/ricercaControlliProgettiFinp"], { queryParams: {} });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  showMessageSuccess(msg: string) {
    this.isMessageSuccessVisible = true;
    this.messageSuccess = msg;
  }
  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  isRichiestaIntegr() {
    if ((this.idStatoControllo == 3 || this.idStatoControllo == 1)) {
      if ((this.isRichiesta == false || this.idStatoRichiesta == 5 || this.idStatoRichiesta == 3 || this.idStatoRichiesta == 2)) {
        return true;
      }
    }
    return false;
  }
  isLoading() {
    if (!this.userloaded || !this.loadedStControllo || !this.controlloLoaded) {
      return true;
    }
    return false;
  }

}
