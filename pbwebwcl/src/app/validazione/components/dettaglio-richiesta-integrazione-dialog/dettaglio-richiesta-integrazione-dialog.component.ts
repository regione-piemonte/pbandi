/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { saveAs } from 'file-saver-es';
import { AnteprimaDialogComponent, ArchivioFileService, InfoDocumentoVo } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DocIntegrRendDTO } from 'src/app/gestione-integrazioni/commons/dto/doc-integr-rend-dto';
import { Constants } from 'src/app/core/commons/util/constants';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-dettaglio-richiesta-integrazione-dialog',
  templateUrl: './dettaglio-richiesta-integrazione-dialog.component.html',
  styleUrls: ['./dettaglio-richiesta-integrazione-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DettaglioRichiestaIntegrazioneDialogComponent implements OnInit {

  title: string;
  motivo: string;
  letteraAccompagnatoria: DocIntegrRendDTO;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedDownload: boolean = true;

  constructor(
    public dialogRef: MatDialogRef<DettaglioRichiestaIntegrazioneDialogComponent>,
    private dialog: MatDialog,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.title = this.data.title;
    this.motivo = this.data.motivo;
    this.letteraAccompagnatoria = this.data.letteraAccompagnatoria;
  }

  anteprimaLettera() {
    this.resetMessageError();
    let comodo = new Array<any>();
    comodo.push(this.letteraAccompagnatoria.nomeFile);
    comodo.push(this.letteraAccompagnatoria.idDocumentoIndex);
    comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(Constants.DESC_BREVE_TIPO_DOC_INDEX_LETTERA_ACC_RICH_INTEG_RENDIC,
      this.letteraAccompagnatoria.nomeFile, null, null, null, null, null, this.letteraAccompagnatoria.idDocumentoIndex.toString(), null)]));
    comodo.push(this.configService.getApiURL());
    comodo.push(Constants.DESC_BREVE_TIPO_DOC_INDEX_LETTERA_ACC_RICH_INTEG_RENDIC);

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });
  }

  downloadAllegato(idDocumentoIndex: string, nomeDocumento: string) {
    this.resetMessageError();
    this.loadedDownload = false;
    this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedDownload) {
      return true;
    }
    return false;
  }

  close() {
    this.dialogRef.close();
  }
}
