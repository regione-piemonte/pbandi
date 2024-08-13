/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { AvvisoDTO } from "../commons/dto/avviso-dto";
import { InizializzaGestioneNewsDTO } from "../commons/dto/inizializza-gesione-news-dto";

@Injectable()
export class GestioneNewsService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }

  inizializzaGestioneNews(): Observable<InizializzaGestioneNewsDTO> {
    let url = this.configService.getApiURL() + '/restfacade/gestionenews/inizializzaGestioneNews';
    return this.http.get<InizializzaGestioneNewsDTO>(url);
  }

  aggiornaAvviso(request: AvvisoDTO): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/gestionenews/aggiornaAvviso';
    return this.http.post<boolean>(url, request);
  }

  eliminaAvviso(idNews: number): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/gestionenews/eliminaAvviso';
    let params = new HttpParams().set("idNews", idNews.toString());
    return this.http.get<boolean>(url, { params: params });
  }
}
