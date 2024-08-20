/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RicercaDistintaPropErogVO  {

  public idDistinta: string;
  public descrizioneDistinta: string;
  public idTipoDistinta: string;
  public descTipoDistinta: string;
  public descBreveTipoDistinta: string;
  public utenteCreazione: string;
  public dataCreazioneDistinta: string;
  public descStatoDistinta: string;
  public statoIterAutorizzativo: string;

  //new
  public idStatoDistinta?: string;
  public propostaErogazioneExcelVOS?: Array<string>;

}
