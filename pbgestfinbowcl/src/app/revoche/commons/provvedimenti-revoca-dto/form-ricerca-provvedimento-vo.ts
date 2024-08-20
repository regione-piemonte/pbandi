/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DenominazioneSuggestVO } from "src/app/ambito-erogazioni/ambito-erogazioni/commons/denominazione-suggest-vo";
import { SuggestIdDescVO } from "../suggest-id-desc-vo";

export class FormRicercaProvvedimentoVO {

  constructor(
    public beneficiario?: DenominazioneSuggestVO,
    public bando?: DenominazioneSuggestVO,
    public progetto?: DenominazioneSuggestVO,
    public causaProvvedimentoRevoca?: SuggestIdDescVO,
    public statoProvvedimentoRevoca?: SuggestIdDescVO,
    public numeroProvvedimentoRevoca?: string,
    public attivitaProvvedimentoRevoca?: SuggestIdDescVO,
    public dataProcedRevocaFrom?: Date,
    public dataProcedRevocaTo?: Date,
  ) { }

}
