/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CodiceDescrizioneDTO } from "./codice-descrizione-dto";
import { DichiarazioneSpesaValidataComboDTO } from "./dichiarazione-spesa-validata-combo-dto";

export class InizializzaSpesaValidataDTO {
    constructor(
        public codiceVisualizzatoProgetto: string,
        public taskVisibile: boolean,
        public elencoTask: Array<string>,		                            // popolato solo se taskVisibile = true.       
        public comboDichiarazioniDiSpesa: Array<DichiarazioneSpesaValidataComboDTO>,
        public comboVociDiSpesa: Array<CodiceDescrizioneDTO>,
        public comboTipiDocumentoDiSpesa: Array<CodiceDescrizioneDTO>,
        public comboTipiFornitore: Array<CodiceDescrizioneDTO>,
        public esistePropostaCertificazione: boolean,                        // Se true, si visualizza il seguente msg:
        // Per il progetto è stata creata almeno una proposta di certificazione. Eventuali rettifiche sulla spesa validata potranno avere effetto sugli importi certificati.
        public richiestaIntegrazioneAbilitata:boolean
    ) { }
}