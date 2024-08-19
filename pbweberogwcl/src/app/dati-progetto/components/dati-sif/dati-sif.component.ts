/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DatiSif } from '../../commons/dto/dati-sif';
import { SharedService } from 'src/app/shared/services/shared.service';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { DatiProgettoService } from '../../services/dati-progetto.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

@Component({
  selector: 'app-dati-sif',
  templateUrl: './dati-sif.component.html',
  styleUrls: ['./dati-sif.component.scss']
})
export class DatiSifComponent implements OnInit {

  @Input("idProgetto") idProgetto: number;
  @Input("datiSif") datiSif: DatiSif = new DatiSif(null, null);

  @Output("messageError") messageError = new EventEmitter<string>();
  @Output("messageSuccess") messageSuccess = new EventEmitter<string>();

  user: UserInfoSec;
  action: string;
  isModificaLimited: boolean;

  loadedSalvaDatiSif: boolean = true;

  subscribers: any = {};

  constructor(
    private userService: UserService,
    private datiProgettoService: DatiProgettoService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
      }
    });
  }

  loadData(): void {
    if (this.isIstruttore()) {
      this.action = 'modifica';
    } else if (this.isBeneficiario()) {
      this.action = 'dettaglio';
    }
    if (this.action == 'modifica') {
      this.isModificaLimited = true;
    }
  }

  modifica() {
    if (this.isModificaLimited) {
      this.isModificaLimited = false; // Modifica
    }
  }

  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedSalvaDatiSif = false;
    this.datiProgettoService.salvaDatiSif(this.idProgetto, this.datiSif).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess(data.msg);
          this.isModificaLimited = true;
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedSalvaDatiSif = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalvaDatiSif = true;
    });
  }

  isBeneficiario() {
    return this.sharedService.isBeneficiario(this.user);
  }

  isIstruttore() {
    return this.sharedService.isIstruttore(this.user);
  }

  showMessageError(msg: string) {
    this.messageError.emit(msg);
  }

  resetMessageError() {
    this.messageError.emit(null);
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess.emit(msg);
  }

  resetMessageSuccess() {
    this.messageSuccess.emit(null);
  }

  isLoading() {
    if (!this.loadedSalvaDatiSif) {
      return true;
    }
    return false;
  }
}
