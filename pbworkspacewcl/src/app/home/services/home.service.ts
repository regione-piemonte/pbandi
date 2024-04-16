/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { HandleExceptionService } from "src/app/core/services/handle-exception.service";
import { ConsensoInformatoDTO } from "../commons/vo/consenso-informato-dto";
import { OperazioneVO } from "../commons/vo/operazione-vo";

@Injectable()
export class HomeService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService
  ) { }
  op: Array<OperazioneVO>;
  private operazioni = new BehaviorSubject<Array<OperazioneVO>>(null);
  operazioni$: Observable<Array<OperazioneVO>> = this.operazioni.asObservable();

  getOperazioni() {
    let url = this.configService.getApiURL() + "/restfacade/home/operazioni";
    this.http.get<Array<OperazioneVO>>(url).subscribe(data => {
      if (data) {
        this.op = data;
        this.operazioni.next(data);
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  trovaConsensoInvioComunicazione(): Observable<ConsensoInformatoDTO> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/trovaConsensoInvioComunicazione";
    return this.http.get<ConsensoInformatoDTO>(url);
  }

  salvaConsensoInvioComunicazione(emailConsenso: string, flagConsensoMail: string): Observable<number> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/salvaConsensoInvioComunicazione";
    let params = new HttpParams().set('flagConsensoMail', flagConsensoMail);
    if (emailConsenso && emailConsenso.length > 0) {
      params = params.set('emailConsenso', emailConsenso);
    }
    return this.http.get<number>(url, { params: params });
  }

}
