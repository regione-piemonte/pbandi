/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CronoprogrammaFasiItemInterface } from "./cronoprogramma-fasi-item-interface"
import { CronoprogrammaListFasiItemAllegati } from "./cronoprogramma-list-fasi-item-allegati"
import { CronoprogrammaListFasiItemAllegatiInterface } from "./cronoprogramma-list-fasi-item-allegati-interface"

export class CronoprogrammaFasiItem implements CronoprogrammaFasiItemInterface {

  public idIter: number;
  public descIter: string;
  public idFaseMonit: number;
  public descFaseMonit: string;
  public dataLimite: Date;
  public dataPrevista: Date;
  public dataEffettiva: Date;
  public motivoScostamento: string;
  public idDichiarazioneSpesa: number;
  public idTipoDichiarazSpesa: number;
  public edit: boolean;

  public errorDataPrevista: string;
  public errorDataEffettiva: string;
  public warnDataEffettiva: string;
  public requireMotivoScostamento: boolean;
  public faseIterAttiva: boolean;
  public flagFaseChiusa: number;

  constructor(input: CronoprogrammaFasiItemInterface) {

    this.idIter = input.idIter
    this.descIter = input.descIter
    this.idFaseMonit = input.idFaseMonit
    this.descFaseMonit = input.descFaseMonit
    this.dataLimite = input.dataLimite ? new Date(input.dataLimite) : null
    this.dataPrevista = input.dataPrevista ? new Date(input.dataPrevista) : null
    this.dataEffettiva = input.dataEffettiva ? new Date(input.dataEffettiva) : null
    this.motivoScostamento = input.motivoScostamento
    this.faseIterAttiva = input.faseIterAttiva
    this.edit = false;
    this.errorDataPrevista = undefined;
    this.errorDataEffettiva = undefined;
    this.requireMotivoScostamento = undefined;
    this.idDichiarazioneSpesa = input.idDichiarazioneSpesa;
    if (this.dataLimite && this.dataEffettiva?.getTime() > this.dataLimite?.getTime()) {
      this.requireMotivoScostamento = true;
    }
    this.flagFaseChiusa = input.flagFaseChiusa;
  }


  public static createStructure(input: Array<CronoprogrammaListFasiItemAllegatiInterface>): Array<CronoprogrammaListFasiItemAllegati> {
    let output: Array<CronoprogrammaListFasiItemAllegati> = new Array<CronoprogrammaListFasiItemAllegati>();
    let temp: CronoprogrammaListFasiItemAllegati;

    let i = 0;
    let j = 0;
    while (i < input.length) {
      j = 0;
      let row = input[i];
      temp = new CronoprogrammaListFasiItemAllegati();

      while (j < row.fasiItemList.length) {
        temp.fasiItemList.push(new CronoprogrammaFasiItem(row.fasiItemList[j]));

        j++;
      }
      temp.allegatiList = input[i].allegatiList;


      output.push(temp);
      i++;
    }

    return output;
  }

  public static trasfornToInterface(input: CronoprogrammaFasiItem): CronoprogrammaFasiItemInterface {

    let ms: string = undefined;
    if (input.requireMotivoScostamento) {
      ms = input.motivoScostamento
    }

    let base: CronoprogrammaFasiItemInterface = {
      idIter: input.idIter,
      descIter: input.descIter,
      idFaseMonit: input.idFaseMonit,
      descFaseMonit: input.descFaseMonit,
      dataLimite: input.dataLimite,
      dataPrevista: input.dataPrevista,
      dataEffettiva: input.dataEffettiva,
      motivoScostamento: ms,
      idDichiarazioneSpesa: input.idDichiarazioneSpesa,
      edit: input.edit,
      faseIterAttiva: input.faseIterAttiva,
      flagFaseChiusa: input.flagFaseChiusa,
    };

    return base;
  }






}