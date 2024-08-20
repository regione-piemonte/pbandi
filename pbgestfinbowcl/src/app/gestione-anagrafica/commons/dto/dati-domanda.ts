/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DatiDomanda {
    constructor(
    //STATO DOMANDA
      public numeroDomanda: any,
      public statoDomanda: any,
      public dataPresentazioneDomanda: any,
    //DOCUMENTO IDENTITA'
      public documentoIdentita: any,
      public idTipoDocumentoIdentita: any,
      public numeroDocumento: any,
      public dataRilascio: Date,
      public enteRilascio: any,
      public scadenzaDocumento: Date,
      //RECAPITI
      public telefono: any,
      public fax: any,
      public email: any,
      public pec: any,
      //CONTO CORRENTE
      public numeroConto: any,
      public iban: any,
      //BANCA DI APPOGGIO
      public banca: any,
      public abi: any,
      public cab: any,
      public idProgetto:any
      // //CONSULENTI
      // public denominazione: any,
      // public ruoloSoggettoCorrelato: any,
      // public tipoSoggetto: any,
      // public codiceFiscale: any,
      // //SEDE LEGALE
      // public indirizzo: any,
      // public partitaIva: any,
      // public comune: any,
      // public provincia: any,
      // public regione: any,
      // public capSedeLegale: any,
      // public nazioneSedeLegale: any,
      // public codiceAteco: any,
      // public descrizioneAteco: any,
    ) { }
  }