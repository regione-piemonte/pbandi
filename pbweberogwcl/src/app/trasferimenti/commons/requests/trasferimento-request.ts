/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export class TrasferimentoRequest {
  constructor(
    public cfBeneficiario?: string,
    public codiceTrasferimento?: string,
    public denominazioneBeneficiario?: string,
    public descCausaleTrasferimento?: string,
    public descPubblicoPrivato?: string,
    public dtTrasferimento?: string,
    public flagPubblicoPrivato?: string,
    public idCausaleTrasferimento?: number,
    public idLineaDiIntervento?: number,
    public idSoggettoBeneficiario?: number,
    public idTrasferimento?: number,
    public importoTrasferimento?: number,
  ) { }
}
