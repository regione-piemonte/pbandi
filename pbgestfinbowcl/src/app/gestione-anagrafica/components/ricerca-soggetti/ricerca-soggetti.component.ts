/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Constants } from '../../../core/commons/util/constants';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { SuggestionService } from '../../services/suggestion-service.service';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { MatSort } from '@angular/material/sort';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { CFB } from 'xlsx';

interface Persona {
  value: string;
}

@Component({
  selector: 'app-ricerca-soggetti',
  templateUrl: './ricerca-soggetti.component.html',
  styleUrls: ['./ricerca-soggetti.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RicercaSoggettiComponent implements OnInit {

  //COSTANTE NECESSARIA AL ROUTING TRA COMPONENTI... DA ACCODARE AL DRAWER COMPONENT
  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;

  //VARIABILI STRUTTURALI
  myControl = new FormControl();
  //DATI TABELLA
  displayedColumns: string[] = ['descTipoSogg', 'idSoggetto', 'denominazione', 'cf', 'pIva', 'blocco', 'azioni'];
  dataSource: any;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  //LOADED
  loadedNormative: boolean;
  loadedCerca: boolean = true;
  //OBJECT SUBSCRIBER
  subscribers: any = {};

  //VARIABILI DI CLASSE
  options: number[] = [];
  filteredOptions: Observable<number[]>;
  personaSelected: string;
  idSoggetto: any = "";
  codiceFiscale: string = "";
  partitaIva: string = "";
  denominazione: string = "";
  cognome: string = "";
  nome: string = "";
  numeroDomanda: string = "";
  codProgetto: string = "";
  descTipoSogg: string = "";

  idDomanda: number = 0;
  idProgetto: number = 0;

  criteriRicercaOpened: boolean = true;
  showTable = false;
  emptyTable = false;
  spinner = false;
  idOperazione: number;
  user: UserInfoSec;
  ndg: any;
  codProgetto2: any;
  numeroDomanda2: any;




  constructor(
    private _snackBar: MatSnackBar,
    private userService: UserService,
    private suggestService: SuggestionService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
  ) {
    this.dataSource = new MatTableDataSource([]);

  }

  ngAfterViewInit() {

  }

  ngOnInit(): void {

    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.loadData();
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {

      this.dataSource.paginator.firstPage();
    }
  }



  goTo() {
    this.router.navigate(["/drawer/" + this.idOp + "/anagraficaBeneficiario"]);
  }

  loadData() {

  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  getIdSoggetto(idFromEvent) {
    this.spinner = true;
    if (idFromEvent != null && idFromEvent != '') {
      this.suggestService.getSuggestionIdSoggetto(idFromEvent).subscribe(data => {
        if (data) {
          this.spinner = false;
          this.options = data;
        }
      })
    } else {
      this.options = [];
      this.spinner = false;
    }
  }


  getCodiceFiscale(cfFromEvent: string) {
    this.spinner = true;
    if (cfFromEvent != null && cfFromEvent != '') {
      this.suggestService.getSuggestionCF(cfFromEvent.toUpperCase()).subscribe(data => {
        if (data) {
          this.spinner = false;
          this.options = data;
        }
      })
    } else {
      this.options = [];
      this.spinner = false;
    }
  }

  getPartitaIva(pIvaFromEvent) {
    this.spinner = true;
    if (pIvaFromEvent != null && pIvaFromEvent != '') {
      this.suggestService.getSuggestionPIva(pIvaFromEvent).subscribe(data => {
        if (data) {
          this.spinner = false;
          this.options = data;
        } else {
          this.spinner = false;
        }
      })
    } else {
      this.options = [];
      this.spinner = false;
    }
  }

  getDenominazione(denominazioneFromEvent) {
    this.spinner = true;
    if (denominazioneFromEvent != null && denominazioneFromEvent != '') {
      this.suggestService.getSuggestionDenominazione(denominazioneFromEvent).subscribe(data => {
        if (data) {
          this.spinner = false;
          this.options = data;
        } else {
          this.spinner = false;
        }
      })
    } else {
      this.options = [];
      this.spinner = false;
    }
  }
  getCognome(denominazioneFromEvent) {
    this.spinner = true;
    if (denominazioneFromEvent != null && denominazioneFromEvent != '') {
      this.suggestService.getSuggestionCognome(denominazioneFromEvent).subscribe(data => {
        if (data) {
          this.spinner = false;
          this.options = data;
        } else {
          this.spinner = false;
        }
      })
    } else {
      this.options = [];
      this.spinner = false;
    }
  }


  getIdDomanda(idDomandaFromEvent) {
    if (idDomandaFromEvent != null && idDomandaFromEvent != '') {
      this.suggestService.getSuggestionNumeroDomanda(idDomandaFromEvent).subscribe(data => {
        if (data) {
          this.options = data;
          this.spinner = false;
        }
      })
    } else {
      this.options = [];
      this.spinner = false;
    }
  }

  getIdProgetto(idProgettoFromEvent) {
    if (idProgettoFromEvent != null && idProgettoFromEvent != '') {
      this.suggestService.getSuggestionCodiceProgetto(idProgettoFromEvent).subscribe(data => {
        if (data) {
          this.options = data;
          this.spinner = false;
        }
      })
    } else {
      this.options = [];
      this.spinner = false;
    }
  }


  cerca() {
    console.log("siamo dentro la ricerca");
    console.log(this.descTipoSogg);

    if (this.descTipoSogg != "") {
      this.spinner = true;
      this.suggestService.cercaSoggetti(this.codiceFiscale, this.idSoggetto, this.partitaIva,
        this.denominazione, this.idDomanda, this.idProgetto, this.nome, this.cognome, this.descTipoSogg).subscribe(data => {
          if (data) {
            this.dataSource = data;
            this.paginator.length = data.length;
            this.paginator.pageIndex = 0;
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
            this.showTable = true;
            this.spinner = false;
          } else {
            this.emptyTable = true;
            this.spinner = false;
          }
        })
    }
  }

  test() {
    this.options = [];
  }


  timeout: any = null;

  private onKeySearchIdSoggetto(event: any) {
    if (event.target.value != "") {
      clearTimeout(this.timeout);
      var $this = this;
      this.timeout = setTimeout(function () {
        if (event.keyCode != 13) {
          $this.getIdSoggetto(event.target.value);
        }
      }, 1000);
    }
  }


  private onKeySearchCf(event: any) {
    if (event.target.value != "") {
      clearTimeout(this.timeout);
      var $this = this;
      this.timeout = setTimeout(function () {
        if (event.keyCode != 13) {
          $this.getCodiceFiscale(event.target.value);
        }
      }, 1000);
    }
  }


  private onKeySearchPIva(event: any) {
    if (event.target.value != "") {
      clearTimeout(this.timeout);
      var $this = this;
      this.timeout = setTimeout(function () {
        if (event.keyCode != 13) {
          $this.getPartitaIva(event.target.value);
        }
      }, 1000);
    }
  }

  private onKeySearchDenominazione(event: any) {
    if (event.target.value != "") {
      clearTimeout(this.timeout);
      var $this = this;
      this.timeout = setTimeout(function () {
        if (event.keyCode != 13) {
          $this.getDenominazione(event.target.value);
        }
      }, 1000);
    }
  }
  private onKeySearchCognome(event: any) {
    if (event.target.value != "") {
      clearTimeout(this.timeout);
      var $this = this;
      this.timeout = setTimeout(function () {
        if (event.keyCode != 13) {
          $this.getCognome(event.target.value);
        }
      }, 1000);
    }
  }


  private onKeySearchDomanda(event: any) {
    clearTimeout(this.timeout);
    var $this = this;
    this.timeout = setTimeout(function () {
      if (event.keyCode != 13) {
        $this.getIdDomanda(event.target.value);
      }
    }, 1000);
  }


  private onKeySearchProgetto(event: any) {
    clearTimeout(this.timeout);
    var $this = this;
    this.timeout = setTimeout(function () {
      if (event.keyCode != 13) {
        $this.getIdProgetto(event.target.value);
      }
    }, 1000);
  }


  isLoading() {
    if (!this.loadedNormative || !this.loadedCerca) {
      return true;
    }
    return false;
  }


  resetOptionsCf(cf, idSoggetto, descTipoSogg) {
    this.descTipoSogg = descTipoSogg;
    //SPINNER = TRUE
    this.suggestService.getAutofill(idSoggetto, descTipoSogg, null, null).subscribe(data => {
      if (data) {
        //SPINNER = FALSE
        this.idSoggetto = data.idSoggetto;
        this.partitaIva = data.pIva;
        this.denominazione = data.denominazione;
        this.cognome = data.cognome;
        this.nome = data.nome;
        // this.codProgetto = data.codVisualizzato;
        // this.numeroDomanda = data.numeroDomanda;
        this.codProgetto2 = data.codVisualizzato;
        this.numeroDomanda2 = data.numeroDomanda;
        this.ndg = data.ndg;
        this.codiceFiscale = data.cf
        this.idSoggetto = idSoggetto
        this.idDomanda = data.idDomanda
        this.idProgetto= data.idProgetto
      }
    });
    this.codiceFiscale = cf;
    this.options = [];
  }

  //DA CORREGGERE
  resetOptionsIdSogg(idSoggetto, descTipoSogg) {
    this.descTipoSogg = descTipoSogg;
    //SPINNER = TRUE

    if (idSoggetto != null) {
      this.suggestService.getAutofill(idSoggetto, descTipoSogg, null, null).subscribe(data => {
        if (data) {
          //SPINNER = FALSE
          this.codiceFiscale = data.cf;
          // this.idSoggetto = data.idSoggetto;
          this.partitaIva = data.pIva;
          this.denominazione = data.denominazione;
          this.cognome = data.cognome;
          this.nome = data.nome;
          // this.codProgetto = data.codVisualizzato;
          // this.numeroDomanda = data.numeroDomanda;
          this.codProgetto2 = data.codVisualizzato;
          this.numeroDomanda2 = data.numeroDomanda;
          this.ndg = data.ndg;
          this.codiceFiscale = data.cf
          this.idSoggetto = idSoggetto
          this.idDomanda = data.idDomanda
          this.idProgetto= data.idProgetto
        }
      });
    }

    this.idSoggetto = idSoggetto;
    this.options = [];
  }

  resetOptionsPiva(pIva, idSoggetto, descTipoSogg) {
    this.descTipoSogg = descTipoSogg;
    //SPINNER = TRUE
    if (idSoggetto != null) {
      this.suggestService.getAutofill(idSoggetto, descTipoSogg, null, null).subscribe(data => {
        if (data) {
          //SPINNER = FALSE
          this.codiceFiscale = data.cf;
          // this.idSoggetto = data.idSoggetto;
          this.partitaIva = data.pIva;
          this.denominazione = data.denominazione;
          this.cognome = data.cognome;
          this.nome = data.nome;
          //this.codProgetto = data.codVisualizzato;
          //this.numeroDomanda = data.numeroDomanda;
          this.codProgetto2 = data.codVisualizzato;
          this.numeroDomanda2 = data.numeroDomanda;
          this.ndg = data.ndg;
          this.codiceFiscale = data.cf
          this.idSoggetto = idSoggetto
          this.idDomanda = data.idDomanda
          this.idProgetto= data.idProgetto
        }
      });
    }
    this.partitaIva = pIva;
    this.options = [];
  }

  //DA CORREGGERE
  resetOptionsDenominazione(denominazione, idSoggetto, descTipoSogg) {
    this.descTipoSogg = descTipoSogg;
    //SPINNER = TRUE
    if (idSoggetto != null) {
      this.suggestService.getAutofill(idSoggetto, descTipoSogg, null, null).subscribe(data => {
        if (data) {
          //SPINNER = FALSE
          this.codiceFiscale = data.cf;
          // this.idSoggetto = data.idSoggetto;
          this.partitaIva = data.pIva;
          this.denominazione = data.denominazione;
          this.cognome = data.cognome;
          this.nome = data.nome;
          //this.codProgetto = data.codVisualizzato;
          //this.numeroDomanda = data.numeroDomanda;
          this.codProgetto2 = data.codVisualizzato;
          this.numeroDomanda2 = data.numeroDomanda;
          this.ndg = data.ndg;
          this.codiceFiscale = data.cf
          this.idSoggetto = idSoggetto
          this.idDomanda = data.idDomanda
          this.idProgetto= data.idProgetto
        }
      });
    }

    this.denominazione = denominazione;
    this.options = [];
  }

  //DA CORREGGERE
  resetCognome(cognome, idSoggetto, descTipoSogg) {
    this.descTipoSogg = descTipoSogg;
    //SPINNER = TRUE
    if (idSoggetto != null) {
      this.suggestService.getAutofill(idSoggetto, descTipoSogg, null, null).subscribe(data => {
        if (data) {
          //SPINNER = FALSE
          this.codiceFiscale = data.cf;
          // this.idSoggetto = data.idSoggetto;
          this.partitaIva = data.pIva;
          this.denominazione = data.denominazione;
          this.cognome = data.cognome;
          this.nome = data.nome;
          //this.codProgetto = data.codVisualizzato;
          //this.numeroDomanda = data.numeroDomanda;
          this.codProgetto2 = data.codVisualizzato;
          this.numeroDomanda2 = data.numeroDomanda;
          this.ndg = data.ndg;
          this.codiceFiscale = data.cf
          this.idSoggetto = idSoggetto
          this.idDomanda = data.idDomanda
          this.idProgetto= data.idProgetto
        }
      });
    }

    this.cognome = cognome;
    this.options = [];
  }


  //DA CORREGGERE
  resetNome(nome, idSoggetto, descTipoSogg) {
    this.descTipoSogg = descTipoSogg;
    //SPINNER = TRUE
    if (idSoggetto != null) {
      this.suggestService.getAutofill(idSoggetto, descTipoSogg, null, null).subscribe(data => {
        if (data) {
          //SPINNER = FALSE
          this.codiceFiscale = data.cf;
          // this.idSoggetto = data.idSoggetto;
          this.partitaIva = data.pIva;
          this.denominazione = data.denominazione;
          this.cognome = data.cognome;
          this.nome = data.nome;
          //this.codProgetto = data.codVisualizzato;
          //this.numeroDomanda = data.numeroDomanda;
          this.codProgetto2 = data.codVisualizzato;
          this.numeroDomanda2 = data.numeroDomanda;
          this.ndg = data.ndg;
          this.codiceFiscale = data.cf
          this.idSoggetto = idSoggetto
          this.idDomanda = data.idDomanda
          this.idProgetto= data.idProgetto
        }
      });
    }

    this.nome = nome;
    this.options = [];
  }

  resetOptionsIdDomanda(numeroDomanda, idSoggetto, descTipoSogg, idDomanda) {
    console.log(descTipoSogg);
    
    if (this.idSoggetto == 0) {
      this.idSoggetto = idSoggetto;
    }
    if (idSoggetto != null) {
      this.suggestService.getAutofill(idSoggetto, descTipoSogg, null, null).subscribe(data => {
        if (data) {
          //SPINNER = FALSE
          this.codiceFiscale = data.cf;
          // this.idSoggetto = data.idSoggetto;
          this.partitaIva = data.pIva;
          this.denominazione = data.denominazione;
          this.cognome = data.cognome;
          this.nome = data.nome;
          this.codProgetto = data.codVisualizzato;
          this.numeroDomanda = data.numeroDomanda;
          this.ndg = data.ndg;
          this.codiceFiscale = data.cf
          this.idSoggetto = idSoggetto
          this.idDomanda = data.idDomanda
          this.idProgetto= data.idProgetto
        }
      });
    }
    this.descTipoSogg = descTipoSogg;
    this.numeroDomanda = numeroDomanda;
    this.options = [];
  }


  resetOptionsDomanda(idSoggetto, descTipoSogg) {
    this.descTipoSogg = descTipoSogg;
    //SPINNER = TRUE
    if (idSoggetto != null) {
      this.suggestService.getAutofill(idSoggetto, descTipoSogg, null, null).subscribe(data => {
        if (data) {
          //SPINNER = FALSE
          this.codiceFiscale = data.cf;
          // this.idSoggetto = data.idSoggetto;
          this.partitaIva = data.pIva;
          this.denominazione = data.denominazione;
          this.cognome = data.cognome;
          this.nome = data.nome;
          this.codProgetto = data.codVisualizzato;
          this.numeroDomanda = data.numeroDomanda;
          this.ndg = data.ndg;
          this.codiceFiscale = data.cf
          this.idSoggetto = idSoggetto
          this.idDomanda = data.idDomanda
          this.idProgetto= data.idProgetto
        }
      });
    }
    this.options = [];
  }

  resetOptionsIdProgetto(codProgetto, idSoggetto, idProgetto, descTipoSogg) {
    // if (this.idSoggetto == 0) {
    //   this.idSoggetto = idSoggetto;
    // }
    this.descTipoSogg = descTipoSogg;
    if (idSoggetto != null) {
      this.suggestService.getAutofill(idSoggetto, descTipoSogg, null, null).subscribe(data => {
        if (data) {
          //SPINNER = FALSE
          this.codiceFiscale = data.cf;
          // this.idSoggetto = data.idSoggetto;
          this.partitaIva = data.pIva;
          this.denominazione = data.denominazione;
          this.cognome = data.cognome;
          this.nome = data.nome;
          this.codProgetto = data.codVisualizzato;
          this.numeroDomanda = data.numeroDomanda;
          this.ndg = data.ndg;
          this.codiceFiscale = data.cf
          this.idSoggetto = idSoggetto
          this.idDomanda = data.idDomanda
          this.idProgetto= data.idProgetto
        }
      });
    }

    
    this.codProgetto = codProgetto;
    //this.idProgetto = idProgetto;
    this.options = [];
  }

  visualizzaBeneficiario(row) {
    console.log("id Progetto ricerca" + this.idProgetto);

    //TOCCA MODIFICARE IL SERVIZIO PER FARMI TORNARE ANCHE L'ID ENTE GIURIDICO O L'ID PERSONA FISICA
    if (row.descTipoSogg == "Persona Fisica") {
      this.router.navigate(["/drawer/" + this.idOp + "/anagraficaBeneficiario"],
        {
          queryParams: {
            idSoggetto: row.idSoggetto,
            ndg: this.ndg,
            tipoSoggetto: row.descTipoSogg,
            idDomanda: (this.numeroDomanda!=null && this.numeroDomanda.toString().length>0)?this.numeroDomanda:this.numeroDomanda2,
            idProgetto: this.idProgetto, 
            numeroDomanda: this.idDomanda
          }
        });
    } else {
      this.router.navigate(["/drawer/" + this.idOp + "/anagraficaBeneficiario"],
        {
          queryParams: {
            idSoggetto: row.idSoggetto,
            ndg: this.ndg,
            //idTipoSoggetto: row.idEnteGiuridico,
            tipoSoggetto: row.descTipoSogg,
            idDomanda: (this.numeroDomanda!=null && this.numeroDomanda.toString().length>0)?this.numeroDomanda:this.numeroDomanda2,
            idProgetto: this.idProgetto,
            numeroDomanda: this.idDomanda
          }
        });
    }
  }

  // modificaBeneficiario(row) {
  //   if (row.descTipoSogg == "Persona Giuridica") {
  //     this.router.navigate(["/drawer/" + this.idOp + "/modificaAnagraficaEG"], { queryParams: { idSoggetto: row.idSoggetto, tipoSoggetto: row.descTipoSogg, idDomanda: row.numeroDomanda, idProgetto: this.idProgetto } });
  //   } else {
  //     this.router.navigate(["/drawer/" + this.idOp + "/modificaAnagraficaPF"], { queryParams: { idSoggetto: row.idSoggetto, idDomanda: row.numeroDomanda, idProgetto: this.idProgetto } });
  //   }
  // }

  azzeraCampi() {
    this.codProgetto = null;
    this.codiceFiscale = null;
    this.partitaIva = null;
    this.cognome = null;
    this.nome = null;
    this.denominazione = null;
    this.numeroDomanda = null;
    this.ndg = null;
    this.idSoggetto = null;
    this.numeroDomanda2=null;
    this.codProgetto2=null; 
    // this.resetCognome('', '', '')
    // this.resetNome('', '', ''); 
    // this.resetOptionsCf('','', '')
    // this.resetOptionsIdSogg( '', '')
    // this.resetOptionsPiva('', '', '')
    // this.resetOptionsIdProgetto('', null, '', '')
    // this.resetOptionsDenominazione('', '', '')
    // this.resetOptionsIdDomanda('', '', '', '')
    // this.idSoggetto=0; 
  }

}
