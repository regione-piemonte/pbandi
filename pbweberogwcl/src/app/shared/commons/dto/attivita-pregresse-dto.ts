/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import {RigoAttivitaPregresseDTO} from "./rigo-attivita-pregresse-dto";

export interface AttivitaPregresseDTO {
  codAttivita?: string;
  data?: string;
  descAttivita?: string;
  idDocumentoIndex?: string;
  nomeDocumento?: string;
  righe?: Array<RigoAttivitaPregresseDTO>;
}
