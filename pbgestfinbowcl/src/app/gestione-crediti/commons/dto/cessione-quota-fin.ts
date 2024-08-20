/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CessioneQuotaDTO {

  public idUtente: string;
  public idProgetto: string;

  public idCurrentRecord: string;
  public impCessQuotaCap: number;
  public impCessOneri: number;
  public impCessInterMora: number;
  public impTotCess: number;
  public dataCessione: Date;
  public corrispettivoCess: number;
  public nominativoCess: string;
  public statoCess: string;
  public note: string;

  public stor_dataInizio: string;
  public stor_dataFine: string;
  public stor_dataInserimento: string;
  public stor_istruttore: string;

  public listStati: Array<string>;
}

