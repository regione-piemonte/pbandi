/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DettaglioFinanziamentoErogato } from "./dettaglio-finanziamento-erogato";

export class FinanziamentoErogato {

  /* TENETE AGGIORNATO L'OGGETTO 'BeneficiarioCreditiVO' SE FATE UNA MODIFICA FE, MANNAGGIA A CRI*** */

  public idSoggetto: number;

  public ndg: string;
  public idTipoSoggetto: number;
  public denominazione: string;
  public codiceFiscale: string;
  public partitaIva: string;
  public idFormaGiuridica: number;
  public formaGiuridica: string;
  public idStatoAzienda: number;
  public statoAzienda: string;
  public idRating: number;
  public rating: string;
  public dataProcedura: Date;
  
  public listaDettagli: Array<DettaglioFinanziamentoErogato>;
  
}
