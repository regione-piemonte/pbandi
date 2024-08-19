/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { DettaglioDatiProgetto } from './dettaglio-dati-progetto';
export interface DatiProgetto {
  codiceProgettoCipe?: string;
  codiceVisualizzato?: string;
  cup?: string;
  dettaglio?: DettaglioDatiProgetto;
  idDomanda?: number;
  idLineaInterventoAsse?: number;
  idProgetto?: number;
  idSedeIntervento?: number;
  numeroDomanda?: string;
  progrSoggettoProgetto?: number;
  titoloProgetto?: string;
}
