/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SedeLegaleBeneficiarioCspDTO } from "./sede-legale-beneficiario-csp-dto";

export class BeneficiarioCspDTO {
    constructor(
        public codiceFiscaleSoggetto: string,
        public iban: string,
        public denominazioneEnteGiuridico: string,
        public dtCostituzione: Date,
        public idFormaGiuridica: number,
        public descFormaGiuridica: string,
        public idSettoreAttivita: number,
        public descSettore: string,
        public idAttivitaAteco: number,
        public descAteco: string,
        public sediLegali: Array<SedeLegaleBeneficiarioCspDTO>,
        public dtCostituzioneStringa: string,
        public sedeSelected?: SedeLegaleBeneficiarioCspDTO  //solo frontend
    ) { }
}