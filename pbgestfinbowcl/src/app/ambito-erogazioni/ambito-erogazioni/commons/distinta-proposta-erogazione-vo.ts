/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DistintaPropostaErogazioneVO {

  public idPropostaErogazione: string;
  public importoLordo: string;
  public importoIres: string;
  public importoNetto: string;
  public dataEsitoDS: Date;

  public idProgetto: string;
  public codiceVisualizzato: string;
  public codiceFondoFinpisProgetto: string;
  public dataConcessione: Date;

  public denominazione: string;

  public idSoggetto: string;
  public codiceFiscaleSoggetto: string;

  public progSuggettoProgetto: string;
  public idEstremiBancari: string;

  public iban: string;

  public idSede: string;
  public partitaIva: string;

  public verificaPosizione: boolean;
  public flagFinistr: string;
  public flagDistinta: string;

  public abilitata: boolean;

}
