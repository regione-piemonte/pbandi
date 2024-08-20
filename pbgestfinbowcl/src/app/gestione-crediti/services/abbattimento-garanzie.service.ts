/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { HandleExceptionService } from "src/app/core/services/handle-exception.service";
import { AbbattimentoGaranzieVO } from "../commons/dto/abbattimento-garanzieVO";
import { AttivitaDTO } from "../commons/dto/attivita-dto";
import { StoricoAbbattimentoGaranziaDTO } from "../commons/dto/storico-abbattimento-garanzia-dto";


@Injectable()
export class AbbattimentoGaranzieService{
    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService
    ) {}

    getAbbattimento(idUtente: number, idProgetto: number,
        idAbbattimentoGaranzia: number, idModalitaAgevolazione:number): Observable <AbbattimentoGaranzieVO>  {
        let url = this.configService.getApiURL() + '/restfacade/abbattimentoGaranzie/getAbbattimentoGaranzie';
         let params = new HttpParams()
        .set("idUtente", idUtente.toString())
        .set("idProgetto", idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        if(idAbbattimentoGaranzia){
            params = params.set("idAbbattimentoGaranzia", idAbbattimentoGaranzia.toString());
        }     
    return this.http.get<AbbattimentoGaranzieVO>(url, { params: params });
    }
    
    insertAbbattimento( abbattimentoGaranzieVO: AbbattimentoGaranzieVO ,idUtente: number,idProgetto: number,idModalitaAgevolazione: number ): Observable <boolean> {
      
        let url = this.configService.getApiURL() + '/restfacade/abbattimentoGaranzie/insertAbbattimentoGaranzie';
        let params = new HttpParams()
        .set("idUtente", idUtente.toString())
        .set("idProgetto", idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.post<boolean>(url, abbattimentoGaranzieVO, { params: params });
    }
    modificaAbbattimento( abbattimentoGaranzieVO: AbbattimentoGaranzieVO ,idUtente: number,idProgetto: number,idAbbattimentoGaranzia: number,idModalitaAgevolazione: number ): Observable <boolean> {  
        let url = this.configService.getApiURL() + '/restfacade/abbattimentoGaranzie/modificaAbbattimentoGaranzie';
        let params = new HttpParams()
        .set("idUtente", idUtente.toString())
        .set("idProgetto", idProgetto.toString())
        .set("idAbbattimentoGaranzia", idAbbattimentoGaranzia.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.post<boolean>(url, abbattimentoGaranzieVO, { params: params });
    }

    /* insertAbbattimento( abbattimentoGaranzieVO: AbbattimentoGaranzieVO ,allegati,idProgetto,idModalitaAgevolazione): Observable <boolean> {
      
        let url = this.configService.getApiURL() + '/restfacade/abbattimentoGaranzie/insertAbbattimentoGaranzie';
        let params = new HttpParams()
        let formData = new FormData();
        for (var i = 0; i < allegati.length; i++) {
          console.log(allegati[i]);
          console.log(allegati[i].name);
          formData.append("file", allegati[i], allegati[i].name);
        }
        formData.append(
          "abbattimentoGaranzieVO",
          JSON.stringify(abbattimentoGaranzieVO)
        ); 
        formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
        formData.append("idProgetto", idProgetto);
        return this.http.post<any>(url, formData, { params: params });
    }
    modificaAbbattimento( abbattimentoGaranzieVO: AbbattimentoGaranzieVO ,idAbbattimentoGaranzia,allegati,idProgetto,idModalitaAgevolazione): Observable <boolean> {  
        let url = this.configService.getApiURL() + '/restfacade/abbattimentoGaranzie/modificaAbbattimentoGaranzie';
        let params = new HttpParams()
        let formData = new FormData();
        for (var i = 0; i < allegati.length; i++) {
          console.log(allegati[i]);
          console.log(allegati[i].name);
          formData.append("file", allegati[i], allegati[i].name);
        }
        formData.append(
          "abbattimentoGaranzieVO",
          JSON.stringify(abbattimentoGaranzieVO)
        );
        formData.append("idAbbattimentoGaranzia", idAbbattimentoGaranzia); 
        formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
        formData.append("idProgetto", idProgetto);
        return this.http.post<any>(url, formData, { params: params });
    } */

    // lista degli abbattimenti con data di fine validita == null 
    getStoricoAbbattimentoGaranzia (idUtente: number, idProgetto: number,idModalitaAgevolazione: number): Observable <Array<StoricoAbbattimentoGaranziaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/abbattimentoGaranzie/getStoricoAbbattimentoGaranzia';
        let params = new HttpParams().set("idUtente", idUtente.toString()).set("idProgetto",idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.get<Array<StoricoAbbattimentoGaranziaDTO>>(url, { params: params });
    }

    // questo ritorna la lista di tutti gli abbattimenti 
    getStoricoAbbattimenti(idUtente: number, idProgetto: number,idModalitaAgevolazione: number): Observable <Array<StoricoAbbattimentoGaranziaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/abbattimentoGaranzie/getStoricoAbbattimenti';
        let params = new HttpParams().set("idUtente", idUtente.toString()).set("idProgetto",idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.get<Array<StoricoAbbattimentoGaranziaDTO>>(url, { params: params });
    }

    // lista delle attivita dedicate all'abbattimento 
    getListaAttivitaAbbattimenti(): Observable <Array<AttivitaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/abbattimentoGaranzie/listaAttivitaAbbattimento'; 
        return this.http.get<Array<AttivitaDTO>>(url);
    }


}