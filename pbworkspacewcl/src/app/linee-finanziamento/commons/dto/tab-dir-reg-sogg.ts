/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Comune } from "./comune";

export class TabDirRegSogg {
    constructor(
        public ruolo: Array<string>,
        public iban: string,
        public ateneo: string,
        public denominazioneEnteDipUni: string,
        public denominazioneEnteDirReg: string,
        public idRelazioneColBeneficiario: string,
        public descRelazioneColBeneficiario: string,
        public denominazione: string,
        public codiceFiscale: string,
        public formaGiuridica: string,
        public settoreAttivita: string,
        public attivitaAteco: string,
        public dimensioneImpresa: string,
        public sedeLegale: Comune,
        public partitaIvaSedeLegale: string,
        public indirizzoSedeLegale: string,
        public capSedeLegale: string,
        public emailSedeLegale: string,
        public pecSedeLegale: string,
        public faxSedeLegale: string,
        public telefonoSedeLegale: string,
        public dataCostituzioneAzienda: string,
        public numCivicoSedeLegale: string,
        public idSettore: string,
        public denominazioneEntePA: string
    ) { }
}