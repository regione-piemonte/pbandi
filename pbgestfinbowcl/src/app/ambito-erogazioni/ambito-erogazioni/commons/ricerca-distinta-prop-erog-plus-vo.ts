/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RicercaDistintaPropErogPlusVO  {

  public idDistinta: string;
  public descrizioneDistinta: string;
  public idTipoDistinta: string;
  public descTipoDistinta: string;
  public descBreveTipoDistinta: string;
  public utenteCreazione: string;
  public dataCreazioneDistinta: string;
  public descStatoDistinta: string;

  public statoIterAutorizzativo: string;

  public idPropostaErogazione: string;

  public idProgetto: string;
  public codiceVisualizzato: string;
  public codiceFondoFinpis: string;

  public idSoggetto: string;
  public denominazione: string;

  public codiceFiscaleSoggetto: string; //CH

  public idSede: string;
  public partitaIva: string;  //CH

  public progrSoggettoProgetto: string;
  public idEstremiBancari: string;

  public iban: string;  //CH
  public codiceFondoFinpisBanca: string;

  public importoLordo: string;
  public importoNetto: string;

  public dataContabileErogazione: Date;
  public importoErogato: string;

}
