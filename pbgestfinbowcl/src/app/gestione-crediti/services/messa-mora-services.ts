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
import { MessaMoraVO } from "../commons/dto/messa-moraVO";
import { StoricoMessaMoraDTO } from "../commons/dto/storico-messa-mora-dto";

@Injectable()
export class MessaMoraService{
    
    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ){}
         
        getListaAttivitaMessaMora(): Observable <Array<AttivitaDTO>>{
            let url = this.configService.getApiURL() + '/restfacade/messaMora/getListaAttivitaMessaMora'; 
            return this.http.get<Array<AttivitaDTO>>(url);
        }
 
        getListaAttivitaRecupero(): Observable <Array<AttivitaDTO>>{
            let url = this.configService.getApiURL() + '/restfacade/messaMora/getListaAttivitaRecupero'; 
            return this.http.get<Array<AttivitaDTO>>(url);
        }
    
         
         getStoricoMessaMora (idProgetto: number,  idModalitaAgevolazione: number): Observable <Array<StoricoMessaMoraDTO>>{
            let url = this.configService.getApiURL() + '/restfacade/messaMora/getStoricoMessaMora';
            let params = new HttpParams()
            .set("idProgetto",idProgetto.toString())
            .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());;
            return this.http.get<Array<StoricoMessaMoraDTO>>(url, { params: params });
        }
    
       
         getStorico(idProgetto: number,  idModalitaAgevolazione:  number): Observable <Array<StoricoMessaMoraDTO>>{
            let url = this.configService.getApiURL() + '/restfacade/messaMora/getStorico';
            let params = new HttpParams()
            .set("idProgetto",idProgetto.toString())
            .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
            return this.http.get<Array<StoricoMessaMoraDTO>>(url, { params: params });
        }
    
        getMessaMora(idMessaMora: number,  idModalitaAgevolazione:number): Observable <MessaMoraVO>  {
            let url = this.configService.getApiURL() + '/restfacade/messaMora/getMessaMora';
             let params = new HttpParams()
             .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
            if(idMessaMora){
                params = params.set("idMessaMora", idMessaMora.toString());
            }     
        return this.http.get<MessaMoraVO>(url, { params: params });
        }
    
        insertMessaMora( messaMora: MessaMoraVO, idModalitaAgevolazione): Observable <boolean> {   
            let url = this.configService.getApiURL() + '/restfacade/messaMora/insertMessaMora';
            let params = new HttpParams()
            .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());; 
            return this.http.post<boolean>(url, messaMora, { params: params });
        }
    
        modificaMessaMora( messamoraVO: MessaMoraVO, idMessaMora: number, idModalitaAgevolazione:number): Observable <boolean> {  
            let url = this.configService.getApiURL() + '/restfacade/messaMora/modificaMessaMora';
            let params = new HttpParams()
            .set("idMessaMora", idMessaMora.toString())
            .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
            return this.http.post<boolean>(url, messamoraVO, { params: params });
            }


            /* insertMessaMora( messaMora: MessaMoraVO,allegati,idModalitaAgevolazione): Observable <boolean> {   
                let url = this.configService.getApiURL() + '/restfacade/messaMora/insertMessaMora';
                let params = new HttpParams()
                let formData = new FormData();
                for (var i = 0; i < allegati.length; i++) {
                console.log(allegati[i]);
                console.log(allegati[i].name);
                formData.append("file", allegati[i], allegati[i].name);
                }
                formData.append(
                "messaMora",
                JSON.stringify(messaMora)
                ); 
                formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
                return this.http.post<any>(url, formData, { params: params });
            }
        
            modificaMessaMora( messamoraVO: MessaMoraVO, idMessaMora: number,allegati,idModalitaAgevolazione): Observable <boolean> {  
                let url = this.configService.getApiURL() + '/restfacade/messaMora/modificaMessaMora';
                let params = new HttpParams()
                let formData = new FormData();
                for (var i = 0; i < allegati.length; i++) {
                console.log(allegati[i]);
                console.log(allegati[i].name);
                formData.append("file", allegati[i], allegati[i].name);
                }
                formData.append(
                "messamoraVO",
                JSON.stringify(messamoraVO)
                ); 
                formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
                formData.append("idMessaMora", idMessaMora);
                return this.http.post<any>(url, formData, { params: params });
                } */
            
}