/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { FiltroProcedimentoRevocaVO } from '../commons/procedimenti-revoca-dto/filtro-procedimento-revoca-vo';
import { ProcedimentoRevocaTableVO } from '../commons/procedimenti-revoca-dto/procedimento-revoca-table-vo';
import { ProcedimentoRevocaVO } from '../commons/procedimenti-revoca-dto/procedimento-revoca-vo';
import { DocumentoRevocaVO } from '../commons/procedimenti-revoca-dto/documento-revoca-vo';
import { GestioneProrogaVO } from '../commons/procedimenti-revoca-dto/gestione-proroga-vo';

@Injectable({
  providedIn: 'root'
})
export class ProcedimentiRevocaResponseService {

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { }


  cercaProcedimento(filtroProcedimentoRevocaVO: FiltroProcedimentoRevocaVO): Observable<Array<ProcedimentoRevocaTableVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getProcedimentoRevoca';
    return this.http.post<Array<ProcedimentoRevocaTableVO>>(url, filtroProcedimentoRevocaVO);
  }


  /*************************
   ** visualizza modifica **
   ************************/

  //fetch dati procedimento (parametro: idProcedimento)
  /*
  @GET
    @Path("/getDettaglioProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDettaglioProcedimentoRevoca(
            @QueryParam("idProcedimentoRevoca") Long idProcedimentoRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  getProcedimentoRevoca(idProcedimentoRevoca: string): Observable<ProcedimentoRevocaVO> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getDettaglioProcedimentoRevoca';
    let params = new HttpParams()
      .set("idProcedimentoRevoca", idProcedimentoRevoca.toString())
    return this.http.get<ProcedimentoRevocaVO>(url, { params: params });
  }


  //get allegati
  /*
  @GET
    @Path("getDocumentiProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDocumentiProcedimentoRevoca(
            @QueryParam("numeroRevoca") Long numeroRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  getDocumenti(numeroRevoca: string): Observable<Array<DocumentoRevocaVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getDocumentiProcedimentoRevoca';
    let params = new HttpParams()
      .set("numeroRevoca", numeroRevoca?.toString())
    return this.http.get<Array<DocumentoRevocaVO>>(url, { params: params });
  }


  //salva modifiche procedimento
  /*
  @PUT
    @Path("/updateProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response updateProcedimentoRevoca(
            ProcedimentoRevocaVO procedimentoRevocaVO,
            @Context HttpServletRequest req) throws Exception;
  */
  updateProcedimentoRevoca(procedimentoRevocaVO: ProcedimentoRevocaVO): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/updateProcedimentoRevoca';
    let params = new HttpParams()
    //.set("idProcedimentoRevoca", idProcedimentoRevoca.toString())
    return this.http.put<ProcedimentoRevocaVO>(url, procedimentoRevocaVO, { params: params });
  }


  //Delete procedimento
  /*
  @DELETE
    @Path("/eliminaProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response eliminaProcedimentoRevoca(
            @QueryParam Long numeroProcedimentoRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  deleteProcedimentoRevoca(numeroProcedimentoRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/eliminaProcedimentoRevoca';

    let params = new HttpParams()
      .set("numeroProcedimentoRevoca", numeroProcedimentoRevoca.toString())

    let headers = new HttpHeaders().set('Content-Type', 'application/json');

    return this.http.delete<any>(url, { params: params });
  }


  //Verifica importi
  /*
  @GET
     @Path("/verificaImporti")
     @Produces(MediaType.APPLICATION_JSON)
     Response verificaImporti(
             @QueryParam Long idGestioneRevoca,
             @Context HttpServletRequest req) throws Exception;
  */
  verificaImporti(idGestioneRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/verificaImporti';
    let params = new HttpParams()
      .set("idGestioneRevoca", idGestioneRevoca.toString())
    return this.http.get<any>(url, { params: params });
  }



  /*
  //Gestione Procedimento di Revoca
    @POST
    @Path("/aggiungiAllegatoProcedimento")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response aggiungiAllegatoProcedimento(
            @QueryParam Long idGestioneRevoca,
            MultipartFormDataInput multipartFormDataInput,
            @Context HttpServletRequest req) throws Exception;
  */
  /*
  //quello che contiene il multipart: (File, string, boolean);
   File allegato = multipartFormDataInput.getFormDataPart("allegato", File.class, null);
   String nomeAllegato = multipartFormDataInput.getFormDataPart("nomeAllegato", String.class, null);
   String letteraAccompagnatoria = multipartFormDataInput.getFormDataPart("letteraAccompagnatoria", String.class, null);
  */
  aggiungiAllegatoProcedimento(file: File, letteraAccompagnatoria: string, allegatoIntegrazione: string, allegatoArchiviazione:string, idGestioneRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/aggiungiAllegatoRevoche';
    let params = new HttpParams()
      .set("idGestioneRevoca", idGestioneRevoca.toString())

    let formData = new FormData();

    formData.append("allegato", file, file.name);
    formData.append("nomeAllegato", file.name);
    formData.append("letteraAccompagnatoria", letteraAccompagnatoria);    //"letteraAccompagnatoria" o "altro"
    formData.append("allegatoIntegrazione", allegatoIntegrazione); //"allegatoIntegrazione" o "altro"
    formData.append("allegatoArchiviazione", allegatoArchiviazione); //"allegatoArchiviazione" o "altro"
    return this.http.post<any>(url, formData, { params: params });
  }

  salvaNoteArchiviazione(note: string, idGestioneRevoca: string) : Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/salvaNoteArchiviazione';
    let params = new HttpParams()
      .set("idGestioneRevoca", idGestioneRevoca.toString())

    return this.http.post<any>(url, note, { params: params });
  }

  /*
    //Gestione Procedimento di Revoca
    @POST
    @Path("/avviaProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response avviaProcedimentoRevoca(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @QueryParam("giorniScadenza") Long giorniScadenza,
            @Context HttpServletRequest req) throws Exception;
  */
  avviaProcedimentoRevoca(numeroProcedimentoRevoca: string, giorniScadenza: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/avviaProcedimentoRevoca';
    let params = new HttpParams()
      .set("numeroProcedimentoRevoca", numeroProcedimentoRevoca.toString())
      .set("giorniScadenza", giorniScadenza.toString())
    return this.http.post<any>(url, null, { params: params });
  }


  /*
  @POST
    @Path("/inviaIncaricoAErogazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response inviaIncaricoAErogazione(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @QueryParam("numeroDichiarazioneSpesa") Long numeroDichiarazioneSpesa,
            @QueryParam("importoDaErogare") BigDecimal importoDaErogare,
            @QueryParam("importoIres") BigDecimal importoIres,
            @QueryParam("giorniScadenza") Long giorniScadenza,
            @Context HttpServletRequest req) throws Exception;
  */
  inviaIncaricoAErogazione(numeroProcedimentoRevoca: string, numeroDichiarazioneSpesa: string, importoDaErogareContributo: string, importoDaErogareFinanziamento: string, importoIres: string, giorniScadenza: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/inviaIncaricoAErogazione';
    let params = new HttpParams()
      .set("numeroProcedimentoRevoca", numeroProcedimentoRevoca)
      .set("numeroDichiarazioneSpesa", numeroDichiarazioneSpesa)
      .set("importoDaErogareContributo", importoDaErogareContributo ? importoDaErogareContributo : "0")
      .set("importoDaErogareFinanziamento", importoDaErogareFinanziamento ? importoDaErogareFinanziamento : "0")
      .set("importoIres", importoIres ? importoIres : "0")
      .set("giorniScadenza", giorniScadenza)
    return this.http.post<any>(url, null, { params: params });
  }


  /*
  @POST
    @Path("/archiviaProcedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response archiviaProcedimentoRevoca(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @QueryParam("note") String note,
            @Context HttpServletRequest req) throws Exception;
  */
  archiviaProcedimentoRevoca(numeroProcedimentoRevoca: string, note: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/archiviaProcedimentoRevoca';
    let params = new HttpParams()
      .set("numeroProcedimentoRevoca", numeroProcedimentoRevoca.toString())
      .set("note", note.toString())
    return this.http.post<any>(url, null, { params: params });
  }


  /*
  @GET
    @Path("/getRichiestaProroga")
    @Produces(MediaType.APPLICATION_JSON)
    Response getRichiestaProroga(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  getRichiestaProroga(numeroProcedimentoRevoca: string): Observable<GestioneProrogaVO> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getRichiestaProroga';
    let params = new HttpParams()
      .set("numeroProcedimentoRevoca", numeroProcedimentoRevoca)
    return this.http.get<GestioneProrogaVO>(url, { params: params });
  }


  /*
  @PUT
    @Path("/updateRichiestaProroga")
    @Produces(MediaType.APPLICATION_JSON)
    Response updateRichiestaProroga(
            @QueryParam("numeroProcedimentoRevoca") Long numeroProcedimentoRevoca,
            @QueryParam("esitoRichiestaProroga") Boolean esitoRichiestaProroga,
            @QueryParam("giorniApprovati") BigDecimal giorniApprovati,
            @Context HttpServletRequest req) throws Exception;
  */
  updateRichiestaProroga(numeroProcedimentoRevoca: string, esitoRichiestaProroga: boolean, giorniApprovati: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/updateRichiestaProroga';
    let params = new HttpParams()
      .set("numeroProcedimentoRevoca", numeroProcedimentoRevoca.toString())
      .set("esitoRichiestaProroga", esitoRichiestaProroga.toString())
      .set("giorniApprovati", giorniApprovati.toString())
      
    return this.http.put<any>(url, null, { params: params });
  }

  /*
  @POST
    @Path("/creaBozzaProvvedimentoRevoca")
    @Produces(MediaType.APPLICATION_JSON)
    Response creaBozzaProvvedimentoRevoca(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  creaBozzaProvvedimentoRevoca(idGestioneRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/creaBozzaProvvedimentoRevoca';
    let params = new HttpParams()
      .set("idGestioneRevoca", idGestioneRevoca.toString())
    return this.http.post<any>(url, null, { params: params });
  }

  /*
  @GET
    @Path("/abilitaRichiediIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response abilitaRichiediIntegrazione(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  abilitaRichiediIntegrazione(idGestioneRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/abilitaRichiediIntegrazione';
    let params = new HttpParams()
      .set("idGestioneRevoca", idGestioneRevoca.toString())
    return this.http.get<any>(url, { params: params });
  }

  /*
  @GET
    @Path("/abilitaChiudiIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response abilitaChiudiIntegrazione(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  abilitaChiudiIntegrazione(idGestioneRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/abilitaChiudiIntegrazione';
    let params = new HttpParams()
      .set("idGestioneRevoca", idGestioneRevoca.toString())
    return this.http.get<any>(url, { params: params });
  }

  /*
   @POST
    @Path("/creaRichiestaIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response creaRichiestaIntegrazione(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @QueryParam("numGiorniScadenza") BigDecimal numGiorniScadenza,
            @Context HttpServletRequest req) throws Exception;
  */
  creaRichiestaIntegrazione(idGestioneRevoca: string, numGiorniScadenza: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/creaRichiestaIntegrazione';
    let params = new HttpParams()
      .set("idGestioneRevoca", idGestioneRevoca.toString())
      .set("numGiorniScadenza", numGiorniScadenza.toString())

    /* il salvataggio degli allegati e autonomo rispetto alla creazione della richiesta integrazione
    let formData = new FormData();

    //lettera accompagnatoria
    if (letteraAccompagnatoria) {
      formData.append("letteraAccompagnatoria", letteraAccompagnatoria, letteraAccompagnatoria.name);
    }
    formData.append("letteraAccompagnatoriaPresente", letteraAccompagnatoriaGiaCaricata ? "S" : "N"); //TRUE se già presente, FALSE altrimenti

    //codice roberta
    if (altriDocumenti && altriDocumenti.length) {
      for (var i = 0; i < altriDocumenti.length; i++) {
        formData.append("file", altriDocumenti[i], altriDocumenti[i].name);
      }
    }
    */

    return this.http.post<any>(url, null, { params: params });
  }

  /*
  @PUT
    @Path("/chiudiRichiestaIntegrazione")
    @Produces(MediaType.APPLICATION_JSON)
    Response chiudiRichiestaIntegrazione(
            @QueryParam("idGestioneRevoca") Long idGestioneRevoca,
            @Context HttpServletRequest req) throws Exception;
  */
  chiudiRichiestaIntegrazione(idGestioneRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/chiudiRichiestaIntegrazione';
    let params = new HttpParams()
      .set("idGestioneRevoca", idGestioneRevoca.toString())
    return this.http.put<any>(url, null, { params: params });
  }

  deleteAllegato(idDocumentoIndex:number){
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/eliminaAllegato';
    let params = new HttpParams()
      .set("idDocumentoIndex", idDocumentoIndex.toString())
    return this.http.post<any>(url, null, { params: params });
    
  }

  eliminaLetteraAccompagnatoria(idDocumentoIndex:number){
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/eliminaAllegato';
    let params = new HttpParams()
      .set("idDocumentoIndex", idDocumentoIndex.toString())
    return this.http.post<any>(url, null, { params: params });
    
  }

}
