/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { DateAdapter } from "@angular/material/core";
import { Constants } from "../../../core/commons/util/constants";
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { DatePipe } from "@angular/common";
import { FideiussioniService } from "../../services/fideiussioni.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { ActivatedRoute, Router } from "@angular/router";
import { InizializzazioneService } from "../../../shared/services/inizializzazione.service";
import { UserService } from "../../../core/services/user.service";
import { NgForm } from "@angular/forms";
import { CodiceDescrizione } from "../../commons/dto/codice-descrizione";
import { CreaAggiornaFideiussioneRequest } from "../../commons/dto/crea-aggiorna-fideiussione-request";
import { Fideiussione } from "../../commons/dto/fideiussione";
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-nuova-fideiussione',
  templateUrl: './nuova-fideiussione.component.html',
  styleUrls: ['./nuova-fideiussione.component.scss']
})
export class NuovaFideiussioneComponent implements OnInit {
  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;

  noElementsMessage: string = "Non ci sono elementi da visualizzare.";
  noElementsMessageVisible: boolean;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  hasValidationError: boolean;

  //LOADED
  loadedTipoTrattamento: boolean;
  loadedSave: boolean;

  @ViewChild("nuovaFideiussioneForm", { static: true }) nuovaFideiussioneForm: NgForm;
  //OBJECT SUBSCRIBER
  subscribers: any = {};

  // MODELLI API REQUEST/RESPONSE
  tipoTrattamentoSelected: CodiceDescrizione;
  tipiTrattamento: Array<CodiceDescrizione>;
  codiceRiferimento: any;
  importo: number;
  descrizione: any;
  enteEmittente: any;
  dataDecorrenza: string;
  dataScadenza: string;
  fideiussione: Fideiussione;
  creaFideiussioneRequest: CreaAggiornaFideiussioneRequest;

  constructor(private activatedRoute: ActivatedRoute,
    private router: Router,
    private fideiussioniService: FideiussioniService,
    private inizializzazioneService: InizializzazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private configService: ConfigService,
    private dialog: MatDialog,
    private datePipe: DatePipe,
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
            this.loadCombo();

            this.subscribers.codice = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
              if (res) {
                this.codiceProgetto = res;
              }
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
            });
          });

        }
      }
    });
  }


  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_FIDEIUSSIONE;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  loadCombo() {
    this.loadedTipoTrattamento = false;
    this.fideiussioniService.getTipiDiTrattamento(this.idProgetto).subscribe(data => {
      if (data) {
        this.tipiTrattamento = data;
      }
      this.loadedTipoTrattamento = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedTipoTrattamento = true;
    })
  }

  validate() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.hasValidationError = false;
    this.checkRequiredForm(this.nuovaFideiussioneForm, "importo");
    this.checkRequiredForm(this.nuovaFideiussioneForm, "enteEmittente");
    this.checkRequiredForm(this.nuovaFideiussioneForm, "dtDecorrenza");
    this.checkRequiredForm(this.nuovaFideiussioneForm, "tipoTrattamento");

    if (this.dataScadenza < this.dataDecorrenza) {
      this.showMessageError("La Data scadenza non può essere anteriore alla Data decorrenza.");
      this.hasValidationError = true;
      return;
    }

    this.setAllMarkAsTouched();
    if (this.hasValidationError) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    }
  }


  salva() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.validate();
    if (!this.hasValidationError) {
      this.loadedSave = true;
      this.fideiussione = {
        codRiferimentoFideiussione: this.codiceRiferimento ? this.codiceRiferimento : null,
        descEnteEmittente: this.enteEmittente,
        dtDecorrenza: this.datePipe.transform(this.dataDecorrenza, 'yyyy-MM-dd'),
        dtScadenza: this.datePipe.transform(this.dataScadenza, 'yyyy-MM-dd'),
        importoFideiussione: this.importo,
        idTipoTrattamento: parseInt(this.tipoTrattamentoSelected.codice),
        descrizioneTipoTrattamento: this.tipoTrattamentoSelected.descrizione,
        noteFideiussione: this.descrizione ? this.descrizione : null
      };
      this.creaFideiussioneRequest = { idProgetto: this.idProgetto, fideiussione: this.fideiussione };
      this.subscribers.save = this.fideiussioniService.creaAggiornaFideiussione(this.creaFideiussioneRequest).subscribe(res => {
        if (res.esito) {
          this.showMessageSuccess(res.message);
          this.goToDettaglioFideiussione(parseInt(res.params[0]));
        } else {
          this.showMessageError(res.message + " " + res.params[0]);
        }
        this.loadedSave = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nel salvataggio della fideiussione.")
        this.loadedSave = false;
      })
    }
  }

  checkRequiredForm(form: NgForm, name: string) {
    if (!form.form.get(name) || !form.form.get(name).value) {
      form.form.get(name).setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
  }

  setAllMarkAsTouched() {
    Object.keys(this.nuovaFideiussioneForm.form.controls).forEach(key => {
      if (this.nuovaFideiussioneForm.form.controls[key]) {
        this.nuovaFideiussioneForm.form.controls[key].markAsTouched();
      }
    });
  }


  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }
  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
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
    if (!this.loadedTipoTrattamento || this.loadedSave) {
      return true;
    }
    return false;
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

  goToRicercaFideiussioni() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/fideiussioni", this.idProgetto]);
  }

  goToDettaglioFideiussione(idFideiussione: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/dettaglioFideiussione", this.idProgetto, idFideiussione]);
  }

}
