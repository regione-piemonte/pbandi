/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SchedaClienteDettaglioErogato {

  public idDomanda: number;
  public idSoggetto: number;
  public idProgetto: number;

  public idAgevolazione: number;
  public idAgevolazioneRif: number;
  public descBreveTipoAgevolazione: string;
  public descTipoAgevolazione: string;
  public numErogazioni: number; // Serve per logica label e Causale
  public dispTipoAgevolazione: string;
  public dispLabelCausale: string;
  public idCausale: number;
  public descCausale: string;
  public descBreveCausale: string;
  public dataErogazione: Date;
  public totFin: number;
  public totBan: number;
  
}
