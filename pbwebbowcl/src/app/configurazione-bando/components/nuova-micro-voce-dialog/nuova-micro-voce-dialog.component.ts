/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { IdDescBreveDescEstesaVo } from '../../commons/vo/id-desc-breve-desc-estesa-vo';
import { VociSpesaService } from '../../services/voci-spesa.service';

@Component({
  selector: 'app-nuova-micro-voce-dialog',
  templateUrl: './nuova-micro-voce-dialog.component.html',
  styleUrls: ['./nuova-micro-voce-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovaMicroVoceDialogComponent implements OnInit {

  // Dichiarazione variabili
  voceSpesaMonitSelected: IdDescBreveDescEstesaVo;
  vociSpesaMonit: Array<IdDescBreveDescEstesaVo>;
  microVoceSpesa: string;
  codiceTipo: string;
  speseGestione: boolean;

  user: UserInfoSec;
  idTipoOperazione: string;
  idNaturaCipe: string;

  // MESSAGGI ERROR
  messageError: string;
  isMessageErrorVisible: boolean;
  hasValidationError: boolean;
  @ViewChild("datiForm", { static: true }) datiForm: NgForm;

  loaded: boolean;

  subscribers: any = {};

  constructor(
    private vociSpesaService: VociSpesaService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: Array<any>,
    public dialogRef: MatDialogRef<NuovaMicroVoceDialogComponent>
  ) { }

  ngOnInit(): void {
    this.user = this.data[0];
    this.idTipoOperazione = this.data[1];
    this.idNaturaCipe = this.data[2];

    this.loaded = false;
    this.vociSpesaMonit = new Array<IdDescBreveDescEstesaVo>();
    this.subscribers.voceSpesa = this.vociSpesaService.voceSpesaMonitoraggio(this.user, this.idTipoOperazione, this.idNaturaCipe).subscribe(data => {
      this.vociSpesaMonit = data;
      this.loaded = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di restituzione delle voci associate.");
      this.loaded = true;
    });
  }

  /* --------------------- Inizio gestione pulsanti --------------------- */
  salva() {
    this.resetMessageError();
    this.validazioneCampi();
    if (!this.hasValidationError) {
      var comodoDatiOutput = new Array<any>();
      comodoDatiOutput.push(this.microVoceSpesa);
      comodoDatiOutput.push(this.codiceTipo);
      comodoDatiOutput.push(this.voceSpesaMonitSelected);
      comodoDatiOutput.push(this.speseGestione);

      this.dialogRef.close({ data: comodoDatiOutput });
    }
  }

  close() {
    this.dialogRef.close();
  }
  /* --------------------- Fine gestione pulsanti --------------------- */

  /* --------------------- Inizio gestione messaggi errore --------------------- */
  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  /* --------------------- Fine gestione messaggi errore --------------------- */

  validazioneCampi() {
    this.hasValidationError = false;

    this.controlRequiredForm("microVoceSpesa");
    this.controlRequiredForm("codiceTipo");

    if (this.hasValidationError) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    }
  }

  controlRequiredForm(name: string) {
    if (!this.datiForm.form.get(name) || !this.datiForm.form.get(name).value) {
      this.datiForm.form.get(name).markAsTouched();
      this.datiForm.form.get(name).setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
  }

  isLoading() {
    if (!this.loaded) {
      return true;
    }
    return false;
  }
}
