/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { GenericSelectVo } from '../../commons/generic-select-vo';
import { GestioneImpegniService } from '../../services/gestione-impegni.service';

@Component({
  selector: 'app-importa-impegno-dialog',
  templateUrl: './importa-impegno-dialog.component.html',
  styleUrls: ['./importa-impegno-dialog.component.scss']
})
export class ImportaImpegnoDialogComponent implements OnInit {

  // Dichiarazioni variabili
  annoEsercizioSelected: string;
  anniEsercizio: Array<string>;
  annoImpegno: string;
  nImpegno: string;
  user: UserInfoSec;
  idSoggetto: string;

  // loaaded
  loadedCercaAnniEsercizioValidi: boolean;
  loadedAcquisisciImpegno: boolean;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Array<any>,
    private _snackBar: MatSnackBar,
    private gestioneImpegniService: GestioneImpegniService,
    public dialogRef: MatDialogRef<ImportaImpegnoDialogComponent>
  ) { }

  ngOnInit(): void {
    this.user = this.data[0];
    this.idSoggetto = this.data[1];

    this.anniEsercizio = new Array<string>();
    this.loadedCercaAnniEsercizioValidi = true;
    this.gestioneImpegniService.cercaAnniEsercizioValidi(this.user).subscribe(data => {
      this.anniEsercizio = data;
      this.annoEsercizioSelected = this.anniEsercizio[0];
      this.loadedCercaAnniEsercizioValidi = false;
    }, err => {
      this.loadedCercaAnniEsercizioValidi = false;
      this.openSnackBar("Errore in fase di caricamento degli anni esercizio");
    });
  }

  importa() {
    this.loadedAcquisisciImpegno = true;
    this.gestioneImpegniService.acquisisciImpegno(this.user, this.annoEsercizioSelected, this.annoImpegno, this.nImpegno, this.idSoggetto).subscribe(data => {
      this.loadedAcquisisciImpegno = false;
      if (data.esito == true) {
        this.dialogRef.close({ data: true });
      } else {
        this.dialogRef.close({ data: false });
      }
    }, err => {
      this.loadedAcquisisciImpegno = false;
      this.dialogRef.close({ data: false });
    });
  }

  annulla() {
    this.dialogRef.close({ data: null });
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  isLoading() {
    if (this.loadedCercaAnniEsercizioValidi || this.loadedAcquisisciImpegno) {
      return true;
    } else {
      return false;
    }
  }
}
