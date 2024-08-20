/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec, HandleExceptionService } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { CampionamentoService } from '../../services/campionamento.services';
import { MatDialog } from '@angular/material/dialog';
import { DialogEditNuovoCampionamentoComponent } from '../dialog-edit-nuovo-campionamento/dialog-edit-nuovo-campionamento.component';
import { DichiarazioneSpesaCampionamentoVO } from '../../commons/dto/DichiarazioneSpesaVO';
import { NuovoCampionamentoVO } from '../../commons/vo/NuovoCampionamentoVO';
import { AttivitaExtendedDTO } from '../../commons/dto/AttivitaExtendedDTO';
import { ProgettoEstrattoVO } from '../../commons/vo/ProgettoEstrattoVO';
import { MatSort, Sort } from '@angular/material/sort';
import * as XLSX from 'xlsx';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ProgettiEstrattiEsclusiDTO } from '../../commons/dto/ProgettiEstratiEsclusiDTO';
import * as FileSaver from 'file-saver';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';



@Component({
  selector: 'app-nuovo-campionamento',
  templateUrl: './nuovo-campionamento.component.html',
  styleUrls: ['./nuovo-campionamento.component.scss']
})
export class NuovoCampionamentoComponent implements OnInit {
  userloaded: boolean = true;
  idUtente: number;
  subscribers: any = {};
  user: UserInfoSec;
  descCampionamento: string;
  tipologiaSelezione: number;
  isBandi: boolean;
  bando: AttivitaDTO = new AttivitaDTO();
  listaBandi: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  isDichSpesa: boolean;
  isStatoAgev: boolean;
  dichiarazioneSpesaVO: DichiarazioneSpesaCampionamentoVO = new DichiarazioneSpesaCampionamentoVO();
  nuovoCampionamentoVO: NuovoCampionamentoVO = new NuovoCampionamentoVO();
  isElaborato: boolean;
  elaboraLoaded: boolean = true;
  progettiEstratti: Array<ProgettoEstrattoVO> = new Array<ProgettoEstrattoVO>();
  sortedData = new MatTableDataSource<ProgettoEstrattoVO>([]);
  progettiEsclusi = new MatTableDataSource<ProgettoEstrattoVO>([]);
  progettiCampionati = new MatTableDataSource<ProgettoEstrattoVO>([]);
  displayedColumns: string[] = ['descBando', 'idProgetto', "descFormaGiuridica", "codFisc", "comuneSedeLegale",
    "provSedeLegale", "comuneSedeIntervento", "provSedeIntervento", "dataUltimoControllo", "rimuovi"];
  displayedColumns2: string[] = ['descBando', 'idProgetto', "descFormaGiuridica", "codFisc", "comuneSedeLegale",
    "provSedeLegale", "comuneSedeIntervento", "provSedeIntervento", "dataUltimoControllo", "motivazione", "riametti"];
  displayedColumns3: string[] = ['descBando', 'idProgetto', "descFormaGiuridica", "codFisc", "comuneSedeLegale",
    "provSedeLegale", "comuneSedeIntervento", "provSedeIntervento", "dataUltimoControllo"];
  isEstratti: boolean;
  excelFile: string = "estrazione.xlsx";
  isEsclusi: boolean;
  isEstrazione: any;
  isCampionati: boolean;
  descTipoSelezione: string;
  percentuale: number;
  importoTotale: string;
  isTest: boolean;
  campionatiLoaded: boolean = true;
  estrattiLoaded: boolean = true;
  importoLoaded: boolean = true;
  importoTotaleFormatted: string;
  controllLocoLoaded: boolean = true;
  insertControlli: boolean;
  numeroCampionamento: number;
  idOp = Constants.ID_OPERAZIONE_CAMPIONAMENTO_RICERCA;
  idOpNuovoCamp = Constants.ID_OPERAZIONE_CAMPIONAMENTO_NUOVO;



  constructor(
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private campionamentoService: CampionamentoService,
    public sharedService: SharedService,
    private router: Router,
    public dialog: MatDialog
  ) { }
  @ViewChild("elaborati") paginator: MatPaginator;
  @ViewChild("elaboratiSort") sort: MatSort;
  @ViewChild("esclusi") paginator2: MatPaginator;
  @ViewChild("esclusiSort") sort2: MatSort;
  @ViewChild("campionati") paginatorCampionati: MatPaginator;
  @ViewChild("campionatiSort") sortCampionati: MatSort;
  @ViewChild('TABLE') table: ElementRef;
  @ViewChild('TABLE2') table2: ElementRef;

  ngOnInit(): void {
    this.userloaded = false;
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.userloaded = true;
      }
    });
  }
  // BANDO
  openDialog() {
    let dialogRef = this.dialog.open(DialogEditNuovoCampionamentoComponent, {
      width: '50%',
      data: {
        element: 1
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data) {
        if (data.length > 0)
          for (let bando of data) {
            if (bando && !this.listaBandi.find(b => b.idAttivita == bando.idAttivita)) {
              bando.selected = true;
              this.listaBandi.push(bando);
            }
          }
        console.log(this.listaBandi);
        this.isBandi = true;
      }
    })

  }
  deleteBando(idBando: number) {

    for (var i = 0; i < this.listaBandi.length; i++) {
      if (this.listaBandi[i].idAttivita == idBando)
        this.listaBandi.splice(i, 1);
    }
    if (this.listaBandi.length == 0) {
      this.isBandi = false;
    }
  }
  deleteAllBandi() {
    this.listaBandi.splice(0, this.listaBandi.length);
    this.isBandi = false;
  }


  openDialogStatoAgev() {

  }

  // DICHIARAZIONE SPESA
  openDialogSpesa() {
    let dialogRef = this.dialog.open(DialogEditNuovoCampionamentoComponent, {
      width: '50%',
      data: {
        dichirazioneSpesa: this.dichiarazioneSpesaVO,
        element: 3
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data != null) {
        this.dichiarazioneSpesaVO = data;
        if (this.dichiarazioneSpesaVO.tipoDichiarazione.length > 0) {
          this.isDichSpesa = true;
        }
      }
    })
  }
  deleteTipoDichiarazSpesa(idTipo: number) {

    for (var i = 0; i < this.dichiarazioneSpesaVO.tipoDichiarazione.length; i++) {
      if (this.dichiarazioneSpesaVO.tipoDichiarazione[i].idAttivita == idTipo)
        this.dichiarazioneSpesaVO.tipoDichiarazione.splice(i, 1);
    }
    if (this.dichiarazioneSpesaVO.tipoDichiarazione.length == 0) {
      this.isDichSpesa = false;
    }

  }
  deleteAllDichiarazSpesa() {
    this.dichiarazioneSpesaVO.tipoDichiarazione.splice(0, this.dichiarazioneSpesaVO.tipoDichiarazione.length);
    this.isDichSpesa = false;
  }
  elaboraCampionamento() {

    this.elaboraLoaded = false;
    // this.subscribers.elaboraCampionamento =  this.campionamentoService.elsabora()

    var bandi = new Array<AttivitaDTO>();
    for (let bando of this.listaBandi) {
      var bando2 = new AttivitaDTO();
      bando2.idAttivita = bando.idAttivita;
      bando2.descAttivita = bando.descAttivita;
      bandi.push(bando2);
    }
    console.log(bandi);
    this.progettiEsclusi = new MatTableDataSource<ProgettoEstrattoVO>([]);

    if (this.tipologiaSelezione == 1) {
      this.descTipoSelezione = "Numero aziende";
    } else {
      this.descTipoSelezione = "Importo validato";
    }
    this.nuovoCampionamentoVO.idTipoCampionamento = this.tipologiaSelezione;
    this.nuovoCampionamentoVO.bandi = bandi;
    this.nuovoCampionamentoVO.dichiarazioneSpesa = this.dichiarazioneSpesaVO;
    this.nuovoCampionamentoVO.idUtenteIns = this.idUtente;
    this.nuovoCampionamentoVO.descCampionamento = this.descCampionamento;

    this.subscribers.elaboraCampionamento = this.campionamentoService.elaboraCampionamento(this.nuovoCampionamentoVO).subscribe(data => {
      if (data != null) {
        console.log("begin elabaorazione");

        this.isElaborato = true;
        this.elaboraLoaded = true;
        this.progettiEstratti = data;
        this.sortedData = new MatTableDataSource<ProgettoEstrattoVO>(data);
        this.paginator.length = data.length;
        this.paginator.pageIndex = 0;
        this.sortedData.paginator = this.paginator;
        this.sortedData.sort = this.sort;
        this.sortedData.sortingDataAccessor = (item, property) => {
          switch (property) {
            case 'codFisc':
              return item.codiceFiscaleSoggetto;
            case 'dataUltimoControllo':
              return item.dataUltimoControllo?.getTime();
            default: return item[property];
          }
        };
        this.isEstratti = true;
        if(data[0]!=null)
          this.numeroCampionamento= data[0].idCampionamento;

      } else {
        this.isElaborato = false;
        this.elaboraLoaded = true;
      }
    }
      // , err => {
      //   this.handleExceptionService.handleNotBlockingError(err);
      //   this.ElaboraLoaded = true;
      // }
    );
  }
  escludiProgetto(idProgetto: number) {

    let dialogRef = this.dialog.open(DialogEditNuovoCampionamentoComponent, {
      width: '40%',
      data: {
        element: 5
        // 5 corrispnde alla motivazione
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data != null) {
        console.log(data);
        if (data.selected) {
          this.addProgettoEscluso(data, idProgetto);
        }
      }
    })
  }
  addProgettoEscluso(data: any, idProgetto: number) {

    // recupero il progetto da escludere e lo aggiungo alla lista di quelli esclusi
    var progettiEsclusi = new Array<ProgettoEstrattoVO>();
    progettiEsclusi = this.progettiEsclusi.data;

    var progetto = new ProgettoEstrattoVO;
    progetto = this.sortedData.data.find(p => p.idProgetto == idProgetto);
    progetto.motivazione = data.descAttivita;
    progettiEsclusi.push(progetto);
    this.progettiEsclusi = new MatTableDataSource<ProgettoEstrattoVO>(progettiEsclusi)
    this.isEsclusi = true;

    this.paginator2.length = this.progettiEsclusi.data.length;
    this.paginator2.pageIndex = 0;
    this.progettiEsclusi.sort = this.sort2;
    this.progettiEsclusi.paginator = this.paginator2;
    this.progettiEsclusi.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'codFisc':
          return item.codiceFiscaleSoggetto;
        case 'dataUltimoControllo':
          return item.dataUltimoControllo?.getTime();
        default: return item[property];
      }
    };

    // cancello il progetto dalla lista di quelli da non escludere ;
    var progetti = new Array<ProgettoEstrattoVO>();
    progetti = this.sortedData.data;
    progetti.splice(progetti.findIndex(p => p.idProgetto == idProgetto), 1);
    // for (var i = 0; i < progetti.length; i++) {
    //   if (progetti[i].idProgetto = idProgetto) {
    //     progetti.splice(i,1);
    //   }
    // }
    this.sortedData.data = (progetti);
    this.paginator.length = data.length;
    this.paginator.pageIndex = 0;



    console.log(this.sortedData.data);


    if (this.sortedData.data.length == 0)
      this.isEstratti = false;

  }
  riamettiProgetto(idProgetto: number) {

    var progetto = this.progettiEsclusi.data.find(p => p.idProgetto == idProgetto);
    progetto.motivazione = "";
    var progetti = new Array<ProgettoEstrattoVO>();
    progetti = this.sortedData.data;
    progetti.push(progetto);
    this.paginator.pageIndex = 0;
    this.paginator.length = progetti.length;
    this.sortedData.data = progetti;
    this.isEstratti = true;
    var esclusi = new Array<ProgettoEstrattoVO>();
    esclusi = this.progettiEsclusi.data;


    esclusi.splice(esclusi.findIndex(p => p.idProgetto == idProgetto), 1);

    this.paginator2.length = esclusi.length;
    this.paginator2.pageIndex = 0;

    this.progettiEsclusi.data = esclusi;
    if (this.progettiEsclusi.data.length == 0)
      this.isEsclusi = false;


    console.log(this.progettiEsclusi.data);

  }


  riammettiTutti() {
    // da implementare dopo..
    let dialogRef = this.dialog.open(DialogEditNuovoCampionamentoComponent, {
      width: '40%',
      data: {
        element: 6
        // 6 corrispnde alla riamisisone di tutti i progetti esclusi
      }
    });

    var result: boolean;
    dialogRef.afterClosed().subscribe(data => {
      if (data == true) {
        result = data;
        this.riammissioneProgetti();
      }

    });
  }

  riammissioneProgetti() {


    var esclusi = new Array<ProgettoEstrattoVO>();
    esclusi = this.progettiEsclusi.data;
    console.log(esclusi);

    // esclusi.forEach(p => this.riamettiProgetto(p.idProgetto));

    // if (esclusi.length != 0) {
    //   this.riammissioneProgetti();
    // }


    var progetti = new Array<ProgettoEstrattoVO>();
    progetti = this.sortedData.data;
    for (let p of esclusi) {
      progetti.push(p);
    }

    this.sortedData.data = progetti;
    this.paginator.pageIndex = 0;
    this.paginator.length = progetti.length;
    this.progettiEsclusi.data = [];
    this.paginator2.length = 0;
    this.paginator2.pageIndex = 0;
    this.isEstratti = true;
    this.isEsclusi = false;
  }

  estrazioneProgetti() {
    // passare gli degli esclusi e dei non esclusi
    var progetti = new ProgettiEstrattiEsclusiDTO();
    this.estrattiLoaded = false;
    this.nuovoCampionamentoVO.idCampionamento = this.progettiEstratti[0].idCampionamento;
    this.nuovoCampionamentoVO.percEstrazione = this.percentuale;
    progetti.esclusi = this.progettiEsclusi.data;
    progetti.estratti = this.sortedData.data;
    this.subscribers.eseguiEstrazione = this.campionamentoService.eseguiEstrazione(progetti, this.idUtente).subscribe(data => {
      if (data) {
        this.isEstrazione = data;
        console.log("esito estrazione: " + data);
        this.estrattiLoaded = true;
      }
      if (this.tipologiaSelezione == 2) {
        this.importoLoaded = false;
        this.subscribers.getTotwleImporto = this.campionamentoService.getImportoTotale(this.nuovoCampionamentoVO).subscribe(data => {
          if (data) {
            this.importoTotale = data;
            this.importoTotaleFormatted = this.sharedService.formatValue(this.importoTotale);
            this.importoLoaded = true;
          }
        })
      }
    })

  }
  campiona() {

    this.campionatiLoaded = false;
    this.nuovoCampionamentoVO.idCampionamento = this.progettiEstratti[0].idCampionamento;
    this.nuovoCampionamentoVO.percEstrazione = this.percentuale;
    this.subscribers.getCampionati = this.campionamentoService.campionaProgetti(this.nuovoCampionamentoVO, this.idUtente).subscribe(data => {
      if (data) {
        //this.progettiCampionati.data = data;
        console.log(data);
        this.campionatiLoaded = true;
        this.progettiCampionati = new MatTableDataSource<ProgettoEstrattoVO>(data);
        console.log(this.progettiCampionati.data);

        this.paginatorCampionati.length = data.length;
        this.paginatorCampionati.pageIndex = 0;
        this.progettiCampionati.paginator = this.paginatorCampionati;
        this.progettiCampionati.sort = this.sortCampionati;
        this.progettiCampionati.sortingDataAccessor = (item, property) => {
          switch (property) {
            case 'codFisc':
              return item.codiceFiscaleSoggetto;
            case 'dataUltimoControllo':
              return item.dataUltimoControllo?.getTime();
            default: return item[property];
          }
        };
        if (data.length > 0)
          this.isCampionati = true;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.campionatiLoaded = true;
    })

  }

  creaControlli() {

    this.isTest = true;
    this.controllLocoLoaded = false;
    var progetti = new ProgettiEstrattiEsclusiDTO();
    progetti.campionati = this.progettiCampionati.data;
    this.subscribers.creaControlli = this.campionamentoService.creaControlloLoco(progetti, this.idUtente).subscribe(data => {
      if (data) {
        this.controllLocoLoaded = true;
        this.insertControlli = true;
        let dialogRef = this.dialog.open(DialogEditNuovoCampionamentoComponent, {
          width: '40%',
          data: {
            element: 7
            // 7 corrispnde alla creazione controllo in loco con successo
          }
        });
      }
    })

  }


  downloadPdf() {
    // var prepare=[];
    // this.progettiCampionati.data.forEach(e=>{
    //   var tempObj =[];
    //   tempObj.push(e.descBando);
    //   tempObj.push(e.idProgetto);
    //   tempObj.push( e.descFormaGiuridica);
    //   tempObj.push( e.codiceFiscaleSoggetto);
    //   tempObj.push( e.comuneSedeLegale);
    //   tempObj.push(e.provSedeLegale);
    //   tempObj.push(e.comuneSedeIntervento);
    //   tempObj.push(e.provSedeIntervento);
    //   prepare.push(tempObj);
    // });
    // const doc = new jsPDF();
    // doc.autoTable({
    //     head: [['Bando','','Progetto','','Forma giuridica','','Codice fiscale','','Comune Sede legale','','Provincia Sede legale','','Comune Sede interv.','','Provincia Sede interv.']],
    //     body: prepare
    // });
    // doc.save('campionati' + '.pdf');


}

  annullaCampionamento() {

    console.log(this.numeroCampionamento);

    let dialogRef = this.dialog.open(DialogEditNuovoCampionamentoComponent, {
      width: '40%',
      data: {
        element: 8,
        idCampionamento: this.numeroCampionamento,
        idUtente: this.idUtente
        // 8 corrispnde annulla controllo in loco con successo
      }
    });
    var result;
    dialogRef.afterClosed().subscribe(data => {
      if (data) {
        result = data;
        if(result==2){
          // true = nuovo campionamento
          this.router.navigate(["/drawer/" + this.idOpNuovoCamp + "/nuovoCampionamento/"]);
          location.reload();
          console.log(result);
          console.log(this.idOpNuovoCamp);

        }
        if(result==1){
           // false = ricerca campionamento
           this.router.navigate(["/drawer/ "+ this.idOp + "/ricercaCampionamenti/"]);
           console.log(result);
           console.log(this.idOpNuovoCamp);
         }
      }
    });

  }

  download(el: number) {
    // let element = document.getElementById('excel');

    // let workbseet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.sortedData.data, { dateNF: 'dd/mm/yyyy', cellDates: true });
    // let workbook: XLSX.WorkBook = { Sheets: { 'data': workbseet }, SheetNames: ['data'] };
    // let excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });

    // const data: Blob = new Blob([excelBuffer], {
    //   type: 'xlsx'
    // });
    // FileSaver.saveAs(data, this.excelFile);

    // const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element, { dateNF: 'dd/mm/yyyy', cellDates: true, raw: true });
    // const wb: XLSX.WorkBook = XLSX.utils.book_new();
    // XLSX.utils.book_append_sheet(wb, ws, 'Excel');
    // XLSX.writeFile(wb, this.excelFile);

    if(el==1){
      this.campionamentoService.generaExcelPropostaProgetto('estrazione', this.sortedData.data);
    } else {
      this.campionamentoService.generaExcelPropostaProgetto('campionati', this.progettiCampionati.data);
    }
  }

  isLoading() {
    if (!this.userloaded || !this.elaboraLoaded || !this.campionatiLoaded || !this.elaboraLoaded || !this.importoLoaded || !this.estrattiLoaded
      || !this.controllLocoLoaded) {
      return true;
    }
    return false;
  }
}
