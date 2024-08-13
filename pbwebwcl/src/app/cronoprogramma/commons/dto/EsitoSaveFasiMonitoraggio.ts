/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoSaveFasiMonitoraggio {
  constructor(
    public messaggi: Array<string>,
    public successo: boolean
  ) { }
}
