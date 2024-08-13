/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AllegatiQuietanzeDTO } from "./allegati-quietanze-dto";
import { DocumentoDiSpesaSospesoDTO } from "./documento-di-spesa-sospeso-dto";

export interface AllegatiDocSpesaQuietanzeDTO extends DocumentoDiSpesaSospesoDTO {
    nomiAllegati: Array<string>;
    allegatiQuietanze: Array<AllegatiQuietanzeDTO>;
}