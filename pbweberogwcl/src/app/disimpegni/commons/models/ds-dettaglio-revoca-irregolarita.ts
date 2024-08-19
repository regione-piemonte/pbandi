/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class DsDettaglioRevocaIrregolarita {
  constructor(
    public idDettRevocaIrreg: number,
    public idDichiarazioneSpesa: number,
    public importoIrregolareDs: number,
    public importoIrregolareDsFormatted?: string
  ) { }
}
