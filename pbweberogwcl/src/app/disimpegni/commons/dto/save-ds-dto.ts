/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SaveDsDTO {
  constructor(
    public idClassRevocaIrreg: number,
    public idIrregolarita: number,
    public idRevoca: number,
    public idDs: number,
    public importoIrregolareDs: number,
    public tipologia: string
  ) { }
}
