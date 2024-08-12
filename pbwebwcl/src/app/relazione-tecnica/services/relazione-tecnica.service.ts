import { HttpClient, HttpHeaders, HttpParams, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ConfigService } from 'src/app/core/services/config.service';
import { DocumentoIndexDTO } from 'src/app/documenti-progetto/commons/dto/documento-index-dto';
import { RelazioneTecnicaDTO } from '../commons/relazione-tecnica-dto';

@Injectable({
  providedIn: 'root'
})
export class RelazioneTecnicaService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }



  // getCodiceProgetto(idProgetto: number): Observable<String> {
  //   let url = this.configService.getApiURL() + '/restfacade/relazioneTecnica/getCodiceProgetto';
  //   let params = new HttpParams().set("idProgetto", idProgetto.toString());
  //   return this.http.get<String>(url, { params: params });
  // }

    /**
   * Finds Codice Progetto by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
    getCodiceProgettoResponse(idProgetto?: number):Observable<HttpResponse<string>> {
      let __params = new HttpParams();
      let __headers = new HttpHeaders();
      let __body: any = null;
      if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
      let req = new HttpRequest<any>(
        'GET',
        this.configService.getApiURL() + `/restfacade/relazioneTecnica/getCodiceProgetto`,
        __body,
        {
          headers: __headers,
          params: __params,
          responseType: 'text'
        });
  
      return this.http.request<any>(req).pipe(
        filter(_r => _r instanceof HttpResponse),
        map((_r) => {
          return _r as HttpResponse<string>;
        })
      );
    }
    /**
     * Finds Codice Progetto by idProgetto
     * @param idProgetto undefined
     * @return successful operation
     */
    getCodiceProgetto(idProgetto?: number): Observable<string> {
      return this.getCodiceProgettoResponse(idProgetto).pipe(
        map(_r => _r.body as string)
      );
    }
 
    salvaRelazioneTecnica (file: File,  nomeFile:  string, idProgetto:  number, idTipoRelazioneTecnica: number): Observable<boolean> {

      let url = this.configService.getApiURL() + '/restfacade/relazioneTecnica/salvaRelazioneTecnica';
      let formData = new FormData();
      formData.append("file", file, nomeFile);
      formData.append("nomeFile", nomeFile);
      formData.append("idTipoRelazioneTecnica", idTipoRelazioneTecnica.toString());
      formData.append("idProgetto", idProgetto.toString());
  
      return this.http.post<boolean>(url, formData);
  }

  getRelazioneTecnica(idProgetto: number, idTipoRelazioneTecnica: number):Observable<RelazioneTecnicaDTO>{

    let url = this.configService.getApiURL() + '/restfacade/relazioneTecnica/getRelazioneTecnica';
   
    console.log("getRelazioneTecnica: "+ idTipoRelazioneTecnica);
    

    let params = new HttpParams().set("idProgetto", idProgetto.toString()).
     set("idTipoRelazioneTecnica", idTipoRelazioneTecnica.toString());
    return this.http.get<RelazioneTecnicaDTO>(url, {params: params});
  }

  validaRifiutaRelazioneTecnica ( idRelazioneTecnica: number, flagConferma: string, nota: string): Observable<boolean> {

    console.log(flagConferma);
    console.log(idRelazioneTecnica);
    console.log(nota);
    
      let url = this.configService.getApiURL() + '/restfacade/relazioneTecnica/validaRifiutaRelazioneTecnica';
      let params = new HttpParams()
      .set("idRelazioneTecnica", idRelazioneTecnica.toString())
      .set("flagConferma", flagConferma.toString())
      .set("nota", nota.toString());
  
      return this.http.get<boolean>(url, {params: params});
  }
}
