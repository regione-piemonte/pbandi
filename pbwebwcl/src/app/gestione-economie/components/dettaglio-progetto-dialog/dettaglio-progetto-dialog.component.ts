/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DatiGeneraliDTO } from '../../commons/dto/dati-generali-dto';

@Component({
  selector: 'app-dettaglio-progetto-dialog',
  templateUrl: './dettaglio-progetto-dialog.component.html',
  styleUrls: ['./dettaglio-progetto-dialog.component.scss']
})
export class DettaglioProgettoDialogComponent implements OnInit {

  type: string;
  datiGenerali: DatiGeneraliDTO;
  cfBeneficiario: string;
  numeroRigheImportiAgevolati: number;
  rowIndexesImportiAgevolati: Array<number>;
  numeroRigheErogazioni: number;
  rowIndexesErogazioni: Array<number>;
  numeroRigheRevoche: number;
  rowIndexesRevoche: Array<number>;
  numeroRigheRecuperi: number
  rowIndexesRecuperi: Array<number>;
  numeroRighePrerecuperi: number;
  rowIndexesPrerecuperi: Array<number>;

  constructor(
    public dialogRef: MatDialogRef<DettaglioProgettoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.type = this.data.type;
    this.datiGenerali = this.data.datiGenerali;
    this.cfBeneficiario = this.data.cfBeneficiario;
    this.numeroRigheImportiAgevolati = this.datiGenerali.importiAgevolati ? this.datiGenerali.importiAgevolati.length / 2 : 0;
    this.rowIndexesImportiAgevolati = new Array<number>();
    for (let i = 0; i < this.numeroRigheImportiAgevolati; i++) {
      this.rowIndexesImportiAgevolati.push(i);
    }
    this.numeroRigheErogazioni = this.datiGenerali.erogazioni ? this.datiGenerali.erogazioni.length / 2 : 0;
    this.rowIndexesErogazioni = new Array<number>();
    for (let i = 0; i < this.numeroRigheErogazioni; i++) {
      this.rowIndexesErogazioni.push(i);
    }
    this.numeroRigheRevoche = this.datiGenerali.revoche ? this.datiGenerali.revoche.length / 2 : 0;
    this.rowIndexesRevoche = new Array<number>();
    for (let i = 0; i < this.numeroRigheRevoche; i++) {
      this.rowIndexesRevoche.push(i);
    }
    this.numeroRigheRecuperi = this.datiGenerali.recuperi ? this.datiGenerali.recuperi.length / 2 : 0;
    this.rowIndexesRecuperi = new Array<number>();
    for (let i = 0; i < this.numeroRigheRecuperi; i++) {
      this.rowIndexesRecuperi.push(i);
    }
    this.numeroRighePrerecuperi = this.datiGenerali.preRecuperi ? this.datiGenerali.preRecuperi.length / 2 : 0;
    this.rowIndexesPrerecuperi = new Array<number>();
    for (let i = 0; i < this.numeroRighePrerecuperi; i++) {
      this.rowIndexesPrerecuperi.push(i);
    }
  }

  close() {
    this.dialogRef.close();
  }

}
