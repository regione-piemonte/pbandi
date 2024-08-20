/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoSalvaModuloCheckListHtml {
    constructor(
        public esito: boolean,
        public message: string,
        public params: string[]

      //  Long idDocumentoIndex
      //  byte[] byteModulo
    ) { }
}