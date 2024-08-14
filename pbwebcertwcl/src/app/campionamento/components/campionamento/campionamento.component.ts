/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { CodiceDescrizione } from 'src/app/shared/commons/dto/codice-descrizione';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { CertificazioneService } from 'src/app/shared/services/certificazione.service';
import { saveAs } from "file-saver-es";
import { ReportCampionamentoDTO } from '../../commons/dto/report-campionamento-dto';
import { MatTableDataSource } from '@angular/material/table';
import { MatTabGroup } from '@angular/material/tabs';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-campionamento',
  templateUrl: './campionamento.component.html',
  styleUrls: ['./campionamento.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CampionamentoComponent implements OnInit, AfterViewChecked {

  normative: Array<CodiceDescrizione>;
  normativaSelected: CodiceDescrizione;
  anniContabili: Array<CodiceDescrizione>;
  annoContabileSelected: CodiceDescrizione;
  elencoReport: Array<ReportCampionamentoDTO>;

  dataSource: MatTableDataSource<ReportCampionamentoDTO>;
  displayedColumns: string[] = ['documento', 'normativa', 'annocontabile', 'data', 'azioni'];
  noElementsMessage: string = "Non ci sono elementi da visualizzare.";
  noElementsMessageVisible: boolean;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedNormative: boolean;
  loadedAnniContabili: boolean;
  loadedEstrai: boolean = true;
  loadedDownload: boolean = true;
  loadedCerca: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  @ViewChild("tab", { static: false }) tab: MatTabGroup;


  constructor(
    private certificazioneService: CertificazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.loadedNormative = false;
        this.subscribers.normative = this.certificazioneService.getLineeInterventoNormative(false).subscribe(data => {
          if (data) {
            this.normative = data;
            this.normative.unshift(new CodiceDescrizione("0", ""));
          }
          this.loadedNormative = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
        this.loadedAnniContabili = false;
        this.subscribers.anniContabili = this.certificazioneService.getAnniContabili().subscribe(data => {
          if (data) {
            this.anniContabili = data;
            this.anniContabili.unshift(new CodiceDescrizione("0", ""));
          }
          this.loadedAnniContabili = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedAnniContabili = true;
        });
      }
    });
  }

  onSelectNormativa() {
    if (this.normativaSelected.codice === "0") {
      this.normativaSelected = null;
    }
  }

  onSelectAnnoContabile() {
    if (this.annoContabileSelected.codice === "0") {
      this.annoContabileSelected = null;
    }
  }

  estrai() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedEstrai = false;
    this.subscribers.estrai = this.certificazioneService.eseguiEstrazioneCampionamento(+this.normativaSelected.codice).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.loadedDownload = false;
          this.subscribers.downloadFile = this.certificazioneService.getContenutoDocumentoById(data.idDocumentoIndex).subscribe(res => {
            if (res) {
              saveAs(res, data.nomeDocumento);
              this.showMessageSuccess(data.messagge);
            }
            this.loadedDownload = true;
          }, error => {
            this.handleExceptionService.handleNotBlockingError(error);
            this.loadedDownload = true;
            this.showMessageError("Errore nel download dell'estrazione.");
          });

        } else {
          this.showMessageError(data.messagge);
        }
      }
      this.loadedEstrai = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedEstrai = true;
      this.showMessageError("Errore nell'estrazione del campionamento.");
    });
  }

  cerca() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedCerca = false;
    this.subscribers.cerca = this.certificazioneService.getElencoReportCampionamento(this.normativaSelected != null ? this.normativaSelected.codice : null,
      this.annoContabileSelected != null ? this.annoContabileSelected.codice : null).subscribe(data => {
        if (data) {
          this.elencoReport = data;
          this.dataSource = new MatTableDataSource<ReportCampionamentoDTO>();
          this.dataSource.data = this.elencoReport;
          if (data.length > 0) {
            this.noElementsMessageVisible = false;
          } else {
            this.noElementsMessageVisible = true;
          }
        }
        this.loadedCerca = true;
        setTimeout(() => { this.tab.selectedIndex = 1; }, 0.1 * 1000);
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedCerca = true;
        this.showMessageError("Errore nella ricerca.");
        setTimeout(() => { this.tab.selectedIndex = 1; }, 0.1 * 1000);
      });

  }

  downloadAllegato(idDocumentoIndex: number, nomeDocumento: string) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downloadFile = this.certificazioneService.getContenutoDocumentoById(idDocumentoIndex).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      this.loadedDownload = true;
      setTimeout(() => { this.tab.selectedIndex = 1; }, 0.1 * 1000);
    }, error => {
      this.handleExceptionService.handleNotBlockingError(error);
      this.loadedDownload = true;
      this.showMessageError("Errore nel download del file.")
      setTimeout(() => { this.tab.selectedIndex = 1; }, 0.1 * 1000);
    });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  clickTab() {
    this.normativaSelected = null;
    this.annoContabileSelected = null;
  }

  isLoading() {
    if (!this.loadedNormative || !this.loadedAnniContabili || !this.loadedEstrai || !this.loadedDownload || !this.loadedCerca) {
      return true;
    }
    return false;
  }

  ngAfterViewChecked() {
    this.tab.realignInkBar();
  }

  /* NON NECESSARIO: scarica excel riepilogativo della tabella
  scaricaExcel(elencoReport: Array<ReportCampionamentoDTO>) {
    this.excelService.scaricaExcelCampionamento(elencoReport);
  }
  */
}
