/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DatiSif {
    dtFirmaAccordo: Date;
    dtCompletamentoValutazione: Date;

    constructor(
        dtFirmaAccordo: Date,
        dtCompletamentoValutazione: Date
    ) {
        if (dtFirmaAccordo) {
            this.dtFirmaAccordo = new Date(dtFirmaAccordo);
        }
        if (dtCompletamentoValutazione) {
            this.dtCompletamentoValutazione = new Date(dtCompletamentoValutazione);
        }
    }
}