/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { WidgetDTO } from '../commons/dto/widget-dto';
import { BandoWidgetDTO } from '../commons/dto/bando-widget-dto';
import { EsitoOperazioneDTO } from 'src/app/shared/commons/dto/esito-operazione-dto';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }

  isDashboardVisible(): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/dashboard/isDashboardVisible';
    return this.http.get<boolean>(url);
  }

  areWidgetsConfigured(): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/dashboard/areWidgetsConfigured';
    return this.http.get<boolean>(url);
  }

  getWidgets(): Observable<Array<WidgetDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/dashboard/widgets';
    return this.http.get<Array<WidgetDTO>>(url);
  }

  getBandiDaAssociare(): Observable<Array<BandoWidgetDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/dashboard/bandiDaAssociare';
    return this.http.get<Array<BandoWidgetDTO>>(url);
  }

  getBandiAssociati(idWidget: number): Observable<Array<BandoWidgetDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/dashboard/bandiAssociati';
    let params = new HttpParams().set("idWidget", idWidget.toString());
    return this.http.get<Array<BandoWidgetDTO>>(url, { params: params });
  }

  changeWidgetAttivo(idWidget: number, flagWidgetAttivo: boolean): Observable<EsitoOperazioneDTO> {
    let url = this.configService.getApiURL() + '/restfacade/dashboard/widgetAttivo';
    let params = new HttpParams().set("idWidget", idWidget.toString()).set("flagWidgetAttivo", flagWidgetAttivo.toString());
    return this.http.post<EsitoOperazioneDTO>(url, { body: null }, { params: params });
  }

  associaBandoAWidget(idWidget: number, progrBandoLineaIntervento: number): Observable<EsitoOperazioneDTO> {
    let url = this.configService.getApiURL() + '/restfacade/dashboard/associaBandoAWidget';
    let params = new HttpParams().set("idWidget", idWidget.toString()).set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString());
    return this.http.post<EsitoOperazioneDTO>(url, { body: null }, { params: params });
  }

  disassociaBandoAWidget(idBandoLinSoggWidget: number): Observable<EsitoOperazioneDTO> {
    let url = this.configService.getApiURL() + '/restfacade/dashboard/disassociaBandoAWidget';
    let params = new HttpParams().set("idBandoLinSoggWidget", idBandoLinSoggWidget.toString());
    return this.http.delete<EsitoOperazioneDTO>(url, { params: params });
  }
}
