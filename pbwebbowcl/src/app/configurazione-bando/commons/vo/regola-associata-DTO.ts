/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RegolaAssociataDTO { 
    constructor(
        public idTipoAnagrafica: number,
        public idTipoBeneficiario: number,
        public idRegola: number,
        public progrBandoLineaIntervento: number,
        public anagraficaBeneficiario: string,
        public tipoAssociazione: string,
        public descRegolaComposta: string
    ) { }
}