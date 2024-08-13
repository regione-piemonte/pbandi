/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PermessoDTOCheck } from 'src/app/associazione-permesso-ruolo/commons/dto/permesso-dto';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { TipoAnagraficaDTOCheck } from '../../commons/dto/tipo-anagrafica-dto';
import { AssociazioneRuoloPermessoService } from '../../services/associazione-ruolo-permesso.service';

@Component({
  selector: 'app-associazione-rp-dialog',
  templateUrl: './associazione-rp-dialog.component.html',
  styleUrls: ['./associazione-rp-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AssociazioneRpDialogComponent implements OnInit {

  user: UserInfoSec;
  codice: string;
  descrizione: string;
  daAssociare: Array<PermessoDTOCheck>;
  associati: Array<PermessoDTOCheck>;
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
    public dialogRef: MatDialogRef<AssociazioneRpDialogComponent>,
    private associazioneRuoloPermessoService: AssociazioneRuoloPermessoService,
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
    this.subscribers.daAssociare = this.associazioneRuoloPermessoService.cercaPermessiDaAssociare(this.codice, this.user.idUtente).subscribe(data => {
      if (data) {
        this.daAssociare = <Array<PermessoDTOCheck>>data;
      }
      this.loadedDaAssociare = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero dei permessi da associare.");
      this.loadedDaAssociare = true;
    });
    this.subscribers.associati = this.associazioneRuoloPermessoService.cercaPermessiAssociati(this.codice, this.user.idUtente).subscribe(data => {
      if (data) {
        this.associati = <Array<PermessoDTOCheck>>data;
      }
      this.loadedAssociati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero dei permessi associati.");
      this.loadedAssociati = true;
    });
  }

  changeDaAssociareCheck() {
    let permessi = this.daAssociare.filter(a => a.checked);
    if (permessi && permessi.length > 0) {
      this.isAssociaDisabled = false;
    } else {
      this.isAssociaDisabled = true;
    }
  }

  changeAssociatiCheck() {
    let permessi = this.associati.filter(a => a.checked);
    if (permessi && permessi.length > 0) {
      this.isDisassociaDisabled = false;
    } else {
      this.isDisassociaDisabled = true;
    }
  }

  getAssociaTooltip() {
    if (this.daAssociare) {
      let permessi = this.daAssociare.filter(a => a.checked);
      if (permessi && permessi.length > 0) {
        return "Associa";
      } else {
        return "Associa: seleziona almeno un permesso da associare";
      }
    }
  }

  getDisassociaTooltip() {
    if (this.associati) {
      let permessi = this.associati.filter(a => a.checked);
      if (permessi && permessi.length > 0) {
        return "Disassocia";
      } else {
        return "Disassocia: seleziona almeno un permesso associato";
      }
    }
  }

  associa() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loaded = false;
    let permessi = new Array<PermessoDTOCheck>();
    this.daAssociare.filter(a => a.checked).forEach(t => permessi.push(Object.assign({}, t)));
    permessi.forEach(t => {
      delete t['checked'];
    });
    this.subscribers.associa = this.associazioneRuoloPermessoService.associaPermessi(this.codice, this.user.idUtente, permessi).subscribe(data => {
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
    let permessi = new Array<PermessoDTOCheck>();
    this.associati.filter(a => a.checked).forEach(t => permessi.push(Object.assign({}, t)));
    permessi.forEach(t => {
      delete t['checked'];
    });
    this.subscribers.disassocia = this.associazioneRuoloPermessoService.disassociaPermessi(this.codice, this.user.idUtente, permessi).subscribe(data => {
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
