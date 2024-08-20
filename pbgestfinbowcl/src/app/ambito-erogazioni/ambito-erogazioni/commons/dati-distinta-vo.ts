/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DatiDistintaVO {

  public idTipoDistinta: string;
  public idModalitaAgevolazione: string;
  public descrizione: string;
  public idEstremiBancari: string;

  public listaIdProposteErogazione?: Array<string>;

}
