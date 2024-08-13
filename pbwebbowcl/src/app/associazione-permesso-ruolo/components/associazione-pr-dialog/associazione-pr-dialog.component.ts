/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { TipoAnagraficaDTOCheck } from '../../commons/dto/tipo-anagrafica-dto';
import { AssociazionePermessoRuoloService } from '../../services/associazione-permesso-ruolo.service';

@Component({
  selector: 'app-associazione-pr-dialog',
  templateUrl: './associazione-pr-dialog.component.html',
  styleUrls: ['./associazione-pr-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AssociazionePrDialogComponent implements OnInit {

  user: UserInfoSec;
  codice: string;
  descrizione: string;
  daAssociare: Array<TipoAnagraficaDTOCheck>;
  associati: Array<TipoAnagraficaDTOCheck>;
  isAssociaDisabled: boolean = true;
  isDisassociaDisabled: boolean = true;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedAssociati: boolean;
  loadedDaAssociare: boolean;
  loaded: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<AssociazionePrDialogComponent>,
    private associazionePermessoRuoloService: AssociazionePermessoRuoloService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.user = this.data.user;
    this.codice = this.data.codice;
    this.descrizione = this.data.descrizione;
    this.loadData();
  }

  loadData() {
    this.loadedDaAssociare = false;
    this.loadedAssociati = false;
    this.daAssociare = null;
    this.associati = null;
    this.subscribers.daAssociare = this.associazionePermessoRuoloService.cercaRuoliDaAssociare(this.codice, this.user.idUtente).subscribe(data => {
      if (data) {
        this.daAssociare = <Array<TipoAnagraficaDTOCheck>>data;
      }
      this.loadedDaAssociare = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero dei ruoli da associare.");
      this.loadedDaAssociare = true;
    });
    this.subscribers.associati = this.associazionePermessoRuoloService.cercaRuoliAssociati(this.codice, this.user.idUtente).subscribe(data => {
      if (data) {
        this.associati = <Array<TipoAnagraficaDTOCheck>>data;
      }
      this.loadedAssociati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero dei ruoli associati.");
      this.loadedAssociati = true;
    });
  }

  changeDaAssociareCheck() {
    let tipi = this.daAssociare.filter(a => a.checked);
    if (tipi && tipi.length > 0) {
      this.isAssociaDisabled = false;
    } else {
      this.isAssociaDisabled = true;
    }
  }

  changeAssociatiCheck() {
    let tipi = this.associati.filter(a => a.checked);
    if (tipi && tipi.length > 0) {
      this.isDisassociaDisabled = false;
    } else {
      this.isDisassociaDisabled = true;
    }
  }

  getAssociaTooltip() {
    if (this.daAssociare) {
      let tipi = this.daAssociare.filter(a => a.checked);
      if (tipi && tipi.length > 0) {
        return "Associa";
      } else {
        return "Associa: seleziona almeno un ruolo da associare";
      }
    }
  }

  getDisassociaTooltip() {
    if (this.associati) {
      let tipi = this.associati.filter(a => a.checked);
      if (tipi && tipi.length > 0) {
        return "Disassocia";
      } else {
        return "Disassocia: seleziona almeno un ruolo associato";
      }
    }
  }

  associa() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loaded = false;
    let tipi = new Array<TipoAnagraficaDTOCheck>();
    this.daAssociare.filter(a => a.checked).forEach(t => tipi.push(Object.assign({}, t)));
    tipi.forEach(t => {
      delete t['checked'];
    });
    this.subscribers.associa = this.associazionePermessoRuoloService.associaRuoli(this.codice, this.user.idUtente, tipi).subscribe(data => {
      if (data && data.code === "OK") {
        this.loadData();
        this.showMessageSuccess(data.message);
      }
      this.loaded = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di associazione.");
      this.loaded = true;
    });
  }

  disassocia() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loaded = false;
    let tipi = new Array<TipoAnagraficaDTOCheck>();
    this.associati.filter(a => a.checked).forEach(t => tipi.push(Object.assign({}, t)));
    tipi.forEach(t => {
      delete t['checked'];
    });
    this.subscribers.disassocia = this.associazionePermessoRuoloService.disassociaRuoli(this.codice, this.user.idUtente, tipi).subscribe(data => {
      if (data && data.code === "OK") {
        this.loadData();
        this.showMessageSuccess(data.message);
      }
      this.loaded = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di disassociazione.");
      this.loaded = true;
    });
  }

  close() {
    this.dialogRef.close();
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

  isLoading() {
    if (!this.loadedAssociati || !this.loadedDaAssociare || !this.loaded) {
      return true;
    }
    return false;
  }

}
