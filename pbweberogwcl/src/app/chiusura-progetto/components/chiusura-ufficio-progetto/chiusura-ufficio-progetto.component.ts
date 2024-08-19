/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { EsitoOperazioni } from "../../../shared/api/models/esito-operazioni";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { InizializzazioneService } from "../../../shared/services/inizializzazione.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { Constants, DatiProgettoAttivitaPregresseDialogComponent } from "@pbandi/common-lib";
import { ChiusuraProgettoService } from "../../services/chiusura-progetto.service";
import { SharedService } from "../../../shared/services/shared.service";
import { RequestChiudiProgetto } from "../../commons/requests/request-chiudi-progetto";
import { EsitoOperazioneChiudiProgetto } from "../../commons/models/esito-operazione-chiudi-progetto";
import { ConfermaDialog } from "../../../shared/components/conferma-dialog/conferma-dialog";
import { MatDialog } from "@angular/material/dialog";
import { RegistroControlliService2 } from 'src/app/registro-controlli/services/registro-controlli2.service';

@Component({
  selector: 'app-chiusura-ufficio-progetto',
  templateUrl: './chiusura-ufficio-progetto.component.html',
  styleUrls: ['./chiusura-ufficio-progetto.component.scss']
})
export class ChiusuraUfficioProgettoComponent implements OnInit {

  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;
  isMessageSuccessVisible: any;
  isMessageErrorVisible: any;

  messageSuccess: any;
  messageError: any;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedEsitoRinunciaPresente: boolean;
  loadedChiudiProgetto: boolean;
  loadedChiudiAttivita: boolean = true;
  loadedVerificaErogazioneSaldo: boolean;

  esisteRinuncia: EsitoOperazioni = { esito: false };
  note: string;
  requestChiudi: RequestChiudiProgetto;

  esitoChiudiProgetto: EsitoOperazioneChiudiProgetto;

  isChiusuraProgettoRinuncia = false;

  messageWarning: string;
  isMessageWarningVisible: boolean;

  constructor(private configService: ConfigService, private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private inizializzazioneService: InizializzazioneService,
    private chiusuraProgettoService: ChiusuraProgettoService,
    private router: Router, private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService, private dialog: MatDialog,
    private registroControlliService2: RegistroControlliService2) { }

  ngOnInit(): void {

    let params: URLSearchParams = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    if (Constants.DESCR_BREVE_TASK_CHIUSURA_RINUNCIA_PROGETTO == params.get('taskIdentity')) {
      this.isChiusuraProgettoRinuncia = true;
    } else {
      this.isChiusuraProgettoRinuncia = false;
    }

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];

            this.loadData();
          });
        }
      }
    });
  }

  loadData() {
    // CODICE PROGETTO
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });

    // LOAD ESITO HAS RINUNCIA
    this.loadedEsitoRinunciaPresente = false;
    this.registroControlliService2.rinuncia(Number(this.idProgetto)).subscribe(data => {
      this.loadedEsitoRinunciaPresente = true;
      if (data.esito) {
        this.showMessageWarning("NB: il beneficiario ha presentato comunicazione di rinuncia al finanziamento per il presente progetto.")
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedEsitoRinunciaPresente = true;
    })
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;

    var task;
    if (this.isChiusuraProgettoRinuncia) {
      task = Constants.DESCR_BREVE_TASK_CHIUSURA_RINUNCIA_PROGETTO;
    } else {
      task = Constants.DESCR_BREVE_TASK_CHIUSURA_UFFICIO_PROGETTO;
    }

    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, task).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  chiudiUfficioProgetto() {
    this.loadedChiudiProgetto = true;
    this.requestChiudi = { idProgetto: this.idProgetto, note: this.note }
    this.subscribers.chiudi = this.chiusuraProgettoService.chiudiProgettoDiUfficio(this.requestChiudi).subscribe(res => {
      if (res) {
        this.esitoChiudiProgetto = res;
        if (res && res.esito == true) {
          this.showMessageSuccess("L’ operazione chiusura del progetto è eseguito con successo.")
        }
      }
      this.loadedChiudiProgetto = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiProgetto = false;
    });
  }

  chiudiProgetto() {
    this.loadedChiudiProgetto = true;
    this.requestChiudi = { idProgetto: this.idProgetto, note: this.note }
    this.subscribers.chiudi = this.chiusuraProgettoService.chiudiProgetto(this.requestChiudi).subscribe(res => {
      if (res) {
        this.esitoChiudiProgetto = res;
        if (res && res.esito == true) {
          this.showMessageSuccess("L’ operazione chiusura del progetto è eseguito con successo.")
        }
      }
      this.loadedChiudiProgetto = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiProgetto = false;
    });
  }

  congermaDialog() {
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "L’ operazione chiuderà la gestione operativa del progetto. Continuare?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        if (this.isChiusuraProgettoRinuncia) {

          this.loadedVerificaErogazioneSaldo = true;
          this.chiusuraProgettoService.verificaErogazioneSaldo(this.idProgetto).subscribe(res => {

            this.loadedVerificaErogazioneSaldo = false;
            if (res.esito) {
              // CHIAMA SERVIZIO chiudi progetto
              this.chiudiProgetto();
            } else {
          let dialogRef2 = this.dialog.open(ConfermaDialog, {
            width: '40%',
            data: {
                  message: res.msg
            }
          });
          dialogRef2.afterClosed().subscribe(res => {
            if (res === "SI") {
                  // CHIAMA SERVIZIO chiudi progetto
                  this.chiudiProgetto();
            }
          });
            }
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.loadedVerificaErogazioneSaldo = false;
          });
        } else {
          // CHIAMA SERVIZIO chiudi progetto ufficio
          this.chiudiUfficioProgetto();
        }
      }
    });
  }

  isLoading() {
     if (!this.loadedCodiceProgetto || !this.loadedEsitoRinunciaPresente || this.loadedChiudiProgetto || this.loadedVerificaErogazioneSaldo || !this.loadedChiudiAttivita) {
      return true;
    }
    return false;
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

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }
  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }
  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }
  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
  }
  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }
}
