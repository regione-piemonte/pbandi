/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { FormRicercaProvvedimentoVO } from '../../commons/provvedimenti-revoca-dto/form-ricerca-provvedimento-vo';

@Injectable({
  providedIn: 'root'
})
export class NavigationProvvedimentoRevocaService extends NavigationService {

  private filtroRicercaProvvedimentoRevoca: FormRicercaProvvedimentoVO = null;

  public set filtroRicercaProvvedimentoRevocaSelezionato(value: FormRicercaProvvedimentoVO) {
    this.filtroRicercaProvvedimentoRevoca = value;
  }

  public get filtroRicercaProvvedimentoRevocaSelezionato(): FormRicercaProvvedimentoVO {
    return this.filtroRicercaProvvedimentoRevoca;
  }
}
