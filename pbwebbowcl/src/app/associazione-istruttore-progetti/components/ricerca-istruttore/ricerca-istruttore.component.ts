/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { CodiceDescrizioneDTO } from '../../commons/dto/codice-descrizione-dto';
import { IstruttoreShowDTO } from '../../commons/dto/istruttore-show-dto';
import { AssociazioneIstruttoreProgettiService } from '../../services/associazione-istruttore-progetti.service';
import { NavigationAssociazioneIstruttoreProgettiService } from '../../services/navigation-associazione-istruttore-progetti.service';

@Component({
  selector: 'app-ricerca-istruttore',
  templateUrl: './ricerca-istruttore.component.html',
  styleUrls: ['./ricerca-istruttore.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RicercaIstruttoreComponent implements OnInit {

  criteriRicercaOpened: boolean = true;
  showResults = false;
  user: UserInfoSec;

  nome: string;
  cognome: string;
  codiceFiscale: string;

  bandi: Array<CodiceDescrizioneDTO>;
  bandoSelected: CodiceDescrizioneDTO;

  tipoIstruttoreSelect: number = 1; // 1 IstruttoreADG,  2 StruttoreAffidamenti

  tipoIstruttoreCercato: number;

  idOperazione: number;

  showImpegniButtons: boolean = false;

  // tabelle
  displayedColumns: string[] = ['select', 'codiceFiscale', 'cognome', 'nome', 'numProgAssoc', 'numBandiLineaAssoc'];
  dataSource: MatTableDataSource<IstruttoreShowDTO>;

  selection = new SelectionModel<IstruttoreShowDTO>(true, []);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedCercaBandi: boolean;
  loadedCercaIstruttore: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  LS_TIPO_ITRUTTORE_SELECTED_KEY = 'ricercaIstruttore_tipoIstruttoreSelected'
  LS_ANAGRAFICA_ITRUTTORE_SELECTED_KEY = 'ricercaIstruttore_anagraficaIstruttoreSelected'

  constructor(
    private activatedRoute: ActivatedRoute,
    private associazioneIstruttoreProgettiService: AssociazioneIstruttoreProgettiService,
    private userService: UserService,
    private router: Router,
    private handleExceptionService: HandleExceptionService,
    private navigationService: NavigationAssociazioneIstruttoreProgettiService
  ) {
    this.dataSource = new MatTableDataSource();
  }

  ngAfterViewInit() {
    // this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {

    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

        this.getTipoIstruttoreFromLocalStorage();

        this.loadedCercaBandi = true;
        this.subscribers.cerca = this.associazioneIstruttoreProgettiService.cercaBandi(this.user).subscribe(data => {
          this.bandi = data;
          this.loadedCercaBandi = false;
          this.loadData();
          this.cerca();
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di cerca dei bandi.");
          this.loadedCercaBandi = false;
        });
      }
    });

  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }


  getTipoIstruttoreFromLocalStorage() {

    let tipoIstruttoreSelectTemp = localStorage.getItem(this.LS_TIPO_ITRUTTORE_SELECTED_KEY);
    if (tipoIstruttoreSelectTemp) {
      this.tipoIstruttoreSelect = +tipoIstruttoreSelectTemp;
    }
    else {
      this.tipoIstruttoreSelect = 1;
      this.setTipoIstruttoreToLocalStorage(this.tipoIstruttoreSelect)
    }

  }

  setTipoIstruttoreToLocalStorage(item: number) {
    let itemStr = item.toString();
    localStorage.setItem(this.LS_TIPO_ITRUTTORE_SELECTED_KEY, itemStr)
  }


  onTipoIstruttoreChange() {
    this.setTipoIstruttoreToLocalStorage(this.tipoIstruttoreSelect);
  }

  // ------------ Inizio gestione checkbox tabelle ------------
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    var numRows;
    if (this.dataSource == undefined) {
      numRows = 0;
    } else {
      numRows = this.dataSource.data.length;
    }

    return numSelected === numRows;
  }

  checkboxLabel(row?: IstruttoreShowDTO): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.idSoggetto + 1}`;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));

    if (this.selection.selected.length == 0) {
      this.showImpegniButtons = false;
    } else {
      this.showImpegniButtons = true;
    }
  }

  changeSingleRow(row: any) {
    if (this.selection.selected.length == 1) {
      this.selection.clear();
    }

    this.selection.toggle(row);

    if (this.selection.selected.length == 0) {
      this.showImpegniButtons = false;
    } else {
      this.showImpegniButtons = true;
    }
  }
  // ------------ Fine gestione checkbox tabelle ------------

  // ------------ Inizio gestione dati navigazione ------------
  saveDataForNavigation() {
    this.navigationService.bandoSelezionato = this.bandoSelected;
    this.navigationService.cognomeSelezionato = this.cognome;
    this.navigationService.nomeSelected = this.nome;
    this.navigationService.codiceFiscaleSelected = this.codiceFiscale;
    this.navigationService.selectionSelezionata = this.selection;
    this.salvaPaginator();
  }

  salvaPaginator() {
    if (this.dataSource && this.dataSource.paginator) {
      this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
      this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    }
  }

  ripristinaPaginator() {
    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
      this.paginator.pageIndex = this.navigationService.paginatorPageIndexTable;
      this.paginator.pageSize = this.navigationService.paginatorPageSizeTable;
      this.dataSource.paginator = this.paginator;
    }
  }

  loadData() {
    if (this.navigationService.cognomeSelezionato) {
      this.cognome = this.navigationService.cognomeSelezionato;
    }

    if (this.navigationService.nomeSelected) {
      this.nome = this.navigationService.nomeSelected;
    }

    if (this.navigationService.codiceFiscaleSelected) {
      this.codiceFiscale = this.navigationService.codiceFiscaleSelected;
    }

    if (this.navigationService.bandoSelezionato) {
      this.bandoSelected = this.bandi.find(n => n.codice === this.navigationService.bandoSelezionato.codice);
    }

    /*if (this.navigationService.cognomeSelezionato || this.navigationService.nomeSelected || this.navigationService.codiceFiscaleSelected || this.navigationService.bandoSelezionato) {
      this.showResults = true;

      this.loadedCercaIstruttore = true;
      this.subscribers.cercaIstruttore = this.associazioneIstruttoreProgettiService.cercaIstruttore(this.user, this.user.idSoggetto.toString(), this.nome, this.cognome, this.codiceFiscale, this.bandoSelected == undefined ? undefined : this.bandoSelected.codice).subscribe(data => {
        var comodo = new Array<IstruttoreShowDTO>();
        data.forEach(element => {
          comodo.push(new IstruttoreShowDTO(element.codiceFiscale, element.cognome, element.nome, element.totaleProgettiAssociati, element.idBando, element.idProgetto, element.idSoggetto, element.progettiAssociati, element.idPersonaFisica, false));
        });
        this.dataSource = new MatTableDataSource(comodo);
        this.paginator.length = comodo.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        this.loadedCercaIstruttore = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di cerca degli istruttori");
        this.loadedCercaIstruttore = false;
      });
    }*/
  }
  // ------------ Fine gestione dati navigazione ------------

  cerca() {
    this.resetMessageError();
    this.showResults = true;
    console.log(this.tipoIstruttoreSelect)
    this.tipoIstruttoreCercato = this.tipoIstruttoreSelect;
    let descBreveTipoAnagrafica: string;
    if (this.tipoIstruttoreSelect == 1) {
      descBreveTipoAnagrafica = undefined;
    }
    else {
      descBreveTipoAnagrafica = 'ISTR-AFFIDAMENTI'
    }
    this.loadedCercaIstruttore = true;
    this.subscribers.cercaIstruttore = this.associazioneIstruttoreProgettiService.cercaIstruttore(this.user, this.user.idSoggetto.toString(), this.nome, this.cognome,
      this.codiceFiscale, this.bandoSelected == undefined ? undefined : this.bandoSelected.codice, descBreveTipoAnagrafica).subscribe(data => {
        var comodo = new Array<IstruttoreShowDTO>();
        data.forEach(element => {
          comodo.push(new IstruttoreShowDTO(element.codiceFiscale, element.cognome, element.nome, element.totaleProgettiAssociati,
            element.totaleBandiLineaAssociati, element.idBando, element.idProgetto, element.idSoggetto, element.progettiAssociati,
            element.bandiLineaAssociati, element.idPersonaFisica, false, false, element.descBreveTipoAnagrafica));
        });
        this.dataSource = new MatTableDataSource(comodo);
        if (this.navigationService.selectionSelezionata && this.navigationService.selectionSelezionata.selected && this.navigationService.selectionSelezionata.selected.length) {
          this.ripristinaPaginator();
        } else {
          this.paginator.length = comodo.length;
          this.paginator.pageIndex = 0;
          this.dataSource.paginator = this.paginator;
        }
        this.criteriRicercaOpened = false;
        this.loadedCercaIstruttore = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di cerca degli istruttori");
        this.loadedCercaIstruttore = false;
      });

  }

  gestisciAss() {
    this.saveDataForNavigation();
    this.setAnagraficaIstruttoreSelected(this.selection.selected[0].descBreveTipoAnagrafica);
    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/gestioneAssociazione?idSoggettoIstruttore=` + this.selection.selected[0].idSoggetto + `&cognome=` + this.selection.selected[0].cognome + `&nome=` + this.selection.selected[0].nome + `&codFisc=` + this.selection.selected[0].codiceFiscale + `&tpAnagr=` + this.selection.selected[0].descBreveTipoAnagrafica + `&idBando=` + this.selection.selected[0].idBando);
  }

  setAnagraficaIstruttoreSelected(item: string) {

    localStorage.setItem(this.LS_ANAGRAFICA_ITRUTTORE_SELECTED_KEY, item);

  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    var element = document.getElementById('scrollId')
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (this.loadedCercaBandi || this.loadedCercaIstruttore) {
      return true;
    }
    return false;
  }
}
