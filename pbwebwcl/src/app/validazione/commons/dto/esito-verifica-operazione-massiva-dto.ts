/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoVerificaOperazioneMassivaDTO {
    constructor(
        public esito: boolean,
        public messaggio: string,
        public messaggioImportoAmmissibileSuperato: string,
        public idDocumenti: Array<number>

    ) { }
}