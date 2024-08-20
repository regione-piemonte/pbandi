/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Constants, HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DenominazioneSuggestVO } from '../../commons/denominazione-suggest-vo';
import { FiltroProcedimentoRevocaVO } from '../../commons/procedimenti-revoca-dto/filtro-procedimento-revoca-vo';
import { ProcedimentoRevocaTableVO } from '../../commons/procedimenti-revoca-dto/procedimento-revoca-table-vo';
import { SuggestIdDescVO } from '../../commons/suggest-id-desc-vo';
import { ProcedimentiRevocaResponseService } from '../../services/procedimenti-revoca-response.service';
import { SuggestResponseService } from '../../services/suggest-response.service';
import { NavigationProcedimentoRevocaService } from '../../services/navigation/navigation-procedimento-revoca.service';
import { FormRicercaProcedimentoVO } from '../../commons/procedimenti-revoca-dto/form-ricerca-procedimento-vo';
import { UserService } from 'src/app/core/services/user.service';
import { FiltroRevocaVO } from '../../commons/filtro-revoca-vo';
import { OkDialogComponent } from 'src/app/iter-autorizzativi/components/iter-autorizzativi/ok-dialog/ok-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-procedimenti-revoca',
  templateUrl: './procedimenti-revoca.component.html',
  styleUrls: ['./procedimenti-revoca.component.scss']
})

@AutoUnsubscribe({ objectName: 'subscribers' })

export class ProcedimentiRevocaComponent implements OnInit {
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
  suggCausaProcedimentoRevoca: DenominazioneSuggestVO[] = []; //CausaleBloccoSuggestVO
  suggStatoProcedimentoRevoca: DenominazioneSuggestVO[] = []; //StatoRevocaSuggestVO
  suggAttivitaProcedimentoRevoca: DenominazioneSuggestVO[] = [];
  suggNumeroProcedimentoRevoca: DenominazioneSuggestVO[] = [];

  //table
  displayedColumns: string[] = ["numeroProcedimentoRevoca", "denominazioneBeneficiario", "codiceVisualizzatoProgetto", "nomeBandoLinea", "descCausaleBlocco",
   "dataProcedimentoRevoca", "dataNotifica", "descStatoRevoca", "dataStatoRevoca", "descAttivitaRevoca", "dataAttivitaRevoca", "actions"];
  dataSource: MatTableDataSource<ProcedimentoRevocaTableVO> = new MatTableDataSource<ProcedimentoRevocaTableVO>([]);
  listTab: Array<ProcedimentoRevocaTableVO>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  subscribers: any = {};
  idUtente: any;
  user: UserInfoSec;
  errore: any;
  message: string;

  constructor(
    private fb: FormBuilder,
    private suggestService: SuggestResponseService,
    private resService: ProcedimentiRevocaResponseService, //ProposteRevocaResponseService,
    private handleExceptionService: HandleExceptionService,
    private router: Router,
    private navigationService: NavigationProcedimentoRevocaService,
    private userService: UserService,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      beneficiario: new FormControl(""),
      bando: new FormControl(""),
      progetto: new FormControl(""),
      causaProcedimentoRevoca: new FormControl(""),
      statoProcedimentoRevoca: new FormControl(""),
      numeroProcedimentoRevoca: new FormControl(""),
      attivitaProcedimentoRevoca: new FormControl(""),
      dataProcedRevocaDa: new FormControl(""),
      dataProcedRevocaA: new FormControl(""),
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = data.idUtente;
        let filtro = this.navigationService.filtroRicercaProcedimentoRevocaSelezionato;

        if (filtro) {
          this.myForm = this.fb.group({
            beneficiario: new FormControl(filtro.beneficiario),
            bando: new FormControl(filtro.bando),
            progetto: new FormControl(filtro.progetto),
            causaProcedimentoRevoca: new FormControl(filtro.causaProcedimentoRevoca == undefined ? "" : filtro.causaProcedimentoRevoca),
            statoProcedimentoRevoca: new FormControl(filtro.statoProcedimentoRevoca == undefined ? "" : filtro.statoProcedimentoRevoca),
            numeroProcedimentoRevoca: new FormControl(filtro.numeroProcedimentoRevoca == undefined ? "" : filtro.numeroProcedimentoRevoca),
            attivitaProcedimentoRevoca: new FormControl(filtro.attivitaProcedimentoRevoca == undefined ? "" : filtro.attivitaProcedimentoRevoca),
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
      causaProcedimentoRevoca: new FormControl(""),
      statoProcedimentoRevoca: new FormControl(""),
      numeroProcedimentoRevoca: new FormControl(""),
      attivitaProcedimentoRevoca: new FormControl(""),
      dataProcedRevocaDa: new FormControl(""),
      dataProcedRevocaA: new FormControl(""),
    });

    this.popolareSelectCausaEStato();
  }

  suggerimentoBeneficiario(value: string) {
    this.popolareSelectCausaEStato();
    this.suggBeneficiario = [];

    let formControls = this.myForm.getRawValue();

    console.log("FORMCONTROLS", formControls)

    //Se il bando è già stato inserito, il suggest mi compare nel momento in cui clicco il campo beneficiario
    if (formControls.bando?.id) {

      this.suggBeneficiario = [
        {
          desc: "Caricamento...",
          id: "",
          altro: "",
        }
      ]

      this.suggestService.suggestBeneficiario(value, formControls.bando?.id == undefined ? "-1" : formControls.bando?.id).subscribe(data => {

        if (data?.length > 0) {

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

  suggerimentoCausaProcedimentoRevoca() {
    this.suggCausaProcedimentoRevoca = [
      {
        desc: "Caricamento...",
        id: "",
        altro: ""
      }
    ];

    let formControls = this.myForm.getRawValue();
console.log(formControls);

    let parametro: FiltroRevocaVO = {
      idTipologiaRevoca: "2",
      numeroRevoca: formControls.numeroProcedimentoRevoca?.desc == "Nessuna corrispondenza..." ? null : formControls.numeroProcedimentoRevoca?.desc,
      idSoggetto: formControls.beneficiario?.id,
      idProgetto: formControls.progetto?.id,
      progrBandoLineaIntervent: formControls.bando?.id,
      idCausaRevoca: null,
      idStatoRevoca: formControls.statoProcedimentoRevoca?.id,
      idAttivitaRevoca: formControls.attivitaProcedimentoRevoca?.id,
      dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
      dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
      value: null,
    }

    this.suggestService.suggestCausaRevoche(parametro).subscribe(data => {

      if (data.length > 0) {

        this.suggCausaProcedimentoRevoca = data;

      } else {

        this.suggCausaProcedimentoRevoca = [
          {
            desc: "Nessun risultato disponibile",
            id: "",
            altro: ""
          }
        ];
      }

    });

  }

  suggerimentoStatoProcedimentoRevoca() {

    this.suggStatoProcedimentoRevoca = [
      {
        desc: "Caricamento...",
        id: "",
        altro: ""
      }
    ];

    let formControls = this.myForm.getRawValue();

    let parametro: FiltroRevocaVO = {
      idTipologiaRevoca: "2",
      numeroRevoca: formControls.numeroProcedimentoRevoca?.desc == "Nessuna corrispondenza..." ? null : formControls.numeroProcedimentoRevoca?.desc,
      idSoggetto: formControls.beneficiario?.id,
      idProgetto: formControls.progetto?.id,
      progrBandoLineaIntervent: formControls.bando?.id,
      idCausaRevoca: formControls.causaProcedimentoRevoca?.id,
      idStatoRevoca: null,
      idAttivitaRevoca: formControls.attivitaProcedimentoRevoca?.id,
      dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
      dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
      value: null,
    }

    this.suggestService.suggestStatoRevoche(parametro).subscribe(data => {
      if (data.length > 0) {

        this.suggStatoProcedimentoRevoca = data;

      } else {

        this.suggStatoProcedimentoRevoca = [
          {
            desc: "Nessun risultato disponibile",
            id: "",
            altro: ""
          }
        ];

      }

    });

  }

  suggerimentoAttivitaProcedimentoRevoca() {
    this.suggAttivitaProcedimentoRevoca = [{ desc: "Caricamento...",id: "",altro: ""}];

    let formControls = this.myForm.getRawValue();

    let parametro: FiltroRevocaVO = {
      idTipologiaRevoca: "2",
      numeroRevoca: formControls.numeroProcedimentoRevoca?.desc == "Nessuna corrispondenza..." ? null : formControls.numeroProcedimentoRevoca?.desc,
      idSoggetto: formControls.beneficiario?.id,
      idProgetto: formControls.progetto?.id,
      progrBandoLineaIntervent: formControls.bando?.id,
      idCausaRevoca: formControls.causaProcedimentoRevoca?.id,
      idStatoRevoca: formControls.statoProcedimentoRevoca?.id,
      idAttivitaRevoca: null,
      dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
      dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
      value: null,
    }

    this.suggestService.suggestAttivitaRevoche(parametro).subscribe(data => {
      if (data.length > 0) {

        this.suggAttivitaProcedimentoRevoca = data;

      } else {

        this.suggAttivitaProcedimentoRevoca = [
          {
            desc: "Nessun risultato disponibile",
            id: "",
            altro: ""
          }
        ];

      }

    });

  }

  suggerimentoNumeroProcedimentoRevoca(value: string) {
    this.popolareSelectCausaEStato();
    this.myForm.get('attivitaProcedimentoRevoca').setValue(null);
    this.myForm.get('statoProcedimentoRevoca').setValue(null);
    this.myForm.get('causaProcedimentoRevoca').setValue(null);
    this.suggNumeroProcedimentoRevoca = [
      {
        desc: "Caricamento...",
        id: "",
        altro: "",
      }
    ];

    let formControls = this.myForm.getRawValue();

    let parametro: FiltroRevocaVO = {
      idTipologiaRevoca: "2",
      numeroRevoca: null,
      idSoggetto: formControls.beneficiario?.id,
      idProgetto: formControls.progetto?.id,
      progrBandoLineaIntervent: formControls.bando?.id,
      idCausaRevoca: formControls.causaPropostaRevoca?.id,
      idStatoRevoca: formControls.statoPropostaRevoca?.id,
      idAttivitaRevoca: formControls.attivitaProcedimentoRevoca?.id,
      dataRevocaFrom: formControls.dataPropRevocaDa != "" ? formControls.dataPropRevocaDa : undefined,
      dataRevocaTo: formControls.dataPropRevocaA != "" ? formControls.dataPropRevocaA : undefined,
      value: value,
    }

    console.log("PARAMETRO NUMERO REVOCA", parametro)

    this.suggestService.suggestNumeroRevoche(parametro).subscribe(data => {

      if (data.length > 0) {

        this.suggNumeroProcedimentoRevoca = data;


      } else {

        this.suggNumeroProcedimentoRevoca = [
          {
            desc: "Nessuna corrispondenza...",
            id: "",
            altro: "",
          }
        ];

      }
    })

    /*
  } else {

    this.suggNumeroProcedimentoRevoca = [""]

  }
  */
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
  /*
  displayNumeroRevoca(element: NumeroRevocaSuggestVO): string {
    return element && element.numeroRevoca ? element.numeroRevoca : '';
  }
  */

  //Metodo che popola i campi delle due select (causa e stato) ogni volta che viene inserito un nuovo campo nel form
  popolareSelectCausaEStato() {

    let formControls = this.myForm.getRawValue();

    if (formControls.numeroProcedimentoRevoca?.desc != "Nessuna corrispondenza...") {
      this.suggerimentoCausaProcedimentoRevoca();
      this.suggerimentoStatoProcedimentoRevoca();
      this.suggerimentoAttivitaProcedimentoRevoca();
    }

  }

  search() {

    let formControls = this.myForm.getRawValue();

    if (
      (formControls.dataProcedRevocaDa == "") &&
      (formControls.dataProcedRevocaA == "") &&
      (formControls.beneficiario.length < 3) &&
      (formControls.bando.length < 3) &&
      (formControls.progetto.length < 3) &&
      (formControls.causaProcedimentoRevoca == "") &&
      (formControls.statoProcedimentoRevoca == "") &&
      (formControls.numeroProcedimentoRevoca.length < 3) &&
      (formControls.attivitaProcedimentoRevoca == "")
    ) {

      this.showMessageError("Inserire almeno un parametro di ricerca.")

    } else {

      this.spinner = true;

      this.resetMessageError();

      let parametro: FiltroProcedimentoRevocaVO = {
        idSoggetto: formControls.beneficiario?.id,
        progrBandoLineaIntervent: formControls.bando?.id,
        idProgetto: formControls.progetto?.id,
        idCausaleBlocco: formControls.causaProcedimentoRevoca?.id,
        idStatoRevoca: formControls.statoProcedimentoRevoca?.id,
        numeroProcedimentoRevoca: formControls.numeroProcedimentoRevoca?.desc,
        idAttivitaRevoca: formControls.attivitaProcedimentoRevoca?.id,
        dataProcedimentoRevocaFrom: formControls.dataProcedRevocaDa != "" ? formControls.dataProcedRevocaDa : undefined,
        dataProcedimentoRevocaTo: formControls.dataProcedRevocaA != "" ? formControls.dataProcedRevocaA : undefined
      }

      this.resService.cercaProcedimento(parametro).subscribe(data => {

        if (data.length == 0) {

          this.listTab = data;
          this.dataSource = new MatTableDataSource<ProcedimentoRevocaTableVO>(this.listTab);

          this.mostraTab = false;
          this.criteriRicercaOpened = true;

          this.spinner = false;

          this.showMessageError("Nessun procedimento di revoca trovata per i parametri inseriti.")

        } else {

          //memorizzo l'array arrivato
          this.listTab = data;
          this.dataSource = new MatTableDataSource<ProcedimentoRevocaTableVO>(this.listTab);

          console.log("this.navigationService.filtroRicercaProvvedimentoRevocaSelezionato", this.navigationService.filtroRicercaProcedimentoRevocaSelezionato)

          if (this.navigationService.filtroRicercaProcedimentoRevocaSelezionato) {
            this.ripristinaPaginator();
          } else {
            this.paginator.length = this.listTab.length;
            this.paginator.pageIndex = 0;
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;

            this.dataSource.sortingDataAccessor = (item, property) => {
              switch (property) {
                case 'numeroProcedimentoRevoca':
                  return item.numeroProcedimentoRevoca;
                case 'beneficiario':
                  return item.denominazioneBeneficiario;
                case 'progetto':
                  return item.codiceVisualizzatoProgetto;
                case 'bando':
                  return item.nomeBandoLinea;
                case 'causaProcedimentoRevoca':
                  return item.descCausaleBlocco;
                case 'statoProcedimentoRevoca':
                  return item.descCausaleBlocco;
                case 'dtStatoProcedimentoRevoca':
                  return item.dataProcedimentoRevoca?.getTime();
                case 'attivita':
                  return item.descAttivitaRevoca;
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

  modificaProcedimentoRevoca(element: ProcedimentoRevocaTableVO) {

    this.saveDataForNavigation();

    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_GESTIONE_REVOCHE_PROCEDIMENTI + "/modificaProcedimentoRevoca"], {
      queryParams: {
        "idProcedimentoRevoca": element.idProcedimentoRevoca
      }
    });

  }

  /* eliminaProcedimentoRevoca(element: ProcedimentoRevocaTableVO) {
    this.saveDataForNavigation();

    this.resService.deleteProcedimentoRevoca(element.numeroProcedimentoRevoca.toString()).subscribe(data => {
      if (data == 'OK') {
        console.log("Procedimento rimosso con successo");
        this.search();

      } else {
      }

    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });

  } */

  eliminaProcedimentoRevoca(element) {
    const dialogRef = this.dialog.open(OkDialogComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        title: 'Elimina procedimento di revoca',
        message: 'Sei sicuro di voler eliminare il procedimento selezionato?',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if(result === true){
        let dataBack =  {
          idWorkFlowList :[element.idWorkFlow],
          motivazione:null,
         }
         this.resService.deleteProcedimentoRevoca(element.numeroProcedimentoRevoca.toString()).subscribe(data => {
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
          this.message =  "Errore non è stato possibile eliminare il procedimento";
          setTimeout(() => {
            this.errore = null;
            this.search();
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

  onBlur(fields: string[]) {
    this.onBlurAutoComplete(this.myForm, fields);
  }
  //BLUR: se non viene selezionato uno dei campi dell'autocomplete, la stringa del campo viene settata vuota
  onBlurAutoComplete(form: FormGroup, fields: string[]) {
    if (!(form.get(fields[0])?.value && form.get(fields[0])?.value instanceof Object)) {
      for (let field of fields) {
        form.get(field)?.setValue(null);
      }
    }
  }

  /****************
  *** PAGINATOR ***
  ****************/
  saveDataForNavigation() {
    let formControls = this.myForm.getRawValue();

    this.navigationService.filtroRicercaProcedimentoRevocaSelezionato = new FormRicercaProcedimentoVO(
      formControls.beneficiario,
      formControls.bando,
      formControls.progetto,
      formControls.causaProcedimentoRevoca,
      formControls.statoProcedimentoRevoca,
      formControls.numeroProcedimentoRevoca,
      formControls.attivitaProcedimentoRevoca,
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
}
