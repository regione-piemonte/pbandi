/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PianoAmmortamentoDTO {

  /* TENETE AGGIORNATA LA CLASSE MANNAGGIA A CR**** */

  public capitale: number;
  public causale: string;
  public dataScadenza: Date;
  public debitoResiduo: number;
  public debitoResiduoIniziale: number;
  public ibanFondo: string;
  public idAgevolazione: number;
  public importoAgevolazione: number;
  public importoBanca: number;
  public interessi: number;
  public numRata: number;
  public statoRata: number;
  public tipoRata: string;

  
  /*// OLD
  public nRata: number;
  //public dataScadenza: Date;
  public tipoRimborso: string;
  public importoCapitale: number;
  public importoInteressi: number;
  //public debitoResiduo: number;

  public ibanFondo: string;
  public idAgevolazione: number;
  public importoAgevolazione: number;
  public debitoResiduoIniziale: number;
  public numRata: number;
  public capitale: number;
  public interessi: number;
  public tipoRata: string;
  public dataScadenza: Date;
  public statoRata: number;
  public debitoResiduo: number;*/

}

