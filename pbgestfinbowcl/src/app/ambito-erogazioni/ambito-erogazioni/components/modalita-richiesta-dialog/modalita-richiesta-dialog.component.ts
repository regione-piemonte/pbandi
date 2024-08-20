/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ControlliPreErogazioneResponseService } from '../../services/controlli-pre-erogazione-response.service';

@Component({
  selector: 'app-modalita-richiesta-dialog',
  templateUrl: './modalita-richiesta-dialog.component.html',
  styleUrls: ['./modalita-richiesta-dialog.component.scss']
})
export class ModalitaRichiestaDialogComponent implements OnInit {

  val :string = "S";

  constructor(
    private resService: ControlliPreErogazioneResponseService,
     private dialogRef: MatDialogRef<ModalitaRichiestaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      numeroDomanda: string,
      codiceFiscale: string,
      codiceBando: string,
      codiceProgetto: string
    }
  ) { }

  ngOnInit(): void {
  }

  closeDialog() {}

  conferma() {
    this.resService.inviaRichiesta(3, this.data.numeroDomanda, this.val, this.data.codiceFiscale, this.data.codiceBando, this.data.codiceProgetto).subscribe(data => {
      console.log("Esito invia richiesta: " + data);

        let objJson = {
          "res": data,
          "modalitaRichiesta": this.val
        }
        this.dialogRef.close(objJson);

    }, err => {

      console.log("Siamo nella merda");
      console.error(err);

    });
  }

}
