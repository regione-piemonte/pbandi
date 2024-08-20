/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class Richiesta {
    constructor(
        public idRichiesta: any,
        public idUtenteAgg: any,
        public idTracciaturaRichiesta: any,
        public idTipoRichiesta: any,
        public idStatoRichiesta: any,
        public tipoComunicazione: any,
        public dataComunicazione: any,
        public destinatarioMittente: any,
        public numeroProtocollo: any,
        public motivazione: any,
        public numeroDomanda: any,
        public nag: any,
        public dataEmissione: any,
        public dataScadenza: any,
        public esito: any,
        public numeroProtocolloInps: any,
        public dataRicezione: any,
        public numeroProtocolloRicevuta: any,
        public numeroProtocolloPrefettura: any,
        public flagUrgenza: any
    ) { }
}

