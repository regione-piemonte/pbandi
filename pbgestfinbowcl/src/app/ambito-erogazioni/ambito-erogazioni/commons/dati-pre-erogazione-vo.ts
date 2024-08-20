/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { InterventoSostitutivoVO } from "./intervento-sostitutivo-vo";

export class DatiPreErogazioneVO {
  public importoLordo: number;
  public importoBeneficiario: number;
  public ibanBeneficiario: string;
  public titoloBando: string;
  public codiceProgetto: string;
  public idModalitaAgevolazioneRif: number;
  public descrizioneModalitaAgevolazione: string;
  public beneficiario: string;
  public dataControlli: Date;
  public flagPubblicoPrivato: string;
  public idSoggetto: number;
  public idProposta: string;
  public numeroDomanda: string;
  public codiceFiscale: string;
  public codiceBando: string;
  public idProgetto: string;
  public iterEsitoValidazioneConcluso: boolean;
  public iterEsitoValidazioneInCorso: boolean;
  public iterCommunicazioneInterventoConcluso: boolean;
  public iterCommunicazioneInterventoInCorso: boolean;
  public distintaCreata: boolean;
  public verificaAntimafia: boolean;
  public flagFinistr: boolean;
  public importoDaErogare: number;

  public listaInterventi: InterventoSostitutivoVO[];

}
