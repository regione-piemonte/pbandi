/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { SchedaClienteMain } from '../../commons/dto/scheda-cliente-main';
import { BehaviorSubject, Observable } from 'rxjs';
import { SaveSchedaCliente } from '../../commons/dto/save-scheda-cliente.all';
import { LiberazioneGaranteDTO } from '../../commons/dto/liberazione-garante-dto';
import { DatePipe } from '@angular/common';
import { BoxSaldoStralcioVO } from '../../commons/dto/box-attivita-istruttore/box-saldo-stralcio-VO';
import { InitBoxSaldoStralcioVO } from '../../commons/dto/box-attivita-istruttore/init-box-saldo-stralcio-VO';


@Injectable({
  providedIn: 'root'
})
export class BoxAttivitaIstruttoreService {

  public noDataLabelCommonText: string = "Nessun dato presente."
  public validatorRequiredCommonText: string = "Valore obbligatorio."
  public validatorPatternCommonText: string = "Valore non valido."
  public currencyValidatorMaxlengthCommonText: string = "Il valore non può essere superiore a 13 cifre."
  public noteValidatorMaxlengthCommonText: string = "Le note non possono superare i 4.000 caratteri."

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
    private datePipe: DatePipe
  ) { }

  /*private spinSync = new BehaviorSubject<boolean>(false);
  spinState = this.spinSync.asObservable();

  setSpinner(state: boolean) {
    this.spinSync.next(state);
  }

  jsonModifiche: string;*/


  // SALDO E STRALCIO //

  getSaldoStralcio(idProgetto: number, idModalitaAgevolazione: number){
    let url = this.configService.getApiURL() + '/restfacade/boxAttivitaIstruttore/getSaldoStralcio';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());

    return this.http.get<Array<BoxSaldoStralcioVO>>(url, { params: params });
  }

  initDialogSaldoStralcio(){
    let url = this.configService.getApiURL() + '/restfacade/boxAttivitaIstruttore/initSaldoStralcio';
    return this.http.get<InitBoxSaldoStralcioVO>(url);
  }
}
