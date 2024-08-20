/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { FinanziamentoErogato } from '../commons/dto/finanziamento-erogato';
import { DettaglioFinanziamentoErogato } from '../commons/dto/dettaglio-finanziamento-erogato';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap, map } from 'rxjs/operators';
import { of } from 'rxjs';
import { RevocaAmministrativaDTO } from '../commons/dto/revoca-amministrativa-dto';

@Injectable({
  providedIn: 'root'
})
export class RicBenResponseService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
  ) { }


  finEro: FinanziamentoErogato;
  dettFinEro: DettaglioFinanziamentoErogato;
  private finEroInfo = new BehaviorSubject<FinanziamentoErogato>(null);
  finEroInfo$: Observable<FinanziamentoErogato> = this.finEroInfo.asObservable();

  cercaBeneficiari(codiceFiscale = "", nag = "", partitaIva = "", denominazione = "", descBando = "", codiceProgetto = ""): Observable<Array<FinanziamentoErogato>> {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getElencoBeneficiari';

    let params = new HttpParams()
      .set("codiceFiscale", codiceFiscale.toString())
      .set("nag", nag.toString())
      .set("partitaIva", partitaIva.toString())
      .set("denominazione", denominazione.toString())
      .set("descBando", descBando.toString())
      .set("codiceProgetto", codiceProgetto.toString())


    return this.http.get<Array<FinanziamentoErogato>>(url, { params: params })/*.subscribe(data => {
      if (data) {
        this.finEro = data;
        this.finEroInfo.next(data);

        //console.log(data);

      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });*/
  }

  getCreditoResiduoEAgevolazione(idProgetto: string, idBando: string, idModalitaAgevolazioneOrig: number, idModalitaAgevolazioneRif: number) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getCreditoResiduoEAgevolazione';

    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idBando", idBando.toString())
      .set("idModalitaAgevolazioneOrig", idModalitaAgevolazioneOrig.toString())
      .set("idModalitaAgevolazioneRif", idModalitaAgevolazioneRif.toString())

    return this.http.get<any>(url, { params: params });
  }


  // One service to rule them all
  getSuggestion(value: string, id: number) {

    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getSuggestion';

    let params = new HttpParams().set("value", value.toString().toUpperCase()).set("id", id.toString());

    return this.http.get<any>(url, { params: params });
  }

  getRevocaAmministrativa(idProgetto: string, idModalitaAgevolazione: number, idModalitaAgevolazioneRif: number, revoche: string) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getRevocaAmministrativa';

    let params = new HttpParams()
    .set("idProgetto", idProgetto.toString())
    .set("idModalitaAgevolazione", idModalitaAgevolazione.toString())
    .set("idModalitaAgevolazioneRif", idModalitaAgevolazioneRif.toString())
    .set("revoche", revoche.toString());

    return this.http.get<Array<RevocaAmministrativaDTO>>(url, { params: params });
  }


  /*
  @DefaultValue("0") @QueryParam("") Long idUtente,
	@DefaultValue("0") @QueryParam("") Long idProgetto,
	@DefaultValue("0") @QueryParam("") String codiceVisualizzato,
	@DefaultValue("0") @QueryParam("") Long ndg,
	@DefaultValue("0") @QueryParam("") Long idAgevolazione,
  */

  getEstrattoConto(idUtente: string, idProgetto: string, codiceVisualizzato: string, ndg: number, idAgevolazione: number, idAgevolazionerif: number) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getEstrattoConto';

    let params = new HttpParams()
      .set("idUtente", idUtente.toString())
      .set("idProgetto", idProgetto.toString())
      .set("codiceVisualizzato", codiceVisualizzato.toString())
      .set("ndg", ndg.toString())
      .set("idAgevolazione", idAgevolazione.toString())
      .set("idAgevolazioneRif", idAgevolazionerif.toString())
    return this.http.get<any>(url, { params: params });
  }

  getPianoAmmortamento(idUtente: string, idProgetto: string, codiceVisualizzato: string, ndg: number, idAgevolazione: number, idAgevolazionerif: number) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getPianoAmmortamento';
    let params = new HttpParams()
      .set("idUtente", idUtente.toString())
      .set("idProgetto", idProgetto.toString())
      .set("codiceVisualizzato", codiceVisualizzato.toString())
      .set("ndg", ndg.toString())
      .set("idAgevolazione", idAgevolazione.toString())
      .set("idAgevolazioneRif", idAgevolazionerif.toString())
    return this.http.get<any>(url, { params: params });
  }


}
