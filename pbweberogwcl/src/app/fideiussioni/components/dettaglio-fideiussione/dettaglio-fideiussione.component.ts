/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FideiussioniService } from "../../services/fideiussioni.service";
import { InizializzazioneService } from "../../../shared/services/inizializzazione.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { UserService } from "../../../core/services/user.service";
import { DatePipe } from "@angular/common";
import { DateAdapter } from "@angular/material/core";
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { Constants } from "../../../core/commons/util/constants";
import { FideiussioneDTO } from "../../commons/dto/fideiussione-dto";
import { Fideiussione } from "../../commons/dto/fideiussione";
import { ConfigService } from "../../../core/services/config.service";
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-dettaglio-fideiussione',
  templateUrl: './dettaglio-fideiussione.component.html',
  styleUrls: ['./dettaglio-fideiussione.component.scss']
})
export class DettaglioFideiussioneComponent implements OnInit {
  idProgetto: number;
  idFideiussione: number;
  codiceProgetto: string;
  user: UserInfoSec;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  hasValidationError: boolean;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedFideiussione: boolean;
  loadedTipoTrattamento: boolean;
  //OBJECT SUBSCRIBER
  subscribers: any = {};

  // MODELLI API REQUEST/RESPONSE
  fideiussione: Fideiussione = new class implements Fideiussione {
    codRiferimentoFideiussione: string = "";
    descEnteEmittente: string = "";
    descrizioneTipoTrattamento: string = "";
    dtDecorrenza: string = "";
    dtScadenza: string = "";
    idFideiussione: number;
    idProgetto: number;
    idTipoTrattamento: number;
    importo: number;
    importoFideiussione: number;
    importoTotaleTipoTrattamento: number;
    noteFideiussione: string;
    numero: string;
  }
  descTipoTrattamento: string;

  constructor(private configService: ConfigService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private fideiussioniService: FideiussioniService,
    private inizializzazioneService: InizializzazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private _adapter: DateAdapter<any>) {
    this._adapter.setLocale('it');
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {

          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.idFideiussione = +params['id1'];
            this.loadData();
          });
        }
      }
    });
  }

  loadData() {
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });

    this.loadedFideiussione = false;
    this.fideiussioniService.dettaglioFideiussione(this.idFideiussione).subscribe(res => {
      if (res) {
        this.fideiussione = res;
        //tipo trattamento
        this.loadedTipoTrattamento = false;
        this.fideiussioniService.getTipoDiTrattamento(this.fideiussione.idTipoTrattamento).subscribe(res => {
          if (res) {
            this.descTipoTrattamento = res;
          }
          this.loadedTipoTrattamento = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedTipoTrattamento = true;
        });
      }
      this.loadedFideiussione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });


  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedFideiussione || !this.loadedTipoTrattamento) {
      return true;
    }
    return false;
  }

  goToRicercaFideiussioni() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/fideiussioni", this.idProgetto]);
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

  goToAttivita() {
    window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
  }

}
