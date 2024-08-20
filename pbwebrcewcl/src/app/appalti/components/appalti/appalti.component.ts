/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { ActivatedRoute, Router } from "@angular/router";
import { DateAdapter } from "@angular/material/core";
import { AppaltiService } from "../../services/appalti.service";
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { CodiceDescrizione } from "../../commons/dto/codice-descrizione";
import { Constants } from "../../../core/commons/util/constants";
import { MatTableDataSource } from "@angular/material/table";
import { AppaltoDTO } from "../../commons/dto/appalto-dto";
import { Appalto } from "../../commons/dto/appalto";
import { CronoprogrammaItem } from "../../../cronoprogramma/commons/dto/cronoprogramma-item";
import { MatPaginator } from "@angular/material/paginator";
import { ExcelService } from "../../../shared/services/excel.service";
import { NgForm } from "@angular/forms";
import { SharedService } from 'src/app/shared/services/shared.service';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-appalti',
  templateUrl: './appalti.component.html',
  styleUrls: ['./appalti.component.scss']
})
export class AppaltiComponent implements OnInit {

  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;

  tipologieAppalti: Array<CodiceDescrizione>;
  tipologieAppaltiSelected: CodiceDescrizione;

  appalto: Appalto;
  getAppaltiParams: AppaltiService.GetAppaltiParams;
  dtPrevistaInizioLavori: any;
  dtConsegnaLavori: any;
  dtFirmaContratto: any;

  elencoAppalti: Array<Appalto> = new Array<Appalto>();
  displayedColumns: string[] = ['dataPrevistaInizioLavori', 'dataConsegnaLavori', 'dataFirmaContratto', 'importoContratto', 'oggettoAppalto', 'proceduraAggiudicazione'];
  dataSource: MatTableDataSource<Appalto> = new MatTableDataSource<Appalto>();

  @ViewChild("ricercaForm", { static: true }) ricercaForm: NgForm;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  criteriRicercaOpened: boolean;

  noElementsMessage: string = "Non ci sono elementi da visualizzare.";
  noElementsMessageVisible: boolean;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedTipologieAppalti: boolean;
  loadedCerca: boolean = true;
  loadedChiudiAttivita: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};


  constructor(private configService: ConfigService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private appaltiService: AppaltiService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private excelService: ExcelService,
    private sharedService: SharedService,
    private dialog: MatDialog,
    private _adapter: DateAdapter<any>) {
    this._adapter.setLocale('it');
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.displayedColumns.push('azioni');
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.loadData();
            this.cerca(this.activatedRoute.snapshot.paramMap.get('message'));
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
    this.loadedTipologieAppalti = false;
    this.subscribers.codice = this.appaltiService.getTipologieAppalti().subscribe(res => {
      if (res) {
        this.tipologieAppalti = res;
      }
      this.loadedTipologieAppalti = true;
    })

  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedTipologieAppalti || !this.loadedCerca || !this.loadedChiudiAttivita) {
      return true;
    }
    return false;
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

  cerca(message?: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (message) {
      this.showMessageSuccess(message);
    }
    this.loadedCerca = false;
    this.appalto = {
      idTipologiaAppalto: this.tipologieAppaltiSelected ? parseInt(this.tipologieAppaltiSelected.codice) : null,
      dtPrevistaInizioLavori: this.dtPrevistaInizioLavori ? this.dtPrevistaInizioLavori.toString() : null,
      dtConsegnaLavori: this.dtConsegnaLavori ? this.dtConsegnaLavori : null,
      dtFirmaContratto: this.dtFirmaContratto ? this.dtFirmaContratto : null,
      idProceduraAggiudicazione: null, idAppalto: null, proceduraAggiudicazione: null
    }
    this.getAppaltiParams = { idProgetto: this.idProgetto, body: this.appalto };
    this.subscribers.codice = this.appaltiService.getAppalti(this.getAppaltiParams).subscribe(data => {
      if (data) {
        this.elencoAppalti = data;
        this.dataSource = new MatTableDataSource<Appalto>();
        this.dataSource.data = this.elencoAppalti;
        this.paginator.length = this.elencoAppalti.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        if (data.length > 0) {
          this.noElementsMessageVisible = false;
        } else {
          this.noElementsMessageVisible = true;
        }
        console.log("noElementsMessageVisible" + this.noElementsMessageVisible);
        this.criteriRicercaOpened = false;
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca.");
      this.loadedCerca = true;
    });
  }

  esportaDettaglioElenco() {
    this.excelService.esportaDettaglioAppalti(this.elencoAppalti, this.idProgetto);
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_GESTIONE_APPALTI).subscribe(data => {
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

  goToNuovoAppalto() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/nuovoAppalto", this.idProgetto]);
  }

  goToModificaAppalto(idAppalto: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI + "/modificaAppalto", this.idProgetto, idAppalto]);
  }
}
