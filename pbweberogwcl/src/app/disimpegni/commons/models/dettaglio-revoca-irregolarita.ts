/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


import { DsDettaglioRevocaIrregolarita } from './ds-dettaglio-revoca-irregolarita';
export class DettaglioRevocaIrregolarita {
  constructor(
    public dsDettagliRevocaIrregolarita: Array<DsDettaglioRevocaIrregolarita>,
    public idClassRevocaIrreg: number,
    public idDettRevocaIrreg: number,
    public idIrregolarita: number,
    public idRevoca: number,
    public importo: number,
    public importoAudit: number,
    public tipologia: string,
    public importoAuditFormatted?: string
  ) { }
}
