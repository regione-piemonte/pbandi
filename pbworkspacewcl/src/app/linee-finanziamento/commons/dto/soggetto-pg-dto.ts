/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ComuneDTO } from "./comune-dto";

export class SoggettoPGDTO {
    constructor(
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
        public denominazioneEnteDirReg: string,
        public ateneo: string,
        public denominazioneEnteDipUni: string,
        public sedeLegale: ComuneDTO,
        public codiceFiscale: string,
        public ruolo: Array<string>,
        public idRelazioneColBeneficiario: string,
        public descRelazioneColBeneficiario: string,
        public dataCostituzioneAzienda: string,
        public numCivicoSedeLegale: string,
        public idSettoreEnte: string,
        public denominazioneEntePA: string,
    ) { }
}