/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ResponseCodeMessageVo } from "src/app/gestione-disimpegni/commons/response-code-message-vo";
import { BeneficiarioBilancioDTO } from "./beneficiario-bilancio-vo";
import { DettaglioBeneficiarioBilancioDTO } from "./dettaglio-beneficiario-bilancio-vo";

export class Beneficiario { 
    constructor(
        public beneficiarioBilancioDTO: BeneficiarioBilancioDTO,
        public dettaglioBeneficiarioBilancioDTO: Array<DettaglioBeneficiarioBilancioDTO>,
        public responseCodeMessage: ResponseCodeMessageVo,
        public idAttoLiquidazione: number
    ) { }
}