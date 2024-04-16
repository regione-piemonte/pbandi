/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { CambiaStatoNotificaRequest } from "../commons/requests/cambia-stato-notifica-request";
import { NotificaRequest } from "../commons/requests/notifica-request";
import { AttivitaVO } from "../commons/vo/attivita-vo";
import { BandoVO } from "../commons/vo/bando-vo";
import { PbandiTNotificaProcessoVO } from "../commons/vo/pbandi-t-notifica-processo-vo";
import { ProgettoVO } from "../commons/vo/progetto-vo";
import { RicercaAttivitaPrecedenteDTO } from "../commons/vo/ricerca-attivita-precedente-dto";

@Injectable()
export class AttivitaService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
  ) { }

  getBandi(idBeneficiario: number): Observable<Array<BandoVO>> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/bandi";
    let params = new HttpParams().set("idBeneficiario", idBeneficiario.toString());
    return this.http.get<Array<BandoVO>>(url, { params: params });
  }

  getBandoByProgr(progrBandoLineaIntervento: number): Observable<BandoVO> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/bandoByProgr";
    let params = new HttpParams().set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString());
    return this.http.get<BandoVO>(url, { params: params });
  }

  getProgetti(idBeneficiario: number, progrBandoLineaIntervento: number): Observable<Array<ProgettoVO>> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/progetti";
    let params = new HttpParams().set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString()).set("idBeneficiario", idBeneficiario.toString());
    return this.http.get<Array<ProgettoVO>>(url, { params: params });
  }

  getAttivita(idBeneficiario: number, progrBandoLineaIntervento: number, descrAttivita: string, idProgetto: number, start: number): Observable<Array<AttivitaVO>> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/getAttivita";
    let params = new HttpParams()
      .set("idBeneficiario", idBeneficiario.toString())
      .set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString())
      .set("descrAttivita", descrAttivita)
      .set("start", start.toString())
      .set("idProgetto", idProgetto != null ? idProgetto.toString() : null);
    return this.http.get<Array<AttivitaVO>>(url, { params: params });
  }

  getNotifiche(progrBandoLineaIntervento: number, idProgetto: number, elencoPrj: Array<ProgettoVO>): Observable<Array<PbandiTNotificaProcessoVO>> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/getNotifiche";
    let request = new NotificaRequest(progrBandoLineaIntervento, idProgetto, elencoPrj);
    return this.http.put<Array<PbandiTNotificaProcessoVO>>(url, request);
  }

  countNotifiche(progrBandoLineaIntervento: number, idProgetto: number, elencoPrj: Array<ProgettoVO>): Observable<Array<number>> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/countNotifiche";
    let request = new NotificaRequest(progrBandoLineaIntervento, idProgetto, elencoPrj);
    return this.http.put<Array<number>>(url, request);
  }

  cambiaStatoNotifica(idNotifica: number, statoNotifica: string): Observable<boolean> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/cambiaStatoNotifica";
    let request = new CambiaStatoNotificaRequest(idNotifica, statoNotifica, null);
    return this.http.post<boolean>(url, request);
  }

  cambiaStatoNotificheSelezionate(idNotificheArray: Array<number>, statoNotifica: string): Observable<boolean> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/cambiaStatoNotificheSelezionate";
    let request = new CambiaStatoNotificaRequest(null, statoNotifica, idNotificheArray);
    return this.http.post<boolean>(url, request);
  }

  startAttivita(idProgetto: number, descBreveTask: string) {
    let url = this.configService.getApiURL() + "/restfacade/attivita/startAttivita";
    let params = new HttpParams().set("descBreveTask", descBreveTask).set("idProgetto", idProgetto.toString());
    return this.http.get<Array<AttivitaVO>>(url, { params: params });
  }

  ricercaAttivitaPrecedente(idBeneficiario: number): Observable<RicercaAttivitaPrecedenteDTO> {
    let url = this.configService.getApiURL() + "/restfacade/attivita/ricercaAttivitaPrecedente";
    let params = new HttpParams().set("idBeneficiario", idBeneficiario.toString());
    return this.http.get<RicercaAttivitaPrecedenteDTO>(url, { params: params });
  }

}
