/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

//È il DTO del form che viene popolato nel ricerca Provvedimento revoca
export class FiltroProvvedimentoRevocaVO {

    public idTipologiaRevoca?: string;
    public idSoggetto?: string;
    public progrBandoLineaIntervent?: string;
    public idProgetto?: string;
    public idCausaRevoca?: string;
    public idStatoRevoca?: string;
    public numeroRevoca?: string;
    public idAttivitaRevoca?: string;
    public dataRevocaFrom?: Date;
    public dataRevocaTo?: Date;

  }

