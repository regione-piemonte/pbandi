/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

//import { Riassicurazione_ProgettiAssociatiVO } from "./riassicurazione_ProgettiAssociatiVO";
import { Riassicurazione_SoggettiCorrelatiVO } from "./riassicurazione_SoggettiCorrelatiVO";

export class Riassicurazione_BeneficiarioDomandaVO {
    
    public idSoggetto: number;
    public idProgetto: number;

    public denominazione: string;
    public idDomanda: number;
    public numeroDomanda: string;
    public idStatoDomanda: number;
    public descBreveStatoDomanda: string;
    public descStatoDomanda: string;
    public titoloBando: string;
    public importoRichiesto: number;
    public importoAmmesso: number;

    //public progettiAssociati: Array<Riassicurazione_ProgettiAssociatiVO>;
    public idUltimaEscussione: number;
    public soggettiCorrelati: Array<Riassicurazione_SoggettiCorrelatiVO>;

}