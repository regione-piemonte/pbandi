/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProcedimentoRevocaTableVO {

    public idProcedimentoRevoca: number;
    public numeroProcedimentoRevoca: number;

    public idSoggetto: number;
    public codiceFiscaleSoggetto: string;
    public idBeneficiario: number;
    public denominazioneBeneficiario: string;

    public idDomanda: number;
    public progrBandoLineaIntervento: number;
    public nomeBandoLinea: string;

    public titoloProgetto: string;
    public idProgetto: number;
    public codiceVisualizzatoProgetto: string;

    public idCausaleBlocco: number;
    public descCausaleBlocco: string;

    public idStatoRevoca: number;
    public descStatoRevoca: string;

    public idAttivitaRevoca: number;
    public descAttivitaRevoca: string;

    public dataProcedimentoRevoca: Date;
    public dataStatoRevoca: Date;
    public dataNotifica: Date;
    public dataAttivitaRevoca: Date;

  }



