/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit,ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Constants, HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { CausaleBloccoSuggestVO } from '../../commons/proposte-revoca-dto/causale-blocco-suggest-vo';
import { DenominazioneSuggestVO } from '../../commons/denominazione-suggest-vo';
import { PropostaRevocaVO } from '../../commons/proposte-revoca-dto/proposta-revoca-vo';
import { StatoRevocaSuggestVO } from '../../commons/proposte-revoca-dto/stato-revoca-suggest-vo';
import { ProposteRevocaResponseService } from '../../services/proposte-revoca-response.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { FiltroPropostaRevocaVO } from '../../commons/proposte-revoca-dto/filtro-proposta-revoca-vo';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { SuggestResponseService } from '../../services/suggest-response.service';
import { NavigationPropostaRevocaService } from '../../services/navigation/navigation-proposta-revoca.service';
import { UserService } from 'src/app/core/services/user.service';
import { FormRicercaPropostaVO } from '../../commons/proposte-revoca-dto/form-ricerca-proposta-vo';
import { FiltroRevocaVO } from '../../commons/filtro-revoca-vo';


@Component({
  selector: 'app-proposta-revoca',
  templateUrl: './proposta-revoca.component.html',
  styleUrls: ['./proposta-revoca.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class PropostaRevocaComponent implements OnInit {

  spinner: boolean = false;
  error: boolean = false;
  success: boolean = false;

  messageError: string;
  messageSuccess: string;

  criteriRicercaOpened: boolean = true;
  mostraTab: boolean = false;

  myForm: FormGroup;

  idUtente: any;
  user: UserInfoSec;

  //suggerimenti form
  suggBeneficiario: DenominazioneSuggestVO[] = [];
  suggBando: DenominazioneSuggestVO[] = [];   //BandoLineaInterventoVO
  suggProgetto: DenominazioneSuggestVO[] = [];    //ProgettoSuggestVO
  suggCausaPropostaRevoca: DenominazioneSuggestVO[] = [];
  suggStatoPropostaRevoca: DenominazioneSuggestVO[] = [];
  suggNumeroPropostaRevoca: DenominazioneSuggestVO[] = [];

  //table
  displayedColumns: string[] = ["numeroPropostaRevoca", "denominazioneBeneficiario", "codiceVisualizzatoProgetto", "nomeBandoLinea", "descCausaleBlocco",
   "dataPropostaRevoca", "descStatoRevoca", "dataStatoRevoca", "actions"];
  dataSource: MatTableDataSource<PropostaRevocaVO> = new MatTableDataSource<PropostaRevocaVO>([]);
  listTab: Array<PropostaRevocaVO>;

  public subscribers: any = {};


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;


  constructor(
    private fb: FormBuilder,
    private resService: ProposteRevocaResponseService,
    private handleExceptionService: HandleExceptionService,
    private router: Router,
    private suggestService: SuggestResponseService,
    private navigationService: NavigationPropostaRevocaService,
    private userService: UserService,
  ) { }

  ngOnInit(): void {

    this.myForm = this.fb.group({
      beneficiario: new FormControl(""),
      bando: new FormControl(""),
      progetto: new FormControl(""),
      causaPropostaRevoca: new FormControl(""),
      statoPropostaRevoca: new FormControl(""),
      numeroPropostaRevoca: new FormControl(""),
      dataPropRevocaDa: new FormControl(""),
      dataPropRevocaA: new FormControl(""),
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = data.idUtente;
        let filtro = this.navigationService.filtroRicercaPropostaRevocaSelezionato;

        if (filtro) {

          this.myForm = this.fb.group({
            beneficiario: new FormControl(filtro.beneficiario),
            bando: new FormControl(filtro.bando),
            progetto: new FormControl(filtro.progetto),
            causaPropostaRevoca: new FormControl(filtro.causaPropostaRevoca  == undefined ? "" : filtro.causaPropostaRevoca),
            statoPropostaRevoca: new FormControl(filtro.statoPropostaRevoca == undefined ? "" : filtro.statoPropostaRevoca),
            numeroPropostaRevoca: new FormControl(filtro.numeroPropostaRevoca == undefined ? "" : filtro.numeroPropostaRevoca),
            dataPropRevocaDa: new FormControl(filtro.dataPropostaRevocaFrom),
            dataPropRevocaA: new FormControl(filtro.dataPropostaRevocaTo),
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
      causaPropostaRevoca: new FormControl(""),
      statoPropostaRevoca: new FormControl(""),
      numeroPropostaRevoca: new FormControl(""),
      dataPropRevocaDa: new FormControl(""),
      dataPropRevocaA: new FormControl(""),
    });

    this.popolareSelectCausaEStato();
  }
  /* ****************************************
  ******* METODI PER AUTOCOMPLETE ***********
  ******************************************/

  suggerimentoBeneficiario(value: string) {
    this.popolareSelectCausaEStato();
    this.suggBeneficiario = [];

    let formControls = this.myForm.getRawValue();

    console.log("FORMCONTROLS", formControls)

    //aggiorno select causa
    // this.popolareSelectCausaEStato()

    //Se il bando è già stato inserito, il suggest mi compare nel momento in cui clicco il campo beneficiario
    if(formControls.bando?.id) {

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

    } else if (value.length >= 3) {

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

    } else {

      this.suggBeneficiario = []

    }

  }
  suggerimentoBando(value: string) {
    this.popolareSelectCausaEStato();
    this.suggBando = [];

    let formControls = this.myForm.getRawValue();

      this.suggBando = [
        {
          desc: "Caricamento...",
          id: "",
          altro: "",
        }
      ];

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

  }
  suggerimentoProgetto(value: string) {
    this.popolareSelectCausaEStato();
    this.suggProgetto = [];

    //La ricerca si attiva SOLO nel caso in cui sia stato selezionato prima un beneficiario e un bando
    let formControls = this.myForm.getRawValue();

    //aggiorno select causa e stato
    // this.popolareSelectCausaEStato()

    if (
      (formControls.beneficiario == "undefined" || formControls.beneficiario == null || formControls.beneficiario == "") ||
      (formControls.bando == undefined || formControls.bando == null || formControls.bando == "")
      ) {

        this.showMessageError("Per inserire il progetto è necessario aver indicato prima il beneficiario e il bando.")

    } else {

    //Nella JIRA è stato richiesto di rimuovere l'obbligo di inserire almeno 3 caratteri prima di fare la suggest
    //if (value.length >= 3) {

      this.suggProgetto =  [
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

          this.suggProgetto =  [
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
  suggerimentoCausaPropostaRevoca() {

    this.suggCausaPropostaRevoca = [];

    this.suggCausaPropostaRevoca =  [
      {
        desc: "Caricamento...",
        id: "",
        altro: "",
      }
    ];

    let formControls = this.myForm.getRawValue();

    console.log("FORM SUGGERIMENTO CAUSA", formControls)

    let parametro: FiltroRevocaVO = {
      idTipologiaRevoca: "1",
      numeroRevoca: formControls.numeroPropostaRevoca?.desc == "Nessuna corrispondenza..." ? null : formControls.numeroPropostaRevoca?.desc,
      idSoggetto: formControls.beneficiario?.id,
      idProgetto: formControls.progetto?.id,
      progrBandoLineaIntervent: formControls.bando?.id,
      idCausaRevoca: null,
      idStatoRevoca: formControls.statoPropostaRevoca?.id,
      idAttivitaRevoca: null,
      dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
      dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
      value: null,
    }

    this.suggestService.suggestCausaRevoche(parametro).subscribe(data => {

      if (data.length > 0) {

        this.suggCausaPropostaRevoca = data;

      } else {

        this.suggCausaPropostaRevoca =  [
          {
            desc: "Nessun risultato disponibile",
            id: "",
            altro: "",
          }
        ];
      }

    });

  }
  suggerimentoStatoPropostaRevoca() {

    this.suggStatoPropostaRevoca =  [
      {
        desc: "Caricamento...",
        id: "",
        altro: "",
      }
    ];

    let formControls = this.myForm.getRawValue();

    let parametro: FiltroRevocaVO = {
        idTipologiaRevoca: "1",
       // numeroRevoca: null,
        numeroRevoca: formControls.numeroPropostaRevoca?.desc == "Nessuna corrispondenza..." ? null : formControls.numeroPropostaRevoca?.desc,
        idSoggetto: formControls.beneficiario?.id,
        idProgetto: formControls.progetto?.id,
        progrBandoLineaIntervent: formControls.bando?.id,
        idCausaRevoca: formControls.causaPropostaRevoca?.id,
        idStatoRevoca: null,
        idAttivitaRevoca: null,
        dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
        dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
        value: null,
      }

    this.suggestService.suggestStatoRevoche(parametro).subscribe(data => {

      if (data.length > 0) {

        this.suggStatoPropostaRevoca = data;

      } else {

        this.suggStatoPropostaRevoca =  [
          {
            desc: "Nessun risultato disponibile",
            id: "",
            altro: "",
          }
        ];

      }

    });

  }
  suggerimentoNumeroPropostaRevoca(value: string) {
    this.popolareSelectCausaEStato();
    this.myForm.get('statoPropostaRevoca').setValue(null);
    this.myForm.get('causaPropostaRevoca').setValue(null);
    this.suggNumeroPropostaRevoca = [];

      this.suggNumeroPropostaRevoca =  [
        {
          desc: "Caricamento...",
          id: "",
          altro: "",
        }
      ];

      let formControls = this.myForm.getRawValue();

      let parametro: FiltroRevocaVO = {
        idTipologiaRevoca: "1",
        numeroRevoca: null,
        idSoggetto: formControls.beneficiario?.id,
        idProgetto: formControls.progetto?.id,
        progrBandoLineaIntervent: formControls.bando?.id,
        idCausaRevoca: formControls.causaPropostaRevoca?.id,
        idStatoRevoca: formControls.statoPropostaRevoca?.id,
        idAttivitaRevoca: null,
        dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
        dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
        value: value,
      }

      console.log("PARAMETRO NUMERO REVOCA", parametro)

      this.suggestService.suggestNumeroRevoche(parametro).subscribe(data => {

        if (data.length > 0) {

          this.suggNumeroPropostaRevoca = data;

        } else {

          this.suggNumeroPropostaRevoca = [
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
  popolareSelectCausaEStato(){

    let formControls = this.myForm.getRawValue();

    if(formControls.numeroPropostaRevoca?.desc != "Nessuna corrispondenza...") {
      this.suggerimentoCausaPropostaRevoca();
      this.suggerimentoStatoPropostaRevoca();
    }

  }

  search() {

    let formControls = this.myForm.getRawValue();

    console.log("FORM CONTROL", formControls);

    if (
      (formControls.dataPropRevocaDa == "" || formControls.dataPropRevocaDa == null || formControls.dataPropRevocaDa == undefined) &&
      (formControls.dataPropRevocaA == "" || formControls.dataPropRevocaA == null || formControls.dataPropRevocaA == undefined) &&
      (formControls.beneficiario == "" || formControls.beneficiario == null || formControls.beneficiario == undefined) &&
      (formControls.bando == "" || formControls.bando == null || formControls.bando == undefined) &&
      (formControls.progetto == "" || formControls.progetto == null || formControls.progetto == undefined) &&
      (formControls.causaPropostaRevoca == ""  || formControls.causaPropostaRevoca == null || formControls.causaPropostaRevoca == undefined) &&
      (formControls.statoPropostaRevoca == "" || formControls.statoPropostaRevoca == null || formControls.statoPropostaRevoca == undefined) &&
      (formControls.numeroPropostaRevoca == "" || formControls.numeroPropostaRevoca == null || formControls.numeroPropostaRevoca == undefined)
      ) {

      this.showMessageError("Inserire almeno un parametro di ricerca.")

      this.listTab = [];
      this.dataSource = new MatTableDataSource<PropostaRevocaVO>(this.listTab);


    } else {

      this.spinner = true;

      this.resetMessageError();

      let parametro: FiltroPropostaRevocaVO = {
        idSoggetto: formControls.beneficiario?.id,
        idBando: formControls.bando?.id,
        idProgetto: formControls.progetto?.id,
        idCausaleBlocco: formControls.causaPropostaRevoca?.id,
        idStatoRevoca: formControls.statoPropostaRevoca?.id,
        numeroPropostaRevoca: formControls.numeroPropostaRevoca?.desc,
        dataPropostaRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
        dataPropostaRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined
      };

      this.resService.cerca(parametro).subscribe(data =>  {

        if(data.length == 0) {

          this.spinner = false;
          this.showMessageError("Nessuna proposta di revoca trovata per i parametri inseriti.")
          this.listTab = [];
          this.dataSource = new MatTableDataSource<PropostaRevocaVO>(this.listTab);

        } else {
          this.spinner = false;

        //  console.table(data);

          //memorizzo l'array arrivato
          this.listTab = data;
          this.dataSource = new MatTableDataSource<PropostaRevocaVO>(this.listTab);

          if (this.navigationService.filtroRicercaPropostaRevocaSelezionato) {
            this.ripristinaPaginator();
          } else {
            console.log("Paginator");
            this.paginator.length = this.listTab.length;
            this.paginator.pageIndex = 0;
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;

            this.dataSource.sortingDataAccessor = (item, property) => {
              switch (property) {
                case 'numeroPropostaRevoca':
                  return item.numeroPropostaRevoca;
                case 'beneficiario':
                  return item.denominazioneBeneficiario;
                case 'progetto':
                  return item.codiceVisualizzatoProgetto;
                case 'bando':
                  return item.nomeBandoLinea;
                case 'causaPropostaRevoca':
                  return item.descCausaleBlocco;
                case 'dtPropostaRevoca':
                  return item.dataPropostaRevoca?.getTime();
                case 'statoPropostaRevoca':
                  return item.descStatoRevoca;
                case 'dtStatoPropostaRevoca':
                  return item.dataStatoRevoca?.getTime();
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

  modificaRevoca(element: PropostaRevocaVO) {

    this.saveDataForNavigation();

     this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_GESTIONE_REVOCHE_PROPOSTE + "/modificaRevoca"],
      { queryParams: { idGestioneRevoca: element.idPropostaRevoca, idDomanda: element.idDomanda, idSoggetto: element.idSoggetto} }
    );

  }

  //BLUR: se non viene selezionato uno dei campi dell'autocomplete, la stringa del campo viene settata vuota
  onBlurAutoComplete (form: FormGroup, fields: string[]) {
    if (!(form.get(fields[0])?.value && form.get(fields[0])?.value instanceof Object)) {
      for (let field of fields) {
        form.get(field)?.setValue(null);
      }
    }
  }
  onBlur(fields: string[]) {
    this.onBlurAutoComplete(this.myForm, fields);
  }

  nuovaProposta() {
     this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_GESTIONE_REVOCHE_PROPOSTE + "/nuovaPropostaRevoca"],
    //  { queryParams: { idGestioneRevoca: element.idPropostaRevoca, idDomanda: element.idDomanda, idSoggetto: element.idSoggetto} }
    );

  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
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

  compareObjects(o1: any, o2: any): boolean {
    return o1.desc === o2.desc && o1.id === o2.id;
  }

  /****************
  *** PAGINATOR ***
  ****************/
  saveDataForNavigation() {
    let formControls = this.myForm.getRawValue();

    this.navigationService.filtroRicercaPropostaRevocaSelezionato = new FormRicercaPropostaVO(
      formControls.beneficiario,
      formControls.bando,
      formControls.progetto,
      formControls.causaPropostaRevoca,
      formControls.statoPropostaRevoca,
      formControls.numeroPropostaRevoca,
      formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
      formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined
    );

    //this.navigationService.filtroRicercaPropostaRevocaSelezionato = parametro;

    console.log("Ciao");

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

}
