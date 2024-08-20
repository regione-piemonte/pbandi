/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpClientModule, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { HandleExceptionService } from "src/app/core/services/handle-exception.service";
import { RevocaBancariaDTO } from "../commons/dto/revoca-bancaria-dto";

import { StoricoRevocaDTO } from "../commons/dto/storico-revoca-dto";

@Injectable()
export class RevocaBancariaService {
  
    
    getRevoca(idUtente: number, idProgetto: number, idModalitaAgevolazione:number): Observable <RevocaBancariaDTO>  {
      let url = this.configService.getApiURL() + '/restfacade/revoca/getRevocaBancaria';
       let params = new HttpParams()
      .set("idUtente", idUtente.toString())
      .set("idProgetto", idProgetto.toString()).set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
       return this.http.get<RevocaBancariaDTO>(url, { params: params });
    }

    
    salvaRevoca( revocaBancariaDTO: RevocaBancariaDTO,idUtente: number,idProgetto: number, idModalitaAgevolazione: number): Observable <boolean> {
      
      let url = this.configService.getApiURL() + '/restfacade/revoca/salvaRevoca';
      let params = new HttpParams()
      .set("idUtente", idUtente.toString())
      .set("idProgetto", idProgetto.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
      return this.http.post<boolean>(url, revocaBancariaDTO, { params: params });
    }

    /* salvaRevoca(revocaBancariaDTO,allegati, idProgetto,idModalitaAgevolazione): Observable<any> {
      console.log(revocaBancariaDTO);
      
      let url = this.configService.getApiURL() + '/restfacade/revoca/salvaRevoca';
      let params = new HttpParams()
      let formData = new FormData();
      for (var i = 0; i < allegati.length; i++) {
        console.log(allegati[i]);
        console.log(allegati[i].name);
        formData.append("file", allegati[i], allegati[i].name);
      }
      formData.append(
        "revocaBancariaDTO",
        JSON.stringify(revocaBancariaDTO)
      );
    
      formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
      formData.append("idProgetto", idProgetto);
      return this.http.post<any>(url, formData, { params: params });
    }  */

    getStorico (idUtente: number, idProgetto: number, idModalitaAgevolazione:number): Observable <Array<StoricoRevocaDTO>>{
      let url = this.configService.getApiURL() + '/restfacade/revoca/getStorico';
      let params = new HttpParams().set("idUtente", idUtente.toString()).set("idProgetto",idProgetto.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
      return this.http.get<Array<StoricoRevocaDTO>>(url, { params: params });
    }


    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService,

    ) { }

    private revocaBancariaDTO: RevocaBancariaDTO;
}