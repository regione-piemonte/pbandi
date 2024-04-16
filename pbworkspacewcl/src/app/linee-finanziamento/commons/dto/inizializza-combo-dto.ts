/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CodiceDescrizione } from "./codice-descrizione"
import { IdDescBreveDescEstesa } from "./id-desc-breve-desc-estesa";

export class InizializzaComboDTO {
    constructor(
        public comboStrumentoAttuativo: Array<CodiceDescrizione>,
        public comboSettoreCpt: Array<CodiceDescrizione>,
        public comboTemaPrioritario: Array<CodiceDescrizione>,
        public comboIndicatoreRisultatoProgramma: Array<CodiceDescrizione>,
        public comboIndicatoreQsn: Array<CodiceDescrizione>,
        public comboTipoAiuto: Array<CodiceDescrizione>,
        public comboStrumentoProgrammazione: Array<CodiceDescrizione>,
        public comboDimensioneTerritoriale: Array<CodiceDescrizione>,
        public comboProgettoComplesso: Array<CodiceDescrizione>,
        public comboSettoreCipe: Array<CodiceDescrizione>,
        public comboNaturaCipe: Array<CodiceDescrizione>,
        public comboRuoli: Array<CodiceDescrizione>,
        public comboAteneo: Array<CodiceDescrizione>,
        public comboSettoreAttivita: Array<CodiceDescrizione>,
        public comboDenominazioneEnteDipReg: Array<CodiceDescrizione>,
        public comboDenominazioneEntePA: Array<CodiceDescrizione>,
        public comboFormaGiuridica: Array<CodiceDescrizione>,
        public comboNazione: Array<IdDescBreveDescEstesa>,
        public comboNazioneNascita: Array<IdDescBreveDescEstesa>,
        public comboObiettivoTematico: Array<CodiceDescrizione>,
        public comboGrandeProgetto: Array<CodiceDescrizione>,
        public comboTipoOperazione: Array<CodiceDescrizione>
    ) { }
}