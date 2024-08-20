/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants, UserInfoSec } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { RicercaAttiService } from 'src/app/ricerca-atti-liquidazione/services/ricerca-atti.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { UserService } from 'src/app/core/services/user.service';
import { LiquidazioneService } from 'src/app/liquidazione/services/liquidazione.service';
import { MatTableDataSource } from '@angular/material/table';
import { RiepilogoAttoDiLiquidazionePerProgetto } from 'src/app/gestione-disimpegni/commons/riepilogo-atto-di-liquidazione-per-progetto-vo';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

@Component({
  selector: 'app-consultazione-atti',
  templateUrl: './consultazione-atti.component.html',
  styleUrls: ['./consultazione-atti.component.scss']
})
export class ConsultazioneAttiComponent implements OnInit {

  beneficiario: string;
  codiceProgetto: string;

  displayedColumns: string[] = ['causaleErog', 'numAtto', 'impLiquid', 'dettaglio'];
  dataSource: MatTableDataSource<RiepilogoAttoDiLiquidazionePerProgetto>;

  idProgetto: number;
  idBando: number;

  user: UserInfoSec;

  modalitaAgevolazione: string;
  ultimoImportoAgevolato: string;
  importoLiquidato: string;

  messageError: string;
  isMessageErrorVisible: boolean;

  // loaaded
  loadedCercaRiepilogoAtti: boolean;
  loadedCaricaDatiGenerali: boolean;
  loadedChiudiAttivita: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private sharedService: SharedService,
    private activatedRoute: ActivatedRoute,
    private configService: ConfigService,
    private userService: UserService,
    private ricercaAttiService: RicercaAttiService,
    private _snackBar: MatSnackBar,
    private liquidazioneService: LiquidazioneService,
    private router: Router,
    private handleExceptionService: HandleExceptionService
  ) {
    this.dataSource = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBando = +params['idBando'];
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

        this.loadedCercaRiepilogoAtti = true;
        this.ricercaAttiService.cercaRiepilogoAtti(this.user, this.idProgetto.toString()).subscribe(data => {
          this.loadedCercaRiepilogoAtti = false;
          this.modalitaAgevolazione = data[0].modalitaAgevolazione;
          this.ultimoImportoAgevolato = data[0].ultimoImportoAgevolato.toString();
          this.importoLiquidato = data[0].importoLiquidato.toString();
          var comodo = new Array<RiepilogoAttoDiLiquidazionePerProgetto>();

          for (let i = 0; i < data.length; i++) {
            if (!(i == 0)) {
              if (!(i == (data.length - 1))) {
                comodo.push(data[i]);
              }
            }
          }

          this.dataSource = new MatTableDataSource(comodo);
        }, err => {
          this.loadedCercaRiepilogoAtti = false;
          this.openSnackBar("Errore in fase di ottenimento dei riepiloghi atti");
        });

        this.loadedCaricaDatiGenerali = true;
        this.liquidazioneService.caricaDatiGenerali(this.user, this.user.beneficiarioSelezionato.idBeneficiario.toString(), this.idBando.toString(), this.idProgetto.toString()).subscribe(data => {
          this.codiceProgetto = data.datiGeneraliDTO.codiceProgetto;
          this.loadedCaricaDatiGenerali = false;
        }, err => {
          this.loadedCaricaDatiGenerali = false;
          this.openSnackBar("Errore in fase di ottenimento del codice progetto");
        });
      }
    });
  }

  tornaAlleAttivitaDaSvolg() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_CONSULTAZIONE_ATTI_LIQUIDAZIONE).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  dettaglio(idAttoDiLiquidazione: number) {
    this.router.navigateByUrl("drawer/" + Constants.DESCR_BREVE_TASK_CONSULTAZIONE_ATTI_LIQUIDAZIONE + "/dettaglioAtto?idAttoLiquidazione=" + idAttoDiLiquidazione + "&beneficiario=" + this.user.beneficiarioSelezionato.denominazione + "&progetto=" + this.codiceProgetto + "&consultazioneAtto=true" + "&idProgetto=" + this.idProgetto + "&idBando=" + this.idBando);
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
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

  isLoading() {
    if (this.loadedCercaRiepilogoAtti || this.loadedCaricaDatiGenerali || !this.loadedChiudiAttivita) {
      return true;
    } else {
      return false;
    }
  }
}
