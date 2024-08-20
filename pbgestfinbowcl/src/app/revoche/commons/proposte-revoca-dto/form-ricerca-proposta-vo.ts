/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DenominazioneSuggestVO } from "src/app/ambito-erogazioni/ambito-erogazioni/commons/denominazione-suggest-vo";
import { CausaleBloccoSuggestVO } from "./causale-blocco-suggest-vo";
import { StatoRevocaSuggestVO } from "./stato-revoca-suggest-vo";

export class FormRicercaPropostaVO {

  constructor(
  public beneficiario: DenominazioneSuggestVO,
  public bando: DenominazioneSuggestVO,
  public progetto: DenominazioneSuggestVO,
  public causaPropostaRevoca: CausaleBloccoSuggestVO,
  public statoPropostaRevoca: StatoRevocaSuggestVO,
  public numeroPropostaRevoca: string,
  public dataPropostaRevocaFrom: Date,
  public dataPropostaRevocaTo: Date,
  ) {}

}
