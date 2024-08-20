/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { SharedService } from 'src/app/shared/services/shared.service';
import * as XLSX from 'xlsx';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RichiesteService } from '../../services/richieste.service';
import { ElencoRichieste } from '../../commons/dto/elenco-richieste'
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatDialog } from '@angular/material/dialog';
import { StoricoRichiestaComponent } from '../storico-richiesta/storico-richiesta.component';
import { StoricoRichiesta } from '../../commons/dto/storico-richiesta';
import { DettaglioRichiesta } from '../../commons/dto/dettaglio-richiesta';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { Richiesta } from '../../commons/dto/richiesta';
import { UserService } from 'src/app/core/services/user.service';
import { MotivazioneComponent } from '../motivazione/motivazione.component';

interface TipoRichiesta {
  value: string;
  viewValue: string;
}

interface StatoRichiesta {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-ricerca-richieste',
  templateUrl: './ricerca-richieste.component.html',
  styleUrls: ['./ricerca-richieste.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})

export class RicercaRichiesteComponent implements OnInit {

  //COSTANTE NECESSARIA AL ROUTING TRA COMPONENTI DA ACCODARE AL DRAWER COMPONENT
  idOp = Constants.ID_OPERAZIONE_GESTIONE_RICHIESTE;
  spinner: boolean = false;

  //FORM
  public myForm: FormGroup;

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  //CHECK PER DISABILITARE I BOTTONI
  richiedenteIns: any;
  domandaIns: any;
  bandoIns: any;

  //LISTE PER LE SUGGEST
  suggRichiedente: string[] = [];
  suggDomanda: string[] = [];
  suggBando: string[] = [];

  //VARIABILI DI RICERCA
  criteriRicercaOpened: boolean = true;
  ordinamentoString: string = "DESC";
  ordinamento: boolean = false;
  primaRicerca: boolean = false;

  //DATI TABELLA
  arrayTitoliColonne: string[] = ['Richiedente', 'Codice Fiscale', 'Tipo Richiesta', 'Stato Richiesta', 'Data Richiesta', 'Codice Bando', 'Numero Domanda', 'Codice Progetto'];
  disposizioneColonne: string[] = ['richiedente', 'codiceFiscale', 'tipoRichiesta', 'statoRichiesta', 'dataRichiesta', 'codiceBando', 'numeroDomanda', 'codiceProgetto', 'actions'];
  displayedColumns: string[] = ['richiedente', 'codiceFiscale', 'tipoRichiesta', 'statoRichiesta', 'dataRichiesta', 'codiceBando', 'numeroDomanda', 'codiceProgetto', 'visualizza'];
  displayedDettaglioColumns: string[] = ["nag", "denominazione", "partitaIva", "modalitaRichiesta", "numeroRichiesta", "dataChiusura", "storico"]
  displayedColumnsExcel: string[] = ['richiedente', 'codiceFiscale', 'tipoRichiesta', 'statoRichiesta', 'dataRichiesta', 'codiceBando', 'numeroDomanda', 'codiceProgetto', 'nag', 'denominazione', 'partitaIva', 'modalitaRichiesta', 'numeroRichiesta', 'dataChiusura'];
  elencoRichieste: Array<ElencoRichieste> =[];
  dataSourceExcel: any;
  espandi: string;
  dataSource: SoggettiDatasource = new SoggettiDatasource([]);
  dataSourceDettaglio: Array<DettaglioRichiesta[]> = [];
  showTable = false;
  emptyTable = false;
  excelFile = 'Richieste.xlsx';
  today: Date;
  idUtente: any;
  sortedDataRichieste = new MatTableDataSource<ElencoRichieste>([]);


  //BOOLEAN PER ALERT PER SUCCESSO O FALLIMENTO
  success: boolean = false;
  error: boolean = false;


  // @ViewChild(MatPaginator) paginator: MatPaginator;
  // @ViewChild(MatSort) sort: MatSort;
  @ViewChild("richiestePaginator") paginatorRichieste: MatPaginator;
  @ViewChild("richiesteSort") sortRichieste: MatSort;
  //OPZIONI PER IL TIPO RICHIESTE
  richieste: TipoRichiesta[] = [
    { value: '1', viewValue: 'DURC' },
    { value: '2', viewValue: 'DSAN in assenza di DURC' },
    { value: '3', viewValue: 'Antimafia' },
  ];


  //OPZIONI PER LO STATO DELLE RICHIESTE
  statiRichieste: StatoRichiesta[] = [
    { value: '1', viewValue: 'Da elaborare' },
    { value: '2', viewValue: 'In elaborazione' },
    { value: '3', viewValue: 'In istruttoria BDNA' },
    { value: '4', viewValue: 'Completata' },
    { value: '5', viewValue: 'Annullata' },
  ];

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    private richiesteService: RichiesteService,
    private fb: FormBuilder,
    public dialog: MatDialog,
    public handleExceptionService: HandleExceptionService,
    private userService: UserService
  ) {
  }

  ngOnInit() {
    this.spinner = true;

    this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.idUtente = data.idUtente
      }
    }, err => {
    });

    this.myForm = this.fb.group({
      idTipoRichiesta: new FormControl({ value: 0, disabled: false }),
      idStatoRichiesta: new FormControl({ value: 0, disabled: false }),
      domanda: new FormControl({ value: ' ', disabled: false }),
      codiceFondo: new FormControl({ value: ' ', disabled: false }),
      richiedente: new FormControl({ value: ' ', disabled: false }),
    });
    //CHIAMARE IL SERVIZIO PER POPOLARE LA TABELLA CON LE 50 RICHIESTE PIU' RECENTI.
    this.richiesteService.getElencoRichieste().subscribe(data => {
      this.dataSourceDettaglio = [];
      if (data) {
        
        this.elencoRichieste = data
        this.sortedDataRichieste = new MatTableDataSource<ElencoRichieste>(this.elencoRichieste);
        this.paginatorRichieste.length = this.elencoRichieste.length;
        this.paginatorRichieste.pageIndex = 0;
        this.sortedDataRichieste.paginator = this.paginatorRichieste;
        this.sortedDataRichieste.sort = this.sortRichieste;
        this.elencoRichieste = data;
        this.dataSourceExcel = data;
        this.dataSource = new SoggettiDatasource(this.elencoRichieste);
        // this.paginator.length = this.elencoRichieste.length;
        // this.paginator.pageIndex = 0;
        // this.dataSource.paginator = this.paginator;
        // this.dataSource.sort = this.sort;
        this.dataSource.data.forEach(val => {
          var dataSourceAlVolo: DettaglioRichiesta[] = [
            {
              nag: val.nag,
              denominazione: val.denominazione,
              partitaIva: val.partitaIva,
              modalitaRichiesta: val.modalitaRichiesta,
              numeroRichiesta: val.numeroRichiesta,
              dataChiusura: val.dataChiusura,
              storico: "",
            }
          ]
          val.listaDettaglio = dataSourceAlVolo;
        });
        if(this.elencoRichieste.length>0){
          this.emptyTable = false;
        }else{
          this.emptyTable=true
        }
        this.spinner = false;
      } else {
        this.emptyTable = true;
        this.spinner = false;
      }
      this.myForm.setValue({
        idTipoRichiesta: 0,
        idStatoRichiesta: 0,
        domanda: "",
        codiceFondo: "",
        richiedente: "",
      })
    });
  }

  sugg(id: number, value: string) {
    if (id == 4) {
      if (value.length >= 3) {
        this.suggRichiedente = ["Caricamento..."];
        this.richiesteService.getSuggestion(value, 4).subscribe(data => { if (data.length > 0) { this.suggRichiedente = data } else { this.suggRichiedente = ["Nessuna corrispondenza"] } })
      } else { this.suggRichiedente = [] }

    } else if (id == 2) {
      if (value.length >= 3) {
        this.suggDomanda = ["Caricamento..."];
        this.richiesteService.getSuggestion(value, 2).subscribe(data => { if (data.length > 0) { this.suggDomanda = data } else { this.suggDomanda = ["Nessuna corrispondenza"] } })
      } else { this.suggDomanda = [] }

    } else if (id == 3) {
      if (value.length >= 3) {
        this.suggBando = ["Caricamento..."];
        this.richiesteService.getSuggestion(value, 3).subscribe(data => { if (data.length > 0) { this.suggBando = data } else { this.suggBando = ["Nessuna corrispondenza"] } })
      } else { this.suggBando = [] }
    }
  }

  //CERCA
  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  cerca() {
    this.error = false;
    this.success = false;
    this.dataSourceDettaglio = [];
    let campoVuoto: boolean = true;

    if (this.myForm.controls.idTipoRichiesta.value.toString() != "0") {
      campoVuoto = false;
    }
    if (this.myForm.controls.idStatoRichiesta.value.toString() != "0") {
      campoVuoto = false;
    }
    if (this.myForm.controls.domanda.value.toString() != " ") {
      campoVuoto = false;
    }
    if (this.myForm.controls.codiceFondo.value.toString() != " ") {
      campoVuoto = false;
    }
    if (this.myForm.controls.richiedente.value.toString() != " ") {
      campoVuoto = false;
    }

    if (campoVuoto) {
      this.error = true;
      this.success = false;
    } else {
      let ordinamento: string = "DESC";
      let colonna: string = "DT_INVIO_RICHIESTA";
      this.spinner = true;
      this.richiesteService.cercaRichieste(
        this.myForm.controls.idTipoRichiesta.value.toString(),
        this.myForm.controls.idStatoRichiesta.value.toString(),
        this.myForm.controls.domanda.value.toString(),
        this.myForm.controls.codiceFondo.value.toString(),
        this.myForm.controls.richiedente.value.toString(),
        ordinamento,
        colonna).subscribe(data => {
          if (data) {
            this.elencoRichieste = data;
            this.dataSourceExcel = data;
            this.dataSource = new SoggettiDatasource(this.elencoRichieste);
            // this.paginator.length = this.elencoRichieste.length;
            // this.paginator.pageIndex = 0;
            // this.dataSource.paginator = this.paginator;
            // this.dataSource.sort = this.sort;
            this.sortedDataRichieste = new MatTableDataSource<ElencoRichieste>(this.elencoRichieste);
            this.paginatorRichieste.length = this.elencoRichieste.length;
            this.paginatorRichieste.pageIndex = 0;
            this.sortedDataRichieste.paginator = this.paginatorRichieste;
            this.sortedDataRichieste.sort = this.sortRichieste;
            this.dataSource.data.forEach(val => {
              var dataSourceAlVolo: DettaglioRichiesta[] = [
                {
                  nag: val.nag,
                  denominazione: val.denominazione,
                  partitaIva: val.partitaIva,
                  modalitaRichiesta: val.modalitaRichiesta,
                  numeroRichiesta: val.numeroRichiesta,
                  dataChiusura: val.dataChiusura,
                  storico: "",
                }
              ]
              val.listaDettaglio = dataSourceAlVolo;
            });
            if(this.elencoRichieste.length>0){
              this.emptyTable = false;
            } else{
              this.emptyTable=true
            }
            this.spinner = false;
          } else {
            this.emptyTable = true;
            this.spinner = false;
          }
          this.myForm.setValue({
            idTipoRichiesta: 0,
            idStatoRichiesta: 0,
            domanda: "",
            codiceFondo: "",
            richiedente: "",
          })
          this.primaRicerca = true;
        });
    }
  }

  reset() {
    this.myForm.setValue({
      idTipoRichiesta: 0,
      idStatoRichiesta: 0,
      domanda: "",
      codiceFondo: "",
      richiedente: "",
    });
  }

  sortColonna(indiceColonna?: HTMLSelectElement) {
    let indiceColonnaString: string;
    if (this.myForm.controls.idTipoRichiesta.value == 0) {
      this.richiesteService.openSnackBar(false);
    } else {
      this.ordinamento = !this.ordinamento;
      if (this.ordinamento) {
        this.ordinamentoString = "DESC";
      } else {
        this.ordinamentoString = "ASC";
      }

      if (indiceColonna.toString() == "Richiedente") {
        indiceColonnaString = "RICHIEDENTE";
      } else if (indiceColonna.toString() == 'Codice Fiscale') {
        indiceColonnaString = "CODICE_FISCALE";
      } else if (indiceColonna.toString() == 'Tipo Richiesta') {
        indiceColonnaString = "TIPO_RICHIESTA";
      } else if (indiceColonna.toString() == 'Stato Richiesta') {
        indiceColonnaString = "STATO_RICHIESTA";
      } else if (indiceColonna.toString() == 'Data Richiesta') {
        indiceColonnaString = "DATA_RICHIESTA";
      } else if (indiceColonna.toString() == 'Codice Bando') {
        indiceColonnaString = "CODICE_BANDO";
      } else if (indiceColonna.toString() == 'Numero Domanda') {
        indiceColonnaString = "NUMERO_DOMANDA";
      } else if (indiceColonna.toString() == 'Codice Progetto') {
        indiceColonnaString = "CODICE_PROGETTO";
      }
      this.spinner = true;
      this.richiesteService.cercaRichieste(
        this.myForm.controls.idTipoRichiesta.value.toString(),
        this.myForm.controls.idStatoRichiesta.value.toString(),
        this.myForm.controls.domanda.value.toString(),
        this.myForm.controls.codiceFondo.value.toString(),
        this.myForm.controls.richiedente.value.toString(),
        this.ordinamentoString,
        indiceColonnaString).subscribe(data => {
          if (data.toString() != "") {
            this.dataSource = new SoggettiDatasource(data);
            // this.paginator.length = data.length;
            // this.paginator.pageIndex = 0;
            // this.dataSource.paginator = this.paginator;
            // this.dataSource.sort = this.sort;
            this.emptyTable = false;
            this.spinner = false;
          } else {
            this.emptyTable = true;
            this.spinner = false;
          }
        })
    }
  }


  //SCARICA EXCEL
  // scaricaExcel() {
  //   if (this.primaRicerca) {
  //     let element = document.getElementById('excel-table');
  //     const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);
  //     const wb: XLSX.WorkBook = XLSX.utils.book_new();
  //     XLSX.utils.book_append_sheet(wb, ws, 'Foglio');
  //     XLSX.writeFile(wb, this.excelFile);
  //   } else {
  //     this.error = true;
  //   }
  // }

  scaricaExcel(){
    this.richiesteService.generaExcel('elenco-richieste', this.sortedDataRichieste.data);
  }

  visualizzaStoricoRichiesta(idRichiesta: HTMLSelectElement) {
    this.spinner = true;
    this.richiesteService.getStoricoRichiesta(idRichiesta).subscribe(data => {
      if (data) {
        let storicoRichiesta: Array<StoricoRichiesta> = data;
        this.dialog.open(StoricoRichiestaComponent, {
          height: 'auto',
          width: 'auto',
          data: {
            colonne: ['desc_tipo_comunicazione', 'destinatario_mittente', 'cognome', 'dt_comunicazione_esterna', 'numero_protocollo', 'motivazione'],
            storicoRichiesta,
          },
        });
      }
      this.spinner = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.spinner = false;
    });
  }

  eliminaRichiesta(idRichiesta: HTMLSelectElement, nag: HTMLSelectElement) {
    var idStatoRichiesta: string = "";
    this.richiesteService.getRichiesta(idRichiesta).subscribe(data => {
      if (data) {
        idStatoRichiesta = data[0].idStatoRichiesta
        if (idStatoRichiesta == "4" || idStatoRichiesta == "5") {
          this.error = true;
          this.success = false;
        } else {
          this.today = new Date();
          let parseDate = this.today.getFullYear() + "-" + ("0" + (this.today.getMonth() + 1)).slice(-2) + "-" + this.today.getDate();
          let motivazione: string = "";
          let dialogRef = this.dialog.open(MotivazioneComponent, {
            width: '70%',
            data: {
              motivazione,
            },
          });
          dialogRef.afterClosed().subscribe(result => {
            if (result != undefined && result != "") {
              this.richiesteService.getRichiesta(idRichiesta).subscribe(data => {
                if (data) {
                  let modifica = new Richiesta(
                    idRichiesta,
                    this.idUtente,
                    null,
                    data[0].idTipoRichiesta,
                    '5',
                    1,
                    parseDate.toString(),
                    "NESSUNO",
                    "NESSUNO",
                    result.motivazione,
                    data[0].numeroDomanda,
                    nag,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null, 
                    null
                  );
                  this.richiesteService.cancellaRichiesta(modifica).subscribe(data => {
                    if (data.toString() == "0") {
                      this.error = false;
                      this.success = true;
                      window.location.reload();
                    } else {
                      this.error = true;
                      this.success = false;
                    }
                  });
                }
              });
            }
          });
        }
      }
    });
  }

  elaboraRichiesta(idRichiesta: HTMLSelectElement, nag: HTMLSelectElement, partitaIva, denominazione, numeroDomanda, ndg) {
    var idTipoRichiesta: string = "";
    var idStatoRichiesta: string = "";
    this.richiesteService.getRichiesta(idRichiesta).subscribe(data => {
      if (data) {
        idTipoRichiesta = data[0].idTipoRichiesta;
        idStatoRichiesta = data[0].idStatoRichiesta
        if (idStatoRichiesta == "1" || idStatoRichiesta == "2" || idStatoRichiesta == "3") {
          this.router.navigate(["/drawer/" + this.idOp + "/elaboraRichiesta"], 
          { queryParams: { idRichiesta, nag,
             idTipoRichiesta, 
             idStatoRichiesta, 
             denominazione,
              numeroDomanda,ndg,
               partitaIva } });
        } else {
          this.error = true;
          this.success = false;
        }
      }
    });
  }

  inserisciRichiesta() {
    this.router.navigate(["/drawer/" + this.idOp + "/inserisciRichiesta"], { queryParams: {} });
  }

}


export class SoggettiDatasource extends MatTableDataSource<ElencoRichieste> {

  _orderData(data: ElencoRichieste[]): ElencoRichieste[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "id";
    }
    let direction = this.sort.direction || this.sort.start;
    let sortedData = null;

    // viene riordinato in ordine ascendente
    switch (this.sort.active) {
      case "richiedente":
        sortedData = data.sort((soggA, soggB) => {
          return soggA.richiedente.localeCompare(soggB.richiedente);
        });
        break;
      case "codiceFiscale":
        sortedData = data.sort((soggA, soggB) => {
          return soggA.codiceFiscale.localeCompare(soggB.codiceFiscale);
        });
        break;
      case "tipoRichiesta":
        sortedData = data.sort((soggA, soggB) => {
          return soggA.tipoRichiesta.localeCompare(soggB.tipoRichiesta);
        });
        break;
      case "statoRichiesta":
        sortedData = data.sort((soggA, soggB) => {
          return soggA.statoRichiesta.localeCompare(soggB.statoRichiesta);
        });
        break;
      case "dataRichiesta":
        sortedData = data.sort((soggA, soggB) => {
          return soggA.dataRichiesta.localeCompare(soggB.dataRichiesta);
        });
        break;
      case "codiceBando":
        sortedData = data.sort((soggA, soggB) => {
          return soggA.codiceBando.localeCompare(soggB.codiceBando);
        });
        break;
      case "numeroDomanda":
        sortedData = data.sort((soggA, soggB) => {
          return soggA.numeroDomanda.localeCompare(soggB.numeroDomanda);
        });
        break;
      case "codiceProgetto":
        sortedData = data.sort((soggA, soggB) => {
          return soggA.codiceProgetto.localeCompare(soggB.codiceProgetto);
        });
        break;
      // default:
      //   sortedData = data.sort((soggA, soggB) => {
      //     return soggA.richiedente.localeCompare(soggB.richiedente);
      //   });
      //   break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: ElencoRichieste[]) {
    super(data);
  }
}
