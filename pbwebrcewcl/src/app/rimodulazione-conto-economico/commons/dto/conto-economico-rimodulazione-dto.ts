/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ContoEconomicoRimodulazioneDTO {
    constructor(
        public importoRichiestoInDomanda: number,
        public importoRichiestoUltimaProposta: number,
        public importoRichiestoNuovaProposta: number,
        public importoSpesaAmmessaInDetermina: number,
        public importoSpesaAmmessaUltima: number,
        public importoSpesaAmmessaRimodulazione: number,
        public importoSpesaRendicontata: number,
        public importoSpesaQuietanziata: number,
        public percSpesaQuietanziataSuAmmessa: number,
        public importoSpesaValidataTotale: number,
        public percSpesaValidataSuAmmessa: number,
        public label: string,
        public idContoEconomico: number,
        public idRigoContoEconomico: number,
        public idVoce: number,
        public figli: Array<ContoEconomicoRimodulazioneDTO>,
        public importoAmmessoDaBando: number,
        public voceAssociataARigo: boolean,
        public flagRibassoAsta: string,
        public importoRibassoAsta: number,
        public percRibassoAsta: number
    ) { }
}