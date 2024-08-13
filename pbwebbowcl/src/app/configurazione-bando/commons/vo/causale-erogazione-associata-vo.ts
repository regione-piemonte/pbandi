/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CausaleDiErogazioneAssociataDTO { 
    constructor(
        public progrBandoCausaleErogaz: number,
        public idBando: number,
        public idCausaleErogazione: number,
        public percSogliaSpesaQuietanzata: number,
        public percErogazione: number,
        public percLimite: number,
        public idDimensioneImpresa: number,
        public idFormaGiuridica: number,
        public descCausale: string,
        public descDimensioneImpresa: string,
        public descFormaGiuridica: string,
        public descTabellare: string,
        public descModalitaAgevolazione: string,
        public idModalitaAgevolazione: number,
        public idTipologieDiAllegato: Array<number>
    ) { }
}