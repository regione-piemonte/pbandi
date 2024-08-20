/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { SegnalazioneCorteContiVO } from "../commons/dto/segnalazione-corte-contiVO";
import { StoricoSegnalazioneCorteContiDTO } from "../commons/dto/storico-segnalazione-corte-conti-dto";

@Injectable()
export class SegnalazioneCorteContiService{
        constructor(
            private configService: ConfigService,
            private http: HttpClient
        ){}


        getSegnalazione(idSegnalazioneCorteConti: number): Observable<SegnalazioneCorteContiVO>{ 
            let url = this.configService.getApiURL() + '/restfacade/segnalazioneCorteConti/getSegnalazione';
            let params = new HttpParams()
           if(idSegnalazioneCorteConti){
               params = params.set("idSegnalazioneCorteConti", idSegnalazioneCorteConti.toString());
           }     
        return this.http.get<SegnalazioneCorteContiVO>(url, { params: params });
        }

        getStorico(idProgetto: number, idModalitaAgevolazione:number): Observable<Array<StoricoSegnalazioneCorteContiDTO>>{
            let url = this.configService.getApiURL()+ "/restfacade/segnalazioneCorteConti/getStorico"; 
            let params= new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idModalitaAgevolazione",  idModalitaAgevolazione.toString()); 
            return this.http.get<Array<StoricoSegnalazioneCorteContiDTO>>(url, {params: params}); 

        }

        getStoricoSegnalazioniCorteConti (idProgetto: number, idModalitaAgevolazione:number): Observable <Array<StoricoSegnalazioneCorteContiDTO>>{
            let url = this.configService.getApiURL() + '/restfacade/segnalazioneCorteConti/getStoricoSegnalazioni';
            let params = new HttpParams()
            .set("idProgetto",idProgetto.toString())
            .set("idModalitaAgevolazione",  idModalitaAgevolazione.toString());
            return this.http.get<Array<StoricoSegnalazioneCorteContiDTO>>(url, { params: params });
        }

        insertSegnalazione(segnalazioneCorteContiVO: SegnalazioneCorteContiVO, idModalitaAgevolazione:number): Observable<boolean>{
            let url = this.configService.getApiURL()+ "/restfacade/segnalazioneCorteConti/insertSegnalazione";
            let params= new HttpParams().set("idModalitaAgevolazione",  idModalitaAgevolazione.toString());  
            return this.http.post<boolean>(url, segnalazioneCorteContiVO, {params: params}); 
        }

        modificaSegnalazione(segnalazioneCorteContiVO: SegnalazioneCorteContiVO, idSegnalazioneCorteConti: number, idModalitaAgevolazione:number): Observable<boolean>{
           let url = this.configService.getApiURL()+ "/restfacade/segnalazioneCorteConti/modifcaSegnalazione";
           let params =  new HttpParams().set("idSegnalazioneCorteConti", idSegnalazioneCorteConti.toString())
           .set("idModalitaAgevolazione", idModalitaAgevolazione.toString()); 
            return this.http.post<boolean>(url, segnalazioneCorteContiVO, {params: params}); 
        }

        /* insertSegnalazione(segnalazioneCorteContiVO: SegnalazioneCorteContiVO,allegati,idModalitaAgevolazione): Observable<boolean>{
            let url = this.configService.getApiURL()+ "/restfacade/segnalazioneCorteConti/insertSegnalazione"; 
            let params = new HttpParams()
            let formData = new FormData();
            for (var i = 0; i < allegati.length; i++) {
            console.log(allegati[i]);
            console.log(allegati[i].name);
            formData.append("file", allegati[i], allegati[i].name);
            }
            formData.append(
            "segnalazioneCorteContiVO",
            JSON.stringify(segnalazioneCorteContiVO)
            );
            formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
            return this.http.post<any>(url, formData, { params: params }); 
        }

        modificaSegnalazione(segnalazioneCorteContiVO: SegnalazioneCorteContiVO, idSegnalazioneCorteConti: number,allegati,idModalitaAgevolazione): Observable<boolean>{
           let url = this.configService.getApiURL()+ "/restfacade/segnalazioneCorteConti/modifcaSegnalazione";
           let params = new HttpParams()
            let formData = new FormData();
            for (var i = 0; i < allegati.length; i++) {
            console.log(allegati[i]);
            console.log(allegati[i].name);
            formData.append("file", allegati[i], allegati[i].name);
            }
            formData.append(
            "segnalazioneCorteContiVO",
            JSON.stringify(segnalazioneCorteContiVO)
            );
            formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
            formData.append("idSegnalazioneCorteConti", idSegnalazioneCorteConti);
            return this.http.post<any>(url, formData, { params: params });
        }
 */
}