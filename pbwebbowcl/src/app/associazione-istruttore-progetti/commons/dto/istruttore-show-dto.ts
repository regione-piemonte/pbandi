/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BandoLineaAssociatoVo } from "src/app/configurazione-bando/commons/vo/bando-linea-associato-vo";
import { ProgettoDTO } from "./progetto-dto";

export class IstruttoreShowDTO {
    constructor(
        public codiceFiscale: string,
        public cognome: string,
        public nome: string,
        public totaleProgettiAssociati: number,
        public totaleBandiLineaAssociati: number,
        public idBando: number,
        public idProgetto: number,
        public idSoggetto: number,
        public progettiAssociati: Array<ProgettoDTO>,
        public bandiLineaAssociati: Array<BandoLineaAssociatoVo>,
        public idPersonaFisica: number,
        public showProgAsso: boolean,
        public showBandiLineaAsso: boolean,
        public descBreveTipoAnagrafica: string,
    ) { }
}