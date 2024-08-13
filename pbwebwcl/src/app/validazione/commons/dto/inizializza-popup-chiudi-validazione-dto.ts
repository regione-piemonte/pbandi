/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AppaltoProgetto } from "./appalto-progetto";

export class InizializzaPopupChiudiValidazioneDTO {
    constructor(
        public note: string,
        public checkDsIntegrativaVisibile: boolean,
        public checkDsIntegrativaSelezionatoENonModificabile: boolean,
        public messaggi: Array<string>,
        public warningImportoMaggioreAmmesso: boolean,
        public appalti: Array<AppaltoProgetto>
    ) { }
}