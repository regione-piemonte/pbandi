/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { NgForm } from "@angular/forms";
import { MatDialog } from '@angular/material/dialog';
import { CronoprogrammaFasiService } from '../../services/cronoprogramma-fasi.service';
import { CronoprogrammaFasiItem } from '../../commons/dto/cronoprogramma-fasi-item';
import { CronoprogrammaFasiItemInterface } from '../../commons/dto/cronoprogramma-fasi-item-interface';
import { CronoprogrammaListFasiItemAllegati } from '../../commons/dto/cronoprogramma-list-fasi-item-allegati';
import { CronoprogrammaListFasiItemAllegatiInterface } from '../../commons/dto/cronoprogramma-list-fasi-item-allegati-interface';
import { saveAs } from 'file-saver-es';
import { AllegatiCronoprogrammaFasi } from '../../commons/dto/allegati-cronoprogramma-fasi';
import { AutoUnsubscribe } from '../../../shared/decorator/auto-unsubscribe';
import { ConfermaDialog } from '../../../shared/components/conferma-dialog/conferma-dialog';
import { GetStringDialog } from '../../../shared/components/get-string-dialog/get-string-dialog.component';
import { ArchivioFileService, ArchivioFileDialogComponent, InfoDocumentoVo, DocumentoAllegatoDTO } from '../../../archivioFile/archivio-file-api';
import { UserInfoSec, HandleExceptionService } from '../../../core/core-api';
import { of } from 'rxjs';
import { CodiceDescrizioneDTO } from '../../../visualizza-conto-economico/visualizza-conto-economico-api';

@Component({
  selector: 'app-cronoprogramma-fasi',
  templateUrl: './cronoprogramma-fasi.component.html',
  styleUrls: ['./cronoprogramma-fasi.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CronoprogrammaFasiComponent implements OnInit, OnChanges {

  @Input("apiURL") apiURL: string;
  @Input('user') user: UserInfoSec;
  @Input('idProgetto') idProgetto: number;
  @Input('idBandoLinea') idBandoLinea: number;
  @Input('isBR58a') isBR58a: boolean;
  @Input('isBR58b') isBR58b: boolean;
  @Input('isInvioDichiarazione') isInvioDichiarazione: boolean;
  @Input('salCorrente') salCorrente: CodiceDescrizioneDTO;

  //DATI PROGETTO
  idDomanda: number;

  codiceProgetto: string;
  dataPresentazione: Date;

  idOperazione: number;
  isReadOnly: boolean;

  today: Date;

  //DATI CRONOPROGRAMMA
  dataCronoprogramma: Array<CronoprogrammaListFasiItemAllegati>;
  @ViewChild("cronoForm", { static: true }) cronoForm: NgForm;

  isErrorPrevista: boolean;
  isErrorEffettiva: boolean;

  //DATA TABELLA
  displayedColumns: string[] = [];
  displayedColumnsInit: string[] = ['fase'];
  displayedColumnsToAddBr58a: string[] = ['dataLimite'];
  displayedColumnsToAdd: string[] = ['dataPrevista', 'dataEffettiva', 'motivo'];
  dataSourceList: Array<MatTableDataSource<CronoprogrammaFasiItem>>
  fasiProgetto: Array<string>

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: Array<string> = new Array<string>();
  isMessageErrorVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedDataPresentazione: boolean;

  loadedDataCronoprogramma: boolean;
  loadedSaveDataCronoprogramma: boolean = true;
  loadedDownload: boolean = true;
  loadedDisassociaAllegato: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};


  constructor(
    private handleExceptionService: HandleExceptionService,
    private cronoprogrammaFasiService: CronoprogrammaFasiService,
    private archivioFileService: ArchivioFileService,
    private dialog: MatDialog,
  ) {
  }

  ngOnInit(): void {
    this.today = new Date();
    this.today.setHours(0, 0, 0, 0);
  }


  ngOnChanges(changes: SimpleChanges): void {
    if (this.user && changes?.user?.currentValue && this.user?.codiceRuolo && this.user?.beneficiarioSelezionato) {
      this.loadData();
    }

    if (changes.isBR58a?.currentValue != null && changes.isBR58a?.currentValue != undefined) {
      this.displayedColumns = this.displayedColumnsInit;
      if (this.isBR58a) {
        this.displayedColumns.push(...this.displayedColumnsToAddBr58a);
      }
      this.displayedColumns.push(...this.displayedColumnsToAdd);
    }
  }

  loadData() {
    this.loadedCodiceProgetto = false;
    this.cronoprogrammaFasiService.getCodiceProgetto(this.apiURL, this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });
    this.loadedDataPresentazione = false;
    this.cronoprogrammaFasiService.getDtPresentazioneDomanda(this.apiURL, this.idProgetto).subscribe(res => {
      if (res) {
        this.dataPresentazione = new Date(res);
        console.log(this.dataPresentazione)
      }
      this.loadedDataPresentazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDataPresentazione = true;
    });

    this.getDataCronoprogramma();
  }

  //////////////////////////   ERRORI    ////////////////////////////////////////

  // resetta gli errori

  resetMessage() {
    this.messageError = null;
    this.messageSuccess = null;
    this.isMessageErrorVisible = false;
    this.isMessageSuccessVisible = false;
  }

  // resetta gli errori e motra il messaggio di errore 
  showMessageError(msg: Array<string>) {
    this.resetMessage()
    if (msg != undefined && msg != null) {
      this.messageError = msg;
    }
    else {
      this.messageError = ["Error"];
    }
    this.isMessageErrorVisible = true;

    //this.vps.scrollToAnchor("sclrollId"); // non funziona

    //const element = document.querySelector('#scrollId2'); // non funziona
    var element = document.getElementById('scrollId2')
    element.scrollIntoView();
  }

  // resetta gli errori e motra il messaggio di successo
  showMessageSucces(msg: string) {
    this.resetMessage()
    if (msg != undefined && msg != null) {
      this.messageSuccess = msg;
    }
    else {
      this.messageSuccess = "Succes";
    }
    this.isMessageSuccessVisible = true;

    //this.vps.scrollToAnchor("sclrollId"); // non funziona
    //const element = document.querySelector('#scrollId2'); // non funziona
    var element = document.getElementById('scrollId2')
    element.scrollIntoView();

  }


  //////////////////////////   METODI    ////////////////////////////////////////

  goToDatiProgettoEAttivitaPregresse() { console.log("Non ancora implementato") }

  goToContoEconomico() { console.log("Non ancora implementato") }

  goToScleltaBeneficiari() { console.log("Non ancora implementato") }

  checkErrori() {
    if (this.isErrorPrevista || this.isErrorEffettiva) {
      this.showMessageError(["Sono presenti degli errori, non è possibile proseguire con il salvataggio."]);
      return true;
    }
    let dataCr = this.dataCronoprogramma?.find(d => d.fasiItemList?.find(f => f.faseIterAttiva && f.requireMotivoScostamento && !f.motivoScostamento?.length));
    if (dataCr) {
      this.showMessageError(["Inserire il motivo scostamento dove necessario."]);
      return true;
    }
    dataCr = this.dataCronoprogramma?.find(d => d.fasiItemList?.find(f => f.faseIterAttiva && f.requireMotivoScostamento && f.motivoScostamento?.length));
    let fasiAttiveConAllegati = this.dataCronoprogramma?.filter(d => d.fasiItemList?.find(f => f.faseIterAttiva) && d.allegatiList?.length);
    if (!fasiAttiveConAllegati?.length && dataCr) {
      this.showMessageError(["In caso di scostamento tra le date occorre inserire un allegato che giustifichi tale scostamento."]);
      return true;
    }
    return false;
  }

  datiCronoprogrammaOld: Array<CronoprogrammaListFasiItemAllegati>;
  salva() {
    this.resetMessage();
    if (this.checkErrori()) {
      return;
    }

    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi il salvataggio dei dati?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.datiCronoprogrammaOld = new Array<CronoprogrammaListFasiItemAllegati>();
        for (let data of this.dataCronoprogramma) {
          this.datiCronoprogrammaOld.push(Object.assign({}, data));
        }
        this.dataCronoprogramma = new Array<CronoprogrammaListFasiItemAllegati>();
        let dataSource: MatTableDataSource<CronoprogrammaFasiItem>

        for (dataSource of this.dataSourceList) {
          let temp = new CronoprogrammaListFasiItemAllegati()
          temp.fasiItemList = dataSource.data;
          this.dataCronoprogramma.push(temp)
        }
        console.log(this.dataCronoprogramma)
        this.saveDataCronoprogramma()
      }
    });
  }

  onChangeDate(row: CronoprogrammaFasiItem, inputDataPiker: Date, j: number, i: number) {
    // aggiorno la data modificata, la data prevista o effettiva della stessa riga e le date delle righe successive
    // in modo che vengano ripetuti i controlli sugli errori
    this.onChangeDatePrevista(row, inputDataPiker, j, i);
    this.onChangeDateEffettiva(row, inputDataPiker, j, i);
    for (let z = i + 1; z < this.dataCronoprogramma[j].fasiItemList?.length; z++) {
      this.onChangeDatePrevista(this.dataCronoprogramma[j].fasiItemList[z], this.dataCronoprogramma[j].fasiItemList[z]?.dataPrevista, j, z);
      this.onChangeDateEffettiva(this.dataCronoprogramma[j].fasiItemList[z], this.dataCronoprogramma[j].fasiItemList[z]?.dataEffettiva, j, z);
    }
    if (j + 1 < this.dataCronoprogramma?.length) {
      // se sono in fase preliminare, la fase successiva potrebbe esserer abilitata, in quel caso ricalcolo gli errori
      let faseSucc = this.dataCronoprogramma[j + 1]?.fasiItemList;
      if (faseSucc?.length && faseSucc[0]?.faseIterAttiva) {
        for (let z = 0; z < this.dataCronoprogramma[j + 1].fasiItemList?.length; z++) {
          this.onChangeDatePrevista(this.dataCronoprogramma[j + 1].fasiItemList[z], this.dataCronoprogramma[j + 1].fasiItemList[z]?.dataPrevista, j + 1, z);
          this.onChangeDateEffettiva(this.dataCronoprogramma[j + 1].fasiItemList[z], this.dataCronoprogramma[j + 1].fasiItemList[z]?.dataEffettiva, j + 1, z);
        }
      }
    }
  }

  onChangeDatePrevista(row: CronoprogrammaFasiItem, inputDataPiker: Date, j: number, i: number) {

    console.log("data!!!: ", this.dataCronoprogramma);
    console.log(this.cronoForm.form);
    this.cronoForm.form.markAllAsTouched();
    let temp: string;

    //parti precedente non compilate
    if (i > 0 && inputDataPiker != null && this.dataCronoprogramma[j].fasiItemList?.find((f, index) => !f.dataPrevista && index < i)) {
      temp = 'Compilare prima le parti precedenti';
      row.requireMotivoScostamento = false;
    }
    else if (j - 1 >= 0 && inputDataPiker != null && this.dataCronoprogramma[j - 1].fasiItemList.length > 0
      && this.dataCronoprogramma[j - 1].fasiItemList?.find(f => !f.dataPrevista || !f.dataEffettiva)) {
      temp = 'Compilare prima le parti precedenti';
      row.requireMotivoScostamento = false;
    }


    // se temp e' undefined continuo coi prossimi check altrimenti lancio l'errore
    if (!temp) {
      let dataPrevistaAttPrec: Date;
      let dataEffettivaMaxFasePrec: Date;
      if (i == 0 && j > 0) {
        let fasiPrec = this.dataCronoprogramma[j - 1].fasiItemList;
        dataEffettivaMaxFasePrec = new Date(Math.max.apply(null, fasiPrec?.map(f => (f.dataEffettiva))));
      } else if (i > 0 && this.dataCronoprogramma[j]?.fasiItemList[i - 1]?.dataPrevista) {
        dataPrevistaAttPrec = this.dataCronoprogramma[j].fasiItemList[i - 1].dataPrevista;
      }

      if (inputDataPiker != undefined) {

        if (!row.dataPrevista && row.dataEffettiva) {
          temp = "La data prevista è obbligatoria se la data effettiva è presente";
          row.requireMotivoScostamento = false;
          // } else if (row.dataLimite && row.dataPrevista?.getTime() > row.dataLimite?.getTime()) {
          //   temp = "La data prevista deve essere minore o uguale alla data prevista dal D.M.";
          //   row.requireMotivoScostamento = false;
        } else if (!row.dataLimite && row.dataPrevista?.getTime() < this.dataPresentazione?.getTime()) {
          temp = "La data prevista deve essere successiva alla data di presentazione della domanda";
          row.requireMotivoScostamento = false;
          // } else if (row.dataPrevista?.getTime() > row.dataEffettiva?.getTime()) {
          //   temp = "La data prevista deve essere minore o uguale alla data effettiva";
          //   row.requireMotivoScostamento = false;
        } else if (i > 0 && row.dataPrevista?.getTime() < dataPrevistaAttPrec?.getTime()) {
          temp = "La data prevista deve essere uguale o successiva alla data prevista dell'attività precedente";
          row.requireMotivoScostamento = false;
        } else if (i == 0 && j > 0 && row.dataPrevista?.getTime() < dataEffettivaMaxFasePrec?.getTime()) {
          temp = "La data prevista deve essere uguale o successiva alla data effettiva massima della fase precedente";
          row.requireMotivoScostamento = false;
        } else if (row.dataEffettiva?.getTime() > row.dataPrevista?.getTime()) {
          row.warnDataEffettiva = "Specificare il motivo dello scostamento tra la data prevista e la data effettiva";
          row.requireMotivoScostamento = true;
        }
      }
    }
    this.isErrorPrevista = false;
    if (temp) {
      console.log(j, i, temp)
      row.errorDataPrevista = temp;
      this.cronoForm.form.get('dataPrevista' + j + '-' + i).setErrors({ error: 'errors' });
      this.isErrorPrevista = true;
    }
    else {
      this.cronoForm.form.get('dataPrevista' + j + '-' + i).setErrors(null);
      row.edit = true;
    }

  }



  onChangeDateEffettiva(row: CronoprogrammaFasiItem, inputDataPiker: Date, j: number, i: number) {
    this.cronoForm.form.markAllAsTouched();
    row.warnDataEffettiva = null;

    let temp: string;

    if (i > 0 && inputDataPiker != null && !this.dataCronoprogramma[j].fasiItemList[i - 1].dataPrevista?.getTime()
      && !this.dataCronoprogramma[j].fasiItemList[i - 1].dataEffettiva?.getTime()) {
      temp = 'compilare prima le parti precedenti'
      row.requireMotivoScostamento = false;
    }
    else if (j - 1 >= 0 && inputDataPiker != null && this.dataCronoprogramma[j - 1].fasiItemList.length > 0 &&
      !this.dataCronoprogramma[j - 1].fasiItemList[this.dataCronoprogramma[j - 1].fasiItemList.length - 1].dataEffettiva?.getTime()) {
      temp = 'compilare prima le parti precedenti'
      row.requireMotivoScostamento = false;
    }


    if (!temp) {
      let dataEffettivaAttPrec: Date;
      if (i > 0 && this.dataCronoprogramma[j]?.fasiItemList[i - 1]?.dataEffettiva) {
        dataEffettivaAttPrec = this.dataCronoprogramma[j].fasiItemList[i - 1].dataEffettiva;
      }

      if (inputDataPiker != undefined && inputDataPiker != null) {
        if (!row.dataPrevista?.getTime()) {
          temp = "Valorizzare prima la data prevista";
          row.requireMotivoScostamento = false;
          // } else if (row.dataPrevista?.getTime() > row.dataEffettiva?.getTime()) {
          //   temp = "La data effettiva deve essere maggiore uguale alla data prevista";
          //   row.requireMotivoScostamento = false;
          // } else if (!row.dataLimite && row.dataEffettiva?.getTime() < this.dataPresentazione?.getTime()) {
          //   temp = "La data effettiva deve essere successiva alla data di presentazione della domanda";
          //   row.requireMotivoScostamento = false;
        } else if (row.dataEffettiva?.getTime() > this.today.getTime()) {
          temp = "La data effettiva deve essere minore o uguale alla data odierna";
          row.requireMotivoScostamento = false;
        } else if (i > 0 && row.dataEffettiva?.getTime() < dataEffettivaAttPrec?.getTime()) {
          temp = "La data effettiva deve essere uguale o successiva alla data effettiva dell'attività precedente";
          row.requireMotivoScostamento = false;
        } else if (row.dataEffettiva?.getTime() > row.dataPrevista?.getTime()) {
          row.warnDataEffettiva = "Specificare il motivo dello scostamento tra la data prevista e la data effettiva";
          row.requireMotivoScostamento = true;
        } else if (row.dataLimite && row.dataEffettiva?.getTime() > row.dataLimite?.getTime()) {
          row.warnDataEffettiva = "Specificare il motivo dello scostamento tra la data definita del D.M. e la data effettiva";
          row.requireMotivoScostamento = true;
        }
        // else if (row.dataPrevista?.getTime() < row.dataEffettiva?.getTime()) {
        //   row.requireMotivoScostamento = true;
        // }
        else if (row.dataPrevista?.getTime() <= row.dataEffettiva?.getTime()) {
          row.requireMotivoScostamento = false;
        }
      }

    }

    this.isErrorEffettiva = false;
    if (temp != undefined) {
      row.errorDataEffettiva = temp;
      this.cronoForm.form.get('dataEffettiva' + j + '-' + i).setErrors({ error: 'errors' });

      this.isErrorEffettiva = true;
    }
    else {
      this.cronoForm.form.get('dataEffettiva' + j + '-' + i).setErrors(null);
      row.edit = true;
    }

  }




  checkDate(): boolean {

    let i = 0;
    let j = 0;
    while (i < this.dataCronoprogramma.length) {
      let row = this.dataCronoprogramma[i];
      j = 0;
      while (j < row.fasiItemList.length) {

        console.log(this.cronoForm)
        console.log('dataPrevista' + i.toString() + j.toString())
        this.cronoForm.form.get('dataPrevista' + i.toString() + j.toString()).setErrors({ error: 'format' });
        this.cronoForm.form.get('dataPrevista' + i.toString() + j.toString()).setErrors({ error: 'format' });
        if (!row.fasiItemList[j].errorDataEffettiva || !row.fasiItemList[j].dataPrevista) {
          return false
        }
        j++;
      }
      i++;
    }

    return true;
  }

  onClickMotivoScostamento(row: CronoprogrammaFasiItem) {
    if (row.dataPrevista && row.dataEffettiva && row.dataPrevista?.getTime() < row.dataEffettiva?.getTime()) {
      let msgDialog = this.dialog.open(GetStringDialog, {
        width: '800px',
        data: { message: "Inserire il motivo dello scostamento" },
      });

      msgDialog.afterClosed().subscribe(result => {
        if (result) {
          row.motivoScostamento = result;
          row.edit = true;
        }
      });
    }
  }

  onClickEditMotivoScostamento(row: CronoprogrammaFasiItem) {
    let msgDialog = this.dialog.open(GetStringDialog, {
      width: '800px',
      data: { message: "Inserire il motivo dello scostamento", value: row.motivoScostamento },
    });

    msgDialog.afterClosed().subscribe(result => {
      if (result) {
        row.motivoScostamento = result;
        let temp = row.motivoScostamento.replace(" ", "")
        if (temp == "") {
          row.motivoScostamento = undefined;
        }

        row.edit = true;
      }
    });
  }

  aggiungiAllegati(index: number) {
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegatiDaSalvare: this.dataCronoprogramma[index].allegatiList?.filter(a => !a.associato)?.map(a =>
          new InfoDocumentoVo(null, a.nomeFile, null, null, false, null, null, a.idDocumentoIndex.toString(), a.idFolder?.toString())),
        allegati: this.dataCronoprogramma[index].allegatiList?.filter(a => a.associato)?.map(a =>
          new DocumentoAllegatoDTO(null, null, a.idDocumentoIndex, null, null, null, null, a.nomeFile, null, null, null, true)),
        apiURL: this.apiURL,
        user: this.user,
        drawerExpanded: of(false)
      }
    });
    dialogRef.afterClosed().subscribe((res: InfoDocumentoVo[]) => {
      if (res && res.length > 0) {
        for (let a of res) {
          let all = new AllegatiCronoprogrammaFasi();
          all.idDocumentoIndex = +a.idDocumentoIndex;
          all.idFolder = +a.idFolder;
          all.nomeFile = a.nome;
          all.associato = false;
          all.idIter = this.dataCronoprogramma[index]?.fasiItemList[0]?.idIter;
          all.idProgetto = this.idProgetto;
          this.dataCronoprogramma[index].allegatiList.push(all);
        }
      }
    });
  }

  rimuoviAllegato(index: number, allegato: AllegatiCronoprogrammaFasi) {
    if (!allegato.associato) {
      this.dataCronoprogramma[index].allegatiList?.splice(this.dataCronoprogramma[index].allegatiList.findIndex(a => a.idDocumentoIndex === allegato.idDocumentoIndex), 1);
    } else {
      this.resetMessage();
      this.loadedDisassociaAllegato = false;
      this.cronoprogrammaFasiService.disassociaAllegato(this.apiURL, allegato.idDocumentoIndex, allegato.idProgettoIter,
        this.idProgetto).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.showMessageSucces("Allegato disassociato con successo.");
              this.loadAllegatiIterProgetto(allegato.idIter, index);
            } else {
              this.showMessageError([data.messaggio]);
            }
          }
          this.loadedDisassociaAllegato = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError(["Errore nella disassociazione del file."]);
          this.loadedDisassociaAllegato = true;
        });
    }
  }

  loadAllegatiIterProgetto(idIter: number, index: number) {
    this.loadedDataCronoprogramma = false;
    this.cronoprogrammaFasiService.getAllegatiIterProgetto(this.apiURL, idIter, this.idProgetto).subscribe(data => {
      if (data) {
        this.dataCronoprogramma[index].allegatiList = data;
      }
      this.loadedDataCronoprogramma = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(["Errore nel caricamento degli allegati"]);
      this.loadedDataCronoprogramma = true;
    });
  }

  //////////////////////////   SERVICE    ////////////////////////////////////////

  getDataCronoprogramma() {
    this.loadedDataCronoprogramma = false;
    this.cronoprogrammaFasiService.getDataCronoprogramma(this.apiURL, this.idProgetto).subscribe(res => {
      if (res) {

        console.log("dataCronoprogramma ", res)
        this.dataCronoprogramma = CronoprogrammaFasiItem.createStructure(res);


        this.dataSourceList = new Array<MatTableDataSource<CronoprogrammaFasiItem>>();
        let i = 0
        while (i < this.dataCronoprogramma.length) {
          let temp = new MatTableDataSource<CronoprogrammaFasiItem>();
          temp.data = this.dataCronoprogramma[i].fasiItemList;


          this.dataSourceList.push(temp)
          i++;
        }


        //console.log(this.dataCronoprogramma[0][0].dataLimite )
        //console.log("datasource", this.dataSourceList[0].data )
        console.log(this.cronoForm.form);
      }
      console.log(this.dataSourceList)
      this.loadedDataCronoprogramma = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDataCronoprogramma = true;
    });
  }

  saveDataCronoprogramma() {
    let data: CronoprogrammaListFasiItemAllegatiInterface[] = this.transformData();
    this.loadedSaveDataCronoprogramma = false;
    this.cronoprogrammaFasiService.saveDataCronoprogramma(this.apiURL, this.idProgetto, data).subscribe(res => {
      if (res.code === "OK") {
        if (data.find(d => d.allegatiList?.length)) {
          if (res.esitoAssociaFilesDTO) {
            if (res.esitoAssociaFilesDTO.elencoIdDocIndexFilesAssociati && res.esitoAssociaFilesDTO.elencoIdDocIndexFilesAssociati.length > 0
              && (!res.esitoAssociaFilesDTO.elencoIdDocIndexFilesNonAssociati || res.esitoAssociaFilesDTO.elencoIdDocIndexFilesNonAssociati.length === 0)) { //tutti associati
              this.messageSuccess = res.message;
            } else if (res.esitoAssociaFilesDTO.elencoIdDocIndexFilesNonAssociati && res.esitoAssociaFilesDTO.elencoIdDocIndexFilesNonAssociati.length > 0
              && (!res.esitoAssociaFilesDTO.elencoIdDocIndexFilesAssociati || res.esitoAssociaFilesDTO.elencoIdDocIndexFilesAssociati.length === 0)) { //tutti non associati
              this.messageSuccess = res.message;
              this.messageError = ["Errore nell'associazione degli allegati."];
            } else if (res.esitoAssociaFilesDTO.elencoIdDocIndexFilesAssociati && res.esitoAssociaFilesDTO.elencoIdDocIndexFilesAssociati.length > 0
              && res.esitoAssociaFilesDTO.elencoIdDocIndexFilesNonAssociati && res.esitoAssociaFilesDTO.elencoIdDocIndexFilesNonAssociati.length > 0) { //alcuni associati e alcuni non associati
              let message = "Errore nell'associazione dei seguenti allegati: ";
              res.esitoAssociaFilesDTO.elencoIdDocIndexFilesNonAssociati.forEach(id => {
                let array: AllegatiCronoprogrammaFasi[] = [];
                for (let data of this.datiCronoprogrammaOld) {
                  array.push(...data.allegatiList);
                }
                let allegato = array.find(a => a.idDocumentoIndex === id);
                message += allegato.nomeFile + ", ";
              });
              message = message.substring(0, message.length - 3);
              this.messageSuccess = res.message;
              this.messageError = [message];
            }

            if (this.messageSuccess?.length) {
              this.showMessageSucces(this.messageSuccess);
            }
            if (this.messageError?.length) {
              this.showMessageError(this.messageError);
            }
          } else {
            this.showMessageError([res.message]);
          }
        } else {
          this.showMessageSucces(res.message);
        }
        this.getDataCronoprogramma();
      } else {
        this.showMessageError([res.message]);
      }
      this.loadedSaveDataCronoprogramma = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedSaveDataCronoprogramma = true;
      console.log(err)
    });
  }

  transformData(): Array<CronoprogrammaListFasiItemAllegatiInterface> {
    let data: Array<CronoprogrammaListFasiItemAllegatiInterface> = new Array<CronoprogrammaListFasiItemAllegatiInterface>()
    for (let row of this.dataCronoprogramma) {
      let newRow: CronoprogrammaListFasiItemAllegatiInterface = new CronoprogrammaListFasiItemAllegatiInterface();
      data.push(newRow);
      for (let col of row.fasiItemList) {
        let newCol: CronoprogrammaFasiItemInterface;
        if (col.edit) {
          //newCol = col as CronoprogrammaFasiItemInterface;
          newCol = CronoprogrammaFasiItem.trasfornToInterface(col);
          newRow.fasiItemList.push(newCol);
          console.log(newCol)
        }
      }

    }
    for (let i = 0; i < data.length; i++) {
      data[i].allegatiList = this.datiCronoprogrammaOld[i].allegatiList;
    }

    return data;
  }

  downloadAllegato(allegato: AllegatiCronoprogrammaFasi) {
    this.resetMessage();
    this.loadedDownload = false;
    this.archivioFileService.leggiFile(this.apiURL, allegato.idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, allegato.nomeFile);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      let errors: Array<string>;
      errors.push("Errore in fase di download del file.");
      this.showMessageError(errors);
      this.loadedDownload = true;
    });
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedDataCronoprogramma || !this.loadedSaveDataCronoprogramma || !this.loadedDownload
      || !this.loadedDisassociaAllegato || !this.loadedDataPresentazione) {
      return true;
    }
    return false;
  }

}

