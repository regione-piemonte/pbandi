/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class LiberazioneBancaDTO {

  public idUtente: string;
  public idProgetto: string;

  public idCurrentRecord: string;
  public dataLiberazione: Date;
  public motivazione: string;
  public bancaLiberata: string;
  public note: string;

  public storData: string;
  public storIstruttore: string;

  public motivazioni: Array<string>;

}
