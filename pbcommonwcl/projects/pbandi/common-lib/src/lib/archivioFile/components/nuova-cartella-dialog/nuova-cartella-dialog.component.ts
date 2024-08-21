/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-nuova-cartella-dialog',
  templateUrl: './nuova-cartella-dialog.component.html',
  styleUrls: ['./nuova-cartella-dialog.component.scss']
})
export class NuovaCartellaDialogComponent implements OnInit {

  constructor(
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<NuovaCartellaDialogComponent>
  ) { }

  nomeCartella = "";

  ngOnInit(): void {

  }

  creaCartella() {
    if (this.nomeCartella == "") {
      this.openSnackBar("Si prega di inserire un nome");
    } else {
      if (this.validateFolderName(this.nomeCartella)) {
        this.dialogRef.close({ data: this.nomeCartella });
      } else {
        this.openSnackBar("Usare un nome valido. Sono ammessi solo lettere, numeri, - e _ senza spazi");
      }
    }
  }

  annulla() {
    this.dialogRef.close({ data: null });
  }

  validateFolderName(name) {
    if (name == null || name.trim().length == 0) {
      return false;
    }
    var pattern = /^[\w,-]+/g;
    var output = name.match(pattern);
    if (name == output)
      return true;
    else
      return false;
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }
}
