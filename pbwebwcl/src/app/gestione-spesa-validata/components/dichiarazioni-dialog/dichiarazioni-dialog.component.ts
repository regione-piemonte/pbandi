/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { saveAs } from 'file-saver-es';
import { GestioneSpesaValidataService } from '../../services/gestione-spesa-validata.service';
import { DichiarazioneSpesaValidataDTO } from '../../commons/dto/dichiarazione-spesa-validata-dto';
import { MatTableDataSource } from '@angular/material/table';
import { DichiarazioneDiSpesaService } from 'src/app/rendicontazione/services/dichiarazione-di-spesa.service';
import { AllegatiDichiarazioneSpesaDialogComponent } from 'src/app/validazione/components/allegati-dichiarazione-spesa-dialog/allegati-dichiarazione-spesa-dialog.component';
import { ArchivioFileService } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { DocumentoIndexDTO } from 'src/app/documenti-progetto/commons/dto/documento-index-dto';
import { AllegatiDichiarazioneDiSpesaDTO } from 'src/app/rendicontazione/commons/dto/allegati-dichiarazione-di-spesa-dto';

@Component({
  selector: 'app-dichiarazioni-dialog',
  templateUrl: './dichiarazioni-dialog.component.html',
  styleUrls: ['./dichiarazioni-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DichiarazioniDialogComponent implements OnInit {

  readonly idUtenteMigrazioneFinpis = Constants.ID_UTENTE_MIGRAZIONE_FINPIS;

  idDocumentoDiSpesa: number;
  idProgetto: number;
  dichiarazioni: Array<DichiarazioneSpesaValidataDTO>;

  displayedColumns: string[] = ['azioni', 'numero', 'tipologia', 'data', 'note'];
  dataSource: MatTableDataSource<DichiarazioneSpesaValidataDTO>;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedDich: boolean;
  loadedDownload: boolean = true;
  loadedAllegati: boolean = true;
  loadedIsFP: boolean = true;
  loadedCostante: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<DichiarazioniDialogComponent>,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private gestioneSpesaValidataService: GestioneSpesaValidataService,
    private dichiarazioneDiSpesaService: DichiarazioneDiSpesaService,
    private userService: UserService,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idDocumentoDiSpesa = this.data.idDocumentoDiSpesa;
    this.idProgetto = this.data.idProgetto;
    this.loadedDich = false;
    this.subscribers.dichiarazioni = this.gestioneSpesaValidataService.dichiarazioniDocumentoDiSpesa(this.idDocumentoDiSpesa, this.idProgetto).subscribe(data => {
      if (data) {
        this.dichiarazioni = data;
        this.dataSource = new MatTableDataSource<DichiarazioneSpesaValidataDTO>(this.dichiarazioni);
      }
      this.loadedDich = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError;
      this.showMessageError("Errore infase di caricamento.");
      this.loadedDich = true;
    });
  }

  downloadAllegato(idDocumentoIndex: string) {
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFileConNome(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        let nome = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nome);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  openAllegatiDichiarazione(idDichiarazioneDiSpesa: number) {
    if (this.data?.isVisible || !this.data?.isFP) {
      this.dichiarazioneDiSpesaService.getAllegatiDichiarazioneDiSpesaIntegrazioni(this.idProgetto, idDichiarazioneDiSpesa).subscribe(data => {
        if (data) {
          this.visualizzaAllegatiDS(data);
        }
        this.loadedAllegati = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nel recupero degli allegati.");
        this.loadedAllegati = true;
      });
    } else {
      this.dichiarazioneDiSpesaService.getAllegatiDichiarazioneDiSpesa(this.idProgetto, idDichiarazioneDiSpesa).subscribe(data => {
        if (data) {
          this.visualizzaAllegatiDS(data);
        }
        this.loadedAllegati = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nel recupero degli allegati.");
        this.loadedAllegati = true;
      });
    }
  }

  visualizzaAllegatiDS(allegati: AllegatiDichiarazioneDiSpesaDTO[]) {
    this.dialog.open(AllegatiDichiarazioneSpesaDialogComponent,
      {
        width: '50%',
        maxHeight: '90%',
        data: {
          allegati: allegati,
          title: "Allegati alla dichiarazione di spesa"
        }
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
    if (!this.loadedDich || !this.loadedDownload || !this.loadedAllegati) {
      return true;
    }
    return false;
  }

  close() {
    this.dialogRef.close();
  }
}
