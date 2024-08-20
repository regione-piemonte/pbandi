/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import { NavigationService } from "src/app/shared/services/navigation.service";
import {FormPropostaErogazione} from "../commons/form-proposta-erogazione";


@Injectable({
  providedIn: 'root'
})
export class NavigationPropostaErogazioneService  extends NavigationService {

  private filtroRicercaProposteErogazione: FormPropostaErogazione = null;

  public set filtroRicercaProposteErogazioneSelezionato(value: FormPropostaErogazione) {
    this.filtroRicercaProposteErogazione = value;
  }

  public get filtroRicercaProposteErogazioneSelezionato(): FormPropostaErogazione {
    return this.filtroRicercaProposteErogazione;
  }
}
