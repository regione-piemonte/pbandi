/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import { NavigationService } from "src/app/shared/services/navigation.service";
import { FormElencoDistinte } from '../commons/form-elenco-distinte';


@Injectable({
  providedIn: 'root'
})
export class NavigationElencoDistinteService  extends NavigationService {

  private formElencoDistinte: FormElencoDistinte = null;

  public set formElencoDistinteSelezionato(value: FormElencoDistinte) {
    this.formElencoDistinte = value;
  }

  public get formElencoDistinteSelezionato(): FormElencoDistinte {
    return this.formElencoDistinte;
  }
}
