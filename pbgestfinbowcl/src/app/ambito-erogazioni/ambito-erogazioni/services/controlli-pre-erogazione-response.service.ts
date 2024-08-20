/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { Observable } from 'rxjs';
import { BloccoVO } from 'src/app/gestione-anagrafica/commons/dto/bloccoVO';
import { ControlliDeggendorfVO } from '../commons/controlli-deggendorf-vo';
import { DatiPreErogazioneVO } from '../commons/dati-pre-erogazione-vo';
import { ControlliAntimafiaVO } from '../commons/controlli-antimafia-vo';
import { ControlliDurcVO } from '../commons/controlli-durc-vo';
import { DistintaInterventiVO } from '../commons/distinta-interventi-vo';
import { DestinatarioInterventoSostVO } from '../commons/destinatario-intervento-sost-vo';


@Injectable({
  providedIn: 'root'
})
export class ControlliPreErogazioneResponseService {

  constructor(private http: HttpClient,
    private configService: ConfigService
  ) { }

  fetchData(idProposta: string): Observable<DatiPreErogazioneVO> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/fetchData';

    let params = new HttpParams()
      .set("idProposta", idProposta.toString())

    return this.http.get<DatiPreErogazioneVO>(url, { params: params })
  }

  salvaImportoDaErogare(idProposta: string, importoDaErogare: number) : Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/salvaImportoDaErogare';

    let params = new HttpParams()
    .set("idProposta", idProposta.toString())
    .set("importoDaErogare", importoDaErogare.toString())

    return this.http.get<boolean>(url, { params: params })
  }

  inviaRichiesta(
    idTipoRichiesta: number,
    numeroDomanda: string,
    flagUrgenza: string,
    codiceFiscale: string,
    codiceBando: string,
    codiceProgetto: string
  ): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/inviaRichiesta';

    let params = new HttpParams()

    let oggJson = {
      "idTipoRichiesta": idTipoRichiesta.toString(),
      "numeroDomanda": numeroDomanda.toString(),
      "flagUrgenza": flagUrgenza.toString(),
      "codiceFiscale": codiceFiscale.toString(),
      "codiceBando": codiceBando.toString(),
      "codiceProgetto": codiceProgetto.toString()
    }


    return this.http.post<boolean>(url, oggJson, { params: params })
  }

  getBloccoSoggetto(idSoggetto: string): Observable<Array<BloccoVO>> {

    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/getBloccoSoggetto';

    let params = new HttpParams()
      .set("idSoggetto", idSoggetto.toString())

    return this.http.get<Array<BloccoVO>>(url, { params: params })
  }

  getBloccoDomanda(idSoggetto: string, numeroDomanda: string): Observable<Array<BloccoVO>> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/getBloccoDomanda';

    let params = new HttpParams()
      .set("idSoggetto", idSoggetto.toString())
      .set("numeroDomanda", numeroDomanda.toString())

    return this.http.get<Array<BloccoVO>>(url, { params: params })
  }

  getEsitoAntimafia(idProgetto: string): Observable<ControlliAntimafiaVO> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/getEsitoAntimafia';

    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())

    return this.http.get<ControlliAntimafiaVO>(url, { params: params })

  }

  getEsitoDurc(idProgetto: string): Observable<ControlliDurcVO> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/getEsitoDurc';

    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())

    return this.http.get<ControlliDurcVO>(url, { params: params })

  }

  getEsitoDeggendorf(idSoggetto: string): Observable<ControlliDeggendorfVO> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/getEsitoDeggendorf';

    let params = new HttpParams()
      .set("idSoggetto", idSoggetto.toString())

    return this.http.get<ControlliDeggendorfVO>(url, { params: params })
  }

  //ritorna o la motivazione (string) oppure null
  getControlloNonApplicabile(idProposta: string, idTipoRichiesta: number): Observable<string> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/getControlloNonApplicabile';

    let params = new HttpParams()
      .set("idProposta", idProposta.toString())
      .set("idTipoRichiesta", idTipoRichiesta.toString())

    return this.http.get(url, { params: params, responseType: 'text' })
  }

  setControlloNonApplicabile(idProposta: string, idTipoRichiesta: number, motivazione: string | null): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/setControlloNonApplicabile';
    let params = new HttpParams();

    let oggJson = {
      "idProposta": idProposta.toString(),
      "idTipoRichiesta": idTipoRichiesta.toString(),
      "motivazione": motivazione
    }

    return this.http.post<boolean>(url, oggJson, { params: params })
  }

  //tasto conclusivo SALVA
  saveControlliPreErogazione(idProposta: string, idSoggetto: string, esitoDeggendorf: boolean, vercor: string): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/saveControlliPreErogazione';

    let params = new HttpParams()

    let oggJson = {
      "idProposta": idProposta.toString(),
      "idSoggetto": idSoggetto.toString(),
      "esitoDeggendorf": esitoDeggendorf ? true : false,
      "vercor": vercor
    }

    return this.http.post<boolean>(url, oggJson, { params: params })
  }

  avviaIterEsitoValidazione(idProposta: string, letteraAccompagnatoria: File, visibilitaLettera: boolean, reportValidazioneSpesa: File, visibilitaReport: boolean, checklistInterna: File, visibilitaChecklist: boolean): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/avviaIterEsitoValidazione';
    let params = new HttpParams()
      .set("idProposta", idProposta.toString())


    let formData = new FormData();

    formData.append("visibilitaLettera", visibilitaLettera ? "S" : "N");
    formData.append("visibilitaReport", visibilitaReport ? "S" : "N");
    formData.append("visibilitaChecklist", visibilitaChecklist ? "S" : "N");

    //letteraAccompagnatoria
    if (letteraAccompagnatoria) {
      formData.append("letteraAccompagnatoria", letteraAccompagnatoria, letteraAccompagnatoria.name);
    }
    //reportValidazioneSpesa
    if (reportValidazioneSpesa) {
      formData.append("reportValidazioneSpesa", reportValidazioneSpesa, reportValidazioneSpesa.name);
    }
    //checklistInterna
    if (checklistInterna) {
      formData.append("checklistInterna", checklistInterna, checklistInterna.name);
    }

    return this.http.post<any>(url, formData, { params: params });
  }

  avviaIterCommunicazioneIntervento(idProposta: string, letteraAccompagnatoria: File, visibilitaLettera: boolean): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/avviaIterCommunicazioneIntervento';
    let params = new HttpParams()
      .set("idProposta", idProposta.toString())


    let formData = new FormData();

    //letteraAccompagnatoria
    formData.append("visibilitaLettera", visibilitaLettera ? "S" : "N");
    if (letteraAccompagnatoria) {
      formData.append("letteraAccompagnatoria", letteraAccompagnatoria, letteraAccompagnatoria.name);
    }
    
    return this.http.post<any>(url, formData, { params: params });
  }

  getDestinatariIntervento(): Observable<DestinatarioInterventoSostVO[]> {
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/getDestinatariInterventi';
    let params = new HttpParams();
    return this.http.get<any>(url, { params: params });
  }

  creaDistintaInterventi(infoDistinta: DistintaInterventiVO, letteraAccompagnatoria: File, visibilitaLettera: boolean, reportValidazioneSpesa: File, visibilitaReport: boolean, checklistInterna: File, visibilitaChecklist: boolean): Observable<any>{
    let url = this.configService.getApiURL() + '/restfacade/controlliPreErogazione/creaDistintaInterventi';
    let params = new HttpParams();

    let formData = new FormData();
    //Oggetto
    formData.append("infoDistinta", JSON.stringify(infoDistinta));

    formData.append("visibilitaLettera", visibilitaLettera ? "S" : "N");
    formData.append("visibilitaReport", visibilitaReport ? "S" : "N");
    formData.append("visibilitaChecklist", visibilitaChecklist ? "S" : "N");
    //letteraAccompagnatoria
    if (letteraAccompagnatoria) {
      formData.append("letteraAccompagnatoria", letteraAccompagnatoria, letteraAccompagnatoria.name);
    }
    //reportValidazioneSpesa
    if (reportValidazioneSpesa) {
      formData.append("reportValidazioneSpesa", reportValidazioneSpesa, reportValidazioneSpesa.name);
    }
    //checklistInterna
    if (checklistInterna) {
      formData.append("checklistInterna", checklistInterna, checklistInterna.name);
    }
    
    return this.http.post<boolean>(url, formData, { params: params });
  }

}
