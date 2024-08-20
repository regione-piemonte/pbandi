/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-conferma-invio-verifica-dialog',
  templateUrl: './conferma-invio-verifica-dialog.component.html',
  styleUrls: ['./conferma-invio-verifica-dialog.component.scss']
})
export class ConfermaInvioVerificaDialogComponent implements OnInit {

  oggetto: string;
  esisteAllegatoNonInviato: boolean;

  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    public dialogRef: MatDialogRef<ConfermaInvioVerificaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.oggetto = this.data.oggetto;
    this.esisteAllegatoNonInviato = this.data.esisteAllegatoNonInviato;
    if (!this.esisteAllegatoNonInviato) {
      let error: string = "ATTENZIONE! NON È STATO AGGIUNTO ALCUN NUOVO ALLEGATO. DOPO LA VERIFICA INTERMEDIA, INSERIRE ED INVIARE AL CONTROLLO I DOCUMENTI DELL'ESECUZIONE";
      this.showMessageError(error);
    }
  }

  conferma() {
    this.dialogRef.close("CONFERMA");
  }

  close() {
    this.dialogRef.close();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

}
