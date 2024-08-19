/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';
import { DatiProgettoAttivitaPregresseDialogComponent, UserInfoSec } from '@pbandi/common-lib';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { Irregolarita } from '../../commons/models/irregolarita';
import { ConfigService } from 'src/app/core/services/config.service';
import { MatDialog } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

@Component({
  selector: 'app-dettaglio-esito-controllo',
  templateUrl: './dettaglio-esito-controllo.component.html',
  styleUrls: ['./dettaglio-esito-controllo.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DettaglioEsitoControlloComponent implements OnInit {

  idOperazione: number;
  user: UserInfoSec;
  idProgetto: string;
  codiceProgetto: string;
  idEsitoControllo: string;
  beneficiario: string;

  dataComunicazioneEsito: string;
  tipoControlli: string;
  annoContabile: string;
  autoritaControllante: string;
  dataCampione: string;
  dataInizioControlli: string;
  dataFineControlli: string;
  note: string;

  params: URLSearchParams;
  postIns: string;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  loadedGetDettaglioEsitoRegolare: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private registroControlliService: RegistroControlliService,
    private router: Router,
    private configService: ConfigService,
    private dialog: MatDialog,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    // RECUPERO PARAMETRI DA URL
    this.idEsitoControllo = this.params.get('idEsitoControllo');
    this.codiceProgetto = this.params.get('codProg');
    this.idProgetto = this.params.get('idProgetto');
    this.postIns = this.params.get('postIns');

    if (this.postIns == 'true') {
      this.showMessageSuccess("Salvataggio avvenuto con successo.");
    }

    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

        this.loadedGetDettaglioEsitoRegolare = true;
        this.subscribers.getDettaglioEsitoRegolare = this.registroControlliService.getDettaglioEsitoRegolare(Number(this.idEsitoControllo)).subscribe((res: Irregolarita) => {
          this.dataComunicazioneEsito = new Date(res.dtComunicazione).toLocaleDateString();
          this.tipoControlli = this.getDescTipoByCode(res.tipoControlli);
          this.annoContabile = res.descPeriodoVisualizzata;
          this.autoritaControllante = res.descCategAnagrafica;
          this.dataCampione = new Date(res.dataCampione).toLocaleDateString();
          this.dataInizioControlli = new Date(res.dataInizioControlli).toLocaleDateString();
          this.dataFineControlli = new Date(res.dataFineControlli).toLocaleDateString();
          this.note = res.note;
          this.loadedGetDettaglioEsitoRegolare = false;
        }, err => {
          this.loadedGetDettaglioEsitoRegolare = false;
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di ottenimento del dettaglio della regolarità");
        });
      }
    });
  }

  getDescTipoByCode(code: string) {
    if (code == 'N') {
      return 'Nessuno';
    }
    if (code == 'R') {
      return 'Revoca';
    }
    if (code == 'S') {
      return 'Soppressione';
    }
    if (code == 'D') {
      return 'Documentali';
    }
    if (code == 'L') {
      return 'In loco';
    }
  }

  indietro() {
    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/registro-controlli/` + this.idProgetto);
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

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (this.loadedGetDettaglioEsitoRegolare) {
      return true;
    } else {
      return false;
    }
  }
}
