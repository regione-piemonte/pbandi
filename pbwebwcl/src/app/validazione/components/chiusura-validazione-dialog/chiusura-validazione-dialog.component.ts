/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { AppaltoProgetto } from '../../commons/dto/appalto-progetto';

const warningImportoMaggiore: string = "Attenzione!<br />Confermando l&rsquo;operazione, almeno una voce di spesa del conto economico risulter&agrave; validata per un importo complessivo maggiore del totale ammesso per la voce di spesa. <br />Pur essendo possibile procedere, si consiglia di visionare il conto economico.";

@Component({
  selector: 'app-chiusura-validazione-dialog',
  templateUrl: './chiusura-validazione-dialog.component.html',
  styleUrls: ['./chiusura-validazione-dialog.component.scss']
})
export class ChiusuraValidazioneDialogComponent implements OnInit {

  note: string;
  checkDsIntegrativaVisibile: boolean;
  checkDsIntegrativaSelezionatoENonModificabile: boolean;
  appalti: Array<AppaltoProgetto>;
  consentiDichirazioneIntegrativa: boolean = true;

  dataSource: MatTableDataSource<AppaltoProgetto>;
  displayedColumns: string[] = ['check', 'desc']

  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageInfo: string = "";
  isMessageInfoVisible: boolean;

  constructor(public dialogRef: MatDialogRef<ChiusuraValidazioneDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.note = this.data.dataInit.note;
    this.checkDsIntegrativaVisibile = this.data.dataInit.checkDsIntegrativaVisibile;
    this.checkDsIntegrativaSelezionatoENonModificabile = this.data.dataInit.checkDsIntegrativaSelezionatoENonModificabile;
    if (!this.checkDsIntegrativaVisibile) {
      this.consentiDichirazioneIntegrativa = false;
    }

    if (this.data.dataInit.messaggi && this.data.dataInit.messaggi.length > 0) {
      this.data.dataInit.messaggi.forEach(m => {
        this.messageInfo += m + "<br/>";
      });
      this.messageInfo = this.messageInfo.substr(0, this.messageInfo.length - 5);
      this.isMessageInfoVisible = true;
    }
    if (this.data.dataInit.warningImportoMaggioreAmmesso) {
      this.showMessageWarning(warningImportoMaggiore);
    }
    this.appalti = this.data.dataInit.appalti;
    if (this.appalti && this.appalti.length > 0) {
      this.dataSource = new MatTableDataSource<AppaltoProgetto>(this.appalti);
    }

  }

  changeAllCheckbox(event: MatCheckboxChange) {
    if (event.checked) {
      this.appalti.forEach(d => d.associato = true);
    } else {
      this.appalti.forEach(d => d.associato = false);
    }
  }

  continua() {
    let appaltiSelezionati: Array<AppaltoProgetto> = this.appalti && this.appalti.length > 0 ? this.appalti.filter(a => a.associato) : null;
    this.dialogRef.close({
      noteChiusura: this.note,
      dsIntegrativaConsentita: this.consentiDichirazioneIntegrativa,
      idAppaltiSelezionati: appaltiSelezionati ? appaltiSelezionati.map(a => a.idAppalto) : null
    });
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  close() {
    this.dialogRef.close();
  }

}
