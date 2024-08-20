/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class IscrizioneRuoloDTO {

  public idUtente: string;
  public idProgetto: string;

  public idCurrentRecord: string;
  public dataRichiestaIscrizione: string; 
  public numProtocollo: string;
  public dataRichiestaDiscarico: string;
  public numProtocolloDiscarico: string;
  public dataIscrizioneRuolo: string;
  public dataDiscarico: string;
  public numProtoDiscReg: string;
  public dataRichiestaSospensione: string;
  public numProtoSosp: string;
  public capitaleRuolo: number;
  public agevolazioneRuolo: number;
  public dataIscrizione: string;
  public numProtoReg: string;
  public tipoPagamento: string;
  public note: string;

  public stor_dataInizio: string;
  public stor_dataFine: string;
  public stor_dataInserimento: string;
  public istruttore: string;
}

