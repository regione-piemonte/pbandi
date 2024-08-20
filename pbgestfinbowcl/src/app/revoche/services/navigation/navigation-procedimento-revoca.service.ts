/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { FormRicercaProcedimentoVO } from '../../commons/procedimenti-revoca-dto/form-ricerca-procedimento-vo';

@Injectable({
  providedIn: 'root'
})
export class NavigationProcedimentoRevocaService extends NavigationService {

  private filtroRicercaProcedimentoRevoca: FormRicercaProcedimentoVO = null;

  public set filtroRicercaProcedimentoRevocaSelezionato(value: FormRicercaProcedimentoVO) {
    this.filtroRicercaProcedimentoRevoca = value;
  }

  public get filtroRicercaProcedimentoRevocaSelezionato(): FormRicercaProcedimentoVO {
    return this.filtroRicercaProcedimentoRevoca;
  }
}
