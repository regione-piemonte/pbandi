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
//import { NoteGeneraliVO } from "../commons/dto/note-generaliVO"; // OLD - SPOSTATO
import { PassaggioPerditaVO } from "../commons/dto/passaggio-perdita-VO";
import { StoricoRevocaDTO } from "../commons/dto/storico-revoca-dto";
import { TransazioneVO } from "../commons/dto/transazioneVO";


@Injectable()
export class PassaggioPerditaService {
  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService
  ) { }

  salvaPassaggioPerdita(passaggioPerdita: PassaggioPerditaVO,  idModalitaAgevolazione:number): Observable<boolean> {

    let url = this.configService.getApiURL() + '/restfacade/passaggioPerdita/salvaPassaggioPerdita';
    let params = new HttpParams()
    .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.post<boolean>(url, passaggioPerdita, { params: params });
  }

  /* salvaPassaggioPerdita(passaggioPerdita: PassaggioPerditaVO,allegati,idModalitaAgevolazione): Observable<boolean> {

    let url = this.configService.getApiURL() + '/restfacade/passaggioPerdita/salvaPassaggioPerdita';
    let params = new HttpParams()
    let formData = new FormData();
    for (var i = 0; i < allegati.length; i++) {
      console.log(allegati[i]);
      console.log(allegati[i].name);
      formData.append("file", allegati[i], allegati[i].name);
    }
    formData.append(
      "passaggioPerdita",
      JSON.stringify(passaggioPerdita)
    );
  
    formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
    return this.http.post<any>(url, formData, { params: params });
  } */

  getPassaggioPerdita(idProgetto: number, idModalitaAgevolazione: number): Observable<PassaggioPerditaVO> {
    let url = this.configService.getApiURL() + '/restfacade/passaggioPerdita/getPassaggioPerdita';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<PassaggioPerditaVO>(url, { params: params });
  }

  getStorico(idProgetto: number, idModalitaAgevolazione: number): Observable<Array<StoricoRevocaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/passaggioPerdita/getStorico';
    let params = new HttpParams().set("idProgetto", idProgetto.toString())
    .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<Array<StoricoRevocaDTO>>(url, { params: params });
  }

  salvaTransazione(transazioneVO: TransazioneVO, idModalitaAgevolazione: number): Observable<boolean> {

    let url = this.configService.getApiURL() + '/restfacade/passaggioPerdita/salvaTransazione';
    let params = new HttpParams()
    .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.post<boolean>(url, transazioneVO, { params: params });
  }

  /* salvaTransazione(transazioneVO: TransazioneVO,allegati,idModalitaAgevolazione): Observable<boolean> {

    let url = this.configService.getApiURL() + '/restfacade/passaggioPerdita/salvaTransazione';
    let params = new HttpParams()
                let formData = new FormData();
                for (var i = 0; i < allegati.length; i++) {
                console.log(allegati[i]);
                console.log(allegati[i].name);
                formData.append("file", allegati[i], allegati[i].name);
                }
                formData.append(
                "transazioneVO",
                JSON.stringify(transazioneVO)
                ); 
                formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
                return this.http.post<any>(url, formData, { params: params });
  } */

  getTransazione(idProgetto: number, idModalitaAgevolazione: number): Observable<TransazioneVO> {
    let url = this.configService.getApiURL() + '/restfacade/passaggioPerdita/getTransazione';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<TransazioneVO>(url, { params: params });
  }

  getStoricoTransazioni(idProgetto: number, idModalitaAgevolazione: number): Observable<Array<StoricoRevocaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/passaggioPerdita/getStoricoTransazioni';
    let params = new HttpParams().set("idProgetto", idProgetto.toString())
    .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<Array<StoricoRevocaDTO>>(url, { params: params });
  }

  getListaBanche2(string: String): Observable<Array<AttivitaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/passaggioPerdita/getListaBanche';
    let params = new HttpParams().set("banca", string.toString()); 
    return this.http.get<Array<AttivitaDTO>>(url, { params: params });
  }

  // NOTE GENERALI
  // Spostate in AttivitàIstruttoreAreaCreditiService



}