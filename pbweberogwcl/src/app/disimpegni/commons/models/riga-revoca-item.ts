/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RigaRevocaItem {
  constructor(
    public data: string,
    public hasCausaliErogazione: boolean,
    public hasIrregolarita: boolean,
    public idModalitaAgevolazione: string,
    public importoAgevolato: number,
    public importoErogato: number,
    public importoRecuperato: number,
    public importoRevocato: number,
    public importoRevocaNew: number,
    public isRigaModalita: boolean,
    public isRigaTotale: boolean,
    public label: string,
    public importoRevocaNewFormatted?: string
  ) { }
}
