/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { NgForm } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { UserService } from "../../../core/services/user.service";
import { MatDialog } from "@angular/material/dialog";
import { DateAdapter } from "@angular/material/core";
import { AppaltiService } from "../../services/appalti.service";
import { Constants } from "../../../core/commons/util/constants";
import { CodiceDescrizione } from "../../commons/dto/codice-descrizione";
import { ProceduraAggiudicazione } from "../../commons/dto/procedura-aggiudicazione";
import { MatTableDataSource } from "@angular/material/table";
import { StepAggiudicazione } from "../../commons/dto/step-aggiudicazione";
import { MotivoScostamento } from "../../../cronoprogramma/commons/dto/motivo-scostamento";
import { CronoprogrammaService } from "../../../cronoprogramma/services/cronoprogramma.service";
import { MatCheckboxChange } from "@angular/material/checkbox";
import { ModificaProcedureAggiudicazioneRequest } from "../../commons/dto/modifica-procedure-aggiudicazione-request";
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-modifica-procedura-aggiudicazione',
  templateUrl: './modifica-procedura-aggiudicazione.component.html',
  styleUrls: ['./modifica-procedura-aggiudicazione.component.scss']
})
export class ModificaProceduraAggiudicazioneComponent implements OnInit {
  idProcedura: number;
  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;
  from: string;
  idAppalto: number;
  proceduraAgg: ProceduraAggiudicazione = { iter: new Array<StepAggiudicazione>() };

  isIterAggVisible: boolean = true;

  tipologieAggiudicaz: Array<CodiceDescrizione>;
  tipologieAggiudicazSelected: CodiceDescrizione = { codice: "", descrizione: "" };

  importoFormatted: string;
  ivaFormatted: string;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  noElementsMessage: string = "Non ci sono elementi da visualizzare.";
  noElementsMessageVisible: boolean;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedMotivoScostamento: boolean;
  loadedTipologieAggiudicaz: boolean;
  loadedModificaProcAgg: boolean;
  loadedStepAgg: boolean;

  displayedColumns: string[] = ['descStepAggiudicazione', 'dtPrevista', 'dtEffettiva', 'importo', 'motivo'];
  dataSource: MatTableDataSource<StepAggiudicazione> = new MatTableDataSource<StepAggiudicazione>();

  motiviScostamento: Array<MotivoScostamento>;

  @ViewChild("aggiudicazioneForm", { static: true }) aggiudicazioneForm: NgForm;
  @ViewChild("stepForm", { static: true }) stepForm: NgForm;
  hasValidationError: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  requestUpdateProgAgg: ModificaProcedureAggiudicazioneRequest;
  constructor(private activatedRoute: ActivatedRoute,
    private router: Router,
    private appaltiService: AppaltiService,
    private cronoprogrammaService: CronoprogrammaService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
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
            this.idProcedura = +params['id1'];
            this.loadData();
            if (this.activatedRoute.snapshot.paramMap.get('from')) {
              this.from = this.activatedRoute.snapshot.paramMap.get('from');
            }
            if (this.activatedRoute.snapshot.paramMap.get('idAppalto')) {
              this.idAppalto = +this.activatedRoute.snapshot.paramMap.get('idAppalto');
            }
          });
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
    //motivi scostamento
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
    //tipologie aggiudicazione
    this.loadedTipologieAggiudicaz = false;
    this.subscribers.codice = this.appaltiService.getTipologieProcedureAggiudicazione(this.idProgetto).subscribe(res => {
      if (res) {
        this.tipologieAggiudicaz = res;

        this.appaltiService.getDettaglioProceduraAggiudicazione(this.idProcedura).subscribe(res => {
          if (res) {
            this.proceduraAgg = res;
            this.importoFormatted = this.sharedService.formatValue(this.proceduraAgg.importo.toString());
            this.ivaFormatted = this.proceduraAgg.iva ? this.sharedService.formatValue(this.proceduraAgg.iva.toString()) : null;
            this.tipologieAggiudicazSelected = this.tipologieAggiudicaz.find(t => t.codice === this.proceduraAgg.idTipologiaAggiudicaz.toString());
            if (!this.proceduraAgg.iter || this.proceduraAgg.iter.length === 0) {
              this.isIterAggVisible = false;
              this.onSelectTipologiaAgg();
            } else {
              this.dataSource = new MatTableDataSource<StepAggiudicazione>();
              this.dataSource.data = this.proceduraAgg.iter;
            }
          }
        });
      }
      this.loadedTipologieAggiudicaz = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    })
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

  selectCaricaIter($event: MatCheckboxChange) {
    if ($event.checked)
      this.isIterAggVisible = true;
    else
      this.isIterAggVisible = false;
  }

  setImporto() {
    this.proceduraAgg.importo = this.sharedService.getNumberFromFormattedString(this.importoFormatted);
    if (this.proceduraAgg.importo !== null) {
      this.importoFormatted = this.sharedService.formatValue(this.proceduraAgg.importo.toString());
    }
  }

  setIva() {
    this.proceduraAgg.iva = this.sharedService.getNumberFromFormattedString(this.ivaFormatted);
    if (this.proceduraAgg.iva !== null) {
      this.ivaFormatted = this.sharedService.formatValue(this.proceduraAgg.iva.toString());
    }
  }

  modificaProcAgg() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.validate();
    if (!this.hasValidationError) {
      if (this.tipologieAggiudicazSelected) {
        this.proceduraAgg.idTipologiaAggiudicaz = +this.tipologieAggiudicazSelected.codice;
        this.proceduraAgg.descTipologiaAggiudicazione = this.tipologieAggiudicazSelected.descrizione;
      }
      if (this.isIterAggVisible) {
        this.proceduraAgg.iter = this.dataSource.data;
      } else this.proceduraAgg.iter = null;

      this.requestUpdateProgAgg = { idProgetto: this.idProgetto, proceduraAggiudicazione: this.proceduraAgg };
      this.loadedModificaProcAgg = true;
      this.appaltiService.modificaProceduraAggiudicazione(this.requestUpdateProgAgg).subscribe(res => {
        if (res) {
          if (!res.esito) {
            this.showMessageError(res.msgs[0].msgKey);
          } else {
            this.goToAppalto();
          }
        }
        this.loadedModificaProcAgg = false;
      }, err => {
        this.showMessageError("Errore durante la modifica delle procedure aggiudicazione.")
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedModificaProcAgg = false;
      });
    }
  }

  validate() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.hasValidationError = false;
    this.checkRequiredForm(this.aggiudicazioneForm, "tipologiaAgg");

    //controlla cpa e cig
    this.aggiudicazioneForm.form.get("cpa").setErrors(null);
    this.aggiudicazioneForm.form.get("cig").setErrors(null);
    if ((!this.proceduraAgg.cigProcAgg || this.proceduraAgg.cigProcAgg.length === 0) && (!this.proceduraAgg.codProcAgg || this.proceduraAgg.codProcAgg.length === 0)) {
      this.aggiudicazioneForm.form.get("cpa").setErrors({ error: 'required' });
      this.aggiudicazioneForm.form.get("cig").setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
    if (this.proceduraAgg.cigProcAgg && this.proceduraAgg.cigProcAgg.length !== 10) {
      this.aggiudicazioneForm.form.get("cig").setErrors({ error: 'numChar' });
      this.hasValidationError = true;
    }
    this.checkRequiredForm(this.aggiudicazioneForm, "importo");
    if (this.proceduraAgg.importo && this.proceduraAgg.importo < 0) {
      this.aggiudicazioneForm.form.get("importo").setErrors({ error: 'minore' });
      this.hasValidationError = true;
    }
    if (this.proceduraAgg.importo && this.proceduraAgg.importo > 999999999999999.99) {
      this.aggiudicazioneForm.form.get("importo").setErrors({ error: 'maggiore' });
      this.hasValidationError = true;
    }
    this.checkRequiredForm(this.aggiudicazioneForm, "descrizione");

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

    this.setAllMarkAsTouched(this.aggiudicazioneForm);
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

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedTipologieAggiudicaz || !this.loadedMotivoScostamento || this.loadedModificaProcAgg || this.loadedStepAgg) {
      return true;
    }
    return false;
  }
  goToAppalto() {
    if (this.from === "nuovo") {
      this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/nuovoAppalto", this.idProgetto, { isProcVisible: "true" }]);
    } else if (this.from === "modifica") {
      this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/modificaAppalto", this.idProgetto, this.idAppalto, { isProcVisible: "true" }]);
    }
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

}
