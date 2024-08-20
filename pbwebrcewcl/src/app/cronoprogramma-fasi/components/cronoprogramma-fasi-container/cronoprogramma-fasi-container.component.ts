/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-cronoprogramma-fasi-container',
  templateUrl: './cronoprogramma-fasi-container.component.html',
  styleUrls: ['./cronoprogramma-fasi-container.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CronoprogrammaFasiContainerComponent implements OnInit {

  apiURL: string;
  user: UserInfoSec;

  idProgetto: number;
  idBandoLinea: number;
  idOperazione: number;

  isReadOnly: boolean;
  isBR58a: boolean;
  isBR58b: boolean;

  messageError: Array<string> = new Array<string>();
  isMessageErrorVisible: boolean;

  loadedBR58a: boolean;
  loadedBR58b: boolean;
  loadedChiudiAttivita: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private sharedService: SharedService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.apiURL = this.configService.getApiURL();

    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['id'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
        this.idOperazione = +params['id'];
        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
          if (data) {
            this.user = data;
            if (this.user?.codiceRuolo && this.user?.beneficiarioSelezionato) {
              if (this.user.codiceRuolo !== Constants.CODICE_RUOLO_BEN_MASTER
                && this.user.codiceRuolo !== Constants.CODICE_RUOLO_PERSONA_FISICA) {
                this.isReadOnly = true;
              }
            }
          }
        });
      });
    });
    this.loadedBR58a = false;
    this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR58a").subscribe(res => {
      this.isBR58a = res;
      this.loadedBR58a = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBR58a = true;
    });
    this.loadedBR58b = false;
    this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR58b").subscribe(res => {
      this.isBR58b = res;
      this.loadedBR58b = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBR58b = true;
    });
  }

  goToAttivita() {
    this.resetMessage();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_CRONOPROGRAMMA_FASI).subscribe(_data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError(["ATTENZIONE: non è stato possibile chiudere l'attività."]);
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  resetMessage() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  // resetta gli errori e motra il messaggio di errore 
  showMessageError(msg: Array<string>) {
    this.resetMessage()
    if (msg != undefined && msg != null) {
      this.messageError = msg;
    }
    else {
      this.messageError = ["Error"];
    }
    this.isMessageErrorVisible = true;

    //this.vps.scrollToAnchor("sclrollId"); // non funziona

    //const element = document.querySelector('#scrollId'); // non funziona
    var element = document.getElementById('scrollId')
    element.scrollIntoView();
  }

  isLoading() {
    if (!this.loadedBR58a || !this.loadedBR58b || !this.loadedChiudiAttivita) {
      return true;
    }
    return false;
  }
}
