/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface IndicatoreItem {
  codIgrue?: string;
  codiceErrore?: string;
  descIndicatore?: string;
  descTipoIndicatore?: string;
  descUnitaMisura?: string;
  flagNonApplicabile?: boolean;
  flagObbligatorio?: boolean;
  idBando?: number;
  idIndicatore?: number;
  idTipoIndicatore?: number;
  infoFinale?: string;
  infoIniziale?: string;
  isFlagNonApplicabileEditabile?: boolean;
  isTipoIndicatore?: boolean;
  isValoreAggiornamentoEditable?: boolean;
  isValoreFinaleEditable?: boolean;
  isValoreInizialeEditable?: boolean;
  label?: string;
  valoreAggiornamento?: string;
  valoreFinale?: string;
  valoreIniziale?: string;
}
