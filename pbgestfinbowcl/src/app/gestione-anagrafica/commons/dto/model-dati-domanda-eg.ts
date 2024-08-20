/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DatiDomandaEG {
  constructor(
    //STATO DOMANDA
    public numeroDomanda: any,
    public statoDomanda: any,
    public dataPresentazioneDomanda: any,
    //SEDE INTERVENTO:
    public descIndirizzo: any,
    public partitaIva: any,
    public descComune: any,
    public descProvincia: any,
    public cap: any,
    public descRegione: any,
    public descNazione: any,
    public idComune: any, 
    public idProvincia: any,
    public idRegione: any,
    public idNazione: any, 
    //SEDE AMMINISTRATIVA:
    public flagSedeAmm: any,
    public descIndirizzoSedeAmm: any,
    public descComuneSedeAmm: any,
    public descProvinciaSedeAmm: any,
    public capSedeAmm: any,
    public descRegioneSedeAmm: any,
    public descNazioneSedeAmm: any,
    public idComuneSedeAmm: any, 
    public idProvinciaSedeAmm: any,
    public idRegioneSedeAmm: any,
    public idNazioneSedeAmm: any, 
    //RECAPITI:
    public idRecapiti: any, 
    public telefono: any,
    public fax: any,
    public email: any,
    public pec: any,
    //CONTO CORRENTE:
    public banca: any,
    public iban: any,
    public idEstremiBancari: any, 
    public idIndirizzo: any,
    
    public idProgetto: any,     
  ) { }
}
