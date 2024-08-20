/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { StoricoBeneficiarioFisico } from '../commons/dto/storico-beneficiario-fisico';
import { StoricoBeneficiarioSec } from '../commons/dto/storico-benficiario';
import { VersioniSec } from '../commons/dto/versioni';
import { VistaStoricoSec } from '../commons/dto/vista-versione';
import { VistaStoricoPF } from '../commons/dto/vista-versione-pf';

@Injectable({
  providedIn: 'root'
})
export class StoricoBenficiarioService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
  ) { }

  storicoBeneficiario: StoricoBeneficiarioSec;
  private storicoBeneficiarioInfo = new BehaviorSubject<StoricoBeneficiarioSec>(null);
  storicoBeneficiarioInfo$: Observable<StoricoBeneficiarioSec> = this.storicoBeneficiarioInfo.asObservable();

  getStorico(idSoggetto: any) {

    let url = this.configService.getApiURL() + '/restfacade/storico/getStorico';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString())

    return this.http.get<StoricoBeneficiarioSec>(url, { params: params }).subscribe(data => {
      if (data) {
        this.storicoBeneficiario = data;
        this.storicoBeneficiarioInfo.next(data);
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });

  }

  storicoBeneficiarioFisico: StoricoBeneficiarioFisico;
  private storicoBeneficiarioFisicoInfo = new BehaviorSubject<StoricoBeneficiarioFisico>(null);
  storicoBeneficiarioFisicoInfo$: Observable<StoricoBeneficiarioFisico> = this.storicoBeneficiarioFisicoInfo.asObservable();

  getStoricoFisico(idSoggetto: any) {

    let url = this.configService.getApiURL() + '/restfacade/storico/getStoricoFisico';
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString())

    return this.http.get<StoricoBeneficiarioFisico>(url, { params: params }).subscribe(data => {
      if (data) {
        this.storicoBeneficiarioFisico = data;
        this.storicoBeneficiarioFisicoInfo.next(data);
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });

  }

  versioni: Array<VersioniSec>;
  private versioniInfo = new BehaviorSubject<VersioniSec[]>(null);
  versioniInfo$: Observable<VersioniSec[]> = this.versioniInfo.asObservable();

  getVersioni(idSoggetto: any) {

    let url = this.configService.getApiURL() + '/restfacade/storico/getVersioni';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString());
    return this.http.get<any>(url, { params: params })

  }

  vistaStorico: VistaStoricoSec;
  private vistaStoricoInfo = new BehaviorSubject<VistaStoricoSec>(null);
  vistaStoricoInfo$: Observable<VistaStoricoSec> = this.vistaStoricoInfo.asObservable();

  getVistaStoricoDomanda(idSoggetto: any, idDomanda: any) {

    let url = this.configService.getApiURL() + '/restfacade/storico/getVistaStoricoDomanda';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idDomanda", idDomanda);

    return this.http.get<VistaStoricoSec>(url, { params: params })
  }


  vistaStoricoPF: VistaStoricoPF;
  private vistaStoricoPFInfo = new BehaviorSubject<VistaStoricoPF>(null);
  vistaStoricoPFInfo$: Observable<VistaStoricoPF> = this.vistaStoricoPFInfo.asObservable();

  getVistaStoricoDomandaPF(idSoggetto: any, idDomanda: any) {

    let url = this.configService.getApiURL() + '/restfacade/storico/getVistaStoricoDomandaPF';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idDomanda", idDomanda);

    return this.http.get<VistaStoricoPF>(url, { params: params })
  }

  getVistaStoricoProgetto(idSoggetto: any, idProgetto: any) {

    let url = this.configService.getApiURL() + '/restfacade/storico/getVistaStoricoProgetto';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idProgetto", idProgetto);

    return this.http.get<VistaStoricoSec>(url, { params: params })
  }

  getVistaStoricoProgettoPF(idSoggetto: any, idProgetto: any) {

    let url = this.configService.getApiURL() + '/restfacade/storico/getVistaStoricoProgettoPF';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idProgetto", idProgetto);

    return this.http.get<VistaStoricoPF>(url, { params: params })
  }
}
