/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export class SoggettoTrasferimentoRequest {
  constructor(
    public cfBeneficiario?: string,
    public denominazioneBeneficiario?: string,
    public idSoggettoBeneficiario?: number,
  ) { }
}
