/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class ConsoleMonitoraggioServiziDTO{

  constructor(
   public esito: string,
   public  codiceErrore: string,
	 public messaggioErrore : string,
	 public dataInizioChiamata: Date,
   public  dataFineChiamata:Date,
	 public nomeServizio: string,
   public categoriaServizio: string
  ){

  }


  public static createConsoleMonitoraggioServiziDTO(): ConsoleMonitoraggioServiziDTO{
    var filter = new ConsoleMonitoraggioServiziDTO(undefined, undefined, undefined, undefined, undefined, undefined,undefined)
    return filter

}
 }
