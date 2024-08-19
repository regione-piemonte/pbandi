/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SaveIrregolaritaDTO {
  constructor(
    public idIrregolarita: number,
    public idRevoca: number,
    public quotaIrregolarita: number
  ) { }
}
