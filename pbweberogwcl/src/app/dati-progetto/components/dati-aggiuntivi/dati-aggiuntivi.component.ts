/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DatiAggiuntiviDTO } from '../../commons/dto/dati-aggiuntivi-dto';
import { DatiProgettoService } from '../../services/dati-progetto.service';

@Component({
  selector: 'app-dati-aggiuntivi',
  templateUrl: './dati-aggiuntivi.component.html',
  styleUrls: ['./dati-aggiuntivi.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DatiAggiuntiviComponent implements OnInit {

  @Input("idProgetto") idProgetto: number;

  @Output("messageError") messageError = new EventEmitter<string>();
  @Output("messageSuccess") messageSuccess = new EventEmitter<string>();

  user: UserInfoSec;

  datiAggiuntivi: DatiAggiuntiviDTO;

  loadedDatiAggiuntivi: boolean;

  subscribers: any = {};

  constructor(
    private userService: UserService,
    private datiProgettoService: DatiProgettoService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
      }
    });
  }

  loadData(isInit?: boolean) {
    if (isInit) {
      this.loadedDatiAggiuntivi = false;
      this.datiProgettoService.getDatiAggiuntivi(this.idProgetto).subscribe(data => {
        this.datiAggiuntivi = data;
        this.loadedDatiAggiuntivi = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei dati aggiuntivi.");
        this.loadedDatiAggiuntivi = true;
      });
    }
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
    if (!this.loadedDatiAggiuntivi) {
      return true;
    }
    return false;
  }
}
