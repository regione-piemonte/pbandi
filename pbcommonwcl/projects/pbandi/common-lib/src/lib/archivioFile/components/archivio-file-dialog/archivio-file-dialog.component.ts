/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { UserInfoSec } from '../../../core/commons/dto/user-info';
import { DocumentoAllegatoDTO } from '../../commons/dto/documento-allegato-dto';
import { InfoDocumentoVo } from '../../commons/infoDocumento-vo';

@Component({
  selector: 'app-archivio-file-dialog',
  templateUrl: './archivio-file-dialog.component.html',
  styleUrls: ['./archivio-file-dialog.component.scss']
})
export class ArchivioFileDialogComponent implements OnInit {

  apiURL: string;
  user: UserInfoSec;
  drawerExpanded: Observable<boolean>;
  allegatiDaSalvare: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();
  allegati: Array<DocumentoAllegatoDTO>;
  onlyOneFile: boolean;

  constructor(
    public dialogRef: MatDialogRef<ArchivioFileDialogComponent>,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.apiURL = this.data.apiURL;
    this.user = this.data.user;
    this.drawerExpanded = this.data.drawerExpanded;
    this.onlyOneFile = this.data.onlyOneFile;
    if (this.data.allegatiDaSalvare) {
      this.allegatiDaSalvare = this.data.allegatiDaSalvare;
    }
    if (this.data.allegati) {
      this.allegati = this.data.allegati;
    }
  }

  allegaFiles(event: InfoDocumentoVo[]) {
    if (this.onlyOneFile && event.length > 1) {
      this.openSnackBar("E' possibile selezionare solamente un file");
      return;
    }
    let message: string = "Documenti già associati in precedenza: ";
    let found: boolean;
    for (let a of event) {
      if (this.allegatiDaSalvare.find(all => all.idDocumentoIndex === a.idDocumentoIndex) !== undefined
        || (this.allegati && this.allegati.find(all => all.id === +a.idDocumentoIndex)) !== undefined) {
        message += a.nome + ", ";
        found = true;
      }
      else {
        this.allegatiDaSalvare.push(a);
      }
    }
    if (found) {
      message = message.substr(0, message.length - 2);
      this.openSnackBar(message);
    }
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  close(event: boolean) {
    if (event) {
      this.dialogRef.close(this.allegatiDaSalvare);
    }
  }

}
