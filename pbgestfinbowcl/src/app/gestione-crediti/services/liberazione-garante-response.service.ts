/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { SchedaClienteMain } from '../commons/dto/scheda-cliente-main';
import { BehaviorSubject, Observable } from 'rxjs';
import { SaveSchedaCliente } from '../commons/dto/save-scheda-cliente.all';
import { LiberazioneGaranteDTO } from '../commons/dto/liberazione-garante-dto';
import { DatePipe } from '@angular/common';


@Injectable({
  providedIn: 'root'
})
export class LiberazioneGaranteService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
    private datePipe: DatePipe
  ) { }


  liberazioneGarante: LiberazioneGaranteDTO;
  private LiberazioneGaranteInfo = new BehaviorSubject<LiberazioneGaranteDTO>(null);
  LiberazioneGaranteInfo$: Observable<LiberazioneGaranteDTO> = this.LiberazioneGaranteInfo.asObservable();

  getLiberazioneGarante(progetto = "") {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getLiberazioneGarante';

    let params = new HttpParams().set("idProgetto", progetto);

    return this.http.get<LiberazioneGaranteDTO>(url, { params: params }).subscribe(data => {
      if (data) {
        this.liberazioneGarante = data;
        /*
        let dataLiberazione: any;
        dataLiberazione = this.datePipe.transform(this.liberazioneGarante.dataLiberazione, "yyyy-MM-dd");
        this.liberazioneGarante = dataLiberazione;
        */
        this.LiberazioneGaranteInfo.next(data);

        //console.log(data);
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });

  }



  jsonModifiche: string;

  setLiberazioneGarante(NewLiberazioneGarante: LiberazioneGaranteDTO) {

    //console.log("SERVICE: ", NewLiberazioneGarante);

    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setLiberazioneGarante';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    this.jsonModifiche = JSON.stringify(NewLiberazioneGarante);

    return this.http.post<String>(url, this.jsonModifiche, { headers }).subscribe(data => {
      if (data) {
        return true;
      }
    }, err => {this.handleExceptionService.handleBlockingError(err);});
  }
}
