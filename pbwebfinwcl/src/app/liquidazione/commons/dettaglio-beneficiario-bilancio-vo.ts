/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SedeBilancioDTO } from "./sede-bilancio-vo";

export class DettaglioBeneficiarioBilancioDTO { 
    constructor(
        public abi: string,
        public bic: string,
        public cab: string,
        public cap: string,
        public cin: string,
        public codiceBeneficiarioBilancio: number,
        public codiceFiscaleBen: string,
        public codModPagBilancio: number,
        public cognome: string,
        public comuneStatoEstero: string,
        public descComune: string,
        public descComuneSecondario: string,
        public descProvincia: string,
        public descProvinciaSecondaria: string,
        public descNazione: string,
        public descComuneNascita: string,
        public descProvinciaNascita: string,
        public dtNascita: Date,
        public iban: string,
        public idBeneficiarioBilancio: number,
        public idComuneNascita: number,
        public idComuneSede: number,
        public idComuneSedeEstero: number,
        public idEnteGiuridico: number,
        public idIndirizzo: number,
        public idModalitaErogazione: number,
        public idPersonaFisica: number,
        public idProvinciaNascita: number,
        public idProvinciaSede: number,
        public idSede: number,
        public idSoggetto: number,
        public idStatoNascita: number,
        public idStatoSede: number,
        public indirizzo: string,
        public mail: string,
        public modalitaPagamento: string,
        public nome: string,
        public numeroConto: string,
        public partitaIva: string,
        public personaFisica: boolean,
        public ragioneSociale: string,
        public ragioneSocialeSecondaria: string,
        public sede: string,
        public sesso: string,
        public sedeSecondaria: string,
        public secondario: boolean,
        public sedePrincipale: SedeBilancioDTO,
    ) { }
}