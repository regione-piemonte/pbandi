/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { MatTableDataSource } from "@angular/material/table";
import { QuadroPrevisionaleItem } from "../../common/dto/quadro-previsionale-item";
import { MatPaginator } from "@angular/material/paginator";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { QuadroPrevisionaleService } from "../../services/quadro-previsionale.service";
import { Constants } from "../../../core/commons/util/constants";
import { IndicatoreItem } from "../../../indicatori/commons/dto/indicatore-item";
import { ResponseGetQuadroPrevisionale } from "../../common/dto/response-get-quadro-previsionale";
import { QuadroPrevisionaleComplessivoItem } from "../../common/dto/quadro-previsionale-complessivo-item";
import { ValidazioneDatiQuadroProvisionaleRequest } from "../../common/request/validazione-dati-quadro-provisionale-request";
import { ExecResults } from "../../common/dto/exec-results";
import { QuadroPrevisionaleModel } from "../../common/dto/quadro-previsionale-model";
import { SharedService } from 'src/app/shared/services/shared.service';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-quadro-previsionale',
  templateUrl: './quadro-previsionale.component.html',
  styleUrls: ['./quadro-previsionale.component.scss']
})
export class QuadroPrevisionaleComponent implements OnInit {

  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;

  displayedColumns: string[] = ['periodo', 'ultimoPreventivo', 'realizzato', 'daRealizzare'];
  displayedColumns2: string[] = ['periodo', 'ultimoPreventivo', 'nuovoPreventivo', 'realizzato', 'daRealizzare'];

  displayedColumns1: string[] = ['col0', 'ultimaSpesaAmmessa', 'ultimoPreventivo', 'realizzato', 'daRealizzare'];
  displayedColumns3: string[] = ['col0', 'ultimaSpesaAmmessa', 'ultimoPreventivo', 'nuovoPreventivo', 'realizzato', 'daRealizzare'];
  dataSource: MatTableDataSource<QuadroPrevisionaleItem> = new MatTableDataSource<QuadroPrevisionaleItem>();
  dataSource1: MatTableDataSource<QuadroPrevisionaleComplessivoItem> = new MatTableDataSource<QuadroPrevisionaleComplessivoItem>();

  @ViewChild("tableForm", { static: true }) tableForm: NgForm;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: any;
  isMessageSuccessVisible: boolean;
  messageError: string[];
  isMessageErrorVisible: boolean;
  messageWarning: string[];
  isMessageWarningVisible: boolean;

  responseGetQuadroPrevisionale1: ResponseGetQuadroPrevisionale = { vociWeb: new Array<QuadroPrevisionaleItem>() };
  responseGetQuadroPrevisionale: ResponseGetQuadroPrevisionale = { vociWeb: new Array<QuadroPrevisionaleItem>() };
  //LOADED
  loadedCodiceProgetto: boolean;
  loadedQuadroPrevisio: boolean;
  loadedSave: boolean;
  loadedChiudiAttivita: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  validazioneDatiIndicatoriRequest: ValidazioneDatiQuadroProvisionaleRequest;
  salvaQuadroPrevisioRequest: ResponseGetQuadroPrevisionale;
  execResults: ExecResults;

  isModifica: boolean;
  confermaSalvataggio: boolean;

  errMsg: Array<string>;
  quadroPrevisionaleModel: QuadroPrevisionaleModel;

  constructor(private configService: ConfigService, private userService: UserService, private handleExceptionService: HandleExceptionService,
    private quadroPrevisionaleService: QuadroPrevisionaleService, private activatedRoute: ActivatedRoute, private sharedService: SharedService,
    private dialog: MatDialog, private router: Router) { }

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
      }
    });
  }

  trackByFn(index, item) {
    return index;
  }

  loadData() {
    this.loadedSave = false;
    this.isModifica = false;
    this.confermaSalvataggio = false;
    // Codice progetto
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.quadroPrevisionaleService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });

    //Quadro previsionale
    this.loadedQuadroPrevisio = false;
    this.subscribers.codice = this.quadroPrevisionaleService.getQuadroPrevisionale(this.idProgetto).subscribe(res => {
      if (res) {
        this.responseGetQuadroPrevisionale1 = res;
        this.responseGetQuadroPrevisionale = {
          vociWeb: this.responseGetQuadroPrevisionale1.vociWeb.filter(res => res.isPeriodo || res.isVociVisibili || res.isRigaTotale || res.isRigaDate),
          note: this.responseGetQuadroPrevisionale1.note, quadroComplessivoList: this.responseGetQuadroPrevisionale1.quadroComplessivoList,
          quadroPrevisionale: this.responseGetQuadroPrevisionale1.quadroPrevisionale, idProgetto: this.responseGetQuadroPrevisionale1.idProgetto
        };
        let tot = this.responseGetQuadroPrevisionale.vociWeb.find(v => v.isRigaTotale);
        if (!tot.nuovoPreventivo || tot.nuovoPreventivo === "0,00") {
          this.responseGetQuadroPrevisionale.vociWeb.forEach(v => {
            v.nuovoPreventivo = v.realizzato ? v.realizzato : "0,00";
          });
        }
        console.log(this.responseGetQuadroPrevisionale.vociWeb);
        this.dataSource = new MatTableDataSource<QuadroPrevisionaleItem>();
        this.dataSource.data = this.responseGetQuadroPrevisionale.vociWeb;

        this.dataSource1 = new MatTableDataSource<QuadroPrevisionaleComplessivoItem>();
        this.dataSource1.data = this.responseGetQuadroPrevisionale.quadroComplessivoList;
      }
      this.loadedQuadroPrevisio = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedQuadroPrevisio = true;
    });
  }

  indietro() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.resetMessageWarning();
    this.responseGetQuadroPrevisionale.vociWeb.shift();
    this.dataSource.data = this.responseGetQuadroPrevisionale.vociWeb;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
    this.isModifica = false;
  }

  modifica() {
    this.responseGetQuadroPrevisionale.vociWeb.unshift(new QuadroPrevisionaleItem());
    this.dataSource.data = this.responseGetQuadroPrevisionale.vociWeb;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
    this.isModifica = true;
  }

  ribaltaUltimoPreventivo() {
    this.responseGetQuadroPrevisionale.vociWeb.forEach((v, i) => {
      if (i > 1) {
        v.nuovoPreventivo = v.ultimoPreventivo ? v.ultimoPreventivo : "0,00";
      }
    });
  }

  ribaltaRealizzato() {
    this.responseGetQuadroPrevisionale.vociWeb.forEach((v, i) => {
      if (i > 1) {
        v.nuovoPreventivo = v.realizzato ? v.realizzato : "0,00";
      }
    });
  }

  modelChangeNuovoPreventivo(inputValue: string, index: number) {
    if (!this.tableForm.form.get('nuovoPreventivo' + index).hasError('pattern')) {
      this.calcolaTotaliNuovoPreventivo(index, "modelChange", this.sharedService.getNumberFromFormattedString(inputValue));
    }
  }

  changeNuovoPreventivo(item: QuadroPrevisionaleItem, index: number) {
    if (!this.tableForm.form.get('nuovoPreventivo' + index).hasError('pattern')) {
      this.formatNuovoPreventivo(item);
      this.calcolaTotaliNuovoPreventivo(index);
    }
  }

  formatNuovoPreventivo(item: QuadroPrevisionaleItem) {
    if (item.nuovoPreventivo) {
      let imp: number = this.sharedService.getNumberFromFormattedString(item.nuovoPreventivo);
      if (imp !== null) {
        item.nuovoPreventivo = this.sharedService.formatValue(imp.toString());
      }
    }
  }

  calcolaTotaliNuovoPreventivo(index: number, from?: string, importoNuovo?: number) {
    let totaleNuovo: number = 0;
    let i: number = 0;
    for (let item of this.responseGetQuadroPrevisionale.vociWeb) {
      if (i > 2) {
        if (from && i === index) {
          item.nuovoPreventivo = importoNuovo ? importoNuovo.toString() : null; //non formatto sul ngModelChange, altrimenti non si riesce a scrivere
          totaleNuovo += importoNuovo ? importoNuovo : 0;
        } else {
          item.nuovoPreventivo = item.nuovoPreventivo ? item.nuovoPreventivo : "0,00";
          totaleNuovo += this.sharedService.getNumberFromFormattedString(item.nuovoPreventivo);
        }
      }
      i++;
    }
    let rigaTot = this.responseGetQuadroPrevisionale.vociWeb.find(v => v.isRigaTotale);
    if (rigaTot) {
      rigaTot.nuovoPreventivo = this.sharedService.formatValue(totaleNuovo.toString());
    }
  }

  salva() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.loadedSave = true;
    this.validazioneDatiIndicatoriRequest = {
      noteQuadroPrevisionale: this.responseGetQuadroPrevisionale.note, quadroPrevisionale: this.responseGetQuadroPrevisionale.quadroPrevisionale,
      voci: this.responseGetQuadroPrevisionale.vociWeb, vociQuadroComplessivo: this.responseGetQuadroPrevisionale.quadroComplessivoList
    };
    this.subscribers.save = this.quadroPrevisionaleService.controllaDatiPerSalvataggio(this.validazioneDatiIndicatoriRequest).subscribe(data => {
      if (data) {
        this.execResults = data;
        this.execResults.globalErrors = this.execResults.globalErrors ? this.execResults.globalErrors.filter(g => g !== null && g.length > 0) : [];
        this.execResults.globalMessages = this.execResults.globalMessages ? this.execResults.globalMessages.filter(g => g !== null && g.length > 0) : [];
        if (this.execResults.resultCode === "ERROR") {

          this.showMessageError(this.execResults.globalErrors);
        } else {
          this.showMessageWarning(this.execResults.globalMessages);
          //  this.responseGetQuadroPrevisionale.vociWeb.shift();
          // this.dataSource.data = this.responseGetQuadroPrevisionale.vociWeb;
          this.isModifica = false;
          this.confermaSalvataggio = true;
        }
        data.model.voci.shift();
        this.responseGetQuadroPrevisionale = { vociWeb: data.model.voci, quadroComplessivoList: data.model.vociQuadroComplessivo };
        this.dataSource.data = this.responseGetQuadroPrevisionale.vociWeb;
        this.dataSource1.data = this.responseGetQuadroPrevisionale.quadroComplessivoList;
        this.loadedSave = false;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.errMsg = [];
      this.errMsg.push("Errore nella validazione dei dati.");
      this.showMessageError(this.errMsg);
      this.loadedSave = false;
    })

  }

  annulla() {
    this.responseGetQuadroPrevisionale.vociWeb.unshift(new QuadroPrevisionaleItem());
    this.dataSource.data = this.responseGetQuadroPrevisionale.vociWeb;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
    this.isModifica = true;
    this.confermaSalvataggio = false;
  }

  conferma() {
    this.loadedSave = true;
    this.salvaQuadroPrevisioRequest = {
      idProgetto: this.idProgetto, quadroPrevisionale: this.responseGetQuadroPrevisionale.quadroPrevisionale,
      quadroComplessivoList: this.responseGetQuadroPrevisionale.quadroComplessivoList, vociWeb: this.responseGetQuadroPrevisionale.vociWeb,
      note: this.responseGetQuadroPrevisionale.note
    };
    this.subscribers.save = this.quadroPrevisionaleService.salvaQuadroPrevisionale(this.salvaQuadroPrevisioRequest).subscribe(data => {
      if (data) {
        this.resetMessageSuccess();
        this.resetMessageError();
        this.resetMessageWarning();
        this.loadData();
        this.showMessageSuccess("Salvataggio avvenuto con successo.");
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.confermaSalvataggio = false;

      this.resetMessageSuccess();
      this.resetMessageError();
      this.resetMessageWarning();

      this.errMsg = [];
      this.errMsg.push("Errore nella validazione dei dati indicatori.");
      this.showMessageError(this.errMsg);
      this.loadedSave = false;
    })
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageWarning(msgs: string[]) {
    this.messageWarning = msgs;
    this.isMessageWarningVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }


  showMessageError(msgs: string[]) {
    this.messageError = msgs;
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

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedQuadroPrevisio || this.loadedSave || !this.loadedChiudiAttivita) {
      return true;
    }
    return false;
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_QUADRO_PREVISIONALE).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError(["ATTENZIONE: non è stato possibile chiudere l'attività."]);
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
  updateNuovoPreventivo(periodo: any, nP: HTMLInputElement) {
    let voci: Array<QuadroPrevisionaleItem>;
    voci = this.responseGetQuadroPrevisionale.vociWeb.map(item => {
      item.periodo === periodo ? item.nuovoPreventivo = nP.value : item.nuovoPreventivo = item.nuovoPreventivo;
      return item;
    });
    this.responseGetQuadroPrevisionale.vociWeb = voci;
    this.dataSource = new MatTableDataSource<QuadroPrevisionaleItem>();
    this.dataSource.data = this.responseGetQuadroPrevisionale.vociWeb;
  }

}
