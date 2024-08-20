/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { BeneficiarioSoggCorrEG } from '../commons/dto/beneficiario-sogg-corr-EG';
import { SoggettoCorrelato } from '../commons/dto/soggettoCorrelato';
import { SoggettoFisicoIndipendenteDomanda } from '../commons/dto/SoggettoFisicoIndipendenteDomanda';
import { SoggettoGiuridicoIndipendenteDomanda } from '../commons/dto/soggettoGiuridicoIndipendenteDomanda';

@Injectable({
  providedIn: 'root'
})
export class SoggettiIndipendentiDomandaService {

  private _tipologiaSoggettoCorrelato: string = '';
  private _nagSoggettoCorrelato: string = '';

  private _idSoggetto:any = null;
  private _numeroDomanda:any = null;


  constructor(private configService: ConfigService,
    private http: HttpClient) { }
    
  getElencoSoggCorrIndipDaDomanda(idDomanda: any, idSoggetto: any): Observable<SoggettoCorrelato[]> {
    const params = new HttpParams()
      .set('idDomanda', idDomanda)
      .set('idSoggetto', idSoggetto);

    return this.http.get<SoggettoCorrelato[]>(`${this.configService.getApiURL()}/restfacade/anagrafica/getElencoSoggCorrIndipDaDomanda`, {
      params: params,
    });
  }

  getSoggCorrIndipDaDomandaEG(idDomanda: any, idSoggetto: any, idSoggCorr: any): Observable<BeneficiarioSoggCorrEG> {
    const params = new HttpParams()
      .set('idDomanda', idDomanda.toString())
      .set('idSoggetto', idSoggetto.toString())
      . set('idSoggCorr',idSoggCorr.toString() );

    return this.http.get<BeneficiarioSoggCorrEG>(`${this.configService.getApiURL()}/restfacade/anagrafica/getSoggCorrIndipDaDomandaEG`, {
      params: params,
    });
  }

  getSoggCorrIndipDaDomandaPF(idDomanda: any, idSoggetto: any): Observable<SoggettoFisicoIndipendenteDomanda> {
    const params = new HttpParams()
      .set('idDomanda', idDomanda)
      .set('idSoggetto', idSoggetto);

    return this.http.get<SoggettoFisicoIndipendenteDomanda>(`${this.configService.getApiURL()}/restfacade/anagrafica/getSoggCorrIndipDaDomandaPF`, {
      params: params,
    });
  }

  get tipologiaSoggettoCorrelato(): string {
    return this._tipologiaSoggettoCorrelato;
  }

  setTipologiaSoggettoCorrelato(value: string): void {
    this._tipologiaSoggettoCorrelato = value;
  }

  get nagSoggettoCorrelato(): string {
    return this._nagSoggettoCorrelato;
  }

  setNagSoggettoCorrelato(value: string): void {
    this._nagSoggettoCorrelato = value;
  }

  get idSoggetto(): any {
    return this._idSoggetto;
  }

  setIdSoggetto(value: any): void {
    this._idSoggetto = value;
  }

  get numeroDomanda(): any {
    return this._numeroDomanda;
  }

  setNumeroDomanda(value: any): void {
    this._numeroDomanda = value;
  }


}