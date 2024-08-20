/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

//È il DTO del form che viene popolato nel ricerca procedimento revoca
export class FiltroProcedimentoRevocaVO {

    public idSoggetto: string;
    public progrBandoLineaIntervent: string;
    public idProgetto: string;
    public idCausaleBlocco: string;
    public idStatoRevoca: string;
    public numeroProcedimentoRevoca: string;
    public idAttivitaRevoca : string;
    public dataProcedimentoRevocaFrom: Date;
    public dataProcedimentoRevocaTo: Date;

  }

