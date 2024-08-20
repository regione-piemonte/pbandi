/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { HandleExceptionService } from "src/app/core/services/handle-exception.service";
import { AttivitaDTO } from "src/app/gestione-crediti/commons/dto/attivita-dto";
import { AtlanteVO } from "../commons/dto/atlante-vo";
import { BeneficiarioSoggCorrEG } from "../commons/dto/beneficiario-sogg-corr-EG";
import { BeneficiarioSoggCorrPF } from "../commons/dto/beneficiario-sogg-corr-PF";
import { DatiDomanda } from "../commons/dto/dati-domanda";
import { DatiDomandaEG } from "../commons/dto/model-dati-domanda-eg";
import { ModificaDatiDomandaEG } from "../commons/dto/modifica-domandaEG";
import { ModificaDatiDomandaPF } from "../commons/dto/modifica-domandaPF";
import { Nazioni } from "../commons/dto/nazioni";
import { Province } from "../commons/dto/provincie";
import { Regioni } from "../commons/dto/regioni";
import { SoggettoCorrelatoVO } from "../commons/dto/soggetto-correlatoVO";

@Injectable({
    providedIn: 'root'
})

export class DatiDomandaService {
   
    
    
   
  
    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService,
    ) { }



    //BISOGNA CAMBIARE IL SERVIZIO BE IN /getDatiDomandaPF e il modello DatiDomanda con DatiDomandaPF
    datiDomandaServizio: DatiDomanda;
    private datiDomandaServizioInfo = new BehaviorSubject<DatiDomanda>(null);
    datiDomandaServizioInfo$: Observable<DatiDomanda> = this.datiDomandaServizioInfo.asObservable();

    getDatiDomandaPF(idSoggetto: any, idDomanda: any) {
        let url = this.configService.getApiURL() + '/restfacade/datiDomanda/getDatidomanda';

        let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idDomanda", idDomanda.toString());

        return this.http.get<DatiDomanda>(url, { params: params })

    }

    //SERVIZIO DA IMPLEMENTARE BE
    datiDomandaEG: DatiDomandaEG;
    private datiDomandaEGInfo = new BehaviorSubject<DatiDomandaEG>(null);
    datiDomandaEGInfo$: Observable<DatiDomandaEG> = this.datiDomandaEGInfo.asObservable();

    getDatiDomandaEG(idSoggetto: any, idDomanda: any) {
        let url = this.configService.getApiURL() + '/restfacade/datiDomanda/getDatiDomandaEG';

        let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idDomanda", idDomanda.toString());

        return this.http.get<DatiDomandaEG>(url, { params: params })
    }


    getAltreSediEG(idSoggetto: any, idDomanda: any) {
        let url = this.configService.getApiURL() + '/restfacade/datiDomanda/getAltreSedi';

        let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idDomanda", idDomanda.toString());

        return this.http.get<Array<DatiDomandaEG>>(url, { params: params })
    }


    jsonModifiche: string;
    //AGGIORNA ModificaDatiDomandaEG CON IL BE
    // updateDomandaEG(modificaDatiDomandaEG: ModificaDatiDomandaEG) {
    //     let url = this.configService.getApiURL() + '/restfacade/anagrafica/modificaDatiDomandaEG';
    //     let headers = new HttpHeaders().set('Content-Type', 'application/json');
    //     this.jsonModifiche = JSON.stringify(modificaDatiDomandaEG);
    //     return this.http.post<String>(url, this.jsonModifiche, { headers });
    // }

    //AGGIORNA ModificaDatiDomandaPF CON IL BE
    updateDomandaPF(modificaDatiDomandaPF: ModificaDatiDomandaPF) {
        let url = this.configService.getApiURL() + '/restfacade/anagrafica/modificaDatiDomandaPF';
        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        this.jsonModifiche = JSON.stringify(modificaDatiDomandaPF);
        return this.http.post<String>(url, this.jsonModifiche, { headers });
    }

    //// sogg corr da domanda// 

    getElencoSoggCorrDipDaDomanda(idDomanda: number, idSoggetto:any): Observable<Array<SoggettoCorrelatoVO>>{
        let url = this.configService.getApiURL() + '/restfacade/datiDomanda/getElencoSoggCorrDipDaDomanda';
        let params = new HttpParams().set("idDomanda", idDomanda.toString())
        .set("idSoggetto", idSoggetto.toString());
        return this.http.get<Array<SoggettoCorrelatoVO>>(url, { params: params }); 
    }

    getBenefSoggCorrEG(idSoggetto: any, idDomanda:  any, idSoggCorr:any): Observable<BeneficiarioSoggCorrEG>{
        let url = this.configService.getApiURL() + '/restfacade/datiDomanda/getAnagSoggCorrDipDaDomEG';
        let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idDomanda", idDomanda.toString())
                                             .set("idSoggCorr", idSoggCorr.toString());
        return this.http.get<BeneficiarioSoggCorrEG>(url, { params: params }); 
    }
    getBenefSoggCorrPF(idSoggetto: any, idDomanda: any, idSoggCorr:any): Observable<BeneficiarioSoggCorrPF>{
        let url = this.configService.getApiURL() + '/restfacade/datiDomanda/getAnagSoggCorrDipDaDomPF';
        let params = new HttpParams().set("idSoggetto", idSoggetto.toString())
                                        .set("idDomanda", idDomanda.toString())
                                        .set("idSoggCorr", idSoggCorr.toString());
        return this.http.get<BeneficiarioSoggCorrPF>(url, { params: params }); 
    }

    /// ruoli//// 
    getRuoli(): Observable <Array<AttivitaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/datiDomanda/listaRuoli'; 
        return this.http.get<Array<AttivitaDTO>>(url);
    }
    getListaSuggest(id: number,  stringa: string): Observable <Array<AttivitaDTO>>{
        let url = this.configService.getApiURL() + '/restfacade/datiDomanda/suggest'; 
        let params = new HttpParams()
        .set("id", id.toString())
        .set("stringa", stringa.toString()); 
        return this.http.get<Array<AttivitaDTO>>(url, {params:params})
    }

    /// aggiorna// 
    modificaPF(soggPF: BeneficiarioSoggCorrPF, idSoggettoCorr: any, idDomanda: any): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/datiDomanda/modificaSoggettoDipDomandaPF'; 
        let params = new HttpParams().set("idSoggetto", idSoggettoCorr)
            .set("idDomanda", idDomanda) ; 
        return this.http.post<boolean>(url, soggPF, {params: params}); 
    }

    modificaEG(soggEG: BeneficiarioSoggCorrEG, idSoggetto: any, idDomanda:any):  Observable<boolean>{
        let url = this.configService.getApiURL() + '/restfacade/datiDomanda/modificaSoggettoDipDomandaEG'; 
        let params = new HttpParams().set("idSoggetto", idSoggetto).set("idDomanda", idDomanda); 
        return this.http.post<boolean>(url, soggEG, {params: params});
    }

    getNazioni(): Observable<Array<Nazioni>> {
        let url = this.configService.getApiURL() + '/restfacade/anagrafica/getNazioni';
        let params = new HttpParams()
        return this.http.get<Array<Nazioni>>(url, { params })
    }

    getRegioni(): Observable<Array<Regioni>> {
        let url = this.configService.getApiURL() + '/restfacade/anagrafica/getRegioni';
        let params = new HttpParams()
        return this.http.get<Array<Regioni>>(url, { params })
    }
    getProvince(): Observable<Array<AtlanteVO>> {
        let url = this.configService.getApiURL() + '/restfacade/anagrafica/getProvincie';
        return this.http.get<Array<AtlanteVO>>(url)
    }
    updateDatiDomandaEG(datiDomanda: DatiDomandaEG, idUtente:any):Observable<boolean>{
        let url = this.configService.getApiURL()+ '/restfacade/datiDomanda/updateDatiDomandaEG'
        let params = new HttpParams().set("idUtente", idUtente); 
        return this.http.post<boolean>(url, datiDomanda, {params:params});
    }

    salvaDatiDomandaPersFisica(datiPF: DatiDomanda, idUtente: any): Observable<boolean> {
        let url = this.configService.getApiURL()+ '/restfacade/datiDomanda/updateDatiDomandaPF'
        let params = new HttpParams().set("idUtente", idUtente); 
        return this.http.post<boolean>(url, datiPF, {params:params});
      }
    checkProgetto(numeroDomanda: string): Observable<boolean> {
      let url = this.configService.getApiURL()+ '/restfacade/datiDomanda/checKProgetto'; 
      let params = new HttpParams().set("numeroDomanda", numeroDomanda); 
      return this.http.get<boolean>(url, {params: params});
  }
}
