/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewInit, Component, OnInit, ViewChild, Inject } from '@angular/core';
import { RicBenResponseService } from '../../services/ricben-response-service.service';
import { Constants } from '../../../core/commons/util/constants';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { EstrattoContoDTO } from '../../commons/dto/estratto-conto';
import * as XLSX from 'xlsx';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Workbook } from "exceljs";
import * as FileSaver from 'file-saver'
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
    selector: 'dialog-estratto-conto',
    templateUrl: 'dialog-estratto-conto.component.html',
    styleUrls: ['dialog-estratto-conto.component.scss'],
})

@AutoUnsubscribe({ objectName: 'subscribers' })
export class DialogEstrattoConto implements OnInit, AfterViewInit {
  @ViewChild(MatSort) sort: MatSort;

  /* Componente di provenienza
  1 - monitoraggio crediti;
  2 - gestione garanzie;
  3 - provvedimento di revoca;
  4 - proposte variazioni stato credito */
  componentFrom: number;

  isLoading: boolean = true;
  noData: boolean = true;

  public subscribers: any = {};

  // Dettaglio beneficiario visualizzato
  dispBando: string;
  dispCodProg: string;
  dispBenef: string;
  dispDebitoResInit: number = 0;
  dispDebitoResAllaData: number = 0;

  isGaranziaPura: boolean = false;

  // Per chiamata 
  user: UserInfoSec;
  idUtente: string;
  idProgetto: string;
  ndg: number = 0;
  idModalitaAgevolazione: number;
  idModalitaAgevolazioneRif: number;


  displayedColumns: string[] = ['dataContabile', 'dataValuta', 'dataScadenza', 'numRata', 'desCausale', 'importoTotale', "importoCapitale", "importoInteressi", "importoMora", "importoOneri", "dispAgevolazione"];

  estrattoConto: Array<EstrattoContoDTO>;
  dataSource: MatTableDataSource<EstrattoContoDTO> = new MatTableDataSource<EstrattoContoDTO>([]);


  // Messages
  showError: boolean = false;
  errorMsg: string = "";
  //showSucc: boolean = false;
  //succMsg: string = "";
  

  constructor(
    private resService: RicBenResponseService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private shServ: SharedService,
    public dialogRef: MatDialogRef<DialogEstrattoConto>,
    @Inject(MAT_DIALOG_DATA) public data: {
        componentFrom: number,

        bando: string,
        codProgetto: string,
        beneficiario: string,

        idProgetto: string,
        ndg: number,
        idModalitaAgevolazione: string,
        idModalitaAgevolazioneRif: string,
      }
  ) {
    console.log("Parametri passati alla dialog Estratto conto: ", data);

    /* Componente di provenienza
    1 - monitoraggio crediti;
    2 - gestione garanzie;
    3 - provvedimento di revoca;
    4 - proposte variazioni stato credito */
    this.componentFrom = data.componentFrom;

    this.dispBando = data.bando;
    this.dispCodProg = data.codProgetto;
    this.dispBenef = data.beneficiario;

    this.idProgetto = data.idProgetto;
    this.ndg = data.ndg;
    this.idModalitaAgevolazione = +data.idModalitaAgevolazione;
    this.idModalitaAgevolazioneRif = +data.idModalitaAgevolazioneRif;

    // Per Garanzie - Se la garanzia è pura, nascondo le colonne 'Interessi pagati' e 'Interessi mora'
    if(this.componentFrom == 2 && this.idModalitaAgevolazione == 10) {
      this.displayedColumns = this.displayedColumns.filter(column => column !== 'importoInteressi' && column !== 'importoMora');
      this.isGaranziaPura = true;
    }
    
  }


  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = String(this.user.idUtente);

        this.getTopServiceData();
      }

    }, err => {
        this.isLoading = false
        this.handleExceptionService.handleBlockingError(err);
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }



  getTopServiceData() {
    this.isLoading = true;

    this.isLoading = true;
    this.subscribers.estrattoConto = this.resService.getEstrattoConto(this.idUtente, this.idProgetto, this.dispCodProg, this.ndg, this.idModalitaAgevolazione, this.idModalitaAgevolazioneRif).subscribe(data => {

      if (data && data?.infoContabili?.length > 0) {
        this.noData = false;

        this.estrattoConto = data.infoContabili;

        // Valorizzo il dettaglio dell'agevolazione, invece dell'id che riceviamo visualizziamo l'agevolazione corrispondente
        this.estrattoConto.forEach (item => {
          item.dispAgevolazione = this.toAgevolazioneFromId(item.idAgevolazione);

          item.importoTotale = this.toSignedNumber(item.importoTotale, item.segno);
          item.importoCapitale = this.toSignedNumber(item.importoCapitale, item.segno);
          item.importoInteressi = this.toSignedNumber(item.importoInteressi, item.segno);
          item.importoMora = this.toSignedNumber(item.importoMora, item.segno);
          item.importoOneri = this.toSignedNumber(item.importoOneri, item.segno);
        });

        this.dataSource = new MatTableDataSource<EstrattoContoDTO>(this.estrattoConto);
        this.dataSource.sort = this.sort;

        // Valorizzo importi debito residuo
        this.dispDebitoResInit = this.estrattoConto[0].debitoResiduoIniziale
        this.dispDebitoResAllaData = this.setDebitoResAllaData(this.estrattoConto); // - OLD - this.estrattoConto[this.estrattoConto.length - 1].importoTotale;

        this.isLoading = false;
      } else {
        this.noData = true
        this.dispDebitoResInit = null;
        this.dispDebitoResAllaData = null;
        this.isLoading = false;
      }
    }, err => {
      this.isLoading = false;
      this.noData = true;
      this.showMessageError("Si è verificato un errore, riprova più tardi.");
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  

  setDebitoResAllaData(ec: Array<EstrattoContoDTO>): number {
    let debTemp: number = 0;

    ec.forEach(item => {
      if(item.importoCapitale != null)
        debTemp = debTemp + item.importoCapitale;
    });

    return Math.abs(debTemp);
  }
  

  toSignedNumber(numero: number, segno: string): number {
    return segno == '-' ? (numero * -1) : numero;
  }
  
  

  toDateFromTimestamp(unixTimestamp: Date): string {
    if (unixTimestamp) {
      const date = new Date(unixTimestamp);

      const day = date.getDate().toString().padStart(2, '0');
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const year = date.getFullYear();
      return `${day}/${month}/${year}`;
    } else {
      return null
    }
  }

  toAgevolazioneFromId(idAgevolazione: number) :string {
    switch(idAgevolazione) {
      case 1:
        return 'Contributo';
      case 5:
        return 'Finanziamento';
      case 10:
        return 'Garanzia';
      default:
        return idAgevolazione.toString();
    }
  }

  download(){
    let fileName: string = 'Estratto Conto (NDG ' + this.ndg + ' - Progetto ' + this.dispCodProg + ' - Agevolazione ' + this.toAgevolazioneFromId(this.idModalitaAgevolazioneRif) + ')';

    let header = ['Data contabile', 'Data valuta','Data scadenza', 'Numero rata', 'Causale', 'Totale pagato', 'Capitale pagato', 'Interessi pagati', 'Interessi mora', 'Oneri agevolazione', 'Tipo agevolazione'];

    // Se la garanzia è pura, nascondo le colonne 'Interessi pagati' e 'Interessi mora'
    if(this.isGaranziaPura) {header = header.filter(column => column !== 'Interessi pagati' && column !== 'Interessi mora');}

    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Estratto conto');

    // Aggiungo righe di dettaglio
    worksheet.addRow(['Bando', this.dispBando ? this.dispBando : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Identificativo progetto', this.dispCodProg ? this.dispCodProg : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Beneficiario', this.dispBenef ? this.dispBenef : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Debito residuo iniziale', this.dispDebitoResInit !== null ? (this.dispDebitoResInit) : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };
    worksheet.addRow(['Debito residuo alla data', this.dispDebitoResAllaData !== null ? (this.dispDebitoResAllaData) : '-']).getCell(1).fill = {
      type: 'pattern',
      pattern: 'solid',
      fgColor: { argb: 'CECECE' },
    };


    let cols = header.map(colName => ({ name: colName, filterButton: false }));

    let data = new Array<any>();
    if(this.isGaranziaPura) { // Se la garanzia è pura, nascondo le colonne 'Interessi pagati' e 'Interessi mora'
      this.estrattoConto.map(p => {
        data.push([this.toDateFromTimestamp(p.dataContabile), this.toDateFromTimestamp(p.dataValuta), this.toDateFromTimestamp(p.dataScadenza), p.numRata, p.desCausale, p.importoTotale, p.importoCapitale, p.importoOneri, p.dispAgevolazione]);
      })
    } else {
      this.estrattoConto.map(p => {
        data.push([this.toDateFromTimestamp(p.dataContabile), this.toDateFromTimestamp(p.dataValuta), this.toDateFromTimestamp(p.dataScadenza), p.numRata, p.desCausale, p.importoTotale, p.importoCapitale, p.importoInteressi, p.importoMora, p.importoOneri, p.dispAgevolazione]);
      })
    }

    worksheet.addTable({
      name: 'EstrattoConto',
      ref: 'A7',
      headerRow: true,
      totalsRow: false,
      style: {
        theme: 'TableStyleLight8',
        showRowStripes: false,
      },
      columns: cols,
      rows: data,
    });

    worksheet.getColumn('F').numFmt = '#,##0.00';
    worksheet.getColumn('G').numFmt = '#,##0.00';
    worksheet.getColumn('H').numFmt = '#,##0.00';

    if(!this.isGaranziaPura) {
      worksheet.getColumn('I').numFmt = '#,##0.00';
      worksheet.getColumn('J').numFmt = '#,##0.00';
    }
    
    // Generate il file Excel
    workbook.xlsx.writeBuffer().then((data: any) => {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        FileSaver.saveAs(blob, fileName + '.xlsx');
    });
  }

 

 

  showMessageError(errorMsg: string) {
    this.resetMessages();
    this.errorMsg = errorMsg;
    this.showError = true;
  }
  
  resetMessages() {
    this.errorMsg = "";
    //this.succMsg = "";
    this.showError = false;
    //this.showSucc = false;
  }
  

  /*showMessageSuccess(succMsg: string) {
    this.resetMessage();
    this.succMsg = succMsg;
    this.showSucc = true;
  }*/
  
  /*showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }*/

  /*resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }*/

}