/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from '@pbandi/common-lib';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { EsitoSalvaRettificaForfettariaDTO } from '../../commons/dto/esito-salva-rettifica-forfettaria-dto';
import { Irregolarita } from '../../commons/models/irregolarita';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { RegistroControlliService2 } from '../../services/registro-controlli2.service';

@Component({
  selector: 'app-registra-invio',
  templateUrl: './registra-invio.component.html',
  styleUrls: ['./registra-invio.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RegistraInvioComponent implements OnInit {

  codiceProgetto: string;
  beneficiario: string;
  numSegIMS: string;
  dataRegIMS: FormControl = new FormControl();
  idOperazione: number;
  idIrregolarita: string;
  user: UserInfoSec;

  //SUBSCRIBERS
  subscribers: any = {};

  isMessageErrorVisible: any;
  messageError: any;

  loadedgetDettaglioIrregolarita: boolean;
  loadedsalvaRegistroInvio: boolean;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private registroControlliService: RegistroControlliService,
    private userService: UserService,
    private registroControlliService2: RegistroControlliService2,
    public dialogRef: MatDialogRef<RegistraInvioComponent>,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idIrregolarita = this.data.idIrregolarita;
    this.beneficiario = this.data.beneficiario;
    this.codiceProgetto = this.data.codiceProgetto;

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;

        // RECUPERO DATI DI DETTAGLIO REGISTRA INVIO
        this.loadedgetDettaglioIrregolarita = true;
        this.subscribers.getDettaglioIrregolarita = this.registroControlliService.getDettaglioIrregolarita(Number(this.idIrregolarita), this.user.idUtente).subscribe((res: Irregolarita) => {
          res.numeroIms == null ? null : this.numSegIMS = res.numeroIms;
          res.dtIms == null ? null : this.dataRegIMS.setValue(new Date(res.dtIms));
          this.loadedgetDettaglioIrregolarita = false;
        }, err => {
          this.loadedgetDettaglioIrregolarita = false;
          this.showMessageError("Errore in fase di ottenimento del dettaglio dell'irregolarità");
        });
      }
    });
  }

  salva() {
    if (this.numSegIMS == undefined || this.dataRegIMS.value == undefined) {
      this.showMessageError("Si prega di popolare i campi obbligatori.");
    } else {
      // INVOCAZIONE SERVIZIO DI SALVATAGGIO
      let request: Irregolarita = {
        idIrregolarita: Number(this.idIrregolarita),
        riferimentoIMS: this.numSegIMS,
        dtIms: new Date(Date.parse(this.dataRegIMS.value)).toLocaleDateString()
      };

      this.loadedsalvaRegistroInvio = true;
      this.subscribers.salvaRegistroInvio = this.registroControlliService2.salvaRegistroInvio(request).subscribe((res: EsitoSalvaRettificaForfettariaDTO) => {
        if (res.esito == true) {
          this.indietro("Salvataggio avvenuto con successo.")
        }
        this.loadedsalvaRegistroInvio = false;
      }, err => {
        this.loadedsalvaRegistroInvio = false;
        this.showMessageError("Errore in fase di salvataggio del registra invio");
      });
      this.resetMessageError();
    }
  }

  goToDatiProgettoEAttivitaPregresse() {
    /* this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
       width: '90%',
       maxHeight: '95%',
       data: {
         idProgetto: this.idProgetto,
         apiURL: this.configService.getApiURL()
       }
     });*/
  }

  indietro(message?: string) {
    let res = { str: "REGISTRA INVIO", message: null }
    if (message) {
      res.message = message;
    }
    this.dialogRef.close(res);
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (this.loadedgetDettaglioIrregolarita || this.loadedsalvaRegistroInvio) {
      return true;
    } else {
      return false;
    }
  }

  close() {
    this.dialogRef.close();
  }
}
