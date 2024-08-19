/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { SedeProgettoDTO } from '../dto/sede-progetto-dto';
export interface DettaglioSoggettoBeneficiarioDTO {
  codUniIpa?: string;
  codiceFiscale?: string;
  descrizioneBeneficiario?: string;
  flagPubblicoPrivato?: number;
  idEnteGiuridico?: number;
  idProgetto?: number;
  idSoggettoBeneficiario?: number;
  sedeLegale?: string;
  email?:string;
  pec?:string;
  telefono?:string;
  fax?:string;
  sediIntervento?: Array<SedeProgettoDTO>;
}
