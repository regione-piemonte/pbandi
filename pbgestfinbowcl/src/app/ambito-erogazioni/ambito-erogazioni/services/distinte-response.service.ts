/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { DistintaVO } from '../commons/distinta-vo';
import { Observable } from 'rxjs';
import { FiltroProposteErogazioneDistVO } from '../commons/filtro-proposte-erogazione-dist-vo';
import { DistintaPropostaErogazioneVO } from '../commons/distinta-proposta-erogazione-vo';
import { DettaglioDistintaVO } from '../commons/dettaglio-distinta-vo';
import { BandoSuggestVO } from '../commons/bando-suggest-vo';
import { AgevolazioneSuggestVO } from '../commons/agevolazione-suggest-vo';
import { DenominazioneSuggestVO } from '../commons/denominazione-suggest-vo';
import { ProgettoSuggestVO } from '../commons/progetto-suggest-vo';
import { DatiDistintaVO } from '../commons/dati-distinta-vo';
import { RicercaDistintaPropErogVO } from '../commons/ricerca-distinta-prop-erog-vo';
import { RicercaDistintaPropErogPlusVO } from '../commons/ricerca-distinta-prop-erog-plus-vo';
import { AllegatoVO } from '../commons/allegato-vo';
import { report } from 'process';
import { SuggestIdDescVO } from 'src/app/revoche/commons/suggest-id-desc-vo';

@Injectable({
  providedIn: 'root'
})
export class DistinteResponseService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  /**********************************
  *****  CARICAMENTO DISTINTE  ******
  **********************************/

  suggestBando(value: string): Observable<Array<BandoSuggestVO>> {

    console.log("Sono nel suggest Bando")

    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestBando';

    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())

    return this.http.get<Array<BandoSuggestVO>>(url, { params: params });

  }

  suggestAgevolazione(value: string): Observable<Array<AgevolazioneSuggestVO>> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestAgevolazione';

    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())

    return this.http.get<Array<AgevolazioneSuggestVO>>(url, { params: params });

  }

  suggestDistinta(value: string): Observable<Array<DistintaVO>> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestDistinta';

    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())

    return this.http.get<Array<DistintaVO>>(url, { params: params });

  }

  suggestCodiceFondoFinpis(value: string): Observable<Array<SuggestIdDescVO>> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestCodiceFondoFinpis';

    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())

    return this.http.get<Array<SuggestIdDescVO>>(url, { params: params });

  }

  creaDistinta(idBando: string, idModalitaAgevolazione: string): Observable<boolean> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/nuovaDistinta';

    let params = new HttpParams()
      .set("idBando", idBando.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());

    return this.http.get<boolean>(url, { params: params });

  }

  copiaDistinta(distinta: string): Observable<boolean> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/existsDistinta';

    let params = new HttpParams()
      .set("idDistinta", distinta.toString())

    return this.http.get<boolean>(url, { params: params });

  }


  /**********************************
  ********  CREA DISTINTE  *********
  **********************************/
  // tutte le info per il secondo component. Marti
  fetchData(idBando: string, idAgevolazione: string): Observable<DettaglioDistintaVO> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/getNuovaDistinta';

    let params = new HttpParams()
      .set("idBando", idBando.toString())
      .set("idModalitaAgevolazione", idAgevolazione.toString())

    return this.http.get<DettaglioDistintaVO>(url, { params: params });

  }

  suggestDenominazione(value: string): Observable<Array<DenominazioneSuggestVO>> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestDenominazione';

    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())

    return this.http.get<Array<DenominazioneSuggestVO>>(url, { params: params });

  }

  suggestProgetto(idBando: string, value: string): Observable<Array<ProgettoSuggestVO>> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestProgetto';

    let params = new HttpParams()
      .set("idBando", idBando.toString().toUpperCase())
      .set("value", value.toString().toUpperCase())

    return this.http.get<Array<ProgettoSuggestVO>>(url, { params: params });

  }

  /*
  getListaBancaTesoriera(): Observable<Array<string>> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/fetchData';

    let params = new HttpParams()

    return this.http.get<Array<string>>(url, { params: params });
  }
  */

  getProposteErogazione(listInput: FiltroProposteErogazioneDistVO): Observable<Array<DistintaPropostaErogazioneVO>> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/getProposteErogazione';

    let params = new HttpParams()
    //.set("filtroProposteErogazioneDistVO", listInput.toString())

    return this.http.post<Array<DistintaPropostaErogazioneVO>>(url, listInput, { params: params });

  }

  /**********************************
  ********  COPIA DISTINTE  *********
  **********************************/

  /* @GET
@Path("/suggestDistintaRespinta")
@Produces(MediaType.APPLICATION_JSON)
  Response suggestDistintaRespinta(
  @DefaultValue("") @QueryParam("value") Long value,
  @Context HttpServletRequest req) throws Exception; */

  suggestDistintaRespinta(value: string) {
    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestDistintaRespinta';

    let params = new HttpParams()
      .set("value", value?.toString().toUpperCase())

    return this.http.get<any>(url, { params: params });
  }

  fetchDataCopiaDistinte(idDistinta: string): Observable<DettaglioDistintaVO> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/copiaDistinta';

    let params = new HttpParams()
      .set("idDistinta", idDistinta.toString())

    return this.http.get<DettaglioDistintaVO>(url, { params: params });

  }

  checkAllegati(idDistinta: string): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/checkAllegati';
    let params = new HttpParams()
    .set("idDistinta", idDistinta.toString())

    return this.http.get<boolean>(url, { params: params });
  }

  uploadFile(file: File, visibilita: boolean, idTipoDocumentoIndex: number, idDistinta: string, idProgetto: number): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/salvaAllegato';
    let formData = new FormData();

    formData.append("file", file, file.name);
    formData.append("visibilita", visibilita ? "S" : "N");
    formData.append("nomeFile", file.name);
    formData.append("idTipoDocumentoIndex", idTipoDocumentoIndex.toString());
    formData.append("idDistinta", idDistinta.toString());
    formData.append("idProgetto", idProgetto.toString());

    return this.http.post<boolean>(url, formData);

  }

  updateVisibilita(idDocumentoIndex: number, visibilita: boolean): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/updateVisibilita';
    let params = new HttpParams()
    .set("idDocumentoIndex", idDocumentoIndex.toString())
    .set("visibilita", visibilita ? "S" : "N")

    return this.http.get<boolean>(url, { params: params });
  }

  deleteFile(idDocumentoIndex: number): Observable<boolean> {

    let url = this.configService.getApiURL() + '/restfacade/distinte/eliminaAllegato';

    let params = new HttpParams()
      .set("idDocumentoIndex", idDocumentoIndex.toString())

    return this.http.delete<boolean>(url, { params: params });

  }

  salvaInBozza(parametro: DatiDistintaVO): Observable<string> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/salvaInBozza';

    return this.http.post<string>(url, parametro);
  }

  isAbilitatoAvvioIter(): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/isAbilitatoAvvioIter';

    return this.http.get<boolean>(url);
  }


  /**********************************
  *******  ELENCO DISTINTE  *********
  **********************************/

  suggestBeneficiario(value: string): Observable<Array<DenominazioneSuggestVO>> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestDenominazione';

    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())

    return this.http.get<Array<DenominazioneSuggestVO>>(url, { params: params });
  }
  
  suggestBandoElencoDistinte(value: string): Observable<Array<BandoSuggestVO>> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestBando';

    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())

    return this.http.get<Array<BandoSuggestVO>>(url, { params: params });
  }
  
  suggestAgevElencoDistinte(value: string): Observable<Array<AgevolazioneSuggestVO>> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestAgevolazione';

    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())

    return this.http.get<Array<AgevolazioneSuggestVO>>(url, { params: params });
  }

  suggestProgettoElencoDistinte(value: string): Observable<Array<ProgettoSuggestVO>> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/suggestProgetto';

    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())

    return this.http.get<Array<ProgettoSuggestVO>>(url, { params: params });
  }

  applicaFiltri(obj: any): Observable<Array<RicercaDistintaPropErogVO>> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/filterDistinte';

    return this.http.post<Array<RicercaDistintaPropErogVO>>(url, obj);
  }

  applicaFiltriPlus(obj: any): Observable<Array<RicercaDistintaPropErogPlusVO>> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/filterDistinte';

    return this.http.post<Array<RicercaDistintaPropErogPlusVO>>(url, obj);
  }


  esportaExcel(idDistinta: any): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/esportaDettaglioDistinta';
    let params = new HttpParams()
      .set('idDistinta', idDistinta)

    return this.http.get(url, { responseType: 'arraybuffer', params });
  }

  fetchDataDettaglioDistinta(idDistinta: string): Observable<DettaglioDistintaVO> { //DettaglioDistintaVO

    let url = this.configService.getApiURL() + '/restfacade/distinte/getDettaglioDistinta';

    let params = new HttpParams()
      .set("idDistinta", idDistinta.toString())

    return this.http.get<DettaglioDistintaVO>(url, { params: params });

  }


  modificaDistinta(idDistinta: string, datiDistintaVO: DatiDistintaVO) {
    let url = this.configService.getApiURL() + '/restfacade/distinte/modificaDistinta';

    let params = new HttpParams()
      .set("idDistinta", idDistinta.toString())

    return this.http.put<boolean>(url, datiDistintaVO, { params: params });
  }

  avviaIter(idDistinta: string) {
    let url = this.configService.getApiURL() + '/restfacade/distinte/avviaIterAutorizzativo';

    let params = new HttpParams()
      .set("idDistinta", idDistinta.toString())

    return this.http.get<string>(url, { params: params });
  }
}
