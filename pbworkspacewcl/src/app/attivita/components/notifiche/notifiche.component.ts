/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/vo/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { BeneficiarioSec } from 'src/app/core/commons/vo/beneficiario-vo';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { BandoVO } from '../../commons/vo/bando-vo';
import { PbandiTNotificaProcessoVO } from '../../commons/vo/pbandi-t-notifica-processo-vo';
import { ProgettoVO } from '../../commons/vo/progetto-vo';
import { AttivitaService } from '../../services/attivita.service';
import { DettaglioNotificaDialog } from '../dettaglio-notifica-dialog/dettaglio-notifica-dialog';

@Component({
  selector: 'app-notifiche',
  templateUrl: './notifiche.component.html',
  styleUrls: ['./notifiche.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NotificheComponent implements OnInit {

  idOperazione: number;
  user: UserInfoSec;
  beneficiario: BeneficiarioSec;
  bando: BandoVO;
  progrBandoLineaIntervento: number;
  idProgetto: number;
  elencoPrj: Array<ProgettoVO>;
  notifiche: Array<PbandiTNotificaProcessoVO> = new Array<PbandiTNotificaProcessoVO>();
  idNotificheChecked: Array<number> = new Array<number>();
  idArchiviateChecked: Array<number> = new Array<number>();
  allNotificheChecked: boolean;
  allArchivioChecked: boolean;
  isArchivioOpened: boolean;

  displayedColumns: string[] = ['check', 'titolo', 'data', 'progetto', 'azioni'];
  dataSourceNotifiche: NotificheDatasource;
  dataSourceArchivio: NotificheDatasource;

  @ViewChild("paginatorNotifiche", { static: true }) paginatorNotifiche: MatPaginator;
  @ViewChild("sortNotifiche", { static: true }) sortNotifiche: MatSort;
  @ViewChild("paginatorArchivio", { static: true }) paginatorArchivio: MatPaginator;
  @ViewChild("sortArchivio", { static: true }) sortArchivio: MatSort;

  //LOADED
  loadedBeneficiario: boolean;
  loadedBando: boolean;
  loadedProgetti: boolean;
  loadedNotifiche: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private router: Router,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private attivitaService: AttivitaService,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    this.progrBandoLineaIntervento = +this.activatedRoute.snapshot.paramMap.get('idBando');
    this.idProgetto = +this.activatedRoute.snapshot.paramMap.get('idProgetto');
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.loadedBeneficiario = false;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (data) {
        this.beneficiario = this.user.beneficiarioSelezionato;
        this.loadedBeneficiario = true;

        this.loadedBando = false;
        this.subscribers.bando = this.attivitaService.getBandoByProgr(this.progrBandoLineaIntervento).subscribe(data => {
          if (data) {
            this.bando = data;
          }
          this.loadedBando = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedBando = true;
        });
        this.loadedNotifiche = false;
        this.loadedProgetti = false;
        this.subscribers.progetti = this.attivitaService.getProgetti(this.beneficiario.idBeneficiario, this.progrBandoLineaIntervento).subscribe(data1 => {
          if (data1) {
            this.elencoPrj = data1;
            this.subscribers.notifiche = this.attivitaService.getNotifiche(this.progrBandoLineaIntervento, this.idProgetto != 0 ? this.idProgetto : null, this.elencoPrj).subscribe(data2 => {
              if (data2) {
                for (let d of data2) {
                  d.codiceVisualizzatoProgetto = this.elencoPrj.find(p => p.id == d.idProgetto).codiceVisualizzato;
                  this.notifiche.push(d);

                }
                this.dataSourceNotifiche = new NotificheDatasource(this.notifiche);
                this.dataSourceNotifiche.statiExcluded = new Array<string>("C");
                this.dataSourceNotifiche.progetti = this.elencoPrj.slice();
                this.paginatorNotifiche.length = this.notifiche.length;
                this.paginatorNotifiche.pageIndex = 0;
                this.dataSourceNotifiche.paginator = this.paginatorNotifiche;
                this.dataSourceNotifiche.sort = this.sortNotifiche;
                this.filterByStateNotifiche();

                this.dataSourceArchivio = new NotificheDatasource(this.notifiche);
                this.dataSourceArchivio.statiExcluded = new Array<string>("I", "R");
                this.dataSourceArchivio.progetti = this.elencoPrj.slice();
                this.paginatorArchivio.length = this.notifiche.length;
                this.paginatorArchivio.pageIndex = 0;
                this.dataSourceArchivio.paginator = this.paginatorArchivio;
                this.dataSourceArchivio.sort = this.sortArchivio;
                this.filterByStateArchiviate();
              }
              this.loadedNotifiche = true;
            }, err => {
              this.handleExceptionService.handleBlockingError(err);
            });
          }
          this.loadedProgetti = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }
    });
  }

  openDettaglio(type: string, idNotifica: number) {
    let n = this.notifiche.find(n => n.idNotifica === idNotifica)
    const dialogRef = this.dialog.open(DettaglioNotificaDialog, {
      width: '50%',
      data: { notifica: n }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (type === "N" && n.statoNotifica !== "R") {
        this.cambiaStato("N", idNotifica, "R");
      }
    });
  }

  openCloseArchivio() {
    this.isArchivioOpened = !this.isArchivioOpened;
  }

  selectAllCheckbox(type: string, e: any) {
    if (type === "N") {
      this.dataSourceNotifiche.setCheckBoxOptionForAll(e.checked);
      if (e.checked) {
        this.idNotificheChecked = this.notifiche.map(n => n.idNotifica);
      } else {
        this.idNotificheChecked = new Array<number>();
      }
    } else {
      this.dataSourceArchivio.setCheckBoxOptionForAll(e.checked);
      if (e.checked) {
        this.idArchiviateChecked = this.notifiche.map(n => n.idNotifica);
      } else {
        this.idArchiviateChecked = new Array<number>();
      }
    }



  }

  selectCheckbox(type: string, idNotifica: number, e: any) {
    if (type === "N") {
      if (e.checked) {
        this.idNotificheChecked.push(idNotifica);
      } else {
        this.idNotificheChecked = this.idNotificheChecked.filter(id => id !== idNotifica);
      }
    } else {
      if (e.checked) {
        this.idArchiviateChecked.push(idNotifica);
      } else {
        this.idArchiviateChecked = this.idArchiviateChecked.filter(id => id !== idNotifica);
      }
    }
  }

  cambiaStato(type: string, idNotifica: number, stato: string) {
    this.subscribers.cambiaStato = this.attivitaService.cambiaStatoNotifica(idNotifica, stato).subscribe(data => {
      if (data) {
        let n = this.notifiche.find(n => n.idNotifica === idNotifica);
        n.statoNotifica = stato;
        if ((stato === "C" && type === "N") || (stato !== "C" && type === "A")) {
          this.filterByStateNotifiche();
          this.filterByStateArchiviate();
        }
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  cambiaStatoSelezionate(type: string, stato: string) {
    this.subscribers.cambiaStato = this.attivitaService.cambiaStatoNotificheSelezionate(type === "N" ? this.idNotificheChecked : this.idArchiviateChecked, stato).subscribe(data => {
      if (data) {
        if (type === "N") {
          for (let id of this.idNotificheChecked) {
            let n = this.notifiche.find(n => n.idNotifica === id)
            n.statoNotifica = stato;
            n.checked = false;
            if (type === "N" && stato === "C") {
              this.filterByStateNotifiche();
              this.filterByStateArchiviate();
            }
          }
          this.idNotificheChecked = new Array<number>();
          this.allNotificheChecked = false;
        } else {
          for (let id of this.idArchiviateChecked) {
            let n = this.notifiche.find(n => n.idNotifica === id)
            n.statoNotifica = stato;
            n.checked = false;
            this.filterByStateNotifiche();
            this.filterByStateArchiviate();
          }
          this.idArchiviateChecked = new Array<number>();
          this.allArchivioChecked = false;
        }
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  filterByStateNotifiche() { //esclude le notifiche in stato chiuso/archiviato
    this.dataSourceNotifiche.filterByState();
    this.paginatorNotifiche.pageIndex = 0;
    this.paginatorNotifiche.length = this.dataSourceNotifiche.filteredData.length;
  }

  filterByStateArchiviate() { //esclude le notifiche in stato letto e da leggere
    this.dataSourceArchivio.filterByState();
    this.paginatorArchivio.pageIndex = 0;
    this.paginatorArchivio.length = this.dataSourceArchivio.filteredData.length;
  }

  tornaAAttivitaDaSvolgere() {
    this.router.navigate(["drawer/" + this.idOperazione + "/attivita"]);
  }

  isLoading() {
    if (!this.loadedBeneficiario || !this.loadedBando || !this.loadedProgetti || !this.loadedNotifiche) {
      return true;
    }
    return false;
  }

}

export class NotificheDatasource extends MatTableDataSource<PbandiTNotificaProcessoVO> {
  public statiExcluded: Array<string>;
  private filterByStateEnable: boolean = true;
  public progetti: Array<ProgettoVO>;



  setCheckBoxOptionForAll(checked: boolean) {
    this.filteredData = this._filterData(this.data);
    this.filteredData.map(data => {
      data.checked = checked;
    })
  }

  filterByState() {
    this.filteredData = this._filterData(this.data);
    this._updateChangeSubscription();
  }

  _filterData(data: PbandiTNotificaProcessoVO[]): PbandiTNotificaProcessoVO[] {
    return data.filter((item: PbandiTNotificaProcessoVO) => {

      let control = undefined;

      if (this.filterByStateEnable) {
        if (!this.statiExcluded.find(s => s == item.statoNotifica)) control = true;//return true;
        else return false;
      }

      if (control != null && control != undefined) {
        if (control) return true;
        else return false;
      }
      else
        return true;
    });
  }


  _orderData(data: PbandiTNotificaProcessoVO[]): PbandiTNotificaProcessoVO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "data";
    }
    let direction = this.sort.direction || "desc";
    let sortedData = null;

    // viene riordinato in ordine ascendente 
    switch (this.sort.active) {
      case "titolo":
        sortedData = data.sort((notA, notB) => {
          return notA.subjectNotifica.localeCompare(notB.subjectNotifica);
        });
        break;
      case "progetto":
        sortedData = data.sort((notA, notB) => {
          let progA = this.progetti.find(p => p.id === notA.idProgetto).codiceVisualizzato;
          let progB = this.progetti.find(p => p.id === notB.idProgetto).codiceVisualizzato;
          return progA.localeCompare(progB);
        });
        break;
      case "data":
      default:
        sortedData = data.sort((notA, notB) => {
          let datanumA = new Date(notA.dtNotifica).getTime();
          let datanumB = new Date(notB.dtNotifica).getTime();
          return datanumA - datanumB;
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: PbandiTNotificaProcessoVO[]) {
    super(data);
  }
}