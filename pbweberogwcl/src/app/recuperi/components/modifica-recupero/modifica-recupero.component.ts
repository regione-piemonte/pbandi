/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { EsitoOperazioni } from "../../../shared/api/models/esito-operazioni";
import { CodiceDescrizione } from "../../../shared/commons/models/codice-descrizione";
import { RevocheService } from "../../../revoche/services/revoche.service";
import { DettaglioRevoca } from "../../../revoche/commons/models/dettaglio-revoca";
import { NgForm } from "@angular/forms";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { InizializzazioneService } from "../../../shared/services/inizializzazione.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { SharedService } from "../../../shared/services/shared.service";
import { DateAdapter } from "@angular/material/core";
import { DatePipe } from "@angular/common";
import { RecuperiService } from "../../services/recuperi.service";
import { DettaglioRecupero } from "../../commons/models/dettaglio-recupero";
import { MatSelectChange } from "@angular/material/select";
import { Constants, DatiProgettoAttivitaPregresseDialogComponent } from "@pbandi/common-lib";
import { EsitoSalvaRevocaDTO } from "../../../revoche/commons/dto/esito-salva-revoca-dto";
import { EsitoSalvaRecuperoDTO } from "../../commons/dto/esito-salva-recupero-dto";
import { RequestModificaRecupero } from "../../commons/requests/request-modifica-recupero";
import { MatDialog } from '@angular/material/dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

@Component({
  selector: 'app-modifica-recupero',
  templateUrl: './modifica-recupero.component.html',
  styleUrls: ['./modifica-recupero.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ModificaRecuperoComponent implements OnInit {
  idProgetto: number;
  idRecupero: number;
  codiceProgetto: string;
  user: UserInfoSec;

  isMessageSuccessVisible: any;
  isMessageWarningVisible: any;
  isMessageErrorVisible: any;

  messageSuccess: any;
  messageWarning: any;
  messageError: any;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedTipologie: boolean;
  loadedDetaggioRecupero: boolean;
  loadedModifica: boolean;

  // REQUEST
  dettaglioRecuperoRequest: RecuperiService.GetDettaglioRecuperoParams;
  requestModificaRecupero: RequestModificaRecupero;

  tipologie: Array<CodiceDescrizione>;
  esitoSalvaRecupero: EsitoSalvaRecuperoDTO;

  // FORM INPUT
  tipologiaSelected: CodiceDescrizione;
  dataRecupero: Date;
  importoFormatted: string;
  dettaglioRecupero: DettaglioRecupero = { modalitaAgevolazione: "" };

  @ViewChild('recuperoForm', { static: true }) recuperoForm: NgForm;

  constructor(
    private configService: ConfigService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private recuperiService: RecuperiService,
    private sharedService: SharedService,
    private inizializzazioneService: InizializzazioneService,
    private router: Router,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.idRecupero = +params['id1']
            this.loadedDetaggioRecupero = false;
            this.dettaglioRecuperoRequest = { idRecupero: this.idRecupero, idProgetto: this.idProgetto }
            this.subscribers.codice = this.recuperiService.getDettaglioRecupero(this.dettaglioRecuperoRequest).subscribe(res => {
              if (res) {
                this.dettaglioRecupero = res;
                if (this.dettaglioRecupero.dataRecupero) {
                  this.dataRecupero = this.sharedService.parseDate(this.dettaglioRecupero.dataRecupero);
                }
                if (this.dettaglioRecupero.importoRecupero !== null) {
                  this.importoFormatted = this.sharedService.formatValue(this.dettaglioRecupero.importoRecupero.toString());
                }
                this.loadData();
              }
              this.loadedDetaggioRecupero = true;
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.showMessageError("Errore in fase di recupero del dettaglio.");
              this.loadedDetaggioRecupero = true;
            });
          });
        }
      }
    });
  }

  loadData() {
    // Codice progetto
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero del codice del progetto");
      this.loadedCodiceProgetto = true;
    });

    this.loadedTipologie = false;
    this.subscribers.tipoRecuperi = this.recuperiService.getTipologieRecuperi().subscribe(res => {
      if (res) {
        this.tipologie = res;
        if (this.dettaglioRecupero.idTipoRecupero) {
          this.tipologiaSelected = this.tipologie.find(m => m.codice === this.dettaglioRecupero.idTipoRecupero.toString());
        }
      }
      this.loadedTipologie = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero delle tipologie.");
      this.loadedTipologie = true;
    });
  }

  setImporto() {
    this.dettaglioRecupero.importoRecupero = this.sharedService.getNumberFromFormattedString(this.importoFormatted);
    if (this.dettaglioRecupero.importoRecupero != null) {
      this.importoFormatted = this.sharedService.formatValue(this.dettaglioRecupero.importoRecupero.toString());
    }
  }

  modificaRecupero() {
    this.resetMessageError();
    this.resetMessageSuccess()
    let hasError: boolean;
    if (this.tipologiaSelected == null || this.tipologiaSelected == undefined) {
      this.recuperoForm.form.get('tipologia').setErrors({ error: 'required' });
      hasError = true;
    }
    if (!this.dataRecupero) {
      this.recuperoForm.form.get('dataRecupero').setErrors({ error: 'required' });
      hasError = true;
    }
    if (this.dettaglioRecupero.importoRecupero === null) {
      this.recuperoForm.form.get('importo').setErrors({ error: 'required' });
      hasError = true;
    }
    this.recuperoForm.form.markAllAsTouched();
    if (hasError) {
      this.showMessageError("Inserire tutti i campi obbligatori.");
      return;
    }
    this.loadedModifica = true;
    this.dettaglioRecupero.idTipoRecupero = this.tipologiaSelected ? +this.tipologiaSelected.codice : null;
    this.dettaglioRecupero.dataRecupero = this.dataRecupero ? this.sharedService.formatDateToString(this.dataRecupero) : null;
    this.requestModificaRecupero = { recupero: this.dettaglioRecupero, idProgetto: this.idProgetto }
    this.subscribers.codice = this.recuperiService.modificaRecupero(this.requestModificaRecupero).subscribe(res => {
      if (res.esito) {
        this.esitoSalvaRecupero = res;
        this.showMessageSuccess(res.msgs[0].msgKey);
      } else {
        this.showMessageError(res.msgs[0].msgKey);
      }
      this.loadedModifica = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedModifica = false;
    });
  }

  goToGestioneRecuperi() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_REVOCHE + "/recuperi", this.idProgetto]);
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
  }
  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
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
  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedTipologie || !this.loadedCodiceProgetto ||
      !this.loadedDetaggioRecupero || this.loadedModifica) {
      return true;
    }
    return false;
  }
}
