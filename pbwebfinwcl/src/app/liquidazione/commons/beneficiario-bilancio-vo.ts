/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SedeBilancioDTO } from "./sede-bilancio-vo"

export class BeneficiarioBilancioDTO { 
    constructor(
        public abi: string,
        public agenzia: string,
        public banca: string,
        public bic: string,
        public cab: string,
        public cap: string,
        public cin: string,
        public codiceBeneficiarioBilancio: number,
        public codiceFiscaleBen: string,
        public codFiscQuietanzante: string,
        public codModPagBilancio: number,
        public cognome: string,
        public dtNascita: Date,
        public email: string,
        public iban: string,
        public idBeneficiarioBilancio: number,
        public idEnteGiuridico: number,
        public idComuneNascita: number,
        public idComuneSede: number,
        public idComuneSedeEstero: number,
        public idEstremiBancari: number,
        public idIndirizzo: number,
        public idModalitaErogazione: number,
        public idPersonaFisica: number,
        public idProvinciaNascita: number,
        public idProvinciaSede: number,
        public idSede: number,
        public idSoggetto: number,
        public idStatoSede: number,
        public indirizzo: string,
        public modalitaPagamento: string,
        public nome: string,
        public numeroConto: string,
        public partitaIva: string,
        public personaFisica: boolean,
        public quietanzante: number,
        public ragioneSociale: string,
        public sede: string,
        public sesso: string,
        public ragioneSocialeSecondaria: string,
        public sedePrincipale: SedeBilancioDTO
    ) { }
}