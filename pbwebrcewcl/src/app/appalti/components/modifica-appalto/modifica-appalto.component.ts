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
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { MatTableDataSource } from "@angular/material/table";
import { StepAggiudicazione } from "../../commons/dto/step-aggiudicazione";
import { MotivoScostamento } from "../../../cronoprogramma/commons/dto/motivo-scostamento";
import { Constants } from "../../../core/commons/util/constants";
import { ProceduraAggiudicazione } from "../../commons/dto/procedura-aggiudicazione";
import { CodiceDescrizione } from "../../commons/dto/codice-descrizione";
import { NgForm } from "@angular/forms";
import { MatCheckboxChange } from "@angular/material/checkbox";
import { Appalto } from "../../commons/dto/appalto";
import { FiltroProcedureAggiudicazione } from "../../commons/dto/filtro-procedure-aggiudicazione";
import { GetProcedureAggiudicazioneRequest } from "../../commons/dto/get-procedure-aggiudicazione-request";
import { MatPaginator } from "@angular/material/paginator";
import { EsitoGestioneAppalti } from "../../commons/dto/esito-gestione-appalti";
import { CreaAppaltoRequest } from "../../commons/dto/crea-appalto-request";
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { Appalti2Service } from '../../services/appalti-2.service';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-modifica-appalto',
  templateUrl: './modifica-appalto.component.html',
  styleUrls: ['./modifica-appalto.component.scss']
})
export class ModificaAppaltoComponent implements OnInit {
  idAppalto: number;
  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;
  eliminazioneAppaltoAbilitata: boolean;
  proceduraAgg: ProceduraAggiudicazione = { iter: new Array<StepAggiudicazione>() };

  isIterAggVisible: boolean = true;

  appalto: Appalto;
  getAppaltiParams: AppaltiService.GetAppaltiParams;

  importoContrattoFormatted: string;
  importoBaseGaraFormatted: string;
  importoRibAstaFormatted: string;
  precentualeRibAstaFormatted: string;

  tipologieAppalti: Array<CodiceDescrizione>;
  tipologiaAppaltoSelected: CodiceDescrizione = { codice: "", descrizione: "" };

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  noElementsMessage: string = "Non ci sono elementi da visualizzare.";
  noElementsMessageVisible: boolean;

  proceduraAggiudicazioneSelected: ProceduraAggiudicazione = { descProcAgg: "", descTipologiaAggiudicazione: "" };

  criteriRicercaOpened: boolean;
  isProcedureAggiudicazVisible: boolean;

  //FILTRO RICERCA (PROCEDURE DI AGGIUDICAZIONE)
  tipologieAggiudicaz: Array<CodiceDescrizione>;
  tipologieAggiudicazSelected: CodiceDescrizione;
  cpa: string; // codice procedura aggiudicazione
  cig: string; // codice identificativo gara

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedMotivoScostamento: boolean;
  loadedStepAgg: boolean;
  loadedDettagglioAppalto: boolean;
  loadedTipologieAppalti: boolean;
  loadedTipologieAggiudicaz: boolean;
  loadedCerca: boolean;
  loadedModifica: boolean;
  loadedElimina: boolean = true;

  elencoProcAggiudicaz: Array<ProceduraAggiudicazione> = new Array<ProceduraAggiudicazione>();
  elencoIter: Array<StepAggiudicazione> = new Array<StepAggiudicazione>();

  displayedColumns: string[] = ['descTipologiaAggiudicazione', 'CPA/CIG', 'importo', 'descProcAgg'];
  dataSource: MatTableDataSource<ProceduraAggiudicazione> = new MatTableDataSource<ProceduraAggiudicazione>();
  @ViewChild(MatPaginator) paginator: MatPaginator;

  motiviScostamento: Array<MotivoScostamento>;

  dettagglioAppalto: Appalto = {};

  @ViewChild("appaltoForm", { static: true }) ngForm: NgForm;
  hasValidationError: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  //REQUESTS
  filtroRicerca: FiltroProcedureAggiudicazione;
  requestGetProcAggiudicaz: GetProcedureAggiudicazioneRequest;
  requestModificaAppalto: CreaAppaltoRequest;
  esitoModifica: EsitoGestioneAppalti;

  constructor(private activatedRoute: ActivatedRoute,
    private router: Router,
    private appaltiService: AppaltiService,
    private appalti2Service: Appalti2Service,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private configService: ConfigService,
    private sharedService: SharedService
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.displayedColumns.push('azioni');
            this.idProgetto = +params['id'];
            this.idAppalto = +params['id1'];
            this.loadData();
            if (this.activatedRoute.snapshot.paramMap.get('success')) {
              this.showMessageSuccess("Salvataggio avvenuto con successo.")
            }
            if (this.activatedRoute.snapshot.paramMap.get('isProcVisible')) {
              this.showProcedureAggiudicaz();
            }
          });
        }
      }
    });
  }

  loadData() {
    //PULSANTE ELIMINA
    this.loadedElimina = false;
    this.subscribers.codice = this.appalti2Service.eliminazioneAppaltoAbilitata(this.user.codiceRuolo).subscribe(res => {
      this.eliminazioneAppaltoAbilitata = res;
      this.loadedElimina = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedElimina = true;
    });
    // CODICE PROGETTO
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

    // DETTAGGLIO APPALTO
    this.loadedDettagglioAppalto = false;
    this.appalto = { idAppalto: this.idAppalto }
    this.getAppaltiParams = { idProgetto: this.idProgetto, body: this.appalto };
    this.subscribers.codice = this.appaltiService.getAppalti(this.getAppaltiParams).subscribe(data => {
      if (data) {
        this.dettagglioAppalto = data[0];
        this.importoContrattoFormatted = this.dettagglioAppalto.importoContratto ? this.sharedService.formatValue(this.dettagglioAppalto.importoContratto.toString()) : null;
        this.importoBaseGaraFormatted = this.dettagglioAppalto.bilancioPreventivo ? this.sharedService.formatValue(this.dettagglioAppalto.bilancioPreventivo.toString()) : null;
        this.importoRibAstaFormatted = this.dettagglioAppalto.importoRibassoAsta ? this.sharedService.formatValue(this.dettagglioAppalto.importoRibassoAsta.toString()) : null;
        this.precentualeRibAstaFormatted = this.dettagglioAppalto.percentualeRibassoAsta ? this.sharedService.formatValue(this.dettagglioAppalto.percentualeRibassoAsta.toString()) : null;
        this.proceduraAggiudicazioneSelected.descProcAgg = this.dettagglioAppalto.descrizioneProceduraAggiudicazione;
        // TIPOLOGIE APPALTI
        this.loadedTipologieAppalti = false;
        this.subscribers.codice = this.appaltiService.getTipologieAppalti().subscribe(res => {
          if (res) {
            this.tipologieAppalti = res;
            this.tipologieAppalti.map(r => {
              if (r.codice === this.dettagglioAppalto.idTipologiaAppalto.toString()) {
                this.tipologiaAppaltoSelected = r;
              }
            })
          }
          this.loadedTipologieAppalti = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedTipologieAppalti = true;
        })
      }
      this.loadedDettagglioAppalto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDettagglioAppalto = true;
    })
  }

  selectCaricaIter($event: MatCheckboxChange) {
    if ($event.checked)
      this.isIterAggVisible = true;
    else
      this.isIterAggVisible = false;
  }

  setImportoContratto() {
    this.dettagglioAppalto.importoContratto = this.sharedService.getNumberFromFormattedString(this.importoContrattoFormatted);
    if (this.dettagglioAppalto.importoContratto !== null) {
      this.importoContrattoFormatted = this.sharedService.formatValue(this.dettagglioAppalto.importoContratto.toString());
    }
  }

  setImportoBaseGara() {
    this.dettagglioAppalto.bilancioPreventivo = this.sharedService.getNumberFromFormattedString(this.importoBaseGaraFormatted);
    if (this.dettagglioAppalto.bilancioPreventivo !== null) {
      this.importoBaseGaraFormatted = this.sharedService.formatValue(this.dettagglioAppalto.bilancioPreventivo.toString());
    }
  }

  setImportoRibAsta() {
    this.dettagglioAppalto.importoRibassoAsta = this.sharedService.getNumberFromFormattedString(this.importoRibAstaFormatted);
    if (this.dettagglioAppalto.importoRibassoAsta !== null) {
      this.importoRibAstaFormatted = this.sharedService.formatValue(this.dettagglioAppalto.importoRibassoAsta.toString());
    }
  }

  setPercentualeRibAsta() {
    this.dettagglioAppalto.percentualeRibassoAsta = this.sharedService.getNumberFromFormattedString(this.precentualeRibAstaFormatted);
    if (this.dettagglioAppalto.percentualeRibassoAsta !== null) {
      this.precentualeRibAstaFormatted = this.sharedService.formatValue(this.dettagglioAppalto.percentualeRibassoAsta.toString());
    }
  }

  validate() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.hasValidationError = false;
    this.checkRequiredForm(this.ngForm, "tipologie");
    this.checkRequiredForm(this.ngForm, "dtFirmaContratto");
    this.checkRequiredForm(this.ngForm, "importoContratto");
    this.checkRequiredForm(this.ngForm, "oggetto");
    this.checkRequiredForm(this.ngForm, "proceduraAggiudicazione");

    this.setAllMarkAsTouched();
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

  setAllMarkAsTouched() {
    Object.keys(this.ngForm.form.controls).forEach(key => {
      if (this.ngForm.form.controls[key]) {
        this.ngForm.form.controls[key].markAsTouched();
      }
    });
  }

  modificaAppalto() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.validate();

    if (!this.hasValidationError) {
      this.loadedModifica = true;
      this.dettagglioAppalto.idTipologiaAppalto = +this.tipologiaAppaltoSelected.codice;
      this.requestModificaAppalto = { appalto: this.dettagglioAppalto, idProceduraAggiudicaz: this.dettagglioAppalto.idProceduraAggiudicazione }
      this.subscribers.modifica = this.appaltiService.creaAppalto(this.requestModificaAppalto).subscribe(res => {
        if (res) {
          this.esitoModifica = res;
          this.showMessageSuccess(res.message);
        }
        this.loadedModifica = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di salvataggio.")
        this.loadedModifica = false;
      });
    }
  }

  eliminaAppalto() {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l’ eliminazione dell'appalto?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedElimina = false;
        this.subscribers.eliminaAffidamento = this.appalti2Service.eliminaAppalto(this.idAppalto).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.goToRicercaAppalti(data.message);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedElimina = true;
          this.showMessageError("Errore nell'eliminazione dell'appalto.");
        });
      }
    });
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedDettagglioAppalto || !this.loadedTipologieAppalti || this.loadedCerca
      || this.loadedTipologieAggiudicaz || this.loadedModifica || !this.loadedElimina) {
      return true;
    }
    return false;
  }

  goToRicercaAppalti(message?: string) {
    let url: string = "drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/appalti/" + this.idProgetto;
    if (message) {
      url += ";message=" + message;
    }
    this.router.navigateByUrl(url);
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

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }
  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showProcedureAggiudicaz() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedTipologieAggiudicaz = true
    this.isProcedureAggiudicazVisible = true;
    this.tipologieAggiudicazSelected = { codice: "", descrizione: "" };
    if (this.elencoProcAggiudicaz.length == 0)
      this.noElementsMessageVisible = true;

    this.appaltiService.getTipologieProcedureAggiudicazione(this.idProgetto).subscribe(res => {
      if (res) {
        this.tipologieAggiudicaz = res;
      }
      this.loadedTipologieAggiudicaz = false;
    }, err => {
      this.loadedTipologieAggiudicaz = false
      this.isProcedureAggiudicazVisible = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore durante il recupero delle tipologie procedure aggiudicazione");
    });
    this.cercaProceduraAggiudicazione();
  }

  concatProcAgg(proceduraAggiudicazione: string, descrizioneProceduraAggiudicazione: string) {
    return proceduraAggiudicazione + " " + descrizioneProceduraAggiudicazione;
  }

  selectProceduraAggiudicazione(row) {
    this.proceduraAggiudicazioneSelected = row;
    this.isProcedureAggiudicazVisible = false;
  }

  goToNuovaProcedura() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/nuovaProcedura", this.idProgetto, { from: "modifica", idAppalto: this.idAppalto }]);
  }

  goToModificaProcedura(idProceduraAggiudicaz: any) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/modificaProcedura", this.idProgetto, idProceduraAggiudicaz, { from: "modifica", idAppalto: this.idAppalto }]);
  }

  cercaProceduraAggiudicazione() {
    this.resetMessageError();
    this.resetMessageSuccess();

    this.loadedCerca = true;
    this.filtroRicerca = { cigProcAgg: this.cig ? this.cig : null, codProcAgg: this.cpa ? this.cpa : null, idTipologiaAggiudicaz: parseInt(this.tipologieAggiudicazSelected.codice) };
    this.requestGetProcAggiudicaz = { filtro: this.filtroRicerca, idProgetto: this.idProgetto };

    this.appaltiService.getAllProcedureAggiudicazione(this.requestGetProcAggiudicaz).subscribe(res => {
      if (res) {
        this.elencoProcAggiudicaz = res;
        this.dataSource = new MatTableDataSource<ProceduraAggiudicazione>();
        this.dataSource.data = this.elencoProcAggiudicaz;
        this.paginator.length = this.elencoProcAggiudicaz.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        if (res.length > 0) {
          this.noElementsMessageVisible = false;
        } else {
          this.noElementsMessageVisible = true;
        }
      }
      this.loadedCerca = false;
    }, err => {
      this.showMessageError("Errore durante la ricerca delle procedure aggiudicazione.")
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }
}
