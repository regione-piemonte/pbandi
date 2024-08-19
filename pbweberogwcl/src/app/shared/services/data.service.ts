/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";
import {ResponseCreaCommunicazioneRinuncia} from "../../rinuncia/commons/dto/response-crea-communicazione-rinuncia";

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private responseCreaCommunicazioneRinuncia = new Subject<ResponseCreaCommunicazioneRinuncia>();

  constructor() { }

  setResponseCreaCommunicazioneRinuncia(res: ResponseCreaCommunicazioneRinuncia) {
    this.responseCreaCommunicazioneRinuncia.next({ dichiarazioneRinuncia: res.dichiarazioneRinuncia, invioDigitale: res.invioDigitale });
  }

  getResponseCreaCommunicazioneRinuncia(): Observable<ResponseCreaCommunicazioneRinuncia> {
    return this.responseCreaCommunicazioneRinuncia.asObservable();
  }

}
