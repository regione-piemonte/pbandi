/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EstrattoContoDTO {

  /* TENETE AGGIORNATA LA CLASSE MANNAGGIA A CR**** */

  public debitoResiduoIniziale: number;
  public codCausale: string;
  public desCausale: string;
  public dataContabile: Date;
  public dataScadenza: Date;
  public dataValuta: Date;
  public idAgevolazione: number;
  public importoCapitale: number;
  public importoInteressi: number;
  public importoMora: number;
  public importoOneri: number;
  public importoTotale: number;
  public numRata: number;
  public segno: string;

  public dispAgevolazione: string; // Non nella classe originaria, solo per visualizzazione

}

