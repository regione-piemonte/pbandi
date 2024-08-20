/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { FiltroPropostaRevocaVO } from '../../commons/proposte-revoca-dto/filtro-proposta-revoca-vo';
import { FormRicercaPropostaVO } from '../../commons/proposte-revoca-dto/form-ricerca-proposta-vo';

@Injectable({
  providedIn: 'root'
})
export class NavigationPropostaRevocaService extends NavigationService {

  private filtroRicercaPropostaRevoca: FormRicercaPropostaVO = null;

  public set filtroRicercaPropostaRevocaSelezionato(value: FormRicercaPropostaVO) {
    this.filtroRicercaPropostaRevoca = value;
  }

  public get filtroRicercaPropostaRevocaSelezionato(): FormRicercaPropostaVO {
    return this.filtroRicercaPropostaRevoca;
  }
}
