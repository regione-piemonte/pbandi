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
import { Constants, DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { RecuperiService } from 'src/app/recuperi/services/recuperi.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { InizializzazioneService } from 'src/app/shared/services/inizializzazione.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Soppressione } from '../../commons/requests/soppressione';
import { SoppressioneService } from '../../services/soppressione.service';
import { NuovoModificaSoppressioneDialogComponent } from '../nuovo-modifica-soppressione-dialog/nuovo-modifica-soppressione-dialog.component';

@Component({
  selector: 'app-soppressioni',
  templateUrl: './soppressioni.component.html',
  styleUrls: ['./soppressioni.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class SoppressioniComponent implements OnInit {

  idProgetto: number;
  idBando: number;
  codiceProgetto: string;
  user: UserInfoSec;

  soppressioni: Array<Soppressione>;
  importoCertificabileLordo: number;
  importoTotaleDisimpegni: number;
  totaleSoppressioni: number = 0;
  insModDelAbilitati: boolean;

  displayedColumns: string[] = ['importo', 'data', 'estremi', 'note', 'flagMonitoraggio', 'azioni'];
  dataSource: SoppressioniDatasource = new SoppressioniDatasource([]);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  isMessageSuccessVisible: boolean;
  isMessageErrorVisible: boolean;
  messageSuccess: string;
  messageError: string;

  //LOADED
  loadedChiudiAttivita: boolean = true;
  loadedElimina: boolean = true;
  loadedCodiceProgetto: boolean;
  loadedInizializza: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private configService: ConfigService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private soppressioneService: SoppressioneService,
    private inizializzazioneService: InizializzazioneService,
    private router: Router,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private recuperiService: RecuperiService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['idProgetto'];
            this.idBando = +params['idBando'];
            this.loadData();
            this.loadCodiceProgetto();
          });
        }
      }
    });
  }

  loadCodiceProgetto() {
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del codice del progetto.");
      this.loadedCodiceProgetto = true;
    });
  }

  loadData() {
    this.loadedInizializza = false;
    this.subscribers.init = this.soppressioneService.inizializzaSoppressioni(this.idProgetto).subscribe(data => {
      if (data) {
        this.soppressioni = data.soppressioni;
        this.importoCertificabileLordo = data.importoCertificabileLordo;
        this.importoTotaleDisimpegni = data.importoTotaleDisimpegni;
        this.insModDelAbilitati = data.insModDelAbilitati;
        this.dataSource = new SoppressioniDatasource(this.soppressioni);
        this.paginator.length = this.soppressioni.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.totaleSoppressioni = 0;
        if (this.soppressioni.length) {
          this.soppressioni.forEach(s => this.totaleSoppressioni += s.importoSoppressione);
        }
      }
      this.loadedInizializza = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di inizializzazione.");
      this.loadedInizializza = true;
    });
  }

  openNuovaSoppressione() {
    let dialogRef = this.dialog.open(NuovoModificaSoppressioneDialogComponent, {
      width: '50%',
      data: {
        isNuovo: true,
        idProgetto: this.idProgetto,
        importoCertificabileLordo: this.importoCertificabileLordo,
        importoTotaleDisimpegni: this.importoTotaleDisimpegni
      }
    });
    dialogRef.afterClosed().subscribe((res: string) => {
      if (res) {
        this.loadData();
        this.showMessageSuccess(res);
      }
    });
  }

  openModificaSoppressione(soppressione: Soppressione) {
    let dialogRef = this.dialog.open(NuovoModificaSoppressioneDialogComponent, {
      width: '50%',
      data: {
        isNuovo: false,
        idProgetto: this.idProgetto,
        importoCertificabileLordo: this.importoCertificabileLordo,
        importoTotaleDisimpegni: this.importoTotaleDisimpegni,
        soppressione: soppressione
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.loadData();
        this.showMessageSuccess(res);
      }
    });
  }

  elimina(soppressione: Soppressione) {
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione della soppressione?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedElimina = false;
        this.subscribers.erogazione = this.recuperiService.cancellaRecupero(soppressione.idSoppressione).subscribe(res => {
          if (res.esito) {
            this.showMessageSuccess(res.msgs[0].msgKey);
            this.loadData();
          } else {
            this.showMessageError(res.msgs[0].msgKey);
          }
          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di eliminazione");
          this.loadedElimina = true;
        });
      }
    });
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_SOPPRESSIONE).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  goToDatiProgettoEAttivitaPregresse() {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    let top = document.getElementById('scrollId');
    top.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    let top = document.getElementById('scrollId');
    top.scrollIntoView();
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
    if (!this.loadedChiudiAttivita || !this.loadedCodiceProgetto || !this.loadedInizializza
      || !this.loadedElimina) {
      return true;
    }
    return false;
  }

}


export class SoppressioniDatasource extends MatTableDataSource<Soppressione> {

  _orderData(data: Soppressione[]): Soppressione[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "idSoppressione";
    }
    let direction = this.sort.direction || this.sort.start;
    let sortedData = null;

    // viene riordinato in ordine ascendente
    switch (this.sort.active) {
      case "importo":
        sortedData = data.sort((sopA, sopB) => {
          return sopA.importoSoppressione - sopB.importoSoppressione;
        });
        break;
      case "data":
        sortedData = data.sort((sopA, sopB) => {
          let dataA = new Date(sopA.dataSoppressione).toLocaleDateString();
          let dataB = new Date(sopB.dataSoppressione).toLocaleDateString();
          return dataA.localeCompare(dataB);
        });
        break;
      case "estremi":
        sortedData = data.sort((sopA, sopB) => {
          return sopA.estremiDetermina.localeCompare(sopB.estremiDetermina);
        });
        break;
      case "note":
        sortedData = data.sort((sopA, sopB) => {
          return sopA.note.localeCompare(sopB.note);
        });
        break;

      default:
        sortedData = data.sort((sopA, sopB) => {
          return sopA.idSoppressione - sopB.idSoppressione;
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: Soppressione[]) {
    super(data);
  }
}
