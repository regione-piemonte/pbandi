/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { EsitoOperazioni } from "../../../shared/api/models/esito-operazioni";
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { RecuperiService } from "../../../recuperi/services/recuperi.service";
import { RevocheService } from "../../../revoche/services/revoche.service";
import { InizializzazioneService } from "../../../shared/services/inizializzazione.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { SharedService } from "../../../shared/services/shared.service";
import { DateAdapter } from "@angular/material/core";
import { DatePipe } from "@angular/common";
import { ChiusuraProgettoService } from "../../services/chiusura-progetto.service";
import { Constants, DatiProgettoAttivitaPregresseDialogComponent } from "@pbandi/common-lib";
import { RequestChiudiProgetto } from "../../commons/requests/request-chiudi-progetto";
import { EsitoOperazioneChiudiProgetto } from "../../commons/models/esito-operazione-chiudi-progetto";
import { ConfermaDialog } from "../../../shared/components/conferma-dialog/conferma-dialog";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: 'app-chiusura-progetto',
  templateUrl: './chiusura-progetto.component.html',
  styleUrls: ['./chiusura-progetto.component.scss']
})
export class ChiusuraProgettoComponent implements OnInit {

  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;
  isMessageSuccessVisible: any;
  isMessageWarningVisible: any;
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

  esisteRinuncia: EsitoOperazioni = { esito: false };
  note: string;

  requestChiudi: RequestChiudiProgetto;
  esitoChiudiProgetto: EsitoOperazioneChiudiProgetto;



  constructor(private configService: ConfigService, private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private inizializzazioneService: InizializzazioneService,
    private chiusuraProgettoService: ChiusuraProgettoService,
    private router: Router, private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService, private dialog: MatDialog) { }

  ngOnInit(): void {
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


  chiudiProgetto() {
    this.loadedChiudiProgetto = true;
    this.requestChiudi = { idProgetto: this.idProgetto, note: this.note }
    this.subscribers.chiudi = this.chiusuraProgettoService.chiudiProgetto(this.requestChiudi).subscribe(res => {
      if (res) {
        this.esitoChiudiProgetto = res;
        if (res && res.esito == true) {
          this.showMessageSuccess("L’ operazione chiusura di ufficio del progetto è eseguito con successo.")
        }
      }
      this.loadedChiudiProgetto = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiProgetto = false;
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
    this.subscribers.regolaRecuperi = this.chiusuraProgettoService.isRinunciaPresente(this.idProgetto).subscribe(res => {
      if (res) {
        this.loadedEsitoRinunciaPresente = res;
      }
      this.loadedEsitoRinunciaPresente = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedEsitoRinunciaPresente = true;
    });
  }


  /////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////// UTILITY FUNCTIONS /////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////
  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedEsitoRinunciaPresente || this.loadedChiudiProgetto || !this.loadedChiudiAttivita) {
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

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_CHIUSURA_PROGETTO).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
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
  resetMessageWarning() {
    this.isMessageWarningVisible = false;
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
        // CHIAMA SERVIZIO chiudi progetto
        this.chiudiProgetto();
      }
    });
  }
}
