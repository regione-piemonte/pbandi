/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SaveDettaglioDTO {
  constructor(
    public idClassRevocaIrreg: number,
    public idIrregolarita: number,
    public idRevoca: number,
    public importo: number,
    public importoAudit: number,
    public tipologia: string
  ) { }
}
