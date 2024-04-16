/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Comune } from "./comune";
import { TabDirRegSogg } from "./tab-dir-reg-sogg";

export class SoggettoPG {
    constructor(
        public ruolo: Array<string>,
        public iban: string,
        public denominazione: string,
        public formaGiuridica: string,
        public settoreAttivita: string,
        public attivitaAteco: string,
        public dimensioneImpresa: string,
        public tipoDipDir: string,
        public partitaIvaSedeLegale: string,
        public indirizzoSedeLegale: string,
        public capSedeLegale: string,
        public emailSedeLegale: string,
        public pecSedeLegale: string,
        public faxSedeLegale: string,
        public telefonoSedeLegale: string,
        public dataCostituzioneAzienda: string,
        public denominazioneEnteDirReg: string,
        public ateneo: string,
        public denominazioneEnteDipUni: string,
        public sedeLegale: Comune,
        public codiceFiscale: string,
        public idRelazioneColBeneficiario: string,
        public descRelazioneColBeneficiario: string,
        public tabDipUni: TabDirRegSogg,
        public tabDirReg: TabDirRegSogg,
        public tabAltro: TabDirRegSogg,
        public numCivicoSedeLegale: string,
        public idSettoreEnte: string,
        public tabPA: TabDirRegSogg,
        public denominazioneEntePA: string
    ) { }
}