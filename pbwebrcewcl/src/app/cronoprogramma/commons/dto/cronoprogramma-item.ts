/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CronoprogrammaItem {
  constructor(
    public idFaseMonit: number,
    public idIter: number,
    public idMotivoScostamento: number,
    public descFaseMonit: string,
    public codIgrue: string,
    public flagObbligatorio: boolean,
    public controlloIndicatori: boolean,
    public dtInizioPrevista: any,   //string per il servizio, ma uso Date in frontend
    public dtFinePrevista: any,     //string per il servizio, ma uso Date in frontend
    public dtInizioEffettiva: any,  //string per il servizio, ma uso Date in frontend
    public dtFineEffettiva: any,    //string per il servizio, ma uso Date in frontend
    public dtInizioPrevistaEditable: boolean,
    public dtFinePrevistaEditable: boolean,
    public dtInizioEffettivaEditable: boolean,
    public dtFineEffettivaEditable: boolean,
    public motivoScostamentoEditable: boolean
  ) { }
}
