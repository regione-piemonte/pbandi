/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { HandleExceptionService } from "src/app/core/services/handle-exception.service";
import { AttivitaDTO } from "../commons/dto/attivita-dto";
import { AzioneRecuperoBancaVO } from "../commons/dto/azione-recupero-bancaVO";
import { StoricoAzioneRecuperoBancaDTO } from "../commons/dto/storico-azione-recupero-banca-dto";


@Injectable()
export class AzioniRecuperoBancaService{

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService,

    ) { }

     
    getAzioneRecupBanca(idUtente: number, idProgetto: number,
        idAzioneRecuperoBanca: number, idModalitaAgevolazione: number): Observable <AzioneRecuperoBancaVO>  {
        let url = this.configService.getApiURL() + '/restfacade/azioniRecuperoBanca/getAzioneRecuperoBanca';
         let params = new HttpParams()
        .set("idUtente", idUtente.toString())
        .set("idProgetto", idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        if(idAzioneRecuperoBanca){
            params = params.set("idAzioneRecuperoBanca", idAzioneRecuperoBanca.toString());
        }
        
    return this.http.get<AzioneRecuperoBancaVO>(url, { params: params });
    }

    insertAzioneRecupBanca( azioneRecupBanca: AzioneRecuperoBancaVO ,idUtente: number,idProgetto: number, idModalitaAgevolazione: number): Observable <boolean> {
      
        let url = this.configService.getApiURL() + '/restfacade/azioniRecuperoBanca/insertAzioneRecuperoBanca';
        let params = new HttpParams()
        .set("idUtente", idUtente.toString())
        .set("idProgetto", idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.post<boolean>(url, azioneRecupBanca, { params: params });
    }
    
    modificaAzioneRecupBanca( azioneRecupBanca: AzioneRecuperoBancaVO ,idUtente: number,idProgetto: number, 
        idAzioneRecuperoBanca: number, idModalitaAgevolazione: number): Observable <boolean> {
      
        let url = this.configService.getApiURL() + '/restfacade/azioniRecuperoBanca/modificaAzioneRecuperoBanca';
        let params = new HttpParams()
        .set("idUtente", idUtente.toString())
        .set("idProgetto", idProgetto.toString())
        .set("idAzioneRecuperoBanca", idAzioneRecuperoBanca.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.post<boolean>(url, azioneRecupBanca, { params: params });
    }


    /* insertAzioneRecupBanca(azioneRecupBanca: AzioneRecuperoBancaVO,allegati,
        idProgetto,idModalitaAgevolazione): Observable <boolean> {
      
        let url = this.configService.getApiURL() + '/restfacade/azioniRecuperoBanca/insertAzioneRecuperoBanca';
        let params = new HttpParams()
        let formData = new FormData();
        for (var i = 0; i < allegati.length; i++) {
          console.log(allegati[i]);
          console.log(allegati[i].name);
          formData.append("file", allegati[i], allegati[i].name);
        }
        formData.append(
          "azioneRecupBanca",
          JSON.stringify(azioneRecupBanca)
        );
        
        formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
        formData.append("idProgetto", idProgetto);
        return this.http.post<any>(url, formData, { params: params });
    }
    
    modificaAzioneRecupBanca( azioneRecupBanca: AzioneRecuperoBancaVO,idAzioneRecuperoBanca: number ,allegati,
         idProgetto,idModalitaAgevolazione ): Observable <boolean> {
      
        let url = this.configService.getApiURL() + '/restfacade/azioniRecuperoBanca/modificaAzioneRecuperoBanca';
        let params = new HttpParams()
        let formData = new FormData();
        for (var i = 0; i < allegati.length; i++) {
          console.log(allegati[i]);
          console.log(allegati[i].name);
          formData.append("file", allegati[i], allegati[i].name);
        }
        formData.append(
          "azioneRecupBanca",
          JSON.stringify(azioneRecupBanca)
        );
        formData.append("idAzioneRecuperoBanca", idAzioneRecuperoBanca.toString()); 
        formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
        formData.append("idProgetto", idProgetto);
        return this.http.post<any>(url, formData, { params: params });
    } */

    // lista delle azioni con data di fine validita null 
    getStoricoAzioneRecupBanca (idUtente: number, idProgetto: number, idModalitaAgevolazione: number): Observable <Array<StoricoAzioneRecuperoBancaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/azioniRecuperoBanca/getStoricoAzioneRecuperoBanca';
        let params = new HttpParams().set("idUtente", idUtente.toString()).set("idProgetto",idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.get<Array<StoricoAzioneRecuperoBancaDTO>>(url, { params: params });
    }

    // lista di tutte le azioni 
    getStoricoAzioni(idUtente: number, idProgetto: number, idModalitaAgevolazione: number): Observable <Array<StoricoAzioneRecuperoBancaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/azioniRecuperoBanca/getStoricoAzioni';
        let params = new HttpParams().set("idUtente", idUtente.toString()).set("idProgetto",idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.get<Array<StoricoAzioneRecuperoBancaDTO>>(url, { params: params });
    }

    // lista attivita di un'azione
    getAttivitaAzioneRecuperoBanca(): Observable <Array<AttivitaDTO>>{

        let url = this.configService.getApiURL() + '/restfacade/azioniRecuperoBanca/listaAttivitaAzioniRecuperoBanca'; 
        return this.http.get<Array<AttivitaDTO>>(url);

    }

}