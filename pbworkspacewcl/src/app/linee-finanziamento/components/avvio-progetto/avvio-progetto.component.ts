/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserInfoSec } from 'src/app/core/commons/vo/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { Progetto } from '../../commons/dto/progetto';
import { AvviaProgettiRequest } from '../../commons/requests/avvia-progetti-request';
import { RicercaProgettiRequest } from '../../commons/requests/ricerca-progetti-request';
import { LineeFinanziamentoService } from '../../services/linee-finanziamento.service';
import { NavigationAvvioProgettoService } from '../../services/navigation-avvio-progetto.service';

@Component({
  selector: 'app-avvio-progetto',
  templateUrl: './avvio-progetto.component.html',
  styleUrls: ['./avvio-progetto.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AvvioProgettoComponent implements OnInit {

  user: UserInfoSec;
  idLinea: number;
  inserimentoModificaProgettoAbilitati: boolean;
  esistonoProgettiSifAvviati: boolean;
  lineaFinanziamento: string;
  criteriRicercaOpened: boolean = true;
  isResultVisible: boolean;
  codiceProgetto: string;
  cup: string;
  titoloProgetto: string;
  beneficiario: string;
  isAccedi: boolean;

  progetti: Array<Progetto>;

  dataSource: ProgettiDatasource;
  displayedColumns: string[] = ['codice', 'cup', 'titolo', 'beneficiario', 'azioni'];

  @ViewChild("paginator", { static: true }) paginator: MatPaginator;
  @ViewChild("sort", { static: true }) sort: MatSort;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedInizializzazione: boolean;
  loadedRicerca: boolean = true;
  loadedAvvia: boolean = true;
  loadedAccedi: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private lineeFinanziamentoService: LineeFinanziamentoService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private navigationService: NavigationAvvioProgettoService
  ) { }

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.paramMap.get('idLinea')) {
      this.idLinea = +this.activatedRoute.snapshot.paramMap.get('idLinea');
    }
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (data && !this.isAccedi) {
        this.loadedInizializzazione = false;
        this.subscribers.inizializza = this.lineeFinanziamentoService.inizializzaLineeDiFinanziamento(this.idLinea, this.user.idSoggetto, this.user.codiceRuolo).subscribe(init => {
          if (init) {
            this.inserimentoModificaProgettoAbilitati = init.inserimentoModificaProgettoAbilitati;
            this.esistonoProgettiSifAvviati = init.esistonoProgettiSifAvviati;
            this.lineaFinanziamento = init.nomeBandoLinea;
            this.codiceProgetto = this.navigationService.lastCodiceProgetto;
            this.cup = this.navigationService.lastCup;
            this.titoloProgetto = this.navigationService.lastTitoloProgetto;
            this.beneficiario = this.navigationService.lastBeneficiario;
            this.ricerca();
          }
          this.loadedInizializzazione = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }
    });
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  ricerca() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedRicerca = false;
    this.subscribers.ricerca = this.lineeFinanziamentoService.ricercaProgetti(new RicercaProgettiRequest(this.idLinea, this.codiceProgetto, this.titoloProgetto, this.cup,
      this.beneficiario, this.user.idSoggetto, this.user.codiceRuolo)).subscribe(data => {
        if (data) {
          this.progetti = data;
          this.dataSource = new ProgettiDatasource(this.progetti);
          if (this.navigationService.lastCodiceProgetto || this.navigationService.lastCup
            || this.navigationService.lastTitoloProgetto || this.navigationService.lastBeneficiario) {
            this.ripristinaSortPaginator()
          } else {
            this.paginator.length = this.progetti.length;
            this.paginator.pageIndex = 0;
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
          }
          this.isResultVisible = true;
          this.criteriRicercaOpened = false;
        }
        this.loadedRicerca = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di ricerca.");
        this.loadedRicerca = true;
      });
  }

  modificaProgetto(idProgetto: number) {
    this.saveDataForNavigation();
    this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_LINEE_FINANZIAMENTO}/progetto/${this.idLinea}/${idProgetto}`]);
  }

  avvia(progetto?: Progetto) {
    this.loadedAvvia = false;
    let request = new AvviaProgettiRequest(this.idLinea, progetto ? [progetto] : this.progetti, this.user.idSoggetto, this.user.codiceRuolo);
    this.subscribers.avvia = this.lineeFinanziamentoService.avviaProgetti(request).subscribe(data => {
      if (data) {
        this.ricerca();
        let messaggio: string = '';
        if (data.messaggi && data.messaggi.length > 0) {
          data.messaggi.forEach(m => {
            messaggio += m + "<br/>";
          });
          messaggio = messaggio.substr(0, messaggio.length - 5);
          this.showMessageSuccess(messaggio);
        }
        let errore: string = '';
        if (data.errori && data.errori.length > 0) {
          data.errori.forEach(m => {
            errore += m + "<br/>";
          });
          errore = errore.substr(0, errore.length - 5);
          this.showMessageError(errore);
        }
        //ricarico lo userInfo e i suoi beneficiari perchè avviando il progetto, se c'è un nuovo beneficiario, deve essere aggiunto
        this.loadedAccedi = false;
        this.isAccedi = true;
        this.subscribers.accedi = this.userService.accedi();
        this.subscribers.accedi$ = this.userService.accediSubject.subscribe(data => {
          this.loadedAccedi = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }
      this.loadedAvvia = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di avvio.");
      this.loadedAvvia = true;
    });
  }

  goToNuovoProgetto() {
    this.saveDataForNavigation();
    this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_LINEE_FINANZIAMENTO}/progetto/${this.idLinea}`])
  }

  goToLinee() {
    this.saveDataForNavigation();
    this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_LINEE_FINANZIAMENTO}/lineeDiFinanziamento`);
  }

  saveDataForNavigation() {
    this.navigationService.lastCodiceProgetto = this.codiceProgetto;
    this.navigationService.lastCup = this.cup;
    this.navigationService.lastTitoloProgetto = this.titoloProgetto;
    this.navigationService.lastBeneficiario = this.beneficiario;
    this.salvaSortPaginator();
  }

  salvaSortPaginator() {
    if (this.dataSource) {
      this.navigationService.sortDirectionTable = this.dataSource.sort.direction;
      this.navigationService.sortActiveTable = this.dataSource.sort.active;
      this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
      this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    }
  }

  ripristinaSortPaginator() {
    if (this.navigationService.sortDirectionTable != null && this.navigationService.sortDirectionTable != undefined) {
      this.sort.direction = this.navigationService.sortDirectionTable;
      this.sort.active = this.navigationService.sortActiveTable;
      this.dataSource.sort = this.sort;
    }
    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
      this.paginator.pageIndex = this.navigationService.paginatorPageIndexTable;
      this.paginator.pageSize = this.navigationService.paginatorPageSizeTable;
      this.dataSource.paginator = this.paginator;
    }
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedInizializzazione || !this.loadedRicerca || !this.loadedAvvia || !this.loadedAccedi) {
      return true;
    }
    return false;
  }

}

export class ProgettiDatasource extends MatTableDataSource<Progetto> {

  _orderData(data: Progetto[]): Progetto[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "data";
    }
    let direction = this.sort.direction || "desc";
    let sortedData = null;

    // viene riordinato in ordine ascendente 
    switch (this.sort.active) {
      case "codice":
        sortedData = data.sort((progA, progB) => {
          return progA.codice ? (progB.codice ? progA.codice.localeCompare(progB.codice) : -1) : 1;
        });
        break;
      case "cup":
        sortedData = data.sort((progA, progB) => {
          return progA.cup ? (progB.cup ? progA.cup.localeCompare(progB.cup) : -1) : 1;
        });
        break;
      case "titolo":
        sortedData = data.sort((progA, progB) => {
          return progA.titolo.localeCompare(progB.titolo);
        });
        break;
      case "beneficiario":
        sortedData = data.sort((progA, progB) => {
          return progA.beneficiario ? (progB.beneficiario ? progA.beneficiario.localeCompare(progB.beneficiario) : -1) : 1;
        });
        break;
      default:
        sortedData = data.sort((progA, progB) => {
          return progA.titolo.localeCompare(progB.titolo);
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: Progetto[]) {
    super(data);
  }
}