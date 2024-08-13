/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class TipoDiTrattamentoAssociatoDTO { 
    constructor(
        public idBando: number,
        public idTipoTrattamento: number,
        public isPredefinito: boolean,
        public descTipoTrattamento: string,
        public descBreveTipoTrattamento: string,
        public descTabellare: string
    ) { }
}