/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { StoricoRicercaCampionamentiDTO } from '../../commons/dto/StoricoRicercaCampionamentiDTO';
import { RicercaCampionamentiVO } from '../../commons/dto/RicercaCampionamentiVO';
import { CampionamentoService } from '../../services/campionamento.services';
import * as XLSX from 'xlsx';
import { Sort } from '@angular/material/sort';

@Component({
  selector: 'app-ricerca-campionamenti',
  templateUrl: './ricerca-campionamenti.component.html',
  styleUrls: ['./ricerca-campionamenti.component.scss']
})
export class RicercaCampionamentiComponent implements OnInit {


  descrizione: string;
  dataInizio: Date;
  dataFine: Date;
  idUtenteCampionamento: string;
  idTipologiaSelezione: number;
  idStatoCampionamento: number;

  listTipologie: Array<AttivitaDTO>;
  listStati: Array<AttivitaDTO>;
  tipologia: AttivitaDTO;
  stato: AttivitaDTO;
  userloaded: boolean;
  subscribers: any = {};
  user: UserInfoSec;
  idUtente: any;
  messageError: string;
  isMessageErrorVisible: boolean;
  criteriRicercaOpened: boolean = true;
  listStatiloaded: boolean;
  listTipologieloaded: boolean;
  listCampionamenti: Array<StoricoRicercaCampionamentiDTO>;
  listaCamloaded: boolean = true;
  ricCamVO: RicercaCampionamentiVO;
  results: boolean;
  isStorico: boolean;
  displayedColumns: string[] = ['idCampione', 'descrizione', 'tipologia', "progetti", "impValidato", "percentuale",
    "dataCampionamento", "utente"];
  excelFile: string = 'campionamenti.xlsx';
  sortedData: StoricoRicercaCampionamentiDTO[];
  denIns: string;
  denomDTO: AttivitaDTO;
  suggesttionnull: AttivitaDTO;
  listaUtenti:  Array<AttivitaDTO>;

  constructor(
    private campionamentiService: CampionamentoService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
    this.userloaded = false;
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.userloaded = true;
        this.loadListe();

      }
    });
  }
  loadListe() {
    this.listStatiloaded = false;
    this.listTipologieloaded = false;
    this.subscribers.listStati = this.campionamentiService.getListaStati().subscribe(data => {
      if (data)
        this.listStati = data;
      this.listStatiloaded = true;
    });
    this.subscribers.listTipologie = this.campionamentiService.getListaTipologie().subscribe(data => {
      if (data)
        this.listTipologie = data;
      this.listTipologieloaded = true;
    });
  }

  search() {
    this.listaCamloaded = false;
    this.results = true;
    this.ricCamVO = new RicercaCampionamentiVO();
    if (this.denIns != null && this.denIns.length > 0) {
      for (let denom of this.listaUtenti) {
        if (denom.descAttivita == this.denIns) {
          this.ricCamVO.idUtenteCampionamento = denom.idSoggetto;
        }
      }
    }
    this.ricCamVO.dataFine = this.dataFine;
    this.ricCamVO.dataInizio = this.dataInizio;
    this.ricCamVO.descrizione = this.descrizione;
    this.ricCamVO.idStatoCampionamento = this.idStatoCampionamento;
    this.ricCamVO.idTipologiaSelezione = this.idTipologiaSelezione;
    // this.ricCamVO.idUtenteCampionamento = this.idUtenteCampionamento;
    console.log(this.ricCamVO);
    this.subscribers.search = this.campionamentiService.getElencoCampionamenti(this.ricCamVO).subscribe(data => {

      if (data.length > 0) {
        this.listCampionamenti = data;
        this.sortedData = data;
        this.listaCamloaded = true
        this.isStorico = true;
      } else {
        this.isStorico = false;
        this.listaCamloaded = true;
      }
    }, err => {

      this.showMessageError("Errore in fase di ricerca.")
      this.handleExceptionService.handleNotBlockingError(err);
    });
    this.criteriRicercaOpened = false;
  }


  download() {
    let element = document.getElementById('excel');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element, { dateNF: 'dd/mm/yyyy', cellDates: true, raw: true });
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Excel');
    XLSX.writeFile(wb, this.excelFile);
  }

  suggest(value: string) {

    this.suggesttionnull = new AttivitaDTO;
    if (value.length >= 3) {
      this.listaUtenti = new Array<AttivitaDTO>();
      this.suggesttionnull.descAttivita = 'caricamento...';
      this.listaUtenti.push(this.suggesttionnull);
      this.subscribers.listaUtenti = this.campionamentiService.getListaUtenti(value).subscribe(data => {
        if (data.length > 0) {
          this.listaUtenti = data;
        } else {
          this.listaUtenti = new Array<AttivitaDTO>();
          this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
          this.listaUtenti.push(this.suggesttionnull);
        }
      });
    }
  }



  isLoading() {
    if (!this.userloaded || !this.listaCamloaded || !this.listStatiloaded || !this.listTipologieloaded) {
      return true;
    }
    return false;
  }
  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }
  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  sortData(sort: Sort) {

    const data = this.sortedData.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }
    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'idCampione':
          return compare(a.idCampione, b.idCampione, isAsc);
        case 'descrizione':
          return compare(a.descrizione, b.descrizione, isAsc);
        case 'utente':
          return compare(a.cognome + '' + a.nome, b.cognome + '' + b.nome, isAsc);
        case 'tipologia':
          return compare(a.descTipologiaCamp, b.descTipologiaCamp, isAsc);
        case 'dataCampionamento':
          return compare(a.dataCampionamento, b.dataCampionamento, isAsc);
        case 'impValidato':
          return compare(a.impValidato, b.impValidato, isAsc);
        case 'percentuale':
          return compare(a.percEstratta, b.percEstratta, isAsc);
        case 'progetti':
          return compare(a.numProgettiSelezionati, b.numProgettiSelezionati, isAsc);
        default:
          return 0;
      }
    });
  }

}

function compare(a: number | string | Date, b: number | string | Date, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
