/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { AppaltiService } from "../../services/appalti.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { UserService } from "../../../core/services/user.service";
import { MatDialog } from "@angular/material/dialog";
import { DateAdapter } from "@angular/material/core";
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { Constants } from "../../../core/commons/util/constants";
import { CodiceDescrizione } from "../../commons/dto/codice-descrizione";
import { NgForm } from "@angular/forms";
import { MatCheckboxChange } from "@angular/material/checkbox";
import { StepAggiudicazione } from "../../commons/dto/step-aggiudicazione";
import { MatTableDataSource } from "@angular/material/table";
import { ProceduraAggiudicazione } from "../../commons/dto/procedura-aggiudicazione";
import { CreaProcAggRequest } from "../../commons/dto/crea-proc-agg-request";
import { DatePipe } from "@angular/common";
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { CronoprogrammaService } from 'src/app/cronoprogramma/services/cronoprogramma.service';
import { MotivoScostamento } from 'src/app/cronoprogramma/commons/dto/motivo-scostamento';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-nuova-procedura-aggiudicazione',
  templateUrl: './nuova-procedura-aggiudicazione.component.html',
  styleUrls: ['./nuova-procedura-aggiudicazione.component.scss']
})
export class NuovaProceduraAggiudicazioneComponent implements OnInit {

  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;
  from: string;
  idAppalto: number;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedTipologieAggiudicaz: boolean;
  loadedStepAgg: boolean;
  loadedSave: boolean;
  loadedMotivoScostamento: boolean;

  tipologieAggiudicaz: Array<CodiceDescrizione>;
  tipologieAggiudicazSelected: CodiceDescrizione;
  motiviScostamento: Array<MotivoScostamento>;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  isIterAggVisible: boolean = true;
  hasValidationError: boolean;

  @ViewChild("nuovaProcForm", { static: true }) nuovaProcForm: NgForm;
  @ViewChild("stepForm", { static: true }) stepForm: NgForm;

  elencoIter: Array<StepAggiudicazione> = new Array<StepAggiudicazione>();
  displayedColumns: string[] = ['descStepAggiudicazione', 'dtPrevista', 'dtEffettiva', 'importo', 'motivo'];
  dataSource: MatTableDataSource<StepAggiudicazione> = new MatTableDataSource<StepAggiudicazione>();

  noElementsMessage: string = "Non ci sono elementi da visualizzare.";
  noElementsMessageVisible: boolean = true;

  cpa: any;
  cig: any;
  importo: number;
  iva: number;
  importoFormatted: string;
  ivaFormatted: string;
  descProcAgg: string;


  procAgg: ProceduraAggiudicazione;
  creaProcAggRequest: CreaProcAggRequest;


  constructor(private activatedRoute: ActivatedRoute,
    private router: Router,
    private appaltiService: AppaltiService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private cronoprogrammaService: CronoprogrammaService,
    private sharedService: SharedService,
    private dialog: MatDialog,
    private configService: ConfigService) {
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {

          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.loadData();
          });
        }
        if (this.activatedRoute.snapshot.paramMap.get('from')) {
          this.from = this.activatedRoute.snapshot.paramMap.get('from');
        }
        if (this.activatedRoute.snapshot.paramMap.get('idAppalto')) {
          this.idAppalto = +this.activatedRoute.snapshot.paramMap.get('idAppalto');
        }
      }
    });
  }

  loadData() {
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.appaltiService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });

    //tipologie aggiudicazione
    this.loadedTipologieAggiudicaz = false;
    this.subscribers.codice = this.appaltiService.getTipologieProcedureAggiudicazione(this.idProgetto).subscribe(res => {
      if (res) {
        this.tipologieAggiudicaz = res;
      }
      this.loadedTipologieAggiudicaz = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });

    this.loadedMotivoScostamento = false;
    this.subscribers.codice = this.cronoprogrammaService.getMotiviScostamento().subscribe(res => {
      if (res) {
        console.log(res);
        this.motiviScostamento = res;
      }
      this.loadedMotivoScostamento = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });

  }

  selectCaricaIter($event: MatCheckboxChange) {
    if ($event.checked)
      this.isIterAggVisible = true;
    else
      this.isIterAggVisible = false;
  }

  onSelectTipologiaAgg() {
    this.resetMessageError();
    this.resetMessageSuccess();

    this.loadedStepAgg = true;
    this.subscribers.codice = this.appaltiService.getStepsAggiudicazione(+this.tipologieAggiudicazSelected.codice)
      .subscribe(res => {
        this.dataSource = new MatTableDataSource<StepAggiudicazione>();
        this.dataSource.data = res;
        if (res.length === 0) {
          this.noElementsMessageVisible = true;
        } else this.noElementsMessageVisible = false;
        this.loadedStepAgg = false;
      }, err => {
        this.loadedStepAgg = false;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore durante il recupero delle tipologie procedure aggiudicazione");
      });

  }

  setImporto() {
    this.importo = this.sharedService.getNumberFromFormattedString(this.importoFormatted);
    if (this.importo !== null) {
      this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
    }
  }

  setIva() {
    this.iva = this.sharedService.getNumberFromFormattedString(this.ivaFormatted);
    if (this.iva !== null) {
      this.ivaFormatted = this.sharedService.formatValue(this.iva.toString());
    }
  }

  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();

    this.validate();
    if (!this.hasValidationError) {
      this.loadedSave = true;

      if (this.isIterAggVisible) {
        this.elencoIter = this.dataSource.data;
      } else this.elencoIter = null;

      this.procAgg = {
        idTipologiaAggiudicaz: parseInt(this.tipologieAggiudicazSelected.codice), descTipologiaAggiudicazione: this.tipologieAggiudicazSelected.descrizione,
        descProcAgg: this.descProcAgg, iva: this.iva, importo: this.importo, codProcAgg: this.cpa, cigProcAgg: this.cig, iter: this.elencoIter
      }
      this.creaProcAggRequest = { idProgetto: this.idProgetto, proceduraAggiudicazione: this.procAgg }
      this.subscribers.codice = this.appaltiService.creaProceduraAggiudicazione(this.creaProcAggRequest).subscribe(res => {
        if (res) {
          if (res.esito) {
            this.goToAppalto();
          } else {
            this.showMessageError(res.msgs[0].msgKey);
          }
          this.loadedSave = false;
        }
      }, err => {
        this.loadedSave = false;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore durante il salvataggio della procedura aggiudicazione");
      });
    }

  }

  validate() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.hasValidationError = false;
    this.checkRequiredForm(this.nuovaProcForm, "tipologiaAgg");

    //controlla cpa e cig
    this.nuovaProcForm.form.get("cpa").setErrors(null);
    this.nuovaProcForm.form.get("cig").setErrors(null);
    if ((!this.cig || this.cig.length === 0) && (!this.cpa || this.cpa.length === 0)) {
      this.nuovaProcForm.form.get("cpa").setErrors({ error: 'required' });
      this.nuovaProcForm.form.get("cig").setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
    if (this.cig && this.cig.length !== 10) {
      this.nuovaProcForm.form.get("cig").setErrors({ error: 'numChar' });
      this.hasValidationError = true;
    }
    this.checkRequiredForm(this.nuovaProcForm, "importo");
    if (this.importo && this.importo < 0) {
      this.nuovaProcForm.form.get("importo").setErrors({ error: 'minore' });
      this.hasValidationError = true;
    }
    if (this.importo && this.importo > 999999999999999.99) {
      this.nuovaProcForm.form.get("importo").setErrors({ error: 'maggiore' });
      this.hasValidationError = true;
    }
    this.checkRequiredForm(this.nuovaProcForm, "descrizione");

    if (this.isIterAggVisible && this.dataSource && this.dataSource.data && this.dataSource.data.length > 0) {
      for (let step of this.dataSource.data) {
        if (!step.dtPrevista) {
          this.stepForm.form.get("dtPrevista" + step.id).setErrors({ error: 'required' });
          this.hasValidationError = true;
        }
        if (!step.dtEffettiva) {
          this.stepForm.form.get("dtEffettiva" + step.id).setErrors({ error: 'required' });
          this.hasValidationError = true;
        }
      }
    }

    this.setAllMarkAsTouched(this.nuovaProcForm);
    this.setAllMarkAsTouched(this.stepForm);
    if (this.hasValidationError) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    }
  }

  checkRequiredForm(form: NgForm, name: string) {
    if (!form.form.get(name) || !form.form.get(name).value) {
      form.form.get(name).setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
  }

  setAllMarkAsTouched(form: NgForm) {
    Object.keys(form.form.controls).forEach(key => {
      if (form.form.controls[key]) {
        form.form.controls[key].markAsTouched();
      }
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

  goToAppalto() {
    if (this.from === "nuovo") {
      this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/nuovoAppalto", this.idProgetto, { isProcVisible: "true" }]);
    } else if (this.from === "modifica") {
      this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/modificaAppalto", this.idProgetto, this.idAppalto, { isProcVisible: "true" }]);
    }
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
    if (!this.loadedCodiceProgetto || !this.loadedTipologieAggiudicaz || this.loadedStepAgg || this.loadedSave || !this.loadedMotivoScostamento) {
      return true;
    }
    return false;
  }

}
