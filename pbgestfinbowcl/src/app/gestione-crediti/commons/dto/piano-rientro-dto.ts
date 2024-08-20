/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class PianoRientroDTO {

  public operation: string;

  public idUtente: string;
  public idProgetto: string;

  public idCurrentRecord: number;
  public idEsito: number;
  public esito: string;
  public dataEsito: Date;
  public utenteModifica: string;
  public dataProposta: Date;
  public importoCapitale: number;
  public importoAgevolazione: number;
  public idRecupero: number;
  public recupero: string;
  public note: string;
  public dataInserimento: string;

  public ID_PROGETTO: any;
  public DT_INIZIO_VALIDITA: String;
  public DT_FINE_VALIDITA: string;
  public ID_UTENTE_INS: any;
  public ID_UTENTE_AGG: any;
  public DT_INSERIMENTO: string;
  public DT_AGGIORNAMENTO: string;

  public esiti: Array<string>;
  public recuperi: Array<string>;
}
