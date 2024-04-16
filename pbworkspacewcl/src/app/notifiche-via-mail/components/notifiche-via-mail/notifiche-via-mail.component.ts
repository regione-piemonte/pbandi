/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { FrequenzaVO } from '../../commons/vo/frequenza-vo';
import { NotificaAlertVO } from '../../commons/vo/notifica-alert-vo';
import { NotificheViaMailService } from '../../services/notifiche-via-mail.service';
import { AssociaProgettiDialog } from '../associa-progetti-dialog/associa-progetti-dialog.component';

@Component({
  selector: 'app-notifiche-via-mail',
  templateUrl: './notifiche-via-mail.component.html',
  styleUrls: ['./notifiche-via-mail.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NotificheViaMailComponent implements OnInit {

  idOperazione: number;
  email: string;
  isEmailSaved: boolean;
  isEmailEditable: boolean;
  notificheAlert: Array<NotificaAlertVO>;
  frequenze: Array<FrequenzaVO>;

  dataSource: MatTableDataSource<NotificaAlertVO>;
  displayedColumns: string[] = ['check', 'tipologia', 'frequenza', 'azioni'];

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedEmail: boolean;
  loadedNotificheFrequenze: boolean;
  loadedAssociate: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private notificheViaMailService: NotificheViaMailService,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.loadedEmail = false;
    this.subscribers.email = this.notificheViaMailService.getMail().subscribe(data => {
      if (data) {
        this.email = data;
        this.isEmailSaved = true;
        this.isEmailEditable = true;
      }
      this.loadedEmail = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.loadNotificheFrequenze();
  }

  loadNotificheFrequenze() {
    this.loadedNotificheFrequenze = false;
    this.subscribers.notificheFrequenze = this.notificheViaMailService.getNotificheFrequenze().subscribe(data => {
      if (data) {
        this.notificheAlert = data.notificheAlert;
        console.log(this.notificheAlert);
        this.frequenze = data.frequenze;
        for (let n of this.notificheAlert) {
          if (n.hasProgettiAssociated === "S" && n.idSoggettoNotifica !== 0) {
            n.selected = true;
          }
          if (n.idFrequenza === 0) {
            n.idFrequenza = 1;
          }
        }
        this.dataSource = new MatTableDataSource<NotificaAlertVO>(this.notificheAlert);
      }
      this.loadedNotificheFrequenze = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  modificaEmail() {
    this.isEmailEditable = false;
  }

  salvaEmail() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedEmail = false;
    this.subscribers.saveMail = this.notificheViaMailService.saveMail(this.email).subscribe(data => {
      if (data) {
        this.isEmailSaved = true;
        this.isEmailEditable = true;
        this.showMessageSuccess("Salvataggio avvenuto correttamente.");
      }
      this.loadedEmail = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel salvataggio dell'indirizzo email.");
      this.loadedEmail = true;
    })

  }

  selectAllCheckbox(e: any) {
    this.notificheAlert.forEach(n => n.selected = e.checked);
  }

  associateNotificheIstruttore() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedAssociate = false;
    this.subscribers.notificheIstruttore = this.notificheViaMailService.associateNotificheIstruttore(this.notificheAlert).subscribe(data => {
      if (data) {
        this.loadNotificheFrequenze();
        this.showMessageSuccess("Salvataggio avvenuto correttamente.");
      }
      this.loadedAssociate = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel salvataggio delle notifiche.");
      this.loadedAssociate = true;
    });
  }

  openAssociaProgetto(idSoggettoNotifica: number) {
    const dialogRef = this.dialog.open(AssociaProgettiDialog, {
      width: '60%',
      maxHeight: '500px',
      data: { idSoggettoNotifica: idSoggettoNotifica }
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

  isLoading() {
    if (!this.loadedEmail || !this.loadedNotificheFrequenze || !this.loadedAssociate) {
      return true;
    }
    return false;
  }

}
