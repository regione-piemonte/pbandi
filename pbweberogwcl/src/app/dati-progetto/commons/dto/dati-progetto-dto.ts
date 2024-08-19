/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DettaglioProgettoDTO } from '../dto/dettaglio-progetto-dto';
export interface DatiProgettoDTO {
  codiceProgetto?: string;
  codiceProgettoCipe?: string;
  codiceVisualizzato?: string;
  cup?: string;
  dettaglio?: DettaglioProgettoDTO;
  idDomanda?: number;
  idLineaInterventoAsse?: number;
  idProgetto?: number;
  idSedeIntervento?: number;
  idSoggettoBeneficiario?: number;
  numeroDomanda?: string;
  progrSoggettoProgetto?: number;
  titoloProgetto?: string;
}
