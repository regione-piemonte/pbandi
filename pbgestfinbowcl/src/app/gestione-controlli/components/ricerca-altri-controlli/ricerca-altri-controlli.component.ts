/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ControlloLocoVo } from '../../commons/controllo-loco-vo';
import { searchControlliDTO } from '../../commons/dto/searchControlliDTO';
import { ControlliService } from '../../services/controlli.service';
import { NavigationControlliService } from '../../services/navigation-controlli.service';

@Component({
  selector: 'app-ricerca-altri-controlli',
  templateUrl: './ricerca-altri-controlli.component.html',
  styleUrls: ['./ricerca-altri-controlli.component.scss']
})
export class RicercaAltriControlliComponent implements OnInit {
  userloaded: boolean = true;
  subscribers: any = {};
  user: UserInfoSec;
  idUtente: any;
  messageError: string;
  isMessageErrorVisible: boolean;
  criteriRicercaOpened: boolean = true;
  denIns: string;
  suggesttionnull: AttivitaDTO = new AttivitaDTO();
  listaDenomin: AttivitaDTO[] = [];
  descBanIns: string;
  listaBando: AttivitaDTO[] = [];
  listaCodiceProgetto: AttivitaDTO[] = [];
  listaStatoControllo: AttivitaDTO[] = [];
  idStatoControllo: number;
  loadedStControllo: boolean = true;
  numCampionamento: string;
  importoDaControllareDaFormatted: string;
  importoDaControllareDa: number;
  importoDaControllareA: number;
  importoDaControllareAFormatted: string;
  importoIrregolareDa: number;
  importoIrregolareDaFormatted: string;
  importoIrregolareA: number;
  importoIrregolareAFormatted: string;
  codProIns: string;
  isStorico: boolean;
  searchDTO: searchControlliDTO = new searchControlliDTO();
  denomDTO: AttivitaDTO = new AttivitaDTO;
  sortedData = new MatTableDataSource<ControlloLocoVo>([]);
  isControlli: boolean = false;
  controlliLoaded: boolean = true;
  displayedColumns: string[] = ['descBando', 'codVisualizzato', "denominazione", "descTipoControllo", "descStatoControllo", "importoDaControllare", "descAutoritaControllante", "descAttivita", "settings"];
  isControls: boolean;
  bandoDTO: AttivitaDTO = new AttivitaDTO;
  codProgDTO: AttivitaDTO = new AttivitaDTO;
  listaAutoritaControllante: AttivitaDTO[] = [];
  idAutoritaControllante: number;
  tipologiaControllo: string;
  numProtocollo: string;

  suggestDTO: searchControlliDTO = new searchControlliDTO();
  isNullCritterioRicerca: boolean;

  constructor(
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private controlliService: ControlliService,
    public sharedService: SharedService,
    private router: Router,
    private navigationService: NavigationControlliService,
  ) { }


  @ViewChild("controlli") paginator: MatPaginator;
  @ViewChild("controlliSort") sort: MatSort;
  ngOnInit(): void {
    this.userloaded = false;
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.userloaded = true;

        this.loadedStControllo = false;
        this.subscribers.staprop = this.controlliService.getListaStatoControllo().subscribe({
          next: data => this.listaStatoControllo = data,
          error: errore => console.log(errore),
          complete: () => this.loadedStControllo = true
        });

        this.subscribers.listaAutoritaControllante = this.controlliService.getAutoritaControlante().subscribe({
          next: data => this.listaAutoritaControllante = data,
          error: errore => console.log(errore),
          complete: () => this.loadedStControllo = true
        });

        if (this.navigationService.searchControlliDTOSceltoAltro) {
          this.searchDTO = this.navigationService.searchControlliDTOSceltoAltro;
          this.search();
        }
      }
    });
  }
  suggest(id: number, value: string) {

    switch (id) {
      case 1:
        if (value.length >= 3) {
          this.listaDenomin = new Array<AttivitaDTO>();
          this.suggesttionnull.descAttivita = 'caricamento...';
          this.listaDenomin.push(this.suggesttionnull);
          this.suggestDTO.idProgetto = this.checkProgetto();
          this.suggestDTO.progrBandoLinea = this.checkProgBando();
          this.suggestDTO.value = value;
          this.subscribers.listaDenomin = this.controlliService.getListaSuggest(id, this.suggestDTO).subscribe(data => {
            if (data.length > 0) {
              this.listaDenomin = data;
            } else {
              this.listaDenomin = new Array<AttivitaDTO>();
              this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
              this.listaDenomin.push(this.suggesttionnull);
            }
          });
        }
        break;
      case 2:
        this.listaBando = new Array<AttivitaDTO>();
        if (value.length >= 3) {
          this.suggesttionnull.descAttivita = 'caricamento...';
          this.listaBando.push(this.suggesttionnull);
          this.suggestDTO.idProgetto = this.checkProgetto();
          this.suggestDTO.idSoggetto = this.checkSoggetto();
          this.suggestDTO.value = value;
          this.subscribers.listaBando = this.controlliService.getListaSuggest(id, this.suggestDTO).subscribe(data => {
            if (data.length > 0) {
              this.listaBando = data;
            } else {
              this.listaBando = new Array<AttivitaDTO>();
              this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
              this.listaBando.push(this.suggesttionnull);
            }
          });
        }
        break;

      case 3:
        this.listaCodiceProgetto = new Array<AttivitaDTO>();
        if (value.length >= 3) {
          this.suggesttionnull.descAttivita = 'caricamento...';
          this.listaCodiceProgetto.push(this.suggesttionnull);
          this.suggestDTO.idSoggetto = this.checkSoggetto();
          this.suggestDTO.progrBandoLinea = this.checkProgBando();
          this.suggestDTO.value = value;
          this.subscribers.listaCodiceProgetto = this.controlliService.getListaSuggest(4, this.suggestDTO).subscribe(data => {
            if (data.length > 0) {
              this.listaCodiceProgetto = data;
            } else {
              this.listaCodiceProgetto = new Array<AttivitaDTO>();
              this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
              this.listaCodiceProgetto.push(this.suggesttionnull);
            }
          });
        }
        break;


      default:
        break;
    }
  }
  setImportoDaControllareDa() {
    this.importoDaControllareDa = this.sharedService.getNumberFromFormattedString(this.importoDaControllareDaFormatted);
    if (this.importoDaControllareDa !== null) {
      this.importoDaControllareDaFormatted = this.sharedService.formatValue(this.importoDaControllareDa.toString());
    }
  }
  setImportoDaControllareA() {
    this.importoDaControllareA = this.sharedService.getNumberFromFormattedString(this.importoDaControllareAFormatted);
    if (this.importoDaControllareA !== null) {
      this.importoDaControllareAFormatted = this.sharedService.formatValue(this.importoDaControllareA.toString());
    }
  }
  setImportoIrregolareDa() {
    this.importoIrregolareDa = this.sharedService.getNumberFromFormattedString(this.importoIrregolareDaFormatted);
    if (this.importoIrregolareDa !== null) {
      this.importoIrregolareDaFormatted = this.sharedService.formatValue(this.importoIrregolareDa.toString());
    }
  }
  setImportoIrregolareA() {
    this.importoIrregolareA = this.sharedService.getNumberFromFormattedString(this.importoIrregolareAFormatted);
    if (this.importoIrregolareA !== null) {
      this.importoIrregolareAFormatted = this.sharedService.formatValue(this.importoIrregolareA.toString());
    }
  }
  search() {
    this.controlliLoaded = false;
    this.isControlli = false;
    this.subscribers.getEleconControlli = this.controlliService.getElencoAltriControlli(this.searchDTO).subscribe(data => {
      if (data) {
        this.isControlli = true;
        this.controlliLoaded = true;
        this.sortedData = new MatTableDataSource<ControlloLocoVo>(data);
        this.paginator.length = data.length;
        this.paginator.pageIndex = 0;
        this.sortedData.paginator = this.paginator;
        this.sortedData.sort = this.sort;
      }
      data?.length > 0 ? this.isControls = true : this.isControls = false;
    });

    this.criteriRicercaOpenClose();

  }

  compilaCampiPerRicerca() {
    this.resetMessageError();
    this.isNullCritterioRicerca = true;


    this.searchDTO = new searchControlliDTO();


    if (this.importoDaControllareA != null) {
      this.searchDTO.importoDaControllareA = this.importoDaControllareA;
      this.isNullCritterioRicerca = false;
    }
    if (this.importoDaControllareDa != null) {
      this.searchDTO.importoDaControllareDa = this.importoDaControllareDa;
      this.isNullCritterioRicerca = false;
    }
    if (this.importoIrregolareA != null) {
      this.searchDTO.importoIrregolareA = this.importoIrregolareA;
      this.isNullCritterioRicerca = false;
    }
    if (this.importoIrregolareDa != null) {
      this.searchDTO.importoIrregolareDa = this.importoIrregolareDa;
      this.isNullCritterioRicerca = false;
    }

    // this.importoDaControllareDa != null ? this.searchDTO.importoDaControllareDa = this.importoDaControllareDa : this.searchDTO.importoDaControllareA;
    // this.importoIrregolareA != null ? this.searchDTO.importoIrregolareA = this.importoIrregolareA : this.searchDTO.importoIrregolareA;
    // this.importoIrregolareDa != null ? this.searchDTO.importoIrregolareDa = this.importoIrregolareDa : this.searchDTO.importoIrregolareDa;

    this.searchDTO.idProgetto = this.checkProgetto();
    this.searchDTO.idSoggetto = this.checkSoggetto();
    this.searchDTO.progrBandoLinea = this.checkProgBando();

    if (this.idStatoControllo != null) {
      this.searchDTO.idStatoControllo = this.idStatoControllo;
      this.isNullCritterioRicerca = false;
    }
    if (this.tipologiaControllo!= null) {
      this.searchDTO.tipoControllo = this.tipologiaControllo;
      this.isNullCritterioRicerca = false;
    }
    if (this.idAutoritaControllante!= null) {
      this.searchDTO.idAutoritaControllante = this.idAutoritaControllante;
      this.isNullCritterioRicerca = false;
    }
    if (this.numProtocollo!= null && this.numProtocollo.toString().length>0) {
      this.searchDTO.numProtocollo = this.numProtocollo;
      this.isNullCritterioRicerca = false;
    }
    if(!this.isNullCritterioRicerca){
      this.search()
    } else {
      this.showMessageError("compilare almeno un campo")
    }
  }

  goToGestione(element: ControlloLocoVo, bool: number) {

    this.saveDataForNavigation();
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI + "/gestioneAltriControlli"],
      {
        queryParams: {
          idControllo: element.idControllo,
          idProgetto: element.idProgetto,
          dettaglio: bool,
          idStatoChecklist: element.idStatoChecklist
        }
      }
    );

  }

  saveDataForNavigation() {

    // let searchDTOSalvato = new searchControlliDTO();
    // if (this.importoDaControllareA != null) searchDTOSalvato.importoDaControllareA = this.importoDaControllareA;
    // this.importoDaControllareDa != null ? searchDTOSalvato.importoDaControllareDa = this.importoDaControllareDa : this.importoDaControllareA=null;
    // this.importoIrregolareA != null ? searchDTOSalvato.importoIrregolareA = this.importoIrregolareA : this.importoIrregolareA=null;
    // this.importoIrregolareDa != null ? searchDTOSalvato.importoIrregolareDa = this.importoIrregolareDa : this.importoDaControllareDa=null;
    // searchDTOSalvato.tipoControllo = this.tipologiaControllo;
    // searchDTOSalvato.idAutoritaControllante = this.idAutoritaControllante;
    // searchDTOSalvato.numProtocollo = this.numProtocollo
    // searchDTOSalvato.idProgetto = this.checkProgetto();
    // searchDTOSalvato.idSoggetto = this.checkSoggetto();
    // searchDTOSalvato.progrBandoLinea = this.checkProgBando();
    // if (this.idStatoControllo != null)searchDTOSalvato.idStatoControllo = this.idStatoControllo;

    this.navigationService.searchControlliDTOSceltoAltro = this.searchDTO;
    this.salvaSortPaginator();
  }

  salvaSortPaginator() {
    if (this.sortedData && this.sortedData.data && this.sortedData.data.length) {
      if (this.sortedData.sort) {
        this.navigationService.sortDirectionTable = this.sortedData.sort.direction;
        this.navigationService.sortActiveTable = this.sortedData.sort.active;
      }
      if (this.sortedData.paginator) {
        this.navigationService.paginatorPageIndexTable = this.sortedData.paginator.pageIndex;
        this.navigationService.paginatorPageSizeTable = this.sortedData.paginator.pageSize;
      }
    }
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
  isLoading() {
    if (!this.userloaded || !this.loadedStControllo || !this.controlliLoaded) {
      return true;
    }
    return false;
  }
  private checkProgBando() {

    let progBandoLinea: any;
    if (this.descBanIns != null && this.descBanIns.toString().length > 0) {
      const bando = this.listaBando.find(b => b.descAttivita == this.descBanIns)
      progBandoLinea = bando.progBandoLinea;
      this.isNullCritterioRicerca = false;
    } else {
      progBandoLinea = null;
    }

    return progBandoLinea;
  }
  private  checkProgetto() {
    let idProgetto: any;
    if (this.codProIns != null && this.codProIns.toString().length > 0) {
      var codv = this.listaCodiceProgetto.find(p => p.descAttivita == this.codProIns)
      idProgetto = codv.idAttivita;
      this.isNullCritterioRicerca = false;
    } else {
      idProgetto = null;
    }
    return idProgetto;
  }
  private checkSoggetto() {
    let idSoggetto: any;
    if (this.denIns != null && this.denIns.toString().length > 0) {
      var den = this.listaDenomin.find(de => de.descAttivita == this.denIns);
      idSoggetto = den.idSoggetto;
      this.isNullCritterioRicerca = false;
    } else {
      idSoggetto = null;
    }

    return idSoggetto;
  }
}
