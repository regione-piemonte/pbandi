/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { EstremiBancariDTO } from '../components/configura-bando-linea/DTO/estremi-bancari-DTO';
import { Observable } from 'rxjs';
import { ModAgevEstremiBancariDTO } from '../components/configura-bando-linea/DTO/mod-agev-estremi-Bancari-DTO';
import { EstremiContoDTO } from '../components/configura-bando-linea/DTO/estremi-conto-DTO';
import { InsertEstremiBancariDTO } from '../components/configura-bando-linea/DTO/insert-estremi-bancari-DTO';
import { ParametriMonitoraggioTempi } from '../components/configura-bando-linea/DTO/parametri-monitoraggio-tempi';

@Injectable({providedIn: 'root'})
export class ConfigurazioneBandoLineaService {
 

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    pathBase = '/restfacade/configurazonebando/configurazonebandolinea/';
    pathBaseEstremiBancari = '/restfacade/configurazonebando/configurazonebandolinea/estremibancari/';
    pathBaseMonitoraggioTempi = '/restfacade/configurazonebando/configurazonebandolinea/monitoraggioTempi/';


    getEstremiBancari(idBando: number): Observable <Array<ModAgevEstremiBancariDTO>> {
        let url = this.configService.getApiURL() + this.pathBaseEstremiBancari + 'getestremibancari';

        let params = new HttpParams()
            .set("idBando", idBando.toString())

        return this.http.get<Array<ModAgevEstremiBancariDTO>>(url, { params: params });
    }

    getBancheByDesc(descrizione: string): Observable<any> {        
        let url = this.configService.getApiURL() + this.pathBaseEstremiBancari + 'getBancheByDesc';
        let params = new HttpParams().set("descrizione", descrizione);
        return this.http.get<any>(url, { params: params });
      }


    sendToAmmCont(idU : number, idBando: number): Observable <any> {
        let url = this.configService.getApiURL() + this.pathBaseEstremiBancari + 'sendToAmministrativoContabile';

        let params = new HttpParams()
            .set("idBando", idBando.toString())
            .set("idU", idU.toString())

        return this.http.post<Array<ModAgevEstremiBancariDTO>>(url, null,{ params: params });
    }


    
    removeAssociazioneIban(idBando: number, idModalitaAgevolazione: number, idBanca: number, estremo: EstremiContoDTO) : Observable <any> {
       console.log(idBando, idModalitaAgevolazione, idBanca, estremo)

       let url = this.configService.getApiURL() + this.pathBaseEstremiBancari + 'removeAssociazioneIban';
       let params = new HttpParams()
            .set("idBando", idBando.toString())
            .set("idModalitaAgevolazione", idModalitaAgevolazione.toString())
            .set("idBanca", idBanca.toString())
            
       return this.http.post<any>(url, estremo, { params: params });

      }

      
      removeAssociazioneBanca(idBando: number, idModalitaAgevolazione: number, idBanca: number) : Observable <any> {
        console.log(idBando, idModalitaAgevolazione, idBanca)

        let url = this.configService.getApiURL() + this.pathBaseEstremiBancari + 'removeAssociazioneIban';
        let params = new HttpParams()
            .set("idBando", idBando.toString())
            .set("idModalitaAgevolazione", idModalitaAgevolazione.toString())
            .set("idBanca", idBanca.toString())

        return this.http.post<any>(url, { params: params });
      }




      insertEstremiBancari(insertEstremiBancariDTO: InsertEstremiBancariDTO) : Observable <any> {
        
        let url = this.configService.getApiURL() + this.pathBaseEstremiBancari + 'insertEstremiBancari';
        let params = new HttpParams()
        return this.http.post<any>(url, insertEstremiBancariDTO,{ params: params });

      }



      getParametriMonitoraggioTempi(): Observable <any>  {

        let url = this.configService.getApiURL() + this.pathBaseMonitoraggioTempi + 'getParametriMonitoraggioTempi';
        let params = new HttpParams()
        return this.http.get<any>(url, { params: params });
      }

      getParametriMonitoraggioTempiAssociati(idBandoLinea: string): Observable <any>  {
        

        let url = this.configService.getApiURL() + this.pathBaseMonitoraggioTempi + 'getParametriMonitoraggioTempiAssociati';
        let params = new HttpParams()
              .set("idBandoLinea", idBandoLinea)
        return this.http.get<any>(url, { params: params });
      }


      insertParametriMonitoraggioTempiAssociati(parametriMonitoraggioTempi: ParametriMonitoraggioTempi) : Observable <any> {
        let url = this.configService.getApiURL() + this.pathBaseMonitoraggioTempi + 'insertParametriMonitoraggioTempiAssociati';
        let params = new HttpParams()              
        return this.http.post<any>(url,parametriMonitoraggioTempi, { params: params });
      }


      deleteParametriMonitoraggioTempiAssociati(idParamMonitBandoLinea: number) : Observable <any>{
        let url = this.configService.getApiURL() + this.pathBaseMonitoraggioTempi + 'deleteParametriMonitoraggioTempiAssociati';
        let params = new HttpParams().set("idParamMonitBandoLinea", idParamMonitBandoLinea.toString())              
        return this.http.post<any>(url,null,{ params: params });
      }

      updateParametriMonitoraggioTempiAssociati(parametriMonitoraggioTempi: ParametriMonitoraggioTempi) : Observable <any>{
        let url = this.configService.getApiURL() + this.pathBaseMonitoraggioTempi + 'updateParametriMonitoraggioTempiAssociati';
        let params = new HttpParams()              
        return this.http.post<any>(url,parametriMonitoraggioTempi,{ params: params });
      }

      bandoIsEnteCompetenzaFinpiemonte(progBandoLineaIntervento: string) : Observable <any>{
        let url = this.configService.getApiURL() + this.pathBase + 'bandoIsEnteCompetenzaFinpiemonte';
        let params = new HttpParams().set("progBandoLineaIntervento", progBandoLineaIntervento);
        return this.http.get<any>(url,{ params: params });
      }





    
}