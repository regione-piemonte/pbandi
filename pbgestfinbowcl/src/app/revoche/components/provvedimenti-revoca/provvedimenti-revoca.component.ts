/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Constants, HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { DenominazioneSuggestVO } from '../../commons/denominazione-suggest-vo';
import { SuggestResponseService } from '../../services/suggest-response.service';
import { FormRicercaProvvedimentoVO } from '../../commons/provvedimenti-revoca-dto/form-ricerca-provvedimento-vo';
import { NavigationProvvedimentoRevocaService } from '../../services/navigation/navigation-provvedimento-revoca.service';
import { ProvvedimentoRevocaTableVO } from '../../commons/provvedimenti-revoca-dto/provvedimento-revoca-table-vo';
import { FiltroProvvedimentoRevocaVO } from '../../commons/provvedimenti-revoca-dto/filtro-provvedimento-revoca-vo';
import { ProvvedimentiRevocaResponseService } from '../../services/provvedimenti-revoca-response.service';
import { Router } from '@angular/router';
import { FiltroRevocaVO } from '../../commons/filtro-revoca-vo';
import { UserService } from 'src/app/core/services/user.service';
import { OkDialogComponent } from 'src/app/iter-autorizzativi/components/iter-autorizzativi/ok-dialog/ok-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-provvedimenti-revoca',
  templateUrl: './provvedimenti-revoca.component.html',
  styleUrls: ['./provvedimenti-revoca.component.scss']
})
export class ProvvedimentiRevocaComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  spinner: boolean = false;
  error: boolean = false;
  success: boolean = false;

  messageError: string;
  messageSuccess: string;

  criteriRicercaOpened: boolean = true;
  mostraTab: boolean = false;

  myForm: FormGroup;

  //suggerimenti form
  suggBeneficiario: DenominazioneSuggestVO[] = [];
  suggBando: DenominazioneSuggestVO[] = [];   //BandoLineaInterventoVO
  suggProgetto: DenominazioneSuggestVO[] = [];    //ProgettoSuggestVO
  suggCausaProvvedimentoRevoca: DenominazioneSuggestVO[] = []; //CausaleBloccoSuggestVO
  suggStatoProvvedimentoRevoca: DenominazioneSuggestVO[] = []; //StatoRevocaSuggestVO
  suggAttivitaProvvedimentoRevoca: DenominazioneSuggestVO[] = [];
  suggNumeroProvvedimentoRevoca: DenominazioneSuggestVO[] = [];

  //table
  displayedColumns: string[] = ["numeroProvvedimentoRevoca", "denominazioneBeneficiario", "codiceVisualizzatoProgetto", "nomeBandoLinea", "descCausaleBlocco",
   "dataProvvedimentoRevoca", "dataNotifica", "descStatoRevoca", "dataStatoRevoca", "descAttivitaRevoca",
    "dataAttivitaRevoca", "actions"];
  dataSource: MatTableDataSource<ProvvedimentoRevocaTableVO> = new MatTableDataSource<ProvvedimentoRevocaTableVO>([]);
  listTab: Array<ProvvedimentoRevocaTableVO>;

  subscribers: any = {};
  idUtente: any;
  user: UserInfoSec;
  errore: boolean;
  message: string;

  constructor(
    private fb: FormBuilder,
    private resService: ProvvedimentiRevocaResponseService,
    private handleExceptionService: HandleExceptionService,
    private suggestService: SuggestResponseService,
    private navigationService: NavigationProvvedimentoRevocaService,
    private router: Router,
    private userService: UserService,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {

    this.myForm = this.fb.group({
      beneficiario: new FormControl(""),
      bando: new FormControl(""),
      progetto: new FormControl(""),
      causaProvvedimentoRevoca: new FormControl(""),
      statoProvvedimentoRevoca: new FormControl(""),
      numeroProvvedimentoRevoca: new FormControl(""),
      attivitaProvvedimentoRevoca: new FormControl(""),
      dataProcedRevocaDa: new FormControl(""),
      dataProcedRevocaA: new FormControl(""),
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = data.idUtente;
        let filtro = this.navigationService.filtroRicercaProvvedimentoRevocaSelezionato;

        if (filtro) {
          this.myForm = this.fb.group({
            beneficiario: new FormControl(filtro.beneficiario),
            bando: new FormControl(filtro.bando),
            progetto: new FormControl(filtro.progetto),
            causaProvvedimentoRevoca: new FormControl(filtro.causaProvvedimentoRevoca == undefined ? "" : filtro.causaProvvedimentoRevoca),
            statoProvvedimentoRevoca: new FormControl(filtro.statoProvvedimentoRevoca == undefined ? "" : filtro.statoProvvedimentoRevoca),
            numeroProvvedimentoRevoca: new FormControl(filtro.numeroProvvedimentoRevoca == undefined ? "" : filtro.numeroProvvedimentoRevoca),
            attivitaProvvedimentoRevoca: new FormControl(filtro.attivitaProvvedimentoRevoca == undefined ? "" : filtro.attivitaProvvedimentoRevoca),
            dataProcedRevocaDa: new FormControl(filtro.dataProcedRevocaFrom),
            dataProcedRevocaA: new FormControl(filtro.dataProcedRevocaTo),
          });

          this.search();
        }
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });

    this.popolareSelectCausaEStato();
  }

  annullaFiltri() {
    this.myForm.reset();
    this.myForm = this.fb.group({
      beneficiario: new FormControl(""),
      bando: new FormControl(""),
      progetto: new FormControl(""),
      causaProvvedimentoRevoca: new FormControl(""),
      statoProvvedimentoRevoca: new FormControl(""),
      numeroProvvedimentoRevoca: new FormControl(""),
      attivitaProvvedimentoRevoca: new FormControl(""),
      dataProcedRevocaDa: new FormControl(""),
      dataProcedRevocaA: new FormControl(""),
    });

    this.popolareSelectCausaEStato();
  }

  suggerimentoBeneficiario(value: string) {
    this.popolareSelectCausaEStato();
    this.suggBeneficiario = [];
    let formControls = this.myForm.getRawValue();
    if (formControls.bando?.id) {

      this.suggBeneficiario = [
        {
          desc: "Caricamento...",
          id: "",
          altro: "",
        }
      ]

      this.suggestService.suggestBeneficiario(value, formControls.bando?.id == undefined ? "-1" : formControls.bando?.id).subscribe(data => {

        if (data.length > 0) {

          this.suggBeneficiario = data;
          this.suggBeneficiario.sort((a, b) => a.desc.localeCompare(b.desc));

        } else {

          this.suggBeneficiario = [
            {
              desc: "Nessuna corrispondenza",
              id: "",
              altro: "",
            }
          ]

        }
      }, err => {

        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);

      });

    } 
    else if (value.length >= 3) {

      this.suggBeneficiario = [
        {
          desc: "Caricamento...",
          id: "",
          altro: "",
        }
      ]

      this.suggestService.suggestBeneficiario(value, formControls.bando?.id == undefined ? "-1" : formControls.bando?.id).subscribe(data => {

        if (data.length > 0) {

          this.suggBeneficiario = data;
          this.suggBeneficiario.sort((a, b) => a.desc.localeCompare(b.desc));

        } else {

          this.suggBeneficiario = [
            {
              desc: "Nessuna corrispondenza",
              id: "",
              altro: "",
            }
          ]

        }
      }, err => {

        this.showMessageError("Errore durante il caricamento dei suggerimenti")
        this.handleExceptionService.handleNotBlockingError(err);

      });

    } 
    else {

      this.suggBeneficiario = []

    }

  }

  suggerimentoBando(value: string) {
    this.popolareSelectCausaEStato();
    this.suggBando = [];
    let formControls = this.myForm.getRawValue();
    this.suggBando = [{desc: "Caricamento...",id: "",altro: "",}];
    this.suggestService.suggestBando(value, formControls.beneficiario?.id).subscribe(data => {

      if (data.length > 0) {

        this.suggBando = data;
        this.suggBando.sort((a, b) => a.desc.localeCompare(b.desc));

      } else {

        this.suggBando = [
          {
            desc: "Nessuna corrispondenza",
            id: "",
            altro: "",
          }
        ]

      }
    })

    /*
  } else {

    this.suggBando = []

  }
  */
  }

  suggerimentoProgetto(value: string) {
    this.popolareSelectCausaEStato();
    this.suggProgetto = [];
    //La ricerca si attiva SOLO nel caso in cui sia stato selezionato prima un beneficiario e un bando
    let formControls = this.myForm.getRawValue();

    if (
      (formControls.beneficiario == "undefined" || formControls.beneficiario == null || formControls.beneficiario == "") ||
      (formControls.bando == undefined || formControls.bando == null || formControls.bando == "")
    ) {

      this.showMessageError("Per inserire il progetto è necessario aver indicato prima il beneficiario e il bando.")

    } else {

      //Nella JIRA è stato richiesto di rimuovere l'obbligo di inserire almeno 3 caratteri prima di fare la suggest
      //if (value.length >= 3) {

      this.suggProgetto = [
        {
          altro: "Caricamento...",
          id: "",
          desc: ""
        }
      ];

      this.suggestService.suggestProgetto(value, formControls.beneficiario?.id, formControls.bando?.id).subscribe(data => {

        if (data.length > 0) {

          this.suggProgetto = data;
          this.suggProgetto.sort((a, b) => a.desc.localeCompare(b.desc));

        } else {

          this.suggProgetto = [
            {
              altro: "Nessuna corrispondenza",
              id: "",
              desc: ""
            }
          ];

        }
      })
    }

    /*
    } else {

      this.suggProgetto = []

    }
    */

  }

  suggerimentoCausaProvvedimentoRevoca() {
    this.suggCausaProvvedimentoRevoca = [];
    this.suggCausaProvvedimentoRevoca = [
      {
        desc: "Caricamento...",
        id: "",
        altro: ""
      }
    ];

    let formControls = this.myForm.getRawValue();
    console.log("FORM SUGGERIMENTO CAUSA", formControls)

    let parametro: FiltroRevocaVO = {
      idTipologiaRevoca: "3",
      numeroRevoca: formControls.numeroProvvedimentoRevoca?.desc == "Nessuna corrispondenza..." ? null : formControls.numeroProvvedimentoRevoca?.desc,
      idSoggetto: formControls.beneficiario?.id,
      idProgetto: formControls.progetto?.id,
      progrBandoLineaIntervent: formControls.bando?.id,
      idCausaRevoca: null,
      idStatoRevoca: formControls.statoPropostaRevoca?.id,
      idAttivitaRevoca: formControls.attivitaProvvedimentoRevoca?.id,
      dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
      dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
      value: null,
    }

    this.suggestService.suggestCausaRevoche(parametro).subscribe(data => {
      console.log(data);

      if (data.length > 0) {

        this.suggCausaProvvedimentoRevoca = data;

      } else {

        this.suggCausaProvvedimentoRevoca = [
          {
            desc: "Nessun risultato disponibile",
            id: "",
            altro: ""
          }
        ];
      }

    });

  }

  suggerimentoStatoProvvedimentoRevoca() {

    this.suggStatoProvvedimentoRevoca = [
      {
        desc: "Caricamento...",
        id: "",
        altro: ""
      }
    ];

    let formControls = this.myForm.getRawValue();

    let parametro: FiltroRevocaVO = {
      idTipologiaRevoca: "3",
      numeroRevoca: formControls.numeroProvvedimentoRevoca?.desc == "Nessuna corrispondenza..." ? null : formControls.numeroProvvedimentoRevoca?.desc,
      idSoggetto: formControls.beneficiario?.id,
      idProgetto: formControls.progetto?.id,
      progrBandoLineaIntervent: formControls.bando?.id,
      idCausaRevoca: formControls.causaProvvedimentoRevoca?.id,
      idStatoRevoca: null,
      idAttivitaRevoca: formControls.attivitaProvvedimentoRevoca?.id,
      dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
      dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
      value: null,
    }

    this.suggestService.suggestStatoRevoche(parametro).subscribe(data => {
      if (data.length > 0) {

        this.suggStatoProvvedimentoRevoca = data;

      } else {

        this.suggStatoProvvedimentoRevoca = [
          {
            desc: "Nessun risultato disponibile",
            id: "",
            altro: ""
          }
        ];

      }

    });

  }

  suggerimentoAttivitaProvvedimentoRevoca() {

    this.suggAttivitaProvvedimentoRevoca = [
      {
        desc: "Caricamento...",
        id: "",
        altro: ""
      }
    ];

    let formControls = this.myForm.getRawValue();
    let parametro: FiltroRevocaVO = {
      idTipologiaRevoca: "3",
      numeroRevoca: formControls.numeroProvvedimentoRevoca?.desc == "Nessuna corrispondenza..." ? null : formControls.numeroProvvedimentoRevoca?.desc,
      idSoggetto: formControls.beneficiario?.id,
      idProgetto: formControls.progetto?.id,
      progrBandoLineaIntervent: formControls.bando?.id,
      idCausaRevoca: formControls.causaProvvedimentoRevoca?.id,
      idStatoRevoca: formControls.statoProvvedimentoRevoca?.id,
      idAttivitaRevoca: null,
      dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
      dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
      value: null,
    }

    this.suggestService.suggestAttivitaRevoche(parametro).subscribe(data => {
      if (data.length > 0) {

        this.suggAttivitaProvvedimentoRevoca = data;

      } else {

        this.suggAttivitaProvvedimentoRevoca = [
          {
            desc: "Nessun risultato disponibile",
            id: "",
            altro: ""
          }
        ];

      }

    });

  }

  suggerimentoNumeroProvvedimentoRevoca(value: string) {
    this.popolareSelectCausaEStato();
    this.myForm.get('attivitaProvvedimentoRevoca').setValue(null);
    this.myForm.get('statoProvvedimentoRevoca').setValue(null);
    this.myForm.get('causaProvvedimentoRevoca').setValue(null);
    this.suggNumeroProvvedimentoRevoca = [];

    this.suggNumeroProvvedimentoRevoca = [{desc: "Caricamento...",id: "",altro: "",}];

    let formControls = this.myForm.getRawValue();

    let parametro: FiltroRevocaVO = {
      idTipologiaRevoca: "3",
      numeroRevoca: null,
      idSoggetto: formControls.beneficiario?.id,
      idProgetto: formControls.progetto?.id,
      progrBandoLineaIntervent: formControls.bando?.id,
      idCausaRevoca: formControls.causaPropostaRevoca?.id,
      idStatoRevoca: formControls.statoPropostaRevoca?.id,
      idAttivitaRevoca: formControls.attivitaProcedimentoRevoca?.idAttivitaRevoca,
      dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
      dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
      value: value,
    }

    this.suggestService.suggestNumeroRevoche(parametro).subscribe(data => {

      if (data.length > 0) {

        this.suggNumeroProvvedimentoRevoca = data;

      } else {

        this.suggNumeroProvvedimentoRevoca = [
          {
            desc: "Nessuna corrispondenza...",
            id: "",
            altro: "",
          }
        ];

      }
    })

  }



  displayBeneficiario(element: DenominazioneSuggestVO): string {
    return element && element.desc ? element.desc + ' - ' + element.altro : '';
  }
  displayBando(element: DenominazioneSuggestVO): string {
    return element && element.desc ? element.desc : '';
  }
  displayProgetto(element: DenominazioneSuggestVO): string {
    return element && element.altro ? element.desc + ' - ' + element.altro : '';
  }
  displayNumero(element: DenominazioneSuggestVO): string {
    return element && element.id ? element.desc : '';
  }

  //Metodo che popola i campi delle due select (causa e stato) ogni volta che viene inserito un nuovo campo nel form
  popolareSelectCausaEStato() {

    let formControls = this.myForm.getRawValue();

    if (formControls.numeroProvvedimentoRevoca?.desc != "Nessuna corrispondenza...") {
      this.suggerimentoCausaProvvedimentoRevoca();
      this.suggerimentoStatoProvvedimentoRevoca();
      this.suggerimentoAttivitaProvvedimentoRevoca();
    }

  }

  compareObjects(o1: any, o2: any): boolean {
    return o1?.desc === o2?.desc && o1?.id === o2?.id;
  }



  search() {

    let formControls = this.myForm.getRawValue();

    console.log("FORM CONTROLS");
    console.log(formControls);

    if (
      (formControls.dataProcedRevocaDa == "") &&
      (formControls.dataProcedRevocaA == "") &&
      (formControls.beneficiario.length < 3) &&
      (formControls.bando.length < 3) &&
      (formControls.progetto.length < 3) &&
      (formControls.causaProvvedimentoRevoca == "") &&
      (formControls.statoProvvedimentoRevoca == "") &&
      (formControls.numeroProvvedimentoRevoca.length < 3) &&
      (formControls.attivitaProvvedimentoRevoca == "")
    ) {

      this.showMessageError("Inserire almeno un parametro di ricerca.")

    } else {

      this.spinner = true;

      this.resetMessageError();

      let parametro: FiltroProvvedimentoRevocaVO = {
        idTipologiaRevoca: null,
        idSoggetto: formControls.beneficiario?.id,
        progrBandoLineaIntervent: formControls.bando?.id,
        idProgetto: formControls.progetto?.id,
        idCausaRevoca: formControls.causaProvvedimentoRevoca?.id,
        idStatoRevoca: formControls.statoProvvedimentoRevoca?.id,
        numeroRevoca: formControls.numeroProvvedimentoRevoca?.desc,
        idAttivitaRevoca: formControls.attivitaProvvedimentoRevoca?.id,
        dataRevocaFrom: formControls.dataProcedRevocaDa != "" ? formControls.dataProcedRevocaDa : undefined,
        dataRevocaTo: formControls.dataProcedRevocaA != "" ? formControls.dataProcedRevocaA : undefined
      }

      console.log("PARAMETRO");
      console.log(parametro);

      this.resService.cercaProvvedimento(parametro).subscribe(data => {

        console.log("DATA", data)

        if (data.length == 0) {

          this.listTab = data;
          this.dataSource = new MatTableDataSource<ProvvedimentoRevocaTableVO>(this.listTab);

          this.mostraTab = false;
          this.criteriRicercaOpened = true;

          this.spinner = false;

          this.showMessageError("Nessuna provvedimento di revoca trovato per i parametri inseriti.")

        } else {

          //memorizzo l'array arrivato
          this.listTab = data;
          this.dataSource = new MatTableDataSource<ProvvedimentoRevocaTableVO>(this.listTab);

          console.log("this.navigationService.filtroRicercaProvvedimentoRevocaSelezionato", this.navigationService.filtroRicercaProvvedimentoRevocaSelezionato)

          //sorting e paginator
          if (this.navigationService.filtroRicercaProvvedimentoRevocaSelezionato) {
            console.log("CIAO")
            this.ripristinaPaginator();
          } else {
            this.paginator.length = this.listTab.length;
            this.paginator.pageIndex = 0;
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;

            this.dataSource.sortingDataAccessor = (item, property) => {
              switch (property) {
                case 'numeroProvvedimentoRevoca':
                  return item.numeroProvvedimentoRevoca;
                case 'beneficiario':
                  return item.denominazioneBeneficiario + " - " + item.codiceFiscaleSoggetto;
                case 'progetto':
                  return item.codiceVisualizzatoProgetto;
                case 'bando':
                  return item.nomeBandoLinea;
                case 'causaProcedimentoRevoca':
                  return item.descCausaleBlocco;
                case 'dtProvvedimentoRevoca':
                  return item.dataProvvedimentoRevoca?.getTime();
                case 'dtNotifica':
                  return item.dataNotifica?.getTime();
                case 'statoProvvedimentoRevoca':
                  return item.descStatoRevoca;
                case 'dtStatoProvvedimentoRevoca':
                  return item.dataStatoRevoca?.getTime();
                case 'attivita':
                  return item.descAttivitaRevoca;
                case 'dtAttivita':
                  return item.dataAttivitaRevoca?.getTime();
                default: return item[property];
              }
            };
          }

          this.mostraTab = true;
          this.criteriRicercaOpened = false;

          this.spinner = false;
        }

      }, err => {

        this.spinner = false;
        this.showMessageError("Errore in fase di ricerca: la ricerca non ha prodotto risultati.")
        this.handleExceptionService.handleNotBlockingError(err);

      });

    }

  }

  modificaProvvedimentoRevoca(element: ProvvedimentoRevocaTableVO) {
    this.saveDataForNavigation();

    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_GESTIONE_REVOCHE_PROVVEDIMENTI + "/modificaProvvedimentoRevoca"], {
      queryParams: {
        "numeroProvvedimentoRevoca": element.numeroProvvedimentoRevoca
      }
    });
  }

  

  eliminaProvvedimentoRevoca(element) {
    const dialogRef = this.dialog.open(OkDialogComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        title: 'Elimina provvedimento di revoca',
        message: 'Sei sicuro di voler eliminare il provvedimento selezionato?',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if(result === true){
        
         this.resService.eliminaProvvedimentoRevoca(element.numeroProvvedimentoRevoca.toString()).subscribe(data => {
        if (data.code == "OK") {
          this.message = data.message;
          this.errore = false;
          setTimeout(() => {
            this.errore = null;
            this.search();
          }, 2000); 
        }
        else if (data.code == "KO") {
          this.errore = true;
          this.message =  "Errore non è stato possibile eliminare il provvedimento";
          setTimeout(() => {
            this.errore = null;
            
          }, 2000); 
        }
      },
        (err) => {
            this.errore = true;
            this.message = err.statusText;
        }
      );
      }else return;
      
    });
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }
  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.success = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }
  showMessageError(msg: string) {
    this.messageError = msg;
    this.error = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }
  resetMessageError() {
    this.messageError = null;
    this.error = false;
  }

  /****************
  *** PAGINATOR ***
  ****************/
  saveDataForNavigation() {
    let formControls = this.myForm.getRawValue();

    this.navigationService.filtroRicercaProvvedimentoRevocaSelezionato = new FormRicercaProvvedimentoVO(
      formControls.beneficiario,
      formControls.bando,
      formControls.progetto,
      formControls.causaProvvedimentoRevoca,
      formControls.statoProvvedimentoRevoca,
      formControls.numeroProvvedimentoRevoca,
      formControls.attivitaProvvedimentoRevoca,
      formControls.dataProcedRevocaDa != "" ? formControls.dataProcedRevocaDa : undefined,
      formControls.dataProcedRevocaA != "" ? formControls.dataProcedRevocaA : undefined
    );

    this.salvaPaginator();
  }

  salvaPaginator() {
    if (this.dataSource) {
      this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
      this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    }
  }
  ripristinaPaginator() {
    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
      this.paginator.pageIndex = this.navigationService.paginatorPageIndexTable;
      this.paginator.pageSize = this.navigationService.paginatorPageSizeTable;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
  }

  //BLUR: se non viene selezionato uno dei campi dell'autocomplete, la stringa del campo viene settata vuota
  onBlurAutoComplete(form: FormGroup, fields: string[]) {
    if (!(form.get(fields[0])?.value && form.get(fields[0])?.value instanceof Object)) {
      for (let field of fields) {
        form.get(field)?.setValue(null);
      }
    }
  }
  onBlur(fields: string[]) {
    this.onBlurAutoComplete(this.myForm, fields);
  }

}
