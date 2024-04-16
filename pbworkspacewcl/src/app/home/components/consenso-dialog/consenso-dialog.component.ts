/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ConsensoInformatoDTO } from '../../commons/vo/consenso-informato-dto';
import { HomeService } from '../../services/home.service';

@Component({
  selector: 'app-consenso-dialog',
  templateUrl: './consenso-dialog.component.html',
  styleUrls: ['./consenso-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ConsensoDialogComponent implements OnInit {

  email: string;
  autorizzato: string;

  @ViewChild("consensoForm", { static: true }) consensoForm: NgForm;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedSalvaConsenso: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<ConsensoDialogComponent>,
    private homeService: HomeService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
  }

  invia() {
    if (this.autorizzato === "S" && (this.consensoForm.form.get('mail').hasError('required') || this.consensoForm.form.get('mail').hasError('pattern'))) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi.")
    } else {
      this.loadedSalvaConsenso = false;
      this.subscribers.salvaConsenso = this.homeService.salvaConsensoInvioComunicazione(this.email, this.autorizzato).subscribe(data => {
        if (data === 1) {
          this.dialogRef.close("OK");
        } else {
          this.showMessageError("Errore nel salvataggio dei dati.");
        }
        this.loadedSalvaConsenso = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedSalvaConsenso = true;
      });
    }
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
    if (!this.loadedSalvaConsenso) {
      return true;
    }
    return false;
  }
}
