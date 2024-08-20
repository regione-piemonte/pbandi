/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { AttivitaDTO } from "src/app/gestione-crediti/commons/dto/attivita-dto";
import { CercaPropostaVarazioneStatoCreditoSearchVO } from "../commons/dto/prop-var-search-VO";
import { SalvaVariazioneStatoCreditVO } from "../commons/dto/salva-var-stato-cred-vo";
import { PropostaVarazioneStatoCreditoDTO } from "../commons/dto/storico-proposta-var-st-cred-DTO";


@Injectable()
export class RicercaProposteVarStCredService {
    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ) { }

    getListaAgev(): Observable <Array<AttivitaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/proposte/listaAgev'; 
        return this.http.get<Array<AttivitaDTO>>(url);
    }
    getListaStatoProposta(): Observable <Array<AttivitaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/proposte/listaStatoProposta'; 
        return this.http.get<Array<AttivitaDTO>>(url);
    }
    getListaSuggest(id: number, stringa: string): Observable <Array<AttivitaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/proposte/suggess'; 
        let params = new HttpParams()
        .set("id", id.toString())
        .set("stringa", stringa.toString()); 
        return this.http.get<Array<AttivitaDTO>>(url, {params:params});
    }

    getStatoCredito(){
        let url = this.configService.getApiURL() + '/restfacade/proposte/statoCredito'; 
        return this.http.get<any>(url);
    }



    getElencoProposte( propostaSearch: CercaPropostaVarazioneStatoCreditoSearchVO)
    : Observable <Array<PropostaVarazioneStatoCreditoDTO>> {  
        let url = this.configService.getApiURL() + '/restfacade/proposte/elencoProposte';
        let params = new HttpParams();
        return this.http.post<Array<PropostaVarazioneStatoCreditoDTO>>(url, propostaSearch, {params:params});
    }

    rifiutaAccettaMassiva(datiBackend, flagConferma){
        let url = this.configService.getApiURL() + `/restfacade/proposte/rifiutaAccettaMassiva`;
        let params = new HttpParams().set('flagConferma', flagConferma.toString());
        return this.http.post<any>(url, datiBackend, { params: params })
    }       
    
    
    salvaStatoCredito(salvaStato: SalvaVariazioneStatoCreditVO): Observable<boolean>{
        let url = this.configService.getApiURL() + '/restfacade/proposte/salvaStatoCred';
        let params = new HttpParams();
        return this.http.post<boolean>(url, salvaStato, {params:params});    
    }
}