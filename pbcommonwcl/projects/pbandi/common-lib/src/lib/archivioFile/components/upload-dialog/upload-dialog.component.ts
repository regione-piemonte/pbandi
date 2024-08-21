/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { InfoDocumentoVo } from '../../commons/infoDocumento-vo';

@Component({
  selector: 'app-upload-dialog',
  templateUrl: './upload-dialog.component.html',
  styleUrls: ['./upload-dialog.component.scss']
})
export class UploadDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<UploadDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Array<any>
  ) { }

  maxFileSize: number;
  files: any;
  filesLoadible: Array<any>;
  dataSourceTable: MatTableDataSource<InfoDocumentoVo>;
  showButtonCarica = false;

  ngOnInit(): void {
    this.maxFileSize = this.data[1];
    this.files = this.data[0];
    this.dataSourceTable = this.data[2];

    this.filesLoadible = new Array<any>();
    for (var val of this.files) {
      var nomeFileValido = this.validateFileName(val.name);
      if (nomeFileValido) {
        if (!((val.size / 1024) > this.maxFileSize)) {
          this.filesLoadible.push(val);
        }
      }
    }
  }

  fileLoadible(file: any) {
    var nomeFileValido = this.validateFileName(file.name);
    if (nomeFileValido) {
      if ((file.size / 1024) > this.maxFileSize) {
        return "Le dimensioni del file eccedono quelle consentite. Dimensioni massime: " + this.maxFileSize;
      } else {
        var filePresente = false;
        this.dataSourceTable.data.forEach(row => {
          if (row.nome == file.name) {
            filePresente = true;
          }
        });

        if (filePresente) {
          return "Nella cartella selezionata è già presente un file con lo stesso nome";
        } else {
          this.showButtonCarica = true;
          return null;
        }
      }
    } else {
      return "Nome file non valido. Sono ammessi lettere o numeri senza spazi, intervallabili con - o _";
    }
  }

  validateFileName(filename) {
    if (filename == null) {
      return false;
    }
    if (/\s/.test(filename)) {
      return false;
    } else {
      var pattern = /([a-zA-Z0-9]+[-_\.]?)*([a-zA-Z0-9])+\.[a-zA-Z0-9]{3,4}$/g;
      var output = filename.match(pattern);
      if (filename == output) {
        return true;
      } else {
        return false;
      }
    }
  }

  carica() {
    this.dialogRef.close({ data: this.filesLoadible });
  }

  chiudi() {
    this.dialogRef.close({ data: null });
  }
}
