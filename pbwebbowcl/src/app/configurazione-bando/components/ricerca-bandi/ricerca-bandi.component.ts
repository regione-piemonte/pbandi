/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ActivatedRoute, Router } from '@angular/router';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ConfigurazioneBandoService } from 'src/app/configurazione-bando/services/configurazione-bando.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { BandoVo } from '../../commons/vo/bando-vo';
import { NormativaVo } from '../../commons/vo/normativa-vo';
import { NavigationConfigurazioneBandoService } from '../../services/navigation-configurazione-bando.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

@Component({
  selector: 'app-ricerca-bandi',
  templateUrl: './ricerca-bandi.component.html',
  styleUrls: ['./ricerca-bandi.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RicercaBandiComponent implements AfterViewInit {

  // Dichiarazione variabili
  normativaSelected: NormativaVo;
  normative: Array<NormativaVo>;
  nomeBando: string;
  nomeBandoLinea: string;
  criteriRicercaOpened: boolean = true;
  showTable = false;
  emptyTable = false;
  idOperazione: number;
  user: UserInfoSec;

  // tabella
  displayedColumns: string[] = ['bando', 'bandoLinea', 'azioni'];
  dataSource: MatTableDataSource<BandoVo>;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  //LOADED
  loadedNormative: boolean;
  loadedCerca: boolean = true;

  // Messaggi errore
  isMessageErrorBandoLineaVisible: boolean;
  messageErrorBandoLinea: string;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private configurazioneBandoService: ConfigurazioneBandoService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private navigationService: NavigationConfigurazioneBandoService,
    private handleExceptionService: HandleExceptionService
  ) {
    this.dataSource = new MatTableDataSource();
  }

  ngAfterViewInit() {

  }

  ngOnInit(): void {
    // Recupero parametri da url
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

  // Gestione risultati di ricerca
  cerca() {

    // Check obbligatorietà campi
    if (this.normativaSelected == undefined && (this.nomeBando == undefined || this.nomeBando == '') && (this.nomeBandoLinea == undefined || this.nomeBandoLinea == '')) {
      this.showMessageErrorBandoLinea("E’ necessario inserire almeno un campo di ricerca.");
    } else {
      this.resetMessageErrorBandoLinea();
      this.loadedCerca = false;

      // Invocazione servizio di ricerca dei bandi
      this.configurazioneBandoService.findBandi(this.user, this.normativaSelected == undefined ? undefined : this.normativaSelected.descEstesa, this.normativaSelected == undefined ? undefined : this.normativaSelected.idLinea, this.nomeBandoLinea, this.nomeBando).subscribe(data => {
        if (data == null) {
          this.emptyTable = true;
          this.showTable = false;
        } else {
          this.emptyTable = false;
          this.showTable = true;
          this.dataSource = new MatTableDataSource(data);
          if (this.navigationService.normativaSelezionata) {
            this.ripristinaPaginator();
          } else {
            this.paginator.pageIndex = 0;
            this.paginator.length = data.length;
            this.dataSource.paginator = this.paginator;
          }
        }
        this.loadedCerca = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageErrorBandoLinea("Errore in fase di ricerca dei bandi");
        this.loadedCerca = true;
      });
    }
  }

  // Caricamento dati iniziali
  loadData() {
    if (this.navigationService.nomeBandoSelected) {
      this.nomeBando = this.navigationService.nomeBandoSelected;
    }
    if (this.navigationService.nomeBandoLineaSelected) {
      this.nomeBandoLinea = this.navigationService.nomeBandoLineaSelected;
    }
    this.normative = new Array<NormativaVo>();
    this.loadedNormative = false;

    // Popolamento dropdown normativa
    this.configurazioneBandoService.getNormativePost2016(this.user).subscribe(data => {
      this.normative = data;
      if (this.navigationService.normativaSelezionata) {
        this.normativaSelected = this.normative.find(n => n.idLinea === this.navigationService.normativaSelezionata.idLinea);
      }
      if (this.nomeBando || this.nomeBandoLinea || this.normativaSelected) {
        this.cerca();
      }
      this.loadedNormative = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageErrorBandoLinea("Errore in fase di caricamento delle normative");
      this.loadedNormative = true;
    });
  }

  // Configurazione bando linea del bando
  configuraBandoLinea(row: BandoVo) {
    if (row.idBandoLinea == null) {
      this.showMessageErrorBandoLinea("E’ necessario inserire almeno un’associazione bando-linea di intervento nella sezione Linea di intervento.");
    } else {
      this.resetMessageErrorBandoLinea();
      this.saveDataForNavigation();

      this.router.navigateByUrl(`drawer/` + this.idOperazione + `/configuraBandoLinea?idLineaDiIntervento=` + row.idLineaDiIntervento + `&idBandoLinea=` +
        row.idBandoLinea + `&nomeBandoLinea=` + row.nomeBandoLinea + `&titoloBando=` + row.titoloBando + `&idBando=` + row.idBando);
    }
  }

  // Configurazione bando del bando
  configuraBando(row: BandoVo) {
    this.resetMessageErrorBandoLinea();
    this.saveDataForNavigation();

    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/configuraBando?titoloBando=` + row.titoloBando + `&nomeBandoLinea=` +
      row.nomeBandoLinea + `&idLineaDiIntervento=` + row.idLineaDiIntervento + `&idBando=` + row.idBando + `&isNuovoBando=false`)
  }

  // Creazione nuovo bando
  nuovoBando() {
    this.saveDataForNavigation();
    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/configuraBando?isNuovoBando=true`)
  }

  /* Inizio gestione salvataggio dati per navigazione */
  saveDataForNavigation() {
    this.navigationService.normativaSelezionata = this.normativaSelected;
    this.navigationService.nomeBandoSelected = this.nomeBando;
    this.navigationService.nomeBandoLineaSelected = this.nomeBandoLinea;
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
  /* Fine gestione salvataggio dati per navigazione */

  // Gestione spinner
  isLoading() {
    if (!this.loadedNormative || !this.loadedCerca) {
      return true;
    }
    return false;
  }

  /* Inizio gestione errori */
  showMessageErrorBandoLinea(msg: string) {
    this.messageErrorBandoLinea = msg;
    this.isMessageErrorBandoLineaVisible = true;
    var element = document.getElementById('scrollId')
    element.scrollIntoView();
  }

  resetMessageErrorBandoLinea() {
    this.messageErrorBandoLinea = null;
    this.isMessageErrorBandoLineaVisible = false;
  }
  /* Fine gestione errori */

  // Gestione visualizzazione dinamica risultati di ricerca
  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }
}