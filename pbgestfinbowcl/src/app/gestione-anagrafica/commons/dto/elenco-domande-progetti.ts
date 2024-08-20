/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ElencoDomandeProgettiSec {
  constructor(
    public codiceBando: any,
    public numeroDomanda: any,
    public descStatoDomanda: string,
    public descProgetto: any,
    public descStatoProgetto: string,
    public legaleRappresentante: string
  ) { }
}
