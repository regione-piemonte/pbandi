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
import { SaldoStralcioVO } from "../commons/dto/saldo-stralcio-VO";
import { StoricoSaldoStralcioDTO } from "../commons/dto/storico-saldo-stralcio-dto";

@Injectable()
export class SaldoStralcioService{
    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService
    ){}

    // lista delle attivita di stralcio e saldo 
    getListaAttivitaSaldoStralcio(): Observable <Array<AttivitaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/saldoStralcio/listaAttivitaSaldoStralcio'; 
        return this.http.get<Array<AttivitaDTO>>(url);
    }

    // lista delle attivita di esito 
    getListaAttivitaEsito(): Observable <Array<AttivitaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/saldoStralcio/listaAttivitaEsito'; 
        return this.http.get<Array<AttivitaDTO>>(url);
    }
    // lista delle attivita di recupero
    getListaAttivitaRecupero(): Observable <Array<AttivitaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/saldoStralcio/listaAttivitaRecupero'; 
        return this.http.get<Array<AttivitaDTO>>(url);
    }

     // lista degli i saldi e stralci con data di fine validita == null 
     getStoricoSaldoStralcio (idUtente: number, idProgetto: number, idModalitaAgevolazione:number): Observable <Array<StoricoSaldoStralcioDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/saldoStralcio/getStoricoSaldoStralcio';
        let params = new HttpParams().set("idUtente", idUtente.toString()).set("idProgetto",idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.get<Array<StoricoSaldoStralcioDTO>>(url, { params: params });
    }

     // lista di tutti saldi e stralci 
     getStorico(idUtente: number, idProgetto: number, idModalitaAgevolazione): Observable <Array<StoricoSaldoStralcioDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/saldoStralcio/getStorico';
        let params = new HttpParams().set("idUtente", idUtente.toString()).set("idProgetto",idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.get<Array<StoricoSaldoStralcioDTO>>(url, { params: params });
    }

    getSaldoStralcio(idSaldoStralcio: number, idModalitaAgevolazione:number): Observable <SaldoStralcioVO>  {
        let url = this.configService.getApiURL() + '/restfacade/saldoStralcio/getSaldoStralcio';
         let params = new HttpParams()
         .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        if(idSaldoStralcio){
            params = params.set("idSaldoStralcio", idSaldoStralcio.toString());
        }     
    return this.http.get<SaldoStralcioVO>(url, { params: params });
    }


    insertSaldoStralcio( saldoStralcio: SaldoStralcioVO ,idUtente: number,idProgetto: number, idModalitaAgevolazione:number): Observable <boolean> {
      
        let url = this.configService.getApiURL() + '/restfacade/saldoStralcio/insertSaldoStralcio';
        let params = new HttpParams()
        .set("idUtente", idUtente.toString())
        .set("idProgetto", idProgetto.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.post<boolean>(url, saldoStralcio, { params: params });
    }

    modificaSaldoStralcio( saldoStralcio: SaldoStralcioVO ,idUtente: number,idProgetto: number, idSaldoStralcio: number, idModalitaAgevolazione:number): Observable <boolean> {  
        let url = this.configService.getApiURL() + '/restfacade/saldoStralcio/modificaSaldoStralcio';
        let params = new HttpParams()
        .set("idUtente", idUtente.toString())
        .set("idProgetto", idProgetto.toString())
        .set("idSaldoStralcio", idSaldoStralcio.toString())
        .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
        return this.http.post<boolean>(url, saldoStralcio, { params: params });
    }

    /* insertSaldoStralcio( saldoStralcio: SaldoStralcioVO,idProgetto,allegati,idModalitaAgevolazione): Observable <boolean> {
      
        let url = this.configService.getApiURL() + '/restfacade/saldoStralcio/insertSaldoStralcio';
        let params = new HttpParams()
        let formData = new FormData();
        for (var i = 0; i < allegati.length; i++) {
            console.log(allegati[i]);
            console.log(allegati[i].name);
            formData.append("file", allegati[i], allegati[i].name);
        }
        formData.append(
            "saldoStralcio",
            JSON.stringify(saldoStralcio)
        );
        formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
        formData.append("idProgetto", idProgetto);
        return this.http.post<any>(url, formData, { params: params });
    }

    modificaSaldoStralcio( saldoStralcio: SaldoStralcioVO,idSaldoStralcio,idProgetto,allegati,idModalitaAgevolazione): Observable <boolean> {  
        let url = this.configService.getApiURL() + '/restfacade/saldoStralcio/modificaSaldoStralcio';
        let params = new HttpParams()
        let formData = new FormData();
        for (var i = 0; i < allegati.length; i++) {
            console.log(allegati[i]);
            console.log(allegati[i].name);
            formData.append("file", allegati[i], allegati[i].name);
        }
        formData.append(
            "saldoStralcio",
            JSON.stringify(saldoStralcio)
        );
        formData.append("idSaldoStralcio", idSaldoStralcio.toString()); 
        formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
        formData.append("idProgetto", idProgetto);
        return this.http.post<any>(url, formData, { params: params });
    } */


}