/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { AffidServtecArDTO } from '../dto/affid-servtec-ar-dto';
import { RappresentanteLegaleDTO } from '../dto/rappresentante-legale-dto';
import { RichiestaErogazioneDTO } from '../dto/richiesta-erogazione-dto';
export interface CreaRichiestaErogazioneRequest {
  idDelegato?: number;
  idProgetto?: number;
  rappresentante?: RappresentanteLegaleDTO;
  richiesta?: RichiestaErogazioneDTO;
  affidamentiServiziLavori?: Array<AffidServtecArDTO>
}
