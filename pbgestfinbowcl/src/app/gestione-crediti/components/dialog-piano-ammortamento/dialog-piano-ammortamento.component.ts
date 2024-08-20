/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewInit, ElementRef, Inject, Component, OnInit, ViewChild } from '@angular/core';
import { RicBenResponseService } from '../../services/ricben-response-service.service';
import { Constants } from '../../../core/commons/util/constants';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DettaglioFinanziamentoErogato } from '../../commons/dto/dettaglio-finanziamento-erogato';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, Sort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { PianoAmmortamentoDTO } from '../../commons/dto/piano-ammortamentoVO';
import { Workbook } from 'exceljs';
import * as FileSaver from 'file-saver'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
    selector: 'dialog-piano-ammortamento',
    templateUrl: 'dialog-piano-ammortamento.component.html',
    styleUrls: ['dialog-piano-ammortamento.component.scss'],
})

@AutoUnsubscribe({ objectName: 'subscribers' })
export class DialogPianoAmmortamento implements OnInit, AfterViewInit {
  @ViewChild(MatSort) sort: MatSort;


  /* Componente di provenienza
  1 - monitoraggio crediti;
  2 - gestione garanzie;
  3 - provvedimento di revoca;
  4 - proposte variazioni stato credito */
  componentFrom: number;


  // Dettaglio beneficiario visualizzato
  dispBando: string;
  dispCodProg: string;
  dispBenef: string;
  dispDebitoResInit: number = 0;
  dispDebitoResAllaData: number = 0;

  //isSpinnerSpinning: boolean = false;
  isLoading: boolean = true;
  noData: boolean = true;
    
  public subscribers: any = {};

  // Per chiamata 
  user: UserInfoSec;
  idUtente: string;
  idProgetto: string;
  ndg: number = 0;
  idModalitaAgevolazione: number;
  idModalitaAgevolazioneRif: number;

  isGaranziaPura: boolean = false;

  displayedColumns: string[] = ["numRata",'causale', "dataScadenza", "tipoRata", "capitale", "importoAgevolazione", "interessi", "debitoResiduo", 'importoBanca'];

  pianoAmmortamento: Array<PianoAmmortamentoDTO>;
  dataSource: MatTableDataSource<PianoAmmortamentoDTO> = new MatTableDataSource<PianoAmmortamentoDTO>([]);

  // Messages
  showError: boolean = false;
  errorMsg: string = "";
  //showSucc: boolean = false;
  //succMsg: string = "";

  constructor(
    private resService: RicBenResponseService,
    public dialogRef: MatDialogRef<DialogPianoAmmortamento>,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: {
      componentFrom: number,

      bando: string,
      codProgetto: string,
      beneficiario: string,
      debitoResiduo: number,

      idProgetto: string,
      ndg: number,
      idModalitaAgevolazione: string,
      idModalitaAgevolazioneRif: string,
    }
  ) {
    console.log("Parametri passati alla dialog Piano ammortamento: ", data);

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

    // Per Garanzie - Se la garanzia è pura, nascondo le revoche (righe che hanno come tipo rata 'revoche')
    if(this.componentFrom == 2 && this.idModalitaAgevolazione == 10) {
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
    this.subscribers.pianoAmmortamento = this.resService.getPianoAmmortamento(this.idUtente, this.idProgetto, this.dispCodProg, this.ndg, this.idModalitaAgevolazione, this.idModalitaAgevolazioneRif).subscribe(data => {

      if (data && data?.infoPiano?.length > 0) {
        this.noData = false;

        // Per Garanzie - Se la garanzia è pura, nascondo le revoche (righe che hanno come tipo rata 'revoche')
        if(this.isGaranziaPura) {
          this.pianoAmmortamento = this.filtraRevochePerGaranziaPura(data.infoPiano);
        } else {
          this.pianoAmmortamento = data.infoPiano;
        }
        

        this.dataSource = new MatTableDataSource<PianoAmmortamentoDTO>(this.pianoAmmortamento);
        this.dataSource.sort = this.sort;

        // Valorizzo importi debito residuo
        this.dispDebitoResInit = this.pianoAmmortamento[0].debitoResiduoIniziale
        this.dispDebitoResAllaData = this.pianoAmmortamento[this.pianoAmmortamento.length - 1].debitoResiduo;

        this.isLoading = false;
      } else {
        this.noData = true;
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

  filtraRevochePerGaranziaPura(data: Array<PianoAmmortamentoDTO>): Array<PianoAmmortamentoDTO> {

    const causaliRevoche = [
      "Revoca Parziale Finanziamento",
      "Revoca Amministrativa Garanzia",
      "Revoca Bancaria Finanziamento",
      "Cessione Revoca", "Revoca Contributo",
      "Revoca Totale Finanziamento"
    ];

    if (data.length > 0) {
      console.log("elementi del Piano filtrati fuori: ", data.filter((obj) => causaliRevoche.includes(obj.causale)));
      
      return data.filter((obj) => !causaliRevoche.includes(obj.causale));
    } else {
      return [];
    }
  }


  download(){
    let fileName: string = 'Piano di Ammortamento (NDG ' + this.ndg + ' - Progetto ' + this.dispCodProg + ' - Agevolazione ' + this.toAgevolazioneFromId(this.idModalitaAgevolazioneRif) + ')';

    let header = ['Numero rata', 'Causale', 'Data scadenza', 'Tipo rimborso', 'Importo capitale', 'Importo agevolazione', 'Importo interessi', 'Debito residuo', 'Importo Banca'];

    const workbook = new Workbook();
    const worksheet = workbook.addWorksheet('Piano di Ammortamento');

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
    this.pianoAmmortamento.map(p => {
      data.push([p.numRata, p.causale, this.toDateFromTimestamp(p.dataScadenza), p.tipoRata, p.capitale, p.importoAgevolazione, p.interessi, p.debitoResiduo, p.importoBanca]);
    })
    

    worksheet.addTable({
      name: 'pianoAmmortamento',
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

    worksheet.getColumn('E').numFmt = '#,##0.00';
    worksheet.getColumn('F').numFmt = '#,##0.00';
    worksheet.getColumn('G').numFmt = '#,##0.00';
    worksheet.getColumn('H').numFmt = '#,##0.00';
    worksheet.getColumn('I').numFmt = '#,##0.00';

    
    // Generate il file Excel
    workbook.xlsx.writeBuffer().then((data: any) => {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        FileSaver.saveAs(blob, fileName + '.xlsx');
    });
  }

  toAgevolazioneFromId(idAgevolazione: number): string {
    switch (idAgevolazione) {
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

  
  /*OLDshowMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }*/

  /*OLDshowMessageSuccess(succMsg: string) {
    this.resetMessage();
    this.succMsg = succMsg;
    this.showSucc = true;
  }*/

  /*OLDresetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }*/


  showMessageError(errorMsg: string) {
    this.resetMessage();
    this.errorMsg = errorMsg;
    this.showError = true;
  }


  resetMessage() {
    this.errorMsg = "";
    //this.succMsg = "";
    this.showError = false;
    //this.showSucc = false;
  }

}