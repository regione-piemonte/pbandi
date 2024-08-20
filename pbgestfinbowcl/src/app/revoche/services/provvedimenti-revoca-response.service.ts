/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { FiltroProvvedimentoRevocaVO } from '../commons/provvedimenti-revoca-dto/filtro-provvedimento-revoca-vo';
import { Observable } from 'rxjs';
import { ProvvedimentoRevocaTableVO } from '../commons/provvedimenti-revoca-dto/provvedimento-revoca-table-vo';
import { ProvvedimentoRevocaVO } from '../commons/provvedimenti-revoca-dto/provvedimento-revoca-vo';
import { DocumentoRevocaVO } from '../commons/procedimenti-revoca-dto/documento-revoca-vo';
import { SuggestIdDescVO } from '../commons/suggest-id-desc-vo';

@Injectable({
  providedIn: 'root'
})
export class ProvvedimentiRevocaResponseService {

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { }

  cercaProvvedimento(filtroProvvedimentoRevocaVO: FiltroProvvedimentoRevocaVO): Observable<Array<ProvvedimentoRevocaTableVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getProvvedimentoRevoca';
    return this.http.post<Array<ProvvedimentoRevocaTableVO>>(url, filtroProvvedimentoRevocaVO);
  }

  /*
  //Dettaglio Provvedimento di Revoca
    @DELETE
    @Path("/eliminaProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response eliminaProvvedimentoRevoca(
            @QueryParam("numeroRevoca") Long numeroRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  eliminaProvvedimentoRevoca(numeroRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/eliminaProvvedimentoRevoca';
    let params = new HttpParams()
      .set("numeroRevoca", numeroRevoca.toString())
    return this.http.delete<any>(url, { params: params });
  }


  /*
  //Dettaglio Provvedimento di Revoca
    @GET
    @Path("/getDettaglioProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDettaglioProvvedimentoRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  getDettaglioProvvedimentoRevoca(numeroGestioneRevoca: string): Observable<ProvvedimentoRevocaVO> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getDettaglioProvvedimentoRevoca';
    let params = new HttpParams()
      .set("numeroGestioneRevoca", numeroGestioneRevoca.toString())
    return this.http.get<ProvvedimentoRevocaVO>(url, { params: params });
  }

  /*
    @GET
    @Path("getDocumentiProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDocumentiProvvedimentoRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  getDocumentiProvvedimentoRevoca(numeroGestioneRevoca: string): Observable<Array<DocumentoRevocaVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getDocumentiProvvedimentoRevoca';
    let params = new HttpParams()
      .set("numeroGestioneRevoca", numeroGestioneRevoca.toString())
    return this.http.get<Array<DocumentoRevocaVO>>(url, { params: params });
  }

  /*
  @GET
    @Path("/getModalitaRecupero")
    @Produces(MediaType.APPLICATION_JSON)
    Response getModalitaRecupero(
            @Context HttpServletRequest req);
  */
  getModalitaRecupero(numeroGestioneRevoca: string): Observable<Array<SuggestIdDescVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getModalitaRecupero';
    let params = new HttpParams()
      .set("numeroGestioneRevoca", numeroGestioneRevoca.toString())
    return this.http.get<Array<SuggestIdDescVO>>(url, { params: params });
  }

  /*
  @GET
    @Path("/getMotivoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getMotivoRevoca(
            @Context HttpServletRequest req);
  */
  getMotivoRevoca(numeroGestioneRevoca: string): Observable<Array<SuggestIdDescVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getMotivoRevoca';
    let params = new HttpParams()
      .set("numeroGestioneRevoca", numeroGestioneRevoca.toString())
    return this.http.get<Array<SuggestIdDescVO>>(url, { params: params });
  }

  /*
  //Modifica Provvedimento di Revoca
    @PUT
    @Path("modificaProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response modificaProvvedimentoRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            ProvvedimentoRevocaVO provvedimentoRevocaVO,
            @Context HttpServletRequest req) throws Exception;
  */
  modificaProvvedimentoRevoca(numeroGestioneRevoca: number, provvedimentoRevoca: ProvvedimentoRevocaVO): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/modificaProvvedimentoRevoca';
    let params = new HttpParams()
      .set("numeroGestioneRevoca", numeroGestioneRevoca.toString())
    return this.http.put<any>(url, provvedimentoRevoca, { params: params });
  }





  /******************
   ***** CDU 15 *****
   ******************/
  /*
     @GET
      @Path("abilitaEmettiProvvedimento")
      @Produces(MediaType.APPLICATION_JSON)
      Response abilitaEmettiProvvedimento(
              @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
              @Context HttpServletRequest req) throws Exception;
   */
  /*
  tu quanto schiacci su emettiProvvedimento chiami questo metodo, se questo metodo restituisce true apri il popup
  per inserire i gg e altre robe senno mostri l'errore all'utente, perchè significa che non ha finito di compilare
  il provvedimento di revoca e ti restituisco ko con il messaggio dell'errore
 */
  abilitaEmettiProvvedimento(numeroGestioneRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/abilitaEmettiProvvedimento';
    let params = new HttpParams()
      .set("numeroGestioneRevoca", numeroGestioneRevoca.toString())
    return this.http.get<any>(url, { params: params });
  }

  /*
    @POST
      @Path("/emettiProvvedimentoRevoca")
      @Consumes(MediaType.MULTIPART_FORM_DATA)
      @Produces(MediaType.APPLICATION_JSON)
      Response emettiProvvedimentoRevoca(
              @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
              @QueryParam("giorniScadenza") Long giorniScadenza,
              @Context HttpServletRequest req) throws Exception;
   */
  emettiProvvedimentoRevoca(numeroGestioneRevoca: number, giorniScadenza: number): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/emettiProvvedimentoRevoca';
    let params = new HttpParams()
      .set("numeroGestioneRevoca", numeroGestioneRevoca.toString())
      .set("giorniScadenza", giorniScadenza.toString())

    return this.http.post<any>(url, null, { params: params });
  }

  /*
  @POST
    @Path("/confermaProvvedimentoRevoca")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response confermaProvvedimentoRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  confermaProvvedimentoRevoca(numeroGestioneRevoca: number): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/confermaProvvedimentoRevoca';
    let params = new HttpParams()
      .set("numeroGestioneRevoca", numeroGestioneRevoca.toString())

    return this.http.post<any>(url, null, { params: params });
  }

  /*
  @POST
    @Path("/ritiroInAutotutelaRevoca")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response ritiroInAutotutelaRevoca(
            @QueryParam("numeroGestioneRevoca") Long numeroGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  ritiroInAutotutelaRevoca(numeroGestioneRevoca: number): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/ritiroInAutotutelaRevoca';
    let params = new HttpParams()
      .set("numeroGestioneRevoca", numeroGestioneRevoca.toString())

    return this.http.post<any>(url, null, { params: params });
  }

  //GESTIONE ALLEGATI

  deleteAllegato(idDocumentoIndex:number){
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/eliminaAllegato';
    let params = new HttpParams()
      .set("idDocumentoIndex", idDocumentoIndex.toString())
    return this.http.post<any>(url, null, { params: params });   
  }

  aggiungiAllegatoProvvedimento(file: File, letteraAccompagnatoria: string, ambito:string, numeroRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/aggiungiAllegatoProvvedimento';
    let params = new HttpParams()
      .set("numeroRevoca", numeroRevoca.toString())

    let formData = new FormData();

    formData.append("allegato", file, file.name);
    formData.append("nomeAllegato", file.name);
    formData.append("letteraAccompagnatoria", letteraAccompagnatoria);    //"letteraAccompagnatoria" o "altro"
    formData.append("ambito", ambito); //"emissione" o "conferma" o "ritiro"
    return this.http.post<any>(url, formData, { params: params });
  }

}
