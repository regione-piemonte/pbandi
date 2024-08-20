/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PropostaErogazioneVO {

  public idProposta: string;
  public idProgetto: string;
  public codiceProgetto: string;
  public beneficiario: string;
  public importoLordo: number;
  public importoIres: number;
  public importoNetto: number;
  public dataConcessione: Date;
  public controlliPreErogazione: string;
  public flagFinistr: string;
  public statoRichiestaDurc: string;
  public statoRichiestaAntimafia: string;

  /*
  public titoloBando: string;
  public idModalitaAgevolazione: string;
  public descrizioneModalitaAgevolazione: string;
  public dataControlli: Date;

  public flagPubblicoPrivato: string;
  public idSoggetto: number;    //id beneficiario
  public numeroDomanda: string;
  public codiceFiscale: string;
  public codiceBando: string;
  */

}
