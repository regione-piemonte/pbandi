/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CheckListBandoLineaDTO { 
    constructor(
        public idTipoDocumentoIndex: number,
        public idModello: number,
        public nomeModello: string,
        public versioneModello: string,
        public descBreveTipoDocIndex: string,
        public descTipoDocIndex: string,
        public idRecord: number
    ) { }
}