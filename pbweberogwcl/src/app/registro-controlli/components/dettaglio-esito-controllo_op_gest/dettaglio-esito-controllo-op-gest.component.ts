/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from '@pbandi/common-lib';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { Irregolarita } from '../../commons/models/irregolarita';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

@Component({
  selector: 'app-dettaglio-esito-controllo-op-gest',
  templateUrl: './dettaglio-esito-controllo-op-gest.component.html',
  styleUrls: ['./dettaglio-esito-controllo-op-gest.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DettaglioEsitoControlloOpGestComponent implements OnInit {

  user: UserInfoSec;
  idEsitoControllo: string;
  beneficiario: string;
  codiceProgetto: string;

  dataComunicazioneEsito: string;
  tipoControlli: string;
  annoContabile: string;
  autoritaControllante: string;
  dataCampione: string;
  dataInizioControlli: string;
  dataFineControlli: string;
  note: string;

  isMessageErrorVisible: any;
  messageError: any;

  loadedGetDettaglioEsitoRegolare: boolean;

  constructor(
    private userService: UserService,
    private registroControlliService: RegistroControlliService,
    public dialogRef: MatDialogRef<DettaglioEsitoControlloOpGestComponent>,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  //SUBSCRIBERS
  subscribers: any = {};

  ngOnInit(): void {
    // RECUPERO PARAMETRI DA URL
    this.idEsitoControllo = this.data.idEsitoControllo;
    this.beneficiario = this.data.beneficiario;
    this.codiceProgetto = this.data.codiceProgetto;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;

        this.loadedGetDettaglioEsitoRegolare = true;
        this.subscribers.getDettaglioEsitoRegolare = this.registroControlliService.getDettaglioEsitoRegolare(Number(this.idEsitoControllo)).subscribe((res: Irregolarita) => {
          this.dataComunicazioneEsito = new Date(res.dtComunicazione).toLocaleDateString();
          this.tipoControlli = this.getDescTipoByCode(res.tipoControlli);
          this.annoContabile = res.descPeriodoVisualizzata;
          this.autoritaControllante = res.descCategAnagrafica;
          this.dataCampione = res.dataCampione ? new Date(res.dataCampione).toLocaleDateString() : null;
          this.dataInizioControlli = new Date(res.dataInizioControlli).toLocaleDateString();
          this.dataFineControlli = new Date(res.dataFineControlli).toLocaleDateString();
          this.note = res.note;
          this.loadedGetDettaglioEsitoRegolare = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di ottenimento del dettaglio della regolarità");
          this.loadedGetDettaglioEsitoRegolare = false;
        });
      }
    });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }
  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
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

  isLoading() {
    if (this.loadedGetDettaglioEsitoRegolare) {
      return true;
    } else {
      return false;
    }
  }

  close() {
    this.dialogRef.close();
  }
}
