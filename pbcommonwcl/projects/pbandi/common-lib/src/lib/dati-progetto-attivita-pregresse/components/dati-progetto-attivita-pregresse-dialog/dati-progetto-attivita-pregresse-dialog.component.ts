/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Router, ActivatedRoute } from '@angular/router';
import { AfterViewChecked, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { DatiProgettoAttivitaPregresseService } from '../../services/dati-progetto-attivita-pregresse.service';
import { DatiGenerali } from '../../commons/dto/dati-generali';
import { AttivitaPregresseDTO } from '../../commons/dto/attivita-pregresse-dto';
import { saveAs } from "file-saver-es";
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AutoUnsubscribe } from './../../../shared/decorator/auto-unsubscribe';
import { HandleExceptionService } from './../../../core/services/handle-exception.service';
import { ArchivioFileService } from '../../../archivioFile/archivio-file-api';

@Component({
  selector: 'app-dati-progetto-attivita-pregresse-dialog',
  templateUrl: './dati-progetto-attivita-pregresse-dialog.component.html',
  styleUrls: ['./dati-progetto-attivita-pregresse-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DatiProgettoAttivitaPregresseDialogComponent implements OnInit, AfterViewChecked {

  idProgetto: number;
  apiURL: string;

  datiGenerali: DatiGenerali;
  attivitaPregresse: Array<AttivitaPregresseDTO>;
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

  @ViewChild('tabs', { static: false }) tabs;

  //MESSAGE
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedDatiGenerali: boolean;
  loadedAttivitaPregresse: boolean;
  loadedDownload: boolean = true;
  loadedDownloadReport: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private datiProgettoAttivitaPregresseService: DatiProgettoAttivitaPregresseService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<DatiProgettoAttivitaPregresseDialogComponent>,
    public archivioFileService: ArchivioFileService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idProgetto = this.data.idProgetto;
    this.apiURL = this.data.apiURL;

    this.loadData();
  }

  loadData() {
    this.loadedDatiGenerali = false;
    this.subscribers.datiGenerali = this.datiProgettoAttivitaPregresseService.getDatiGenerali(this.apiURL, this.idProgetto).subscribe(data => {
      console.log(data);
      if (data) {
        this.datiGenerali = data;
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
      this.loadedDatiGenerali = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDatiGenerali = true;
    });
    this.loadedAttivitaPregresse = false;
    this.subscribers.attivitaPregresse = this.datiProgettoAttivitaPregresseService.getAttivitaPregresse(this.apiURL, this.idProgetto).subscribe(data => {
      console.log(data);
      if (data) {
        this.attivitaPregresse = data;
      }
      this.loadedAttivitaPregresse = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedAttivitaPregresse = true;
    });
  }

  scarica(attivita: AttivitaPregresseDTO) {
    if (attivita.idDocumentoIndex) {
      this.loadedDownload = false;
      this.subscribers.download = this.archivioFileService.leggiFile(this.apiURL, attivita.idDocumentoIndex).subscribe(res => {
        if (res) {
          saveAs(res, attivita.nomeDocumento);
          this.messageSuccess = "Download avvenuto con successo.";
          this.isMessageSuccessVisible = true;
        }
        this.loadedDownload = true;
      }, error => {
        this.handleExceptionService.handleNotBlockingError(error);
        this.loadedDownload = true;
        this.messageError = "Errore nel download.";
        this.isMessageErrorVisible = true;
      });
    }
    if (attivita.idDocumentoIndexReport) {
      this.loadedDownloadReport = false;
      this.subscribers.download = this.archivioFileService.leggiFile(this.apiURL, attivita.idDocumentoIndexReport).subscribe(res => {
        if (res) {
          saveAs(res, attivita.nomeDocumentoReport);
          this.messageSuccess = "Download avvenuto con successo.";
          this.isMessageSuccessVisible = true;
        }
        this.loadedDownloadReport = true;
      }, error => {
        this.handleExceptionService.handleNotBlockingError(error);
        this.loadedDownloadReport = true;
        this.messageError = "Errore nel download.";
        this.isMessageErrorVisible = true;
      });
    }
  }

  isLoading() {
    if (!this.loadedAttivitaPregresse || !this.loadedDatiGenerali || !this.loadedDownload || !this.loadedDownloadReport) {
      return true;
    }
    return false;
  }

  ngAfterViewChecked() {
    this.tabs.realignInkBar();
  }

  close() {
    this.dialogRef.close();
  }
}
