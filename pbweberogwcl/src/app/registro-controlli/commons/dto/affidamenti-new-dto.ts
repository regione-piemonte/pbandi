/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ChecklistNewDTO } from "./checklist-new";

/* tslint:disable */
export interface AffidamentiNewDTO {
  cigProcAgg?: string;
  codProcAgg?: string;
  descTipologiaAppalto?: string;
  esitoDefinitivo?: string;
  esitoIntermedio?: string;
  flagRettificaDefinitivo?: string;
  flagRettificaIntermedio?: string;
  idAppalto?: number;
  idProceduraAggiudicaz?: number;
  idStatoTipoDocIndex?: number;
  idTipologiaAppalto?: number;
  importo?: number;
  importoContratto?: number;
  oggettoAppalto?: string;
  checklist: Array<ChecklistNewDTO>;
}
