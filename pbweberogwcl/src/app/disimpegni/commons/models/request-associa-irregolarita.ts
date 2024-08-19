/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SaveDettaglioDTO } from '../dto/save-dettaglio-dto';
import { SaveDsDTO } from '../dto/save-ds-dto';
import { SaveIrregolaritaDTO } from '../dto/save-irregolarita-dto';
export class RequestAssociaIrregolarita {
  constructor(
    public elencoSaveDettaglioDTO: Array<SaveDettaglioDTO>,
    public elencoSaveDsDTO: Array<SaveDsDTO>,
    public elencoSaveIrregolaritaDTO: Array<SaveIrregolaritaDTO>,
    public idRevoca: number
  ) { }
}
