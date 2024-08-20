/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Constants, HandleExceptionService } from '@pbandi/common-lib';
import { DenominazioneSuggestVO } from '../../commons/denominazione-suggest-vo';
import { ProgettoSuggestVO } from '../../commons/progetto-suggest-vo';
import { RicercaDistintaPropErogPlusVO } from '../../commons/ricerca-distinta-prop-erog-plus-vo';
import { RicercaDistintaPropErogVO } from '../../commons/ricerca-distinta-prop-erog-vo';
import { DistinteResponseService } from '../../services/distinte-response.service';
import { FormElencoDistinte } from '../../commons/form-elenco-distinte';
import { NavigationElencoDistinteService } from '../../services/navigation-elenco-distinte.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DistintaVO } from '../../commons/distinta-vo';
import { Router } from '@angular/router';
import { AgevolazioneSuggestVO } from '../../commons/agevolazione-suggest-vo';
import { BandoSuggestVO } from '../../commons/bando-suggest-vo';
import { SuggestIdDescVO } from 'src/app/revoche/commons/suggest-id-desc-vo';
import { SuggestVO } from '../../commons/suggest-vo';

@Component({
  selector: 'app-elenco-distinte',
  templateUrl: './elenco-distinte.component.html',
  styleUrls: ['./elenco-distinte.component.scss']
})
export class ElencoDistinteComponent implements OnInit {

  spinner: boolean = false;
  error: boolean = false;
  success: boolean = false;
  messageError: string;
  isMessageErrorVisible: boolean;
  criteriRicercaOpened: boolean = true;

  excelFile: string = 'campionamenti.xlsx';

  //mostra tabelle
  mostraTabGenerica: boolean = false;
  mostraTabBenProg: boolean = false;

  public myForm: FormGroup;

  //suggerimenti form
  suggBeneficiario: DenominazioneSuggestVO[] = [];
  suggProgetto: ProgettoSuggestVO[] = [];
  suggDistinta: DistintaVO[] = [];
  suggBando: BandoSuggestVO[] = [];
  suggAgevolazione: AgevolazioneSuggestVO[] = [];
  suggCodiceFondoFinpis: SuggestIdDescVO[] = [];

  //table
  displayedColumnsGenerica: string[] = ["numeroDistinta", "descrizione", "dataCreazioneDistinta", "utente", "statoDistinta", "statoIterAutorizzativo", "actions"];
  displayedColumnsBenProg: string[] = ["numeroDistinta", "descrizione", "tipologiaDistinta", "dataCreazioneDistinta", "utente", "statoDistinta", "statoIterAutorizzativo", "progetto", "beneficiario", "importoLordo",  "importoNetto", "dataContabileErogazione", "importoErogato"];
  dataSourceGenerica: MatTableDataSource<RicercaDistintaPropErogVO> = new MatTableDataSource<RicercaDistintaPropErogVO>([]);
  dataSourceBenProg: MatTableDataSource<RicercaDistintaPropErogPlusVO> = new MatTableDataSource<RicercaDistintaPropErogPlusVO>([]);
  listTabGenerica: Array<RicercaDistintaPropErogVO>;
  listTabBenProg: Array<RicercaDistintaPropErogPlusVO>;

  //Paginator e sort
  @ViewChild("paginatorGenerico") paginatorGenerico: MatPaginator;
  @ViewChild("paginatorBenProg") paginatorBenProg: MatPaginator;
  @ViewChild("matSortGenerico") matSortGenerico: MatSort;
  @ViewChild("matSortBenProg") matSortBenProg: MatSort;

  constructor(
    private fb: FormBuilder,
    private resService: DistinteResponseService,
    private handleExceptionService: HandleExceptionService,
    private navigationService: NavigationElencoDistinteService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      dataCreazioneDistintaDa: new FormControl(""),
      dataCreazioneDistintaA: new FormControl(""),
      beneficiario: new FormControl(""),
      bando: new FormControl(""),
      codiceFondoFinpis: new FormControl(""),
      agevolazione: new FormControl(""),
      progetto: new FormControl(""),
      distinta: new FormControl(""),
    });
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  //Metodo che serve per l'autocomplete
  displayBeneficiario(element: DenominazioneSuggestVO): string {
    return element && element.denominazione ? (element.denominazione + (element.codiceFiscaleSoggetto ? ' - ' + element.codiceFiscaleSoggetto : '')) : '';
  }
  displayBando(element: BandoSuggestVO): string {
    return element && element.titoloBando ? element.titoloBando : '';
  }
  displayAgevolazione(element: AgevolazioneSuggestVO): string {
    return element && element.descModalitaAgevolazione ? element.descModalitaAgevolazione : '';
  }
  displayProgetto(element: ProgettoSuggestVO): string {
    return element && element.codiceVisualizzato ? element.codiceVisualizzato : '';
  }
  displayDistinta(element: DistintaVO): string {
    return element && element.idDistinta ? element.idDistinta : '';
  }
  displayCodiceFondoFinpis(element: SuggestIdDescVO): string {
    return element && element.desc ? element.desc : '';
  }

  annullaFiltri() {
    this.myForm.reset();
    this.myForm = this.fb.group({
      dataCreazioneDistintaDa: new FormControl(""),
      dataCreazioneDistintaA: new FormControl(""),
      beneficiario: new FormControl(""),
      bando: new FormControl(""),
      codiceFondoFinpis: new FormControl(""),
      agevolazione: new FormControl(""),
      progetto: new FormControl(""),
      distinta: new FormControl(""),
    });
  }

  suggBen(value: string) {


    if (value.length >= 3) {

      this.suggBeneficiario = [{
        denominazione: "Caricamento...",
        codiceFiscaleSoggetto: "",
        idSoggetto: "",
      }]

      this.resService.suggestBeneficiario(value).subscribe(data => {

        if (data.length > 0) {

          this.suggBeneficiario = data;

        } else {

          this.suggBeneficiario = [{
            denominazione: "Nessuna corrispondenza",
            codiceFiscaleSoggetto: "",
            idSoggetto: "",
          }]

        }
      })

    } else {

      this.suggBeneficiario = []

    }

    console.log(this.suggBeneficiario);

  }
  
  suggBan(value: string) {


    if (value.length >= 3) {

      this.suggBando = [{
        titoloBando: "Caricamento...",
        idBando: "",
      }]

      this.resService.suggestBandoElencoDistinte(value).subscribe(data => {

        if (data.length > 0) {

          this.suggBando = data

        } else {

          this.suggBando = [{
            titoloBando: "Nessuna corrispondenza",
            idBando: "",
          }]

        }
      })

    } else {

      this.suggBando = []

    }

  }
  
  suggAgev(value: string) {


    if (value.length >= 3) {

      this.suggAgevolazione = [{
        descModalitaAgevolazione: "Caricamento...",
        descBreveModalitaAgevolazione: "Caricamento...",
        idModalitaAgevolazione: ""
      }]

      this.resService.suggestAgevElencoDistinte(value).subscribe(data => {

        if (data.length > 0) {

          this.suggAgevolazione = data

        } else {

          this.suggAgevolazione = [{
            descModalitaAgevolazione: "Nessuna corrispondenza",
            descBreveModalitaAgevolazione: "Nessuna corrispondenza",
            idModalitaAgevolazione: ""
          }]

        }
      })

    } else {

      this.suggAgevolazione = []

    }

  }

  suggProg(value: string) {


    if (value.length >= 5) {

      this.suggProgetto = [{
        codiceVisualizzato: "Caricamento...",
        idProgetto: ""
      }]

      this.resService.suggestProgettoElencoDistinte(value).subscribe(data => {

        if (data.length > 0) {

          this.suggProgetto = data

        } else {

          this.suggProgetto = [{
            codiceVisualizzato: "Nessuna corrispondenza",
            idProgetto: ""
          }]

        }
      })

    } else {

      this.suggProgetto = []

    }

  }

  suggDis(value: string) {
    if (value.length >= 1) {

      this.suggDistinta = [];

      this.suggDistinta[0] = {
        idDistinta: "",
        descrizioneDistinta: "Caricamento...",
        descrizioneModalitaAgevolazione: "Caricamento..."
      };

      this.resService.suggestDistinta(value).subscribe(data => {

        if (data.length > 0) {

          this.suggDistinta = data

        } else {

          this.suggDistinta[0] = {
            idDistinta: "",
            descrizioneDistinta: "Nessuna corrispondenza",
            descrizioneModalitaAgevolazione: "Nessuna corrispondenza"
          }

        }
      })
    } else {
      this.suggDistinta = []
    }
  }

  suggCodiceFondo(value: string) {
    if (value.length >= 1) {

      this.suggCodiceFondoFinpis = [{
        desc: "Caricamento...",
        id: ""
      }]

      this.resService.suggestCodiceFondoFinpis(value).subscribe(data => {
        if (data.length > 0) {

          this.suggCodiceFondoFinpis = data

        } else {

          this.suggCodiceFondoFinpis = [{
            desc: "Nessuna corrispondenza",
            id: ""
          }]

        }
      })
    } else {
      this.suggCodiceFondoFinpis = []
    }
  }

//TODO AGGIUNGERE BANDO E AGEVOLAZIONE
  search() {

    let formControls = this.myForm.getRawValue();

    console.log("formControls.dataCreazioneDistintaDa", formControls.dataCreazioneDistintaDa)
    console.log("formControls.dataCreazioneDistintaA", formControls.dataCreazioneDistintaA)

    if (
      (formControls.dataCreazioneDistintaDa == undefined || formControls.dataCreazioneDistintaDa == null || formControls.dataCreazioneDistintaDa == "") &&
      (formControls.dataCreazioneDistintaA == undefined || formControls.dataCreazioneDistintaA == null || formControls.dataCreazioneDistintaA == "") &&
      (formControls.beneficiario.length < 3) &&
      (formControls.bando.length < 3) &&
      (formControls.codiceFondoFinpis.length < 1) &&
      (formControls.agevolazione.length < 5) &&
      (formControls.progetto.length < 1) &&
      (formControls.distinta.length < 1)) {

      this.showMessageError("Compilare almeno un campo per avviare la ricerca")

    } else if (
      (formControls.dataCreazioneDistintaDa && (formControls.dataCreazioneDistintaA == '' || formControls.dataCreazioneDistintaA == null || formControls.dataCreazioneDistintaA == undefined)) ||
      ((formControls.dataCreazioneDistintaDa == '' || formControls.dataCreazioneDistintaDa == null || formControls.dataCreazioneDistintaDa == undefined) && formControls.dataCreazioneDistintaA)) {

      this.showMessageError("Se viene indicata la data di creazione di inizio periodo allora deve essere indicata anche la data di creazione di fine periodo e viceversa.")

    } else {

      this.spinner = true;

      this.resetMessageError();

      /*************************
      **** RICERCA GENERICA ****
      **************************/
      //se NON è stato inserito né il beneficiario né il progetto
      if ((formControls.beneficiario.length < 3) && (formControls.progetto.length < 1)) {

        //creo un oggetto contenente le date
        let obj = {
          dataCreazioneFrom: formControls.dataCreazioneDistintaDa == "" ? null : formControls.dataCreazioneDistintaDa,
          dataCreazioneTo: formControls.dataCreazioneDistintaA == "" ? null : formControls.dataCreazioneDistintaA,
          idBeneficiario: null,
          idBando: formControls.codiceFondoFinpis != null ? formControls.codiceFondoFinpis.id : (formControls.bando == "" ? null : formControls.bando.idBando), 
          idAgevolazione: formControls.agevolazione == "" ? null : formControls.agevolazione.idModalitaAgevolazione,
          idProgetto: null,
          idDistinta: formControls.distinta ? formControls.distinta.idDistinta : null,
        };

        console.log("PARAMETRO APPLICA FILTRI GENERICO", obj)

        this.resService.applicaFiltri(obj).subscribe(data => {

          if (data) {

            console.log("DATA GENERICO", data);

            this.listTabGenerica = data;
            this.dataSourceGenerica = new MatTableDataSource<RicercaDistintaPropErogVO>(this.listTabGenerica);

            if (this.navigationService.formElencoDistinteSelezionato) {
              this.ripristinaPaginatorGenerica();
            } else {
              this.paginatorGenerico.length = this.listTabGenerica.length;
              this.paginatorGenerico.pageIndex = 0;
              this.dataSourceGenerica.paginator = this.paginatorGenerico;
              this.dataSourceGenerica.sort = this.matSortGenerico;

              this.dataSourceGenerica.sortingDataAccessor = (item, property) => {
                switch (property) {
                  case "numeroDistinta":
                    return item.idDistinta;
                  case "descrizione":
                    return item.descrizioneDistinta;
                  case "dataCreazioneDistinta":
                    return item.dataCreazioneDistinta;
                  case "utente":
                    return item.utenteCreazione;
                  case "statoDistinta":
                    return item.descStatoDistinta;
                  case "statoIterAutorizzativo":
                    return item.statoIterAutorizzativo;

                  default: return item[property];
                }
              };

            }

          }

          this.mostraTabGenerica = true;
          this.mostraTabBenProg = false;

          this.criteriRicercaOpened = false;
          this.spinner = false;

        }, err => {

          this.spinner = false;
          this.showMessageError("Errore in fase di ricerca: la ricerca non ha prodotto risultati.")
          this.handleExceptionService.handleNotBlockingError(err);

        });

      } else {

        /*************************
        **** RICERCA BEN PROG ****
        **************************/

        let obj = {
          dataCreazioneFrom: formControls.dataCreazioneDistintaDa == "" ? null : formControls.dataCreazioneDistintaDa,
          dataCreazioneTo: formControls.dataCreazioneDistintaA == "" ? null : formControls.dataCreazioneDistintaA,
          idBeneficiario: formControls.beneficiario == "" ? null : formControls.beneficiario.idSoggetto,
          idBando: formControls.codiceFondoFinpis != null ? formControls.codiceFondoFinpis.id : (formControls.bando == "" ? null : formControls.bando.idBando), 
          idAgevolazione: formControls.agevolazione == "" ? null : formControls.agevolazione.idModalitaAgevolazione,
          idProgetto: formControls.progetto == "" ? null : formControls.progetto.idProgetto,
          idDistinta: formControls.distinta ? formControls.distinta.idDistinta : null,
        };

        console.log("PARAMETRO APPLICA FILTRI PLUS", obj)

        this.resService.applicaFiltriPlus(obj).subscribe(data => {

          if (data) {
            this.listTabBenProg = data;
            //console.table(this.fin);
            this.dataSourceBenProg = new MatTableDataSource<RicercaDistintaPropErogPlusVO>(this.listTabBenProg);

            if (this.navigationService.formElencoDistinteSelezionato) {
              this.ripristinaPaginatorBenProg();
            } else {
              this.paginatorBenProg.length = this.listTabBenProg.length;
              this.paginatorBenProg.pageIndex = 0;
              this.dataSourceBenProg.paginator = this.paginatorBenProg;
              this.dataSourceBenProg.sort = this.matSortBenProg;

              this.dataSourceBenProg.sortingDataAccessor = (item, property) => {
                switch (property) {
                  case "numeroDistinta":
                    return item.idDistinta;
                  case "descrizione":
                    return item.descrizioneDistinta;
                  case "tipologiaDistinta":
                    return item.idTipoDistinta;
                  case "utente":
                    return item.utenteCreazione;
                  case "dataCreazioneDistinta":
                    return item.dataCreazioneDistinta;
                  case "statoDistinta":
                    return item.descStatoDistinta + " " + item.codiceFondoFinpis;
                  case "statoIterAutorizzativo":
                    return item.statoIterAutorizzativo;
                  case "progetto":
                    return item.codiceFondoFinpis;
                  case "beneficiario":
                    return item.denominazione;
                  case "importoLordo":
                    return item.importoLordo;
                  case "importoNetto":
                    return item.importoNetto;
                  case "dataContabileErogazione":
                    return item.dataContabileErogazione?.getTime();
                  // case "importoErogato":
                  //   return item.importoErogato;
                  default: return item[property];
                }
              };
            }
          }

          this.mostraTabBenProg = true;
          this.mostraTabGenerica = false;

          this.criteriRicercaOpened = false;
          this.spinner = false;

        }, err => {

          this.spinner = false;
          this.showMessageError("Errore in fase di ricerca: la ricerca non ha prodotto risultati.")
          this.handleExceptionService.handleNotBlockingError(err);

        });

      }
    }
  }

  visualizzaDettaglioDistinta(element: any) {
    this.saveDataForNavigation();

    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_ELENCO_DISTINTE + "/dettaglioDistinta"], {
      queryParams: {
        "idDistinta": element.idDistinta
      }
    });
  }


  /*  METODO PER MEMORIZZARE LE RICERCHE EFFETTUATE */
  saveDataForNavigation() {

    let formControls = this.myForm.getRawValue();

    this.navigationService.formElencoDistinteSelezionato = new FormElencoDistinte(
      formControls.dataCreazioneDistintaDa || "",
      formControls.dataCreazioneDistintaA || "",
      formControls.beneficiario || "",
      formControls.bando || "",
      formControls.codiceFondoFinpis || "",
      formControls.agevolazione || "",
      formControls.progetto || "",
      formControls.distinta || "",
    );

    if (
      (formControls.beneficiario == "" || formControls.beneficiario == null || formControls.beneficiario == undefined) &&
      (formControls.progetto == "" || formControls.progetto == null || formControls.progetto == undefined)
    ) {

      this.salvaPaginatorGenerica();

    } else {

      this.salvaPaginatorBenProg();

    }

  }
  salvaPaginatorGenerica() {
    if (this.dataSourceGenerica) {
      this.navigationService.paginatorPageIndexTable = this.dataSourceGenerica.paginator.pageIndex;
      this.navigationService.paginatorPageSizeTable = this.dataSourceGenerica.paginator.pageSize;
    }
  }
  salvaPaginatorBenProg() {
    if (this.dataSourceBenProg) {
      this.navigationService.paginatorPageIndexTable = this.dataSourceBenProg.paginator.pageIndex;
      this.navigationService.paginatorPageSizeTable = this.dataSourceBenProg.paginator.pageSize;
    }
  }
  ripristinaPaginatorGenerica() {
    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
      this.paginatorGenerico.pageIndex = this.navigationService.paginatorPageIndexTable;
      this.paginatorGenerico.pageSize = this.navigationService.paginatorPageSizeTable;

      this.dataSourceGenerica.paginator = this.paginatorGenerico;
      this.dataSourceGenerica.sort = this.matSortGenerico;
    }
  }
  ripristinaPaginatorBenProg() {
    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
      this.paginatorBenProg.pageIndex = this.navigationService.paginatorPageIndexTable;
      this.paginatorBenProg.pageSize = this.navigationService.paginatorPageSizeTable;

      this.dataSourceBenProg.paginator = this.paginatorBenProg;
      this.dataSourceBenProg.sort = this.matSortBenProg;
    }
  }


  //metodo martina
  esportaExcel(row: any) {
    this.resService
      .esportaExcel(row.idDistinta)
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

  svuotaNumeroDistinta() {
    this.myForm.get("distinta").setValue('');
  }

}
