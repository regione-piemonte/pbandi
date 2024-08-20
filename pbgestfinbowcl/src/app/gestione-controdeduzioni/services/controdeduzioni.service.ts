/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHandler, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { ControdeduzioneVO } from "src/app/gestione-crediti/commons/dto/controdeduzioneVO";
import { SchedaClienteMain } from "src/app/gestione-crediti/commons/dto/scheda-cliente-main";
import { AllegatiControdeduzioniVO } from "../common/dto/allegati-controdeduzioni.vo";
import { ControdeduzioniVO } from "../common/controdeduzioniVO";

@Injectable({
    providedIn: 'root'
})

export class ControdeduzioniService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ) { }

    //ROBA NUOVA 

    getControdeduzioni(idProgetto:number): Observable <Array<ControdeduzioniVO>> {
        let url = this.configService.getApiURL() + '/restfacade/controdeduzioni/getControdeduzioni';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<Array<ControdeduzioniVO>>(url, { params: params });
    }

    

    getGestioniRevoca(idProgetto:number) {
        let url = this.configService.getApiURL() + '/restfacade/controdeduzioni/getGestioniRevoca';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<any[]>(url, { params: params });
    }

    getIntestazioneControdeduzioni(idProgetto : number) {
        let url = this.configService.getApiURL() + '/restfacade/controdeduzioni/getIntestazioneControdeduzioni';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<any[]>(url, { params: params });
    }

    deleteControdeduz(idControdeduz){
        let url = this.configService.getApiURL() + '/restfacade/controdeduzioni/deleteControdeduz';
        let params = new HttpParams().set('idControdeduz', idControdeduz.toString());
        return this.http.post<boolean>(url,{body:null},{ params: params });
    }

    insertControdeduzione(idGestioneRevoca){
        let url = this.configService.getApiURL() + '/restfacade/controdeduzioni/insertControdeduz';
        let params = new HttpParams().set('idGestioneRevoca', idGestioneRevoca.toString());
        return this.http.post<boolean>(url,{body:null},{ params: params });
    }

    

    richiediAccessoAtti(idControdeduz){
        let url = this.configService.getApiURL() + '/restfacade/controdeduzioni/richiediAccessoAtti';
        let params = new HttpParams().set('idControdeduz', idControdeduz.toString());
        return this.http.post<boolean>(url,{body:null}, { params: params });
    }

    inviaControdeduz(idControdeduz){
        let url = this.configService.getApiURL() + '/restfacade/controdeduzioni/inviaControdeduz';
        let params = new HttpParams().set('idControdeduz', idControdeduz.toString());
        return this.http.post<boolean>(url,{body:null}, { params: params });
    }

//////////////////////////////////////// 
    
    richiediProroga(datiBackend,idControdeduz:number){
        let url = this.configService.getApiURL() + `/restfacade/controdeduzioni/richiediProroga/${idControdeduz}`;
        return this.http.post<any>(url,datiBackend)
        
    }

    getRichiestaProroga(idControdeduz:number){
        let url = this.configService.getApiURL() + '/restfacade/controdeduzioni/getRichiestaProroga';
        let params = new HttpParams()
            .set('idControdeduz', idControdeduz.toString());
        return this.http.get<Array<any>>(url, { params: params });
        
    }

    uploadFile(datiBackend:AllegatiControdeduzioniVO[],idControdeduz:number){
        let url = this.configService.getApiURL() + `/restfacade/controdeduzioni/inserisciAllegati/${idControdeduz}`;
        return this.http.post<any>(url,datiBackend)
        
    }

    getAllegati(idControdeduz) {
        let url = this.configService.getApiURL() + '/restfacade/controdeduzioni/getAllegati';
        let params = new HttpParams().set('idControdeduz', idControdeduz.toString());
        return this.http.get<any[]>(url, { params: params });
      }
    
    
     deleteAllegati(idFileEntita){
        let url = this.configService.getApiURL() + '/restfacade/controdeduzioni/deleteAllegato';
        let params = new HttpParams().set('idFileEntita', idFileEntita.toString());
        return this.http.post<boolean>(url,{body:null}, { params: params });
    }


}