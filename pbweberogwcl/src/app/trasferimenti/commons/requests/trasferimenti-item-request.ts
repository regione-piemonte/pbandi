/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export class TrasferimentiItemRequest {
  constructor(
    public cfBeneficiario?: string,
    public codiceTrasferimento?: string,
    public denominazioneBeneficiario?: string,
    public descCausaleTrasferimento?: string,
    public descPubblicoPrivato?: string,
    public dtTrasferimento?: string,
    public flagPubblicoPrivato?: string,
    public idCausaleTrasferimento?: number,
    public idSoggettoBeneficiario?: number,
    public idTrasferimento?: number,
    public importoTrasferimento?: number,
    public isEliminabile?: boolean,
    public isModificabile?: boolean,
    public lnkDettaglio?: string,
    public lnkElimina?: string,
    public lnkModifica?: string,
  ) { }
}
