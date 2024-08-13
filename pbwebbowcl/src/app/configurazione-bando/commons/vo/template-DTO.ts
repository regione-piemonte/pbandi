/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class TemplateDTO { 
    constructor(
        public idTemplate: number,
        public idTipoTemplate: number,
        public idTipoDocumentoIndex: number,
        public progrBandolineaIntervento: number,
        public descBreveTipoDocIndex: string,
        public descTipoDocIndex: string,
        public descBreveTipoTemplate: string,
        public nomeBandolinea: string,
        public dtInserimento: Date
    ) { }
}