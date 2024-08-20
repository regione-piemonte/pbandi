/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ImpegnoDTO } from "./impegno-vo";
import { ResponseCodeMessageVo } from "./response-code-message-vo";

export class DettaglioAttoDTO { 
    constructor(
        public impegnoDTO: ImpegnoDTO,
        public responseCodeMessage: ResponseCodeMessageVo,
    ) { }
}