/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { CodiceDescrizione } from 'src/app/shared/commons/dto/codice-descrizione';
import { LineaInterventoDTO } from 'src/app/shared/commons/dto/linea-intervento-dto';
import { PropostaCertificazioneDTO } from 'src/app/shared/commons/dto/proposta-certificazione-dto';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { CertificazioneService } from 'src/app/shared/services/certificazione.service';
import { CreaPropostaRequest } from '../../commons/requests/crea-proposta-request';

@Component({
  selector: 'app-crea-anteprima-proposta',
  templateUrl: './crea-anteprima-proposta.component.html',
  styleUrls: ['./crea-anteprima-proposta.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CreaAnteprimaPropostaComponent implements OnInit {

  lineeIntervento: Array<LineaInterventoDTO>;
  lineaDiInterventoSelected: LineaInterventoDTO;

  minData: Date = new Date("1/1/2007");
  dataYesterday: Date;
  dataPagamenti: FormControl;
  dataValidazioni: FormControl;
  dataFideiussioni: FormControl;
  dataErogazioni: FormControl;
  descrizione: string;
  isBozza: boolean;

  isValidated: boolean;
  hasError: boolean;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  @ViewChild("creaForm", { static: true }) creaForm: NgForm;

  //LOADED
  loadedLineeIntervento: boolean;
  loadedCrea: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private router: Router,
    private certificazioneService: CertificazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private _adapter: DateAdapter<any>
  ) {
    this._adapter.setLocale('it');
  }

  ngOnInit(): void {
    this.dataYesterday = new Date();
    this.dataYesterday.setHours(0, 0, 0, 0);
    this.dataYesterday.setDate(this.dataYesterday.getDate() - 1);
    this.dataPagamenti = new FormControl(this.dataYesterday);
    this.dataValidazioni = new FormControl(this.dataYesterday);
    this.dataFideiussioni = new FormControl(this.dataYesterday);
    this.dataErogazioni = new FormControl(this.dataYesterday);
    this.loadedLineeIntervento = false;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.subscribers.linee = this.certificazioneService.getLineeDiInterventoDisponibili().subscribe(data => {
          if (data) {
            this.lineeIntervento = data;
            this.lineaDiInterventoSelected = data[0];
          }
          this.loadedLineeIntervento = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }
    });
  }

  valida() {
    this.resetMessageSuccess();
    this.resetMessageError();
    /*
    lineaIntervento!=null
    dataPagamenti!=null
    dataValidazioni!=null
    dataFideiussioni!=null
    dataErogazioni!=null
    date <= oggi
    date >= 1/1/2007 
    dataValidazione >= dataPagamenti
    */
    if (!this.lineaDiInterventoSelected || !this.lineaDiInterventoSelected.idLineaDiIntervento) {
      this.hasError = true;
      this.creaForm.form.get('linea').setErrors({ error: "required" });
    }
    if (!this.dataPagamenti.value) {
      this.hasError = true;
      this.dataPagamenti.setErrors({ error: "required" });
    } else if ((new Date(this.dataPagamenti.value)) > this.dataYesterday) {
      this.hasError = true;
      this.dataPagamenti.setErrors({ error: "maggioreYesterday" });
    } else if ((new Date(this.dataPagamenti.value)) < this.minData) {
      this.hasError = true;
      this.dataPagamenti.setErrors({ error: "minoreMinData" });
    }
    if (!this.dataValidazioni.value) {
      this.hasError = true;
      this.dataValidazioni.setErrors({ error: "required" });
    } else if ((new Date(this.dataValidazioni.value)) > this.dataYesterday) {
      this.hasError = true;
      this.dataValidazioni.setErrors({ error: "maggioreYesterday" });
    } else if ((new Date(this.dataValidazioni.value)) < this.minData) {
      this.hasError = true;
      this.dataValidazioni.setErrors({ error: "minoreMinData" });
    }
    if (!this.dataFideiussioni.value) {
      this.hasError = true;
      this.dataFideiussioni.setErrors({ error: "required" });
    } else if ((new Date(this.dataFideiussioni.value)) > this.dataYesterday) {
      this.hasError = true;
      this.dataFideiussioni.setErrors({ error: "maggioreYesterday" });
    } else if ((new Date(this.dataFideiussioni.value)) < this.minData) {
      this.hasError = true;
      this.dataFideiussioni.setErrors({ error: "minoreMinData" });
    }
    if (!this.dataErogazioni.value) {
      this.hasError = true;
      this.dataErogazioni.setErrors({ error: "required" });
    } else if ((new Date(this.dataErogazioni.value)) > this.dataYesterday) {
      this.hasError = true;
      this.dataErogazioni.setErrors({ error: "maggioreYesterday" });
    } else if ((new Date(this.dataErogazioni.value)) < this.minData) {
      this.hasError = true;
      this.dataErogazioni.setErrors({ error: "minoreMinData" });
    }
    if ((new Date(this.dataValidazioni.value)) < (new Date(this.dataPagamenti.value))) {
      this.hasError = true;
      this.dataValidazioni.setErrors({ error: "minoreDataPagamenti" });
    }

    if (this.hasError) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    } else {
      this.isValidated = true;
      this.showMessageSuccess("I parametri di elaborazione risultano corretti.");
    }
  }

  creaAnteprimaProposta() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedCrea = false;
    let proposta = new PropostaCertificazioneDTO();
    proposta.idLineaDiIntervento = this.lineaDiInterventoSelected.idLineaDiIntervento;
    proposta.dataPagamenti = this.dataPagamenti.value;
    proposta.dataValidazioni = this.dataValidazioni.value;
    proposta.dataFideiussioni = this.dataFideiussioni.value;
    proposta.dataErogazioni = this.dataErogazioni.value;
    proposta.descProposta = this.descrizione;
    proposta.isBozza = this.isBozza;
    this.subscribers.creaProposta = this.certificazioneService.creaAnteprimaPropostaCertificazione(
      new CreaPropostaRequest(proposta, this.lineeIntervento.map(l => new CodiceDescrizione(l.idLineaDiIntervento.toString(), l.descLinea)))).subscribe(data => {
        if (data) {
          if (data.esito) {
            if (data.params && data.params[0]) {
              this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_CERTIFICAZIONE_CREA_PROPOSTA + "/creaProposta/" + data.params[0],
              { idLineaDiIntervento: this.lineaDiInterventoSelected.idLineaDiIntervento, descLinea: this.lineaDiInterventoSelected.descLinea, isBozza: this.isBozza ? 'S' : 'N' }]);
            }
          } else {
            this.showMessageError(data.msg);
          }
        }
        this.loadedCrea = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
  }

  indietro() {
    this.isValidated = false;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
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

  isLoading() {
    if (!this.loadedLineeIntervento || !this.loadedCrea) {
      return true;
    }
    return false;
  }

}
