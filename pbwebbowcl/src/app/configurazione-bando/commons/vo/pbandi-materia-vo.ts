/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PbandiMateriaVo { 
    constructor(
        public dtFineValidita: Date,
        public idMateria: number,
        public descMateria: string,
        public dtInizioValidita: Date,
        public descBreveMateria: string
    ) { }
}