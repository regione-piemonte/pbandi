/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { UserService } from "../../../core/services/user.service";
import { AppaltiService } from "../../services/appalti.service";
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { Constants } from "../../../core/commons/util/constants";
import { NgForm } from "@angular/forms";
import { CodiceDescrizione } from "../../commons/dto/codice-descrizione";
import { MatTableDataSource } from "@angular/material/table";
import { GetProcedureAggiudicazioneRequest } from "../../commons/dto/get-procedure-aggiudicazione-request";
import { FiltroProcedureAggiudicazione } from "../../commons/dto/filtro-procedure-aggiudicazione";
import { ProceduraAggiudicazione } from "../../commons/dto/procedura-aggiudicazione";
import { MatPaginator } from "@angular/material/paginator";
import { CreaAppaltoRequest } from "../../commons/dto/crea-appalto-request";
import { Appalto } from "../../commons/dto/appalto";
import { DatePipe } from "@angular/common";
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { MatDialog } from '@angular/material/dialog';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-nuovo-appalto',
  templateUrl: './nuovo-appalto.component.html',
  styleUrls: ['./nuovo-appalto.component.scss']
})
export class NuovoAppaltoComponent implements OnInit {
  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;

  tipologieAppalti: Array<CodiceDescrizione>;
  tipologieAppaltiSelected: CodiceDescrizione;
  dtPrevistaInizioLavori: Date;
  dtConsegnaLavori: any;
  dtFirmaContratto: any;
  importoContratto: number;
  importoContrattoFormatted: string;
  importoBaseGara: number;
  importoBaseGaraFormatted: string;
  importoRibAsta: number;
  importoRibAstaFormatted: string;
  precentualeRibAsta: number;
  precentualeRibAstaFormatted: string;
  oggetto: string;
  impresa: string;
  identificativoPISU: string;
  dtPubblicazioneGUUE: Date;
  dtPubblicazioneGURI: Date;
  dtPubblicazioneNazionali: Date;
  dtPubblicazioneAppaltante: Date;
  dtPubblicazioneRegLLPP: Date;
  proceduraAggiudicazioneSelected: ProceduraAggiudicazione = { descProcAgg: "", descTipologiaAggiudicazione: "" };

  criteriRicercaOpened: boolean;
  isProcedureAggiudicazVisible: boolean;

  //FILTRO RICERCA (PROCEDURE DI AGGIUDICAZIONE)
  tipologieAggiudicaz: Array<CodiceDescrizione>;
  tipologieAggiudicazSelected: CodiceDescrizione;
  cpa: string; // codice procedura aggiudicazione
  cig: string; // codice identificativo gara

  //LOADED
  loadedTipologieAppalti: boolean;
  loadedSave: boolean;
  loadedTipologieAggiudicaz: boolean;
  loadedCerca: boolean;

  noElementsMessage: string = "Non ci sono elementi da visualizzare.";
  noElementsMessageVisible: boolean;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  hasValidationError: boolean;

  elencoProcAggiudicaz: Array<ProceduraAggiudicazione> = new Array<ProceduraAggiudicazione>();
  displayedColumns: string[] = ['descTipologiaAggiudicazione', 'CPA/CIG', 'importo', 'descProcAgg'];
  dataSource: MatTableDataSource<ProceduraAggiudicazione> = new MatTableDataSource<ProceduraAggiudicazione>();

  @ViewChild("nuovoppaltoForm", { static: true }) ngForm: NgForm;
  @ViewChild("procAggiudForm", { static: true }) procAggiudForm: NgForm;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  //REQUESTS
  filtroRicerca: FiltroProcedureAggiudicazione;
  requestGetProcAggiudicaz: GetProcedureAggiudicazioneRequest;

  appalto: Appalto;
  creaAppaltoRequest: CreaAppaltoRequest;

  constructor(private activatedRoute: ActivatedRoute,
    private router: Router,
    private appaltiService: AppaltiService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private datePipe: DatePipe,
    private configService: ConfigService,
    private sharedService: SharedService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.displayedColumns.push('azioni');
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.loadCombo();
            if (this.activatedRoute.snapshot.paramMap.get('isProcVisible')) {
              this.showProcedureAggiudicaz();
            }
            this.subscribers.codice = this.appaltiService.getCodiceProgetto(this.idProgetto).subscribe(res => {
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

  loadCombo() {
    this.loadedTipologieAppalti = false;
    this.subscribers.codice = this.appaltiService.getTipologieAppalti().subscribe(res => {
      if (res) {
        this.tipologieAppalti = res;
      }
      this.loadedTipologieAppalti = true;
    })
  }

  setImportoContratto() {
    this.importoContratto = this.sharedService.getNumberFromFormattedString(this.importoContrattoFormatted);
    if (this.importoContratto !== null) {
      this.importoContrattoFormatted = this.sharedService.formatValue(this.importoContratto.toString());
    }
  }

  setImportoBaseGara() {
    this.importoBaseGara = this.sharedService.getNumberFromFormattedString(this.importoBaseGaraFormatted);
    if (this.importoBaseGara !== null) {
      this.importoBaseGaraFormatted = this.sharedService.formatValue(this.importoBaseGara.toString());
    }
  }

  setImportoRibAsta() {
    this.importoRibAsta = this.sharedService.getNumberFromFormattedString(this.importoRibAstaFormatted);
    if (this.importoRibAsta !== null) {
      this.importoRibAstaFormatted = this.sharedService.formatValue(this.importoRibAsta.toString());
    }
  }

  setPercentualeRibAsta() {
    this.precentualeRibAsta = this.sharedService.getNumberFromFormattedString(this.precentualeRibAstaFormatted);
    if (this.precentualeRibAsta !== null) {
      this.precentualeRibAstaFormatted = this.sharedService.formatValue(this.precentualeRibAsta.toString());
    }
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



  salva() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.validate();

    if (!this.hasValidationError) {
      this.loadedSave = true;

      this.appalto = {
        idTipologiaAppalto: parseInt(this.tipologieAppaltiSelected.codice),
        dtPrevistaInizioLavori: this.datePipe.transform(this.dtPrevistaInizioLavori, 'yyyy-MM-dd'),
        dtConsegnaLavori: this.dtConsegnaLavori ? this.datePipe.transform(this.dtConsegnaLavori, 'yyyy-MM-dd') : null,
        dtFirmaContratto: this.dtFirmaContratto ? this.datePipe.transform(this.dtFirmaContratto, 'yyyy-MM-dd') : null,
        importoContratto: this.importoContratto, importoRibassoAsta: this.importoRibAsta, percentualeRibassoAsta: this.precentualeRibAsta,
        oggettoAppalto: this.oggetto, impresaAppaltatrice: this.impresa, identificativoIntervento: this.identificativoPISU,
        dtPubGUUE: this.dtPubblicazioneGUUE ? this.datePipe.transform(this.dtPubblicazioneGUUE, 'yyyy-MM-dd') : null,
        dtPubGURI: this.dtPubblicazioneGURI ? this.datePipe.transform(this.dtPubblicazioneGURI, 'yyyy-MM-dd') : null,
        dtPubQuotidianiNazionali: this.dtPubblicazioneNazionali ? this.datePipe.transform(this.dtPubblicazioneNazionali, 'yyyy-MM-dd') : null,
        dtPubStazioneAppaltante: this.dtPubblicazioneAppaltante ? this.datePipe.transform(this.dtPubblicazioneAppaltante, 'yyyy-MM-dd') : null,
        dtPubLLPP: this.dtPubblicazioneRegLLPP ? this.datePipe.transform(this.dtPubblicazioneRegLLPP, 'yyyy-MM-dd') : null,
        idProceduraAggiudicazione: this.proceduraAggiudicazioneSelected ? this.proceduraAggiudicazioneSelected.idProceduraAggiudicaz : null,
        descrizioneProceduraAggiudicazione: this.proceduraAggiudicazioneSelected ? this.proceduraAggiudicazioneSelected.descProcAgg : null,
        proceduraAggiudicazione: this.proceduraAggiudicazioneSelected ? this.proceduraAggiudicazioneSelected.cigProcAgg + " " + this.proceduraAggiudicazioneSelected.descProcAgg : null
      };
      this.creaAppaltoRequest = { appalto: this.appalto, idProceduraAggiudicaz: this.proceduraAggiudicazioneSelected.idProceduraAggiudicaz };

      this.subscribers.save = this.appaltiService.creaAppalto(this.creaAppaltoRequest).subscribe(res => {
        if (res) {
          if (res.esito) {
            this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/modificaAppalto", this.idProgetto, res.appalto.idAppalto, { success: 'true' }]);
          } else {
            this.showMessageError(res.message);
          }
        }
        this.loadedSave = false;
      }), err => {
        this.handleExceptionService.handleNotBlockingError(err);

        this.showMessageError("Errore nel salvataggio dell'appalto.")
        this.loadedSave = false;
      };;
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
    if (!this.loadedTipologieAppalti || this.loadedTipologieAggiudicaz || this.loadedCerca || this.loadedSave) {
      return true;
    }
    return false;
  }

  selectProceduraAggiudicazione(row: ProceduraAggiudicazione) {
    this.proceduraAggiudicazioneSelected = row;
    this.isProcedureAggiudicazVisible = false;
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
  goToRicercaAppalti() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/appalti", this.idProgetto]);
  }
  goToModificaProcedura(idProceduraAggiudicaz: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/modificaProcedura", this.idProgetto, idProceduraAggiudicaz, { from: "nuovo" }]);
  }
  goToNuovaProcedura() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/nuovaProcedura", this.idProgetto, { from: "nuovo" }]);
  }

}
