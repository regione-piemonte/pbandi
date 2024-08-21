/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-rinomina-dialog',
  templateUrl: './rinomina-dialog.component.html',
  styleUrls: ['./rinomina-dialog.component.scss']
})
export class RinominaDialogComponent implements OnInit {

  constructor(
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<RinominaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Array<string>
  ) { }

  tipo = "";
  nomeCartellaFile = "";
  
  ngOnInit(): void {
    this.tipo = this.data[0];
    this.nomeCartellaFile = this.data[1];
  }

  rinomina() {
    if (this.nomeCartellaFile == "") {
      this.openSnackBar("Si prega di inserire un nome");
    } else {
      if (this.nomeCartellaFile == this.data[1]) {
        this.dialogRef.close({ data: null });
      } else {
        if (this.tipo == "Cartella") {
          if (this.nomeCartellaFile.length > 55) {
            this.openSnackBar("Il nome cartella non deve superare 55 caratteri");
          } else {
            if (this.nomeCartellaFile.length < 2) {
              this.openSnackBar("Sono ammessi un minimo di 2 caratteri");
            } else {
              if (this.validateFolderName(this.nomeCartellaFile)) {
                this.dialogRef.close({ data: this.nomeCartellaFile });
              } else {
                this.openSnackBar("Nome cartella non valido. Sono ammessi un minimo di due caratteri (lettere o numeri) senza spazi, intervallabili con - o _ ");
              }
            }
          }
        } else {
          if (this.nomeCartellaFile.length > 55) {
            this.openSnackBar("Il nome file non deve superare 55 caratteri");
          } else {
            if (this.nomeCartellaFile.length < 5) {
              this.openSnackBar("Nome file non valido. Sono ammessi un minimo di 5 caratteri (lettere o numeri) senza spazi, intervallabili con - o _");
            } else {
              if (this.validateFileName(this.nomeCartellaFile)) {
                this.dialogRef.close({ data: this.nomeCartellaFile });
              } else {
                this.openSnackBar("Nome file non valido. Sono ammessi lettere o numeri senza spazi, intervallabili con - o _");
              }
            }
          }
        }
      }
    }
  }

  annulla() {
    this.dialogRef.close({ data: null });
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
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

  validateFileName(filename) {
    if (filename == null) {
      return false;
    }
    var pattern = /([a-zA-Z0-9]+[-_\.]?)*([a-zA-Z0-9])+\.[a-zA-Z0-9]{3,4}$/g;
    var output = filename.match(pattern);
    if (filename == output)
      return true;
    else
      return false;
  }
}
