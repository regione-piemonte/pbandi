/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from '@angular/common';
import { Component, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatAccordion } from '@angular/material/expansion';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { AnteprimaDialogComponent, ArchivioFileService, DocumentoAllegatoDTO, HandleExceptionService, InfoDocumentoVo } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { AllegatiContestazioniVO } from 'src/app/gestione-contestazioni/commons/dto/allegatiContestazione';
import { DocumentoIndexVO } from 'src/app/gestione-controlli/commons/documento-index-vo';
import { IterAutorizzativiService } from '../../services/iter-autorizzativi-service';
import { saveAs } from 'file-saver-es';
@Component({
  selector: 'app-allegati-iter',
  templateUrl: './allegati-iter.component.html',
  styleUrls: ['./allegati-iter.component.scss']
})
export class AllegatiIterComponent implements OnInit {
  subscribers: any = {};
  idProgetto: number;
  idBandoLinea: number;
  isLoading = false;
  messageError: string;
  isMessageErrorVisible: boolean;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;


  nomeFile: string;
  nomeFileSecond: string;
  allegatiSecond: Array<File> = new Array<File>();
  file: File;
  fileSecond: File;
  idTarget: number;
  showError: boolean = false;
  showSucc: boolean = false;
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  allegatiDistinta: any = [];
  allegati;
  isSubmitted = false;
  motivazioni: Array<Object> = [];
  allegatiDisponibili: boolean;
  dichiarazioneDispesa = false;
  distinta = false;
  loadedDownload: boolean = true;
  @Input()
  count: any;
  @ViewChild('myaccordion') myPanels: MatAccordion;
  public tipo_elaborazione = this.data.dataKey.tipo_elaborazione;
  idFunzionario: number;
  errore = null;
  message;
  constructor(private iterAutorizzativiService: IterAutorizzativiService,
    private archivioFileService: ArchivioFileService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public datepipe: DatePipe,
    private handleExceptionService: HandleExceptionService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private configService: ConfigService,
    private dialog: MatDialog,
    private router: Router) {

  }

  ngOnInit(): void {
    this.getAllegatiIter();

  }

  getAllegatiIter() {
    // this.loadedAllegati = false;
    this.subscribers.allegati = this.iterAutorizzativiService.getAllegatiIter(this.data.dataKey.idWorkFlow).subscribe(data => {
      if (data.length > 0) {
        this.allegatiDisponibili = true;
        for (let a of data) {
          if(a.idProgetto) {
            this.allegatiDistinta.push(Object.assign({}, a))
          } else if (a.flagEntita === 'I') {
            this.letteraAccompagnatoria.push(Object.assign({}, a))
          } else {
            this.allegatiGenerici.push(Object.assign({}, a))
          }
          if (a.flagExcel === true) {
            if (a.tipoExcel === 1) {
              this.dichiarazioneDispesa = true;
            } else if (a.tipoExcel === 2) {
              this.distinta = true;
            }
          }
        }
        this.ordinaAllegatiDistinta();
      } else {
        this.allegati = new Array<DocumentoAllegatoDTO>();
        this.allegatiDisponibili = false;
      }
      //  this.loadedAllegati = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  ordinaAllegatiDistinta() {
    let listaProgetti = [];
    this.allegatiDistinta.forEach(element => {
      let presente = false;
      listaProgetti.forEach(progetto => {
        if(progetto.idProgetto == element.idProgetto){
          presente = true;
          progetto.listaAllegati.push(element);
        }
      })
      if(!presente){
        let progetto = {
          idProgetto: element.idProgetto,
          codiceVisualizzato: element.codiceVisualizzato,
          listaAllegati: [] 
        };
        progetto.listaAllegati.push(element);
        listaProgetti.push(progetto);
      }
    });
    this.allegatiDistinta = listaProgetti;
    console.log(listaProgetti)
  }


  scaricaFileExcelSpesa(file: any) {
    this.iterAutorizzativiService.getExcelIterDichiarazioneDiSpesa(file.idTargetExcel)
      .subscribe(data => {
        var byteArray = new Uint8Array(data);
        var blob = new Blob([byteArray], { type: 'application/xls' });
        const a = document.createElement('a')
        const objectUrl = URL.createObjectURL(blob)
        a.href = objectUrl
        a.download = 'DettaglioSpesa.xlsx';
        a.click();
        URL.revokeObjectURL(objectUrl);
      })

  }

  scaricaFileExcelDistinta(file: any) {
    this.iterAutorizzativiService.getExcelIterDistinte(file.idTargetExcel)
      .subscribe(data => {
        var byteArray = new Uint8Array(data);
        var blob = new Blob([byteArray], { type: 'application/xls' });
        const a = document.createElement('a')
        const objectUrl = URL.createObjectURL(blob)
        a.href = objectUrl
        a.download = 'DettaglioDistinta.xlsx';
        a.click();
        URL.revokeObjectURL(objectUrl);
      })
  }

  anteprimaReportDettaglio(file: any) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.iterAutorizzativiService.reportDettaglioDocumentoDiSpesa(file.idTargetExcel).subscribe(res => {
      //console.log(res.headers.get("header-nome-file"));
      //console.log(res.body.type + "  -  " + res.body.size);
      let nomeFile = res.headers.get("header-nome-file");
      saveAs(new Blob([res.body]), nomeFile);
      /*if (res) {
        saveAs(res, "reportValidazione" + this.idDichiarazioneDiSpesa + ".xls");
      }*/
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
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  scaricaFile(idDocumentoIndex: string) {
    this.loadedDownload = false;
    this.archivioFileService.leggiFileConNome(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
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


  anteprimaAllegato(lettera: AllegatiContestazioniVO) {
    this.resetMessageError();
    let comodo = new Array<any>();
    comodo.push(lettera.nomeFile); //nome del file
    comodo.push(lettera.idDocumentoIndex);
    comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(lettera.codTipoDoc, lettera.nomeFile, null, null, null, null, null, lettera.idDocumentoIndex, null)]));
    comodo.push(this.configService.getApiURL());
    comodo.push(lettera.codTipoDoc); //dalla pbandi_t_documenti_index e pbandi_c_tipo_documento_index

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });

  }

  isAnteprimaVisible(nomeFile: string) { //duplicato di isFileWithPreview dentro AnteprimaDialogComponent
    const splitted = nomeFile.split(".");
    if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "PDF" || splitted[splitted.length - 1] == "xml" || splitted[splitted.length - 1] == "XML" || splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M") {
      return true;
    } else {
      return false;
    }
  }


  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }



}
