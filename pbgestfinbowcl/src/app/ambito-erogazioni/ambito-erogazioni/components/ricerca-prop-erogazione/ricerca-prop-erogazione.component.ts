/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { MatSort, Sort } from '@angular/material/sort';
import { AmbErogResponseService } from '../../services/amb-erog-response.service';
import { PropostaErogazioneVO } from '../../commons/proposta-erogazione-vo';
import { MatTableDataSource } from '@angular/material/table';
import { NavigationPropostaErogazioneService } from '../../services/navigation-proposta-erogazione.service';
import { FormPropostaErogazione } from '../../commons/form-proposta-erogazione';
import { Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { AgevolazioneSuggestVO } from '../../commons/agevolazione-suggest-vo';
import { SuggestVO } from '../../commons/suggest-vo';
import { SuggestIdDescVO } from 'src/app/revoche/commons/suggest-id-desc-vo';

@Component({
  selector: 'app-ricerca-prop-erogazione',
  templateUrl: './ricerca-prop-erogazione.component.html',
  styleUrls: ['./ricerca-prop-erogazione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RicercaPropErogazioneComponent implements OnInit {

  public myForm: FormGroup;
  fin: Array<PropostaErogazioneVO>;
  idUtente: any;

  user: UserInfoSec;

  suggBando: SuggestVO[] = [];
  suggAgevolazione: SuggestVO[] = [];
  suggFondoFinpis: SuggestVO[] = [];
  suggDenom: SuggestVO[] = [];
  suggCodPro: SuggestVO[] = [];

  spinner: boolean;
  showResults: boolean = false;

  messageError: string;
  isMessageErrorVisible: boolean;

  criteriRicercaOpened: boolean = true;
  displayedColumns: string[] = ["codiceProgetto", "beneficiario", "importoLordo", "importoIres", "importoNetto", "dataConcessione", "controlliPreErogazione", "statoRichiestaAntimafia", "statoRichiestaDurc", "flagFinistr"];
  dataSource: MatTableDataSource<PropostaErogazioneVO> = new MatTableDataSource<PropostaErogazioneVO>([]);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  public subscribers: any = {};

  constructor(
    private fb: FormBuilder,
    private resService: AmbErogResponseService,
    private handleExceptionService: HandleExceptionService,
    private navigationService: NavigationPropostaErogazioneService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      bando: new FormControl(""),
      agevolazione: new FormControl(""),
      codiceFondoFinpis: new FormControl(""),
      denominazione: new FormControl(""),
      codiceProgetto: new FormControl(""),
      contrPreErogazione: new FormControl("T"),
    });

    //secondo me questo metodo va a cercare se l'utente ha già fatto questa ricerca e
    //mostra all'interno dei campi del form i valori precedentemente inseriti.
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = data.idUtente;
        let filtro = this.navigationService.filtroRicercaProposteErogazioneSelezionato;
        if (filtro) {
          this.myForm = this.fb.group({
            bando: new FormControl(filtro.bando),
            agevolazione: new FormControl(filtro.agevolazione),
            codiceFondoFinpis: new FormControl(filtro.codiceFondoFinpis),
            denominazione: new FormControl(filtro.denominazione),
            codiceProgetto: new FormControl(filtro.codiceProgetto),
            contrPreErogazione: new FormControl(filtro.contrPreErogazione)
          });

          this.search();
        }
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  annullaFiltri() {
    this.myForm.reset();
    this.myForm = this.fb.group({
      bando: new FormControl(""),
      agevolazione: new FormControl(""),
      codiceFondoFinpis: new FormControl(""),
      denominazione: new FormControl(""),
      codiceProgetto: new FormControl(""),
      contrPreErogazione: new FormControl("N"),
    });
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

  sugg(id: number, value: string) {

    if (id == 1) {

      if (value.length >= 3) {
        this.suggBando = [{id : undefined, desc : "Caricamento..."}];
        this.resService.getSuggestion(value, 1).subscribe(data => { if (data.length > 0) { this.suggBando = data } else { this.suggBando = [{id : undefined, desc : "Nessuna corrispondenza"}] } })
      } else { this.suggBando = [] }

    } else if (id == 2) {

      // if (value.length >= 3) {
      this.suggAgevolazione = [{id : undefined, desc : "Caricamento..."}];
      this.resService.getSuggestion(value, 2).subscribe(data => { if (data.length > 0) { this.suggAgevolazione = data } else { this.suggAgevolazione = [{id : undefined, desc : "Nessuna corrispondenza"}] } })
      // } else { this.suggAgevolazione = [] }

    } else if (id == 3) {

      if (value.length >= 3) {
        this.suggDenom = [{id : undefined, desc : "Caricamento..."}];
        this.resService.getSuggestion(value, 3).subscribe(data => { if (data.length > 0) { this.suggDenom = data } else { this.suggDenom = [{id : undefined, desc : "Nessuna corrispondenza"}] } })
      } else {
        this.suggDenom = []
      }

    } else if (id == 4) {

      if (value.length >= 1) {
        this.suggDenom = [{id : undefined, desc : "Caricamento..."}];
        this.resService.getSuggestion(value, 4).subscribe(data => { if (data.length > 0) { this.suggFondoFinpis = data } else { this.suggFondoFinpis = [{id : undefined, desc : "Nessuna corrispondenza"}] } })
      } else {
        this.suggDenom = []
      }

    } else if (id == 5) {

      if (value.length >= 5) {
        this.suggCodPro = [{id : undefined, desc : "Caricamento..."}];
        this.resService.getSuggestion(value, 5).subscribe(data => { if (data.length > 0) { this.suggCodPro = data } else { this.suggCodPro = [{id : undefined, desc : "Nessuna corrispondenza"}] } })
      } else {
        this.suggCodPro = []
      }

    }
  }

  displayDesc(element: SuggestVO): string {
    return element && element.desc ? element.desc : '';
  }

  search() {

    let formControls = this.myForm.getRawValue();

    console.log(formControls.agevolazione);

    if (
      (formControls.bando?.length < 3) &&
      (formControls.agevolazione?.length < 3) &&
      (formControls.codiceFondoFinpis?.length < 3) &&
      (formControls.denominazione?.length < 3) &&
      (formControls.codiceProgetto?.length < 1)) {

      this.showMessageError("Inserire almeno 3 caratteri per avviare una ricerca.")

    } else {

      this.spinner = true;

      this.resetMessageError();

      this.resService.cercaProposteErogazione(
        formControls.bando?.id,
        formControls.agevolazione?.id,
        formControls.codiceFondoFinpis?.id,
        formControls.denominazione?.id,
        formControls.codiceProgetto?.id,  
        formControls.contrPreErogazione).subscribe(data => {
          if (data) {
            this.fin = data;
            //console.table(this.fin);
            this.dataSource = new MatTableDataSource<PropostaErogazioneVO>(this.fin);
            if (this.navigationService.filtroRicercaProposteErogazioneSelezionato) {
              this.ripristinaPaginator();
            } else {
              this.paginator.length = this.fin.length;
              this.paginator.pageIndex = 0;
              this.dataSource.paginator = this.paginator;
              this.dataSource.sort = this.sort;
            }
          }
          console.log(this.fin);
          this.showResults = true;
          this.criteriRicercaOpened = false;
          this.spinner = false;
        }, err => {
          this.spinner = false;
          this.showMessageError("Errore in fase di ricerca.")
          this.handleExceptionService.handleNotBlockingError(err);
        });
    }
  }


  goToControlliPreErogazioni(element: PropostaErogazioneVO) {
    this.saveDataForNavigation();

    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_PROPOSTE + "/controlliPreErogazione"], {
      queryParams: {
        "idProposta": element.idProposta
      }
    });
  }

  saveDataForNavigation() {
    let formControls = this.myForm.getRawValue();

    this.navigationService.filtroRicercaProposteErogazioneSelezionato = new FormPropostaErogazione(
      formControls.bando,
      formControls.agevolazione,
      formControls.codiceFondoFinpis,
      formControls.denominazione,
      formControls.codiceProgetto,
      formControls.contrPreErogazione,
    );

    this.salvaPaginator();
  }

  //non so cosa faccia questo metodo...
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
