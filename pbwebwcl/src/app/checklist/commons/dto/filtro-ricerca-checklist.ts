/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FormControl } from "@angular/forms";
import { CodiceDescrizioneDTO } from "src/app/gestione-spesa-validata/commons/dto/codice-descrizione-dto";

export class FiltroRicercaChecklist {
    constructor(
        public dichiarazioneSpesaSelected: CodiceDescrizioneDTO,
        public dataControllo: FormControl,
        public soggettoControllo: string,
        public statoSelected: CodiceDescrizioneDTO,
        public tipologiaSelected: string,
        public rilevazioneSelected: string,
        public versione: string
    ) { }
}