/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { BandoProgettiVO } from '../../commons/vo/bando-progetti-vo';
import { NotificaAlertVO } from '../../commons/vo/notifica-alert-vo';
import { ProgettoNotificheVO } from '../../commons/vo/progetto-notifiche-vo';
import { NotificheViaMailService } from '../../services/notifiche-via-mail.service';

interface FlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

@Component({
  selector: 'app-associa-progetti-dialog',
  templateUrl: './associa-progetti-dialog.component.html',
  styleUrls: ['./associa-progetti-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AssociaProgettiDialog implements OnInit {

  idSoggettoNotifica: number;
  bandiProgetti: Array<BandoProgettiVO>;
  bandiProgettiFiltered: Array<BandoProgettiVO>;
  bando: string = "";
  progetto: string = "";
  allVisible: boolean;
  filteredVisible: boolean;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedBandi: boolean;
  loadedSave: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private notificheViaMailService: NotificheViaMailService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<AssociaProgettiDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idSoggettoNotifica = this.data.idSoggettoNotifica;
    this.loadedBandi = false;
    this.subscribers.bandiProgetti = this.notificheViaMailService.getBandiProgetti(this.idSoggettoNotifica).subscribe(data => {
      if (data) {
        this.bandiProgetti = data;
        this.bandiProgettiFiltered = data.slice();
      }
      this.loadedBandi = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
      this.loadedBandi = true;
      this.showMessageError("Errore nel recupero dei bandi e dei progetti.");
    })
  }

  filterBando() {
    this.progetto = "";
    if (this.bando.length >= 3) {
      this.bandiProgettiFiltered = this.bandiProgetti.filter(b => b.nomeBandoLinea.toLowerCase().includes(this.bando.toLowerCase()));
      this.allVisible = false;
      this.filteredVisible = true;
    } else {
      this.bandiProgettiFiltered = this.bandiProgetti.slice();
      this.allVisible = false;
      this.filteredVisible = false;
    }
  }

  filterProgetto() {
    this.bando = "";
    if (this.progetto.length >= 3) {
      this.bandiProgettiFiltered = this.bandiProgetti.filter(b => {
        for (let p of b.progetti) {
          if (p.codiceVisualizzato.toLowerCase().includes(this.progetto.toLowerCase())
            || p.titoloProgetto.toLowerCase().includes(this.progetto.toLowerCase())) {
            return true;
          }
        }
        return false;
      });
      this.filteredVisible = true;
      this.allVisible = false;
    } else {
      this.bandiProgettiFiltered = this.bandiProgetti.slice();
      this.allVisible = false;
      this.filteredVisible = false;
    }
  }

  viewAll() {
    this.bandiProgettiFiltered = this.bandiProgetti.slice();
    this.bando = "";
    this.progetto = "";
    this.allVisible = true;
    this.filteredVisible = false;
  }

  selectAll(progrBandoLineaIntervento: number) {
    let bp = this.bandiProgetti.find(b => b.progrBandoLineaIntervento === progrBandoLineaIntervento);
    for (let p of bp.progetti) {
      if (bp.allSelected) {
        p.isAssociated = true;
      } else {
        p.isAssociated = false;
      }
    }
  }

  salva() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedSave = false;
    let progettiChecked = new Array<number>();
    for (let b of this.bandiProgetti) {
      b.progetti.filter(p => p.isAssociated === true).forEach(p => {
        progettiChecked.push(p.idProgetto);
      });
    }
    let notificaAlert = new NotificaAlertVO("", null, "", null, null, progettiChecked, this.idSoggettoNotifica, null);
    this.subscribers.associate = this.notificheViaMailService.associateProgettiToNotifica(notificaAlert).subscribe(data => {
      this.loadedSave = true;
      this.showMessageSuccess("Salvataggio avvenuto correttamente.");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedSave = true;
      this.showMessageError("Errore nell'associazione dei progetti alla notifica.");
    })
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
    if (!this.loadedSave || !this.loadedBandi) {
      return true;
    }
    return false;
  }

}
