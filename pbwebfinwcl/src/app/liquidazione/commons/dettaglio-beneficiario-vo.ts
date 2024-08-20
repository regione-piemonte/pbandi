/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ComuneDTO } from "./comune-vo";
import { CoordinateBancarieDTO } from "./coordinate-bancarie-vo";
import { Decodifica } from "./decodifica";
import { ProvinciaDTO } from "./provincia-vo";

export class DettaglioBeneficiario {
    constructor(
        public modalitaErogazione: Array<Decodifica>,
        public nazioni: Array<Decodifica>,
        public province: Array<ProvinciaDTO>,
        public comuneDTO: Array<ComuneDTO>,
        public coordinateBancarieDTO: CoordinateBancarieDTO
    ) { }
}