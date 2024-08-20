/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild, Inject, LOCALE_ID, DEFAULT_CURRENCY_CODE } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatTableDataSource } from '@angular/material/table';
import { MatDividerModule } from '@angular/material/divider';
import { MatPaginator } from '@angular/material/paginator';
import { SchedaClienteMain } from '../../commons/dto/scheda-cliente-main';
import { ModBenResponseService } from '../../services/modben-response-service.service';
import { Constants } from '../../../core/commons/util/constants';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Observable } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { startWith, debounceTime, distinctUntilChanged, switchMap, map } from 'rxjs/operators';
import { MatIconModule } from '@angular/material/icon';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatListModule } from '@angular/material/list';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EditDialogComponent } from '../dialog-edit-modben/dialog-edit.component';
import { SchedaClienteDettaglioErogato } from '../../commons/dto/scheda-cliente-dettaglio-erogato';
import { HistoryDialogComponent } from '../dialog-history-modben/dialog-history.component';
import { SchedaClienteHistory } from '../../commons/dto/scheda-cliente-history';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import localeIt from '@angular/common/locales/it';
import { registerLocaleData } from '@angular/common';
import { AttivitàIstruttoreAreaCreditiService } from '../../services/attivita-istruttore-area-crediti.service';
import { SuggestRatingVO } from '../../commons/dto/suggest-rating-VO';
import { RicBenResponseService } from '../../services/ricben-response-service.service';
import { DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { LavorazioneBoxListVO } from '../../commons/dto/lavorazione-box-list-VO';


// Register the localization
registerLocaleData(localeIt, 'it');

@Component({
  selector: 'app-modifica-beneficiari',
  templateUrl: './modifica-beneficiari.component.html',
  styleUrls: ['./modifica-beneficiari.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ModificaBeneficiariComponent implements OnInit {

  spinner: boolean;

  schedaClienteMain: SchedaClienteMain = new SchedaClienteMain();
  //listDettaglioErogato: Array<SchedaClienteDettaglioErogato> = [];

  listaBoxLavorati: Array<LavorazioneBoxListVO> = new Array<LavorazioneBoxListVO>();

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  displayedColumns: string[] = ['tipoAgevolazione', 'causale', 'dataErogazione', 'importoErogato'];
  @ViewChild(MatPaginator) paginator: MatPaginator;

  //USED BY STATO AZIENDA DIALOG
  statoAzienda: string;
  stAz_dataInizio: Date;
  stAz_dataFine: Date;
  stAz_stati: Array<string>;

  staCre_statiCredito: Array<string>;

  rat_statiRating: Array<SuggestRatingVO>;
  //rat_statiProvider: Array<string>;

  baBen_banche: Array<string>;
  //baBen_motivazioni: Array<string>;

  idUtente: string;
  idSoggetto: string;

  bando: string;
  idProgetto: string; // USED FOR PAGE POPULATION

  beneficiario = "";

  dataCancellazione: Date;
  dataCessazione: Date;
  dataPignoramento: Date;
  dataConcordato: Date;
  dataFallimento: Date;
  dataChiusuraFallimento: Date;
  dataLiquidazione: Date;
  dataProceduraConsersuale: Date;

  bancaBeneficiario: string;

  storicoStatoAzienda: Array<SchedaClienteHistory>;
  storicoStatoCreditoFin: Array<SchedaClienteHistory>;
  storicoRating: Array<SchedaClienteHistory>;
  storicoBancaBeneficiario: Array<SchedaClienteHistory>;

  showSucc: boolean = false;
  succMsg: string = "Salvataggio avvenuto con successo."
  
  showError: boolean = false;
  errorMsg: string = "Si è verificato un problema nel salvataggio dei dati."
  idModalitaAgevolazione: string;

  constructor(
    private resService: ModBenResponseService,
    //private agevService: RicBenResponseService,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router,
    private handleExceptionService: HandleExceptionService,
    private attivitaIstruttoreAreaCreditiService: AttivitàIstruttoreAreaCreditiService,
    private configService: ConfigService,
  ) { }

  ngOnInit(): void {

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.idUtente = String(data.idUtente);
        this.spinner = true;
        this.idProgetto = this.route.snapshot.queryParamMap.get('idProgetto');
        this.idModalitaAgevolazione = this.route.snapshot.queryParamMap.get('idModalitaAgevolazione');
        this.resService.getSchedaCliente(this.idProgetto,this.idModalitaAgevolazione).subscribe( data => {
            if (data) {
              this.schedaClienteMain = data;
              if (this.schedaClienteMain[0].denominazEnteGiu == "" || this.schedaClienteMain[0].denominazEnteGiu == null) {
                this.beneficiario = this.schedaClienteMain[0].denominazPerFis;
              } else {
                this.beneficiario = this.schedaClienteMain[0].denominazEnteGiu;
              }
              this.initDateProcedure();
              this.spinner = false;
            } else {
              this.spinner = false;
            }
          }, err => {
            this.spinner = false;
            this.handleExceptionService.handleBlockingError(err);
          });
          
          this.getBoxInLavorazione(); // Dettaglio lavorato

      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  // Dettaglio lavorato
  getBoxInLavorazione() {
    this.attivitaIstruttoreAreaCreditiService.getLavorazioneBox(this.idModalitaAgevolazione, this.idProgetto).subscribe(data => {
      if (data) {
        //console.log("Lavorati: ", data);
        this.listaBoxLavorati = data;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }


  openDialogDatiProgettoAttivitaPregresse() {
    //console.log("idProgetto: ", this.idProgetto);
    //console.log("apiURL: ", this.configService.getApiURL());

    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL(),
      },
    });
  }

  openDialogContoEconomico() {
		this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
			width: '90%',
			maxHeight: '90%',
			data: {
				idBandoLinea: this.userService.getProgrBandoLineaByIdProgetto(+this.idProgetto),
				idProgetto: this.idProgetto,
				apiURL: this.configService.getApiURL(),
			},
		});
	}

  openEditDialog(id: number): void {
    this.showSucc = false;
    this.showError = false;
    let dialogRef = this.dialog.open(EditDialogComponent, {
      width: '480px',
      data: {
        id,
        idUtente: this.idUtente,
        schedaCliente: this.schedaClienteMain[0],
        idModalitaAgevolazione: this.idModalitaAgevolazione
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != undefined && result != "" && result.save == "save") {
        this.showError = false;
        this.showSucc = false;
        this.spinner = true;

        this.resService.setSchedaCliente(result.newSchedaCliente, this.idModalitaAgevolazione, this.idProgetto, result.flagStatoAzienda, ).subscribe(data => {
          if (data) {
            this.showSucc = true;
            this.resService.getSchedaCliente(this.idProgetto,this.idModalitaAgevolazione).subscribe(data => {
              if (data) {
                this.schedaClienteMain = data;
                this.spinner = false;
                setTimeout(() => {
                  this.showSucc = null;
                  this.initDateProcedure();
                  }, 4000);
                
              } else {
                this.spinner = false;
              }
            }, err => {
              this.spinner = false;
              this.handleExceptionService.handleBlockingError(err);
            });
          }
        }, err => {
          this.spinner = false

          this.showError = true;
        });

      }
    });
  }



  openHistoryDialog(id: number): void {

    this.showError = false;
    this.showSucc = false;

    this.dialog.open(HistoryDialogComponent, {
      width: '700px',
      data: {
        id,
        schedaClienteMain: this.schedaClienteMain[0],

        storicoStatoAzienda: this.storicoStatoAzienda,
        storicoStatoCreditoFin: this.storicoStatoCreditoFin,
        storicoRating: this.storicoRating,
        storicoBancaBeneficiario: this.storicoBancaBeneficiario
      }
    });

  }


  initDateProcedure(): void {

    if(this.schedaClienteMain[0].statoAzienda!="Attiva" && this.schedaClienteMain[0].statoAzienda!="Inattiva"
    && this.schedaClienteMain[0].statoAzienda!="ND"){
      this.dataProceduraConsersuale = this.schedaClienteMain[0].staAz_dtInizioValidita
    } else {
      this.dataProceduraConsersuale = null; 
    }
    // let canc = this.schedaClienteMain[0].storicoStatoAzienda.find((obj) => {
    //   return obj.staAz_statoAzienda == "Cancellata";
    // });
    // if (canc) { this.dataProceduraConsersuale = canc.staAz_dataInizio }

    // let cess = this.schedaClienteMain[0].storicoStatoAzienda.find((obj) => {
    //   return obj.staAz_statoAzienda == "Cessata";
    // });
    // if (cess) { this.dataProceduraConsersuale = cess.staAz_dataInizio }


    // let con = this.schedaClienteMain[0].storicoStatoAzienda.find((obj) => {
    //   return obj.staAz_statoAzienda == "Concordato preventivo";
    // });
    // if (con) { this.dataProceduraConsersuale = con.staAz_dataInizio }


    // let fall = this.schedaClienteMain[0].storicoStatoAzienda.find((obj) => {
    //   return obj.staAz_statoAzienda == "Fallimento";
    // });
    // if (fall) {
    //   this.dataFallimento = fall.staAz_dataInizio;
    //   if (fall.staAz_dataFine != null) { this.dataProceduraConsersuale = fall.staAz_dataInizio }
    // }


    // let liq = this.schedaClienteMain[0].storicoStatoAzienda.find((obj) => {
    //   return obj.staAz_statoAzienda == "Scioglimento e Liquidazione";
    // });
    // if (liq) { this.dataProceduraConsersuale = liq.staAz_dataInizio }

    //console.log("obb: ", liq);
  }


  goBack() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_MONITORAGGIO_CREDITI + "/monitoraggioCrediti"]);
  }
}
