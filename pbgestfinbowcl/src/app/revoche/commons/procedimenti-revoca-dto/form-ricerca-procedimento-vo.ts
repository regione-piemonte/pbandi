/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DenominazioneSuggestVO } from "src/app/ambito-erogazioni/ambito-erogazioni/commons/denominazione-suggest-vo";
import { SuggestIdDescVO } from "../suggest-id-desc-vo";

export class FormRicercaProcedimentoVO {

  constructor(
    public beneficiario: DenominazioneSuggestVO,
    public bando: DenominazioneSuggestVO,
    public progetto: DenominazioneSuggestVO,
    public causaProcedimentoRevoca: SuggestIdDescVO,
    public statoProcedimentoRevoca: SuggestIdDescVO,
    public numeroProcedimentoRevoca: string,
    public attivitaProcedimentoRevoca: SuggestIdDescVO,
    public dataProcedRevocaFrom: Date,
    public dataProcedRevocaTo: Date,
  ) { }

}
