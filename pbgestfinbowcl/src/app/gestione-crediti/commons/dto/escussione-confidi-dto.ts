/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class EscussioneConfidiDTO {

  public idUtente: string;
  public idProgetto: string;

  public idCurrentRecord: string;
  public nominativo: string;
  public dataEscussione: Date;
  public dataPagamento: Date;
  public idGaranzia: number;
  public garanzia: string;
  public percGaranzia: number;
  public note: string;

  public dataInserimento: string;
  public istruttore: string;

  public listGaranzie: Array<string>;

}
