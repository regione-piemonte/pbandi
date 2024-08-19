/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface SedeProgetto {
  cap?: string;
  civico?: number;
  codIstatComune?: string;
  descBreveTipoSede?: string;
  descComune?: string;
  descIndirizzo?: string;
  descIndirizzoComposto?: string;
  descNazione?: string;
  descProvincia?: string;
  descRegione?: string;
  descTipoSede?: string;
  email?: string;
  fax?: string;
  flagSedeAmministrativa?: boolean;
  idComune?: number;
  idIndirizzo?: number;
  idNazione?: number;
  idProgetto?: number;
  idProvincia?: number;
  idRecapiti?: number;
  idRegione?: number;
  idSede?: number;
  idSoggettoBeneficiario?: number;
  idTipoSede?: number;
  idViaL2?: number;
  isEliminabile?: boolean;
  isModificabile?: boolean;
  linkDettaglio?: string;
  linkElimina?: string;
  linkModifica?: string;
  partitaIva?: string;
  progrSoggettoProgettoSede?: number;
  telefono?: string;
}
