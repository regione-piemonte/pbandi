/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class TipoDiAiutoAssociatoDTO { 
    constructor(
        public idTipoAiuto: number,
        public descBreveTipoAiuto: string,
        public descTipoAiuto: string,
        public codIgrueT1: string,
    ) { }
}