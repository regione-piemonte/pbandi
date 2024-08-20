/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { EnteCompetenzaDTO } from "./ente-competenza-vo";
import { TipoFondoDTO } from "./tipo-fondo-vo";

export class CapitoloDTO { 
    constructor(
        public idCapitolo: number,
        public numeroCapitolo: number,
        public numeroArticolo: number,
        public descCapitolo: string,
        public enteCompetenza: EnteCompetenzaDTO,
        public tipoFondo: TipoFondoDTO,
        public provenienza: string
    ) { }
}