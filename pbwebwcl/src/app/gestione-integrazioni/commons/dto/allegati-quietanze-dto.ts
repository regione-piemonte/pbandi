/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { QuietanzaDTO } from "./quietanza-dto";

export interface AllegatiQuietanzeDTO extends QuietanzaDTO {
    nomiAllegati: Array<string>;
}