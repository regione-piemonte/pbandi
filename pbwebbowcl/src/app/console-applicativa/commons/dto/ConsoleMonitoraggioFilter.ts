/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ConsoleMonitoraggioFilter {
 constructor(
  public dataInserimentoDal : Date,
  public dataInserimentoAl :  Date,
  public oraInserimentoDal: string,
  public oraInserimentoAl : string,
  public codiceErrore: string,
  public idServizio: string
 )
 { }



 public static createEmptyConsoleMonitoraggioFilter(): ConsoleMonitoraggioFilter{
  var filter = new ConsoleMonitoraggioFilter(undefined, undefined,undefined ,undefined,undefined,undefined)
  return filter

}
}
