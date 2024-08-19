/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export class FiltroTrasferimentoRequest {
  constructor(
    public aDataTrasferimento?: string,
    public codiceTrasferimento?: string,
    public daDataTrasferimento?: string,
    public flagPubblicoPrivato?: string,
    public idCausaleTrasferimento?: number,
    public idLineaDiIntervento?: number,
    public idSoggettoBeneficiario?: number,
    public idTrasferimento?: number,
  ) { }
}
