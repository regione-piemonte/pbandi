/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { saveAs } from 'file-saver-es';
import { DocumentoAllegatoDTO } from 'src/app/rendicontazione/commons/dto/documento-allegato-dto';
import { DocumentoAllegatoPagamentoDTO } from 'src/app/validazione/commons/dto/documento-allegato-pagamento-dto';
import { ArchivioFileService } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';

@Component({
  selector: 'app-allegati-dialog',
  templateUrl: './allegati-dialog.component.html',
  styleUrls: ['./allegati-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AllegatiDialogComponent implements OnInit {
  //VENIVA USATO IN RETTIFICA DOCUMENTO DI SPESA PRIMA CHE VENISSE RESO UGUALE ALLA GRAFICA DI ESAME DOCUMENTO IN VALIDAZIONE
  allegatiDoc: Array<DocumentoAllegatoDTO>;
  allegatiQuietanze: Array<DocumentoAllegatoPagamentoDTO>;
  allegatiNoteDiCredito: Array<DocumentoAllegatoDTO>;
  allegatiFornitore: Array<DocumentoAllegatoDTO>;
  quietanze: Array<any> = new Array<any>();

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedDownload: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<AllegatiDialogComponent>,
    private archivioFileService: ArchivioFileService,
    private handleExceptionService: HandleExceptionService,
    private configService: ConfigService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }
  ngOnInit(): void {
    this.allegatiDoc = this.data.allegatiDoc;
    this.allegatiQuietanze = this.data.allegatiQuietanze;
    if (this.allegatiQuietanze && this.allegatiQuietanze.length > 0) {
      this.allegatiQuietanze.forEach(a => {
        if (!this.quietanze.find(q => q.idPagamento === a.idPagamento)) {
          this.quietanze.push({
            idPagamento: a.idPagamento, descrizioneModalitaPagamento: a.descrizioneModalitaPagamento, dtPagamento: a.dtPagamento,
            importoPagamento: a.importoPagamento, allegati: this.allegatiQuietanze.filter(all => all.idPagamento === a.idPagamento)
          });
        }
      });
    }
    this.allegatiNoteDiCredito = this.data.allegatiNoteDiCredito;
    this.allegatiFornitore = this.data.allegatiFornitore;
  }

  downloadAllegato(idDocumentoIndex: string, nomeDocumento: string) {
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
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
