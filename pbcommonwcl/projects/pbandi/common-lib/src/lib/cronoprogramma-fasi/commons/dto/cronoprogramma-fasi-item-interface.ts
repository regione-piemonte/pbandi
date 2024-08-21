/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface CronoprogrammaFasiItemInterface {


  idIter: number,
  descIter: string,
  idFaseMonit: number,
  descFaseMonit: string,
  dataLimite: any,
  dataPrevista: any,
  dataEffettiva: any,
  motivoScostamento: string,
  idDichiarazioneSpesa: number,
  edit: boolean,
  faseIterAttiva: boolean,
  flagFaseChiusa: number


}


