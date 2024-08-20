/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class SchedaClienteHistory
{
  constructor
  (
    // STORICO STATO AZIENDA
    public staAz_statoAzienda: string,
    public staAz_dataInizio: string,
    public staAz_dataFine: string,
    public staAz_utenteModifica: string,
    
    // STORICO STATO CREDITO FINPIEMONTE
    public creFin_statoCredito: string,
    public creFin_dataModifica: string,
    public creFin_utenteModifica: string,

    // STORICO RATING
    public rat_rating: string,
    public rat_provider: string,
    public rat_dataClass: string,
    public rat_utenteModifica: string,

    // BANCA BENEFICIARIO
    public banBen_banca: string,
    public banBen_dataModifica: string,
    public banBen_motivazione: string,
    public banBen_soggettoTerzo: string,
    public banBen_utenteModifica: string,

  )
  {

  }
}
