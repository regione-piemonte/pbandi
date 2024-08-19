/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SedeProgettoDTO {
  constructor(
    public cap: string,
    public civico: number,
    public codIstatComune: string,
    public descBreveTipoSede: string,
    public descComune: string,
    public descIndirizzo: string,
    public descNazione: string,
    public descProvincia: string,
    public descRegione: string,
    public descTipoSede: string,
    public email: string,
    public fax: string,
    public flagSedeAmministrativa: string,
    public idComune: number,
    public idIndirizzo: number,
    public idNazione: number,
    public idProgetto: number,
    public idProvincia: number,
    public idRecapiti: number,
    public idRegione: number,
    public idSede: number,
    public idSoggettoBeneficiario: number,
    public idTipoSede: number,
    public partitaIva: string,
    public progrSoggettoProgettoSede: number,
    public telefono: string
  ) { }
}
