/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import { NavigationService } from 'src/app/shared/services/navigation.service';
import { searchControlliDTO } from '../commons/dto/searchControlliDTO';

@Injectable({
  providedIn: 'root'
})
export class NavigationControlliService extends NavigationService {

  private searchControlliDTO: searchControlliDTO= null; 
  private searchControlliDTOAltro: searchControlliDTO= null; 
  constructor() {
    super();
  }
  public set searchControlliDTOScelto(value: searchControlliDTO) {
    this.searchControlliDTO = value;
  }

  public get searchControlliDTOScelto(): searchControlliDTO {
    return this.searchControlliDTO;
  }
  public set searchControlliDTOSceltoAltro(value: searchControlliDTO) {
    this.searchControlliDTOAltro = value;
  }

  public get searchControlliDTOSceltoAltro(): searchControlliDTO {
    return this.searchControlliDTOAltro;
  }


}
