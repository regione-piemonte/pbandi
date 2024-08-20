/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ElencoRichieste {
  constructor(
    public richiedente: any,
    public codiceFiscale: any,
    public tipoRichiesta: any,
    public statoRichiesta: any,
    public dataRichiesta: any,
    public codiceBando: any,
    public numeroDomanda: any,
    public nag: any,
    public denominazione: any,
    public partitaIva: any,
    public modalitaRichiesta: any,
    public dataChiusura: any,
    public numeroRichiesta: any,
    public codiceProgetto: any,
    public listaDettaglio: any ,
    public ndg:any,
  ) { }
}
