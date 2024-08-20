/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SharedService } from 'src/app/shared/services/shared.service';
import { DocumentoAllegato } from '../../commons/dto/documento-allegato';
import { saveAs } from "file-saver-es";
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { MatTableDataSource } from '@angular/material/table';
import { AnteprimaDialogComponent, InfoDocumentoVo } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { AffidamentiService } from '../../services/affidamenti.service';
import { AllegatiChecklistDialogComponent } from '../allegati-checklist-dialog/allegati-checklist-dialog.component';

@Component({
  selector: 'app-documenti-generati-dialog',
  templateUrl: './documenti-generati-dialog.component.html',
  styleUrls: ['./documenti-generati-dialog.component.scss']
})
export class DocumentiGeneratiDialogComponent implements OnInit {

  documenti: Array<DocumentoAllegato>;
  dataSourceTable: MatTableDataSource<InfoDocumentoVo>;
  idProgetto: number;


  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedDownload: boolean = true;
  loadedAllegati: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private sharedService: SharedService,
    private handleExceptionService: HandleExceptionService,
    private configService: ConfigService,
    private affidamentiService: AffidamentiService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<DocumentiGeneratiDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.documenti = this.data.documenti;
    this.dataSourceTable = new MatTableDataSource(this.documenti.map(a => new InfoDocumentoVo(null, a.nome, null, a.sizeFile.toString(), true, null, null, a.id.toString(), null)));
    this.idProgetto = this.data.idProgetto;
  }

  downloadDoc(allegato: DocumentoAllegato) {
    this.loadedDownload = false;
    this.subscribers.download = this.sharedService.getContenutoDocumentoById(allegato.id).subscribe(res => {
      if (res) {
        saveAs(res, allegato.nome);
        this.resetMessageSuccess();
        this.resetMessageError();

        this.showMessageSuccess("Download avvenuto con successo.", 5);
      }
      this.loadedDownload = true;
    }, error => {
      this.handleExceptionService.handleNotBlockingError(error);
      this.loadedDownload = true;
      this.resetMessageSuccess();
      this.resetMessageError();

      this.showMessageError("Errore nel download.");
    });
  }

  visualizzaAnteprima(doc: DocumentoAllegato) {
    this.resetMessageError();
    let comodo = new Array<any>();
    comodo.push(doc.nome);
    comodo.push(doc.id);
    comodo.push(this.dataSourceTable);
    comodo.push(this.configService.getApiURL());

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });
  }

  openAllegati(doc: DocumentoAllegato, messageSuccess?: string) {
    this.loadedAllegati = false;
    this.affidamentiService.allegatiVerbaleChecklist(+doc.id).subscribe(data => {
      let dialogRef = this.dialog.open(AllegatiChecklistDialogComponent,
        {
          width: '50%',
          maxHeight: '90%',
          data: {
            allegati: data,
            title: "Documenti allegati alla Checklist",
            nascondiSizeFile: true,
            idProgetto: this.idProgetto,
            idDocumentoIndexChecklist: doc.id,
            codTipoChecklist: doc.descBreveTipoDocIndex,
            messageSuccess: messageSuccess
          }
        });
      dialogRef.afterClosed().subscribe(res => {
        if (res && res.length > 0) {
          this.openAllegati(doc, res);
        }
      });
      this.loadedAllegati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel recupero degli allegati.");
      this.loadedAllegati = true;
    });
  }

  showMessageSuccess(msg: string, interval: number) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  close() {
    this.dialogRef.close();
  }

  isLoading() {
    if (!this.loadedDownload || !this.loadedAllegati) {
      return true;
    }
    return false;
  }
  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

}
